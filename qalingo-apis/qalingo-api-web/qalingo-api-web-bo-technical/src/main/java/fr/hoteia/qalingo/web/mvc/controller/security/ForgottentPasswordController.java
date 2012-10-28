/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

/**
 * 
 */
@Controller
public class ForgottentPasswordController extends AbstractQalingoController {

	@Autowired
    protected WebBackofficeService webCommerceService;
	
	@RequestMapping(value = "/forgotten-password.html*", method = RequestMethod.GET)
	public ModelAndView forgottenPassword(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/forgotten-password-form");
		
		final String titleKeyPrefixSufix = "forgotten.password";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initLoginModelAndView(request, modelAndView);
		modelAndView.addObject("formForgottenPassword", new ForgottenPasswordForm());
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/forgotten-password.html*", method = RequestMethod.POST)
	public ModelAndView forgottenPassword(final HttpServletRequest request, final HttpServletResponse response, @Valid ForgottenPasswordForm forgottenPasswordForm,
			BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/forgotten-password-success");

		final String titleKeyPrefixSufix = "forgotten.password";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initLoginModelAndView(request, modelAndView);

		if (result.hasErrors()) {
			modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/forgotten-password-form");
			modelAndView.addObject("formForgottenPassword", new ForgottenPasswordForm());
			return modelAndView;
		}
		
		final Locale locale = getCurrentLocale(request);
//		webCommerceService.buildAndSaveCustomerForgottenPasswordMail(request, forgottenPasswordForm);
		
        return modelAndView;
	}
	
}
