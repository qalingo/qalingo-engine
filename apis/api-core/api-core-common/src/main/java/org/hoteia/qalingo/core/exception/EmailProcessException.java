/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.exception;

public class EmailProcessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4847328603757117163L;

	public static final String EMAIl_TO_ADDRESS_IS_EMPTY				= "EMAIl_TO_ADDRESS_IS_EMPTY";
	public static final String EMAIl_FROM_ADDRESS_IS_EMPTY				= "EMAIl_FROM_ADDRESS_IS_EMPTY";
	public static final String EMAIl_REPLY_TO_ADDRESS_IS_EMPTY			= "EMAIl_REPLY_TO_ADDRESS_IS_EMPTY";

	private String code;
	
	public EmailProcessException(String code) {
		this.code = code;
	}
	
}
