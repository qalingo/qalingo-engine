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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.CustomerNetworkOrigin;
import org.hoteia.qalingo.core.domain.enumtype.CustomerPlatformOrigin;

@Entity
@Table(name="TECO_CUSTOMER", uniqueConstraints = {@UniqueConstraint(columnNames= {"LOGIN", "CODE", "EMAIL"})})
public class Customer extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6596549095870442990L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "AVATAR_IMG")
    private String avatarImg;

    @Column(name = "PERMALINK")
    private String permalink;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DEFAULT_LOCALE")
    private String defaultLocale;

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean active;

    @Column(name = "VALIDATED", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean validated;

    @Column(name = "VALIDATION_TOKEN")
    private String validationToken;

    @Column(name = "IS_ANONYMOUS", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean anonymous;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerCredential> credentials = new HashSet<CustomerCredential>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();

    @Column(name = "DEFAULT_SHIPPING_ADDRESS")
    private Long defaultShippingAddressId;

    @Column(name = "DEFAULT_BILLING_ADDRESS")
    private Long defaultBillingAddressId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerConnectionLog> connectionLogs = new HashSet<CustomerConnectionLog>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerMarketArea> customerMarketAreas = new HashSet<CustomerMarketArea>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerAttribute> attributes = new HashSet<CustomerAttribute>(); 
	
	@ManyToMany(
	        targetEntity=org.hoteia.qalingo.core.domain.CustomerGroup.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_CUST_GROUP_REL",
	        joinColumns=@JoinColumn(name="CUSTOMER_ID"),
	        inverseJoinColumns=@JoinColumn(name="GROUP_ID")
	    )
	private Set<CustomerGroup> groups = new HashSet<CustomerGroup>(); 

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerOAuth> oauthAccesses = new HashSet<CustomerOAuth>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<CustomerPaymentInformation> paymentInformations = new HashSet<CustomerPaymentInformation>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerOrderAudit customerOrderAudit;

    @Column(name = "PLATFORM_ORIGN")
    @Enumerated(EnumType.STRING)
    private CustomerPlatformOrigin platformOrigin;

    @Column(name = "NETWORK_ORIGN")
    @Enumerated(EnumType.STRING)
    private CustomerNetworkOrigin networkOrigin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;
	
	public Customer() {
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
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getAvatarImg() {
	    return avatarImg;
    }
	
	public void setAvatarImg(String avatarImg) {
	    this.avatarImg = avatarImg;
    }
	
	public String getPermalink() {
	    return permalink;
    }
	
	public void setPermalink(String permalink) {
	    this.permalink = permalink;
    }
	
	public String getGender() {
	    return gender;
    }
	
	public void setGender(String gender) {
	    this.gender = gender;
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
	
	public String getDefaultLocale() {
		return defaultLocale;
	}
	
	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isValidated() {
    	return validated;
    }

	public void setValidated(boolean validated) {
    	this.validated = validated;
    }
	
	public String getValidationToken() {
	    return validationToken;
    }
	
	public void setValidationToken(String validationToken) {
	    this.validationToken = validationToken;
    }

	public boolean isAnonymous() {
    	return anonymous;
    }

	public void setAnonymous(boolean anonymous) {
    	this.anonymous = anonymous;
    }

	public Set<CustomerCredential> getCredentials() {
	    return credentials;
    }
	
	public CustomerCredential getCurrentCredential() {
		if(credentials != null
		        && Hibernate.isInitialized(credentials)){
			List<CustomerCredential> sortedObjects = new LinkedList<CustomerCredential>(credentials);
			Collections.sort(sortedObjects, new Comparator<CustomerCredential>() {
				@Override
				public int compare(CustomerCredential o1, CustomerCredential o2) {
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
			return sortedObjects.get(0);
		}
	    return null;
    }
	
	public void setCredentials(Set<CustomerCredential> credentials) {
	    this.credentials = credentials;
    }
	
	public Set<CustomerAddress> getAddresses() {
		return addresses;
	}
	
	public CustomerAddress getAddress(final Long customerAddressId) {
		CustomerAddress customerAddressToReturn = null;
		if(addresses != null
                && Hibernate.isInitialized(addresses)){
	        for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
	            CustomerAddress customerAddress = (CustomerAddress) iterator.next();
	            if(customerAddress.getId().equals(customerAddressId)) {
	                customerAddressToReturn = customerAddress;
	            }
	        }
		}
		return customerAddressToReturn;
	}
	
	public void setAddresses(Set<CustomerAddress> addresses) {
		this.addresses = addresses;
	}
	
	public Long getDefaultShippingAddressId() {
        if(defaultShippingAddressId != null){
            return defaultShippingAddressId;
        } else {
            if(addresses != null
                    && Hibernate.isInitialized(addresses)){
                return getAddresses().iterator().next().getId();
            }
        }
        return null;
    }
	
	public void setDefaultShippingAddressId(Long defaultShippingAddressId) {
        this.defaultShippingAddressId = defaultShippingAddressId;
    }
	
	public Long getDefaultBillingAddressId() {
	    if(defaultBillingAddressId != null){
	        return defaultBillingAddressId;
	    } else {
            if(addresses != null
                    && Hibernate.isInitialized(addresses)){
	            return getAddresses().iterator().next().getId();
	        }
	    }
	    return null;
    }
	
	public void setDefaultBillingAddressId(Long defaultBillingAddressId) {
        this.defaultBillingAddressId = defaultBillingAddressId;
    }
	
	public Set<CustomerConnectionLog> getConnectionLogs() {
		return connectionLogs;
	}
	
	public void setConnectionLogs(Set<CustomerConnectionLog> connectionLogs) {
		this.connectionLogs = connectionLogs;
	}
	
	public Set<CustomerMarketArea> getCustomerMarketAreas() {
		return customerMarketAreas;
	}
	
	public CustomerMarketArea getCurrentCustomerMarketArea(Long marketAreaId) {
		CustomerMarketArea currentCustomerMarketArea = null;
		if(customerMarketAreas != null
		        && Hibernate.isInitialized(customerMarketAreas)) {
			for (Iterator<CustomerMarketArea> iterator = customerMarketAreas.iterator(); iterator.hasNext();) {
				CustomerMarketArea customerMarketArea = (CustomerMarketArea) iterator.next();
				if(customerMarketArea.getMarketAreaId().equals(marketAreaId)) {
					currentCustomerMarketArea = customerMarketArea;
				}
			}
		}
		return currentCustomerMarketArea;
	}
	
	public void setCustomerMarketAreas(Set<CustomerMarketArea> customerMarketAreas) {
		this.customerMarketAreas = customerMarketAreas;
	}
	
	public Set<CustomerAttribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Set<CustomerAttribute> attributes) {
		this.attributes = attributes;
	}
	
    public Set<CustomerGroup> getGroups() {
        return groups;
    }
	
	public void setGroups(Set<CustomerGroup> groups) {
		this.groups = groups;
	}
	
	public Set<CustomerOAuth> getOauthAccesses() {
	    return oauthAccesses;
    }
	
	public void setOauthAccesses(Set<CustomerOAuth> oauthAccesses) {
	    this.oauthAccesses = oauthAccesses;
    }
	
	public Set<CustomerPaymentInformation> getPaymentInformations() {
        return paymentInformations;
    }
	
	public void setPaymentInformations(Set<CustomerPaymentInformation> paymentInformations) {
        this.paymentInformations = paymentInformations;
    }
	
	public CustomerOrderAudit getCustomerOrderAudit() {
	    return customerOrderAudit;
    }
	
	public void setCustomerOrderAudit(CustomerOrderAudit customerOrderAudit) {
	    this.customerOrderAudit = customerOrderAudit;
    }
	
	public CustomerPlatformOrigin getPlatformOrigin() {
    	return platformOrigin;
    }

	public void setPlatformOrigin(CustomerPlatformOrigin platformOrigin) {
    	this.platformOrigin = platformOrigin;
    }

	public CustomerNetworkOrigin getNetworkOrigin() {
    	return networkOrigin;
    }

	public void setNetworkOrigin(CustomerNetworkOrigin networkOrigin) {
    	this.networkOrigin = networkOrigin;
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
	
	public List<CustomerRole> getRoles() {
		List<CustomerRole> roles = null;
		Set<CustomerGroup> customerGroups = getGroups();
		if(customerGroups != null
		        && Hibernate.isInitialized(customerGroups)){
	        roles = new ArrayList<CustomerRole>();
	        Iterator<CustomerGroup> it = customerGroups.iterator();
	        while (it.hasNext()) {
	            CustomerGroup customerGroup = (CustomerGroup) it.next();
	            roles.addAll(customerGroup.getRoles());
	        }
		}
		return roles;
	}
	
	// Attributes
	
	public CustomerAttribute getCustomerAttribute(String attributeCode) {
		return getCustomerAttribute(attributeCode, null, null);
	}
	
	public CustomerAttribute getCustomerAttribute(String attributeCode, String localizationCode) {
		return getCustomerAttribute(attributeCode, null, localizationCode);
	}
	
	public CustomerAttribute getCustomerAttribute(String attributeCode, Long marketAreaId) {
		return getCustomerAttribute(attributeCode, marketAreaId, null);
	}
	
	public CustomerAttribute getCustomerAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		CustomerAttribute customerAttributeToReturn = null;
		if(attributes != null
		        && Hibernate.isInitialized(attributes)) {
			List<CustomerAttribute> customerAttributesFilter = new ArrayList<CustomerAttribute>();
			for (Iterator<CustomerAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
				CustomerAttribute customerAttribute = (CustomerAttribute) iterator.next();
				AttributeDefinition attributeDefinition = customerAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					customerAttributesFilter.add(customerAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<CustomerAttribute> iterator = customerAttributesFilter.iterator(); iterator.hasNext();) {
					CustomerAttribute customerAttribute = (CustomerAttribute) iterator.next();
					AttributeDefinition attributeDefinition = customerAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(customerAttribute.getMarketAreaId() != null
								&& BooleanUtils.negate(customerAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
				if(customerAttributesFilter.size() == 0){
					// TODO : throw error ?
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<CustomerAttribute> iterator = customerAttributesFilter.iterator(); iterator.hasNext();) {
					CustomerAttribute customerAttribute = (CustomerAttribute) iterator.next();
					AttributeDefinition attributeDefinition = customerAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = customerAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode)
								&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
				if(customerAttributesFilter.size() == 0){
					// TODO : throw error ?
					
					for (Iterator<CustomerAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
						CustomerAttribute customerAttribute = (CustomerAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(customerAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)){
							customerAttributeToReturn = customerAttribute;
						}
					}
					
				}
			}
			if(customerAttributesFilter.size() == 1){
				customerAttributeToReturn = customerAttributesFilter.get(0);
			} else {
				// TODO : throw error ?
			}
		}
		return customerAttributeToReturn;
	}
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
		CustomerAttribute customerAttribute = getCustomerAttribute(attributeCode, marketAreaId, localizationCode);
		if(customerAttribute != null) {
			return customerAttribute.getValue();
		}
		return null;
	}
	
	public String getScreenName() {
		return (String) getValue(CustomerAttribute.CUSTOMER_ATTRIBUTE_SCREENAME, null, null);
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Customer other = (Customer) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
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
        return "Customer [id=" + id + ", version=" + version + ", code=" + code + ", login=" + login + ", avatarImg=" + avatarImg + ", permalink=" + permalink + ", gender=" + gender + ", title="
                + title + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", defaultLocale=" + defaultLocale + ", active=" + active
                + ", validated=" + validated + ", validationToken=" + validationToken + ", anonymous=" + anonymous + ", defaultShippingAddressId=" + defaultShippingAddressId
                + ", defaultBillingAddressId=" + defaultBillingAddressId + ", platformOrigin=" + platformOrigin + ", networkOrigin=" + networkOrigin + ", dateCreate=" + dateCreate + ", dateUpdate="
                + dateUpdate + "]";
    }


}