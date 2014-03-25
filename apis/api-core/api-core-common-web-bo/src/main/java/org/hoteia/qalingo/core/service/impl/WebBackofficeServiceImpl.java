/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import org.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.DeliveryMethodService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.UserService;
import org.hoteia.qalingo.core.service.WarehouseService;
import org.hoteia.qalingo.core.service.WebBackofficeService;
import org.hoteia.qalingo.core.web.mvc.form.AssetForm;
import org.hoteia.qalingo.core.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.core.web.mvc.form.DeliveryMethodForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingValueForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.core.web.mvc.form.RetailerForm;
import org.hoteia.qalingo.core.web.mvc.form.UserForm;
import org.hoteia.qalingo.core.web.mvc.form.WarehouseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("webBackofficeService")
@Transactional
public class WebBackofficeServiceImpl implements WebBackofficeService {

    @Autowired
    protected UserService userService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected RetailerService retailerService;

    @Autowired
    protected WarehouseService warehouseService;
    
    @Autowired
    protected DeliveryMethodService deliveryMethodService;

    
    @Autowired
    protected AttributeService attributeService;

    @Autowired
    protected EngineSettingService engineSettingService;
	   
	public void updateUser(final User user, final UserForm userForm) {
		user.setLogin(userForm.getLogin());
		user.setLastname(userForm.getLastname());
		user.setFirstname(userForm.getFirstname());
		user.setEmail(userForm.getEmail());
		userService.saveOrUpdateUser(user);
	}
	
	public void createCatalogCategory(final MarketArea currentMarketArea, final Localization currentLocalization, final CatalogCategoryMaster parentCatalogCategory, final CatalogCategoryMaster catalogCategory, final CatalogCategoryForm catalogCategoryForm) throws UniqueConstraintCodeCategoryException {
		String catalogCategoryCode = catalogCategoryForm.getCode();
		catalogCategory.setBusinessName(catalogCategoryForm.getName());
		catalogCategory.setCode(catalogCategoryForm.getCode());
		catalogCategory.setDescription(catalogCategoryForm.getDescription());
		catalogCategory.setDefaultParentCatalogCategory(parentCatalogCategory);
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getMarketAreaAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getMarketAreaAttributes();
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				String value = attributes.get(attributeKey);
				if(StringUtils.isNotEmpty(value)) {
					catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId()).add(buildCatalogCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
				}
			}
		}
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getGlobalAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getGlobalAttributes();
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				String value = attributes.get(attributeKey);
				if(StringUtils.isNotEmpty(value)) {
					catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId()).add(buildCatalogCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
				}
			}
		}
		
		try {
			catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
			
			if(parentCatalogCategory != null) {
				if(!parentCatalogCategory.getCatalogCategories().contains(catalogCategory)) {
					// PARENT DOESN'T CONTAIN THE NEW CATEGORY - ADD IT IN THE MANY TO MANY
					CatalogCategoryMaster reloadedCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode);
					parentCatalogCategory.getCatalogCategories().add(reloadedCatalogCategory);
					catalogCategoryService.saveOrUpdateCatalogCategory(parentCatalogCategory);
				}
			}
			
		} catch (PersistenceException e) {
			if(e.getMessage().contains("ConstraintViolationException")) {
				throw new UniqueConstraintCodeCategoryException();
			} else {
				throw new UniqueConstraintCodeCategoryException();
			}
		}
		
	}
	
	public void updateCatalogCategory(final MarketArea currentMarketArea, final Retailer currentRetailer, final Localization currentLocalization, final CatalogCategoryMaster catalogCategory, final CatalogCategoryForm catalogCategoryForm) {
		catalogCategory.setBusinessName(catalogCategoryForm.getName());
		catalogCategory.setCode(catalogCategoryForm.getCode());
		catalogCategory.setDescription(catalogCategoryForm.getDescription());
		if(StringUtils.isNotEmpty(catalogCategoryForm.getDefaultParentCategoryCode())) {
			if(catalogCategory.getDefaultParentCatalogCategory() == null
					|| (catalogCategory.getDefaultParentCatalogCategory() != null
						&& !catalogCategoryForm.getDefaultParentCategoryCode().equalsIgnoreCase(catalogCategory.getDefaultParentCatalogCategory().getCode()))) {
				final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), catalogCategoryForm.getDefaultParentCategoryCode());
				catalogCategory.setDefaultParentCatalogCategory(parentCatalogCategory);
			}
		}

		if(catalogCategoryForm != null
				&& catalogCategoryForm.getGlobalAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getGlobalAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryMasterAttribute> iteratorCategoryGlobalAttributes = catalogCategory.getCatalogCategoryGlobalAttributes().iterator(); iteratorCategoryGlobalAttributes.hasNext();) {
					CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iteratorCategoryGlobalAttributes.next();
					if(catalogCategoryMasterAttribute.getAttributeDefinition().getCode().equals(attributeKey)) {
						updateCatalogCategoryMasterAttribute(catalogCategoryMasterAttribute, catalogCategoryForm.getGlobalAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist) {
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)) {
						catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId()).add(buildCatalogCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
					}
				}
			}
		}
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getMarketAreaAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getMarketAreaAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryMasterAttribute> iteratorCategoryMarketAttributes = catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId()).iterator(); iteratorCategoryMarketAttributes.hasNext();) {
					CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iteratorCategoryMarketAttributes.next();
					if(catalogCategoryMasterAttribute.getAttributeDefinition().getCode().equals(attributeKey)) {
						updateCatalogCategoryMasterAttribute(catalogCategoryMasterAttribute, catalogCategoryForm.getMarketAreaAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist) {
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)) {
						catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId()).add(buildCatalogCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
					}
				}
			}
		}
		
		catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
	}
	
	public void createCatalogCategory(final MarketArea currentMarketArea, final Localization currentLocalization, final CatalogCategoryVirtual catalogCategory, final CatalogCategoryForm catalogCategoryForm) {
		catalogCategory.setBusinessName(catalogCategoryForm.getName());
		catalogCategory.setCode(catalogCategoryForm.getCode());
		catalogCategory.setDescription(catalogCategoryForm.getDescription());
		catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
	}
	
	public void updateCatalogCategory(final MarketArea currentMarketArea, final Retailer currentRetailer, final Localization currentLocalization, final CatalogCategoryVirtual catalogCategory, final CatalogCategoryForm catalogCategoryForm) {
		catalogCategory.setBusinessName(catalogCategoryForm.getName());
		catalogCategory.setCode(catalogCategoryForm.getCode());
		catalogCategory.setDescription(catalogCategoryForm.getDescription());
		if(StringUtils.isNotEmpty(catalogCategoryForm.getDefaultParentCategoryCode())) {
			if(catalogCategory.getDefaultParentCatalogCategory() == null
					|| (catalogCategory.getDefaultParentCatalogCategory() != null
						&& !catalogCategoryForm.getDefaultParentCategoryCode().equalsIgnoreCase(catalogCategory.getDefaultParentCatalogCategory().getCode()))) {
				final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), catalogCategoryForm.getDefaultParentCategoryCode());
				catalogCategory.setDefaultParentCatalogCategory(parentCatalogCategory);
			}
		}
		
