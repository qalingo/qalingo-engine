/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.security.fo.component;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerConnectionLog;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.security.RedirectStrategy;
import org.hoteia.qalingo.core.service.CustomerConnectionLogService;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component(value = "simpleUrlAuthenticationSuccessHandler")
public class SimpleUrlAuthenticationSuccessHandler extends org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerConnectionLogService customerConnectionLogService;

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected UrlService urlService;

    @Autowired
    protected RedirectStrategy redirectStrategy;

//    @Autowired
//    protected WebManagementService webManagementService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {
            final RequestData requestData = requestUtil.getRequestData(request);
            // CUSTOMER
            Customer customer = customerService.getCustomerByLoginOrEmail(authentication.getName());

            // TODO : move the CustomerConnectionLog Save in an async process : save cost time
            // Persit the new CustomerConnectionLog
            CustomerConnectionLog customerConnectionLog = new CustomerConnectionLog();
            customerConnectionLog.setCustomerId(customer.getId());
            customerConnectionLog.setLoginDate(new Date());
            customerConnectionLog.setAppCode(Constants.APP_NAME_FO_MCOMMERCE_CODE);
            customerConnectionLog.setHost(request.getRemoteHost());
            
            customerConnectionLog.setPublicAddress(request.getHeader(Constants.X_FORWARDED_FOR));
            customerConnectionLog.setPrivateAddress(requestUtil.getRemoteAddr(request));

            customer.getConnectionLogs().add(customerConnectionLog);
            customerConnectionLogService.saveOrUpdateCustomerConnectionLog(customerConnectionLog);

            requestUtil.updateCurrentCustomer(request, customer);

            // UPDATE CART WITH THE CUSTOMER INFORMATIONS
//            webManagementService.linkAndUpdateCartWithCustomer(requestData, customer);

            setUseReferer(false);
            String targetUrl = urlService.generateRedirectUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
            String lastUrl = requestUtil.getCurrentRequestUrlNotSecurity(request);

            // SANITY CHECK
            if (StringUtils.isNotEmpty(lastUrl)) {
                targetUrl = lastUrl;
            }

            if (lastUrl.contains("cart-") || lastUrl.contains("checkout-")) {
                // STAY ON THE CHECKOUT - REDIRECT ON THE ADDRESSES PAGES
                targetUrl = urlService.generateRedirectUrl(FoUrls.CART_AUTH, requestUtil.getRequestData(request));
            } 

            setDefaultTargetUrl(targetUrl);
            
            redirectStrategy.sendRedirect(request, response, targetUrl);

        } catch (Exception e) {
            logger.error("", e);
        }

    }

}