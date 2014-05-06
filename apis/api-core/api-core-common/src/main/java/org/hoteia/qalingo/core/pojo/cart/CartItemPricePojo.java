/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.cart;

import java.math.BigDecimal;

import org.hoteia.qalingo.core.pojo.CurrencyReferentialPojo;

public class CartItemPricePojo {

	private BigDecimal catalogPrice;
	private CurrencyReferentialPojo currency;
    protected BigDecimal salePrice;
    protected BigDecimal moneySaving;
    protected String priceWithStandardCurrencySign;
    
	public CartItemPricePojo() {
	}
	
	public BigDecimal getCatalogPrice() {
        return catalogPrice;
    }
	
	public void setCatalogPrice(BigDecimal catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public CurrencyReferentialPojo getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyReferentialPojo currency) {
        this.currency = currency;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getMoneySaving() {
        return moneySaving;
    }

    public void setMoneySaving(BigDecimal moneySaving) {
        this.moneySaving = moneySaving;
    }

    public String getPriceWithStandardCurrencySign() {
        return priceWithStandardCurrencySign;
    }

    public void setPriceWithStandardCurrencySign(String priceWithStandardCurrencySign) {
        this.priceWithStandardCurrencySign = priceWithStandardCurrencySign;
    }
	
}