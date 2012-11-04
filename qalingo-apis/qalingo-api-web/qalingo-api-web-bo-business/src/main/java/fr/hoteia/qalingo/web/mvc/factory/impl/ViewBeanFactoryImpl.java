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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.service.BoBusinessUrlService;

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
    protected BoBusinessUrlService boBusinessUrlService;
	
	/**
     * 
     */
	public CommonViewBean buildCommonViewBean(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final CommonViewBean commonViewBean = new CommonViewBean();
		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request, EngineSettingService.ENGINE_SETTING_CONTEXT_BO_BUSINESS);
		commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

		commonViewBean.setHomeUrl(boBusinessUrlService.buildHomeUrl(request));
		commonViewBean.setLoginUrl(boBusinessUrlService.buildLoginUrl(request));
		commonViewBean.setLoginLabel(coreMessageSource.getMessage("header.link.login", null, locale));
//		commonViewBean.setForgottenPasswordUrl(urlService.buildContactUrl(request));
		commonViewBean.setLogoutUrl(boBusinessUrlService.buildLogoutUrl(request));
		commonViewBean.setLogoutLabel(coreMessageSource.getMessage("header.link.logout", null, locale));
		commonViewBean.setUserDetailsUrl(boBusinessUrlService.buildUserDetailsUrl(request));
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
		menu.setName(coreMessageSource.getMessage("header.menu.home", null, locale));
		menu.setUrl(boBusinessUrlService.buildHomeUrl(request));
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setName("Engine Setting");
		menu.setUrl(boBusinessUrlService.buildEngineSettingUrl(request));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setName("Cache");
		menu.setUrl(boBusinessUrlService.buildCacheUrl(request));
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setName("Users");
		menu.setUrl(boBusinessUrlService.buildUserListUrl(request));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setName("Batch");
		menu.setUrl(boBusinessUrlService.buildBatchUrl(request));
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
		footerMenuList.setUrl(boBusinessUrlService.buildHomeUrl(request));
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
		
		localizationViewBean.setUrl(boBusinessUrlService.buildChangeLanguageUrl(request, localization));
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
		security.setLoginUrl(boBusinessUrlService.buildSpringSecurityCheckUrl(request));
		security.setLoginLabel(coreMessageSource.getMessage("login.form.login.label", null, locale));
		security.setForgottenPasswordUrl(boBusinessUrlService.buildForgottenPasswordUrl(request));
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
		quickSsearch.setUrlFormSubmit(boBusinessUrlService.buildSearchUrl(request));
		return quickSsearch;
	}
}
