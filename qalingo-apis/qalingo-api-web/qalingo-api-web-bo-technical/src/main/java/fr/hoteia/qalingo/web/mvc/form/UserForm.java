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

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class UserForm {
	
    private String id;
	private String login;
	private String firstname;
	private String lastname;
	private String email;
	private boolean active;
	private Date dateCreate;
	private Date dateUpdate;
    
	@NotEmpty(message = "error.form.user.id.is.empty")
    public String getId() {
		return id;
	}
    
    public void setId(String id) {
		this.id = id;
	}
    
	@NotEmpty(message = "error.form.user.lastname.is.empty")
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@NotEmpty(message = "error.form.user.login.is.empty")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotEmpty(message = "error.form.user.firstname.is.empty")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@NotEmpty(message = "error.form.user.email.is.empty")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	

}