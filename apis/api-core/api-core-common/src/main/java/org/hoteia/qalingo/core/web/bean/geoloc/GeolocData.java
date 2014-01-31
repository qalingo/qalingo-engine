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