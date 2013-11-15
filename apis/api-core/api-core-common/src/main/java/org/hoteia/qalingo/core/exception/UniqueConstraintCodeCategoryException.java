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
