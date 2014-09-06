package org.hoteia.qalingo.core.pojo;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPojoResponse {

    private boolean success = true;
    
    private List<ReponseMessagePojo> successMessages = new ArrayList<ReponseMessagePojo>();
    private List<ReponseMessagePojo> errorMessages = new ArrayList<ReponseMessagePojo>();
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public List<ReponseMessagePojo> getSuccessMessages() {
        return successMessages;
    }
    
    public void setSuccessMessages(List<ReponseMessagePojo> successMessages) {
        this.successMessages = successMessages;
    }
    
    public List<ReponseMessagePojo> getErrorMessages() {
        return errorMessages;
    }
    
    public void setErrorMessages(List<ReponseMessagePojo> errorMessages) {
        this.errorMessages = errorMessages;
    }
    
}