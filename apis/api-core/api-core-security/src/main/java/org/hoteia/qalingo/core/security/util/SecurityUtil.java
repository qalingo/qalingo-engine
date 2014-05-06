/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.security.util;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.Customer;

public interface SecurityUtil {

	static final String ALPHA_CAPS	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String ALPHA		= "abcdefghijklmnopqrstuvwxyz";
	static final String NUM			= "0123456789";
	static final String SPL_CHARS	= "!@#$%*";
    
	String generatePermalink();
	
	void authenticationCustomer(HttpServletRequest request, Customer customer);

	String encodePassword(String clearPassword);

	String generateAndEncodePassword();
	
	String generatePassword();
	
}
