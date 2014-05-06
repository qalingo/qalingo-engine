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

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class WarehouseMarketAreaPk extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 577731551604474087L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_AREA_ID")
    private MarketArea marketArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSE_ID")
    private Warehouse warehouse;

    public WarehouseMarketAreaPk() {
    }

    public MarketArea getMarketArea() {
        return marketArea;
    }

    public void setMarketArea(MarketArea marketArea) {
        this.marketArea = marketArea;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((marketArea == null) ? 0 : marketArea.hashCode());
        result = prime * result + ((warehouse == null) ? 0 : warehouse.hashCode());
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
        WarehouseMarketAreaPk other = (WarehouseMarketAreaPk) obj;
        if (marketArea == null) {
            if (other.marketArea != null)
                return false;
        } else if (!marketArea.equals(other.marketArea))
            return false;
        if (warehouse == null) {
            if (other.warehouse != null)
                return false;
        } else if (!warehouse.equals(other.warehouse))
            return false;
        return true;
    }

}