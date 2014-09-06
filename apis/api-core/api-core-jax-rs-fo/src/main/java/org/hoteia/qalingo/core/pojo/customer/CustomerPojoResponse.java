package org.hoteia.qalingo.core.pojo.customer;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class CustomerPojoResponse extends AbstractPojoResponse {

    private CustomerPojo customerPojo;
    
    public CustomerPojo getCustomerPojo() {
        return customerPojo;
    }
    
    public void setCustomerPojo(CustomerPojo customerPojo) {
        this.customerPojo = customerPojo;
    }
    
}
