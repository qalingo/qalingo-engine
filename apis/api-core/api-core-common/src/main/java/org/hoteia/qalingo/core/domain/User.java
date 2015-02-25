/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.Hibernate;

@Entity
@Table(name = "TBO_USER")
public class User extends AbstractEntity<User> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 5584130360546711677L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;
    
    @Column(name = "LOGIN",unique = true)
    private String login;

    @Column(name = "TITLE")
    private String title;
    
    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean active;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "ADDITIONAL_INFORMATION")
    private String addressAdditionalInformation;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE_CODE")
    private String stateCode;

    @Column(name = "AREA_CODE")
    private String areaCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_LOCALIZATION_ID", insertable = true, updatable = true)
    private Localization defaultLocalization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID", insertable = true, updatable = true)
    private Company company;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.UserCredential.class)
    @JoinColumn(name = "USER_ID")
    private Set<UserCredential> credentials = new HashSet<UserCredential>();
    
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.UserGroup.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TBO_USER_GROUP_REL", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    private Set<UserGroup> groups = new HashSet<UserGroup>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private Set<UserConnectionLog> connectionLogs = new HashSet<UserConnectionLog>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public User() {
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
        this.code = code.replaceAll(" ", "");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
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

    public Set<UserCredential> getCredentials() {
        return credentials;
    }
    
    public UserCredential getCurrentCredential() {
        if(credentials != null
                && Hibernate.isInitialized(credentials)){
            List<UserCredential> sortedObjects = new LinkedList<UserCredential>(credentials);
            Collections.sort(sortedObjects, new Comparator<UserCredential>() {
                @Override
                public int compare(UserCredential o1, UserCredential o2) {
                    if(o1 != null
                            && o2 != null){
                        Date order1 = o1.getDateCreate();
                        Date order2 = o2.getDateCreate();
                        if(order1 != null
                                && order2 != null){
                            return order1.compareTo(order2);                
                        } else {
                            return o1.getId().compareTo(o2.getId());    
                        }
                    }
                    return 0;
                }
            });
            if(sortedObjects != null && sortedObjects.size() > 0){
                return sortedObjects.get(0);
            }
        }
        return null;
    }
    
    public void setCredentials(Set<UserCredential> credentials) {
        this.credentials = credentials;
    }
    
    public Set<UserGroup> getGroups() {
        return groups;
    }

    public boolean hasGroup(String groupCode) {
        if(groups != null 
                && Hibernate.isInitialized(groups)){
            for (Iterator<UserGroup> iterator = groups.iterator(); iterator.hasNext();) {
                UserGroup group = (UserGroup) iterator.next();
                if(group.getCode().equalsIgnoreCase(groupCode)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean hasRole(String roleCode) {
        if(groups != null 
                && Hibernate.isInitialized(groups)){
            for (Iterator<UserGroup> iteratorUserGroup = groups.iterator(); iteratorUserGroup.hasNext();) {
                UserGroup group = (UserGroup) iteratorUserGroup.next();
                for (Iterator<UserRole> iteratorUserRole = group.getRoles().iterator(); iteratorUserRole.hasNext();) {
                    UserRole role = (UserRole) iteratorUserRole.next();
                    if (role.getCode().equalsIgnoreCase(roleCode)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void setGroups(Set<UserGroup> groups) {
        this.groups = groups;
    }

    public List<UserRole> getRoles() {
        List<UserRole> roles = null;
        if(groups != null 
                && Hibernate.isInitialized(groups)){
            roles = new ArrayList<UserRole>();
            for (Iterator<UserGroup> iterator = groups.iterator(); iterator.hasNext();) {
                UserGroup group = (UserGroup) iterator.next();
                roles.addAll(group.getRoles());
            }
        }
        return roles;
    }

    public List<UserPermission> getPermissions() {
        List<UserPermission> permission = null;
        if(groups != null 
                && Hibernate.isInitialized(groups)){
            permission = new ArrayList<UserPermission>();
            for (Iterator<UserGroup> iteratorUserGroup = groups.iterator(); iteratorUserGroup.hasNext();) {
                UserGroup group = (UserGroup) iteratorUserGroup.next();
                for (Iterator<UserRole> iteratorUserRole = group.getRoles().iterator(); iteratorUserRole.hasNext();) {
                    UserRole role = (UserRole) iteratorUserRole.next();
                    permission.addAll(role.getPermissions());
                }
            }
        }
        return permission;
    }

    public Set<UserConnectionLog> getConnectionLogs() {
        return connectionLogs;
    }

    public List<UserConnectionLog> getSortedConnectionLogs() {
        List<UserConnectionLog> sortedConnectionLogs = null;
        if (connectionLogs != null 
                && Hibernate.isInitialized(connectionLogs)) {
            sortedConnectionLogs = new LinkedList<UserConnectionLog>(connectionLogs);
            Collections.sort(sortedConnectionLogs, new Comparator<UserConnectionLog>() {
                @Override
                public int compare(UserConnectionLog o1, UserConnectionLog o2) {
                    if (o1 != null && o1.getLoginDate() != null && o2 != null && o2.getLoginDate() != null) {
                        return o1.getLoginDate().compareTo(o2.getLoginDate());
                    }
                    return 0;
                }
            });
        }
        return sortedConnectionLogs;
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
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
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
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", version=" + version + ", code=" + code + ", login=" + login + ", title=" + title + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
                + ", password=" + password + ", active=" + active + ", defaultLocalization=" + defaultLocalization + ", company=" + company + ", userGroups=" + groups + ", connectionLogs="
                + connectionLogs + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}