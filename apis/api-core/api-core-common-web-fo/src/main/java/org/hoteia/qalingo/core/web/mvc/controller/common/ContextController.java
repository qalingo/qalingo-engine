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

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.ContextPojo;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.UrlPojo;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("contextController")
public class ContextController extends AbstractFrontofficeQalingoController {

	@RequestMapping(FoUrls.CONTEXT_URL)
	public ModelAndView error500(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CONTEXT.getVelocityPage());

        final RequestData requestData = requestUtil.getRequestData(request);
        final ContextPojo context = new ContextPojo();
        
        UrlPojo url = new UrlPojo();
        url.setCode("GET_CART");
        url.setUrl("/get-cart.json");
        context.getUrls().add(url);
        
        model.addAttribute(ModelConstants.CONTEXT_JSON, context);
        
        return modelAndView;
	}

}