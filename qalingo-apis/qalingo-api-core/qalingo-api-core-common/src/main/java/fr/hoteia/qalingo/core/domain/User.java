/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TBO_USER")
public class User implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5584130360546711677L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="LOGIN")
	private String login;
	
	@Column(name="FIRSTNAME")
	private String firstname;
	
	@Column(name="LASTNAME")
	private String lastname;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PASSWORD")
	private String password;

	@Column(name="IS_ACTIVE", nullable=false, columnDefinition="tinyint(1) default 1")
	private boolean active;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="DEFAULT_LOCALIZATION_ID", insertable=false, updatable=false)
	private Localization defaultLocalization;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="COMPANY_ID", insertable=false, updatable=false)
	private Company company;
	
	@ManyToMany(
			fetch = FetchType.LAZY,
	        targetEntity=fr.hoteia.qalingo.core.domain.UserGroup.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TBO_USER_GROUP_REL",
	        joinColumns=@JoinColumn(name="USER_ID"),
	        inverseJoinColumns=@JoinColumn(name="GROUP_ID")
	    )
	private Set<UserGroup> userGroups;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID")
	@OrderBy("LOGIN_DATE DESC") 
	private Set<UserConnectionLog> connectionLogs = new HashSet<UserConnectionLog>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public User(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
	
	public Localization getDefaultLocalization() {
		return defaultLocalization;
	}
	
	public void setDefaultLocalization(Localization defaultLocalization) {
		this.defaultLocalization = defaultLocalization;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }
	
	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<UserRole> getRoles(){
		List<UserRole> roles = new ArrayList<UserRole>();
		Set<UserGroup> userGroups = getUserGroups();
		Iterator<UserGroup> it = userGroups.iterator();
		while (it.hasNext()) {
			UserGroup userGroup = (UserGroup) it.next();
			roles.addAll(userGroup.getGroupRoles());
		}
		return roles;
	}
	
	public List<UserPermission> getPermissions(){
		List<UserPermission> permission = new ArrayList<UserPermission>();
		Set<UserGroup> userGroups = getUserGroups();
		Iterator<UserGroup> itUserGroup = userGroups.iterator();
		while (itUserGroup.hasNext()) {
			UserGroup userGroup = (UserGroup) itUserGroup.next();
			
			Iterator<UserRole> itUserRole = userGroup.getGroupRoles().iterator();
			while (itUserRole.hasNext()) {
				UserRole userRole = (UserRole) itUserRole.next();
				permission.addAll(userRole.getRolePermissions());
			}
		}
		return permission;
	}
	
	public Set<UserConnectionLog> getConnectionLogs() {
		return connectionLogs;
	}
	
	public void setConnectionLogs(Set<UserConnectionLog> connectionLogs) {
		this.connectionLogs = connectionLogs;
	}
	
	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result
				+ ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (dateCreate == null) {
			if (other.dateCreate != null)
				return false;
		} else if (!dateCreate.equals(other.dateCreate))
			return false;
		if (dateUpdate == null) {
			if (other.dateUpdate != null)
				return false;
		} else if (!dateUpdate.equals(other.dateUpdate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BoUser [id=" + id + ", version=" + version + ", login="
				+ login + ", firstname=" + firstname + ", lastname="
				+ lastname + ", email=" + email + ", password=" + password
				+ ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
				+ "]";
	}
	
}