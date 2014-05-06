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

import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		modelAndView.addObject("storeFilter", storeFilter);
		
        modelAndView.addObject("storeSearchUrl", urlService.generateUrl(FoUrls.STORE_SEARCH, requestData));
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.STORE_LOCATION.getKey());

        return modelAndView;
	}
 
}