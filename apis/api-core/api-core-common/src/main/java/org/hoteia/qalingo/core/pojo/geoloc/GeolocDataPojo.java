/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.geoloc;

public class GeolocDataPojo {

    private String remoteAddress;
    private String countryName;
    private String countryIsoCode;
    private String cityName;
    private Integer geoNameId;

    public GeolocDataPojo() {
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }
    
    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public String getCountryIsoCode() {
        return countryIsoCode;
    }
    
    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public Integer getGeoNameId() {
        return geoNameId;
    }
    
    public void setGeoNameId(Integer geoNameId) {
        this.geoNameId = geoNameId;
    }
    
}