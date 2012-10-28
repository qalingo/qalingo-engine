/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.common.service.MarketPlaceService;
import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;

/**
 * 
 */
@Controller
public class HomeController extends AbstractQalingoController {

	@Autowired
	protected MarketPlaceService marketPlaceService;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	@Autowired
    protected UrlService urlService;
	
	@RequestMapping("/home*")
	public ModelAndView home(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "prehome");
		
		final String titleKeyPrefixSufix = "prehome";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);

		// Markets
		modelAndView.addObject("marketPlaces", viewBeanFactory.buildMarketPlaceViewBeans(request));
		
        return modelAndView;
	}
    
}
