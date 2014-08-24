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
	
    private String id;
    private int version;
    private String code;
	private String login;
    private String password;
    private String title;
	private String firstname;
	private String lastname;
	private String email;
	
    private String userType;
    
    private String address1;
    private String address2;
    private String postalCode;
    private String city;
    private String stateCode;
    private String areaCode;
    private String countryCode;
    
	private boolean active;
	private Date dateCreate;
	private Date dateUpdate;
	
	private String backUrl;
	private String userDetailsUrl;
	private String userEditUrl;
	private String formSubmitUrl;
    
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

	@NotEmpty(message = "error.form.user.email.is.empty")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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