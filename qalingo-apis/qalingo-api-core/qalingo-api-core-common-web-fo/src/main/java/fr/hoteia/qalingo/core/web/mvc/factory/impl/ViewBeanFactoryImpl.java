/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.mvc.factory.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.Cart;
import fr.hoteia.qalingo.core.domain.CartItem;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.OrderItem;
import fr.hoteia.qalingo.core.domain.OrderShipment;
import fr.hoteia.qalingo.core.domain.OrderTax;
import fr.hoteia.qalingo.core.domain.ProductAssociationLink;
import fr.hoteia.qalingo.core.domain.ProductBrand;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.RetailerAddress;
import fr.hoteia.qalingo.core.domain.RetailerCustomerComment;
import fr.hoteia.qalingo.core.domain.RetailerTag;
import fr.hoteia.qalingo.core.domain.Shipping;
import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.domain.Tax;
import fr.hoteia.qalingo.core.domain.enumtype.ImageSize;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.core.domain.enumtype.ProductAssociationLinkType;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.CatalogCategoryService;
import fr.hoteia.qalingo.core.service.CatalogService;
import fr.hoteia.qalingo.core.service.CustomerProductCommentService;
import fr.hoteia.qalingo.core.service.MarketPlaceService;
import fr.hoteia.qalingo.core.service.MarketService;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.service.ProductSkuService;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.solr.bean.ProductSolr;
import fr.hoteia.qalingo.core.solr.response.ProductResponseBean;
import fr.hoteia.qalingo.core.web.cache.util.WebCacheHelper;
import fr.hoteia.qalingo.core.web.cache.util.WebElementType;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.viewbean.CartItemViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CartShippingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CartTaxViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ConditionsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressListViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressViewBean;
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
import fr.hoteia.qalingo.web.mvc.viewbean.LegalTermsViewBean;
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
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerCustomerCommentViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerTagViewBean;
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
public class ViewBeanFactoryImpl extends AbstractFrontofficeViewBeanFactory implements ViewBeanFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected RequestUtil requestUtil;

	@Autowired
	protected MarketPlaceService marketPlaceService;

	@Autowired
	protected MarketService marketService;

	@Autowired
	protected CatalogService ProductCatalogService;

	@Autowired
	protected CatalogCategoryService productCategoryService;

	@Autowired
	protected ProductMarketingService productMarketingService;

	@Autowired
	protected ProductSkuService productSkuService;

	@Autowired
	protected CustomerProductCommentService customerProductCommentService;

	@Autowired
	protected UrlService urlService;

	@Resource(name = "menuMarketNavigationCacheHelper")
	protected WebCacheHelper menuMarketNavigationCacheHelper;

	@Resource(name = "menuTopCacheHelper")
	protected WebCacheHelper menuTopCacheHelper;

	@Resource(name = "menuFooterCacheHelper")
	protected WebCacheHelper menuFooterCacheHelper;

	@Resource(name = "menuCustomerCacheHelper")
	protected WebCacheHelper menuCustomerCacheHelper;

	@Resource(name = "storeLocatorCacheHelper")
	protected WebCacheHelper storeLocatorCacheHelper;

	/**
     * 
     */
	public CommonViewBean buildCommonViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		final CommonViewBean commonViewBean = new CommonViewBean();

		// NO CACHE FOR THIS PART

		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request);
		commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

		commonViewBean.setHomeUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setLoginUrl(urlService.buildLoginUrl(request, marketArea));
		commonViewBean.setForgottenPasswordUrl(urlService.buildContactUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setLogoutUrl(urlService.buildLogoutUrl(request, marketArea));
		commonViewBean.setCreateAccountUrl(urlService.buildCustomerCreateAccountUrl(request, marketPlace, market, marketArea, localization, retailer));
		commonViewBean.setCustomerDetailsUrl(urlService.buildCustomerDetailsUrl(request, marketArea));
		commonViewBean.setContactUrl(urlService.buildContactUrl(request, marketPlace, market, marketArea, localization, retailer));

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
		if (currentCart.getCartItems().size() == 1) {
			headerCartViewBean.setCartTotalSummaryLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_one_item", locale));
		} else if (currentCart.getCartItems().size() > 1) {
			Object[] cartTotalSummaryLabelParams = { currentCart.getCartItems().size() };
			headerCartViewBean.setCartTotalSummaryLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_many_items", cartTotalSummaryLabelParams, locale));
		} else {
			headerCartViewBean.setCartTotalSummaryLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_no_item", locale));
		}

		return headerCartViewBean;
	}

	/**
     * 
     */
	public List<MenuViewBean> buildMenuViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		final WebElementType menuTopElementType = WebElementType.TOP_MENU_VIEW_BEAN_LIST;
		String menuTopPrefixCacheKey = menuTopCacheHelper.buildPrefixKey(marketPlace, market, marketArea, localization, retailer, menuTopElementType);
		String menuTopCacheKey = menuTopPrefixCacheKey + "_GLOBAL";
		List<MenuViewBean> menuViewBeans = (List<MenuViewBean>) menuTopCacheHelper.getFromCache(menuTopElementType, menuTopCacheKey);
		if (menuViewBeans == null) {
			final Locale locale = localization.getLocale();
			final String localeCode = localization.getCode();

			menuViewBeans = new ArrayList<MenuViewBean>();

			MenuViewBean menu = new MenuViewBean();
			menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
			menu.setUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));
			menuViewBeans.add(menu);

			CatalogVirtual productCatalog = ProductCatalogService.getCatalogVirtualByCode(marketArea.getId(), retailer.getId(), marketArea.getVirtualCatalog().getCode());

			final List<CatalogCategoryVirtual> productCategoies = productCatalog.getProductCategories(marketArea.getId());
			if (productCategoies != null) {
				for (Iterator<CatalogCategoryVirtual> iteratorProductCategory = productCategoies.iterator(); iteratorProductCategory.hasNext();) {
					final CatalogCategoryVirtual productCategory = (CatalogCategoryVirtual) iteratorProductCategory.next();
					menu = new MenuViewBean();
					final String seoProductCategoryName = productCategory.getI18nName(localeCode);
					final String seoProductCategoryCode = productCategory.getCode();
					menu.setName(seoProductCategoryName);
					menu.setUrl(urlService.buildProductCategoryUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, seoProductCategoryName, seoProductCategoryCode));

					List<CatalogCategoryVirtual> subProductCategories = productCategory.getCatalogCategories(marketArea.getId());
					if (subProductCategories != null) {
						List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();
						for (Iterator<CatalogCategoryVirtual> iteratorSubProductCategory = subProductCategories.iterator(); iteratorSubProductCategory.hasNext();) {
							final CatalogCategoryVirtual subProductCategory = (CatalogCategoryVirtual) iteratorSubProductCategory.next();
							final MenuViewBean subMenu = new MenuViewBean();
							final String seoSubProductCategoryName = productCategory.getI18nName(localeCode) + " " + subProductCategory.getI18nName(localeCode);
							final String seoSubProductCategoryCode = subProductCategory.getCode();
							subMenu.setName(seoSubProductCategoryName);
							subMenu.setUrl(urlService.buildProductCategoryUrlAsProductLineUrl(request, marketPlace, market, marketArea, localization, retailer, seoSubProductCategoryName,
							        seoSubProductCategoryCode));
							subMenus.add(subMenu);
						}
						menu.setSubMenus(subMenus);
					}
					menuViewBeans.add(menu);
				}
			}

			menu = new MenuViewBean();
			menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "our_company", locale));
			menu.setUrl(urlService.buildOurCompanyUrl(request, marketPlace, market, marketArea, localization, retailer));
			menuViewBeans.add(menu);

			menuTopCacheHelper.addToCache(menuTopElementType, menuTopCacheKey, menuViewBeans);
		}

		return menuViewBeans;
	}

	/**
     * 
     */
	public List<FooterMenuViewBean> buildFooterMenuViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer) throws Exception {
		final WebElementType footerMenuElementType = WebElementType.FOOTER_MENU_VIEW_BEAN_LIST;
		final String footerMenuPrefixCacheKey = menuFooterCacheHelper.buildGlobalPrefixKey(localization);
		final String footerMenuCacheKey = footerMenuPrefixCacheKey + "_FOOTER_MENU";
		List<FooterMenuViewBean> footerMenuViewBeans = (List<FooterMenuViewBean>) menuFooterCacheHelper.getFromCache(footerMenuElementType, footerMenuCacheKey);
		if (footerMenuViewBeans == null) {
			final Locale locale = localization.getLocale();
			footerMenuViewBeans = new ArrayList<FooterMenuViewBean>();

			FooterMenuViewBean footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "conditionsofuse", locale));
			footerMenuList.setUrl(urlService.buildConditionOfUseUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "legal_terms", locale));
			footerMenuList.setUrl(urlService.buildLegalTermsUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "faq", locale));
			footerMenuList.setUrl(urlService.buildFaqUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "store_location", locale));
			footerMenuList.setUrl(urlService.buildStoreLocationUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "contactus", locale));
			footerMenuList.setUrl(urlService.buildContactUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "followus", locale));
			footerMenuList.setUrl(urlService.buildFollowUsUrl(request, marketPlace, market, marketArea, localization, retailer));
			footerMenuViewBeans.add(footerMenuList);

			menuFooterCacheHelper.addToCache(footerMenuElementType, footerMenuCacheKey, footerMenuViewBeans);
		}
		return footerMenuViewBeans;
	}

	/**
     * 
     */
	public FollowUsViewBean buildFollowUsViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();

		final FollowUsViewBean followUs = new FollowUsViewBean();

		followUs.setSubmitUrlShortForm(urlService.buildNewsletterRegisterUrl(request, marketArea));
		followUs.setSubmitUrlFullForm(urlService.buildFollowUsUrl(request, marketPlace, market, marketArea, localization, retailer));

		final List<FollowUsOptionViewBean> followOptions = new ArrayList<FollowUsOptionViewBean>();
		followOptions.add(buildFollowOption(request, locale, "facebook"));
		followOptions.add(buildFollowOption(request, locale, "twitter"));
		followOptions.add(buildFollowOption(request, locale, "google-plus"));
		followOptions.add(buildFollowOption(request, locale, "rss"));
		followUs.setFollowOptions(followOptions);

		return followUs;
	}

	public FollowUsOptionViewBean buildFollowOption(final HttpServletRequest request, final Locale locale, String followType) throws Exception {
		String followTypeMessageKey = followType;
		if (followTypeMessageKey.contains("-")) {
			// REPLACE DASH BY DOT - DOT WILL BE REPLACE LATER TO GET MESSAGE
			// (google-plus -> google.plus)
			followTypeMessageKey = followTypeMessageKey.replaceAll("-", ".");
		}
		FollowUsOptionViewBean followOption = new FollowUsOptionViewBean();
		followOption.setCode(followType);
		followOption.setUrl(getSpecificMessage(ScopeWebMessage.FOLLOW_US, followTypeMessageKey + "_url", locale));
		followOption.setUrlLabel(getSpecificMessage(ScopeWebMessage.FOLLOW_US, followTypeMessageKey + "_url_label", locale));
		followOption.setTitle(getSpecificMessage(ScopeWebMessage.FOLLOW_US, followTypeMessageKey + "_title", locale));
		followOption.setText(getSpecificMessage(ScopeWebMessage.FOLLOW_US, followTypeMessageKey + "_text", locale));
		return followOption;
	}

	/**
     * 
     */
	public LegalTermsViewBean buildLegalTermsViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();

		final LegalTermsViewBean legalTerms = new LegalTermsViewBean();

		legalTerms.setWarning(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "warning", locale));
		legalTerms.setCopyright(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "copyright", locale));

		legalTerms.setCompanyName(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_name", locale));
		legalTerms.setCompanyAddress(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_address", locale));
		legalTerms.setCompanyAddressAdditionalInfo(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_address_additional_info", locale));
		legalTerms.setCompanyZipOrPostalCode(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_zip_or_postal_code", locale));
		legalTerms.setCompanyCity(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_city", locale));
		legalTerms.setCompanyState(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_state", locale));
		legalTerms.setCompanyCountry(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_country", locale));
		legalTerms.setCompanyPhone(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_phone", locale));
		legalTerms.setCompanyFax(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_fax", locale));
		legalTerms.setCompanyEmail(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_email", locale));
		String websiteUrl = getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_website_url", locale);
		if (StringUtils.isNotEmpty(websiteUrl) && !websiteUrl.contains("http")) {
			websiteUrl = "http://" + websiteUrl;
		}
		legalTerms.setCompanyWebsiteUrl(websiteUrl);
		legalTerms.setCompanyWebsiteName(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_website_name", locale));

		return legalTerms;
	}

	/**
     * 
     */
	public OurCompanyViewBean buildOurCompanyViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();

		final OurCompanyViewBean ourCompany = new OurCompanyViewBean();
		return ourCompany;
	}

	/**
     * 
     */
	public FaqViewBean buildFaqViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		final FaqViewBean faq = new FaqViewBean();
		return faq;
	}

	/**
     * 
     */
	public SecurityViewBean buildSecurityViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		final SecurityViewBean security = new SecurityViewBean();

		security.setLoginUrl(urlService.buildSpringSecurityCheckUrl(request, marketArea));
		security.setForgottenPasswordUrl(urlService.buildForgottenPasswordUrl(request, marketArea));

		security.getUrls().put(OAuthType.FACEBOOK.name() + "_CONNECT", 			urlService.buildOAuthConnectUrl(request, marketArea, OAuthType.FACEBOOK.getPropertyKey()));
		security.getUrls().put(OAuthType.WINDOWS_LIVE.name() + "_CONNECT",		urlService.buildOAuthConnectUrl(request, marketArea, OAuthType.WINDOWS_LIVE.getPropertyKey()));
		security.getUrls().put(OAuthType.GOOGLE_CONTACT.name() + "_CONNECT",	urlService.buildOAuthConnectUrl(request, marketArea, OAuthType.GOOGLE_CONTACT.getPropertyKey()));
		security.getUrls().put(OAuthType.YAHOO.name() + "_CONNECT",				urlService.buildOAuthConnectUrl(request, marketArea, OAuthType.YAHOO.getPropertyKey()));
		
		return security;
	}

	/**
     * 
     */
	public List<RetailerViewBean> buildRetailerViewBeansForTheMarketArea(final HttpServletRequest request, final MarketArea marketArea, final Localization localization, final Retailer retailer)
	        throws Exception {
		final WebElementType retailerElementType = WebElementType.RETAILER_VIEW_BEAN_LIST;
		final String retailerPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String retailerCacheKey = retailerPrefixCacheKey + "_RETAILER";
		List<RetailerViewBean> retailerViewBeans = (List<RetailerViewBean>) menuMarketNavigationCacheHelper.getFromCache(retailerElementType, retailerCacheKey);
		if (retailerViewBeans == null) {
			final List<Retailer> retailers = new ArrayList<Retailer>(marketArea.getRetailers());
			retailerViewBeans = buildRetailerViewBeans(request, marketArea, localization, retailer, retailers);
			menuMarketNavigationCacheHelper.addToCache(retailerElementType, retailerCacheKey, retailerViewBeans);
		}
		return retailerViewBeans;
	}

	/**
     * 
     */
	public List<RetailerViewBean> buildRetailerViewBeans(final HttpServletRequest request, final MarketArea marketArea, final Localization localization, final Retailer retailer,
	        final List<Retailer> retailers) throws Exception {
		List<RetailerViewBean> retailerViewBeans = new ArrayList<RetailerViewBean>();
		retailerViewBeans = new ArrayList<RetailerViewBean>();
		for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
			final Retailer retailerTmp = (Retailer) iterator.next();
			retailerViewBeans.add(buildRetailerViewBean(request, marketArea, localization, retailer, retailerTmp));
		}
		return retailerViewBeans;
	}

	/**
     * 
     */
	public RetailerViewBean buildRetailerViewBean(final HttpServletRequest request, final MarketArea marketArea, final Localization localization, final Retailer retailer,
	        final Retailer currentRetailer) throws Exception {
		final Market market = marketArea.getMarket();
		final MarketPlace marketPlace = market.getMarketPlace();
		final RetailerViewBean retailerViewBean = new RetailerViewBean();
		final Locale locale = localization.getLocale();
		
		retailerViewBean.setName(currentRetailer.getName());
		retailerViewBean.setDescription(currentRetailer.getDescription());

		if (currentRetailer.getAddresses() != null) {
			Set<RetailerAddress> addresses = currentRetailer.getAddresses();
			RetailerAddress defaultAddress = null;
			for (Iterator<RetailerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
				RetailerAddress retailerAddress = (RetailerAddress) iterator.next();
				if (retailerAddress.isDefault()) {
					defaultAddress = retailerAddress;
				}
			}
			if (defaultAddress != null) {
				retailerViewBean.getDefaultAddress().setAddress1(defaultAddress.getAddress1());
				retailerViewBean.getDefaultAddress().setAddress2(defaultAddress.getAddress2());
				retailerViewBean.getDefaultAddress().setAddressAdditionalInformation(defaultAddress.getAddressAdditionalInformation());
				retailerViewBean.getDefaultAddress().setPostalCode(defaultAddress.getPostalCode());
				retailerViewBean.getDefaultAddress().setCity(defaultAddress.getCity());
				retailerViewBean.getDefaultAddress().setStateCode(defaultAddress.getStateCode());
				retailerViewBean.getDefaultAddress().setStateLabel(defaultAddress.getStateCode());
				retailerViewBean.getDefaultAddress().setAreaCode(defaultAddress.getAreaCode());
				retailerViewBean.getDefaultAddress().setAreaLabel(defaultAddress.getAreaCode());
				retailerViewBean.getDefaultAddress().setCountryCode(defaultAddress.getCountryCode());
				retailerViewBean.getDefaultAddress().setCountryLabel(defaultAddress.getCountryCode());

				retailerViewBean.getDefaultAddress().setLongitude(defaultAddress.getLongitude());
				retailerViewBean.getDefaultAddress().setLatitude(defaultAddress.getLatitude());

				retailerViewBean.getDefaultAddress().setPhone(defaultAddress.getPhone());
				retailerViewBean.getDefaultAddress().setMobile(defaultAddress.getMobile());
				retailerViewBean.getDefaultAddress().setFax(defaultAddress.getFax());
				retailerViewBean.getDefaultAddress().setEmail(defaultAddress.getEmail());
				String websiteUrl = defaultAddress.getWebsite();
				if (StringUtils.isNotEmpty(websiteUrl) && !websiteUrl.contains("http")) {
					websiteUrl = "http://" + websiteUrl;
				}
				retailerViewBean.getDefaultAddress().setWebsite(websiteUrl);
			}
		}

		retailerViewBean.setUrl(urlService.buildRetailerDetailsUrl(request, marketPlace, market, marketArea, localization, retailer, currentRetailer.getName(), currentRetailer.getCode()));

		retailerViewBean.setQualityOfService(currentRetailer.getQualityOfService());
		retailerViewBean.setPriceScore(currentRetailer.getPriceScore());
		retailerViewBean.setRatioQualityPrice(currentRetailer.getRatioQualityPrice());

		int reviewCount = retailerViewBean.getComments().size();
		retailerViewBean.setReviewCount(reviewCount);
		Object[] reviewCountLabelParams = {reviewCount};
		retailerViewBean.setReviewCountLabel(getSpecificMessage(ScopeWebMessage.SOCIAL, "review_count_label", reviewCountLabelParams, locale));

		Set<RetailerCustomerComment> customerComments = currentRetailer.getCustomerComments();
		for (Iterator<RetailerCustomerComment> iterator = customerComments.iterator(); iterator.hasNext();) {
			RetailerCustomerComment retailerCustomerComment = (RetailerCustomerComment) iterator.next();
			RetailerCustomerCommentViewBean retailerCustomerCommentViewBean = new RetailerCustomerCommentViewBean();
			
			retailerCustomerCommentViewBean.setCustomerDisplayName(retailerCustomerComment.getCustomer().getScreenName());
			retailerCustomerCommentViewBean.setCustomerUrl(urlService.buildCustomerDetailsUrl(request, marketArea));
			
			DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
			if (retailerCustomerComment.getDateCreate() != null) {
				retailerCustomerCommentViewBean.setDateCreate(dateFormat.format(retailerCustomerComment.getDateCreate()));
			} else {
				retailerCustomerCommentViewBean.setDateCreate(Constants.NOT_AVAILABLE);
			}
			
			retailerCustomerCommentViewBean.setComment(retailerCustomerComment.getComment());
	        retailerViewBean.getComments().add(retailerCustomerCommentViewBean);
        }

		Set<RetailerTag> tags = currentRetailer.getRetailerTags();
		for (Iterator<RetailerTag> iterator = tags.iterator(); iterator.hasNext();) {
	        RetailerTag retailerTag = (RetailerTag) iterator.next();
	        RetailerTagViewBean retailerTagViewBean = new RetailerTagViewBean();
	        retailerTagViewBean.setCode(retailerTag.getCode());
	        retailerTagViewBean.setName(retailerTag.getName());
	        retailerTagViewBean.setDescription(retailerTag.getDescription());
	        retailerViewBean.getTags().add(retailerTagViewBean);
        }
		
		Set<Store> stores = currentRetailer.getStores();
		for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
			Store store = (Store) iterator.next();
			StoreViewBean storeViewBean = buildStoreViewBean(request, marketPlace, market, marketArea, localization, currentRetailer, store);
			retailerViewBean.getStores().add(storeViewBean);
        }
		
		return retailerViewBean;
	}

	/**
     * 
     */
	public List<CutomerMenuViewBean> buildCutomerMenuViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer) throws Exception {
		final WebElementType customerMenuElementType = WebElementType.CUSTOMER_MENU_VIEW_BEAN_LIST;
		final String customerMenuPrefixCacheKey = menuCustomerCacheHelper.buildGlobalPrefixKey(localization);
		final String customerMenuCacheKey = customerMenuPrefixCacheKey + "_CUSTOMER_MENU";
		List<CutomerMenuViewBean> customerLinks = (List<CutomerMenuViewBean>) menuCustomerCacheHelper.getFromCache(customerMenuElementType, customerMenuCacheKey);
		if (customerLinks == null) {
			final Locale locale = localization.getLocale();

			customerLinks = new ArrayList<CutomerMenuViewBean>();
			CutomerMenuViewBean cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_details_label", locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerDetailsUrl(request, marketArea));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_address_list_label", locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerAddressListUrl(request, marketArea));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_add_address_label", locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerAddAddressUrl(request, marketArea));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_order_list_label", locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerOrderListUrl(request, marketArea));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_wishlist_label", locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerWishlistUrl(request, marketArea));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_product_comment_label", locale));
			cutomerMenuViewBean.setUrl(urlService.buildCustomerProductCommentUrl(request, marketArea));
			customerLinks.add(cutomerMenuViewBean);

			menuCustomerCacheHelper.addToCache(customerMenuElementType, customerMenuCacheKey, customerLinks);
		}
		return customerLinks;
	}

	/**
     * 
     */
	public ConditionsViewBean buildConditionsViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer) throws Exception {
		final ConditionsViewBean conditions = new ConditionsViewBean();

		return conditions;
	}

	/**
     * 
     */
	public List<MarketPlaceViewBean> buildMarketPlaceViewBeans(final HttpServletRequest request) throws Exception {
		final WebElementType marketPlaceElementType = WebElementType.MARKET_PLACE_NAVIGATION_VIEW_BEAN_LIST;
		final String marketPlacePrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String marketPlaceCacheKey = marketPlacePrefixCacheKey + "_MARKETPLACE_LIST";
		List<MarketPlaceViewBean> marketPlaceViewBeans = (List<MarketPlaceViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketPlaceElementType, marketPlaceCacheKey);
		if (marketPlaceViewBeans == null) {
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

		marketPlaceViewBean.setMarkets(buildMarketViewBeans(request, marketPlace, new ArrayList<Market>(marketPlace.getMarkets()), defaultLocalization));

		return marketPlaceViewBean;
	}

	/**
     * 
     */
	public List<MarketViewBean> buildMarketViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final List<Market> markets, final Localization localization) throws Exception {
		final WebElementType marketElementType = WebElementType.MARKET_NAVIGATION_VIEW_BEAN_LIST;
		final String marketPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String marketCacheKey = marketPrefixCacheKey + "_" + marketPlace.getCode() + "_MARKET_LIST";
		List<MarketViewBean> marketViewBeans = (List<MarketViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketElementType, marketCacheKey);
		if (marketViewBeans == null) {
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

		marketViewBean.setMarketAreas(buildMarketAreaViewBeans(request, market, new ArrayList<MarketArea>(market.getMarketAreas()), defaultLocalization));

		return marketViewBean;
	}

	/**
     * 
     */
	public List<MarketAreaViewBean> buildMarketAreaViewBeans(final HttpServletRequest request, final Market market, final List<MarketArea> marketAreas, final Localization localization)
	        throws Exception {
		final WebElementType marketAreaElementType = WebElementType.MARKET_AREA_VIEW_BEAN_LIST;
		final String marketAreaPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String marketAreaCacheKey = marketAreaPrefixCacheKey + "_" + market.getCode() + "_MARKET_AREA_LIST";
		List<MarketAreaViewBean> marketAreaViewBeans = (List<MarketAreaViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketAreaElementType, marketAreaCacheKey);
		if (marketAreaViewBeans == null) {
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
		marketAreaViewBean.setDescription(marketArea.getDescription());
		marketAreaViewBean.setCode(marketArea.getCode());
		marketAreaViewBean.setUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, defaultLocalization, defaultRetailer));
		marketAreaViewBean.setLatitude(marketArea.getLatitude());
		marketAreaViewBean.setLongitude(marketArea.getLongitude());
		return marketAreaViewBean;
	}

	/**
     * 
     */
	public List<LocalizationViewBean> buildLocalizationViewBeans(final HttpServletRequest request, final MarketArea marketArea, final Localization currentLocalization) throws Exception {
		final WebElementType localizationElementType = WebElementType.LOCALIZATION_VIEW_BEAN_LIST;
		final String localizationPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String localizationCacheKey = localizationPrefixCacheKey + "_" + marketArea.getCode() + "_LOCALIZATION_LIST";
		List<LocalizationViewBean> localizationViewBeans = (List<LocalizationViewBean>) menuMarketNavigationCacheHelper.getFromCache(localizationElementType, localizationCacheKey);
		if (localizationViewBeans == null) {
			final List<Localization> translationAvailables = new ArrayList<Localization>(marketArea.getLocalizations());
			localizationViewBeans = new ArrayList<LocalizationViewBean>();
			for (Iterator<Localization> iterator = translationAvailables.iterator(); iterator.hasNext();) {
				final Localization localizationAvailable = (Localization) iterator.next();
				localizationViewBeans.add(buildLocalizationViewBean(request, marketArea, localizationAvailable));
			}
			menuMarketNavigationCacheHelper.addToCache(localizationElementType, localizationCacheKey, localizationViewBeans);
		}
		for (Iterator<LocalizationViewBean> iterator = localizationViewBeans.iterator(); iterator.hasNext();) {
			final LocalizationViewBean localizationViewBean = (LocalizationViewBean) iterator.next();
			localizationViewBean.setActive(false);
			if (localizationViewBean.getCode().equals(currentLocalization.getCode())) {
				localizationViewBean.setActive(true);
			}
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
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);

		final LocalizationViewBean localizationViewBean = new LocalizationViewBean();
		localizationViewBean.setCode(localeCodeNavigation);

		if (StringUtils.isNotEmpty(localeCodeNavigation) && localeCodeNavigation.length() == 2) {
			localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation.toLowerCase(), locale));
		} else {
			localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation, locale));
		}

		localizationViewBean.setUrl(urlService.buildChangeLanguageUrl(request, marketPlace, market, marketArea, localization, retailer));
		if (localization.getCode().equals(currentLocalization.getCode())) {
			localizationViewBean.setActive(true);
		}
		return localizationViewBean;
	}

	/**
     * 
     */
	public StoreLocatorViewBean buildStoreLocatorViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final List<Store> stores) throws Exception {

		final WebElementType storeLocatorElementType = WebElementType.STORE_VIEW_BEAN_LIST;
		final String storeLocatorPrefixCacheKey = storeLocatorCacheHelper.buildGlobalPrefixKey(localization);
		final String storeLocatorCacheKey = storeLocatorPrefixCacheKey + "_STORE_LOCATOR";
		StoreLocatorViewBean storeLocator = (StoreLocatorViewBean) storeLocatorCacheHelper.getFromCache(storeLocatorElementType, storeLocatorCacheKey);
		if (storeLocator == null) {
			final Locale locale = localization.getLocale();
			storeLocator = new StoreLocatorViewBean();
			storeLocator.setPageTitle(getSpecificMessage(ScopeWebMessage.STORE_LOCATOR, "header.title", locale));
			storeLocator.setTextHtml(getSpecificMessage(ScopeWebMessage.STORE_LOCATOR, "content.text", locale));
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
	public StoreViewBean buildStoreViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final Store store) throws Exception {

		final StoreViewBean storeLocator = new StoreViewBean();
		storeLocator.setCode(store.getCode());
		storeLocator.setBusinessName(store.getBusinessName());
		storeLocator.setAddress1(store.getAddress1());
		storeLocator.setAddress2(store.getAddress2());
		storeLocator.setAddressAdditionalInformation(store.getAddressAdditionalInformation());
		storeLocator.setPostalCode(store.getPostalCode());

		// I18n values
		storeLocator.setCity(store.getI18nCity(localization));

		storeLocator.setStateCode(store.getStateCode());
		storeLocator.setCountry(store.getCountryCode());
		storeLocator.setCountryCode(store.getCountryCode());
		storeLocator.setLongitude(store.getLongitude());
		storeLocator.setLatitude(store.getLatitude());

		return storeLocator;
	}

	/**
     * 
     */
	public CustomerViewBean buildCustomerViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final Customer customer) throws Exception {
		final Locale locale = localization.getLocale();
		final CustomerViewBean customerViewBean = new CustomerViewBean();
		if(customer != null){
			customerViewBean.setFirstname(customer.getFirstname());
			customerViewBean.setLastname(customer.getLastname());
			customerViewBean.setEmail(customer.getEmail());

			DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
			if (customer.getDateCreate() != null) {
				customerViewBean.setDateCreate(dateFormat.format(customer.getDateCreate()));
			} else {
				customerViewBean.setDateCreate(Constants.NOT_AVAILABLE);
			}

			if (customer.getDateUpdate() != null) {
				customerViewBean.setDateUpdate(dateFormat.format(customer.getDateUpdate()));
			} else {
				customerViewBean.setDateUpdate(Constants.NOT_AVAILABLE);
			}

			final ValueBean customerScreenNameValueBean = new ValueBean();
			customerScreenNameValueBean.setKey(getSpecificMessage(ScopeWebMessage.CUSTOMER, "screenname.label", locale));
			customerScreenNameValueBean.setValue(customer.getScreenName());
			customerViewBean.getCustomerAttributes().put(CustomerViewBean.SCREEN_NAME, customerScreenNameValueBean);
			
		}
		return customerViewBean;
	}

	/**
     * 
     */
	public CustomerWishlistViewBean buildCustomerWishlistViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final Customer customer) throws Exception {

		final CustomerWishlistViewBean customerWishlistViewBean = new CustomerWishlistViewBean();

		final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getCode());
		if (customerMarketArea != null) {
			final Set<CustomerWishlist> customerWishlists = customerMarketArea.getWishlistProducts();
			if (customerWishlists != null) {
				for (Iterator<CustomerWishlist> iterator = customerWishlists.iterator(); iterator.hasNext();) {
					final CustomerWishlist customerWishlist = (CustomerWishlist) iterator.next();
					final ProductSku productSku = productSkuService.getProductSkuByCode(customerMarketArea.getId(), retailer.getId(), customerWishlist.getProductSkuCode());
					final ProductMarketing productMarketing = productSku.getProductMarketing();
					final CatalogCategoryVirtual productCategory = productCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
					customerWishlistViewBean.getProductSkus().add(
					        buildProductSkuViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing, productSku));
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
		if (customerMarketArea != null) {
			final Set<CustomerProductComment> customerProductComments = customerMarketArea.getProductComments();
			if (customerProductComments != null) {
				for (Iterator<CustomerProductComment> iterator = customerProductComments.iterator(); iterator.hasNext();) {
					final CustomerProductComment customerProductComment = (CustomerProductComment) iterator.next();
					final ProductSku productSku = productSkuService.getProductSkuByCode(customerMarketArea.getId(), retailer.getId(), customerProductComment.getProductSkuCode());
					final ProductMarketing productMarketing = productSku.getProductMarketing();
					final CatalogCategoryVirtual productCategory = productCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
					customerProductCommentsViewBean.getCustomerProductCommentViewBeans()
					        .add(buildCustomerProductCommentViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing, productSku,
					                customerProductComment));
				}
			}
		}
		return customerProductCommentsViewBean;
	}

	/**
     * 
     */
	public CustomerProductCommentViewBean buildCustomerProductCommentViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final CatalogCategoryVirtual productCategory, final ProductMarketing productMarketing, final ProductSku productSku,
	        final CustomerProductComment customerProductComment) throws Exception {
		final CustomerProductCommentViewBean customerProductCommentViewBean = new CustomerProductCommentViewBean();
		customerProductCommentViewBean.setProductSku(buildProductSkuViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing, productSku));
		customerProductCommentViewBean.setComment(customerProductComment.getComment());
		return customerProductCommentViewBean;
	}

	/**
     * 
     */
	public CustomerAddressListViewBean buildCustomerAddressListViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final Customer customer) throws Exception {

		final CustomerAddressListViewBean customerAddressListViewBean = new CustomerAddressListViewBean();
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

		final CustomerAddressViewBean customerAddressViewBean = new CustomerAddressViewBean();

		customerAddressViewBean.setId(customerAddress.getId());

		String addressName = customerAddress.getAddressName();
		if (StringUtils.isNotEmpty(addressName)) {
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
		customerAddressViewBean.setStateCode(customerAddress.getStateCode());
		customerAddressViewBean.setAreaCode(customerAddress.getAreaCode());
		customerAddressViewBean.setCountryCode(customerAddress.getCountryCode());

		customerAddressViewBean.setDefaultBilling(customerAddress.isDefaultBilling());
		customerAddressViewBean.setDefaultShipping(customerAddress.isDefaultShipping());

		Long customerAddressId = customerAddress.getId();

		customerAddressViewBean.setEditUrl(urlService.buildCustomerEditAddressUrl(request, marketArea, customerAddressId.toString()));
		customerAddressViewBean.setDeleteUrl(urlService.buildCustomerDeleteAddressUrl(request, marketArea, customerAddressId.toString()));

		return customerAddressViewBean;
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
			CatalogCategoryVirtual productCategory = productCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
			productBrandViewBean.getProductMarketings().add(buildProductMarketingViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing));
		}
		return productBrandViewBean;
	}

	/**
     * 
     */
	public ProductCategoryViewBean buildMasterProductCategoryViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final CatalogCategoryVirtual productCategory) throws Exception {
		final ProductCategoryViewBean productCategoryViewBean = buildProductCategoryViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory);
		return productCategoryViewBean;
	}

	/**
     * 
     */
	public ProductCategoryViewBean buildProductCategoryViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final CatalogCategoryVirtual productCategory) throws Exception {
		final String localeCode = localization.getCode();
		final ProductCategoryViewBean productCategoryViewBean = new ProductCategoryViewBean();

		productCategoryViewBean.setName(productCategory.getI18nName(localeCode));
		productCategoryViewBean.setDescription(productCategory.getDescription());
		productCategoryViewBean.setRoot(productCategory.isRoot());

		final Asset defaultBackgroundImage = productCategory.getDefaultBackgroundImage();
		if (defaultBackgroundImage != null) {
			final String backgroundImage = requestUtil.getCatalogImageWebPath(request, defaultBackgroundImage);
			productCategoryViewBean.setBackgroundImage(backgroundImage);
		} else {
			productCategoryViewBean.setBackgroundImage("");
		}
		final Asset defaultPaskshotImage = productCategory.getDefaultPaskshotImage(ImageSize.SMALL.getPropertyKey());
		if (defaultPaskshotImage != null) {
			final String carouselImage = requestUtil.getCatalogImageWebPath(request, defaultPaskshotImage);
			productCategoryViewBean.setCarouselImage(carouselImage);
		} else {
			productCategoryViewBean.setCarouselImage("");
		}
		final Asset defaultIconImage = productCategory.getDefaultIconImage();
		if (defaultIconImage != null) {
			final String iconImage = requestUtil.getCatalogImageWebPath(request, defaultIconImage);
			productCategoryViewBean.setIconImage(iconImage);
		} else {
			productCategoryViewBean.setIconImage("");
		}

		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		if (productCategory.isRoot()) {
			productCategoryViewBean.setProductAxeUrl(urlService.buildProductCategoryUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode));
		} else {
			productCategoryViewBean.setProductLineUrl(urlService.buildProductCategoryUrlAsProductLineUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode));
		}

		List<ProductCategoryViewBean> subProductCategoryViewBeans = new ArrayList<ProductCategoryViewBean>();
		Set<CatalogCategoryVirtual> subCategories = productCategory.getCatalogCategories();
		if (subCategories != null) {
			for (Iterator<CatalogCategoryVirtual> iteratorSubProductCategory = subCategories.iterator(); iteratorSubProductCategory.hasNext();) {
				final CatalogCategoryVirtual subProductCategory = (CatalogCategoryVirtual) iteratorSubProductCategory.next();
				subProductCategoryViewBeans.add(buildProductCategoryViewBean(request, marketPlace, market, marketArea, localization, retailer, subProductCategory));
			}
		}
		productCategoryViewBean.setSubCategories(subProductCategoryViewBeans);

		List<ProductMarketingViewBean> productMarketingViewBeans = new ArrayList<ProductMarketingViewBean>();
		Set<ProductMarketing> productMarketings = productCategory.getProductMarketings();
		if (productMarketings != null) {
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
	        final Localization localization, final Retailer retailer, final CatalogCategoryVirtual productCategory, final ProductMarketing productMarketing) throws Exception {
		final String localeCode = localization.getCode();
		final ProductMarketingViewBean productMarketingViewBean = new ProductMarketingViewBean();

		productMarketingViewBean.setName(productMarketing.getI18nName(localeCode));
		productMarketingViewBean.setDescription(productMarketing.getDescription());

		final Asset defaultBackgroundImage = productMarketing.getDefaultBackgroundImage();
		if (defaultBackgroundImage != null) {
			final String backgroundImage = requestUtil.getProductMarketingImageWebPath(request, defaultBackgroundImage);
			productMarketingViewBean.setBackgroundImage(backgroundImage);
		} else {
			productMarketingViewBean.setBackgroundImage("");
		}
		final Asset defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL);
		if (defaultPaskshotImage != null) {
			final String carouselImage = requestUtil.getProductMarketingImageWebPath(request, defaultPaskshotImage);
			productMarketingViewBean.setCarouselImage(carouselImage);
		} else {
			productMarketingViewBean.setCarouselImage("");
		}
		final Asset defaultIconImage = productMarketing.getDefaultIconImage();
		if (defaultIconImage != null) {
			final String iconImage = requestUtil.getProductMarketingImageWebPath(request, defaultIconImage);
			productMarketingViewBean.setIconImage(iconImage);
		} else {
			productMarketingViewBean.setIconImage("");
		}

		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		final String productName = productMarketing.getI18nName(localeCode);
		final String productCode = productMarketing.getCode();
		productMarketingViewBean.setProductDetailsUrl(urlService
		        .buildProductUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode));

		final ProductBrand productBrand = productMarketing.getProductBrand();
		if (productBrand != null) {
			final String brandName = productBrand.getName();
			final String brandCode = productBrand.getCode();
			productMarketingViewBean.setBrandDetailsUrl(urlService.buildProductBrandDetailsUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, brandName, brandCode));
			productMarketingViewBean.setBrandLineDetailsUrl(urlService.buildProductBrandLineUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, brandName, brandCode));
		}

		Set<ProductSku> skus = productMarketing.getProductSkus();
		if (skus != null) {
			for (Iterator<ProductSku> iterator = skus.iterator(); iterator.hasNext();) {
				final ProductSku productSku = (ProductSku) iterator.next();
				productMarketingViewBean.getProductSkus().add(buildProductSkuViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing, productSku));
			}
		}

		Set<ProductAssociationLink> productCrossLinks = productMarketing.getProductAssociationLinks();
		if (productCrossLinks != null) {
			for (Iterator<ProductAssociationLink> iterator = productCrossLinks.iterator(); iterator.hasNext();) {
				final ProductAssociationLink productCrossLink = (ProductAssociationLink) iterator.next();
				if (productCrossLink.getType().equals(ProductAssociationLinkType.CROSS_SELLING)) {
					productMarketingViewBean.getProductCrossLinks().add(
					        buildProductCrossLinkViewBean(request, marketPlace, market, marketArea, localization, retailer, productCategory, productMarketing));
				}
			}
		}

		return productMarketingViewBean;
	}

	/**
	 * @throws Exception
	 * 
	 */
	public CartViewBean buildCartViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final Cart cart) throws Exception {
		final Locale locale = localization.getLocale();

		final CartViewBean cartViewBean = new CartViewBean();

		cartViewBean.setCartDetailsUrl(urlService.buildCartDetailsUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setCartAuthUrl(urlService.buildCartAuthUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setCartDeliveryAndOrderDetailsUrl(urlService.buildCartDeliveryAndOrderDetailsUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setCartOrderPaymentUrl(urlService.buildCartOrderPaymentUrl(request, marketPlace, market, marketArea, localization, retailer));
		cartViewBean.setCartOrderConfirmationUrl(urlService.buildCartOrderConfirmationUrl(request, marketPlace, market, marketArea, localization, retailer));

		cartViewBean.setAddNewAddressUrl(urlService.buildCustomerAddAddressUrl(request, marketArea));

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
			if (cartItem.getPrice() != null) {
				cartItemsTotal = cartItemsTotal.add(cartItem.getPrice());
			}
		}

		// SUB PART : Shippings
		final List<CartShippingViewBean> cartShippingViewBeans = new ArrayList<CartShippingViewBean>();
		final Set<Shipping> shippings = cart.getShippings();
		if (shippings != null) {
			for (Iterator<Shipping> iterator = shippings.iterator(); iterator.hasNext();) {
				final Shipping shipping = (Shipping) iterator.next();
				final CartShippingViewBean cartShippingViewBean = new CartShippingViewBean();
				if (shipping.getPrice() != null) {
					cartShippingTotal = cartShippingTotal.add(shipping.getPrice());
					cartShippingViewBean.setCartShippingTotal(formatter.format(shipping.getPrice()));
				}
				Object[] params = { shipping.getName() };
				cartShippingViewBean.setCartShippingTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.shippings", params, locale));
				cartShippingViewBeans.add(cartShippingViewBean);
			}
			cartViewBean.setCartShippings(cartShippingViewBeans);
		}

		// SUB PART : Taxes
		final List<CartTaxViewBean> cartTaxViewBeans = new ArrayList<CartTaxViewBean>();
		final Set<Tax> taxes = cart.getTaxes();
		if (taxes != null) {
			for (Iterator<Tax> iterator = taxes.iterator(); iterator.hasNext();) {
				final Tax tax = (Tax) iterator.next();
				final CartTaxViewBean cartTaxViewBean = new CartTaxViewBean();
				BigDecimal taxesCalc = cartItemsTotal;
				taxesCalc = taxesCalc.multiply(tax.getPercent());
				taxesCalc = taxesCalc.divide(new BigDecimal("100"));
				cartFeesTotal = cartFeesTotal.add(taxesCalc);
				Object[] params = { tax.getName() };
				cartTaxViewBean.setCartTaxTotal(formatter.format(taxesCalc));
				cartTaxViewBean.setCartTaxTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.taxes", params, locale));
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

		return cartViewBean;
	}

	/**
     * 
     */
	private CartItemViewBean buildCartItemViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final CartItem cartItem) throws Exception {
		final String localizationCode = localization.getCode();

		final CartItemViewBean cartItemViewBean = new CartItemViewBean();

		cartItemViewBean.setSkuCode(cartItem.getProductSkuCode());
		cartItemViewBean.setName(cartItem.getProductSku().getI18nName(localizationCode));
		cartItemViewBean.setQuantity(cartItem.getQuantity());

		final String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(request, currencyCode);
		final BigDecimal price = cartItem.getPrice();
		if (price != null) {
			cartItemViewBean.setPrice(formatter.format(price));
		}
		final BigDecimal totalAmountCartItem = cartItem.getTotalAmountCartItem();
		if (totalAmountCartItem != null) {
			cartItemViewBean.setAmount(formatter.format(totalAmountCartItem));
		}

		cartItemViewBean.setDeleteUrl(urlService.buildProductRemoveFromCartUrl(request, marketPlace, market, marketArea, localization, retailer, cartItem.getProductSkuCode()));

		return cartItemViewBean;
	}

	/**
     * 
     */
	public List<OrderViewBean> buildOrderViewBeans(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final List<Order> orders) throws Exception {
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
	public OrderViewBean buildOrderViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final Order order) throws Exception {
		final Locale locale = localization.getLocale();
		final String orderId = order.getId().toString();
		final OrderViewBean orderViewBean = new OrderViewBean();

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
			if (orderItem.getPrice() != null) {
				orderItemsTotal = orderItemsTotal.add(orderItem.getPrice());
			}
		}

		// SUB PART : Shippings
		final List<OrderShippingViewBean> orderShippingViewBeans = new ArrayList<OrderShippingViewBean>();
		final Set<OrderShipment> orderShipments = order.getOrderShipments();
		if (orderShipments != null) {
			for (Iterator<OrderShipment> iterator = orderShipments.iterator(); iterator.hasNext();) {
				final OrderShipment orderShipment = (OrderShipment) iterator.next();
				final OrderShippingViewBean orderShippingViewBean = new OrderShippingViewBean();
				if (orderShipment.getPrice() != null) {
					orderShippingTotal = orderShippingTotal.add(orderShipment.getPrice());
					orderShippingViewBean.setOrderShippingTotal(formatter.format(orderShipment.getPrice()));
				}
				Object[] params = { orderShipment.getName() };
				orderShippingViewBean.setOrderShippingTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.shippings", params, locale));
				orderShippingViewBeans.add(orderShippingViewBean);
			}
			orderViewBean.setOrderShippings(orderShippingViewBeans);
		}

		// SUB PART : Taxes
		final List<OrderTaxViewBean> orderTaxViewBeans = new ArrayList<OrderTaxViewBean>();
		final Set<OrderTax> orderTaxes = order.getOrderTaxes();
		if (orderTaxes != null) {
			for (Iterator<OrderTax> iterator = orderTaxes.iterator(); iterator.hasNext();) {
				final OrderTax orderTax = (OrderTax) iterator.next();
				final OrderTaxViewBean orderTaxViewBean = new OrderTaxViewBean();
				BigDecimal taxesCalc = orderItemsTotal;
				taxesCalc = taxesCalc.multiply(orderTax.getPercent());
				taxesCalc = taxesCalc.divide(new BigDecimal("100"));
				orderFeesTotal = orderFeesTotal.add(taxesCalc);
				Object[] params = { orderTax.getName() };
				orderTaxViewBean.setOrderTaxTotal(formatter.format(taxesCalc));
				orderTaxViewBean.setOrderTaxTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.taxes", params, locale));
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

		// final Object[] params = {order.getOrderNum()};
		// orderViewBean.setConfirmationMessage(getSpecificMessage(ScopeWebMessage.COMMON,
		// "order.confirmation.message", params, locale));

		orderViewBean.setOrderDetailsUrl(urlService.buildCustomerOrderDetailsUrl(request, marketArea, orderId));

		return orderViewBean;
	}

	/**
     * 
     */
	public OrderItemViewBean buildOrderItemViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final OrderItem orderItem) throws Exception {
		final String localeCode = localization.getCode();

		final OrderItemViewBean orderItemViewBean = new OrderItemViewBean();

		orderItemViewBean.setSkuCode(orderItem.getProductSkuCode());
		orderItemViewBean.setName(orderItem.getProductSku().getI18nName(localeCode));

		String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(request, currencyCode);
		final BigDecimal price = orderItem.getPrice();
		if (price != null) {
			orderItemViewBean.setPrice(formatter.format(price));
		}

		orderItemViewBean.setQuantity(orderItem.getQuantity());

		final BigDecimal totalAmountOrderItem = orderItem.getTotalAmountOrderItem();
		if (totalAmountOrderItem != null) {
			orderItemViewBean.setAmount(formatter.format(totalAmountOrderItem));
		}
		return orderItemViewBean;
	}

	/**
     * 
     */
	public ProductCrossLinkViewBean buildProductCrossLinkViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final CatalogCategoryVirtual productCategory, final ProductMarketing productMarketing) throws Exception {
		final String localeCode = localization.getCode();
		final ProductCrossLinkViewBean productCrossLinkViewBean = new ProductCrossLinkViewBean();

		// TODO : WRONG : CROSS IS SKU not marketing

		productCrossLinkViewBean.setName(productMarketing.getI18nName(localeCode));
		productCrossLinkViewBean.setDescription(productMarketing.getDescription());

		final Asset defaultBackgroundImage = productMarketing.getDefaultBackgroundImage();
		if (defaultBackgroundImage != null) {
			String backgroundImage = requestUtil.getProductMarketingImageWebPath(request, defaultBackgroundImage);
			productCrossLinkViewBean.setBackgroundImage(backgroundImage);
		} else {
			productCrossLinkViewBean.setBackgroundImage("");
		}
		final Asset defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL);
		if (defaultPaskshotImage != null) {
			String carouselImage = requestUtil.getProductMarketingImageWebPath(request, defaultPaskshotImage);
			productCrossLinkViewBean.setCrossLinkImage(carouselImage);
		} else {
			productCrossLinkViewBean.setCrossLinkImage("");
		}
		final Asset defaultIconImage = productMarketing.getDefaultIconImage();
		if (defaultIconImage != null) {
			String iconImage = requestUtil.getProductMarketingImageWebPath(request, defaultIconImage);
			productCrossLinkViewBean.setIconImage(iconImage);
		} else {
			productCrossLinkViewBean.setIconImage("");
		}

		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		final String productName = productMarketing.getI18nName(localeCode);
		final String productCode = productMarketing.getCode();
		productCrossLinkViewBean.setProductDetailsUrl(urlService
		        .buildProductUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode));

		return productCrossLinkViewBean;
	}

	/**
     * 
     */
	public ProductSkuViewBean buildProductSkuViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final CatalogCategoryVirtual productCategory, final ProductMarketing productMarketing, final ProductSku productSku)
	        throws Exception {
		final Locale locale = localization.getLocale();
		final String localeCode = localization.getCode();
		final ProductSkuViewBean productSkuViewBean = new ProductSkuViewBean();

		productSkuViewBean.setName(productSku.getI18nName(localeCode));
		productSkuViewBean.setDescription(productSku.getDescription());

		final Asset defaultBackgroundImage = productSku.getDefaultBackgroundImage();
		if (defaultBackgroundImage != null) {
			String backgroundImage = requestUtil.getProductSkuImageWebPath(request, defaultBackgroundImage);
			productSkuViewBean.setBackgroundImage(backgroundImage);
		} else {
			productSkuViewBean.setBackgroundImage("");
		}
		final Asset defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL);
		if (defaultPaskshotImage != null) {
			String carouselImage = requestUtil.getProductSkuImageWebPath(request, defaultPaskshotImage);
			productSkuViewBean.setCarouselImage(carouselImage);
		} else {
			productSkuViewBean.setCarouselImage("");
		}
		final Asset defaultIconImage = productSku.getDefaultIconImage();
		if (defaultIconImage != null) {
			String iconImage = requestUtil.getProductSkuImageWebPath(request, defaultIconImage);
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

		productSkuViewBean.setAddToCartUrl(urlService.buildProductAddToCartUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode,
		        productSkuName, productSkuCode));
		productSkuViewBean.setRemoveFromCartUrl(urlService.buildProductRemoveFromCartUrl(request, marketPlace, market, marketArea, localization, retailer, productSkuCode));
		productSkuViewBean.setAddToWishlistUrl(urlService.buildProductAddToWishlistUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName,
		        productCode, productSkuName, productSkuCode));
		productSkuViewBean.setRemoveFromWishlistUrl(urlService.buildProductRemoveFromWishlistUrl(request, marketPlace, market, marketArea, localization, retailer, productSkuCode));

		return productSkuViewBean;
	}

	// SEARCH

	/**
     * 
     */
	public SearchViewBean buildSearchViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();

		final SearchViewBean search = new SearchViewBean();
		search.setTextLabel(getSpecificMessage(ScopeWebMessage.SEARCH, "form.label.text", locale));

		return search;
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
		final String localeCode = localization.getCode();

		final String productCode = productSolr.getCode();
		ProductMarketing productMarketing = productMarketingService.getProductMarketingByCode(marketArea.getId(), retailer.getId(), productCode);

		final String productName = productMarketing.getCode();
		final CatalogCategoryVirtual productCategory = productCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		final String productSkuName = productMarketing.getDefaultProductSku().getI18nName(localeCode);
		final String productSkuCode = productMarketing.getDefaultProductSku().getCode();

		final SearchProductItemViewBean searchProductItemViewBean = new SearchProductItemViewBean();
		searchProductItemViewBean.setName(productMarketing.getI18nName(localeCode));
		searchProductItemViewBean.setDescription(productMarketing.getDescription());
		searchProductItemViewBean.setCode(productCode);

		searchProductItemViewBean.setProductDetailsUrl(urlService.buildProductUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName,
		        productCode));
		searchProductItemViewBean.setAddToCartUrl(urlService.buildProductAddToCartUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName,
		        productCode, productSkuName, productSkuCode));

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