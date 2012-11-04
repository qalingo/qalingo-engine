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

import fr.hoteia.qalingo.core.common.service.EmailService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller
public class FollowUsController extends AbstractQalingoController {

	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping(value = "/follow-us-form.html*")
	public ModelAndView followUsForm(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception{
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-form");
		
		final String titleKeyPrefixSufix = "followus";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		formFactory.buildFollowUsForm(request, modelAndView);
		modelAndViewFactory.initFollowUsModelAndView(request, modelAndView);
		
		final String contentTest = coreMessageSource.getMessage("followus.content.text", null, getCurrentLocale(request));
		modelAndView.addObject("contentTest", contentTest);
		
        return modelAndView;
	}

	@RequestMapping(value = "/follow-us.html*", method = RequestMethod.GET)
	public ModelAndView followUs(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception{
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-form");
		
		final String titleKeyPrefixSufix = "followus";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		formFactory.buildFollowUsForm(request, modelAndView);
		modelAndViewFactory.initFollowUsModelAndView(request, modelAndView);
		
        return modelAndView;
	}

	@RequestMapping(value = "/follow-us.html*", method = RequestMethod.POST)
	public ModelAndView followUs(final HttpServletRequest request, final HttpServletResponse response, @Valid FollowUsForm followUsForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-success");

		final String titleKeyPrefixSufix = "followus";

		if (result.hasErrors()) {
			modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-form");
			initPage(request, response, modelAndView, titleKeyPrefixSufix);
			modelAndViewFactory.initFollowUsModelAndView(request, modelAndView);
			return modelAndView;
		}

		final Locale locale = getCurrentLocale(request);
		webCommerceService.saveAndBuildNewsletterRegistrationConfirmationMail(request, followUsForm);
		
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initFollowUsModelAndView(request, modelAndView);

        return modelAndView;
	}
	
}
