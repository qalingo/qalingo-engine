package fr.hoteia.qalingo.core.pojo.catalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import fr.hoteia.qalingo.core.pojo.AssetPojo;
import fr.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;

public class CatalogCategoryPojo {

    private Long id;
    private int version;
    private String code;
    private String businessName;
    private String description;
    private boolean isDefault;
    private boolean isRoot;
    private Date dateCreate;
    private Date dateUpdate;

    private CatalogCategoryTypePojo catalogCategoryType;
    private CatalogCategoryPojo defaultParentCatalogCategory;

    private Collection<CatalogCategoryAttributePojo> catalogCategoryGlobalAttributes = new ArrayList<CatalogCategoryAttributePojo>();
    private Collection<CatalogCategoryAttributePojo> catalogCategoryMarketAreaAttributes = new ArrayList<CatalogCategoryAttributePojo>();
    private Collection<CatalogCategoryPojo> catalogCategories = new ArrayList<CatalogCategoryPojo>();
    private Collection<ProductMarketingPojo> productMarketings = new ArrayList<ProductMarketingPojo>();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
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

    public CatalogCategoryTypePojo getCatalogCategoryType() {
        return catalogCategoryType;
    }

    public void setCatalogCategoryType(CatalogCategoryTypePojo catalogCategoryType) {
        this.catalogCategoryType = catalogCategoryType;
    }

    @JsonBackReference
    public CatalogCategoryPojo getDefaultParentCatalogCategory() {
        return defaultParentCatalogCategory;
    }

    public void setDefaultParentCatalogCategory(CatalogCategoryPojo defaultParentCatalogCategory) {
        this.defaultParentCatalogCategory = defaultParentCatalogCategory;
    }

    public Collection<CatalogCategoryAttributePojo> getCatalogCategoryGlobalAttributes() {
        return catalogCategoryGlobalAttributes;
    }

    public void setCatalogCategoryGlobalAttributes(Collection<CatalogCategoryAttributePojo> catalogCategoryGlobalAttributes) {
        this.catalogCategoryGlobalAttributes = catalogCategoryGlobalAttributes;
    }

    public Collection<CatalogCategoryAttributePojo> getCatalogCategoryMarketAreaAttributes() {
        return catalogCategoryMarketAreaAttributes;
    }

    public void setCatalogCategoryMarketAreaAttributes(Collection<CatalogCategoryAttributePojo> catalogCategoryMarketAreaAttributes) {
        this.catalogCategoryMarketAreaAttributes = catalogCategoryMarketAreaAttributes;
    }

    @JsonManagedReference
    public Collection<CatalogCategoryPojo> getCatalogCategories() {
        return catalogCategories;
    }

    public void setCatalogCategories(Collection<CatalogCategoryPojo> catalogCategories) {
        this.catalogCategories = catalogCategories;
    }

    public Collection<ProductMarketingPojo> getProductMarketings() {
        return productMarketings;
    }

    public void setProductMarketings(Collection<ProductMarketingPojo> productMarketings) {
        this.productMarketings = productMarketings;
    }

    public Collection<AssetPojo> getAssetsIsGlobal() {
        return assetsIsGlobal;
    }

    public void setAssetsIsGlobal(Collection<AssetPojo> assetsIsGlobal) {
        this.assetsIsGlobal = assetsIsGlobal;
    }

    public Collection<AssetPojo> getAssetsByMarketArea() {
        return assetsByMarketArea;
    }

    public void setAssetsByMarketArea(Collection<AssetPojo> assetsByMarketArea) {
        this.assetsByMarketArea = assetsByMarketArea;
    }

}