/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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

}