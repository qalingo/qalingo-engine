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

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryMasterAttribute;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtualAttribute;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import fr.hoteia.qalingo.core.service.AttributeService;
import fr.hoteia.qalingo.core.service.ProductCategoryService;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.service.ProductSkuService;
import fr.hoteia.qalingo.core.service.UserService;
import fr.hoteia.qalingo.web.mvc.form.ProductCategoryForm;
import fr.hoteia.qalingo.web.mvc.form.ProductMarketingForm;
import fr.hoteia.qalingo.web.mvc.form.ProductSkuForm;
import fr.hoteia.qalingo.web.mvc.form.UserForm;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

@Service("webCommerceService")
@Transactional
public class WebBackofficeServiceImpl implements WebBackofficeService {

	@Autowired
	protected UserService userService;
	
	@Autowired
	protected ProductCategoryService productCategoryService;

	@Autowired
	protected ProductMarketingService productMarketingService;
	
	@Autowired
	protected ProductSkuService productSkuService;
	
	@Autowired
	protected AttributeService attributeService;
	
	public void updateUser(final User user, final UserForm userForm){
		user.setLogin(userForm.getLogin());
		user.setLastname(userForm.getLastname());
		user.setFirstname(userForm.getFirstname());
		user.setEmail(userForm.getEmail());
		userService.saveOrUpdateUser(user);
	}
	
