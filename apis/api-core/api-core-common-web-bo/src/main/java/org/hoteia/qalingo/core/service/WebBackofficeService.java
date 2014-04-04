/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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

    void createOrUpdatePersonalUser(User user, UserForm userForm);

    void createOrUpdateUser(User user, UserForm userForm);

    void createOrUpdateCustomer(Customer customer, CustomerForm customerForm) throws Exception;

    void createCatalogCategory(MarketArea marketArea, Localization currentLocalization, CatalogCategoryMaster parentCatalogCategory, CatalogCategoryMaster catalogCategory,
                               CatalogCategoryForm catalogCategoryForm) throws UniqueConstraintCodeCategoryException;

    void updateCatalogCategory(MarketArea marketArea, Retailer currentRetailer, Localization currentLocalization, CatalogCategoryMaster catalogCategory, CatalogCategoryForm catalogCategoryForm)
                               throws UniqueConstraintCodeCategoryException;

    void createCatalogCategory(MarketArea marketArea, Localization currentLocalization, CatalogCategoryVirtual catalogCategory, CatalogCategoryForm catalogCategoryForm);

    void updateCatalogCategory(MarketArea marketArea, Retailer currentRetailer, Localization currentLocalization, CatalogCategoryVirtual catalogCategory, CatalogCategoryForm catalogCategoryForm);

    void createOrUpdateProductMarketing(ProductMarketing productMarketing, ProductMarketingForm productMarketingForm);

    void createOrUpdateProductSku(ProductSku productSku, ProductSkuForm productSkuForm);

    void createOrUpdateProductMarketingAsset(Asset asset, AssetForm assetForm);

    void createOrUpdateRetailer(Retailer retailer, RetailerForm retailerForm) throws Exception;

    void createOrUpdateWarehouse(RequestData requestData, Warehouse warehouse, WarehouseForm warehouseForm);
    
    void createOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod, DeliveryMethodForm deliveryMethodForm);

    void createOrUpdateTax(Tax tax, TaxForm taxForm);

    void updateEngineSettingValue(EngineSettingValue engineSettingValue, EngineSettingValueForm engineSettingValueForm);

    void createOrUpdatePaymentGateway(MarketArea marketArea, AbstractPaymentGateway paymentGateway, PaymentGatewayForm paymentGatewayForm);

    void createOrUpdateEngineSetting(EngineSetting engineSetting, EngineSettingForm engineSettingForm);
    
    void createOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue, EngineSettingValueForm engineSettingValueForm);
    
    void createOrUpdateStore(Retailer retailer, Store store, StoreForm storeForm) throws Exception;
}
