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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.hoteia.tools.richsnippets.mapping.datavocabulary.pojo.ReviewDataVocabularyPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.Cart;
import fr.hoteia.qalingo.core.domain.CartItem;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
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
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
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
import fr.hoteia.qalingo.core.service.ReferentialDataService;
import fr.hoteia.qalingo.core.service.RetailerService;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.service.pojo.RequestData;
import fr.hoteia.qalingo.core.solr.bean.ProductSkuSolr;
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
import fr.hoteia.qalingo.web.mvc.viewbean.ShareOptionViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreLocatorViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ValueBean;

/**
 * 
 */
@Service("viewBeanFactory")
@Transactional
public class ViewBeanFactoryImpl extends AbstractFrontofficeViewBeanFactory implements ViewBeanFactory {

	@Autowired
	protected RequestUtil requestUtil;

	@Autowired
	protected MarketPlaceService marketPlaceService;

	@Autowired
	protected MarketService marketService;

	@Autowired
	protected CatalogService catalogService;

	@Autowired
	protected CatalogCategoryService productCategoryService;

	@Autowired
	protected ProductMarketingService productMarketingService;

	@Autowired
	protected ProductSkuService productSkuService;

	@Autowired
	protected CustomerProductCommentService customerProductCommentService;

	@Autowired
	protected RetailerService retailerService;
	
	@Autowired
	protected ReferentialDataService referentialDataService;
	
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
	public CommonViewBean buildCommonViewBean(final RequestData requestData) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final CommonViewBean commonViewBean = new CommonViewBean();