	public void createProductCategory(final MarketArea currentMarketArea, final Localization currentLocalization, final ProductCategoryMaster parentProductCategory, final ProductCategoryMaster productCategory, final ProductCategoryForm productCategoryForm) throws UniqueConstraintCodeCategoryException {
		String productCategoryCode = productCategoryForm.getCode();
		productCategory.setBusinessName(productCategoryForm.getName());
		productCategory.setCode(productCategoryForm.getCode());
		productCategory.setDescription(productCategoryForm.getDescription());
		productCategory.setDefaultParentProductCategory(parentProductCategory);
		
		if(productCategoryForm != null
				&& productCategoryForm.getMarketAreaAttributes() != null){
			Map<String, String> attributes = productCategoryForm.getMarketAreaAttributes();
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				String value = attributes.get(attributeKey);
				if(StringUtils.isNotEmpty(value)){
					productCategory.getProductCategoryMarketAreaAttributes().add(buildProductCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
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
					productCategory.getProductCategoryMarketAreaAttributes().add(buildProductCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
				}
			}
		}
		
		try {
			productCategoryService.saveOrUpdateProductCategory(productCategory);
			
			if(parentProductCategory != null){
				if(!parentProductCategory.getProductCategories().contains(productCategory)){
					// PARENT DOESN'T CONTAIN THE NEW CATEGORY - ADD IT IN THE MANY TO MANY
					ProductCategoryMaster reloadedProductCategory = productCategoryService.getMasterProductCategoryByCode(productCategoryCode);
					parentProductCategory.getProductCategories().add(reloadedProductCategory);
					productCategoryService.saveOrUpdateProductCategory(parentProductCategory);
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
	
	public void updateProductCategory(final MarketArea currentMarketArea, final Retailer currentRetailer, final Localization currentLocalization, final ProductCategoryMaster productCategory, final ProductCategoryForm productCategoryForm){
		productCategory.setBusinessName(productCategoryForm.getName());
		productCategory.setCode(productCategoryForm.getCode());
		productCategory.setDescription(productCategoryForm.getDescription());
		if(StringUtils.isNotEmpty(productCategoryForm.getDefaultParentCategoryCode())){
			if(productCategory.getDefaultParentProductCategory() == null
					|| (productCategory.getDefaultParentProductCategory() != null
						&& !productCategoryForm.getDefaultParentCategoryCode().equalsIgnoreCase(productCategory.getDefaultParentProductCategory().getCode()))){
				final ProductCategoryMaster parentProductCategory = productCategoryService.getMasterProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryForm.getDefaultParentCategoryCode());
				productCategory.setDefaultParentProductCategory(parentProductCategory);
			}
		}

		if(productCategoryForm != null
				&& productCategoryForm.getGlobalAttributes() != null){
			Map<String, String> attributes = productCategoryForm.getGlobalAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<ProductCategoryMasterAttribute> iteratorCategoryGlobalAttributes = productCategory.getProductCategoryGlobalAttributes().iterator(); iteratorCategoryGlobalAttributes.hasNext();) {
					ProductCategoryMasterAttribute productCategoryMasterAttribute = (ProductCategoryMasterAttribute) iteratorCategoryGlobalAttributes.next();
					if(productCategoryMasterAttribute.getAttributeDefinition().getCode().equals(attributeKey)){
						updateProductCategoryMasterAttribute(productCategoryMasterAttribute, productCategoryForm.getGlobalAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist){
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)){
						productCategory.getProductCategoryMarketAreaAttributes().add(buildProductCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
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
				for (Iterator<ProductCategoryMasterAttribute> iteratorCategoryMarketAttributes = productCategory.getProductCategoryMarketAreaAttributes().iterator(); iteratorCategoryMarketAttributes.hasNext();) {
					ProductCategoryMasterAttribute productCategoryMasterAttribute = (ProductCategoryMasterAttribute) iteratorCategoryMarketAttributes.next();
					if(productCategoryMasterAttribute.getAttributeDefinition().getCode().equals(attributeKey)){
						updateProductCategoryMasterAttribute(productCategoryMasterAttribute, productCategoryForm.getMarketAreaAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist){
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)){
						productCategory.getProductCategoryMarketAreaAttributes().add(buildProductCategoryMasterAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
					}
				}
			}
		}
		
		productCategoryService.saveOrUpdateProductCategory(productCategory);
	}
	
	public void createProductCategory(final MarketArea currentMarketArea, final Localization currentLocalization, final ProductCategoryVirtual productCategory, final ProductCategoryForm productCategoryForm){
		productCategory.setBusinessName(productCategoryForm.getName());
		productCategory.setCode(productCategoryForm.getCode());
		productCategory.setDescription(productCategoryForm.getDescription());
		productCategoryService.saveOrUpdateProductCategory(productCategory);
	}
	
	public void updateProductCategory(final MarketArea currentMarketArea, final Retailer currentRetailer, final Localization currentLocalization, final ProductCategoryVirtual productCategory, final ProductCategoryForm productCategoryForm){
		productCategory.setBusinessName(productCategoryForm.getName());
		productCategory.setCode(productCategoryForm.getCode());
		productCategory.setDescription(productCategoryForm.getDescription());
		if(StringUtils.isNotEmpty(productCategoryForm.getDefaultParentCategoryCode())){
			if(productCategory.getDefaultParentProductCategory() == null
					|| (productCategory.getDefaultParentProductCategory() != null
						&& !productCategoryForm.getDefaultParentCategoryCode().equalsIgnoreCase(productCategory.getDefaultParentProductCategory().getCode()))){
				final ProductCategoryVirtual parentProductCategory = productCategoryService.getVirtualProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryForm.getDefaultParentCategoryCode());
				productCategory.setDefaultParentProductCategory(parentProductCategory);
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
				for (Iterator<ProductCategoryVirtualAttribute> iteratorCategoryGlobalAttributes = productCategory.getProductCategoryGlobalAttributes().iterator(); iteratorCategoryGlobalAttributes.hasNext();) {
					ProductCategoryVirtualAttribute productCategoryVirtualAttribute = (ProductCategoryVirtualAttribute) iteratorCategoryGlobalAttributes.next();
					if(productCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(attributeKey)){
						updateProductCategoryVirtualAttribute(productCategoryVirtualAttribute, productCategoryForm.getGlobalAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist){
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)){
						productCategory.getProductCategoryMarketAreaAttributes().add(buildProductCategoryVirtualAttribute(currentMarketArea, currentLocalization, attributeKey, value, true));
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
				for (Iterator<ProductCategoryVirtualAttribute> iteratorCategoryMarketAttributes = productCategory.getProductCategoryMarketAreaAttributes().iterator(); iteratorCategoryMarketAttributes.hasNext();) {
					ProductCategoryVirtualAttribute productCategoryVirtualAttribute = (ProductCategoryVirtualAttribute) iteratorCategoryMarketAttributes.next();
					if(productCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(attributeKey)){
						updateProductCategoryVirtualAttribute(productCategoryVirtualAttribute, productCategoryForm.getMarketAreaAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist){
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)){
						productCategory.getProductCategoryMarketAreaAttributes().add(buildProductCategoryVirtualAttribute(currentMarketArea, currentLocalization, attributeKey, value, false));
					}
				}
			}
		}
		
		productCategoryService.saveOrUpdateProductCategory(productCategory);
	}

	private void updateProductCategoryMasterAttribute(final ProductCategoryMasterAttribute productCategoryMasterAttribute, final String attributeValue){
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
	
	private ProductCategoryMasterAttribute buildProductCategoryMasterAttribute(final MarketArea currentMarketArea, final Localization currentLocalization, final String attributeKey, final String attributeValue, boolean isGlobal){
		
		//TODO : denis : 20130125 : add cache
		
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);
		ProductCategoryMasterAttribute productCategoryMasterAttribute = new ProductCategoryMasterAttribute();
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
	
	private void updateProductCategoryVirtualAttribute(final ProductCategoryVirtualAttribute productCategoryVirtualAttribute, final String attributeValue){
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
	
	private ProductCategoryVirtualAttribute buildProductCategoryVirtualAttribute(final MarketArea currentMarketArea, final Localization currentLocalization, final String attributeKey, final String attributeValue, boolean isGlobal){
		
		//TODO : denis : 20130125 : add cache
		
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);
		ProductCategoryVirtualAttribute productCategoryVirtualAttribute = new ProductCategoryVirtualAttribute();
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
	
}
