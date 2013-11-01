package fr.hoteia.qalingo.core.web.clickstream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ClickstreamSession implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -353480945411897928L;
    
    private Date lastRequest;
    private String hostname;
    private String initialReferrer;
    private boolean isBot;
    
    private List<ClickstreamRequest> requests = Collections.synchronizedList(new ArrayList());

    public ClickstreamSession() {
    }
    
    public Date getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(Date lastRequest) {
        this.lastRequest = lastRequest;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getInitialReferrer() {
        return initialReferrer;
    }
    
    public void setInitialReferrer(String initialReferrer) {
        this.initialReferrer = initialReferrer;
    }
    
    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean isBot) {
        this.isBot = isBot;
    }

    public List<ClickstreamRequest> getRequests() {
        return requests;
    }
    
    public void setRequests(List<ClickstreamRequest> requests) {
        this.requests = requests;
    }
    
}