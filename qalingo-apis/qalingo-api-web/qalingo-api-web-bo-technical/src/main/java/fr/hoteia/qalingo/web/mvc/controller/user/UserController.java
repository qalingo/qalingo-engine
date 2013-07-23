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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.UserService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.UserForm;
import fr.hoteia.qalingo.web.mvc.viewbean.LinkMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserViewBean;

/**
 * 
 */
@Controller("userController")
public class UserController extends AbstractTechnicalBackofficeController {

	@Autowired
	protected UserService userService;
	
	@RequestMapping("/search-user.html*")
	public ModelAndView searchUser(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request),  "user/user-list");

		final String contentText = getSpecificMessage(ScopeWebMessage.USER, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		formFactory.buildUserQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/users.html*", method = RequestMethod.GET)
	public ModelAndView userList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-list");
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "user.list";
		initLinks(request, modelAndView, locale, null);
		
		List<User> users = userService.findUsers();
		
		String url = requestUtil.getCurrentRequestUrl(request);
		
		String sessionKey = "PagedListHolder_Search_List_Product_" + request.getSession().getId();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		PagedListHolder<UserViewBean> userViewBeanPagedListHolder;

        if(StringUtils.isEmpty(page)){
        	userViewBeanPagedListHolder = initList(request, sessionKey, currentLocalization, users, new PagedListHolder<UserViewBean>());
        } else {
	        userViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (userViewBeanPagedListHolder == null) { 
	        	userViewBeanPagedListHolder = initList(request, sessionKey, currentLocalization, users, userViewBeanPagedListHolder);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = userViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		userViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
		        	userViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
		modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
		modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, userViewBeanPagedListHolder);
		
		formFactory.buildUserQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/user-details.html*", method = RequestMethod.GET)
	public ModelAndView userDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-details");

		final String userId = request.getParameter(RequestConstants.REQUEST_PARAM_USER_ID);
		if(StringUtils.isNotEmpty(userId)){
			final User user = userService.getUserById(userId);
			if(user != null){
				initUserDetailsPage(request, response, modelAndView, user);
			} else {
				final String url = requestUtil.getLastRequestUrl(request);
				return new ModelAndView(new RedirectView(url));
			}
		} else {
			User user = requestUtil.getCurrentUser(request);
			// Refresh Data cause CurrentUser is from Session or Spring Security
			user = userService.getUserById(user.getId().toString());
			if(user != null){
				initUserDetailsPage(request, response, modelAndView, user);
			} else {
				final String url = requestUtil.getLastRequestUrl(request);
				return new ModelAndView(new RedirectView(url));
			}
		}
		
		formFactory.buildUserQuickSearchForm(request, modelAndView);

        return modelAndView;
	}
	
	@RequestMapping(value = "/user-edit.html*", method = RequestMethod.GET)
	public ModelAndView userEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-edit");
		
		final String titleKeyPrefixSufix = "user.edit";

		final String userId = request.getParameter(RequestConstants.REQUEST_PARAM_USER_ID);
		if(StringUtils.isNotEmpty(userId)){
			final User user = userService.getUserById(userId);
			if(user != null){
				final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
				
				modelAndView.addObject("userEdit", viewBeanFactory.buildUserViewBean(request, currentLocalization, user));

				formFactory.buildUserForm(request, modelAndView, user);
				return modelAndView;
			}
		} else {
			final Long currentUserId = requestUtil.getCurrentUserId(request);
			final User user = userService.getUserById(currentUserId.toString());
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			
			modelAndView.addObject("userEdit", viewBeanFactory.buildUserViewBean(request, currentLocalization, user));

			formFactory.buildUserForm(request, modelAndView, user);
			return modelAndView;
		}

		final String urlRedirect = backofficeUrlService.buildUserListUrl();
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	@RequestMapping(value = "/user-edit.html*", method = RequestMethod.POST)
	public ModelAndView userEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid UserForm userForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		final String titleKeyPrefixSufix = "user.edit";
		final String userId = userForm.getId();
		User user = userService.getUserById(userId);
		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "user/user-edit");
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			modelAndView.addObject("userEdit", viewBeanFactory.buildUserViewBean(request, currentLocalization, user));
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
				modelAndView.addObject("userEdit", viewBeanFactory.buildUserViewBean(request, currentLocalization, user));
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
				modelAndView.addObject("userEdit", viewBeanFactory.buildUserViewBean(request, currentLocalization, user));
				formFactory.buildUserForm(request, modelAndView, user);
				return modelAndView;
			}
		}
		
		// UPDATE USER
		webBackofficeService.updateUser(user, userForm);
		
		final String urlRedirect = backofficeUrlService.buildUserDetailsUrl(userId);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	protected void initUserDetailsPage(final HttpServletRequest request, final HttpServletResponse response, final ModelAndViewThemeDevice modelAndView, final User user) throws Exception{
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "user.details";

		initLinks(request, modelAndView, locale, user);
		
		modelAndView.addObject("userDetails", viewBeanFactory.buildUserViewBean(request, currentLocalization, user));
	}
	
	protected PagedListHolder<UserViewBean> initList(final HttpServletRequest request, final String sessionKey, final Localization currentLocalization, final List<User> users,
			PagedListHolder<UserViewBean> userViewBeanPagedListHolder) throws Exception {
		List<UserViewBean> userViewBeans = viewBeanFactory.buildUserViewBeans(request, currentLocalization, users);
		userViewBeanPagedListHolder = new PagedListHolder<UserViewBean>(userViewBeans);

		userViewBeanPagedListHolder.setPageSize(Constants.PAGINATION_DEFAULT_PAGE_SIZE); 
		String pageSize = engineSettingService.getEngineSettingValueByCode(EngineSettingService.ENGINE_SETTING_CODE_COUNT_ITEM_BY_PAGE, EngineSettingService.ENGINE_SETTING_CONTEXT_BO_TECHNICAL_ENGINE_SETTING_LIST);
		if(StringUtils.isNotEmpty(pageSize)){
			userViewBeanPagedListHolder.setPageSize(Integer.parseInt(pageSize)); 
		}
		
        request.getSession().setAttribute(sessionKey, userViewBeanPagedListHolder);
        return userViewBeanPagedListHolder;
	}
	
	protected void initLinks(final HttpServletRequest request, final ModelAndViewThemeDevice modelAndView, final Locale locale, final User user){
		List<LinkMenuViewBean> customerLinks = new ArrayList<LinkMenuViewBean>();

		LinkMenuViewBean linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.user.list", locale));
		linkMenuViewBean.setUrl(backofficeUrlService.buildUserListUrl());
		customerLinks.add(linkMenuViewBean);

		if(user != null){
			linkMenuViewBean = new LinkMenuViewBean();
			linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.user.details", locale));
			linkMenuViewBean.setUrl(backofficeUrlService.buildUserDetailsUrl(user.getId().toString()));
			customerLinks.add(linkMenuViewBean);
		}
		
		modelAndView.addObject("links", customerLinks);
	}
}
