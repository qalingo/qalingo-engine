/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.cart;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.product.ProductSkuPojo;

public class FoAddToWishlistPojo {

    private boolean statuts = true;
    
    private ProductSkuPojo productSku;

    private String wishListDetailsUrl;

    private List<FoMessagePojo> successMessages = new ArrayList<FoMessagePojo>();
    private List<FoMessagePojo> errorMessages = new ArrayList<FoMessagePojo>();

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

    public String getWishListDetailsUrl() {
        return wishListDetailsUrl;
    }
    
    public void setWishListDetailsUrl(String wishListDetailsUrl) {
        this.wishListDetailsUrl = wishListDetailsUrl;
    }
    
    public List<FoMessagePojo> getSuccessMessages() {
        return successMessages;
    }
    
    public void setSuccessMessages(List<FoMessagePojo> successMessages) {
        this.successMessages = successMessages;
    }

    public List<FoMessagePojo> getErrorMessages() {
        return errorMessages;
    }
    
    public void setErrorMessages(List<FoMessagePojo> errorMessages) {
        this.errorMessages = errorMessages;
    }

}