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

    private String address1;
    private String address2;
    private String addressAdditionalInformation;
    private String postalCode;
    private String city;
    private String stateCode;
    private String areaCode;
    private String countryCode;
    
    private Map<String, String> groups = new HashMap<String, String>();
    private Map<String, String> roles = new HashMap<String, String>();
    private Map<String, String> permissions = new HashMap<String, String>();
    
    private List<UserConnectionLogValueBean> userConnectionLogs = new ArrayList<UserConnectionLogValueBean>();
	
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

	public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddressAdditionalInformation() {
        return addressAdditionalInformation;
    }

    public void setAddressAdditionalInformation(String addressAdditionalInformation) {
        this.addressAdditionalInformation = addressAdditionalInformation;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<UserConnectionLogValueBean> getUserConnectionLogs() {
		return userConnectionLogs;
	}
	
	public void setUserConnectionLogs(List<UserConnectionLogValueBean> userConnectionLogs) {
		this.userConnectionLogs = userConnectionLogs;
	}
	
	public Map<String, String> getGroups() {
		return groups;
	}
	
    public boolean hasGroup(String groupCode) {
        if (groups != null 
                && !groups.isEmpty() 
                && groups.get(groupCode) != null) {
            return true;
        }
        return false;
    }
	   
	public void setGroups(Map<String, String> groups) {
		this.groups = groups;
	}
	
	public Map<String, String> getRoles() {
		return roles;
	}
	
    public boolean hasRole(String roleCode) {
        if (roles != null 
                && !roles.isEmpty() 
                && roles.get(roleCode) != null) {
            return true;
        }
        return false;
    }

	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}

	public Map<String, String> getPermissions() {
		return permissions;
	}
	
    public boolean hasPermission(String permissionCode) {
        if (permissions != null 
                && !permissions.isEmpty() 
                && permissions.get(permissionCode) != null) {
            return true;
        }
        return false;
    }
	
	public void setPermissions(Map<String, String> permissions) {
		this.permissions = permissions;
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