/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuAttribute;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.qalingo.core.service.BackofficeUrlService;
import org.hoteia.qalingo.core.web.mvc.factory.BackofficeFormFactory;
import org.hoteia.qalingo.core.web.mvc.form.AssetForm;
import org.hoteia.qalingo.core.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.core.web.mvc.form.CustomerForm;
import org.hoteia.qalingo.core.web.mvc.form.DeliveryMethodForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingValueForm;
import org.hoteia.qalingo.core.web.mvc.form.OrderForm;
import org.hoteia.qalingo.core.web.mvc.form.PaymentGatewayForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.core.web.mvc.form.QuickSearchForm;
import org.hoteia.qalingo.core.web.mvc.form.RetailerForm;
import org.hoteia.qalingo.core.web.mvc.form.RuleForm;
import org.hoteia.qalingo.core.web.mvc.form.StoreForm;
import org.hoteia.qalingo.core.web.mvc.form.TaxForm;
import org.hoteia.qalingo.core.web.mvc.form.UserForm;
import org.hoteia.qalingo.core.web.mvc.form.WarehouseForm;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service("backofficeFormFactory")
public class BackofficeFormFactoryImpl implements BackofficeFormFactory {

    @Autowired
    protected CoreMessageSource coreMessageSource;

    @Autowired
    protected RequestUtil requestUtil;
    
    @Autowired
    protected BackofficeUrlService backofficeUrlService;
    
    @Autowired
    protected AttributeService attributeService;

    public EngineSettingForm buildEngineSettingForm(final RequestData requestData, final EngineSetting engineSetting) throws Exception {
        final EngineSettingForm engineSettingForm = new EngineSettingForm();
        if(engineSetting != null){
            engineSettingForm.setId(engineSetting.getId().toString());
            engineSettingForm.setCode(engineSetting.getCode());
            engineSettingForm.setVersion(engineSetting.getVersion());
            engineSettingForm.setName(engineSetting.getName());
            engineSettingForm.setDescription(engineSetting.getDescription());
            engineSettingForm.setDefaultValue(engineSetting.getDefaultValue());
        }
        return engineSettingForm;
    }
    
    public EngineSettingValueForm buildEngineSettingValueForm(final RequestData requestData, final EngineSettingValue engineSettingValue) throws Exception {
        final EngineSettingValueForm engineSettingValueForm = new EngineSettingValueForm();
        if(engineSettingValue != null){
            engineSettingValueForm.setId(engineSettingValue.getId().toString());
            engineSettingValueForm.setContext(engineSettingValue.getContext());
            engineSettingValueForm.setValue(engineSettingValue.getValue());
        }
        return engineSettingValueForm;
    }

    public PaymentGatewayForm buildPaymentGatewayForm(final MarketArea marketArea, final AbstractPaymentGateway paymentGateway) throws Exception {
        final PaymentGatewayForm paymentGatewayForm = new PaymentGatewayForm();
        if(paymentGateway != null){
            paymentGatewayForm.setId(paymentGateway.getId().toString());
            paymentGatewayForm.setVersion(paymentGateway.getVersion());
            paymentGatewayForm.setCode(paymentGateway.getCode());
            paymentGatewayForm.setName(paymentGateway.getName());
            paymentGatewayForm.setDescription(paymentGateway.getDescription());
            paymentGatewayForm.setActive(paymentGateway.getMarketAreas().contains(marketArea));
        }
        return paymentGatewayForm;
    }

    public UserForm buildUserForm(final RequestData requestData, final User user) throws Exception {
        final UserForm userForm = new UserForm();
        if(user != null){
            userForm.setId(user.getId().toString());
            userForm.setVersion(user.getVersion());
            userForm.setCode(user.getCode());
            userForm.setLogin(user.getLogin());
            userForm.setTitle(user.getTitle());
            userForm.setFirstname(user.getFirstname());
            userForm.setLastname(user.getLastname());
            userForm.setEmail(user.getEmail());
            userForm.setActive(user.isActive());
        }
        return userForm;
    }

