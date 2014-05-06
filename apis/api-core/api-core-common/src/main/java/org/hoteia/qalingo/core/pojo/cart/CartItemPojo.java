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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.pojo.product.ProductSkuPojo;
import org.hoteia.qalingo.core.pojo.product.ProductSkuPricePojo;

public class CartItemPojo {

    private Long id;
    private int quantity;
    
    private CartItemPricePojo cartItemPrice;
    
    private Set<CartItemTaxPojo> taxes = new HashSet<CartItemTaxPojo>();

    private String productSkuCode;
    private ProductSkuPojo productSku;

    private String productMarketingCode;
    private ProductMarketingPojo productMarketing;

    private String catalogCategoryCode;
    private CatalogCategoryPojo catalogCategory;
    
    private ProductSkuPricePojo price;
    private BigDecimal totalAmountCartItem;
    
	public CartItemPojo(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getProductSkuCode() {
		return productSkuCode;
	}
	
	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}
	
	public CartItemPricePojo getCartItemPrice() {
        return cartItemPrice;
    }
	
	public void setCartItemPrice(CartItemPricePojo cartItemPrice) {
        this.cartItemPrice = cartItemPrice;
    }
	
	public Set<CartItemTaxPojo> getTaxes() {
        return taxes;
    }
	
	public void setTaxes(Set<CartItemTaxPojo> taxes) {
        this.taxes = taxes;
    }
	
	public ProductSkuPojo getProductSku() {
		return productSku;
	}
	
	public void setProductSku(ProductSkuPojo productSku) {
		this.productSku = productSku;
	}
	
	public String getProductMarketingCode() {
        return productMarketingCode;
    }

    public void setProductMarketingCode(String productMarketingCode) {
        this.productMarketingCode = productMarketingCode;
    }

    public ProductMarketingPojo getProductMarketing() {
        return productMarketing;
    }

    public void setProductMarketing(ProductMarketingPojo productMarketing) {
        this.productMarketing = productMarketing;
    }

    public String getCatalogCategoryCode() {
        return catalogCategoryCode;
    }

    public void setCatalogCategoryCode(String catalogCategoryCode) {
        this.catalogCategoryCode = catalogCategoryCode;
    }

    public CatalogCategoryPojo getCatalogCategory() {
        return catalogCategory;
    }
    
    public void setCatalogCategory(CatalogCategoryPojo catalogCategory) {
        this.catalogCategory = catalogCategory;
    }

    public ProductSkuPricePojo getPrice() {
        return price;
    }

    public void setPrice(ProductSkuPricePojo price) {
        this.price = price;
    }

    public BigDecimal getTotalAmountCartItem() {
        return totalAmountCartItem;
    }

    public void setTotalAmountCartItem(BigDecimal totalAmountCartItem) {
        this.totalAmountCartItem = totalAmountCartItem;
    }
    
}