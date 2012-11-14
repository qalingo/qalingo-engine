/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.common.domain.BatchProcessObject;
import fr.hoteia.qalingo.core.common.domain.EngineSetting;
import fr.hoteia.qalingo.core.common.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.User;
import fr.hoteia.qalingo.core.common.domain.UserConnectionLog;
import fr.hoteia.qalingo.core.common.domain.UserGroup;
import fr.hoteia.qalingo.core.common.domain.UserPermission;
import fr.hoteia.qalingo.core.common.domain.UserRole;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.BatchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingDetailsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingValueEditViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingValueViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserConnectionLogValueBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserEditViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserDetailsViewBean;
import fr.hoteia.qalingo.web.service.BoTechnicalUrlService;

/**
 * 
 */
@Service("viewBeanFactory")
public class ViewBeanFactoryImpl implements ViewBeanFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected BoTechnicalUrlService boTechnicalUrlService;
	
	/**
     * 
     */
	public CommonViewBean buildCommonViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final CommonViewBean commonViewBean = new CommonViewBean();
		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request, EngineSettingService.ENGINE_SETTING_CONTEXT_BO_TECHNICAL);
		commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

		commonViewBean.setHomeUrl(boTechnicalUrlService.buildHomeUrl(request));
		commonViewBean.setLoginUrl(boTechnicalUrlService.buildLoginUrl(request));
		commonViewBean.setLoginLabel(coreMessageSource.getMessage("header.link.login", null, locale));
