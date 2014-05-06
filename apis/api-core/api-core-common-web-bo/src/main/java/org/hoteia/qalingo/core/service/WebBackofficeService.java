/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import org.hoteia.qalingo.core.pojo.RequestData;
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
import org.hoteia.qalingo.core.web.mvc.form.StoreForm;
import org.hoteia.qalingo.core.web.mvc.form.TaxForm;
import org.hoteia.qalingo.core.web.mvc.form.UserForm;
import org.hoteia.qalingo.core.web.mvc.form.WarehouseForm;

public interface WebBackofficeService {

    User createOrUpdatePersonalUser(User user, UserForm userForm);

    User createOrUpdateUser(User user, UserForm userForm);

    Customer createOrUpdateCustomer(Customer customer, CustomerForm customerForm) throws Exception;

    CatalogCategoryMaster createCatalogCategory(RequestData requestData, MarketArea marketArea, Localization localization, CatalogCategoryMaster parentCatalogCategory, CatalogCategoryMaster catalogCategory,
                               CatalogCategoryForm catalogCategoryForm) throws UniqueConstraintCodeCategoryException;

    CatalogCategoryMaster updateCatalogCategory(RequestData requestData, MarketArea marketArea, Retailer retailer, Localization localization, CatalogCategoryMaster catalogCategory, CatalogCategoryForm catalogCategoryForm)
                               throws UniqueConstraintCodeCategoryException;

    CatalogCategoryVirtual createCatalogCategory(RequestData requestData, MarketArea marketArea, Localization localization, CatalogCategoryVirtual parentCatalogCategory, CatalogCategoryVirtual catalogCategory, 
                               CatalogCategoryForm catalogCategoryForm) throws UniqueConstraintCodeCategoryException;

    CatalogCategoryVirtual updateCatalogCategory(RequestData requestData, MarketArea marketArea, Retailer retailer, Localization localization, CatalogCategoryVirtual catalogCategory, CatalogCategoryForm catalogCategoryForm);

    ProductMarketing createOrUpdateProductMarketing(ProductMarketing productMarketing, ProductMarketingForm productMarketingForm);

    ProductSku createOrUpdateProductSku(ProductSku productSku, ProductSkuForm productSkuForm);

    Asset createOrUpdateProductMarketingAsset(Asset asset, AssetForm assetForm);

    Retailer createOrUpdateRetailer(Retailer retailer, RetailerForm retailerForm) throws Exception;

    Warehouse createOrUpdateWarehouse(RequestData requestData, Warehouse warehouse, WarehouseForm warehouseForm);
    
    DeliveryMethod createOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod, DeliveryMethodForm deliveryMethodForm);

    Tax createOrUpdateTax(Tax tax, TaxForm taxForm);

    EngineSettingValue updateEngineSettingValue(EngineSettingValue engineSettingValue, EngineSettingValueForm engineSettingValueForm);

    AbstractPaymentGateway createOrUpdatePaymentGateway(MarketArea marketArea, AbstractPaymentGateway paymentGateway, PaymentGatewayForm paymentGatewayForm);

    EngineSetting createOrUpdateEngineSetting(EngineSetting engineSetting, EngineSettingForm engineSettingForm);
    
    EngineSettingValue createOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue, EngineSettingValueForm engineSettingValueForm);
    
    Store createOrUpdateStore(Retailer retailer, Store store, StoreForm storeForm) throws Exception;
}
