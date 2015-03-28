/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.email.bean;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractCustomerEmailBean extends AbstractEmailBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -5893372387173121144L;
    
    private String title;
	private String firstname;
	private String lastname;
    private String email;
	
	public String getTitle() {
        if(StringUtils.isNotEmpty(title)){
            return title;
        }
        return "";
    }
	
	public void setTitle(String title) {
	    this.title = title;
    }

	public String getFirstname() {
        if(StringUtils.isNotEmpty(firstname)){
            return firstname;
        }
        return "";
    }

	public void setFirstname(String firstname) {
    	this.firstname = firstname;
    }

	public String getLastname() {
        if(StringUtils.isNotEmpty(lastname)){
            return lastname;
        }
        return "";
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
	
	public String getFullLastnameFirstnameLabel(){
	    StringBuffer label = new StringBuffer();
	    if(StringUtils.isNotEmpty(title)){
	        label.append(title);
            label.append(" ");
	    }
        if(StringUtils.isNotEmpty(lastname)){
            label.append(lastname);
            label.append(" ");
        }
        if(StringUtils.isNotEmpty(firstname)){
            label.append(firstname);
            label.append(" ");
        }
        return label.toString();
	}

   public String getFullFirstnameLastnameLabel(){
        StringBuffer label = new StringBuffer();
        if(StringUtils.isNotEmpty(title)){
            label.append(title);
            label.append(" ");
        }
        if(StringUtils.isNotEmpty(firstname)){
            label.append(firstname);
            label.append(" ");
        }
        if(StringUtils.isNotEmpty(lastname)){
            label.append(lastname);
            label.append(" ");
        }
        return label.toString();
    }
	   
}