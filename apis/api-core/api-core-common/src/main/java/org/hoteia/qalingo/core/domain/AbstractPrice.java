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

import javax.persistence.Transient;

public abstract class AbstractPrice extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 3213518619670352263L;

    @Transient
    protected BigDecimal salePrice;

    @Transient
    protected BigDecimal moneySaving;
    
    public abstract BigDecimal getSalePrice();
    
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
    
    public BigDecimal getMoneySaving() {
        if (moneySaving == null) {
            return new BigDecimal(0);
        }
        return moneySaving;
    }
    
    public void setMoneySaving(BigDecimal moneySaving) {
        this.moneySaving = moneySaving;
    }
    
    public abstract CurrencyReferential getCurrency();
    
    public String getPriceWithStandardCurrencySign(){
        return getCurrency().formatPriceWithStandardCurrencySign(getSalePrice());
    }
    
}