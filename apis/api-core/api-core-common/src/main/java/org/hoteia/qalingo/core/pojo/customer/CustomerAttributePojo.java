/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.customer;

import org.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;

import java.util.Date;

public class CustomerAttributePojo {

    private Long id;
    private int version;
    private AttributeDefinitionPojo attributeDefinition;
    private String stringValue;
    private Integer integerValue;
    private Double doubleValue;
    private Float floatValue;
    private byte[] blobValue;
    private Boolean booleanValue;
    private String localizationCode;
    private Long marketAreaId;
    private Date startDate;
    private Date endDate;
    private Date dateCreate;
    private Date dateUpdate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public AttributeDefinitionPojo getAttributeDefinition() {
        return attributeDefinition;
    }

    public void setAttributeDefinition(final AttributeDefinitionPojo attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(final String stringValue) {
        this.stringValue = stringValue;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(final Integer integerValue) {
        this.integerValue = integerValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(final Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(final Float floatValue) {
        this.floatValue = floatValue;
    }

    public byte[] getBlobValue() {
        return blobValue;
    }

    public void setBlobValue(final byte[] blobValue) {
        this.blobValue = blobValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(final Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getLocalizationCode() {
        return localizationCode;
    }

    public void setLocalizationCode(final String localizationCode) {
        this.localizationCode = localizationCode;
    }

    public Long getMarketAreaId() {
        return marketAreaId;
    }

    public void setMarketAreaId(final Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(final Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(final Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

}
