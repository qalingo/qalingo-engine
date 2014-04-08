package org.hoteia.qalingo.core.pojo.catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CatalogPojo {

    private Long id;
    private int version;
    private String code;
    private String name;
    private String description;
    private boolean isDefault = false;
    private boolean isMaster = false;
    private Date dateCreate;
    private Date dateUpdate;

    private List<CatalogCategoryPojo> sortedCatalogCategories = new ArrayList<CatalogCategoryPojo>();

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

    public List<CatalogCategoryPojo> getSortedCatalogCategories() {
        return sortedCatalogCategories;
    }
    
    public void setSortedCatalogCategories(List<CatalogCategoryPojo> sortedCatalogCategories) {
        this.sortedCatalogCategories = sortedCatalogCategories;
    }
    
}