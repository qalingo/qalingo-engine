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
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.CommonUrls;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractUrlService {

    @Autowired
    protected CoreMessageSource coreMessageSource;

    @Autowired
    protected EngineSettingService engineSettingService;

    public abstract String generateUrl(final String urlWithoutWildcard, final boolean isEncoded, final boolean isSEO, final RequestData requestData, Object... params);
    
    public String generateUrl(final CommonUrls url, final RequestData requestData, Object... params) {
        return generateUrl(url.getUrlWithoutWildcard(), false, url.withPrefixSEO(), requestData, params);
    }

    public String generateRedirectUrl(final CommonUrls url, final RequestData requestData, Object... params) {
        return generateUrl(url.getUrlWithoutWildcard(), true, url.withPrefixSEO(), requestData, params);
    }

    public String buildAbsoluteUrl(final RequestData requestData, final String relativeUrl) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        String cleanedRelativeUrl = relativeUrl.replace(buildDefaultPrefix(requestData), "");
        String absoluteUrl = buildDomainePathUrl(requestData);
        if (!cleanedRelativeUrl.startsWith("/")) {
            absoluteUrl = absoluteUrl + "/" + cleanedRelativeUrl;
        } else {
            absoluteUrl = absoluteUrl + cleanedRelativeUrl;
        }
        if (request != null && !absoluteUrl.startsWith(request.getScheme() + "://")) {
            absoluteUrl = request.getScheme() + "://" + absoluteUrl;
        }
        return absoluteUrl;
    }

    // ALIAS FOR DOMAINE PATH
    public String buildBaseUrl(final RequestData requestData) throws Exception {
        return buildDomainePathUrl(requestData);
    }
    
    public String buildDomainePathUrl(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketPlace marketPlace = requestData.getMarketPlace();
        final Market market = requestData.getMarket();
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        // CHOSE DOMAIN PATH FROM MARKET PLACE AND MARKET AND MARKET AREA
        String domainePathUrl = "";
        if (marketPlace != null) {
            String domainName = marketPlace.getDomainName(contextNameValue);
            if (StringUtils.isNotEmpty(domainName)) {
                domainePathUrl = domainName;
            }
        }
        if (market != null) {
            String domainName = market.getDomainName(contextNameValue);
            if (StringUtils.isNotEmpty(domainName)) {
                domainePathUrl = domainName;
            }
        }
        if (marketArea != null) {
            String domainName = marketArea.getDomainName(contextNameValue);
            if (StringUtils.isNotEmpty(domainName)) {
                domainePathUrl = domainName;
            }
        }
        if (request != null && StringUtils.isEmpty(domainePathUrl)) {
            String requestUrl = request.getRequestURL().toString();
            requestUrl = requestUrl.replace(request.getScheme() + "://", "");
            String[] urlBlock = requestUrl.split("/");
            domainePathUrl = urlBlock[0];
        }
        if (request != null && !domainePathUrl.startsWith(request.getScheme())) {
            domainePathUrl = request.getScheme() + "://" + domainePathUrl;
        }
        if (!domainePathUrl.contains("http")) {
            // Default value
            domainePathUrl = "http://" + domainePathUrl;
        }
        return domainePathUrl;
    }

    public String getSeoSegmentMain(Locale locale, boolean isEncoded) throws Exception {
        return handleSeoSegmentMain(coreMessageSource.getSpecificMessage(getMessageAppUniverse(), ScopeWebMessage.SEO.getPropertyKey(), "seo.url.main", locale), isEncoded);
    }

    public String handleSeoSegmentMain(String seoSegment, boolean isEncoded) throws Exception {
        return CoreUtil.handleSeoSpecificEscape(seoSegment, isEncoded);
    }
    
    public String getFullPrefixUrl(final RequestData requestData, boolean isEncoded) throws Exception {
        return getSeoPrefixUrl(requestData, isEncoded) + "/";
    }

    protected String getMessageAppUniverse(){
        return I18nKeyValueUniverse.FO.getPropertyKey();
    }
    
    protected String handleUrlParameters(String url, Map<String, String> urlParams, Map<String, String> getParams) {
        if (StringUtils.isNotEmpty(url)) {
            if (urlParams != null) {
                for (Entry<String, String> entry : urlParams.entrySet()) {
                    String key = String.format("\\{%s(:[^\\}]+)?\\}", entry.getKey());
                    if (StringUtils.equals(entry.getKey(), "slug")) {
                        key = "\\*\\*";
                    }
                    if (entry.getValue() != null) {
                        url = url.replaceAll(key, entry.getValue());
                    }
                }
            }

            String queryString = "";
            if (getParams != null) {
                for (Entry<String, String> entry : getParams.entrySet()) {
                    queryString += "&" + entry.getKey() + "=" + entry.getValue();
                }
            }
            return url.toLowerCase() + queryString.replaceFirst("&", "?");
        }
        return url;
    }
    
    protected String getSeoPrefixUrl(final RequestData requestData, boolean isEncoded) throws Exception {
        final MarketPlace marketPlace = requestData.getMarketPlace();
        final Market market = requestData.getMarket();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final Locale locale = requestData.getLocale();
        String seoPrefixUrl = buildContextPath(requestData) + "/" + getMarketPlacePrefixUrl(marketPlace) + getMarketPrefixUrl(market) + getMarketAreaPrefixUrl(marketArea)
                + getLocalizationPrefixUrl(localization) + getRetailerPrefixUrl(retailer);

        String seoSegmentMain = getSeoSegmentMain(locale, isEncoded);
        if (StringUtils.isNotEmpty(seoSegmentMain)) {
            seoPrefixUrl = seoPrefixUrl + getSeoSegmentMain(locale, isEncoded);
        }

        if (StringUtils.isNotEmpty(seoPrefixUrl)) {
            seoPrefixUrl = seoPrefixUrl.replace(" ", "-");
        }

        if (seoPrefixUrl.endsWith("/")) {
            seoPrefixUrl = seoPrefixUrl.substring(0, seoPrefixUrl.length() - 1);
        }

        return seoPrefixUrl;
    }

    protected String getMarketPlacePrefixUrl(final MarketPlace marketPlace) throws Exception {
        return marketPlace.getCode().toLowerCase() + "/";
    }

    protected String getMarketPrefixUrl(final Market market) throws Exception {
        return market.getCode().toLowerCase() + "/";
    }

    protected String getMarketAreaPrefixUrl(final MarketArea marketArea) throws Exception {
        return marketArea.getCode().toLowerCase() + "/";
    }

    protected String getLocalizationPrefixUrl(final Localization localization) throws Exception {
        return localization.getCode().toLowerCase() + "/";
    }

    protected String getRetailerPrefixUrl(final Retailer retailer) throws Exception {
        return retailer.getCode().toLowerCase() + "/";
    }

    protected String encodeString(String url) throws Exception {
        if (StringUtils.isNotEmpty(url)) {
            return URLEncoder.encode(url, Constants.UTF8);
        }
        return url;
    }

    protected String handleParamValue(String string) throws Exception {
        return CoreUtil.replaceSpaceAndUnderscore(string);
    }

    protected String buildDefaultPrefix(final RequestData requestData) {
        return buildContextPath(requestData) + Constants.SPRING_URL_PATH;
    }

    protected String buildContextPath(final RequestData requestData) {
        if (StringUtils.isNotEmpty(requestData.getContextPath())) {
            return requestData.getContextPath();
        }
        return "";
    }

}