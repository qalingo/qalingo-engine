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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
@Controller("storeLocationController")
public class StoreLocationController extends AbstractMCommerceController {

	@Autowired
    protected RetailerService retailerService;
	
	@RequestMapping(FoUrls.STORE_LOCATION_URL)
	public ModelAndView storeLocation(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.STORE_LOCATION.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        final List<Store> stores = retailerService.findStores();
		final List<StoreViewBean> storeViewBeans = frontofficeViewBeanFactory.buildListViewBeanStore(requestUtil.getRequestData(request), stores);
        modelAndView.addObject("stores", storeViewBeans);

        final StoreLocatorFilterBean storeFilter = frontofficeViewBeanFactory.buildFilterBeanStoreLocator(storeViewBeans, locale);
        ObjectMapper mapper = new ObjectMapper();
        try {
            modelAndView.addObject("jsonStores", mapper.writeValueAsString(storeFilter.getCountries()));
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage(), e);
        }
		
        modelAndView.addObject("storeSearchUrl", urlService.generateUrl(FoUrls.STORE_SEARCH, requestData));
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.STORE_LOCATION.getKey());

        modelAndView.addObject(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));
        
        return modelAndView;
	}
	
	protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData){
        final Locale locale = requestData.getLocale();
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, "store_location", locale));
        
        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
        MenuViewBean menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "store_location", locale));
        menu.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
        menuViewBeans.add(menu);
        
        breadcrumbViewBean.setMenus(menuViewBeans);
        return breadcrumbViewBean;
	}
 
}