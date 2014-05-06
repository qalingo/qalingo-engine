/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.product;

public class ProductSkuStockPojo {

    private Long id;
    private int version;
    private Integer stockReservedEco;
    private Integer stockGlobal;
    private Integer stockPreordered;
    private Long marketAreaId;
    private Long retailerId;

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

    public Integer getStockReservedEco() {
        return stockReservedEco;
    }

    public void setStockReservedEco(Integer stockReservedEco) {
        this.stockReservedEco = stockReservedEco;
    }

    public Integer getStockGlobal() {
        return stockGlobal;
    }

    public void setStockGlobal(Integer stockGlobal) {
        this.stockGlobal = stockGlobal;
    }

    public Integer getStockPreordered() {
        return stockPreordered;
    }

    public void setStockPreordered(Integer stockPreordered) {
        this.stockPreordered = stockPreordered;
    }

    public Long getMarketAreaId() {
        return marketAreaId;
    }

    public void setMarketAreaId(Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }
}
