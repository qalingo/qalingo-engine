/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("loginController")
public class LoginController extends AbstractMCommerceController {

    @RequestMapping(FoUrls.LOGIN_URL)
    public ModelAndView login(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.LOGIN.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        // SANITY CHECK: Customer logged
        final Customer currentCustomer = requestData.getCustomer();
        if (currentCustomer != null) {
            final String url = urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(url));
        }

        // SANITY CHECK : Param from spring-security
        String error = request.getParameter(RequestConstants.REQUEST_PARAMETER_AUTH_ERROR);
        if (BooleanUtils.toBoolean(error)) {
            model.addAttribute(ModelConstants.AUTH_HAS_FAIL, BooleanUtils.toBoolean(error));
            model.addAttribute(ModelConstants.AUTH_ERROR_MESSAGE, getCommonMessage(ScopeCommonMessage.AUTH, "login_or_password_are_wrong", locale));
        }
        
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.LOGIN.getKey());
        
        return modelAndView;
    }
    
    @RequestMapping(FoUrls.CART_AUTH_URL)
    public ModelAndView checkoutAuth(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.LOGIN.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        // SANITY CHECK: Customer logged
        final Customer currentCustomer = requestData.getCustomer();
        if (currentCustomer != null) {
            final String url = urlService.generateUrl(FoUrls.CART_DELIVERY, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(url));
        }

        // SANITY CHECK : Param from spring-security
        String error = request.getParameter(RequestConstants.REQUEST_PARAMETER_AUTH_ERROR);
        if (BooleanUtils.toBoolean(error)) {
            model.addAttribute(ModelConstants.AUTH_HAS_FAIL, BooleanUtils.toBoolean(error));
            model.addAttribute(ModelConstants.AUTH_ERROR_MESSAGE, getCommonMessage(ScopeCommonMessage.AUTH, "login_or_password_are_wrong", locale));
        }
        
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.CART_AUTH.getKey());
        
        modelAndView.addObject(ModelConstants.CHECKOUT_STEP, 2);

        return modelAndView;
    }

    @RequestMapping(FoUrls.LOGIN_CHECK_URL)
    public ModelAndView loginCheck(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        ModelAndView modelAndView = new ModelAndView(FoUrls.LOGIN.getVelocityPage());

        final Customer currentCustomer = requestData.getCustomer();
        if (currentCustomer != null) {
            final String urlRedirect = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(urlRedirect));
        }

        return modelAndView;
    }

    @ModelAttribute(ModelConstants.SECURITY_VIEW_BEAN)
    protected SecurityViewBean initSecurityViewBean(final HttpServletRequest request, final Model model) throws Exception {
        return frontofficeViewBeanFactory.buildViewBeanSecurity(requestUtil.getRequestData(request));
    }

}