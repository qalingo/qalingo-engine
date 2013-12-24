package org.hoteia.qalingo.core.pojo.product;

import java.math.BigDecimal;

import org.hoteia.qalingo.core.pojo.CurrencyReferentialPojo;

public class ProductSkuPricePojo {

    private Long id;
    private int version;
    private BigDecimal price;
    private CurrencyReferentialPojo currency;
    private Long marketAreaId;
    private Long retailerId;
    protected BigDecimal salePrice;
    protected BigDecimal moneySaving;
    protected String priceWithStandardCurrencySign;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CurrencyReferentialPojo getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyReferentialPojo currency) {
        this.currency = currency;
    }

    public Long getMarketAreaId() {
        return marketAreaId;
    }

    public void setMarketAreaId(Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getMoneySaving() {
        return moneySaving;
    }

    public void setMoneySaving(BigDecimal moneySaving) {
        this.moneySaving = moneySaving;
    }

    public String getPriceWithStandardCurrencySign() {
        return priceWithStandardCurrencySign;
    }

    public void setPriceWithStandardCurrencySign(String priceWithStandardCurrencySign) {
        this.priceWithStandardCurrencySign = priceWithStandardCurrencySign;
    }
    
}