/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.FoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;

/**
 * 
 */
@Controller("conditionsOfUseController")
public class ConditionsOfUseController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.CONDITIONS_OF_USE_URL)
	public ModelAndView conditionsOfUse(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CONDITIONS_OF_USE.getVelocityPage());

		final Locale locale = requestUtil.getCurrentLocale(request);
		
		final String pageKey = FoUrls.CONDITIONS_OF_USE.getKey();
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

		final String contentText = getSpecificMessage(ScopeWebMessage.CONDITIONS_OF_USE, FoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
        return modelAndView;
	}
	
}