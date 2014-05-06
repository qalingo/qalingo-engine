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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((catalogPrice == null) ? 0 : catalogPrice.hashCode());
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
        CartItemPrice other = (CartItemPrice) obj;
        if (catalogPrice == null) {
            if (other.catalogPrice != null)
                return false;
        } else if (!catalogPrice.equals(other.catalogPrice))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CartItemPrice [catalogPrice=" + catalogPrice + "]";
    }

}