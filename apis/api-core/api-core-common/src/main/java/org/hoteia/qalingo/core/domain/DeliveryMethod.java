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

import java.math.BigDecimal;
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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "TECO_DELIVERY_METHOD", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
public class DeliveryMethod extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6023814328491726235L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DELIVERY_TIME")
    private String deliveryTime;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_METHOD_ID")
    private Set<DeliveryMethodCountry> countries = new HashSet<DeliveryMethodCountry>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="DELIVERY_METHOD_ID")
    private Set<DeliveryMethodPrice> prices = new HashSet<DeliveryMethodPrice>(); 
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public DeliveryMethod() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getDeliveryTime() {
        return deliveryTime;
    }
    
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Set<DeliveryMethodCountry> getCountries() {
        return countries;
    }
    
    public void setCountries(Set<DeliveryMethodCountry> countries) {
        this.countries = countries;
    }

    public Set<DeliveryMethodPrice> getPrices() {
        return prices;
    }

    public DeliveryMethodPrice getDeliveryMethodPrice(final Long marketAreaId, final Long retailerId){
        if(prices != null){
            for (DeliveryMethodPrice deliveryMethodPrice : prices) {
                if(deliveryMethodPrice.getMarketAreaId().equals(marketAreaId) 
                        && deliveryMethodPrice.getRetailerId().equals(retailerId)) {
                    return deliveryMethodPrice;
                }
            }    
        }
        return null;
    }
    
    public BigDecimal getPrice(final Long marketAreaId, final Long retailerId){
        DeliveryMethodPrice deliveryMethodPrice = getDeliveryMethodPrice(marketAreaId, retailerId);
        if(deliveryMethodPrice != null){
            return deliveryMethodPrice.getPrice(); 
        }
        return null;
    }
    
    public String getPriceWithStandardCurrencySign(final Long marketAreaId, final Long retailerId){
        DeliveryMethodPrice deliveryMethodPrice = getDeliveryMethodPrice(marketAreaId, retailerId);
        if(deliveryMethodPrice != null){
            return deliveryMethodPrice.getPriceWithStandardCurrencySign(); 
        }
        return null;
    }
    
    public void setPrices(Set<DeliveryMethodPrice> prices) {
        this.prices = prices;
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