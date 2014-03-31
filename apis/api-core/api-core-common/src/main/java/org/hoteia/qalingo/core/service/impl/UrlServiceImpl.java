/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "urlService")
@Transactional
public class UrlServiceImpl extends AbstractUrlServiceImpl implements UrlService {

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

    public String buildChangeLanguageUrl(final RequestData requestData, final Localization localization) throws Exception {
        return buildDefaultPrefix(requestData) + FoUrls.CHANGE_LANGUAGE.getUrlWithoutWildcard() + "?" + RequestConstants.REQUEST_PARAMETER_LOCALE_CODE + "=" + replaceSpaceAndUnderscore(localization.getCode());
    }

    public String buildChangeLanguageUrl(final RequestData requestData) throws Exception {
        final MarketPlace marketPlace = requestData.getMarketPlace();
        final Market market = requestData.getMarket();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final CurrencyReferential currency = requestData.getMarketAreaCurrency();

        String url = buildDefaultPrefix(requestData) + FoUrls.CHANGE_LANGUAGE.getUrlWithoutWildcard() + "?";
        url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + replaceSpaceAndUnderscore(marketPlace.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + replaceSpaceAndUnderscore(market.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + replaceSpaceAndUnderscore(marketArea.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_LANGUAGE + "=" + replaceSpaceAndUnderscore(localization.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_RETAILER_CODE + "=" + replaceSpaceAndUnderscore(retailer.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CURRENCY_CODE + "=" + replaceSpaceAndUnderscore(currency.getCode());
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
        url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + replaceSpaceAndUnderscore(marketPlace.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + replaceSpaceAndUnderscore(market.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + replaceSpaceAndUnderscore(marketArea.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_LANGUAGE + "=" + replaceSpaceAndUnderscore(localization.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_RETAILER_CODE + "=" + replaceSpaceAndUnderscore(retailer.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CURRENCY_CODE + "=" + replaceSpaceAndUnderscore(currency.getCode());
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
        return generateUrl(url.getUrlWithoutWildcard(), url.withPrefixSEO(), requestData, params);
    }

    @SuppressWarnings("unchecked")
    public String generateUrl(final String urlWithoutWildcard, final boolean isSEO, final RequestData requestData, Object... params) {
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
                        urlStr = addFullPrefixUrl(requestData, urlStr) + replaceSpaceAndUnderscore(retailer.getName()) + "/";
                    } else if (param instanceof ProductSku) {
                        ProductSku productSku = (ProductSku) param;
                        urlParams.put(RequestConstants.URL_PATTERN_PRODUCT_SKU_CODE, handleParamValue(productSku.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr);
                    } else if (param instanceof ProductMarketing) {
                        ProductMarketing productMarketing = (ProductMarketing) param;
                        urlParams.put(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE, handleParamValue(productMarketing.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr) + replaceSpaceAndUnderscore(productMarketing.getName()) + "/";
                    } else if (param instanceof CatalogCategoryVirtual) {
                        CatalogCategoryVirtual category = (CatalogCategoryVirtual) param;
                        urlParams.put(RequestConstants.URL_PATTERN_CATEGORY_CODE, handleParamValue(category.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr) + replaceSpaceAndUnderscore(category.getName()) + "/";
                    } else if (param instanceof CatalogCategoryMaster) {
                        CatalogCategoryMaster category = (CatalogCategoryMaster) param;
                        urlParams.put(RequestConstants.URL_PATTERN_CATEGORY_CODE, handleParamValue(category.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr) + replaceSpaceAndUnderscore(category.getName()) + "/";
                    } else if (param instanceof ProductBrand) {
                        ProductBrand productBrand = (ProductBrand) param;
                        urlParams.put(RequestConstants.URL_PATTERN_BRAND_CODE, handleParamValue(productBrand.getCode()));
                        urlStr = addFullPrefixUrl(requestData, urlStr) + replaceSpaceAndUnderscore(productBrand.getName()) + "/";
                    } else if (param instanceof CartItem) {
                        CartItem cartItem = (CartItem) param;
                        urlParams.put(RequestConstants.URL_PATTERN_CART_ITEM_CODE, handleParamValue(cartItem.getId().toString()));
                    }  else if (param instanceof Store) {
                        Store store = (Store) param;
                        urlParams.put(RequestConstants.URL_PATTERN_STORE_CODE, handleParamValue(store.getCode().toString()));
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
                    urlStr = getFullPrefixUrl(requestData);
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

    protected String addFullPrefixUrl(final RequestData requestData, String url) throws Exception {
        String fullPrefixUrl = getFullPrefixUrl(requestData);
        if (url == null || !url.contains(fullPrefixUrl)) {
            url = fullPrefixUrl;
        }
        return url;
    }

}