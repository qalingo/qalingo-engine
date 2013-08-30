/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

/**
 * 
 */
@Controller("timeoutController")
public class TimeoutController extends AbstractMCommerceController {

	@RequestMapping("/timeout.html*")
	public ModelAndView timeout(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/timeout");
		
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		if(currentCustomer != null){
			final String urlRedirect = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		
		SecurityViewBean security = viewBeanFactory.buildSecurityViewBean(requestUtil.getRequestData(request));
		modelAndView.addObject("security", security);
		
        return modelAndView;
	}
	
}
