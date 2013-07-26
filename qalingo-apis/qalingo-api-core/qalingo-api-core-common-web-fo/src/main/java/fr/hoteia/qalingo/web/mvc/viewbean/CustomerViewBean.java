/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CustomerViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6264101125517957897L;
	
	private String firstname;
	private String lastname;
	private String email;
	private String dateCreate;
	private String dateUpdate;

	private Map<String, ValueBean> customerAttributes = new HashMap<String, ValueBean>();

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

	public String getEmail() {
    	return email;
    }

	public void setEmail(String email) {
    	this.email = email;
    }

	public String getDateCreate() {
    	return dateCreate;
    }

	public void setDateCreate(String dateCreate) {
    	this.dateCreate = dateCreate;
    }

	public String getDateUpdate() {
    	return dateUpdate;
    }

	public void setDateUpdate(String dateUpdate) {
    	this.dateUpdate = dateUpdate;
    }

	public Map<String, ValueBean> getCustomerAttributes() {
    	return customerAttributes;
    }

	public void setCustomerAttributes(Map<String, ValueBean> customerAttributes) {
    	this.customerAttributes = customerAttributes;
    }
	
}