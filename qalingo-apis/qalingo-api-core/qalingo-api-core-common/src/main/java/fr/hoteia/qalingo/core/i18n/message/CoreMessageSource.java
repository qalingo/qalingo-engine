package fr.hoteia.qalingo.core.i18n.message;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import fr.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeEmailMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;

public interface CoreMessageSource extends MessageSource {

	// Common-Message
	String getCommonMessage(ScopeCommonMessage scope, String key, Locale locale);
	
	String getCommonMessage(ScopeCommonMessage scope, String key, Object[] params, Locale locale);
	
	// Email-Message
	String getEmailMessage(ScopeEmailMessage scope, String key, Locale locale);
	
	String getEmailMessage(ScopeEmailMessage scope, String key, Object[] params, Locale locale);
	
	// Specific
	String getSpecificMessage(I18nKeyValueUniverse universe, ScopeWebMessage scope, String key, Locale locale);
	
	String getSpecificMessage(I18nKeyValueUniverse universe, ScopeWebMessage scope, String key, Object[] params, Locale locale);
	
	// ReferenceData
	String getReferenceData(ScopeReferenceDataMessage scope, String key, Locale locale);
	
	String getReferenceData(ScopeReferenceDataMessage scope, String key, Object[] params, Locale locale);
	
	/**
	 * Load all worgind key/value by webappUnivers and univers/group
	 */
	Map<String, String> loadWording(EngineSettingWebAppContext context, String group, Locale locale);
	
	/**
	 * Load all worgind key/value by webappUnivers
	 */
	Map<String, String> loadWording(EngineSettingWebAppContext context, Locale locale);
	
	String getMessage(String code, String defaultMessage, Locale locale);
	
	String getMessage(String code, Object args[], String defaultMessage, Locale locale);

	String getMessage(String code, Locale locale) throws NoSuchMessageException;
	
	String getMessage(String code, Object args[], Locale locale) throws NoSuchMessageException;

	String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException;
	
	void clearCache();
}
