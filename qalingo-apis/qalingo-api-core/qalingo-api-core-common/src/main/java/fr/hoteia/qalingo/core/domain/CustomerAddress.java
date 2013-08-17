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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TECO_CUSTOMER_ADDRESS")
public class CustomerAddress extends AbstractAddress implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2501911341288490523L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="ADDRESS_NAME")
    private String addressName;
	
	@Column(name="TITLE")
    private String title;
	
	@Column(name="LASTNAME")
    private String lastname;
	
	@Column(name="FIRSTNAME")
    private String firstname;
    
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="ADDRESS1")
    private String address1;
	
	@Column(name="ADDRESS2")
    private String address2;
	
	@Column(name="ADDITIONAL_INFORMATION")
    private String addressAdditionalInformation;
	
	@Column(name="POSTAL_CODE")
    private String postalCode;
	
	@Column(name="CITY")
    private String city;

	@Column(name="STATE_CODE")
    private String stateCode;

	@Column(name="AREA_CODE")
    private String areaCode;

	@Column(name="COUNTRY_CODE")
    private String countryCode;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	@Column(name="IS_DEFAULT_BILLING", nullable=false, columnDefinition="tinyint(1) default 1")
	private boolean isDefaultBilling;
	
	@Column(name="IS_DEFAULT_SHIPPING", nullable=false, columnDefinition="tinyint(1) default 1")
	private boolean isDefaultShipping;
	
	@Column(name="LONGITUDE")
	private String longitude;
	
	@Column(name="LATITUDE")
	private String latitude;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public CustomerAddress() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddressName() {
		return addressName;
	}
	
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
    public String getTitle() {
		return title;
	}
    
    public void setTitle(String title) {
		this.title = title;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getVersion() {
    	return version;
    }

	public void setVersion(int version) {
    	this.version = version;
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

	public boolean isDefault() {
    	return isDefault;
    }

	public void setDefault(boolean isDefault) {
    	this.isDefault = isDefault;
    }
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public boolean isDefaultBilling() {
		return isDefaultBilling;
	}

	public void setDefaultBilling(boolean isDefaultBilling) {
		this.isDefaultBilling = isDefaultBilling;
	}

	public boolean isDefaultShipping() {
		return isDefaultShipping;
	}

	public void setDefaultShipping(boolean isDefaultShipping) {
		this.isDefaultShipping = isDefaultShipping;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
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
	    result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
	    result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
	    result = prime * result + ((addressAdditionalInformation == null) ? 0 : addressAdditionalInformation.hashCode());
	    result = prime * result + ((addressName == null) ? 0 : addressName.hashCode());
	    result = prime * result + ((areaCode == null) ? 0 : areaCode.hashCode());
	    result = prime * result + ((city == null) ? 0 : city.hashCode());
	    result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
	    result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
	    result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
	    result = prime * result + ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
	    result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + (isDefault ? 1231 : 1237);
	    result = prime * result + (isDefaultBilling ? 1231 : 1237);
	    result = prime * result + (isDefaultShipping ? 1231 : 1237);
	    result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
	    result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
	    result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
	    result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
	    result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
	    result = prime * result + ((title == null) ? 0 : title.hashCode());
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
	    CustomerAddress other = (CustomerAddress) obj;
	    if (address1 == null) {
		    if (other.address1 != null)
			    return false;
	    } else if (!address1.equals(other.address1))
		    return false;
	    if (address2 == null) {
		    if (other.address2 != null)
			    return false;
	    } else if (!address2.equals(other.address2))
		    return false;
	    if (addressAdditionalInformation == null) {
		    if (other.addressAdditionalInformation != null)
			    return false;
	    } else if (!addressAdditionalInformation.equals(other.addressAdditionalInformation))
		    return false;
	    if (addressName == null) {
		    if (other.addressName != null)
			    return false;
	    } else if (!addressName.equals(other.addressName))
		    return false;
	    if (areaCode == null) {
		    if (other.areaCode != null)
			    return false;
	    } else if (!areaCode.equals(other.areaCode))
		    return false;
	    if (city == null) {
		    if (other.city != null)
			    return false;
	    } else if (!city.equals(other.city))
		    return false;
	    if (countryCode == null) {
		    if (other.countryCode != null)
			    return false;
	    } else if (!countryCode.equals(other.countryCode))
		    return false;
	    if (customerId == null) {
		    if (other.customerId != null)
			    return false;
	    } else if (!customerId.equals(other.customerId))
		    return false;
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
	    if (isDefault != other.isDefault)
		    return false;
	    if (isDefaultBilling != other.isDefaultBilling)
		    return false;
	    if (isDefaultShipping != other.isDefaultShipping)
		    return false;
	    if (lastname == null) {
		    if (other.lastname != null)
			    return false;
	    } else if (!lastname.equals(other.lastname))
		    return false;
	    if (latitude == null) {
		    if (other.latitude != null)
			    return false;
	    } else if (!latitude.equals(other.latitude))
		    return false;
	    if (longitude == null) {
		    if (other.longitude != null)
			    return false;
	    } else if (!longitude.equals(other.longitude))
		    return false;
	    if (postalCode == null) {
		    if (other.postalCode != null)
			    return false;
	    } else if (!postalCode.equals(other.postalCode))
		    return false;
	    if (stateCode == null) {
		    if (other.stateCode != null)
			    return false;
	    } else if (!stateCode.equals(other.stateCode))
		    return false;
	    if (title == null) {
		    if (other.title != null)
			    return false;
	    } else if (!title.equals(other.title))
		    return false;
	    if (version != other.version)
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "CustomerAddress [id=" + id + ", addressName=" + addressName + ", title=" + title + ", lastname=" + lastname + ", firstname=" + firstname + ", version=" + version + ", address1="
	            + address1 + ", address2=" + address2 + ", addressAdditionalInformation=" + addressAdditionalInformation + ", postalCode=" + postalCode + ", city=" + city + ", stateCode=" + stateCode
	            + ", areaCode=" + areaCode + ", countryCode=" + countryCode + ", isDefault=" + isDefault + ", customerId=" + customerId + ", isDefaultBilling=" + isDefaultBilling
	            + ", isDefaultShipping=" + isDefaultShipping + ", longitude=" + longitude + ", latitude=" + latitude + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}