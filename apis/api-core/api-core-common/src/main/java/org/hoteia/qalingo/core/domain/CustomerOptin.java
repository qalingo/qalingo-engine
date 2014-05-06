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
@Table(name="TECO_CUSTOMER_OPTIN")
public class CustomerOptin extends AbstractEntity {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -8032856482540522113L;

    public static final String OPTIN_TYPE_WWW_NEWSLETTER = "WWW_NEWSLETTER";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="ORIGIN")
	private String origin;
	
	@Column(name="CUSTOMER_MARKET_AREA_ID")
	private Long customerMarketAreaId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public CustomerOptin() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
    	return type;
    }

	public void setType(String type) {
    	this.type = type;
    }

	public String getOrigin() {
    	return origin;
    }

	public void setOrigin(String origin) {
    	this.origin = origin;
    }

	public Long getCustomerMarketAreaId() {
    	return customerMarketAreaId;
    }

	public void setCustomerMarketAreaId(Long customerMarketAreaId) {
    	this.customerMarketAreaId = customerMarketAreaId;
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
        result = prime * result + ((customerMarketAreaId == null) ? 0 : customerMarketAreaId.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        CustomerOptin other = (CustomerOptin) obj;
        if (customerMarketAreaId == null) {
            if (other.customerMarketAreaId != null)
                return false;
        } else if (!customerMarketAreaId.equals(other.customerMarketAreaId))
            return false;
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
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomerOptin [id=" + id + ", type=" + type + ", origin=" + origin + ", customerMarketAreaId=" + customerMarketAreaId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
                + "]";
    }

}