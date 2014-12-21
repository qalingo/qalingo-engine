package org.hoteia.qalingo.core.web.bean.geoloc.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleGeoCode implements Serializable {

    @JsonProperty("success")
    private String success;

    @JsonProperty("error-codes")
    private List<String> errorCodes;
    
    public String getSuccess() {
        return success;
    }
    
    public void setSuccess(String success) {
        this.success = success;
    }
    
    public List<String> getErrorCodes() {
        return errorCodes;
    }
    
    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

}