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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="TECO_ENGINE_SESSION")
public class EngineEcoSession implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5720734402204437327L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="JSESSION_ID")
	private String jSessionId;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="CART_ID")
	private Cart cart;

	@Transient 
	private Customer currentCustomer;

	@Transient 
	private MarketPlace currentMarketPlace;

	@Transient 
	private Market currentMarket;

	@Transient 
	private MarketArea currentMarketArea;

	@Transient 
	private Localization currentLocalization;
	
	@Transient 
	private Retailer currentRetailer;
	
	@Transient 
	private User currentUser;
	
	@Transient 
	private Order lastOrder;
	
	@Transient 
	private String theme;
	
	@Transient 
	private String device;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public EngineEcoSession(){
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

	public String getjSessionId() {
		return jSessionId;
	}
	
	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}

	public Cart getCart() {
		return cart;
	}
	
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public Customer getCurrentCustomer() {
		return currentCustomer;
	}
	
	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}
	
	public MarketPlace getCurrentMarketPlace() {
		return currentMarketPlace;
	}
	
	public void setCurrentMarketPlace(MarketPlace currentMarketPlace) {
		this.currentMarketPlace = currentMarketPlace;
	}
	
	public Market getCurrentMarket() {
		return currentMarket;
	}
	
	public void setCurrentMarket(Market currentMarket) {
		this.currentMarket = currentMarket;
	}
	
	public MarketArea getCurrentMarketArea() {
		return currentMarketArea;
	}
	
	public void setCurrentMarketArea(MarketArea currentMarketArea) {
		this.currentMarketArea = currentMarketArea;
	}
	
	public Localization getCurrentLocalization() {
		return currentLocalization;
	}
	
	public void setCurrentLocalization(Localization currentLocalization) {
		this.currentLocalization = currentLocalization;
	}
	
	public Retailer getCurrentRetailer() {
		return currentRetailer;
	}
	
	public void setCurrentRetailer(Retailer currentRetailer) {
		this.currentRetailer = currentRetailer;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public Order getLastOrder() {
		return lastOrder;
	}
	
	public void setLastOrder(Order lastOrder) {
		this.lastOrder = lastOrder;
	}
	
	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getDevice() {
		return device;
	}
	
	public void setDevice(String device) {
		this.device = device;
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
