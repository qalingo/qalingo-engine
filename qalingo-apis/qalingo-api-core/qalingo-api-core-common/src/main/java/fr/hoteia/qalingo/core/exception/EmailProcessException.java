package fr.hoteia.qalingo.core.exception;

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
