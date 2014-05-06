/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.handler.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.hoteia.qalingo.core.web.handler.security.ExtRedirectStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component(value = "extSimpleUrlAuthenticationFailureHandler")
public class ExtSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UrlService urlService;

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected ExtRedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        try {
            Map<String, String> urlParams = new HashMap<String, String>();
            urlParams.put(RequestConstants.REQUEST_PARAMETER_AUTH_ERROR, "true");
            String url = requestUtil.getCurrentRequestUrlNotSecurity(request);
            String lastUrl = requestUtil.getCurrentRequestUrlNotSecurity(request);
            // SANITY CHECK
            if (StringUtils.isNotEmpty(lastUrl) && (lastUrl.contains("cart") || lastUrl.contains("checkout"))) {
                url = urlService.generateUrl(FoUrls.CART_AUTH, requestUtil.getRequestData(request), urlParams);
            } else {
                url = urlService.generateUrl(FoUrls.LOGIN, requestUtil.getRequestData(request), urlParams);
            }

            setDefaultFailureUrl(url);
            saveException(request, exception);
            redirectStrategy.sendRedirect(request, response, url);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}