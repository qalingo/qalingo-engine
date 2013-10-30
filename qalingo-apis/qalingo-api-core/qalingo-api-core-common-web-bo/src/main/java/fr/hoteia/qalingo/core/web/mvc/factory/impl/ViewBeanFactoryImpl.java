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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import fr.hoteia.qalingo.core.domain.AbstractRuleReferential;
import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.BatchProcessObject;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
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
import fr.hoteia.qalingo.core.domain.ProductAssociationLink;
import fr.hoteia.qalingo.core.domain.ProductBrand;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.ProductSkuAttribute;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.RetailerAddress;
import fr.hoteia.qalingo.core.domain.Shipping;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.domain.UserConnectionLog;
import fr.hoteia.qalingo.core.domain.UserGroup;
import fr.hoteia.qalingo.core.domain.UserPermission;
import fr.hoteia.qalingo.core.domain.UserRole;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.pojo.RequestData;
import fr.hoteia.qalingo.core.service.MarketPlaceService;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.service.ProductSkuService;
import fr.hoteia.qalingo.core.service.ReferentialDataService;
import fr.hoteia.qalingo.core.web.cache.util.WebCacheHelper;
import fr.hoteia.qalingo.core.web.cache.util.WebElementType;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.viewbean.AssetViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.BatchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.BrandViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CatalogCategoryViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CatalogViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CurrencyReferentialViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingValueViewBean;
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
import fr.hoteia.qalingo.web.mvc.viewbean.ProductAssociationLinkViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductMarketingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductSkuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RuleViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ShippingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserConnectionLogValueBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserViewBean;

/**
 * 
 */
@Service("viewBeanFactory")
public class ViewBeanFactoryImpl extends AbstractBackofficeViewBeanFactory implements ViewBeanFactory {

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected BackofficeUrlService backofficeUrlService;

    @Autowired
    protected MarketPlaceService marketPlaceService;

    @Autowired
    private ProductMarketingService productMarketingService;

    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    protected ReferentialDataService referentialDataService;

    @Resource(name = "menuMarketNavigationCacheHelper")
    protected WebCacheHelper menuMarketNavigationCacheHelper;

    /**
     * @throws Exception
     * 
     */
    public CommonViewBean buildCommonViewBean(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketPlace marketPlace = requestData.getMarketPlace();
        final Market market = requestData.getMarket();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getLocalization();
        final Retailer retailer = requestData.getRetailer();

        final CommonViewBean commonViewBean = new CommonViewBean();
        final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request);
        commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

        commonViewBean.setHomeUrl(backofficeUrlService.generateUrl(BoUrls.HOME, requestData));
        commonViewBean.setLoginUrl(backofficeUrlService.generateUrl(BoUrls.LOGIN, requestData));
        commonViewBean.setLogoutUrl(backofficeUrlService.generateUrl(BoUrls.LOGOUT, requestData));
        commonViewBean.setUserDetailsUrl(backofficeUrlService.generateUrl(BoUrls.USER_DETAILS, requestData));

        commonViewBean.setCurrentMarketPlace(buildMarketPlaceViewBean(requestData, marketPlace));
        commonViewBean.setCurrentMarket(buildMarketViewBean(requestData, market));
        commonViewBean.setCurrentMarketArea(buildMarketAreaViewBean(requestData, marketArea));
        commonViewBean.setCurrentMarketLocalization(buildLocalizationViewBeanByMarketArea(requestData, localization));
        commonViewBean.setCurrentRetailer(buildRetailerViewBean(requestData, retailer));

        return commonViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<MenuViewBean> buildMenuViewBeans(final RequestData requestData) throws Exception {
        return new ArrayList<MenuViewBean>();
    }

