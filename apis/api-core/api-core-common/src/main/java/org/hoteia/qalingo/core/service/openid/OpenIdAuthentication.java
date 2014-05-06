/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.openid;

import java.io.Serializable;

/**
 * 
 */
public class OpenIdAuthentication implements Serializable {

    private static final long serialVersionUID = -7031455139710566518L;

    private String identity;
    private String email;
    private String fullname;
    private String firstname;
    private String lastname;
    private String language;
    private String gender;
    
    public String getIdentity() {
	    return identity;
    }
    
    public void setIdentity(String identity) {
	    this.identity = identity;
    }
    
	public String getEmail() {
    	return email;
    }

	public void setEmail(String email) {
    	this.email = email;
    }

	public String getFullname() {
    	return fullname;
    }

	public void setFullname(String fullname) {
    	this.fullname = fullname;
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

	public String getLanguage() {
    	return language;
    }

	public void setLanguage(String language) {
    	this.language = language;
    }

	public String getGender() {
    	return gender;
    }

	public void setGender(String gender) {
    	this.gender = gender;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((email == null) ? 0 : email.hashCode());
	    result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
	    result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
	    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
	    result = prime * result + ((identity == null) ? 0 : identity.hashCode());
	    result = prime * result + ((language == null) ? 0 : language.hashCode());
	    result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
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
	    OpenIdAuthentication other = (OpenIdAuthentication) obj;
	    if (email == null) {
		    if (other.email != null)
			    return false;
	    } else if (!email.equals(other.email))
		    return false;
	    if (firstname == null) {
		    if (other.firstname != null)
			    return false;
	    } else if (!firstname.equals(other.firstname))
		    return false;
	    if (fullname == null) {
		    if (other.fullname != null)
			    return false;
	    } else if (!fullname.equals(other.fullname))
		    return false;
	    if (gender == null) {
		    if (other.gender != null)
			    return false;
	    } else if (!gender.equals(other.gender))
		    return false;
	    if (identity == null) {
		    if (other.identity != null)
			    return false;
	    } else if (!identity.equals(other.identity))
		    return false;
	    if (language == null) {
		    if (other.language != null)
			    return false;
	    } else if (!language.equals(other.language))
		    return false;
	    if (lastname == null) {
		    if (other.lastname != null)
			    return false;
	    } else if (!lastname.equals(other.lastname))
		    return false;
	    return true;
    }
	@Override
    public String toString() {
	    return "OpenIdAuthentication [identity=" + identity + ", email=" + email + ", fullname=" + fullname + ", firstname=" + firstname + ", lastname=" + lastname + ", language=" + language
	            + ", gender=" + gender + "]";
    }

}