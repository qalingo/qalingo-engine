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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.common.domain.Cart;
import fr.hoteia.qalingo.core.common.domain.CartItem;
import fr.hoteia.qalingo.core.common.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.CustomerAddress;
import fr.hoteia.qalingo.core.common.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.common.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.common.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Order;
import fr.hoteia.qalingo.core.common.domain.OrderItem;
import fr.hoteia.qalingo.core.common.domain.OrderShipment;
import fr.hoteia.qalingo.core.common.domain.OrderTax;
import fr.hoteia.qalingo.core.common.domain.ProductBrand;
import fr.hoteia.qalingo.core.common.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.common.domain.ProductCrossLink;
import fr.hoteia.qalingo.core.common.domain.ProductImage;
import fr.hoteia.qalingo.core.common.domain.ProductMarketing;
import fr.hoteia.qalingo.core.common.domain.ProductSku;
import fr.hoteia.qalingo.core.common.domain.Retailer;
import fr.hoteia.qalingo.core.common.domain.Shipping;
import fr.hoteia.qalingo.core.common.domain.Store;
import fr.hoteia.qalingo.core.common.domain.Tax;
import fr.hoteia.qalingo.core.common.domain.enumtype.ImageSize;
import fr.hoteia.qalingo.core.common.service.CustomerProductCommentService;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;
import fr.hoteia.qalingo.core.common.service.MarketPlaceService;
import fr.hoteia.qalingo.core.common.service.MarketService;
import fr.hoteia.qalingo.core.common.service.ProductCatalogService;
import fr.hoteia.qalingo.core.common.service.ProductCategoryService;
import fr.hoteia.qalingo.core.common.service.ProductMarketingService;
import fr.hoteia.qalingo.core.common.service.ProductSkuService;
import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.solr.bean.ProductSolr;
import fr.hoteia.qalingo.core.solr.response.ProductResponseBean;
import fr.hoteia.qalingo.core.web.cache.util.WebCacheHelper;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.CartItemViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CartShippingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CartTaxViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ConditionsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ContactUsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressFormViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressListViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerCreateAccountViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerProductCommentViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerProductCommentsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerWishlistViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CutomerMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FaqViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FollowUsOptionViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FollowUsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.HeaderCartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketAreaViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderItemViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderShippingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderTaxViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OurCompanyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductBrandViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductCategoryViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductCrossLinkViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductMarketingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductSkuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchFacetViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchProductItemViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreLocatorViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ValueBean;

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
	protected MarketPlaceService marketPlaceService;
	
	@Autowired
	protected MarketService marketService;
	
	@Autowired
	protected ProductCatalogService ProductCatalogService;
	
	@Autowired
	protected ProductCategoryService productCategoryService;
	
	@Autowired
	protected ProductMarketingService productMarketingService;
	
	@Autowired
	protected ProductSkuService productSkuService;
	
	@Autowired
	protected CustomerProductCommentService customerProductCommentService;
	
	@Autowired
    protected UrlService urlService;
	
	@Autowired
	@Qualifier("menuMarketNavigationCacheHelper")
    protected WebCacheHelper menuMarketNavigationCacheHelper;
	
	@Autowired
	@Qualifier("menuTopCacheHelper")
    protected WebCacheHelper menuTopCacheHelper;
	
	@Autowired
	@Qualifier("menuFooterCacheHelper")
    protected WebCacheHelper menuFooterCacheHelper;
	
	@Autowired
	@Qualifier("menuCustomerCacheHelper")
    protected WebCacheHelper menuCustomerCacheHelper;
	
	@Autowired
	@Qualifier("storeLocatorCacheHelper")
    protected WebCacheHelper storeLocatorCacheHelper;
	
	/**
     * 
     */
	public CommonViewBean buildCommonViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		final CommonViewBean commonViewBean = new CommonViewBean();
		
		// NO CACHE FOR THIS PART
		
		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request, EngineSettingService.ENGINE_SETTING_CONTEXT_FO_MCOMMERCE);
		commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

		commonViewBean.setHomeUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setLoginUrl(urlService.buildLoginUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setLoginLabel(coreMessageSource.getMessage("header.link.login", null, locale));
		commonViewBean.setForgottenPasswordUrl(urlService.buildContactUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setLogoutUrl(urlService.buildLogoutUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setLogoutLabel(coreMessageSource.getMessage("header.link.logout", null, locale));
		
		commonViewBean.setCreateAccountSectionTitle(coreMessageSource.getMessage("login.main.create.account.title", null, locale));
		commonViewBean.setCreateAccountSectionText(coreMessageSource.getMessage("login.main.create.account.text", null, locale));
		commonViewBean.setCreateAccountUrl(urlService.buildCustomerCreateAccountUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setCreateAccountLabel(coreMessageSource.getMessage("header.link.create.account", null, locale));
		
		commonViewBean.setCustomerDetailsUrl(urlService.buildCustomerDetailsUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setCustomerDetailsLabel(coreMessageSource.getMessage("header.link.my.account", null, locale));
		
		commonViewBean.setCurrentMarketPlace(buildMarketPlaceViewBean(request, marketPlace));
		commonViewBean.setCurrentMarket(buildMarketViewBean(request, market));
		commonViewBean.setCurrentMarketArea(buildMarketAreaViewBean(request, marketArea));
		commonViewBean.setCurrentLocalization(buildLocalizationViewBean(request, marketArea, localization));
		
		return commonViewBean;
	}
	
	/**
     * 
     */
	public HeaderCartViewBean buildHeaderCartViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		final HeaderCartViewBean headerCartViewBean = new HeaderCartViewBean();

		// NO CACHE FOR THIS PART

		final Cart currentCart = requestUtil.getCurrentCart(request);
		headerCartViewBean.setCartUrl(urlService.buildCartDetailsUrl(request, marketPlace, market, marketArea, localization, retailer));
		headerCartViewBean.setCartTotalItems(currentCart.getCartItems().size());
		if(currentCart.getCartItems().size() == 1) {
			headerCartViewBean.setCartTotalSummaryLabel(coreMessageSource.getMessage("cart.total.summary.label.one.item", null, locale));
		} else if(currentCart.getCartItems().size() > 1) {
			Object[] cartTotalSummaryLabelParams = {currentCart.getCartItems().size()};
			headerCartViewBean.setCartTotalSummaryLabel(coreMessageSource.getMessage("cart.total.summary.label.many.items", cartTotalSummaryLabelParams, locale));
		} else {
			headerCartViewBean.setCartTotalSummaryLabel(coreMessageSource.getMessage("cart.total.summary.label.no.item", null, locale));
		}
		
		return headerCartViewBean;
	}
	
	/**
     * 
     */
	public List<MarketPlaceViewBean> buildMarketPlaceViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType marketPlaceElementType = WebCacheHelper.ElementType.MARKET_PLACE_NAVIGATION_VIEW_BEAN_LIST;
		final String marketPlacePrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String marketPlaceCacheKey = marketPlacePrefixCacheKey + "_MARKETPLACE";
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
		marketPlaceViewBean.setUrl(urlService.buildHomeUrl(request, marketPlace, defaultMarket, defaultMarketArea, defaultLocalization, defaultRetailer));
		
		marketPlaceViewBean.setMarkets(buildMarketViewBeans(request, new ArrayList<Market>(marketPlace.getMarkets()), defaultLocalization));
		
		return marketPlaceViewBean;
	}
	
	/**
     * 
     */
	public List<MarketViewBean> buildMarketViewBeans(final HttpServletRequest request, final List<Market> markets, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType marketElementType = WebCacheHelper.ElementType.MARKET_NAVIGATION_VIEW_BEAN_LIST;
		final String marketPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String marketCacheKey = marketPrefixCacheKey + "_MARKET";
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
		marketViewBean.setUrl(urlService.buildHomeUrl(request, marketPlace, market, defaultMarketArea, defaultLocalization, defaultRetailer));
		
		marketViewBean.setMarketAreas(buildMarketAreaViewBeans(request, new ArrayList<MarketArea>(market.getMarketAreas()), defaultLocalization));
		
		return marketViewBean;
	}
	
	/**
     * 
     */
	public List<MarketAreaViewBean> buildMarketAreaViewBeans(final HttpServletRequest request, final List<MarketArea> marketAreas, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType marketAreaElementType = WebCacheHelper.ElementType.MARKET_AREA_VIEW_BEAN_LIST;
		final String marketAreaPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String marketAreaCacheKey = marketAreaPrefixCacheKey + "_MARKET_AREA";
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
		marketAreaViewBean.setUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, defaultLocalization, defaultRetailer));
		return marketAreaViewBean;
	}
	
	/**
     * 
     */
	public List<LocalizationViewBean> buildLocalizationViewBeans(final HttpServletRequest request, final MarketArea marketArea, final Localization localization) throws Exception {
		final WebCacheHelper.ElementType localizationElementType = WebCacheHelper.ElementType.LOCALIZATION_VIEW_BEAN_LIST;
		final String localizationPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String localizationCacheKey = localizationPrefixCacheKey + "_LOCALIZATION";
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
		final String localeCodeNavigation = localization.getLocaleCode();
		final Retailer retailer = requestUtil.getCurrentRetailer(request);
		
		final LocalizationViewBean localizationViewBean = new LocalizationViewBean();
		
		if(StringUtils.isNotEmpty(localeCodeNavigation)
				&& localeCodeNavigation.length() == 2) {
			localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCodeNavigation.toLowerCase(), null, locale));
		} else {
			localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCodeNavigation, null, locale));
		}
		
		localizationViewBean.setUrl(urlService.buildChangeLanguageUrl(request, marketPlace, market, marketArea, localization, retailer));
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
		retailerViewBean.setUrl(urlService.buildChangeLanguageUrl(request, marketPlace, market, marketArea, localization, retailer));
		return retailerViewBean;
	}
	
	/**
     * 
     */
	public List<MenuViewBean> buildMenuViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final WebCacheHelper.ElementType menuTopElementType = WebCacheHelper.ElementType.TOP_MENU_VIEW_BEAN_LIST;
		String menuTopPrefixCacheKey = menuTopCacheHelper.buildPrefixKey(marketPlace, market, marketArea, localization, retailer, menuTopElementType);
		String menuTopCacheKey = menuTopPrefixCacheKey + "_GLOBAL";
		List<MenuViewBean> menuViewBeans = (List<MenuViewBean>) menuTopCacheHelper.getFromCache(menuTopElementType, menuTopCacheKey);
		if(menuViewBeans == null){
			final Locale locale = localization.getLocale();
			final String localeCode = localization.getLocaleCode();

			menuViewBeans = new ArrayList<MenuViewBean>();
			
			MenuViewBean menu = new MenuViewBean();
			menu.setName(coreMessageSource.getMessage("header.menu.home", null, locale));
			menu.setUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));
			menuViewBeans.add(menu);

			CatalogVirtual productCatalog = ProductCatalogService.getCatalogVirtualByCode(marketArea.getId(), retailer.getId(), marketArea.getVirtualCatalog().getCode());
			
			final List<ProductCategoryVirtual> productCategoies = productCatalog.getProductCategories(marketArea.getId());
			if(productCategoies != null) {
				for (Iterator<ProductCategoryVirtual> iteratorProductCategory = productCategoies.iterator(); iteratorProductCategory.hasNext();) {
					final ProductCategoryVirtual productCategory = (ProductCategoryVirtual) iteratorProductCategory.next();
					menu = new MenuViewBean();
					final String seoProductCategoryName = productCategory.getI18nName(localeCode);
					final String seoProductCategoryCode = productCategory.getCode();
					menu.setName(seoProductCategoryName);
					menu.setUrl(urlService.buildProductCategoryUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, seoProductCategoryName, seoProductCategoryCode));
					
					List<ProductCategoryVirtual> subProductCategories = productCategory.getProductCategories(marketArea.getId());
					if(subProductCategories != null) {
						List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();
						for (Iterator<ProductCategoryVirtual> iteratorSubProductCategory = subProductCategories.iterator(); iteratorSubProductCategory.hasNext();) {
							final ProductCategoryVirtual subProductCategory = (ProductCategoryVirtual) iteratorSubProductCategory.next();
							final MenuViewBean subMenu = new MenuViewBean();
							final String seoSubProductCategoryName = productCategory.getI18nName(localeCode) + " " + subProductCategory.getI18nName(localeCode);
							final String seoSubProductCategoryCode = subProductCategory.getCode();
							subMenu.setName(seoSubProductCategoryName);
							subMenu.setUrl(urlService.buildProductCategoryUrlAsProductLineUrl(request, marketPlace, market, marketArea, localization, retailer, seoSubProductCategoryName, seoSubProductCategoryCode));
							subMenus.add(subMenu);
						}
						menu.setSubMenus(subMenus);
					}
					menuViewBeans.add(menu);
				}
			}
			
			menu = new MenuViewBean();
			menu.setName(coreMessageSource.getMessage("header.menu.our.company", null, locale));
			menu.setUrl(urlService.buildOurCompanyUrl(request, marketPlace, market, marketArea, localization, retailer));
			menuViewBeans.add(menu);
			
			menuTopCacheHelper.addToCache(menuTopElementType, menuTopCacheKey, menuViewBeans);
		}
		
		return menuViewBeans;
	}
	
	/**
     * 
     */
	public List<CutomerMenuViewBean> buildCutomerMenuViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final WebCacheHelper.ElementType customerMenuElementType = WebCacheHelper.ElementType.CUSTOMER_MENU_VIEW_BEAN_LIST;
		final String customerMenuPrefixCacheKey = menuCustomerCacheHelper.buildGlobalPrefixKey(localization);
		final String customerMenuCacheKey = customerMenuPrefixCacheKey + "_CUSTOMER_MENU";
		List<CutomerMenuViewBean> customerLinks = (List<CutomerMenuViewBean>) menuCustomerCacheHelper.getFromCache(customerMenuElementType, customerMenuCacheKey);
		if(customerLinks == null){
			final Locale locale = localization.getLocale();

			customerLinks = new ArrayList<CutomerMenuViewBean>();
			CutomerMenuViewBean cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(coreMessageSource.getMessage("customer.details.label", null, locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerDetailsUrl(request, marketPlace, market, marketArea, localization, retailer));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(coreMessageSource.getMessage("customer.address.list.label", null, locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerAddressListUrl(request, marketPlace, market, marketArea, localization, retailer));
			customerLinks.add(cutomerMenuViewBean);
			
			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(coreMessageSource.getMessage("customer.add.address.label", null, locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerAddAddressUrl(request, marketPlace, market, marketArea, localization, retailer));
			customerLinks.add(cutomerMenuViewBean);
			
			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(coreMessageSource.getMessage("customer.order.list.label", null, locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerOrderListUrl(request, marketPlace, market, marketArea, localization, retailer));
			customerLinks.add(cutomerMenuViewBean);
			
			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(coreMessageSource.getMessage("customer.wishlist.label", null, locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerWishlistUrl(request, marketPlace, market, marketArea, localization, retailer));
			customerLinks.add(cutomerMenuViewBean);
			
			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(coreMessageSource.getMessage("customer.product.comment.label", null, locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerProductCommentUrl(request, marketPlace, market, marketArea, localization, retailer));
			customerLinks.add(cutomerMenuViewBean);
			
			menuCustomerCacheHelper.addToCache(customerMenuElementType, customerMenuCacheKey, customerLinks);
		}
		return customerLinks;
	}
	
	/**
     * 
     */
	public List<FooterMenuViewBean> buildFooterMenuViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final WebCacheHelper.ElementType footerMenuElementType = WebCacheHelper.ElementType.FOOTER_MENU_VIEW_BEAN_LIST;
		final String footerMenuPrefixCacheKey = menuFooterCacheHelper.buildGlobalPrefixKey(localization);
		final String footerMenuCacheKey = footerMenuPrefixCacheKey + "_FOOTER_MENU";
		List<FooterMenuViewBean> footerMenuViewBeans = (List<FooterMenuViewBean>) menuFooterCacheHelper.getFromCache(footerMenuElementType, footerMenuCacheKey);
		if(footerMenuViewBeans == null){
			final Locale locale = localization.getLocale();
			footerMenuViewBeans = new ArrayList<FooterMenuViewBean>();
			
			FooterMenuViewBean footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(coreMessageSource.getMessage("header.menu.conditionsofuse", null, locale));
			footerMenuList.setUrl(urlService.buildConditionOfUseUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);
			
			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(coreMessageSource.getMessage("header.menu.legacy", null, locale));
			footerMenuList.setUrl(urlService.buildLegacyUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);
			
			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(coreMessageSource.getMessage("header.menu.faq", null, locale));
			footerMenuList.setUrl(urlService.buildFaqUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);
	
			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(coreMessageSource.getMessage("header.menu.store.location", null, locale));
			footerMenuList.setUrl(urlService.buildStoreLocationUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);
			
			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(coreMessageSource.getMessage("header.menu.contactus", null, locale));
			footerMenuList.setUrl(urlService.buildContactUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);
			
			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(coreMessageSource.getMessage("header.menu.followus", null, locale));
			footerMenuList.setUrl(urlService.buildFollowUsUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);
			
			menuFooterCacheHelper.addToCache(footerMenuElementType, footerMenuCacheKey, footerMenuViewBeans);
		}
		return footerMenuViewBeans;
	}
	
	/**
     * 
     */
	public ContactUsViewBean buildContactUsViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final ContactUsViewBean contactUs = new ContactUsViewBean();
		contactUs.setLastnameLabel(coreMessageSource.getMessage("form.contact.us.label.lastname", null, locale));
		contactUs.setFirstnameLabel(coreMessageSource.getMessage("form.contact.us.label.firstname", null, locale));
		contactUs.setCountryLabel(coreMessageSource.getMessage("form.contact.us.label.country", null, locale));
		contactUs.setEmailLabel(coreMessageSource.getMessage("form.contact.us.label.email", null, locale));
		contactUs.setPhoneLabel(coreMessageSource.getMessage("form.contact.us.label.phone", null, locale));
		contactUs.setFaxLabel(coreMessageSource.getMessage("form.contact.us.label.fax", null, locale));
		contactUs.setMobileLabel(coreMessageSource.getMessage("form.contact.us.label.mobile", null, locale));
		contactUs.setWebsiteLabel(coreMessageSource.getMessage("form.contact.us.label.website", null, locale));
		contactUs.setSubjectLabel(coreMessageSource.getMessage("form.contact.us.label.subject", null, locale));
		contactUs.setMessageLabel(coreMessageSource.getMessage("form.contact.us.label.message", null, locale));

		contactUs.setSuccessMessage(coreMessageSource.getMessage("form.contact.us.success.message", null, locale));
		contactUs.setFailMessage(coreMessageSource.getMessage("form.contact.us.fail.message", null, locale));

		contactUs.setSubmitLabel(coreMessageSource.getMessage("form.contact.us.label.submit", null, locale));
		contactUs.setCancelLabel(coreMessageSource.getMessage("form.contact.us.label.cancel", null, locale));
		contactUs.setBackUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));

		return contactUs;
	}
	
	/**
     * 
     */
	public FollowUsViewBean buildFollowUsViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final FollowUsViewBean followUs = new FollowUsViewBean();
		followUs.setEmailLabel(coreMessageSource.getMessage("form.follow.us.label.email", null, locale));
		followUs.setSubmitLabel(coreMessageSource.getMessage("form.follow.us.label.submit", null, locale));
		followUs.setCancelLabel(coreMessageSource.getMessage("form.follow.us.label.cancel", null, locale));
		
		followUs.setBackUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));

		followUs.setSuccessMessage(coreMessageSource.getMessage("form.follow.us.success.message", null, locale));
		followUs.setFailMessage(coreMessageSource.getMessage("form.follow.us.fail.message", null, locale));

		final List<FollowUsOptionViewBean> followOptions = new ArrayList<FollowUsOptionViewBean>();
		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request, EngineSettingService.ENGINE_SETTING_CONTEXT_FO_MCOMMERCE);

		String followType = "facebook";
		FollowUsOptionViewBean followOption = new FollowUsOptionViewBean();
		followOption.setUrl(coreMessageSource.getMessage("follow.us." + followType + ".url", null, locale));
		followOption.setUrlLabel(coreMessageSource.getMessage("follow.us." + followType + ".url.label", null, locale));
		followOption.setUrlImg(currentThemeResourcePrefixPath + coreMessageSource.getMessage("follow.us." + followType + ".url.img", null, locale));
		followOption.setTitle(coreMessageSource.getMessage("follow.us." + followType + ".title", null, locale));
		followOption.setText(coreMessageSource.getMessage("follow.us." + followType + ".text", null, locale));
		followOptions.add(followOption);
		
		followType = "twitter";
		followOption = new FollowUsOptionViewBean();
		followOption.setUrl(coreMessageSource.getMessage("follow.us." + followType + ".url", null, locale));
		followOption.setUrlLabel(coreMessageSource.getMessage("follow.us." + followType + ".url.label", null, locale));
		followOption.setUrlImg(currentThemeResourcePrefixPath + coreMessageSource.getMessage("follow.us." + followType + ".url.img", null, locale));
		followOption.setTitle(coreMessageSource.getMessage("follow.us." + followType + ".title", null, locale));
		followOption.setText(coreMessageSource.getMessage("follow.us." + followType + ".text", null, locale));
		followOptions.add(followOption);
		
		followType = "google.plus";
		followOption = new FollowUsOptionViewBean();
		followOption.setUrl(coreMessageSource.getMessage("follow.us." + followType + ".url", null, locale));
		followOption.setUrlLabel(coreMessageSource.getMessage("follow.us." + followType + ".url.label", null, locale));
		followOption.setUrlImg(currentThemeResourcePrefixPath + coreMessageSource.getMessage("follow.us." + followType + ".url.img", null, locale));
		followOption.setTitle(coreMessageSource.getMessage("follow.us." + followType + ".title", null, locale));
		followOption.setText(coreMessageSource.getMessage("follow.us." + followType + ".text", null, locale));
		followOptions.add(followOption);
		
		followType = "blog";
		followOption = new FollowUsOptionViewBean();
		followOption.setUrl(coreMessageSource.getMessage("follow.us." + followType + ".url", null, locale));
		followOption.setUrlLabel(coreMessageSource.getMessage("follow.us." + followType + ".url.label", null, locale));
		followOption.setUrlImg(currentThemeResourcePrefixPath + coreMessageSource.getMessage("follow.us." + followType + ".url.img", null, locale));
		followOption.setTitle(coreMessageSource.getMessage("follow.us." + followType + ".title", null, locale));
		followOption.setText(coreMessageSource.getMessage("follow.us." + followType + ".text", null, locale));
		followOptions.add(followOption);
		
		followUs.setFollowOptions(followOptions);
		
		return followUs;
	}
	
	/**
     * 
     */
	public LegacyViewBean buildLegacyViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
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
	public OurCompanyViewBean buildOurCompanyViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final OurCompanyViewBean ourCompany = new OurCompanyViewBean();
		ourCompany.setPageTitle(coreMessageSource.getMessage("header.title.our.company", null, locale));
		ourCompany.setTextHtml(coreMessageSource.getMessage("our.company.content.text", null, locale));
		return ourCompany;
	}
	
	/**
     * 
     */
	public FaqViewBean buildFaqViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final FaqViewBean faq = new FaqViewBean();
		faq.setPageTitle(coreMessageSource.getMessage("header.title.faq", null, locale));
		faq.setTextHtml(coreMessageSource.getMessage("faq.content.text", null, locale));
		return faq;
	}
	
	/**
     * 
     */
	public SecurityViewBean buildSecurityViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
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
		security.setLoginUrl(urlService.buildSpringSecurityCheckUrl(request, marketPlace, market, marketArea, localization, retailer));
		security.setLoginLabel(coreMessageSource.getMessage("login.form.login.label", null, locale));
		security.setForgottenPasswordUrl(urlService.buildForgottenPasswordUrl(request, marketPlace, market, marketArea, localization, retailer));
		security.setForgottenPasswordLabel(coreMessageSource.getMessage("login.form.forgotten.password.label", null, locale));
		security.setPasswordLabel(coreMessageSource.getMessage("login.form.password.label", null, locale));
		security.setRememberLabel(coreMessageSource.getMessage("login.form.remember.label", null, locale));
		security.setSubmitLabel(coreMessageSource.getMessage("login.form.login.submit", null, locale));
		
		return security;
	}
	
	/**
     * 
     */
	public StoreLocatorViewBean buildStoreLocatorViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final List<Store> stores) throws Exception {
		
		final WebCacheHelper.ElementType storeLocatorElementType = WebCacheHelper.ElementType.STORE_VIEW_BEAN_LIST;
		final String storeLocatorPrefixCacheKey = storeLocatorCacheHelper.buildGlobalPrefixKey(localization);
		final String storeLocatorCacheKey = storeLocatorPrefixCacheKey + "_STORE_LOCATOR";
		StoreLocatorViewBean storeLocator = (StoreLocatorViewBean) storeLocatorCacheHelper.getFromCache(storeLocatorElementType, storeLocatorCacheKey);
		if(storeLocator == null){
			final Locale locale = localization.getLocale();
			storeLocator = new StoreLocatorViewBean();
			storeLocator.setPageTitle(coreMessageSource.getMessage("header.title.store.location", null, locale));
			storeLocator.setTextHtml(coreMessageSource.getMessage("store.location.content.text", null, locale));
			for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
				final Store store = (Store) iterator.next();
				storeLocator.getStores().add(buildStoreViewBean(request, marketPlace, market, marketArea, localization, retailer, store));
			}
			storeLocatorCacheHelper.addToCache(storeLocatorElementType, storeLocatorCacheKey, storeLocator);
		}
		return storeLocator;
	}
	
	/**
     * 
     */
	public StoreViewBean buildStoreViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final Store store) throws Exception {
		
		final StoreViewBean storeLocator = new StoreViewBean();
		storeLocator.setCode(store.getCode());
		storeLocator.setBusinessName(store.getBusinessName());
		storeLocator.setAddress1(store.getAddress1());
		storeLocator.setAddress2(store.getAddress2());
		storeLocator.setAddressAdditionalInformation(store.getAddressAdditionalInformation());
		storeLocator.setPostalCode(store.getPostalCode());
		
		// I18n values
		storeLocator.setCity(store.getI18nCity(localization));

		storeLocator.setCountyCode(store.getCountyCode());
		storeLocator.setCounty(store.getCountyCode());
		storeLocator.setCountry(store.getCountryCode());
		storeLocator.setCountryCode(store.getCountryCode());
		storeLocator.setLongitude(store.getLongitude());
		storeLocator.setLatitude(store.getLatitude());
		
		return storeLocator;
	}
	
	/**
     * 
     */
	public CustomerViewBean buildCustomerDetailsAccountViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final Customer customer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final CustomerViewBean customerViewBean = new CustomerViewBean();
		customerViewBean.setFirstnameLabel(coreMessageSource.getMessage("customer.details.firstname.label", null, locale));
		customerViewBean.setFirstnameValue(customer.getFirstname());

		customerViewBean.setLastnameLabel(coreMessageSource.getMessage("customer.details.lastname.label", null, locale));
		customerViewBean.setLastnameValue(customer.getLastname());

		customerViewBean.setEmailLabel(coreMessageSource.getMessage("customer.details.email.label", null, locale));
		customerViewBean.setEmailValue(customer.getEmail());

		customerViewBean.setDateCreateLabel(coreMessageSource.getMessage("customer.details.datecreate.label", null, locale));
		DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
		if(customer.getDateCreate() != null) {
			customerViewBean.setDateCreateValue(dateFormat.format(customer.getDateCreate()));
		} else {
			customerViewBean.setDateCreateValue(Constants.NOT_AVAILABLE);
		}
		
		customerViewBean.setDateUpdateLabel(coreMessageSource.getMessage("customer.details.dateupdate.label", null, locale));
		if(customer.getDateUpdate() != null) {
			customerViewBean.setDateUpdateValue(dateFormat.format(customer.getDateUpdate()));
		} else {
			customerViewBean.setDateUpdateValue(Constants.NOT_AVAILABLE);
		}
		
		final ValueBean customerScreenNameValueBean = new ValueBean();
		customerScreenNameValueBean.setKey(coreMessageSource.getMessage("customer.details.screenname.label", null, locale));
		customerScreenNameValueBean.setValue(customer.getScreenName());
		customerViewBean.getCustomerAttributes().put("screenName", customerScreenNameValueBean);
		
		return customerViewBean;
	}
	
	/**
     * 
     */
	public CustomerWishlistViewBean buildCustomerWishlistViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final Customer customer) throws Exception {
		
		final CustomerWishlistViewBean customerWishlistViewBean = new CustomerWishlistViewBean();
		
		final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getCode());
		if(customerMarketArea != null) {
			final Set<CustomerWishlist> customerWishlists = customerMarketArea.getWishlistProducts();
			if(customerWishlists != null) {
				for (Iterator<CustomerWishlist> iterator = customerWishlists.iterator(); iterator.hasNext();) {
					final CustomerWishlist customerWishlist = (CustomerWishlist) iterator.next();
					final ProductSku productSku = productSkuService.getProductSkuByCode(customerMarketArea.getId(), retailer.getId(), customerWishlist.getProductSkuCode());
					final ProductMarketing productMarketing = productSku.getProductMarketing();
					final ProductCategoryVirtual productCategory = productCategoryService.getDefaultProductCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
					customerWishlistViewBean.getProductSkus().add(buildProductSkuViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing, productSku));
				}
			}
		}
		
		return customerWishlistViewBean;
	}
	
	/**
     * 
     */
	public CustomerProductCommentsViewBean buildCustomerProductCommentsViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final Customer customer) throws Exception {

		final CustomerProductCommentsViewBean customerProductCommentsViewBean = new CustomerProductCommentsViewBean();
		
		final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getCode());
		if(customerMarketArea != null) {
			final Set<CustomerProductComment> customerProductComments = customerMarketArea.getProductComments();
			if(customerProductComments != null) {
				for (Iterator<CustomerProductComment> iterator = customerProductComments.iterator(); iterator.hasNext();) {
					final CustomerProductComment customerProductComment = (CustomerProductComment) iterator.next();
					final ProductSku productSku = productSkuService.getProductSkuByCode(customerMarketArea.getId(), retailer.getId(), customerProductComment.getProductSkuCode());
					final ProductMarketing productMarketing = productSku.getProductMarketing();
					final ProductCategoryVirtual productCategory = productCategoryService.getDefaultProductCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
					customerProductCommentsViewBean.getCustomerProductCommentViewBeans().add(buildCustomerProductCommentViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing, productSku, customerProductComment));
				}
			}
		}
		return customerProductCommentsViewBean;
	}
	
	/**
     * 
     */
	public CustomerProductCommentViewBean buildCustomerProductCommentViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
													   final Retailer retailer, final ProductCategoryVirtual productCategory, final ProductMarketing productMarketing, final ProductSku productSku, final CustomerProductComment customerProductComment) 
													   throws Exception {
		final CustomerProductCommentViewBean customerProductCommentViewBean = new CustomerProductCommentViewBean();
		customerProductCommentViewBean.setProductSku(buildProductSkuViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing, productSku));
		customerProductCommentViewBean.setComment(customerProductComment.getComment());
		return customerProductCommentViewBean;
	}
	
	/**
     * 
     */
	public CustomerCreateAccountViewBean buildCustomerCreateAccountViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final CustomerCreateAccountViewBean customerCreateAccountViewBean = new CustomerCreateAccountViewBean();
		customerCreateAccountViewBean.setTitleLabel(coreMessageSource.getMessage("form.customer.create.account.label.title", null, locale));
		customerCreateAccountViewBean.setLastnameLabel(coreMessageSource.getMessage("form.customer.create.account.label.lastname", null, locale));
		customerCreateAccountViewBean.setFirstnameLabel(coreMessageSource.getMessage("form.customer.create.account.label.firstname", null, locale));
		
		customerCreateAccountViewBean.setAddress1Label(coreMessageSource.getMessage("form.customer.create.account.label.address1", null, locale));
		customerCreateAccountViewBean.setAddress2Label(coreMessageSource.getMessage("form.customer.create.account.label.address2", null, locale));
		customerCreateAccountViewBean.setAddressAdditionalInformationLabel(coreMessageSource.getMessage("form.customer.create.account.label.address.additional.information", null, locale));
		customerCreateAccountViewBean.setPostalCodeLabel(coreMessageSource.getMessage("form.customer.create.account.label.postal.code", null, locale));
		customerCreateAccountViewBean.setCityLabel(coreMessageSource.getMessage("form.customer.create.account.label.city", null, locale));
		customerCreateAccountViewBean.setCountyCodeLabel(coreMessageSource.getMessage("form.customer.create.account.label.county.code", null, locale));
		customerCreateAccountViewBean.setCountryCodeLabel(coreMessageSource.getMessage("form.customer.create.account.label.country.code", null, locale));
		
		customerCreateAccountViewBean.setEmailLabel(coreMessageSource.getMessage("form.customer.create.account.label.email", null, locale));
		customerCreateAccountViewBean.setPasswordLabel(coreMessageSource.getMessage("form.customer.create.account.label.password", null, locale));
		
		customerCreateAccountViewBean.setPhoneLabel(coreMessageSource.getMessage("form.customer.create.account.label.phone", null, locale));
		customerCreateAccountViewBean.setFaxLabel(coreMessageSource.getMessage("form.customer.create.account.label.fax", null, locale));
		customerCreateAccountViewBean.setMobileLabel(coreMessageSource.getMessage("form.customer.create.account.label.mobile", null, locale));

		customerCreateAccountViewBean.setOptinLabel(coreMessageSource.getMessage("form.customer.create.account.label.optin", null, locale));

		customerCreateAccountViewBean.setSuccessMessage(coreMessageSource.getMessage("form.customer.create.account.success.message", null, locale));
		customerCreateAccountViewBean.setFailMessage(coreMessageSource.getMessage("form.customer.create.account.fail.message", null, locale));

		customerCreateAccountViewBean.setSubmitLabel(coreMessageSource.getMessage("form.customer.create.account.label.submit", null, locale));
		customerCreateAccountViewBean.setCancelLabel(coreMessageSource.getMessage("form.customer.create.account.label.cancel", null, locale));
		customerCreateAccountViewBean.setBackUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));

		return customerCreateAccountViewBean;
	}
	
	/**
     * 
     */
	public CustomerAddressFormViewBean buildCustomerAddressFormViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final CustomerAddressFormViewBean customerAddAddressViewBean = new CustomerAddressFormViewBean();
		customerAddAddressViewBean.setAddressNameLabel(coreMessageSource.getMessage("form.customer.address.label.address.name", null, locale));
		customerAddAddressViewBean.setTitleLabel(coreMessageSource.getMessage("form.customer.address.label.title", null, locale));
		customerAddAddressViewBean.setLastnameLabel(coreMessageSource.getMessage("form.customer.address.label.lastname", null, locale));
		customerAddAddressViewBean.setFirstnameLabel(coreMessageSource.getMessage("form.customer.address.label.firstname", null, locale));
		
		customerAddAddressViewBean.setAddress1Label(coreMessageSource.getMessage("form.customer.address.label.address1", null, locale));
		customerAddAddressViewBean.setAddress2Label(coreMessageSource.getMessage("form.customer.address.label.address2", null, locale));
		customerAddAddressViewBean.setAddressAdditionalInformationLabel(coreMessageSource.getMessage("form.customer.address.label.address.additional.information", null, locale));
		customerAddAddressViewBean.setPostalCodeLabel(coreMessageSource.getMessage("form.customer.address.label.postal.code", null, locale));
		customerAddAddressViewBean.setCityLabel(coreMessageSource.getMessage("form.customer.address.label.city", null, locale));
		customerAddAddressViewBean.setCountyCodeLabel(coreMessageSource.getMessage("form.customer.address.label.county.code", null, locale));
		customerAddAddressViewBean.setCountryCodeLabel(coreMessageSource.getMessage("form.customer.address.label.country.code", null, locale));
		
		customerAddAddressViewBean.setSuccessMessage(coreMessageSource.getMessage("form.customer.address.success.message", null, locale));
		customerAddAddressViewBean.setFailMessage(coreMessageSource.getMessage("form.customer.address.fail.message", null, locale));

		customerAddAddressViewBean.setCreateLabel(coreMessageSource.getMessage("form.customer.address.label.create", null, locale));
		customerAddAddressViewBean.setUpdateLabel(coreMessageSource.getMessage("form.customer.address.label.update", null, locale));
		customerAddAddressViewBean.setCancelLabel(coreMessageSource.getMessage("form.customer.address.label.cancel", null, locale));
		customerAddAddressViewBean.setBackUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));

		return customerAddAddressViewBean;
	}
	
	/**
     * 
     */
	public CustomerAddressListViewBean buildCustomerAddressListViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final Customer customer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final CustomerAddressListViewBean customerAddressListViewBean = new CustomerAddressListViewBean();
		customerAddressListViewBean.setAddressNameLabel(coreMessageSource.getMessage("customer.address.list.label.address.name", null, locale));
		customerAddressListViewBean.setTitleLabel(coreMessageSource.getMessage("customer.address.list.label.title", null, locale));
		customerAddressListViewBean.setLastnameLabel(coreMessageSource.getMessage("customer.address.list.label.lastname", null, locale));
		customerAddressListViewBean.setFirstnameLabel(coreMessageSource.getMessage("customer.address.list.label.firstname", null, locale));
		
		customerAddressListViewBean.setAddress1Label(coreMessageSource.getMessage("customer.address.list.label.address1", null, locale));
		customerAddressListViewBean.setAddress2Label(coreMessageSource.getMessage("customer.address.list.label.address2", null, locale));
		customerAddressListViewBean.setAddressAdditionalInformationLabel(coreMessageSource.getMessage("customer.address.list.label.address.additional.information", null, locale));
		customerAddressListViewBean.setPostalCodeLabel(coreMessageSource.getMessage("customer.address.list.label.postal.code", null, locale));
		customerAddressListViewBean.setCityLabel(coreMessageSource.getMessage("customer.address.list.label.city", null, locale));
		customerAddressListViewBean.setCountyCodeLabel(coreMessageSource.getMessage("customer.address.list.label.county.code", null, locale));
		customerAddressListViewBean.setCountryCodeLabel(coreMessageSource.getMessage("customer.address.list.label.country.code", null, locale));

		customerAddressListViewBean.setDefaultShippingAddressLabel(coreMessageSource.getMessage("customer.address.list.label.default.shipping.address", null, locale));
		customerAddressListViewBean.setDefaultBillingAddressLabel(coreMessageSource.getMessage("customer.address.list.label.default.billing.address", null, locale));

		customerAddressListViewBean.setBackUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));

		Set<CustomerAddress> addresses = customer.getAddresses();
		for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
			CustomerAddress customerAddress = (CustomerAddress) iterator.next();
			customerAddressListViewBean.getCustomerAddressList().add(buildCustomeAddressViewBean(request, marketPlace, market, marketArea, localization, retailer, customerAddress));
		}
		
		return customerAddressListViewBean;
	}
	
	/**
     * 
     */
	public CustomerAddressViewBean buildCustomeAddressViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final CustomerAddress customerAddress) throws Exception {
		final Locale locale = localization.getLocale();
		
		final CustomerAddressViewBean customerAddressViewBean = new CustomerAddressViewBean();

		customerAddressViewBean.setId(customerAddress.getId());
		
		String addressName = customerAddress.getAddressName();
		if(StringUtils.isNotEmpty(addressName)) {
			customerAddressViewBean.setAddressName(addressName);
		} else {
			customerAddressViewBean.setAddressName(customerAddress.getCity());
		}

		customerAddressViewBean.setTitle(customerAddress.getTitle());
		customerAddressViewBean.setLastname(customerAddress.getLastname());
		customerAddressViewBean.setFirstname(customerAddress.getFirstname());
		
		customerAddressViewBean.setAddress1(customerAddress.getAddress1());
		customerAddressViewBean.setAddress2(customerAddress.getAddress2());
		customerAddressViewBean.setAddressAdditionalInformation(customerAddress.getAddressAdditionalInformation());
		customerAddressViewBean.setPostalCode(customerAddress.getPostalCode());
		customerAddressViewBean.setCity(customerAddress.getCity());
		customerAddressViewBean.setCountyCode(customerAddress.getCountyCode());
		customerAddressViewBean.setCountryCode(customerAddress.getCountryCode());
		
		customerAddressViewBean.setDefaultBilling(customerAddress.isDefaultBilling());
		customerAddressViewBean.setDefaultShipping(customerAddress.isDefaultShipping());
		
		Long customerAddressId = customerAddress.getId();
		
		customerAddressViewBean.setEditLabel(coreMessageSource.getMessage("form.customer.address.label.edit", null, locale));
		customerAddressViewBean.setEditUrl(urlService.buildCustomerEditAddressUrl(request, marketPlace, market, marketArea, localization, retailer, customerAddressId.toString()));

		customerAddressViewBean.setDeleteLabel(coreMessageSource.getMessage("form.customer.address.label.delete", null, locale));
		customerAddressViewBean.setDeleteUrl(urlService.buildCustomerDeleteAddressUrl(request, marketPlace, market, marketArea, localization, retailer, customerAddressId.toString()));
		
		return customerAddressViewBean;
	}
	
	/**
     * 
     */
	public ConditionsViewBean buildConditionsViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final ConditionsViewBean conditions = new ConditionsViewBean();
		
		conditions.setPageTitle(coreMessageSource.getMessage("header.title.conditionsofuse", null, locale));
		conditions.setTextHtml(coreMessageSource.getMessage("conditionsofuse.content.text", null, locale));
		
		return conditions;
	}
	
	/**
     * 
     */
	public ProductBrandViewBean buildProductBrandViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final ProductBrand productBrand) throws Exception {
		final ProductBrandViewBean productBrandViewBean = new ProductBrandViewBean();
		productBrandViewBean.setName(productBrand.getName());
		productBrandViewBean.setDescription(productBrand.getDescription());
		return productBrandViewBean;
	}
	
	/**
     * 
     */
	public ProductBrandViewBean buildProductBrandViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final ProductBrand productBrand, final List<ProductMarketing> productMarketings) throws Exception {
		final ProductBrandViewBean productBrandViewBean = buildProductBrandViewBean(request, marketPlace, market, marketArea, localization, retailer, productBrand);
		for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
			final ProductMarketing productMarketing = (ProductMarketing) iterator.next();
			ProductCategoryVirtual productCategory = productCategoryService.getDefaultProductCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
			productBrandViewBean.getProductMarketings().add(buildProductMarketingViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing));
		}
		return productBrandViewBean;
	}
	
	/**
     * 
     */
	public ProductCategoryViewBean buildMasterProductCategoryViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final ProductCategoryVirtual productCategory) throws Exception {
		final ProductCategoryViewBean productCategoryViewBean = buildProductCategoryViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory);
		return productCategoryViewBean;
	}
	
	/**
     * 
     */
	public ProductCategoryViewBean buildProductCategoryViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
																final Localization localization, final Retailer retailer, final ProductCategoryVirtual productCategory) throws Exception {
		final String localeCode = localization.getLocaleCode();
		final ProductCategoryViewBean productCategoryViewBean = new ProductCategoryViewBean();
		
		productCategoryViewBean.setName(productCategory.getI18nName(localeCode));
		productCategoryViewBean.setDescription(productCategory.getDescription());
		productCategoryViewBean.setRoot(productCategory.isRoot());
		
		final String currentCatalogResourcePrefixPath = requestUtil.getCurrentCatalogImageResourcePrefixPath(request, marketArea.getCode());
		final ProductImage defaultBackgroundImage = productCategory.getDefaultBackgroundImage();
		if(defaultBackgroundImage != null){
			final String backgroundImage = currentCatalogResourcePrefixPath + defaultBackgroundImage.getPath();
			productCategoryViewBean.setBackgroundImage(backgroundImage);
		} else {
			productCategoryViewBean.setBackgroundImage("");
		}
		final ProductImage defaultPaskshotImage = productCategory.getDefaultPaskshotImage(ImageSize.SMALL.getPropertyKey());
		if(defaultPaskshotImage != null){
			final String carouselImage = currentCatalogResourcePrefixPath + defaultPaskshotImage.getPath();
			productCategoryViewBean.setCarouselImage(carouselImage);
		} else {
			productCategoryViewBean.setCarouselImage("");
		}
		final ProductImage defaultIconImage = productCategory.getDefaultIconImage();
		if(defaultIconImage != null){
			final String iconImage = currentCatalogResourcePrefixPath + defaultIconImage.getPath();
			productCategoryViewBean.setIconImage(iconImage);
		} else {
			productCategoryViewBean.setIconImage("");
		}
		
		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		if(productCategory.isRoot()) {
			productCategoryViewBean.setProductAxeUrl(urlService.buildProductCategoryUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode));
		} else {
			productCategoryViewBean.setProductLineUrl(urlService.buildProductCategoryUrlAsProductLineUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode));
		}

		List<ProductCategoryViewBean> subProductCategoryViewBeans = new ArrayList<ProductCategoryViewBean>();
		Set<ProductCategoryVirtual> subCategories = productCategory.getProductCategories();
		if(subCategories != null){
			for (Iterator<ProductCategoryVirtual> iteratorSubProductCategory = subCategories.iterator(); iteratorSubProductCategory.hasNext();) {
				final ProductCategoryVirtual subProductCategory = (ProductCategoryVirtual) iteratorSubProductCategory.next();
				subProductCategoryViewBeans.add(buildProductCategoryViewBean(request, marketPlace, market, marketArea, localization, retailer, subProductCategory));
			}
		}
		productCategoryViewBean.setSubCategories(subProductCategoryViewBeans);

		List<ProductMarketingViewBean> productMarketingViewBeans = new ArrayList<ProductMarketingViewBean>();
		Set<ProductMarketing> productMarketings = productCategory.getProductMarketings();
		if(productMarketings != null){
			for (Iterator<ProductMarketing> iteratorProductMarketing = productMarketings.iterator(); iteratorProductMarketing.hasNext();) {
				final ProductMarketing productMarketing = (ProductMarketing) iteratorProductMarketing.next();
				productMarketingViewBeans.add(buildProductMarketingViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing));
			}
		}
		productCategoryViewBean.setProductMarketings(productMarketingViewBeans);

		return productCategoryViewBean;
	}
	
	/**
     * 
     */
	public ProductMarketingViewBean buildProductMarketingViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
																   final Localization localization, final Retailer retailer, final ProductCategoryVirtual productCategory, final ProductMarketing productMarketing) 
																   throws Exception {
		final String localeCode = localization.getLocaleCode();
		final ProductMarketingViewBean productMarketingViewBean = new ProductMarketingViewBean();
		
		productMarketingViewBean.setName(productMarketing.getI18nName(localeCode));
		productMarketingViewBean.setDescription(productMarketing.getDescription());
		
		final String currentCatalogResourcePrefixPath = requestUtil.getCurrentCatalogImageResourcePrefixPath(request, marketArea.getCode());
		final ProductImage defaultBackgroundImage = productMarketing.getDefaultBackgroundImage();
		if(defaultBackgroundImage != null){
			final String backgroundImage = currentCatalogResourcePrefixPath + defaultBackgroundImage.getPath();
			productMarketingViewBean.setBackgroundImage(backgroundImage);
		} else {
			productMarketingViewBean.setBackgroundImage("");
		}
		final ProductImage defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL.getPropertyKey());
		if(defaultPaskshotImage != null){
			final String carouselImage = currentCatalogResourcePrefixPath + defaultPaskshotImage.getPath();
			productMarketingViewBean.setCarouselImage(carouselImage);
		} else {
			productMarketingViewBean.setCarouselImage("");
		}
		final ProductImage defaultIconImage = productMarketing.getDefaultIconImage();
		if(defaultIconImage != null){
			final String iconImage = currentCatalogResourcePrefixPath + defaultIconImage.getPath();
			productMarketingViewBean.setIconImage(iconImage);
		} else {
			productMarketingViewBean.setIconImage("");
		}
		
		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		final String productName = productMarketing.getI18nName(localeCode);
		final String productCode = productMarketing.getCode();
		productMarketingViewBean.setProductDetailsUrl(urlService.buildProductUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode));
		
		final ProductBrand productBrand = productMarketing.getProductBrand();
		if(productBrand != null) {
			final String brandName = productBrand.getName();
			final String brandCode = productBrand.getCode();
			productMarketingViewBean.setBrandDetailsUrl(urlService.buildProductBrandDetailsUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, brandName, brandCode));
			productMarketingViewBean.setBrandLineDetailsUrl(urlService.buildProductBrandLineUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, brandName, brandCode));
		}
		
		Set<ProductSku> skus = productMarketing.getProductSkus();
		if(skus != null) {
			for (Iterator<ProductSku> iterator = skus.iterator(); iterator.hasNext();) {
				final ProductSku productSku = (ProductSku) iterator.next();
				productMarketingViewBean.getProductSkus().add(buildProductSkuViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing, productSku));
			}
		}

		Set<ProductCrossLink> productCrossLinks = productMarketing.getProductCrossLinks();
		if(productCrossLinks != null) {
			for (Iterator<ProductCrossLink> iterator = productCrossLinks.iterator(); iterator.hasNext();) {
				final ProductCrossLink productCrossLink = (ProductCrossLink) iterator.next();
				if(productCrossLink.getType().equals("CROSSSELL")) {
					productMarketingViewBean.getProductCrossLinks().add(buildProductCrossLinkViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing));
				}
			}
		}

		return productMarketingViewBean;
	}
	
