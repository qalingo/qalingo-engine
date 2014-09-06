package org.hoteia.qalingo.core.pojo.cart;

public class UpdateItemQuantityCartPojoRequest {

    private String cartId;
    private String productSkuCode;
    private int quantity;
    
    public String getCartId() {
        return cartId;
    }
    
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
    
    public String getProductSkuCode() {
        return productSkuCode;
    }
    
    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}