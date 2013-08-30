/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.service.RetailerService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreLocatorViewBean;

/**
 * 
 */
@Controller("storeLocationController")
public class StoreLocationController extends AbstractMCommerceController {

	@Autowired
    protected RetailerService storeService;
	
	@RequestMapping(FoUrls.STORE_LOCATION_URL)
	public ModelAndView storeLocation(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.STORE_LOCATION.getVelocityPage());
		
		final List<Store> stores = storeService.findStores();
		final StoreLocatorViewBean storeLocator = viewBeanFactory.buildStoreLocatorViewBean(requestUtil.getRequestData(request), stores);
		modelAndView.addObject("storeLocator", storeLocator);
		
        return modelAndView;
	}
 
}
