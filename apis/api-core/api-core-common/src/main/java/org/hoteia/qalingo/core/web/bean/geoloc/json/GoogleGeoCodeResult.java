package org.hoteia.qalingo.core.web.bean.geoloc.json;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleGeoCodeResult implements Serializable {

    @JsonProperty("address_components")
    private List<GoogleGeoCodeAddressComponent> addressComponents;

    @JsonProperty("formatted_address")
    private String formattedAddress;
    
    @JsonProperty("geometry")
    private GoogleGeoCodeGeometry geometry;
    
    @JsonProperty("types")
    private List<String> types;
    
    public List<GoogleGeoCodeAddressComponent> getAddressComponents() {
        return addressComponents;
    }
    
    public void setAddressComponents(List<GoogleGeoCodeAddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }
    
    public String getFormattedAddress() {
        return formattedAddress;
    }
    
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
    
    public GoogleGeoCodeGeometry getGeometry() {
        return geometry;
    }
    
    public void setGeometry(GoogleGeoCodeGeometry geometry) {
        this.geometry = geometry;
    }
    
    public List<String> getTypes() {
        return types;
    }
    
    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getAddress(){
        return getShortValue("street_number") + " " + getLongValue("route");
    }
    
    public String getPostalCode(){
        return getShortValue("postal_code");
    }
    
    public String getCity(){
        return getLongValue("administrative_area_level_1");
    }
    
    public String getCountryCode(){
        return getShortValue("country");
    }
    
    protected String getLongValue(String type){
        for (Iterator<GoogleGeoCodeAddressComponent> iterator = addressComponents.iterator(); iterator.hasNext();) {
            GoogleGeoCodeAddressComponent googleGeoCodeAddressComponent = (GoogleGeoCodeAddressComponent) iterator.next();
            if(googleGeoCodeAddressComponent.getTypes().contains(type)){
                return googleGeoCodeAddressComponent.getLongName();
            }
        }
        return null;
    }
    
    protected String getShortValue(String type){
        for (Iterator<GoogleGeoCodeAddressComponent> iterator = addressComponents.iterator(); iterator.hasNext();) {
            GoogleGeoCodeAddressComponent googleGeoCodeAddressComponent = (GoogleGeoCodeAddressComponent) iterator.next();
            if(googleGeoCodeAddressComponent.getTypes().contains(type)){
                return googleGeoCodeAddressComponent.getShortName();
            }
        }
        return null;
    }
}
