/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.bean.clickstream;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class ClickstreamRequest implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 6172522785730511477L;
    
    private String protocol;
    private String serverName;
    private int serverPort;
    private String requestURI;
    private String queryString;
    private String remoteUser;
    private Date timestamp;

    public ClickstreamRequest(HttpServletRequest request, Date timestamp) {
        protocol = request.getProtocol();
        serverName = request.getServerName();
        serverPort = request.getServerPort();
        requestURI = request.getRequestURI();
        queryString = request.getQueryString();
        remoteUser = request.getRemoteUser();
        this.timestamp = timestamp;
    }

    public String getProtocol() {
        return protocol;
    }
    
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
}