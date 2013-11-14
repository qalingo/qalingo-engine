/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.RetailerAddress;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import fr.hoteia.qalingo.core.service.AttributeService;
import fr.hoteia.qalingo.core.service.CatalogCategoryService;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.service.ProductSkuService;
import fr.hoteia.qalingo.core.service.RetailerService;
import fr.hoteia.qalingo.core.service.UserService;
import fr.hoteia.qalingo.web.mvc.form.AssetForm;
import fr.hoteia.qalingo.web.mvc.form.ProductCategoryForm;
import fr.hoteia.qalingo.web.mvc.form.ProductMarketingForm;
import fr.hoteia.qalingo.web.mvc.form.ProductSkuForm;
import fr.hoteia.qalingo.web.mvc.form.RetailerForm;
import fr.hoteia.qalingo.web.mvc.form.UserForm;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

@Service("webCommerceService")
@Transactional
public class WebBackofficeServiceImpl implements WebBackofficeService {

	@Autowired
	protected UserService userService;
	
	@Autowired
	protected CatalogCategoryService productCategoryService;

	@Autowired
	protected ProductMarketingService productMarketingService;
	
	@Autowired
	protected ProductSkuService productSkuService;

	@Autowired
	protected RetailerService retailerService;

	@Autowired
	protected AttributeService attributeService;
	
	public void updateUser(final User user, final UserForm userForm){
		user.setLogin(userForm.getLogin());
		user.setLastname(userForm.getLastname());
		user.setFirstname(userForm.getFirstname());
		user.setEmail(userForm.getEmail());
		userService.saveOrUpdateUser(user);
	}
	
	public void createProductCategory(final MarketArea currentMarketArea, final Localization currentLocalization, final CatalogCategoryMaster parentProductCategory, final CatalogCategoryMaster productCategory, final ProductCategoryForm productCategoryForm) throws UniqueConstraintCodeCategoryException {
		String productCategoryCode = productCategoryForm.getCode();
		productCategory.setBusinessName(productCategoryForm.getName());
		productCategory.setCode(productCategoryForm.getCode());
		productCategory.setDescription(productCategoryForm.getDescription());
		productCategory.setDefaultParentCatalogCategory(parentProductCategory);
		
		if(productCategoryForm != null
				&& productCategoryForm.getMarketAreaAttributes() != null){
			Map<String, String> attributes = productCategoryForm.getMarketAreaAttributes();
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				String value = attributes.get(attributeKey);
				if(StringUtils.isNotEmpty(value)){
					productCategory.getCatalogCategoryMarketAreaAttributes().add(buildProductCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
				}
			}
		}
		
		if(productCategoryForm != null
				&& productCategoryForm.getGlobalAttributes() != null){
			Map<String, String> attributes = productCategoryForm.getGlobalAttributes();
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				String value = attributes.get(attributeKey);
				if(StringUtils.isNotEmpty(value)){
					productCategory.getCatalogCategoryMarketAreaAttributes().add(buildProductCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
				}
			}
		}
		
		try {
			productCategoryService.saveOrUpdateCatalogCategory(productCategory);
			
			if(parentProductCategory != null){
				if(!parentProductCategory.getCatalogCategories().contains(productCategory)){
					// PARENT DOESN'T CONTAIN THE NEW CATEGORY - ADD IT IN THE MANY TO MANY
					CatalogCategoryMaster reloadedProductCategory = productCategoryService.getMasterCatalogCategoryByCode(productCategoryCode);
					parentProductCategory.getCatalogCategories().add(reloadedProductCategory);
					productCategoryService.saveOrUpdateCatalogCategory(parentProductCategory);
				}
			}
			
		} catch (PersistenceException e) {
			if(e.getMessage().contains("ConstraintViolationException")){
				throw new UniqueConstraintCodeCategoryException();
			} else {
				throw new UniqueConstraintCodeCategoryException();
			}
		}
		
	}
	
	public void updateProductCategory(final MarketArea currentMarketArea, final Retailer currentRetailer, final Localization currentLocalization, final CatalogCategoryMaster productCategory, final ProductCategoryForm productCategoryForm){
		productCategory.setBusinessName(productCategoryForm.getName());
		productCategory.setCode(productCategoryForm.getCode());
		productCategory.setDescription(productCategoryForm.getDescription());
		if(StringUtils.isNotEmpty(productCategoryForm.getDefaultParentCategoryCode())){
			if(productCategory.getDefaultParentCatalogCategory() == null
					|| (productCategory.getDefaultParentCatalogCategory() != null
						&& !productCategoryForm.getDefaultParentCategoryCode().equalsIgnoreCase(productCategory.getDefaultParentCatalogCategory().getCode()))){
				final CatalogCategoryMaster parentProductCategory = productCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryForm.getDefaultParentCategoryCode());
				productCategory.setDefaultParentCatalogCategory(parentProductCategory);
			}
		}

