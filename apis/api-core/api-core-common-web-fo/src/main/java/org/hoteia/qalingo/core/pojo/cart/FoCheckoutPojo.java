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

import org.hoteia.qalingo.core.pojo.FoMessagePojo;
import org.hoteia.qalingo.core.pojo.deliverymethod.FoDeliveryMethodPojo;

import java.util.ArrayList;
import java.util.List;

public class FoCheckoutPojo {

    private boolean status = true;
    private FoCartPojo cart;
    private List<FoDeliveryMethodPojo> deliveryMethods = new ArrayList<FoDeliveryMethodPojo>();
    private List<FoMessagePojo> successMessages = new ArrayList<FoMessagePojo>();
    private List<FoMessagePojo> errorMessages = new ArrayList<FoMessagePojo>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public FoCartPojo getCart() {
        return cart;
    }

    public void setCart(FoCartPojo cart) {
        this.cart = cart;
    }

    public List<FoDeliveryMethodPojo> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(List<FoDeliveryMethodPojo> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
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