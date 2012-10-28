/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.common.domain.Cart;
import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Order;
import fr.hoteia.qalingo.core.common.domain.ProductBrand;
import fr.hoteia.qalingo.core.common.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.common.domain.ProductMarketing;
import fr.hoteia.qalingo.core.common.domain.Retailer;
import fr.hoteia.qalingo.core.common.domain.Store;
import fr.hoteia.qalingo.core.common.service.ProductMarketingService;
import fr.hoteia.qalingo.core.common.service.StoreService;
import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.factory.ModelAndViewFactory;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.CartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ConditionsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ContactUsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressFormViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressListViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerCreateAccountViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerWishlistViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FaqViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FollowUsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.HeaderCartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OurCompanyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductBrandViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductCategoryViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductMarketingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreLocatorViewBean;

/**
 * 
 */
@Service("modelAndViewFactory")
public class ModelAndViewFactoryImpl implements ModelAndViewFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected UrlService urlService;
	
	@Autowired
    protected StoreService storeService;
	
	@Autowired
    protected ProductMarketingService productMarketingService;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	@Autowired
    protected FormFactory formFactory;
	
	/**
     * 
     */
	public void initCommonModelAndView(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final String customerId) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		// COMMON
		CommonViewBean commonViewBean = viewBeanFactory.buildCommonViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("common", commonViewBean);

		// QUICK SEARCH
		QuickSearchViewBean quickSearchViewBean = viewBeanFactory.buildQuickSearchViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("quickSearch", quickSearchViewBean);
		
		formFactory.buildQuickSearchForm(request, modelAndView);
		
		// LEGACY
		LegacyViewBean legacyViewBean = viewBeanFactory.buildLegacyViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("legacy", legacyViewBean);
		
		// CONDITIONS OF USE
		ConditionsViewBean conditionsViewBean = viewBeanFactory.buildConditionsViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("conditions", conditionsViewBean);
		
		// CART
		HeaderCartViewBean headerCartViewBean = viewBeanFactory.buildHeaderCartViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("headerCart", headerCartViewBean);
		
		// ALL MARKETPLACES
		List<MarketPlaceViewBean> marketPlaceViewBeans = viewBeanFactory.buildMarketPlaceViewBeans(request);
		modelAndView.addObject("marketPlaces", marketPlaceViewBeans);
		
		// MARKETS FOR THE CURRENT MARKETPLACE
		Set<Market> marketList = currentMarketPlace.getMarkets();
		modelAndView.addObject("markets", viewBeanFactory.buildMarketViewBeans(request, new ArrayList<Market>(marketList)));
		
		// MARKET AREAS FOR THE CURRENT MARKET
		Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
		modelAndView.addObject("marketAreas", viewBeanFactory.buildMarketAreaViewBeans(request, new ArrayList<MarketArea>(marketAreaList)));
		
		// LOCALIZATIONS FOR THE CURRENT MARKET AREA
		modelAndView.addObject("languages", viewBeanFactory.buildLocalizationViewBeans(request, currentMarketArea));

		// RETAILERS FOR THE CURRENT MARKET AREA
		modelAndView.addObject("retailers", viewBeanFactory.buildRetailerViewBeans(request, currentMarketArea, currentLocalization));

		// HEADER
		modelAndView.addObject("menus", viewBeanFactory.buildMenuViewBeans(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer));
		
		// FOOTER
		modelAndView.addObject("footerMenus", viewBeanFactory.buildFooterMenuViewBeans(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer));
	}
	
	/**
     * 
     */
	public void initContactUsModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final ContactUsViewBean contactUs = viewBeanFactory.buildContactUsViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("contactUs", contactUs);
	}
	
	/**
     * 
     */
	public void initSearchModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final SearchViewBean search = viewBeanFactory.buildSearchViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("search", search);
	}
	
	/**
     * 
     */
	public void initOurCompanyModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final OurCompanyViewBean ourCompany = viewBeanFactory.buildOurCompanyViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("ourCompany", ourCompany);
	}
	
	/**
     * 
     */
	public void initFollowUsModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final FollowUsViewBean followUs = viewBeanFactory.buildFollowUsViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("followUs", followUs);
	}
	
	/**
     * 
     */
	public void initStoreLocatorModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final List<Store> stores = storeService.findStores();
		final StoreLocatorViewBean storeLocator = viewBeanFactory.buildStoreLocatorViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, stores);
		modelAndView.addObject("storeLocator", storeLocator);
	}
	
	/**
     * 
     */
	public void initCustomerDetailsAccountModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final Customer customer) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerViewBean customerViewBean = viewBeanFactory.buildCustomerDetailsAccountViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, customer);
		modelAndView.addObject("customerDetails", customerViewBean);
	}
	
	/**
     * 
     */
	public void initCustomerCreateAccountModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerCreateAccountViewBean customerCreateAccount = viewBeanFactory.buildCustomerCreateAccountViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("customerCreateAccount", customerCreateAccount);
	}
	
	/**
     * 
     */
	public void initCustomerAddAddressModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerAddressFormViewBean customerFormAddressViewBean = viewBeanFactory.buildCustomerAddressFormViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("customerAddress", customerFormAddressViewBean);
	}
	
	/**
     * 
     */
	public void initCustomerAddressListModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final Customer customer) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerAddressListViewBean customerAdressesViewBean = viewBeanFactory.buildCustomerAddressListViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, customer);
		modelAndView.addObject("customerAdresses", customerAdressesViewBean);
	}
	
	/**
     * 
     */
	public void initCustomerOrderListModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final Customer customer) throws Exception {
		// TODO
	}
	
	/**
     * 
     */
	public void initCustomerOrderDetailsModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final Customer customer) throws Exception {
		// TODO
	}
	
	/**
     * 
     */
	public void initCustomerWishlistModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final Customer customer) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerWishlistViewBean customerWishListViewBean = viewBeanFactory.buildCustomerWishlistViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, customer);
		modelAndView.addObject("customerWishList", customerWishListViewBean);
	}
	
	/**
     * 
     */
	public void initCustomerWishListModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final Customer customer) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerWishlistViewBean customerWishlistViewBean = viewBeanFactory.buildCustomerWishlistViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, customer);
		modelAndView.addObject("customerWishlist", customerWishlistViewBean);
	}
	
	/**
     * 
     */
	public void initFaqModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final FaqViewBean faq = viewBeanFactory.buildFaqViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("faq", faq);
	}
	
	public void initPageBrandLine(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final ProductBrand productBrand, final String titleKeyPrefixSufix) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		List<ProductMarketing>  productMarketings = productMarketingService.findProductMarketingsByBrandId(currentMarketArea.getId(), currentRetailer.getId(), productBrand.getId());
		final ProductBrandViewBean productBrandViewBean = viewBeanFactory.buildProductBrandViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, productBrand, productMarketings);
		modelAndView.addObject("productBrand", productBrandViewBean);
		
	}

	public void initPageBrandDetails(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final ProductBrand productBrand, final String titleKeyPrefixSufix) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final ProductBrandViewBean productBrandViewBean = viewBeanFactory.buildProductBrandViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, productBrand);
		modelAndView.addObject("productBrand", productBrandViewBean);
	}
	
	/**
	 * 
	 */
	public void initPageProductCategory(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final ProductCategoryVirtual productCategory, final String titleKeyPrefixSufix) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		String seoPageMetaKeywords = coreMessageSource.getMessage("page.meta.keywords", null, locale);
        modelAndView.addObject("seoPageMetaKeywords", seoPageMetaKeywords);

		String seoPageMetaDescription = coreMessageSource.getMessage("page.meta.description", null, locale);
        modelAndView.addObject("seoPageMetaDescription", seoPageMetaDescription);

		String pageTitleKey = "header.title." + titleKeyPrefixSufix;
		String seoPageTitle = coreMessageSource.getMessage("page.title.prefix", null, locale) + " - " + coreMessageSource.getMessage(pageTitleKey, null, locale);
        modelAndView.addObject("seoPageTitle", seoPageTitle);
        
        initMasterProductCategoryModelAndView(request, modelAndView, productCategory);
    }
	
	/**
	 * 
	 */
	public void initPageProductMarketing(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final ProductCategoryVirtual productCategory, final ProductMarketing productMarketing, final String titleKeyPrefixSufix) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		String seoPageMetaKeywords = coreMessageSource.getMessage("page.meta.keywords", null, locale);
        modelAndView.addObject("seoPageMetaKeywords", seoPageMetaKeywords);

		String seoPageMetaDescription = coreMessageSource.getMessage("page.meta.description", null, locale);
        modelAndView.addObject("seoPageMetaDescription", seoPageMetaDescription);

		String pageTitleKey = "header.title." + titleKeyPrefixSufix;
		String seoPageTitle = coreMessageSource.getMessage("page.title.prefix", null, locale) + " - " + coreMessageSource.getMessage(pageTitleKey, null, locale);
        modelAndView.addObject("seoPageTitle", seoPageTitle);
        
        initProductMarketingModelAndView(request, modelAndView, productCategory, productMarketing);
    }
	
	/**
     * 
     */
	public void initMasterProductCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductCategoryVirtual productCategory) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final ProductCategoryViewBean productCategoryViewBean = viewBeanFactory.buildProductCategoryViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, productCategory);
		modelAndView.addObject("productCategory", productCategoryViewBean);
	}

	/**
     * 
     */
	public void initProductMarketingModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductCategoryVirtual productCategory, final ProductMarketing productMarketing) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final ProductCategoryViewBean productCategoryViewBean = viewBeanFactory.buildProductCategoryViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, productCategory);
		modelAndView.addObject("productCategory", productCategoryViewBean);
		
		final ProductMarketingViewBean productMarketingViewBean = viewBeanFactory.buildProductMarketingViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, productCategory, productMarketing);
		modelAndView.addObject("productMarketing", productMarketingViewBean);
	}
	
	/**
     * 
     */
	public void initCartModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Cart currentCart = requestUtil.getCurrentCart(request);
		final CartViewBean cartViewBean = viewBeanFactory.buildCartViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCart);
		modelAndView.addObject("cart", cartViewBean);
	}
	
	/**
     * 
     */
	public void initOrderModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final Order order) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final OrderViewBean orderViewBean = viewBeanFactory.buildOrderViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, order);
		modelAndView.addObject("order", orderViewBean);
	}
	
	/**
     * 
     */
	public void initLoginModelAndView(final HttpServletRequest request, ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		SecurityViewBean security = viewBeanFactory.buildSecurityViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("security", security);
	}

}
