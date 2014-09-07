package org.hoteia.qalingo.core.pojo.checkout;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;
import org.hoteia.qalingo.core.pojo.OrderCustomerPojo;

public class CheckoutProcessPojoResponse extends AbstractPojoResponse {

    private OrderCustomerPojo orderCustomer;
    
    public OrderCustomerPojo getOrderCustomer() {
        return orderCustomer;
    }
    
    public void setOrderCustomer(OrderCustomerPojo orderCustomer) {
        this.orderCustomer = orderCustomer;
    }
    
}
