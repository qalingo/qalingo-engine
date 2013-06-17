package fr.hoteia.qalingo.core.web.factory;

import java.util.Locale;

import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.mvc.factory.AbstractViewBeanFactory;

public abstract class AbstractFrontofficeViewBeanFactory extends AbstractViewBeanFactory {

	protected String getSpecificMessage(ScopeWebMessage scope, String key, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, scope, key, locale);
	}
	
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, scope, key, params, locale);
	}
	
}