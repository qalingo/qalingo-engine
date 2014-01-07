package org.hoteia.qalingo.core.service.pojo;

import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.pojo.OrderCustomerPojo;

public interface OrderPojoService {
    
    OrderCustomerPojo handleOrderMapping(OrderCustomer order);

}
