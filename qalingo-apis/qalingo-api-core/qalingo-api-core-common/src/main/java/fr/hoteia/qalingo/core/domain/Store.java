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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import fr.hoteia.qalingo.core.Constants;

@Entity
@Table(name="TECO_STORE")
public class Store implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 9020657992879779713L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="CODE")
	private String code;
	
//	@ElementCollection(targetClass = StoreType.class)
//	@Enumerated(EnumType.STRING)
//	@Fetch(value = FetchMode.JOIN)
//	@CollectionTable(name = "TECO_STORE_TYPE_REL", joinColumns = @JoinColumn(name = "STORE_ID"))
//	@Column(name = "TYPE")
//	private Set<StoreType> types = new HashSet<StoreType>(); 

	@Column(name="TYPE")
	private String type;

	@Column(name="BUSINESS_NAME")
	private String businessName;
	
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
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="STORE_ID")
	private Set<StoreAttribute> storeAttributes = new HashSet<StoreAttribute>(); 
	
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

	public Store() {
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
		
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getBusinessName() {
		return businessName;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
	
	public Set<StoreAttribute> getStoreAttributes() {
		return storeAttributes;
	}
	
	public void setStoreAttributes(Set<StoreAttribute> storeAttributes) {
		this.storeAttributes = storeAttributes;
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
	
	// Attributes
	
	public StoreAttribute getStoreAttribute(String attributeCode) {
		return getStoreAttribute(attributeCode, null, null);
	}
	
	public StoreAttribute getStoreAttribute(String attributeCode, String localizationCode) {
		return getStoreAttribute(attributeCode, null, localizationCode);
	}
	
	public StoreAttribute getStoreAttribute(String attributeCode, Long marketAreaId) {
		return getStoreAttribute(attributeCode, marketAreaId, null);
	}
	
	public StoreAttribute getStoreAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		StoreAttribute storeAttributeToReturn = null;
		if(storeAttributes != null) {
			List<StoreAttribute> storeAttributesFilter = new ArrayList<StoreAttribute>();
			for (Iterator<StoreAttribute> iterator = storeAttributes.iterator(); iterator.hasNext();) {
				StoreAttribute storeAttribute = (StoreAttribute) iterator.next();
				AttributeDefinition attributeDefinition = storeAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					storeAttributesFilter.add(storeAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<StoreAttribute> iterator = storeAttributesFilter.iterator(); iterator.hasNext();) {
					StoreAttribute storeAttribute = (StoreAttribute) iterator.next();
					AttributeDefinition attributeDefinition = storeAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(storeAttribute.getMarketAreaId() != null
								&& BooleanUtils.negate(storeAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
				if(storeAttributesFilter.size() == 0){
					// TODO : throw error ?
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<StoreAttribute> iterator = storeAttributesFilter.iterator(); iterator.hasNext();) {
					StoreAttribute storeAttribute = (StoreAttribute) iterator.next();
					AttributeDefinition attributeDefinition = storeAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = storeAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode)
								&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
				if(storeAttributesFilter.size() == 0){
					// TODO : throw error ?
					
					for (Iterator<StoreAttribute> iterator = storeAttributes.iterator(); iterator.hasNext();) {
						StoreAttribute storeAttribute = (StoreAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(storeAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)){
							storeAttributeToReturn = storeAttribute;
						}
					}
				}
			}
			if(storeAttributesFilter.size() == 1){
				storeAttributeToReturn = storeAttributesFilter.get(0);
			} else {
				// TODO : throw error ?
			}
		}
		return storeAttributeToReturn;
	}
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
		StoreAttribute storeAttribute = getStoreAttribute(attributeCode, marketAreaId, localizationCode);
		if(storeAttribute != null) {
			return storeAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nCity(Localization localization) {
		return (String) getValue(StoreAttribute.STORE_ATTRIBUTE_I18N_CITY, null, localization.getCode());
	}
	
}
