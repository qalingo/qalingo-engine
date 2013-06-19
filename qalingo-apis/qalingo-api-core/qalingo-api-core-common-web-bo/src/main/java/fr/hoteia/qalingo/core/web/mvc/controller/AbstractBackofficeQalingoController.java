package fr.hoteia.qalingo.core.web.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Company;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.LocalizationService;
import fr.hoteia.qalingo.core.service.UserService;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegalTermsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;

/**
 * 
 * <p>
 * <a href="AbstractBackofficeQalingoController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractBackofficeQalingoController extends AbstractQalingoController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
    protected BackofficeUrlService backofficeUrlService;

	@Autowired
	protected UserService userService;
	
	@Autowired
	protected LocalizationService localizationService;
	
	@Autowired
	protected EngineSettingService engineSettingService;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;

	@Autowired
    protected RequestUtil requestUtil;
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initApp(final HttpServletRequest request, final Model model) throws Exception {
		final Locale locale  = requestUtil.getCurrentLocale(request);
		// APP NAME
		model.addAttribute(Constants.APP_NAME, getAppName(request));
		Object[] params = {StringUtils.capitalize(requestUtil.getApplicationName())};
		model.addAttribute(Constants.APP_NAME_HTML, getCommonMessage(ScopeCommonMessage.APP, "name_html", params, locale));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initConfig(final HttpServletRequest request, final Model model) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		model.addAttribute(ModelConstants.LOCALE_LANGUAGE_CODE, locale.getLanguage());
		model.addAttribute(ModelConstants.CONTEXT_PATH, request.getContextPath());
		model.addAttribute(ModelConstants.THEME, requestUtil.getCurrentTheme(request));
		Object[] params = {StringUtils.capitalize(requestUtil.getEnvironmentName())};
		model.addAttribute(ModelConstants.ENV_NAME, getSpecificMessage(ScopeWebMessage.COMMON, "header.env.name", params, locale));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initSeo(final HttpServletRequest request, final Model model) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		String seoPageMetaAuthor = getCommonMessage(ScopeCommonMessage.SEO, BoMessageKey.SEO_META_AUTHOR, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_AUTHOR, seoPageMetaAuthor);

		String seoPageMetaKeywords = getCommonMessage(ScopeCommonMessage.SEO, BoMessageKey.SEO_META_KEYWORDS, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_KEYWORDS, seoPageMetaKeywords);

		String seoPageMetaDescription = getCommonMessage(ScopeCommonMessage.SEO, BoMessageKey.SEO_META_DESCRIPTION, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_DESCIPRTION, seoPageMetaDescription);

        // DEFAULT EMPTY VALUE
        model.addAttribute(ModelConstants.SEO_PAGE_META_TITLE, getAppName(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initCommon(final HttpServletRequest request, final Model model) throws Exception {
		// COMMON
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		CommonViewBean commonViewBean = viewBeanFactory.buildCommonViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		model.addAttribute(ModelConstants.COMMON_VIEW_BEAN, commonViewBean);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initQuickSearch(final HttpServletRequest request, final Model model) throws Exception {
		// QUICK SEARCH
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		QuickSearchViewBean quickSearchViewBean = viewBeanFactory.buildQuickSearchViewBean(request, currentLocalization);
		model.addAttribute(ModelConstants.QUICK_SEARCH_VIEW_BEAN, quickSearchViewBean);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initHeader(final HttpServletRequest request, final Model model) throws Exception {
		// HEADER
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		model.addAttribute(ModelConstants.MENUS_VIEW_BEAN, viewBeanFactory.buildMenuViewBeans(request, currentLocalization));
		model.addAttribute(ModelConstants.MORE_PAGE_MENUS_VIEW_BEAN, viewBeanFactory.buildMorePageMenuViewBeans(request, currentLocalization));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initLocalizations(final HttpServletRequest request, final Model model) throws Exception {
		// LOCALIZATIONS
		Company company = requestUtil.getCurrentCompany(request);
		if(company != null){
			Set<Localization> localizations = company.getLocalizations();
			model.addAttribute(ModelConstants.LANGUAGE_VIEW_BEAN, viewBeanFactory.buildLocalizationViewBeans(request, new ArrayList<Localization>(localizations)));
		} else {
			Localization defaultLocalization = localizationService.getLocalizationByCode("en");
			List<Localization> defaultLocalizations = new ArrayList<Localization>();
			defaultLocalizations.add(defaultLocalization);
			model.addAttribute(ModelConstants.LANGUAGE_VIEW_BEAN, viewBeanFactory.buildLocalizationViewBeans(request, defaultLocalizations));
		}
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initLegalTerms(final HttpServletRequest request, final Model model) throws Exception {
		// LEGAL-TERMS
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		LegalTermsViewBean legalTermsViewBean = viewBeanFactory.buildLegalTermsViewBean(request, currentLocalization);
		model.addAttribute(ModelConstants.LEGAl_TERMS_VIEW_BEAN, legalTermsViewBean);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initFooter(final HttpServletRequest request, final Model model) throws Exception {
		// FOOTER
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		model.addAttribute(ModelConstants.FOOTER_MENUS_VIEW_BEAN, viewBeanFactory.buildFooterMenuViewBeans(request, currentLocalization));
	}
	
	/**
	 * 
	 */
	protected void overrideMainContentTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String title) throws Exception {
//		final Locale locale  = requestUtil.getCurrentLocale(request);
//		String mainContentTitleKey = BoMessageKey.MAIN_CONTENT_TITLE_PREFIX + pageKey;
//		try {
//			String mainContentTitle = getSpecificMessage(ScopeWebMessage.BREADCRUMB_AND_HEADER, mainContentTitleKey, locale);
//	        modelAndView.addObject(ModelConstants.MAIN_CONTENT_TITLE, mainContentTitle);
//		} catch (Exception e) {
//			// DOESN'T NEED WARNING OR ERROR LOG
//			LOG.debug("mainContentTitle with key header.title doesn't exist for:" + pageKey);
//		}
		if(StringUtils.isNotEmpty(title)){
			 modelAndView.addObject(ModelConstants.MAIN_CONTENT_TITLE, title);
		}
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	public void initWording(final HttpServletRequest request, final Model model) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		String contextName = requestUtil.getContextName();
		try {
			EngineSettingWebAppContext contextValue = EngineSettingWebAppContext.valueOf(contextName);
			model.addAttribute(ModelConstants.WORDING, coreMessageSource.loadWording(contextValue, locale));
	        
        } catch (Exception e) {
        	LOG.error("Context name, " + contextName + " can't be resolve by EngineSettingWebAppContext class.", e);
        }
	}
	
	/**
	 * 
	 */
	protected void overrideSeoTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String title) throws Exception {
		final String fullTitle = getAppName(request) + " - " + title;
		if(StringUtils.isNotEmpty(fullTitle)){
	        modelAndView.addObject(ModelConstants.SEO_PAGE_META_TITLE, fullTitle);
		}
	}
	
	/**
	 * 
	 */
	protected String getMessageTitleKey(final String pageKey) throws Exception {
		return "page_title_" + pageKey;
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initBreadcrumAndHeaderContent(final HttpServletRequest request, final Model model) throws Exception {
        // DEFAULT EMPTY VALUE
        model.addAttribute(ModelConstants.MAIN_CONTENT_TITLE, "");
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initUser(final HttpServletRequest request, final Model model) throws Exception {
		// USER
		final User user = getUser();
		model.addAttribute("user", user);
	}
	
	/**
	 * 
	 */
	protected User getUser(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = null;
		if(StringUtils.isNotEmpty(username)
				&& !username.equalsIgnoreCase("anonymousUser")){
			user = userService.getUserByLoginOrEmail(username);
		}
		return user;
	}
	
	/**
	 * 
	 */
	protected String getUserId(){
		User user = getUser();
		if(user != null){
			Long userId = user.getId();
			if(userId != null){
				return userId.toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getAppName(HttpServletRequest request) throws Exception {
		final Locale locale  = requestUtil.getCurrentLocale(request);
		Object[] params = {StringUtils.capitalize(requestUtil.getApplicationName())};
		String appName = getCommonMessage(ScopeCommonMessage.APP, "name_text", params, locale);
		return appName;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected Localization getCurrentLocalization(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentLocalization(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected Locale getCurrentLocale(HttpServletRequest request) throws Exception {
		return getCurrentLocalization(request).getLocale();
	}

	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentTheme(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentTheme(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentDevice(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentDevice(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentVelocityPath(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentVelocityPrefix(request);
	}
	
	/**
	 * 
	 */
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO, scope, key, locale);
	}
	
	/**
	 * 
	 */
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO, scope, key, params, locale);
	}
}