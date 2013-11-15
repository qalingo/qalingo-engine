package org.hoteia.qalingo.core.jms.cacheserveurmanagement.listener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractMessageJms {

    @JacksonXmlProperty(localName="EnvironmentName")
    private String environmentName;

    @JacksonXmlProperty(localName="EnvironmentId")
    private String environmentId;

    @JacksonXmlProperty(localName="ApplicationName")
    private String applicationName;

    @JacksonXmlProperty(localName="ServerName")
    private String serverName;
    
    @JacksonXmlProperty(localName="ServerIp")
    private String serverIp;

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
    
    
}
