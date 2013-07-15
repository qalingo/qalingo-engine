/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.handler.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;

@Component
public class ExtSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected UrlService urlService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected ExtRedirectStrategy redirectStrategy;
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		try {
			final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
			final Market currentMarket = requestUtil.getCurrentMarket(request);
			final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
			String url = urlService.buildLoginUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer)  + "?" + RequestConstants.REQUEST_PARAM_AUTH_ERROR + "=true";
			setDefaultFailureUrl(url);
	        saveException(request, exception);
	        redirectStrategy.sendRedirect(request, response, url);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	
}
