/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.email.bean;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.util.CoreUtil;

public class OrderItemEmailBean extends AbstractEmailBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 8722711859740312774L;
    

    protected String skuCode;
    protected String ean;

    protected String i18nName;
    protected String i18nDescription;
    protected String i18nShortDescription;

    protected String storeCode;
    protected String storeName;

    protected int quantity;
    protected String price;
    protected String priceWithTaxes;
    protected String amount;
    protected String defaultAssetFullPath;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getEan() {
        return ean;
    }
    
    public void setEan(String ean) {
        this.ean = ean;
    }
    
    public String getI18nName() {
        return i18nName;
    }
    
    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }
    
    public String getI18nTruncatedName() {
        int size = Constants.POJO_NAME_MAX_LENGTH;
        if (StringUtils.isNotEmpty(getI18nName())){
            if(getI18nName().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nName(), size);
            } else {
                return getI18nName();
            }
        }
        return "";
    }
    
    public String getI18nDescription() {
        return i18nDescription;
    }

    public void setI18nDescription(String i18nDescription) {
        this.i18nDescription = i18nDescription;
    }
    
    public String getI18nShortDescription() {
        return i18nShortDescription;
    }
    
    public void setI18nShortDescription(String i18nShortDescription) {
        this.i18nShortDescription = i18nShortDescription;
    }
    
    public String getI18nTruncatedDescription() {
        int size = Constants.POJO_DESCRIPTION_MAX_LENGTH;
        if(StringUtils.isNotEmpty(getI18nShortDescription())){
            if(getI18nShortDescription().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nShortDescription(), size);
            } else {
                return getI18nShortDescription();
            }
        } else if (StringUtils.isNotEmpty(getI18nDescription())){
            if(getI18nDescription().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nDescription(), size);
            } else {
                return getI18nDescription();
            }
        }
        return "";
    }
    
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceWithTaxes() {
        return priceWithTaxes;
    }
    
    public void setPriceWithTaxes(String priceWithTaxes) {
        this.priceWithTaxes = priceWithTaxes;
    }
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDefaultAssetFullPath() {
        return defaultAssetFullPath;
    }
    
    public void setDefaultAssetFullPath(String defaultAssetFullPath) {
        this.defaultAssetFullPath = defaultAssetFullPath;
    }
    
}