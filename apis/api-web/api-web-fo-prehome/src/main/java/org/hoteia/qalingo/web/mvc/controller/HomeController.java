/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.FoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.service.MarketPlaceService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;

/**
 * 
 */
@Controller("homeController")
public class HomeController extends AbstractPrehomeController {

	@Autowired
	protected MarketPlaceService marketPlaceService;
	
	@RequestMapping(FoUrls.HOME_URL)
	public ModelAndView displayHome(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.HOME.getVelocityPage());

		final Locale locale = requestUtil.getCurrentLocale(request);
		
		final String pageKey = FoUrls.HOME.getKey();
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

		final String contentText = getSpecificMessage(ScopeWebMessage.HOME, FoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		model.addAttribute(ModelConstants.CONTENT_TEXT, contentText);
		
        return modelAndView;
	}
	
	@RequestMapping(FoUrls.INDEX_URL)
	public ModelAndView displayIndex(final HttpServletRequest request, final Model model) throws Exception {
        return displayHome(request, model);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMarketPlaces(final HttpServletRequest request, final Model model) throws Exception {
		// Markets
		model.addAttribute("marketPlaces", viewBeanFactory.buildMarketPlaceViewBeans(requestUtil.getRequestData(request)));
	}
    
}