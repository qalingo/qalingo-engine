/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo;

import java.io.Serializable;

public class UrlParameterMapping implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 633605376073973865L;

    private String marketPlaceCode = null;
    private String marketCode = null;
    private String marketAreaCode = null;
    private String localizationCode = null;
    private String retailerCode = null;
    private String currencyCode = null;
	
	public UrlParameterMapping() {
    }
	
	public String getMarketPlaceCode() {
        return marketPlaceCode;
    }
	
	public void setMarketPlaceCode(String marketPlaceCode) {
        this.marketPlaceCode = marketPlaceCode;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMarketAreaCode() {
        return marketAreaCode;
    }

    public void setMarketAreaCode(String marketAreaCode) {
        this.marketAreaCode = marketAreaCode;
    }

    public String getLocalizationCode() {
        return localizationCode;
    }

    public void setLocalizationCode(String localizationCode) {
        this.localizationCode = localizationCode;
    }

    public String getRetailerCode() {
        return retailerCode;
    }

    public void setRetailerCode(String retailerCode) {
        this.retailerCode = retailerCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
	
}