//		commonViewBean.setForgottenPasswordUrl(urlService.buildContactUrl(request));
		commonViewBean.setLogoutUrl(boTechnicalUrlService.buildLogoutUrl(request));
		commonViewBean.setLogoutLabel(coreMessageSource.getMessage("header.link.logout", null, locale));
		commonViewBean.setUserDetailsUrl(boTechnicalUrlService.buildUserDetailsUrl(request));
		commonViewBean.setUserDetailsLabel(coreMessageSource.getMessage("header.link.my.account", null, locale));
		
		return commonViewBean;
	}
	
	/**
     * 
     */
	public List<MenuViewBean> buildMenuViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
		
		MenuViewBean menu = new MenuViewBean();
		menu.setCssClass("active");
		menu.setCssIcon("icon-home");
		menu.setName(coreMessageSource.getMessage("header.menu.home", null, locale));
		menu.setUrl(boTechnicalUrlService.buildHomeUrl(request));
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setCssIcon("icon-cogs");
		menu.setName("Engine Setting");
		menu.setUrl(boTechnicalUrlService.buildEngineSettingListUrl(request));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-reorder");
		menu.setName("Cache");
		menu.setUrl(boTechnicalUrlService.buildCacheUrl(request));
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setCssIcon("icon-random");
		menu.setName("Batch");
		menu.setUrl(boTechnicalUrlService.buildBatchUrl(request));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-group");
		menu.setName("Users");
		menu.setUrl(boTechnicalUrlService.buildUserListUrl(request));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-bar-chart");
		menu.setName("Monitoring");
		menu.setUrl(boTechnicalUrlService.buildMonitoringUrl(request));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-group");
		menu.setName("FAQ");
		menu.setUrl(boTechnicalUrlService.buildFaqUrl(request));
		menuViewBeans.add(menu);
		
		return menuViewBeans;
	}
	
	/**
     * 
     */
	public List<FooterMenuViewBean> buildFooterMenuViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final List<FooterMenuViewBean> footerMenuViewBeans = new ArrayList<FooterMenuViewBean>();
		
		FooterMenuViewBean footerMenuList = new FooterMenuViewBean();
		footerMenuList.setName(coreMessageSource.getMessage("header.menu.home", null, locale));
		footerMenuList.setUrl(boTechnicalUrlService.buildHomeUrl(request));
		footerMenuViewBeans.add(footerMenuList);
		
		return footerMenuViewBeans;
	}
	
	/**
     * 
     */
	public List<LocalizationViewBean> buildLocalizationViewBeans(final HttpServletRequest request, final List<Localization> localizations) throws Exception {
		final List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
		if(localizations != null){
			for (Iterator<Localization> iterator = localizations.iterator(); iterator.hasNext();) {
				Localization localization = (Localization) iterator.next();
				localizationViewBeans.add(buildLocalizationViewBean(request, localization));
			}
		}
		return localizationViewBeans;
	}
	
	/**
     * 
     */
	public LocalizationViewBean buildLocalizationViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		final String localeCodeNavigation = localization.getLocaleCode();
		
		final LocalizationViewBean localizationViewBean = new LocalizationViewBean();
		
		if(StringUtils.isNotEmpty(localeCodeNavigation)
				&& localeCodeNavigation.length() == 2) {
			localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCodeNavigation.toLowerCase(), null, locale));
		} else {
			localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCodeNavigation, null, locale));
		}
		
		localizationViewBean.setUrl(boTechnicalUrlService.buildChangeLanguageUrl(request, localization));
		return localizationViewBean;
	}
	
	/**
     * 
     */
	public LegacyViewBean buildLegacyViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final LegacyViewBean legacy = new LegacyViewBean();
		
		legacy.setPageTitle(coreMessageSource.getMessage("header.title.legacy", null, locale));
		legacy.setTextHtml(coreMessageSource.getMessage("legacy.content.text", null, locale));

		legacy.setWarning(coreMessageSource.getMessage("legacy.warning", null, locale));
		legacy.setCopyright(coreMessageSource.getMessage("footer.copyright", null, locale));
		
		return legacy;
	}
	
	/**
     * 
     */
	public SecurityViewBean buildSecurityViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final SecurityViewBean security = new SecurityViewBean();
		
		security.setLoginPageTitle(coreMessageSource.getMessage("header.title.login", null, locale));
		security.setLoginPageText(coreMessageSource.getMessage("login.content.text", null, locale));

		security.setLogoutPageTitle(coreMessageSource.getMessage("header.title.logout", null, locale));
		security.setLogoutPageText(coreMessageSource.getMessage("main.content.title.logout", null, locale));

		security.setForbiddenPageTitle(coreMessageSource.getMessage("header.title.forbidden", null, locale));
		security.setForbiddenPageText(coreMessageSource.getMessage("forbidden.content.text", null, locale));

		security.setTimeoutPageTitle(coreMessageSource.getMessage("header.title.timeout", null, locale));
		security.setTimeoutPageText(coreMessageSource.getMessage("timeout.content.text", null, locale));

		security.setForgottenPasswordPageTitle(coreMessageSource.getMessage("header.title.forgotten.password", null, locale));
		security.setForgottenPasswordPageText(coreMessageSource.getMessage("forgotten.password.content.text", null, locale));
		security.setEmailOrLoginLabel(coreMessageSource.getMessage("forgotten.password.email.or.login", null, locale));
		security.setForgottenPasswordEmailSucces(coreMessageSource.getMessage("forgotten.password.email.success", null, locale));
	    
		security.setLoginFormTitle(coreMessageSource.getMessage("login.form.login.title", null, locale));
		security.setLoginUrl(boTechnicalUrlService.buildSpringSecurityCheckUrl(request));
		security.setLoginLabel(coreMessageSource.getMessage("login.form.login.label", null, locale));
		security.setForgottenPasswordUrl(boTechnicalUrlService.buildForgottenPasswordUrl(request));
		security.setForgottenPasswordLabel(coreMessageSource.getMessage("login.form.forgotten.password.label", null, locale));
		security.setPasswordLabel(coreMessageSource.getMessage("login.form.password.label", null, locale));
		security.setRememberLabel(coreMessageSource.getMessage("login.form.remember.label", null, locale));
		security.setSubmitLabel(coreMessageSource.getMessage("login.form.login.submit", null, locale));
		
		return security;
	}
	
	/**
     * 
     */
	public QuickSearchViewBean buildQuickSearchViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		final QuickSearchViewBean quickSsearch = new QuickSearchViewBean();
		quickSsearch.setTextLabel(coreMessageSource.getMessage("form.search.label.text", null, locale));
		quickSsearch.setLabelSubmit(coreMessageSource.getMessage("form.search.label.submit", null, locale));
		quickSsearch.setUrlFormSubmit(boTechnicalUrlService.buildSearchUrl(request));
		quickSsearch.setUrlEngineSettingFormSubmit(boTechnicalUrlService.buildSearchUrl(request));
		quickSsearch.setUrlUserFormSubmit(boTechnicalUrlService.buildSearchUrl(request));
		quickSsearch.setUrlBatchFormSubmit(boTechnicalUrlService.buildSearchUrl(request));
		return quickSsearch;
	}
	
	/**
     * 
     */
	public List<EngineSettingViewBean> buildEngineSettingViewBeans(final HttpServletRequest request, final List<EngineSetting> engineSettings) throws Exception {
		final List<EngineSettingViewBean> engineSettingViewBeans = new ArrayList<EngineSettingViewBean>();
		for (Iterator<EngineSetting> iterator = engineSettings.iterator(); iterator.hasNext();) {
			EngineSetting engineSetting = (EngineSetting) iterator.next();
			engineSettingViewBeans.add(buildEngineSettingViewBean(request, engineSetting));
		}
		return engineSettingViewBeans;
	}
	
	/**
     * 
     */
	public EngineSettingViewBean buildEngineSettingViewBean(final HttpServletRequest request, final EngineSetting engineSetting) throws Exception {
		final EngineSettingViewBean engineSettingViewBean = new EngineSettingViewBean();
		engineSettingViewBean.setName(engineSetting.getName());
		engineSettingViewBean.setDescription(engineSetting.getDescription());
		engineSettingViewBean.setEngineSettingDetailsUrl(boTechnicalUrlService.buildEngineSettingDetailsUrl(request, engineSetting.getId().toString()));
		Set<EngineSettingValue> engineSettingValues = engineSetting.getEngineSettingValues();
		if(engineSettingValues != null){
			for (Iterator<EngineSettingValue> iterator = engineSettingValues.iterator(); iterator.hasNext();) {
				EngineSettingValue engineSettingValue = (EngineSettingValue) iterator.next();
				engineSettingViewBean.getEngineSettingValues().add(buildEngineSettingValueViewBean(request, engineSettingValue));
			}
		}
		return engineSettingViewBean;
	}
	
	/**
     * 
     */
	public EngineSettingDetailsViewBean buildEngineSettingDetailsViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		final EngineSettingDetailsViewBean engineSettingValueDetailsViewBean = new EngineSettingDetailsViewBean();
		engineSettingValueDetailsViewBean.setEditLabel(coreMessageSource.getMessage("engine.setting.details.edit.value", null, locale));
		return engineSettingValueDetailsViewBean;
	}
	
	/**
     * 
     */
	public EngineSettingValueViewBean buildEngineSettingValueViewBean(final HttpServletRequest request, final EngineSettingValue engineSettingValue) throws Exception {
		final EngineSettingValueViewBean engineSettingValueViewBean = new EngineSettingValueViewBean();
		engineSettingValueViewBean.setContext(engineSettingValue.getContext());
		engineSettingValueViewBean.setValue(engineSettingValue.getValue());
		engineSettingValueViewBean.setEditUrl(boTechnicalUrlService.buildEngineSettingValueEditUrl(request, engineSettingValue.getId().toString()));
		return engineSettingValueViewBean;
	}
	
	/**
     * 
     */
	public EngineSettingValueEditViewBean buildEngineSettingValueEditViewBean(final HttpServletRequest request, final Localization localization, final EngineSettingValue engineSettingValue) throws Exception {
		final Locale locale = localization.getLocale();
		final EngineSettingValueEditViewBean engineSettingValueEdit = new EngineSettingValueEditViewBean();
		engineSettingValueEdit.setSubmitLabel(coreMessageSource.getMessage("form.engine.setting.value.edit.submit.label", null, locale));
		engineSettingValueEdit.setFormSubmitUrl(boTechnicalUrlService.buildEngineSettingValueEditUrl(request, engineSettingValue.getId().toString()));
		return engineSettingValueEdit;
	}
	
	/**
     * 
     */
	public List<UserDetailsViewBean> buildUserViewBeans(final HttpServletRequest request, final Localization localization, final List<User> users) throws Exception {
		final List<UserDetailsViewBean> userViewBeans = new ArrayList<UserDetailsViewBean>();
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			userViewBeans.add(buildUserViewBean(request, localization, user));
		}
		return userViewBeans;
	}
	
	/**
     * 
     */
	public UserDetailsViewBean buildUserViewBean(final HttpServletRequest request, final Localization localization, final User user) throws Exception {
		final Locale locale = localization.getLocale();
		final UserDetailsViewBean userDetailsViewBean = new UserDetailsViewBean();
		userDetailsViewBean.setId(user.getId());
		userDetailsViewBean.setLoginLabel(coreMessageSource.getMessage("user.login.label", null, locale));
		userDetailsViewBean.setLogin(user.getLogin());
		userDetailsViewBean.setFirstnameLabel(coreMessageSource.getMessage("user.firstname.label", null, locale));
		userDetailsViewBean.setFirstname(user.getFirstname());
		userDetailsViewBean.setLastnameLabel(coreMessageSource.getMessage("user.lastname.label", null, locale));
		userDetailsViewBean.setLastname(user.getLastname());
		userDetailsViewBean.setEmailLabel(coreMessageSource.getMessage("user.email.label", null, locale));
		userDetailsViewBean.setEmail(user.getEmail());
		userDetailsViewBean.setPasswordLabel(coreMessageSource.getMessage("user.password.label", null, locale));
		userDetailsViewBean.setPassword(user.getPassword());
		userDetailsViewBean.setActiveLabel(coreMessageSource.getMessage("user.active.label", null, locale));
		userDetailsViewBean.setActive(user.isActive());
		if(user.isActive()){
			userDetailsViewBean.setActiveValueLabel(coreMessageSource.getMessage("user.active.value.true", null, locale));
		} else {
			userDetailsViewBean.setActiveValueLabel(coreMessageSource.getMessage("user.active.value.false", null, locale));
		}
		
		DateFormat dateFormat = requestUtil.getFormatDate(request, DateFormat.MEDIUM, DateFormat.MEDIUM);
		userDetailsViewBean.setDateCreateLabel(coreMessageSource.getMessage("user.date.create.label", null, locale));
		if(user.getDateCreate() != null){
			userDetailsViewBean.setDateCreate(dateFormat.format(user.getDateCreate()));
		} else {
			userDetailsViewBean.setDateCreate("NA");
		}
		userDetailsViewBean.setDateUpdateLabel(coreMessageSource.getMessage("user.date.update.label", null, locale));
		if(user.getDateUpdate() != null){
			userDetailsViewBean.setDateUpdate(dateFormat.format(user.getDateUpdate()));
		} else {
			userDetailsViewBean.setDateUpdate("NA");
		}
		
		final Set<UserGroup> userGroups = user.getUserGroups();
		for (Iterator<UserGroup> iteratorUserGroup = userGroups.iterator(); iteratorUserGroup.hasNext();) {
			UserGroup userGroup = (UserGroup) iteratorUserGroup.next();
			String keyUserGroup = userGroup.getCode();
			String valueUserGroup = userGroup.getName();
			userDetailsViewBean.getUserGroups().put(keyUserGroup, valueUserGroup);
			
			final Set<UserRole> userRoles = userGroup.getGroupRoles();
			for (Iterator<UserRole> iteratorUserRole = userRoles.iterator(); iteratorUserRole.hasNext();) {
				UserRole userRole = (UserRole) iteratorUserRole.next();
				String keyUserRole = userRole.getCode() + " (" + userGroup.getCode() + ")";
				String valueUserRole = userRole.getName();
				userDetailsViewBean.getUserRoles().put(keyUserRole, valueUserRole);
				
				final Set<UserPermission> rolePermissions = userRole.getRolePermissions();
				for (Iterator<UserPermission> iteratorUserPermission = rolePermissions.iterator(); iteratorUserPermission.hasNext();) {
					UserPermission userPermission = (UserPermission) iteratorUserPermission.next();
					String keyUserPermission = userPermission.getCode() + " (" + userRole.getCode() + ")";
					String valueUserPermission = userPermission.getName();
					userDetailsViewBean.getUserPermissions().put(keyUserPermission, valueUserPermission);
				}
			}
		}
		
		userDetailsViewBean.setUserConnectionLogLabel(coreMessageSource.getMessage("user.last.login.label", null, locale));
		userDetailsViewBean.setUserGroupsLabel(coreMessageSource.getMessage("user.groups.label", null, locale));
		userDetailsViewBean.setUserRolesLabel(coreMessageSource.getMessage("user.roles.label", null, locale));
		userDetailsViewBean.setUserPermissionsLabel(coreMessageSource.getMessage("user.permissions.label", null, locale));
		
		userDetailsViewBean.setTableDateLabel(coreMessageSource.getMessage("user.details.table.date", null, locale));
		userDetailsViewBean.setTableHostLabel(coreMessageSource.getMessage("user.details.table.host", null, locale));
		userDetailsViewBean.setTableAddressLabel(coreMessageSource.getMessage("user.details.table.address", null, locale));
		userDetailsViewBean.setTableCodeLabel(coreMessageSource.getMessage("user.details.table.code", null, locale));
		userDetailsViewBean.setTableNameLabel(coreMessageSource.getMessage("user.details.table.name", null, locale));
		
		final Set<UserConnectionLog> connectionLogs = user.getConnectionLogs();
		for (Iterator<UserConnectionLog> iteratorUserConnectionLog = connectionLogs.iterator(); iteratorUserConnectionLog.hasNext();) {
			UserConnectionLog userConnectionLog = (UserConnectionLog) iteratorUserConnectionLog.next();
			UserConnectionLogValueBean userConnectionLogValueBean = new UserConnectionLogValueBean();
			userConnectionLogValueBean.setDate(dateFormat.format(userConnectionLog.getLoginDate()));
			userConnectionLogValueBean.setHost(userConnectionLog.getHost());
			userConnectionLogValueBean.setAddress(userConnectionLog.getAddress());
			userDetailsViewBean.getUserConnectionLogs().add(userConnectionLogValueBean);
		}

		userDetailsViewBean.setBackLabel(coreMessageSource.getMessage("user.back.label", null, locale));
		userDetailsViewBean.setBackUrl(requestUtil.getLastRequestUrl(request));

		userDetailsViewBean.setUserDetailsLabel(coreMessageSource.getMessage("user.details.label", null, locale));
		userDetailsViewBean.setUserDetailsUrl(boTechnicalUrlService.buildUserDetailsUrl(request, user.getId().toString()));

		userDetailsViewBean.setUserEditLabel(coreMessageSource.getMessage("user.edit.label", null, locale));
		userDetailsViewBean.setUserEditUrl(boTechnicalUrlService.buildUserEditUrl(request, user.getId().toString()));
		
		return userDetailsViewBean;
	}
	
	/**
     * 
     */
	public UserEditViewBean buildUserEditViewBean(final HttpServletRequest request, final Localization localization, final User user) throws Exception {
		final Locale locale = localization.getLocale();
		final UserEditViewBean userEditViewBean = new UserEditViewBean();

		userEditViewBean.setId(user.getId());
		userEditViewBean.setLoginLabel(coreMessageSource.getMessage("user.login.label", null, locale));
		userEditViewBean.setFirstnameLabel(coreMessageSource.getMessage("user.firstname.label", null, locale));
		userEditViewBean.setLastnameLabel(coreMessageSource.getMessage("user.lastname.label", null, locale));
		userEditViewBean.setEmailLabel(coreMessageSource.getMessage("user.email.label", null, locale));
		userEditViewBean.setPasswordLabel(coreMessageSource.getMessage("user.password.label", null, locale));
		userEditViewBean.setActiveLabel(coreMessageSource.getMessage("user.active.label", null, locale));
		
		userEditViewBean.setDateCreateLabel(coreMessageSource.getMessage("user.date.create.label", null, locale));
		userEditViewBean.setDateUpdateLabel(coreMessageSource.getMessage("user.date.update.label", null, locale));
		
		userEditViewBean.setCancelLabel(coreMessageSource.getMessage("user.edit.cancel.label", null, locale));
		userEditViewBean.setBackUrl(requestUtil.getLastRequestUrl(request));

		userEditViewBean.setSubmitLabel(coreMessageSource.getMessage("user.edit.submit.label", null, locale));
		userEditViewBean.setFormSubmitUrl(boTechnicalUrlService.buildUserEditUrl(request, user.getId().toString()));
		
		return userEditViewBean;
	}
	
	/**
     * 
     */
	public List<BatchViewBean> buildBatchViewBeans(final HttpServletRequest request, final Localization localization, final List<BatchProcessObject> batchProcessObjects) throws Exception {
		final List<BatchViewBean> batchViewBeans = new ArrayList<BatchViewBean>();
		for (Iterator<BatchProcessObject> iterator = batchProcessObjects.iterator(); iterator.hasNext();) {
			BatchProcessObject batchProcessObject = (BatchProcessObject) iterator.next();
			batchViewBeans.add(buildBatchViewBean(request, localization, batchProcessObject));
		}
		return batchViewBeans;
	}
	
	/**
     * 
     */
	public BatchViewBean buildBatchViewBean(final HttpServletRequest request, final Localization localization, final BatchProcessObject batchProcessObject) throws Exception {
		final BatchViewBean batchViewBean = new BatchViewBean();
		batchViewBean.setId(batchProcessObject.getId());
		batchViewBean.setStatus(batchProcessObject.getStatus());
		batchViewBean.setTypeObject(batchProcessObject.getTypeObject());
		batchViewBean.setProcessedCount(batchProcessObject.getProcessedCount());
		return batchViewBean;
	}

}