		if(productCategoryForm != null
				&& productCategoryForm.getGlobalAttributes() != null){
			Map<String, String> attributes = productCategoryForm.getGlobalAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryMasterAttribute> iteratorCategoryGlobalAttributes = productCategory.getCatalogCategoryGlobalAttributes().iterator(); iteratorCategoryGlobalAttributes.hasNext();) {
					CatalogCategoryMasterAttribute productCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iteratorCategoryGlobalAttributes.next();
					if(productCategoryMasterAttribute.getAttributeDefinition().getCode().equals(attributeKey)){
						updateProductCategoryMasterAttribute(productCategoryMasterAttribute, productCategoryForm.getGlobalAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist){
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)){
						productCategory.getCatalogCategoryMarketAreaAttributes().add(buildProductCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
					}
				}
			}
		}
		
		if(productCategoryForm != null
				&& productCategoryForm.getMarketAreaAttributes() != null){
			Map<String, String> attributes = productCategoryForm.getMarketAreaAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryMasterAttribute> iteratorCategoryMarketAttributes = productCategory.getCatalogCategoryMarketAreaAttributes().iterator(); iteratorCategoryMarketAttributes.hasNext();) {
					CatalogCategoryMasterAttribute productCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iteratorCategoryMarketAttributes.next();
					if(productCategoryMasterAttribute.getAttributeDefinition().getCode().equals(attributeKey)){
						updateProductCategoryMasterAttribute(productCategoryMasterAttribute, productCategoryForm.getMarketAreaAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist){
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)){
						productCategory.getCatalogCategoryMarketAreaAttributes().add(buildProductCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
					}
				}
			}
		}
		
		productCategoryService.saveOrUpdateCatalogCategory(productCategory);
	}
	
	public void createProductCategory(final MarketArea currentMarketArea, final Localization currentLocalization, final CatalogCategoryVirtual productCategory, final ProductCategoryForm productCategoryForm){
		productCategory.setBusinessName(productCategoryForm.getName());
		productCategory.setCode(productCategoryForm.getCode());
		productCategory.setDescription(productCategoryForm.getDescription());
		productCategoryService.saveOrUpdateCatalogCategory(productCategory);
	}
	
	public void updateProductCategory(final MarketArea currentMarketArea, final Retailer currentRetailer, final Localization currentLocalization, final CatalogCategoryVirtual productCategory, final ProductCategoryForm productCategoryForm){
		productCategory.setBusinessName(productCategoryForm.getName());
		productCategory.setCode(productCategoryForm.getCode());
		productCategory.setDescription(productCategoryForm.getDescription());
		if(StringUtils.isNotEmpty(productCategoryForm.getDefaultParentCategoryCode())){
			if(productCategory.getDefaultParentCatalogCategory() == null
					|| (productCategory.getDefaultParentCatalogCategory() != null
						&& !productCategoryForm.getDefaultParentCategoryCode().equalsIgnoreCase(productCategory.getDefaultParentCatalogCategory().getCode()))){
				final CatalogCategoryVirtual parentProductCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryForm.getDefaultParentCategoryCode());
				productCategory.setDefaultParentCatalogCategory(parentProductCategory);
			}
		}
		
