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
public class CustomerEditForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 533390528530898417L;
    
	private String title;
    private String lastname;
    private String firstname;
    
    private String email;
    private String emailConfirm;

    private String phone;
    private String fax;
    private String mobile;

    private boolean optin;

	@NotEmpty(message = "error.form.customer.create.account.title.empty")
    public String getTitle() {
		return title;
	}
    
    public void setTitle(String title) {
		this.title = title;
	}
    
	@NotEmpty(message = "error.form.customer.create.account.lastname.empty")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@NotEmpty(message = "error.form.customer.create.account.firstname.empty")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@NotEmpty(message = "error.form.customer.create.account.email.empty")
	@Email(message = "error.form.customer.create.account.email.is.not.valid")
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
	    result = prime * result + ((email == null) ? 0 : email.hashCode());
	    result = prime * result + ((emailConfirm == null) ? 0 : emailConfirm.hashCode());
	    result = prime * result + ((fax == null) ? 0 : fax.hashCode());
	    result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
	    result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
	    result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
	    result = prime * result + (optin ? 1231 : 1237);
	    result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
	    CustomerEditForm other = (CustomerEditForm) obj;
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
	    if (phone == null) {
		    if (other.phone != null)
			    return false;
	    } else if (!phone.equals(other.phone))
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
	    return "CustomerEditForm [title=" + title + ", lastname=" + lastname + ", firstname=" + firstname + ", email=" + email + ", emailConfirm=" + emailConfirm + ", phone=" + phone + ", fax=" + fax
	            + ", mobile=" + mobile + ", optin=" + optin + "]";
    }

}