/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TECO_CUSTOMER_CREDENTIAL")
public class CustomerCredential extends AbstractAddress {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 3355851476679100735L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="PASSWORD")
    private String password;
	
	@Column(name="RESET_TOKEN")
    private String resetToken;
	
	@Column(name="TOKEN_TIMESTAMP")
    private Timestamp tokenTimestamp;

	@Column(name="RESET_PROCESSED_DATE")
    private Date resetProcessedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public CustomerCredential() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPassword() {
    	return password;
    }

	public void setPassword(String password) {
    	this.password = password;
    }

	public String getResetToken() {
    	return resetToken;
    }

	public void setResetToken(String resetToken) {
    	this.resetToken = resetToken;
    }

	public Timestamp getTokenTimestamp() {
    	return tokenTimestamp;
    }

	public void setTokenTimestamp(Timestamp tokenTimestamp) {
    	this.tokenTimestamp = tokenTimestamp;
    }

	public Date getResetProcessedDate() {
	    return resetProcessedDate;
    }
	
	public void setResetProcessedDate(Date resetProcessedDate) {
	    this.resetProcessedDate = resetProcessedDate;
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
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
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
        CustomerCredential other = (CustomerCredential) obj;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomerCredential [id=" + id + ", password=" + password + ", resetToken=" + resetToken + ", tokenTimestamp=" + tokenTimestamp + ", resetProcessedDate=" + resetProcessedDate
                + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}