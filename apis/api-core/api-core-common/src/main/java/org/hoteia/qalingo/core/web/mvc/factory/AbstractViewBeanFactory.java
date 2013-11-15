package org.hoteia.qalingo.core.web.mvc.factory;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;

public abstract class AbstractViewBeanFactory {

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
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