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

@Entity
@Table(name="TECO_CUSTOMER_MARKET_AREA")
public class CustomerMarketArea implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6596549095870442990L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="PHONE")
    private String phone;

	@Column(name="FAX")
    private String fax;
	
	@Column(name="MOBILE")
    private String mobile;

	@Column(name="OPTIN", nullable=false, columnDefinition="int(11) default 0")
    private boolean optin;
    
	@Column(name="MARKET_AREA_CODE")
    private String marketAreaCode;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="CUSTOMER_MARKET_AREA_ID")
	private Set<CustomerWishlist> wishlistProducts = new HashSet<CustomerWishlist>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="CUSTOMER_MARKET_AREA_ID")
	private Set<CustomerProductComment> productComments = new HashSet<CustomerProductComment>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
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

	public boolean isOptin() {
		return optin;
	}

	public void setOptin(boolean optin) {
		this.optin = optin;
	}
	
	public String getMarketAreaCode() {
		return marketAreaCode;
	}
	
	public void setMarketAreaCode(String marketAreaCode) {
		this.marketAreaCode = marketAreaCode;
	}

	public Set<CustomerWishlist> getWishlistProducts() {
		return wishlistProducts;
	}
	
	public CustomerWishlist getCustomerWishlistByProductSkuCode(final String productSkuCode) {
		CustomerWishlist customerWishlistToReturn = null;
		for (Iterator<CustomerWishlist> iterator = wishlistProducts.iterator(); iterator.hasNext();) {
			CustomerWishlist customerWishlist = (CustomerWishlist) iterator.next();
			if(customerWishlist.getProductSkuCode().equalsIgnoreCase(productSkuCode)){
				customerWishlistToReturn = customerWishlist;
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
	
}