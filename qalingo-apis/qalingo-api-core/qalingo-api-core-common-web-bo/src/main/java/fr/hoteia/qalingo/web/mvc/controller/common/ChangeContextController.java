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
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.web.mvc.controller.AbstractBackofficeQalingoController;

/**
 * Change context
 */
@Controller
public class ChangeContextController extends AbstractBackofficeQalingoController {

	@RequestMapping("/	*")
	public ModelAndView changeLanguage(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String url = requestUtil.getLastRequestUrl(request);
        return new ModelAndView(new RedirectView(url));
	}
	
	@RequestMapping("/change-context.html*")
	public ModelAndView changeContext(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String url = requestUtil.getLastRequestUrl(request);
        return new ModelAndView(new RedirectView(url));
	}
	
}
