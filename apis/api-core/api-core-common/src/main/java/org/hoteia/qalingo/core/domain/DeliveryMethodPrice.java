/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name="TECO_DELIVERY_METHOD_PRICE")
public class DeliveryMethodPrice extends AbstractPrice {

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
	
	@Column(name="PRICE")
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="CURRENCY_ID", insertable=false, updatable=false)
	private CurrencyReferential currency;
	
	@Column(name="MARKET_AREA_ID")
	private Long marketAreaId;
	
	@Column(name="RETAILER_ID")
	private Long retailerId;

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
    
	public DeliveryMethodPrice() {
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

	public BigDecimal getPrice() {
        return price;
    }
	
	public void setPrice(BigDecimal price) {
        this.price = price;
    }
	
	@Override
    public BigDecimal getSalePrice() {
        if (salePrice == null) {
            return price;
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
    
}