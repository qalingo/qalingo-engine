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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.i18n.FoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.security.helper.SecurityUtil;
import org.hoteia.qalingo.core.security.util.SecurityRequestUtil;
import org.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.qalingo.core.service.BackofficeUrlService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.LocalizationService;
import org.hoteia.qalingo.core.service.UserService;
import org.hoteia.qalingo.core.service.WebBackofficeService;
import org.hoteia.qalingo.core.web.mvc.factory.BackofficeFormFactory;
import org.hoteia.qalingo.core.web.mvc.factory.BackofficeViewBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

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

	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BackofficeUrlService backofficeUrlService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected LocalizationService localizationService;

    @Autowired
    protected AttributeService attributeService;

    @Autowired
    protected EngineSettingService engineSettingService;

    @Autowired
    protected BackofficeViewBeanFactory backofficeViewBeanFactory;

    @Autowired
    protected BackofficeFormFactory backofficeFormFactory;

    @Autowired
    protected WebBackofficeService webBackofficeService;
    
    @Autowired
    protected SecurityRequestUtil securityRequestUtil;
    
    @Autowired
    protected SecurityUtil securityUtil;

    /**
     * 
     */
    protected void overrideDefaultPageTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKey) throws Exception {
        overrideDefaultPageTitle(request, modelAndView, titleKey, null);
    }
    
    /**
     * 
     */
    protected void overrideDefaultPageTitle(final HttpServletRequest request, final ModelAndView modelAndView, String pageTitleKey, Object[] params) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        String headerTitle = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_PAGE_TITLE_SITE_NAME, locale);
        if(StringUtils.isNotEmpty(pageTitleKey)){
            pageTitleKey = pageTitleKey.replace("-", "_");
            if(params != null){
                headerTitle += " - " + getSpecificMessage(ScopeWebMessage.HEADER_TITLE, pageTitleKey, params, locale);
            } else {
                headerTitle += " - " + getSpecificMessage(ScopeWebMessage.HEADER_TITLE, pageTitleKey, locale);
            }
            modelAndView.addObject(ModelConstants.PAGE_TITLE, headerTitle);
        }
    }
    
    /**
     * 
     */
    protected void overrideDefaultMainContentTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKey) throws Exception {
        overrideDefaultMainContentTitle(request, modelAndView, titleKey, null);
    }
    
    /**
     * 
     */
    protected void overrideDefaultMainContentTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKey, Object[] params) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        String pageTitleKey = titleKey;
        String headerTitle = "";
        if(params != null){
            headerTitle = getSpecificMessage(ScopeWebMessage.HEADER_TITLE, pageTitleKey, params, locale);
        } else {
            headerTitle = getSpecificMessage(ScopeWebMessage.HEADER_TITLE, pageTitleKey, locale);
        }
        modelAndView.addObject(ModelConstants.PAGE_TITLE, headerTitle);
    }
	
	/**
	 * 
	 */
	protected String getMessageTitleKey(final String pageKey) throws Exception {
		return "page_title_" + pageKey;
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
		return getSpecificMessage(scope.getPropertyKey(), key, locale);
	}
	
	/**
	 * 
	 */
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale){
		return getSpecificMessage(scope.getPropertyKey(), key, params, locale);
	}
	
	   /**
     * 
     */
    protected String getSpecificMessage(String scope, String key, Locale locale){
        return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO.getPropertyKey(), scope, key, locale);
    }
    
    /**
     * 
     */
    protected String getSpecificMessage(String scope, String key, Object[] params, Locale locale){
        return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO.getPropertyKey(), scope, key, params, locale);
    }
    
}