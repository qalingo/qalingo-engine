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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;

/**
 * CLP Customer Loyalty Program
 */
@Controller
public class ClpController extends AbstractQalingoController {

	@RequestMapping("/clp.html*")
	public ModelAndView clp(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "clp/clp");

		final String titleKeyPrefixSufix = "clp";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		final String contentTest = coreMessageSource.getMessage("clp.content.text", null, getCurrentLocale(request));
		modelAndView.addObject("contentTest", contentTest);
		
		return modelAndView;
	}
	
}
