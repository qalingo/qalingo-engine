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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.core.web.servlet.VelocityLayoutViewResolver;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("velocityCacheController")
public class VelocityCacheController extends AbstractFrontofficeQalingoController {

	@Resource(name="viewResolver")
	protected VelocityLayoutViewResolver viewResolver;

	@Autowired
	protected CoreMessageSource coreMessageSource;

	@RequestMapping(FoUrls.VELOCITY_CACHE_URL)
	public ModelAndView flushCache(final HttpServletRequest request) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.VELOCITY_CACHE.getVelocityPage());

		viewResolver.clearCache();
		
		coreMessageSource.clearCache();
		
        return modelAndView;
	}
	
}