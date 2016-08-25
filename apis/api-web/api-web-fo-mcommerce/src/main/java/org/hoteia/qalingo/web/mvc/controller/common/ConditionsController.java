/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.common;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("conditionsController")
public class ConditionsController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.CONDITIONS_OF_USE_URL)
	public ModelAndView conditionsOfUse(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CONDITIONS_OF_USE.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		
        overrideDefaultPageTitle(request, modelAndView, FoUrls.CONDITIONS_OF_USE.getKey());

        modelAndView.addObject(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBeanConditionsOfUse(requestData));
        
        return modelAndView;
	}

    protected BreadcrumbViewBean buildBreadcrumbViewBeanConditionsOfUse(final RequestData requestData) {
        final Locale locale = requestData.getLocale();

        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.CONDITIONS_OF_USE.getMessageKey(), locale));

        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();

        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setKey(FoUrls.CONDITIONS_OF_USE.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.CONDITIONS_OF_USE.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.CONDITIONS_OF_USE, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);

        return breadcrumbViewBean;
    }
    
    @RequestMapping(FoUrls.CONDITIONS_OF_SALE_URL)
    public ModelAndView conditionsOfSale(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CONDITIONS_OF_SALE.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        overrideDefaultPageTitle(request, modelAndView, FoUrls.CONDITIONS_OF_SALE.getKey());

        modelAndView.addObject(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBeanConditionsOfSale(requestData));
        
        return modelAndView;
    }

    protected BreadcrumbViewBean buildBreadcrumbViewBeanConditionsOfSale(final RequestData requestData) {
        final Locale locale = requestData.getLocale();

        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.CONDITIONS_OF_SALE.getMessageKey(), locale));

        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();

        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setKey(FoUrls.CONDITIONS_OF_SALE.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.CONDITIONS_OF_SALE.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.CONDITIONS_OF_SALE, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);

        return breadcrumbViewBean;
    }
    
}