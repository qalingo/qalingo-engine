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

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractReportingBackofficeController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("homeController")
public class HomeController extends AbstractReportingBackofficeController {

	@RequestMapping(BoUrls.HOME_URL)
	public ModelAndView displayHome(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.HOME.getVelocityPage());
        
        return modelAndView;
	}
    
	@RequestMapping(BoUrls.INDEX_URL)
	public ModelAndView displayIndex(final HttpServletRequest request, final Model model) throws Exception {
        return displayHome(request, model);
	}
	
    @RequestMapping("/")
    public ModelAndView displayDefaultPage(final HttpServletRequest request, final Model model) throws Exception {
        
        // DEFAULT HOME
        RequestData requestData = requestUtil.getRequestData(request);
        String defaultUrl = backofficeUrlService.generateUrl(BoUrls.HOME, requestData);
        
        return new ModelAndView(new RedirectView(defaultUrl));
    }
    
}