    public QuickSearchForm buildEngineSettingQuickSearchForm(final RequestData requestData) throws Exception {
        final QuickSearchForm engineSettingQuickSearchForm = new QuickSearchForm();
        return engineSettingQuickSearchForm;
    }

    public QuickSearchForm buildUserQuickSearchForm(final RequestData requestData) throws Exception {
        final QuickSearchForm userQuickSearchForm = new QuickSearchForm();
        return userQuickSearchForm;
    }

    public QuickSearchForm buildBatchQuickSearchForm(final RequestData requestData) throws Exception {
        final QuickSearchForm batchQuickSearchForm = new QuickSearchForm();
        return batchQuickSearchForm;
    }

    public CatalogCategoryForm buildCatalogCategoryForm(final RequestData requestData) throws Exception {
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

    public CatalogCategoryForm buildCatalogCategoryForm(final RequestData requestData, final CatalogCategoryMaster catalogCategory) throws Exception {
        CatalogCategoryMaster parentProductCategory = catalogCategory.getDefaultParentCatalogCategory();
        return buildCatalogCategoryForm(requestData, parentProductCategory, catalogCategory);
    }

    public CatalogCategoryForm buildCatalogCategoryForm(final RequestData requestData, final CatalogCategoryMaster parentProductCategory, final CatalogCategoryMaster catalogCategory) throws Exception {
        final MarketArea currentMarketArea = requestData.getMarketArea();
        
        final CatalogCategoryForm catalogCategoryForm = buildCatalogCategoryForm(requestData);
        if(parentProductCategory != null){
            catalogCategoryForm.setDefaultParentCategoryCode(parentProductCategory.getCode());
        }
        if(catalogCategory != null){
            catalogCategoryForm.setId(catalogCategory.getId().toString());
            catalogCategoryForm.setCatalogCode(catalogCategory.getName());
            catalogCategoryForm.setName(catalogCategory.getName());
            catalogCategoryForm.setCode(catalogCategory.getCode());
            catalogCategoryForm.setDescription(catalogCategory.getDescription());
            
            List<CatalogCategoryMasterAttribute> globalAttributes = catalogCategory.getCatalogCategoryGlobalAttributes();
            for (Iterator<CatalogCategoryMasterAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                catalogCategoryForm.getGlobalAttributes().put(catalogCategoryMasterAttribute.getAttributeDefinition().getCode(), catalogCategoryMasterAttribute.getValueAsString());
            }
            
            List<CatalogCategoryMasterAttribute> marketAreaAttributes = catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId());
            for (Iterator<CatalogCategoryMasterAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                catalogCategoryForm.getMarketAreaAttributes().put(catalogCategoryMasterAttribute.getAttributeDefinition().getCode(), catalogCategoryMasterAttribute.getValueAsString());
            }
        }
        return catalogCategoryForm;
    }
    
    public CatalogCategoryForm buildCatalogCategoryForm(final RequestData requestData, final CatalogCategoryVirtual catalogCategory) throws Exception {
        CatalogCategoryVirtual parentProductCategory = catalogCategory.getDefaultParentCatalogCategory();
        return buildCatalogCategoryForm(requestData, parentProductCategory, parentProductCategory);
    }
    
    public CatalogCategoryForm buildCatalogCategoryForm(final RequestData requestData, final CatalogCategoryVirtual parentProductCategory, final CatalogCategoryVirtual catalogCategory) throws Exception {
        final CatalogCategoryForm catalogCategoryForm = buildCatalogCategoryForm(requestData);
        if(parentProductCategory != null){
            catalogCategoryForm.setDefaultParentCategoryCode(parentProductCategory.getCode());
        }
        if(catalogCategory != null){
            catalogCategoryForm.setId(catalogCategory.getId().toString());
            catalogCategoryForm.setCatalogCode(catalogCategory.getName());
            catalogCategoryForm.setName(catalogCategory.getName());
            catalogCategoryForm.setCode(catalogCategory.getCode());
            catalogCategoryForm.setDescription(catalogCategory.getDescription());
        }
        return catalogCategoryForm;
    }
    
