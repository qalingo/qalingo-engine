package org.hoteia.qalingo.core.web.bean.geoloc.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleGeoCode implements Serializable {

    @JsonProperty("results")
    private List<GoogleGeoCodeResult> results;
    
    @JsonProperty("status")
    private String status;
    
    public List<GoogleGeoCodeResult> getResults() {
        return results;
    }
    
    public void setResults(List<GoogleGeoCodeResult> results) {
        this.results = results;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getLongitude() {
        if(results != null
                && results.size() > 0){
            GoogleGeoCodeResult result = results.iterator().next();
            if(result.getGeometry() != null
                    && result.getGeometry().getLocation() != null){
                return result.getGeometry().getLocation().getLng();
            }
        }
        return null;
    }

    public String getLatitude() {
        if(results != null
                && results.size() > 0){
            GoogleGeoCodeResult result = results.iterator().next();
            if(result.getGeometry() != null
                    && result.getGeometry().getLocation() != null){
                return result.getGeometry().getLocation().getLat();
            }
        }
        return null;
    }

}