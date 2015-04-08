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

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TECO_PRODUCT_SKU_PRICE")
public class ProductSkuPrice extends AbstractPrice<ProductSkuPrice> {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -4776613203202967926L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="SALE_PRICE")
	private BigDecimal salePrice;

    @Column(name = "IS_CATALOG_PRICE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isCatalogPrice;
    
    @Column(name = "IS_DISCOUNT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDiscount;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRODUCT_SKU_PRICE_ID", insertable = true, updatable = true)
	private ProductSkuPrice price;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CURRENCY_ID", insertable = true, updatable = true)
	private CurrencyReferential currency;
	
	@Column(name="MARKET_AREA_ID")
	private Long marketAreaId;
	
	@Column(name="STORE_ID")
	private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRODUCT_SKU_ID", insertable = true, updatable = true)
    private ProductSku productSku;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_START")
    private Date dateStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_END")
    private Date dateEnd;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;
    
	public ProductSkuPrice() {
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

	@Override
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	
    @Override
	public CurrencyReferential getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyReferential currency) {
		this.currency = currency;
	}

	public Long getMarketAreaId() {
		return marketAreaId;
	}

	public void setMarketAreaId(Long marketAreaId) {
		this.marketAreaId = marketAreaId;
	}
	
	public Long getStoreId() {
        return storeId;
    }
	
	public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
	
	public ProductSku getProductSku() {
        return productSku;
    }
	
	public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }
	
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((marketAreaId == null) ? 0 : marketAreaId.hashCode());
        result = prime * result + ((storeId == null) ? 0 : storeId.hashCode());
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
        ProductSkuPrice other = (ProductSkuPrice) obj;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (marketAreaId == null) {
            if (other.marketAreaId != null)
                return false;
        } else if (!marketAreaId.equals(other.marketAreaId))
            return false;
        if (storeId == null) {
            if (other.storeId != null)
                return false;
        } else if (!storeId.equals(other.storeId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductSkuPrice [id=" + id + ", version=" + version + ", currency=" + currency + ", marketAreaId=" + marketAreaId + ", storeId=" + storeId
                + ", productSku=" + productSku + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}