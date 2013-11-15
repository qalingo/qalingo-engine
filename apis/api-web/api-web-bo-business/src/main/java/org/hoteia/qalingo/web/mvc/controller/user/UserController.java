/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.hoteia.qalingo.web.mvc.form.UserForm;
import org.hoteia.qalingo.web.service.WebBackofficeService;

/**
 * 
 */
@Controller("userController")
public class UserController extends AbstractBusinessBackofficeController {

	@Autowired
	protected WebBackofficeService webBackofficeService;
	
	@RequestMapping(value = BoUrls.USER_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView userDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.USER_DETAILS.getVelocityPage());

		User user = requestUtil.getCurrentUser(request);
		if(user != null){
			// Refresh Data cause CurrentUser is from Session or Spring Security
			user = userService.getUserById(user.getId().toString());
			initUserDetailsPage(request, model, modelAndView, user);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.USER_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView userEdit(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.USER_EDIT.getVelocityPage());
		
		final Long currentUserId = requestUtil.getCurrentUserId(request);
		final User user = userService.getUserById(currentUserId.toString());
		modelAndView.addObject("userEdit", viewBeanFactory.buildUserViewBean(requestUtil.getRequestData(request), user));
		modelAndView.addObject("userForm", formFactory.buildUserForm(request, user));
		// BoPageConstants.USER_KEY
		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.USER_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitUserEdit(final HttpServletRequest request, final Model model, @Valid UserForm userForm,
								BindingResult result) throws Exception {

		final Long currentUserId = requestUtil.getCurrentUserId(request);
		final User user = userService.getUserById(currentUserId.toString());
		
		if (result.hasErrors()) {
			return userEdit(request, model);
		}
		
		// SANITY CHECK
		if(BooleanUtils.negate(userForm.getLogin().equalsIgnoreCase(user.getLogin()))){
			User userCheck = userService.getUserByLoginOrEmail(userForm.getLogin());
			if(userCheck != null){
				result.rejectValue("login", "error.form.user.update.login.already.exist", null, "This email customer account already exist!.");
				return userEdit(request, model);
				
			}
		}
		if(BooleanUtils.negate(userForm.getEmail().equalsIgnoreCase(user.getEmail()))){
			User userCheck = userService.getUserByLoginOrEmail(userForm.getEmail());
			if(userCheck != null){
				result.rejectValue("email", "error.form.user.update.email.already.exist", null, "This email customer account already exist!.");
				return userEdit(request, model);
			}
		}
		
		// UPDATE USER
		webBackofficeService.updateUser(user, userForm);
		
		final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.USER_DETAILS, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	protected void initUserDetailsPage(final HttpServletRequest request, final Model model, final ModelAndViewThemeDevice modelAndView, final User user) throws Exception{
		modelAndView.addObject("userDetails", viewBeanFactory.buildUserViewBean(requestUtil.getRequestData(request), user));
	}
    
}