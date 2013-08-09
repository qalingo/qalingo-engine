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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.FoPageConstants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;

/**
 * 
 */
@Controller("homeController")
public class HomeController extends AbstractMCommerceController {

	@RequestMapping(FoPageConstants.HOME_URL + "*")
	public ModelAndView displayHome(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoPageConstants.HOME_VELOCITY_PAGE);

		final Locale locale = requestUtil.getCurrentLocale(request);
		
		final String pageKey = FoPageConstants.HOME_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

		final String contentText = getSpecificMessage(ScopeWebMessage.HOME, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		model.addAttribute(ModelConstants.CONTENT_TEXT, contentText);
		
        return modelAndView;
	}
	
	@RequestMapping(FoPageConstants.INDEX_URL + "*")
	public ModelAndView displayIndex(final HttpServletRequest request, final Model model) throws Exception {
        return displayHome(request, model);
	}
    
}
