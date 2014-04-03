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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.PersistenceException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.PaymentGatewayOption;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.DeliveryMethodService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.PaymentGatewayService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.TaxService;
import org.hoteia.qalingo.core.service.UserService;
import org.hoteia.qalingo.core.service.WarehouseService;
import org.hoteia.qalingo.core.service.WebBackofficeService;
import org.hoteia.qalingo.core.web.mvc.form.AssetForm;
import org.hoteia.qalingo.core.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.core.web.mvc.form.CustomerForm;
import org.hoteia.qalingo.core.web.mvc.form.DeliveryMethodForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingValueForm;
import org.hoteia.qalingo.core.web.mvc.form.PaymentGatewayForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.core.web.mvc.form.RetailerForm;
import org.hoteia.qalingo.core.web.mvc.form.TaxForm;
import org.hoteia.qalingo.core.web.mvc.form.UserForm;
import org.hoteia.qalingo.core.web.mvc.form.WarehouseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("webBackofficeService")
@Transactional
public class WebBackofficeServiceImpl implements WebBackofficeService {

    @Autowired
    protected UserService userService;

    @Autowired
    protected CustomerService customerService;

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
    protected TaxService taxService;

    @Autowired
    protected AttributeService attributeService;

    @Autowired
    protected EngineSettingService engineSettingService;

    @Autowired
    protected PaymentGatewayService paymentGatewayService;

    public void createOrUpdatePersonalUser(User user, final UserForm userForm) {
        if(user == null){
            user = new User();
        }
        user.setLogin(userForm.getLogin());
        user.setLastname(userForm.getLastname());
        user.setFirstname(userForm.getFirstname());
        user.setEmail(userForm.getEmail());
        userService.saveOrUpdateUser(user);
    }
    
	public void createOrUpdateUser(User user, final UserForm userForm) {
	    if(user == null){
	        user = new User();
	    }
		user.setLogin(userForm.getLogin());
		user.setLastname(userForm.getLastname());
		user.setFirstname(userForm.getFirstname());
		user.setEmail(userForm.getEmail());
		user.setActive(userForm.isActive());
		userService.saveOrUpdateUser(user);
	}
	
    public void createOrUpdateCustomer(Customer customer, final CustomerForm customerForm) throws Exception {
        if(customer == null){
            customer = new Customer();
        }
        customer.setLogin(customerForm.getLogin());
        customer.setLastname(customerForm.getLastname());
        customer.setFirstname(customerForm.getFirstname());
        customer.setEmail(customerForm.getEmail());
        customer.setActive(customerForm.isActive());
        customerService.saveOrUpdateCustomer(customer);
    }
    
	public void createCatalogCategory(final MarketArea currentMarketArea, final Localization currentLocalization, final CatalogCategoryMaster parentCatalogCategory, final CatalogCategoryMaster catalogCategory, final CatalogCategoryForm catalogCategoryForm) throws UniqueConstraintCodeCategoryException {
		String catalogCategoryCode = catalogCategoryForm.getCode();
		catalogCategory.setName(catalogCategoryForm.getName());
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
		catalogCategory.setName(catalogCategoryForm.getName());
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
					    catalogCategoryMasterAttribute.setValue(catalogCategoryForm.getGlobalAttributes().get(attributeKey));
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
					    catalogCategoryMasterAttribute.setValue(catalogCategoryForm.getMarketAreaAttributes().get(attributeKey));
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
		catalogCategory.setName(catalogCategoryForm.getName());
		catalogCategory.setCode(catalogCategoryForm.getCode());
		catalogCategory.setDescription(catalogCategoryForm.getDescription());
		catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
	}
	
	public void updateCatalogCategory(final MarketArea currentMarketArea, final Retailer currentRetailer, final Localization currentLocalization, final CatalogCategoryVirtual catalogCategory, final CatalogCategoryForm catalogCategoryForm) {
		catalogCategory.setName(catalogCategoryForm.getName());
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
					    catalogCategoryVirtualAttribute.setValue(catalogCategoryForm.getGlobalAttributes().get(attributeKey));
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
					    catalogCategoryVirtualAttribute.setValue(catalogCategoryForm.getMarketAreaAttributes().get(attributeKey));
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

	private CatalogCategoryMasterAttribute buildCatalogCategoryMasterAttribute(final MarketArea currentMarketArea, final Localization currentLocalization, final String attributeKey, final String attributeValue, boolean isGlobal) {
		
		//TODO : denis : 20130125 : add cache
		
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);
		CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = new CatalogCategoryMasterAttribute();
		catalogCategoryMasterAttribute.setAttributeDefinition(attributeDefinition);
		catalogCategoryMasterAttribute.setLocalizationCode(currentLocalization.getCode());
		catalogCategoryMasterAttribute.setMarketAreaId(currentMarketArea.getId());
		catalogCategoryMasterAttribute.setStartDate(new Date());
		catalogCategoryMasterAttribute.setValue(attributeValue);
		return catalogCategoryMasterAttribute;
	}
	
