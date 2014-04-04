/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory;

import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.pojo.RequestData;
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

public interface BackofficeFormFactory {

    EngineSettingForm buildEngineSettingForm(RequestData requestData, EngineSetting engineSetting) throws Exception;

    EngineSettingValueForm buildEngineSettingValueForm(RequestData requestData, EngineSettingValue engineSettingValue) throws Exception;

	PaymentGatewayForm buildPaymentGatewayForm(MarketArea marketArea, AbstractPaymentGateway paymentGateway) throws Exception;

    UserForm buildUserForm(RequestData requestData, User user) throws Exception;

    QuickSearchForm buildEngineSettingQuickSearchForm(RequestData requestData) throws Exception;
	
    QuickSearchForm buildUserQuickSearchForm(RequestData requestData) throws Exception;

	QuickSearchForm buildBatchQuickSearchForm(RequestData requestData) throws Exception;
	
    CatalogCategoryForm buildCatalogCategoryForm(RequestData requestData) throws Exception;

    CatalogCategoryForm buildCatalogCategoryForm(RequestData requestData, CatalogCategoryMaster catalogCategory) throws Exception;

    CatalogCategoryForm buildCatalogCategoryForm(RequestData requestData, CatalogCategoryMaster parentProductCategory, CatalogCategoryMaster catalogCategory) throws Exception;

    CatalogCategoryForm buildCatalogCategoryForm(RequestData requestData, CatalogCategoryVirtual catalogCategory) throws Exception;

    CatalogCategoryForm buildCatalogCategoryForm(RequestData requestData, CatalogCategoryVirtual parentProductCategory, CatalogCategoryVirtual catalogCategory) throws Exception;

    ProductMarketingForm buildProductMarketingForm(RequestData requestData, ProductMarketing productMarketing) throws Exception;
    
    AssetForm buildProductMarketingAssetForm(RequestData requestData, Asset productMarketingAsset) throws Exception;
    
    ProductSkuForm buildProductSkuForm(RequestData requestData, ProductSku productSku) throws Exception;
    
    CustomerForm buildCustomerForm(RequestData requestData, Customer customer) throws Exception;
    
    OrderForm buildOrderForm(RequestData requestData, OrderCustomer order) throws Exception;
    
    RuleForm buildRuleForm(RequestData requestData, AbstractRuleReferential promotion) throws Exception;
    
    WarehouseForm buildWarehouseForm(RequestData requestData, Warehouse warehouse) throws Exception;

    DeliveryMethodForm buildDeliveryMethodForm(RequestData requestData, DeliveryMethod deliveryMethod) throws Exception;

    TaxForm buildTaxForm(RequestData requestData, Tax tax) throws Exception;

    RetailerForm buildRetailerForm(RequestData requestData, Retailer retailer) throws Exception;
    
    StoreForm buildStoreForm(RequestData requestData, Store store) throws Exception;

}
