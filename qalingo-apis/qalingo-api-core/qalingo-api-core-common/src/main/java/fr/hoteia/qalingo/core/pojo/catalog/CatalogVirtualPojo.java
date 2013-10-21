package fr.hoteia.qalingo.core.pojo.catalog;

import fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CatalogVirtualPojo {

    private Long id;
    private int version;
    private String businessName;
    private String description;
    private boolean isDefault;
    private String code;
    private Date dateCreate;
    private Date dateUpdate;

    private CatalogMasterPojo catalogMaster;
    private Collection<CatalogCategoryVirtualPojo> productCategories = new ArrayList<CatalogCategoryVirtualPojo>();

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

    public CatalogMasterPojo getCatalogMaster() {
        return catalogMaster;
    }

    public void setCatalogMaster(CatalogMasterPojo catalogMaster) {
        this.catalogMaster = catalogMaster;
    }

    public Collection<CatalogCategoryVirtualPojo> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(Collection<CatalogCategoryVirtualPojo> productCategories) {
        this.productCategories = PojoUtil.asList(productCategories);
    }
}
