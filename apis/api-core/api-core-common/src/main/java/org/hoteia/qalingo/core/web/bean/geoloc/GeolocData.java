/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.bean.geoloc;

import java.io.Serializable;

import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;

public class GeolocData implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -4664799447578348774L;
    
    private String remoteAddress;
    private Country country;
    private City city;

    public GeolocData() {
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }
    
    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
    
    public Country getCountry() {
        return country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }
    
    public City getCity() {
        return city;
    }
    
    public void setCity(City city) {
        this.city = city;
    }
    
}