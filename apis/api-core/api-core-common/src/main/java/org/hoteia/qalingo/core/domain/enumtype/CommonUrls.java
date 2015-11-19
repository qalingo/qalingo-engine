/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain.enumtype;

import org.apache.commons.lang.StringUtils;

public enum CommonUrls {

    ERROR_500(CommonUrls.ERROR_500_URL, CommonUrls.ERROR_500_KEY, CommonUrls.ERROR_500_VELOCITY_PAGE, false),
    ERROR_400(CommonUrls.ERROR_400_URL, CommonUrls.ERROR_400_KEY, CommonUrls.ERROR_400_VELOCITY_PAGE, false),
    ERROR_403(CommonUrls.ERROR_403_URL, CommonUrls.ERROR_403_KEY, CommonUrls.ERROR_403_VELOCITY_PAGE, false),
    ERROR_404(CommonUrls.ERROR_404_URL, CommonUrls.ERROR_404_KEY, CommonUrls.ERROR_404_VELOCITY_PAGE, false),
    
    XRDS(CommonUrls.XRDS_URL, CommonUrls.XRDS_KEY, CommonUrls.XRDS_VELOCITY_PAGE, true),

    ENTITY_CACHE(CommonUrls.ENTITY_CACHE_URL, CommonUrls.ENTITY_CACHE_KEY, CommonUrls.ENTITY_CACHE_PAGE, false),
    VELOCITY_CACHE(CommonUrls.VELOCITY_CACHE_URL, CommonUrls.VELOCITY_CACHE_KEY, CommonUrls.VELOCITY_CACHE_PAGE, false);

    public static final String XRDS_KEY             = "xrds";
    public static final String XRDS_URL             = "/**/xrds.html";
    public static final String XRDS_VELOCITY_PAGE   = "openid/xrds";
    
    public static final String ERROR_500_KEY            = "error-500";
    public static final String ERROR_500_URL            = "/500.html";
    public static final String ERROR_500_VELOCITY_PAGE  = "error/error-500";

    public static final String ERROR_400_KEY            = "error-400";
    public static final String ERROR_400_URL            = "/400.html";
    public static final String ERROR_400_VELOCITY_PAGE  = "error/error-400";

    public static final String ERROR_403_KEY            = "error-403";
    public static final String ERROR_403_URL            = "/403.html";
    public static final String ERROR_403_VELOCITY_PAGE  = "error/error-403";

    public static final String ERROR_404_KEY            = "error-404";
    public static final String ERROR_404_URL            = "/404.html";
    public static final String ERROR_404_VELOCITY_PAGE  = "error/error-404";
    
    public static final String ENTITY_CACHE_KEY           = "flush-cache-ihm";
    public static final String ENTITY_CACHE_URL           = "/admin/cachemanager.html";
    public static final String ENTITY_CACHE_PAGE          = "/velocity/admin/cache-manager";

    public static final String VELOCITY_CACHE_KEY           = "flush-cache-ihm";
    public static final String VELOCITY_CACHE_URL           = "/admin/flush-cache-ihm.html";
    public static final String VELOCITY_CACHE_PAGE          = "tools/flush-cache-ihm";
    
    private final String url;
    private final String key; // CODE IS USE TO GET THE ASSOCIATED MENU AND THE SEO VALUE
    private final String velocityPage;
    private final boolean withPrefixSEO;
    
    CommonUrls(String url, String key, String velocityPage, boolean withPrefixSEO) {
        this.url = url;
        this.key = key;
        this.velocityPage = velocityPage;
        this.withPrefixSEO = withPrefixSEO;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlWithoutWildcard() {
        if (StringUtils.isNotEmpty(url)) {
            return url.replace("/**", "");
        }
        return url;
    }
    
    public String getUrlPatternKey() {
        if (StringUtils.isNotEmpty(url)) {
            String patternKey = url;
            patternKey = patternKey.replace("/**/", "");
            patternKey = patternKey.replace(".html", "");
            if(patternKey.contains("-${")){
                patternKey = patternKey.replace(patternKey.substring(patternKey.indexOf("-${"), patternKey.indexOf("}") + 1), "");
            }
            return patternKey;
        }
        return url;
    }

    public String getKey() {
        return this.key;
    }

    public String getMessageKey() {
        return this.key.replace("-", "_");
    }
    
    public String getVelocityPage() {
        return this.velocityPage;
    }

    public boolean withPrefixSEO() {
        return withPrefixSEO;
    }

    public static CommonUrls fromKey(String key) {
        for (CommonUrls url : CommonUrls.values()) {
            if (url.getKey() == key)
                return url;
        }
        return null;
    }

}