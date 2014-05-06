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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

@Entity
@Table(name="TECO_CUSTOMER_MARKET_AREA")
public class CustomerMarketArea extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6596549095870442990L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "MARKET_AREA_ID")
    private Long marketAreaId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CUSTOMER_MARKET_AREA_ID")
    private Set<CustomerOptin> optins = new HashSet<CustomerOptin>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_MARKET_AREA_ID")
    private Set<CustomerWishlist> wishlistProducts = new HashSet<CustomerWishlist>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_MARKET_AREA_ID")
    private Set<CustomerProductComment> productComments = new HashSet<CustomerProductComment>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;
	
	public CustomerMarketArea(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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

	public Long getMarketAreaId() {
	    return marketAreaId;
    }
	
	public void setMarketAreaId(Long marketAreaId) {
	    this.marketAreaId = marketAreaId;
    }
	
	public Set<CustomerOptin> getOptins() {
	    return optins;
    }
	
	public CustomerOptin getOptins(String type) {
		CustomerOptin customerOptin = null;
		if(optins != null
		        && Hibernate.isInitialized(optins)
				&& StringUtils.isNotEmpty(type)){
			for (Iterator<CustomerOptin> iterator = optins.iterator(); iterator.hasNext();) {
				CustomerOptin customerOptinIt = (CustomerOptin) iterator.next();
				if(type.equalsIgnoreCase(customerOptinIt.getType())){
					customerOptin = customerOptinIt;
				}
            }
		}
	    return customerOptin;
    }
	
	public void addOptins(CustomerOptin customerOptin) {
		customerOptin.setCustomerMarketAreaId(getId());
		customerOptin.setDateCreate(new Date());
		customerOptin.setDateUpdate(new Date());
		optins.add(customerOptin);
    }
	
	public void setOptins(Set<CustomerOptin> optins) {
	    this.optins = optins;
    }

	public Set<CustomerWishlist> getWishlistProducts() {
		return wishlistProducts;
	}
	
	public CustomerWishlist getCustomerWishlistByProductSkuCode(final String productSkuCode) {
		CustomerWishlist customerWishlistToReturn = null;
		if(wishlistProducts != null
                && Hibernate.isInitialized(wishlistProducts)){
	        for (Iterator<CustomerWishlist> iterator = wishlistProducts.iterator(); iterator.hasNext();) {
	            CustomerWishlist customerWishlist = (CustomerWishlist) iterator.next();
	            if(customerWishlist.getProductSkuCode().equalsIgnoreCase(productSkuCode)){
	                customerWishlistToReturn = customerWishlist;
	            }
	        }
		}
		return customerWishlistToReturn;
	}
	
	public void setWishlistProducts(Set<CustomerWishlist> wishlistProducts) {
		this.wishlistProducts = wishlistProducts;
	}
	
	public Set<CustomerProductComment> getProductComments() {
		return productComments;
	}
	
	public void setProductComments(Set<CustomerProductComment> productComments) {
		this.productComments = productComments;
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
        result = prime * result + ((marketAreaId == null) ? 0 : marketAreaId.hashCode());
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
        CustomerMarketArea other = (CustomerMarketArea) obj;
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
        if (marketAreaId == null) {
            if (other.marketAreaId != null)
                return false;
        } else if (!marketAreaId.equals(other.marketAreaId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomerMarketArea [id=" + id + ", version=" + version + ", phone=" + phone + ", fax=" + fax + ", mobile=" + mobile + ", marketAreaId=" + marketAreaId + ", dateCreate=" + dateCreate
                + ", dateUpdate=" + dateUpdate + "]";
    }
	
}