/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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
     * Generated UID
     */
    private static final long serialVersionUID = -6292956614074854660L;
    
    private String paymentType;
	private String cardHolderName;
    private String cardNumber;
    private String cardExpMonth;
    private String cardExpYear;
    private String cardCVV;
    private boolean wantSavedPaymentInformations = false;
    
    public String getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
	public String getCardHolderName() {
        return cardHolderName;
    }
	
	public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

    public String getCardExpMonth() {
        return cardExpMonth;
    }

    public void setCardExpMonth(String cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
    }

    public String getCardExpYear() {
        return cardExpYear;
    }

    public void setCardExpYear(String cardExpYear) {
        this.cardExpYear = cardExpYear;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public boolean isWantSavedPaymentInformations() {
        return wantSavedPaymentInformations;
    }

    public void setWantSavedPaymentInformations(boolean wantSavedPaymentInformations) {
        this.wantSavedPaymentInformations = wantSavedPaymentInformations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardCVV == null) ? 0 : cardCVV.hashCode());
        result = prime * result + ((cardExpMonth == null) ? 0 : cardExpMonth.hashCode());
        result = prime * result + ((cardExpYear == null) ? 0 : cardExpYear.hashCode());
        result = prime * result + ((cardHolderName == null) ? 0 : cardHolderName.hashCode());
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
        result = prime * result + (wantSavedPaymentInformations ? 1231 : 1237);
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
        if (cardCVV == null) {
            if (other.cardCVV != null)
                return false;
        } else if (!cardCVV.equals(other.cardCVV))
            return false;
        if (cardExpMonth == null) {
            if (other.cardExpMonth != null)
                return false;
        } else if (!cardExpMonth.equals(other.cardExpMonth))
            return false;
        if (cardExpYear == null) {
            if (other.cardExpYear != null)
                return false;
        } else if (!cardExpYear.equals(other.cardExpYear))
            return false;
        if (cardHolderName == null) {
            if (other.cardHolderName != null)
                return false;
        } else if (!cardHolderName.equals(other.cardHolderName))
            return false;
        if (cardNumber == null) {
            if (other.cardNumber != null)
                return false;
        } else if (!cardNumber.equals(other.cardNumber))
            return false;
        if (paymentType == null) {
            if (other.paymentType != null)
                return false;
        } else if (!paymentType.equals(other.paymentType))
            return false;
        if (wantSavedPaymentInformations != other.wantSavedPaymentInformations)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PaymentForm [paymentType=" + paymentType + ", cardHolderName=" + cardHolderName + ", cardNumber=" + cardNumber + ", cardExpMonth=" + cardExpMonth + ", cardExpYear=" + cardExpYear
                + ", cardCVV=" + cardCVV + ", wantSavedPaymentInformations=" + wantSavedPaymentInformations + "]";
    }
    
}