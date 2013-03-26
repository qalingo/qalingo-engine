/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryMasterAttribute;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.ProductSkuAttribute;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.AttributeService;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.form.ProductCategoryForm;
import fr.hoteia.qalingo.web.mvc.form.ProductMarketingForm;
import fr.hoteia.qalingo.web.mvc.form.ProductSkuForm;
import fr.hoteia.qalingo.web.mvc.form.UserForm;

/**
 * 
 */
@Service("formFactory")
public class FormFactoryImpl implements FormFactory {

	@Autowired
	protected CoreMessageSource coreMessageSource;

	@Autowired
	protected AttributeService attributeService;
	
	public ProductCategoryForm buildProductCategoryForm(final HttpServletRequest request) throws Exception {
		final ProductCategoryForm productCategoryForm = new ProductCategoryForm();
		List<AttributeDefinition> attributeDefinitions = attributeService.findCatalogCategoryAttributeDefinitions();
		for (Iterator<AttributeDefinition> iterator = attributeDefinitions.iterator(); iterator.hasNext();) {
			AttributeDefinition attributeDefinition = (AttributeDefinition) iterator.next();
			if(attributeDefinition.isGlobal()){
				productCategoryForm.getGlobalAttributes().put(attributeDefinition.getCode(), "");
			} else {
				productCategoryForm.getMarketAreaAttributes().put(attributeDefinition.getCode(), "");
			}
		}
		return productCategoryForm;
	}

	public ProductCategoryForm buildProductCategoryForm(final HttpServletRequest request, final ProductCategoryMaster productCategory) throws Exception {
		ProductCategoryMaster parentProductCategory = productCategory.getDefaultParentProductCategory();
		return buildProductCategoryForm(request, parentProductCategory, productCategory);
	}

	public ProductCategoryForm buildProductCategoryForm(final HttpServletRequest request, final ProductCategoryMaster parentProductCategory, final ProductCategoryMaster productCategory) throws Exception {
		final ProductCategoryForm productCategoryForm = buildProductCategoryForm(request);
		if(parentProductCategory != null){
			productCategoryForm.setDefaultParentCategoryCode(parentProductCategory.getCode());
		}
		if(productCategory != null){
			productCategoryForm.setId(productCategory.getId().toString());
			productCategoryForm.setCatalogCode(productCategory.getBusinessName());
			productCategoryForm.setName(productCategory.getBusinessName());
			productCategoryForm.setCode(productCategory.getCode());
			productCategoryForm.setDescription(productCategory.getDescription());
			
			Set<ProductCategoryMasterAttribute> globalAttributes = productCategory.getProductCategoryGlobalAttributes();
			for (Iterator<ProductCategoryMasterAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
				ProductCategoryMasterAttribute productCategoryMasterAttribute = (ProductCategoryMasterAttribute) iterator.next();
				productCategoryForm.getGlobalAttributes().put(productCategoryMasterAttribute.getAttributeDefinition().getCode(), productCategoryMasterAttribute.getValueAsString());
			}
			
			Set<ProductCategoryMasterAttribute> marketAreaAttributes = productCategory.getProductCategoryMarketAreaAttributes();
			for (Iterator<ProductCategoryMasterAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
				ProductCategoryMasterAttribute productCategoryMasterAttribute = (ProductCategoryMasterAttribute) iterator.next();
				productCategoryForm.getMarketAreaAttributes().put(productCategoryMasterAttribute.getAttributeDefinition().getCode(), productCategoryMasterAttribute.getValueAsString());
			}
		}
		return productCategoryForm;
	}
	
	public ProductCategoryForm buildProductCategoryForm(final HttpServletRequest request, final ProductCategoryVirtual productCategory) throws Exception {
		ProductCategoryVirtual parentProductCategory = productCategory.getDefaultParentProductCategory();
		return buildProductCategoryForm(request, parentProductCategory, parentProductCategory);
	}
	
	public ProductCategoryForm buildProductCategoryForm(final HttpServletRequest request, final ProductCategoryVirtual parentProductCategory, final ProductCategoryVirtual productCategory) throws Exception {
		final ProductCategoryForm productCategoryForm = buildProductCategoryForm(request);
		if(parentProductCategory != null){
			productCategoryForm.setDefaultParentCategoryCode(parentProductCategory.getCode());
		}
		if(productCategory != null){
			productCategoryForm.setId(productCategory.getId().toString());
			productCategoryForm.setCatalogCode(productCategory.getBusinessName());
			productCategoryForm.setName(productCategory.getBusinessName());
			productCategoryForm.setCode(productCategory.getCode());
			productCategoryForm.setDescription(productCategory.getDescription());
		}
		return productCategoryForm;
	}
	
	public ProductMarketingForm buildProductMarketingForm(final HttpServletRequest request, final ProductMarketing productMarketing) throws Exception {
		final ProductMarketingForm productMarketingForm = new ProductMarketingForm();
		if(productMarketing != null){
			productMarketingForm.setId(productMarketing.getId().toString());
			productMarketingForm.setName(productMarketing.getBusinessName());
			productMarketingForm.setCode(productMarketing.getCode());
			productMarketingForm.setDescription(productMarketing.getDescription());
			
			Set<ProductMarketingAttribute> globalAttributes = productMarketing.getProductMarketingGlobalAttributes();
			for (Iterator<ProductMarketingAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
				ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
				productMarketingForm.getGlobalAttributes().put(productMarketingAttribute.getAttributeDefinition().getCode(), productMarketingAttribute.getValueAsString());
			}
			
			Set<ProductMarketingAttribute> marketAreaAttributes = productMarketing.getProductMarketingMarketAreaAttributes();
			for (Iterator<ProductMarketingAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
				ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
				productMarketingForm.getMarketAreaAttributes().put(productMarketingAttribute.getAttributeDefinition().getCode(), productMarketingAttribute.getValueAsString());
			}
		}
		
		return productMarketingForm;
	}
	
	public ProductSkuForm buildProductSkuForm(final HttpServletRequest request, final ProductSku productSku) throws Exception {
		final ProductSkuForm productSkuForm = new ProductSkuForm();
		if(productSku != null){
			
			productSkuForm.setId(productSku.getId().toString());
			productSkuForm.setName(productSku.getBusinessName());
			productSkuForm.setCode(productSku.getCode());
			productSkuForm.setDescription(productSku.getDescription());

			
			Set<ProductSkuAttribute> globalAttributes = productSku.getProductSkuGlobalAttributes();
			for (Iterator<ProductSkuAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
				ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
				productSkuForm.getGlobalAttributes().put(productSkuAttribute.getAttributeDefinition().getCode(), productSkuAttribute.getValueAsString());
			}
			
			Set<ProductSkuAttribute> marketAreaAttributes = productSku.getProductSkuMarketAreaAttributes();
			for (Iterator<ProductSkuAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
				ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
				productSkuForm.getMarketAreaAttributes().put(productSkuAttribute.getAttributeDefinition().getCode(), productSkuAttribute.getValueAsString());
			}
		}
		return productSkuForm;
	}
	
	public UserForm buildUserForm(final HttpServletRequest request, final User user) throws Exception {
		final UserForm userForm = new UserForm();
		if(user != null){
			userForm.setId(user.getId().toString());
			userForm.setLogin(user.getLogin());
			userForm.setFirstname(user.getFirstname());
			userForm.setLastname(user.getLastname());
			userForm.setEmail(user.getEmail());
			userForm.setActive(user.isActive());
		}
		return userForm;
	}
	
}
