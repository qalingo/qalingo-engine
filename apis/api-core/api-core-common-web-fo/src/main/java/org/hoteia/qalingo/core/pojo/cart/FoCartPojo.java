package org.hoteia.qalingo.core.pojo.cart;

import org.hoteia.qalingo.core.pojo.CurrencyReferentialPojo;
import org.hoteia.qalingo.core.pojo.deliverymethod.FoDeliveryMethodPojo;

import java.util.HashSet;
import java.util.Set;

public class FoCartPojo implements ICartPojo {

    protected Long billingAddressId;
    protected Long shippingAddressId;

    protected FoDeliveryMethodPojo deliveryMethod;

    protected String deliveryPrice;

    protected String tvaPrice;
    protected String tvaPercentage;

    protected String subTotalPrice;
    protected String totalPrice;

    protected int totalCartItems;

    private CurrencyReferentialPojo currency;

    protected Set<FoCartItemPojo> cartItems = new HashSet<FoCartItemPojo>();

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

    public FoDeliveryMethodPojo getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(FoDeliveryMethodPojo deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getTvaPrice() {
        return tvaPrice;
    }

    public void setTvaPrice(String tvaPrice) {
        this.tvaPrice = tvaPrice;
    }

    public String getTvaPercentage() {
        return tvaPercentage;
    }

    public void setTvaPercentage(String tvaPercentage) {
        this.tvaPercentage = tvaPercentage;
    }

    public String getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(String subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<FoCartItemPojo> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<FoCartItemPojo> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalCartItems() {
        return totalCartItems;
    }

    public void setTotalCartItems(int totalCartItems) {
        this.totalCartItems = totalCartItems;
    }

    public CurrencyReferentialPojo getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyReferentialPojo currency) {
        this.currency = currency;
    }
}
