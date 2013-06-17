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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceFrontofficeController;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller
public class FollowUsController extends AbstractMCommerceFrontofficeController {

	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping(value = "/follow-us-form.html*")
	public ModelAndView followUsForm(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception{
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-form");
		
		// "followus";
		formFactory.buildFollowUsForm(request, modelAndView);
		modelAndViewFactory.initFollowUsModelAndView(request, modelAndView);
		
		final String contentText = getSpecificMessage(ScopeWebMessage.FOLLOW_US, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
        return modelAndView;
	}

	@RequestMapping(value = "/follow-us.html*", method = RequestMethod.GET)
	public ModelAndView followUs(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception{
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-form");
		
		// "followus";
		formFactory.buildFollowUsForm(request, modelAndView);
		modelAndViewFactory.initFollowUsModelAndView(request, modelAndView);
		
        return modelAndView;
	}

	@RequestMapping(value = "/follow-us.html*", method = RequestMethod.POST)
	public ModelAndView followUs(final HttpServletRequest request, final HttpServletResponse response, @Valid FollowUsForm followUsForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-success");

		// "followus";

		if (result.hasErrors()) {
			modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-form");
			modelAndViewFactory.initFollowUsModelAndView(request, modelAndView);
			return modelAndView;
		}

		webCommerceService.saveAndBuildNewsletterRegistrationConfirmationMail(request, followUsForm);
		
		modelAndViewFactory.initFollowUsModelAndView(request, modelAndView);

        return modelAndView;
	}
	
}