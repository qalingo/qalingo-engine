/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.factory.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuAttribute;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.Shipping;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.qalingo.core.service.BackofficeUrlService;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.hoteia.qalingo.web.mvc.factory.FormFactory;
import org.hoteia.qalingo.web.mvc.form.AssetForm;
import org.hoteia.qalingo.web.mvc.form.CustomerForm;
import org.hoteia.qalingo.web.mvc.form.OrderForm;
import org.hoteia.qalingo.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.web.mvc.form.RetailerForm;
import org.hoteia.qalingo.web.mvc.form.RuleForm;
import org.hoteia.qalingo.web.mvc.form.ShippingForm;
import org.hoteia.qalingo.web.mvc.form.UserForm;

/**
 * 
 */
@Service("formFactory")
public class FormFactoryImpl implements FormFactory {

	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected BackofficeUrlService backofficeUrlService;
	
	@Autowired
	protected AttributeService attributeService;
	
	public CatalogCategoryForm buildCatalogCategoryForm(final HttpServletRequest request) throws Exception {
		final CatalogCategoryForm catalogCategoryForm = new CatalogCategoryForm();
		List<AttributeDefinition> attributeDefinitions = attributeService.findCatalogCategoryAttributeDefinitions();
		for (Iterator<AttributeDefinition> iterator = attributeDefinitions.iterator(); iterator.hasNext();) {
			AttributeDefinition attributeDefinition = (AttributeDefinition) iterator.next();
			if(attributeDefinition.isGlobal()){
				catalogCategoryForm.getGlobalAttributes().put(attributeDefinition.getCode(), "");
			} else {
				catalogCategoryForm.getMarketAreaAttributes().put(attributeDefinition.getCode(), "");
			}
		}
		return catalogCategoryForm;
	}

	public CatalogCategoryForm buildCatalogCategoryForm(final HttpServletRequest request, final CatalogCategoryMaster catalogCategory) throws Exception {
		CatalogCategoryMaster parentProductCategory = catalogCategory.getDefaultParentCatalogCategory();
		return buildCatalogCategoryForm(request, parentProductCategory, catalogCategory);
	}

	public CatalogCategoryForm buildCatalogCategoryForm(final HttpServletRequest request, final CatalogCategoryMaster parentProductCategory, final CatalogCategoryMaster catalogCategory) throws Exception {
		final CatalogCategoryForm catalogCategoryForm = buildCatalogCategoryForm(request);
		if(parentProductCategory != null){
			catalogCategoryForm.setDefaultParentCategoryCode(parentProductCategory.getCode());
		}
		if(catalogCategory != null){
			catalogCategoryForm.setId(catalogCategory.getId().toString());
			catalogCategoryForm.setCatalogCode(catalogCategory.getBusinessName());
			catalogCategoryForm.setName(catalogCategory.getBusinessName());
			catalogCategoryForm.setCode(catalogCategory.getCode());
			catalogCategoryForm.setDescription(catalogCategory.getDescription());
			
			Set<CatalogCategoryMasterAttribute> globalAttributes = catalogCategory.getCatalogCategoryGlobalAttributes();
			for (Iterator<CatalogCategoryMasterAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
				CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iterator.next();
				catalogCategoryForm.getGlobalAttributes().put(catalogCategoryMasterAttribute.getAttributeDefinition().getCode(), catalogCategoryMasterAttribute.getValueAsString());
			}
			
			Set<CatalogCategoryMasterAttribute> marketAreaAttributes = catalogCategory.getCatalogCategoryMarketAreaAttributes();
			for (Iterator<CatalogCategoryMasterAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
				CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iterator.next();
				catalogCategoryForm.getMarketAreaAttributes().put(catalogCategoryMasterAttribute.getAttributeDefinition().getCode(), catalogCategoryMasterAttribute.getValueAsString());
			}
		}
		return catalogCategoryForm;
	}
	
	public CatalogCategoryForm buildCatalogCategoryForm(final HttpServletRequest request, final CatalogCategoryVirtual catalogCategory) throws Exception {
		CatalogCategoryVirtual parentProductCategory = catalogCategory.getDefaultParentCatalogCategory();
		return buildCatalogCategoryForm(request, parentProductCategory, parentProductCategory);
	}
	
