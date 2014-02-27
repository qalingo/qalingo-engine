package org.hoteia.qalingo.core.pojo.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hoteia.qalingo.core.pojo.AssetPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

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

    private List<ProductSkuAttributePojo> productSkuGlobalAttributes = new ArrayList<ProductSkuAttributePojo>();
    private List<ProductSkuAttributePojo> productSkuMarketAreaAttributes = new ArrayList<ProductSkuAttributePojo>();
    private List<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private List<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();
    private List<ProductSkuPricePojo> prices = new ArrayList<ProductSkuPricePojo>();
    private List<ProductSkuStockPojo> stocks = new ArrayList<ProductSkuStockPojo>();
    private List<RetailerPojo> retailers = new ArrayList<RetailerPojo>();

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

    public List<ProductSkuAttributePojo> getProductSkuGlobalAttributes() {
        return productSkuGlobalAttributes;
    }

    public void setProductSkuGlobalAttributes(List<ProductSkuAttributePojo> productSkuGlobalAttributes) {
        this.productSkuGlobalAttributes = productSkuGlobalAttributes;
    }

    public List<ProductSkuAttributePojo> getProductSkuMarketAreaAttributes() {
        return productSkuMarketAreaAttributes;
    }

    public void setProductSkuMarketAreaAttributes(List<ProductSkuAttributePojo> productSkuMarketAreaAttributes) {
        this.productSkuMarketAreaAttributes = productSkuMarketAreaAttributes;
    }

    public List<AssetPojo> getAssetsIsGlobal() {
        return assetsIsGlobal;
    }

    public void setAssetsIsGlobal(List<AssetPojo> assetsIsGlobal) {
        this.assetsIsGlobal = assetsIsGlobal;
    }

    public List<AssetPojo> getAssetsByMarketArea() {
        return assetsByMarketArea;
    }

    public void setAssetsByMarketArea(List<AssetPojo> assetsByMarketArea) {
        this.assetsByMarketArea = assetsByMarketArea;
    }

    public List<ProductSkuPricePojo> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductSkuPricePojo> prices) {
        this.prices = prices;
    }

    public List<ProductSkuStockPojo> getStocks() {
        return stocks;
    }

    public void setStocks(List<ProductSkuStockPojo> stocks) {
        this.stocks = stocks;
    }

    public List<RetailerPojo> getRetailers() {
        return retailers;
    }

    public void setRetailers(List<RetailerPojo> retailers) {
        this.retailers = retailers;
    }
    
}