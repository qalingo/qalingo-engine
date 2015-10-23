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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBO_COMPANY_PAYMENT")
public class CompanyPayment extends AbstractExtendEntity<CompanyPayment, CompanyPaymentAttribute> {

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

    @Column(name="MARKET_AREA_ID")
    private Long marketAreaId;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CompanyPaymentAttribute.class)
    @JoinColumn(name = "COMPANY_PAYMENT_ID")
    private Set<CompanyPaymentAttribute> attributes = new HashSet<CompanyPaymentAttribute>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public CompanyPayment() {
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
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
    
    public Long getMarketAreaId() {
        return marketAreaId;
    }
    
    public void setMarketAreaId(Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

    public Set<CompanyPaymentAttribute> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Set<CompanyPaymentAttribute> attributes) {
        this.attributes = attributes;
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
        result = prime * result + ((marketAreaId == null) ? 0 : marketAreaId.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompanyPayment other = (CompanyPayment) obj;
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
        if (marketAreaId == null) {
            if (other.marketAreaId != null)
                return false;
        } else if (!marketAreaId.equals(other.marketAreaId))
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
                + ", cardExpYear=" + cardExpYear + ", cardCVV=" + cardCVV + ", marketAreaId=" + marketAreaId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
    
}