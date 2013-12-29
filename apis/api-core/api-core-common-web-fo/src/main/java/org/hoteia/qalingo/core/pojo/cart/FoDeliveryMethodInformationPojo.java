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