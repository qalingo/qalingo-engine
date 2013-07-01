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
public enum I18nBasename {

	/**
	 * 
	 */
	COMMON_BASENAME("qalingo-common-wording"),
	
	/**
	 * 
	 */
	EMAIL_BASENAME("qalingo-email-wording"),
	
	/**
	 * 
	 */
	BO_BUSINESS_BASENAME("qalingo-bo-wording"),
	
	/**
	 * 
	 */
	BO_TECHNICAL_BASENAME("qalingo-bo-wording"),
	
	/**
	 * 
	 */
	BO_REPORTING_BASENAME("qalingo-bo-wording"),
	
	/**
	 * 
	 */
	FO_MARKETPLACE_BASENAME("qalingo-fo-wording"),
	
	/**
	 * 
	 */
	FO_SHOWROOM_BASENAME("qalingo-fo-wording"),
	
	/**
	 * 
	 */
	FO_MCOMMERCE_BASENAME("qalingo-fo-wording"),
	
	/**
	 * 
	 */
	FO_PREHOME_BASENAME("qalingo-fo-wording");
	
	private String propertyKey = "";

	/**
	 * Constructor.
	 * 
	 * @param propertyKey the property key.
	 */
	I18nBasename(final String propertyKey) {
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
