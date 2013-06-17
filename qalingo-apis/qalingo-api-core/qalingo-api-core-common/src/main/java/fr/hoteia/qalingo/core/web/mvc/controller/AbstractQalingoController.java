package fr.hoteia.qalingo.core.web.mvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.UrlService;

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

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static ApplicationContext ctx = null;

	@Autowired
	protected CoreMessageSource coreMessageSource;

	@Autowired
    protected UrlService urlService;
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initVelocityLayout(final HttpServletRequest request, final Model model) throws Exception {
		// Velocity layout mandatory attributes
		model.addAttribute(Constants.VELOCITY_LAYOUT_ATTRIBUTE_HEAD_CONTENT, "../_include/head-common-empty-content.vm");
		model.addAttribute(Constants.VELOCITY_LAYOUT_ATTRIBUTE_FOOTER_SCRIPT_CONTENT, "../_include/body-footer-empty-script-content.vm");
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMessages(final HttpServletRequest request, final Model model) throws Exception {
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