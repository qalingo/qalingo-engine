/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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
    protected String summaryImage;
	protected String i18nName;
	protected int quantity;
	protected String unitPriceWithCurrencySign;
    protected String feesWithCurrencySign;
	protected String amountWithCurrencySign;
	
	protected String deleteUrl;
    protected String productDetailsUrl;

	public String getSkuCode() {
		return skuCode;
	}
	
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
	public String getSummaryImage() {
        return summaryImage;
    }
	
	public void setSummaryImage(String summaryImage) {
        this.summaryImage = summaryImage;
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
	
	public String getUnitPriceWithCurrencySign() {
        return unitPriceWithCurrencySign;
    }

    public void setUnitPriceWithCurrencySign(String unitPriceWithCurrencySign) {
        this.unitPriceWithCurrencySign = unitPriceWithCurrencySign;
    }

    public String getFeesWithCurrencySign() {
        return feesWithCurrencySign;
    }

    public void setFeesWithCurrencySign(String feesWithCurrencySign) {
        this.feesWithCurrencySign = feesWithCurrencySign;
    }

    public String getAmountWithCurrencySign() {
        return amountWithCurrencySign;
    }

    public void setAmountWithCurrencySign(String amountWithCurrencySign) {
        this.amountWithCurrencySign = amountWithCurrencySign;
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