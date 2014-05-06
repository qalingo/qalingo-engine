/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.i18n.message.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeDocumentMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeEmailMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.i18n.message.ExtReloadableResourceBundleMessageSource;

@Service("coreMessageSource")
public class CoreMessageSourceImpl implements CoreMessageSource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExtReloadableResourceBundleMessageSource messageSource;

    /**
     * Common-Message
     */
    public String getCommonMessage(final ScopeCommonMessage scope, String key, final Locale locale) {
        return getMessage(buildCommonFullKey(scope, key), locale);
    }

    public String getCommonMessage(final ScopeCommonMessage scope, String key, final Object[] params, final Locale locale) {
        return getMessage(buildCommonFullKey(scope, key), params, locale);
    }

    /**
     * Email-Message
     */
    public String getEmailMessage(final ScopeEmailMessage scope, String key, final Locale locale) {
        return getMessage(buildEmailFullKey(scope, key), locale);
    }

    public String getEmailMessage(final ScopeEmailMessage scope, String key, final Object[] params, final Locale locale) {
        return getMessage(buildEmailFullKey(scope, key), params, locale);
    }
    
    /**
     * Document-Message
     */
    public String getDocumentMessage(final ScopeDocumentMessage scope, String key, final Locale locale) {
        return getMessage(buildDocumentFullKey(scope, key), locale);
    }

    public String getDocumentMessage(final ScopeDocumentMessage scope, String key, final Object[] params, final Locale locale) {
        return getMessage(buildDocumentFullKey(scope, key), params, locale);
    }

    /**
     * Specific
     */
    public String getSpecificMessage(final I18nKeyValueUniverse universe, final ScopeWebMessage scope, String key, final Locale locale) {
        return getMessage(buildSpecificFullKey(universe, scope, key), locale);
    }

    public String getSpecificMessage(final I18nKeyValueUniverse universe, final ScopeWebMessage scope, String key, final Object[] params, final Locale locale) {
        return getMessage(buildSpecificFullKey(universe, scope, key), params, locale);
    }

    /**
     * ReferenceData
     */
    public String getReferenceData(final ScopeReferenceDataMessage scope, String key, final Locale locale) {
        return getMessage(buildReferenceDataFullKey(scope, key), locale);
    }

    public String getReferenceData(final ScopeReferenceDataMessage scope, String key, final Object[] params, final Locale locale) {
        return getMessage(buildReferenceDataFullKey(scope, key), params, locale);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hoteia.qalingo.core.i18n.message.CoreMessageSource#loadWording(org
     * .hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext,
     * java.lang.String, java.util.Locale)
     */
    public Map<String, String> loadWording(final EngineSettingWebAppContext context, final String group, final Locale locale) {
        final Map<String, String> wordingKeyValues = loadWording(context, locale);
        final Map<String, String> wordingFilteredKeyValues = new HashMap<String, String>();
        for (Iterator<String> iterator = wordingKeyValues.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if (key.contains(group)) {
                wordingFilteredKeyValues.put(key, wordingKeyValues.get(key));
            }
        }
        return wordingFilteredKeyValues;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hoteia.qalingo.core.i18n.message.CoreMessageSource#loadWording(org
     * .hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext,
     * java.util.Locale)
     */
    public Map<String, String> loadWording(final EngineSettingWebAppContext context, final Locale locale) {
        final Map<String, String> wordingKeyValues = new HashMap<String, String>();
        if (context != null) {
            String[] contextSplit = context.getPropertyKey().split("_");
            String prefix = contextSplit[0].toLowerCase() + "-";
            wordingKeyValues.putAll(loadWording(prefix, locale));
        }
        return wordingKeyValues;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hoteia.qalingo.core.i18n.message.CoreMessageSource#loadWording(java
     * .util.String, java.util.Locale)
     */
    public Map<String, String> loadWording(final String pattern, final Locale locale) {
        final Map<String, String> wordingKeyValues = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(pattern)) {
            List<String> allFileNames = messageSource.getFileBasenames();
            for (Iterator<String> iterator = allFileNames.iterator(); iterator.hasNext();) {
                String fileName = (String) iterator.next();
                if (fileName.contains("wording") && (fileName.contains("common-") || fileName.contains(pattern))) {
                    wordingKeyValues.putAll(messageSource.getWordingProperties(fileName, locale));
                }
            }
        }
        return wordingKeyValues;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(java
     * .lang.String, java.lang.String, java.util.Locale)
     */
    public String getMessage(final String code, final String defaultMessage, final Locale locale) {
        Object args[] = {};
        return getMessage(code, args, defaultMessage, locale);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(java
     * .lang.String, java.lang.Object[], java.lang.String, java.util.Locale)
     */
    public String getMessage(final String code, final Object args[], final String defaultMessage, final Locale locale) {
        try {
            return messageSource.getMessage(code, args, defaultMessage, locale);
        } catch (Exception e) {
            if (code != null && code.contains("javax")) {
                logger.info("This message key doesn't exist: " + code + ", for this locale: " + locale.toString());
            } else {
                logger.info("This message key doesn't exist: " + code + ", for this locale: " + locale.toString());
            }
            if (BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))) {
                return getMessage(code, args, defaultMessage, new Locale(Constants.DEFAULT_LOCALE_CODE));
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(java
     * .lang.String, java.util.Locale)
     */
    public String getMessage(final String code, final Locale locale) throws NoSuchMessageException {
        Object args[] = {};
        return getMessage(code, args, locale);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(java
     * .lang.String, java.lang.Object[], java.util.Locale)
     */
    public String getMessage(final String code, final Object args[], final Locale locale) throws NoSuchMessageException {
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            logger.info("This message key doesn't exist: " + code + ", for this locale: " + locale.toString());
            if (BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))) {
                return getMessage(code, args, new Locale(Constants.DEFAULT_LOCALE_CODE));
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hoteia.qalingo.core.i18n.message.CoreMessageSource#getMessage(org
     * .springframework.context.MessageSourceResolvable, java.util.Locale)
     */
    public String getMessage(final MessageSourceResolvable resolvable, final Locale locale) throws NoSuchMessageException {
        try {
            return messageSource.getMessage(resolvable, locale);
        } catch (Exception e) {
            logger.info("This message key doesn't exist: " + resolvable.getCodes() + ", for this locale: " + locale.toString());
            if (BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))) {
                return getMessage(resolvable, new Locale(Constants.DEFAULT_LOCALE_CODE));
            }
        }
        return null;
    }

    public void clearCache() {
        messageSource.clearCache();
    }

    private String buildCommonFullKey(final ScopeCommonMessage scope, String key) {
        return I18nKeyValueUniverse.COMMON.getPropertyKey() + "." + scope.getPropertyKey() + "." + handleKey(key);
    }

    private String buildEmailFullKey(final ScopeEmailMessage scope, String key) {
        return I18nKeyValueUniverse.EMAIL.getPropertyKey() + "." + scope.getPropertyKey() + "." + handleKey(key);
    }

    private String buildDocumentFullKey(final ScopeDocumentMessage scope, String key) {
        return I18nKeyValueUniverse.DOCUMENT.getPropertyKey() + "." + scope.getPropertyKey() + "." + handleKey(key);
    }

    private String buildSpecificFullKey(final I18nKeyValueUniverse universe, final ScopeWebMessage scope, String key) {
        return universe.getPropertyKey() + "." + scope.getPropertyKey() + "." + handleKey(key);
    }

    private String buildReferenceDataFullKey(final ScopeReferenceDataMessage scope, String key) {
        return scope.getPropertyKey() + "." + handleKey(key);
    }

    private String handleKey(String key) {
        key = key.replaceAll("\\.", "_");
        key = key.replaceAll("\\-", "_");
        return key;
    }

}