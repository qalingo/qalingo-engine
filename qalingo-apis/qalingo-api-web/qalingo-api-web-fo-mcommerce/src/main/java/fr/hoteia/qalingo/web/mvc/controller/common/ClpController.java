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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;

/**
 * CLP Customer Loyalty Program
 */
@Controller("clpController")
public class ClpController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.CLP_URL)
	public ModelAndView clp(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CLP.getVelocityPage());

		final String contentText = getSpecificMessage(ScopeWebMessage.CLP, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		return modelAndView;
	}
	
}