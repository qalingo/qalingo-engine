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
public class ContactForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = -3169760453881902024L;
    
	private String lastname;
    private String firstname;
    private String country;
    private String email;
    private String phone;
    private String fax;
    private String mobile;
    private String website;
    private String subject;
    private String message;
    
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@NotEmpty(message = "fo.contact.error_form_email_empty")
	@Email(message = "fo.contact.error_form_email_is_not_valid")
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@NotEmpty(message = "fo.contact.error_form_subject_empty")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

	@NotEmpty(message = "fo.contact.error_form_message_empty")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((country == null) ? 0 : country.hashCode());
	    result = prime * result + ((email == null) ? 0 : email.hashCode());
	    result = prime * result + ((fax == null) ? 0 : fax.hashCode());
	    result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
	    result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
	    result = prime * result + ((message == null) ? 0 : message.hashCode());
	    result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
	    result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	    result = prime * result + ((subject == null) ? 0 : subject.hashCode());
	    result = prime * result + ((website == null) ? 0 : website.hashCode());
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
	    ContactForm other = (ContactForm) obj;
	    if (country == null) {
		    if (other.country != null)
			    return false;
	    } else if (!country.equals(other.country))
		    return false;
	    if (email == null) {
		    if (other.email != null)
			    return false;
	    } else if (!email.equals(other.email))
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
	    if (message == null) {
		    if (other.message != null)
			    return false;
	    } else if (!message.equals(other.message))
		    return false;
	    if (mobile == null) {
		    if (other.mobile != null)
			    return false;
	    } else if (!mobile.equals(other.mobile))
		    return false;
	    if (phone == null) {
		    if (other.phone != null)
			    return false;
	    } else if (!phone.equals(other.phone))
		    return false;
	    if (subject == null) {
		    if (other.subject != null)
			    return false;
	    } else if (!subject.equals(other.subject))
		    return false;
	    if (website == null) {
		    if (other.website != null)
			    return false;
	    } else if (!website.equals(other.website))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "ContactUsForm [lastname=" + lastname + ", firstname=" + firstname + ", country=" + country + ", email=" + email + ", phone=" + phone + ", fax=" + fax + ", mobile=" + mobile
	            + ", website=" + website + ", subject=" + subject + ", message=" + message + "]";
    }

}