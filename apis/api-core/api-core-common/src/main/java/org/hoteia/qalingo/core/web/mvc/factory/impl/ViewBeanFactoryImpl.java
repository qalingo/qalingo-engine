/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
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
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.CustomerConnectionLog;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.CustomerProductComment;
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
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuPrice;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerTag;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.domain.enumtype.ImageSize;
import org.hoteia.qalingo.core.domain.enumtype.OAuthType;
import org.hoteia.qalingo.core.domain.enumtype.ProductAssociationLinkType;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.CustomerProductCommentService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.ReferentialDataService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.service.openid.OpenProvider;
import org.hoteia.qalingo.core.web.mvc.factory.AbstractViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartDeliveryMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CommonViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ConditionsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CurrencyReferentialViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerAddressListViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerAddressViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductCommentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductCommentsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerWishlistViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CutomerMenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.DeliveryMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FaqViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FollowUsOptionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FollowUsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FooterMenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.HeaderCartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LegalTermsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketAreaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketPlaceViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderShippingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderTaxViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OurCompanyViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentMethodOptionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductAssociationLinkViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerCustomerCommentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerTagViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ShareOptionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.TaxViewBean;
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
public class ViewBeanFactoryImpl extends AbstractViewBeanFactory implements ViewBeanFactory {

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
    protected CustomerProductCommentService customerProductCommentService;

    @Autowired
    protected RetailerService retailerService;

    @Autowired
    protected ReferentialDataService referentialDataService;

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
    public List<MenuViewBean> buildListViewBeanMenu(final RequestData requestData) throws Exception {
        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();

        return menuViewBeans;
    }

