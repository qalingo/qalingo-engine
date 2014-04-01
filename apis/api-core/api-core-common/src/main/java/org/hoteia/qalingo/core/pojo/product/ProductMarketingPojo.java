package org.hoteia.qalingo.core.pojo.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hoteia.qalingo.core.pojo.AssetPojo;

public class ProductMarketingPojo {

    private Long id;
    private int version;
    private String name;
    private String description;
    private boolean isDefault;
    private String code;
    private Date dateCreate;
    private Date dateUpdate;

    private ProductBrandPojo productBrand;
    private ProductMarketingTypePojo productMarketingType;

    private List<ProductMarketingAttributePojo> productMarketingGlobalAttributes = new ArrayList<ProductMarketingAttributePojo>();
    private List<ProductMarketingAttributePojo> productMarketingMarketAreaAttributes = new ArrayList<ProductMarketingAttributePojo>();
    private List<ProductSkuPojo> productSkus = new ArrayList<ProductSkuPojo>();
    private List<ProductAssociationLinkPojo> productAssociationLinks = new ArrayList<ProductAssociationLinkPojo>();
    private List<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private List<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ProductBrandPojo getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(ProductBrandPojo productBrand) {
        this.productBrand = productBrand;
    }

    public ProductMarketingTypePojo getProductMarketingType() {
        return productMarketingType;
    }

    public void setProductMarketingType(ProductMarketingTypePojo productMarketingType) {
        this.productMarketingType = productMarketingType;
    }

    public List<ProductMarketingAttributePojo> getProductMarketingGlobalAttributes() {
        return productMarketingGlobalAttributes;
    }

    public void setProductMarketingGlobalAttributes(List<ProductMarketingAttributePojo> productMarketingGlobalAttributes) {
        this.productMarketingGlobalAttributes = productMarketingGlobalAttributes;
    }

    public List<ProductMarketingAttributePojo> getProductMarketingMarketAreaAttributes() {
        return productMarketingMarketAreaAttributes;
    }

    public void setProductMarketingMarketAreaAttributes(List<ProductMarketingAttributePojo> productMarketingMarketAreaAttributes) {
        this.productMarketingMarketAreaAttributes = productMarketingMarketAreaAttributes;
    }

    @JsonManagedReference
    public List<ProductSkuPojo> getProductSkus() {
        return productSkus;
    }

    public void setProductSkus(List<ProductSkuPojo> productSkus) {
        this.productSkus = productSkus;
    }

    @JsonManagedReference
    public List<ProductAssociationLinkPojo> getProductAssociationLinks() {
        return productAssociationLinks;
    }

    public void setProductAssociationLinks(List<ProductAssociationLinkPojo> productAssociationLinks) {
        this.productAssociationLinks = productAssociationLinks;
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
    
}