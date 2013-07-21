/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.user;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractReportingBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.UserForm;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

/**
 * 
 */
@Controller("userController")
public class UserController extends AbstractReportingBackofficeController {

	@Autowired
	protected WebBackofficeService webBackofficeService;
	
	@RequestMapping(value = "/user-details.html*", method = RequestMethod.GET)
	public ModelAndView userDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-details");

		User user = requestUtil.getCurrentUser(request);
		// Refresh Data cause CurrentUser is from Session or Spring Security
		user = userService.getUserById(user.getId().toString());
		if(user != null){
			initUserDetailsPage(request, response, modelAndView, user);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/user-edit.html*", method = RequestMethod.GET)
	public ModelAndView userEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-edit");
		
		final String titleKeyPrefixSufix = "user.edit";

		final Long currentUserId = requestUtil.getCurrentUserId(request);
		final User user = userService.getUserById(currentUserId.toString());
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		
//		modelAndView.addObject("userEdit", viewBeanFactory.buildUserEditViewBean(request, currentLocalization, user));

		formFactory.buildUserForm(request, modelAndView, user);
		return modelAndView;
	}
	
	@RequestMapping(value = "/user-edit.html*", method = RequestMethod.POST)
	public ModelAndView userEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid UserForm userForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		final String titleKeyPrefixSufix = "user.edit";
		final Long currentUserId = requestUtil.getCurrentUserId(request);
		final User user = userService.getUserById(currentUserId.toString());
		
		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-edit");
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
//			modelAndView.addObject("userEdit", viewBeanFactory.buildUserEditViewBean(request, currentLocalization, user));
			formFactory.buildUserForm(request, modelAndView, user);
			return modelAndView;
		}
		
		// SANITY CHECK
		if(BooleanUtils.negate(userForm.getLogin().equalsIgnoreCase(user.getLogin()))){
			User userCheck = userService.getUserByLoginOrEmail(userForm.getLogin());
			if(userCheck != null){
				result.rejectValue("login", "error.form.user.update.login.already.exist", null,"This email customer account already exist!.");

				ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-edit");
				final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
//				modelAndView.addObject("userEdit", viewBeanFactory.buildUserEditViewBean(request, currentLocalization, user));
				formFactory.buildUserForm(request, modelAndView, user);
				return modelAndView;
				
			}
		}
		if(BooleanUtils.negate(userForm.getEmail().equalsIgnoreCase(user.getEmail()))){
			User userCheck = userService.getUserByLoginOrEmail(userForm.getEmail());
			if(userCheck != null){
				result.rejectValue("email", "error.form.user.update.email.already.exist", null,"This email customer account already exist!.");

				ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-edit");
				final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
//				modelAndView.addObject("userEdit", viewBeanFactory.buildUserEditViewBean(request, currentLocalization, user));
				formFactory.buildUserForm(request, modelAndView, user);
				return modelAndView;
			}
		}
		
		// UPDATE USER
		webBackofficeService.updateUser(user, userForm);
		
		final String urlRedirect = backofficeUrlService.buildUserDetailsUrl();
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	protected void initUserDetailsPage(final HttpServletRequest request, final HttpServletResponse response, final ModelAndViewThemeDevice modelAndView, final User user) throws Exception{
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "user.details";

		modelAndView.addObject("userDetails", viewBeanFactory.buildUserViewBean(request, currentLocalization, user));
	}
    
}
