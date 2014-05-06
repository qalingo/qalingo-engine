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

public class UniqueConstraintCodeCategoryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4847767103757117163L;

	public UniqueConstraintCodeCategoryException() {
	}
	
    public UniqueConstraintCodeCategoryException(String message) {
    	super(message);
    }
    
    public UniqueConstraintCodeCategoryException(String message, Throwable cause) {
    	super(message, cause);
    }

    public UniqueConstraintCodeCategoryException(Throwable cause) {
        super(cause);
    }
}
