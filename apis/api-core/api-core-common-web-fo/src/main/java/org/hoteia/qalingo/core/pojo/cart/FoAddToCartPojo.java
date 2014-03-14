package org.hoteia.qalingo.core.pojo.cart;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.product.ProductSkuPojo;

public class FoAddToCartPojo {

    private boolean statuts = true;
    
    private ProductSkuPojo productSku;
    private int quantity;

    private String checkoutShoppingCartHeaderLabel;
    private String checkoutShoppingCartUrl;

    private List<FoErrorPojo> errors = new ArrayList<FoErrorPojo>();

    public boolean isStatuts() {
        return statuts;
    }

    public void setStatuts(boolean statuts) {
        this.statuts = statuts;
    }

    public ProductSkuPojo getProductSku() {
        return productSku;
    }
    
    public void setProductSku(ProductSkuPojo productSku) {
        this.productSku = productSku;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getCheckoutShoppingCartHeaderLabel() {
        return checkoutShoppingCartHeaderLabel;
    }
    
    public void setCheckoutShoppingCartHeaderLabel(String checkoutShoppingCartHeaderLabel) {
        this.checkoutShoppingCartHeaderLabel = checkoutShoppingCartHeaderLabel;
    }
    
    public String getCheckoutShoppingCartUrl() {
        return checkoutShoppingCartUrl;
    }
    
    public void setCheckoutShoppingCartUrl(String checkoutShoppingCartUrl) {
        this.checkoutShoppingCartUrl = checkoutShoppingCartUrl;
    }

    public List<FoErrorPojo> getErrors() {
        return errors;
    }

    public void setErrors(List<FoErrorPojo> errors) {
        this.errors = errors;
    }

}