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

import org.hoteia.qalingo.core.pojo.tax.TaxPojo;

public class CartItemTaxPojo {

	private TaxPojo tax;
	private BigDecimal taxAmount;
    
	public CartItemTaxPojo() {
	}
	
	public TaxPojo getTax() {
        return tax;
    }
	
	public void setTax(TaxPojo tax) {
        this.tax = tax;
    }
	
	public BigDecimal getTaxAmount() {
        return taxAmount;
    }
	
	public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

}