/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TBO_MENU")
public class Menu implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 4941716986199595500L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="NAME")
	private String name;

	@Column(name="POSITION")
	private int position;

	@Column(name="IS_ACTIVE", nullable=false, columnDefinition="tinyint(1) default 1")
	private boolean active;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="MENU_ID")
	@OrderBy("POSITION") 
	private Set<Menu> subMenus = new HashSet<Menu>(); 
	
	@ManyToMany(
			fetch = FetchType.LAZY,
	        targetEntity=org.hoteia.qalingo.core.domain.UserGroup.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TBO_MENU_GROUP_REL",
	        joinColumns=@JoinColumn(name="MENU_ID"),
	        inverseJoinColumns=@JoinColumn(name="GROUP_ID")
	    )
	private Set<UserGroup> userGroups = new HashSet<UserGroup>(); 
	
	@ManyToMany(
			fetch = FetchType.LAZY,
	        targetEntity=org.hoteia.qalingo.core.domain.UserRole.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TBO_MENU_ROLE_REL",
	        joinColumns=@JoinColumn(name="MENU_ID"),
	        inverseJoinColumns=@JoinColumn(name="ROLE_ID")
	    )	
	private Set<UserRole> groupRoles = new HashSet<UserRole>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public Menu(){
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

	public String getCode() {
    	return code;
    }

	public void setCode(String code) {
    	this.code = code;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public int getPosition() {
    	return position;
    }

	public void setPosition(int position) {
    	this.position = position;
    }

	public boolean isActive() {
    	return active;
    }

	public void setActive(boolean active) {
    	this.active = active;
    }

	public Set<Menu> getSubMenus() {
    	return subMenus;
    }

	public void setSubMenus(Set<Menu> subMenus) {
    	this.subMenus = subMenus;
    }

	public Set<UserGroup> getUserGroups() {
    	return userGroups;
    }

	public void setUserGroups(Set<UserGroup> userGroups) {
    	this.userGroups = userGroups;
    }

	public Set<UserRole> getGroupRoles() {
    	return groupRoles;
    }

	public void setGroupRoles(Set<UserRole> groupRoles) {
    	this.groupRoles = groupRoles;
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

}