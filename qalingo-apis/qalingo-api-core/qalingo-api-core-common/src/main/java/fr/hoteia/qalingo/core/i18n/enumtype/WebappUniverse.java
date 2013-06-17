/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.i18n.enumtype;

/**
 * 
 */
public enum WebappUniverse {

	/**
	 * 
	 */
	BACKOFFICE_BUSINESS("BACKOFFICE_BUSINESS"),
	
	/**
	 * 
	 */
	BACKOFFICE_TECHNICAL("BACKOFFICE_TECHNICAL"),
	
	/**
	 * 
	 */
	BACKOFFICE_REPORTING("BACKOFFICE_REPORTING"),
	
	/**
	 * 
	 */
	FRONTOFFICE_MCOMMERCE("FRONTOFFICE_MCOMMERCE"),
	
	/**
	 * 
	 */
	FRONTOFFICE_PREHOME("FRONTOFFICE_PREHOME");
	
	private String propertyKey = "";

	/**
	 * Constructor.
	 * 
	 * @param propertyKey the property key.
	 */
	WebappUniverse(final String propertyKey) {
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
	public static I18nBasename getI18nBasenameAssociated(WebappUniverse webappUniverse) {
		if(webappUniverse.equals(WebappUniverse.BACKOFFICE_BUSINESS)){
			return I18nBasename.BO_BUSINESS_BASENAME;
		} else if(webappUniverse.equals(WebappUniverse.BACKOFFICE_TECHNICAL)){
			return I18nBasename.BO_TECHNICAL_BASENAME;
		} else if(webappUniverse.equals(WebappUniverse.BACKOFFICE_REPORTING)){
			return I18nBasename.BO_REPORTING_BASENAME;
		} else if(webappUniverse.equals(WebappUniverse.FRONTOFFICE_MCOMMERCE)){
			return I18nBasename.FO_MCOMMERCE_BASENAME;
		} else if(webappUniverse.equals(WebappUniverse.FRONTOFFICE_PREHOME)){
			return I18nBasename.FO_PREHOME_BASENAME;
		}
		return null;
	}
	
}
