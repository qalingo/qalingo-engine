/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;

public abstract class AbstractViewBeanFactory {

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
    protected String getSpecificMessage(ScopeWebMessage scope, String key, Locale locale) {
        return getSpecificMessage(scope.getPropertyKey(), key, locale);
    }

    protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale) {
        return getSpecificMessage(scope.getPropertyKey(), key, params, locale);
    }

    protected String getSpecificMessage(String scope, String key, Locale locale) {
        return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO.getPropertyKey(), scope, key, locale);
    }

    protected String getSpecificMessage(String scope, String key, Object[] params, Locale locale) {
        return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO.getPropertyKey(), scope, key, params, locale);
    }

    protected String getCommonMessage(ScopeCommonMessage scope, String key, Locale locale) {
        return getCommonMessage(scope.getPropertyKey(), key, locale);
    }

    protected String getCommonMessage(ScopeCommonMessage scope, String key, Object[] params, Locale locale) {
        return getCommonMessage(scope.getPropertyKey(), key, params, locale);
    }

    protected String getCommonMessage(String scope, String key, Locale locale) {
        return coreMessageSource.getCommonMessage(scope, key, locale);
    }

    protected String getCommonMessage(String scope, String key, Object[] params, Locale locale) {
        return coreMessageSource.getCommonMessage(scope, key, params, locale);
    }

    protected String getReferenceData(ScopeReferenceDataMessage scope, String key, Locale locale) {
        return getReferenceData(scope.getPropertyKey(), key, locale);
    }

    protected String getReferenceData(ScopeReferenceDataMessage scope, String key, Object[] params, Locale locale) {
        return getReferenceData(scope.getPropertyKey(), key, params, locale);
    }

    protected String getReferenceData(String scope, String key, Locale locale) {
        return coreMessageSource.getReferenceData(scope, key, locale);
    }

    protected String getReferenceData(String scope, String key, Object[] params, Locale locale) {
        return coreMessageSource.getReferenceData(scope, key, params, locale);
    }

}