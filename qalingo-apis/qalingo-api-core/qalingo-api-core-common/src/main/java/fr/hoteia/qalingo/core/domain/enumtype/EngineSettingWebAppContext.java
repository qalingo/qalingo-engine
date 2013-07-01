/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain.enumtype;

import fr.hoteia.qalingo.core.i18n.enumtype.I18nBasename;

/**
 * 
 */
public enum EngineSettingWebAppContext {

	/**
	 * 
	 */
	BO_BUSINESS("BO_BUSINESS"),
	
	/**
	 * 
	 */
	BO_REPORTING("BO_REPORTING"),
	
	/**
	 * 
	 */
	BO_TECHNICAL("BO_TECHNICAL"),
	
	/**
	 * 
	 */
	FO_MARKETPLACE("FO_MARKETPLACE"),
	
	/**
	 * 
	 */
	FO_SHOWROOM("FO_SHOWROOM"),
	
	/**
	 * 
	 */
	FO_MCOMMERCE("FO_MCOMMERCE"),
	
	/**
	 * 
	 */
	FO_PREHOME("FO_PREHOME");
	
	private String propertyKey = "";

	/**
	 * Constructor.
	 * 
	 * @param propertyKey the property key.
	 */
	EngineSettingWebAppContext(final String propertyKey) {
		this.propertyKey = propertyKey;
	}

	/**
	 * Get the localization property key.
	 * 
	 * @return the localized property key
	 */
	public String getPropertyKey() {
		return this.propertyKey;
	}
	
	/**
	 * Get the i18n basename property value.
	 * 
	 * @return the i18n basename property value
	 */
	public static I18nBasename getI18nBasenameAssociated(EngineSettingWebAppContext context) {
		if(context.equals(EngineSettingWebAppContext.BO_BUSINESS)){
			return I18nBasename.BO_BUSINESS_BASENAME;
		} else if(context.equals(EngineSettingWebAppContext.BO_TECHNICAL)){
			return I18nBasename.BO_TECHNICAL_BASENAME;
		} else if(context.equals(EngineSettingWebAppContext.BO_REPORTING)){
			return I18nBasename.BO_REPORTING_BASENAME;
		} else if(context.equals(EngineSettingWebAppContext.FO_MARKETPLACE)){
			return I18nBasename.FO_MARKETPLACE_BASENAME;
		} else if(context.equals(EngineSettingWebAppContext.FO_SHOWROOM)){
			return I18nBasename.FO_SHOWROOM_BASENAME;
		} else if(context.equals(EngineSettingWebAppContext.FO_MCOMMERCE)){
			return I18nBasename.FO_MCOMMERCE_BASENAME;
		} else if(context.equals(EngineSettingWebAppContext.FO_PREHOME)){
			return I18nBasename.FO_PREHOME_BASENAME;
		}
		return null;
	}
	
	
}
