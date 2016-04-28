/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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
public class ProductBrandForm {
	
    protected String id;
    protected String code;
	protected String name;
	protected String description;
    
    protected boolean enabled;
    protected boolean enabledB2B;
    protected boolean enabledB2C;
    
	protected Map<String, String> globalAttributes = new HashMap<String, String>();
	protected Map<String, String> marketAreaAttributes = new HashMap<String, String>();
	
    public String getId() {
		if(id == null){
			return "";
		}
		return id;
	}
    
    public void setId(String id) {
		this.id = id;
	}
    
    @NotEmpty(message = "bo.product_brand.error_form_code_is_empty")
    public String getCode() {
        if(code == null){
            return "";
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
	@NotEmpty(message = "bo.product_brand.error_form_name_is_empty")
	public String getName() {
		if(name == null){
			return "";
		}
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		if(description == null){
			return "";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
	public boolean isEnabledB2B() {
        return enabledB2B;
    }

    public void setEnabledB2B(boolean enabledB2B) {
        this.enabledB2B = enabledB2B;
    }

    public boolean isEnabledB2C() {
        return enabledB2C;
    }

    public void setEnabledB2C(boolean enabledB2C) {
        this.enabledB2C = enabledB2C;
    }

    public Map<String, String> getGlobalAttributes() {
		return globalAttributes;
	}
	
	public void setGlobalAttributes(Map<String, String> globalAttributes) {
		this.globalAttributes = globalAttributes;
	}
	
	public Map<String, String> getMarketAreaAttributes() {
		return marketAreaAttributes;
	}
	
	public void setMarketAreaAttributes(Map<String, String> marketAreaAttributes) {
		this.marketAreaAttributes = marketAreaAttributes;
	}
}