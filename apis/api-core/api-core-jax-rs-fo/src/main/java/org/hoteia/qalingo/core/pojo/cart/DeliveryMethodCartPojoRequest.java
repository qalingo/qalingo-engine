package org.hoteia.qalingo.core.pojo.cart;

public class DeliveryMethodCartPojoRequest {

    private String cartId;
    private String deliveryMethodCode;
    
    public String getCartId() {
        return cartId;
    }
    
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
    
    public String getDeliveryMethodCode() {
        return deliveryMethodCode;
    }
    
    public void setDeliveryMethodCode(String deliveryMethodCode) {
        this.deliveryMethodCode = deliveryMethodCode;
    }

}