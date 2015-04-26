/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
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
@Table(name = "TECO_PRODUCT_SKU_OPTION_DEFINITION_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.productSku", joinColumns = @JoinColumn(name = "PRODUCT_SKU_ID")),
    @AssociationOverride(name = "pk.productSkuOptionDefinition", joinColumns = @JoinColumn(name = "PRODUCT_SKU_OPTION_DEFINITION_ID"))})
public class ProductSkuOptionRel extends AbstractEntity<ProductSkuOptionRel> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 2179540014678304872L;

    @EmbeddedId
    private ProductSkuOptionPk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;

    @Column(name = "IS_DEFAULT_SKU_OPTION", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefaultSkuOption;

    public ProductSkuOptionRel() {
        this.pk = new ProductSkuOptionPk();
    }
    
    public ProductSkuOptionRel(final ProductSku productSku, final ProductSkuOptionDefinition productSkuOptionDefinition) {
        this.pk = new ProductSkuOptionPk(productSku, productSkuOptionDefinition);
    }

    public ProductSkuOptionPk getPk() {
        return pk;
    }

    public void setPk(ProductSkuOptionPk pk) {
        this.pk = pk;
    }

    @Transient
    public ProductSku getProductSku() {
        return getPk().getProductSku();
    }

    public void setProductSku(final ProductSku productSku) {
        pk.setProductSku(productSku);
    }
    
    @Transient
    public ProductSkuOptionDefinition getProductSkuOptionDefinition() {
        return getPk().getProductSkuOptionDefinition();
    }

    public void setProductSkuOptionDefinition(final ProductSkuOptionDefinition productSkuOptionDefinition) {
        pk.setProductSkuOptionDefinition(productSkuOptionDefinition);
    }
    
    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public boolean isDefaultSkuOption() {
        return isDefaultSkuOption;
    }

    public void setDefaultSkuOption(boolean isDefaultSkuOption) {
        this.isDefaultSkuOption = isDefaultSkuOption;
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
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductSkuOptionRel other = (ProductSkuOptionRel) obj;
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