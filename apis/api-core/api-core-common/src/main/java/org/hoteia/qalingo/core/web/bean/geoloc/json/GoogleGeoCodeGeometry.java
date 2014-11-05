package org.hoteia.qalingo.core.web.bean.geoloc.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleGeoCodeGeometry {

    @JsonProperty("location")
    private GoogleGeoCodeGeometryLocation location;

    @JsonProperty("location_type")
    private String locationType;
    
    public GoogleGeoCodeGeometryLocation getLocation() {
        return location;
    }
    
    public void setLocation(GoogleGeoCodeGeometryLocation location) {
        this.location = location;
    }
    
    public String getLocationType() {
        return locationType;
    }
    
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
    
}
