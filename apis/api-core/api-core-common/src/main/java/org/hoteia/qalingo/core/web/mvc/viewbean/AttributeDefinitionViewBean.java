package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class AttributeDefinitionViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -7857159376069186573L;

    private String name;
    private String description;
    private String code;
    
    private String attributeType;
    private String objectType;
    
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

}