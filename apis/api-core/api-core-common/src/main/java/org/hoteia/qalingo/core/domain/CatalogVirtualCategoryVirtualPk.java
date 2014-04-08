package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CatalogVirtualCategoryVirtualPk extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 2408760296487323798L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIRTUAL_CATALOG_ID")
    private CatalogVirtual catalogVirtual;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIRTUAL_CATEGORY_ID")
    private CatalogCategoryVirtual catalogCategoryVirtual;
    
    public CatalogVirtualCategoryVirtualPk() {
    }

    public CatalogVirtual getCatalogVirtual() {
        return catalogVirtual;
    }

    public void setCatalogVirtual(CatalogVirtual catalogVirtual) {
        this.catalogVirtual = catalogVirtual;
    }

    public CatalogCategoryVirtual getCatalogCategoryVirtual() {
        return catalogCategoryVirtual;
    }

    public void setCatalogCategoryVirtual(CatalogCategoryVirtual catalogCategoryVirtual) {
        this.catalogCategoryVirtual = catalogCategoryVirtual;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((catalogCategoryVirtual == null) ? 0 : catalogCategoryVirtual.hashCode());
        result = prime * result + ((catalogVirtual == null) ? 0 : catalogVirtual.hashCode());
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
        CatalogVirtualCategoryVirtualPk other = (CatalogVirtualCategoryVirtualPk) obj;
        if (catalogCategoryVirtual == null) {
            if (other.catalogCategoryVirtual != null)
                return false;
        } else if (!catalogCategoryVirtual.equals(other.catalogCategoryVirtual))
            return false;
        if (catalogVirtual == null) {
            if (other.catalogVirtual != null)
                return false;
        } else if (!catalogVirtual.equals(other.catalogVirtual))
            return false;
        return true;
    }

}