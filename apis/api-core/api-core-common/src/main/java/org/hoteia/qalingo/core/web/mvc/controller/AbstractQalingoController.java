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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.ReferentialDataService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.mvc.viewbean.MonitoringViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.TrackingViewBean;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 
 * <p>
 * <a href="AbstractQalingoController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractQalingoController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CoreMessageSource coreMessageSource;

	@Autowired
	protected EngineSettingService engineSettingService;
	
	@Autowired
    protected UrlService urlService;
	
	@Autowired
    protected ReferentialDataService referentialDataService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initVelocityLayout(final HttpServletRequest request, final Model model) throws Exception {
		// Velocity layout mandatory attributes
		model.addAttribute(Constants.VELOCITY_LAYOUT_ATTRIBUTE_HEAD_META, "../_include/head-common-empty-content.vm");
		model.addAttribute(Constants.VELOCITY_LAYOUT_ATTRIBUTE_HEAD_CONTENT, "../_include/head-common-empty-content.vm");
		model.addAttribute(Constants.VELOCITY_LAYOUT_ATTRIBUTE_FOOTER_SCRIPT_CONTENT, "../_include/body-footer-empty-script-content.vm");
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void handleMessages(final HttpServletRequest request, final Model model) throws Exception {
		// WE USE SESSION FOR MESSAGES BECAUSE REDIRECT CLEAN REQUEST
		// ERROR MESSAGE
		String errorMessage = (String) request.getSession().getAttribute(Constants.ERROR_MESSAGE);
		if(StringUtils.isNotEmpty(errorMessage)){
			model.addAttribute(Constants.ERROR_MESSAGE, errorMessage);
			request.getSession().removeAttribute(Constants.ERROR_MESSAGE);
		}
		// INFO MESSAGE
		String infoMessage = (String) request.getSession().getAttribute(Constants.INFO_MESSAGE);
		if(StringUtils.isNotEmpty(infoMessage)){
			model.addAttribute(Constants.INFO_MESSAGE, infoMessage);
			request.getSession().removeAttribute(Constants.INFO_MESSAGE);
		}
		// SUCCESS MESSAGE
		String successMessage = (String) request.getSession().getAttribute(Constants.SUCCESS_MESSAGE);
		if(StringUtils.isNotEmpty(successMessage)){
			model.addAttribute(Constants.SUCCESS_MESSAGE, successMessage);
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE);
		}
	}

	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.TRACKING_VIEW_BEAN)
	protected TrackingViewBean initTracking(final HttpServletRequest request, final Model model) throws Exception {
		TrackingViewBean trackingViewBean = null;
    	final String contextValue = requestUtil.getCurrentContextNameValue(request);

	    EngineSetting webTrackingNumberEngineSetting = engineSettingService.getWebTrackingNumber();
	    EngineSettingValue webTrackingNumberEngineSettingValue = webTrackingNumberEngineSetting.getEngineSettingValue(contextValue);
	    if(webTrackingNumberEngineSettingValue != null
	    		&& StringUtils.isNotEmpty(webTrackingNumberEngineSettingValue.getValue())){
	    	trackingViewBean = new TrackingViewBean();
	    	trackingViewBean.setTrackingNumber(webTrackingNumberEngineSettingValue.getValue());
	    	
		    EngineSetting webTrackingNameEngineSetting = engineSettingService.getWebTrackingName();
		    EngineSettingValue webTrackingNameEngineSettingValue = webTrackingNameEngineSetting.getEngineSettingValue(contextValue);
		    if(webTrackingNameEngineSettingValue != null){
		    	trackingViewBean.setTrackingName(webTrackingNameEngineSettingValue.getValue());
		    }

	    }
		return trackingViewBean;
	}
	
    /**
     * 
     */
    @ModelAttribute(ModelConstants.URL_BACK)
    protected String initBackUrl(final HttpServletRequest request, final Model model) throws Exception {
        String url = requestUtil.getCurrentRequestUrl(request);
        List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add(url);
        return requestUtil.getLastRequestUrl(request, excludedPatterns);
    }

	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MONITORING_VIEW_BEAN)
	protected MonitoringViewBean initMonitoring(final HttpServletRequest request, final Model model) throws Exception {
		MonitoringViewBean monitoringViewBean = new MonitoringViewBean();
    	final String contextValue = requestUtil.getCurrentContextNameValue(request);
	    EngineSetting webMonitoringNumberEngineSetting = engineSettingService.getWebMonitoringNumber();
	    if(webMonitoringNumberEngineSetting != null){
	        EngineSettingValue webMonitoringNumberEngineSettingValue = webMonitoringNumberEngineSetting.getEngineSettingValue(contextValue);
	        if(webMonitoringNumberEngineSettingValue != null
	                && StringUtils.isNotEmpty(webMonitoringNumberEngineSettingValue.getValue())){
	            monitoringViewBean = new MonitoringViewBean();
	            monitoringViewBean.setMonitoringNumber(webMonitoringNumberEngineSettingValue.getValue());
	            
	            EngineSetting webMonitoringNameEngineSetting = engineSettingService.getWebMonitoringName();
	            EngineSettingValue webMonitoringNameEngineSettingValue = webMonitoringNameEngineSetting.getEngineSettingValue(contextValue);
	            if(webMonitoringNameEngineSettingValue != null){
	                monitoringViewBean.setMonitoringName(webMonitoringNameEngineSettingValue.getValue());
	            }
	        }
	    }
		return monitoringViewBean;
	}
	
    /**
     * @throws Exception 
     * 
     */
    protected String getCurrentVelocityPath(HttpServletRequest request) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        return requestUtil.getCurrentVelocityWebPrefix(requestData);
    }
    
	protected void addMessageError(BindingResult result, Exception e, String formKey, String fieldKey, String errorMessage){
        if(StringUtils.isEmpty(errorMessage)){
        	errorMessage = ""; // EMPTY VALUE TO EVENT VELOCITY MethodInvocationException
        }
        FieldError error = new FieldError(formKey, fieldKey, errorMessage);
        result.addError(error);
        result.rejectValue(error.getField(), "");
        if(e != null){
            logger.error(errorMessage, e);
        } else {
            logger.warn(errorMessage);
        }
    }
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void addErrorMessage(HttpServletRequest request, String message) throws Exception {
		request.getSession().setAttribute(Constants.ERROR_MESSAGE, message);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void addInfoMessage(HttpServletRequest request, String message) throws Exception {
		request.getSession().setAttribute(Constants.INFO_MESSAGE, message);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void addSuccessMessage(HttpServletRequest request, String message) throws Exception {
		request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, message);
	}
	
	protected String getCommonMessage(ScopeCommonMessage scope, String key, Locale locale){
		return coreMessageSource.getCommonMessage(scope, key, locale);
	}
	
	protected String getCommonMessage(ScopeCommonMessage scope, String key, Object[] params, Locale locale){
		return coreMessageSource.getCommonMessage(scope, key, params, locale);
	}
	
	protected String getReferenceData(ScopeReferenceDataMessage scope, String key, Locale locale){
		return coreMessageSource.getReferenceData(scope, key, locale);
	}
	
	protected String getReferenceData(ScopeReferenceDataMessage scope, String key, Object[] params, Locale locale){
		return coreMessageSource.getReferenceData(scope, key, params, locale);
	}
	
}