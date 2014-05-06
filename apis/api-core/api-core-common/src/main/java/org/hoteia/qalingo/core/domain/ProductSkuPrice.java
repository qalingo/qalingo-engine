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
public class ProductSkuPrice extends AbstractPrice {

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
	
	@Column(name="PRICE_CATALOG")
	private BigDecimal catalogPrice;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CURRENCY_ID", insertable = true, updatable = true)
	private CurrencyReferential currency;
	
	@Column(name="MARKET_AREA_ID")
	private Long marketAreaId;
	
	@Column(name="RETAILER_ID")
	private Long retailerId;

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

	public BigDecimal getCatalogPrice() {
        return catalogPrice;
    }
	
	public void setCatalogPrice(BigDecimal catalogPrice) {
        this.catalogPrice = catalogPrice;
    }
	
	@Override
    public BigDecimal getSalePrice() {
        if (salePrice == null) {
            return catalogPrice;
        }
        return salePrice;
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
	
	public Long getRetailerId() {
		return retailerId;
	}
	
	public void setRetailerId(Long retailerId) {
		this.retailerId = retailerId;
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
        result = prime * result + ((retailerId == null) ? 0 : retailerId.hashCode());
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
        if (retailerId == null) {
            if (other.retailerId != null)
                return false;
        } else if (!retailerId.equals(other.retailerId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductSkuPrice [id=" + id + ", version=" + version + ", catalogPrice=" + catalogPrice + ", currency=" + currency + ", marketAreaId=" + marketAreaId + ", retailerId=" + retailerId
                + ", productSku=" + productSku + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}