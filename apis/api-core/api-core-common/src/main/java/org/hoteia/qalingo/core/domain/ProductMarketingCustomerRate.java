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
@Table(name="TECO_PRODUCT_MARKETING_CUSTOMER_RATE")
public class ProductMarketingCustomerRate extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2501911341288490523L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="RATE")
    private Integer rate;
	
	@Column(name="TYPE")
    private String type;
	
    @Column(name="MARKET_AREA_ID")
    private Long marketAreaId;
    
	@Column(name="PRODUCT_MARKETING_ID")
	private Long productMarketingId;
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	@Column(name="PROCESSED", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean processed;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_CREATE")
    private Date dateCreate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_UPDATE")
    private Date dateUpdate;
    
	public ProductMarketingCustomerRate() {
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

    public Long getMarketAreaId() {
        return marketAreaId;
    }
    
    public void setMarketAreaId(Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

	public Long getProductMarketingId() {
        return productMarketingId;
    }
	
	public void setProductMarketingId(Long productMarketingId) {
        this.productMarketingId = productMarketingId;
    }
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public boolean isProcessed() {
    	return processed;
    }

	public void setProcessed(boolean processed) {
    	this.processed = processed;
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
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
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
        ProductMarketingCustomerRate other = (ProductMarketingCustomerRate) obj;
        if (customerId == null) {
            if (other.customerId != null)
                return false;
        } else if (!customerId.equals(other.customerId))
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
        return true;
    }

    @Override
    public String toString() {
        return "ProductMarketingCustomerRate [id=" + id + ", rate=" + rate + ", type=" + type + ", marketAreaId=" + marketAreaId + ", productMarketingId=" + productMarketingId + ", customerId="
                + customerId + ", processed=" + processed + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}