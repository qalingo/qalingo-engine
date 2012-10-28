/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
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

import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;

/**
 * 
 */
@Controller
public class TimeoutController extends AbstractQalingoController {

	@RequestMapping("/timeout.html*")
	public ModelAndView timeout(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/timeout");
		
		if(getUser() != null){
			final String urlRedirect = boTechnicalUrlService.buildHomeUrl(request);
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		
		final String titleKeyPrefixSufix = "timeout";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		modelAndViewFactory.initLoginModelAndView(request, modelAndView);
		
        return modelAndView;
	}
	
}
