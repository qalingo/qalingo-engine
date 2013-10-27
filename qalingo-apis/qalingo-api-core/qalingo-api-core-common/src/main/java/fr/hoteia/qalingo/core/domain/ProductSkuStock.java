/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name="TECO_PRODUCT_STOCK")
@FilterDef(name="marketArea", parameters=@ParamDef( name="marketAreaId", type="long" ) )
@Filters({ 
	@Filter(name="marketArea", condition="MARKET_AREA_ID = :marketAreaId"),
	@Filter(name="marketAreaAndRetailer", condition="MARKET_AREA_ID = :marketAreaId AND RETAILER_ID = :retailerId")
}) 
public class ProductSkuStock implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3373100081158498135L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="STOCK_RESERVED_ECO")
	private Integer stockReservedEco;

	@Column(name="STOCK_GLOBAL")
	private Integer stockGlobal;
	
	@Column(name="STOCK_PREORDERED")
	private Integer stockPreordered;
	
	@Column(name="MARKET_AREA_ID")
	private Long marketAreaId;
	
	@Column(name="RETAILER_ID")
	private Long retailerId;
	
	public ProductSkuStock() {
	}

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
