/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.AbstractAttribute;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice_;
import org.hoteia.qalingo.core.domain.AbstractCatalogCategory;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuRel;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.CustomerConnectionLog;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.CustomerPermission;
import org.hoteia.qalingo.core.domain.CustomerRole;
import org.hoteia.qalingo.core.domain.CustomerWishlist;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.DeliveryMethodPrice;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.OrderItem;
import org.hoteia.qalingo.core.domain.OrderShipment;
import org.hoteia.qalingo.core.domain.OrderTax;
import org.hoteia.qalingo.core.domain.PaymentGatewayOption;
import org.hoteia.qalingo.core.domain.ProductAssociationLink;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductBrandAttribute;
import org.hoteia.qalingo.core.domain.ProductBrandCustomerComment;
import org.hoteia.qalingo.core.domain.ProductBrandTag;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.ProductMarketingTag;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuAttribute;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinition;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinitionType;
import org.hoteia.qalingo.core.domain.ProductSkuOptionRel;
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice;
import org.hoteia.qalingo.core.domain.ProductSkuTag;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerTag;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.StoreBusinessHour;
import org.hoteia.qalingo.core.domain.StoreCustomerComment;
import org.hoteia.qalingo.core.domain.StoreTag;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.UserConnectionLog;
import org.hoteia.qalingo.core.domain.UserGroup;
import org.hoteia.qalingo.core.domain.UserPermission;
import org.hoteia.qalingo.core.domain.UserRole;
import org.hoteia.qalingo.core.domain.bean.GeolocData;
import org.hoteia.qalingo.core.domain.enumtype.AssetType;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.domain.enumtype.OAuthType;
import org.hoteia.qalingo.core.domain.enumtype.ProductAssociationLinkType;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphProduct;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.ReferentialDataService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.service.openid.OpenProvider;
import org.hoteia.qalingo.core.web.mvc.viewbean.AssetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.AttributeDefinitionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.AttributeValueViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartDeliveryMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CommonViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CompanyViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CurrencyReferentialViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerAddressListViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerAddressViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerConnectionLogValueBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductRatesViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerWishlistViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.DeliveryMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FollowUsOptionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FollowUsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.HeaderCartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LegalTermsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketAreaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketPlaceViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OperationHourViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderShippingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderTaxViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OurCompanyViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentMethodOptionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductAssociationLinkViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandCustomerCommentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandTagViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingCustomerCommentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingTagViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuOptionDefinitionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuTagViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerCustomerCommentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerTagViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SeoDataViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ShareOptionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreBusinessHourViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreCustomerCommentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreTagViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.TaxViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.UserConnectionLogValueBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.UserViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.hoteia.tools.richsnippets.mapping.datavocabulary.pojo.ReviewDataVocabularyPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 */
@Service("viewBeanFactory")
@Transactional
public class ViewBeanFactory extends AbstractViewBeanFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected MarketService marketService;

    @Autowired
    protected CatalogService catalogService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected RetailerService retailerService;

    @Autowired
    protected ReferentialDataService referentialDataService;

    @Autowired
    protected EngineSettingService engineSettingService;
    
    @Autowired
    protected UrlService urlService;

    /**
     * 
     */
    public CommonViewBean buildViewBeanCommon(final RequestData requestData) throws Exception {
        final CommonViewBean commonViewBean = new CommonViewBean();

        final MarketPlace marketPlace = requestData.getMarketPlace();
        final Market market = requestData.getMarket();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final CurrencyReferential currency = requestData.getMarketAreaCurrency();

        // NO CACHE FOR THIS PART

        final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(requestData);
        commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

        commonViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        commonViewBean.setLoginUrl(urlService.generateUrl(FoUrls.LOGIN, requestData));
        commonViewBean.setForgottenPasswordUrl(urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestData));
        commonViewBean.setLogoutUrl(urlService.generateUrl(FoUrls.LOGOUT, requestData));
        commonViewBean.setCreateAccountUrl(urlService.generateUrl(FoUrls.CUSTOMER_CREATE_ACCOUNT, requestData));
        commonViewBean.setCheckoutCreateAccountUrl(urlService.generateUrl(FoUrls.CART_CREATE_ACCOUNT, requestData));
        commonViewBean.setCustomerDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
        commonViewBean.setPersonalDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
        commonViewBean.setNewsletterRegisterUrl(urlService.generateUrl(FoUrls.NEWSLETTER_REGISTER, requestData));
        commonViewBean.setContactUrl(urlService.generateUrl(FoUrls.CONTACT, requestData));

        commonViewBean.setContextJsonUrl(urlService.generateUrl(FoUrls.CONTEXT, requestData));

        commonViewBean.setCurrentMarketPlace(buildViewBeanMarketPlace(requestData, marketPlace));
        commonViewBean.setCurrentMarket(buildViewBeanMarket(requestData, market));
        commonViewBean.setCurrentMarketArea(buildViewBeanMarketArea(requestData, marketArea));
        commonViewBean.setCurrentMarketAreaLocalization(buildViewBeanLocalization(requestData, localization));
        commonViewBean.setCurrentMarketAreaRetailer(buildViewBeanRetailer(requestData, retailer));
        commonViewBean.setCurrentMarketAreaCurrency(buildViewBeanCurrencyReferential(requestData, currency));

        return commonViewBean;
    }

    /**
     * 
     */
    public SeoDataViewBean buildViewSeoData(final RequestData requestData) throws Exception {
        final SeoDataViewBean seoDataViewBean = new SeoDataViewBean();

        return seoDataViewBean;
    }
    
    /**
     * 
     */
    public HeaderCartViewBean buildViewBeanHeaderCart(final RequestData requestData) throws Exception {
        final Locale locale = requestData.getLocale();
        final HeaderCartViewBean headerCartViewBean = new HeaderCartViewBean();

        // NO CACHE FOR THIS PART

        final Cart currentCart = requestData.getCart();
        headerCartViewBean.setCheckoutShoppingCartUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));
        if(currentCart != null && currentCart.getCartItems() != null){
            headerCartViewBean.setCartTotalItems(currentCart.getCartItems().size());
            if (currentCart.getCartItems().size() == 1) {
                headerCartViewBean.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_one_item", locale));
            } else if (currentCart.getCartItems().size() > 1) {
                Object[] cartTotalSummaryLabelParams = { currentCart.getCartItems().size() };
                headerCartViewBean.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_many_items", cartTotalSummaryLabelParams, locale));
            } else {
                headerCartViewBean.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_no_item", locale));
            }
            
        } else {
            headerCartViewBean.setCartTotalItems(0);
            headerCartViewBean.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_no_item", locale));
        }


        return headerCartViewBean;
    }

    /**
     * 
     */
    public FollowUsViewBean buildViewBeanFollowUs(final RequestData requestData) throws Exception {
        final Locale locale = requestData.getLocale();

        final FollowUsViewBean followUs = new FollowUsViewBean();

        followUs.setSubmitUrlShortForm(urlService.generateUrl(FoUrls.NEWSLETTER_REGISTER, requestData));
        followUs.setSubmitUrlFullForm(urlService.generateUrl(FoUrls.FOLLOW_US, requestData));

        final List<FollowUsOptionViewBean> followOptions = new ArrayList<FollowUsOptionViewBean>();
        followOptions.add(buildViewBeanFollowOption(requestData, locale, "facebook"));
        followOptions.add(buildViewBeanFollowOption(requestData, locale, "twitter"));
        followOptions.add(buildViewBeanFollowOption(requestData, locale, "google-plus"));
        followOptions.add(buildViewBeanFollowOption(requestData, locale, "rss"));
        followUs.setFollowOptions(followOptions);

        return followUs;
    }

    public FollowUsOptionViewBean buildViewBeanFollowOption(final RequestData requestData, final Locale locale, String followType) throws Exception {
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
    public LegalTermsViewBean buildViewBeanLegalTerms(final RequestData requestData) throws Exception {
        final Locale locale = requestData.getLocale();

        final LegalTermsViewBean legalTerms = new LegalTermsViewBean();

        String legalTermsService= getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "service_label", locale);
        String countryLabel = getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "company_country", locale);
        Object[] paramsLegalTermsAdresse = { legalTermsService, countryLabel };
        String fullAddressCompany = getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "full_address", paramsLegalTermsAdresse, locale);
        Object[] paramsWarning = { fullAddressCompany };
        legalTerms.setWarning(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "warning", paramsWarning, locale));

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
    public OurCompanyViewBean buildViewBeanOurCompany(final RequestData requestData) throws Exception {
        final OurCompanyViewBean ourCompany = new OurCompanyViewBean();
        return ourCompany;
    }

    /**
     * 
     */
    public SecurityViewBean buildViewBeanSecurity(final RequestData requestData) throws Exception {
        final SecurityViewBean security = new SecurityViewBean();

        security.setLoginUrl(urlService.generateUrl(FoUrls.LOGIN, requestData));
        security.setSubmitLoginUrl(urlService.buildSpringSecurityCheckUrl(requestData));
        security.setForgottenPasswordUrl(urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestData));
        security.setResetPasswordUrl(urlService.generateUrl(FoUrls.RESET_PASSWORD, requestData));
        security.setCreateAccountUrl(urlService.generateUrl(FoUrls.CUSTOMER_CREATE_ACCOUNT, requestData));

        security.getUrls().put(OAuthType.FACEBOOK.name() + "_CONNECT", urlService.buildOAuthConnectUrl(requestData, OAuthType.FACEBOOK.getPropertyKey()));
        security.getUrls().put(OAuthType.WINDOWS_LIVE.name() + "_CONNECT", urlService.buildOAuthConnectUrl(requestData, OAuthType.WINDOWS_LIVE.getPropertyKey()));
        security.getUrls().put(OpenProvider.GOOGLE_ACCOUNT.name() + "_CONNECT", urlService.buildOAuthConnectUrl(requestData, OAuthType.GOOGLE_ACCOUNT.getPropertyKey()));
        security.getUrls().put(OpenProvider.YAHOO.name() + "_CONNECT", urlService.buildOpenIdConnectUrl(requestData, OpenProvider.YAHOO.getPropertyKey()));

        return security;
    }

    /**
     * 
     */
    public List<MenuViewBean> buildListViewBeanCutomerMenu(final RequestData requestData) throws Exception {
        final Locale locale = requestData.getLocale();

        int ordering = 1;
        List<MenuViewBean> customerLinks = new ArrayList<MenuViewBean>();
        MenuViewBean menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_details_label", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
        menuViewBean.setOrdering(ordering++);
        customerLinks.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_address_list_label", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_ADDRESS_LIST, requestData));
        menuViewBean.setOrdering(ordering++);
        customerLinks.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_add_address_label", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_ADD_ADDRESS, requestData));
        menuViewBean.setOrdering(ordering++);
        customerLinks.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_order_list_label", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_ORDER_LIST, requestData));
        menuViewBean.setOrdering(ordering++);
        customerLinks.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_wishlist_label", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestData));
        menuViewBean.setOrdering(ordering++);
        customerLinks.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.CUSTOMER, "customer_product_comment_label", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.PERSONAL_PRODUCT_COMMENT_LIST, requestData));
        menuViewBean.setOrdering(ordering++);
        customerLinks.add(menuViewBean);

        return customerLinks;
    }

    /**
     * 
     */
    public List<MarketPlaceViewBean> buildListViewBeanMarketPlace(final RequestData requestData) throws Exception {
        List<MarketPlaceViewBean> marketPlaceViewBeans = new ArrayList<MarketPlaceViewBean>();
        final List<MarketPlace> marketPlaceList = marketService.findMarketPlaces();
        for (Iterator<MarketPlace> iteratorMarketPlace = marketPlaceList.iterator(); iteratorMarketPlace.hasNext();) {
            final MarketPlace marketPlaceNavigation = (MarketPlace) iteratorMarketPlace.next();
            
            // TODO : why : SET A RELOAD OBJECT MARKET -> event
            // LazyInitializationException: could not initialize proxy - no Session
            final Market reloadedMarket = marketService.getMarketById(marketPlaceNavigation.getDefaultMarket().getId().toString());
            final MarketArea defaultMarketArea = marketService.getMarketAreaByCode(reloadedMarket.getDefaultMarketArea().getCode());
            final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
            final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();
            
            RequestData requestDataForThisMarketPlace = new RequestData();
            BeanUtils.copyProperties(requestData, requestDataForThisMarketPlace);
            requestDataForThisMarketPlace.setMarketPlace(marketPlaceNavigation);
            requestDataForThisMarketPlace.setMarket(reloadedMarket);
            requestDataForThisMarketPlace.setMarketArea(defaultMarketArea);
            requestDataForThisMarketPlace.setMarketAreaLocalization(defaultLocalization);
            requestDataForThisMarketPlace.setMarketAreaRetailer(defaultRetailer);
            
            marketPlaceViewBeans.add(buildViewBeanMarketPlace(requestDataForThisMarketPlace, marketPlaceNavigation));
        }
        return marketPlaceViewBeans;
    }

    /**
     * 
     */
    public MarketPlaceViewBean buildViewBeanMarketPlace(final RequestData requestData, final MarketPlace marketPlace) throws Exception {
        final MarketPlaceViewBean marketPlaceViewBean = new MarketPlaceViewBean();
        marketPlaceViewBean.setName(marketPlace.getName());

        marketPlaceViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestData));
        marketPlaceViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestData));

        marketPlaceViewBean.setMarkets(buildListViewBeanMarket(requestData, marketPlace, new ArrayList<Market>(marketPlace.getMarkets())));

        return marketPlaceViewBean;
    }

    /**
     * 
     */
    public List<MarketViewBean> buildListViewBeanMarket(final RequestData requestData, final MarketPlace marketPlace, final List<Market> markets) throws Exception {
        List<MarketViewBean> marketViewBeans = new ArrayList<MarketViewBean>();
        for (Iterator<Market> iteratorMarket = markets.iterator(); iteratorMarket.hasNext();) {
            final Market marketNavigation = (Market) iteratorMarket.next();
            // TODO : why : SET A RELOAD OBJECT MARKET -> event
            // LazyInitializationException: could not initialize proxy - no Session
            final Market marketNavigationReloaded = marketService.getMarketById(marketNavigation.getId().toString());
            
            if(marketNavigationReloaded.getDefaultMarketArea() != null){
                // RELOAD THE MARKET TO KEEP AN ENTITY WITH RIGHT FETCHS
                final MarketArea defaultMarketArea = marketService.getMarketAreaByCode(marketNavigationReloaded.getDefaultMarketArea().getCode());
                final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
                final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();

                RequestData requestDataForThisMarket = new RequestData();
                BeanUtils.copyProperties(requestData, requestDataForThisMarket);
                requestDataForThisMarket.setMarket(marketNavigationReloaded);
                requestDataForThisMarket.setMarketArea(defaultMarketArea);
                requestDataForThisMarket.setMarketAreaLocalization(defaultLocalization);
                requestDataForThisMarket.setMarketAreaRetailer(defaultRetailer);

                marketViewBeans.add(buildViewBeanMarket(requestDataForThisMarket, marketNavigationReloaded));
                
            } else {
                logger.warn("This market, " + marketNavigation.getCode() + ", doesn't have a default MarketArea! This a mandatory value!");
                marketViewBeans.add(buildViewBeanMarket(requestData, marketNavigationReloaded));
            }
            
        }
        return marketViewBeans;
    }

    /**
     * 
     */
    public MarketViewBean buildViewBeanMarket(final RequestData requestData, final Market market) throws Exception {
        final MarketViewBean marketViewBean = new MarketViewBean();
        marketViewBean.setName(market.getName());

        marketViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestData));
        marketViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestData));

        marketViewBean.setMarketAreas(buildListViewBeanMarketArea(requestData, market, new ArrayList<MarketArea>(market.getMarketAreas())));

        return marketViewBean;
    }

    /**
     * 
     */
    public List<MarketAreaViewBean> buildListViewBeanMarketArea(final RequestData requestData, final Market market, final List<MarketArea> marketAreas) throws Exception {
        List<MarketAreaViewBean> marketAreaViewBeans = new ArrayList<MarketAreaViewBean>();
        final MarketArea currentMarketArea = requestData.getMarketArea();
        if(marketAreas != null){
            for (Iterator<MarketArea> iteratorMarketArea = marketAreas.iterator(); iteratorMarketArea.hasNext();) {
                final MarketArea marketArea = (MarketArea) iteratorMarketArea.next();
                
                // RELOAD THE MARKET TO KEEP AN ENTITY WITH RIGHT FETCHS
                final Market reloadedMarket = marketService.getMarketByCode(marketArea.getMarket().getCode());
                // RELOAD THE MARKETPLACE TO KEEP AN ENTITY WITH RIGHT FETCHS
                final MarketPlace reloadedMarketPlace = marketService.getMarketPlaceByCode(reloadedMarket.getMarketPlace().getCode());
                
                final MarketArea reloadedMarketArea = marketService.getMarketAreaByCode(marketArea.getCode());
                final Localization defaultLocalization = reloadedMarketArea.getDefaultLocalization();
                final Retailer defaultRetailer = reloadedMarketArea.getDefaultRetailer();
                final CurrencyReferential defaultCurrency = reloadedMarketArea.getDefaultCurrency();

                RequestData requestDataForThisMarketArea = new RequestData();
                BeanUtils.copyProperties(requestData, requestDataForThisMarketArea);
                requestDataForThisMarketArea.setMarketPlace(reloadedMarketPlace);
                requestDataForThisMarketArea.setMarket(reloadedMarket);
                requestDataForThisMarketArea.setMarketArea(reloadedMarketArea);
                requestDataForThisMarketArea.setMarketAreaLocalization(defaultLocalization);
                requestDataForThisMarketArea.setMarketAreaRetailer(defaultRetailer);
                requestDataForThisMarketArea.setMarketAreaCurrency(defaultCurrency);
                
                MarketAreaViewBean marketAreaViewBean = buildViewBeanMarketArea(requestDataForThisMarketArea, marketArea);
                if(marketAreaViewBean.getCode().equalsIgnoreCase(currentMarketArea.getCode())){
                    marketAreaViewBean.setActive(true);
                }
                marketAreaViewBeans.add(marketAreaViewBean);
            }
        }
        return marketAreaViewBeans;
    }

    /**
     * 
     */
    public MarketAreaViewBean buildViewBeanMarketArea(final RequestData requestData, final MarketArea marketArea) throws Exception {
        final MarketAreaViewBean marketAreaViewBean = new MarketAreaViewBean();
        marketAreaViewBean.setCode(marketArea.getCode());
        marketAreaViewBean.setName(marketArea.getName());
        marketAreaViewBean.setDescription(marketArea.getDescription());

        marketAreaViewBean.setOpened(marketArea.isOpened());
        marketAreaViewBean.setEcommerce(marketArea.isEcommerce());
        marketAreaViewBean.setDefault(marketArea.isDefault());

        marketAreaViewBean.setLatitude(marketArea.getLatitude());
        marketAreaViewBean.setLongitude(marketArea.getLongitude());

        marketAreaViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestData));
        marketAreaViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestData));

        return marketAreaViewBean;
    }

    /**
     * 
     */
    public List<LocalizationViewBean> buildListViewBeanLocalizationByMarketArea(final RequestData requestData, final Localization currentLocalization) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final List<Localization> translationAvailables = new ArrayList<Localization>(marketArea.getLocalizations());
        List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
        for (Iterator<Localization> iterator = translationAvailables.iterator(); iterator.hasNext();) {
            final Localization localizationAvailable = (Localization) iterator.next();
            localizationViewBeans.add(buildViewBeanLocalization(requestData, localizationAvailable));
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
    public LocalizationViewBean buildViewBeanLocalization(final RequestData requestData, final Localization localization) throws Exception {
        final Locale locale = requestData.getLocale();
        final String localizationCode = localization.getCode();

        final LocalizationViewBean localizationViewBean = new LocalizationViewBean();
        localizationViewBean.setCode(localizationCode);
        localizationViewBean.setDescription(localization.getDescription());
        localizationViewBean.setCountry(localization.getCountry());
        localizationViewBean.setLanguage(localization.getLanguage());

        localizationViewBean.setLocaleCode(localization.getLocale().toString());
        localizationViewBean.setFallbackLocaleCode(localization.getFallbackLocaleCode());

        localizationViewBean.setName(localization.getName());
        String localizationCodeTranslation = localizationCode;
        if(localizationCodeTranslation.contains("-")){
            String[] split = localizationCodeTranslation.split("-");
            localizationCodeTranslation = split[0] + "-" + split[1].toUpperCase();
            localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localizationCodeTranslation, locale));
        } else {
            localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localizationCodeTranslation.toLowerCase(), locale));
        }

        if (localization.getLocale().equals(locale)) {
            localizationViewBean.setActive(true);
        }

        if (localization.getDateCreate() != null) {
            localizationViewBean.setDateCreate(buildCommonFormatDate(requestData, localization.getDateCreate()));
        }
        if (localization.getDateUpdate() != null) {
            localizationViewBean.setDateUpdate(buildCommonFormatDate(requestData, localization.getDateUpdate()));
        }
        
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketAreaLocalization(localization);
        localizationViewBean.setChangeContextUrl(urlService.buildChangeLanguageUrl(requestDataChangecontext));
        localizationViewBean.setChangeBackofficeLanguageUrl(urlService.buildChangeBackofficeLanguageUrl(requestDataChangecontext, localization));
        localizationViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestDataChangecontext));

        return localizationViewBean;
    }
    
    public List<CurrencyReferentialViewBean> buildListViewBeanCurrenciesByMarketArea(final RequestData requestData) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final List<CurrencyReferential> currencies = new ArrayList<CurrencyReferential>(marketArea.getCurrencies());
        List<CurrencyReferentialViewBean> retailerViewBeans = buildListViewBeanCurrencyReferential(requestData, currencies);
        return retailerViewBeans;
    }

    /**
     *
     */
    public List<CurrencyReferentialViewBean> buildListViewBeanCurrencyReferential(final RequestData requestData, final List<CurrencyReferential> currencyReferentials) throws Exception {
        final List<CurrencyReferentialViewBean> currencyReferentialViewBeans = new ArrayList<CurrencyReferentialViewBean>();
        if (currencyReferentials != null) {
            for (Iterator<CurrencyReferential> iterator = currencyReferentials.iterator(); iterator.hasNext();) {
                CurrencyReferential currencyReferential = (CurrencyReferential) iterator.next();
                currencyReferentialViewBeans.add(buildViewBeanCurrencyReferential(requestData, currencyReferential));
            }
        }
        return currencyReferentialViewBeans;
    }
    
    /**
     *
     */
    public CurrencyReferentialViewBean buildViewBeanCurrencyReferential(final RequestData requestData, final CurrencyReferential currencyReferential) throws Exception {
        final CurrencyReferentialViewBean currencyReferentialViewBean = new CurrencyReferentialViewBean();
        if (currencyReferential != null) {
            currencyReferentialViewBean.setName(currencyReferential.getName());
            currencyReferentialViewBean.setDescription(currencyReferential.getDescription());
            currencyReferentialViewBean.setCode(currencyReferential.getCode());
            currencyReferentialViewBean.setSign(currencyReferential.getSign());
            currencyReferentialViewBean.setAbbreviated(currencyReferential.getAbbreviated());

            DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            if (currencyReferential.getDateCreate() != null) {
                currencyReferentialViewBean.setDateCreate(dateFormat.format(currencyReferential.getDateCreate()));
            }
            if (currencyReferential.getDateUpdate() != null) {
                currencyReferentialViewBean.setDateUpdate(dateFormat.format(currencyReferential.getDateUpdate()));
            }
            
            // CLONE THE CURRENT REQUEST DATE TO BUILD THE CHANGE CONTEXT URL (MENU)
            RequestData requestDataChangecontext = new RequestData();
            BeanUtils.copyProperties(requestData, requestDataChangecontext);
            requestDataChangecontext.setMarketAreaCurrency(currencyReferential);

            currencyReferentialViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestDataChangecontext));
            currencyReferentialViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestDataChangecontext));

        }
        return currencyReferentialViewBean;
    }

    /**
     * 
     */
    public List<RetailerViewBean> buildListViewBeanRetailerByMarketArea(final RequestData requestData) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final List<Retailer> retailers = new ArrayList<Retailer>(marketArea.getRetailers());
        List<RetailerViewBean> retailerViewBeans = buildListViewBeanRetailer(requestData, retailers);
        return retailerViewBeans;
    }

    /**
     * 
     */
    public List<RetailerViewBean> buildListViewBeanRetailer(final RequestData requestData, final List<Retailer> retailers) throws Exception {
        List<RetailerViewBean> retailerViewBeans = new ArrayList<RetailerViewBean>();
        retailerViewBeans = new ArrayList<RetailerViewBean>();
        for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
            final Retailer retailer = (Retailer) iterator.next();
            retailerViewBeans.add(buildViewBeanRetailer(requestData, retailer));
        }
        return retailerViewBeans;
    }

    /**
     * 
     */
    public RetailerViewBean buildViewBeanRetailer(final RequestData requestData, final Retailer retailer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final Locale locale = requestData.getLocale();

        final RetailerViewBean retailerViewBean = new RetailerViewBean();

        retailerViewBean.setCode(retailer.getCode());
        retailerViewBean.setName(retailer.getName());
        retailerViewBean.setDescription(retailer.getDescription());

        retailerViewBean.setI18nName(retailer.getI18nName(localizationCode));
        retailerViewBean.setI18nDescription(retailer.getI18nDescription(localizationCode));
        
        retailerViewBean.setOfficialRetailer(retailer.isOfficialRetailer());
        retailerViewBean.setBrand(retailer.isBrand());
        retailerViewBean.setEcommerce(retailer.isEcommerce());
        retailerViewBean.setCorner(retailer.isCorner());

        String logo = retailerService.buildRetailerLogoWebPath(retailer.getLogo());
        retailerViewBean.setImg(logo);
        
        if (Hibernate.isInitialized(retailer.getAddresses()) 
                && retailer.getAddresses() != null) {
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
                retailerViewBean.getDefaultAddress().setCountry(countryLabel);

                retailerViewBean.getDefaultAddress().setLongitude(defaultAddress.getLongitude());
                retailerViewBean.getDefaultAddress().setLatitude(defaultAddress.getLatitude());

                retailerViewBean.getDefaultAddress().setPhone(defaultAddress.getPhone());
                retailerViewBean.getDefaultAddress().setMobile(defaultAddress.getMobile());
                retailerViewBean.getDefaultAddress().setFax(defaultAddress.getFax());
                retailerViewBean.getDefaultAddress().setEmail(defaultAddress.getEmail());
                retailerViewBean.getDefaultAddress().setEmail(defaultAddress.getEmail());
                retailerViewBean.getDefaultAddress().setWebsite(defaultAddress.getWebsite());
            }
        }

        retailerViewBean.setQualityOfService(retailer.getQualityOfService());
        retailerViewBean.setPriceScore(retailer.getPriceScore());
        retailerViewBean.setRatioQualityPrice(retailer.getRatioQualityPrice());

        int reviewCount = retailerViewBean.getComments().size();
        retailerViewBean.setReviewCount(reviewCount);
        Object[] reviewCountLabelParams = { reviewCount };
        retailerViewBean.setReviewCountLabel(getSpecificMessage(ScopeWebMessage.SOCIAL, "review_count_label", reviewCountLabelParams, locale));

        Set<RetailerCustomerComment> customerComments = retailer.getCustomerComments();
        if (Hibernate.isInitialized(customerComments) &&
                customerComments != null) {
            for (Iterator<RetailerCustomerComment> iterator = customerComments.iterator(); iterator.hasNext();) {
                RetailerCustomerComment customerComment = (RetailerCustomerComment) iterator.next();
                RetailerCustomerCommentViewBean customerCommentViewBean = buildViewBeanRetailerCustomerComment(requestData, retailer, customerComment);
                retailerViewBean.getComments().add(customerCommentViewBean);
            }
        }

        // TAGS
        Set<RetailerTag> tags = retailer.getTags();
        if (Hibernate.isInitialized(tags) &&
                tags != null) {
            for (Iterator<RetailerTag> iterator = tags.iterator(); iterator.hasNext();) {
                RetailerTag retailerTag = (RetailerTag) iterator.next();
                RetailerTagViewBean retailerTagViewBean = new RetailerTagViewBean();
                retailerTagViewBean.setCode(retailerTag.getCode());
                retailerTagViewBean.setName(retailerTag.getName());
                retailerTagViewBean.setDescription(retailerTag.getDescription());
                retailerViewBean.getTags().add(retailerTagViewBean);
            }
        }

        // STORES
        Set<Store> stores = retailer.getStores();
        if (Hibernate.isInitialized(stores) &&
                stores != null) {
            for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
                Store store = (Store) iterator.next();
                StoreViewBean storeViewBean = buildViewBeanStore(requestData, store);
                retailerViewBean.getStores().add(storeViewBean);
            }
        }

        final String contextNameValue = requestUtil.getCurrentContextNameValue();
        List<String> shareOptions = marketArea.getShareOptions(contextNameValue);
        if (shareOptions != null) {
            for (Iterator<String> iterator = shareOptions.iterator(); iterator.hasNext();) {
                String shareOption = (String) iterator.next();
                String relativeUrl = urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestData, retailer);
                ShareOptionViewBean shareOptionViewBean = buildViewBeanShareOption(requestData, shareOption, relativeUrl);
                retailerViewBean.getShareOptions().add(shareOptionViewBean);
            }
        }

        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (retailer.getDateCreate() != null) {
            retailerViewBean.setDateCreate(dateFormat.format(retailer.getDateCreate()));
        }

        if (retailer.getDateUpdate() != null) {
            retailerViewBean.setDateUpdate(dateFormat.format(retailer.getDateUpdate()));
        }
        
        // CLONE THE CURRENT REQUEST DATE TO BUILD THE CHANGE CONTEXT URL (MENU)
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketAreaRetailer(retailer);
        
        retailerViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestDataChangecontext));
        retailerViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestDataChangecontext));

        retailerViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestData, retailer));
        
        return retailerViewBean;
    }
    
    /**
     * 
     */
    public List<RetailerCustomerCommentViewBean> buildListViewBeanRetailerCustomerComments(final RequestData requestData, final List<RetailerCustomerComment> customerComments) throws Exception {
        final List<RetailerCustomerCommentViewBean> customerCommentViewBeans = new ArrayList<RetailerCustomerCommentViewBean>();
        for (Iterator<RetailerCustomerComment> iterator = customerComments.iterator(); iterator.hasNext();) {
            RetailerCustomerComment customerComment = (RetailerCustomerComment) iterator.next();
            customerCommentViewBeans.add(buildViewBeanRetailerCustomerComment(requestData, customerComment.getRetailer(), customerComment));
        }
        return customerCommentViewBeans;
    }
    
    /**
     * 
     */
    public RetailerCustomerCommentViewBean buildViewBeanRetailerCustomerComment(final RequestData requestData, final Retailer retailer, final RetailerCustomerComment customerComment) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final RetailerCustomerCommentViewBean customerCommentViewBean = new RetailerCustomerCommentViewBean();
        customerCommentViewBean.setTitle(customerComment.getTitle());
        customerCommentViewBean.setComment(customerComment.getComment());

        if (customerComment.getCustomer() != null) {
            customerCommentViewBean.setCustomerDisplayName(customerComment.getCustomer().getScreenName());
            customerCommentViewBean.setCustomerUrl(urlService.buildCustomerDetailsUrl(requestData, customerComment.getCustomer().getPermalink()));
            customerCommentViewBean.setCustomerAvatarImg(requestUtil.getCustomerAvatar(requestData.getRequest(), customerComment.getCustomer()));
        }
        
        customerCommentViewBean.setComment(customerComment.getComment());
        
        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (customerComment.getDateCreate() != null) {
            customerCommentViewBean.setDateCreate(dateFormat.format(customerComment.getDateCreate()));
        }
        if (customerComment.getDateUpdate() != null) {
            customerCommentViewBean.setDateUpdate(dateFormat.format(customerComment.getDateUpdate()));
        }
        
        ReviewDataVocabularyPojo reviewDataVocabulary = new ReviewDataVocabularyPojo();
        reviewDataVocabulary.setItemreviewed(retailer.getI18nName(localizationCode));
        if (customerComment.getCustomer() != null) {
            reviewDataVocabulary.setReviewer(customerComment.getCustomer().getScreenName());
        }
        DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
        reviewDataVocabulary.setDtreviewed(dateFormat.format(customerComment.getDateCreate()));
        // reviewDataVocabulary.setSummary(summary);
        reviewDataVocabulary.setDescription(customerComment.getComment());
        // reviewDataVocabulary.setRating(rating);

        customerCommentViewBean.setReviewDataVocabulary(reviewDataVocabulary);

        return customerCommentViewBean;
    }
    
    /**
     * 
     */
    public List<StoreViewBean> buildListViewBeanStore(final RequestData requestData, final List<Store> stores) throws Exception {
        List<StoreViewBean> storeViewBeans = new ArrayList<StoreViewBean>();
        for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
            final Store store = (Store) iterator.next();
            storeViewBeans.add(buildViewBeanStore(requestData, store));
        }
        return storeViewBeans;
    }

    /**
     * 
     */
    public StoreViewBean buildViewBeanStore(final RequestData requestData, final Store store) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final Locale locale = requestData.getLocale();
        final GeolocData geolocData = requestData.getGeolocData();
        
        final StoreViewBean storeViewBean = new StoreViewBean();
        storeViewBean.setCode(store.getCode());
        storeViewBean.setName(store.getName());
        
        storeViewBean.setI18nName(store.getI18nName(localizationCode));
        storeViewBean.setI18nDescription(store.getI18nDescription(localizationCode));
        
        storeViewBean.setAddress1(store.getAddress1());
        storeViewBean.setAddress2(store.getAddress2());
        storeViewBean.setAddressAdditionalInformation(store.getAddressAdditionalInformation());
        storeViewBean.setPostalCode(store.getPostalCode());

        storeViewBean.setCity(store.getCity());
        String i18nCityName = store.getI18nCity(localizationCode);
        if(StringUtils.isNotEmpty(i18nCityName)){
            storeViewBean.setCity(i18nCityName);
        }

        storeViewBean.setStateCode(store.getStateCode());
        
        storeViewBean.setCountryCode(store.getCountryCode());
        String coutryLabel = referentialDataService.getCountryByLocale(store.getCountryCode(), locale);
        storeViewBean.setCountry(coutryLabel);
        
        storeViewBean.setEmail(store.getEmail());
        storeViewBean.setPhone(store.getPhone());
        storeViewBean.setFax(store.getFax());
        storeViewBean.setWebsite(store.getWebsite());
        
        storeViewBean.setLongitude(store.getLongitude());
        storeViewBean.setLatitude(store.getLatitude());
        
        if(geolocData != null && StringUtils.isNotEmpty(geolocData.getLatitude()) && StringUtils.isNotEmpty(geolocData.getLongitude())
                && StringUtils.isNotEmpty(store.getLongitude()) && StringUtils.isNotEmpty(store.getLongitude())){
            NumberFormat formatter = new DecimalFormat("#0.00");
            storeViewBean.setDistance(formatter.format(store.getDistanceFromInKm(geolocData.getLatitude(), geolocData.getLongitude())));
        }
        
        // ASSETS
        if (Hibernate.isInitialized(store.getAssets()) && store.getAssets() != null) {
            for (Iterator<Asset> iterator = store.getAssets().iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
                final String path = engineSettingService.getRetailerOrStoreImageWebPath(asset);
                assetViewBean.setRelativeWebPath(path);
                assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                storeViewBean.getAssets().add(assetViewBean);
            }
        } 
        // FALLBACK ASSET
        Asset asset = new Asset();
        asset.setType(Asset.ASSET_TYPE_DEFAULT);
        asset.setScope("store");
        asset.setPath("default-store.png");
        AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
        final String path = engineSettingService.getRetailerOrStoreImageWebPath(asset);
        assetViewBean.setRelativeWebPath(path);
        assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
        storeViewBean.getAssets().add(assetViewBean);
        
        // TAGS
        Set<StoreTag> tags = store.getTags();
        if (Hibernate.isInitialized(tags) 
                && tags != null) {
            for (Iterator<StoreTag> iterator = tags.iterator(); iterator.hasNext();) {
                StoreTag storeTag = (StoreTag) iterator.next();
                StoreTagViewBean storeTagViewBean = new StoreTagViewBean();
                storeTagViewBean.setCode(storeTag.getCode());
                storeTagViewBean.setName(storeTag.getName());
                storeTagViewBean.setDescription(storeTag.getDescription());
                storeViewBean.getTags().add(storeTagViewBean);
            }
        }
        
        if (store.getDateCreate() != null) {
            storeViewBean.setDateCreate(buildCommonFormatDate(requestData, store.getDateCreate()));
        }
        if (store.getDateUpdate() != null) {
            storeViewBean.setDateUpdate(buildCommonFormatDate(requestData, store.getDateUpdate()));
        }
        
        storeViewBean.setBusinessHour(buildViewBeanStoreBusinessHour(store));
        
        // ASSETS
        Set<Asset> assets = store.getAssets();
        if (Hibernate.isInitialized(assets) 
                && assets != null) {
            List<String> sliders = new ArrayList<String>();
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                Asset assetSlideshow = (Asset) iterator.next();
                if(AssetType.SLIDESHOW.getPropertyKey().equals(assetSlideshow.getType())){
                    final String iconImage = engineSettingService.getRetailerOrStoreImageWebPath(assetSlideshow);
                    sliders.add(iconImage);
                }
            }
            storeViewBean.setSliders(sliders);
        }
	    
        storeViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.STORE_DETAILS, requestData, store));
        
        return storeViewBean;
    }
    
    /**
     * 
     */
    public StoreBusinessHourViewBean buildViewBeanStoreBusinessHour(final Store store) {
        StoreBusinessHourViewBean storeBusinessHourViewBean = null;
        if (Hibernate.isInitialized(store.getStoreBusinessHours()) &&
                store.getStoreBusinessHours() != null) {
            List<StoreBusinessHour> storeBusinessHours = store.getStoreBusinessHours();
            if (storeBusinessHours != null 
                    && storeBusinessHours.size() > 0) {
                storeBusinessHourViewBean = new StoreBusinessHourViewBean();
                for (StoreBusinessHour storeBusinessHour : storeBusinessHours) {
                    OperationHourViewBean operationHourViewBean = new OperationHourViewBean();
                    operationHourViewBean.setEndHour(storeBusinessHour.getEndHour());
                    operationHourViewBean.setStartHour(storeBusinessHour.getStartHour());
                    if (storeBusinessHour.isMonday()) {
                        storeBusinessHourViewBean.setMonday(operationHourViewBean);
                    }
                    if (storeBusinessHour.isTuesday()) {
                        storeBusinessHourViewBean.setTuesday(operationHourViewBean);
                    }
                    if (storeBusinessHour.isWednesday()) {
                        storeBusinessHourViewBean.setWednesday(operationHourViewBean);
                    }
                    if (storeBusinessHour.isThursday()) {
                        storeBusinessHourViewBean.setThursday(operationHourViewBean);
                    }
                    if (storeBusinessHour.isFriday()) {
                        storeBusinessHourViewBean.setFriday(operationHourViewBean);
                    }
                    if (storeBusinessHour.isSaturday()) {
                        storeBusinessHourViewBean.setSaturday(operationHourViewBean);
                    }
                    if (storeBusinessHour.isSunday()) {
                        storeBusinessHourViewBean.setSunday(operationHourViewBean);
                    }
                }
            }
        }
        return storeBusinessHourViewBean;
    }
    
    /**
     * 
     */
    public List<StoreCustomerCommentViewBean> buildListViewBeanStoreCustomerComments(final RequestData requestData, final List<StoreCustomerComment> customerComments) throws Exception {
        final List<StoreCustomerCommentViewBean> customerCommentViewBeans = new ArrayList<StoreCustomerCommentViewBean>();
        for (Iterator<StoreCustomerComment> iterator = customerComments.iterator(); iterator.hasNext();) {
            StoreCustomerComment customerComment = (StoreCustomerComment) iterator.next();
            customerCommentViewBeans.add(buildViewBeanStoreCustomerComment(requestData, customerComment.getStore(), customerComment));
        }
        return customerCommentViewBeans;
    }
    
    /**
     * 
     */
    public StoreCustomerCommentViewBean buildViewBeanStoreCustomerComment(final RequestData requestData, final Store store, final StoreCustomerComment customerComment) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final StoreCustomerCommentViewBean customerCommentViewBean = new StoreCustomerCommentViewBean();
        customerCommentViewBean.setTitle(customerComment.getTitle());
        customerCommentViewBean.setComment(customerComment.getComment());

        if (customerComment.getCustomer() != null) {
            customerCommentViewBean.setCustomerDisplayName(customerComment.getCustomer().getScreenName());
            customerCommentViewBean.setCustomerUrl(urlService.buildCustomerDetailsUrl(requestData, customerComment.getCustomer().getPermalink()));
            customerCommentViewBean.setCustomerAvatarImg(requestUtil.getCustomerAvatar(requestData.getRequest(), customerComment.getCustomer()));
        }
        
        customerCommentViewBean.setComment(customerComment.getComment());
        
        if (customerComment.getDateCreate() != null) {
            customerCommentViewBean.setDateCreate(buildCommonFormatDate(requestData, customerComment.getDateCreate()));
        }
        if (customerComment.getDateUpdate() != null) {
            customerCommentViewBean.setDateUpdate(buildCommonFormatDate(requestData, customerComment.getDateUpdate()));
        }
        
        ReviewDataVocabularyPojo reviewDataVocabulary = new ReviewDataVocabularyPojo();
        reviewDataVocabulary.setItemreviewed(store.getI18nName(localizationCode));
        if (customerComment.getCustomer() != null) {
            reviewDataVocabulary.setReviewer(customerComment.getCustomer().getScreenName());
        }
        DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        reviewDataVocabulary.setDtreviewed(dateFormat.format(customerComment.getDateCreate()));
        // reviewDataVocabulary.setSummary(summary);
        reviewDataVocabulary.setDescription(customerComment.getComment());
        // reviewDataVocabulary.setRating(rating);

        customerCommentViewBean.setReviewDataVocabulary(reviewDataVocabulary);

        return customerCommentViewBean;
    }
    
    /**
     * 
     */
    public ShareOptionViewBean buildViewBeanShareOption(final RequestData requestData, final String shareOption, final String relativeUrl) throws Exception {
        final Locale locale = requestData.getLocale();

        String shareOptionCode = shareOption;
        String shareOptionColor = "";
        if (shareOptionCode.contains(":")) {
            String[] shareOptionAddInfo = shareOptionCode.split(":");
            shareOptionCode = shareOptionAddInfo[0];
            for (int i = 0; i < shareOptionAddInfo.length; i++) {
                String addInfo = (String) shareOptionAddInfo[i];
                if (addInfo.contains("#")) {
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
    public CustomerViewBean buildViewBeanCustomer(final RequestData requestData, final Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Locale locale = requestData.getLocale();
        final CustomerViewBean customerViewBean = new CustomerViewBean();
        if (customer != null) {
            customerViewBean.setCode(customer.getCode());
            customerViewBean.setAvatarImg(requestUtil.getCustomerAvatar(request, customer));
            customerViewBean.setTitle(referentialDataService.getTitleByLocale(customer.getTitle(), locale));
            customerViewBean.setFirstname(customer.getFirstname());
            customerViewBean.setLastname(customer.getLastname());
            customerViewBean.setEmail(customer.getEmail());
            customerViewBean.setLogin(customer.getLogin());

            if (customer.getDateCreate() != null) {
                customerViewBean.setDateCreate(buildCommonFormatDate(requestData, customer.getDateCreate()));
            }
            if (customer.getDateUpdate() != null) {
                customerViewBean.setDateUpdate(buildCommonFormatDate(requestData, customer.getDateUpdate()));
            }

            if (customer.getGroups() != null && Hibernate.isInitialized(customer.getGroups())) {
                final Set<CustomerGroup> groups = customer.getGroups();
                for (Iterator<CustomerGroup> iteratorGroup = groups.iterator(); iteratorGroup.hasNext();) {
                    CustomerGroup group = (CustomerGroup) iteratorGroup.next();
                    String keyCustomerGroup = group.getCode();
                    String valueCustomerGroup = group.getName();
                    customerViewBean.getGroups().put(keyCustomerGroup, valueCustomerGroup);

                    if (group.getRoles() != null && Hibernate.isInitialized(group.getRoles())) {
                        final Set<CustomerRole> roles = group.getRoles();
                        for (Iterator<CustomerRole> iteratorRole = roles.iterator(); iteratorRole.hasNext();) {
                            CustomerRole role = (CustomerRole) iteratorRole.next();
                            String keyCustomerRole = role.getCode();
                            String valueCustomerRole = role.getName();
                            customerViewBean.getRoles().put(keyCustomerRole, valueCustomerRole);

                            if (role.getPermissions() != null && Hibernate.isInitialized(role.getPermissions())) {
                                final Set<CustomerPermission> permissions = role.getPermissions();
                                for (Iterator<CustomerPermission> iteratorPermission = permissions.iterator(); iteratorPermission.hasNext();) {
                                    CustomerPermission permission = (CustomerPermission) iteratorPermission.next();
                                    String keyCustomerPermission = permission.getCode();
                                    String valueCustomerPermission = permission.getName();
                                    customerViewBean.getPermissions().put(keyCustomerPermission, valueCustomerPermission);
                                }
                            }
                        }
                    }
                }
            }
            
            if (customer.getConnectionLogs() != null && Hibernate.isInitialized(customer.getConnectionLogs())) {
                int count = 0;
                DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
                for (Iterator<CustomerConnectionLog> iteratorCustomerConnectionLog = customer.getSortedConnectionLogs().iterator(); iteratorCustomerConnectionLog.hasNext();) {
                    CustomerConnectionLog connectionLog = (CustomerConnectionLog) iteratorCustomerConnectionLog.next();
                    if(count == 0){
                        if (connectionLog.getLoginDate() != null) {
                            customerViewBean.setLastConnectionDate(dateFormat.format(connectionLog.getLoginDate()));
                        } else {
                            customerViewBean.setLastConnectionDate(Constants.NOT_AVAILABLE);
                        }
                    }
                    CustomerConnectionLogValueBean connectionLogValueBean = new CustomerConnectionLogValueBean();
                    connectionLogValueBean.setDate(dateFormat.format(connectionLog.getLoginDate()));
                    connectionLogValueBean.setHost(Constants.NOT_AVAILABLE);
                    if (StringUtils.isNotEmpty(connectionLog.getHost())) {
                        connectionLogValueBean.setHost(connectionLog.getHost());
                    }
                    connectionLogValueBean.setPublicAddress(Constants.NOT_AVAILABLE);
                    if (StringUtils.isNotEmpty(connectionLog.getPublicAddress())) {
                        connectionLogValueBean.setPublicAddress(connectionLog.getPublicAddress());
                    }
                    connectionLogValueBean.setPrivateAddress(Constants.NOT_AVAILABLE);
                    if (StringUtils.isNotEmpty(connectionLog.getPrivateAddress())) {
                        connectionLogValueBean.setPublicAddress(connectionLog.getPrivateAddress());
                    }
                    customerViewBean.getCustomerConnectionLogs().add(connectionLogValueBean);
                }
            }

            if (customer.getAddresses() != null && Hibernate.isInitialized(customer.getAddresses())) {
                for (Iterator<CustomerAddress> iteratorGroup = customer.getAddresses().iterator(); iteratorGroup.hasNext();) {
                    CustomerAddress address = (CustomerAddress) iteratorGroup.next();
                    customerViewBean.getAddresses().add(buildViewBeanCustomeAddress(requestData, address));
                }
            }

            customerViewBean.setValidated(customer.isValidated());
            customerViewBean.setActive(customer.isActive());

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
    public CustomerWishlistViewBean buildViewBeanCustomerWishlist(final RequestData requestData, final Customer customer) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final CustomerWishlistViewBean customerWishlistViewBean = new CustomerWishlistViewBean();
        final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
        if (customerMarketArea != null) {
            final Set<CustomerWishlist> customerWishlists = customerMarketArea.getWishlistProducts();
            if (Hibernate.isInitialized(customerWishlists) && customerWishlists != null) {
                for (Iterator<CustomerWishlist> iterator = customerWishlists.iterator(); iterator.hasNext();) {
                    final CustomerWishlist customerWishlist = (CustomerWishlist) iterator.next();
                    final ProductSku productSku = productService.getProductSkuByCode(customerWishlist.getProductSkuCode(), FetchPlanGraphProduct.productSkuDisplayFetchPlan());
                    final ProductMarketing productMarketing =  productService.getProductMarketingByCode(productSku.getProductMarketing().getCode());
                    CatalogCategoryVirtual catalogCategory = null;
                    if(StringUtils.isNotEmpty(customerWishlist.getCatalogCategoryCode())){
                        catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(customerWishlist.getCatalogCategoryCode(), marketArea.getCatalog().getCode());
                    } else {
                        catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductSkuId(productSku.getId());
                    }
                    customerWishlistViewBean.getProductSkus().add(buildViewBeanProductSku(requestData, catalogCategory, productMarketing, productSku));
                }
            }
        }
        return customerWishlistViewBean;
    }

    /**
     * 
     */
    public CustomerAddressListViewBean buildViewBeanCustomerAddressList(final RequestData requestData, final Customer customer) throws Exception {
        final CustomerAddressListViewBean customerAddressListViewBean = new CustomerAddressListViewBean();
        customerAddressListViewBean.setBackUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        final Set<CustomerAddress> addresses = customer.getAddresses();
        if(Hibernate.isInitialized(addresses) && addresses != null){
            for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
                CustomerAddress customerAddress = (CustomerAddress) iterator.next();
                customerAddressListViewBean.getCustomerAddressList().add(buildViewBeanCustomeAddress(requestData, customerAddress));
            }
        }
        return customerAddressListViewBean;
    }

    /**
     * 
     */
    public CustomerAddressViewBean buildViewBeanCustomeAddress(final RequestData requestData, final CustomerAddress customerAddress) throws Exception {
        final Locale locale = requestData.getLocale();
        final CustomerAddressViewBean customerAddressViewBean = new CustomerAddressViewBean();
        if(customerAddress.getId() != null){
            customerAddressViewBean.setId(customerAddress.getId().toString());
        }

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
        customerAddressViewBean.setCountry(coutryLabel);

        customerAddressViewBean.setDefaultBilling(customerAddress.isDefaultBilling());
        customerAddressViewBean.setDefaultShipping(customerAddress.isDefaultShipping());

        Long customerAddressId = customerAddress.getId();
        if(customerAddressId != null){
            Map<String, String> urlParams = new HashMap<String, String>();
            urlParams.put(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ADDRESS_GUID, customerAddressId.toString());
            customerAddressViewBean.setEditUrl(urlService.generateUrl(FoUrls.PERSONAL_EDIT_ADDRESS, requestData, urlParams));
            customerAddressViewBean.setDeleteUrl(urlService.generateUrl(FoUrls.PERSONAL_DELETE_ADDRESS, requestData, urlParams));
        }

        return customerAddressViewBean;
    }

    /**
     * 
     */
    public ProductBrandViewBean buildViewBeanProductBrand(final RequestData requestData, final ProductBrand productBrand) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final ProductBrandViewBean productBrandViewBean = new ProductBrandViewBean();
        productBrandViewBean.setCode(productBrand.getCode());
        productBrandViewBean.setName(productBrand.getName());
        productBrandViewBean.setDescription(productBrand.getDescription());
        
        productBrandViewBean.setI18nName(productBrand.getI18nName(localizationCode));
        productBrandViewBean.setI18nLongDescription(productBrand.getI18nLongDescription(localizationCode));
        productBrandViewBean.setI18nShortDescription(productBrand.getI18nShortDescription(localizationCode));
        productBrandViewBean.setOriginCountryCode(productBrand.getOriginCountryCode());
        
        // ATTRIBUTES
        if (Hibernate.isInitialized(productBrand.getAttributes()) && productBrand.getAttributes() != null) {
            List<ProductBrandAttribute> globalAttributes = productBrand.getGlobalAttributes();
            if(globalAttributes != null){
                for (Iterator<ProductBrandAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                    ProductBrandAttribute attribute = (ProductBrandAttribute) iterator.next();
                    productBrandViewBean.getGlobalAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
                }
            }

            List<ProductBrandAttribute> marketAreaAttributes = productBrand.getMarketAreaAttributes(marketArea.getId());
            if(marketAreaAttributes != null){
                for (Iterator<ProductBrandAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                    ProductBrandAttribute attribute = (ProductBrandAttribute) iterator.next();
                    productBrandViewBean.getMarketAreaAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
                }
            }
        }
        
        // ASSETS
        if (Hibernate.isInitialized(productBrand.getAssets()) && productBrand.getAssets() != null) {
            for (Iterator<Asset> iterator = productBrand.getAssets().iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
                final String path = engineSettingService.getProductBrandImageWebPath(asset);
                assetViewBean.setRelativeWebPath(path);
                assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                productBrandViewBean.getAssets().add(assetViewBean);
            }
        } 
        // FALLBACK ASSET
        Asset asset = new Asset();
        asset.setType(Asset.ASSET_TYPE_DEFAULT);
        asset.setPath("default-brand.png");
        AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
        final String path = engineSettingService.getProductBrandImageWebPath(asset);
        assetViewBean.setRelativeWebPath(path);
        assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
        productBrandViewBean.getAssets().add(assetViewBean);
        
        // TAGS
        Set<ProductBrandTag> tags = productBrand.getTags();
        if (Hibernate.isInitialized(tags) &&
                tags != null) {
            for (Iterator<ProductBrandTag> iterator = tags.iterator(); iterator.hasNext();) {
                ProductBrandTag productBrandTag = (ProductBrandTag) iterator.next();
                ProductBrandTagViewBean productBrandTagViewBean = new ProductBrandTagViewBean();
                productBrandTagViewBean.setCode(productBrandTag.getCode());
                productBrandTagViewBean.setName(productBrandTag.getName());
                productBrandTagViewBean.setDescription(productBrandTag.getDescription());
                productBrandViewBean.getTags().add(productBrandTagViewBean);
            }
        }
        
        productBrandViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestData, productBrand));
        return productBrandViewBean;
    }

    /**
     * 
     */
    public ProductBrandViewBean buildViewBeanProductBrand(final RequestData requestData, final ProductBrand productBrand, final List<ProductMarketing> productMarketings) throws Exception {
        final ProductBrandViewBean productBrandViewBean = buildViewBeanProductBrand(requestData, productBrand);
        for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
            final ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            final ProductSku productSku = productMarketing.getDefaultProductSku();
            CatalogCategoryVirtual catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductSkuId(productSku.getId());
            productBrandViewBean.getProductMarketings().add(buildViewBeanProductMarketing(requestData, catalogCategory, productMarketing, productSku));
        }
        return productBrandViewBean;
    }

    /**
     * 
     */
    public List<ProductBrandCustomerCommentViewBean> buildListViewBeanProductBrandCustomerComments(final RequestData requestData, final List<ProductBrandCustomerComment> customerComments) throws Exception {
        final List<ProductBrandCustomerCommentViewBean> customerCommentViewBeans = new ArrayList<ProductBrandCustomerCommentViewBean>();
        for (Iterator<ProductBrandCustomerComment> iterator = customerComments.iterator(); iterator.hasNext();) {
            ProductBrandCustomerComment customerComment = (ProductBrandCustomerComment) iterator.next();
            customerCommentViewBeans.add(buildViewBeanProductBrandCustomerComment(requestData, customerComment.getProductBrand(), customerComment));
        }
        return customerCommentViewBeans;
    }
    
    /**
     * 
     */
    public ProductBrandCustomerCommentViewBean buildViewBeanProductBrandCustomerComment(final RequestData requestData, final ProductBrand productBrand, final ProductBrandCustomerComment customerComment) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final ProductBrandCustomerCommentViewBean customerCommentViewBean = new ProductBrandCustomerCommentViewBean();
        customerCommentViewBean.setTitle(customerComment.getTitle());
        customerCommentViewBean.setComment(customerComment.getComment());

        if (customerComment.getCustomer() != null) {
            customerCommentViewBean.setCustomerDisplayName(customerComment.getCustomer().getScreenName());
            customerCommentViewBean.setCustomerUrl(urlService.buildCustomerDetailsUrl(requestData, customerComment.getCustomer().getPermalink()));
            customerCommentViewBean.setCustomerAvatarImg(requestUtil.getCustomerAvatar(requestData.getRequest(), customerComment.getCustomer()));
        }
        
        customerCommentViewBean.setComment(customerComment.getComment());
        
        if (customerComment.getDateCreate() != null) {
            customerCommentViewBean.setDateCreate(buildCommonFormatDate(requestData, customerComment.getDateCreate()));
        }
        if (customerComment.getDateUpdate() != null) {
            customerCommentViewBean.setDateUpdate(buildCommonFormatDate(requestData, customerComment.getDateUpdate()));
        }
        
        ReviewDataVocabularyPojo reviewDataVocabulary = new ReviewDataVocabularyPojo();
        reviewDataVocabulary.setItemreviewed(productBrand.getI18nName(localizationCode));
        if (customerComment.getCustomer() != null) {
            reviewDataVocabulary.setReviewer(customerComment.getCustomer().getScreenName());
        }
        DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        reviewDataVocabulary.setDtreviewed(dateFormat.format(customerComment.getDateCreate()));
        // reviewDataVocabulary.setSummary(summary);
        reviewDataVocabulary.setDescription(customerComment.getComment());
        // reviewDataVocabulary.setRating(rating);

        customerCommentViewBean.setReviewDataVocabulary(reviewDataVocabulary);

        return customerCommentViewBean;
    }
    
    /**
     * 
     */
    public List<CatalogCategoryViewBean> buildListViewBeanMasterCatalogCategory(final RequestData requestData, final List<CatalogCategoryMaster> catalogCategories) throws Exception {
        final List<CatalogCategoryViewBean> catalogCategoryViewBeans = new ArrayList<CatalogCategoryViewBean>();
        for (Iterator<CatalogCategoryMaster> iterator = catalogCategories.iterator(); iterator.hasNext();) {
            CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) iterator.next();
            catalogCategoryViewBeans.add(buildViewBeanMasterCatalogCategory(requestData, catalogCategory));
        }
        
        return catalogCategoryViewBeans;
    }
    
    /**
     * 
     */
    public CatalogCategoryViewBean buildViewBeanMasterCatalogCategory(final RequestData requestData, final CatalogCategoryMaster catalogCategory) throws Exception {
        final CatalogCategoryViewBean catalogCategoryViewBean = buildViewBeanCatalogCategory(requestData, (AbstractCatalogCategory) catalogCategory);
        
        // PARENT CATEGORY
        if (catalogCategory != null && catalogCategory.getParentCatalogCategory() != null
                && Hibernate.isInitialized(catalogCategory.getParentCatalogCategory())) {
            catalogCategoryViewBean.setDefaultParentCategory(buildViewBeanCatalogCategory(requestData, (AbstractCatalogCategory) catalogCategory.getParentCatalogCategory()));
        }
       
//        // SUB CATEGORIES
//        List<CatalogCategoryViewBean> subcatalogCategoryVirtualViewBeans = new ArrayList<CatalogCategoryViewBean>();
//        final List<CatalogCategoryMaster> subCategories = catalogCategory.getSortedChildCatalogCategories();
//        if (subCategories != null) {
//            for (Iterator<CatalogCategoryMaster> iteratorSubcatalogCategoryVirtual = subCategories.iterator(); iteratorSubcatalogCategoryVirtual.hasNext();) {
//                final AbstractCatalogCategory subcatalogCategoryVirtual = (AbstractCatalogCategory) iteratorSubcatalogCategoryVirtual.next();
//                subcatalogCategoryVirtualViewBeans.add(buildViewBeanCatalogCategory(requestData, subcatalogCategoryVirtual));
//            }
//            catalogCategoryViewBean.setCountSubCategories(subCategories.size());
//        }
//        catalogCategoryViewBean.setSubCategories(subcatalogCategoryVirtualViewBeans);
//        
//        // FEATURED PRODUCTS
//        for (CatalogCategoryViewBean subcatalogCategoryVirtualViewBean : subcatalogCategoryVirtualViewBeans) {
//            catalogCategoryViewBean.getFeaturedProductMarketings().addAll(subcatalogCategoryVirtualViewBean.getFeaturedProductMarketings());
//        }
        
        return catalogCategoryViewBean;
    }
    
    /**
     * 
     */
    public List<CatalogCategoryViewBean> buildListViewBeanVirtualCatalogCategory(final RequestData requestData, final List<CatalogCategoryVirtual> catalogCategories) throws Exception {
        final List<CatalogCategoryViewBean> catalogCategoryViewBeans = new ArrayList<CatalogCategoryViewBean>();
        for (Iterator<CatalogCategoryVirtual> iterator = catalogCategories.iterator(); iterator.hasNext();) {
            CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) iterator.next();
            catalogCategoryViewBeans.add(buildViewBeanVirtualCatalogCategory(requestData, catalogCategory));
        }
        return catalogCategoryViewBeans;
    }
    
    /**
     * 
     */
    public CatalogCategoryViewBean buildViewBeanVirtualCatalogCategory(final RequestData requestData, final CatalogCategoryVirtual catalogCategory) throws Exception {
        final CatalogCategoryViewBean catalogCategoryViewBean = buildViewBeanCatalogCategory(requestData, (AbstractCatalogCategory) catalogCategory);
        if (catalogCategory != null && catalogCategory.getCategoryMaster() != null
                && Hibernate.isInitialized(catalogCategory.getCategoryMaster())) {
            catalogCategoryViewBean.setMasterCategory(buildViewBeanCatalogCategory(requestData, (AbstractCatalogCategory) catalogCategory.getCategoryMaster()));
        }
        
        // PARENT CATEGORY
        if (catalogCategory != null && catalogCategory.getParentCatalogCategory() != null
                && Hibernate.isInitialized(catalogCategory.getParentCatalogCategory())) {
            catalogCategoryViewBean.setDefaultParentCategory(buildViewBeanCatalogCategory(requestData, (AbstractCatalogCategory) catalogCategory.getParentCatalogCategory()));
        }
       
//        // SUB CATEGORIES
//        List<CatalogCategoryViewBean> subcatalogCategoryVirtualViewBeans = new ArrayList<CatalogCategoryViewBean>();
//        final List<CatalogCategoryVirtual> subCategories = catalogCategory.getSortedChildCatalogCategories();
//        if (subCategories != null) {
//            for (Iterator<CatalogCategoryVirtual> iteratorSubcatalogCategoryVirtual = subCategories.iterator(); iteratorSubcatalogCategoryVirtual.hasNext();) {
//                final AbstractCatalogCategory subcatalogCategoryVirtual = (AbstractCatalogCategory) iteratorSubcatalogCategoryVirtual.next();
//                subcatalogCategoryVirtualViewBeans.add(buildViewBeanCatalogCategory(requestData, subcatalogCategoryVirtual));
//            }
//            catalogCategoryViewBean.setCountSubCategories(subCategories.size());
//        }
//        catalogCategoryViewBean.setSubCategories(subcatalogCategoryVirtualViewBeans);
//        
//        // FEATURED PRODUCTS
//        for (CatalogCategoryViewBean subcatalogCategoryVirtualViewBean : subcatalogCategoryVirtualViewBeans) {
//            catalogCategoryViewBean.getFeaturedProductMarketings().addAll(subcatalogCategoryVirtualViewBean.getFeaturedProductMarketings());
//        }
        
        return catalogCategoryViewBean;
    }
    
    /**
     * 
     */
    protected CatalogCategoryViewBean buildViewBeanCatalogCategory(final RequestData requestData, final AbstractCatalogCategory catalogCategory) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final CatalogCategoryViewBean catalogCategoryViewBean = new CatalogCategoryViewBean();
        
        if(catalogCategory != null){
            if(catalogCategory.getId() != null){
                catalogCategoryViewBean.setId(catalogCategory.getId().toString());
            }
            catalogCategoryViewBean.setCode(catalogCategory.getCode());
            catalogCategoryViewBean.setName(catalogCategory.getName());
            catalogCategoryViewBean.setDescription(catalogCategory.getDescription());
            
            catalogCategoryViewBean.setI18nName(catalogCategory.getI18nName(localizationCode));
            catalogCategoryViewBean.setI18nDescription(catalogCategory.getI18nDescription(localizationCode));
            
            catalogCategoryViewBean.setRanking("" + catalogCategory.getRanking());
            catalogCategoryViewBean.setRoot(catalogCategory.isRoot());

            // ATTRIBUTES
            if (Hibernate.isInitialized(catalogCategory.getAttributes()) && catalogCategory.getAttributes() != null) {
                List<AbstractAttribute> globalAttributes = catalogCategory.getGlobalAttributes();
                if(globalAttributes != null){
                    for (Iterator<AbstractAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                        AbstractAttribute attribute = (AbstractAttribute) iterator.next();
                        catalogCategoryViewBean.getGlobalAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
                    }
                }

                List<AbstractAttribute> marketAreaAttributes = catalogCategory.getMarketAreaAttributes(marketArea.getId());
                if(marketAreaAttributes != null){
                    for (Iterator<AbstractAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                        AbstractAttribute attribute = (AbstractAttribute) iterator.next();
                        catalogCategoryViewBean.getMarketAreaAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
                    }
                }
            }
            
            // ASSETS
            if (Hibernate.isInitialized(catalogCategory.getAssets()) && catalogCategory.getAssets() != null) {
                for (Iterator<Asset> iterator = catalogCategory.getAssets().iterator(); iterator.hasNext();) {
                    Asset asset = (Asset) iterator.next();
                    AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
                    final String path = engineSettingService.getCatalogImageWebPath(asset);
                    assetViewBean.setRelativeWebPath(path);
                    assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                    catalogCategoryViewBean.getAssets().add(assetViewBean);
                }
            } 
            // FALLBACK ASSET
            Asset asset = new Asset();
            asset.setType(Asset.ASSET_TYPE_DEFAULT);
            asset.setPath("default-category.png");
            AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
            final String path = engineSettingService.getCatalogImageWebPath(asset);
            assetViewBean.setRelativeWebPath(path);
            assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
            catalogCategoryViewBean.getAssets().add(assetViewBean);

            if (catalogCategory.isRoot()) {
                catalogCategoryViewBean.setProductAxeUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_AXE, requestData, catalogCategory));
            } else {
                catalogCategoryViewBean.setProductLineUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, catalogCategory));
            }

            // SUB CATEGORIES
            List<CatalogCategoryViewBean> subcatalogCategoryVirtualViewBeans = new ArrayList<CatalogCategoryViewBean>();
            final List<AbstractCatalogCategory> subCategories = catalogCategory.getSortedChildCatalogCategories();
            if (subCategories != null) {
                for (Iterator<AbstractCatalogCategory> iteratorSubcatalogCategoryVirtual = subCategories.iterator(); iteratorSubcatalogCategoryVirtual.hasNext();) {
                    final AbstractCatalogCategory subcatalogCategoryVirtual = (AbstractCatalogCategory) iteratorSubcatalogCategoryVirtual.next();
                    subcatalogCategoryVirtualViewBeans.add(buildViewBeanCatalogCategory(requestData, subcatalogCategoryVirtual));
                }
            }
            catalogCategoryViewBean.setSubCategories(subcatalogCategoryVirtualViewBeans);
            
            // PRODUCTS
            final List<ProductSku> productSkus = catalogCategory.getSortedProductSkus();
            if (productSkus != null) {
                for (Iterator<ProductSku> iteratorProductMarketing = productSkus.iterator(); iteratorProductMarketing.hasNext();) {
                    final ProductSku productSku = (ProductSku) iteratorProductMarketing.next();
                    final ProductSku reloadedProductSku = productService.getProductSkuByCode(productSku.getCode());
                    final ProductMarketing productMarketing = productService.getProductMarketingByCode(reloadedProductSku.getProductMarketing().getCode());
                    ProductMarketingViewBean productMarketingViewBean = buildViewBeanProductMarketing(requestData, catalogCategory, productMarketing, reloadedProductSku);
                    catalogCategoryViewBean.getProductMarketings().add(productMarketingViewBean);
                    if (productMarketing.isFeatured()) {
                        catalogCategoryViewBean.getFeaturedProductMarketings().add(productMarketingViewBean);
                    }
                }
            }
        }

        return catalogCategoryViewBean;
    }
    
    /**
     * 
     */
    public List<ProductMarketingViewBean> buildListViewBeanProductMarketing(final RequestData requestData, final List<ProductMarketing> productMarketings) throws Exception {
        final List<ProductMarketingViewBean> productMarketingViewBeans = new ArrayList<ProductMarketingViewBean>();
        for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            productMarketingViewBeans.add(buildViewBeanProductMarketing(requestData, productMarketing));
        }
        return productMarketingViewBeans;
    }
    
    /**
     * 
     */
    public ProductMarketingViewBean buildViewBeanProductMarketing(final RequestData requestData, final AbstractCatalogCategory catalogCategory, final ProductMarketing productMarketing, final ProductSku productSku) throws Exception {
        final ProductMarketingViewBean productMarketingViewBean = buildViewBeanProductMarketing(requestData, productMarketing);

        if(productSku != null){
            productMarketingViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku));
        } else {
            productMarketingViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));
        }

        final Set<ProductSku> skus = productMarketing.getProductSkus();
        if (Hibernate.isInitialized(skus) && skus != null) {
            for (Iterator<ProductSku> iterator = skus.iterator(); iterator.hasNext();) {
                final ProductSku productSkuIt = (ProductSku) iterator.next();
                final ProductSku reloadedProductSku = productService.getProductSkuByCode(productSkuIt.getCode());
                productMarketingViewBean.getProductSkus().add(buildViewBeanProductSku(requestData, catalogCategory, productMarketing, reloadedProductSku));
            }
        }

        final Set<ProductAssociationLink> productAssociationLinks = productMarketing.getProductAssociationLinks();
        if (Hibernate.isInitialized(productAssociationLinks) && productAssociationLinks != null) {
            for (Iterator<ProductAssociationLink> iterator = productAssociationLinks.iterator(); iterator.hasNext();) {
                final ProductAssociationLink productAssociationLink = (ProductAssociationLink) iterator.next();
                if (productAssociationLink.getType().equals(ProductAssociationLinkType.CROSS_SELLING)) {
                    final ProductMarketing reloadedAssociatedProductMarketing = productService.getProductMarketingByCode(productAssociationLink.getProductSku().getProductMarketing().getCode());
                    productMarketingViewBean.getProductAssociationLinks().add(buildViewBeanProductAssociationLink(requestData, catalogCategory, reloadedAssociatedProductMarketing));
                }
            }
        }
        
        return productMarketingViewBean;
    }

    /**
     * 
     */
    public ProductMarketingViewBean buildViewBeanProductMarketing(final RequestData requestData, final ProductMarketing productMarketing) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final ProductMarketingViewBean productMarketingViewBean = new ProductMarketingViewBean();

        productMarketingViewBean.setCode(productMarketing.getCode());
        productMarketingViewBean.setName(productMarketing.getName());
        productMarketingViewBean.setDescription(productMarketing.getDescription());
        
        productMarketingViewBean.setI18nName(productMarketing.getI18nName(localizationCode));
        productMarketingViewBean.setI18nDescription(productMarketing.getI18nDescription(localizationCode));

        productMarketingViewBean.setDefault(productMarketing.isDefault());

        if (productMarketing.getDateCreate() != null) {
            productMarketingViewBean.setDateCreate(buildCommonFormatDate(requestData, productMarketing.getDateCreate()));
        }
        if (productMarketing.getDateUpdate() != null) {
            productMarketingViewBean.setDateUpdate(buildCommonFormatDate(requestData, productMarketing.getDateUpdate()));
        }

        // ATTRIBUTES
        List<ProductMarketingAttribute> globalAttributes = productMarketing.getGlobalAttributes();
        if(globalAttributes != null){
            for (Iterator<ProductMarketingAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                ProductMarketingAttribute attribute = (ProductMarketingAttribute) iterator.next();
                productMarketingViewBean.getGlobalAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
            }
        }

        List<ProductMarketingAttribute> marketAreaAttributes = productMarketing.getMarketAreaAttributes(marketArea.getId());
        if(marketAreaAttributes != null){
            for (Iterator<ProductMarketingAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                ProductMarketingAttribute attribute = (ProductMarketingAttribute) iterator.next();
                productMarketingViewBean.getMarketAreaAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
            }
        }
        
        // BRAND
        final ProductBrand productBrand = productMarketing.getProductBrand();
        if (Hibernate.isInitialized(productBrand) && productBrand != null) {
            productMarketingViewBean.setBrand(buildViewBeanProductBrand(requestData, productBrand));
            productMarketingViewBean.getBrand().setDetailsUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestData, productBrand));
            productMarketingViewBean.getBrand().setProductLineUrl(urlService.generateUrl(FoUrls.BRAND_LINE, requestData, productBrand));
        }
        
        // ASSETS
        if (Hibernate.isInitialized(productMarketing.getAssets()) && productMarketing.getAssets() != null) {
            for (Iterator<Asset> iterator = productMarketing.getAssets().iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
                final String path = engineSettingService.getProductMarketingImageWebPath(asset);
                assetViewBean.setRelativeWebPath(path);
                assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                productMarketingViewBean.getAssets().add(assetViewBean);
            }
        } 
        
        // FALLBACK ASSET
        Asset asset = new Asset();
        asset.setType(Asset.ASSET_TYPE_DEFAULT);
        asset.setPath("default-product.png");
        AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
        final String path = engineSettingService.getProductMarketingImageWebPath(asset);
        assetViewBean.setRelativeWebPath(path);
        assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
        productMarketingViewBean.getAssets().add(assetViewBean);

        // TAGS
        Set<ProductMarketingTag> tags = productMarketing.getTags();
        if (Hibernate.isInitialized(tags) &&
                tags != null) {
            for (Iterator<ProductMarketingTag> iterator = tags.iterator(); iterator.hasNext();) {
                ProductMarketingTag productMarketingTag = (ProductMarketingTag) iterator.next();
                ProductMarketingTagViewBean productMarketingTagViewBean = new ProductMarketingTagViewBean();
                productMarketingTagViewBean.setCode(productMarketingTag.getCode());
                productMarketingTagViewBean.setName(productMarketingTag.getName());
                productMarketingTagViewBean.setDescription(productMarketingTag.getDescription());
                productMarketingViewBean.getTags().add(productMarketingTagViewBean);
            }
        }
        
        // SKUS
        if (Hibernate.isInitialized(productMarketing.getProductSkus()) && productMarketing.getProductSkus() != null) {
            for (Iterator<ProductSku> iterator = productMarketing.getProductSkus().iterator(); iterator.hasNext();) {
                ProductSku productSku = (ProductSku) iterator.next();
                ProductSkuViewBean productSkuViewBean = buildViewBeanProductSku(requestData, productMarketingViewBean, productSku);
                productMarketingViewBean.getProductSkus().add(productSkuViewBean);
            }
        } 
        
        ProductSku productSku = productMarketing.getDefaultProductSku();
        if(productSku != null){
            CatalogCategoryVirtual catalogCategory = productSku.getDefaultCatalogCategoryVirtual(marketArea.getCatalog());
            productMarketingViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku));
        }
        
        productMarketingViewBean.setFeatured(productMarketing.isFeatured());
        
        return productMarketingViewBean;
    }
    
    /**
     * 
     */
    public List<ProductMarketingCustomerCommentViewBean> buildListViewBeanProductMarketingCustomerComments(final RequestData requestData, final List<ProductMarketingCustomerComment> customerComments) throws Exception {
        final List<ProductMarketingCustomerCommentViewBean> customerCommentViewBeans = new ArrayList<ProductMarketingCustomerCommentViewBean>();
        for (Iterator<ProductMarketingCustomerComment> iterator = customerComments.iterator(); iterator.hasNext();) {
            ProductMarketingCustomerComment customerComment = (ProductMarketingCustomerComment) iterator.next();
            customerCommentViewBeans.add(buildViewBeanProductMarketingCustomerComment(requestData, customerComment.getProductMarketing(), customerComment));
        }
        return customerCommentViewBeans;
    }
    
    /**
     * 
     */
    public ProductMarketingCustomerCommentViewBean buildViewBeanProductMarketingCustomerComment(final RequestData requestData, final ProductMarketing productMarketing, final ProductMarketingCustomerComment customerComment) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final ProductMarketingCustomerCommentViewBean customerCommentViewBean = new ProductMarketingCustomerCommentViewBean();
        customerCommentViewBean.setTitle(customerComment.getTitle());
        customerCommentViewBean.setComment(customerComment.getComment());

        if (customerComment.getCustomer() != null) {
            customerCommentViewBean.setCustomerDisplayName(customerComment.getCustomer().getScreenName());
            customerCommentViewBean.setCustomerUrl(urlService.buildCustomerDetailsUrl(requestData, customerComment.getCustomer().getPermalink()));
            customerCommentViewBean.setCustomerAvatarImg(requestUtil.getCustomerAvatar(requestData.getRequest(), customerComment.getCustomer()));
        }
        
        customerCommentViewBean.setComment(customerComment.getComment());
        
        if (customerComment.getDateCreate() != null) {
            customerCommentViewBean.setDateCreate(buildCommonFormatDate(requestData, customerComment.getDateCreate()));
        }
        if (customerComment.getDateUpdate() != null) {
            customerCommentViewBean.setDateUpdate(buildCommonFormatDate(requestData, customerComment.getDateUpdate()));
        }
        
        ReviewDataVocabularyPojo reviewDataVocabulary = new ReviewDataVocabularyPojo();
        reviewDataVocabulary.setItemreviewed(productMarketing.getI18nName(localizationCode));
        if (customerComment.getCustomer() != null) {
            reviewDataVocabulary.setReviewer(customerComment.getCustomer().getScreenName());
        }
        DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        reviewDataVocabulary.setDtreviewed(dateFormat.format(customerComment.getDateCreate()));
        // reviewDataVocabulary.setSummary(summary);
        reviewDataVocabulary.setDescription(customerComment.getComment());
        // reviewDataVocabulary.setRating(rating);

        customerCommentViewBean.setReviewDataVocabulary(reviewDataVocabulary);

        return customerCommentViewBean;
    }
    
    public CustomerProductRatesViewBean getProductMarketingCustomerRateDetails(final Long productMarketingId, Object... params){
        List<ProductMarketingCustomerRate> qualityRates = productService.findProductMarketingCustomerRatesByProductMarketingId(productMarketingId, Constants.PRODUCT_QUALITY_RATING_TYPE);
        List<ProductMarketingCustomerRate> priceRates = productService.findProductMarketingCustomerRatesByProductMarketingId(productMarketingId, Constants.PRODUCT_PRICE_RATING_TYPE);
        List<ProductMarketingCustomerRate> valueRates = productService.findProductMarketingCustomerRatesByProductMarketingId(productMarketingId, Constants.PRODUCT_VALUE_RATING_TYPE);
        
        Float avgQualityRates = 0F;
        Float avgPriceRates = 0F;
        Float avgValueRates = 0F;
        Float avgRate = 0F;
        
        for (ProductMarketingCustomerRate productMarketingCustomerRate : qualityRates) {
            avgQualityRates += productMarketingCustomerRate.getRate();          
        }
        
        for (ProductMarketingCustomerRate productMarketingCustomerRate : priceRates) {
            avgPriceRates += productMarketingCustomerRate.getRate();
        }
        
        for (ProductMarketingCustomerRate productMarketingCustomerRate : valueRates) {
            avgValueRates += productMarketingCustomerRate.getRate();
        }
        
        if(qualityRates.size() > 0){
            avgQualityRates = avgQualityRates/qualityRates.size();
        }
        
        if(priceRates.size() > 0){
            avgPriceRates = avgPriceRates/priceRates.size();
        }
        
        if(valueRates.size() > 0){
            avgValueRates = avgValueRates/valueRates.size();
        }
        
        avgRate = (avgQualityRates + avgPriceRates + avgValueRates) / 3;
        
        CustomerProductRatesViewBean customerProductRatesViewBean = new CustomerProductRatesViewBean();
        customerProductRatesViewBean.setAvgPriceRates(avgPriceRates);
        customerProductRatesViewBean.setAvgQualityRates(avgQualityRates);
        customerProductRatesViewBean.setAvgValueRates(avgValueRates);
        
        customerProductRatesViewBean.setPriceRateCount(priceRates.size());
        customerProductRatesViewBean.setQualityRateCount(qualityRates.size());
        customerProductRatesViewBean.setValueRateCount(valueRates.size());
        customerProductRatesViewBean.setAvgRate(avgRate);
        
        return customerProductRatesViewBean;
    }
    
    public CustomerProductRatesViewBean calculateProductMarketingCustomerRatesByProductId(final Long productMarketingId) {
        Float avgRate = productService.calculateProductMarketingCustomerRatesByProductMarketingId(productMarketingId);
        CustomerProductRatesViewBean customerProductRatesViewBean = new CustomerProductRatesViewBean();
        customerProductRatesViewBean.setAvgRate(avgRate);
        return customerProductRatesViewBean;
    }
    
    /**
     * 
     */
    public AssetViewBean buildViewBeanAsset(final RequestData requestData, final Asset asset) throws Exception {
        final AssetViewBean assetViewBean = new AssetViewBean();
        assetViewBean.setName(asset.getName());
        assetViewBean.setDescription(asset.getDescription());
        assetViewBean.setScope(asset.getScope());
        assetViewBean.setType(asset.getType());
        assetViewBean.setPath(asset.getPath());
        assetViewBean.setSize(asset.getSize());
        if(asset.getFileSize() != null){
            assetViewBean.setFileSize(asset.getFileSize().toString());
        }
        assetViewBean.setIsDefault(asset.isDefault());

        return assetViewBean;
    }
    
    /**
     * 
     */
    public ProductSkuViewBean buildViewBeanProductSku(final RequestData requestData, final AbstractCatalogCategory catalogCategory, 
                                                      final ProductMarketing productMarketing, final ProductSku productSku) throws Exception {
        final ProductSkuViewBean productSkuViewBean = buildViewBeanProductSku(requestData, productSku);

        if(catalogCategory != null){
            productSkuViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku));

            Map<String, String> getParams = new HashMap<String, String>();
            getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE, productSku.getCode());
            getParams.put(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE, catalogCategory.getCode());

            productSkuViewBean.setAddToCartUrl(urlService.generateUrl(FoUrls.CART_ADD_ITEM, requestData, getParams));
            productSkuViewBean.setRemoveFromCartUrl(urlService.generateUrl(FoUrls.CART_REMOVE_ITEM, requestData, getParams));

            productSkuViewBean.setAddToWishlistUrl(urlService.generateUrl(FoUrls.WISHLIST_ADD_PRODUCT, requestData, getParams));
            productSkuViewBean.setRemoveFromWishlistUrl(urlService.generateUrl(FoUrls.WISHLIST_REMOVE_ITEM, requestData, getParams));
        }

        return productSkuViewBean;
    }
    
    /**
     * 
     */
    public List<ProductSkuViewBean> buildListViewBeanProductSku(final RequestData requestData, final List<ProductSku> productSkus) throws Exception {
        final List<ProductSkuViewBean> productSkuViewBeans = new ArrayList<ProductSkuViewBean>();
        for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
            ProductSku productSku = (ProductSku) iterator.next();
            productSkuViewBeans.add(buildViewBeanProductSku(requestData, productSku));
        }
        return productSkuViewBeans;
    }
    
    /**
     * 
     */
    public ProductSkuViewBean buildViewBeanProductSku(final RequestData requestData, final ProductMarketingViewBean productMarketingViewBean, final ProductSku productSku) throws Exception {
        final ProductSkuViewBean productSkuViewBean = buildViewBeanProductSku(requestData, productSku);

        // PRODUCT MARKETING
        productSkuViewBean.setProductMarketing(productMarketingViewBean);
        
        return productSkuViewBean;
    }

    /**
     * 
     */
    public ProductSkuViewBean buildViewBeanProductSku(final RequestData requestData, final ProductSku productSku) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        
        final ProductMarketing productMarketing = productSku.getProductMarketing();
        
        final ProductSkuViewBean productSkuViewBean = new ProductSkuViewBean();

        productSkuViewBean.setCode(productSku.getCode());
        productSkuViewBean.setName(productSku.getName());
        productSkuViewBean.setDescription(productSku.getDescription());
        
        productSkuViewBean.setI18nName(productSku.getI18nName(localizationCode));
        productSkuViewBean.setI18nDescription(productSku.getI18nDescription(localizationCode));

        productSkuViewBean.setDefault(productSku.isDefault());
        productSkuViewBean.setSalable(productSku.isSalable(marketArea.getId()));

        if (productMarketing.getDateCreate() != null) {
            productSkuViewBean.setDateCreate(buildCommonFormatDate(requestData, productMarketing.getDateCreate()));
        }
        if (productMarketing.getDateUpdate() != null) {
            productSkuViewBean.setDateUpdate(buildCommonFormatDate(requestData, productMarketing.getDateUpdate()));
        }
        
        final ProductSkuStorePrice productSkuBestPrice = productSku.getBestPrice(marketArea.getId());
        if(productSkuBestPrice != null){
            productSkuViewBean.setBestPriceWithCurrencySign(productSkuBestPrice.getPriceWithStandardCurrencySign());
        } else {
            productSkuViewBean.setBestPriceWithCurrencySign("NA");
        }
        final ProductSkuStorePrice productSkuSalePrice = productSku.getPublicPrice(marketArea.getId());
        if(productSkuSalePrice != null){
            productSkuViewBean.setPriceWithCurrencySign(productSkuSalePrice.getPriceWithStandardCurrencySign());
        } else {
            productSkuViewBean.setPriceWithCurrencySign("NA");
        }
        
        // BRAND
        if (Hibernate.isInitialized(productSku.getProductMarketing()) && productSku.getProductMarketing() != null) {
            final ProductBrand productBrand = productSku.getProductMarketing().getProductBrand();
            if (Hibernate.isInitialized(productBrand) && productBrand != null) {
                productSkuViewBean.setBrand(buildViewBeanProductBrand(requestData, productBrand));
                productSkuViewBean.getBrand().setDetailsUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestData, productBrand));
                productSkuViewBean.getBrand().setProductLineUrl(urlService.generateUrl(FoUrls.BRAND_LINE, requestData, productBrand));
            }
        }
        
        // ATTRIBUTES
        List<ProductSkuAttribute> globalAttributes = productSku.getGlobalAttributes();
        if(globalAttributes != null){
            for (Iterator<ProductSkuAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                ProductSkuAttribute attribute = (ProductSkuAttribute) iterator.next();
                productSkuViewBean.getGlobalAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
            }
        }

        List<ProductSkuAttribute> marketAreaAttributes = productSku.getMarketAreaAttributes(marketArea.getId());
        if(marketAreaAttributes != null){
            for (Iterator<ProductSkuAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                ProductSkuAttribute attribute = (ProductSkuAttribute) iterator.next();
                productSkuViewBean.getMarketAreaAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
            }
        }
        
        // ASSETS
        if (Hibernate.isInitialized(productSku.getAssets()) && productSku.getAssets() != null) {
            for (Iterator<Asset> iterator = productSku.getAssets().iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
                final String path = engineSettingService.getProductSkuImageWebPath(asset);
                assetViewBean.setRelativeWebPath(path);
                assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                productSkuViewBean.getAssets().add(assetViewBean);
            }
        }
        // FALLBACK ASSET
        Asset asset = new Asset();
        asset.setType(Asset.ASSET_TYPE_DEFAULT);
        asset.setPath("default-product-sku.png");
        AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
        final String path = engineSettingService.getProductSkuImageWebPath(asset);
        assetViewBean.setRelativeWebPath(path);
        assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
        productSkuViewBean.getAssets().add(assetViewBean);
        
        // TAGS
        Set<ProductSkuTag> tags = productSku.getTags();
        if (Hibernate.isInitialized(tags) 
                && tags != null) {
            for (Iterator<ProductSkuTag> iterator = tags.iterator(); iterator.hasNext();) {
                ProductSkuTag productSkuTag = (ProductSkuTag) iterator.next();
                ProductSkuTagViewBean productSkuTagViewBean = new ProductSkuTagViewBean();
                productSkuTagViewBean.setCode(productSkuTag.getCode());
                productSkuTagViewBean.setName(productSkuTag.getName());
                productSkuTagViewBean.setDescription(productSkuTag.getDescription());
                productSkuViewBean.getTags().add(productSkuTagViewBean);
            }
        }
        
        // SKU OPTIONS
        Set<ProductSkuOptionRel> optionRels = productSku.getOptionRels();
        if (Hibernate.isInitialized(optionRels) 
                && optionRels != null) {
            for (Iterator<ProductSkuOptionRel> iterator = optionRels.iterator(); iterator.hasNext();) {
                ProductSkuOptionRel productSkuOptionRel = (ProductSkuOptionRel) iterator.next();
                if (Hibernate.isInitialized(productSkuOptionRel.getProductSkuOptionDefinition()) 
                        && productSkuOptionRel.getProductSkuOptionDefinition() != null) {
                    ProductSkuOptionDefinition productSkuOptionDefinition = productSkuOptionRel.getProductSkuOptionDefinition();
                    
                    ProductSkuOptionDefinitionViewBean productSkuOptionDefinitionViewBean = new ProductSkuOptionDefinitionViewBean();
                    productSkuOptionDefinitionViewBean.setCode(productSkuOptionDefinition.getCode());
                    productSkuOptionDefinitionViewBean.setName(productSkuOptionDefinition.getName());
                    
                    if (Hibernate.isInitialized(productSkuOptionDefinition.getOptionDefinitionType()) 
                            && productSkuOptionDefinition.getOptionDefinitionType() != null) {
                        ProductSkuOptionDefinitionType productSkuOptionDefinitionType = productSkuOptionDefinition.getOptionDefinitionType();
                        productSkuOptionDefinitionViewBean.setTypeCode(productSkuOptionDefinitionType.getCode());
                        productSkuOptionDefinitionViewBean.setTypeName(productSkuOptionDefinitionType.getName());
                    }
                    
                    productSkuViewBean.getSkuOptionDefinitions().add(productSkuOptionDefinitionViewBean);
                }
            }
        }
        
        // CATALOG CATEGORIES
        Set<CatalogCategoryVirtualProductSkuRel> catalogCategories = productSku.getCatalogCategoryVirtualProductSkuRels();
        if (Hibernate.isInitialized(catalogCategories) 
                && catalogCategories != null) {
            for (Iterator<CatalogCategoryVirtualProductSkuRel> iterator = catalogCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel = (CatalogCategoryVirtualProductSkuRel) iterator.next();
                CatalogCategoryViewBean catalogCategoryViewBean = buildViewBeanCatalogCategory(requestData, (AbstractCatalogCategory) catalogCategoryVirtualProductSkuRel.getCatalogCategoryVirtual());
                productSkuViewBean.getCatalogCategories().add(catalogCategoryViewBean);
            }
        }

        return productSkuViewBean;
    }
    
    /**
     * 
     */
    public ProductAssociationLinkViewBean buildViewBeanProductAssociationLink(final RequestData requestData, final AbstractCatalogCategory catalogCategory, 
                                                                              final ProductMarketing productMarketing) throws Exception {
        final ProductAssociationLinkViewBean productAssociationLinkViewBean = buildViewBeanProductAssociationLink(requestData, productMarketing);

        productAssociationLinkViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));

        return productAssociationLinkViewBean;
    }
    
    /**
     * 
     */
    public ProductAssociationLinkViewBean buildViewBeanProductAssociationLink(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, 
                                                                              final ProductMarketing productMarketing) throws Exception {
        final ProductAssociationLinkViewBean productAssociationLinkViewBean = buildViewBeanProductAssociationLink(requestData, productMarketing);

        productAssociationLinkViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));

        return productAssociationLinkViewBean;
    }
    
    /**
     * 
     */
    public ProductAssociationLinkViewBean buildViewBeanProductAssociationLink(final RequestData requestData, final ProductMarketing productMarketing) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final ProductAssociationLinkViewBean productAssociationLinkViewBean = new ProductAssociationLinkViewBean();

        // TODO : WRONG : CROSS IS SKU not marketing

        productAssociationLinkViewBean.setName(productMarketing.getName());
        productAssociationLinkViewBean.setDescription(productMarketing.getDescription());
        
        productAssociationLinkViewBean.setI18nName(productMarketing.getI18nName(localizationCode));
        productAssociationLinkViewBean.setI18nDescription(productMarketing.getI18nDescription(localizationCode));

        // ASSETS
        if (Hibernate.isInitialized(productMarketing.getAssets()) && productMarketing.getAssets() != null) {
            for (Iterator<Asset> iterator = productMarketing.getAssets().iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                productAssociationLinkViewBean.getAssets().add(buildViewBeanAsset(requestData, asset));
            }
        }
        
        return productAssociationLinkViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public CartViewBean buildViewBeanCart(final RequestData requestData, final Cart cart) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final Locale locale = requestData.getLocale();
        
        List<SpecificFetchMode> productSkuFetchPlans = new ArrayList<SpecificFetchMode>();
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName() + "." + ProductSkuStorePrice_.currency.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.assets.getName()));
        FetchPlan productSkuFetchPlan = new FetchPlan(productSkuFetchPlans);
        
        final CartViewBean cartViewBean = new CartViewBean();
        cartViewBean.setWithPromoCode(true);
        cartViewBean.setWithItemQuantityActions(true);

        cartViewBean.setCartDetailsUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));
        cartViewBean.setCartAuthUrl(urlService.generateUrl(FoUrls.CART_AUTH, requestData));
        cartViewBean.setCartDeliveryAndOrderDetailsUrl(urlService.generateUrl(FoUrls.CART_DELIVERY, requestData));
        cartViewBean.setCartOrderPaymentUrl(urlService.generateUrl(FoUrls.CART_ORDER_PAYMENT, requestData));
        cartViewBean.setCartOrderConfirmationUrl(urlService.generateUrl(FoUrls.CART_ORDER_CONFIRMATION, requestData));

        cartViewBean.setAddNewAddressUrl(urlService.generateUrl(FoUrls.PERSONAL_ADD_ADDRESS, requestData));

        if(cart != null){
            // ITEMS PART
            List<CartItemViewBean> cartItemViewBeans = new ArrayList<CartItemViewBean>();
            Set<CartItem> cartItems = cart.getCartItems();
            for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
                final CartItem cartItem = (CartItem) iterator.next();
                cartItemViewBeans.add(buildViewBeanCartItem(requestData, cartItem, productSkuFetchPlan));
            }
            cartViewBean.setCartItems(cartItemViewBeans);

            // SUB PART : Shippings
            final List<CartDeliveryMethodViewBean> cartDeliveryMethodViewBeans = new ArrayList<CartDeliveryMethodViewBean>();
            final Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
            if (deliveryMethods != null) {
                for (Iterator<DeliveryMethod> iterator = deliveryMethods.iterator(); iterator.hasNext();) {
                    final DeliveryMethod deliveryMethod = (DeliveryMethod) iterator.next();
                    if(deliveryMethod != null){
                        final CartDeliveryMethodViewBean cartDeliveryMethodViewBean = new CartDeliveryMethodViewBean();
                        cartDeliveryMethodViewBean.setLabel(deliveryMethod.getName());
                        cartDeliveryMethodViewBean.setAmountWithCurrencySign(deliveryMethod.getPriceWithStandardCurrencySign(cart.getCurrency().getId()));
                        Object[] params = { deliveryMethod.getName() };
                        cartDeliveryMethodViewBean.setLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.deliveryMethods", params, locale));
                        cartDeliveryMethodViewBeans.add(cartDeliveryMethodViewBean);
                    }
                }
                cartViewBean.setCartDeliveryMethods(cartDeliveryMethodViewBeans);
            }

            // SUB PART : Taxes
