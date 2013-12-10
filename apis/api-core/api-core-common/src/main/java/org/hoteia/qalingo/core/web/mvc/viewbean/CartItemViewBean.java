/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class CartItemViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7032831815877311774L;
	
	protected String skuCode;
	protected String i18nName;
	protected int quantity;
	protected String unitPrice;
    protected String fees;
	protected String amount;
	protected String deleteUrl;
    protected String productDetailsUrl;

	public String getSkuCode() {
		return skuCode;
	}
	
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
	public String getI18nName() {
        return i18nName;
    }
	
	public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getUnitPrice() {
        return unitPrice;
    }
	
	public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
	
	public String getFees() {
        return fees;
    }
	
	public void setFees(String fees) {
        this.fees = fees;
    }
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDeleteUrl() {
		return deleteUrl;
	}
	
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	
	public String getProductDetailsUrl() {
        return productDetailsUrl;
    }
	
	public void setProductDetailsUrl(String productDetailsUrl) {
        this.productDetailsUrl = productDetailsUrl;
    }
	
}