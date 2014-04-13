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
@Table(name = "TECO_CATALOG_MASTER_CATEGORY_PRODUCT_SKU_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.catalogCategoryMaster", joinColumns = @JoinColumn(name = "MASTER_CATEGORY_ID")),
    @AssociationOverride(name = "pk.productSku", joinColumns = @JoinColumn(name = "PRODUCT_SKU_ID")) })
public class CatalogCategoryMasterProductSkuRel extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 2322332601183745543L;

    @EmbeddedId
    private CatalogCategoryMasterProductSkuPk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;
    
    public CatalogCategoryMasterProductSkuRel() {
    }

    public CatalogCategoryMasterProductSkuPk getPk() {
        return pk;
    }

    public void setPk(CatalogCategoryMasterProductSkuPk pk) {
        this.pk = pk;
    }

    @Transient
    public CatalogCategoryMaster getCatalogCategoryMaster() {
        return getPk().getCatalogCategoryMaster();
    }

    public void setCatalogCategoryMaster(final CatalogCategoryMaster catalogCategoryMaster) {
        pk.setCatalogCategoryMaster(catalogCategoryMaster);
    }

    @Transient
    public ProductSku getProductSku() {
        return getPk().getProductSku();
    }

    public void setProductSku(final ProductSku productSku) {
        pk.setProductSku(productSku);
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
        CatalogCategoryMasterProductSkuRel other = (CatalogCategoryMasterProductSkuRel) obj;
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