//		for (Iterator<String> iterator = catalogCategoryForm.getGlobalAttributes().keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			for (Iterator<CatalogCategoryVirtualAttribute> iteratorGlobalAttributes = catalogCategory.getCatalogCategoryGlobalAttributes().iterator(); iteratorGlobalAttributes.hasNext();) {
//				CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorGlobalAttributes.next();
//				if(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(key)) {
//					updateCatalogCategoryVirtualAttribute(catalogCategoryVirtualAttribute, catalogCategoryForm.getGlobalAttributes().get(key));
//				}
//			}
//		}
//			
//		for (Iterator<String> iterator = catalogCategoryForm.getMarketAreaAttributes().keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			for (Iterator<CatalogCategoryVirtualAttribute> iteratorMarketAreaAttribute = catalogCategory.getCatalogCategoryMarketAreaAttributes().iterator(); iteratorMarketAreaAttribute.hasNext();) {
//				CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorMarketAreaAttribute.next();
//				if(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(key)) {
//					updateCatalogCategoryVirtualAttribute(catalogCategoryVirtualAttribute, catalogCategoryForm.getMarketAreaAttributes().get(key));
//				}
//			}
//		}
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getGlobalAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getGlobalAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryVirtualAttribute> iteratorCategoryGlobalAttributes = catalogCategory.getCatalogCategoryGlobalAttributes().iterator(); iteratorCategoryGlobalAttributes.hasNext();) {
					CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorCategoryGlobalAttributes.next();
					if(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(attributeKey)) {
						updateCatalogCategoryVirtualAttribute(catalogCategoryVirtualAttribute, catalogCategoryForm.getGlobalAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist) {
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)) {
						catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId()).add(buildCatalogCategoryVirtualAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
					}
				}
			}
		}
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getMarketAreaAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getMarketAreaAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryVirtualAttribute> iteratorCategoryMarketAttributes = catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId()).iterator(); iteratorCategoryMarketAttributes.hasNext();) {
					CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorCategoryMarketAttributes.next();
					if(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(attributeKey)) {
						updateCatalogCategoryVirtualAttribute(catalogCategoryVirtualAttribute, catalogCategoryForm.getMarketAreaAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist) {
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)) {
						catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId()).add(buildCatalogCategoryVirtualAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
					}
				}
			}
		}
		
		catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
	}

	private void updateCatalogCategoryMasterAttribute(final CatalogCategoryMasterAttribute catalogCategoryMasterAttribute, final String attributeValue) {
		AttributeDefinition attributeDefinition = catalogCategoryMasterAttribute.getAttributeDefinition();
		if(AttributeDefinition.ATTRIBUTE_TYPE_STRING == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setStringValue(attributeValue);
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setDoubleValue(Double.parseDouble(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_FLOAT == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setFloatValue(Float.parseFloat(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_INTEGER == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setIntegerValue(Integer.parseInt(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BLOB == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setBlobValue(attributeValue.getBytes());
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setBooleanValue(BooleanUtils.toBooleanObject(attributeValue));
		}
	}
	
	private CatalogCategoryMasterAttribute buildCatalogCategoryMasterAttribute(final MarketArea currentMarketArea, final Localization currentLocalization, final String attributeKey, final String attributeValue, boolean isGlobal) {
		
		//TODO : denis : 20130125 : add cache
		
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);
		CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = new CatalogCategoryMasterAttribute();
		catalogCategoryMasterAttribute.setAttributeDefinition(attributeDefinition);
		catalogCategoryMasterAttribute.setLocalizationCode(currentLocalization.getCode());
		catalogCategoryMasterAttribute.setMarketAreaId(currentMarketArea.getId());
		catalogCategoryMasterAttribute.setStartDate(new Date());
		
		if(AttributeDefinition.ATTRIBUTE_TYPE_STRING == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setStringValue(attributeValue);
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setDoubleValue(Double.parseDouble(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_FLOAT == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setFloatValue(Float.parseFloat(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_INTEGER == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setIntegerValue(Integer.parseInt(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BLOB == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setBlobValue(attributeValue.getBytes());
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN == attributeDefinition.getAttributeType()) {
			catalogCategoryMasterAttribute.setBooleanValue(BooleanUtils.toBooleanObject(attributeValue));
		}
		return catalogCategoryMasterAttribute;
	}
	
	private void updateCatalogCategoryVirtualAttribute(final CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute, final String attributeValue) {
		AttributeDefinition attributeDefinition = catalogCategoryVirtualAttribute.getAttributeDefinition();
		if(AttributeDefinition.ATTRIBUTE_TYPE_STRING == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setStringValue(attributeValue);
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setDoubleValue(Double.parseDouble(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_FLOAT == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setFloatValue(Float.parseFloat(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_INTEGER == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setIntegerValue(Integer.parseInt(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BLOB == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setBlobValue(attributeValue.getBytes());
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setBooleanValue(BooleanUtils.toBooleanObject(attributeValue));
		}
	}
	
	private CatalogCategoryVirtualAttribute buildCatalogCategoryVirtualAttribute(final MarketArea currentMarketArea, final Localization currentLocalization, final String attributeKey, final String attributeValue, boolean isGlobal) {
		
		//TODO : denis : 20130125 : add cache
		
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);
		CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = new CatalogCategoryVirtualAttribute();
		catalogCategoryVirtualAttribute.setAttributeDefinition(attributeDefinition);
		catalogCategoryVirtualAttribute.setLocalizationCode(currentLocalization.getCode());
		catalogCategoryVirtualAttribute.setMarketAreaId(currentMarketArea.getId());
		catalogCategoryVirtualAttribute.setStartDate(new Date());
		
		if(AttributeDefinition.ATTRIBUTE_TYPE_STRING == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setStringValue(attributeValue);
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setDoubleValue(Double.parseDouble(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_FLOAT == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setFloatValue(Float.parseFloat(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_INTEGER == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setIntegerValue(Integer.parseInt(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BLOB == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setBlobValue(attributeValue.getBytes());
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN == attributeDefinition.getAttributeType()) {
			catalogCategoryVirtualAttribute.setBooleanValue(BooleanUtils.toBooleanObject(attributeValue));
		}
		return catalogCategoryVirtualAttribute;
	}
	
	public void updateProductMarketing(final ProductMarketing productMarketing, final ProductMarketingForm productMarketingForm) {
		productMarketing.setBusinessName(productMarketingForm.getName());
		productMarketing.setCode(productMarketingForm.getCode());
		productMarketing.setDescription(productMarketingForm.getDescription());
		productService.saveOrUpdateProductMarketing(productMarketing);
	}
	
	public void createProductMarketing(final ProductMarketing productMarketing, final ProductMarketingForm productMarketingForm) {
		productMarketing.setBusinessName(productMarketingForm.getName());
		productMarketing.setCode(productMarketingForm.getCode());
		productMarketing.setDescription(productMarketingForm.getDescription());
		productService.saveOrUpdateProductMarketing(productMarketing);
	}
	
	public void updateProductSku(final ProductSku productSku, final ProductSkuForm productSkuForm) {
		productSku.setBusinessName(productSkuForm.getName());
		productSku.setCode(productSkuForm.getCode());
		productSku.setDescription(productSkuForm.getDescription());
		productService.saveOrUpdateProductSku(productSku);
	}
	
	public void createProductSku(final ProductSku productSku, final ProductSkuForm productSkuForm) {
		productSku.setBusinessName(productSkuForm.getName());
		productSku.setCode(productSkuForm.getCode());
		productSku.setDescription(productSkuForm.getDescription());
		productService.saveOrUpdateProductSku(productSku);
	}
	
	public void updateProductMarketingAsset(final Asset asset, final AssetForm assetForm) {
		asset.setName(assetForm.getName());
		asset.setCode(assetForm.getCode());
		asset.setDescription(assetForm.getDescription());

//		asset.setType(assetForm.getType);
//		private boolean isDefault;
//		private boolean isGlobal;
//		private Integer ordering;
//		private Long marketAreaId;
		
		productService.saveOrUpdateProductMarketingAsset(asset);
	}
	
	public void createProductMarketingAsset(final Asset asset, final AssetForm assetForm) {
		asset.setName(assetForm.getName());
		asset.setCode(assetForm.getCode());
		asset.setDescription(assetForm.getDescription());
		
		// ...
		
		productService.saveOrUpdateProductMarketingAsset(asset);
	}
	
	public void createOrUpdateRetailer(final Retailer retailer, final RetailerForm retailerForm) {
		retailer.setCode(retailerForm.getCode());
		retailer.setName(retailerForm.getName());
		retailer.setDescription(retailerForm.getDescription());
		
		RetailerAddress retailerAddress = retailer.getDefaultAddress();
        if (retailerAddress == null) {
            retailerAddress = new RetailerAddress();
            retailer.getAddresses().add(retailerAddress);
        }
		
		retailerAddress.setAddress1(retailerForm.getAddress1());
		retailerAddress.setAddress2(retailerForm.getAddress2());
		retailerAddress.setAddressAdditionalInformation(retailerForm.getAddressAdditionalInformation());
		retailerAddress.setPostalCode(retailerForm.getPostalCode());
		retailerAddress.setCity(retailerForm.getCity());
		retailerAddress.setStateCode(retailerForm.getStateCode());
		retailerAddress.setCountryCode(retailerForm.getCountryCode());
		
		retailerAddress.setPhone(retailerForm.getPhone());
		retailerAddress.setFax(retailerForm.getFax());
		retailerAddress.setMobile(retailerForm.getMobile());
		retailerAddress.setEmail(retailerForm.getEmail());
		retailerAddress.setWebsite(retailerForm.getWebsite());
		
		retailerService.saveOrUpdateRetailer(retailer);
	}

    public void createOrUpdateWarehouse(final Warehouse warehouse, final WarehouseForm warehouseForm) {
        warehouse.setCode(warehouseForm.getCode());
        warehouse.setName(warehouseForm.getName());
        warehouse.setDescription(warehouseForm.getDescription());

        warehouseService.saveOrUpdateWarehouse(warehouse);
    }
    
    public void createOrUpdateDeliveryMethod(final DeliveryMethod deliveryMethod, final DeliveryMethodForm deliveryMethodForm) {
        deliveryMethod.setCode(deliveryMethodForm.getCode());
        deliveryMethod.setName(deliveryMethodForm.getName());
        deliveryMethod.setDescription(deliveryMethodForm.getDescription());

        deliveryMethodService.saveOrUpdateDeliveryMethod(deliveryMethod);
    }

    public void updateEngineSettingValue(final EngineSettingValue engineSettingValue, final EngineSettingValueForm engineSettingValueForm) {
        engineSettingValue.setValue(engineSettingValueForm.getValue());
        engineSettingService.saveOrUpdateEngineSettingValue(engineSettingValue);
    }

}