/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractBackofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;

/**
 * Change context
 */
@Controller("changeContextController")
public class ChangeContextController extends AbstractBackofficeQalingoController {

	@RequestMapping(BoUrls.CHANGE_LANGUAGE_URL)
	public ModelAndView changeLanguage(final HttpServletRequest request, final Model model) throws Exception {
		List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add(BoUrls.CHANGE_LANGUAGE_URL);
		String fallbackUrl = backofficeUrlService.generateUrl(BoUrls.HOME, requestUtil.getRequestData(request));
		final String url = requestUtil.getLastRequestUrl(request, excludedPatterns, fallbackUrl);
        return new ModelAndView(new RedirectView(url));
	}
	
	@RequestMapping(BoUrls.CHANGE_CONTEXT_URL)
	public ModelAndView changeContext(final HttpServletRequest request, final Model model) throws Exception {
		List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add(BoUrls.CHANGE_CONTEXT_URL);
		String fallbackUrl = backofficeUrlService.generateUrl(BoUrls.HOME, requestUtil.getRequestData(request));
		final String url = requestUtil.getLastRequestUrl(request, excludedPatterns, fallbackUrl);
        return new ModelAndView(new RedirectView(url));
	}
	
}
