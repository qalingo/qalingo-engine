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

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("errorController")
public class ErrorController extends AbstractFrontofficeQalingoController {

	@RequestMapping(FoUrls.ERROR_500_URL)
	public ModelAndView error500(final HttpServletRequest request) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.ERROR_500.getVelocityPage());

        return modelAndView;
	}

    @RequestMapping(FoUrls.ERROR_400_URL)
    public ModelAndView error400(final HttpServletRequest request) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.ERROR_400.getVelocityPage());

        return modelAndView;
    }

    @RequestMapping(FoUrls.ERROR_403_URL)
    public ModelAndView error403(final HttpServletRequest request) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.ERROR_403.getVelocityPage());

        return modelAndView;
    }

    @RequestMapping(FoUrls.ERROR_404_URL)
    public ModelAndView error404(final HttpServletRequest request) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.ERROR_404.getVelocityPage());

        return modelAndView;
    }

}