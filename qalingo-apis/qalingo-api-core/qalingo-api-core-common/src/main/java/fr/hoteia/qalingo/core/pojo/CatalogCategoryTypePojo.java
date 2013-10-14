package fr.hoteia.qalingo.core.pojo;

import javax.persistence.*;
import java.util.*;

import fr.hoteia.qalingo.core.domain.CatalogCategoryTypeAttribute;

public class CatalogCategoryTypePojo {

    private Long id;
    private int version;
    private String name;
    private String description;
    private String code;
    private Date dateCreate;
    private Date dateUpdate;

    private Collection<CatalogCategoryTypeAttributePojo> catalogCategoryTypeAttributes = new ArrayList<CatalogCategoryTypeAttributePojo>();

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

    public Collection<CatalogCategoryTypeAttributePojo> getCatalogCategoryTypeAttributes() {
        return catalogCategoryTypeAttributes;
    }

    public void setCatalogCategoryTypeAttributes(Collection<CatalogCategoryTypeAttributePojo> catalogCategoryTypeAttributes) {
        this.catalogCategoryTypeAttributes = new ArrayList<CatalogCategoryTypeAttributePojo>(catalogCategoryTypeAttributes);
    }
}
