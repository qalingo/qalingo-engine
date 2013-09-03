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
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.OurCompanyViewBean;

/**
 * 
 */
@Controller("ourCompanyController")
public class OurCompanyController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.OUR_COMPANY_URL)
	public ModelAndView ourCompany(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.OUR_COMPANY.getVelocityPage());
		
		final OurCompanyViewBean ourCompany = viewBeanFactory.buildOurCompanyViewBean(requestUtil.getRequestData(request));
		modelAndView.addObject(ModelConstants.OUR_COMPANY_VIEW_BEAN, ourCompany);
		
        return modelAndView;
	}
	
}
