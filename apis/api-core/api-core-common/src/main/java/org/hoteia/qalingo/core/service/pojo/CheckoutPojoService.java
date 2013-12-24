package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.pojo.cart.CartPojo;
import org.hoteia.qalingo.core.pojo.deliverymethod.DeliveryMethodPojo;

public interface CheckoutPojoService {
    
    CartPojo handleCartMapping(Cart cart);

    List<DeliveryMethodPojo> getAvailableDeliveryMethods(MarketArea marketArea);

}
