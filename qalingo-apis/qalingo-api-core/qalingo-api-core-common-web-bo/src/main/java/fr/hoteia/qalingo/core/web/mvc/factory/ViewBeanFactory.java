/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.mvc.factory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import fr.hoteia.qalingo.core.domain.AbstractRuleReferential;
import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.BatchProcessObject;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.CurrencyReferential;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Shipping;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.service.pojo.RequestData;
import fr.hoteia.qalingo.web.mvc.viewbean.AssetViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.BatchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CatalogCategoryViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CatalogViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CurrencyReferentialViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingDetailsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingValueEditViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.GlobalSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegalTermsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketAreaViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.PaymentGatewayViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductMarketingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductSkuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RuleViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ShippingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserViewBean;

public interface ViewBeanFactory {

	CommonViewBean buildCommonViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	List<MenuViewBean> buildMenuViewBeans(HttpServletRequest request, RequestData requestData) throws Exception;

	List<MenuViewBean> buildMorePageMenuViewBeans(HttpServletRequest request, RequestData requestData) throws Exception;

	List<FooterMenuViewBean> buildFooterMenuViewBeans(HttpServletRequest request, RequestData requestData) throws Exception;
	
	List<MarketPlaceViewBean> buildMarketPlaceViewBeans(HttpServletRequest request, RequestData requestData) throws Exception;
	
	MarketPlaceViewBean buildMarketPlaceViewBean(HttpServletRequest request, RequestData requestData, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildMarketViewBeansByMarketPlace(HttpServletRequest request, RequestData requestData, MarketPlace marketPlace, List<Market> markets) throws Exception;
	
	List<MarketAreaViewBean> buildMarketAreaViewBeansByMarket(HttpServletRequest request, RequestData requestData, Market market, List<MarketArea> marketAreas) throws Exception;
	
	List<LocalizationViewBean> buildLocalizationViewBeansByMarketArea(HttpServletRequest request, RequestData requestData, List<Localization> localizations) throws Exception;

	List<LocalizationViewBean> buildLocalizationViewBeans(HttpServletRequest request, RequestData requestData, List<Localization> localizations) throws Exception;

	LocalizationViewBean buildLocalizationViewBean(HttpServletRequest request, RequestData requestData, Localization localization) throws Exception;

	List<RetailerViewBean> buildRetailerViewBeans(HttpServletRequest request, RequestData requestData) throws Exception;
	
	CatalogViewBean buildMasterCatalogViewBean(HttpServletRequest request, RequestData requestData, CatalogMaster catalogVirtual, List<CatalogCategoryMaster> productCategories) throws Exception;

	CatalogViewBean buildVirtualCatalogViewBean(HttpServletRequest request, RequestData requestData, CatalogVirtual catalogVirtual, List<CatalogCategoryVirtual> productCategories) throws Exception;

	List<CatalogCategoryViewBean> buildMasterProductCategoryViewBeans(HttpServletRequest request, RequestData requestData, List<CatalogCategoryMaster> productCategories, boolean fullPopulate) throws Exception;

	List<CatalogCategoryViewBean> buildVirtualProductCategoryViewBeans(HttpServletRequest request, RequestData requestData, List<CatalogCategoryVirtual> productCategories, boolean fullPopulate) throws Exception;
	
	CatalogCategoryViewBean buildMasterProductCategoryViewBean(HttpServletRequest request, RequestData requestData, CatalogCategoryMaster productCategory, boolean fullPopulate) throws Exception;

	CatalogCategoryViewBean buildVirtualProductCategoryViewBean(HttpServletRequest request, RequestData requestData, CatalogCategoryVirtual productCategory, boolean fullPopulate) throws Exception;

	List<ProductMarketingViewBean> buildProductMarketingViewBeans(HttpServletRequest request, RequestData requestData, List<ProductMarketing> productMarketings, boolean withDependency) throws Exception;
	
	ProductMarketingViewBean buildProductMarketingViewBean(HttpServletRequest request, RequestData requestData, ProductMarketing productMarketing, boolean withDependency) throws Exception;

	ProductSkuViewBean buildProductSkuViewBean(HttpServletRequest request, RequestData requestData, ProductSku productSku) throws Exception;

	AssetViewBean buildAssetViewBean(HttpServletRequest request, RequestData requestData, Asset asset) throws Exception;
	
	LegalTermsViewBean buildLegalTermsViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	SecurityViewBean buildSecurityViewBean(HttpServletRequest request, RequestData requestData) throws Exception;

	UserViewBean buildUserViewBean(HttpServletRequest request, RequestData requestData, User user) throws Exception;
	
	List<UserViewBean> buildUserViewBeans(HttpServletRequest request, RequestData requestData, List<User> users) throws Exception;
	
	ShippingViewBean buildShippingViewBean(HttpServletRequest request, RequestData requestData, Shipping shipping) throws Exception;
	
	OrderViewBean buildOrderViewBean(HttpServletRequest request, RequestData requestData, Order order) throws Exception;
	
	RuleViewBean buildRuleViewBean(HttpServletRequest request, RequestData requestData, AbstractRuleReferential rule) throws Exception;
	
	CustomerViewBean buildCustomerViewBean(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception;
	
	List<GlobalSearchViewBean> buildGlobalSearchViewBean(HttpServletRequest request, RequestData requestData, String searchText) throws Exception;

	List<EngineSettingViewBean> buildEngineSettingViewBeans(HttpServletRequest request, List<EngineSetting> engineSettings) throws Exception;
	
	EngineSettingViewBean buildEngineSettingViewBean(HttpServletRequest request, EngineSetting engineSetting) throws Exception;
	
	EngineSettingDetailsViewBean buildEngineSettingDetailsViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	EngineSettingValueEditViewBean buildEngineSettingValueEditViewBean(HttpServletRequest request, RequestData requestData, EngineSettingValue engineSettingValue) throws Exception;

	List<BatchViewBean> buildBatchViewBeans(HttpServletRequest request, RequestData requestData, List<BatchProcessObject> batchProcessObjects) throws Exception;
	
	BatchViewBean buildBatchViewBean(HttpServletRequest request, RequestData requestData, BatchProcessObject batchProcessObject) throws Exception;
	
	List<CurrencyReferentialViewBean> buildCurrencyReferentialViewBeans(HttpServletRequest request, List<CurrencyReferential> currencyReferentials) throws Exception;
	
	CurrencyReferentialViewBean buildCurrencyReferentialViewBean(HttpServletRequest request, CurrencyReferential currencyReferential) throws Exception;
	
	List<PaymentGatewayViewBean> buildPaymentGatewayViewBeans(HttpServletRequest request, List<AbstractPaymentGateway> paymentGateways) throws Exception;
	
	PaymentGatewayViewBean buildPaymentGatewayViewBean(HttpServletRequest request, AbstractPaymentGateway paymentGateway) throws Exception;

}
