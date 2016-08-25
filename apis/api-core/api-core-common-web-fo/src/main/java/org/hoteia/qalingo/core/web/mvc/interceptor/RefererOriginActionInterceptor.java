/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.pojo.GeolocData;
import org.hoteia.qalingo.core.service.GeolocService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RefererOriginActionInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected GeolocService geolocService;

    @Autowired
    protected MarketService marketService;

    @Autowired
    protected UrlService urlService;

    @Autowired
    protected RequestUtil requestUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String referer = request.getHeader(Constants.REFERER);
            if (StringUtils.isNotEmpty(referer)
                    && !referer.startsWith("http:/.qalingo.com")
                    && !referer.startsWith("http://www.qalingo.com")
                    && !referer.startsWith("https://www.qalingo.com")) {

                logger.debug("Referer :" + referer);

                final String regexp = ".*\\.facebook\\..*|.*\\.twitter\\..*|.*\\.yahoo\\..*|.*\\.google\\..*|.*\\.bing\\..*";

                if (referer.matches(regexp)) {
                    String targetUrl = request.getRequestURI().replace(request.getContextPath(), "");
                    if (!targetUrl.contains("oauth") & !targetUrl.contains("openid")) {
                        final String remoteAddress = requestUtil.getRemoteAddr(request);
                        logger.debug("Referer matchs: '" + referer + "', remoteAddress:" + remoteAddress);

                        GeolocData geolocData = geolocService.getGeolocData(remoteAddress);
                        if (geolocData != null
                        		&& geolocData.getCountry() != null) {
                            MarketArea marketAreaGeoloc = null;
                            List<MarketArea> marketAreas = marketService.findMarketAreaOpenedByGeolocCountryCode(geolocData.getCountry().getIsoCode());
                            if (marketAreas != null) {
                                if (marketAreas.size() == 1) {
                                    marketAreaGeoloc = marketAreas.get(0);
                                } else {
                                    // WE HAVE MANY MARKET AREA FOR THE CURRENT COUNTRY CODE - WE SELECT THE DEFAULT MARKET PLACE ASSOCIATE
                                    for (MarketArea marketAreaIt : marketAreas) {
                                        if (marketAreaIt.getMarket().getMarketPlace().isDefault()) {
                                            marketAreaGeoloc = marketAreaIt;
                                        }
                                    }
                                }
                            }

                            if (marketAreaGeoloc != null) {
                                String context = "/" + marketAreaGeoloc.getCode() + "/" + marketAreaGeoloc.getDefaultLocalization().getCode() + "/";
                                String newSegmentl = urlService.getSeoSegmentMain(marketAreaGeoloc.getDefaultLocalization().getLocale(), true);
                                String[] splits = request.getRequestURI().split("/");
                                String target = "";
                                int count = 0;
                                for (String split : splits) {
                                    if (count == (splits.length - 1)) {
                                        target = split;
                                    }
                                    count++;
                                }

                                final RequestData requestData = requestUtil.getRequestData(request);
                                String newUrl = context.toLowerCase() + newSegmentl + "/" + target;
                                String newAbsoluteUrl = urlService.buildAbsoluteUrl(requestData, newUrl);

                                if (!response.isCommitted() && !newAbsoluteUrl.contains(targetUrl)) {
                                    response.sendRedirect(newAbsoluteUrl);
                                }
                                logger.debug("Redirect to:" + newAbsoluteUrl);
                            }

                        } else {
                            logger.debug("Can't redirect geoloc is empty, IP:" + remoteAddress);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Referer origin action failed", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

}