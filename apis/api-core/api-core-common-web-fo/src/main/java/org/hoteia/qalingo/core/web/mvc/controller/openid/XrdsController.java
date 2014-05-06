/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller.openid;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.VelocityLayoutViewResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("xrdsController")
public class XrdsController extends AbstractFrontofficeQalingoController {

	@Resource(name="viewResolver")
	protected VelocityLayoutViewResolver viewResolver;

	@RequestMapping(FoUrls.XRDS_URL)
	public ModelAndView displayXRDS(final HttpServletRequest request, final HttpServletResponse response, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.XRDS.getVelocityPage());
		String openIdCallBackURL = urlService.buildOpenIdCallBackUrl(requestUtil.getRequestData(request));
		String returnToURL = urlService.buildAbsoluteUrl(requestUtil.getRequestData(request), openIdCallBackURL);
		
		model.addAttribute("returnToURL", returnToURL);
		
		response.setContentType("application/xrds xml");
		
        return modelAndView;
	}
	
}