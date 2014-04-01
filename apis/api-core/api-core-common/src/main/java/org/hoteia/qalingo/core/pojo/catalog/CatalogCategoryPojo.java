package org.hoteia.qalingo.core.pojo.catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hoteia.qalingo.core.pojo.AssetPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class CatalogCategoryPojo {

    private Long id;
    private int version;
    private String code;
    private String name;
    private String description;
    private boolean isDefault;
    private boolean isRoot;
    private Date dateCreate;
    private Date dateUpdate;

    private CatalogCategoryTypePojo catalogCategoryType;
    private CatalogCategoryPojo defaultParentCatalogCategory;

    private List<CatalogCategoryAttributePojo> catalogCategoryGlobalAttributes = new ArrayList<CatalogCategoryAttributePojo>();
    private List<CatalogCategoryAttributePojo> catalogCategoryMarketAreaAttributes = new ArrayList<CatalogCategoryAttributePojo>();
    private List<CatalogCategoryPojo> catalogCategories = new ArrayList<CatalogCategoryPojo>();
    private List<ProductMarketingPojo> productMarketings = new ArrayList<ProductMarketingPojo>();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<CatalogCategoryAttributePojo> getCatalogCategoryGlobalAttributes() {
        return catalogCategoryGlobalAttributes;
    }

    public void setCatalogCategoryGlobalAttributes(List<CatalogCategoryAttributePojo> catalogCategoryGlobalAttributes) {
        this.catalogCategoryGlobalAttributes = catalogCategoryGlobalAttributes;
    }

    public List<CatalogCategoryAttributePojo> getCatalogCategoryMarketAreaAttributes() {
        return catalogCategoryMarketAreaAttributes;
    }

    public void setCatalogCategoryMarketAreaAttributes(List<CatalogCategoryAttributePojo> catalogCategoryMarketAreaAttributes) {
        this.catalogCategoryMarketAreaAttributes = catalogCategoryMarketAreaAttributes;
    }

    @JsonManagedReference
    public List<CatalogCategoryPojo> getCatalogCategories() {
        return catalogCategories;
    }

    public void setCatalogCategories(List<CatalogCategoryPojo> catalogCategories) {
        this.catalogCategories = catalogCategories;
    }

    public List<ProductMarketingPojo> getProductMarketings() {
        return productMarketings;
    }

    public void setProductMarketings(List<ProductMarketingPojo> productMarketings) {
        this.productMarketings = productMarketings;
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