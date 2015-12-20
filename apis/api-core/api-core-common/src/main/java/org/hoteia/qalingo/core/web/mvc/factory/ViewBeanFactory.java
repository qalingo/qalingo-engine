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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
import org.hoteia.qalingo.core.domain.CustomerPermission;
import org.hoteia.qalingo.core.domain.CustomerRole;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.DeliveryMethodPrice;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderAddress;
import org.hoteia.qalingo.core.domain.OrderItem;
import org.hoteia.qalingo.core.domain.OrderPayment;
import org.hoteia.qalingo.core.domain.OrderPurchase;
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
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuAttribute;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinition;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinitionType;
import org.hoteia.qalingo.core.domain.ProductSkuOptionRel;
import org.hoteia.qalingo.core.domain.ProductSkuPrice;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.StoreBusinessHour;
import org.hoteia.qalingo.core.domain.StoreCustomerComment;
import org.hoteia.qalingo.core.domain.Tag;
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
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CartService;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.OrderPurchaseService;
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
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderAddressViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderPaymentViewBean;
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
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuOptionDefinitionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuTagViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerCustomerCommentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SeoDataViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ShareOptionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreBusinessHourViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreCustomerCommentViewBean;
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

    @Autowired
    protected CartService cartService;

    @Autowired
    protected OrderPurchaseService orderPurchaseService;

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
        return new OurCompanyViewBean();
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
        security.getUrls().put(OpenProvider.YAHOO.name() + "_CONNECT", urlService.buildOAuthConnectUrl(requestData, OAuthType.YAHOO.getPropertyKey()));

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
        menuViewBean.setOrdering(ordering);
        customerLinks.add(menuViewBean);

        return customerLinks;
    }

    /**
     * 
     */
    public List<MarketPlaceViewBean> buildListViewBeanMarketPlace(final RequestData requestData) throws Exception {
        List<MarketPlaceViewBean> marketPlaceViewBeans = new ArrayList<MarketPlaceViewBean>();
        final List<MarketPlace> marketPlaceList = marketService.findMarketPlaces();
        for (final MarketPlace marketPlaceNavigation : marketPlaceList) {
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
        for (final Market marketNavigation : markets) {
            // TODO : why : SET A RELOAD OBJECT MARKET -> event
            // LazyInitializationException: could not initialize proxy - no Session
            final Market marketNavigationReloaded = marketService.getMarketById(marketNavigation.getId().toString());

            if (marketNavigationReloaded.getDefaultMarketArea() != null) {
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
            for (final MarketArea marketArea : marketAreas) {
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
                if (marketAreaViewBean.getCode().equalsIgnoreCase(currentMarketArea.getCode())) {
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

        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketArea(marketArea);
        marketAreaViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestDataChangecontext));
        marketAreaViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestDataChangecontext));

        return marketAreaViewBean;
    }

    /**
     * 
     */
    public List<LocalizationViewBean> buildListViewBeanLocalizationByMarketArea(final RequestData requestData, final Localization currentLocalization) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final List<Localization> translationAvailables = new ArrayList<Localization>(marketArea.getLocalizations());
        List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
        for (final Localization localizationAvailable : translationAvailables) {
            localizationViewBeans.add(buildViewBeanLocalization(requestData, localizationAvailable));
        }
        for (final LocalizationViewBean localizationViewBean : localizationViewBeans) {
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
        localizationViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestDataChangecontext));
        localizationViewBean.setChangeBackofficeLanguageUrl(urlService.buildChangeBackofficeLanguageUrl(requestDataChangecontext, localization));
        localizationViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestDataChangecontext));

        return localizationViewBean;
    }
    
    public List<CurrencyReferentialViewBean> buildListViewBeanCurrenciesByMarketArea(final RequestData requestData) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final List<CurrencyReferential> currencies = new ArrayList<CurrencyReferential>(marketArea.getCurrencies());
        return buildListViewBeanCurrencyReferential(requestData, currencies);
    }

    /**
     *
     */
    public List<CurrencyReferentialViewBean> buildListViewBeanCurrencyReferential(final RequestData requestData, final List<CurrencyReferential> currencyReferentials) throws Exception {
        final List<CurrencyReferentialViewBean> currencyReferentialViewBeans = new ArrayList<CurrencyReferentialViewBean>();
        if (currencyReferentials != null) {
            for (CurrencyReferential currencyReferential : currencyReferentials) {
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
        return buildListViewBeanRetailer(requestData, retailers);
    }

    /**
     * 
     */
    public List<RetailerViewBean> buildListViewBeanRetailer(final RequestData requestData, final List<Retailer> retailers) throws Exception {
        List<RetailerViewBean> retailerViewBeans;
        retailerViewBeans = new ArrayList<RetailerViewBean>();
        for (final Retailer retailer : retailers) {
            retailerViewBeans.add(buildViewBeanRetailer(requestData, retailer));
        }
        return retailerViewBeans;
    }

    /**
     * 
     */
    public RetailerViewBean buildViewBeanRetailer(final RequestData requestData, final Retailer retailer) throws Exception {
//        final HttpServletRequest request = requestData.getRequest();
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
        
        if (Hibernate.isInitialized(retailer.getAddresses()) && retailer.getAddresses() != null) {
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
            for (RetailerCustomerComment customerComment : customerComments) {
                RetailerCustomerCommentViewBean customerCommentViewBean = buildViewBeanRetailerCustomerComment(requestData, retailer, customerComment);
                retailerViewBean.getComments().add(customerCommentViewBean);
            }
        }

//        // TAGS
//        Set<RetailerTag> tags = retailer.getTags();
//        if (Hibernate.isInitialized(tags) &&
//                tags != null) {
//            for (Iterator<RetailerTag> iterator = tags.iterator(); iterator.hasNext();) {
//                RetailerTag retailerTag = (RetailerTag) iterator.next();
//                RetailerTagViewBean retailerTagViewBean = new RetailerTagViewBean();
//                retailerTagViewBean.setCode(retailerTag.getCode());
//                retailerTagViewBean.setName(retailerTag.getName());
//                retailerTagViewBean.setDescription(retailerTag.getDescription());
//                retailerViewBean.getTags().add(retailerTagViewBean);
//            }
//        }

        // STORES
        Set<Store> stores = retailer.getStores();
        if (Hibernate.isInitialized(stores) && stores != null) {
            for (Store store : stores) {
                StoreViewBean storeViewBean = buildViewBeanStore(requestData, store);
                retailerViewBean.getStores().add(storeViewBean);
            }
        }

        final String contextNameValue = requestUtil.getCurrentContextNameValue();
        List<String> shareOptions = marketArea.getShareOptions(contextNameValue);
        if (shareOptions != null) {
            for (String shareOption : shareOptions) {
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
        for (RetailerCustomerComment customerComment : customerComments) {
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
//        DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
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
        for (final Store store : stores) {
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
        storeViewBean.setType(store.getType());
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
            for (Asset asset : store.getAssets()) {
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
        
//        // TAGS
//        Set<StoreTag> tags = store.getTags();
//        if (Hibernate.isInitialized(tags) 
//                && tags != null) {
//            for (Iterator<StoreTag> iterator = tags.iterator(); iterator.hasNext();) {
//                StoreTag storeTag = (StoreTag) iterator.next();
//                StoreTagViewBean storeTagViewBean = new StoreTagViewBean();
//                storeTagViewBean.setCode(storeTag.getCode());
//                storeTagViewBean.setName(storeTag.getName());
//                storeTagViewBean.setDescription(storeTag.getDescription());
//                storeViewBean.getTags().add(storeTagViewBean);
//            }
//        }
        
        if (store.getDateCreate() != null) {
            storeViewBean.setDateCreate(buildCommonFormatDate(requestData, store.getDateCreate()));
        }
        if (store.getDateUpdate() != null) {
            storeViewBean.setDateUpdate(buildCommonFormatDate(requestData, store.getDateUpdate()));
        }
        
        // BUSINESS HOUR
        Set<StoreBusinessHour> storeBusinessHours = store.getBusinessHours();
        if (Hibernate.isInitialized(storeBusinessHours) && storeBusinessHours != null) {
            List<StoreBusinessHourViewBean> storeBusinessHourViewBeans = new ArrayList<StoreBusinessHourViewBean>();
            for (StoreBusinessHour storeBusinessHour : storeBusinessHours) {
                StoreBusinessHourViewBean storeBusinessHourViewBean = buildViewBeanStoreBusinessHour(requestData, storeBusinessHour);
                storeBusinessHourViewBeans.add(storeBusinessHourViewBean);
            }
            
            List<StoreBusinessHourViewBean> sortedStoreBusinessHourViewBeans = new LinkedList<StoreBusinessHourViewBean>(storeBusinessHourViewBeans);
            Collections.sort(sortedStoreBusinessHourViewBeans, new Comparator<StoreBusinessHourViewBean>() {
                @Override
                public int compare(StoreBusinessHourViewBean o1, StoreBusinessHourViewBean o2) {
                    if (o1 != null && o1.getRanking() != null && o2 != null && o2.getRanking() != null) {
                        return o1.getRanking().compareTo(o2.getRanking());
                    }
                    return 0;
                }
            });
            storeViewBean.setBusinessHours(sortedStoreBusinessHourViewBeans);
        }
        
        // ASSETS
        Set<Asset> assets = store.getAssets();
        if (Hibernate.isInitialized(assets) && assets != null) {
            List<String> sliders = new ArrayList<String>();
            for (Asset assetSlideshow : assets) {
                if (AssetType.SLIDESHOW.getPropertyKey().equals(assetSlideshow.getType())) {
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
    public StoreBusinessHourViewBean buildViewBeanStoreBusinessHour(final RequestData requestData, final StoreBusinessHour storeBusinessHour) {
        final Locale locale = requestData.getLocale();
        
        StoreBusinessHourViewBean storeBusinessHourViewBean = new StoreBusinessHourViewBean();
        
        storeBusinessHourViewBean.setClosingDateStart(storeBusinessHour.getClosingDateStart());
        storeBusinessHourViewBean.setClosingDateEnd(storeBusinessHour.getClosingDateEnd());

        storeBusinessHourViewBean.setStartHour(storeBusinessHour.getStartHour());
        storeBusinessHourViewBean.setEndHour(storeBusinessHour.getEndHour());

        if (storeBusinessHour.isDay()) {
            storeBusinessHourViewBean.setDayKey(storeBusinessHour.getDayKey());
            storeBusinessHourViewBean.setDayLabel(getCommonMessage("week", storeBusinessHour.getDayKey(), locale));
        }

        storeBusinessHourViewBean.setComment(storeBusinessHour.getComment());

        storeBusinessHourViewBean.setClosed(storeBusinessHour.isClosed());
        storeBusinessHourViewBean.setOff(storeBusinessHour.isOff());

        if(storeBusinessHour.isDay()){
            if(storeBusinessHour.isMonday()){
                storeBusinessHourViewBean.setRanking(1);
            } else if(storeBusinessHour.isTuesday()){
                storeBusinessHourViewBean.setRanking(2);
            } else if(storeBusinessHour.isWednesday()){
                storeBusinessHourViewBean.setRanking(3);
            } else if(storeBusinessHour.isThursday()){
                storeBusinessHourViewBean.setRanking(4);
            } else if(storeBusinessHour.isFriday()){
                storeBusinessHourViewBean.setRanking(5);
            } else if(storeBusinessHour.isSaturday()){
                storeBusinessHourViewBean.setRanking(6);
            } else if(storeBusinessHour.isSunday()){
                storeBusinessHourViewBean.setRanking(7);
            }
        }
        
        return storeBusinessHourViewBean;
    }
    
    /**
     * 
     */
    public List<StoreCustomerCommentViewBean> buildListViewBeanStoreCustomerComments(final RequestData requestData, final List<StoreCustomerComment> customerComments) throws Exception {
        final List<StoreCustomerCommentViewBean> customerCommentViewBeans = new ArrayList<StoreCustomerCommentViewBean>();
        for (StoreCustomerComment customerComment : customerComments) {
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
//        DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
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
            for (String aShareOptionAddInfo : shareOptionAddInfo) {
                String addInfo = (String) aShareOptionAddInfo;
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
                for (CustomerGroup group : groups) {
                    String keyCustomerGroup = group.getCode();
                    String valueCustomerGroup = group.getName();
                    customerViewBean.getGroups().put(keyCustomerGroup, valueCustomerGroup);

                    if (group.getRoles() != null && Hibernate.isInitialized(group.getRoles())) {
                        final Set<CustomerRole> roles = group.getRoles();
                        for (CustomerRole role : roles) {
                            String keyCustomerRole = role.getCode();
                            String valueCustomerRole = role.getName();
                            customerViewBean.getRoles().put(keyCustomerRole, valueCustomerRole);

                            if (role.getPermissions() != null && Hibernate.isInitialized(role.getPermissions())) {
                                final Set<CustomerPermission> permissions = role.getPermissions();
                                for (CustomerPermission permission : permissions) {
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
                DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
                for (CustomerConnectionLog connectionLog : customer.getSortedConnectionLogs()) {
                    if (connectionLog.getLoginDate() != null) {
                        customerViewBean.setLastConnectionDate(dateFormat.format(connectionLog.getLoginDate()));
                    } else {
                        customerViewBean.setLastConnectionDate(Constants.NOT_AVAILABLE);
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
                for (CustomerAddress address : customer.getAddresses()) {
                    customerViewBean.getAddresses().add(buildViewBeanCustomerAddress(requestData, address));
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
    public CustomerViewBean buildViewBeanCustomer(final RequestData requestData, final User user) throws Exception {
        final Locale locale = requestData.getLocale();
        final CustomerViewBean customerViewBean = new CustomerViewBean();
        if (user != null) {
            customerViewBean.setCode(user.getCode());
            customerViewBean.setTitle(referentialDataService.getTitleByLocale(user.getTitle(), locale));
            customerViewBean.setFirstname(user.getFirstname());
            customerViewBean.setLastname(user.getLastname());
            customerViewBean.setEmail(user.getEmail());
            customerViewBean.setLogin(user.getLogin());

            if (user.getDateCreate() != null) {
                customerViewBean.setDateCreate(buildCommonFormatDate(requestData, user.getDateCreate()));
            }
            if (user.getDateUpdate() != null) {
                customerViewBean.setDateUpdate(buildCommonFormatDate(requestData, user.getDateUpdate()));
            }

            customerViewBean.setValidated(user.isValidated());
            customerViewBean.setActive(user.isActive());
        }
        return customerViewBean;
    }

//    /**
//     * 
//     */
//    public CustomerWishlistViewBean buildViewBeanCustomerWishlist(final RequestData requestData, final Customer customer) throws Exception {
//        final MarketArea marketArea = requestData.getMarketArea();
//        final CustomerWishlistViewBean customerWishlistViewBean = new CustomerWishlistViewBean();
//        final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
//        if (customerMarketArea != null) {
//            final Set<CustomerWishlist> customerWishlists = customerMarketArea.getWishlistProducts();
//            if (Hibernate.isInitialized(customerWishlists) && customerWishlists != null) {
//                for (Iterator<CustomerWishlist> iterator = customerWishlists.iterator(); iterator.hasNext();) {
//                    final CustomerWishlist customerWishlist = (CustomerWishlist) iterator.next();
//                    final ProductSku productSku = productService.getProductSkuByCode(customerWishlist.getProductSkuCode(), FetchPlanGraphProduct.productSkuDisplayFetchPlan());
//                    final ProductMarketing productMarketing =  productService.getProductMarketingByCode(productSku.getProductMarketing().getCode());
//                    CatalogCategoryVirtual catalogCategory = null;
//                    if(StringUtils.isNotEmpty(customerWishlist.getCatalogCategoryCode())){
//                        catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(customerWishlist.getCatalogCategoryCode(), marketArea.getCatalog().getCode());
//                    } else {
//                        catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductSkuId(productSku.getId());
//                    }
//                    customerWishlistViewBean.getProductSkus().add(buildViewBeanProductSku(requestData, catalogCategory, productMarketing, productSku));
//                }
//            }
//        }
//        return customerWishlistViewBean;
//    }

    /**
     * 
     */
    public CustomerAddressListViewBean buildViewBeanCustomerAddressList(final RequestData requestData, final Customer customer) throws Exception {
        final CustomerAddressListViewBean customerAddressListViewBean = new CustomerAddressListViewBean();
        customerAddressListViewBean.setBackUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        final Set<CustomerAddress> addresses = customer.getAddresses();
        if(Hibernate.isInitialized(addresses) && addresses != null){
            for (CustomerAddress customerAddress : addresses) {
                customerAddressListViewBean.getCustomerAddressList().add(buildViewBeanCustomerAddress(requestData, customerAddress));
            }
        }
        return customerAddressListViewBean;
    }

    /**
     * 
     */
    public CustomerAddressViewBean buildViewBeanCustomerAddress(final RequestData requestData, final CustomerAddress address) throws Exception {
        final Locale locale = requestData.getLocale();
        final CustomerAddressViewBean addressViewBean = new CustomerAddressViewBean();
        if(address.getId() != null){
            addressViewBean.setId(address.getId().toString());
        }

        String addressName = address.getAddressName();
        if (StringUtils.isNotEmpty(addressName)) {
            addressViewBean.setAddressName(addressName);
        } else {
            addressViewBean.setAddressName(address.getCity());
        }

        addressViewBean.setTitleCode(address.getTitle());
        String titleLabel = referentialDataService.getTitleByLocale(address.getTitle(), locale);
        addressViewBean.setTitleLabel(titleLabel);

        addressViewBean.setLastname(address.getLastname());
        addressViewBean.setFirstname(address.getFirstname());

        addressViewBean.setAddress1(address.getAddress1());
        addressViewBean.setAddress2(address.getAddress2());
        addressViewBean.setAddressAdditionalInformation(address.getAddressAdditionalInformation());
        addressViewBean.setPostalCode(address.getPostalCode());
        addressViewBean.setCity(address.getCity());
        addressViewBean.setStateCode(address.getStateCode());
        addressViewBean.setAreaCode(address.getAreaCode());
        addressViewBean.setCountryCode(address.getCountryCode());

        String coutryLabel = referentialDataService.getCountryByLocale(address.getCountryCode(), locale);
        addressViewBean.setCountry(coutryLabel);

        addressViewBean.setDefaultBilling(address.isDefaultBilling());
        addressViewBean.setDefaultShipping(address.isDefaultShipping());

        Long customerAddressId = address.getId();
        if(customerAddressId != null){
            Map<String, String> urlParams = new HashMap<String, String>();
            urlParams.put(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ADDRESS_GUID, customerAddressId.toString());
            addressViewBean.setEditUrl(urlService.generateUrl(FoUrls.PERSONAL_EDIT_ADDRESS, requestData, urlParams));
            addressViewBean.setDeleteUrl(urlService.generateUrl(FoUrls.PERSONAL_DELETE_ADDRESS, requestData, urlParams));
        }

        return addressViewBean;
    }
    
    /**
     * 
     */
    public OrderAddressViewBean buildViewBeanOrderAddress(final RequestData requestData, final OrderAddress address) throws Exception {
        final Locale locale = requestData.getLocale();
        final OrderAddressViewBean addressViewBean = new OrderAddressViewBean();
        if(address.getId() != null){
            addressViewBean.setId(address.getId().toString());
        }

        addressViewBean.setTitleCode(address.getTitle());
        String titleLabel = referentialDataService.getTitleByLocale(address.getTitle(), locale);
        addressViewBean.setTitleLabel(titleLabel);

        addressViewBean.setLastname(address.getLastname());
        addressViewBean.setFirstname(address.getFirstname());

        addressViewBean.setAddress1(address.getAddress1());
        addressViewBean.setAddress2(address.getAddress2());
        addressViewBean.setAddressAdditionalInformation(address.getAddressAdditionalInformation());
        addressViewBean.setPostalCode(address.getPostalCode());
        addressViewBean.setCity(address.getCity());
        addressViewBean.setStateCode(address.getStateCode());
        addressViewBean.setAreaCode(address.getAreaCode());
        addressViewBean.setCountryCode(address.getCountryCode());

        String coutryLabel = referentialDataService.getCountryByLocale(address.getCountryCode(), locale);
        addressViewBean.setCountry(coutryLabel);

        return addressViewBean;
    }
    
    /**
     * 
     */
    public List<ProductBrandViewBean> buildListViewBeanProductBrand(final RequestData requestData, final List<ProductBrand> productBrands) throws Exception {
        final List<ProductBrandViewBean> productBrandViewBeans = new ArrayList<ProductBrandViewBean>();
        for (ProductBrand productBrand : productBrands) {
            productBrandViewBeans.add(buildViewBeanProductBrand(requestData, productBrand));
        }
        return productBrandViewBeans;
    }
    
    /**
     * 
     */
    public ProductBrandViewBean buildViewBeanProductBrand(final RequestData requestData, final ProductBrand productBrand) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final ProductBrandViewBean productBrandViewBean = new ProductBrandViewBean();
        productBrandViewBean.setId(productBrand.getId().toString());
        productBrandViewBean.setCode(productBrand.getCode());
        productBrandViewBean.setName(productBrand.getName());
        productBrandViewBean.setDescription(productBrand.getDescription());
        
        productBrandViewBean.setI18nName(productBrand.getI18nName(localizationCode));
        productBrandViewBean.setI18nLongDescription(productBrand.getI18nLongDescription(localizationCode));
        productBrandViewBean.setI18nShortDescription(productBrand.getI18nShortDescription(localizationCode));
        productBrandViewBean.setOriginCountryCode(productBrand.getOriginCountryCode());

        productBrandViewBean.setEnabled(productBrand.isEnabled());
        productBrandViewBean.setEnabledB2B(productBrand.isEnabledB2B());
        productBrandViewBean.setEnabledB2C(productBrand.isEnabledB2C());

        // ATTRIBUTES
        if (Hibernate.isInitialized(productBrand.getAttributes()) && productBrand.getAttributes() != null) {
            List<ProductBrandAttribute> globalAttributes = productBrand.getGlobalAttributes();
            if(globalAttributes != null){
                for (ProductBrandAttribute attribute : globalAttributes) {
                    productBrandViewBean.getGlobalAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
                }
            }

            List<ProductBrandAttribute> marketAreaAttributes = productBrand.getMarketAreaAttributes(marketArea.getId());
            if(marketAreaAttributes != null){
                for (ProductBrandAttribute attribute : marketAreaAttributes) {
                    productBrandViewBean.getMarketAreaAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
                }
            }
        }
        
        // ASSETS
        if (Hibernate.isInitialized(productBrand.getAssets()) && productBrand.getAssets() != null) {
            for (Asset asset : productBrand.getAssets()) {
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
        if (Hibernate.isInitialized(tags) && tags != null) {
            for (ProductBrandTag productBrandTag : tags) {
                ProductBrandTagViewBean productBrandTagViewBean = new ProductBrandTagViewBean();
                productBrandTagViewBean.setCode(productBrandTag.getCode());
                productBrandTagViewBean.setName(productBrandTag.getName());
                productBrandTagViewBean.setDescription(productBrandTag.getDescription());
                productBrandViewBean.getTags().add(productBrandTagViewBean);
            }
        }
        
        productBrandViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestData, productBrand));
        productBrandViewBean.setProductLineUrl(urlService.generateUrl(FoUrls.BRAND_LINE, requestData, productBrand));
        return productBrandViewBean;
    }

    /**
     * 
     */
    public ProductBrandViewBean buildViewBeanProductBrand(final RequestData requestData, final ProductBrand productBrand, final List<ProductMarketing> productMarketings) throws Exception {
        final ProductBrandViewBean productBrandViewBean = buildViewBeanProductBrand(requestData, productBrand);
        for (final ProductMarketing productMarketing : productMarketings) {
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
        for (ProductBrandCustomerComment customerComment : customerComments) {
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
//        DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
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
        for (CatalogCategoryMaster catalogCategory : catalogCategories) {
            catalogCategoryViewBeans.add(buildViewBeanMasterCatalogCategory(requestData, catalogCategory));
        }
        
        return catalogCategoryViewBeans;
    }
    
    /**
     * 
     */
    public CatalogCategoryViewBean buildViewBeanMasterCatalogCategory(final RequestData requestData, final CatalogCategoryMaster catalogCategory) throws Exception {
        final CatalogCategoryViewBean catalogCategoryViewBean = buildViewBeanCatalogCategory(requestData, catalogCategory);
        
        // PARENT CATEGORY
        if (catalogCategory != null && catalogCategory.getParentCatalogCategory() != null
                && Hibernate.isInitialized(catalogCategory.getParentCatalogCategory())) {
            catalogCategoryViewBean.setDefaultParentCategory(buildViewBeanCatalogCategory(requestData, catalogCategory.getParentCatalogCategory()));
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
        for (CatalogCategoryVirtual catalogCategory : catalogCategories) {
            catalogCategoryViewBeans.add(buildViewBeanVirtualCatalogCategory(requestData, catalogCategory));
        }
        return catalogCategoryViewBeans;
    }
    
    /**
     * 
     */
    public CatalogCategoryViewBean buildViewBeanVirtualCatalogCategory(final RequestData requestData, final CatalogCategoryVirtual catalogCategory) throws Exception {
        final CatalogCategoryViewBean catalogCategoryViewBean = buildViewBeanCatalogCategory(requestData, catalogCategory);
        if (catalogCategory != null && catalogCategory.getCategoryMaster() != null
                && Hibernate.isInitialized(catalogCategory.getCategoryMaster())) {
            catalogCategoryViewBean.setMasterCategory(buildViewBeanCatalogCategory(requestData, catalogCategory.getCategoryMaster()));
        }
        
        // PARENT CATEGORY
        if (catalogCategory != null && catalogCategory.getParentCatalogCategory() != null
                && Hibernate.isInitialized(catalogCategory.getParentCatalogCategory())) {
            catalogCategoryViewBean.setDefaultParentCategory(buildViewBeanCatalogCategory(requestData, catalogCategory.getParentCatalogCategory()));
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
                    for (AbstractAttribute attribute : globalAttributes) {
                        catalogCategoryViewBean.getGlobalAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
                    }
                }

                List<AbstractAttribute> marketAreaAttributes = catalogCategory.getMarketAreaAttributes(marketArea.getId());
                if(marketAreaAttributes != null){
                    for (AbstractAttribute attribute : marketAreaAttributes) {
                        catalogCategoryViewBean.getMarketAreaAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
                    }
                }
            }
            
            // ASSETS
            if (Hibernate.isInitialized(catalogCategory.getAssets()) && catalogCategory.getAssets() != null) {
                for (Asset asset : (Iterable<Asset>) catalogCategory.getAssets()) {
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
                for (final AbstractCatalogCategory subcatalogCategoryVirtual : subCategories) {
                    subcatalogCategoryVirtualViewBeans.add(buildViewBeanCatalogCategory(requestData, subcatalogCategoryVirtual));
                }
            }
            catalogCategoryViewBean.setSubCategories(subcatalogCategoryVirtualViewBeans);
            
            // PRODUCTS
            final List<ProductSku> productSkus = catalogCategory.getSortedProductSkus();
            if (productSkus != null) {
                for (final ProductSku productSku : productSkus) {
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
        for (ProductMarketing productMarketing : productMarketings) {
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
            List<ProductSkuViewBean> productSkus = new ArrayList<ProductSkuViewBean>();
            for (final ProductSku productSkuIt : skus) {
                final ProductSku reloadedProductSku = productService.getProductSkuByCode(productSkuIt.getCode());
                productSkus.add(buildViewBeanProductSku(requestData, catalogCategory, productMarketing, reloadedProductSku));
            }
            if(!productSkus.isEmpty()){
                productMarketingViewBean.setProductSkus(productSkus);
            }
        }

        final Set<ProductAssociationLink> productAssociationLinks = productMarketing.getProductAssociationLinks();
        if (Hibernate.isInitialized(productAssociationLinks) && productAssociationLinks != null) {
            for (final ProductAssociationLink productAssociationLink : productAssociationLinks) {
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

        productMarketingViewBean.setId(productMarketing.getId().toString());
        productMarketingViewBean.setCode(productMarketing.getCode());
        productMarketingViewBean.setName(productMarketing.getName());
        productMarketingViewBean.setDescription(productMarketing.getDescription());
        
        productMarketingViewBean.setI18nName(productMarketing.getI18nName(localizationCode));
        productMarketingViewBean.setI18nDescription(productMarketing.getI18nDescription(localizationCode));

        productMarketingViewBean.setDefault(productMarketing.isDefault());
        productMarketingViewBean.setEnabledB2B(productMarketing.isEnabledB2B());
        productMarketingViewBean.setEnabledB2C(productMarketing.isEnabledB2C());
        productMarketingViewBean.setSalableB2B(productMarketing.isSalableB2B());
        productMarketingViewBean.setSalableB2C(productMarketing.isSalableB2C());

        if (productMarketing.getDateCreate() != null) {
            productMarketingViewBean.setDateCreate(buildCommonFormatDate(requestData, productMarketing.getDateCreate()));
        }
        if (productMarketing.getDateUpdate() != null) {
            productMarketingViewBean.setDateUpdate(buildCommonFormatDate(requestData, productMarketing.getDateUpdate()));
        }

        // ATTRIBUTES
        List<ProductMarketingAttribute> globalAttributes = productMarketing.getGlobalAttributes();
        if(globalAttributes != null){
            for (ProductMarketingAttribute attribute : globalAttributes) {
                productMarketingViewBean.getGlobalAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
            }
        }

        List<ProductMarketingAttribute> marketAreaAttributes = productMarketing.getMarketAreaAttributes(marketArea.getId());
        if(marketAreaAttributes != null){
            for (ProductMarketingAttribute attribute : marketAreaAttributes) {
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
        if (Hibernate.isInitialized(productMarketing.getAssets()) && productMarketing.getAssets() != null && !productMarketing.getAssets().isEmpty()) {
            for (Asset asset : productMarketing.getAssets()) {
                AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
                final String path = engineSettingService.getProductMarketingImageWebPath(productMarketing, asset);
                assetViewBean.setRelativeWebPath(path);
                assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                productMarketingViewBean.getAssets().add(assetViewBean);
            }
        } else {
            // FALLBACK ASSET
            Asset asset = new Asset();
            asset.setType(Asset.ASSET_TYPE_DEFAULT);
            asset.setPath("default-product.png");
            AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
            final String path = engineSettingService.getProductMarketingImageWebPath(asset);
            assetViewBean.setRelativeWebPath(path);
            assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
            productMarketingViewBean.getAssets().add(assetViewBean);
        }

//        // TAGS
//        Set<ProductMarketingTag> tags = productMarketing.getTags();
//        if (Hibernate.isInitialized(tags) &&
//                tags != null) {
//            for (Iterator<ProductMarketingTag> iterator = tags.iterator(); iterator.hasNext();) {
//                ProductMarketingTag productMarketingTag = (ProductMarketingTag) iterator.next();
//                ProductMarketingTagViewBean productMarketingTagViewBean = new ProductMarketingTagViewBean();
//                productMarketingTagViewBean.setCode(productMarketingTag.getCode());
//                productMarketingTagViewBean.setName(productMarketingTag.getName());
//                productMarketingTagViewBean.setDescription(productMarketingTag.getDescription());
//                productMarketingViewBean.getTags().add(productMarketingTagViewBean);
//            }
//        }
        
        // SKUS
        if (Hibernate.isInitialized(productMarketing.getProductSkus()) && productMarketing.getProductSkus() != null) {
            for (ProductSku productSku : productMarketing.getProductSkus()) {
                ProductSkuViewBean productSkuViewBean = buildViewBeanProductSku(requestData, productMarketingViewBean, productSku);
                productMarketingViewBean.getProductSkus().add(productSkuViewBean);
            }
        } 
        
        ProductSku productSku = productMarketing.getDefaultProductSku();
        if(productSku != null){
            CatalogCategoryVirtual catalogCategory = productSku.getDefaultCatalogCategoryVirtual(marketArea.getCatalog());
            if(catalogCategory != null){
                productMarketingViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku));
            }
        }
        
        productMarketingViewBean.setFeatured(productMarketing.isFeatured());
        
        return productMarketingViewBean;
    }
    
    /**
     * 
     */
    public List<ProductMarketingCustomerCommentViewBean> buildListViewBeanProductMarketingCustomerComments(final RequestData requestData, final List<ProductMarketingCustomerComment> customerComments) throws Exception {
        final List<ProductMarketingCustomerCommentViewBean> customerCommentViewBeans = new ArrayList<ProductMarketingCustomerCommentViewBean>();
        for (ProductMarketingCustomerComment customerComment : customerComments) {
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
//        DateFormat dateFormatDataVocabulary = requestUtil.getDataVocabularyFormatDate(requestData);
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
        if(asset.getId() != null){
            assetViewBean.setId(asset.getId().toString());
        }
        assetViewBean.setName(asset.getName());
        assetViewBean.setDescription(asset.getDescription());
        if(StringUtils.isNotEmpty(asset.getSeo())){
            assetViewBean.setAlt(asset.getSeo());
            assetViewBean.setTitle(asset.getSeo());
        } else {
            assetViewBean.setAlt(asset.getDescription());
            assetViewBean.setTitle(asset.getDescription());
        }
        assetViewBean.setScope(asset.getScope());
        assetViewBean.setType(asset.getType());
        assetViewBean.setPath(asset.getPath());
        assetViewBean.setSize(asset.getSize());
        if(asset.getFileSize() != null){
            assetViewBean.setFileSize(asset.getFileSize().toString());
        }
        assetViewBean.setDefault(asset.isDefault());
        assetViewBean.setOrdering(asset.getOrdering());
        
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
        for (ProductSku productSku : productSkus) {
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
//        final Retailer retailer = requestData.getMarketAreaRetailer();
        
        final ProductSkuViewBean productSkuViewBean = new ProductSkuViewBean();

        productSkuViewBean.setId(productSku.getId().toString());
        productSkuViewBean.setCode(productSku.getCode());
        productSkuViewBean.setName(productSku.getName());
        productSkuViewBean.setDescription(productSku.getDescription());
        
        productSkuViewBean.setI18nName(productSku.getI18nName(localizationCode));
        productSkuViewBean.setI18nDescription(productSku.getI18nDescription(localizationCode));

        productSkuViewBean.setDefault(productSku.isDefault());
        productSkuViewBean.setEnabledB2B(productSku.isEnabledB2B());
        productSkuViewBean.setEnabledB2C(productSku.isEnabledB2C());
        productSkuViewBean.setSalableB2B(productSku.isSalableB2B());
        productSkuViewBean.setSalableB2C(productSku.isSalableB2C());
        
        if (productSku.getDateCreate() != null) {
            productSkuViewBean.setDateCreate(buildCommonFormatDate(requestData, productSku.getDateCreate()));
        }
        if (productSku.getDateUpdate() != null) {
            productSkuViewBean.setDateUpdate(buildCommonFormatDate(requestData, productSku.getDateUpdate()));
        }
        
        final ProductSkuPrice productSkuBestPrice = productSku.getBestPrice(marketArea.getId());
        if(productSkuBestPrice != null
                && StringUtils.isNotEmpty(productSkuBestPrice.getPriceWithStandardCurrencySign())){
            productSkuViewBean.setBestPriceWithCurrencySign(productSkuBestPrice.getPriceWithStandardCurrencySign());
        } else {
            productSkuViewBean.setBestPriceWithCurrencySign("NA");
        }
        final ProductSkuPrice productSkuSalePrice = productSku.getPublicPrice(marketArea.getId());
        if(productSkuSalePrice != null
                && StringUtils.isNotEmpty(productSkuSalePrice.getPriceWithStandardCurrencySign())){
            productSkuViewBean.setSalePriceWithCurrencySign(productSkuSalePrice.getPriceWithStandardCurrencySign());
        } else {
            productSkuViewBean.setSalePriceWithCurrencySign("NA");
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
            for (ProductSkuAttribute attribute : marketAreaAttributes) {
                productSkuViewBean.getMarketAreaAttributes().put(attribute.getAttributeDefinition().getCode(), buildViewBeanAttributeValue(requestData, attribute));
            }
        }
            
         // ASSETS
        if (Hibernate.isInitialized(productSku.getAssets()) && productSku.getAssets() != null && !productSku.getAssets().isEmpty()) {
            for (Asset asset : productSku.getAssets()) {
                AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
                final String path = engineSettingService.getProductSkuImageWebPath(productSku, asset);
                assetViewBean.setRelativeWebPath(path);
                assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
                productSkuViewBean.getAssets().add(assetViewBean);
            }
        } else {
            // FALLBACK ASSET
            Asset asset = new Asset();
            asset.setType(Asset.ASSET_TYPE_DEFAULT);
            asset.setPath("default-product-sku.png");
            AssetViewBean assetViewBean = buildViewBeanAsset(requestData, asset);
            final String path = engineSettingService.getProductSkuImageWebPath(asset);
            assetViewBean.setRelativeWebPath(path);
            assetViewBean.setAbsoluteWebPath(urlService.buildAbsoluteUrl(requestData, path));
            productSkuViewBean.getAssets().add(assetViewBean);
        }
        
        // TAGS
        List<Tag> tags = productSku.getTags();
        if (Hibernate.isInitialized(tags)  && tags != null) {
            productSkuViewBean.setTags(buildListViewBeanProductSkuTags(requestData, tags));
        }
        
        // SKU OPTIONS
        Set<ProductSkuOptionRel> optionRels = productSku.getOptionRels();
        if (Hibernate.isInitialized(optionRels) && optionRels != null) {
            
            List<ProductSkuOptionDefinitionViewBean> productSkuOptionDefinitionViewBeans = new ArrayList<ProductSkuOptionDefinitionViewBean>();
            for (ProductSkuOptionRel productSkuOptionRel : optionRels) {
                if (Hibernate.isInitialized(productSkuOptionRel.getProductSkuOptionDefinition())
                        && productSkuOptionRel.getProductSkuOptionDefinition() != null) {
                    final ProductSkuOptionDefinition productSkuOptionDefinition = productService.getProductSkuOptionDefinitionByCode(productSkuOptionRel.getProductSkuOptionDefinition().getCode());
                    ProductSkuOptionDefinitionViewBean productSkuOptionDefinitionViewBean = new ProductSkuOptionDefinitionViewBean();
                    productSkuOptionDefinitionViewBean.setCode(productSkuOptionDefinition.getCode());
                    productSkuOptionDefinitionViewBean.setName(productSkuOptionDefinition.getI18nName(localizationCode));

                    if (Hibernate.isInitialized(productSkuOptionDefinition.getOptionDefinitionType())
                            && productSkuOptionDefinition.getOptionDefinitionType() != null) {
                        ProductSkuOptionDefinitionType productSkuOptionDefinitionType = productSkuOptionDefinition.getOptionDefinitionType();
                        productSkuOptionDefinitionViewBean.setTypeCode(productSkuOptionDefinitionType.getCode());
                        productSkuOptionDefinitionViewBean.setTypeName(productSkuOptionDefinitionType.getI18nName(localizationCode));
                        productSkuOptionDefinitionViewBean.setTypeRanking(productSkuOptionDefinitionType.getRanking());
                    }
                    productSkuOptionDefinitionViewBeans.add(productSkuOptionDefinitionViewBean);
                }
            }
            Collections.sort(productSkuOptionDefinitionViewBeans, new Comparator<ProductSkuOptionDefinitionViewBean>() {
                @Override
                public int compare(ProductSkuOptionDefinitionViewBean o1, ProductSkuOptionDefinitionViewBean o2) {
                    if (o1 != null && o1.getTypeRanking() != null && o2 != null && o2.getTypeRanking() != null) {
                        return o1.getTypeRanking().compareTo(o2.getTypeRanking());
                    }
                    return 0;
                }
            });
            productSkuViewBean.setSkuOptionDefinitions(productSkuOptionDefinitionViewBeans);
        }
        
        // CATALOG CATEGORIES
        Set<CatalogCategoryVirtualProductSkuRel> catalogCategories = productSku.getCatalogCategoryVirtualProductSkuRels();
        if (Hibernate.isInitialized(catalogCategories) && catalogCategories != null) {
            for (CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel : catalogCategories) {
                CatalogCategoryViewBean catalogCategoryViewBean = buildViewBeanCatalogCategory(requestData, catalogCategoryVirtualProductSkuRel.getCatalogCategoryVirtual());
                productSkuViewBean.getCatalogCategories().add(catalogCategoryViewBean);
            }
        }
        
        ProductMarketing productMarketing = productSku.getProductMarketing();
        if(productMarketing != null){
            CatalogCategoryVirtual catalogCategory = productSku.getDefaultCatalogCategoryVirtual(marketArea.getCatalog());
            if(catalogCategory != null){
                productSkuViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku));
            }
        }

        return productSkuViewBean;
    }
    
    /**
     * 
     */
    public List<ProductSkuTagViewBean> buildListViewBeanProductSkuTags(final RequestData requestData, final List<Tag> productSkuTags) throws Exception {
        final List<ProductSkuTagViewBean> productSkuTagViewBeans = new ArrayList<ProductSkuTagViewBean>();
        for (Tag tag : productSkuTags) {
            productSkuTagViewBeans.add(buildViewBeanTag(requestData, tag));
        }
        return productSkuTagViewBeans;
    }
    
    public ProductSkuTagViewBean buildViewBeanTag(final RequestData requestData, Tag productSkuTag) {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        ProductSkuTagViewBean productSkuTagViewBean = new ProductSkuTagViewBean();
        if(productSkuTag.getId() != null){
            productSkuTagViewBean.setId(productSkuTag.getId().toString());
        }
        productSkuTagViewBean.setCode(productSkuTag.getCode());
        productSkuTagViewBean.setName(productSkuTag.getName());
        productSkuTagViewBean.setDescription(productSkuTag.getDescription());
        productSkuTagViewBean.setI18nName(productSkuTag.getI18nName(localizationCode));
        return productSkuTagViewBean;
    }

    /**
     * 
     */
    public ProductSkuOptionDefinitionViewBean buildViewBeanProductSkuOptionDefinition(final RequestData requestData, final ProductSkuOptionDefinition productSkuOptionDefinition) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        ProductSkuOptionDefinitionViewBean productSkuOptionDefinitionViewBean = new ProductSkuOptionDefinitionViewBean();
        if(productSkuOptionDefinition.getId() != null){
            productSkuOptionDefinitionViewBean.setId(productSkuOptionDefinition.getId().toString());
        }
        productSkuOptionDefinitionViewBean.setCode(productSkuOptionDefinition.getCode());
        productSkuOptionDefinitionViewBean.setName(productSkuOptionDefinition.getName());
        productSkuOptionDefinitionViewBean.setI18nName(productSkuOptionDefinition.getI18nName(localizationCode));
        
        if (Hibernate.isInitialized(productSkuOptionDefinition.getOptionDefinitionType()) 
                && productSkuOptionDefinition.getOptionDefinitionType() != null) {
            ProductSkuOptionDefinitionType productSkuOptionDefinitionType = productSkuOptionDefinition.getOptionDefinitionType();
            productSkuOptionDefinitionViewBean.setTypeCode(productSkuOptionDefinitionType.getCode());
            productSkuOptionDefinitionViewBean.setTypeName(productSkuOptionDefinitionType.getName());
            productSkuOptionDefinitionViewBean.setTypeI18nName(productSkuOptionDefinitionType.getI18nName(localizationCode));
        }
        return productSkuOptionDefinitionViewBean;
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
            for (Asset asset : productMarketing.getAssets()) {
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
//        final MarketArea marketArea = requestData.getMarketArea();
//        final Retailer retailer = requestData.getMarketAreaRetailer();
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
            for (final CartItem cartItem : cartItems) {
                cartItemViewBeans.add(buildViewBeanCartItem(requestData, cartItem, productSkuFetchPlan));
            }
            cartViewBean.setCartItems(cartItemViewBeans);

            // SUB PART : Shippings
            final List<CartDeliveryMethodViewBean> cartDeliveryMethodViewBeans = new ArrayList<CartDeliveryMethodViewBean>();
            final Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
            if (deliveryMethods != null) {
                for (final DeliveryMethod deliveryMethod : deliveryMethods) {
                    if (deliveryMethod != null) {
                        final CartDeliveryMethodViewBean cartDeliveryMethodViewBean = new CartDeliveryMethodViewBean();
                        cartDeliveryMethodViewBean.setLabel(deliveryMethod.getName());
                        cartDeliveryMethodViewBean.setAmountWithCurrencySign(deliveryMethod.getPriceWithStandardCurrencySign(cart.getCurrency().getId()));
                        Object[] params = {deliveryMethod.getName()};
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
            
            
            cartViewBean.setCartItemsTotalWithCurrencySign(cartService.getCartItemTotalWithStandardCurrencySign(cart));
            cartViewBean.setCartShippingTotalWithCurrencySign(cartService.getDeliveryMethodTotalWithStandardCurrencySign(cart));
            cartViewBean.setCartFeesTotalWithCurrencySign(cartService.getTaxTotalWithStandardCurrencySign(cart));
            cartViewBean.setCartTotalWithCurrencySign(cartService.getCartTotalWithStandardCurrencySign(cart));
        }

        return cartViewBean;
    }

    /**
     * 
     */
    protected CartItemViewBean buildViewBeanCartItem(final RequestData requestData, final CartItem cartItem, final FetchPlan productSkuFetchPlan) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final CartItemViewBean cartItemViewBean = new CartItemViewBean();

        cartItemViewBean.setSkuCode(cartItem.getProductSku().getCode());
        final ProductSku productSku = productService.getProductSkuByCode(cartItem.getProductSku().getCode(), productSkuFetchPlan);
        cartItem.setProductSku(productSku);
        
        cartItemViewBean.setI18nName(productSku.getI18nName(localizationCode));
        cartItemViewBean.setI18nDescription(productSku.getI18nDescription(localizationCode));
        
        cartItemViewBean.setQuantity(cartItem.getQuantity());

        // ASSETS
        if (Hibernate.isInitialized(productSku.getAssets()) && productSku.getAssets() != null) {
            for (Asset asset : productSku.getAssets()) {
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
        cartItemViewBean.setUnitPriceWithCurrencySign(cartItem.getPriceWithStandardCurrencySign(marketArea.getId()));

        // FEES AMOUNT FOR THIS PRODUCT SKU AND THIS QUANTITY
        
        //...
        
        // TOTAL AMOUNT FOR THIS PRODUCT SKU AND THIS QUANTITY
        cartItemViewBean.setAmountWithCurrencySign(cartItem.getTotalAmountWithStandardCurrencySign(marketArea.getId()));

        Map<String, String> getParams = new HashMap<String, String>();
        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE, cartItem.getProductSku().getCode());
        cartItemViewBean.setDeleteUrl(urlService.generateUrl(FoUrls.CART_REMOVE_ITEM, requestData, getParams));

        final ProductMarketing productMarketing = productService.getProductMarketingByCode(cartItem.getProductMarketing().getCode());
        final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(cartItem.getCatalogCategory().getCode(), requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode());
        
        cartItemViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, cartItem.getProductSku()));

        return cartItemViewBean;
    }

    /**
     * 
     */
    public List<OrderViewBean> buildListViewBeanOrder(final RequestData requestData, final List<OrderPurchase> orders) throws Exception {
        final List<OrderViewBean> orderViewBeans = new ArrayList<OrderViewBean>();
        for (OrderPurchase order : orders) {
            orderViewBeans.add(buildViewBeanOrder(requestData, order));
        }
        return orderViewBeans;
    }
    
    /**
     * 
     */
    public OrderViewBean buildViewBeanOrder(final RequestData requestData, final OrderPurchase order) throws Exception {
        final Locale locale = requestData.getLocale();
        final OrderViewBean orderViewBean = new OrderViewBean();
        if (order != null) {
            orderViewBean.setStatus(order.getStatus());
            orderViewBean.setStatusLabel(getCommonMessage(ScopeCommonMessage.ORDER, "order.status." + order.getStatus().toLowerCase(), locale));
            orderViewBean.setOrderNum(order.getOrderNum());
            
            if (Hibernate.isInitialized(order.getCustomer()) && order.getCustomer() != null) {
                orderViewBean.setCustomer(buildViewBeanCustomer(requestData, order.getCustomer()));
            }
            
            if (Hibernate.isInitialized(order.getUser()) && order.getUser() != null) {
                orderViewBean.setUser(buildViewBeanUser(requestData, order.getUser()));
            }
            
            if (Hibernate.isInitialized(order.getBillingAddress()) && order.getBillingAddress() != null) {
                orderViewBean.setBillingAddress(buildViewBeanOrderAddress(requestData, order.getBillingAddress()));
            }
            
            if (Hibernate.isInitialized(order.getShippingAddress()) && order.getShippingAddress() != null) {
                orderViewBean.setShippingAddress(buildViewBeanOrderAddress(requestData, order.getShippingAddress()));
            }
            
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
            for (OrderItem orderItem : orderItems) {
                orderItemViewBeans.add(buildViewBeanOrderItem(requestData, orderItem));
            }
            orderViewBean.setOrderItems(orderItemViewBeans);

            // SHIPPINGS
            final List<OrderShippingViewBean> orderShippingViewBeans = new ArrayList<OrderShippingViewBean>();
            final Set<OrderShipment> orderShipments = order.getShipments();
            if (Hibernate.isInitialized(orderShipments) && orderShipments != null) {
                for (final OrderShipment orderShipment : orderShipments) {
                    final OrderShippingViewBean orderShippingViewBean = new OrderShippingViewBean();
                    Object[] params = {orderShipment.getName()};
                    orderShippingViewBean.setOrderShippingTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.deliveryMethods", params, locale));
                    orderShippingViewBeans.add(orderShippingViewBean);
                }
                orderViewBean.setOrderShippings(orderShippingViewBeans);
            }

            // TAXES
            final List<OrderTaxViewBean> orderTaxViewBeans = new ArrayList<OrderTaxViewBean>();
            final Set<OrderTax> orderTaxes = order.getOrderTaxes();
            if (Hibernate.isInitialized(orderTaxes) && orderTaxes != null) {
                for (final OrderTax orderTax : orderTaxes) {
                    final OrderTaxViewBean orderTaxViewBean = new OrderTaxViewBean();
                    Object[] params = {orderTax.getAmount()};
                    orderTaxViewBean.setOrderTaxTotal(order.getCurrency().formatPriceWithStandardCurrencySign(orderTax.getAmount()));
                    orderTaxViewBean.setOrderTaxTotalLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.taxes", params, locale));
                    orderTaxViewBeans.add(orderTaxViewBean);
                }
                orderViewBean.setOrderTaxes(orderTaxViewBeans);
            }
            
            // PAYMENTS
            final List<OrderPaymentViewBean> orderPaymentViewBeans = new ArrayList<OrderPaymentViewBean>();
            final Set<OrderPayment> orderPayments = order.getPayments();
            if (Hibernate.isInitialized(orderPayments) && orderPayments != null) {
                for (final OrderPayment orderPayment : orderPayments) {
                    final OrderPaymentViewBean orderPaymentViewBean = buildViewBeanOrderPayment(requestData, order, orderPayment);
                    orderPaymentViewBeans.add(orderPaymentViewBean);
                }
                orderViewBean.setPayments(orderPaymentViewBeans);
            }
            
            orderViewBean.setOrderItemsTotalWithCurrencySign(orderPurchaseService.getOrderItemTotalWithTaxesWithStandardCurrencySign(order));
            orderViewBean.setOrderShippingTotalWithCurrencySign(orderPurchaseService.getDeliveryMethodTotalWithStandardCurrencySign(order));
            orderViewBean.setOrderTaxesTotalWithCurrencySign(orderPurchaseService.getTaxTotalWithStandardCurrencySign(order));
            orderViewBean.setOrderTotalWithCurrencySign(orderPurchaseService.getOrderTotalWithStandardCurrencySign(order));

            Map<String, String> getParams = new HashMap<String, String>();
            getParams.put(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ORDER_GUID, order.getId().toString());

            orderViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_ORDER_DETAILS, requestData, getParams));
        }

        return orderViewBean;
    }
    
    /**
     * 
     */
    public OrderPaymentViewBean buildViewBeanOrderPayment(final RequestData requestData, final OrderPurchase order, final OrderPayment orderPayment) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final OrderPaymentViewBean orderPaymentViewBean = new OrderPaymentViewBean();
        
        orderPaymentViewBean.setId(orderPayment.getId().toString());
        orderPaymentViewBean.setAmount(order.getCurrency().formatPriceWithStandardCurrencySign(orderPayment.getAmount()));
        orderPaymentViewBean.setIpAddress(orderPayment.getIpAddress());
        orderPaymentViewBean.setRequestToken(orderPayment.getRequestToken());
        orderPaymentViewBean.setPaymentType(orderPayment.getPaymentType());
        orderPaymentViewBean.setTransactionType(orderPayment.getTransactionType());
        orderPaymentViewBean.setCardType(orderPayment.getCardType());
        orderPaymentViewBean.setStatus(orderPayment.getStatus());
        orderPaymentViewBean.setCardHolderName(orderPayment.getCardHolderName());
        orderPaymentViewBean.setExpirationMonth(orderPayment.getExpirationMonth());
        orderPaymentViewBean.setExpirationYear(orderPayment.getExpirationYear());
        orderPaymentViewBean.setCvv2Code(orderPayment.getCvv2Code());
        orderPaymentViewBean.setAuthorizationCode(orderPayment.getAuthorizationCode());
        orderPaymentViewBean.setCurrencyCode(orderPayment.getCurrencyCode());
        
        if (orderPayment.getDateCreate() != null) {
            orderPaymentViewBean.setDateCreate(buildCommonFormatDate(requestData, orderPayment.getDateCreate()));
        }
        if (orderPayment.getDateUpdate() != null) {
            orderPaymentViewBean.setDateUpdate(buildCommonFormatDate(requestData, orderPayment.getDateUpdate()));
        }
        
        return orderPaymentViewBean;
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
            orderItemViewBean.setEan(productSku.getEan());
            orderItemViewBean.setI18nName(productSku.getI18nName(localizationCode));
            orderItemViewBean.setI18nDescription(productSku.getI18nDescription(localizationCode));
        }
        orderItemViewBean.setPrice(orderPurchaseService.getOrderItemPriceWithTaxesWithStandardCurrencySign(orderItem));
        orderItemViewBean.setQuantity(orderItem.getQuantity());
        orderItemViewBean.setAmount(orderPurchaseService.getOrderItemTotalPriceWithTaxesWithStandardCurrencySign(orderItem));
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
            for (PaymentGatewayOption paymentGatewayOption : paymentGatewayOptions) {
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
            for (UserGroup group : groups) {
                String keyUserGroup = group.getCode();
                String valueUserGroup = group.getName();
                userViewBean.getGroups().put(keyUserGroup, valueUserGroup);

                if (group.getRoles() != null && Hibernate.isInitialized(group.getRoles())) {
                    final Set<UserRole> roles = group.getRoles();
                    for (UserRole role : roles) {
                        String keyUserRole = role.getCode();
                        String valueUserRole = role.getName();
                        userViewBean.getRoles().put(keyUserRole, valueUserRole);

                        if (role.getPermissions() != null && Hibernate.isInitialized(role.getPermissions())) {
                            final Set<UserPermission> permissions = role.getPermissions();
                            for (UserPermission permission : permissions) {
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
            for (UserConnectionLog connectionLog : connectionLogs) {
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
        for (User user : users) {
            userViewBeans.add(buildViewBeanUser(requestData, user));
        }
        return userViewBeans;
    }

    /**
     * 
     */
    public List<CompanyViewBean> buildListViewBeanCompany(final RequestData requestData, final List<Company> companies) throws Exception {
        final List<CompanyViewBean> companyViewBeans = new ArrayList<CompanyViewBean>();
        for (Company company : companies) {
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
        for (String scopeUrl : scopeUrls) {
            if (menuIsActive(currentUrl, scopeUrl)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean menuIsActive(String currentUrl, String scopeUrl){
        return currentUrl.contains(scopeUrl);
    }
    
    protected String buildCommonFormatDate(RequestData requestData, Date date) throws Exception {
        return buildMediumFormatDate(requestData, date);
    }
    
    protected String buildLongFormatDate(RequestData requestData, Date date) throws Exception {
        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.LONG, DateFormat.LONG);
        return dateFormat.format(date);
    }
    
    protected String buildMediumFormatDate(RequestData requestData, Date date) throws Exception {
        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        return dateFormat.format(date);
    }
    
    protected String buildShortFormatDate(RequestData requestData, Date date) throws Exception {
        DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.SHORT, DateFormat.SHORT);
        return dateFormat.format(date);
    }
    
}