    /**
     * @throws Exception
     * 
     */
    public List<MenuViewBean> buildMorePageMenuViewBeans(final RequestData requestData) throws Exception {
        final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();

        // TODO : denis : move this part in the global list menu java/vm

        MenuViewBean menu = new MenuViewBean();
        menu.setCssIcon("icon-paper-clip");
        menu.setName("FAQ");
        menu.setUrl(backofficeUrlService.generateUrl(BoUrls.FAQ, requestData));
        menuViewBeans.add(menu);

        return menuViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public List<FooterMenuViewBean> buildFooterMenuViewBeans(final RequestData requestData) throws Exception {
        final Localization localization = requestData.getLocalization();
        final Locale locale = localization.getLocale();

        final List<FooterMenuViewBean> footerMenuViewBeans = new ArrayList<FooterMenuViewBean>();

        FooterMenuViewBean footerMenuList = new FooterMenuViewBean();
        footerMenuList.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
        footerMenuList.setUrl(backofficeUrlService.generateUrl(BoUrls.HOME, requestData));
        footerMenuViewBeans.add(footerMenuList);

        return footerMenuViewBeans;
    }

    /**
     * @throws Exception
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
     * @throws Exception
     * 
     */
    public MarketPlaceViewBean buildMarketPlaceViewBean(final RequestData requestData, final MarketPlace marketPlace) throws Exception {
        final Market defaultMarket = marketPlace.getDefaultMarket();
        final MarketArea defaultMarketArea = defaultMarket.getDefaultMarketArea();
        final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
        final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();

        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketPlace(marketPlace);
        requestDataChangecontext.setMarket(defaultMarket);
        requestDataChangecontext.setMarketArea(defaultMarketArea);
        requestDataChangecontext.setLocalization(defaultLocalization);
        requestDataChangecontext.setRetailer(defaultRetailer);

        MarketPlaceViewBean marketPlaceViewBean = new MarketPlaceViewBean();
        marketPlaceViewBean.setName(marketPlace.getName());

        marketPlaceViewBean.setUrl(backofficeUrlService.buildChangeContextUrl(requestDataChangecontext));

        marketPlaceViewBean.setMarkets(buildMarketViewBeansByMarketPlace(requestData, marketPlace, new ArrayList<Market>(marketPlace.getMarkets())));

        return marketPlaceViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<MarketViewBean> buildMarketViewBeansByMarketPlace(final RequestData requestData, final MarketPlace marketPlace, final List<Market> markets) throws Exception {
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
     * @throws Exception
     * 
     */
    public MarketViewBean buildMarketViewBean(final RequestData requestData, final Market market) throws Exception {
        final MarketPlace marketPlace = market.getMarketPlace();
        final MarketArea defaultMarketArea = market.getDefaultMarketArea();
        final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
        final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();

        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketPlace(marketPlace);
        requestDataChangecontext.setMarket(market);
        requestDataChangecontext.setMarketArea(defaultMarketArea);
        requestDataChangecontext.setLocalization(defaultLocalization);
        requestDataChangecontext.setRetailer(defaultRetailer);

        final MarketViewBean marketViewBean = new MarketViewBean();
        marketViewBean.setName(market.getName());

        marketViewBean.setChangeContextUrl(backofficeUrlService.buildChangeContextUrl(requestDataChangecontext));

        marketViewBean.setMarketAreas(buildMarketAreaViewBeansByMarket(requestData, market, new ArrayList<MarketArea>(market.getMarketAreas())));

        return marketViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<MarketAreaViewBean> buildMarketAreaViewBeansByMarket(final RequestData requestData, final Market market, final List<MarketArea> marketAreas) throws Exception {
        final Localization localization = requestData.getLocalization();
        final WebElementType marketAreaElementType = WebElementType.MARKET_AREA_VIEW_BEAN_LIST;
        final String marketAreaPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
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
     * @throws Exception
     * 
     */
    public MarketAreaViewBean buildMarketAreaViewBean(final RequestData requestData, final MarketArea marketArea) throws Exception {
        final Market market = marketArea.getMarket();
        final MarketPlace marketPlace = market.getMarketPlace();
        final Localization defaultLocalization = marketArea.getDefaultLocalization();
        final Retailer defaultRetailer = marketArea.getDefaultRetailer();

        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketPlace(marketPlace);
        requestDataChangecontext.setMarket(market);
        requestDataChangecontext.setMarketArea(marketArea);
        requestDataChangecontext.setLocalization(defaultLocalization);
        requestDataChangecontext.setRetailer(defaultRetailer);

        final MarketAreaViewBean marketAreaViewBean = new MarketAreaViewBean();
        marketAreaViewBean.setName(marketArea.getName());

        marketAreaViewBean.setChangeContextUrl(backofficeUrlService.buildChangeContextUrl(requestDataChangecontext));

        return marketAreaViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public LocalizationViewBean buildLocalizationViewBeanByMarketArea(final RequestData requestData, final Localization localization) throws Exception {
        final Locale locale = localization.getLocale();
        final String localeCodeNavigation = localization.getCode();

        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setLocalization(localization);

        final LocalizationViewBean localizationViewBean = new LocalizationViewBean();

        if (StringUtils.isNotEmpty(localeCodeNavigation) && localeCodeNavigation.length() == 2) {
            localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation.toLowerCase(), locale));
        } else {
            localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation, locale));
        }

        localizationViewBean.setChangeContextUrl(backofficeUrlService.buildChangeContextUrl(requestDataChangecontext));

        return localizationViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<LocalizationViewBean> buildLocalizationViewBeansByMarketArea(final RequestData requestData, final List<Localization> localizations) throws Exception {
        final List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
        if (localizations != null) {
            for (Iterator<Localization> iterator = localizations.iterator(); iterator.hasNext();) {
                Localization localization = (Localization) iterator.next();
                localizationViewBeans.add(buildLocalizationViewBeanByMarketArea(requestData, localization));
            }
        }
        return localizationViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public List<LocalizationViewBean> buildLocalizationViewBeans(final RequestData requestData, final List<Localization> localizations) throws Exception {
        final List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
        if (localizations != null) {
            for (Iterator<Localization> iterator = localizations.iterator(); iterator.hasNext();) {
                Localization localization = (Localization) iterator.next();
                localizationViewBeans.add(buildLocalizationViewBean(requestData, localization));
            }
        }
        return localizationViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public LocalizationViewBean buildLocalizationViewBean(final RequestData requestData, final Localization localization) throws Exception {
        final Locale locale = localization.getLocale();
        final String localeCodeNavigation = localization.getCode();

        final LocalizationViewBean localizationViewBean = new LocalizationViewBean();

        if (StringUtils.isNotEmpty(localeCodeNavigation) && localeCodeNavigation.length() == 2) {
            localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation.toLowerCase(), locale));
        } else {
            localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation, locale));
        }

        localizationViewBean.setCode(localization.getCode());
        localizationViewBean.setCountry(localization.getCountry());
        localizationViewBean.setLanguage(localization.getLanguage());
        localizationViewBean.setDescription(localization.getDescription());
        localizationViewBean.setImg(localization.getCode());

        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setLocalization(localization);
        localizationViewBean.setChangeContextUrl(backofficeUrlService.buildChangeLanguageUrl(requestDataChangecontext));

        return localizationViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<RetailerViewBean> buildRetailerViewBeans(final RequestData requestData) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getLocalization();

        final WebElementType retailerElementType = WebElementType.RETAILER_VIEW_BEAN_LIST;
        final String retailerPrefixCacheKey = menuMarketNavigationCacheHelper.buildGlobalPrefixKey(localization);
        final String retailerCacheKey = retailerPrefixCacheKey + "_RETAILER";
        List<RetailerViewBean> retailerViewBeans = (List<RetailerViewBean>) menuMarketNavigationCacheHelper.getFromCache(retailerElementType, retailerCacheKey);
        if (retailerViewBeans == null) {
            final List<Retailer> retailers = new ArrayList<Retailer>(marketArea.getRetailers());
            retailerViewBeans = new ArrayList<RetailerViewBean>();
            for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
                final Retailer retailerIt = (Retailer) iterator.next();
                retailerViewBeans.add(buildRetailerViewBean(requestData, retailerIt));
            }
            menuMarketNavigationCacheHelper.addToCache(retailerElementType, retailerCacheKey, retailerViewBeans);
        }
        return retailerViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public RetailerViewBean buildRetailerViewBean(final RequestData requestData, final Retailer retailer) throws Exception {
        final Localization localization = requestData.getLocalization();
        final Locale locale = localization.getLocale();

        final RetailerViewBean retailerViewBean = new RetailerViewBean();

        // CLONE THE CURRENT REQUEST DATE TO BUILD THE CHANGE CONTEXT URL (MENU)
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setRetailer(retailer);
        retailerViewBean.setChangeContextUrl(backofficeUrlService.buildChangeContextUrl(requestDataChangecontext));

        retailerViewBean.setId(retailer.getId());
        retailerViewBean.setVersion(retailer.getVersion());
        retailerViewBean.setCode(retailer.getCode());
        retailerViewBean.setName(retailer.getName());
        retailerViewBean.setDescription(retailer.getDescription());

        if (retailer.getAddresses() != null) {
            RetailerAddress defaultAddress = retailer.getDefaultAddress();
            if (defaultAddress != null) {
                retailerViewBean.getDefaultAddress().setAddress1(defaultAddress.getAddress1());
                retailerViewBean.getDefaultAddress().setAddress2(defaultAddress.getAddress2());
                retailerViewBean.getDefaultAddress().setAddressAdditionalInformation(defaultAddress.getAddressAdditionalInformation());
                retailerViewBean.getDefaultAddress().setPostalCode(defaultAddress.getPostalCode());
                retailerViewBean.getDefaultAddress().setCityLabel(defaultAddress.getCity());
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

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date createdDate = retailer.getDateCreate();
        if (createdDate != null) {
            retailerViewBean.setCreatedDate(dateFormat.format(createdDate));
        } else {
            retailerViewBean.setCreatedDate("NA");
        }
        Date updatedDate = retailer.getDateUpdate();
        if (updatedDate != null) {
            retailerViewBean.setUpdatedDate(dateFormat.format(updatedDate));
        } else {
            retailerViewBean.setUpdatedDate("NA");
        }

        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(RequestConstants.REQUEST_PARAMETER_RETAILER_DETAILS_CODE, retailer.getCode());
        String detailsUrl = backofficeUrlService.generateUrl(BoUrls.RETAILER_DETAILS, requestData, urlParams);
        String editUrl = backofficeUrlService.generateUrl(BoUrls.RETAILER_EDIT, requestData, urlParams);

        retailerViewBean.setDetailsUrl(detailsUrl);
        retailerViewBean.setEditUrl(editUrl);

        return retailerViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public CatalogViewBean buildMasterCatalogViewBean(final RequestData requestData, final CatalogMaster catalogMaster, final List<CatalogCategoryMaster> catalogCategories) throws Exception {
        final CatalogViewBean catalogViewBean = new CatalogViewBean();
        catalogViewBean.setBusinessName(catalogMaster.getBusinessName());
        catalogViewBean.setCode(catalogMaster.getCode());

        if (catalogCategories != null) {
            catalogViewBean.setCategories(buildMasterCatalogCategoryViewBeans(requestData, catalogCategories, true));
        }

        catalogViewBean.setAddRootCategoryUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_ADD, requestData));

        return catalogViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public CatalogViewBean buildVirtualCatalogViewBean(final RequestData requestData, final CatalogVirtual catalogVirtual, final List<CatalogCategoryVirtual> catalogCategories) throws Exception {
        final CatalogViewBean catalogViewBean = new CatalogViewBean();
        catalogViewBean.setBusinessName(catalogVirtual.getBusinessName());
        catalogViewBean.setCode(catalogVirtual.getCode());

        if (catalogCategories != null) {
            catalogViewBean.setCategories(buildVirtualCatalogCategoryViewBeans(requestData, catalogCategories, true));
        }

        catalogViewBean.setAddRootCategoryUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_ADD, requestData));

        return catalogViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<CatalogCategoryViewBean> buildMasterCatalogCategoryViewBeans(final RequestData requestData, final List<CatalogCategoryMaster> catalogCategories, boolean fullPopulate) throws Exception {
        List<CatalogCategoryViewBean> categoryViewBeans = new ArrayList<CatalogCategoryViewBean>();
        for (Iterator<CatalogCategoryMaster> iterator = catalogCategories.iterator(); iterator.hasNext();) {
            final CatalogCategoryMaster category = (CatalogCategoryMaster) iterator.next();
            categoryViewBeans.add(buildMasterCatalogCategoryViewBean(requestData, category, fullPopulate));
        }
        return categoryViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public List<CatalogCategoryViewBean> buildVirtualCatalogCategoryViewBeans(final RequestData requestData, final List<CatalogCategoryVirtual> catalogCategories, boolean fullPopulate)
            throws Exception {
        List<CatalogCategoryViewBean> categoryViewBeans = new ArrayList<CatalogCategoryViewBean>();
        for (Iterator<CatalogCategoryVirtual> iterator = catalogCategories.iterator(); iterator.hasNext();) {
            final CatalogCategoryVirtual category = (CatalogCategoryVirtual) iterator.next();
            categoryViewBeans.add(buildVirtualCatalogCategoryViewBean(requestData, category, fullPopulate));
        }
        return categoryViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public CatalogCategoryViewBean buildMasterCatalogCategoryViewBean(final RequestData requestData, final CatalogCategoryMaster category, boolean fullPopulate) throws Exception {
        final CatalogCategoryViewBean catalogCategoryViewBean = new CatalogCategoryViewBean();

        if (category != null) {
            final String categoryCode = category.getCode();

            catalogCategoryViewBean.setBusinessName(category.getBusinessName());
            catalogCategoryViewBean.setCode(categoryCode);
            catalogCategoryViewBean.setDescription(category.getDescription());

            if (category.getDefaultParentCatalogCategory() != null) {
                catalogCategoryViewBean.setDefaultParentCategory(buildMasterCatalogCategoryViewBean(requestData, category.getDefaultParentCatalogCategory(), false));
            }

            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            Date createdDate = category.getDateCreate();
            if (createdDate != null) {
                catalogCategoryViewBean.setCreatedDate(dateFormat.format(createdDate));
            } else {
                catalogCategoryViewBean.setCreatedDate("NA");
            }
            Date updatedDate = category.getDateUpdate();
            if (updatedDate != null) {
                catalogCategoryViewBean.setUpdatedDate(dateFormat.format(updatedDate));
            } else {
                catalogCategoryViewBean.setUpdatedDate("NA");
            }

            if (fullPopulate) {
                if (category.getCatalogCategories() != null) {
                    catalogCategoryViewBean.setSubCategories(buildMasterCatalogCategoryViewBeans(requestData, new ArrayList<CatalogCategoryMaster>(category.getCatalogCategories()), fullPopulate));
                }

                Set<CatalogCategoryMasterAttribute> globalAttributes = category.getCatalogCategoryGlobalAttributes();
                for (Iterator<CatalogCategoryMasterAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                    catalogCategoryViewBean.getGlobalAttributes().put(catalogCategoryMasterAttribute.getAttributeDefinition().getCode(), catalogCategoryMasterAttribute.getValueAsString());
                }

                Set<CatalogCategoryMasterAttribute> marketAreaAttributes = category.getCatalogCategoryMarketAreaAttributes();
                for (Iterator<CatalogCategoryMasterAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                    catalogCategoryViewBean.getMarketAreaAttributes().put(catalogCategoryMasterAttribute.getAttributeDefinition().getCode(), catalogCategoryMasterAttribute.getValueAsString());
                }

                List<ProductMarketingViewBean> productMarketingViewBeans = buildProductMarketingViewBeans(requestData, new ArrayList<ProductMarketing>(category.getProductMarketings()), true);
                catalogCategoryViewBean.setProductMarketings(productMarketingViewBeans);

                int countProduct = category.getProductMarketings().size();
                for (Iterator<CatalogCategoryViewBean> iterator = catalogCategoryViewBean.getSubCategories().iterator(); iterator.hasNext();) {
                    CatalogCategoryViewBean subCategoryViewBean = (CatalogCategoryViewBean) iterator.next();
                    countProduct = countProduct + subCategoryViewBean.getCountProduct();
                }
                catalogCategoryViewBean.setCountProduct(countProduct);

                Set<Asset> assets = category.getAssetsIsGlobal();
                for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                    Asset asset = (Asset) iterator.next();
                    catalogCategoryViewBean.getAssets().add(buildAssetViewBean(requestData, asset));
                }
            }

            catalogCategoryViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, category));
            catalogCategoryViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_EDIT, requestData, category));
        }

