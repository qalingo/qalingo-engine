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
public class PaymentGatewayForm {
	
    private String id;

    @NotEmpty(message = "error.form.engine.setting.id.is.empty")
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}