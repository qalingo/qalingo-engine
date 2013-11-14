/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.rest.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import fr.hoteia.qalingo.core.domain.StoreAttribute;
import fr.hoteia.qalingo.core.json.pojo.AbstractJsonPojo;

/**
 *
 * <p>
 * <a href="StoreJsonPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class StoreJsonPojo extends AbstractJsonPojo {

	private Long id;
	private int version;
	private String code;
	private String businessName;
    private String address1;
    private String address2;
    private String addressAdditionalInformation;
    private String postalCode;
    private String city;
    private String stateCode;
    private String areaCode;
    private String countryCode;
	
    private Set<StoreAttribute> storeAttributes = new HashSet<StoreAttribute>(); 
	
	private String longitude;
	private String latitude;

	private Date dateCreate;
	private Date dateUpdate;
	
	public StoreJsonPojo() {
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

	@JsonAnySetter
	@Override
	public void handleUnknown(String key, Object value) {
		super.handleUnknown(key, value);
	}
}