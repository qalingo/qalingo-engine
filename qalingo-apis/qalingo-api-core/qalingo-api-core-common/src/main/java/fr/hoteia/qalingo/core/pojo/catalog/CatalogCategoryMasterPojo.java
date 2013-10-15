package fr.hoteia.qalingo.core.pojo.catalog;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import fr.hoteia.qalingo.core.pojo.AssetPojo;
import fr.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

public class CatalogCategoryMasterPojo {

    private Long id;
    private int version;
    private String businessName;
    private String description;
    private String code;
    private boolean isDefault;
    private Date dateCreate;
    private Date dateUpdate;

    private CatalogCategoryTypePojo catalogCategoryType;
    private CatalogCategoryMasterPojo defaultParentCatalogCategory;

    private Collection<CatalogCategoryMasterAttributePojo> catalogCategoryGlobalAttributes = Collections.emptyList();
    private Collection<CatalogCategoryMasterAttributePojo> catalogCategoryMarketAreaAttributes = Collections.emptyList();
    private Collection<CatalogCategoryMasterPojo> catalogCategories = Collections.emptyList();
    private Collection<ProductMarketingPojo> productMarketings = Collections.emptyList();
    private Collection<AssetPojo> assetsIsGlobal = Collections.emptyList();
    private Collection<AssetPojo> assetsByMarketArea = Collections.emptyList();

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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
    public CatalogCategoryMasterPojo getDefaultParentCatalogCategory() {
        return defaultParentCatalogCategory;
    }

    public void setDefaultParentCatalogCategory(CatalogCategoryMasterPojo defaultParentCatalogCategory) {
        this.defaultParentCatalogCategory = defaultParentCatalogCategory;
    }

    public Collection<CatalogCategoryMasterAttributePojo> getCatalogCategoryGlobalAttributes() {
        return catalogCategoryGlobalAttributes;
    }

    public void setCatalogCategoryGlobalAttributes(Collection<CatalogCategoryMasterAttributePojo> catalogCategoryGlobalAttributes) {
        this.catalogCategoryGlobalAttributes = catalogCategoryGlobalAttributes;
    }

    public Collection<CatalogCategoryMasterAttributePojo> getCatalogCategoryMarketAreaAttributes() {
        return catalogCategoryMarketAreaAttributes;
    }

    public void setCatalogCategoryMarketAreaAttributes(Collection<CatalogCategoryMasterAttributePojo> catalogCategoryMarketAreaAttributes) {
        this.catalogCategoryMarketAreaAttributes = catalogCategoryMarketAreaAttributes;
    }

    @JsonManagedReference
    public Collection<CatalogCategoryMasterPojo> getCatalogCategories() {
        return catalogCategories;
    }

    public void setCatalogCategories(Collection<CatalogCategoryMasterPojo> catalogCategories) {
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
