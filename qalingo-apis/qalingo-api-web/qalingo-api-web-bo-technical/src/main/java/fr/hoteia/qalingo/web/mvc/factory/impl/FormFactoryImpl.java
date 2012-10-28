/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.common.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.common.domain.User;
import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.form.EngineSettingValueForm;
import fr.hoteia.qalingo.web.mvc.form.QuickSearchForm;
import fr.hoteia.qalingo.web.mvc.form.UserForm;

/**
 * 
 */
@Service("formFactory")
public class FormFactoryImpl implements FormFactory {

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected UrlService urlService;
	
	public void buildEngineSettingValueEditForm(final HttpServletRequest request, final ModelAndView modelAndView, final EngineSettingValue engineSettingValue) throws Exception {
		final EngineSettingValueForm engineSettingValueForm = new EngineSettingValueForm();
		engineSettingValueForm.setId(engineSettingValue.getId().toString());
		engineSettingValueForm.setValue(engineSettingValue.getValue());
		modelAndView.addObject("engineSettingValueForm", engineSettingValueForm);
	}
	
	public void buildUserForm(final HttpServletRequest request, final ModelAndView modelAndView, final User user) throws Exception {
		final UserForm userForm = new UserForm();
		userForm.setId(user.getId().toString());
		userForm.setLogin(user.getLogin());
		userForm.setFirstname(user.getFirstname());
		userForm.setLastname(user.getLastname());
		userForm.setEmail(user.getEmail());
		userForm.setActive(user.isActive());
		modelAndView.addObject("userForm", userForm);
	}
	
	public void buildEngineSettingQuickSearchForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final QuickSearchForm engineSettingQuickSearchForm = new QuickSearchForm();
		modelAndView.addObject("engineSettingQuickSearchForm", engineSettingQuickSearchForm);
	}
	
	public void buildUserQuickSearchForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final QuickSearchForm userQuickSearchForm = new QuickSearchForm();
		modelAndView.addObject("userQuickSearchForm", userQuickSearchForm);
	}
	
	public void buildBatchQuickSearchForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final QuickSearchForm batchQuickSearchForm = new QuickSearchForm();
		modelAndView.addObject("batchQuickSearchForm", batchQuickSearchForm);
	}
	
}
