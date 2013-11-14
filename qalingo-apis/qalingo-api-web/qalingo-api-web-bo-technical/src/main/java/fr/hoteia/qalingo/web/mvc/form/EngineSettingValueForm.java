/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class EngineSettingValueForm {
	
    private String id;
    private String value;
    
	@NotEmpty(message = "error.form.engine.setting.id.is.empty")
    public String getId() {
		return id;
	}
    
    public void setId(String id) {
		this.id = id;
	}
    
	@NotEmpty(message = "error.form.engine.setting.value.is.empty")
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}