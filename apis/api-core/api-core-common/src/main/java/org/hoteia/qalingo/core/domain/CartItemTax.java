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

public class CartItemTax extends AbstractEntity {

    /**
	 * Generated UID
	 */
    private static final long serialVersionUID = 8729023033376034948L;
    
	private Tax tax;

	private BigDecimal taxAmount;
    
	public CartItemTax() {
	}
	
	public Tax getTax() {
        return tax;
    }
	
	public void setTax(Tax tax) {
        this.tax = tax;
    }
	
	public BigDecimal getTaxAmount() {
        return taxAmount;
    }
	
	public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((taxAmount == null) ? 0 : taxAmount.hashCode());
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
        CartItemTax other = (CartItemTax) obj;
        if (taxAmount == null) {
            if (other.taxAmount != null)
                return false;
        } else if (!taxAmount.equals(other.taxAmount))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CartItemTax [taxAmount=" + taxAmount + "]";
    }

}