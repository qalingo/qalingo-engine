/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.GeolocService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;

/**
 * 
 */
@Controller("homeController")
public class HomeController extends AbstractPrehomeController {

    @Autowired
    protected GeolocService geolocService;
    
	@RequestMapping(FoUrls.PREHOME_URL)
	public ModelAndView displayHome(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PREHOME.getVelocityPage());

		final String remoteAddress = requestUtil.getRemoteAddr(request);
        model.addAttribute(ModelConstants.GEOLOC_REMOTE_ADDRESS, remoteAddress);
        
		final Country country = geolocService.geolocAndGetCountry(remoteAddress);
        model.addAttribute(ModelConstants.GEOLOC_COUNTRY, country);

        final City city = geolocService.geolocAndGetCity(remoteAddress);
        model.addAttribute(ModelConstants.GEOLOC_CITY, city);

        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.PREHOME.getKey());

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
		model.addAttribute(ModelConstants.MARKET_PLACES_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanMarketPlace(requestData));
	}
    
}