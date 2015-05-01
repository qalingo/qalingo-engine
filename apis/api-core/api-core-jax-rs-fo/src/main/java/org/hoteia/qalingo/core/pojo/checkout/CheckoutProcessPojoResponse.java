package org.hoteia.qalingo.core.pojo.checkout;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;
import org.hoteia.qalingo.core.pojo.OrderPurchasePojo;

public class CheckoutProcessPojoResponse extends AbstractPojoResponse {

    private OrderPurchasePojo orderPurchase;
    
    public OrderPurchasePojo getOrderPurchase() {
        return orderPurchase;
    }
    
    public void setOrderPurchase(OrderPurchasePojo orderPurchase) {
        this.orderPurchase = orderPurchase;
    }
    
}
