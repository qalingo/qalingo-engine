/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TECO_RETAILER_CUSTOMER_RATE")
public class RetailerCustomerRate extends AbstractAddress implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2501911341288490523L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="rate")
    private Integer rate;
	
	@Column(name="TYPE")
    private String type;
	
	@Column(name="RETAILER_ID")
	private Long retailerId;
	
	public RetailerCustomerRate() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getRate() {
    	return rate;
    }

	public void setRate(Integer rate) {
    	this.rate = rate;
    }

	public String getType() {
    	return type;
    }

	public void setType(String type) {
    	this.type = type;
    }

	public Long getRetailerId() {
	    return retailerId;
    }
	
	public void setRetailerId(Long retailerId) {
	    this.retailerId = retailerId;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((rate == null) ? 0 : rate.hashCode());
	    result = prime * result + ((retailerId == null) ? 0 : retailerId.hashCode());
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
	    RetailerCustomerRate other = (RetailerCustomerRate) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    if (rate == null) {
		    if (other.rate != null)
			    return false;
	    } else if (!rate.equals(other.rate))
		    return false;
	    if (retailerId == null) {
		    if (other.retailerId != null)
			    return false;
	    } else if (!retailerId.equals(other.retailerId))
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
	    return "RetailerCustomerRate [id=" + id + ", rate=" + rate + ", type=" + type + ", retailerId=" + retailerId + "]";
    }
	
}