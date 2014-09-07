package org.hoteia.qalingo.core.pojo.checkout;

public class CheckoutProcessPojoRequest {

    private String MarketAreaCode;
    private PaymentPojo paymentPojo;

    public String getMarketAreaCode() {
        return MarketAreaCode;
    }
    
    public void setMarketAreaCode(String marketAreaCode) {
        MarketAreaCode = marketAreaCode;
    }
    
    public PaymentPojo getPaymentPojo() {
        return paymentPojo;
    }
    
    public void setPaymentPojo(PaymentPojo paymentPojo) {
        this.paymentPojo = paymentPojo;
    }
    
}
