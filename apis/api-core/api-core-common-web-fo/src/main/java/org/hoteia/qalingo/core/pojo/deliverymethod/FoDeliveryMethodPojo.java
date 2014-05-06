/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.deliverymethod;

import java.math.BigDecimal;

public class FoDeliveryMethodPojo extends DeliveryMethodPojo {

    private String arrivalTime;
    public BigDecimal price;
    public String priceWithStandardCurrencySign;
    private boolean selected;
    
    public FoDeliveryMethodPojo() {
    }
    
    public String getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceWithStandardCurrencySign() {
        return priceWithStandardCurrencySign;
    }

    public void setPriceWithStandardCurrencySign(String priceWithStandardCurrencySign) {
        this.priceWithStandardCurrencySign = priceWithStandardCurrencySign;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
}