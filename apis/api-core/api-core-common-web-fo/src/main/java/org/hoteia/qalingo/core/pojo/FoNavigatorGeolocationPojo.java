/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.cart.FoMessagePojo;

public class FoNavigatorGeolocationPojo {

    private boolean status = true;
    
    private String latitude;
    private String longitude;

    private String city;
    private String country;

    private List<FoMessagePojo> successMessages = new ArrayList<FoMessagePojo>();
    private List<FoMessagePojo> errorMessages = new ArrayList<FoMessagePojo>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}