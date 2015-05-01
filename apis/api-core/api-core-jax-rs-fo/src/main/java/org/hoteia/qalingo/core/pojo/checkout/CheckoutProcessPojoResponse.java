package org.hoteia.qalingo.core.pojo.checkout;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;
import org.hoteia.qalingo.core.pojo.OrderPurchasePojo;

public class CheckoutProcessPojoResponse extends AbstractPojoResponse {

    private OrderPurchasePojo orderCustomer;
    
    public OrderPurchasePojo getOrderCustomer() {
        return orderCustomer;
    }
    
    public void setOrderCustomer(OrderPurchasePojo orderCustomer) {
        this.orderCustomer = orderCustomer;
    }
    
}
