/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.retailer;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("storeController")
public class StoreController extends AbstractMCommerceController {

	@Autowired
	protected RetailerService retailerService;
	
	@RequestMapping(FoUrls.STORE_DETAILS_URL)
	public ModelAndView displayRetailerDetails(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_STORE_CODE) final String storeCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.STORE_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

		Store store = retailerService.getStoreByCode(storeCode);
		
		StoreViewBean storeViewBean = frontofficeViewBeanFactory.buildStoreViewBean(requestUtil.getRequestData(request), store);
		model.addAttribute(ModelConstants.STORE_VIEW_BEAN, storeViewBean);
		
		model.addAttribute("withMap", true);
		
        return modelAndView;
	}
    
    
}