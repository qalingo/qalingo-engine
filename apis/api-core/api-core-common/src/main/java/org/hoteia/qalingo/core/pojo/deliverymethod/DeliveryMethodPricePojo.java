/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.deliverymethod;

import java.math.BigDecimal;
import java.util.Date;

import org.hoteia.qalingo.core.pojo.CurrencyReferentialPojo;

public class DeliveryMethodPricePojo {

	private Long id;
	private int version;
	private BigDecimal price;
    private BigDecimal salePrice;
	private CurrencyReferentialPojo currency;
    private String priceWithStandardCurrencySign;
	private Long marketAreaId;
	private Long retailerId;
    private Date dateStart;
    private Date dateEnd;
    private Date dateCreate;
    private Date dateUpdate;
    
	public DeliveryMethodPricePojo() {
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
	
    public BigDecimal getSalePrice() {
        if (salePrice == null) {
            return price;
        }
        return salePrice;
    }
	
	public CurrencyReferentialPojo getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyReferentialPojo currency) {
		this.currency = currency;
	}

	public String getPriceWithStandardCurrencySign() {
        return priceWithStandardCurrencySign;
    }
	
	public void setPriceWithStandardCurrencySign(String priceWithStandardCurrencySign) {
        this.priceWithStandardCurrencySign = priceWithStandardCurrencySign;
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