package org.hoteia.qalingo.core.pojo.cart;

public class AddToCartPojoRequest {

    private String marketAreaCode;
    private String cartId;   
    private String productSkuCode;
    private String catalogCategoryCode;
    private int quantity;

    public String getMarketAreaCode() {
        return marketAreaCode;
    }
    
    public void setMarketAreaCode(String marketAreaCode) {
        this.marketAreaCode = marketAreaCode;
    }
    
    public String getCartId() {
        return cartId;
    }
    
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
    
    public String getCatalogCategoryCode() {
        return catalogCategoryCode;
    }
    
    public void setCatalogCategoryCode(String catalogCategoryCode) {
        this.catalogCategoryCode = catalogCategoryCode;
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
