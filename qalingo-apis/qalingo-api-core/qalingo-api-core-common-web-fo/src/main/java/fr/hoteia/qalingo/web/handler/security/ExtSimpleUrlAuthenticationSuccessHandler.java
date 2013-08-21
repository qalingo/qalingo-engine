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
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.service.CustomerConnectionLogService;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;

@Component(value="extSimpleUrlAuthenticationSuccessHandler")
public class ExtSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerConnectionLogService customerConnectionLogService;
    
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected UrlService urlService;
	
	@Autowired
    protected ExtRedirectStrategy redirectStrategy;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

    	// Find the current customer
    	Customer customer = customerService.getCustomerByLoginOrEmail(authentication.getName());

    	// Persit only the new CustomerConnectionLog
    	CustomerConnectionLog customerConnectionLog = new CustomerConnectionLog();
    	customerConnectionLog.setCustomerId(customer.getId());
    	customerConnectionLog.setLoginDate(new Date());
    	customerConnectionLog.setApp(Constants.APP_NAME_FO_MCOMMERCE_CODE);
    	customerConnectionLog.setHost(request.getRemoteHost());
    	customerConnectionLog.setAddress(request.getRemoteAddr());
    	customerConnectionLogService.saveOrUpdateCustomerConnectionLog(customerConnectionLog);
    	
		try {
	    	// Update the Customer in Session
	    	customer.getConnectionLogs().add(customerConnectionLog);
	    	requestUtil.updateCurrentCustomer(request, customer);
	    	setUseReferer(false);
			String url = requestUtil.getCurrentRequestUrlNotSecurity(request);
			
	        // SANITY CHECK
	        if(StringUtils.isEmpty(url)){
	    		url = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
	        } else {
	        	String cartDetails = "cart-details.html";
	        	if(url.contains(cartDetails)){
		    		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		    		final Market currentMarket = requestUtil.getCurrentMarket(request);
		    		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		    		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		    		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		    		url = urlService.buildCartDeliveryAndOrderDetailsUrl( currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
	        	}
	        }
			
	    	setDefaultTargetUrl(url);
	        redirectStrategy.sendRedirect(request, response, url);
	        
		} catch (Exception e) {
			LOG.error("", e);
		}

    }
}
