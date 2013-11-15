package org.hoteia.qalingo.core.pojo.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hoteia.qalingo.core.pojo.AssetPojo;
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;

public class ProductMarketingPojo {

    private Long id;
    private int version;
    private String businessName;
    private String description;
    private boolean isDefault;
    private String code;
    private Date dateCreate;
    private Date dateUpdate;

    private ProductBrandPojo productBrand;
    private ProductMarketingTypePojo productMarketingType;

    private Collection<ProductMarketingAttributePojo> productMarketingGlobalAttributes = new ArrayList<ProductMarketingAttributePojo>();
    private Collection<ProductMarketingAttributePojo> productMarketingMarketAreaAttributes = new ArrayList<ProductMarketingAttributePojo>();
    private Collection<ProductSkuPojo> productSkus = new ArrayList<ProductSkuPojo>();
    private Collection<ProductAssociationLinkPojo> productAssociationLinks = new ArrayList<ProductAssociationLinkPojo>();
    private Collection<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private Collection<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();

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

    public Collection<ProductMarketingAttributePojo> getProductMarketingGlobalAttributes() {
        return productMarketingGlobalAttributes;
    }

    public void setProductMarketingGlobalAttributes(Collection<ProductMarketingAttributePojo> productMarketingGlobalAttributes) {
        this.productMarketingGlobalAttributes = PojoUtil.asList(productMarketingGlobalAttributes);
    }

    public Collection<ProductMarketingAttributePojo> getProductMarketingMarketAreaAttributes() {
        return productMarketingMarketAreaAttributes;
    }

    public void setProductMarketingMarketAreaAttributes(Collection<ProductMarketingAttributePojo> productMarketingMarketAreaAttributes) {
        this.productMarketingMarketAreaAttributes = PojoUtil.asList(productMarketingMarketAreaAttributes);
    }

    @JsonManagedReference
    public Collection<ProductSkuPojo> getProductSkus() {
        return productSkus;
    }

    public void setProductSkus(Collection<ProductSkuPojo> productSkus) {
        this.productSkus = PojoUtil.asList(productSkus);
    }

    @JsonManagedReference
    public Collection<ProductAssociationLinkPojo> getProductAssociationLinks() {
        return productAssociationLinks;
    }

    public void setProductAssociationLinks(Collection<ProductAssociationLinkPojo> productAssociationLinks) {
        this.productAssociationLinks = PojoUtil.asList(productAssociationLinks);
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
    
}