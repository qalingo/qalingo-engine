/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailsViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1826858352678981858L;
	
	private Long id;
	private String login;
	private String loginLabel;
	private String firstname;
	private String firstnameLabel;
	private String lastname;
	private String lastnameLabel;
	private String email;
	private String emailLabel;
	private String password;
	private String passwordLabel;
	private boolean active;
	private String activeLabel;
	private String activeValueLabel;
	private String dateCreate;
	private String dateCreateLabel;
	private String dateUpdate;
	private String dateUpdateLabel;

	protected List<UserConnectionLogValueBean> userConnectionLogs = new ArrayList<UserConnectionLogValueBean>();
	protected Map<String, String> userGroups = new HashMap<String, String>();
	protected Map<String, String> userRoles = new HashMap<String, String>();
	protected Map<String, String> userPermissions = new HashMap<String, String>();
	
	private String userConnectionLogLabel;
	private String userGroupsLabel;
	private String userRolesLabel;
	private String userPermissionsLabel;
	
	private String tableDateLabel;
	private String tableHostLabel;
	private String tableAddressLabel;
	private String tableCodeLabel;
	private String tableNameLabel;
	
	protected String backUrl;
	protected String backLabel;
	protected String userDetailsUrl;
	protected String userDetailsLabel;
	protected String userEditUrl;
	protected String userEditLabel;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLoginLabel() {
		return loginLabel;
	}

	public void setLoginLabel(String loginLabel) {
		this.loginLabel = loginLabel;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstnameLabel() {
		return firstnameLabel;
	}

	public void setFirstnameLabel(String firstnameLabel) {
		this.firstnameLabel = firstnameLabel;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastnameLabel() {
		return lastnameLabel;
	}

	public void setLastnameLabel(String lastnameLabel) {
		this.lastnameLabel = lastnameLabel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailLabel() {
		return emailLabel;
	}

	public void setEmailLabel(String emailLabel) {
		this.emailLabel = emailLabel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordLabel() {
		return passwordLabel;
	}

	public void setPasswordLabel(String passwordLabel) {
		this.passwordLabel = passwordLabel;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getActiveLabel() {
		return activeLabel;
	}

	public void setActiveLabel(String activeLabel) {
		this.activeLabel = activeLabel;
	}

	public String getActiveValueLabel() {
		return activeValueLabel;
	}
	
	public void setActiveValueLabel(String activeValueLabel) {
		this.activeValueLabel = activeValueLabel;
	}
	
	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getDateCreateLabel() {
		return dateCreateLabel;
	}

	public void setDateCreateLabel(String dateCreateLabel) {
		this.dateCreateLabel = dateCreateLabel;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getDateUpdateLabel() {
		return dateUpdateLabel;
	}

	public void setDateUpdateLabel(String dateUpdateLabel) {
		this.dateUpdateLabel = dateUpdateLabel;
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
	
	public String getUserGroupsLabel() {
		return userGroupsLabel;
	}

	public String getUserConnectionLogLabel() {
		return userConnectionLogLabel;
	}

	public void setUserConnectionLogLabel(String userConnectionLogLabel) {
		this.userConnectionLogLabel = userConnectionLogLabel;
	}
	
	public void setUserGroupsLabel(String userGroupsLabel) {
		this.userGroupsLabel = userGroupsLabel;
	}

	public String getUserRolesLabel() {
		return userRolesLabel;
	}

	public void setUserRolesLabel(String userRolesLabel) {
		this.userRolesLabel = userRolesLabel;
	}

	public String getUserPermissionsLabel() {
		return userPermissionsLabel;
	}

	public void setUserPermissionsLabel(String userPermissionsLabel) {
		this.userPermissionsLabel = userPermissionsLabel;
	}

	public String getTableDateLabel() {
		return tableDateLabel;
	}

	public void setTableDateLabel(String tableDateLabel) {
		this.tableDateLabel = tableDateLabel;
	}

	public String getTableHostLabel() {
		return tableHostLabel;
	}

	public void setTableHostLabel(String tableHostLabel) {
		this.tableHostLabel = tableHostLabel;
	}

	public String getTableAddressLabel() {
		return tableAddressLabel;
	}
	
	public void setTableAddressLabel(String tableAddressLabel) {
		this.tableAddressLabel = tableAddressLabel;
	}
	
	public String getTableCodeLabel() {
		return tableCodeLabel;
	}

	public void setTableCodeLabel(String tableCodeLabel) {
		this.tableCodeLabel = tableCodeLabel;
	}

	public String getTableNameLabel() {
		return tableNameLabel;
	}

	public void setTableNameLabel(String tableNameLabel) {
		this.tableNameLabel = tableNameLabel;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getBackLabel() {
		return backLabel;
	}

	public void setBackLabel(String backLabel) {
		this.backLabel = backLabel;
	}

	public String getUserDetailsUrl() {
		return userDetailsUrl;
	}

	public void setUserDetailsUrl(String userDetailsUrl) {
		this.userDetailsUrl = userDetailsUrl;
	}

	public String getUserDetailsLabel() {
		return userDetailsLabel;
	}

	public void setUserDetailsLabel(String userDetailsLabel) {
		this.userDetailsLabel = userDetailsLabel;
	}

	public String getUserEditUrl() {
		return userEditUrl;
	}

	public void setUserEditUrl(String userEditUrl) {
		this.userEditUrl = userEditUrl;
	}

	public String getUserEditLabel() {
		return userEditLabel;
	}

	public void setUserEditLabel(String userEditLabel) {
		this.userEditLabel = userEditLabel;
	}
	
}
