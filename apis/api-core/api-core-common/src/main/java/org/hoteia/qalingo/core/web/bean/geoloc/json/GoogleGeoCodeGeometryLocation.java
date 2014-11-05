package org.hoteia.qalingo.core.web.bean.geoloc.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleGeoCodeGeometryLocation {

    @JsonProperty("lat")
    private String lat;
    
    @JsonProperty("lng")
    private String lng;
    
    public String getLat() {
        return lat;
    }
    
    public void setLat(String lat) {
        this.lat = lat;
    }
    
    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
