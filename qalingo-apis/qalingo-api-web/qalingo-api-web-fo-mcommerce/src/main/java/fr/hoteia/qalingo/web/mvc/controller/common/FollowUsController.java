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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller
public class FollowUsController extends AbstractMCommerceController {

	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping(value = "/follow-us.html*", method = RequestMethod.GET)
	public ModelAndView displayFollowUs(final HttpServletRequest request, final Model model, @ModelAttribute("followUsForm") FollowUsForm followUsForm) throws Exception{
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-form");
		
		// "followus";
		
        return modelAndView;
	}

	@RequestMapping(value = "/follow-us.html*", method = RequestMethod.POST)
	public ModelAndView followUs(final HttpServletRequest request, @Valid @ModelAttribute("followUsForm") FollowUsForm followUsForm,
								BindingResult result, Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "follow-us/follow-us-success");

		// "followus";

		if (result.hasErrors()) {
			return displayFollowUs(request, model, followUsForm);
		}

		webCommerceService.saveAndBuildNewsletterRegistrationConfirmationMail(request, followUsForm);
		
        return modelAndView;
	}
	
	/**
	 * 
	 */
    @ModelAttribute("followUsForm")
	protected FollowUsForm getFollowUsForm(final HttpServletRequest request, final Model model) throws Exception {
    	return formFactory.buildFollowUsForm(request);
	}
	
}