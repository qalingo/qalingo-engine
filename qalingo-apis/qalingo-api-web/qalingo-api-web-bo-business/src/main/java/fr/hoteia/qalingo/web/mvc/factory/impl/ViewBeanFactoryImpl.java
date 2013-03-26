/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.ProductBrand;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryMasterAttribute;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtualAttribute;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.ProductSkuAttribute;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.domain.UserConnectionLog;
import fr.hoteia.qalingo.core.domain.UserGroup;
import fr.hoteia.qalingo.core.domain.UserPermission;
import fr.hoteia.qalingo.core.domain.UserRole;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.MarketPlaceService;
import fr.hoteia.qalingo.core.web.cache.util.WebCacheHelper;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.BrandViewBean;
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
import fr.hoteia.qalingo.web.mvc.viewbean.UserConnectionLogValueBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserViewBean;
import fr.hoteia.qalingo.web.service.BackofficeUrlService;

/**
 * 
 */
@Service("viewBeanFactory")
public class ViewBeanFactoryImpl implements ViewBeanFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected BackofficeUrlService backofficeUrlService;
	
	@Autowired
	protected MarketPlaceService marketPlaceService;
	
	@Autowired
	@Qualifier("menuMarketNavigationCacheHelper")
    protected WebCacheHelper menuMarketNavigationCacheHelper;
	
	/**
     * 
     */
	public CommonViewBean buildCommonViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale  = requestUtil.getCurrentLocale(request);
		
		final CommonViewBean commonViewBean = new CommonViewBean();
		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request, EngineSettingService.ENGINE_SETTING_CONTEXT_BO_BUSINESS);
		commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

		commonViewBean.setHomeUrl(backofficeUrlService.buildHomeUrl());
		commonViewBean.setLoginUrl(backofficeUrlService.buildLoginUrl());
		commonViewBean.setLoginLabel(coreMessageSource.getMessage("header.link.login", null, locale));