//
//	/**
//     * 
//     */
//	public ProductMarketingViewBean buildProductMarketingViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
//			 final Localization localization, final Retailer retailer, final ProductCategory productCategory, final ProductMarketing productMarketing) throws Exception {
//		final ProductMarketingViewBean productMarketingViewBean = buildProductMarketingViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing);
//		return productMarketingViewBean;
//	}
	
	/**
	 * @throws Exception 
     * 
     */
	public CartViewBean buildCartViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final Cart cart) throws Exception {
		final Locale locale = localization.getLocale();
		
		final CartViewBean cartViewBean = new CartViewBean();

		cartViewBean.setOrderItemNameLabel(coreMessageSource.getMessage("shoppingcart.item.sku.label", null, locale));
		cartViewBean.setOrderItemQuantityLabel(coreMessageSource.getMessage("shoppingcart.item.quantity.label", null, locale));
		cartViewBean.setOrderItemDeleteActionLabel(coreMessageSource.getMessage("shoppingcart.item.remove.label", null, locale));
		cartViewBean.setOrderItemPriceLabel(coreMessageSource.getMessage("shoppingcart.item.price", null, locale));
		cartViewBean.setOrderItemSubTotalLabel(coreMessageSource.getMessage("shoppingcart.item.subtotal", null, locale));
		
		cartViewBean.setCartItemsTotalLabel(coreMessageSource.getMessage("shoppingcart.amount.without.shippings", null, locale));
		cartViewBean.setCartTotalLabel(coreMessageSource.getMessage("shoppingcart.amount.total", null, locale));

		cartViewBean.setShippingAddressLabel(coreMessageSource.getMessage("shoppingcart.shipping.address", null, locale));
		cartViewBean.setBillingAddressLabel(coreMessageSource.getMessage("shoppingcart.billing.address", null, locale));

		cartViewBean.setCartDetailsUrl(urlService.buildCartDetailsUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setCartAuthUrl(urlService.buildCartAuthUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setCartDeliveryAndOrderDetailsUrl(urlService.buildCartDeliveryAndOrderDetailsUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setCartOrderPaymentUrl(urlService.buildCartOrderPaymentUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setCartOrderConfirmationUrl(urlService.buildCartOrderConfirmationUrl(request, marketPlace, market, marketArea, localization, retailer));

		cartViewBean.setAddNewAddressUrl(urlService.buildCustomerAddAddressUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setAddNewAddressLabel(coreMessageSource.getMessage("shoppingcart.add.new.address", null, locale));

		cartViewBean.setCardHolderLabel(coreMessageSource.getMessage("shoppingcart.payment.card.holder", null, locale));
		cartViewBean.setCardNumberLabel(coreMessageSource.getMessage("shoppingcart.payment.card.number", null, locale));
		cartViewBean.setCardCryptoLabel(coreMessageSource.getMessage("shoppingcart.payment.card.crypto", null, locale));
		cartViewBean.setCardExpirationDateLabel(coreMessageSource.getMessage("shoppingcart.payment.card.expiration.date", null, locale));
		cartViewBean.setCardExpirationMonthLabel(coreMessageSource.getMessage("shoppingcart.payment.card.expiration.month", null, locale));
		cartViewBean.setCardExpirationYearLabel(coreMessageSource.getMessage("shoppingcart.payment.card.expiration.year", null, locale));

		// ITEMS PART
		List<CartItemViewBean> cartItemViewBeans = new ArrayList<CartItemViewBean>();
		Set<CartItem> cartItems = cart.getCartItems();
		for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
			final CartItem cartItem = (CartItem) iterator.next();
			cartItemViewBeans.add(buildCartItemViewBean(request, marketPlace, market, marketArea, localization, retailer, cartItem));
		}
		cartViewBean.setCartItems(cartItemViewBeans);
		
		// SUB PART : Subtotal
		final String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(request, currencyCode);
		BigDecimal cartItemsTotal = new BigDecimal("0");
		BigDecimal cartShippingTotal = new BigDecimal("0");
		BigDecimal cartFeesTotal = new BigDecimal("0");
		BigDecimal carTotal = new BigDecimal("0");
		for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
			final CartItem cartItem = (CartItem) iterator.next();
			if(cartItem.getPrice() != null) {
				cartItemsTotal = cartItemsTotal.add(cartItem.getPrice());
			}
		}
		
		// SUB PART : Shippings
		final List<CartShippingViewBean> cartShippingViewBeans = new ArrayList<CartShippingViewBean>();
		final Set<Shipping> shippings = cart.getShippings();
		if(shippings != null){
			for (Iterator<Shipping> iterator = shippings.iterator(); iterator.hasNext();) {
				final Shipping shipping = (Shipping) iterator.next();
				final CartShippingViewBean cartShippingViewBean = new CartShippingViewBean();
				if(shipping.getPrice() != null) {
					cartShippingTotal = cartShippingTotal.add(shipping.getPrice());
					cartShippingViewBean.setCartShippingTotal(formatter.format(shipping.getPrice()));
				}
				Object[] params = {shipping.getName()};
				cartShippingViewBean.setCartShippingTotalLabel(coreMessageSource.getMessage("shoppingcart.amount.shippings", params, locale));
				cartShippingViewBeans.add(cartShippingViewBean);
			}
			cartViewBean.setCartShippings(cartShippingViewBeans);
		}
		
		// SUB PART : Taxes
		final List<CartTaxViewBean> cartTaxViewBeans = new ArrayList<CartTaxViewBean>();
		final Set<Tax> taxes = cart.getTaxes();
		if(taxes != null){
			for (Iterator<Tax> iterator = taxes.iterator(); iterator.hasNext();) {
				final Tax tax = (Tax) iterator.next();
				final CartTaxViewBean cartTaxViewBean = new CartTaxViewBean();
				BigDecimal taxesCalc = cartItemsTotal;
				taxesCalc = taxesCalc.multiply(tax.getPercent());
				taxesCalc = taxesCalc.divide(new BigDecimal("100"));
				cartFeesTotal = cartFeesTotal.add(taxesCalc);
				Object[] params = {tax.getName()};
				cartTaxViewBean.setCartTaxTotal(formatter.format(taxesCalc));
				cartTaxViewBean.setCartTaxTotalLabel(coreMessageSource.getMessage("shoppingcart.amount.taxes", params, locale));
				cartTaxViewBeans.add(cartTaxViewBean);
			}
			cartViewBean.setCartTaxes(cartTaxViewBeans);
		}
		carTotal = carTotal.add(cartItemsTotal);
		carTotal = carTotal.add(cartShippingTotal);
		carTotal = carTotal.add(cartFeesTotal);
		cartViewBean.setCartItemsTotal(formatter.format(cartItemsTotal));
		cartViewBean.setCartShippingTotal(formatter.format(cartShippingTotal));
		cartViewBean.setCartFeesTotal(formatter.format(cartFeesTotal));
		cartViewBean.setCartTotal(formatter.format(carTotal));
		
		cartViewBean.setStep1SubmitLabel(coreMessageSource.getMessage("shoppingcart.step1.submit.label", null, locale));
		cartViewBean.setStep2SubmitLabel(coreMessageSource.getMessage("shoppingcart.step2.submit.label", null, locale));
		cartViewBean.setStep3SubmitLabel(coreMessageSource.getMessage("shoppingcart.step3.submit.label", null, locale));
		
		return cartViewBean;
	}
	
	/**
     * 
     */
	private CartItemViewBean buildCartItemViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final CartItem cartItem) throws Exception {
		final Locale locale = localization.getLocale();
		final String localizationCode = localization.getLocaleCode();
		
		final CartItemViewBean cartItemViewBean = new CartItemViewBean();

		cartItemViewBean.setSkuCode(cartItem.getProductSkuCode());
		cartItemViewBean.setName(cartItem.getProductSku().getI18nName(localizationCode));
		cartItemViewBean.setQuantity(cartItem.getQuantity());
		
		final String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(request, currencyCode);
		final BigDecimal price = cartItem.getPrice();
		if(price != null) {
			cartItemViewBean.setPrice(formatter.format(price));
		}
		final BigDecimal totalAmountCartItem = cartItem.getTotalAmountCartItem();
		if(totalAmountCartItem != null) {
			cartItemViewBean.setAmount(formatter.format(totalAmountCartItem));
		}
		
		cartItemViewBean.setDeleteUrl(urlService.buildProductRemoveFromCartUrl(request, marketPlace, market, marketArea, localization, retailer, cartItem.getProductSkuCode()));
		cartItemViewBean.setDeleteLabel(coreMessageSource.getMessage("shoppingcart.delete.from.cart", null, locale));
		
		return cartItemViewBean;
	}
	
	/**
     * 
     */
	public List<OrderViewBean> buildOrderViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			final Localization localization, final Retailer retailer, final  List<Order> orders) throws Exception {
		final List<OrderViewBean> orderViewBeans = new ArrayList<OrderViewBean>();
		for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			orderViewBeans.add(buildOrderViewBean(request, marketPlace, market, marketArea, localization, retailer, order));
		}
		return orderViewBeans;
	}
	
	/**
     * 
     */
	public OrderViewBean buildOrderViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
											final Localization localization, final Retailer retailer, final Order order) throws Exception {
		final Locale locale = localization.getLocale();
		final String orderId = order.getId().toString();
		final OrderViewBean orderViewBean = new OrderViewBean();

		orderViewBean.setOrderItemNameLabel(coreMessageSource.getMessage("shoppingcart.item.sku.label", null, locale));
		orderViewBean.setOrderItemQuantityLabel(coreMessageSource.getMessage("shoppingcart.item.quantity.label", null, locale));
		orderViewBean.setOrderItemDeleteActionLabel(coreMessageSource.getMessage("shoppingcart.item.remove.label", null, locale));
		orderViewBean.setOrderItemPriceLabel(coreMessageSource.getMessage("shoppingcart.item.price", null, locale));
		orderViewBean.setOrderItemSubTotalLabel(coreMessageSource.getMessage("shoppingcart.item.subtotal", null, locale));
		
		orderViewBean.setOrderItemsTotalLabel(coreMessageSource.getMessage("shoppingcart.amount.without.shippings", null, locale));
		orderViewBean.setOrderTotalLabel(coreMessageSource.getMessage("shoppingcart.amount.total", null, locale));

		orderViewBean.setShippingAddressLabel(coreMessageSource.getMessage("shoppingcart.shipping.address", null, locale));
		orderViewBean.setBillingAddressLabel(coreMessageSource.getMessage("shoppingcart.billing.address", null, locale));

		orderViewBean.setCardHolderLabel(coreMessageSource.getMessage("shoppingcart.payment.card.holder", null, locale));
		orderViewBean.setCardNumberLabel(coreMessageSource.getMessage("shoppingcart.payment.card.number", null, locale));
		orderViewBean.setCardCryptoLabel(coreMessageSource.getMessage("shoppingcart.payment.card.crypto", null, locale));
		orderViewBean.setCardExpirationDateLabel(coreMessageSource.getMessage("shoppingcart.payment.card.expiration.date", null, locale));
		orderViewBean.setCardExpirationMonthLabel(coreMessageSource.getMessage("shoppingcart.payment.card.expiration.month", null, locale));
		orderViewBean.setCardExpirationYearLabel(coreMessageSource.getMessage("shoppingcart.payment.card.expiration.year", null, locale));

		// ITEMS PART
		final List<OrderItemViewBean> orderItemViewBeans = new ArrayList<OrderItemViewBean>();
		final Set<OrderItem> orderItems = order.getOrderItems();
		for (Iterator<OrderItem> iterator = orderItems.iterator(); iterator.hasNext();) {
			OrderItem orderItem = (OrderItem) iterator.next();
			orderItemViewBeans.add(buildOrderItemViewBean(request, marketPlace, market, marketArea, localization, retailer, orderItem));
		}
		orderViewBean.setOrderItems(orderItemViewBeans);
		
		// SUB PART : Subtotal
		final String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(request, currencyCode);
		BigDecimal orderItemsTotal = new BigDecimal("0");
		BigDecimal orderShippingTotal = new BigDecimal("0");
		BigDecimal orderFeesTotal = new BigDecimal("0");
		BigDecimal orderTotal = new BigDecimal("0");
		for (Iterator<OrderItem> iterator = orderItems.iterator(); iterator.hasNext();) {
			final OrderItem orderItem = (OrderItem) iterator.next();
			if(orderItem.getPrice() != null) {
				orderItemsTotal = orderItemsTotal.add(orderItem.getPrice());
			}
		}
		
		// SUB PART : Shippings
		final List<OrderShippingViewBean> orderShippingViewBeans = new ArrayList<OrderShippingViewBean>();
		final Set<OrderShipment> orderShipments = order.getOrderShipments();
		if(orderShipments != null){
			for (Iterator<OrderShipment> iterator = orderShipments.iterator(); iterator.hasNext();) {
				final OrderShipment orderShipment = (OrderShipment) iterator.next();
				final OrderShippingViewBean orderShippingViewBean = new OrderShippingViewBean();
				if(orderShipment.getPrice() != null) {
					orderShippingTotal = orderShippingTotal.add(orderShipment.getPrice());
					orderShippingViewBean.setOrderShippingTotal(formatter.format(orderShipment.getPrice()));
				}
				Object[] params = {orderShipment.getName()};
				orderShippingViewBean.setOrderShippingTotalLabel(coreMessageSource.getMessage("shoppingcart.amount.shippings", params, locale));
				orderShippingViewBeans.add(orderShippingViewBean);
			}
			orderViewBean.setOrderShippings(orderShippingViewBeans);
		}
		
		// SUB PART : Taxes
		final List<OrderTaxViewBean> orderTaxViewBeans = new ArrayList<OrderTaxViewBean>();
		final Set<OrderTax> orderTaxes = order.getOrderTaxes();
		if(orderTaxes != null){
			for (Iterator<OrderTax> iterator = orderTaxes.iterator(); iterator.hasNext();) {
				final OrderTax orderTax = (OrderTax) iterator.next();
				final OrderTaxViewBean orderTaxViewBean = new OrderTaxViewBean();
				BigDecimal taxesCalc = orderItemsTotal;
				taxesCalc = taxesCalc.multiply(orderTax.getPercent());
				taxesCalc = taxesCalc.divide(new BigDecimal("100"));
				orderFeesTotal = orderFeesTotal.add(taxesCalc);
				Object[] params = {orderTax.getName()};
				orderTaxViewBean.setOrderTaxTotal(formatter.format(taxesCalc));
				orderTaxViewBean.setOrderTaxTotalLabel(coreMessageSource.getMessage("shoppingcart.amount.taxes", params, locale));
				orderTaxViewBeans.add(orderTaxViewBean);
			}
			orderViewBean.setOrderTaxes(orderTaxViewBeans);
		}
		orderTotal = orderTotal.add(orderItemsTotal);
		orderTotal = orderTotal.add(orderShippingTotal);
		orderTotal = orderTotal.add(orderFeesTotal);
		orderViewBean.setOrderItemsTotal(formatter.format(orderItemsTotal));
		orderViewBean.setOrderShippingTotal(formatter.format(orderShippingTotal));
		orderViewBean.setOrderFeesTotal(formatter.format(orderFeesTotal));
		orderViewBean.setOrderTotal(formatter.format(orderTotal));

		final Object[] params = {order.getOrderNum()};
		orderViewBean.setConfirmationMessage(coreMessageSource.getMessage("order.confirmation.message", params, locale));

		orderViewBean.setOrderDetailsUrl(urlService.buildCustomerOrderDetailsUrl(request, marketPlace, market, marketArea, localization, retailer, orderId));
		
		return orderViewBean;
	}
	
	/**
     * 
     */
	public OrderItemViewBean buildOrderItemViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
													 final Localization localization, final Retailer retailer, final OrderItem orderItem) throws Exception {
		final String localeCode = localization.getLocaleCode();
		
		final OrderItemViewBean orderItemViewBean = new OrderItemViewBean();

		orderItemViewBean.setSkuCode(orderItem.getProductSkuCode());
		orderItemViewBean.setName(orderItem.getProductSku().getI18nName(localeCode));
		
		String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(request, currencyCode);
		final BigDecimal price = orderItem.getPrice();
		if(price != null){
			orderItemViewBean.setPrice(formatter.format(price));
		}
		
		orderItemViewBean.setQuantity(orderItem.getQuantity());
		
		final BigDecimal totalAmountOrderItem = orderItem.getTotalAmountOrderItem();
		if(totalAmountOrderItem != null) {
			orderItemViewBean.setAmount(formatter.format(totalAmountOrderItem));
		}
		return orderItemViewBean;
	}
	
	/**
     * 
     */
	public ProductCrossLinkViewBean buildProductCrossLinkViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
																   final Localization localization, final Retailer retailer, final ProductCategoryVirtual productCategory, final ProductMarketing productMarketing) 
																   throws Exception {
		final String localeCode = localization.getLocaleCode();
		final ProductCrossLinkViewBean productCrossLinkViewBean = new ProductCrossLinkViewBean();

		
		
		// TODO : WRONG : CROSS IS SKU not marketing
		
		
		
		productCrossLinkViewBean.setName(productMarketing.getI18nName(localeCode));
		productCrossLinkViewBean.setDescription(productMarketing.getDescription());
		
		final String currentCatalogResourcePrefixPath = requestUtil.getCurrentCatalogImageResourcePrefixPath(request, marketArea.getCode());
		final ProductImage defaultBackgroundImage = productMarketing.getDefaultBackgroundImage();
		if(defaultBackgroundImage != null){
			String backgroundImage = currentCatalogResourcePrefixPath + defaultBackgroundImage.getPath();
			productCrossLinkViewBean.setBackgroundImage(backgroundImage);
		} else {
			productCrossLinkViewBean.setBackgroundImage("");
		}
		final ProductImage defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL.getPropertyKey());
		if(defaultPaskshotImage != null){
			String carouselImage = currentCatalogResourcePrefixPath + defaultPaskshotImage.getPath();
			productCrossLinkViewBean.setCrossLinkImage(carouselImage);
		} else {
			productCrossLinkViewBean.setCrossLinkImage("");
		}
		final ProductImage defaultIconImage = productMarketing.getDefaultIconImage();
		if(defaultIconImage != null){
			String iconImage = currentCatalogResourcePrefixPath + defaultIconImage.getPath();
			productCrossLinkViewBean.setIconImage(iconImage);
		} else {
			productCrossLinkViewBean.setIconImage("");
		}
		
		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		final String productName = productMarketing.getI18nName(localeCode);
		final String productCode = productMarketing.getCode();
		productCrossLinkViewBean.setProductDetailsUrl(urlService.buildProductUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode));
		
		return productCrossLinkViewBean;
	}
	
	/**
     * 
     */
	public ProductSkuViewBean buildProductSkuViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
													   final Retailer retailer, final ProductCategoryVirtual productCategory, final ProductMarketing productMarketing, final ProductSku productSku) 
													   throws Exception {
		final Locale locale = localization.getLocale();
		final String localeCode = localization.getLocaleCode();
		final ProductSkuViewBean productSkuViewBean = new ProductSkuViewBean();

		productSkuViewBean.setName(productSku.getI18nName(localeCode));
		productSkuViewBean.setDescription(productSku.getDescription());
		
		final String currentCatalogResourcePrefixPath = requestUtil.getCurrentCatalogImageResourcePrefixPath(request, marketArea.getCode());
		final ProductImage defaultBackgroundImage = productSku.getDefaultBackgroundImage();
		if(defaultBackgroundImage != null){
			String backgroundImage = currentCatalogResourcePrefixPath + defaultBackgroundImage.getPath();
			productSkuViewBean.setBackgroundImage(backgroundImage);
		} else {
			productSkuViewBean.setBackgroundImage("");
		}
		final ProductImage defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL.getPropertyKey());
		if(defaultPaskshotImage != null){
			String carouselImage = currentCatalogResourcePrefixPath + defaultPaskshotImage.getPath();
			productSkuViewBean.setCarouselImage(carouselImage);
		} else {
			productSkuViewBean.setCarouselImage("");
		}
		final ProductImage defaultIconImage = productSku.getDefaultIconImage();
		if(defaultIconImage != null){
			String iconImage = currentCatalogResourcePrefixPath + defaultIconImage.getPath();
			productSkuViewBean.setIconImage(iconImage);
		} else {
			productSkuViewBean.setIconImage("");
		}
		
		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		final String productName = productMarketing.getI18nName(localeCode);
		final String productCode = productMarketing.getCode();
		final String productSkuName = productSku.getI18nName(localeCode);
		final String productSkuCode = productSku.getCode();
		productSkuViewBean.setProductDetailsUrl(urlService.buildProductUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode));
		productSkuViewBean.setProductDetailsLabel(coreMessageSource.getMessage("product.details", null, locale));

		productSkuViewBean.setAddToCartUrl(urlService.buildProductAddToCartUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode, productSkuName, productSkuCode));
		productSkuViewBean.setAddToCartLabel(coreMessageSource.getMessage("product.add.to.cart", null, locale));

		productSkuViewBean.setRemoveFromCartUrl(urlService.buildProductRemoveFromCartUrl(request, marketPlace, market, marketArea, localization, retailer, productSkuCode));
		productSkuViewBean.setRemoveFromCartLabel(coreMessageSource.getMessage("product.remove.from.cart", null, locale));
		
		productSkuViewBean.setAddToWishlistUrl(urlService.buildProductAddToWishlistUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode, productSkuName, productSkuCode));
		productSkuViewBean.setAddToWishlistLabel(coreMessageSource.getMessage("product.add.to.wishlist", null, locale));

		productSkuViewBean.setRemoveFromWishlistUrl(urlService.buildProductRemoveFromWishlistUrl(request, marketPlace, market, marketArea, localization, retailer, productSkuCode));
		productSkuViewBean.setRemoveFromWishlistLabel(coreMessageSource.getMessage("product.remove.from.wishlist", null, locale));

		return productSkuViewBean;
	}
	
	// SEARCH

	/**
     * 
     */
	public SearchViewBean buildSearchViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final SearchViewBean search = new SearchViewBean();
		search.setTextLabel(coreMessageSource.getMessage("form.search.label.text", null, locale));

		return search;
	}
	
	/**
     * 
     */
	public QuickSearchViewBean buildQuickSearchViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		final QuickSearchViewBean quickSsearch = new QuickSearchViewBean();
		quickSsearch.setTextLabel(coreMessageSource.getMessage("form.search.label.text", null, locale));
		quickSsearch.setUrlFormSubmit(urlService.buildSearchUrl(request, marketPlace, market, marketArea, localization, retailer));
		return quickSsearch;
	}
	
	/**
     * 
     */
	public List<SearchProductItemViewBean> buildSearchProductItemViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			final Localization localization, final Retailer retailer, final ProductResponseBean productResponseBean) throws Exception {
		final List<SearchProductItemViewBean> searchProductItems = new ArrayList<SearchProductItemViewBean>();
		List<ProductSolr> products = productResponseBean.getProductSolrList();
		for (Iterator<ProductSolr> iterator = products.iterator(); iterator.hasNext();) {
			ProductSolr productSolr = (ProductSolr) iterator.next();
			searchProductItems.add(buildSearchProductItemViewBean(request, marketPlace, market, marketArea, localization, retailer, productSolr));
		}
		return searchProductItems;
	}
	
	/**
     * 
     */
	public SearchProductItemViewBean buildSearchProductItemViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			final Localization localization, final Retailer retailer, final ProductSolr productSolr) throws Exception {
		final String localeCode = localization.getLocaleCode();
		
		final String productCode = productSolr.getCode();
		ProductMarketing productMarketing = productMarketingService.getProductMarketingByCode(marketArea.getId(), retailer.getId(), productCode);
		
		final String productName = productMarketing.getCode();
		final ProductCategoryVirtual productCategory = productCategoryService.getDefaultProductCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		final String productSkuName = productMarketing.getDefaultProductSku().getI18nName(localeCode);
		final String productSkuCode = productMarketing.getDefaultProductSku().getCode();
		
		final SearchProductItemViewBean searchProductItemViewBean = new SearchProductItemViewBean();
		searchProductItemViewBean.setName(productMarketing.getI18nName(localeCode));
		searchProductItemViewBean.setDescription(productMarketing.getDescription());
		searchProductItemViewBean.setCode(productCode);
		
		searchProductItemViewBean.setProductDetailsUrl(urlService.buildProductUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode));
		searchProductItemViewBean.setAddToCartUrl(urlService.buildProductAddToCartUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode, productSkuName, productSkuCode));

		return searchProductItemViewBean;
	}
	
	/**
     * 
     */
	public List<SearchFacetViewBean> buildSearchFacetViewBeans(final HttpServletRequest request, final ProductResponseBean productResponseBean) throws Exception {
		final List<SearchFacetViewBean> searchFacetViewBeans = new ArrayList<SearchFacetViewBean>();
		List<FacetField> productFacetFields = productResponseBean.getProductSolrFacetFieldList();
		for (Iterator<FacetField> iterator = productFacetFields.iterator(); iterator.hasNext();) {
			FacetField facetField = (FacetField) iterator.next();
			searchFacetViewBeans.add(buildSearchFacetViewBean(request, facetField));
		}
		return searchFacetViewBeans;
	}
	
	/**
     * 
     */
	public SearchFacetViewBean buildSearchFacetViewBean(final HttpServletRequest request, final FacetField facetField) throws Exception {
		final SearchFacetViewBean searchFacetViewBean = new SearchFacetViewBean();
		searchFacetViewBean.setName(facetField.getName());
		List<String> values = new ArrayList<String>();
		for (Iterator<Count> iterator = facetField.getValues().iterator(); iterator.hasNext();) {
			Count count = (Count) iterator.next();
			values.add(count.getName() + "(" + count.getCount() + ")");
		}
		searchFacetViewBean.setValues(values);
		return searchFacetViewBean;
	}

}
