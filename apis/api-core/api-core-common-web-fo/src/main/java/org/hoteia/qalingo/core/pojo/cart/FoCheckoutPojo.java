/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.cart;

import java.util.ArrayList;
import java.util.List;

public class FoCheckoutPojo {

    private boolean statuts = true;
    private CartPojo cart;
    private List<FoDeliveryMethodInformationPojo> deliveryMethodInformations = new ArrayList<FoDeliveryMethodInformationPojo>();

    private List<FoMessagePojo> successMessages = new ArrayList<FoMessagePojo>();
    private List<FoMessagePojo> errorMessages = new ArrayList<FoMessagePojo>();

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

    public List<FoMessagePojo> getSuccessMessages() {
        return successMessages;
    }
    
    public void setSuccessMessages(List<FoMessagePojo> successMessages) {
        this.successMessages = successMessages;
    }

    public List<FoMessagePojo> getErrorMessages() {
        return errorMessages;
    }
    
    public void setErrorMessages(List<FoMessagePojo> errorMessages) {
        this.errorMessages = errorMessages;
    }

}