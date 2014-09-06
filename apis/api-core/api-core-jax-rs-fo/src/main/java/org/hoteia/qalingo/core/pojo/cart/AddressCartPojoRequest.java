package org.hoteia.qalingo.core.pojo.cart;

public class AddressCartPojoRequest {

    private String cartId;
    private String customerAddressId;
    
    public String getCartId() {
        return cartId;
    }
    
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
    
    public String getCustomerAddressId() {
        return customerAddressId;
    }
    
    public void setCustomerAddressId(String customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

}