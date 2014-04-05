/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1826858352678981858L;
	
	private Long id;
    private String code;
	private String login;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private boolean active;

	private List<UserConnectionLogValueBean> userConnectionLogs = new ArrayList<UserConnectionLogValueBean>();
	private Map<String, String> userGroups = new HashMap<String, String>();
	private Map<String, String> userRoles = new HashMap<String, String>();
	private Map<String, String> userPermissions = new HashMap<String, String>();
	
	private String backUrl;
	private String userDetailsUrl;
	private String userEditUrl;

    private String personalDetailsUrl;
	private String personalEditUrl;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
        return code;
    }
	
	public void setCode(String code) {
        this.code = code;
    }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<UserConnectionLogValueBean> getUserConnectionLogs() {
		return userConnectionLogs;
	}
	
	public void setUserConnectionLogs(List<UserConnectionLogValueBean> userConnectionLogs) {
		this.userConnectionLogs = userConnectionLogs;
	}
	
	public Map<String, String> getUserGroups() {
		return userGroups;
	}
	
	public void setUserGroups(Map<String, String> userGroups) {
		this.userGroups = userGroups;
	}
	
	public Map<String, String> getUserRoles() {
		return userRoles;
	}
	
	public void setUserRoles(Map<String, String> userRoles) {
		this.userRoles = userRoles;
	}

	public Map<String, String> getUserPermissions() {
		return userPermissions;
	}
	
	public void setUserPermissions(Map<String, String> userPermissions) {
		this.userPermissions = userPermissions;
	}
	
	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getUserDetailsUrl() {
		return userDetailsUrl;
	}

	public void setUserDetailsUrl(String userDetailsUrl) {
		this.userDetailsUrl = userDetailsUrl;
	}

	public String getUserEditUrl() {
		return userEditUrl;
	}

	public void setUserEditUrl(String userEditUrl) {
		this.userEditUrl = userEditUrl;
	}

    public String getPersonalDetailsUrl() {
        return personalDetailsUrl;
    }

    public void setPersonalDetailsUrl(String personalDetailsUrl) {
        this.personalDetailsUrl = personalDetailsUrl;
    }

    public String getPersonalEditUrl() {
        return personalEditUrl;
    }

    public void setPersonalEditUrl(String personalEditUrl) {
        this.personalEditUrl = personalEditUrl;
    }
	
}