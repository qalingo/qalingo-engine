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

public class FoCartItemPojo extends CartItemPojo {

    private String summaryImage;
    private String i18nName;
    
    private String priceWithStandardCurrencySign;
    private String totalAmountWithStandardCurrencySign;
    
    private String productDetailsUrl;
    
	public FoCartItemPojo(){
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
	
    public String getPriceWithStandardCurrencySign() {
        return priceWithStandardCurrencySign;
    }

    public void setPriceWithStandardCurrencySign(String priceWithStandardCurrencySign) {
        this.priceWithStandardCurrencySign = priceWithStandardCurrencySign;
    }

    public String getTotalAmountWithStandardCurrencySign() {
        return totalAmountWithStandardCurrencySign;
    }

    public void setTotalAmountWithStandardCurrencySign(String totalAmountWithStandardCurrencySign) {
        this.totalAmountWithStandardCurrencySign = totalAmountWithStandardCurrencySign;
    }
    
    public String getProductDetailsUrl() {
        return productDetailsUrl;
    }
    
    public void setProductDetailsUrl(String productDetailsUrl) {
        this.productDetailsUrl = productDetailsUrl;
    }
    
}