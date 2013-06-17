/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.i18n.message.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nBasename;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeEmailMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.WebappUniverse;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.i18n.message.ExtReloadableResourceBundleMessageSource;

@Service("coreMessageSource")
public class CoreMessageSourceImpl implements CoreMessageSource {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private ExtReloadableResourceBundleMessageSource messageSource;
	
	// Common-Message
	public String getCommonMessage(ScopeCommonMessage scope, String key, Locale locale){
		return getMessage(buildCommonFullKey(scope, key), locale);
	}
	
	public String getCommonMessage(ScopeCommonMessage scope, String key, Object[] params, Locale locale){
		return getMessage(buildCommonFullKey(scope, key), params, locale);
	}
	
	// Email-Message
	public String getEmailMessage(ScopeEmailMessage scope, String key, Locale locale){
		return getMessage(buildEmailFullKey(scope, key), locale);
	}
	
	public String getEmailMessage(ScopeEmailMessage scope, String key, Object[] params, Locale locale){
		return getMessage(buildEmailFullKey(scope, key), params, locale);
	}
	
	// Specific
	public String getSpecificMessage(I18nKeyValueUniverse universe, ScopeWebMessage scope, String key, Locale locale){
		return getMessage(buildSpecificFullKey(universe, scope, key), locale);
	}
	
	public String getSpecificMessage(I18nKeyValueUniverse universe, ScopeWebMessage scope, String key, Object[] params, Locale locale){
		return getMessage(buildSpecificFullKey(universe, scope, key), params, locale);
	}
	
	// ReferenceData
	public String getReferenceData(ScopeReferenceDataMessage scope, String key, Locale locale){
		return getMessage(buildReferenceDataFullKey(scope, key), locale);
	}
	
	public String getReferenceData(ScopeReferenceDataMessage scope, String key, Object[] params, Locale locale){
		return getMessage(buildReferenceDataFullKey(scope, key), params, locale);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.i18n.message.CoreMessageSource#loadWording(fr.hoteia.qalingo.core.domain.enumtype.WebappUniverse, java.lang.String, java.util.Locale)
	 */
	public Map<String, String> loadWording(WebappUniverse webappUniverse, String group, Locale locale) {
		final Map<String, String> wordingKeyValues = loadWording(webappUniverse, locale);
		final Map<String, String> wordingFilteredKeyValues = new HashMap<String, String>();
		for (Iterator<String> iterator = wordingKeyValues.keySet().iterator(); iterator.hasNext();) {
			final String key = (String) iterator.next();
			if(key.contains(group)){
				wordingFilteredKeyValues.put(key, wordingKeyValues.get(key));
			}
		}
		return wordingFilteredKeyValues;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.i18n.message.CoreMessageSource#loadWording(fr.hoteia.qalingo.core.domain.enumtype.WebappUniverse, java.util.Locale)
	 */
	public Map<String, String> loadWording(WebappUniverse webappUniverse, Locale locale) {
		final Map<String, String> wordingKeyValues = new HashMap<String, String>();
		wordingKeyValues.putAll(messageSource.getWordingProperties(I18nBasename.COMMON_BASENAME, locale));
		wordingKeyValues.putAll(messageSource.getWordingProperties(WebappUniverse.getI18nBasenameAssociated(webappUniverse), locale));
		return wordingKeyValues;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(java.lang.String, java.lang.String, java.util.Locale)
	 */
	public String getMessage(String code, String defaultMessage, Locale locale) {
		Object args[] = {};
		return getMessage(code, args, defaultMessage, locale);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(java.lang.String, java.lang.Object[], java.lang.String, java.util.Locale)
	 */
	public String getMessage(String code, Object args[], String defaultMessage, Locale locale) {
		try {
			return messageSource.getMessage(code, args, defaultMessage, locale);
		} catch (Exception e) {
			LOG.error("This message key doesn't exist: " + code + ", for this locale: " + locale.toString());
			if(BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))){
				return getMessage(code, args, defaultMessage, new Locale(Constants.DEFAULT_LOCALE_CODE));
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(java.lang.String, java.util.Locale)
	 */
	public String getMessage(String code, Locale locale) throws NoSuchMessageException {
		Object args[] = {};
		return getMessage(code, args, locale);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(java.lang.String, java.lang.Object[], java.util.Locale)
	 */
	public String getMessage(String code, Object args[], Locale locale) throws NoSuchMessageException {
		try {
			return messageSource.getMessage(code, args, locale);
		} catch (Exception e) {
			LOG.error("This message key doesn't exist: " + code + ", for this locale: " + locale.toString());
			if(BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))){
				return getMessage(code, args, new Locale(Constants.DEFAULT_LOCALE_CODE));
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(org.springframework.context.MessageSourceResolvable, java.util.Locale)
	 */
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		try {
			return messageSource.getMessage(resolvable, locale);
		} catch (Exception e) {
			LOG.error("This message key doesn't exist: " + resolvable.getCodes() + ", for this locale: " + locale.toString());
			if(BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))){
				return getMessage(resolvable, new Locale(Constants.DEFAULT_LOCALE_CODE));
			}
		}
		return null;
	}
	
	private String buildCommonFullKey(ScopeCommonMessage scope, String key){
		return I18nKeyValueUniverse.COMMON.getPropertyKey() + "." + scope.getPropertyKey() + "." + key.replaceAll("\\.", "_");
	}
	
	private String buildEmailFullKey(ScopeEmailMessage scope, String key){
		return I18nKeyValueUniverse.EMAIL.getPropertyKey() + "." + scope.getPropertyKey() + "." + key.replaceAll("\\.", "_");
	}
	
	private String buildSpecificFullKey(I18nKeyValueUniverse universe, ScopeWebMessage scope, String key){
		return universe.getPropertyKey() + "." + scope.getPropertyKey() + "." + key.replaceAll("\\.", "_");
	}
	
	private String buildReferenceDataFullKey(ScopeReferenceDataMessage scope, String key){
		return scope.getPropertyKey() + "." + key.replaceAll("\\.", "_");
	}

}