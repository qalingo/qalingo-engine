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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.common.domain.Company;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.service.LocalizationService;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;

/**
 * Change language
 */
@Controller
public class ChangeLanguageController extends AbstractQalingoController {

	@Autowired
	protected LocalizationService localizationService;
	
	@RequestMapping("/change-language.html*")
	public ModelAndView clp(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String localeCode = request.getParameter(Constants.REQUEST_PARAMETER_LOCALE_CODE);
		final Company company = requestUtil.getCurrentCompany(request);
		final Localization localization = company.getLocalization(localeCode);
		final String url = requestUtil.getLastRequestUrl(request);
		if(localization == null){
	        return new ModelAndView(new RedirectView(url));
		}
		requestUtil.updateCurrentLocalization(request, localization);
        return new ModelAndView(new RedirectView(url));
	}
	
}
