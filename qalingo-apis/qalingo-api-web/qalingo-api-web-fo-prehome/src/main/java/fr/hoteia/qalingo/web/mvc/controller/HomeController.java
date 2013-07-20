/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.FoPageConstants;
import fr.hoteia.qalingo.core.service.MarketPlaceService;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;

/**
 * 
 */
@Controller
public class HomeController extends AbstractPrehomeController {

	@Autowired
	protected MarketPlaceService marketPlaceService;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	@Autowired
    protected UrlService urlService;
	
	@RequestMapping(FoPageConstants.PRE_HOME_URL + "*")
	public ModelAndView home(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoPageConstants.PRE_HOME_VELOCITY_PAGE);
		
		// FoPageConstants.PRE_HOME_KEY

        return modelAndView;
	}
	
	@RequestMapping(FoPageConstants.INDEX_URL + "*")
	public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return home(request, response);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMarketPlaces(final HttpServletRequest request, final Model model) throws Exception {
		// Markets
		model.addAttribute("marketPlaces", viewBeanFactory.buildMarketPlaceViewBeans(request));
	}
    
}