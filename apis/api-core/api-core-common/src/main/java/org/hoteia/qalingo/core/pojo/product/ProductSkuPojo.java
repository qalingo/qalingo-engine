package org.hoteia.qalingo.core.pojo.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hoteia.qalingo.core.pojo.AssetPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;

public class ProductSkuPojo {

    private Long id;
    private int version;
    private String businessName;
    private String description;
    private boolean isDefault;
    private String code;
    private Date dateCreate;
    private Date dateUpdate;

    private ProductMarketingPojo productMarketing;

    private Collection<ProductSkuAttributePojo> productSkuGlobalAttributes = new ArrayList<ProductSkuAttributePojo>();
    private Collection<ProductSkuAttributePojo> productSkuMarketAreaAttributes = new ArrayList<ProductSkuAttributePojo>();
    private Collection<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private Collection<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();
    private Collection<ProductSkuPricePojo> prices = new ArrayList<ProductSkuPricePojo>();
    private Collection<ProductSkuStockPojo> stocks = new ArrayList<ProductSkuStockPojo>();
    private Collection<RetailerPojo> retailers = new ArrayList<RetailerPojo>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @JsonBackReference
    public ProductMarketingPojo getProductMarketing() {
        return productMarketing;
    }

    public void setProductMarketing(ProductMarketingPojo productMarketing) {
        this.productMarketing = productMarketing;
    }

    public Collection<ProductSkuAttributePojo> getProductSkuGlobalAttributes() {
        return productSkuGlobalAttributes;
    }

    public void setProductSkuGlobalAttributes(Collection<ProductSkuAttributePojo> productSkuGlobalAttributes) {
        this.productSkuGlobalAttributes = PojoUtil.asList(productSkuGlobalAttributes);
    }

    public Collection<ProductSkuAttributePojo> getProductSkuMarketAreaAttributes() {
        return productSkuMarketAreaAttributes;
    }

    public void setProductSkuMarketAreaAttributes(Collection<ProductSkuAttributePojo> productSkuMarketAreaAttributes) {
        this.productSkuMarketAreaAttributes = PojoUtil.asList(productSkuMarketAreaAttributes);
    }

    public Collection<AssetPojo> getAssetsIsGlobal() {
        return assetsIsGlobal;
    }

    public void setAssetsIsGlobal(Collection<AssetPojo> assetsIsGlobal) {
        this.assetsIsGlobal = PojoUtil.asList(assetsIsGlobal);
    }

    public Collection<AssetPojo> getAssetsByMarketArea() {
        return assetsByMarketArea;
    }

    public void setAssetsByMarketArea(Collection<AssetPojo> assetsByMarketArea) {
        this.assetsByMarketArea = PojoUtil.asList(assetsByMarketArea);
    }

    public Collection<ProductSkuPricePojo> getPrices() {
        return prices;
    }

    public void setPrices(Collection<ProductSkuPricePojo> prices) {
        this.prices = PojoUtil.asList(prices);
    }

    public Collection<ProductSkuStockPojo> getStocks() {
        return stocks;
    }

    public void setStocks(Collection<ProductSkuStockPojo> stocks) {
        this.stocks = PojoUtil.asList(stocks);
    }

    public Collection<RetailerPojo> getRetailers() {
        return retailers;
    }

    public void setRetailers(Collection<RetailerPojo> retailers) {
        this.retailers = PojoUtil.asList(retailers);
    }
    
}