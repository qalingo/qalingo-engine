package fr.hoteia.qalingo.core.service.openid;

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