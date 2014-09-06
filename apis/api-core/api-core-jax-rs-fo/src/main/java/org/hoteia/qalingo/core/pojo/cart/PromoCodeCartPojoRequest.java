package org.hoteia.qalingo.core.pojo.cart;

public class PromoCodeCartPojoRequest {

    private String cartId;
    private String promoCode;
    
    public String getCartId() {
        return cartId;
    }
    
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

}