//            final List<CartTaxViewBean> cartTaxViewBeans = new ArrayList<CartTaxViewBean>();
//            final Set<Tax> taxes = cart.getTaxes();
//            if (taxes != null) {
//                for (Iterator<Tax> iterator = taxes.iterator(); iterator.hasNext();) {
//                    final Tax tax = (Tax) iterator.next();
//                    final CartTaxViewBean cartTaxViewBean = new CartTaxViewBean();
//                    BigDecimal taxesCalc = cartItemsTotal;
//                    taxesCalc = taxesCalc.multiply(tax.getPercent());
//                    taxesCalc = taxesCalc.divide(new BigDecimal("100"));
//                    cartFeesTotal = cartFeesTotal.add(taxesCalc);
//                    Object[] params = { tax.getName() };
//                    cartTaxViewBean.setCartTaxTotal(formatter.format(taxesCalc));
//                    cartTaxViewBean.setCartTaxTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.taxes", params, locale));
//                    cartTaxViewBeans.add(cartTaxViewBean);
//                }
//                cartViewBean.setCartTaxes(cartTaxViewBeans);
//            }
            
            
            cartViewBean.setCartItemsTotalWithCurrencySign(cart.getCartItemTotalWithStandardCurrencySign());
            cartViewBean.setCartShippingTotalWithCurrencySign(cart.getDeliveryMethodTotalWithStandardCurrencySign());
            cartViewBean.setCartFeesTotalWithCurrencySign(cart.getTaxTotalWithStandardCurrencySign());
            cartViewBean.setCartTotalWithCurrencySign(cart.getCartTotalWithStandardCurrencySign());
        }

        return cartViewBean;
    }

    /**
     * 
     */
    protected CartItemViewBean buildViewBeanCartItem(final RequestData requestData, final CartItem cartItem, final FetchPlan productSkuFetchPlan) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final CartItemViewBean cartItemViewBean = new CartItemViewBean();

        cartItemViewBean.setSkuCode(cartItem.getProductSkuCode());
        final ProductSku productSku = productService.getProductSkuByCode(cartItem.getProductSkuCode(), productSkuFetchPlan);
        cartItem.setProductSku(productSku);
        
        cartItemViewBean.setI18nName(productSku.getI18nName(localizationCode));
        cartItemViewBean.setI18nDescription(productSku.getI18nDescription(localizationCode));
        
        cartItemViewBean.setQuantity(cartItem.getQuantity());

        // ASSETS
        if (Hibernate.isInitialized(productSku.getAssets()) && productSku.getAssets() != null) {
            for (Iterator<Asset> iterator = productSku.getAssets().iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
                final String path = engineSettingService.getProductSkuImageWebPath(asset);
                assetViewBean.setRelativeWebPath(path);
                assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                cartItemViewBean.getAssets().add(assetViewBean);
            }
        } 
        // FALLBACK ASSET
        Asset asset = new Asset();
        asset.setType(Asset.ASSET_TYPE_DEFAULT);
        asset.setPath("default-cart-item.png");
        AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
        final String path = engineSettingService.getProductSkuImageWebPath(asset);
        assetViewBean.setRelativeWebPath(path);
        assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
        cartItemViewBean.getAssets().add(assetViewBean);
        
        // UNIT PRICE
        cartItemViewBean.setUnitPriceWithCurrencySign(cartItem.getPriceWithStandardCurrencySign(marketArea.getId(), retailer.getId()));

        // FEES AMOUNT FOR THIS PRODUCT SKU AND THIS QUANTITY
        
        //...
        
        // TOTAL AMOUNT FOR THIS PRODUCT SKU AND THIS QUANTITY
        cartItemViewBean.setAmountWithCurrencySign(cartItem.getTotalAmountWithStandardCurrencySign(marketArea.getId(), retailer.getId()));

        Map<String, String> getParams = new HashMap<String, String>();
        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE, cartItem.getProductSkuCode());
        cartItemViewBean.setDeleteUrl(urlService.generateUrl(FoUrls.CART_REMOVE_ITEM, requestData, getParams));

        final ProductMarketing productMarketing = productService.getProductMarketingByCode(cartItem.getProductMarketingCode());
        final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(cartItem.getCatalogCategoryCode(), requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode());
        
        cartItemViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, cartItem.getProductSku()));

        return cartItemViewBean;
    }

    /**
     * 
     */
    public List<OrderViewBean> buildListViewBeanOrder(final RequestData requestData, final List<OrderCustomer> orders) throws Exception {
        final List<OrderViewBean> orderViewBeans = new ArrayList<OrderViewBean>();
        for (Iterator<OrderCustomer> iterator = orders.iterator(); iterator.hasNext();) {
            OrderCustomer order = (OrderCustomer) iterator.next();
            orderViewBeans.add(buildViewBeanOrder(requestData, order));
        }
        return orderViewBeans;
    }

    /**
     * 
     */
    public OrderViewBean buildViewBeanOrder(final RequestData requestData, final OrderCustomer order) throws Exception {
        final Locale locale = requestData.getLocale();
        final OrderViewBean orderViewBean = new OrderViewBean();
        orderViewBean.setOrderNum(order.getOrderNum());
        
        if (order != null) {
            if (order.getExpectedDeliveryDate() != null) {
                orderViewBean.setExpectedDeliveryDate(buildCommonFormatDate(requestData, order.getExpectedDeliveryDate()));
            } else {
                orderViewBean.setExpectedDeliveryDate(Constants.NOT_AVAILABLE);
            }
            
            if (order.getDateCreate() != null) {
                orderViewBean.setDateCreate(buildCommonFormatDate(requestData, order.getDateCreate()));
            }
            if (order.getDateUpdate() != null) {
                orderViewBean.setDateUpdate(buildCommonFormatDate(requestData, order.getDateUpdate()));
            }
            
            // ITEMS PART
            final List<OrderItemViewBean> orderItemViewBeans = new ArrayList<OrderItemViewBean>();
            final Set<OrderItem> orderItems = order.getOrderItems();
            for (Iterator<OrderItem> iterator = orderItems.iterator(); iterator.hasNext();) {
                OrderItem orderItem = (OrderItem) iterator.next();
                orderItemViewBeans.add(buildViewBeanOrderItem(requestData, orderItem));
            }
            orderViewBean.setOrderItems(orderItemViewBeans);

            // SUB PART : Shippings
            final List<OrderShippingViewBean> orderShippingViewBeans = new ArrayList<OrderShippingViewBean>();
            final Set<OrderShipment> orderShipments = order.getOrderShipments();
            if (Hibernate.isInitialized(orderShipments) && orderShipments != null) {
                for (Iterator<OrderShipment> iterator = orderShipments.iterator(); iterator.hasNext();) {
                    final OrderShipment orderShipment = (OrderShipment) iterator.next();
                    final OrderShippingViewBean orderShippingViewBean = new OrderShippingViewBean();
                    Object[] params = { orderShipment.getName() };
                    orderShippingViewBean.setOrderShippingTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.deliveryMethods", params, locale));
                    orderShippingViewBeans.add(orderShippingViewBean);
                }
                orderViewBean.setOrderShippings(orderShippingViewBeans);
            }

            // SUB PART : Taxes
            final List<OrderTaxViewBean> orderTaxViewBeans = new ArrayList<OrderTaxViewBean>();
            final Set<OrderTax> orderTaxes = order.getOrderTaxes();
            if (Hibernate.isInitialized(orderTaxes) && orderTaxes != null) {
                for (Iterator<OrderTax> iterator = orderTaxes.iterator(); iterator.hasNext();) {
                    final OrderTax orderTax = (OrderTax) iterator.next();
                    final OrderTaxViewBean orderTaxViewBean = new OrderTaxViewBean();
                    Object[] params = { orderTax.getAmount() };
                    orderTaxViewBean.setOrderTaxTotal(order.getCurrency().formatPriceWithStandardCurrencySign(orderTax.getAmount()));
                    orderTaxViewBean.setOrderTaxTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.taxes", params, locale));
                    orderTaxViewBeans.add(orderTaxViewBean);
                }
                orderViewBean.setOrderTaxes(orderTaxViewBeans);
            }
            orderViewBean.setOrderItemsTotalWithCurrencySign(order.getOrderItemTotalWithStandardCurrencySign());
            orderViewBean.setOrderShippingTotalWithCurrencySign(order.getShippingTotalWithStandardCurrencySign());
            orderViewBean.setOrderTaxesTotalWithCurrencySign(order.getTaxTotalWithStandardCurrencySign());
            orderViewBean.setOrderTotalWithCurrencySign(order.getOrderTotalWithStandardCurrencySign());

            Map<String, String> getParams = new HashMap<String, String>();
            getParams.put(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ORDER_GUID, order.getId().toString());

            orderViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_ORDER_DETAILS, requestData, getParams));
        }

        return orderViewBean;
    }

    /**
     * 
     */
    public OrderItemViewBean buildViewBeanOrderItem(final RequestData requestData, final OrderItem orderItem) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();

        final OrderItemViewBean orderItemViewBean = new OrderItemViewBean();

        orderItemViewBean.setSkuCode(orderItem.getProductSkuCode());
        
        if(StringUtils.isNotEmpty(orderItem.getProductSkuCode())){
            ProductSku productSku = productService.getProductSkuByCode(orderItem.getProductSkuCode());
            orderItemViewBean.setI18nName(productSku.getI18nName(localizationCode));
            orderItemViewBean.setI18nDescription(productSku.getI18nDescription(localizationCode));
        }

        final BigDecimal price = orderItem.getPrice();
        if (price != null) {
            orderItemViewBean.setPrice(orderItem.getCurrency().formatPriceWithStandardCurrencySign(price));
        }

        orderItemViewBean.setQuantity(orderItem.getQuantity());

        final BigDecimal totalAmountOrderItem = orderItem.getTotalAmountOrderItem();
        if (totalAmountOrderItem != null) {
            orderItemViewBean.setAmount(orderItem.getCurrency().formatPriceWithStandardCurrencySign(totalAmountOrderItem));
        }
        
        return orderItemViewBean;
    }
    
    /**
     * @throws Exception
     * 
     */
    public DeliveryMethodViewBean buildViewBeanDeliveryMethod(final RequestData requestData, final DeliveryMethod deliveryMethod) throws Exception {
        final DeliveryMethodViewBean deliveryMethodViewBean = new DeliveryMethodViewBean();
        
        if(deliveryMethod.getId() != null){
            deliveryMethodViewBean.setId(deliveryMethod.getId().toString());
        }

        deliveryMethodViewBean.setVersion(deliveryMethod.getVersion());
        deliveryMethodViewBean.setName(deliveryMethod.getName());
        deliveryMethodViewBean.setDescription(deliveryMethod.getDescription());
        deliveryMethodViewBean.setCode(deliveryMethod.getCode());
        
        final Set<DeliveryMethodPrice> prices = deliveryMethod.getPrices();
        if(Hibernate.isInitialized(prices) && prices != null){
            for (DeliveryMethodPrice deliveryMethodPrice : prices) {
                
                // TODO : denis : fix the context
                
                
//                if(deliveryMethodPrice.getMarketAreaId().equals(marketArea.getId()) && deliveryMethodPrice.getRetailerId().equals(retailer.getId())) {
//                }
                deliveryMethodViewBean.setCatalogPrice(deliveryMethodPrice.getPrice().toString());
                deliveryMethodViewBean.setSalePrice(deliveryMethodPrice.getSalePrice().toString());
                deliveryMethodViewBean.setPriceWithCurrencySign(deliveryMethodPrice.getPriceWithStandardCurrencySign());
                
                deliveryMethodViewBean.setCurrencySign(deliveryMethodPrice.getCurrency().getSign());
                deliveryMethodViewBean.setCurrencyAbbreviated(deliveryMethodPrice.getCurrency().getAbbreviated());
    
                break;

            }
        }

        if (deliveryMethod.getDateCreate() != null) {
            deliveryMethodViewBean.setDateCreate(buildCommonFormatDate(requestData, deliveryMethod.getDateCreate()));
        }
        if (deliveryMethod.getDateUpdate() != null) {
            deliveryMethodViewBean.setDateUpdate(buildCommonFormatDate(requestData, deliveryMethod.getDateUpdate()));
        }

        // TODO : CMS page to describe Delivery Methods
//        deliveryMethodViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.DELIVERY_METHOD_DETAILS, requestData, deliveryMethod));
//        deliveryMethodViewBean.setEditUrl(urlService.generateUrl(FoUrls.DELIVERY_METHOD_EDIT, requestData, deliveryMethod));

        return deliveryMethodViewBean;
    }
    
    /**
     * @throws Exception
     * 
     */
    public TaxViewBean buildViewBeanTax(final RequestData requestData, final Tax tax) throws Exception {
        final TaxViewBean taxViewBean = new TaxViewBean();
        if(tax.getId() != null){
            taxViewBean.setId(tax.getId().toString());
        }

        taxViewBean.setVersion(tax.getVersion());
        taxViewBean.setCode(tax.getCode());
        taxViewBean.setName(tax.getName());
        taxViewBean.setDescription(tax.getDescription());
        taxViewBean.setPercent("" + tax.getPercent());
        
        if (tax.getDateCreate() != null) {
            taxViewBean.setDateCreate(buildCommonFormatDate(requestData, tax.getDateCreate()));
        }
        if (tax.getDateUpdate() != null) {
            taxViewBean.setDateUpdate(buildCommonFormatDate(requestData, tax.getDateUpdate()));
        }

        return taxViewBean;
    }
    
    /**
     * @throws Exception
     * 
     */
    public PaymentMethodViewBean buildViewBeanPaymentMethod(final RequestData requestData, final AbstractPaymentGateway paymentGateway) throws Exception {
        final PaymentMethodViewBean paymentMethodViewBean = new PaymentMethodViewBean();

        paymentMethodViewBean.setCode(paymentGateway.getCode());
        paymentMethodViewBean.setName(paymentGateway.getName());
        paymentMethodViewBean.setDescription(paymentGateway.getDescription());
        
        final Set<PaymentGatewayOption> paymentGatewayOptions = paymentGateway.getOptions();
        if(Hibernate.isInitialized(paymentGatewayOptions) && paymentGatewayOptions != null){
            for (Iterator<PaymentGatewayOption> iterator = paymentGatewayOptions.iterator(); iterator.hasNext();) {
                PaymentGatewayOption paymentGatewayOption = (PaymentGatewayOption) iterator.next();
                paymentMethodViewBean.getPaymentMethodOptions().add(buildViewBeanPaymentMethodOption(requestData, paymentGatewayOption));
            }
        }
        
        return paymentMethodViewBean;
    }
    
    /**
     * @throws Exception
     * 
     */
    public PaymentMethodOptionViewBean buildViewBeanPaymentMethodOption(final RequestData requestData, final PaymentGatewayOption paymentGatewayOption) throws Exception {
        final PaymentMethodOptionViewBean paymentMethodOptionViewBean = new PaymentMethodOptionViewBean();

        paymentMethodOptionViewBean.setCode(paymentGatewayOption.getCode());
        paymentMethodOptionViewBean.setName(paymentGatewayOption.getName());
        paymentMethodOptionViewBean.setDescription(paymentGatewayOption.getDescription());
        
        return paymentMethodOptionViewBean;
    }
    
    /**
     * @throws Exception
     * 
     */
    public UserViewBean buildViewBeanUser(final RequestData requestData, final User user) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final UserViewBean userViewBean = new UserViewBean();
        if(user.getId() != null){
            userViewBean.setId(user.getId().toString());
        }
        userViewBean.setCode(user.getCode());
        userViewBean.setLogin(user.getLogin());
        userViewBean.setFirstname(user.getFirstname());
        userViewBean.setLastname(user.getLastname());
        userViewBean.setEmail(user.getEmail());
        userViewBean.setPassword(user.getPassword());
        userViewBean.setActive(user.isActive());
        userViewBean.setValidated(user.isValidated());
        
        userViewBean.setAddress1(user.getAddress1());
        userViewBean.setAddress2(user.getAddress2());
        userViewBean.setAddressAdditionalInformation(user.getAddressAdditionalInformation());
        userViewBean.setPostalCode(user.getPostalCode());
        userViewBean.setCity(user.getCity());
        userViewBean.setStateCode(user.getStateCode());
        userViewBean.setAreaCode(user.getAreaCode());
        userViewBean.setCountryCode(user.getCountryCode());
        
        if (user.getDateCreate() != null) {
            userViewBean.setDateCreate(buildCommonFormatDate(requestData, user.getDateCreate()));
        }
        if (user.getDateUpdate() != null) {
            userViewBean.setDateUpdate(buildCommonFormatDate(requestData, user.getDateUpdate()));
        }

        if(user.getGroups() != null && Hibernate.isInitialized(user.getGroups())){
            final Set<UserGroup> groups = user.getGroups();
            for (Iterator<UserGroup> iteratorGroup = groups.iterator(); iteratorGroup.hasNext();) {
                UserGroup group = (UserGroup) iteratorGroup.next();
                String keyUserGroup = group.getCode();
                String valueUserGroup = group.getName();
                userViewBean.getGroups().put(keyUserGroup, valueUserGroup);

                if(group.getRoles() != null && Hibernate.isInitialized(group.getRoles())){
                    final Set<UserRole> roles = group.getRoles();
                    for (Iterator<UserRole> iteratorRole = roles.iterator(); iteratorRole.hasNext();) {
                        UserRole role = (UserRole) iteratorRole.next();
                        String keyUserRole = role.getCode();
                        String valueUserRole = role.getName();
                        userViewBean.getRoles().put(keyUserRole, valueUserRole);

                        if(role.getPermissions() != null && Hibernate.isInitialized(role.getPermissions())){
                            final Set<UserPermission> permissions = role.getPermissions();
                            for (Iterator<UserPermission> iteratorPermission = permissions.iterator(); iteratorPermission.hasNext();) {
                                UserPermission permission = (UserPermission) iteratorPermission.next();
                                String keyUserPermission = permission.getCode();
                                String valueUserPermission = permission.getName();
                                userViewBean.getPermissions().put(keyUserPermission, valueUserPermission);
                            }
                        }
                    }
                }
            }
        }

        if(user.getConnectionLogs() != null && Hibernate.isInitialized(user.getConnectionLogs())){
            final Set<UserConnectionLog> connectionLogs = user.getConnectionLogs();
            for (Iterator<UserConnectionLog> iteratorUserConnectionLog = connectionLogs.iterator(); iteratorUserConnectionLog.hasNext();) {
                UserConnectionLog connectionLog = (UserConnectionLog) iteratorUserConnectionLog.next();
                UserConnectionLogValueBean connectionLogValueBean = new UserConnectionLogValueBean();
                DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
                connectionLogValueBean.setDate(dateFormat.format(connectionLog.getLoginDate()));
                connectionLogValueBean.setHost(Constants.NOT_AVAILABLE);
                if (StringUtils.isNotEmpty(connectionLog.getHost())) {
                    connectionLogValueBean.setHost(connectionLog.getHost());
                }
                connectionLogValueBean.setPublicAddress(Constants.NOT_AVAILABLE);
                if (StringUtils.isNotEmpty(connectionLog.getPublicAddress())) {
                    connectionLogValueBean.setPublicAddress(connectionLog.getPublicAddress());
                }
                connectionLogValueBean.setPrivateAddress(Constants.NOT_AVAILABLE);
                if (StringUtils.isNotEmpty(connectionLog.getPrivateAddress())) {
                    connectionLogValueBean.setPublicAddress(connectionLog.getPrivateAddress());
                }
                userViewBean.getUserConnectionLogs().add(connectionLogValueBean);
            }
        }

        final List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add("form");
        userViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

        return userViewBean;
    }

    /**
     * 
     */
    public List<UserViewBean> buildListViewBeanUser(final RequestData requestData, final List<User> users) throws Exception {
        final List<UserViewBean> userViewBeans = new ArrayList<UserViewBean>();
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            userViewBeans.add(buildViewBeanUser(requestData, user));
        }
        return userViewBeans;
    }

    /**
     * 
     */
    public List<CompanyViewBean> buildListViewBeanCompany(final RequestData requestData, final List<Company> companies) throws Exception {
        final List<CompanyViewBean> companyViewBeans = new ArrayList<CompanyViewBean>();
        for (Iterator<Company> iterator = companies.iterator(); iterator.hasNext();) {
            Company company = (Company) iterator.next();
            companyViewBeans.add(buildViewBeanCompany(requestData, company));
        }
        return companyViewBeans;
    }
    
    /**
     * @throws Exception
     * 
     */
    public CompanyViewBean buildViewBeanCompany(final RequestData requestData, final Company company) throws Exception {
        final Locale locale = requestData.getLocale();
        
        final CompanyViewBean companyViewBean = new CompanyViewBean();
        if(company.getId() != null){
            companyViewBean.setId(company.getId().toString());
        }
        companyViewBean.setCode(company.getCode());
        companyViewBean.setActive(company.isActive());
        companyViewBean.setName(company.getName());
        companyViewBean.setDescription(company.getDescription());

        companyViewBean.setAddress1(company.getAddress1());
        companyViewBean.setAddress2(company.getAddress2());
        companyViewBean.setAddressAdditionalInformation(company.getAddressAdditionalInformation());
        companyViewBean.setPostalCode(company.getPostalCode());
        companyViewBean.setCity(company.getCity());
        
        String stateName = referentialDataService.getStateByLocale(company.getStateCode(), locale);
        companyViewBean.setStateCode(company.getStateCode());
        companyViewBean.setStateName(stateName);
        
        String areaName = referentialDataService.getAreaByLocale(company.getAreaCode(), locale);
        companyViewBean.setAreaCode(company.getAreaCode());
        companyViewBean.setAreaName(areaName);
        
        String countryName = referentialDataService.getCountryByLocale(company.getCountryCode(), locale);
        companyViewBean.setCountryCode(company.getCountryCode());
        companyViewBean.setCountryName(countryName);
        
        if (company.getDateCreate() != null) {
            companyViewBean.setDateCreate(buildCommonFormatDate(requestData, company.getDateCreate()));
        } else {
            companyViewBean.setDateCreate(Constants.NOT_AVAILABLE);
        }
        if (company.getDateUpdate() != null) {
            companyViewBean.setDateUpdate(buildCommonFormatDate(requestData, company.getDateUpdate()));
        } else {
            companyViewBean.setDateUpdate(Constants.NOT_AVAILABLE);
        }

        return companyViewBean;
    }

    protected AttributeValueViewBean buildViewBeanAttributeValue(final RequestData requestData, final AbstractAttribute abstractAttribute) throws Exception {
        AttributeValueViewBean attributeValueViewBean = new AttributeValueViewBean();
        attributeValueViewBean.setAttributeDefinition(buildViewBeanAttributeDefinition(requestData, abstractAttribute.getAttributeDefinition()));
        attributeValueViewBean.setLocalizationCode(abstractAttribute.getLocalizationCode());
        attributeValueViewBean.setValue(abstractAttribute.getValueAsString());
        return attributeValueViewBean;
    }
    
    protected AttributeDefinitionViewBean buildViewBeanAttributeDefinition(final RequestData requestData, final AttributeDefinition attributeDefinition) throws Exception {
        AttributeDefinitionViewBean attributeDefinitionViewBean = new AttributeDefinitionViewBean();
        
        attributeDefinitionViewBean.setName(attributeDefinition.getName());
        attributeDefinitionViewBean.setDescription(attributeDefinition.getDescription());
        attributeDefinitionViewBean.setCode(attributeDefinition.getCode());
        
        attributeDefinitionViewBean.setAttributeType("" + attributeDefinition.getAttributeType());
        attributeDefinitionViewBean.setObjectType("" + attributeDefinition.getObjectType());

        attributeDefinitionViewBean.setLocalizable(attributeDefinition.isLocalizable());
        attributeDefinitionViewBean.setGlobal(attributeDefinition.isGlobal());
        attributeDefinitionViewBean.setMultiValue(attributeDefinition.isMultiValue());
        attributeDefinitionViewBean.setWithPlanner(attributeDefinition.isWithPlanner());
        
        if (attributeDefinition.getDateCreate() != null) {
            attributeDefinitionViewBean.setDateCreate(buildCommonFormatDate(requestData, attributeDefinition.getDateCreate()));
        }
        if (attributeDefinition.getDateUpdate() != null) {
            attributeDefinitionViewBean.setDateUpdate(buildCommonFormatDate(requestData, attributeDefinition.getDateUpdate()));
        }
        
        return attributeDefinitionViewBean;
    }
    
    protected boolean menuIsActive(String currentUrl, List<String> scopeUrls){
        for (Iterator<String> iterator = scopeUrls.iterator(); iterator.hasNext();) {
            String scopeUrl = (String) iterator.next();
            if(menuIsActive(currentUrl, scopeUrl)){
                return true;
            }
        }
        return false;
    }
    
    protected boolean menuIsActive(String currentUrl, String scopeUrl){
        if(currentUrl.contains(scopeUrl)){
            return true;
        }
        return false;
    }
    
    protected String buildCommonFormatDate(RequestData requestData, Date date) throws Exception {
        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        return dateFormat.format(date);
    }
    
}