//		for (Iterator<String> iterator = productCategoryForm.getGlobalAttributes().keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			for (Iterator<ProductCategoryVirtualAttribute> iteratorGlobalAttributes = productCategory.getProductCategoryGlobalAttributes().iterator(); iteratorGlobalAttributes.hasNext();) {
//				ProductCategoryVirtualAttribute productCategoryVirtualAttribute = (ProductCategoryVirtualAttribute) iteratorGlobalAttributes.next();
//				if(productCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(key)){
//					updateProductCategoryVirtualAttribute(productCategoryVirtualAttribute, productCategoryForm.getGlobalAttributes().get(key));
//				}
//			}
//		}
//			
//		for (Iterator<String> iterator = productCategoryForm.getMarketAreaAttributes().keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			for (Iterator<ProductCategoryVirtualAttribute> iteratorMarketAreaAttribute = productCategory.getProductCategoryMarketAreaAttributes().iterator(); iteratorMarketAreaAttribute.hasNext();) {
//				ProductCategoryVirtualAttribute productCategoryVirtualAttribute = (ProductCategoryVirtualAttribute) iteratorMarketAreaAttribute.next();
//				if(productCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(key)){
//					updateProductCategoryVirtualAttribute(productCategoryVirtualAttribute, productCategoryForm.getMarketAreaAttributes().get(key));
//				}
//			}
//		}
		
		if(productCategoryForm != null
				&& productCategoryForm.getGlobalAttributes() != null){
			Map<String, String> attributes = productCategoryForm.getGlobalAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryVirtualAttribute> iteratorCategoryGlobalAttributes = productCategory.getCatalogCategoryGlobalAttributes().iterator(); iteratorCategoryGlobalAttributes.hasNext();) {
					CatalogCategoryVirtualAttribute productCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorCategoryGlobalAttributes.next();
					if(productCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(attributeKey)){
						updateProductCategoryVirtualAttribute(productCategoryVirtualAttribute, productCategoryForm.getGlobalAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist){
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)){
						productCategory.getCatalogCategoryMarketAreaAttributes().add(buildProductCategoryVirtualAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
					}
				}
			}
		}
		
		if(productCategoryForm != null
				&& productCategoryForm.getMarketAreaAttributes() != null){
			Map<String, String> attributes = productCategoryForm.getMarketAreaAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryVirtualAttribute> iteratorCategoryMarketAttributes = productCategory.getCatalogCategoryMarketAreaAttributes().iterator(); iteratorCategoryMarketAttributes.hasNext();) {
					CatalogCategoryVirtualAttribute productCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorCategoryMarketAttributes.next();
					if(productCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(attributeKey)){
						updateProductCategoryVirtualAttribute(productCategoryVirtualAttribute, productCategoryForm.getMarketAreaAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist){
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)){
						productCategory.getCatalogCategoryMarketAreaAttributes().add(buildProductCategoryVirtualAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
					}
				}
			}
		}
		
		productCategoryService.saveOrUpdateCatalogCategory(productCategory);
	}

	private void updateProductCategoryMasterAttribute(final CatalogCategoryMasterAttribute productCategoryMasterAttribute, final String attributeValue){
		AttributeDefinition attributeDefinition = productCategoryMasterAttribute.getAttributeDefinition();
		if(AttributeDefinition.ATTRIBUTE_TYPE_STRING == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setStringValue(attributeValue);
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setDoubleValue(Double.parseDouble(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_FLOAT == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setFloatValue(Float.parseFloat(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_INTEGER == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setIntegerValue(Integer.parseInt(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BLOB == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setBlobValue(attributeValue.getBytes());
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setBooleanValue(BooleanUtils.toBooleanObject(attributeValue));
		}
	}
	
	private CatalogCategoryMasterAttribute buildProductCategoryMasterAttribute(final MarketArea currentMarketArea, final Localization currentLocalization, final String attributeKey, final String attributeValue, boolean isGlobal){
		
		//TODO : denis : 20130125 : add cache
		
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);
		CatalogCategoryMasterAttribute productCategoryMasterAttribute = new CatalogCategoryMasterAttribute();
		productCategoryMasterAttribute.setAttributeDefinition(attributeDefinition);
		productCategoryMasterAttribute.setLocalizationCode(currentLocalization.getCode());
		productCategoryMasterAttribute.setMarketAreaId(currentMarketArea.getId());
		productCategoryMasterAttribute.setStartDate(new Date());
		
		if(AttributeDefinition.ATTRIBUTE_TYPE_STRING == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setStringValue(attributeValue);
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setDoubleValue(Double.parseDouble(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_FLOAT == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setFloatValue(Float.parseFloat(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_INTEGER == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setIntegerValue(Integer.parseInt(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BLOB == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setBlobValue(attributeValue.getBytes());
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN == attributeDefinition.getAttributeType()){
			productCategoryMasterAttribute.setBooleanValue(BooleanUtils.toBooleanObject(attributeValue));
		}
		return productCategoryMasterAttribute;
	}
	
	private void updateProductCategoryVirtualAttribute(final CatalogCategoryVirtualAttribute productCategoryVirtualAttribute, final String attributeValue){
		AttributeDefinition attributeDefinition = productCategoryVirtualAttribute.getAttributeDefinition();
		if(AttributeDefinition.ATTRIBUTE_TYPE_STRING == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setStringValue(attributeValue);
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setDoubleValue(Double.parseDouble(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_FLOAT == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setFloatValue(Float.parseFloat(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_INTEGER == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setIntegerValue(Integer.parseInt(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BLOB == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setBlobValue(attributeValue.getBytes());
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setBooleanValue(BooleanUtils.toBooleanObject(attributeValue));
		}
	}
	
	private CatalogCategoryVirtualAttribute buildProductCategoryVirtualAttribute(final MarketArea currentMarketArea, final Localization currentLocalization, final String attributeKey, final String attributeValue, boolean isGlobal){
		
		//TODO : denis : 20130125 : add cache
		
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);
		CatalogCategoryVirtualAttribute productCategoryVirtualAttribute = new CatalogCategoryVirtualAttribute();
		productCategoryVirtualAttribute.setAttributeDefinition(attributeDefinition);
		productCategoryVirtualAttribute.setLocalizationCode(currentLocalization.getCode());
		productCategoryVirtualAttribute.setMarketAreaId(currentMarketArea.getId());
		productCategoryVirtualAttribute.setStartDate(new Date());
		
		if(AttributeDefinition.ATTRIBUTE_TYPE_STRING == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setStringValue(attributeValue);
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setDoubleValue(Double.parseDouble(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_FLOAT == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setFloatValue(Float.parseFloat(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_INTEGER == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setIntegerValue(Integer.parseInt(attributeValue));
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BLOB == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setBlobValue(attributeValue.getBytes());
		} else if(AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN == attributeDefinition.getAttributeType()){
			productCategoryVirtualAttribute.setBooleanValue(BooleanUtils.toBooleanObject(attributeValue));
		}
		return productCategoryVirtualAttribute;
	}
	
	public void updateProductMarketing(final ProductMarketing productMarketing, final ProductMarketingForm productMarketingForm){
		productMarketing.setBusinessName(productMarketingForm.getName());
		productMarketing.setCode(productMarketingForm.getCode());
		productMarketing.setDescription(productMarketingForm.getDescription());
		productMarketingService.saveOrUpdateProductMarketing(productMarketing);
	}
	
	public void createProductMarketing(final ProductMarketing productMarketing, final ProductMarketingForm productMarketingForm){
		productMarketing.setBusinessName(productMarketingForm.getName());
		productMarketing.setCode(productMarketingForm.getCode());
		productMarketing.setDescription(productMarketingForm.getDescription());
		productMarketingService.saveOrUpdateProductMarketing(productMarketing);
	}
	
	public void updateProductSku(final ProductSku productSku, final ProductSkuForm productSkuForm){
		productSku.setBusinessName(productSkuForm.getName());
		productSku.setCode(productSkuForm.getCode());
		productSku.setDescription(productSkuForm.getDescription());
		productSkuService.saveOrUpdateProductSku(productSku);
	}
	
	public void createProductSku(final ProductSku productSku, final ProductSkuForm productSkuForm){
		productSku.setBusinessName(productSkuForm.getName());
		productSku.setCode(productSkuForm.getCode());
		productSku.setDescription(productSkuForm.getDescription());
		productSkuService.saveOrUpdateProductSku(productSku);
	}
	
	public void updateProductMarketingAsset(final Asset asset, final AssetForm assetForm){
		asset.setName(assetForm.getName());
		asset.setCode(assetForm.getCode());
		asset.setDescription(assetForm.getDescription());

//		asset.setType(assetForm.getType);
//		private boolean isDefault;
//		private boolean isGlobal;
//		private Integer ordering;
//		private Long marketAreaId;
		
		productMarketingService.saveOrUpdateProductMarketingAsset(asset);
	}
	
	public void createProductMarketingAsset(final Asset asset, final AssetForm assetForm){
		asset.setName(assetForm.getName());
		asset.setCode(assetForm.getCode());
		asset.setDescription(assetForm.getDescription());
		
		// ...
		
		productMarketingService.saveOrUpdateProductMarketingAsset(asset);
	}
	
	public void createOrUpdateRetailer(final Retailer retailer, final RetailerForm retailerForm){
		retailer.setCode(retailerForm.getCode());
		retailer.setName(retailerForm.getName());
		retailer.setDescription(retailerForm.getDescription());
		
		retailer.setDescription(retailerForm.getDescription());
		
		RetailerAddress retailerAddress = retailer.getDefaultAddress();
		if(retailerAddress == null){
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
	
}