    /**
     * 
     */
    public List<FooterMenuViewBean> buildViewBeanFooterMenu(final RequestData requestData) throws Exception {
        final Locale locale = requestData.getLocale();
        List<FooterMenuViewBean> footerMenuViewBeans = new ArrayList<FooterMenuViewBean>();

        FooterMenuViewBean footerMenuList = new FooterMenuViewBean();
        footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "conditionsofuse", locale));
        footerMenuList.setUrl(urlService.generateUrl(FoUrls.CONDITIONS_OF_USE, requestData));
        footerMenuList.setType(FooterMenuViewBean.MENU_TYPE_CUSTOMER_CARE);
        footerMenuViewBeans.add(footerMenuList);

        footerMenuList = new FooterMenuViewBean();
        footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "legal_terms", locale));
        footerMenuList.setUrl(urlService.generateUrl(FoUrls.LEGAL_TERMS, requestData));
        footerMenuList.setType(FooterMenuViewBean.MENU_TYPE_OUR_COMPANY);
        footerMenuViewBeans.add(footerMenuList);

        footerMenuList = new FooterMenuViewBean();
        footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "faq", locale));
        footerMenuList.setUrl(urlService.generateUrl(FoUrls.FAQ, requestData));
        footerMenuList.setType(FooterMenuViewBean.MENU_TYPE_CUSTOMER_CARE);
        footerMenuViewBeans.add(footerMenuList);

        footerMenuList = new FooterMenuViewBean();
        footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "store_location", locale));
        footerMenuList.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
        footerMenuList.setType(FooterMenuViewBean.MENU_TYPE_CUSTOMER_CARE);
        footerMenuViewBeans.add(footerMenuList);

        footerMenuList = new FooterMenuViewBean();
        footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "contactus", locale));
        footerMenuList.setUrl(urlService.generateUrl(FoUrls.CONTACT, requestData));
        footerMenuList.setType(FooterMenuViewBean.MENU_TYPE_OUR_COMPANY);
        footerMenuViewBeans.add(footerMenuList);

        footerMenuList = new FooterMenuViewBean();
        footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "followus", locale));
        footerMenuList.setUrl(urlService.generateUrl(FoUrls.FOLLOW_US, requestData));
        footerMenuList.setType(FooterMenuViewBean.MENU_TYPE_OUR_COMPANY);
        footerMenuViewBeans.add(footerMenuList);

        return footerMenuViewBeans;
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
    public OurCompanyViewBean buildViewBeanOurCompany(final RequestData requestData) throws Exception {
        final OurCompanyViewBean ourCompany = new OurCompanyViewBean();
        return ourCompany;
    }

    /**
     * 
     */
    public FaqViewBean buildViewBeanFaq(final RequestData requestData) throws Exception {
        final FaqViewBean faq = new FaqViewBean();
        return faq;
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

        security.getUrls().put(OAuthType.FACEBOOK.name() + "_CONNECT", urlService.buildOAuthConnectUrl(requestData, OAuthType.FACEBOOK.getPropertyKey()));
        security.getUrls().put(OAuthType.WINDOWS_LIVE.name() + "_CONNECT", urlService.buildOAuthConnectUrl(requestData, OAuthType.WINDOWS_LIVE.getPropertyKey()));
        security.getUrls().put(OpenProvider.GOOGLE_ACCOUNT.name() + "_CONNECT", urlService.buildOpenIdConnectUrl(requestData, OpenProvider.GOOGLE_ACCOUNT.getPropertyKey()));
        security.getUrls().put(OpenProvider.YAHOO.name() + "_CONNECT", urlService.buildOpenIdConnectUrl(requestData, OpenProvider.YAHOO.getPropertyKey()));

        return security;
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
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();

        List<RetailerViewBean> retailerViewBeans = new ArrayList<RetailerViewBean>();
        retailerViewBeans = new ArrayList<RetailerViewBean>();
        for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
            final Retailer retailerIt = (Retailer) iterator.next();
            final Retailer reloadedRetailer = retailerService.getRetailerByCode(marketArea.getId(), retailer.getId(), retailerIt.getCode());
            retailerViewBeans.add(buildViewBeanRetailer(requestData, reloadedRetailer));
        }
        return retailerViewBeans;
    }

    /**
     * 
     */
    public RetailerViewBean buildViewBeanRetailer(final RequestData requestData, final Retailer retailer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();

        final RetailerViewBean retailerViewBean = new RetailerViewBean();

        retailerViewBean.setCode(retailer.getCode());
        retailerViewBean.setName(retailer.getName());
        retailerViewBean.setDescription(retailer.getDescription());

        retailerViewBean.setOfficialRetailer(retailer.isOfficialRetailer());
        retailerViewBean.setBrand(retailer.isBrand());
        retailerViewBean.setEcommerce(retailer.isEcommerce());
        retailerViewBean.setCorner(retailer.isCorner());

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

        // CLONE THE CURRENT REQUEST DATE TO BUILD THE CHANGE CONTEXT URL (MENU)
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketAreaRetailer(retailer);
        
        retailerViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestDataChangecontext));
        retailerViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestDataChangecontext));

        retailerViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestData, retailer));

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
                // reviewDataVocabulary.setSummary(summary);
                reviewDataVocabulary.setDescription(retailerCustomerComment.getComment());
                // reviewDataVocabulary.setRating(rating);

                retailerCustomerCommentViewBean.setReviewDataVocabulary(reviewDataVocabulary);

                retailerViewBean.getComments().add(retailerCustomerCommentViewBean);
            }
        }

        Set<RetailerTag> tags = retailer.getRetailerTags();
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

        Set<Store> stores = retailer.getStores();
        if (Hibernate.isInitialized(stores) &&
                stores != null) {
            for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
                Store store = (Store) iterator.next();
                StoreViewBean storeViewBean = buildViewBeanStore(requestData, store);
                retailerViewBean.getStores().add(storeViewBean);
            }
        }

        final String contextNameValue = requestUtil.getCurrentContextNameValue(request);
        List<String> shareOptions = marketArea.getShareOptions(contextNameValue);
        if (shareOptions != null) {
            for (Iterator<String> iterator = shareOptions.iterator(); iterator.hasNext();) {
                String shareOption = (String) iterator.next();
                String relativeUrl = urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestData, retailer);
                ShareOptionViewBean shareOptionViewBean = buildViewBeanShareOption(requestData, shareOption, relativeUrl);
                retailerViewBean.getShareOptions().add(shareOptionViewBean);
            }
        }

        return retailerViewBean;
    }

    /**
     * 
     */
    public List<CutomerMenuViewBean> buildListViewBeanCutomerMenu(final RequestData requestData) throws Exception {
        final Locale locale = requestData.getLocale();

        List<CutomerMenuViewBean> customerLinks = new ArrayList<CutomerMenuViewBean>();
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

        return customerLinks;
    }

    /**
     * 
     */
    public ConditionsViewBean buildViewBeanConditions(final RequestData requestData) throws Exception {
        final ConditionsViewBean conditions = new ConditionsViewBean();

        return conditions;
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
            
            marketAreaViewBeans.add(buildViewBeanMarketArea(requestDataForThisMarketArea, marketArea));
        }
        return marketAreaViewBeans;
    }

    /**
     * 
     */
    public MarketAreaViewBean buildViewBeanMarketArea(final RequestData requestData, final MarketArea marketArea) throws Exception {
        final MarketAreaViewBean marketAreaViewBean = new MarketAreaViewBean();
        marketAreaViewBean.setName(marketArea.getName());
        marketAreaViewBean.setDescription(marketArea.getDescription());
        marketAreaViewBean.setCode(marketArea.getCode());

        marketAreaViewBean.setChangeContextUrl(urlService.buildChangeContextUrl(requestData));
        marketAreaViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestData));

        marketAreaViewBean.setLatitude(marketArea.getLatitude());
        marketAreaViewBean.setLongitude(marketArea.getLongitude());
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
        final String localizationCodeNavigation = localization.getCode();

        final LocalizationViewBean localizationViewBean = new LocalizationViewBean();
        localizationViewBean.setCode(localizationCodeNavigation);
        localizationViewBean.setDescription(localization.getDescription());
        localizationViewBean.setCountry(localization.getCountry());
        localizationViewBean.setLanguage(localization.getLanguage());

        localizationViewBean.setName(localization.getName());
        String localizationCodeTranslation = localizationCodeNavigation;
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

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date dateCreate = localization.getDateCreate();
        if (dateCreate != null) {
            localizationViewBean.setDateCreate(dateFormat.format(dateCreate));
        } else {
            localizationViewBean.setDateCreate("NA");
        }

        Date dateUpdate = localization.getDateUpdate();
        if (dateUpdate != null) {
            localizationViewBean.setDateUpdate(dateFormat.format(dateUpdate));
        } else {
            localizationViewBean.setDateUpdate("NA");
        }
        
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketAreaLocalization(localization);
        localizationViewBean.setChangeContextUrl(urlService.buildChangeLanguageUrl(requestDataChangecontext));
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

            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            Date dateCreate = currencyReferential.getDateCreate();
            if (dateCreate != null) {
                currencyReferentialViewBean.setDateCreate(dateFormat.format(dateCreate));
            } else {
                currencyReferentialViewBean.setDateCreate("NA");
            }

            Date dateUpdate = currencyReferential.getDateUpdate();
            if (dateUpdate != null) {
                currencyReferentialViewBean.setDateUpdate(dateFormat.format(dateUpdate));
            } else {
                currencyReferentialViewBean.setDateUpdate("NA");
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
    public StoreLocatorViewBean buildViewBeanStoreLocator(final RequestData requestData, final List<Store> stores) throws Exception {
        final Locale locale = requestData.getLocale();
        StoreLocatorViewBean storeLocator = new StoreLocatorViewBean();
        storeLocator.setPageTitle(getSpecificMessage(ScopeWebMessage.STORE_LOCATOR, "header.title", locale));
        storeLocator.setTextHtml(getSpecificMessage(ScopeWebMessage.STORE_LOCATOR, "content.text", locale));
        for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
            final Store store = (Store) iterator.next();
            storeLocator.getStores().add(buildViewBeanStore(requestData, store));
        }
        return storeLocator;
    }

    /**
     * 
     */
    public StoreViewBean buildViewBeanStore(final RequestData requestData, final Store store) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();

        final StoreViewBean storeViewBean = new StoreViewBean();
        storeViewBean.setCode(store.getCode());
        storeViewBean.setName(store.getName());
        storeViewBean.setAddress1(store.getAddress1());
        storeViewBean.setAddress2(store.getAddress2());
        storeViewBean.setAddressAdditionalInformation(store.getAddressAdditionalInformation());
        storeViewBean.setPostalCode(store.getPostalCode());

        // I18n values
        storeViewBean.setCity(store.getCity());
        String i18nCityName = store.getI18nCity(localization);
        if(StringUtils.isNotEmpty(i18nCityName)){
            storeViewBean.setCity(i18nCityName);
        }

        storeViewBean.setStateCode(store.getStateCode());
        storeViewBean.setCountry(store.getCountryCode());
        storeViewBean.setCountryCode(store.getCountryCode());
        storeViewBean.setLongitude(store.getLongitude());
        storeViewBean.setLatitude(store.getLatitude());
        
        final Asset defaultPackshotImage = store.getDefaultPackshotImage(ImageSize.SMALL.name());
        if (defaultPackshotImage != null) {
            final String defaultImage = requestUtil.getRetailerOrStoreImageWebPath(requestData.getRequest(), defaultPackshotImage);
            storeViewBean.setDefaultImage(defaultImage);
        } else {
            storeViewBean.setDefaultImage("");
        }
        final Asset defaultIconImage = store.getDefaultIconImage();
        if (defaultIconImage != null) {
            final String iconImage = requestUtil.getRetailerOrStoreImageWebPath(requestData.getRequest(), defaultIconImage);
            storeViewBean.setIconImage(iconImage);
        } else {
            storeViewBean.setIconImage("");
        }
        
		final List<Asset> assets = store.getSlideShows();
		if(assets != null){
	        List<String> sliders = new ArrayList<String>();
	        for(Asset asset : assets ){
	            final String iconImage = requestUtil.getRetailerOrStoreImageWebPath(requestData.getRequest(), asset);
	            sliders.add(iconImage);
	        }
	        storeViewBean.setSliders(sliders);
		}
        storeViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.STORE_DETAILS, requestData, store));
        
        return storeViewBean;
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

            final Set<CustomerConnectionLog> connectionLogs = customer.getConnectionLogs();
            if (connectionLogs != null && Hibernate.isInitialized(connectionLogs) && connectionLogs.size() > 0) {
                CustomerConnectionLog customerConnectionLog = connectionLogs.iterator().next();
                if (customerConnectionLog.getLoginDate() != null) {
                    customerViewBean.setLastConnectionDate(dateFormat.format(customerConnectionLog.getLoginDate()));
                } else {
                    customerViewBean.setLastConnectionDate(Constants.NOT_AVAILABLE);
                }
            }
            
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
                    final ProductSku productSku = productService.getProductSkuByCode(customerWishlist.getProductSkuCode());
                    final ProductMarketing productMarketing =  productService.getProductMarketingByCode(productSku.getProductMarketing().getCode());
                    final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), productMarketing.getCode());
                    customerWishlistViewBean.getProductSkus().add(buildViewBeanProductSku(requestData, catalogCategory, productMarketing, productSku));
                }
            }
        }

        return customerWishlistViewBean;
    }

    /**
     * 
     */
    public CustomerProductCommentsViewBean buildViewBeanCustomerProductComments(final RequestData requestData, final Customer customer) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();

        final CustomerProductCommentsViewBean customerProductCommentsViewBean = new CustomerProductCommentsViewBean();
        final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
        if (customerMarketArea != null) {
            final Set<CustomerProductComment> customerProductComments = customerMarketArea.getProductComments();
            if (Hibernate.isInitialized(customerProductComments) && customerProductComments != null) {
                for (Iterator<CustomerProductComment> iterator = customerProductComments.iterator(); iterator.hasNext();) {
                    final CustomerProductComment customerProductComment = (CustomerProductComment) iterator.next();
                    final ProductSku reloadedProductSku = productService.getProductSkuByCode(customerProductComment.getProductSkuCode());
                    final ProductMarketing productMarketing = reloadedProductSku.getProductMarketing();
                    final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), productMarketing.getCode());
                    customerProductCommentsViewBean.getCustomerProductCommentViewBeans().add(
                            buildViewBeanCustomerProductComment(requestData, catalogCategory, productMarketing, reloadedProductSku, customerProductComment));
                }
            }
        }
        return customerProductCommentsViewBean;
    }

    /**
     * 
     */
    public CustomerProductCommentViewBean buildViewBeanCustomerProductComment(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, final ProductMarketing productMarketing,
            final ProductSku productSku, final CustomerProductComment customerProductComment) throws Exception {
        final CustomerProductCommentViewBean customerProductCommentViewBean = new CustomerProductCommentViewBean();
        customerProductCommentViewBean.setProductSku(buildViewBeanProductSku(requestData, catalogCategory, productMarketing, productSku));
        customerProductCommentViewBean.setComment(customerProductComment.getComment());
        return customerProductCommentViewBean;
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
        urlParams.put(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ADDRESS_GUID, customerAddressId.toString());

        customerAddressViewBean.setEditUrl(urlService.generateUrl(FoUrls.PERSONAL_EDIT_ADDRESS, requestData, urlParams));
        customerAddressViewBean.setDeleteUrl(urlService.generateUrl(FoUrls.PERSONAL_DELETE_ADDRESS, requestData, urlParams));

        return customerAddressViewBean;
    }

    /**
     * 
     */
    public ProductBrandViewBean buildViewBeanProductBrand(final RequestData requestData, final ProductBrand productBrand) throws Exception {
        final ProductBrandViewBean productBrandViewBean = new ProductBrandViewBean();
        productBrandViewBean.setCode(productBrand.getCode());
        productBrandViewBean.setName(productBrand.getName());
        productBrandViewBean.setDescription(productBrand.getDescription());
        return productBrandViewBean;
    }

    /**
     * 
     */
    public ProductBrandViewBean buildViewBeanProductBrand(final RequestData requestData, final ProductBrand productBrand, final List<ProductMarketing> productMarketings) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();

        final ProductBrandViewBean productBrandViewBean = buildViewBeanProductBrand(requestData, productBrand);
        for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
            final ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            CatalogCategoryVirtual catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), productMarketing.getCode());
            productBrandViewBean.getProductMarketings().add(buildViewBeanProductMarketing(requestData, catalogCategory, productMarketing));
        }
        return productBrandViewBean;
    }

    /**
     * 
     */
    public CatalogCategoryViewBean buildViewBeanMastercatalogCategoryVirtual(final RequestData requestData, final CatalogCategoryVirtual catalogCategory) throws Exception {
        final CatalogCategoryViewBean catalogCategoryViewBean = buildViewBeanCatalogCategory(requestData, catalogCategory);
        return catalogCategoryViewBean;
    }

    /**
     * 
     */
    public CatalogCategoryViewBean buildViewBeanCatalogCategory(final RequestData requestData, final CatalogCategoryVirtual catalogCategory) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final CatalogCategoryViewBean catalogCategoryViewBean = new CatalogCategoryViewBean();
        
        catalogCategoryViewBean.setCode(catalogCategory.getCode());
        catalogCategoryViewBean.setName(catalogCategory.getI18nName(localizationCode));
        catalogCategoryViewBean.setDescription(catalogCategory.getDescription());
        catalogCategoryViewBean.setRoot(catalogCategory.isRoot());

        final Asset defaultBackgroundImage = catalogCategory.getDefaultBackgroundImage();
        if (defaultBackgroundImage != null) {
            final String backgroundImage = requestUtil.getCatalogImageWebPath(request, defaultBackgroundImage);
            catalogCategoryViewBean.setBackgroundImage(backgroundImage);
        } else {
            catalogCategoryViewBean.setBackgroundImage("");
        }
        final Asset defaultSlideshowImage = catalogCategory.getDefaultSlideshowImage();
        if (defaultSlideshowImage != null) {
            final String slideshowImage = requestUtil.getCatalogImageWebPath(request, defaultSlideshowImage);
            catalogCategoryViewBean.setSlideshowImage(slideshowImage);
        } else {
            catalogCategoryViewBean.setBackgroundImage("");
        }

        final Asset defaultPaskshotImage = catalogCategory.getDefaultPaskshotImage(ImageSize.SMALL.getPropertyKey());
        if (defaultPaskshotImage != null) {
            final String carouselImage = requestUtil.getCatalogImageWebPath(request, defaultPaskshotImage);
            catalogCategoryViewBean.setCarouselImage(carouselImage);
        } else {
            catalogCategoryViewBean.setCarouselImage("");
        }
        final Asset defaultIconImage = catalogCategory.getDefaultIconImage();
        if (defaultIconImage != null) {
            final String iconImage = requestUtil.getCatalogImageWebPath(request, defaultIconImage);
            catalogCategoryViewBean.setIconImage(iconImage);
        } else {
            catalogCategoryViewBean.setIconImage("");
        }

        if (catalogCategory.isRoot()) {
            catalogCategoryViewBean.setProductAxeUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_AXE, requestData, catalogCategory));
        } else {
            catalogCategoryViewBean.setProductLineUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, catalogCategory));
        }

        List<CatalogCategoryViewBean> subcatalogCategoryVirtualViewBeans = new ArrayList<CatalogCategoryViewBean>();
        final Set<CatalogCategoryVirtual> subCategories = catalogCategory.getCatalogCategories();
        if (Hibernate.isInitialized(subCategories) && subCategories != null) {
            for (Iterator<CatalogCategoryVirtual> iteratorSubcatalogCategoryVirtual = subCategories.iterator(); iteratorSubcatalogCategoryVirtual.hasNext();) {
                final CatalogCategoryVirtual subcatalogCategoryVirtual = (CatalogCategoryVirtual) iteratorSubcatalogCategoryVirtual.next();
                final CatalogCategoryVirtual reloadedSubCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(marketArea.getId(), subcatalogCategoryVirtual.getCode());
                subcatalogCategoryVirtualViewBeans.add(buildViewBeanCatalogCategory(requestData, reloadedSubCatalogCategory));
            }
        }
        catalogCategoryViewBean.setSubCategories(subcatalogCategoryVirtualViewBeans);

        List<ProductMarketingViewBean> productMarketingViewBeans = new ArrayList<ProductMarketingViewBean>();
        List<ProductMarketingViewBean> featuredProductMarketings = new ArrayList<ProductMarketingViewBean>();
        final Set<ProductMarketing> productMarketings = catalogCategory.getProductMarketings();
        if (Hibernate.isInitialized(productMarketings) && productMarketings != null) {
            for (Iterator<ProductMarketing> iteratorProductMarketing = productMarketings.iterator(); iteratorProductMarketing.hasNext();) {
                final ProductMarketing productMarketing = (ProductMarketing) iteratorProductMarketing.next();
                final ProductMarketing reloadedProductMarketing = productService.getProductMarketingByCode(productMarketing.getCode());
                ProductMarketingViewBean productMarketingViewBean = buildViewBeanProductMarketing(requestData, catalogCategory, reloadedProductMarketing);
                productMarketingViewBeans.add(productMarketingViewBean);
                if (productMarketingViewBean.isFeatured()) {
                    featuredProductMarketings.add(productMarketingViewBean);
                }
            }
        }
        catalogCategoryViewBean.setProductMarketings(productMarketingViewBeans);

        for (CatalogCategoryViewBean subcatalogCategoryVirtualViewBean : subcatalogCategoryVirtualViewBeans) {
            featuredProductMarketings.addAll(subcatalogCategoryVirtualViewBean.getFeaturedProductMarketings());
        }

        catalogCategoryViewBean.setFeaturedProductMarketings(featuredProductMarketings);

        return catalogCategoryViewBean;
    }
    
    /**
     * 
     */
    public ProductMarketingViewBean buildViewBeanProductMarketing(final RequestData requestData, final CatalogCategoryMaster catalogCategory, final ProductMarketing productMarketing) throws Exception {
        final ProductMarketingViewBean productMarketingViewBean = buildViewBeanProductMarketing(requestData, productMarketing);

        productMarketingViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));

        final ProductBrand productBrand = productMarketing.getProductBrand();
        if (Hibernate.isInitialized(productBrand) && productBrand != null) {
            productMarketingViewBean.setBrand(buildViewBeanProductBrand(requestData, productBrand));
            productMarketingViewBean.setBrandDetailsUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestData, productBrand));
            productMarketingViewBean.setBrandLineDetailsUrl(urlService.generateUrl(FoUrls.BRAND_LINE, requestData, productBrand));
        }

        final Set<ProductSku> skus = productMarketing.getProductSkus();
        if (Hibernate.isInitialized(skus) && skus != null) {
            for (Iterator<ProductSku> iterator = skus.iterator(); iterator.hasNext();) {
                final ProductSku productSku = (ProductSku) iterator.next();
                final ProductSku reloadedProductSku = productService.getProductSkuByCode(productSku.getCode());
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

        productMarketingViewBean.setFeatured(productMarketing.isFeatured());

        return productMarketingViewBean;
    }

    /**
     * 
     */
    public ProductMarketingViewBean buildViewBeanProductMarketing(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, final ProductMarketing productMarketing)
            throws Exception {
        final ProductMarketingViewBean productMarketingViewBean = buildViewBeanProductMarketing(requestData, productMarketing);

        productMarketingViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));

        final ProductBrand productBrand = productMarketing.getProductBrand();
        if (Hibernate.isInitialized(productBrand) && productBrand != null) {
            productMarketingViewBean.setBrandDetailsUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestData, productBrand));
            productMarketingViewBean.setBrandLineDetailsUrl(urlService.generateUrl(FoUrls.BRAND_LINE, requestData, productBrand));
        }

        final Set<ProductSku> skus = productMarketing.getProductSkus();
        if (Hibernate.isInitialized(skus) && skus != null) {
            for (Iterator<ProductSku> iterator = skus.iterator(); iterator.hasNext();) {
                final ProductSku productSku = (ProductSku) iterator.next();
                final ProductSku reloadedProductSku = productService.getProductSkuByCode(productSku.getCode());
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

        productMarketingViewBean.setFeatured(productMarketing.isFeatured());
        
        productMarketingViewBean.setCustomerProductRates(productService.calculateProductMarketingCustomerRatesByProductCode(productMarketing.getId()));

        return productMarketingViewBean;
    }
    
    /**
     * 
     */
    private ProductMarketingViewBean buildViewBeanProductMarketing(final RequestData requestData, final ProductMarketing productMarketing) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final ProductMarketingViewBean productMarketingViewBean = new ProductMarketingViewBean();

        productMarketingViewBean.setCode(productMarketing.getCode());
        productMarketingViewBean.setName(productMarketing.getName());
        productMarketingViewBean.setI18nName(productMarketing.getI18nName(localizationCode));
        productMarketingViewBean.setDescription(productMarketing.getDescription());

        productMarketingViewBean.setDefault(productMarketing.isDefault());

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date dateCreate = productMarketing.getDateCreate();
        if (dateCreate != null) {
            productMarketingViewBean.setCreatedDate(dateFormat.format(dateCreate));
        } else {
            productMarketingViewBean.setCreatedDate("NA");
        }

        Date dateUpdate = productMarketing.getDateUpdate();
        if (dateUpdate != null) {
            productMarketingViewBean.setUpdatedDate(dateFormat.format(dateUpdate));
        } else {
            productMarketingViewBean.setUpdatedDate("NA");
        }
        
        final Asset defaultBackgroundImage = productMarketing.getDefaultBackgroundImage();
        if (defaultBackgroundImage != null) {
            final String backgroundImage = requestUtil.getProductMarketingImageWebPath(request, defaultBackgroundImage);
            productMarketingViewBean.setBackgroundImage(backgroundImage);
        } else {
            productMarketingViewBean.setBackgroundImage("");
        }
        final Asset defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL.name());
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

        return productMarketingViewBean;
    }
    
    /**
     * 
     */
    public ProductSkuViewBean buildViewBeanProductSku(final RequestData requestData, final CatalogCategoryMaster catalogCategory, final ProductMarketing productMarketing, 
                                                      final ProductSku productSku) throws Exception {
        final ProductSkuViewBean productSkuViewBean = buildViewBeanProductSku(requestData, productMarketing, productSku);

        productSkuViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku));

        Map<String, String> getParams = new HashMap<String, String>();
        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE, productSku.getCode());
        getParams.put(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE, catalogCategory.getCode());

        productSkuViewBean.setAddToCartUrl(urlService.generateUrl(FoUrls.CART_ADD_ITEM, requestData, getParams));
        productSkuViewBean.setRemoveFromCartUrl(urlService.generateUrl(FoUrls.CART_REMOVE_ITEM, requestData, getParams));

        productSkuViewBean.setAddToWishlistUrl(urlService.generateUrl(FoUrls.WISHLIST_ADD_PRODUCT, requestData, getParams));
        productSkuViewBean.setRemoveFromWishlistUrl(urlService.generateUrl(FoUrls.WISHLIST_REMOVE_ITEM, requestData, getParams));

        return productSkuViewBean;
    }
    
    /**
     * 
     */
    public ProductSkuViewBean buildViewBeanProductSku(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, final ProductMarketing productMarketing, final ProductSku productSku)
            throws Exception {
        final ProductSkuViewBean productSkuViewBean = buildViewBeanProductSku(requestData, productMarketing, productSku);

        productSkuViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku));

        Map<String, String> getParams = new HashMap<String, String>();
        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE, productSku.getCode());
        getParams.put(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE, catalogCategory.getCode());

        productSkuViewBean.setAddToCartUrl(urlService.generateUrl(FoUrls.CART_ADD_ITEM, requestData, getParams));
        productSkuViewBean.setRemoveFromCartUrl(urlService.generateUrl(FoUrls.CART_REMOVE_ITEM, requestData, getParams));

        productSkuViewBean.setAddToWishlistUrl(urlService.generateUrl(FoUrls.WISHLIST_ADD_PRODUCT, requestData, getParams));
        productSkuViewBean.setRemoveFromWishlistUrl(urlService.generateUrl(FoUrls.WISHLIST_REMOVE_ITEM, requestData, getParams));

        return productSkuViewBean;
    }
    
    /**
     * 
     */
    public ProductSkuViewBean buildViewBeanProductSku(final RequestData requestData, final ProductMarketing productMarketing, final ProductSku productSku)
            throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        
        final ProductSkuViewBean productSkuViewBean = new ProductSkuViewBean();

        productSkuViewBean.setCode(productSku.getCode());
        productSkuViewBean.setName(productSku.getName());
        productSkuViewBean.setI18nName(productSku.getI18nName(localizationCode));
        productSkuViewBean.setDescription(productSku.getDescription());
        productSkuViewBean.setDefault(productSku.isDefault());
        
        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date dateCreate = productMarketing.getDateCreate();
        if (dateCreate != null) {
            productSkuViewBean.setCreatedDate(dateFormat.format(dateCreate));
        } else {
            productSkuViewBean.setCreatedDate("NA");
        }

        Date dateUpdate = productMarketing.getDateUpdate();
        if (dateUpdate != null) {
            productSkuViewBean.setUpdatedDate(dateFormat.format(dateUpdate));
        } else {
            productSkuViewBean.setUpdatedDate("NA");
        }
        
        final ProductSkuPrice productSkuPrice = productSku.getPrice(marketArea.getId(), retailer.getId());
        if(productSkuPrice != null){
            productSkuViewBean.setCatalogPrice(productSkuPrice.getCatalogPrice().toString());
            productSkuViewBean.setSalePrice(productSkuPrice.getSalePrice().toString());
            productSkuViewBean.setPriceWithCurrencySign(productSkuPrice.getPriceWithStandardCurrencySign());
        } else {
            productSkuViewBean.setPriceWithCurrencySign("NA");
        }
        
        final Asset defaultBackgroundImage = productSku.getDefaultBackgroundImage();
        if (defaultBackgroundImage != null) {
            String backgroundImage = requestUtil.getProductSkuImageWebPath(request, defaultBackgroundImage);
            productSkuViewBean.setBackgroundImage(backgroundImage);
        } else {
            productSkuViewBean.setBackgroundImage("");
        }
        final Asset defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL.name());
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
        
        productSkuViewBean.setProductMarketing(buildViewBeanProductMarketing(requestData, productMarketing));

        return productSkuViewBean;
    }
    
    /**
     * 
     */
    public ProductAssociationLinkViewBean buildViewBeanProductAssociationLink(final RequestData requestData, final CatalogCategoryMaster catalogCategory, final ProductMarketing productMarketing)
            throws Exception {
        final ProductAssociationLinkViewBean productAssociationLinkViewBean = buildViewBeanProductAssociationLink(requestData, productMarketing);

        productAssociationLinkViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));

        return productAssociationLinkViewBean;
    }
    
    /**
     * 
     */
    public ProductAssociationLinkViewBean buildViewBeanProductAssociationLink(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, final ProductMarketing productMarketing)
            throws Exception {
        final ProductAssociationLinkViewBean productAssociationLinkViewBean = buildViewBeanProductAssociationLink(requestData, productMarketing);

        productAssociationLinkViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));

        return productAssociationLinkViewBean;
    }
    
    /**
     * 
     */
    public ProductAssociationLinkViewBean buildViewBeanProductAssociationLink(final RequestData requestData, final ProductMarketing productMarketing)
            throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final ProductAssociationLinkViewBean productAssociationLinkViewBean = new ProductAssociationLinkViewBean();

        // TODO : WRONG : CROSS IS SKU not marketing

        productAssociationLinkViewBean.setName(productMarketing.getI18nName(localizationCode));
        productAssociationLinkViewBean.setDescription(productMarketing.getDescription());

        final Asset defaultBackgroundImage = productMarketing.getDefaultBackgroundImage();
        if (defaultBackgroundImage != null) {
            String backgroundImage = requestUtil.getProductMarketingImageWebPath(request, defaultBackgroundImage);
            productAssociationLinkViewBean.setBackgroundImage(backgroundImage);
        } else {
            productAssociationLinkViewBean.setBackgroundImage("");
        }
        final Asset defaultPaskshotImage = productMarketing.getDefaultPaskshotImage(ImageSize.SMALL.name());
        if (defaultPaskshotImage != null) {
            String carouselImage = requestUtil.getProductMarketingImageWebPath(request, defaultPaskshotImage);
            productAssociationLinkViewBean.setCrossLinkImage(carouselImage);
        } else {
            productAssociationLinkViewBean.setCrossLinkImage("");
        }
        final Asset defaultIconImage = productMarketing.getDefaultIconImage();
        if (defaultIconImage != null) {
            String iconImage = requestUtil.getProductMarketingImageWebPath(request, defaultIconImage);
            productAssociationLinkViewBean.setIconImage(iconImage);
        } else {
            productAssociationLinkViewBean.setIconImage("");
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
                cartItemViewBeans.add(buildViewBeanCartItem(requestData, cartItem));
            }
            cartViewBean.setCartItems(cartItemViewBeans);

            // SUB PART : Shippings
            final List<CartDeliveryMethodViewBean> cartDeliveryMethodViewBeans = new ArrayList<CartDeliveryMethodViewBean>();
            final Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
            if (deliveryMethods != null) {
                for (Iterator<DeliveryMethod> iterator = deliveryMethods.iterator(); iterator.hasNext();) {
                    final DeliveryMethod deliveryMethod = (DeliveryMethod) iterator.next();
                    final CartDeliveryMethodViewBean cartDeliveryMethodViewBean = new CartDeliveryMethodViewBean();
                    cartDeliveryMethodViewBean.setLabel(deliveryMethod.getName());
                    cartDeliveryMethodViewBean.setAmountWithCurrencySign(deliveryMethod.getPriceWithStandardCurrencySign(marketArea.getId(), retailer.getId()));
                    Object[] params = { deliveryMethod.getName() };
                    cartDeliveryMethodViewBean.setLabel(getSpecificMessage(ScopeWebMessage.COMMON, "shoppingcart.amount.deliveryMethods", params, locale));
                    cartDeliveryMethodViewBeans.add(cartDeliveryMethodViewBean);
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
    private CartItemViewBean buildViewBeanCartItem(final RequestData requestData, final CartItem cartItem) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final CartItemViewBean cartItemViewBean = new CartItemViewBean();

        cartItemViewBean.setSkuCode(cartItem.getProductSkuCode());
        final ProductSku productSku = productService.getProductSkuByCode(cartItem.getProductSkuCode());
        cartItem.setProductSku(productSku);
        
        cartItemViewBean.setI18nName(productSku.getI18nName(localizationCode));
        cartItemViewBean.setQuantity(cartItem.getQuantity());

        final Asset defaultPaskshotImage = productSku.getDefaultPaskshotImage(ImageSize.SMALL.name());
        if (defaultPaskshotImage != null) {
            String summaryImage = requestUtil.getProductMarketingImageWebPath(request, defaultPaskshotImage);
            cartItemViewBean.setSummaryImage(summaryImage);
        } else {
            cartItemViewBean.setSummaryImage("");
        }
        
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
        final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(cartItem.getCatalogCategoryCode());
        
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
            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            if (order.getExpectedDeliveryDate() != null) {
                orderViewBean.setExpectedDeliveryDate(dateFormat.format(order.getExpectedDeliveryDate()));
            } else {
                orderViewBean.setExpectedDeliveryDate("NA");
            }
            
            Date dateCreate = order.getDateCreate();
            if (dateCreate != null) {
                orderViewBean.setCreatedDate(dateFormat.format(dateCreate));
            } else {
                orderViewBean.setCreatedDate("NA");
            }

            Date dateUpdate = order.getDateUpdate();
            if (dateUpdate != null) {
                orderViewBean.setUpdatedDate(dateFormat.format(dateUpdate));
            } else {
                orderViewBean.setUpdatedDate("NA");
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
            ProductSku ProductSku = productService.getProductSkuByCode(orderItem.getProductSkuCode());
            orderItemViewBean.setName(ProductSku.getI18nName(localizationCode));
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
        deliveryMethodViewBean.setId(deliveryMethod.getId());

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

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (deliveryMethod.getDateCreate() != null) {
            deliveryMethodViewBean.setDateCreate(dateFormat.format(deliveryMethod.getDateCreate()));
        } else {
            deliveryMethodViewBean.setDateCreate("NA");
        }
        if (deliveryMethod.getDateUpdate() != null) {
            deliveryMethodViewBean.setDateUpdate(dateFormat.format(deliveryMethod.getDateUpdate()));
        } else {
            deliveryMethodViewBean.setDateUpdate("NA");
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
        taxViewBean.setId(tax.getId());

        taxViewBean.setVersion(tax.getVersion());
        taxViewBean.setName(tax.getName());
        taxViewBean.setDescription(tax.getDescription());
        taxViewBean.setCode(tax.getCode());
        
        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (tax.getDateCreate() != null) {
            taxViewBean.setDateCreate(dateFormat.format(tax.getDateCreate()));
        } else {
            taxViewBean.setDateCreate("NA");
        }
        if (tax.getDateUpdate() != null) {
            taxViewBean.setDateUpdate(dateFormat.format(tax.getDateUpdate()));
        } else {
            taxViewBean.setDateUpdate("NA");
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
    
    protected boolean menuIsActive(String currentUrl, List<String> scopeUrls){
        for (Iterator<String> iterator = scopeUrls.iterator(); iterator.hasNext();) {
            String scopeUrl = (String) iterator.next();
            if(currentUrl.contains(scopeUrl)){
                return true;
            }
        }
        return false;
    }
    
}