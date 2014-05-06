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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.service.BackofficeUrlService;
import org.hoteia.qalingo.core.web.util.RequestUtil;

@Component(value = "extSimpleUrlAuthenticationFailureHandler")
public class ExtSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BackofficeUrlService backofficeUrlService;

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected ExtRedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        try {
            Map<String, String> urlParams = new HashMap<String, String>();
            urlParams.put(RequestConstants.REQUEST_PARAMETER_AUTH_ERROR, "true");
            String url = backofficeUrlService.generateUrl(BoUrls.LOGIN, requestUtil.getRequestData(request), urlParams);
            setDefaultFailureUrl(url);
            saveException(request, exception);
            redirectStrategy.sendRedirect(request, response, url);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}