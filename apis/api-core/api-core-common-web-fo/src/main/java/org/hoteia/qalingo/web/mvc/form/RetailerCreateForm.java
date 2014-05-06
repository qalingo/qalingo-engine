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

/**
 * 
 * 
 */
public class RetailerCreateForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 912070782972183228L;
    
	private String name;
    private String email;
    private String website;
    
    private RetailerAddressForm address;
    
    public String getName() {
	    return name;
    }
    
    public void setName(String name) {
	    this.name = name;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RetailerAddressForm getAddress() {
	    return address;
    }
	
	public void setAddress(RetailerAddressForm address) {
	    this.address = address;
    }

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((address == null) ? 0 : address.hashCode());
	    result = prime * result + ((email == null) ? 0 : email.hashCode());
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	    RetailerCreateForm other = (RetailerCreateForm) obj;
	    if (address == null) {
		    if (other.address != null)
			    return false;
	    } else if (!address.equals(other.address))
		    return false;
	    if (email == null) {
		    if (other.email != null)
			    return false;
	    } else if (!email.equals(other.email))
		    return false;
	    if (name == null) {
		    if (other.name != null)
			    return false;
	    } else if (!name.equals(other.name))
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
	    return "RetailerCreateForm [name=" + name + ", email=" + email + ", website=" + website + ", address=" + address + "]";
    }

}