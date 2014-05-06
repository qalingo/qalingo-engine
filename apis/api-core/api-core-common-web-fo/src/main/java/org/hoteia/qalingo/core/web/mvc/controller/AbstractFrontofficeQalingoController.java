/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.FoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.FollowUsViewBean;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

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

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CustomerService customerService;

	@Autowired
    protected FrontofficeViewBeanFactory frontofficeViewBeanFactory;
	
    @Autowired
    protected RequestUtil requestUtil;

	/**
	 * 
	 */
	@ModelAttribute
	protected void initDefaultSeo(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

		String seoPageMetaAuthor = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_AUTHOR, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_AUTHOR, seoPageMetaAuthor);

		String seoPageMetaKeywords = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_KEYWORDS, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_KEYWORDS, seoPageMetaKeywords);

		String seoPageMetaDescription = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_DESCRIPTION, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_DESCIPRTION, seoPageMetaDescription);

		String seoPageTitle = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_PAGE_TITLE_SITE_NAME, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_TITLE, seoPageTitle);
        
        String metaOgShareTitle = getSpecificMessage(ScopeWebMessage.SEO, FoMessageKey.PAGE_META_OG_TITLE, locale);
        model.addAttribute(ModelConstants.PAGE_META_OG_TITLE, metaOgShareTitle);
        
        String metaOgShareDescription = getSpecificMessage(ScopeWebMessage.SEO, FoMessageKey.PAGE_META_OG_DESCRIPTION, locale);
        model.addAttribute(ModelConstants.PAGE_META_OG_DESCRIPTION, metaOgShareDescription);
        
        String metaOgShareImage = getSpecificMessage(ScopeWebMessage.SEO, FoMessageKey.PAGE_META_OG_IMAGE, locale);
        model.addAttribute(ModelConstants.PAGE_META_OG_IMAGE, metaOgShareImage);
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.URL_SUBMIT_QUICK_SEARCH)
	protected String initQuickSearch(final HttpServletRequest request, final Model model) throws Exception {
		// QUICK SEARCH
		return urlService.generateUrl(FoUrls.CATALOG_SEARCH, requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.FOLLOW_US_VIEW_BEAN)
	protected FollowUsViewBean initFollowUs(final HttpServletRequest request, final Model model) throws Exception {
		// QUICK SEARCH
		final FollowUsViewBean followUs = frontofficeViewBeanFactory.buildViewBeanFollowUs(requestUtil.getRequestData(request));
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
    protected void overrideDefaultSeoPageTitleAndMainContentTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKey) throws Exception {
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, titleKey, null);
    }
    
    /**
     * 
     */
    protected void overrideDefaultSeoPageTitleAndMainContentTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKey, Object[] params) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        String pageTitleKey = titleKey;
        String headerTitle = "";
        if(params != null){
            headerTitle = getSpecificMessage(ScopeWebMessage.HEADER_TITLE, pageTitleKey, params, locale);
        } else {
            headerTitle = getSpecificMessage(ScopeWebMessage.HEADER_TITLE, pageTitleKey, locale);
        }
        String seoPageTitle = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_PAGE_TITLE_SITE_NAME, locale);
        modelAndView.addObject(ModelConstants.SEO_PAGE_TITLE, seoPageTitle + " - " + headerTitle);
        modelAndView.addObject(ModelConstants.MAIN_CONTENT_TITLE, headerTitle);
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
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
		model.addAttribute(ModelConstants.LOCALE_LANGUAGE_CODE, locale.getLanguage());
		model.addAttribute(ModelConstants.CONTEXT_PATH, request.getContextPath());
		model.addAttribute(ModelConstants.THEME, requestUtil.getCurrentTheme(requestData));
		Object[] params = {StringUtils.capitalize(requestUtil.getEnvironmentName())};
		model.addAttribute(ModelConstants.ENV_NAME, getSpecificMessage(ScopeWebMessage.COMMON, "header.env.name", params, locale));
	}

	/**
	 * @return 
	 * 
	 */
	@ModelAttribute(ModelConstants.WORDING)
	public Map<String, String> initWording(final HttpServletRequest request, final Model model) throws Exception {
		try {
			return getWordingMap(request);
        } catch (Exception e) {
        	logger.error("Context name, " + requestUtil.getContextName() + " can't be resolve by EngineSettingWebAppContext class.", e);
        }
		return null;
	}
	
	protected Map<String, String> getWordingMap(final HttpServletRequest request){
		try {
	        final RequestData requestData = requestUtil.getRequestData(request);
	        final Locale locale = requestData.getLocale();
			String contextName = requestUtil.getContextName();
			EngineSettingWebAppContext contextValue = EngineSettingWebAppContext.valueOf(contextName);
			return coreMessageSource.loadWording(contextValue, locale);
	        
        } catch (Exception e) {
        	logger.error("Failed to load wording map.", e);
        }
		return null;
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