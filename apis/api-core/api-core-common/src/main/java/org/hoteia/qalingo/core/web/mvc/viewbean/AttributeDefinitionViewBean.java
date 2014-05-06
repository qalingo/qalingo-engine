/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class AttributeDefinitionViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -876473249270937070L;

    private String name;
    private String description;
    private String code;
    
    private String attributeType;
    private String objectType;
    
    private boolean localizable;
    private boolean global;
    private boolean multiValue;
    private boolean withPlanner;
    
    private Integer ordering;
    
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
    
    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public boolean isLocalizable() {
        return localizable;
    }

    public void setLocalizable(boolean localizable) {
        this.localizable = localizable;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public boolean isMultiValue() {
        return multiValue;
    }

    public void setMultiValue(boolean multiValue) {
        this.multiValue = multiValue;
    }

    public boolean isWithPlanner() {
        return withPlanner;
    }

    public void setWithPlanner(boolean withPlanner) {
        this.withPlanner = withPlanner;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributeType == null) ? 0 : attributeType.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (global ? 1231 : 1237);
        result = prime * result + (localizable ? 1231 : 1237);
        result = prime * result + (multiValue ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
        result = prime * result + ((ordering == null) ? 0 : ordering.hashCode());
        result = prime * result + (withPlanner ? 1231 : 1237);
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
        AttributeDefinitionViewBean other = (AttributeDefinitionViewBean) obj;
        if (attributeType == null) {
            if (other.attributeType != null)
                return false;
        } else if (!attributeType.equals(other.attributeType))
            return false;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (global != other.global)
            return false;
        if (localizable != other.localizable)
            return false;
        if (multiValue != other.multiValue)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (objectType == null) {
            if (other.objectType != null)
                return false;
        } else if (!objectType.equals(other.objectType))
            return false;
        if (ordering == null) {
            if (other.ordering != null)
                return false;
        } else if (!ordering.equals(other.ordering))
            return false;
        if (withPlanner != other.withPlanner)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AttributeDefinitionViewBean [name=" + name + ", description=" + description + ", code=" + code + ", attributeType=" + attributeType + ", objectType=" + objectType + ", localizable="
                + localizable + ", global=" + global + ", multiValue=" + multiValue + ", withPlanner=" + withPlanner + ", ordering=" + ordering + "]";
    }

}