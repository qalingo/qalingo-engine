/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class UserConnectionLogValueBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6316591620404013343L;
	
	String date;
	String host;
	String publicAddress;
    String privateAddress;
    String app;
	
	public UserConnectionLogValueBean(){
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getPublicAddress() {
        return publicAddress;
    }
	
	public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }
	
	public String getPrivateAddress() {
        return privateAddress;
    }
	
	public void setPrivateAddress(String privateAddress) {
        this.privateAddress = privateAddress;
    }
	
	public String getApp() {
        return app;
    }
	
	public void setApp(String app) {
        this.app = app;
    }
	
}