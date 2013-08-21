/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.email.bean;

import java.io.Serializable;

public class CustomerForgottenPasswordEmailBean extends AbstractEmailBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -5410830735779028978L;
    
    private String token;

    public String getToken() {
	    return token;
    }
    
    public void setToken(String token) {
	    this.token = token;
    }
    
}