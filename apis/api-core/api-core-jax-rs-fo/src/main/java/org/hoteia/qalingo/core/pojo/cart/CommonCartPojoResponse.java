package org.hoteia.qalingo.core.pojo.cart;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class CommonCartPojoResponse extends AbstractPojoResponse {

    private CartPojo cart;
    
    public CartPojo getCart() {
        return cart;
    }
    
    public void setCart(CartPojo cart) {
        this.cart = cart;
    }
    
}
