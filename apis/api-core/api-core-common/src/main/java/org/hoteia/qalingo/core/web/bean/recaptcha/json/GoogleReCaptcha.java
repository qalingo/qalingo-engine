package org.hoteia.qalingo.core.web.bean.recaptcha.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleReCaptcha implements Serializable {

    @JsonProperty("status")
    private String status;

    @JsonProperty("error_message")
    private List<String> errorMessage;
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<String> getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}