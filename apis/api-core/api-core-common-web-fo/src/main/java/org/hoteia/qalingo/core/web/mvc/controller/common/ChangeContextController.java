/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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

import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;

/**
 * Change context
 */
@Controller("changeContextController")
public class ChangeContextController extends AbstractFrontofficeQalingoController {

	@RequestMapping(FoUrls.CHANGE_LANGUAGE_URL)
	public ModelAndView changeLanguage(final HttpServletRequest request, final Model model) throws Exception {
		List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add(FoUrls.CHANGE_LANGUAGE_URL);
		String fallbackUrl = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
		final String url = requestUtil.getLastRequestUrl(request, excludedPatterns, fallbackUrl);
		RedirectView redirectView = new RedirectView(url);
		redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
	}
	
	@RequestMapping(FoUrls.CHANGE_CONTEXT_URL)
	public ModelAndView changeContext(final HttpServletRequest request, final Model model) throws Exception {
		List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add(FoUrls.CHANGE_CONTEXT_URL);
		String fallbackUrl = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
		final String url = requestUtil.getLastRequestUrl(request, excludedPatterns, fallbackUrl);
		RedirectView redirectView = new RedirectView(url);
		redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
	}
	
}
