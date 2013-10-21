package fr.hoteia.qalingo.core.pojo.catalog;

import fr.hoteia.qalingo.core.pojo.AssetPojo;
import fr.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CatalogCategoryVirtualPojo {

    private Long id;
    private int version;
    private String businessName;
    private String description;
    private String code;
    private boolean isDefault;
    private Date dateCreate;
    private Date dateUpdate;

    private CatalogCategoryVirtualPojo defaultParentCatalogCategory;
    private CatalogCategoryMasterPojo categoryMaster;

    private Collection<CatalogCategoryVirtualAttributePojo> catalogCategoryGlobalAttributes = new ArrayList<CatalogCategoryVirtualAttributePojo>();
    private Collection<CatalogCategoryVirtualAttributePojo> catalogCategoryMarketAreaAttributes = new ArrayList<CatalogCategoryVirtualAttributePojo>();
    private Collection<CatalogCategoryVirtualPojo> catalogCategories = new ArrayList<CatalogCategoryVirtualPojo>();
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

    @JsonBackReference
    public CatalogCategoryVirtualPojo getDefaultParentCatalogCategory() {
        return defaultParentCatalogCategory;
    }

    public void setDefaultParentCatalogCategory(CatalogCategoryVirtualPojo defaultParentCatalogCategory) {
        this.defaultParentCatalogCategory = defaultParentCatalogCategory;
    }

    public CatalogCategoryMasterPojo getCategoryMaster() {
        return categoryMaster;
    }

    public void setCategoryMaster(CatalogCategoryMasterPojo categoryMaster) {
        this.categoryMaster = categoryMaster;
    }

    public Collection<CatalogCategoryVirtualAttributePojo> getCatalogCategoryGlobalAttributes() {
        return catalogCategoryGlobalAttributes;
    }

    public void setCatalogCategoryGlobalAttributes(Collection<CatalogCategoryVirtualAttributePojo> catalogCategoryGlobalAttributes) {
        this.catalogCategoryGlobalAttributes = catalogCategoryGlobalAttributes;
    }

    public Collection<CatalogCategoryVirtualAttributePojo> getCatalogCategoryMarketAreaAttributes() {
        return catalogCategoryMarketAreaAttributes;
    }

    public void setCatalogCategoryMarketAreaAttributes(Collection<CatalogCategoryVirtualAttributePojo> catalogCategoryMarketAreaAttributes) {
        this.catalogCategoryMarketAreaAttributes = catalogCategoryMarketAreaAttributes;
    }

    @JsonManagedReference
    public Collection<CatalogCategoryVirtualPojo> getCatalogCategories() {
        return catalogCategories;
    }

    public void setCatalogCategories(Collection<CatalogCategoryVirtualPojo> catalogCategories) {
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
