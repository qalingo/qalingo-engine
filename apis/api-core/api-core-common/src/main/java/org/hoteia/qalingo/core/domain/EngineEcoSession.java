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

import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.domain.bean.GeolocData;
import org.hoteia.qalingo.core.domain.enumtype.EnvironmentType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TECO_ENGINE_SESSION")
public class EngineEcoSession extends AbstractEngineSession<EngineEcoSession> {

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

    @Column(name = "JSESSION_ID", unique = true)
    private String jSessionId;

    @Column(name = "ENGINE_SESSION_GUID", unique = true)
    private String engineSessionGuid;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST })
    @JoinColumn(name="ECO_ENGINE_SESSION_ID", referencedColumnName="ID")
    private Set<Cart> carts = new HashSet<Cart>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", insertable = true, updatable = true)
    private Customer currentCustomer;

    @Transient
    private boolean environmentStagingModeEnabled;

    @Transient
    private EnvironmentType environmentType;

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
    private Set<OrderPurchase> lastOrders = new HashSet<OrderPurchase>();

    @Transient
    private GeolocData geolocData;
    
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
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
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

    public String getEngineSessionGuid() {
        return engineSessionGuid;
    }

    public void setEngineSessionGuid(String engineSessionGuid) {
        this.engineSessionGuid = engineSessionGuid;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public Cart getCart() {
        if (carts != null && Hibernate.isInitialized(carts)) {
            for (Cart cart : carts) {
                if (cart != null && cart.getMarketAreaId().equals(getCurrentMarketArea().getId())
                        && cart.getRetailerId().equals(getCurrentMarketAreaRetailer().getId()))
                    return cart;
            }
        }
        return null;
    }

    public Cart addNewCart() {
        Cart cart = new Cart();
        cart.setVersion(1);
        cart.setMarketAreaId(getCurrentMarketArea().getId());
        cart.setLocalizationId(getCurrentMarketAreaLocalization().getId());
        cart.setRetailerId(getCurrentMarketAreaRetailer().getId());
        cart.setCurrency(getCurrentMarketAreaCurrency());
        Date date = new Date();
        cart.setDateCreate(date);
        cart.setDateUpdate(date);

        if (getCurrentCustomer() != null) {
            cart.setCustomerId(getCurrentCustomer().getId());
            cart.setBillingAddressId(getCurrentCustomer().getDefaultBillingAddressId());
            cart.setShippingAddressId(getCurrentCustomer().getDefaultShippingAddressId());
        }

        this.carts.add(cart);
        return cart;
    }

    public Cart resetCurrentCart() {
        Cart cart = getCart();
        cart.getCartItems().clear();
        cart.setStatus(null);
        cart.setDeliveryMethods(null);
        cart.setTaxes(null);
        cart.setMarketAreaId(getCurrentMarketArea().getId());
        cart.setLocalizationId(getCurrentMarketAreaLocalization().getId());
        cart.setRetailerId(getCurrentMarketAreaRetailer().getId());
        cart.setCurrency(getCurrentMarketAreaCurrency());
        return cart;
    }

    public void deleteCurrentCart() {
        if (carts != null) {
            Set<Cart> checkedCarts = new HashSet<Cart>(carts);
            for (Cart cart : checkedCarts) {
                if (cart != null && cart.getMarketAreaId().equals(getCurrentMarketArea().getId())
                        && cart.getRetailerId().equals(getCurrentMarketAreaRetailer().getId())) {
                    this.carts.remove(cart);
                }
            }
        }
    }

    public void updateCart(Cart cart) {
        if(this.carts != null){
        	Set<Cart> updatedCarts = new HashSet<Cart>();
            for (Cart cartIt : carts) {
                if (cartIt.getId().equals(cart.getId())) {
                    updatedCarts.add(cart);
                } else {
                    updatedCarts.add(cartIt);
                }
            }
            this.carts.clear();
            this.carts = updatedCarts;
        }
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

    public Set<OrderPurchase> getLastOrders() {
        return lastOrders;
    }

    public OrderPurchase getLastOrder() {
        if (lastOrders != null && Hibernate.isInitialized(lastOrders)) {
            for (OrderPurchase orderPurchase : lastOrders) {
                if (orderPurchase != null && getCurrentMarketArea() != null && getCurrentMarketAreaRetailer() != null
                        && orderPurchase.getMarketAreaId().equals(getCurrentMarketArea().getId())
                        && orderPurchase.getRetailerId().equals(getCurrentMarketAreaRetailer().getId()))
                    return orderPurchase;
            }
        }
        return null;
    }

    public void setLastOrder(OrderPurchase lastOrder) {
        if (lastOrders != null && Hibernate.isInitialized(lastOrders) && lastOrder != null) {
            for (OrderPurchase orderPurchase : lastOrders) {
                if (orderPurchase != null && getCurrentMarketArea() != null && getCurrentMarketAreaRetailer() != null
                        && orderPurchase.getMarketAreaId().equals(getCurrentMarketArea().getId())
                        && orderPurchase.getRetailerId().equals(getCurrentMarketAreaRetailer().getId()))
                    lastOrders.remove(orderPurchase);
            }
            lastOrders.add(lastOrder);
        }
    }

    public void setLastOrders(Set<OrderPurchase> lastOrders) {
        this.lastOrders = lastOrders;
    }
    
    public GeolocData getGeolocData() {
        return geolocData;
    }
    
    public void setGeolocData(GeolocData geolocData) {
        this.geolocData = geolocData;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((jSessionId == null) ? 0 : jSessionId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EngineEcoSession other = (EngineEcoSession) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (jSessionId == null) {
            if (other.jSessionId != null)
                return false;
        } else if (!jSessionId.equals(other.jSessionId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EngineEcoSession [id=" + id + ", version=" + version + ", jSessionId=" + jSessionId + ", engineSessionGuid=" + engineSessionGuid + ", theme=" + theme + ", device=" + device
                + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}