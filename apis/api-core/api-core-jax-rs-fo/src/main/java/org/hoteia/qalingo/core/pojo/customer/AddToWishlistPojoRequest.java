package org.hoteia.qalingo.core.pojo.customer;

public class AddToWishlistPojoRequest {

    private String marketAreaCode;
    private String catalogCategoryCode;
    private String productSkuCode;

    public String getMarketAreaCode() {
        return marketAreaCode;
    }
    
    public void setMarketAreaCode(String marketAreaCode) {
        this.marketAreaCode = marketAreaCode;
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

}
