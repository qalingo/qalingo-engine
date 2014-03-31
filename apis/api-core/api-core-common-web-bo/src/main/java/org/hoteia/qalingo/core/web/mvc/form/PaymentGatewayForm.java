/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.form;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * 
 * 
 */
public class PaymentGatewayForm {
	
    private String id;
    private int version;
    private String code;
    private String name;
    private String description;

    private Map<String, String> attributeMap = new HashMap<String, String>();
    private Map<String, String> optionMap = new HashMap<String, String>();
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    @NotEmpty(message = "bo.payment_gateway.error_form_code_empty")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotEmpty(message = "bo.payment_gateway.error_form_name_empty")
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
    
    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }
    
    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }
    
    public Map<String, String> getOptionMap() {
        return optionMap;
    }
    
    public void setOptionMap(Map<String, String> optionMap) {
        this.optionMap = optionMap;
    }

}