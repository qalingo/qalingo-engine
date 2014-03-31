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

import java.util.List;

import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.BatchProcessObject;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.AssetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.AttributeDefinitionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.BatchViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.EngineSettingValueViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.EngineSettingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.GlobalSearchViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketAreaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentGatewayViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RuleViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.UserViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.WarehouseViewBean;

public interface BackofficeViewBeanFactory extends ViewBeanFactory {

    List<MenuViewBean> buildListViewBeanMorePageMenu(RequestData requestData) throws Exception;

    List<MarketViewBean> buildListViewBeanMarketByMarketPlace(RequestData requestData, MarketPlace marketPlace, List<Market> markets) throws Exception;

    List<MarketAreaViewBean> buildListViewBeanMarketAreaByMarket(RequestData requestData, Market market, List<MarketArea> marketAreas) throws Exception;

    List<LocalizationViewBean> buildListViewBeanLocalizationByMarketArea(RequestData requestData, List<Localization> localizations) throws Exception;

    List<LocalizationViewBean> buildListViewBeanLocalization(RequestData requestData, List<Localization> localizations) throws Exception;

    List<RetailerViewBean> buildListViewBeanRetailer(RequestData requestData) throws Exception;

    RetailerViewBean buildViewBeanRetailer(RequestData requestData, Retailer retailer) throws Exception;

    CatalogViewBean buildViewBeanMasterCatalog(RequestData requestData, CatalogMaster catalogVirtual, List<CatalogCategoryMaster> catalogCategories) throws Exception;

    CatalogViewBean buildViewBeanVirtualCatalog(RequestData requestData, CatalogVirtual catalogVirtual, List<CatalogCategoryVirtual> catalogCategories) throws Exception;

    List<CatalogCategoryViewBean> buildListViewBeanMasterCatalogCategory(RequestData requestData, List<CatalogCategoryMaster> catalogCategories, boolean fullPopulate) throws Exception;

    List<CatalogCategoryViewBean> buildListViewBeanVirtualCatalogCategory(RequestData requestData, List<CatalogCategoryVirtual> catalogCategories, boolean fullPopulate) throws Exception;

    CatalogCategoryViewBean buildViewBeanMasterCatalogCategory(RequestData requestData, CatalogCategoryMaster catalogCategory, boolean fullPopulate) throws Exception;

    CatalogCategoryViewBean buildViewBeanVirtualCatalogCategory(RequestData requestData, CatalogCategoryVirtual catalogCategory, boolean fullPopulate) throws Exception;

    ProductMarketingViewBean buildViewBeanProductMarketing(RequestData requestData, ProductMarketing productMarketing) throws Exception;

    ProductSkuViewBean buildViewBeanProductSku(RequestData requestData, ProductSku productSku) throws Exception;

    AssetViewBean buildViewBeanAsset(RequestData requestData, Asset asset) throws Exception;

    UserViewBean buildViewBeanUser(RequestData requestData, User user) throws Exception;

    List<UserViewBean> buildListViewBeanUser(RequestData requestData, List<User> users) throws Exception;

    OrderViewBean buildViewBeanOrder(RequestData requestData, OrderCustomer order) throws Exception;

    RuleViewBean buildViewBeanRule(RequestData requestData, AbstractRuleReferential rule) throws Exception;

    CustomerViewBean buildViewBeanCustomer(RequestData requestData, Customer customer) throws Exception;

    List<GlobalSearchViewBean> buildListViewBeanGlobalSearch(RequestData requestData, String searchText) throws Exception;

    List<EngineSettingViewBean> buildListViewBeanEngineSetting(RequestData requestData, List<EngineSetting> engineSettings) throws Exception;

    EngineSettingViewBean buildViewBeanEngineSetting(RequestData requestData, EngineSetting engineSetting) throws Exception;

    EngineSettingValueViewBean buildViewBeanEngineSettingValue(RequestData requestData, EngineSettingValue engineSettingValue) throws Exception;

    List<BatchViewBean> buildListViewBeanBatch(RequestData requestData, List<BatchProcessObject> batchProcessObjects) throws Exception;

    BatchViewBean buildViewBeanBatch(RequestData requestData, BatchProcessObject batchProcessObject) throws Exception;

    WarehouseViewBean buildViewBeanWarehouse(RequestData requestData, Warehouse warehouse) throws Exception;

    List<PaymentGatewayViewBean> buildListViewBeanPaymentGateway(RequestData requestData, List<AbstractPaymentGateway> paymentGateways) throws Exception;

    PaymentGatewayViewBean buildViewBeanPaymentGateway(RequestData requestData, AbstractPaymentGateway paymentGateway) throws Exception;

    List<AttributeDefinitionViewBean> buildListViewBeanAttributeDefinition(RequestData requestData, List<AttributeDefinition> attributeDefinitions) throws Exception;

}