    public ProductMarketingForm buildProductMarketingForm(final RequestData requestData, final ProductMarketing productMarketing) throws Exception {
        final MarketArea currentMarketArea = requestData.getMarketArea();
        
        final ProductMarketingForm productMarketingForm = new ProductMarketingForm();
        if(productMarketing != null){
            productMarketingForm.setId(productMarketing.getId().toString());
            productMarketingForm.setName(productMarketing.getName());
            productMarketingForm.setCode(productMarketing.getCode());
            productMarketingForm.setDescription(productMarketing.getDescription());
            
            List<ProductMarketingAttribute> globalAttributes = productMarketing.getProductMarketingGlobalAttributes();
            for (Iterator<ProductMarketingAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
                productMarketingForm.getGlobalAttributes().put(productMarketingAttribute.getAttributeDefinition().getCode(), productMarketingAttribute.getValueAsString());
            }
            
            List<ProductMarketingAttribute> marketAreaAttributes = productMarketing.getProductMarketingMarketAreaAttributes(currentMarketArea.getId());
            for (Iterator<ProductMarketingAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
                productMarketingForm.getMarketAreaAttributes().put(productMarketingAttribute.getAttributeDefinition().getCode(), productMarketingAttribute.getValueAsString());
            }
        }
        return productMarketingForm;
    }
    
