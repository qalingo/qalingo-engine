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
@Table(name = "TECO_CATALOG_VIRTUAL_CATEGORY_CHILD_CATEGORY_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.parentCatalogCategoryVirtual", joinColumns = @JoinColumn(name = "PARENT_VIRTUAL_CATALOG_CATEGORY_ID")),
    @AssociationOverride(name = "pk.childCatalogCategoryVirtual", joinColumns = @JoinColumn(name = "CHILD_VIRTUAL_CATALOG_CATEGORY_ID")) })
public class CatalogCategoryVirtualChildCategoryRel extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8801997327038167002L;

    @EmbeddedId
    private CatalogCategoryVirtualChildCategoryPk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;
    
    public CatalogCategoryVirtualChildCategoryRel() {
    }

    public CatalogCategoryVirtualChildCategoryPk getPk() {
        return pk;
    }

    public void setPk(CatalogCategoryVirtualChildCategoryPk pk) {
        this.pk = pk;
    }

    @Transient
    public CatalogCategoryVirtual getParentCatalogCategoryVirtual() {
        return getPk().getParentCatalogCategoryVirtual();
    }

    public void setParentCatalogCategoryVirtual(final CatalogCategoryVirtual parentCatalogCategoryVirtual) {
        pk.setParentCatalogCategoryVirtual(parentCatalogCategoryVirtual);
    }
    
    @Transient
    public CatalogCategoryVirtual getChildCatalogCategoryVirtual() {
        return getPk().getChildCatalogCategoryVirtual();
    }

    public void setChildCatalogCategoryVirtual(final CatalogCategoryVirtual childCatalogCategoryVirtual) {
        pk.setChildCatalogCategoryVirtual(childCatalogCategoryVirtual);
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
        CatalogCategoryVirtualChildCategoryRel other = (CatalogCategoryVirtualChildCategoryRel) obj;
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