        return catalogCategoryViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public CatalogCategoryViewBean buildVirtualCatalogCategoryViewBean(final RequestData requestData, final CatalogCategoryVirtual category, boolean fullPopulate) throws Exception {
        final CatalogCategoryViewBean catalogCategoryViewBean = new CatalogCategoryViewBean();

        if (category != null) {
            final String categoryCode = category.getCode();

            catalogCategoryViewBean.setBusinessName(category.getBusinessName());
            catalogCategoryViewBean.setCode(categoryCode);
            catalogCategoryViewBean.setDescription(category.getDescription());

            if (category.getDefaultParentCatalogCategory() != null) {
                catalogCategoryViewBean.setDefaultParentCategory(buildVirtualCatalogCategoryViewBean(requestData, category.getDefaultParentCatalogCategory(), false));
            }

            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            Date createdDate = category.getDateCreate();
            if (createdDate != null) {
                catalogCategoryViewBean.setCreatedDate(dateFormat.format(createdDate));
            } else {
                catalogCategoryViewBean.setCreatedDate("NA");
            }
            Date updatedDate = category.getDateUpdate();
            if (updatedDate != null) {
                catalogCategoryViewBean.setUpdatedDate(dateFormat.format(updatedDate));
            } else {
                catalogCategoryViewBean.setUpdatedDate("NA");
            }

            if (fullPopulate) {
                if (category.getCatalogCategories() != null) {
                    catalogCategoryViewBean.setSubCategories(buildVirtualCatalogCategoryViewBeans(requestData, new ArrayList<CatalogCategoryVirtual>(category.getCatalogCategories()), fullPopulate));
                }

                Set<CatalogCategoryVirtualAttribute> globalAttributes = category.getCatalogCategoryGlobalAttributes();
                for (Iterator<CatalogCategoryVirtualAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
                    catalogCategoryViewBean.getGlobalAttributes().put(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode(), catalogCategoryVirtualAttribute.getValueAsString());
                }

                Set<CatalogCategoryVirtualAttribute> marketAreaAttributes = category.getCatalogCategoryMarketAreaAttributes();
                for (Iterator<CatalogCategoryVirtualAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
                    catalogCategoryViewBean.getMarketAreaAttributes().put(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode(), catalogCategoryVirtualAttribute.getValueAsString());
                }

                List<ProductMarketingViewBean> productMarketingViewBeans = buildProductMarketingViewBeans(requestData, new ArrayList<ProductMarketing>(category.getProductMarketings()), true);
                catalogCategoryViewBean.setProductMarketings(productMarketingViewBeans);

                int countProduct = category.getProductMarketings().size();
                for (Iterator<CatalogCategoryViewBean> iterator = catalogCategoryViewBean.getSubCategories().iterator(); iterator.hasNext();) {
                    CatalogCategoryViewBean subCategoryViewBean = (CatalogCategoryViewBean) iterator.next();
                    countProduct = countProduct + subCategoryViewBean.getCountProduct();
                }
                catalogCategoryViewBean.setCountProduct(countProduct);

                Set<Asset> assets = category.getAssetsIsGlobal();
                for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                    Asset asset = (Asset) iterator.next();
                    catalogCategoryViewBean.getAssets().add(buildAssetViewBean(requestData, asset));
                }
            }

            catalogCategoryViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_DETAILS, requestData, category));
            catalogCategoryViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_EDIT, requestData, category));
        }

        return catalogCategoryViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<ProductMarketingViewBean> buildProductMarketingViewBeans(final RequestData requestData, final List<ProductMarketing> productMarketings, boolean withDependency) throws Exception {
        List<ProductMarketingViewBean> products = new ArrayList<ProductMarketingViewBean>();
        for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            products.add(buildProductMarketingViewBean(requestData, productMarketing, withDependency));
        }
        return products;
    }

    /**
     * @throws Exception
     * 
     */
    public ProductMarketingViewBean buildProductMarketingViewBean(final RequestData requestData, final ProductMarketing productMarketing, boolean withDependency) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Long marketAreaId = marketArea.getId();

        final ProductMarketingViewBean productMarketingViewBean = new ProductMarketingViewBean();

        Integer position = productMarketing.getOrder(marketAreaId);
        if (position != null) {
            productMarketingViewBean.setPositionItem(position);
        }
        productMarketingViewBean.setBusinessName(productMarketing.getBusinessName());
        productMarketingViewBean.setCode(productMarketing.getCode());
        productMarketingViewBean.setDescription(productMarketing.getDescription());
        productMarketingViewBean.setDefault(productMarketing.isDefault());
        productMarketingViewBean.setBrand(buildBrandViewBean(requestData, productMarketing.getProductBrand()));

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

        if (withDependency) {
            Set<ProductSku> productSkus = productMarketing.getProductSkus();
            for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
                ProductSku productSku = (ProductSku) iterator.next();
                productMarketingViewBean.getProductSkus().add(buildProductSkuViewBean(requestData, productSku));
            }

            Set<ProductAssociationLink> productCrossLinks = productMarketing.getProductAssociationLinks();
            for (Iterator<ProductAssociationLink> iterator = productCrossLinks.iterator(); iterator.hasNext();) {
                ProductAssociationLink productAssociationLink = (ProductAssociationLink) iterator.next();
                productMarketingViewBean.getProductAssociationLinks().add(buildProductAssociationLinkViewBean(requestData, productAssociationLink));
            }

            Set<Asset> assets = productMarketing.getAssetsIsGlobal();
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                productMarketingViewBean.getAssets().add(buildAssetViewBean(requestData, asset));
            }

        }

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date createdDate = productMarketing.getDateCreate();
        if (createdDate != null) {
            productMarketingViewBean.setCreatedDate(dateFormat.format(createdDate));
        } else {
            productMarketingViewBean.setCreatedDate("NA");
        }
        Date updatedDate = productMarketing.getDateUpdate();
        if (updatedDate != null) {
            productMarketingViewBean.setUpdatedDate(dateFormat.format(updatedDate));
        } else {
            productMarketingViewBean.setUpdatedDate("NA");
        }

        productMarketingViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing));
        productMarketingViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_EDIT, requestData, productMarketing));

        return productMarketingViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public ProductAssociationLinkViewBean buildProductAssociationLinkViewBean(final RequestData requestData, final ProductAssociationLink productAssociationLink) throws Exception {
        final ProductAssociationLinkViewBean productCrossLinkViewBean = new ProductAssociationLinkViewBean();

        productCrossLinkViewBean.setOrderItem(productAssociationLink.getRankPosition());
        productCrossLinkViewBean.setName(productAssociationLink.getProductMarketing().getBusinessName());
        productCrossLinkViewBean.setType(productAssociationLink.getType().name());
        productCrossLinkViewBean.setDescription(productAssociationLink.getType().name());
        productCrossLinkViewBean.setProductDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productAssociationLink.getProductMarketing()));

        return productCrossLinkViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public ProductSkuViewBean buildProductSkuViewBean(final RequestData requestData, final ProductSku productSku) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Long marketAreaId = marketArea.getId();

        final ProductSkuViewBean productSkuViewBean = new ProductSkuViewBean();

        Integer position = productSku.getOrder(marketAreaId);
        if (position != null) {
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

        productSkuViewBean.setProductMarketing(buildProductMarketingViewBean(requestData, productSku.getProductMarketing(), false));

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

        Set<Asset> assets = productSku.getAssetsIsGlobal();
        for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
            Asset asset = (Asset) iterator.next();
            productSkuViewBean.getAssets().add(buildAssetViewBean(requestData, asset));
        }

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date createdDate = productSku.getDateCreate();
        if (createdDate != null) {
            productSkuViewBean.setCreatedDate(dateFormat.format(createdDate));
        } else {
            productSkuViewBean.setCreatedDate("NA");
        }
        Date updatedDate = productSku.getDateUpdate();
        if (updatedDate != null) {
            productSkuViewBean.setUpdatedDate(dateFormat.format(updatedDate));
        } else {
            productSkuViewBean.setUpdatedDate("NA");
        }

        productSkuViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, productSku));
        productSkuViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_EDIT, requestData, productSku));

        return productSkuViewBean;
    }

    /**
     * 
     */
    public BrandViewBean buildBrandViewBean(final RequestData requestData, final ProductBrand productBrand) throws Exception {
        final BrandViewBean brandViewBean = new BrandViewBean();

        brandViewBean.setBusinessName(productBrand.getName());
        brandViewBean.setCode(productBrand.getCode());
        // brandViewBean.setBrandDetailsUrl(brandDetailsUrl);
        // brandViewBean.setBrandLineDetailsUrl(brandLineDetailsUrl);

        return brandViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public AssetViewBean buildAssetViewBean(final RequestData requestData, final Asset asset) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final String assetCode = asset.getCode();

        AssetViewBean assetViewBean = new AssetViewBean();

        assetViewBean.setName(asset.getName());
        assetViewBean.setCode(assetCode);
        assetViewBean.setDescription(asset.getDescription());
        assetViewBean.setPath(asset.getPath());
        if (asset.getScope() != null) {
            assetViewBean.setScope(asset.getScope().getPropertyKey());
        }
        if (asset.getType() != null) {
            assetViewBean.setType(asset.getType().getPropertyKey());
        }
        if (asset.getSize() != null) {
            assetViewBean.setSize(asset.getSize().getPropertyKey());
        }
        assetViewBean.setFileSize("" + asset.getFileSize());
        assetViewBean.setIsDefault("" + asset.isDefault());

        assetViewBean.setAbsoluteWebPath(requestUtil.getProductMarketingImageWebPath(request, asset));

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date createdDate = asset.getDateCreate();
        if (createdDate != null) {
            assetViewBean.setCreatedDate(dateFormat.format(createdDate));
        } else {
            assetViewBean.setCreatedDate("NA");
        }
        Date updatedDate = asset.getDateUpdate();
        if (updatedDate != null) {
            assetViewBean.setUpdatedDate(dateFormat.format(updatedDate));
        } else {
            assetViewBean.setUpdatedDate("NA");
        }

        assetViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.ASSET_DETAILS, requestData, asset));
        assetViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.ASSET_EDIT, requestData, asset));

        return assetViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public LegalTermsViewBean buildLegalTermsViewBean(final RequestData requestData) throws Exception {
        final Localization localization = requestData.getLocalization();
        final Locale locale = localization.getLocale();

        final LegalTermsViewBean legalTerms = new LegalTermsViewBean();

        legalTerms.setWarning(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "warning", locale));
        legalTerms.setCopyright(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "copyright", locale));

        return legalTerms;
    }

    /**
     * @throws Exception
     * 
     */
    public SecurityViewBean buildSecurityViewBean(final RequestData requestData) throws Exception {
        final SecurityViewBean security = new SecurityViewBean();
        security.setLoginUrl(backofficeUrlService.generateUrl(BoUrls.LOGIN, requestData));
        security.setSubmitLoginUrl(backofficeUrlService.buildSpringSecurityCheckUrl(requestData));
        security.setForgottenPasswordUrl(backofficeUrlService.generateUrl(BoUrls.FORGOTTEN_PASSWORD, requestData));
        return security;
    }

    /**
     * @throws Exception
     * 
     */
    public UserViewBean buildUserViewBean(final RequestData requestData, final User user) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final UserViewBean userViewBean = new UserViewBean();
        userViewBean.setId(user.getId());
        userViewBean.setLogin(user.getLogin());
        userViewBean.setFirstname(user.getFirstname());
        userViewBean.setLastname(user.getLastname());
        userViewBean.setEmail(user.getEmail());
        userViewBean.setPassword(user.getPassword());
        userViewBean.setActive(user.isActive());

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (user.getDateCreate() != null) {
            userViewBean.setDateCreate(dateFormat.format(user.getDateCreate()));
        } else {
            userViewBean.setDateCreate("NA");
        }
        if (user.getDateUpdate() != null) {
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

        final Set<UserConnectionLog> connectionLogs = user.getConnectionLogs();
        for (Iterator<UserConnectionLog> iteratorUserConnectionLog = connectionLogs.iterator(); iteratorUserConnectionLog.hasNext();) {
            UserConnectionLog userConnectionLog = (UserConnectionLog) iteratorUserConnectionLog.next();
            UserConnectionLogValueBean userConnectionLogValueBean = new UserConnectionLogValueBean();
            userConnectionLogValueBean.setDate(dateFormat.format(userConnectionLog.getLoginDate()));
            if (StringUtils.isNotEmpty(userConnectionLog.getHost())) {
                userConnectionLogValueBean.setHost(userConnectionLog.getHost());
            } else {
                userConnectionLogValueBean.setHost("NA");
            }
            if (StringUtils.isNotEmpty(userConnectionLog.getAddress())) {
                userConnectionLogValueBean.setAddress(userConnectionLog.getAddress());
            } else {
                userConnectionLogValueBean.setAddress("NA");
            }
            userViewBean.getUserConnectionLogs().add(userConnectionLogValueBean);
        }

        final List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add("form");
        userViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

        userViewBean.setUserDetailsUrl(backofficeUrlService.generateUrl(BoUrls.USER_DETAILS, requestData, user));
        userViewBean.setUserEditUrl(backofficeUrlService.generateUrl(BoUrls.USER_EDIT, requestData, user));

        return userViewBean;
    }

    /**
     * 
     */
    public List<UserViewBean> buildUserViewBeans(final RequestData requestData, final List<User> users) throws Exception {
        final List<UserViewBean> userViewBeans = new ArrayList<UserViewBean>();
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            userViewBeans.add(buildUserViewBean(requestData, user));
        }
        return userViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public ShippingViewBean buildShippingViewBean(final RequestData requestData, final Shipping shipping) throws Exception {
        ShippingViewBean shippingViewBean = new ShippingViewBean();
        shippingViewBean.setId(shipping.getId());

        shippingViewBean.setVersion(shipping.getVersion());
        shippingViewBean.setName(shipping.getName());
        shippingViewBean.setDescription(shipping.getDescription());
        shippingViewBean.setCode(shipping.getCode());
        shippingViewBean.setPrice(shipping.getPrice());

        shippingViewBean.setMarketAreaId(shipping.getMarketAreaId());

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (shipping.getDateCreate() != null) {
            shippingViewBean.setDateCreate(dateFormat.format(shipping.getDateCreate()));
        } else {
            shippingViewBean.setDateCreate("NA");
        }
        if (shipping.getDateUpdate() != null) {
            shippingViewBean.setDateUpdate(dateFormat.format(shipping.getDateUpdate()));
        } else {
            shippingViewBean.setDateUpdate("NA");
        }

        shippingViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.SHIPPING_DETAILS, requestData, shipping));
        shippingViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.SHIPPING_EDIT, requestData, shipping));

        return shippingViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public OrderViewBean buildOrderViewBean(final RequestData requestData, final Order order) throws Exception {
        OrderViewBean orderViewBean = new OrderViewBean();
        orderViewBean.setId(order.getId());

        orderViewBean.setVersion(order.getVersion());
        orderViewBean.setStatus(order.getStatus());
        orderViewBean.setOrderNum(order.getOrderNum());
        orderViewBean.setCustomerId(order.getCustomerId());
        orderViewBean.setBillingAddressId(order.getBillingAddressId());
        orderViewBean.setShippingAddressId(order.getShippingAddressId());

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (order.getDateCreate() != null) {
            orderViewBean.setDateCreate(dateFormat.format(order.getDateCreate()));
        } else {
            orderViewBean.setDateCreate("NA");
        }
        if (order.getDateUpdate() != null) {
            orderViewBean.setDateUpdate(dateFormat.format(order.getDateUpdate()));
        } else {
            orderViewBean.setDateUpdate("NA");
        }

        orderViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.ORDER_DETAILS, requestData, order));

        return orderViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public RuleViewBean buildRuleViewBean(final RequestData requestData, final AbstractRuleReferential rule) throws Exception {
        RuleViewBean ruleViewBean = new RuleViewBean();
        ruleViewBean.setId(rule.getId());

        ruleViewBean.setVersion(rule.getVersion());
        ruleViewBean.setName(rule.getName());
        ruleViewBean.setDescription(rule.getDescription());
        ruleViewBean.setSalience(rule.getSalience());

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (rule.getDateCreate() != null) {
            ruleViewBean.setDateCreate(dateFormat.format(rule.getDateCreate()));
        } else {
            ruleViewBean.setDateCreate("NA");
        }
        if (rule.getDateUpdate() != null) {
            ruleViewBean.setDateUpdate(dateFormat.format(rule.getDateUpdate()));
        } else {
            ruleViewBean.setDateUpdate("NA");
        }

        ruleViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.RULE_DETAILS, requestData, rule));
        ruleViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.RULE_EDIT, requestData, rule));

        return ruleViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public CustomerViewBean buildCustomerViewBean(final RequestData requestData, final Customer customer) throws Exception {
        CustomerViewBean customerViewBean = new CustomerViewBean();
        customerViewBean.setId(customer.getId());

        customerViewBean.setVersion(customer.getVersion());
        customerViewBean.setLogin(customer.getLogin());
        customerViewBean.setTitle(customer.getTitle());
        customerViewBean.setFirstname(customer.getFirstname());
        customerViewBean.setLastname(customer.getLastname());
        customerViewBean.setEmail(customer.getEmail());
        customerViewBean.setPassword(customer.getPassword());
        customerViewBean.setDefaultLocale(customer.getDefaultLocale());
        customerViewBean.setActive(customer.isActive());

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (customer.getDateCreate() != null) {
            customerViewBean.setDateCreate(dateFormat.format(customer.getDateCreate()));
        } else {
            customerViewBean.setDateCreate("NA");
        }
        if (customer.getDateUpdate() != null) {
            customerViewBean.setDateUpdate(dateFormat.format(customer.getDateUpdate()));
        } else {
            customerViewBean.setDateUpdate("NA");
        }

        customerViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.CUSTOMER_DETAILS, requestData, customer));
        customerViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.CUSTOMER_EDIT, requestData, customer));

        return customerViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<GlobalSearchViewBean> buildGlobalSearchViewBean(final RequestData requestData, final String searchText) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getRetailer();

        final List<GlobalSearchViewBean> globalSearchViewBeans = new ArrayList<GlobalSearchViewBean>();

        final List<ProductMarketing> productMarketings = productMarketingService.findProductMarketings(marketArea.getId(), retailer.getId(), searchText);
        for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();

            final GlobalSearchViewBean globalSearchViewBean = new GlobalSearchViewBean();
            globalSearchViewBean.setValue(productMarketing.getBusinessName() + " : " + productMarketing.getCode());
            globalSearchViewBean.setType("ProductMarketing");
            globalSearchViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing));

            globalSearchViewBeans.add(globalSearchViewBean);
        }

        final List<ProductSku> productSkus = productSkuService.findProductSkus(marketArea.getId(), retailer.getId(), searchText);
        for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
            ProductSku productSku = (ProductSku) iterator.next();

            final GlobalSearchViewBean globalSearchViewBean = new GlobalSearchViewBean();
            globalSearchViewBean.setValue(productSku.getBusinessName() + " : " + productSku.getCode());
            globalSearchViewBean.setType("ProductSku");
            globalSearchViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, productSku));

            globalSearchViewBeans.add(globalSearchViewBean);
        }

        return globalSearchViewBeans;
    }

    /**
     * 
     */
    public List<EngineSettingViewBean> buildEngineSettingViewBeans(final RequestData requestData, final List<EngineSetting> engineSettings) throws Exception {
        final List<EngineSettingViewBean> engineSettingViewBeans = new ArrayList<EngineSettingViewBean>();
        for (Iterator<EngineSetting> iterator = engineSettings.iterator(); iterator.hasNext();) {
            EngineSetting engineSetting = (EngineSetting) iterator.next();
            engineSettingViewBeans.add(buildEngineSettingViewBean(requestData, engineSetting));
        }
        return engineSettingViewBeans;
    }

    /**
     * 
     */
    public EngineSettingViewBean buildEngineSettingViewBean(final RequestData requestData, final EngineSetting engineSetting) throws Exception {
        final EngineSettingViewBean engineSettingViewBean = new EngineSettingViewBean();
        engineSettingViewBean.setName(engineSetting.getName());
        engineSettingViewBean.setCode(engineSetting.getCode());
        engineSettingViewBean.setDescription(engineSetting.getDescription());
        if(StringUtils.isNotEmpty(engineSetting.getDefaultValue())){
            engineSettingViewBean.setDefaultValue(engineSetting.getDefaultValue());
        } else {
            engineSettingViewBean.setDefaultValue("NA");
        }
        
        engineSettingViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestData, engineSetting));

        Set<EngineSettingValue> engineSettingValues = engineSetting.getEngineSettingValues();
        if (engineSettingValues != null) {
            for (Iterator<EngineSettingValue> iterator = engineSettingValues.iterator(); iterator.hasNext();) {
                EngineSettingValue engineSettingValue = (EngineSettingValue) iterator.next();
                engineSettingViewBean.getEngineSettingValues().add(buildEngineSettingValueViewBean(requestData, engineSettingValue));
            }
        }
        return engineSettingViewBean;
    }

    /**
     * 
     */
    public EngineSettingValueViewBean buildEngineSettingValueViewBean(final RequestData requestData, final EngineSettingValue engineSettingValue) throws Exception {
        final EngineSettingValueViewBean engineSettingValueViewBean = new EngineSettingValueViewBean();
        engineSettingValueViewBean.setContext(engineSettingValue.getContext());
        engineSettingValueViewBean.setValue(engineSettingValue.getValue());
        engineSettingValueViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_VALUE_EDIT, requestData, engineSettingValue));
        return engineSettingValueViewBean;
    }

    /**
     * 
     */
    public List<BatchViewBean> buildBatchViewBeans(final RequestData requestData, final List<BatchProcessObject> batchProcessObjects) throws Exception {
        final List<BatchViewBean> batchViewBeans = new ArrayList<BatchViewBean>();
        for (Iterator<BatchProcessObject> iterator = batchProcessObjects.iterator(); iterator.hasNext();) {
            BatchProcessObject batchProcessObject = (BatchProcessObject) iterator.next();
            batchViewBeans.add(buildBatchViewBean(requestData, batchProcessObject));
        }
        return batchViewBeans;
    }

    /**
     * 
     */
    public BatchViewBean buildBatchViewBean(final RequestData requestData, final BatchProcessObject batchProcessObject) throws Exception {
        final BatchViewBean batchViewBean = new BatchViewBean();
        batchViewBean.setId(batchProcessObject.getId());
        batchViewBean.setStatus(batchProcessObject.getStatus());
        batchViewBean.setTypeObject(batchProcessObject.getTypeObject().getPropertyKey());
        batchViewBean.setProcessedCount(batchProcessObject.getProcessedCount());
        return batchViewBean;
    }

    /**
     * 
     */
    public List<CurrencyReferentialViewBean> buildCurrencyReferentialViewBeans(final RequestData requestData, final List<CurrencyReferential> currencyReferentials) throws Exception {
        final List<CurrencyReferentialViewBean> currencyReferentialViewBeans = new ArrayList<CurrencyReferentialViewBean>();
        if (currencyReferentials != null) {
            for (Iterator<CurrencyReferential> iterator = currencyReferentials.iterator(); iterator.hasNext();) {
                CurrencyReferential currencyReferential = (CurrencyReferential) iterator.next();
                currencyReferentialViewBeans.add(buildCurrencyReferentialViewBean(requestData, currencyReferential));
            }
        }
        return currencyReferentialViewBeans;
    }

    /**
     * 
     */
    public CurrencyReferentialViewBean buildCurrencyReferentialViewBean(final RequestData requestData, final CurrencyReferential currencyReferential) throws Exception {
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

        }
        return currencyReferentialViewBean;
    }

    /**
     * 
     */
    public List<PaymentGatewayViewBean> buildPaymentGatewayViewBeans(final RequestData requestData, final List<AbstractPaymentGateway> paymentGateways) throws Exception {
        final List<PaymentGatewayViewBean> paymentGatewayViewBeans = new ArrayList<PaymentGatewayViewBean>();
        if (paymentGateways != null) {
            for (Iterator<AbstractPaymentGateway> iterator = paymentGateways.iterator(); iterator.hasNext();) {
                AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) iterator.next();
                paymentGatewayViewBeans.add(buildPaymentGatewayViewBean(requestData, paymentGateway));
            }
        }
        return paymentGatewayViewBeans;
    }

    /**
     * 
     */
    public PaymentGatewayViewBean buildPaymentGatewayViewBean(final RequestData requestData, final AbstractPaymentGateway paymentGateway) throws Exception {
        final PaymentGatewayViewBean paymentGatewayViewBean = new PaymentGatewayViewBean();
        if (paymentGateway != null) {
            paymentGatewayViewBean.setName(paymentGateway.getName());
            paymentGatewayViewBean.setDescription(paymentGateway.getDescription());
            paymentGatewayViewBean.setCode(paymentGateway.getCode());

            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            Date dateCreate = paymentGateway.getDateCreate();
            if (dateCreate != null) {
                paymentGatewayViewBean.setDateCreate(dateFormat.format(dateCreate));
            } else {
                paymentGatewayViewBean.setDateCreate("NA");
            }

            Date dateUpdate = paymentGateway.getDateUpdate();
            if (dateUpdate != null) {
                paymentGatewayViewBean.setDateUpdate(dateFormat.format(dateUpdate));
            } else {
                paymentGatewayViewBean.setDateUpdate("NA");
            }

        }
        return paymentGatewayViewBean;
    }

    // /**
    // *
    // */
    // public UserViewBean buildUserViewBean(final RequestData requestData,
    // final Localization localization, final User user) throws Exception {
    // final Locale locale = localization.getLocale();
    // final UserViewBean userEditViewBean = new UserViewBean();
    //
    // userEditViewBean.setId(user.getId());
    // userEditViewBean.setLoginLabel(getSpecificMessage("user.login.label",
    // locale));
    // userEditViewBean.setFirstnameLabel(getSpecificMessage("user.firstname.label",
    // locale));
    // userEditViewBean.setLastnameLabel(getSpecificMessage("user.lastname.label",
    // locale));
    // userEditViewBean.setEmailLabel(getSpecificMessage("user.email.label",
    // locale));
    // userEditViewBean.setPasswordLabel(getSpecificMessage("user.password.label",
    // locale));
    // userEditViewBean.setActiveLabel(getSpecificMessage("user.active.label",
    // locale));
    // if(user.isActive()){
    // userEditViewBean.setActiveValueLabel(getSpecificMessage("user.active.value.true",
    // locale));
    // } else {
    // userEditViewBean.setActiveValueLabel(getSpecificMessage("user.active.value.false",
    // locale));
    // }
    //
    //
    // userEditViewBean.setDateCreateLabel(getSpecificMessage("user.date.create.label",
    // locale));
    // userEditViewBean.setDateUpdateLabel(getSpecificMessage("user.date.update.label",
    // locale));
    //
    //
    // return userEditViewBean;
    // }

}