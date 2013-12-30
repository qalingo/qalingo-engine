package org.hoteia.qalingo.core.pojo.cart;

import java.util.ArrayList;
import java.util.List;

public class FoCheckoutPojo {

    private boolean statuts = true;
    private CartPojo cart;
    private List<FoDeliveryMethodInformationPojo> deliveryMethodInformations = new ArrayList<FoDeliveryMethodInformationPojo>();

    private List<FoErrorPojo> errors = new ArrayList<FoErrorPojo>();

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

    public List<FoDeliveryMethodInformationPojo> getDeliveryMethodInformations() {
        return deliveryMethodInformations;
    }
    
    public void setDeliveryMethodInformations(List<FoDeliveryMethodInformationPojo> deliveryMethodInformations) {
        this.deliveryMethodInformations = deliveryMethodInformations;
    }

    public List<FoErrorPojo> getErrors() {
        return errors;
    }

    public void setErrors(List<FoErrorPojo> errors) {
        this.errors = errors;
    }

}