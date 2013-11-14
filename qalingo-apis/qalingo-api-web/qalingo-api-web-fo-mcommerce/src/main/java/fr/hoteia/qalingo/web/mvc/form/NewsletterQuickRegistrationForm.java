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

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class NewsletterQuickRegistrationForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 5141197211982561210L;
    
	private String email;
    
	@NotEmpty(message = "fo.follow_us.error_form_email_empty")
	@Email(message = "fo.follow_us.error_form_email_is_not_valid")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((email == null) ? 0 : email.hashCode());
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
	    NewsletterQuickRegistrationForm other = (NewsletterQuickRegistrationForm) obj;
	    if (email == null) {
		    if (other.email != null)
			    return false;
	    } else if (!email.equals(other.email))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "NewsletterQuickRegistrationForm [email=" + email + "]";
    }

}