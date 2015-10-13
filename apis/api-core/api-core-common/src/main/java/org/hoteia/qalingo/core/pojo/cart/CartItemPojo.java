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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hoteia.qalingo.core.pojo.AssetPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.pojo.product.ProductSkuPojo;
import org.hoteia.qalingo.core.pojo.product.ProductSkuPricePojo;

public class CartItemPojo {

    private Long id;
    private int quantity;
    
    protected List<AssetPojo> assets = new ArrayList<AssetPojo>();
    
    private CartItemPricePojo cartItemPrice;
    
    private Set<CartItemTaxPojo> taxes = new HashSet<CartItemTaxPojo>();

    private ProductSkuPojo productSku;
    private ProductMarketingPojo productMarketing;
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

    public List<AssetPojo> getAssets() {
        return assets;
    }

    public String getAssetPath(String type) {
        for (Iterator<AssetPojo> iterator = assets.iterator(); iterator.hasNext();) {
            AssetPojo assetViewBean = (AssetPojo) iterator.next();
            if (assetViewBean.getType().equals(type)) {
                return assetViewBean.getPath();
            }
        }
        return null;
    }

    public String getAssetAbsoluteWebPath(String type) {
        for (Iterator<AssetPojo> iterator = assets.iterator(); iterator.hasNext();) {
            AssetPojo assetViewBean = (AssetPojo) iterator.next();
            if (assetViewBean.getType().equals(type)) {
                return assetViewBean.getAbsoluteWebPath();
            }
        }
        return null;
    }

    public String getAssetRelativeWebPath(String type) {
        for (Iterator<AssetPojo> iterator = assets.iterator(); iterator.hasNext();) {
            AssetPojo assetViewBean = (AssetPojo) iterator.next();
            if (assetViewBean.getType().equals(type)) {
                return assetViewBean.getRelativeWebPath();
            }
        }
        return null;
    }

    public void setAssets(List<AssetPojo> assets) {
        this.assets = assets;
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

    public ProductMarketingPojo getProductMarketing() {
        return productMarketing;
    }

    public void setProductMarketing(ProductMarketingPojo productMarketing) {
        this.productMarketing = productMarketing;
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

    @Override
    public String toString() {
        return "CartItemPojo [id=" + id + ", quantity=" + quantity + ", assets=" + assets + ", cartItemPrice=" + cartItemPrice + ", taxes=" + taxes + ", productSku=" + productSku
                + ", productMarketing=" + productMarketing + ", catalogCategory=" + catalogCategory + ", price=" + price + ", totalAmountCartItem=" + totalAmountCartItem + "]";
    }
    
}