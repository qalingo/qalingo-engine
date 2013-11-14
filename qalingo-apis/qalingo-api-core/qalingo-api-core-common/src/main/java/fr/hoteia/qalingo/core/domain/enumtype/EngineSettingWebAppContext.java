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
	
}