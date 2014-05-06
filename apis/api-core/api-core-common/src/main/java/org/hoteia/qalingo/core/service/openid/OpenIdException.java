/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.openid;

/**
 * 
 */
public class OpenIdException extends RuntimeException {

    private static final long serialVersionUID = -2542042427711205435L;

	public OpenIdException() {
        super();
    }

    public OpenIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenIdException(String message) {
        super(message);
    }

    public OpenIdException(Throwable cause) {
        super(cause);
    }

}