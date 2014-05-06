/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class CreateAccountForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 8411240584011044748L;
    
	private String addressName;
    private String title;
    private String lastname;
    private String firstname;
    
    private String address1;
    private String address2;
    private String addressAdditionalInformation;
    private String postalCode;
    private String city;
    private String stateCode;
    private String areaCode;
    private String countryCode;
    
    private String email;
    private String emailConfirm;

    private String password;
    private String passwordConfirm;

    private String phone;
    private String fax;
    private String mobile;

    private boolean optin;

    public String getAddressName() {
		return addressName;
	}
    
    public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
    
	@NotEmpty(message = "fo.customer.error_form_create_account_title_empty")
    public String getTitle() {
		return title;
	}
    
    public void setTitle(String title) {
		this.title = title;
	}
    
	@NotEmpty(message = "fo.customer.error_form_create_account_lastname_empty")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@NotEmpty(message = "fo.customer.error_form_create_account_firstname_empty")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@NotEmpty(message = "fo.customer.error_form_address_address1_empty")
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

	public String getAddressAdditionalInformation() {
		return addressAdditionalInformation;
	}

	public void setAddressAdditionalInformation(String addressAdditionalInformation) {
		this.addressAdditionalInformation = addressAdditionalInformation;
	}

	@NotEmpty(message = "fo.customer.error_form_address_zip_or_postal_code_empty")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@NotEmpty(message = "fo.customer.error_form_address_city_empty")
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

	@NotEmpty(message = "fo.customer.error_form_address_country_code_empty")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getAreaCode() {
	    return areaCode;
    }
	
	public void setAreaCode(String areaCode) {
	    this.areaCode = areaCode;
    }

	@NotEmpty(message = "fo.customer.error_form_create_account_email_empty")
	@Email(message = "fo.customer.error_form_create_account_email_is_not_valid")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailConfirm() {
		return emailConfirm;
	}
	
	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isOptin() {
		return optin;
	}

	public void setOptin(boolean optin) {
		this.optin = optin;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
	    result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
	    result = prime * result + ((addressAdditionalInformation == null) ? 0 : addressAdditionalInformation.hashCode());
	    result = prime * result + ((addressName == null) ? 0 : addressName.hashCode());
	    result = prime * result + ((areaCode == null) ? 0 : areaCode.hashCode());
	    result = prime * result + ((city == null) ? 0 : city.hashCode());
	    result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
	    result = prime * result + ((email == null) ? 0 : email.hashCode());
	    result = prime * result + ((emailConfirm == null) ? 0 : emailConfirm.hashCode());
	    result = prime * result + ((fax == null) ? 0 : fax.hashCode());
	    result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
	    result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
	    result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
	    result = prime * result + (optin ? 1231 : 1237);
	    result = prime * result + ((password == null) ? 0 : password.hashCode());
	    result = prime * result + ((passwordConfirm == null) ? 0 : passwordConfirm.hashCode());
	    result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	    result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
	    result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
	    result = prime * result + ((title == null) ? 0 : title.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    CreateAccountForm other = (CreateAccountForm) obj;
	    if (address1 == null) {
		    if (other.address1 != null)
			    return false;
	    } else if (!address1.equals(other.address1))
		    return false;
	    if (address2 == null) {
		    if (other.address2 != null)
			    return false;
	    } else if (!address2.equals(other.address2))
		    return false;
	    if (addressAdditionalInformation == null) {
		    if (other.addressAdditionalInformation != null)
			    return false;
	    } else if (!addressAdditionalInformation.equals(other.addressAdditionalInformation))
		    return false;
	    if (addressName == null) {
		    if (other.addressName != null)
			    return false;
	    } else if (!addressName.equals(other.addressName))
		    return false;
	    if (areaCode == null) {
		    if (other.areaCode != null)
			    return false;
	    } else if (!areaCode.equals(other.areaCode))
		    return false;
	    if (city == null) {
		    if (other.city != null)
			    return false;
	    } else if (!city.equals(other.city))
		    return false;
	    if (countryCode == null) {
		    if (other.countryCode != null)
			    return false;
	    } else if (!countryCode.equals(other.countryCode))
		    return false;
	    if (email == null) {
		    if (other.email != null)
			    return false;
	    } else if (!email.equals(other.email))
		    return false;
	    if (emailConfirm == null) {
		    if (other.emailConfirm != null)
			    return false;
	    } else if (!emailConfirm.equals(other.emailConfirm))
		    return false;
	    if (fax == null) {
		    if (other.fax != null)
			    return false;
	    } else if (!fax.equals(other.fax))
		    return false;
	    if (firstname == null) {
		    if (other.firstname != null)
			    return false;
	    } else if (!firstname.equals(other.firstname))
		    return false;
	    if (lastname == null) {
		    if (other.lastname != null)
			    return false;
	    } else if (!lastname.equals(other.lastname))
		    return false;
	    if (mobile == null) {
		    if (other.mobile != null)
			    return false;
	    } else if (!mobile.equals(other.mobile))
		    return false;
	    if (optin != other.optin)
		    return false;
	    if (password == null) {
		    if (other.password != null)
			    return false;
	    } else if (!password.equals(other.password))
		    return false;
	    if (passwordConfirm == null) {
		    if (other.passwordConfirm != null)
			    return false;
	    } else if (!passwordConfirm.equals(other.passwordConfirm))
		    return false;
	    if (phone == null) {
		    if (other.phone != null)
			    return false;
	    } else if (!phone.equals(other.phone))
		    return false;
	    if (postalCode == null) {
		    if (other.postalCode != null)
			    return false;
	    } else if (!postalCode.equals(other.postalCode))
		    return false;
	    if (stateCode == null) {
		    if (other.stateCode != null)
			    return false;
	    } else if (!stateCode.equals(other.stateCode))
		    return false;
	    if (title == null) {
		    if (other.title != null)
			    return false;
	    } else if (!title.equals(other.title))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "CreateAccountForm [addressName=" + addressName + ", title=" + title + ", lastname=" + lastname + ", firstname=" + firstname + ", address1=" + address1 + ", address2=" + address2
	            + ", addressAdditionalInformation=" + addressAdditionalInformation + ", postalCode=" + postalCode + ", city=" + city + ", stateCode=" + stateCode + ", areaCode=" + areaCode
	            + ", countryCode=" + countryCode + ", email=" + email + ", emailConfirm=" + emailConfirm + ", password=" + password + ", passwordConfirm=" + passwordConfirm + ", phone=" + phone
	            + ", fax=" + fax + ", mobile=" + mobile + ", optin=" + optin + "]";
    }

}