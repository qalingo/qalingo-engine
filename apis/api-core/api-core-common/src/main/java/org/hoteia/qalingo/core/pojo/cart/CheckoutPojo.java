package org.hoteia.qalingo.core.pojo.cart;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPojo {

    private boolean statuts;
    private CartPojo cart;
    
    public boolean isStatuts() {
        return statuts;
    }

    public void setStatuts(boolean statuts) {
        this.statuts = statuts;
    }

    public CartPojo getCart() {
        return cart;
    }
    
    public void setCart(CartPojo cart) {
        this.cart = cart;
    }
    
    private List<ErrorPojo> errors = new ArrayList<ErrorPojo>();
    
    public List<ErrorPojo> getErrors() {
        return errors;
    }
    
    public void setErrors(List<ErrorPojo> errors) {
        this.errors = errors;
    }
    
}