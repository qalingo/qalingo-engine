package fr.hoteia.qalingo.web.mvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.i18n.FoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.FollowUsViewBean;

/**
 * 
 * <p>
 * <a href="AbstractFrontofficeQalingoController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractFrontofficeQalingoController extends AbstractQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CustomerService customerService;

	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initSeo(final HttpServletRequest request, final Model model) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		String seoPageMetaAuthor = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_AUTHOR, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_AUTHOR, seoPageMetaAuthor);

		String seoPageMetaKeywords = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_KEYWORDS, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_KEYWORDS, seoPageMetaKeywords);

		String seoPageMetaDescription = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_DESCRIPTION, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_DESCIPRTION, seoPageMetaDescription);

		String seoPageTitle = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_PAGE_TITLE_SITE_NAME, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_TITLE, seoPageTitle);
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.URL_SUBMIT_QUICK_SEARCH)
	protected String initQuickSearch(final HttpServletRequest request, final Model model) throws Exception {
		// QUICK SEARCH
		return urlService.generateUrl(FoUrls.SEARCH, requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.FOLLOW_US_VIEW_BEAN)
	protected FollowUsViewBean initFollowUs(final HttpServletRequest request, final Model model) throws Exception {
		// QUICK SEARCH
		final FollowUsViewBean followUs = viewBeanFactory.buildFollowUsViewBean(requestUtil.getRequestData(request));
		return followUs;
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.XRDS_URL_VIEW_BEAN)
	protected String setXrdsUrl(final HttpServletRequest request, final Model model) throws Exception {
		String xrdsURL = urlService.generateUrl(FoUrls.XRDS, requestUtil.getRequestData(request));
		String fullXrdsURL = urlService.buildAbsoluteUrl(requestUtil.getRequestData(request), xrdsURL);
		return fullXrdsURL;
	}
	
	/**
	 * 
	 */
	protected void overrideSeoTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String title) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final String fullTitle = currentMarketPlace.getName() + " - " + title;
		if(StringUtils.isNotEmpty(fullTitle)){
	        modelAndView.addObject("seoPageTitle", fullTitle);
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
	protected String getTheme(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentTheme(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getDevice(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentDevice(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentVelocityPath(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentVelocityWebPrefix(request);
	}
	/**
	 * 
	 */
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, scope, key, locale);
	}
	
	/**
	 * 
	 */
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, scope, key, params, locale);
	}
}