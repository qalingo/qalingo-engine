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
@Table(name = "TECO_MARKET_AREA_WAREHOUSE_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.warehouse", joinColumns = @JoinColumn(name = "WAREHOUSE_ID")),
    @AssociationOverride(name = "pk.marketarea", joinColumns = @JoinColumn(name = "MARKET_AREA_ID")) })
public class WarehouseMarketAreaRel extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 577731551604474087L;

    @EmbeddedId
    private WarehouseMarketAreaPk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;
    
    public WarehouseMarketAreaRel() {
    }

    public WarehouseMarketAreaPk getPk() {
        return pk;
    }
    
    public void setPk(WarehouseMarketAreaPk pk) {
        this.pk = pk;
    }
    
    @Transient
    public Warehouse getWarehouse() {
        return getPk().getWarehouse();
    }

    public void setWarehouse(final Warehouse warehouse) {
        pk.setWarehouse(warehouse);
    }

    @Transient
    public MarketArea getMarketArea() {
        return getPk().getMarketArea();
    }

    public void setMarketArea(final MarketArea marketArea) {
        pk.setMarketArea(marketArea);
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
        WarehouseMarketAreaRel other = (WarehouseMarketAreaRel) obj;
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