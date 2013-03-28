/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.web.mvc.viewbean.CatalogViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketAreaViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductCategoryViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductMarketingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductSkuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserViewBean;

public interface ViewBeanFactory {

	CommonViewBean buildCommonViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer) throws Exception;
	
	List<MenuViewBean> buildMenuViewBeans(HttpServletRequest request, Localization localization) throws Exception;

	List<MenuViewBean> buildMorePageMenuViewBeans(HttpServletRequest request, Localization localization) throws Exception;

	List<FooterMenuViewBean> buildFooterMenuViewBeans(HttpServletRequest request, Localization localization) throws Exception;
	
	List<MarketPlaceViewBean> buildMarketPlaceViewBeans(HttpServletRequest request, Localization localization) throws Exception;
	
	MarketPlaceViewBean buildMarketPlaceViewBean(HttpServletRequest request, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildMarketViewBeans(HttpServletRequest request, MarketPlace marketPlace, List<Market> markets, Localization localization) throws Exception;
	
	List<MarketAreaViewBean> buildMarketAreaViewBeans(HttpServletRequest request, Market market, List<MarketArea> marketAreas, Localization localization) throws Exception;
	
	List<LocalizationViewBean> buildLocalizationViewBeans(HttpServletRequest request, MarketArea marketArea, Localization localization) throws Exception;

	List<LocalizationViewBean> buildLocalizationViewBeans(HttpServletRequest request, List<Localization> localizations) throws Exception;

	List<RetailerViewBean> buildRetailerViewBeans(HttpServletRequest request, MarketArea marketArea, Localization localization) throws Exception;
	
	CatalogViewBean buildMasterCatalogViewBean(HttpServletRequest request, MarketArea marketArea, Localization localization, CatalogMaster catalogVirtual, List<CatalogCategoryMaster> productCategories) throws Exception;

	CatalogViewBean buildVirtualCatalogViewBean(HttpServletRequest request, MarketArea marketArea, Localization localization, CatalogVirtual catalogVirtual, List<CatalogCategoryVirtual> productCategories) throws Exception;

	List<ProductCategoryViewBean> buildMasterProductCategoryViewBeans(HttpServletRequest request, MarketArea marketArea, Localization localization, List<CatalogCategoryMaster> productCategories, boolean fullPopulate) throws Exception;

	List<ProductCategoryViewBean> buildVirtualProductCategoryViewBeans(HttpServletRequest request, MarketArea marketArea, Localization localization, List<CatalogCategoryVirtual> productCategories, boolean fullPopulate) throws Exception;
	
	ProductCategoryViewBean buildMasterProductCategoryViewBean(HttpServletRequest request, MarketArea marketArea, Localization localization, CatalogCategoryMaster productCategory, boolean fullPopulate) throws Exception;

	ProductCategoryViewBean buildVirtualProductCategoryViewBean(HttpServletRequest request, MarketArea marketArea, Localization localization, CatalogCategoryVirtual productCategory, boolean fullPopulate) throws Exception;

	List<ProductMarketingViewBean> buildProductMarketingViewBeans(HttpServletRequest request, MarketArea marketArea, Localization localization, List<ProductMarketing> productMarketings, boolean withDependency) throws Exception;
	
	ProductMarketingViewBean buildProductMarketingViewBean(HttpServletRequest request, MarketArea marketArea, Localization localization, ProductMarketing productMarketing, boolean withDependency) throws Exception;

	ProductSkuViewBean buildProductSkuViewBean(HttpServletRequest request, MarketArea marketArea, Localization localization, ProductSku productSku) throws Exception;
	
	LocalizationViewBean buildLocalizationViewBean(HttpServletRequest request, Localization localization) throws Exception;

	LegacyViewBean buildLegacyViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	SecurityViewBean buildSecurityViewBean(HttpServletRequest request, Localization localization) throws Exception;

	QuickSearchViewBean buildQuickSearchViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	UserViewBean buildUserViewBean(HttpServletRequest request, Localization localization, User user) throws Exception;
	
//	UserEditViewBean buildUserEditViewBean(HttpServletRequest request, Localization localization, User user) throws Exception;
}