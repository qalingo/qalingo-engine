/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="TECO_CART")
public class Cart implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1607025617573183198L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="STATUS")
	private String status;

	@Column(name="CUSTOMER_ID")
	private Long customerId;

	@Column(name="BILLING_ADDRESS_ID")
	private Long billingAddressId;
	
	@Column(name="SHIPPING_ADDRESS_ID")
	private Long shippingAddressId;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="SESSION_ID")
	private EngineEcoSession session;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="CART_ID")
	private Set<CartItem> cartItems = new HashSet<CartItem>();

	@Transient
	private Set<Shipping> shippings = new HashSet<Shipping>();
	
	@Transient
	private Set<Tax> taxes = new HashSet<Tax>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public Cart(){
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

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Long getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(Long billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	public Long getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public EngineEcoSession getSession() {
		return session;
	}
	
	public void setSession(EngineEcoSession session) {
		this.session = session;
	}
	
	public Set<CartItem> getCartItems() {
		return cartItems;
	}
	
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	public Set<Shipping> getShippings() {
		return shippings;
	}

	public void setShippings(Set<Shipping> shippings) {
		this.shippings = shippings;
	}

	public Set<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(Set<Tax> taxes) {
		this.taxes = taxes;
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