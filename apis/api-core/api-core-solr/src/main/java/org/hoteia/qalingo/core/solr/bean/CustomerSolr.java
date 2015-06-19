/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr.bean;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class CustomerSolr {

	@Field
	private Long id;
	
    @Field
	private String lastname;

    @Field
    private String firstname;

    @Field
    private String email;
    
    @Field
    private String gender ;
    
    @Field
    private String title;
    
    @Field
    private String random;
    
    @Field("datecreate")
    private Date dateCreate;

    @Field("dateupdate")
    private Date dateUpdate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

    public String getRandom() {
        return random;
    }
    
    public void setRandom(String random) {
        this.random = random;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        CustomerSolr other = (CustomerSolr) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomerSolr [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", email=" + email + ", gender=" + gender + ", title=" + title + ", dateCreate=" + dateCreate
                + ", dateUpdate=" + dateUpdate + "]";
    }
    
}