/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;

/**
 * 
 * 
 */
public class PaymentForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = -6292956614074854660L;
    
	private String cardHolder;
    private String cardNumber;
    private String cardCrypto;
    private String cardMonth;
    private String cardYear;
    
	public String getCardHolder() {
		return cardHolder;
	}
	
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardCrypto() {
		return cardCrypto;
	}

	public void setCardCrypto(String cardCrypto) {
		this.cardCrypto = cardCrypto;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public String getCardYear() {
		return cardYear;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((cardCrypto == null) ? 0 : cardCrypto.hashCode());
	    result = prime * result + ((cardHolder == null) ? 0 : cardHolder.hashCode());
	    result = prime * result + ((cardMonth == null) ? 0 : cardMonth.hashCode());
	    result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
	    result = prime * result + ((cardYear == null) ? 0 : cardYear.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    PaymentForm other = (PaymentForm) obj;
	    if (cardCrypto == null) {
		    if (other.cardCrypto != null)
			    return false;
	    } else if (!cardCrypto.equals(other.cardCrypto))
		    return false;
	    if (cardHolder == null) {
		    if (other.cardHolder != null)
			    return false;
	    } else if (!cardHolder.equals(other.cardHolder))
		    return false;
	    if (cardMonth == null) {
		    if (other.cardMonth != null)
			    return false;
	    } else if (!cardMonth.equals(other.cardMonth))
		    return false;
	    if (cardNumber == null) {
		    if (other.cardNumber != null)
			    return false;
	    } else if (!cardNumber.equals(other.cardNumber))
		    return false;
	    if (cardYear == null) {
		    if (other.cardYear != null)
			    return false;
	    } else if (!cardYear.equals(other.cardYear))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "PaymentForm [cardHolder=" + cardHolder + ", cardNumber=" + cardNumber + ", cardCrypto=" + cardCrypto + ", cardMonth=" + cardMonth + ", cardYear=" + cardYear + "]";
    }
	
}