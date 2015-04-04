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

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("timeoutController")
public class TimeoutController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.TIMEOUT_URL)
	public ModelAndView timeout(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.TIMEOUT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		if(securityUtil.isAuthenticated()){
			final String urlRedirect = urlService.generateRedirectUrl(FoUrls.HOME, requestUtil.getRequestData(request));
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		
		SecurityViewBean security = frontofficeViewBeanFactory.buildViewBeanSecurity(requestUtil.getRequestData(request));
		modelAndView.addObject("security", security);
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.TIMEOUT.getKey());
		
        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));

        return modelAndView;
	}
	
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData) {
        final Locale locale = requestData.getLocale();
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, FoUrls.TIMEOUT.getKey(), locale));
        
        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setKey(FoUrls.TIMEOUT.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.TIMEOUT.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.TIMEOUT, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);
        
        return breadcrumbViewBean;
    }
	
}
