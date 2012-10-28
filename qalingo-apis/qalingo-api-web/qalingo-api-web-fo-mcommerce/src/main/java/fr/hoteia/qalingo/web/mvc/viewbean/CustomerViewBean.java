/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
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

public class CustomerViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6264101125517957897L;
	
	private String firstnameLabel;
	private String firstnameValue;

	private String lastnameLabel;
	private String lastnameValue;

	private String emailLabel;
	private String emailValue;

	private String dateCreateLabel;
	private String dateCreateValue;

	private String dateUpdateLabel;
	private String dateUpdateValue;

	private Map<String, ValueBean> customerAttributes = new HashMap<String, ValueBean>();
	
	private String editLabel;
	private String editUrl;

	public String getFirstnameLabel() {
		return firstnameLabel;
	}
	
	public void setFirstnameLabel(String firstnameLabel) {
		this.firstnameLabel = firstnameLabel;
	}

	public String getFirstnameValue() {
		return firstnameValue;
	}

	public void setFirstnameValue(String firstnameValue) {
		this.firstnameValue = firstnameValue;
	}

	public String getLastnameLabel() {
		return lastnameLabel;
	}

	public void setLastnameLabel(String lastnameLabel) {
		this.lastnameLabel = lastnameLabel;
	}

	public String getLastnameValue() {
		return lastnameValue;
	}

	public void setLastnameValue(String lastnameValue) {
		this.lastnameValue = lastnameValue;
	}

	public String getEmailLabel() {
		return emailLabel;
	}

	public void setEmailLabel(String emailLabel) {
		this.emailLabel = emailLabel;
	}

	public String getEmailValue() {
		return emailValue;
	}

	public void setEmailValue(String emailValue) {
		this.emailValue = emailValue;
	}

	public String getDateCreateLabel() {
		return dateCreateLabel;
	}

	public void setDateCreateLabel(String dateCreateLabel) {
		this.dateCreateLabel = dateCreateLabel;
	}

	public String getDateCreateValue() {
		return dateCreateValue;
	}

	public void setDateCreateValue(String dateCreateValue) {
		this.dateCreateValue = dateCreateValue;
	}

	public String getDateUpdateLabel() {
		return dateUpdateLabel;
	}

	public void setDateUpdateLabel(String dateUpdateLabel) {
		this.dateUpdateLabel = dateUpdateLabel;
	}

	public String getDateUpdateValue() {
		return dateUpdateValue;
	}

	public void setDateUpdateValue(String dateUpdateValue) {
		this.dateUpdateValue = dateUpdateValue;
	}
	
	public Map<String, ValueBean> getCustomerAttributes() {
		return customerAttributes;
	}
	
	public void setCustomerAttributes(Map<String, ValueBean> customerAttributes) {
		this.customerAttributes = customerAttributes;
	}
	
	public String getEditLabel() {
		return editLabel;
	}
	
	public void setEditLabel(String editLabel) {
		this.editLabel = editLabel;
	}
	
	public String getEditUrl() {
		return editUrl;
	}
	
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
}
