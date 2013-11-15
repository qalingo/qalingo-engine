package org.hoteia.qalingo.core.pojo.catalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CatalogPojo {

    private Long id;
    private int version;
    private String code;
    private String businessName;
    private String description;
    private boolean isDefault = false;
    private boolean isMaster = false;
    private Date dateCreate;
    private Date dateUpdate;

    private Collection<CatalogCategoryPojo> catalogCategories = new ArrayList<CatalogCategoryPojo>();

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

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean isMaster) {
        this.isMaster = isMaster;
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

    public Collection<CatalogCategoryPojo> getCatalogCategories() {
        return catalogCategories;
    }

    public void setCatalogCategories(Collection<CatalogCategoryPojo> catalogCategories) {
        this.catalogCategories = new ArrayList<CatalogCategoryPojo>(catalogCategories);
    }
    
}