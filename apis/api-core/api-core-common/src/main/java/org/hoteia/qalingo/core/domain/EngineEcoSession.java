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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hoteia.qalingo.core.domain.enumtype.EnvironmentType;

@Entity
@Table(name = "TECO_ENGINE_SESSION")
public class EngineEcoSession extends AbstractEngineSession {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 5720734402204437327L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "JSESSION_ID")
    private String jSessionId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ECO_ENGINE_SESSION_ID")
    private Set<Cart> carts = new HashSet<Cart>(); 
    
    @Transient
    private boolean environmentStagingModeEnabled;
    
    @Transient
    private EnvironmentType environmentType;

    @Transient
    private Customer currentCustomer;

    @Transient
    private MarketPlace currentMarketPlace;

    @Transient
    private Market currentMarket;

    @Transient
    private MarketArea currentMarketArea;

    @Transient
    private Localization currentMarketAreaLocalization;

    @Transient
    private Retailer currentMarketAreaRetailer;

    @Transient
    private CurrencyReferential currentMarketAreaCurrency;
    
    @Transient
    private User currentUser;

    @Transient
    private OrderCustomer lastOrder;

    @Transient
    private String theme;

    @Transient
    private String device;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public EngineEcoSession() {
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

    public Set<Cart> getCarts() {
        return carts;
    }

    public Cart getCart() {
        if(carts != null){
            for (Iterator<Cart> iterator = carts.iterator(); iterator.hasNext();) {
                Cart cart = (Cart) iterator.next();
                if(cart != null 
                        && cart.getMarketAreaId().equals(getCurrentMarketArea().getId())
                        && cart.getRetailerId().equals(getCurrentMarketAreaRetailer().getId()))
                    return cart;
            }
        }
        return null;
    }
    
    public Cart addNewCart() {
        Cart cart = new Cart();
        cart.setMarketAreaId(getCurrentMarketArea().getId());
        cart.setRetailerId(getCurrentMarketAreaRetailer().getId());
        cart.setCurrency(getCurrentMarketAreaCurrency());
        this.carts.add(cart);
        return cart;
    }
    
    public Cart resetCurrentCart() {
        Cart cart = getCart();
        cart = new Cart();
        cart.setMarketAreaId(getCurrentMarketArea().getId());
        cart.setRetailerId(getCurrentMarketAreaRetailer().getId());
        cart.setCurrency(getCurrentMarketAreaCurrency());
        return cart;
    }
    
    public Cart updateCart(Cart cart) {
        Cart cartToUpdate = getCart();
        cartToUpdate = cart;
        this.carts.add(cartToUpdate);
        return cartToUpdate;
    }
    
    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public boolean isEnvironmentStagingModeEnabled() {
        return environmentStagingModeEnabled;
    }

    public void setEnvironmentStagingModeEnabled(boolean environmentStagingModeEnabled) {
        this.environmentStagingModeEnabled = environmentStagingModeEnabled;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }
    
    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }
    
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
    }

    public MarketPlace getCurrentMarketPlace() {
        return currentMarketPlace;
    }

    public void setCurrentMarketPlace(MarketPlace marketPlace) {
        this.currentMarketPlace = marketPlace;
    }

    public Market getCurrentMarket() {
        return currentMarket;
    }

    public void setCurrentMarket(Market market) {
        this.currentMarket = market;
    }

    public MarketArea getCurrentMarketArea() {
        return currentMarketArea;
    }

    public void setCurrentMarketArea(MarketArea marketArea) {
        this.currentMarketArea = marketArea;
    }

    public Localization getCurrentMarketAreaLocalization() {
        return currentMarketAreaLocalization;
    }

    public void setCurrentMarketAreaLocalization(Localization localization) {
        this.currentMarketAreaLocalization = localization;
    }

    public Retailer getCurrentMarketAreaRetailer() {
        return currentMarketAreaRetailer;
    }

    public void setCurrentMarketAreaRetailer(Retailer retailer) {
        this.currentMarketAreaRetailer = retailer;
    }
    
    public CurrencyReferential getCurrentMarketAreaCurrency() {
        return currentMarketAreaCurrency;
    }
    
    public void setCurrentMarketAreaCurrency(CurrencyReferential currentMarketAreaCurrency) {
        this.currentMarketAreaCurrency = currentMarketAreaCurrency;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public OrderCustomer getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(OrderCustomer lastOrder) {
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