    public AssetForm buildProductMarketingAssetForm(final RequestData requestData, final Asset productMarketingAsset) throws Exception {
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
    
    public ProductSkuForm buildProductSkuForm(final RequestData requestData, final ProductSku productSku) throws Exception {
        final MarketArea currentMarketArea = requestData.getMarketArea();
        
        final ProductSkuForm productSkuForm = new ProductSkuForm();
        if(productSku != null){
            
            productSkuForm.setId(productSku.getId().toString());
            productSkuForm.setName(productSku.getName());
            productSkuForm.setCode(productSku.getCode());
            productSkuForm.setDescription(productSku.getDescription());

            
            List<ProductSkuAttribute> globalAttributes = productSku.getProductSkuGlobalAttributes();
            for (Iterator<ProductSkuAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
                productSkuForm.getGlobalAttributes().put(productSkuAttribute.getAttributeDefinition().getCode(), productSkuAttribute.getValueAsString());
            }
            
            List<ProductSkuAttribute> marketAreaAttributes = productSku.getProductSkuMarketAreaAttributes(currentMarketArea.getId());
            for (Iterator<ProductSkuAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
                productSkuForm.getMarketAreaAttributes().put(productSkuAttribute.getAttributeDefinition().getCode(), productSkuAttribute.getValueAsString());
            }
        }
        return productSkuForm;
    }
    
    public CustomerForm buildCustomerForm(final RequestData requestData, final Customer customer) throws Exception {
        final CustomerForm customerForm = new CustomerForm();
        if(customer != null){
            customerForm.setId(customer.getId().toString());
            customerForm.setVersion(customer.getVersion());
            customerForm.setCode(customer.getCode());
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
    
    public OrderForm buildOrderForm(final RequestData requestData, final OrderCustomer order) throws Exception {
        final OrderForm orderForm = new OrderForm();
        if(order != null){
            orderForm.setId(order.getId().toString());
            orderForm.setVersion(order.getVersion());
            orderForm.setStatus(order.getStatus());
            orderForm.setOrderNum(order.getOrderNum());
            orderForm.setCustomerId(order.getCustomerId());
            orderForm.setBillingAddressId(order.getBillingAddress().getId());
            orderForm.setShippingAddressId(order.getShippingAddress().getId());
        }
        return orderForm;
    }
    
    public RuleForm buildRuleForm(final RequestData requestData, final AbstractRuleReferential rule) throws Exception {
        final RuleForm ruleForm = new RuleForm();
        if(rule != null){
            ruleForm.setId(rule.getId());
            ruleForm.setVersion(rule.getVersion());
            ruleForm.setCode(rule.getCode());
            ruleForm.setName(rule.getName());
            ruleForm.setDescription(rule.getDescription());
            ruleForm.setSalience(rule.getSalience());
        }
        return ruleForm;
    }
    
    public WarehouseForm buildWarehouseForm(final RequestData requestData, final Warehouse warehouse) throws Exception {
        final WarehouseForm warehouseForm = new WarehouseForm();
        if(warehouse != null){
            warehouseForm.setId(warehouse.getId().toString());
            warehouseForm.setVersion(warehouse.getVersion());
            warehouseForm.setCode(warehouse.getCode());
            warehouseForm.setName(warehouse.getName());
            warehouseForm.setDescription(warehouse.getDescription());
        }
        return warehouseForm;
    }
    
    public DeliveryMethodForm buildDeliveryMethodForm(final RequestData requestData, final DeliveryMethod deliveryMethod) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        
        final DeliveryMethodForm deliveryMethodForm = new DeliveryMethodForm();
        if(deliveryMethod != null){
            deliveryMethodForm.setId(deliveryMethod.getId().toString());
            deliveryMethodForm.setVersion(deliveryMethod.getVersion());
            deliveryMethodForm.setCode(deliveryMethod.getCode());
            deliveryMethodForm.setName(deliveryMethod.getName());
            deliveryMethodForm.setDescription(deliveryMethod.getDescription());
            deliveryMethodForm.setPrice(deliveryMethod.getPrice(marketArea.getId(), retailer.getId()));
        }
        return deliveryMethodForm;
    }
    
    public TaxForm buildTaxForm(final RequestData requestData, final Tax tax) throws Exception {
        final TaxForm taxForm = new TaxForm();
        if(tax != null){
            taxForm.setId(tax.getId().toString());
            taxForm.setCode(tax.getCode());
            taxForm.setVersion(tax.getVersion());
            taxForm.setName(tax.getName());
            taxForm.setDescription(tax.getDescription());
            taxForm.setPercent("" + tax.getPercent());
        }
        return taxForm;
    }
    
    public RetailerForm buildRetailerForm(final RequestData requestData, final Retailer retailer) throws Exception {
        final RetailerForm retailerForm = new RetailerForm();
        if(retailer != null){
            retailerForm.setId(retailer.getId().toString());
            retailerForm.setCode(retailer.getCode());
            retailerForm.setName(retailer.getName());
            retailerForm.setDescription(retailer.getDescription());
            
            retailerForm.setBrand(retailer.isBrand());
    		retailerForm.setCorner(retailer.isCorner());
    		retailerForm.setOfficialRetailer(retailer.isOfficialRetailer());
    		retailerForm.setEcommerce(retailer.isEcommerce());
    		
    		if(retailer.getWarehouse() != null){
    			retailerForm.setWarehouseId(retailer.getWarehouse().getId().toString());
    		}

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
    
    @Override
    public StoreForm buildStoreForm(RequestData requestData, Store store) throws Exception {
    	final StoreForm storeForm = new StoreForm();
    	if(store != null){
	    	storeForm.setId(store.getId().toString());
	    	storeForm.setCode(store.getCode());
	    	storeForm.setName(store.getName());
	    	storeForm.setRetailerId(store.getRetailer().getId().toString());
	    	
	    	storeForm.setAddress1(store.getAddress1());
	    	storeForm.setAddress2(store.getAddress2());
	    	storeForm.setAddressAdditionalInformation(store.getAddressAdditionalInformation());
	    	
	    	storeForm.setAreaCode(store.getAreaCode());
	    	storeForm.setCity(store.getCity());
	    	storeForm.setCountryCode(store.getCountryCode());
	    	storeForm.setLatitude(store.getLatitude());
	    	storeForm.setLongitude(store.getLongitude());
	    	storeForm.setPostalCode(store.getPostalCode());
	    	storeForm.setStateCode(store.getStateCode());    	
    	}
    	
    	return storeForm;
    }
    
}