	public CatalogCategoryForm buildCatalogCategoryForm(final HttpServletRequest request, final CatalogCategoryVirtual parentProductCategory, final CatalogCategoryVirtual catalogCategory) throws Exception {
		final CatalogCategoryForm catalogCategoryForm = buildCatalogCategoryForm(request);
		if(parentProductCategory != null){
			catalogCategoryForm.setDefaultParentCategoryCode(parentProductCategory.getCode());
		}
		if(catalogCategory != null){
			catalogCategoryForm.setId(catalogCategory.getId().toString());
			catalogCategoryForm.setCatalogCode(catalogCategory.getBusinessName());
			catalogCategoryForm.setName(catalogCategory.getBusinessName());
			catalogCategoryForm.setCode(catalogCategory.getCode());
			catalogCategoryForm.setDescription(catalogCategory.getDescription());
		}
		return catalogCategoryForm;
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
	
	public AssetForm buildProductMarketingAssetForm(final HttpServletRequest request, final Asset productMarketingAsset) throws Exception {
		final AssetForm assetForm = new AssetForm();
		if(productMarketingAsset != null){
			assetForm.setId(productMarketingAsset.getId().toString());
			assetForm.setName(productMarketingAsset.getName());
			assetForm.setCode(productMarketingAsset.getCode());
			assetForm.setDescription(productMarketingAsset.getDescription());
			assetForm.setDefault(productMarketingAsset.isDefault());
			assetForm.setPath(productMarketingAsset.getPath());
			assetForm.setType(productMarketingAsset.getType().getPropertyKey());
			assetForm.setSize(productMarketingAsset.getSize().getPropertyKey());
		}
		return assetForm;
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
	
	public CustomerForm buildCustomerForm(final HttpServletRequest request, final Customer customer) throws Exception {
		final CustomerForm customerForm = new CustomerForm();
		if(customer != null){
			customerForm.setId(customer.getId());
			customerForm.setVersion(customer.getVersion());
			customerForm.setLogin(customer.getLogin());
			customerForm.setTitle(customer.getTitle());
			customerForm.setFirstname(customer.getFirstname());
			customerForm.setLastname(customer.getLastname());
			customerForm.setEmail(customer.getEmail());
			customerForm.setPassword(customer.getPassword());
			customerForm.setDefaultLocale(customer.getDefaultLocale());
			customerForm.setActive(customer.isActive());
		}
		return customerForm;
	}
	
	public OrderForm buildOrderForm(final HttpServletRequest request, final OrderCustomer order) throws Exception {
		final OrderForm orderForm = new OrderForm();
		if(order != null){
			orderForm.setId(order.getId());
			orderForm.setVersion(order.getVersion());
			orderForm.setStatus(order.getStatus());
			orderForm.setOrderNum(order.getOrderNum());
			orderForm.setCustomerId(order.getCustomerId());
			orderForm.setBillingAddressId(order.getBillingAddressId());
			orderForm.setShippingAddressId(order.getShippingAddressId());
		}
		return orderForm;
	}
	
	public RuleForm buildRuleForm(final HttpServletRequest request, final AbstractRuleReferential rule) throws Exception {
		final RuleForm ruleForm = new RuleForm();
		if(rule != null){
			ruleForm.setId(rule.getId());
			ruleForm.setVersion(rule.getVersion());
			ruleForm.setName(rule.getName());
			ruleForm.setDescription(rule.getDescription());
			ruleForm.setSalience(rule.getSalience());
		}
		return ruleForm;
	}
	
	public ShippingForm buildShippingForm(final HttpServletRequest request, final Shipping shipping) throws Exception {
		final ShippingForm shippingForm = new ShippingForm();
		if(shipping != null){
			shippingForm.setId(shipping.getId());
			shippingForm.setVersion(shipping.getVersion());
			shippingForm.setName(shipping.getName());
			shippingForm.setDescription(shipping.getDescription());
			shippingForm.setCode(shipping.getCode());
			shippingForm.setPrice(shipping.getPrice());
			shippingForm.setMarketAreaId(shipping.getMarketAreaId());
		}
		return shippingForm;
	}
	
	public RetailerForm buildRetailerForm(final HttpServletRequest request, final Retailer retailer) throws Exception {
		final RetailerForm retailerForm = new RetailerForm();
		if(retailer != null){
			retailerForm.setId(retailer.getId().toString());
			retailerForm.setCode(retailer.getCode());
			retailerForm.setName(retailer.getName());
			retailerForm.setDescription(retailer.getDescription());

			if (retailer.getAddresses() != null) {
				RetailerAddress defaultAddress = retailer.getDefaultAddress();
				if (defaultAddress != null) {
					retailerForm.setAddress1(defaultAddress.getAddress1());
					retailerForm.setAddress2(defaultAddress.getAddress2());
					retailerForm.setAddressAdditionalInformation(defaultAddress.getAddressAdditionalInformation());
					retailerForm.setPostalCode(defaultAddress.getPostalCode());
					retailerForm.setCity(defaultAddress.getCity());
					retailerForm.setStateCode(defaultAddress.getStateCode());
					retailerForm.setAreaCode(defaultAddress.getAreaCode());
					retailerForm.setCountryCode(defaultAddress.getCountryCode());
					
					retailerForm.setLongitude(defaultAddress.getLongitude());
					retailerForm.setLatitude(defaultAddress.getLatitude());

					retailerForm.setPhone(defaultAddress.getPhone());
					retailerForm.setMobile(defaultAddress.getMobile());
					retailerForm.setFax(defaultAddress.getFax());
					retailerForm.setEmail(defaultAddress.getEmail());
					String websiteUrl = defaultAddress.getWebsite();
					if (StringUtils.isNotEmpty(websiteUrl) && !websiteUrl.contains("http")) {
						websiteUrl = "http://" + websiteUrl;
					}
					retailerForm.setWebsite(websiteUrl);
				}
			}
			
		}
		return retailerForm;
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
		
		final List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add("form");
		userForm.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));
		userForm.setUserDetailsUrl(backofficeUrlService.generateUrl(BoUrls.USER_DETAILS, requestUtil.getRequestData(request)));
		userForm.setUserEditUrl(backofficeUrlService.generateUrl(BoUrls.USER_EDIT, requestUtil.getRequestData(request)));
		userForm.setFormSubmitUrl(backofficeUrlService.generateUrl(BoUrls.USER_EDIT, requestUtil.getRequestData(request)));
		
		return userForm;
	}
	
}