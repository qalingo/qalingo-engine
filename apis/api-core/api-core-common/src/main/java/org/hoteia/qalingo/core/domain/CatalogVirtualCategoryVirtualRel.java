package org.hoteia.qalingo.core.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TECO_CATALOG_VIRTUAL_CATEGORY_VIRTUAL_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.catalogVirtual", joinColumns = @JoinColumn(name = "VIRTUAL_CATALOG_ID")),
    @AssociationOverride(name = "pk.catalogCategoryVirtual", joinColumns = @JoinColumn(name = "VIRTUAL_CATEGORY_ID")) })
public class CatalogVirtualCategoryVirtualRel extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 7841708743235644167L;

    @EmbeddedId
    private CatalogVirtualCategoryVirtualPk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;
    
    public CatalogVirtualCategoryVirtualRel() {
    }

    public CatalogVirtualCategoryVirtualPk getPk() {
        return pk;
    }

    public void setPk(CatalogVirtualCategoryVirtualPk pk) {
        this.pk = pk;
    }

    public void setCatalogVirtual(final CatalogVirtual marketArea) {
        pk.setCatalogVirtual(marketArea);
    }

    @Transient
    public CatalogVirtual getCatalogVirtual() {
        return getPk().getCatalogVirtual();
    }

    @Transient
    public CatalogCategoryVirtual getCatalogCategoryVirtual() {
        return getPk().getCatalogCategoryVirtual();
    }

    public void setCatalogCategoryVirtual(final CatalogCategoryVirtual warehouse) {
        pk.setCatalogCategoryVirtual(warehouse);
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());
        result = prime * result + ((ranking == null) ? 0 : ranking.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CatalogVirtualCategoryVirtualRel other = (CatalogVirtualCategoryVirtualRel) obj;
        if (pk == null) {
            if (other.pk != null)
                return false;
        } else if (!pk.equals(other.pk))
            return false;
        if (ranking == null) {
            if (other.ranking != null)
                return false;
        } else if (!ranking.equals(other.ranking))
            return false;
        return true;
    }

}