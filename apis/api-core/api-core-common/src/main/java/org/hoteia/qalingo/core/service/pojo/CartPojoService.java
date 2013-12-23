package org.hoteia.qalingo.core.service.pojo;

import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.pojo.cart.CartPojo;

public interface CartPojoService {
    
    CartPojo handleCartMapping(final Cart cart);
    
}