//		commonViewBean.setForgottenPasswordUrl(backofficeUrlService.buildContactUrl(request));
		commonViewBean.setLogoutUrl(backofficeUrlService.buildLogoutUrl());
		commonViewBean.setLogoutLabel(coreMessageSource.getMessage("header.link.logout", null, locale));
		commonViewBean.setUserDetailsUrl(backofficeUrlService.buildUserDetailsUrl());
		commonViewBean.setUserDetailsLabel(coreMessageSource.getMessage("header.link.my.account", null, locale));
		
		commonViewBean.setCurrentMarketPlace(buildMarketPlaceViewBean(request, marketPlace));
		commonViewBean.setCurrentMarket(buildMarketViewBean(request, market));
		commonViewBean.setCurrentMarketArea(buildMarketAreaViewBean(request, marketArea));
		commonViewBean.setCurrentMarketLocalization(buildLocalizationViewBean(request, marketArea, localization));
		commonViewBean.setCurrentRetailer(buildRetailerViewBean(request, marketArea, localization, retailer));
		
		return commonViewBean;
	}
	
	/**
     * 
     */
	public List<MenuViewBean> buildMenuViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		final String currentUrl = requestUtil.getCurrentRequestUrl(request);
		
		final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
		
		MenuViewBean menu = new MenuViewBean();
		if(currentUrl.contains("home")){
			menu.setCssClass("active");
		}
		menu.setCssIcon("icon-home");
		menu.setName(coreMessageSource.getMessage("header.menu.home", null, locale));
		menu.setUrl(backofficeUrlService.buildHomeUrl());
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		if(currentUrl.contains("catalog")){
			menu.setCssClass("dropdown active");
		} else {
			menu.setCssClass("dropdown");
		}
		menu.setCssIcon("icon-sitemap");
		menu.setName("Catalog");
		menuViewBeans.add(menu);
		
		MenuViewBean subMenu = new MenuViewBean();
		subMenu.setName("Manage Master Catalog");
		subMenu.setUrl(backofficeUrlService.buildManageMasterCatalogUrl());
		menu.getSubMenus().add(subMenu);
		
		subMenu = new MenuViewBean();
		subMenu.setName("Manage Virtual Catalog");
		subMenu.setUrl(backofficeUrlService.buildManageVirtualCatalogUrl());
		menu.getSubMenus().add(subMenu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-money");
		menu.setName("Promotion");
		menu.setUrl(backofficeUrlService.buildPromotionUrl());
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setCssIcon("icon-truck");
		menu.setName("Shipping");
		menu.setUrl(backofficeUrlService.buildShippingUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-shopping-cart");
		menu.setName("Orders");
		menu.setUrl(backofficeUrlService.buildOrderListUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-group");
		menu.setName("Customers");
		menu.setUrl(backofficeUrlService.buildCustomerListUrl());
		menuViewBeans.add(menu);
		
		return menuViewBeans;
	}
	
	/**
     * 
     */
	public List<MenuViewBean> buildMorePageMenuViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
		
		// TODO : denis : move this part in the global list menu java/vm
		
		MenuViewBean menu = new MenuViewBean();
		menu.setCssIcon("icon-paper-clip");
		menu.setName("FAQ");
		menu.setUrl(backofficeUrlService.buildFaqUrl());
		menuViewBeans.add(menu);
		
		return menuViewBeans;
	}
	
	/**
     * 
     */
	public List<FooterMenuViewBean> buildFooterMenuViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final List<FooterMenuViewBean> footerMenuViewBeans = new ArrayList<FooterMenuViewBean>();
		
		FooterMenuViewBean footerMenuList = new FooterMenuViewBean();
		footerMenuList.setName(coreMessageSource.getMessage("header.menu.home", null, locale));
		footerMenuList.setUrl(backofficeUrlService.buildHomeUrl());
		footerMenuViewBeans.add(footerMenuList);
		
		return footerMenuViewBeans;
	}
	
	/**
     * 
     */
	public List<MarketPlaceViewBean> buildMarketPlaceViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType marketPlaceElementType = WebCacheHelper.ElementType.MARKET_PLACE_NAVIGATION_VIEW_BEAN_LIST;
		final String marketPlacePrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String marketPlaceCacheKey = marketPlacePrefixCacheKey + "_MARKETPLACE_LIST";
		List<MarketPlaceViewBean> marketPlaceViewBeans = (List<MarketPlaceViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketPlaceElementType, marketPlaceCacheKey);
		if(marketPlaceViewBeans == null){
			marketPlaceViewBeans = new ArrayList<MarketPlaceViewBean>();
			final List<MarketPlace> marketPlaceList = marketPlaceService.findMarketPlaces();
			for (Iterator<MarketPlace> iteratorMarketPlace = marketPlaceList.iterator(); iteratorMarketPlace.hasNext();) {
				final MarketPlace marketPlaceNavigation = (MarketPlace) iteratorMarketPlace.next();
				marketPlaceViewBeans.add(buildMarketPlaceViewBean(request, marketPlaceNavigation));
			}
			menuMarketNavigationCacheHelper.addToCache(marketPlaceElementType, marketPlaceCacheKey, marketPlaceViewBeans);
		}
		return marketPlaceViewBeans;
	}
	
	/**
     * 
     */
	public MarketPlaceViewBean buildMarketPlaceViewBean(final HttpServletRequest request, final MarketPlace marketPlace) throws Exception {
		final Market defaultMarket = marketPlace.getDefaultMarket();
		final MarketArea defaultMarketArea = defaultMarket.getDefaultMarketArea();
		final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
		final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();
		
		MarketPlaceViewBean marketPlaceViewBean = new MarketPlaceViewBean();
		marketPlaceViewBean.setName(marketPlace.getName());
		marketPlaceViewBean.setUrl(backofficeUrlService.buildChangeContextUrl(marketPlace, defaultMarket, defaultMarketArea, defaultLocalization, defaultRetailer));
		
		marketPlaceViewBean.setMarkets(buildMarketViewBeans(request, marketPlace, new ArrayList<Market>(marketPlace.getMarkets()), defaultLocalization));
		
		return marketPlaceViewBean;
	}
	
	/**
     * 
     */
	public List<MarketViewBean> buildMarketViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final List<Market> markets, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType marketElementType = WebCacheHelper.ElementType.MARKET_NAVIGATION_VIEW_BEAN_LIST;
		final String marketPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String marketCacheKey = marketPrefixCacheKey + "_" + marketPlace.getCode() + "_MARKET_LIST";
		List<MarketViewBean> marketViewBeans = (List<MarketViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketElementType, marketCacheKey);
		if(marketViewBeans == null){
			marketViewBeans = new ArrayList<MarketViewBean>();
			for (Iterator<Market> iteratorMarket = markets.iterator(); iteratorMarket.hasNext();) {
				final Market marketNavigation = (Market) iteratorMarket.next();
				marketViewBeans.add(buildMarketViewBean(request, marketNavigation));
			}
			menuMarketNavigationCacheHelper.addToCache(marketElementType, marketCacheKey, marketViewBeans);
		}
		return marketViewBeans;
	}
	
	/**
     * 
     */
	public MarketViewBean buildMarketViewBean(final HttpServletRequest request, final Market market) throws Exception {
		final MarketPlace marketPlace = market.getMarketPlace();
		final MarketArea defaultMarketArea = market.getDefaultMarketArea();
		final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
		final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();
		
		final MarketViewBean marketViewBean = new MarketViewBean();
		marketViewBean.setName(market.getName());
		marketViewBean.setUrl(backofficeUrlService.buildChangeContextUrl(marketPlace, market, defaultMarketArea, defaultLocalization, defaultRetailer));
		
		marketViewBean.setMarketAreas(buildMarketAreaViewBeans(request, market, new ArrayList<MarketArea>(market.getMarketAreas()), defaultLocalization));
		
		return marketViewBean;
	}
	
	/**
     * 
     */
	public List<MarketAreaViewBean> buildMarketAreaViewBeans(final HttpServletRequest request, final Market market, final List<MarketArea> marketAreas, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType marketAreaElementType = WebCacheHelper.ElementType.MARKET_AREA_VIEW_BEAN_LIST;
		final String marketAreaPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String marketAreaCacheKey = marketAreaPrefixCacheKey + "_" + market.getCode() + "_MARKET_AREA_LIST";
		List<MarketAreaViewBean> marketAreaViewBeans = (List<MarketAreaViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketAreaElementType, marketAreaCacheKey);
		if(marketAreaViewBeans == null){
			marketAreaViewBeans = new ArrayList<MarketAreaViewBean>();
			for (Iterator<MarketArea> iteratorMarketArea = marketAreas.iterator(); iteratorMarketArea.hasNext();) {
				final MarketArea marketArea = (MarketArea) iteratorMarketArea.next();
				marketAreaViewBeans.add(buildMarketAreaViewBean(request, marketArea));
			}
			menuMarketNavigationCacheHelper.addToCache(marketAreaElementType, marketAreaCacheKey, marketAreaViewBeans);
		}
		return marketAreaViewBeans;
	}
	
	/**
     * 
     */
	public MarketAreaViewBean buildMarketAreaViewBean(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		final Market market = marketArea.getMarket();
		final MarketPlace marketPlace = market.getMarketPlace();
		final Localization defaultLocalization = marketArea.getDefaultLocalization();
		final Retailer defaultRetailer = marketArea.getDefaultRetailer();
		
		final MarketAreaViewBean marketAreaViewBean = new MarketAreaViewBean();
		marketAreaViewBean.setName(marketArea.getName());
		marketAreaViewBean.setUrl(backofficeUrlService.buildChangeContextUrl(marketPlace, market, marketArea, defaultLocalization, defaultRetailer));
		return marketAreaViewBean;
	}
	
	/**
     * 
     */
	public List<LocalizationViewBean> buildLocalizationViewBeans(final HttpServletRequest request, final MarketArea marketArea, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType localizationElementType = WebCacheHelper.ElementType.LOCALIZATION_VIEW_BEAN_LIST;
		final String localizationPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String localizationCacheKey = localizationPrefixCacheKey + "_" + marketArea.getCode() + "_LOCALIZATION_LIST";
		List<LocalizationViewBean> localizationViewBeans = (List<LocalizationViewBean>) menuMarketNavigationCacheHelper.getFromCache(localizationElementType, localizationCacheKey);
		if(localizationViewBeans == null){
			final List<Localization> translationAvailables = new ArrayList<Localization>(marketArea.getLocalizations());
			localizationViewBeans = new ArrayList<LocalizationViewBean>();
			for (Iterator<Localization> iterator = translationAvailables.iterator(); iterator.hasNext();) {
				final Localization localizationAvailable = (Localization) iterator.next();
				localizationViewBeans.add(buildLocalizationViewBean(request, marketArea, localizationAvailable));
			}
			menuMarketNavigationCacheHelper.addToCache(localizationElementType, localizationCacheKey, localizationViewBeans);
		}
		return localizationViewBeans;
	}
	
	/**
     * 
     */
	public LocalizationViewBean buildLocalizationViewBean(final HttpServletRequest request, final MarketArea marketArea, final Localization localization) throws Exception {
		final Market market = marketArea.getMarket();
		final MarketPlace marketPlace = market.getMarketPlace();
		final Locale locale = localization.getLocale();
		final String localeCodeNavigation = localization.getCode();
		final Retailer retailer = requestUtil.getCurrentRetailer(request);
		
		final LocalizationViewBean localizationViewBean = new LocalizationViewBean();
		
		if(StringUtils.isNotEmpty(localeCodeNavigation)
				&& localeCodeNavigation.length() == 2) {
			localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCodeNavigation.toLowerCase(), null, locale));
		} else {
			localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCodeNavigation, null, locale));
		}
		
		localizationViewBean.setUrl(backofficeUrlService.buildChangeContextUrl(marketPlace, market, marketArea, localization, retailer));
		return localizationViewBean;
	}
	
	/**
     * 
     */
	public List<LocalizationViewBean> buildLocalizationViewBeans(final HttpServletRequest request, final List<Localization> localizations) throws Exception {
		final List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
		if(localizations != null){
			for (Iterator<Localization> iterator = localizations.iterator(); iterator.hasNext();) {
				Localization localization = (Localization) iterator.next();
				localizationViewBeans.add(buildLocalizationViewBean(request, localization));
			}
		}
		return localizationViewBeans;
	}
	
	/**
     * 
     */
	public LocalizationViewBean buildLocalizationViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		final String localeCodeNavigation = localization.getCode();
		
		final LocalizationViewBean localizationViewBean = new LocalizationViewBean();
		
		if(StringUtils.isNotEmpty(localeCodeNavigation)
				&& localeCodeNavigation.length() == 2) {
			localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCodeNavigation.toLowerCase(), null, locale));
		} else {
			localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCodeNavigation, null, locale));
		}
		
		localizationViewBean.setUrl(backofficeUrlService.buildChangeLanguageUrl(localization));
		return localizationViewBean;
	}
	
	/**
     * 
     */
	public List<RetailerViewBean> buildRetailerViewBeans(final HttpServletRequest request, final MarketArea marketArea, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType retailerElementType = WebCacheHelper.ElementType.RETAILER_VIEW_BEAN_LIST;
		final String retailerPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String retailerCacheKey = retailerPrefixCacheKey + "_RETAILER";
		List<RetailerViewBean> retailerViewBeans = (List<RetailerViewBean>) menuMarketNavigationCacheHelper.getFromCache(retailerElementType, retailerCacheKey);
		if(retailerViewBeans == null){
			final List<Retailer> retailers = new ArrayList<Retailer>(marketArea.getRetailers());
			retailerViewBeans = new ArrayList<RetailerViewBean>();
			for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
				final Retailer retailer = (Retailer) iterator.next();
				retailerViewBeans.add(buildRetailerViewBean(request, marketArea, localization, retailer));
			}
			menuMarketNavigationCacheHelper.addToCache(retailerElementType, retailerCacheKey, retailerViewBeans);
		}
		return retailerViewBeans;
	}
	
	/**
     * 
     */
	public RetailerViewBean buildRetailerViewBean(final HttpServletRequest request, final MarketArea marketArea, final Localization localization, final Retailer retailer) throws Exception {
		final Market market = marketArea.getMarket();
		final MarketPlace marketPlace = market.getMarketPlace();
		final RetailerViewBean retailerViewBean = new RetailerViewBean();
		retailerViewBean.setName(retailer.getName());
		retailerViewBean.setUrl(backofficeUrlService.buildChangeContextUrl(marketPlace, market, marketArea, localization, retailer));
		return retailerViewBean;
	}
	
	/**
     * 
     */
	public CatalogViewBean buildMasterCatalogViewBean(final HttpServletRequest request, final MarketArea marketArea, final Localization localization, final CatalogMaster catalogMaster, final List<ProductCategoryMaster> productCategories) throws Exception {
		final Locale locale = localization.getLocale();
		final CatalogViewBean catalogViewBean = new CatalogViewBean();
		catalogViewBean.setBusinessName(catalogMaster.getBusinessName());
		catalogViewBean.setCode(catalogMaster.getCode());
		
		if(productCategories != null){
			catalogViewBean.setCategories(buildMasterProductCategoryViewBeans(request, marketArea, localization, productCategories, true));
		}

		catalogViewBean.setAddRootCategoryUrl(backofficeUrlService.buildAddMasterProductCategoryUrl(null));
		catalogViewBean.setAddRootCategoryUrlLabel(coreMessageSource.getMessage("business.catalog.add.master.category.label", null, locale));

		return catalogViewBean;
	}
	
	/**
     * 
     */
	public CatalogViewBean buildVirtualCatalogViewBean(final HttpServletRequest request, final MarketArea marketArea, final Localization localization, final CatalogVirtual catalogVirtual, final List<ProductCategoryVirtual> productCategories) throws Exception {
		final Locale locale = localization.getLocale();
		final CatalogViewBean catalogViewBean = new CatalogViewBean();
		catalogViewBean.setBusinessName(catalogVirtual.getBusinessName());
		catalogViewBean.setCode(catalogVirtual.getCode());
		
		if(productCategories != null){
			catalogViewBean.setCategories(buildVirtualProductCategoryViewBeans(request, marketArea, localization, productCategories, true));
		}

		catalogViewBean.setAddRootCategoryUrl(backofficeUrlService.buildAddVirtualProductCategoryUrl(null));
		catalogViewBean.setAddRootCategoryUrlLabel(coreMessageSource.getMessage("business.catalog.add.virtual.category.label", null, locale));

		return catalogViewBean;
	}
	
	/**
     * 
     */
	public List<ProductCategoryViewBean> buildMasterProductCategoryViewBeans(final HttpServletRequest request, final MarketArea marketArea, final Localization localization, 
																			 final List<ProductCategoryMaster> productCategories, boolean fullPopulate) throws Exception {
		List<ProductCategoryViewBean> categoryViewBeans = new ArrayList<ProductCategoryViewBean>();
		for (Iterator<ProductCategoryMaster> iterator = productCategories.iterator(); iterator.hasNext();) {
			final ProductCategoryMaster category = (ProductCategoryMaster) iterator.next();
			categoryViewBeans.add(buildMasterProductCategoryViewBean(request, marketArea, localization, category, fullPopulate));
		}
		return categoryViewBeans;
	}
	
	/**
     * 
     */
	public List<ProductCategoryViewBean> buildVirtualProductCategoryViewBeans(final HttpServletRequest request, final MarketArea marketArea, final Localization localization, 
																			  final List<ProductCategoryVirtual> productCategories, boolean fullPopulate) throws Exception {
		List<ProductCategoryViewBean> categoryViewBeans = new ArrayList<ProductCategoryViewBean>();
		for (Iterator<ProductCategoryVirtual> iterator = productCategories.iterator(); iterator.hasNext();) {
			final ProductCategoryVirtual category = (ProductCategoryVirtual) iterator.next();
			categoryViewBeans.add(buildVirtualProductCategoryViewBean(request, marketArea, localization, category, fullPopulate));
		}
		return categoryViewBeans;
	}

	/**
     * 
     */
	public ProductCategoryViewBean buildMasterProductCategoryViewBean(final HttpServletRequest request, final MarketArea marketArea, 
																	  final Localization localization, final ProductCategoryMaster category, boolean fullPopulate) throws Exception {
		final Locale locale = localization.getLocale();
		final String localCode = localization.getCode();
		
		final ProductCategoryViewBean productCategoryViewBean = new ProductCategoryViewBean();
		
		// VIEW/FORM LABELS
		productCategoryViewBean.setBusinessNameLabel(coreMessageSource.getMessage("business.product.category.details.business.name.label", null, locale));
		productCategoryViewBean.setBusinessNameInformation(coreMessageSource.getMessage("business.product.category.details.business.name.information", null, locale));
		productCategoryViewBean.setDescriptionLabel(coreMessageSource.getMessage("business.product.category.details.description.label", null, locale));
		productCategoryViewBean.setDescriptionInformation(coreMessageSource.getMessage("business.product.category.details.description.information", null, locale));
		productCategoryViewBean.setIsDefaultLabel(coreMessageSource.getMessage("business.product.category.details.is.default.label", null, locale));
		productCategoryViewBean.setCodeLabel(coreMessageSource.getMessage("business.product.category.details.code.label", null, locale));
		productCategoryViewBean.setDefaultParentCategoryLabel(coreMessageSource.getMessage("business.product.category.details.default.parent.category.label", null, locale));
		productCategoryViewBean.setProductBrandLabel(coreMessageSource.getMessage("business.product.category.details.product.brand.label", null, locale));
		productCategoryViewBean.setProductMarketingGlobalAttributesLabel(coreMessageSource.getMessage("business.product.category.details.global.attribute.list.label", null, locale)); 
		productCategoryViewBean.setProductMarketingMarketAreaAttributesLabel(coreMessageSource.getMessage("business.product.category.details.area.attribute.list.label", null, locale)); 
		productCategoryViewBean.setProductMarketingLabel(coreMessageSource.getMessage("business.product.category.details.product.marketing.list.label", null, locale));
		productCategoryViewBean.setSubCategoriesLabel(coreMessageSource.getMessage("business.product.category.details.sub.category.list.label", null, locale));
		productCategoryViewBean.setDateCreateLabel(coreMessageSource.getMessage("business.product.category.details.created.date.label", null, locale));
		productCategoryViewBean.setDateUpdateLabel(coreMessageSource.getMessage("business.product.category.details.updated.date.label", null, locale));

		if(category != null){
			final String categoryCode = category.getCode();

			productCategoryViewBean.setBusinessName(category.getBusinessName());
			productCategoryViewBean.setCode(categoryCode);
			productCategoryViewBean.setDescription(category.getDescription());
			
			if(category.getDefaultParentProductCategory() != null){
				productCategoryViewBean.setDefaultParentCategory(buildMasterProductCategoryViewBean(request, marketArea, localization, category.getDefaultParentProductCategory(), false));
			}
			
			DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
			Date createdDate = category.getDateCreate();
			if(createdDate != null){
				productCategoryViewBean.setCreatedDate(dateFormat.format(createdDate));
			} else {
				productCategoryViewBean.setCreatedDate("NA");
			}
			Date updatedDate = category.getDateUpdate();
			if(updatedDate != null){
				productCategoryViewBean.setUpdatedDate(dateFormat.format(updatedDate));
			} else {
				productCategoryViewBean.setUpdatedDate("NA");
			}

			if(fullPopulate){
				if(category.getProductCategories() != null){
					productCategoryViewBean.setSubCategories(buildMasterProductCategoryViewBeans(request, marketArea, localization, new ArrayList<ProductCategoryMaster>(category.getProductCategories()), fullPopulate));
				}
				
				Set<ProductCategoryMasterAttribute> globalAttributes = category.getProductCategoryGlobalAttributes();
				for (Iterator<ProductCategoryMasterAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
					ProductCategoryMasterAttribute productCategoryMasterAttribute = (ProductCategoryMasterAttribute) iterator.next();
					productCategoryViewBean.getGlobalAttributes().put(productCategoryMasterAttribute.getAttributeDefinition().getCode(), productCategoryMasterAttribute.getValueAsString());
				}
				
				Set<ProductCategoryMasterAttribute> marketAreaAttributes = category.getProductCategoryMarketAreaAttributes();
				for (Iterator<ProductCategoryMasterAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
					ProductCategoryMasterAttribute productCategoryMasterAttribute = (ProductCategoryMasterAttribute) iterator.next();
					productCategoryViewBean.getMarketAreaAttributes().put(productCategoryMasterAttribute.getAttributeDefinition().getCode(), productCategoryMasterAttribute.getValueAsString());
				}
				
				List<ProductMarketingViewBean> productMarketingViewBeans = buildProductMarketingViewBeans(request, marketArea, localization, new ArrayList<ProductMarketing>(category.getProductMarketings()), true);
				productCategoryViewBean.setProductMarketings(productMarketingViewBeans);

				int countProduct = category.getProductMarketings().size();
				for (Iterator<ProductCategoryViewBean> iterator = productCategoryViewBean.getSubCategories().iterator(); iterator.hasNext();) {
					ProductCategoryViewBean subCategoryViewBean = (ProductCategoryViewBean) iterator.next();
					countProduct = countProduct + subCategoryViewBean.getCountProduct();
				}
				productCategoryViewBean.setCountProduct(countProduct);
			}

			productCategoryViewBean.setCategoryDetailsLabel(coreMessageSource.getMessage("business.product.category.details.url.label", null, locale));
			productCategoryViewBean.setCategoryDetailsUrl(backofficeUrlService.buildProductMasterCategoryDetailsUrl(categoryCode));

			productCategoryViewBean.setCategoryEditLabel(coreMessageSource.getMessage("business.product.category.edit.url.label", null, locale));
			productCategoryViewBean.setCategoryEditUrl(backofficeUrlService.buildMasterProductCategoryEditUrl(categoryCode));
		}
		
		productCategoryViewBean.setSubmitLabel(coreMessageSource.getMessage("business.product.category.edit.submit.label", null, locale));
		productCategoryViewBean.setFormSubmitUrl(backofficeUrlService.buildMasterProductCategoryFormPostUrl());

		productCategoryViewBean.setCancelLabel(coreMessageSource.getMessage("business.product.category.edit.cancel.label", null, locale));
		final List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add("form");
		productCategoryViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

		return productCategoryViewBean;
	}
	
	/**
     * 
     */
	public ProductCategoryViewBean buildVirtualProductCategoryViewBean(final HttpServletRequest request, final MarketArea marketArea, 
																	   final Localization localization, final ProductCategoryVirtual category, boolean fullPopulate) throws Exception {
		final Locale locale = localization.getLocale();
		final String localCode = localization.getCode();
		
		final ProductCategoryViewBean productCategoryViewBean = new ProductCategoryViewBean();
		
		// VIEW/FORM LABELS
		productCategoryViewBean.setBusinessNameLabel(coreMessageSource.getMessage("business.product.category.details.business.name.label", null, locale));
		productCategoryViewBean.setBusinessNameInformation(coreMessageSource.getMessage("business.product.category.details.business.name.information", null, locale));
		productCategoryViewBean.setDescriptionLabel(coreMessageSource.getMessage("business.product.category.details.description.label", null, locale));
		productCategoryViewBean.setDescriptionInformation(coreMessageSource.getMessage("business.product.category.details.description.information", null, locale));
		productCategoryViewBean.setIsDefaultLabel(coreMessageSource.getMessage("business.product.category.details.is.default.label", null, locale));
		productCategoryViewBean.setCodeLabel(coreMessageSource.getMessage("business.product.category.details.code.label", null, locale));
		productCategoryViewBean.setDefaultParentCategoryLabel(coreMessageSource.getMessage("business.product.category.details.default.parent.category.label", null, locale));
		productCategoryViewBean.setProductBrandLabel(coreMessageSource.getMessage("business.product.category.details.product.brand.label", null, locale));
		productCategoryViewBean.setProductMarketingGlobalAttributesLabel(coreMessageSource.getMessage("business.product.category.details.global.attribute.list.label", null, locale)); 
		productCategoryViewBean.setProductMarketingMarketAreaAttributesLabel(coreMessageSource.getMessage("business.product.category.details.area.attribute.list.label", null, locale)); 
		productCategoryViewBean.setProductMarketingLabel(coreMessageSource.getMessage("business.product.category.details.product.marketing.label", null, locale));
		productCategoryViewBean.setSubCategoriesLabel(coreMessageSource.getMessage("business.product.category.details.sub.category.list.label", null, locale));
		productCategoryViewBean.setDateCreateLabel(coreMessageSource.getMessage("business.product.category.details.created.date.label", null, locale));
		productCategoryViewBean.setDateUpdateLabel(coreMessageSource.getMessage("business.product.category.details.updated.date.label", null, locale));

		if(category != null){
			final String categoryCode = category.getCode();

			productCategoryViewBean.setBusinessName(category.getBusinessName());
			productCategoryViewBean.setCode(categoryCode);
			productCategoryViewBean.setDescriptionInformation(category.getDescription());

			if(category.getDefaultParentProductCategory() != null){
				productCategoryViewBean.setDefaultParentCategory(buildVirtualProductCategoryViewBean(request, marketArea, localization, category.getDefaultParentProductCategory(), false));
			}
			
			DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
			Date createdDate = category.getDateCreate();
			if(createdDate != null){
				productCategoryViewBean.setCreatedDate(dateFormat.format(createdDate));
			} else {
				productCategoryViewBean.setCreatedDate("NA");
			}
			Date updatedDate = category.getDateUpdate();
			if(updatedDate != null){
				productCategoryViewBean.setUpdatedDate(dateFormat.format(updatedDate));
			} else {
				productCategoryViewBean.setUpdatedDate("NA");
			}

			if(fullPopulate){
				if(category.getProductCategories() != null){
					productCategoryViewBean.setSubCategories(buildVirtualProductCategoryViewBeans(request, marketArea, localization, new ArrayList<ProductCategoryVirtual>(category.getProductCategories()), fullPopulate));
				}

				Set<ProductCategoryVirtualAttribute> globalAttributes = category.getProductCategoryGlobalAttributes();
				for (Iterator<ProductCategoryVirtualAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
					ProductCategoryVirtualAttribute productCategoryVirtualAttribute = (ProductCategoryVirtualAttribute) iterator.next();
					productCategoryViewBean.getGlobalAttributes().put(productCategoryVirtualAttribute.getAttributeDefinition().getCode(), productCategoryVirtualAttribute.getValueAsString());
				}
				
				Set<ProductCategoryVirtualAttribute> marketAreaAttributes = category.getProductCategoryMarketAreaAttributes();
				for (Iterator<ProductCategoryVirtualAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
					ProductCategoryVirtualAttribute productCategoryVirtualAttribute = (ProductCategoryVirtualAttribute) iterator.next();
					productCategoryViewBean.getMarketAreaAttributes().put(productCategoryVirtualAttribute.getAttributeDefinition().getCode(), productCategoryVirtualAttribute.getValueAsString());
				}
	
				List<ProductMarketingViewBean> productMarketingViewBeans = buildProductMarketingViewBeans(request, marketArea, localization, new ArrayList<ProductMarketing>(category.getProductMarketings()), true);
				productCategoryViewBean.setProductMarketings(productMarketingViewBeans);
				
				int countProduct = category.getProductMarketings().size();
				for (Iterator<ProductCategoryViewBean> iterator = productCategoryViewBean.getSubCategories().iterator(); iterator.hasNext();) {
					ProductCategoryViewBean subCategoryViewBean = (ProductCategoryViewBean) iterator.next();
					countProduct = countProduct + subCategoryViewBean.getCountProduct();
				}
				productCategoryViewBean.setCountProduct(countProduct);
			}

			productCategoryViewBean.setCategoryDetailsLabel(coreMessageSource.getMessage("business.product.category.details.url.label", null, locale));
			productCategoryViewBean.setCategoryDetailsUrl(backofficeUrlService.buildProductVirtualCategoryDetailsUrl(categoryCode));

			productCategoryViewBean.setCategoryEditLabel(coreMessageSource.getMessage("business.product.category.edit.url.label", null, locale));
			productCategoryViewBean.setCategoryEditUrl(backofficeUrlService.buildVirtualProductCategoryEditUrl(categoryCode));
		}
		
		productCategoryViewBean.setCancelLabel(coreMessageSource.getMessage("business.product.category.edit.cancel.label", null, locale));
		final List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add("form");
		productCategoryViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

		productCategoryViewBean.setSubmitLabel(coreMessageSource.getMessage("business.product.category.edit.submit.label", null, locale));
		productCategoryViewBean.setFormSubmitUrl(backofficeUrlService.buildVirtualProductCategoryFormPostUrl());
		
		return productCategoryViewBean;
	}

	/**
     * 
     */
	public List<ProductMarketingViewBean> buildProductMarketingViewBeans(HttpServletRequest request, MarketArea marketArea, Localization localization, List<ProductMarketing> productMarketings, boolean withDependency) throws Exception {
		List<ProductMarketingViewBean> products = new ArrayList<ProductMarketingViewBean>();
		for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
			ProductMarketing productMarketing = (ProductMarketing) iterator.next();
			products.add(buildProductMarketingViewBean(request, marketArea, localization, productMarketing, withDependency));
		}
		return products;
	}

	/**
     * 
     */
	public ProductMarketingViewBean buildProductMarketingViewBean(HttpServletRequest request, MarketArea marketArea, Localization localization, ProductMarketing productMarketing, boolean withDependency) throws Exception {
		final Locale locale = localization.getLocale();
		final Long marketAreaId = marketArea.getId();
		final String productCode = productMarketing.getCode();
		
		final ProductMarketingViewBean productMarketingViewBean = new ProductMarketingViewBean();

		// VIEW/FORM LABELS
		productMarketingViewBean.setBusinessNameLabel(coreMessageSource.getMessage("business.product.marketing.details.business.name.label", null, locale));
		productMarketingViewBean.setBusinessNameInformation(coreMessageSource.getMessage("business.product.marketing.details.business.name.information", null, locale));
		productMarketingViewBean.setDescriptionLabel(coreMessageSource.getMessage("business.product.marketing.details.description.label", null, locale));
		productMarketingViewBean.setDescriptionInformation(coreMessageSource.getMessage("business.product.marketing.details.description.information", null, locale));
		productMarketingViewBean.setIsDefaultLabel(coreMessageSource.getMessage("business.product.marketing.details.is.default.label", null, locale));
		productMarketingViewBean.setCodeLabel(coreMessageSource.getMessage("business.product.marketing.details.code.label", null, locale));
		productMarketingViewBean.setProductBrandLabel(coreMessageSource.getMessage("business.product.marketing.details.product.brand.label", null, locale));
		productMarketingViewBean.setProductMarketingGlobalAttributesLabel(coreMessageSource.getMessage("business.product.marketing.details.global.attribute.list.label", null, locale)); 
		productMarketingViewBean.setProductMarketingMarketAreaAttributesLabel(coreMessageSource.getMessage("business.product.marketing.details.area.attribute.list.label", null, locale)); 
		productMarketingViewBean.setProductSkusLabel(coreMessageSource.getMessage("business.product.marketing.details.sku.list.label", null, locale));
		productMarketingViewBean.setProductCrossLinksLabel(coreMessageSource.getMessage("business.product.marketing.details.cross.product.list.label", null, locale));
		productMarketingViewBean.setAssetsLabel(coreMessageSource.getMessage("business.product.marketing.details.asset.list.label", null, locale)); 
		productMarketingViewBean.setDateCreateLabel(coreMessageSource.getMessage("business.product.marketing.details.created.date.label", null, locale));
		productMarketingViewBean.setDateUpdateLabel(coreMessageSource.getMessage("business.product.marketing.details.updated.date.label", null, locale));
		
		Integer position = productMarketing.getOrder(marketAreaId);
		if(position != null){
			productMarketingViewBean.setPositionItem(position);
		}
		productMarketingViewBean.setBusinessName(productMarketing.getBusinessName());
		productMarketingViewBean.setCode(productMarketing.getCode());
		productMarketingViewBean.setDescription(productMarketing.getDescription());
		productMarketingViewBean.setDefault(productMarketing.isDefault());
		productMarketingViewBean.setBrand(buildBrandViewBean(request, productMarketing.getProductBrand()));

		Set<ProductMarketingAttribute> globalAttributes = productMarketing.getProductMarketingGlobalAttributes();
		for (Iterator<ProductMarketingAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
			ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
			productMarketingViewBean.getGlobalAttributes().put(productMarketingAttribute.getAttributeDefinition().getCode(), productMarketingAttribute.getValueAsString());
		}
		
		Set<ProductMarketingAttribute> marketAreaAttributes = productMarketing.getProductMarketingMarketAreaAttributes();
		for (Iterator<ProductMarketingAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
			ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
			productMarketingViewBean.getMarketAreaAttributes().put(productMarketingAttribute.getAttributeDefinition().getCode(), productMarketingAttribute.getValueAsString());
		}
		
		if(withDependency){
			Set<ProductSku> productSkus = productMarketing.getProductSkus();
			for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSku productSku = (ProductSku) iterator.next();
				productMarketingViewBean.getProductSkus().add(buildProductSkuViewBean(request, marketArea, localization, productSku));
			}
		}
		
		DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
		Date createdDate = productMarketing.getDateCreate();
		if(createdDate != null){
			productMarketingViewBean.setCreatedDate(dateFormat.format(createdDate));
		} else {
			productMarketingViewBean.setCreatedDate("NA");
		}
		Date updatedDate = productMarketing.getDateUpdate();
		if(updatedDate != null){
			productMarketingViewBean.setUpdatedDate(dateFormat.format(updatedDate));
		} else {
			productMarketingViewBean.setUpdatedDate("NA");
		}
		
