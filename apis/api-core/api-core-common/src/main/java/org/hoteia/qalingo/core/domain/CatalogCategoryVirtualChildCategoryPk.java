package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CatalogCategoryVirtualChildCategoryPk extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -47805508981335812L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_VIRTUAL_CATALOG_CATEGORY_ID")
    private CatalogCategoryVirtual parentCatalogCategoryVirtual;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_VIRTUAL_CATALOG_CATEGORY_ID")
    private CatalogCategoryVirtual childCatalogCategoryVirtual;
    
    public CatalogCategoryVirtualChildCategoryPk() {
    }

    public CatalogCategoryVirtual getParentCatalogCategoryVirtual() {
        return parentCatalogCategoryVirtual;
    }

    public void setParentCatalogCategoryVirtual(CatalogCategoryVirtual parentCatalogCategoryVirtual) {
        this.parentCatalogCategoryVirtual = parentCatalogCategoryVirtual;
    }

    public CatalogCategoryVirtual getChildCatalogCategoryVirtual() {
        return childCatalogCategoryVirtual;
    }

    public void setChildCatalogCategoryVirtual(CatalogCategoryVirtual childCatalogCategoryVirtual) {
        this.childCatalogCategoryVirtual = childCatalogCategoryVirtual;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((childCatalogCategoryVirtual == null) ? 0 : childCatalogCategoryVirtual.hashCode());
        result = prime * result + ((parentCatalogCategoryVirtual == null) ? 0 : parentCatalogCategoryVirtual.hashCode());
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
        CatalogCategoryVirtualChildCategoryPk other = (CatalogCategoryVirtualChildCategoryPk) obj;
        if (childCatalogCategoryVirtual == null) {
            if (other.childCatalogCategoryVirtual != null)
                return false;
        } else if (!childCatalogCategoryVirtual.equals(other.childCatalogCategoryVirtual))
            return false;
        if (parentCatalogCategoryVirtual == null) {
            if (other.parentCatalogCategoryVirtual != null)
                return false;
        } else if (!parentCatalogCategoryVirtual.equals(other.parentCatalogCategoryVirtual))
            return false;
        return true;
    }

}