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

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class UserForm {

    protected String id;
    protected int version;
    protected boolean active;

    protected String code;
    protected String login;
    protected String password;
    protected String title;
    protected String firstname;
    protected String lastname;

    protected String userType;

    protected String address1;
    protected String address2;
    protected String postalCode;
    protected String city;
    protected String stateCode;
    protected String areaCode;
    protected String countryCode;

    protected String email;
    protected String phone;
    protected String mobile;

    protected Date dateCreate;
    protected Date dateUpdate;

    protected String backUrl;
    protected String userDetailsUrl;
    protected String userEditUrl;
    protected String formSubmitUrl;

	@NotEmpty(message = "error.form.user.id.is.empty")
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @NotEmpty(message = "error.form.user.code.is.empty")
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
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
	
	public String getPassword() {
        return password;
    }
	
	public void setPassword(String password) {
        this.password = password;
    }
	
	public String getTitle() {
        return title;
    }
	
	public void setTitle(String title) {
        this.title = title;
    }

	@NotEmpty(message = "error.form.user.firstname.is.empty")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getUserType() {
        return userType;
    }
	
	public void setUserType(String userType) {
        this.userType = userType;
    }

	public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    @NotEmpty(message = "error.form.user.email.is.empty")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
    
	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getUserDetailsUrl() {
		return userDetailsUrl;
	}

	public void setUserDetailsUrl(String userDetailsUrl) {
		this.userDetailsUrl = userDetailsUrl;
	}

	public String getUserEditUrl() {
		return userEditUrl;
	}

	public void setUserEditUrl(String userEditUrl) {
		this.userEditUrl = userEditUrl;
	}

	public String getFormSubmitUrl() {
		return formSubmitUrl;
	}

	public void setFormSubmitUrl(String formSubmitUrl) {
		this.formSubmitUrl = formSubmitUrl;
	}
	
}