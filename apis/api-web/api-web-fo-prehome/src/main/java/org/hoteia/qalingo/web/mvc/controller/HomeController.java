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

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.FoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("homeController")
public class HomeController extends AbstractPrehomeController {

	@RequestMapping(FoUrls.PREHOME_URL)
	public ModelAndView displayHome(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PREHOME.getVelocityPage());

		final RequestData requestData = requestUtil.getRequestData(request);
		final Locale locale = requestData.getLocale();
		
		final String pageKey = FoUrls.PREHOME.getKey();
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

		final String contentText = getSpecificMessage(ScopeWebMessage.HOME, FoMessageKey.MAIN_CONTENT_TEXT, locale);
		model.addAttribute(ModelConstants.CONTENT_TEXT, contentText);
		
        return modelAndView;
	}
	
	@RequestMapping(FoUrls.INDEX_URL)
	public ModelAndView displayIndex(final HttpServletRequest request, final Model model) throws Exception {
        return displayHome(request, model);
	}
	
    @RequestMapping("/")
    public ModelAndView displayDefaultPage(final HttpServletRequest request, final Model model) throws Exception {
        
        // DEFAULT HOME
        RequestData requestData = requestUtil.getRequestData(request);
        String defaultUrl = urlService.generateUrl(FoUrls.PREHOME, requestData);
        
        // TODO: GEOLOC AND CHOOSE THE GOOD MARKET
        
        return new ModelAndView(new RedirectView(defaultUrl));
    }
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMarketPlaces(final HttpServletRequest request, final Model model) throws Exception {
		// Markets
        RequestData requestData = requestUtil.getRequestData(request);
		model.addAttribute(ModelConstants.MARKET_PLACES_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketPlaceViewBeans(requestData));
	}
    
}