/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Tag;
import org.hoteia.qalingo.core.domain.enumtype.CommonUrls;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "urlService")
@Transactional
public class UrlService extends AbstractUrlService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String buildCustomerDetailsUrl(final RequestData requestData, String permalink) throws Exception {
        return buildContextPath(requestData) + "/customer/" + permalink;
    }

    public String buildAddThisUrl(String shareCode, String absoluteUrl) throws Exception {
        String url = null;
        if (StringUtils.isNotEmpty(shareCode) && StringUtils.isNotEmpty(absoluteUrl)) {
            try {
                url = "http://api.addthis.com/oexchange/0.8/forward/" + shareCode + "/offer?url=" + URLEncoder.encode(absoluteUrl, Constants.ANSI);
            } catch (Exception e) {
                logger.error("SocialNetwork AddThis URL can't be encode!", e);
            }
        }
        return url;
    }

    public String buildChangeBackofficeLanguageUrl(final RequestData requestData, final Localization localization) throws Exception {
        return buildDefaultPrefix(requestData) + FoUrls.CHANGE_LANGUAGE.getUrlWithoutWildcard() + "?" + RequestConstants.REQUEST_PARAMETER_LOCALE_CODE + "=" + handleParamValue(localization.getCode());
    }

    public String buildChangeLanguageUrl(final RequestData requestData) throws Exception {
        final MarketPlace marketPlace = requestData.getMarketPlace();
        final Market market = requestData.getMarket();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final CurrencyReferential currency = requestData.getMarketAreaCurrency();

        String url = buildDefaultPrefix(requestData) + FoUrls.CHANGE_LANGUAGE.getUrlWithoutWildcard() + "?";
        url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + handleParamValue(marketPlace.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + handleParamValue(market.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + handleParamValue(marketArea.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_LANGUAGE + "=" + handleParamValue(localization.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_RETAILER_CODE + "=" + handleParamValue(retailer.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CURRENCY_CODE + "=" + handleParamValue(currency.getCode());
        return url;
    }

    public String buildChangeContextUrl(final RequestData requestData) throws Exception {
        final MarketPlace marketPlace = requestData.getMarketPlace();
        final Market market = requestData.getMarket();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final CurrencyReferential currency = requestData.getMarketAreaCurrency();
        
        String url = buildDefaultPrefix(requestData) + FoUrls.CHANGE_CONTEXT.getUrlWithoutWildcard() + "?";
        url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + handleParamValue(marketPlace.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + handleParamValue(market.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + handleParamValue(marketArea.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_LANGUAGE + "=" + handleParamValue(localization.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_RETAILER_CODE + "=" + handleParamValue(retailer.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CURRENCY_CODE + "=" + handleParamValue(currency.getCode());
        return url;
    }

    public String buildOAuthConnectUrl(final RequestData requestData, final String socialNetworkCode) throws Exception {
        return buildContextPath(requestData) + "/connect-oauth-" + socialNetworkCode + ".html";
    }

    public String buildOAuthCallBackUrl(final RequestData requestData, String socialNetworkCode) throws Exception {
        return buildContextPath(requestData) + "/callback-oauth-" + socialNetworkCode + ".html";
    }

    public String buildOpenIdConnectUrl(final RequestData requestData, final String socialNetworkCode) throws Exception {
        return buildContextPath(requestData) + "/connect-openid-" + socialNetworkCode + ".html";
    }

    public String buildOpenIdCallBackUrl(final RequestData requestData) throws Exception {
        return buildContextPath(requestData) + "/callback-openid.html";
    }

    public String generateUrl(final FoUrls url, final RequestData requestData, Object... params) {
        return generateUrl(url.getUrlWithoutWildcard(), false, url.withPrefixSEO(), requestData, params);
    }
    
    public String generateRedirectUrl(final FoUrls url, final RequestData requestData, Object... params) {
        return generateUrl(url.getUrlWithoutWildcard(), true, url.withPrefixSEO(), requestData, params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String generateUrl(final String urlWithoutWildcard, final boolean isEncoded, final boolean isSEO, final RequestData requestData, Object... params) {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        String urlStr = null;
        Map<String, String> getParams = new HashMap<String, String>();
        Map<String, String> urlParams = new HashMap<String, String>();
        try {
            if (params != null) {
                for (Object param : params) {
                    if (param == null)
                        continue;
                    if (param instanceof Retailer) {
                        Retailer retailer = (Retailer) param;
                        urlParams.put(RequestConstants.URL_PATTERN_RETAILER_CODE, handleParamValue(retailer.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr, isEncoded) + handleSeoSegmentMain(retailer.getI18nName(localizationCode), isEncoded) + "/";
                        
                    } else if (param instanceof ProductSku) {
                        ProductSku productSku = (ProductSku) param;
                        urlParams.put(RequestConstants.URL_PATTERN_PRODUCT_SKU_CODE, handleParamValue(productSku.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr, isEncoded);
                        
                    } else if (param instanceof ProductMarketing) {
                        ProductMarketing productMarketing = (ProductMarketing) param;
                        urlParams.put(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE, handleParamValue(productMarketing.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr, isEncoded) + handleSeoSegmentMain(productMarketing.getI18nName(localizationCode), isEncoded) + "/";
                        
                    } else if (param instanceof CatalogCategoryVirtual) {
                        CatalogCategoryVirtual category = (CatalogCategoryVirtual) param;
                        urlParams.put(RequestConstants.URL_PATTERN_CATEGORY_CODE, handleParamValue(category.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr, isEncoded);
                        if(Hibernate.isInitialized(category.getParentCatalogCategory()) && category.getParentCatalogCategory() != null){
                            urlStr = urlStr + handleSeoSegmentMain(category.getParentCatalogCategory().getI18nName(localizationCode), isEncoded) + "/";
                        }
                        urlStr = urlStr + handleSeoSegmentMain(category.getI18nName(localizationCode), isEncoded) + "/";
                        
                    } else if (param instanceof CatalogCategoryMaster) {
                        CatalogCategoryMaster category = (CatalogCategoryMaster) param;
                        urlStr = addFullPrefixUrl(requestData, urlStr, isEncoded);
                        if(Hibernate.isInitialized(category.getParentCatalogCategory()) && category.getParentCatalogCategory() != null){
                            urlStr = urlStr + handleSeoSegmentMain(category.getParentCatalogCategory().getI18nName(localizationCode), isEncoded) + "/";
                        }
                        urlStr = urlStr + handleSeoSegmentMain(category.getI18nName(localizationCode), isEncoded) + "/";
                        
                    } else if (param instanceof ProductBrand) {
                        ProductBrand productBrand = (ProductBrand) param;
                        urlParams.put(RequestConstants.URL_PATTERN_BRAND_CODE, handleParamValue(productBrand.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr, isEncoded) + handleSeoSegmentMain(productBrand.getI18nName(localizationCode), isEncoded) + "/";
                        
                    } else if (param instanceof CartItem) {
                        CartItem cartItem = (CartItem) param;
                        urlParams.put(RequestConstants.URL_PATTERN_CART_ITEM_CODE, handleParamValue(cartItem.getId().toString()));
                        
                    } else if (param instanceof Store) {
                        Store store = (Store) param;
                        urlParams.put(RequestConstants.URL_PATTERN_STORE_CODE, handleParamValue(store.getCode().toString()));
                        urlStr = addFullPrefixUrl(requestData, urlStr, isEncoded) + handleSeoSegmentMain(store.getI18nName(localizationCode), isEncoded) + "/";
                        
                    } else if (param instanceof Tag) {
                        Tag tag = (Tag) param;
                        urlParams.put(RequestConstants.URL_PATTERN_TAG_CODE, handleParamValue(tag.getCode().toString()));
                        urlStr = addFullPrefixUrl(requestData, urlStr, isEncoded) + handleSeoSegmentMain(tag.getI18nName(localizationCode), isEncoded) + "/";
                        
                    } else if (param instanceof Map) {
                        getParams = (Map<String, String>) param;
                    } else {
                        logger.warn("Unknowned url parameter : [{}]", param);
                    }
                }
            }

            if (StringUtils.isEmpty(urlStr)) {
                // AD THE DEFAULT PREFIX - DEFAULT PATH IS 
                urlStr = buildDefaultPrefix(requestData);
                if(isSEO){
                    urlStr = getFullPrefixUrl(requestData, isEncoded);
                }
            }
            
            // REMOVE THE / AT EH END BEFORE ADDING THE /**.html segment
            if (urlStr.endsWith("/")) {
                urlStr = urlStr.substring(0, urlStr.length() - 1);
            }

            urlStr = urlStr + urlWithoutWildcard;

        } catch (Exception e) {
            logger.error("Can't build Url!", e);
        }
        return handleUrlParameters(urlStr, urlParams, getParams);
    }

    public String buildSpringSecurityCheckUrl(final RequestData requestData) throws Exception {
        return buildContextPath(requestData) + FoUrls.SPRING_SECURITY_URL;
    }

    protected String addFullPrefixUrl(final RequestData requestData, String url, boolean isEncoded) throws Exception {
        String fullPrefixUrl = getFullPrefixUrl(requestData, isEncoded);
        if (url == null || !url.contains(fullPrefixUrl)) {
            url = fullPrefixUrl;
        }
        return url;
    }

}