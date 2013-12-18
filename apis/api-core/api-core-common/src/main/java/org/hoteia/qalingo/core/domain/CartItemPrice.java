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

public class CartItemPrice extends AbstractPrice {

    /**
	 * Generated UID
	 */
    private static final long serialVersionUID = -452848911575097360L;
    
	private BigDecimal catalogPrice;

	private CurrencyReferential currency;
	
    
	public CartItemPrice() {
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

}