/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo;

import java.util.Date;

public class AttributeDefinitionPojo {
    
    private Long id;
    private int version;
    private String name;
    private String description;
    private String code;
    private int attributeType;
    private int objectType;
    private boolean localizable;
    private boolean global;
    private boolean multiValue;
    private boolean planned;
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public int getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(final int attributeType) {
        this.attributeType = attributeType;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(final int objectType) {
        this.objectType = objectType;
    }

    public boolean isLocalizable() {
        return localizable;
    }

    public void setLocalizable(final boolean localizable) {
        this.localizable = localizable;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(final boolean global) {
        this.global = global;
    }

    public boolean isMultiValue() {
        return multiValue;
    }

    public void setMultiValue(final boolean multiValue) {
        this.multiValue = multiValue;
    }

    public boolean isPlanned() {
        return planned;
    }

    public void setPlanned(final boolean planned) {
        this.planned = planned;
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
