/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TECO_CUSTOMER_PAYMENT_INFORMATION")
public class CustomerPaymentInformation extends AbstractAddress {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -3877798990315671631L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "HOLDERNAME")
    private String cardHolderName;

    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "CARD_EXP_MONTH")
    private String cardExpMonth;

    @Column(name = "CARD_EXP_YEAR")
    private String cardExpYear;

    @Column(name = "CARD_CVV")
    private String cardCVV;

    @Column(name="CUSTOMER_MARKET_AREA_ID")
    private Long customerMarketAreaId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public CustomerPaymentInformation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    
    public Long getCustomerMarketAreaId() {
        return customerMarketAreaId;
    }

    public void setCustomerMarketAreaId(Long customerMarketAreaId) {
        this.customerMarketAreaId = customerMarketAreaId;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((customerMarketAreaId == null) ? 0 : customerMarketAreaId.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        CustomerPaymentInformation other = (CustomerPaymentInformation) obj;
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
        if (customerMarketAreaId == null) {
            if (other.customerMarketAreaId != null)
                return false;
        } else if (!customerMarketAreaId.equals(other.customerMarketAreaId))
            return false;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomerPaymentInformation [id=" + id + ", paymentType=" + paymentType + ", cardHolderName=" + cardHolderName + ", cardNumber=" + cardNumber + ", cardExpMonth=" + cardExpMonth
                + ", cardExpYear=" + cardExpYear + ", cardCVV=" + cardCVV + ", customerMarketAreaId=" + customerMarketAreaId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
    
}