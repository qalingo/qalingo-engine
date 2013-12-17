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
import org.hoteia.qalingo.core.domain.BatchProcessObject;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.AssetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.BatchViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CurrencyReferentialViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
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
import org.hoteia.qalingo.core.web.mvc.viewbean.DeliveryMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.UserViewBean;

public interface BackofficeViewBeanFactory extends ViewBeanFactory {

//	CommonViewBean buildCommonViewBean(RequestData requestData) throws Exception;
//	
//	List<MenuViewBean> buildMenuViewBeans(RequestData requestData) throws Exception;

	List<MenuViewBean> buildMorePageMenuViewBeans(RequestData requestData) throws Exception;

//	List<FooterMenuViewBean> buildFooterMenuViewBeans(RequestData requestData) throws Exception;
//	
//	List<MarketPlaceViewBean> buildMarketPlaceViewBeans(RequestData requestData) throws Exception;
//	
//	MarketPlaceViewBean buildMarketPlaceViewBean(RequestData requestData, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildMarketViewBeansByMarketPlace(RequestData requestData, MarketPlace marketPlace, List<Market> markets) throws Exception;
	
	List<MarketAreaViewBean> buildMarketAreaViewBeansByMarket(RequestData requestData, Market market, List<MarketArea> marketAreas) throws Exception;
	
	List<LocalizationViewBean> buildLocalizationViewBeansByMarketArea(RequestData requestData, List<Localization> localizations) throws Exception;

	List<LocalizationViewBean> buildLocalizationViewBeans(RequestData requestData, List<Localization> localizations) throws Exception;

//	LocalizationViewBean buildLocalizationViewBean(RequestData requestData, Localization localization) throws Exception;

	List<RetailerViewBean> buildRetailerViewBeans(RequestData requestData) throws Exception;
	
	RetailerViewBean buildRetailerViewBean(RequestData requestData, Retailer retailer) throws Exception;
	
	CatalogViewBean buildMasterCatalogViewBean(RequestData requestData, CatalogMaster catalogVirtual, List<CatalogCategoryMaster> catalogCategories) throws Exception;

	CatalogViewBean buildVirtualCatalogViewBean(RequestData requestData, CatalogVirtual catalogVirtual, List<CatalogCategoryVirtual> catalogCategories) throws Exception;

	List<CatalogCategoryViewBean> buildMasterCatalogCategoryViewBeans(RequestData requestData, List<CatalogCategoryMaster> catalogCategories, boolean fullPopulate) throws Exception;

	List<CatalogCategoryViewBean> buildVirtualCatalogCategoryViewBeans(RequestData requestData, List<CatalogCategoryVirtual> catalogCategories, boolean fullPopulate) throws Exception;
	
	CatalogCategoryViewBean buildMasterCatalogCategoryViewBean(RequestData requestData, CatalogCategoryMaster catalogCategory, boolean fullPopulate) throws Exception;

	CatalogCategoryViewBean buildVirtualCatalogCategoryViewBean(RequestData requestData, CatalogCategoryVirtual catalogCategory, boolean fullPopulate) throws Exception;

	List<ProductMarketingViewBean> buildProductMarketingViewBeans(RequestData requestData, List<ProductMarketing> productMarketings, boolean withDependency) throws Exception;
	
	ProductMarketingViewBean buildProductMarketingViewBean(RequestData requestData, ProductMarketing productMarketing, boolean withDependency) throws Exception;

	ProductSkuViewBean buildProductSkuViewBean(RequestData requestData, ProductSku productSku) throws Exception;

	AssetViewBean buildAssetViewBean(RequestData requestData, Asset asset) throws Exception;
	
//	LegalTermsViewBean buildLegalTermsViewBean(RequestData requestData) throws Exception;
//	
//	SecurityViewBean buildSecurityViewBean(RequestData requestData) throws Exception;

	UserViewBean buildUserViewBean(RequestData requestData, User user) throws Exception;
	
	List<UserViewBean> buildUserViewBeans(RequestData requestData, List<User> users) throws Exception;
	
	OrderViewBean buildOrderViewBean(RequestData requestData, OrderCustomer order) throws Exception;
	
	RuleViewBean buildRuleViewBean(RequestData requestData, AbstractRuleReferential rule) throws Exception;
	
	CustomerViewBean buildCustomerViewBean(RequestData requestData, Customer customer) throws Exception;
	
	List<GlobalSearchViewBean> buildGlobalSearchViewBean(RequestData requestData, String searchText) throws Exception;

	List<EngineSettingViewBean> buildEngineSettingViewBeans(RequestData requestData, List<EngineSetting> engineSettings) throws Exception;
	
	EngineSettingViewBean buildEngineSettingViewBean(RequestData requestData, EngineSetting engineSetting) throws Exception;
	
	List<BatchViewBean> buildBatchViewBeans(RequestData requestData, List<BatchProcessObject> batchProcessObjects) throws Exception;
	
	BatchViewBean buildBatchViewBean(RequestData requestData, BatchProcessObject batchProcessObject) throws Exception;
	
	List<PaymentGatewayViewBean> buildPaymentGatewayViewBeans(RequestData requestData, List<AbstractPaymentGateway> paymentGateways) throws Exception;
	
	PaymentGatewayViewBean buildPaymentGatewayViewBean(RequestData requestData, AbstractPaymentGateway paymentGateway) throws Exception;

}