	private CatalogCategoryVirtualAttribute buildCatalogCategoryVirtualAttribute(final MarketArea currentMarketArea, final Localization currentLocalization, final String attributeKey, final String attributeValue, boolean isGlobal) {
		
		//TODO : denis : 20130125 : add cache
		
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);
		CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = new CatalogCategoryVirtualAttribute();
		catalogCategoryVirtualAttribute.setAttributeDefinition(attributeDefinition);
		catalogCategoryVirtualAttribute.setLocalizationCode(currentLocalization.getCode());
		catalogCategoryVirtualAttribute.setMarketAreaId(currentMarketArea.getId());
		catalogCategoryVirtualAttribute.setStartDate(new Date());
	    catalogCategoryVirtualAttribute.setValue(attributeValue);
	    
		return catalogCategoryVirtualAttribute;
	}
	
	public void createOrUpdateProductMarketing(ProductMarketing productMarketing, final ProductMarketingForm productMarketingForm) {
       if(productMarketing == null){
           productMarketing = new ProductMarketing();
        }
		productMarketing.setName(productMarketingForm.getName());
		productMarketing.setCode(productMarketingForm.getCode());
		productMarketing.setDescription(productMarketingForm.getDescription());
		productService.saveOrUpdateProductMarketing(productMarketing);
	}
	
	public void createOrUpdateProductSku(ProductSku productSku, final ProductSkuForm productSkuForm) {
	    if(productSku == null){
	        productSku = new ProductSku();
	    }
		productSku.setName(productSkuForm.getName());
		productSku.setCode(productSkuForm.getCode());
		productSku.setDescription(productSkuForm.getDescription());
		productService.saveOrUpdateProductSku(productSku);
	}
	
	public void createOrUpdateProductMarketingAsset(final Asset asset, final AssetForm assetForm) {
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
	
	public void createOrUpdateRetailer(Retailer retailer, final RetailerForm retailerForm) throws Exception {
	    if (retailer == null) {
	        retailer = new Retailer();
	    }
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
		
		retailer.setBrand(retailerForm.isBrand());
		retailer.setCorner(retailerForm.isCorner());
		retailer.setEcommerce(retailerForm.isEcommerce());
		retailer.setOfficialRetailer(retailerForm.isOfficialRetailer());
		
		if(StringUtils.isNotBlank(retailerForm.getWarehouseId())){
			final Warehouse warehouse = warehouseService.getWarehouseById(retailerForm.getWarehouseId());
			if(warehouse != null){
				retailer.setWarehouse(warehouse);
			}
		} else {
			retailer.setWarehouse(null);
		}
		
        MultipartFile multipartFile = retailerForm.getFile();
        if (multipartFile.getSize() > 0) {
            UUID uuid = UUID.randomUUID();
            String pathRetailerLogoImage = new StringBuilder(uuid.toString()).append(".").append(FilenameUtils.getExtension(multipartFile.getOriginalFilename())).toString();

            String absoluteFilePath = retailerService.getRetailerLogoFilePath(pathRetailerLogoImage);
            String absoluteFolderPath = absoluteFilePath.replace(pathRetailerLogoImage, "");

            InputStream inputStream = multipartFile.getInputStream();
            URI fileUrl = new URI(absoluteFilePath);
            File fileLogo;
            File folderLogo;
            try {
                folderLogo = new File(absoluteFolderPath);
                folderLogo.mkdirs();
                fileLogo = new File(fileUrl);
                
            } catch (IllegalArgumentException e) {
                fileLogo = new File(fileUrl.getPath());
            }
            OutputStream outputStream = new FileOutputStream(fileLogo);
            int readBytes = 0;
            byte[] buffer = new byte[8192];
            while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
                outputStream.write(buffer, 0, readBytes);
            }
            outputStream.close();
            inputStream.close();
            retailer.setLogo(pathRetailerLogoImage);
        }
		
		retailerService.saveOrUpdateRetailer(retailer);
	}

    public void createOrUpdateWarehouse(final RequestData requestData, Warehouse warehouse, final WarehouseForm warehouseForm) {
        if (warehouse == null) {
            warehouse = new Warehouse();
            Set<MarketArea> marketAreas = new HashSet<MarketArea>();
            marketAreas.add(requestData.getMarketArea());
            warehouse.setMarketAreas(marketAreas);
        }
        warehouse.setCode(warehouseForm.getCode());
        warehouse.setName(warehouseForm.getName());
        warehouse.setDescription(warehouseForm.getDescription());

        warehouseService.saveOrUpdateWarehouse(warehouse);
    }

    public void createOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod, final DeliveryMethodForm deliveryMethodForm) {
        if (deliveryMethod == null) {
            deliveryMethod = new DeliveryMethod();
        }
        deliveryMethod.setCode(deliveryMethodForm.getCode());
        deliveryMethod.setName(deliveryMethodForm.getName());
        deliveryMethod.setDescription(deliveryMethodForm.getDescription());

        deliveryMethodService.saveOrUpdateDeliveryMethod(deliveryMethod);
    }
    
    public void createOrUpdateTax(Tax tax, final TaxForm taxForm) {
        if (tax == null) {
            tax = new Tax();
        }
        tax.setCode(taxForm.getCode());
        tax.setName(taxForm.getName());
        tax.setDescription(taxForm.getDescription());

        taxService.saveOrUpdateTax(tax);
    }

    public void updateEngineSettingValue(EngineSettingValue engineSettingValue, final EngineSettingValueForm engineSettingValueForm) {
        if (engineSettingValue == null) {
            engineSettingValue = new EngineSettingValue();
        }
        engineSettingValue.setValue(engineSettingValueForm.getValue());
        engineSettingService.saveOrUpdateEngineSettingValue(engineSettingValue);
    }

    public void createOrUpdatePaymentGateway(final MarketArea marketArea, AbstractPaymentGateway paymentGateway, final PaymentGatewayForm paymentGatewayForm) {
        if (paymentGateway != null) {
            paymentGateway.setCode(paymentGatewayForm.getCode());
            paymentGateway.setName(paymentGatewayForm.getName());
            paymentGateway.setDescription(paymentGatewayForm.getDescription());
            
            if (paymentGatewayForm.isActive()) {
                if (paymentGateway.getMarketAreas() == null || !paymentGateway.getMarketAreas().contains(marketArea)) {
                    paymentGateway.addMarketArea(marketArea);
                }
            } else {
                if (paymentGateway.getMarketAreas() == null || paymentGateway.getMarketAreas().contains(marketArea)) {
                    paymentGateway.getMarketAreas().remove(marketArea);
                }
            }

            for (Iterator<String> iterator = paymentGatewayForm.getGlobalAttributeMap().keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                String value = paymentGatewayForm.getGlobalAttributeMap().get(key);
                boolean success = paymentGateway.updateAttribute(key, value);
                if(!success){
                    // ATTRIBUTE DOESN'T EXIT - ADD
                    AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(key);
                    paymentGateway.addAttribute(null, attributeDefinition, value);
                }
            }
            
            for (Iterator<String> iterator = paymentGatewayForm.getMarketAreaAttributeMap().keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                String value = paymentGatewayForm.getMarketAreaAttributeMap().get(key);
                boolean success = paymentGateway.updateAttribute(key, value);
                if(!success){
                    // ATTRIBUTE DOESN'T EXIT - ADD
                    AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(key);
                    paymentGateway.addAttribute(marketArea, attributeDefinition, value);
                }
            }
            
            List<PaymentGatewayOption> availableOptions = paymentGatewayService.findPaymentGatewayOptions();
            Set<PaymentGatewayOption> options = new HashSet<PaymentGatewayOption>();
            for (Iterator<PaymentGatewayOption> iterator = availableOptions.iterator(); iterator.hasNext();) {
                PaymentGatewayOption paymentGatewayOption = (PaymentGatewayOption) iterator.next();
                if(paymentGatewayForm.getOptionMap().keySet().contains(paymentGatewayOption.getCode())){
                    options.add(paymentGatewayOption);
                }
            }
            paymentGateway.setOptions(options);
            
            paymentGatewayService.saveOrUpdatePaymentGateway(paymentGateway);
        }
    }

    public void createOrUpdateEngineSetting(EngineSetting engineSetting, final EngineSettingForm engineSettingForm) {
        if (engineSetting != null) {
            engineSetting.setDefaultValue(engineSettingForm.getDefaultValue());
            engineSettingService.saveOrUpdateEngineSetting(engineSetting);
        }
    }

    public void createOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue, final EngineSettingValueForm engineSettingValueForm) {
        if (engineSettingValue != null) {
            engineSettingValue.setContext(engineSettingValueForm.getContext());
            engineSettingValue.setValue(engineSettingValueForm.getValue());

            engineSettingService.saveOrUpdateEngineSettingValue(engineSettingValue);
        }
    }
    
}
