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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractBackofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.VelocityLayoutViewResolver;

/**
 * 
 */
@Controller("velocityCacheController")
public class VelocityCacheController extends AbstractBackofficeQalingoController {

	@Resource(name="viewResolver")
	protected VelocityLayoutViewResolver viewResolver;
	
	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@RequestMapping(BoUrls.VELOCITY_CACHE_URL)
	public ModelAndView searchEngineSetting(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VELOCITY_CACHE.getVelocityPage());

		viewResolver.clearCache();

		coreMessageSource.clearCache();

        return modelAndView;
	}
	
}