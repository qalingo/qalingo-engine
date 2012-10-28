/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;

/**
 * 
 * <p>
 * <a href="AbstractQalingoController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractQalingoController extends AbstractController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static ApplicationContext ctx = null;

	@Autowired
	protected CoreMessageSource coreMessageSource;

	@Autowired
    protected RequestUtil requestUtil;
	
	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		return null;
	}

	/**
	 * 
	 */
	protected void initPage(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final String titleKeyPrefixSufix) throws Exception {
		initCommon(request, response, modelAndView, titleKeyPrefixSufix);
		initSeo(request, modelAndView, titleKeyPrefixSufix);
    }
	
	/**
	 * 
	 */
	protected void initCommon(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final String titleKeyPrefixSufix) throws Exception {
		final Locale locale = getCurrentLocale(request);
		modelAndView.addObject("localeLanguageCode", locale.getLanguage());
		modelAndView.addObject("contextPath", request.getContextPath());
		modelAndView.addObject("theme", requestUtil.getCurrentTheme(request));
		
		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request, EngineSettingService.ENGINE_SETTING_CONTEXT_FO_PREHOME);
		modelAndView.addObject("themeResourcePrefixPath", currentThemeResourcePrefixPath);
	}
	
	/**
	 * 
	 */
	protected void initSeo(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKeyPrefixSufix) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		String seoPageMetaAuthor = coreMessageSource.getMessage("seo.meta.author", null, locale);
        modelAndView.addObject("seoPageMetaAuthor", seoPageMetaAuthor);

		String seoPageMetaKeywords = coreMessageSource.getMessage("page.meta.keywords", null, locale);
        modelAndView.addObject("seoPageMetaKeywords", seoPageMetaKeywords);

		String seoPageMetaDescription = coreMessageSource.getMessage("page.meta.description", null, locale);
        modelAndView.addObject("seoPageMetaDescription", seoPageMetaDescription);

		String pageTitleKey = "header.title." + titleKeyPrefixSufix;
		String seoPageTitle = coreMessageSource.getMessage("page.title.prefix", null, locale) + " - " + coreMessageSource.getMessage(pageTitleKey, null, locale);
        modelAndView.addObject("seoPageTitle", seoPageTitle);
	}
	
	/**
	 * Convenience method to bind objects in Actions
	 * 
	 * @param name
	 * @return
	 */
	public ApplicationContext getContext() {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		}
		return ctx;
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
		return requestUtil.getCurrentVelocityPrefix(request);
	}
	

}
