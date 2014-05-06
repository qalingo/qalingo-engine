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

public class AttributeValueViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 3569191522671970806L;

    private AttributeDefinitionViewBean attributeDefinition;
    private String value;
    private String localizationCode;
    
    public AttributeDefinitionViewBean getAttributeDefinition() {
        return attributeDefinition;
    }
    
    public void setAttributeDefinition(AttributeDefinitionViewBean attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
    }

    public String getCode() {
        if(attributeDefinition != null){
            return attributeDefinition.getCode();
        }
        return null;
    }
    
    public boolean isLocalizable() {
        if(attributeDefinition != null){
            return attributeDefinition.isLocalizable();
        }
        return false;
    }
    
    public boolean isGlobal() {
        if(attributeDefinition != null){
            return attributeDefinition.isGlobal();
        }
        return false;
    }
    
    public boolean isMultiValue() {
        if(attributeDefinition != null){
            return attributeDefinition.isMultiValue();
        }
        return false;
    }
    
    public boolean isWithPlanner() {
        if(attributeDefinition != null){
            return attributeDefinition.isWithPlanner();
        }
        return false;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLocalizationCode() {
        return localizationCode;
    }

    public void setLocalizationCode(String localizationCode) {
        this.localizationCode = localizationCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributeDefinition == null) ? 0 : attributeDefinition.hashCode());
        result = prime * result + ((localizationCode == null) ? 0 : localizationCode.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        AttributeValueViewBean other = (AttributeValueViewBean) obj;
        if (attributeDefinition == null) {
            if (other.attributeDefinition != null)
                return false;
        } else if (!attributeDefinition.equals(other.attributeDefinition))
            return false;
        if (localizationCode == null) {
            if (other.localizationCode != null)
                return false;
        } else if (!localizationCode.equals(other.localizationCode))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AttributeValueViewBean [attributeDefinition=" + attributeDefinition + ", value=" + value + ", localizationCode=" + localizationCode + "]";
    }
    
}