		final MarketPlace marketPlace = requestData.getMarketPlace();
		final Market market = requestData.getMarket();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		
		// NO CACHE FOR THIS PART

		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request);
		commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

		commonViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestData));
		commonViewBean.setLoginUrl(urlService.generateUrl(FoUrls.LOGIN, requestData));
		commonViewBean.setForgottenPasswordUrl(urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestData));
		commonViewBean.setLogoutUrl(urlService.generateUrl(FoUrls.LOGOUT, requestData));
		commonViewBean.setCreateAccountUrl(urlService.generateUrl(FoUrls.CUSTOMER_CREATE_ACCOUNT, requestUtil.getRequestData(request)));
		commonViewBean.setCustomerDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
		commonViewBean.setContactUrl(urlService.generateUrl(FoUrls.CONTACT, requestData));

		commonViewBean.setCurrentMarketPlace(buildMarketPlaceViewBean(requestData, marketPlace));
		commonViewBean.setCurrentMarket(buildMarketViewBean(requestData, market));
		commonViewBean.setCurrentMarketArea(buildMarketAreaViewBean(requestData, marketArea));
		commonViewBean.setCurrentLocalization(buildLocalizationViewBean(requestData, localization));

		return commonViewBean;
	}

	/**
     * 
     */
	public HeaderCartViewBean buildHeaderCartViewBean(final RequestData requestData) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final Localization localization = requestData.getLocalization();
		final Locale locale = localization.getLocale();
		final HeaderCartViewBean headerCartViewBean = new HeaderCartViewBean();

		// NO CACHE FOR THIS PART

		final Cart currentCart = requestUtil.getCurrentCart(request);
		headerCartViewBean.setCartUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));
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
	public List<MenuViewBean> buildMenuViewBeans(final RequestData requestData) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final MarketPlace marketPlace = requestData.getMarketPlace();
		final Market market = requestData.getMarket();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final Retailer retailer = requestData.getRetailer();
		
		final WebElementType menuTopElementType = WebElementType.TOP_MENU_VIEW_BEAN_LIST;
		String menuTopPrefixCacheKey = menuTopCacheHelper.buildPrefixKey(marketPlace, market, marketArea, localization, retailer, menuTopElementType);
		String menuTopCacheKey = menuTopPrefixCacheKey + "_GLOBAL";
		List<MenuViewBean> menuViewBeans = (List<MenuViewBean>) menuTopCacheHelper.getFromCache(menuTopElementType, menuTopCacheKey);
		String currentUrl = request.getQueryString();
		if (menuViewBeans == null) {
			final Locale locale = localization.getLocale();
			final String localeCode = localization.getCode();

			menuViewBeans = new ArrayList<MenuViewBean>();

			MenuViewBean menu = new MenuViewBean();
			menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
			if(currentUrl != null && currentUrl.contains("home.html")){
				menu.setActive(true);
			}
			menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
			menuViewBeans.add(menu);

			CatalogVirtual catalogVirtual = catalogService.getCatalogVirtual(marketArea.getId(), retailer.getId());
			if(catalogVirtual != null){
				final List<CatalogCategoryVirtual> productCategoies = catalogVirtual.getProductCategories(marketArea.getId());
				if (productCategoies != null) {
					for (Iterator<CatalogCategoryVirtual> iteratorProductCategory = productCategoies.iterator(); iteratorProductCategory.hasNext();) {
						final CatalogCategoryVirtual productCategory = (CatalogCategoryVirtual) iteratorProductCategory.next();
						menu = new MenuViewBean();
						final String seoProductCategoryName = productCategory.getI18nName(localeCode);
						menu.setName(seoProductCategoryName);
						menu.setUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_AXE, requestData, productCategory));

						List<CatalogCategoryVirtual> subProductCategories = productCategory.getCatalogCategories(marketArea.getId());
						if (subProductCategories != null) {
							List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();
							for (Iterator<CatalogCategoryVirtual> iteratorSubProductCategory = subProductCategories.iterator(); iteratorSubProductCategory.hasNext();) {
								final CatalogCategoryVirtual subProductCategory = (CatalogCategoryVirtual) iteratorSubProductCategory.next();
								final MenuViewBean subMenu = new MenuViewBean();
								final String seoSubProductCategoryName = productCategory.getI18nName(localeCode) + " " + subProductCategory.getI18nName(localeCode);
								subMenu.setName(seoSubProductCategoryName);
								subMenu.setUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, productCategory));
								subMenus.add(subMenu);
							}
							menu.setSubMenus(subMenus);
						}
						menuViewBeans.add(menu);
					}
				}
			}
			
			menu = new MenuViewBean();
			menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "our_company", locale));
			menu.setUrl(urlService.generateUrl(FoUrls.OUR_COMPANY, requestData));
			menuViewBeans.add(menu);

			menuTopCacheHelper.addToCache(menuTopElementType, menuTopCacheKey, menuViewBeans);
		}

		return menuViewBeans;
	}

	/**
     * 
     */
	public List<FooterMenuViewBean> buildFooterMenuViewBeans(final RequestData requestData) throws Exception {
		final Localization localization = requestData.getLocalization();
		
		final WebElementType footerMenuElementType = WebElementType.FOOTER_MENU_VIEW_BEAN_LIST;
		final String footerMenuPrefixCacheKey = menuFooterCacheHelper.buildGlobalPrefixKey(localization);
		final String footerMenuCacheKey = footerMenuPrefixCacheKey + "_FOOTER_MENU";
		List<FooterMenuViewBean> footerMenuViewBeans = (List<FooterMenuViewBean>) menuFooterCacheHelper.getFromCache(footerMenuElementType, footerMenuCacheKey);
		if (footerMenuViewBeans == null) {
			final Locale locale = localization.getLocale();
			footerMenuViewBeans = new ArrayList<FooterMenuViewBean>();

			FooterMenuViewBean footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "conditionsofuse", locale));
			footerMenuList.setUrl(urlService.generateUrl(FoUrls.CONDITIONS_OF_USE, requestData));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "legal_terms", locale));
			footerMenuList.setUrl(urlService.generateUrl(FoUrls.LEGAL_TERMS, requestData));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "faq", locale));
			footerMenuList.setUrl(urlService.generateUrl(FoUrls.FAQ, requestData));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "store_location", locale));
			footerMenuList.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "contactus", locale));
			footerMenuList.setUrl(urlService.generateUrl(FoUrls.CONTACT, requestData));
			footerMenuViewBeans.add(footerMenuList);

			footerMenuList = new FooterMenuViewBean();
			footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "followus", locale));
			footerMenuList.setUrl(urlService.generateUrl(FoUrls.FOLLOW_US, requestData));
			footerMenuViewBeans.add(footerMenuList);

			menuFooterCacheHelper.addToCache(footerMenuElementType, footerMenuCacheKey, footerMenuViewBeans);
		}
		return footerMenuViewBeans;
	}

	/**
     * 
     */
	public FollowUsViewBean buildFollowUsViewBean(final RequestData requestData) throws Exception {
		final Localization localization = requestData.getLocalization();
		final Locale locale = localization.getLocale();

		final FollowUsViewBean followUs = new FollowUsViewBean();

		followUs.setSubmitUrlShortForm(urlService.generateUrl(FoUrls.NEWSLETTER_REGISTER, requestData));
		followUs.setSubmitUrlFullForm(urlService.generateUrl(FoUrls.FOLLOW_US, requestData));

		final List<FollowUsOptionViewBean> followOptions = new ArrayList<FollowUsOptionViewBean>();
		followOptions.add(buildFollowOption(requestData, locale, "facebook"));
		followOptions.add(buildFollowOption(requestData, locale, "twitter"));
		followOptions.add(buildFollowOption(requestData, locale, "google-plus"));
		followOptions.add(buildFollowOption(requestData, locale, "rss"));
		followUs.setFollowOptions(followOptions);

		return followUs;
	}

	public FollowUsOptionViewBean buildFollowOption(final RequestData requestData, final Locale locale, String followType) throws Exception {
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
	public LegalTermsViewBean buildLegalTermsViewBean(final RequestData requestData) throws Exception {
		final Localization localization = requestData.getLocalization();
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
	public OurCompanyViewBean buildOurCompanyViewBean(final RequestData requestData) throws Exception {
		final OurCompanyViewBean ourCompany = new OurCompanyViewBean();
		return ourCompany;
	}

	/**
     * 
     */
	public FaqViewBean buildFaqViewBean(final RequestData requestData) throws Exception {
		final FaqViewBean faq = new FaqViewBean();
		return faq;
	}

	/**
     * 
     */
	public SecurityViewBean buildSecurityViewBean(final RequestData requestData) throws Exception {
		final SecurityViewBean security = new SecurityViewBean();

		security.setLoginUrl(urlService.generateUrl(FoUrls.LOGIN, requestData));
		security.setSubmitLoginUrl(urlService.buildSpringSecurityCheckUrl(requestData));
		security.setForgottenPasswordUrl(urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestData));

		security.getUrls().put(OAuthType.FACEBOOK.name() + "_CONNECT", 			urlService.buildOAuthConnectUrl(requestData, OAuthType.FACEBOOK.getPropertyKey()));
		security.getUrls().put(OAuthType.WINDOWS_LIVE.name() + "_CONNECT",		urlService.buildOAuthConnectUrl(requestData, OAuthType.WINDOWS_LIVE.getPropertyKey()));
		security.getUrls().put(OAuthType.GOOGLE_CONTACT.name() + "_CONNECT",	urlService.buildOAuthConnectUrl(requestData, OAuthType.GOOGLE_CONTACT.getPropertyKey()));
		security.getUrls().put(OAuthType.YAHOO.name() + "_CONNECT",				urlService.buildOAuthConnectUrl(requestData, OAuthType.YAHOO.getPropertyKey()));
		
		return security;
	}

	/**
     * 
     */
	public List<RetailerViewBean> buildRetailerViewBeansForTheMarketArea(final RequestData requestData) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final WebElementType retailerElementType = WebElementType.RETAILER_VIEW_BEAN_LIST;
		final String retailerPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
		final String retailerCacheKey = retailerPrefixCacheKey + "_RETAILER";
		List<RetailerViewBean> retailerViewBeans = (List<RetailerViewBean>) menuMarketNavigationCacheHelper.getFromCache(retailerElementType, retailerCacheKey);
		if (retailerViewBeans == null) {
			final List<Retailer> retailers = new ArrayList<Retailer>(marketArea.getRetailers());
			retailerViewBeans = buildRetailerViewBeans(requestData, retailers);
			menuMarketNavigationCacheHelper.addToCache(retailerElementType, retailerCacheKey, retailerViewBeans);
		}
		return retailerViewBeans;
	}

	/**
     * 
     */
	public List<RetailerViewBean> buildRetailerViewBeans(final RequestData requestData, final List<Retailer> retailers) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Retailer retailer = requestData.getRetailer();
		
		List<RetailerViewBean> retailerViewBeans = new ArrayList<RetailerViewBean>();
		retailerViewBeans = new ArrayList<RetailerViewBean>();
		for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
			final Retailer retailerIt = (Retailer) iterator.next();
			final Retailer reloadedRetailer = retailerService.getRetailerByCode(marketArea.getId(), retailer.getId(), retailerIt.getCode());
			retailerViewBeans.add(buildRetailerViewBean(requestData, reloadedRetailer));
		}
		return retailerViewBeans;
	}

	/**
     * 
     */
	public RetailerViewBean buildRetailerViewBean(final RequestData requestData, final Retailer retailer) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final Locale locale = localization.getLocale();

		final RetailerViewBean retailerViewBean = new RetailerViewBean();

		retailerViewBean.setCode(retailer.getCode());
		retailerViewBean.setName(retailer.getName());
		retailerViewBean.setDescription(retailer.getDescription());

		retailerViewBean.setOfficialRetailer(retailer.isOfficialRetailer());
		retailerViewBean.setDefault(retailer.isDefault());
		retailerViewBean.setBrand(retailer.isBrand());
		retailerViewBean.setEcommerce(retailer.isEcommerce());

		if (retailer.getAddresses() != null) {
			RetailerAddress defaultAddress = retailer.getDefaultAddress();
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
				
				String countryLabel = referentialDataService.getCountryByLocale(defaultAddress.getCountryCode(), locale);
				retailerViewBean.getDefaultAddress().setCountryLabel(countryLabel);

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

		retailerViewBean.setUrl(urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestData, retailer));

		retailerViewBean.setQualityOfService(retailer.getQualityOfService());
		retailerViewBean.setPriceScore(retailer.getPriceScore());
		retailerViewBean.setRatioQualityPrice(retailer.getRatioQualityPrice());

		int reviewCount = retailerViewBean.getComments().size();
		retailerViewBean.setReviewCount(reviewCount);
		Object[] reviewCountLabelParams = {reviewCount};
		retailerViewBean.setReviewCountLabel(getSpecificMessage(ScopeWebMessage.SOCIAL, "review_count_label", reviewCountLabelParams, locale));

		Set<RetailerCustomerComment> customerComments = retailer.getCustomerComments();
		if(customerComments != null){
			for (Iterator<RetailerCustomerComment> iterator = customerComments.iterator(); iterator.hasNext();) {
				RetailerCustomerComment retailerCustomerComment = (RetailerCustomerComment) iterator.next();
				RetailerCustomerCommentViewBean retailerCustomerCommentViewBean = new RetailerCustomerCommentViewBean();
				
				retailerCustomerCommentViewBean.setCustomerDisplayName(retailerCustomerComment.getCustomer().getScreenName());
				retailerCustomerCommentViewBean.setCustomerUrl(urlService.buildCustomerDetailsUrl(requestData, retailerCustomerComment.getCustomer().getPermalink()));
				retailerCustomerCommentViewBean.setCustomerAvatarImg(requestUtil.getCustomerAvatar(requestData.getRequest(), retailerCustomerComment.getCustomer()));
				
				DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
				if (retailerCustomerComment.getDateCreate() != null) {
					retailerCustomerCommentViewBean.setDateCreate(dateFormat.format(retailerCustomerComment.getDateCreate()));
				} else {
					retailerCustomerCommentViewBean.setDateCreate(Constants.NOT_AVAILABLE);
				}
				
				retailerCustomerCommentViewBean.setComment(retailerCustomerComment.getComment());
				
				ReviewDataVocabularyPojo reviewDataVocabulary = new ReviewDataVocabularyPojo();
				reviewDataVocabulary.setItemreviewed(retailer.getName());
				reviewDataVocabulary.setReviewer(retailerCustomerComment.getCustomer().getScreenName());
				DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
				reviewDataVocabulary.setDtreviewed(dateFormat.format(retailerCustomerComment.getDateCreate()));
//				reviewDataVocabulary.setSummary(summary);
				reviewDataVocabulary.setDescription(retailerCustomerComment.getComment());
//				reviewDataVocabulary.setRating(rating);
				
				retailerCustomerCommentViewBean.setReviewDataVocabulary(reviewDataVocabulary);
				
		        retailerViewBean.getComments().add(retailerCustomerCommentViewBean);
	        }
		}

		Set<RetailerTag> tags = retailer.getRetailerTags();
		if(tags != null){
			for (Iterator<RetailerTag> iterator = tags.iterator(); iterator.hasNext();) {
		        RetailerTag retailerTag = (RetailerTag) iterator.next();
		        RetailerTagViewBean retailerTagViewBean = new RetailerTagViewBean();
		        retailerTagViewBean.setCode(retailerTag.getCode());
		        retailerTagViewBean.setName(retailerTag.getName());
		        retailerTagViewBean.setDescription(retailerTag.getDescription());
		        retailerViewBean.getTags().add(retailerTagViewBean);
	        }
		}
		
		Set<Store> stores = retailer.getStores();
		if(stores != null){
			for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
				Store store = (Store) iterator.next();
				StoreViewBean storeViewBean = buildStoreViewBean( requestData, store);
				retailerViewBean.getStores().add(storeViewBean);
	        }
		}
		
		final String contextNameValue = requestUtil.getCurrentContextNameValue(request);
		List<String> shareOptions = marketArea.getShareOptions(contextNameValue);
		if(shareOptions != null){
			for (Iterator<String> iterator = shareOptions.iterator(); iterator.hasNext();) {
				String shareOption = (String) iterator.next();
				String relativeUrl = urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestData, retailer);
				ShareOptionViewBean shareOptionViewBean = buildShareOptionViewBean(requestData, shareOption, relativeUrl);
				retailerViewBean.getShareOptions().add(shareOptionViewBean);
	        }
		}
		
		return retailerViewBean;
	}

	/**
     * 
     */
	public List<CutomerMenuViewBean> buildCutomerMenuViewBeans(final RequestData requestData) throws Exception {
		final Localization localization = requestData.getLocalization();
		
		final WebElementType customerMenuElementType = WebElementType.CUSTOMER_MENU_VIEW_BEAN_LIST;
		final String customerMenuPrefixCacheKey = menuCustomerCacheHelper.buildGlobalPrefixKey(localization);
		final String customerMenuCacheKey = customerMenuPrefixCacheKey + "_CUSTOMER_MENU";
		List<CutomerMenuViewBean> customerLinks = (List<CutomerMenuViewBean>) menuCustomerCacheHelper.getFromCache(customerMenuElementType, customerMenuCacheKey);
		if (customerLinks == null) {
			final Locale locale = localization.getLocale();

			customerLinks = new ArrayList<CutomerMenuViewBean>();
			CutomerMenuViewBean cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_details_label", locale));
			cutomerMenuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_address_list_label", locale));
			cutomerMenuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_ADDRESS_LIST, requestData));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_add_address_label", locale));
			cutomerMenuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_ADD_ADDRESS, requestData));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_order_list_label", locale));
			cutomerMenuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_ORDER_LIST, requestData));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_wishlist_label", locale));
			cutomerMenuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestData));
			customerLinks.add(cutomerMenuViewBean);

			cutomerMenuViewBean = new CutomerMenuViewBean();
			cutomerMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_product_comment_label", locale));
			cutomerMenuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_PRODUCT_COMMENT_LIST, requestData));
			customerLinks.add(cutomerMenuViewBean);

			menuCustomerCacheHelper.addToCache(customerMenuElementType, customerMenuCacheKey, customerLinks);
		}
		return customerLinks;
	}

	/**
     * 
     */
	public ConditionsViewBean buildConditionsViewBean(final RequestData requestData) throws Exception {
		final ConditionsViewBean conditions = new ConditionsViewBean();

		return conditions;
	}

	/**
     * 
     */
	public List<MarketPlaceViewBean> buildMarketPlaceViewBeans(final RequestData requestData) throws Exception {
		final WebElementType marketPlaceElementType = WebElementType.MARKET_PLACE_NAVIGATION_VIEW_BEAN_LIST;
		final String marketPlacePrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String marketPlaceCacheKey = marketPlacePrefixCacheKey + "_MARKETPLACE_LIST";
		List<MarketPlaceViewBean> marketPlaceViewBeans = (List<MarketPlaceViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketPlaceElementType, marketPlaceCacheKey);
		if (marketPlaceViewBeans == null) {
			marketPlaceViewBeans = new ArrayList<MarketPlaceViewBean>();
			final List<MarketPlace> marketPlaceList = marketPlaceService.findMarketPlaces();
			for (Iterator<MarketPlace> iteratorMarketPlace = marketPlaceList.iterator(); iteratorMarketPlace.hasNext();) {
				final MarketPlace marketPlaceNavigation = (MarketPlace) iteratorMarketPlace.next();
				marketPlaceViewBeans.add(buildMarketPlaceViewBean(requestData, marketPlaceNavigation));
			}
			menuMarketNavigationCacheHelper.addToCache(marketPlaceElementType, marketPlaceCacheKey, marketPlaceViewBeans);
		}
		return marketPlaceViewBeans;
	}

	/**
     * 
     */
	public MarketPlaceViewBean buildMarketPlaceViewBean(final RequestData requestData, final MarketPlace marketPlace) throws Exception {
		final Market defaultMarket = marketPlace.getDefaultMarket();
		final MarketArea defaultMarketArea = defaultMarket.getDefaultMarketArea();
		final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
		final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();

		MarketPlaceViewBean marketPlaceViewBean = new MarketPlaceViewBean();
		marketPlaceViewBean.setName(marketPlace.getName());
		
		RequestData newRequestData = new RequestData();
		BeanUtils.copyProperties(requestData, newRequestData);
		newRequestData.setMarket(defaultMarket);
		newRequestData.setMarketArea(defaultMarketArea);
		newRequestData.setLocalization(defaultLocalization);
		newRequestData.setRetailer(defaultRetailer);
		
		marketPlaceViewBean.setUrl(urlService.generateUrl(FoUrls.HOME, newRequestData));

		marketPlaceViewBean.setMarkets(buildMarketViewBeans(newRequestData, marketPlace, new ArrayList<Market>(marketPlace.getMarkets())));

		return marketPlaceViewBean;
	}

	/**
     * 
     */
	public List<MarketViewBean> buildMarketViewBeans(final RequestData requestData, final MarketPlace marketPlace, final List<Market> markets) throws Exception {
		final WebElementType marketElementType = WebElementType.MARKET_NAVIGATION_VIEW_BEAN_LIST;
		final String marketPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String marketCacheKey = marketPrefixCacheKey + "_" + marketPlace.getCode() + "_MARKET_LIST";
		List<MarketViewBean> marketViewBeans = (List<MarketViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketElementType, marketCacheKey);
		if (marketViewBeans == null) {
			marketViewBeans = new ArrayList<MarketViewBean>();
			for (Iterator<Market> iteratorMarket = markets.iterator(); iteratorMarket.hasNext();) {
				final Market marketNavigation = (Market) iteratorMarket.next();
				marketViewBeans.add(buildMarketViewBean(requestData, marketNavigation));
			}
			menuMarketNavigationCacheHelper.addToCache(marketElementType, marketCacheKey, marketViewBeans);
		}
		return marketViewBeans;
	}

	/**
     * 
     */
	public MarketViewBean buildMarketViewBean(final RequestData requestData, final Market market) throws Exception {
		final MarketArea defaultMarketArea = market.getDefaultMarketArea();
		final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
		final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();

		final MarketViewBean marketViewBean = new MarketViewBean();
		marketViewBean.setName(market.getName());
		
		RequestData RequestData = requestData;
		RequestData.setMarketArea(defaultMarketArea);
		RequestData.setLocalization(defaultLocalization);
		RequestData.setRetailer(defaultRetailer);
		
		marketViewBean.setUrl(urlService.generateUrl(FoUrls.HOME, RequestData));

		marketViewBean.setMarketAreas(buildMarketAreaViewBeans(requestData, market, new ArrayList<MarketArea>(market.getMarketAreas())));

		return marketViewBean;
	}

	/**
     * 
     */
	public List<MarketAreaViewBean> buildMarketAreaViewBeans(final RequestData requestData, final Market market, final List<MarketArea> marketAreas)
	        throws Exception {
		final WebElementType marketAreaElementType = WebElementType.MARKET_AREA_VIEW_BEAN_LIST;
		final String marketAreaPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String marketAreaCacheKey = marketAreaPrefixCacheKey + "_" + market.getCode() + "_MARKET_AREA_LIST";
		List<MarketAreaViewBean> marketAreaViewBeans = (List<MarketAreaViewBean>) menuMarketNavigationCacheHelper.getFromCache(marketAreaElementType, marketAreaCacheKey);
		if (marketAreaViewBeans == null) {
			marketAreaViewBeans = new ArrayList<MarketAreaViewBean>();
			for (Iterator<MarketArea> iteratorMarketArea = marketAreas.iterator(); iteratorMarketArea.hasNext();) {
				final MarketArea marketArea = (MarketArea) iteratorMarketArea.next();
				marketAreaViewBeans.add(buildMarketAreaViewBean(requestData, marketArea));
			}
			menuMarketNavigationCacheHelper.addToCache(marketAreaElementType, marketAreaCacheKey, marketAreaViewBeans);
		}
		return marketAreaViewBeans;
	}

	/**
     * 
     */
	public MarketAreaViewBean buildMarketAreaViewBean(final RequestData requestData, final MarketArea marketArea) throws Exception {
		final Localization defaultLocalization = marketArea.getDefaultLocalization();
		final Retailer defaultRetailer = marketArea.getDefaultRetailer();

		final MarketAreaViewBean marketAreaViewBean = new MarketAreaViewBean();
		marketAreaViewBean.setName(marketArea.getName());
		marketAreaViewBean.setDescription(marketArea.getDescription());
		marketAreaViewBean.setCode(marketArea.getCode());
		
		RequestData RequestData = requestData;
		RequestData.setLocalization(defaultLocalization);
		RequestData.setRetailer(defaultRetailer);
		
		marketAreaViewBean.setUrl(urlService.generateUrl(FoUrls.HOME, RequestData));
		marketAreaViewBean.setLatitude(marketArea.getLatitude());
		marketAreaViewBean.setLongitude(marketArea.getLongitude());
		return marketAreaViewBean;
	}

	/**
     * 
     */
	public List<LocalizationViewBean> buildLocalizationViewBeansByMarketArea(final RequestData requestData, final Localization currentLocalization) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		
		final WebElementType localizationElementType = WebElementType.LOCALIZATION_VIEW_BEAN_LIST;
		final String localizationPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey();
		final String localizationCacheKey = localizationPrefixCacheKey + "_" + marketArea.getCode() + "_LOCALIZATION_LIST";
		List<LocalizationViewBean> localizationViewBeans = (List<LocalizationViewBean>) menuMarketNavigationCacheHelper.getFromCache(localizationElementType, localizationCacheKey);
		if (localizationViewBeans == null) {
			final List<Localization> translationAvailables = new ArrayList<Localization>(marketArea.getLocalizations());
			localizationViewBeans = new ArrayList<LocalizationViewBean>();
			for (Iterator<Localization> iterator = translationAvailables.iterator(); iterator.hasNext();) {
				final Localization localizationAvailable = (Localization) iterator.next();
				localizationViewBeans.add(buildLocalizationViewBean(requestData, localizationAvailable));
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
	public LocalizationViewBean buildLocalizationViewBean(final RequestData requestData, final Localization localization) throws Exception {
		final Localization currentLocalization = requestData.getLocalization();
		final Locale locale = localization.getLocale();
		final String localeCodeNavigation = localization.getCode();

		final LocalizationViewBean localizationViewBean = new LocalizationViewBean();
		localizationViewBean.setCode(localeCodeNavigation);

		if (StringUtils.isNotEmpty(localeCodeNavigation) && localeCodeNavigation.length() == 2) {
			localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation.toLowerCase(), locale));
		} else {
			localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation, locale));
		}

		localizationViewBean.setUrl(urlService.generateUrl(FoUrls.CHANGE_LANGUAGE, requestData));
		if (localization.getCode().equals(currentLocalization.getCode())) {
			localizationViewBean.setActive(true);
		}
		return localizationViewBean;
	}

	/**
     * 
     */
	public StoreLocatorViewBean buildStoreLocatorViewBean(final RequestData requestData, final List<Store> stores) throws Exception {
		final Localization localization = requestData.getLocalization();
		
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
				storeLocator.getStores().add(buildStoreViewBean(requestData, store));
			}
			storeLocatorCacheHelper.addToCache(storeLocatorElementType, storeLocatorCacheKey, storeLocator);
		}
		return storeLocator;
	}

	/**
     * 
     */
	public StoreViewBean buildStoreViewBean(final RequestData requestData, final Store store) throws Exception {
		final Localization localization = requestData.getLocalization();
		
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
	public ShareOptionViewBean buildShareOptionViewBean(final RequestData requestData, final String shareOption, final String relativeUrl ) throws Exception {
		final Localization localization = requestData.getLocalization();
		final Locale locale = localization.getLocale();
		
		String shareOptionCode = shareOption;
		String shareOptionColor = "";
		if(shareOptionCode.contains(":")){
			String[] shareOptionAddInfo = shareOptionCode.split(":");
			shareOptionCode = shareOptionAddInfo[0];
			for (int i = 0; i < shareOptionAddInfo.length; i++) {
                String addInfo = (String) shareOptionAddInfo[i];
                if(addInfo.contains("#")){
                	shareOptionColor = addInfo;
                }
            }
		}
		
		final ShareOptionViewBean shareOptionViewBean = new ShareOptionViewBean();
		shareOptionViewBean.setCode(shareOptionCode);
		shareOptionViewBean.setName(shareOptionCode);
		shareOptionViewBean.setLinkColor(shareOptionColor);
		shareOptionViewBean.setLinkLabel(getSpecificMessage(ScopeWebMessage.SHARE_OPTION, "label_" + shareOptionCode.toLowerCase(), locale));
		
		String absoluteUrl = urlService.buildAbsoluteUrl(requestData, relativeUrl);
		String addThisUrl = urlService.buildAddThisUrl(shareOption, absoluteUrl);

		shareOptionViewBean.setUrl(addThisUrl);

		return shareOptionViewBean;
	}
	
	/**
     * 
     */
	public CustomerViewBean buildCustomerViewBean(final RequestData requestData, final Customer customer) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final Localization localization = requestData.getLocalization();
		
		final Locale locale = localization.getLocale();
		final CustomerViewBean customerViewBean = new CustomerViewBean();
		if(customer != null){
			customerViewBean.setAvatarImg(requestUtil.getCustomerAvatar(request, customer));
			customerViewBean.setFirstname(customer.getFirstname());
			customerViewBean.setLastname(customer.getLastname());
			customerViewBean.setEmail(customer.getEmail());

			DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
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

			if(customer.getConnectionLogs() != null
					&& customer.getConnectionLogs().size() > 0){
				CustomerConnectionLog customerConnectionLog = customer.getConnectionLogs().iterator().next();
				if (customerConnectionLog.getLoginDate() != null) {
					customerViewBean.setLastConnectionDate(dateFormat.format(customerConnectionLog.getLoginDate() ));
				} else {
					customerViewBean.setLastConnectionDate(Constants.NOT_AVAILABLE);
				}
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
	public CustomerWishlistViewBean buildCustomerWishlistViewBean(final RequestData requestData, final Customer customer) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Retailer retailer = requestData.getRetailer();
		
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
					        buildProductSkuViewBean(requestData, productCategory, productMarketing, productSku));
				}
			}
		}

		return customerWishlistViewBean;
	}

	/**
     * 
     */
	public CustomerProductCommentsViewBean buildCustomerProductCommentsViewBean(final RequestData requestData, final Customer customer) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Retailer retailer = requestData.getRetailer();
		
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
					        .add(buildCustomerProductCommentViewBean(requestData, productCategory, productMarketing, productSku, customerProductComment));
				}
			}
		}
		return customerProductCommentsViewBean;
	}

	/**
     * 
     */
	public CustomerProductCommentViewBean buildCustomerProductCommentViewBean(final RequestData requestData, final CatalogCategoryVirtual productCategory, 
			final ProductMarketing productMarketing, final ProductSku productSku, final CustomerProductComment customerProductComment) throws Exception {
		final CustomerProductCommentViewBean customerProductCommentViewBean = new CustomerProductCommentViewBean();
		customerProductCommentViewBean.setProductSku(buildProductSkuViewBean(requestData, productCategory, productMarketing, productSku));
		customerProductCommentViewBean.setComment(customerProductComment.getComment());
		return customerProductCommentViewBean;
	}

	/**
     * 
     */
	public CustomerAddressListViewBean buildCustomerAddressListViewBean(final RequestData requestData, final Customer customer) throws Exception {
		final CustomerAddressListViewBean customerAddressListViewBean = new CustomerAddressListViewBean();
		customerAddressListViewBean.setBackUrl(urlService.generateUrl(FoUrls.HOME, requestData));

		Set<CustomerAddress> addresses = customer.getAddresses();
		for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
			CustomerAddress customerAddress = (CustomerAddress) iterator.next();
			customerAddressListViewBean.getCustomerAddressList().add(buildCustomeAddressViewBean(requestData, customerAddress));
		}

		return customerAddressListViewBean;
	}

	/**
     * 
     */
	public CustomerAddressViewBean buildCustomeAddressViewBean(final RequestData requestData, final CustomerAddress customerAddress) throws Exception {
		final Localization localization = requestData.getLocalization();
		final Locale locale = localization.getLocale();
		final CustomerAddressViewBean customerAddressViewBean = new CustomerAddressViewBean();
		customerAddressViewBean.setId(customerAddress.getId());

		String addressName = customerAddress.getAddressName();
		if (StringUtils.isNotEmpty(addressName)) {
			customerAddressViewBean.setAddressName(addressName);
		} else {
			customerAddressViewBean.setAddressName(customerAddress.getCity());
		}

		customerAddressViewBean.setTitleCode(customerAddress.getTitle());
		String titleLabel = referentialDataService.getTitleByLocale(customerAddress.getTitle(), locale);
		customerAddressViewBean.setTitleLabel(titleLabel);

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
		
		String coutryLabel = referentialDataService.getCountryByLocale(customerAddress.getCountryCode(), locale);
		customerAddressViewBean.setCountryLabel(coutryLabel);

		customerAddressViewBean.setDefaultBilling(customerAddress.isDefaultBilling());
		customerAddressViewBean.setDefaultShipping(customerAddress.isDefaultShipping());

		Long customerAddressId = customerAddress.getId();

		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ADDRESS_ID, customerAddressId.toString());
		
		customerAddressViewBean.setEditUrl(urlService.generateUrl(FoUrls.PERSONAL_EDIT_ADDRESS, requestData, urlParams));
		customerAddressViewBean.setDeleteUrl(urlService.generateUrl(FoUrls.PERSONAL_DELETE_ADDRESS, requestData, urlParams));

		return customerAddressViewBean;
	}

	/**
     * 
     */
	public ProductBrandViewBean buildProductBrandViewBean(final RequestData requestData, final ProductBrand productBrand) throws Exception {
		final ProductBrandViewBean productBrandViewBean = new ProductBrandViewBean();
		productBrandViewBean.setName(productBrand.getName());
		productBrandViewBean.setDescription(productBrand.getDescription());
		return productBrandViewBean;
	}

	/**
     * 
     */
	public ProductBrandViewBean buildProductBrandViewBean(final RequestData requestData, final ProductBrand productBrand, 
														  final List<ProductMarketing> productMarketings) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Retailer retailer = requestData.getRetailer();
		
		final ProductBrandViewBean productBrandViewBean = buildProductBrandViewBean(requestData, productBrand);
		for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
			final ProductMarketing productMarketing = (ProductMarketing) iterator.next();
			CatalogCategoryVirtual productCategory = productCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);
			productBrandViewBean.getProductMarketings().add(buildProductMarketingViewBean(requestData, productCategory, productMarketing));
		}
		return productBrandViewBean;
	}

	/**
     * 
     */
	public ProductCategoryViewBean buildMasterProductCategoryViewBean(final RequestData requestData, final CatalogCategoryVirtual productCategory) throws Exception {
		final ProductCategoryViewBean productCategoryViewBean = buildProductCategoryViewBean(requestData, productCategory);
		return productCategoryViewBean;
	}

	/**
     * 
     */
	public ProductCategoryViewBean buildProductCategoryViewBean(final RequestData requestData, final CatalogCategoryVirtual productCategory) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final Localization localization = requestData.getLocalization();
		
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

		if (productCategory.isRoot()) {
			productCategoryViewBean.setProductAxeUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_AXE, requestData, productCategory));
		} else {
			productCategoryViewBean.setProductLineUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, productCategory));
		}

		List<ProductCategoryViewBean> subProductCategoryViewBeans = new ArrayList<ProductCategoryViewBean>();
		Set<CatalogCategoryVirtual> subCategories = productCategory.getCatalogCategories();
		if (subCategories != null) {
			for (Iterator<CatalogCategoryVirtual> iteratorSubProductCategory = subCategories.iterator(); iteratorSubProductCategory.hasNext();) {
				final CatalogCategoryVirtual subProductCategory = (CatalogCategoryVirtual) iteratorSubProductCategory.next();
				subProductCategoryViewBeans.add(buildProductCategoryViewBean(requestData, subProductCategory));
			}
		}
		productCategoryViewBean.setSubCategories(subProductCategoryViewBeans);

		List<ProductMarketingViewBean> productMarketingViewBeans = new ArrayList<ProductMarketingViewBean>();
		Set<ProductMarketing> productMarketings = productCategory.getProductMarketings();
		if (productMarketings != null) {
			for (Iterator<ProductMarketing> iteratorProductMarketing = productMarketings.iterator(); iteratorProductMarketing.hasNext();) {
				final ProductMarketing productMarketing = (ProductMarketing) iteratorProductMarketing.next();
				productMarketingViewBeans.add(buildProductMarketingViewBean(requestData, productCategory, productMarketing));
			}
		}
		productCategoryViewBean.setProductMarketings(productMarketingViewBeans);

		return productCategoryViewBean;
	}
	
	/**
     * 
     */
	public ProductMarketingViewBean buildProductMarketingViewBean(final RequestData requestData, final CatalogCategoryVirtual productCategory, 
																  final ProductMarketing productMarketing) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final Localization localization = requestData.getLocalization();
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

		productMarketingViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, productCategory, productMarketing, productMarketing.getDefaultProductSku()));

		final ProductBrand productBrand = productMarketing.getProductBrand();
		if (productBrand != null) {
			productMarketingViewBean.setBrandDetailsUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestUtil.getRequestData(request), productBrand));
			productMarketingViewBean.setBrandLineDetailsUrl(urlService.generateUrl(FoUrls.BRAND_LINE, requestUtil.getRequestData(request), productBrand));
		}

		Set<ProductSku> skus = productMarketing.getProductSkus();
		if (skus != null) {
			for (Iterator<ProductSku> iterator = skus.iterator(); iterator.hasNext();) {
				final ProductSku productSku = (ProductSku) iterator.next();
				productMarketingViewBean.getProductSkus().add(buildProductSkuViewBean(requestData, productCategory, productMarketing, productSku));
			}
		}

		Set<ProductAssociationLink> productCrossLinks = productMarketing.getProductAssociationLinks();
		if (productCrossLinks != null) {
			for (Iterator<ProductAssociationLink> iterator = productCrossLinks.iterator(); iterator.hasNext();) {
				final ProductAssociationLink productCrossLink = (ProductAssociationLink) iterator.next();
				if (productCrossLink.getType().equals(ProductAssociationLinkType.CROSS_SELLING)) {
					productMarketingViewBean.getProductCrossLinks().add(
					        buildProductCrossLinkViewBean(requestData, productCategory, productMarketing));
				}
			}
		}

		return productMarketingViewBean;
	}

	/**
	 * @throws Exception
	 * 
	 */
	public CartViewBean buildCartViewBean(final RequestData requestData, final Cart cart) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final Locale locale = localization.getLocale();

		final CartViewBean cartViewBean = new CartViewBean();

		cartViewBean.setCartDetailsUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));
		cartViewBean.setCartAuthUrl(urlService.generateUrl(FoUrls.CART_AUTH, requestData));
		cartViewBean.setCartDeliveryAndOrderDetailsUrl(urlService.generateUrl(FoUrls.CART_DELIVERY, requestData));
		cartViewBean.setCartOrderPaymentUrl(urlService.generateUrl(FoUrls.CART_ORDER_PAYMENT, requestData));
		cartViewBean.setCartOrderConfirmationUrl(urlService.generateUrl(FoUrls.CART_ORDER_CONFIRMATION, requestData));

		cartViewBean.setAddNewAddressUrl(urlService.generateUrl(FoUrls.PERSONAL_ADD_ADDRESS, requestData));

		// ITEMS PART
		List<CartItemViewBean> cartItemViewBeans = new ArrayList<CartItemViewBean>();
		Set<CartItem> cartItems = cart.getCartItems();
		for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
			final CartItem cartItem = (CartItem) iterator.next();
			cartItemViewBeans.add(buildCartItemViewBean(requestData, cartItem));
		}
		cartViewBean.setCartItems(cartItemViewBeans);

		// SUB PART : Subtotal
		final String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(requestData, currencyCode);
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
	private CartItemViewBean buildCartItemViewBean(final RequestData requestData, final CartItem cartItem) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final String localizationCode = localization.getCode();

		final CartItemViewBean cartItemViewBean = new CartItemViewBean();

		cartItemViewBean.setSkuCode(cartItem.getProductSkuCode());
		cartItemViewBean.setName(cartItem.getProductSku().getI18nName(localizationCode));
		cartItemViewBean.setQuantity(cartItem.getQuantity());

		final String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(requestData, currencyCode);
		final BigDecimal price = cartItem.getPrice();
		if (price != null) {
			cartItemViewBean.setPrice(formatter.format(price));
		}
		final BigDecimal totalAmountCartItem = cartItem.getTotalAmountCartItem();
		if (totalAmountCartItem != null) {
			cartItemViewBean.setAmount(formatter.format(totalAmountCartItem));
		}

		cartItemViewBean.setDeleteUrl(urlService.generateUrl(FoUrls.CART_REMOVE_ITEM, requestData, cartItem));

		return cartItemViewBean;
	}

	/**
     * 
     */
	public List<OrderViewBean> buildOrderViewBeans(final RequestData requestData, final List<Order> orders) throws Exception {
		final List<OrderViewBean> orderViewBeans = new ArrayList<OrderViewBean>();
		for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			orderViewBeans.add(buildOrderViewBean(requestData, order));
		}
		return orderViewBeans;
	}

	/**
     * 
     */
	public OrderViewBean buildOrderViewBean(final RequestData requestData, final Order order) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final Locale locale = localization.getLocale();
		final String orderId = order.getId().toString();
		final OrderViewBean orderViewBean = new OrderViewBean();

		// ITEMS PART
		final List<OrderItemViewBean> orderItemViewBeans = new ArrayList<OrderItemViewBean>();
		final Set<OrderItem> orderItems = order.getOrderItems();
		for (Iterator<OrderItem> iterator = orderItems.iterator(); iterator.hasNext();) {
			OrderItem orderItem = (OrderItem) iterator.next();
			orderItemViewBeans.add(buildOrderItemViewBean(requestData, orderItem));
		}
		orderViewBean.setOrderItems(orderItemViewBeans);

		// SUB PART : Subtotal
		final String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(requestData, currencyCode);
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
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ORDER_ID, orderId.toString());

		orderViewBean.setOrderDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_ORDER_DETAILS, requestData, urlParams));

		return orderViewBean;
	}

	/**
     * 
     */
	public OrderItemViewBean buildOrderItemViewBean(final RequestData requestData, final OrderItem orderItem) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final String localeCode = localization.getCode();

		final OrderItemViewBean orderItemViewBean = new OrderItemViewBean();

		orderItemViewBean.setSkuCode(orderItem.getProductSkuCode());
		orderItemViewBean.setName(orderItem.getProductSku().getI18nName(localeCode));

		String currencyCode = marketArea.getCurrency().getCode();
		final NumberFormat formatter = requestUtil.getCartItemPriceNumberFormat(requestData, currencyCode);
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
	public ProductCrossLinkViewBean buildProductCrossLinkViewBean(final RequestData requestData, final CatalogCategoryVirtual productCategory, final ProductMarketing productMarketing) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final Localization localization = requestData.getLocalization();
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

		productCrossLinkViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, productCategory, productMarketing, productMarketing.getDefaultProductSku()));

		return productCrossLinkViewBean;
	}

	/**
     * 
     */
	public ProductSkuViewBean buildProductSkuViewBean(final RequestData requestData, final CatalogCategoryVirtual productCategory, final ProductMarketing productMarketing, final ProductSku productSku)
	        throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final Localization localization = requestData.getLocalization();
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

		productSkuViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, productCategory, productMarketing, productSku));
		
		productSkuViewBean.setAddToCartUrl(urlService.generateUrl(FoUrls.CART_ADD_ITEM, requestData, productCategory, productMarketing, productSku));
		productSkuViewBean.setRemoveFromCartUrl(urlService.generateUrl(FoUrls.CART_REMOVE_ITEM, requestData, productSku));
		
		productSkuViewBean.setAddToWishlistUrl(urlService.generateUrl(FoUrls.WISHLIST_ADD_PRODUCT, requestData, productCategory, productMarketing, productSku));
		productSkuViewBean.setRemoveFromWishlistUrl(urlService.generateUrl(FoUrls.WISHLIST_REMOVE_ITEM, requestData, productSku));

		return productSkuViewBean;
	}

	// SEARCH

	/**
     * 
     */
	public SearchViewBean buildSearchViewBean(final RequestData requestData) throws Exception {
		final Localization localization = requestData.getLocalization();
		final Locale locale = localization.getLocale();

		final SearchViewBean search = new SearchViewBean();
		search.setTextLabel(getSpecificMessage(ScopeWebMessage.SEARCH, "form.label.text", locale));

		return search;
	}

	/**
     * 
     */
	public List<SearchProductItemViewBean> buildSearchProductItemViewBeans(final RequestData requestData, 
																		   final ProductResponseBean productResponseBean) throws Exception {
		final List<SearchProductItemViewBean> searchProductItems = new ArrayList<SearchProductItemViewBean>();
		List<ProductSkuSolr> productSkus = productResponseBean.getProductSolrList();
		for (Iterator<ProductSkuSolr> iterator = productSkus.iterator(); iterator.hasNext();) {
			ProductSkuSolr productSkuSolr = (ProductSkuSolr) iterator.next();
			searchProductItems.add(buildSearchProductItemViewBean(requestData, productSkuSolr));
		}
		return searchProductItems;
	}

	/**
     * 
     */
	public SearchProductItemViewBean buildSearchProductItemViewBean(final RequestData requestData, final ProductSkuSolr productSkuSolr) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final Retailer retailer = requestData.getRetailer();
		final String localeCode = localization.getCode();

		final String productSkuCode = productSkuSolr.getCode();
		final ProductSku productSku = productSkuService.getProductSkuByCode(marketArea.getId(), retailer.getId(), productSkuCode);
		final ProductMarketing productMarketing = productMarketingService.getProductMarketingByCode(marketArea.getId(), retailer.getId(), productSku.getProductMarketing().getCode());
		final CatalogCategoryVirtual productCategory = productCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);

		final String productName = productMarketing.getCode();
		final String categoryName = productCategory.getI18nName(localeCode);
		final String categoryCode = productCategory.getCode();
		final String productSkuName = productMarketing.getDefaultProductSku().getI18nName(localeCode);

		final SearchProductItemViewBean searchProductItemViewBean = new SearchProductItemViewBean();
		searchProductItemViewBean.setName(categoryName + " " + productName + " " + productSkuName);
		searchProductItemViewBean.setDescription(productMarketing.getDescription());
		searchProductItemViewBean.setCode(productSkuCode);

		searchProductItemViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, productCategory, productMarketing, productSku));
		searchProductItemViewBean.setAddToCartUrl(urlService.generateUrl(FoUrls.CART_ADD_ITEM, requestData, productCategory, productMarketing, productSku));

		return searchProductItemViewBean;
	}

	/**
     * 
     */
	public List<SearchFacetViewBean> buildSearchFacetViewBeans(final RequestData requestData, final ProductResponseBean productResponseBean) throws Exception {
		final List<SearchFacetViewBean> searchFacetViewBeans = new ArrayList<SearchFacetViewBean>();
		List<FacetField> productFacetFields = productResponseBean.getProductSolrFacetFieldList();
		for (Iterator<FacetField> iterator = productFacetFields.iterator(); iterator.hasNext();) {
			FacetField facetField = (FacetField) iterator.next();
			searchFacetViewBeans.add(buildSearchFacetViewBean(requestData, facetField));
		}
		return searchFacetViewBeans;
	}

	/**
     * 
     */
	public SearchFacetViewBean buildSearchFacetViewBean(final RequestData requestData, final FacetField facetField) throws Exception {
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