//		productMarketingViewBean.setBackgroundImage(productMarketing.getbackgroundImage);
//		productMarketingViewBean.setCarouselImage(productMarketing.getcarouselImage);
//		productMarketingViewBean.setIconImage(productMarketing.geticonImage);

//		productMarketingViewBean.setproductSkus();
//		productMarketingViewBean.setproductCrossLinks();

		productMarketingViewBean.setProductDetailsLabel(coreMessageSource.getMessage("business.product.marketing.details.url.label", null, locale));
		productMarketingViewBean.setProductDetailsUrl(backofficeUrlService.buildProductMarketingDetailsUrl(productCode));

		productMarketingViewBean.setProductEditLabel(coreMessageSource.getMessage("business.product.marketing.edit.url.label", null, locale));
		productMarketingViewBean.setProductEditUrl(backofficeUrlService.buildProductMarketingEditUrl(productCode));

		productMarketingViewBean.setCancelLabel(coreMessageSource.getMessage("business.product.marketing.edit.cancel.label", null, locale));
		final List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add("form");
		productMarketingViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

		productMarketingViewBean.setSubmitLabel(coreMessageSource.getMessage("business.product.marketing.edit.submit.label", null, locale));
		productMarketingViewBean.setFormSubmitUrl(backofficeUrlService.buildProductMarketingEditUrl(productCode));
		
		return productMarketingViewBean;
	}
	
	/**
     * 
     */
	public ProductSkuViewBean buildProductSkuViewBean(HttpServletRequest request, MarketArea marketArea, Localization localization, ProductSku productSku) throws Exception {
		final Locale locale = localization.getLocale();
		final Long marketAreaId = marketArea.getId();
		final String productSkuCode = productSku.getCode();
		
		final ProductSkuViewBean productSkuViewBean = new ProductSkuViewBean();

		// VIEW/FORM LABELS
		productSkuViewBean.setBusinessNameLabel(coreMessageSource.getMessage("business.product.sku.details.business.name.label", null, locale));
		productSkuViewBean.setBusinessNameInformation(coreMessageSource.getMessage("business.product.sku.details.business.name.information", null, locale));
		productSkuViewBean.setDescriptionLabel(coreMessageSource.getMessage("business.product.sku.details.description.label", null, locale));
		productSkuViewBean.setDescriptionInformation(coreMessageSource.getMessage("business.product.sku.details.description.information", null, locale));
		productSkuViewBean.setIsDefaultLabel(coreMessageSource.getMessage("business.product.sku.details.is.default.label", null, locale));
		productSkuViewBean.setCodeLabel(coreMessageSource.getMessage("business.product.sku.details.code.label", null, locale));
		productSkuViewBean.setProductSkuGlobalAttributesLabel(coreMessageSource.getMessage("business.product.marketing.details.global.attribute.list.label", null, locale)); 
		productSkuViewBean.setProductSkuMarketAreaAttributesLabel(coreMessageSource.getMessage("business.product.marketing.details.area.attribute.list.label", null, locale)); 

		productSkuViewBean.setProductMarketingAssociatedLabel(coreMessageSource.getMessage("business.product.sku.details.product.marketing.associated.label", null, locale));
		productSkuViewBean.setProductBrandLabel(coreMessageSource.getMessage("business.product.sku.details.product.brand.label", null, locale));
		productSkuViewBean.setProductMarketingGlobalAttributesLabel(coreMessageSource.getMessage("business.product.marketing.details.global.attribute.list.label", null, locale)); 
		productSkuViewBean.setProductMarketingMarketAreaAttributesLabel(coreMessageSource.getMessage("business.product.marketing.details.area.attribute.list.label", null, locale)); 
		productSkuViewBean.setProductSkusLabel(coreMessageSource.getMessage("business.product.sku.details.sku.list.label", null, locale));
		productSkuViewBean.setProductCrossLinksLabel(coreMessageSource.getMessage("business.product.sku.details.cross.product.list.label", null, locale));
		productSkuViewBean.setAssetsLabel(coreMessageSource.getMessage("business.product.sku.details.asset.list.label", null, locale)); 
		productSkuViewBean.setDateCreateLabel(coreMessageSource.getMessage("business.product.sku.details.created.date.label", null, locale));
		productSkuViewBean.setDateUpdateLabel(coreMessageSource.getMessage("business.product.sku.details.updated.date.label", null, locale));
		
		Integer position = productSku.getOrder(marketAreaId);
		if(position != null){
			productSkuViewBean.setPositionItem(position);
		}
		productSkuViewBean.setBusinessName(productSku.getBusinessName());
		productSkuViewBean.setCode(productSku.getCode());
		productSkuViewBean.setDescription(productSku.getDescription());
		productSkuViewBean.setDefault(productSku.isDefault());

		Set<ProductSkuAttribute> skuGlobalAttributes = productSku.getProductSkuGlobalAttributes();
		for (Iterator<ProductSkuAttribute> iterator = skuGlobalAttributes.iterator(); iterator.hasNext();) {
			ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
			productSkuViewBean.getGlobalAttributes().put(productSkuAttribute.getAttributeDefinition().getCode(), productSkuAttribute.getValueAsString());
		}
		
		Set<ProductSkuAttribute> skuMarketAreaAttributes = productSku.getProductSkuMarketAreaAttributes();
		for (Iterator<ProductSkuAttribute> iterator = skuMarketAreaAttributes.iterator(); iterator.hasNext();) {
			ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
			productSkuViewBean.getMarketAreaAttributes().put(productSkuAttribute.getAttributeDefinition().getCode(), productSkuAttribute.getValueAsString());
		}
		
		productSkuViewBean.setProductMarketing(buildProductMarketingViewBean(request, marketArea, localization, productSku.getProductMarketing(), false));

		Set<ProductMarketingAttribute> productMarketingGlobalAttributes = productSku.getProductMarketing().getProductMarketingGlobalAttributes();
		for (Iterator<ProductMarketingAttribute> iterator = productMarketingGlobalAttributes.iterator(); iterator.hasNext();) {
			ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
			productSkuViewBean.getGlobalAttributes().put(productMarketingAttribute.getAttributeDefinition().getCode(), productMarketingAttribute.getValueAsString());
		}
		
		Set<ProductMarketingAttribute> productMarketingMarketAreaAttributes = productSku.getProductMarketing().getProductMarketingMarketAreaAttributes();
		for (Iterator<ProductMarketingAttribute> iterator = productMarketingMarketAreaAttributes.iterator(); iterator.hasNext();) {
			ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
			productSkuViewBean.getMarketAreaAttributes().put(productMarketingAttribute.getAttributeDefinition().getCode(), productMarketingAttribute.getValueAsString());
		}
		
		DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
		Date createdDate = productSku.getDateCreate();
		if(createdDate != null){
			productSkuViewBean.setCreatedDate(dateFormat.format(createdDate));
		} else {
			productSkuViewBean.setCreatedDate("NA");
		}
		Date updatedDate = productSku.getDateUpdate();
		if(updatedDate != null){
			productSkuViewBean.setUpdatedDate(dateFormat.format(updatedDate));
		} else {
			productSkuViewBean.setUpdatedDate("NA");
		}
		
//		productSkuViewBean.setBackgroundImage(productMarketing.getbackgroundImage);
//		productSkuViewBean.setCarouselImage(productMarketing.getcarouselImage);
//		productSkuViewBean.setIconImage(productMarketing.geticonImage);

		productSkuViewBean.setProductSkuDetailsLabel(coreMessageSource.getMessage("business.product.sku.details.url.label", null, locale));
		productSkuViewBean.setProductSkuDetailsUrl(backofficeUrlService.buildProductSkuDetailsUrl(productSkuCode));

		productSkuViewBean.setProductSkuEditLabel(coreMessageSource.getMessage("business.product.sku.edit.url.label", null, locale));
		productSkuViewBean.setProductSkuEditUrl(backofficeUrlService.buildProductSkuEditUrl(productSkuCode));

		productSkuViewBean.setCancelLabel(coreMessageSource.getMessage("business.product.sku.edit.cancel.label", null, locale));
		final List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add("form");
		productSkuViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

		productSkuViewBean.setSubmitLabel(coreMessageSource.getMessage("business.product.sku.edit.submit.label", null, locale));
		productSkuViewBean.setFormSubmitUrl(backofficeUrlService.buildProductSkuEditUrl(productSkuCode));
		
		return productSkuViewBean;
	}
	
	/**
     * 
     */
	public BrandViewBean buildBrandViewBean(final HttpServletRequest request, final ProductBrand productBrand) throws Exception {
		final BrandViewBean brandViewBean = new BrandViewBean();
		
		brandViewBean.setBusinessName(productBrand.getName());
		brandViewBean.setCode(productBrand.getCode());
//		brandViewBean.setBrandDetailsUrl(brandDetailsUrl);
//		brandViewBean.setBrandLineDetailsUrl(brandLineDetailsUrl);

		return brandViewBean;
	}
	
	/**
     * 
     */
	public LegacyViewBean buildLegacyViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final LegacyViewBean legacy = new LegacyViewBean();
		
		legacy.setPageTitle(coreMessageSource.getMessage("header.title.legacy", null, locale));
		legacy.setTextHtml(coreMessageSource.getMessage("legacy.content.text", null, locale));

		legacy.setWarning(coreMessageSource.getMessage("legacy.warning", null, locale));
		legacy.setCopyright(coreMessageSource.getMessage("footer.copyright", null, locale));
		
		return legacy;
	}
	
	/**
     * 
     */
	public SecurityViewBean buildSecurityViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final SecurityViewBean security = new SecurityViewBean();
		
		security.setLoginPageTitle(coreMessageSource.getMessage("header.title.login", null, locale));
		security.setLoginPageText(coreMessageSource.getMessage("login.content.text", null, locale));

		security.setLogoutPageTitle(coreMessageSource.getMessage("header.title.logout", null, locale));
		security.setLogoutPageText(coreMessageSource.getMessage("main.content.title.logout", null, locale));

		security.setForbiddenPageTitle(coreMessageSource.getMessage("header.title.forbidden", null, locale));
		security.setForbiddenPageText(coreMessageSource.getMessage("forbidden.content.text", null, locale));

		security.setTimeoutPageTitle(coreMessageSource.getMessage("header.title.timeout", null, locale));
		security.setTimeoutPageText(coreMessageSource.getMessage("timeout.content.text", null, locale));

		security.setForgottenPasswordPageTitle(coreMessageSource.getMessage("header.title.forgotten.password", null, locale));
		security.setForgottenPasswordPageText(coreMessageSource.getMessage("forgotten.password.content.text", null, locale));
		security.setEmailOrLoginLabel(coreMessageSource.getMessage("forgotten.password.email.or.login", null, locale));
		security.setForgottenPasswordEmailSucces(coreMessageSource.getMessage("forgotten.password.email.success", null, locale));
	    
		security.setLoginFormTitle(coreMessageSource.getMessage("login.form.login.title", null, locale));
		security.setLoginUrl(backofficeUrlService.buildSpringSecurityCheckUrl());
		security.setLoginLabel(coreMessageSource.getMessage("login.form.login.label", null, locale));
		security.setForgottenPasswordUrl(backofficeUrlService.buildForgottenPasswordUrl());
		security.setForgottenPasswordLabel(coreMessageSource.getMessage("login.form.forgotten.password.label", null, locale));
		security.setPasswordLabel(coreMessageSource.getMessage("login.form.password.label", null, locale));
		security.setRememberLabel(coreMessageSource.getMessage("login.form.remember.label", null, locale));
		security.setSubmitLabel(coreMessageSource.getMessage("login.form.login.submit", null, locale));
		
		return security;
	}
	
	/**
     * 
     */
	public QuickSearchViewBean buildQuickSearchViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		final QuickSearchViewBean quickSsearch = new QuickSearchViewBean();
		quickSsearch.setTextLabel(coreMessageSource.getMessage("form.search.label.text", null, locale));
		quickSsearch.setUrlFormSubmit(backofficeUrlService.buildSearchUrl());
		return quickSsearch;
	}
	
	/**
     * 
     */
	public UserViewBean buildUserViewBean(final HttpServletRequest request, final Localization localization, final User user) throws Exception {
		final Locale locale = localization.getLocale();
		final UserViewBean userViewBean = new UserViewBean();
		userViewBean.setId(user.getId());
		userViewBean.setLoginLabel(coreMessageSource.getMessage("user.login.label", null, locale));
		userViewBean.setLogin(user.getLogin());
		userViewBean.setFirstnameLabel(coreMessageSource.getMessage("user.firstname.label", null, locale));
		userViewBean.setFirstname(user.getFirstname());
		userViewBean.setLastnameLabel(coreMessageSource.getMessage("user.lastname.label", null, locale));
		userViewBean.setLastname(user.getLastname());
		userViewBean.setEmailLabel(coreMessageSource.getMessage("user.email.label", null, locale));
		userViewBean.setEmail(user.getEmail());
		userViewBean.setPasswordLabel(coreMessageSource.getMessage("user.password.label", null, locale));
		userViewBean.setPassword(user.getPassword());
		userViewBean.setActiveLabel(coreMessageSource.getMessage("user.active.label", null, locale));
		userViewBean.setActive(user.isActive());
		if(user.isActive()){
			userViewBean.setActiveValueLabel(coreMessageSource.getMessage("user.active.value.true", null, locale));
		} else {
			userViewBean.setActiveValueLabel(coreMessageSource.getMessage("user.active.value.false", null, locale));
		}
		
		DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
		userViewBean.setDateCreateLabel(coreMessageSource.getMessage("user.date.create.label", null, locale));
		if(user.getDateCreate() != null){
			userViewBean.setDateCreate(dateFormat.format(user.getDateCreate()));
		} else {
			userViewBean.setDateCreate("NA");
		}
		userViewBean.setDateUpdateLabel(coreMessageSource.getMessage("user.date.update.label", null, locale));
		if(user.getDateUpdate() != null){
			userViewBean.setDateUpdate(dateFormat.format(user.getDateUpdate()));
		} else {
			userViewBean.setDateUpdate("NA");
		}
		
		final Set<UserGroup> userGroups = user.getUserGroups();
		for (Iterator<UserGroup> iteratorUserGroup = userGroups.iterator(); iteratorUserGroup.hasNext();) {
			UserGroup userGroup = (UserGroup) iteratorUserGroup.next();
			String keyUserGroup = userGroup.getCode();
			String valueUserGroup = userGroup.getName();
			userViewBean.getUserGroups().put(keyUserGroup, valueUserGroup);
			
			final Set<UserRole> userRoles = userGroup.getGroupRoles();
			for (Iterator<UserRole> iteratorUserRole = userRoles.iterator(); iteratorUserRole.hasNext();) {
				UserRole userRole = (UserRole) iteratorUserRole.next();
				String keyUserRole = userRole.getCode() + " (" + userGroup.getCode() + ")";
				String valueUserRole = userRole.getName();
				userViewBean.getUserRoles().put(keyUserRole, valueUserRole);
				
				final Set<UserPermission> rolePermissions = userRole.getRolePermissions();
				for (Iterator<UserPermission> iteratorUserPermission = rolePermissions.iterator(); iteratorUserPermission.hasNext();) {
					UserPermission userPermission = (UserPermission) iteratorUserPermission.next();
					String keyUserPermission = userPermission.getCode() + " (" + userRole.getCode() + ")";
					String valueUserPermission = userPermission.getName();
					userViewBean.getUserPermissions().put(keyUserPermission, valueUserPermission);
				}
			}
		}
		
		userViewBean.setUserConnectionLogLabel(coreMessageSource.getMessage("user.last.login.label", null, locale));
		userViewBean.setUserGroupsLabel(coreMessageSource.getMessage("user.groups.label", null, locale));
		userViewBean.setUserRolesLabel(coreMessageSource.getMessage("user.roles.label", null, locale));
		userViewBean.setUserPermissionsLabel(coreMessageSource.getMessage("user.permissions.label", null, locale));
		
		userViewBean.setTableDateLabel(coreMessageSource.getMessage("user.details.table.date", null, locale));
		userViewBean.setTableHostLabel(coreMessageSource.getMessage("user.details.table.host", null, locale));
		userViewBean.setTableAddressLabel(coreMessageSource.getMessage("user.details.table.address", null, locale));
		userViewBean.setTableCodeLabel(coreMessageSource.getMessage("user.details.table.code", null, locale));
		userViewBean.setTableNameLabel(coreMessageSource.getMessage("user.details.table.name", null, locale));
		
		final Set<UserConnectionLog> connectionLogs = user.getConnectionLogs();
		for (Iterator<UserConnectionLog> iteratorUserConnectionLog = connectionLogs.iterator(); iteratorUserConnectionLog.hasNext();) {
			UserConnectionLog userConnectionLog = (UserConnectionLog) iteratorUserConnectionLog.next();
			UserConnectionLogValueBean userConnectionLogValueBean = new UserConnectionLogValueBean();
			userConnectionLogValueBean.setDate(dateFormat.format(userConnectionLog.getLoginDate()));
			if(StringUtils.isNotEmpty(userConnectionLog.getHost())){
				userConnectionLogValueBean.setHost(userConnectionLog.getHost());
			} else {
				userConnectionLogValueBean.setHost("NA");
			}
			if(StringUtils.isNotEmpty(userConnectionLog.getAddress())){
				userConnectionLogValueBean.setAddress(userConnectionLog.getAddress());
			} else {
				userConnectionLogValueBean.setAddress("NA");
			}
			userViewBean.getUserConnectionLogs().add(userConnectionLogValueBean);
		}

		userViewBean.setBackLabel(coreMessageSource.getMessage("user.back.label", null, locale));
		final List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add("form");
		userViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

		userViewBean.setUserDetailsLabel(coreMessageSource.getMessage("user.details.label", null, locale));
		userViewBean.setUserDetailsUrl(backofficeUrlService.buildUserDetailsUrl());

		userViewBean.setUserEditLabel(coreMessageSource.getMessage("user.edit.label", null, locale));
		userViewBean.setUserEditUrl(backofficeUrlService.buildUserEditUrl());
		
		userViewBean.setCancelLabel(coreMessageSource.getMessage("user.edit.cancel.label", null, locale));
		userViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

		userViewBean.setSubmitLabel(coreMessageSource.getMessage("user.edit.submit.label", null, locale));
		userViewBean.setFormSubmitUrl(backofficeUrlService.buildUserEditUrl());
		
		return userViewBean;
	}

