package fr.hoteia.qalingo.core.exception;

public class CustomerAttributeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4847458603757117163L;

	public CustomerAttributeException() {
	}
	
    public CustomerAttributeException(String message) {
    	super(message);
    }
    
    public CustomerAttributeException(String message, Throwable cause) {
    	super(message, cause);
    }

    public CustomerAttributeException(Throwable cause) {
        super(cause);
    }
}
