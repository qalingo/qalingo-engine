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

import org.hoteia.qalingo.core.pojo.deliverymethod.DeliveryMethodPojo;

public class FoDeliveryMethodInformationPojo {

    private String id;
    private String message;
    
    private List<DeliveryMethodPojo> availableDeliveryMethods = new ArrayList<DeliveryMethodPojo>();

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<DeliveryMethodPojo> getAvailableDeliveryMethods() {
        return availableDeliveryMethods;
    }
    
    public void setAvailableDeliveryMethods(List<DeliveryMethodPojo> availableDeliveryMethods) {
        this.availableDeliveryMethods = availableDeliveryMethods;
    }
    
}