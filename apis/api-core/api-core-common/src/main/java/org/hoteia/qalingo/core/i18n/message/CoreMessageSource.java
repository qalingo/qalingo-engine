/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.i18n.message;

import java.util.Locale;
import java.util.Map;

import org.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeDocumentMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeEmailMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

public interface CoreMessageSource extends MessageSource {

    /**
     * Common-Message
     */
    String getCommonMessage(ScopeCommonMessage scope, String key, Locale locale);

    String getCommonMessage(ScopeCommonMessage scope, String key, Object[] params, Locale locale);

    /**
     * Email-Message
     */
    String getEmailMessage(ScopeEmailMessage scope, String key, Locale locale);

    String getEmailMessage(ScopeEmailMessage scope, String key, Object[] params, Locale locale);

    /**
     * Document-Message
     */
    String getDocumentMessage(ScopeDocumentMessage scope, String key, Locale locale);

    String getDocumentMessage(ScopeDocumentMessage scope, String key, Object[] params, Locale locale);

    /**
     * Specific
     */
    String getSpecificMessage(I18nKeyValueUniverse universe, ScopeWebMessage scope, String key, Locale locale);

    String getSpecificMessage(I18nKeyValueUniverse universe, ScopeWebMessage scope, String key, Object[] params, Locale locale);

    /**
     * ReferenceData
     */
    String getReferenceData(ScopeReferenceDataMessage scope, String key, Locale locale);

    String getReferenceData(ScopeReferenceDataMessage scope, String key, Object[] params, Locale locale);

    /**
     * Load all wording key/value by webappUnivers and univers/group
     */
    Map<String, String> loadWording(EngineSettingWebAppContext context, String group, Locale locale);

    /**
     * Load all wording key/value by webappUnivers
     */
    Map<String, String> loadWording(EngineSettingWebAppContext context, Locale locale);

    Map<String, String> loadWording(String pattern, Locale locale);

    String getMessage(String code, String defaultMessage, Locale locale);

    String getMessage(String code, Object args[], String defaultMessage, Locale locale);

    String getMessage(String code, Locale locale) throws NoSuchMessageException;

    String getMessage(String code, Object args[], Locale locale) throws NoSuchMessageException;

    String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException;

    void clearCache();
}
