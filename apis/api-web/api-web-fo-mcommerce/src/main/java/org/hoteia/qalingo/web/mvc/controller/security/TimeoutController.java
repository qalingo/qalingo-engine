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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;

/**
 * 
 */
@Controller("timeoutController")
public class TimeoutController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.TIMEOUT_URL)
	public ModelAndView timeout(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.TIMEOUT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final Customer currentCustomer = requestData.getCustomer();
		if(currentCustomer != null){
			final String urlRedirect = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		
		SecurityViewBean security = frontofficeViewBeanFactory.buildViewBeanSecurity(requestUtil.getRequestData(request));
		modelAndView.addObject("security", security);
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.TIMEOUT.getKey());
		
        return modelAndView;
	}
	
}