//	/**
//     * 
//     */
//	public UserViewBean buildUserViewBean(final HttpServletRequest request, final Localization localization, final User user) throws Exception {
//		final Locale locale = localization.getLocale();
//		final UserViewBean userEditViewBean = new UserViewBean();
//
//		userEditViewBean.setId(user.getId());
//		userEditViewBean.setLoginLabel(coreMessageSource.getMessage("user.login.label", null, locale));
//		userEditViewBean.setFirstnameLabel(coreMessageSource.getMessage("user.firstname.label", null, locale));
//		userEditViewBean.setLastnameLabel(coreMessageSource.getMessage("user.lastname.label", null, locale));
//		userEditViewBean.setEmailLabel(coreMessageSource.getMessage("user.email.label", null, locale));
//		userEditViewBean.setPasswordLabel(coreMessageSource.getMessage("user.password.label", null, locale));
//		userEditViewBean.setActiveLabel(coreMessageSource.getMessage("user.active.label", null, locale));
//		if(user.isActive()){
//			userEditViewBean.setActiveValueLabel(coreMessageSource.getMessage("user.active.value.true", null, locale));
//		} else {
//			userEditViewBean.setActiveValueLabel(coreMessageSource.getMessage("user.active.value.false", null, locale));
//		}
//
//		
//		userEditViewBean.setDateCreateLabel(coreMessageSource.getMessage("user.date.create.label", null, locale));
//		userEditViewBean.setDateUpdateLabel(coreMessageSource.getMessage("user.date.update.label", null, locale));
//		
//		
//		return userEditViewBean;
//	}
}
