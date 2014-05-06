/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.deliverymethod;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DeliveryMethodPojo {

    private Long id;
    private int version;
    private String name;
    private String description;
    private String code;
    private String deliveryTime;
    private Set<DeliveryMethodCountryPojo> deliveryMethodCountries = new HashSet<DeliveryMethodCountryPojo>();
    private Set<DeliveryMethodPricePojo> prices = new HashSet<DeliveryMethodPricePojo>(); 
    private Date dateCreate;
    private Date dateUpdate;

    public DeliveryMethodPojo() {
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

    public Set<DeliveryMethodCountryPojo> getDeliveryMethodCountries() {
        return deliveryMethodCountries;
    }

    public void setDeliveryMethodCountries(Set<DeliveryMethodCountryPojo> deliveryMethodCountries) {
        this.deliveryMethodCountries = deliveryMethodCountries;
    }

    public Set<DeliveryMethodPricePojo> getPrices() {
        return prices;
    }

    public void setPrices(Set<DeliveryMethodPricePojo> prices) {
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