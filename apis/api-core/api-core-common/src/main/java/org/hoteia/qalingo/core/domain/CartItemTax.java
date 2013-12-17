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

}