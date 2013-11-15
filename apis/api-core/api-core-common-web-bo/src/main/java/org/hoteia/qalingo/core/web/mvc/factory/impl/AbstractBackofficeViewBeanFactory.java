package org.hoteia.qalingo.core.web.mvc.factory.impl;

import java.util.Locale;

import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.web.mvc.factory.AbstractViewBeanFactory;

public abstract class AbstractBackofficeViewBeanFactory extends AbstractViewBeanFactory {

	protected String getSpecificMessage(ScopeWebMessage scope, String key, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO, scope, key, locale);
	}
	
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO, scope, key, params, locale);
	}
	
}