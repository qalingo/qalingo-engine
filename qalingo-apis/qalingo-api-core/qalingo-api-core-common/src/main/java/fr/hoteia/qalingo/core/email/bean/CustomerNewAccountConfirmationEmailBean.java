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

public class CustomerNewAccountConfirmationEmailBean extends AbstractEmailBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -1890252657487021821L;
    
    private String title;
	private String firstname;
	private String lastname;

	public String getTitle() {
	    return title;
    }
	
	public void setTitle(String title) {
	    this.title = title;
    }

	public String getFirstname() {
    	return firstname;
    }

	public void setFirstname(String firstname) {
    	this.firstname = firstname;
    }

	public String getLastname() {
    	return lastname;
    }

	public void setLastname(String lastname) {
    	this.lastname = lastname;
    }
	
}