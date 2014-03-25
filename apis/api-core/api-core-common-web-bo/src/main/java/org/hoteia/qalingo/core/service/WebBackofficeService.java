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

import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import org.hoteia.qalingo.core.web.mvc.form.AssetForm;
import org.hoteia.qalingo.core.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.core.web.mvc.form.DeliveryMethodForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingValueForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.core.web.mvc.form.RetailerForm;
import org.hoteia.qalingo.core.web.mvc.form.UserForm;
import org.hoteia.qalingo.core.web.mvc.form.WarehouseForm;

public interface WebBackofficeService {

    void updateUser(User user, UserForm userForm);

    void createCatalogCategory(MarketArea currentMarketArea, Localization currentLocalization, CatalogCategoryMaster parentCatalogCategory, CatalogCategoryMaster catalogCategory,
                               CatalogCategoryForm catalogCategoryForm) throws UniqueConstraintCodeCategoryException;

    void updateCatalogCategory(MarketArea currentMarketArea, Retailer currentRetailer, Localization currentLocalization, CatalogCategoryMaster catalogCategory, CatalogCategoryForm catalogCategoryForm)
                               throws UniqueConstraintCodeCategoryException;

    void createCatalogCategory(MarketArea currentMarketArea, Localization currentLocalization, CatalogCategoryVirtual catalogCategory, CatalogCategoryForm catalogCategoryForm);

    void updateCatalogCategory(MarketArea currentMarketArea, Retailer currentRetailer, Localization currentLocalization, CatalogCategoryVirtual catalogCategory, CatalogCategoryForm catalogCategoryForm);

    void updateProductMarketing(ProductMarketing productMarketing, ProductMarketingForm productMarketingForm);

    void createProductMarketing(ProductMarketing productMarketing, ProductMarketingForm productMarketingForm);

    void updateProductSku(ProductSku productSku, ProductSkuForm productSkuForm);

    void createProductSku(ProductSku productSku, ProductSkuForm productSkuForm);

    void updateProductMarketingAsset(Asset asset, AssetForm assetForm);

    void createProductMarketingAsset(Asset asset, AssetForm assetForm);

    void createOrUpdateRetailer(Retailer retailer, RetailerForm retailerForm);

    void createOrUpdateWarehouse(Warehouse warehouse, WarehouseForm warehouseForm);
    
    void createOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod, DeliveryMethodForm deliveryMethodForm);
    
    void updateEngineSettingValue(EngineSettingValue engineSettingValue, EngineSettingValueForm engineSettingValueForm);

}
