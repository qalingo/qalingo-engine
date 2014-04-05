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

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;

public class WarehouseViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387549593050575454L;

    // ENTITY
    protected Long id;
    protected int version;
    protected String code;
    protected String name;
    protected String description;

    private String address1;
    private String address2;
    private String addressAdditionalInformation;
    private String postalCode;
    private String city;
    private String stateCode;
    private String areaCode;
    private String countryCode;
    private String country;
    private String longitude;
    private String latitude;

    protected String detailsUrl;
    protected String editUrl;

    // MENU
    protected String img;
    protected String changeContextUrl;
    protected String homeUrl;
    
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

	public String getDescription() {
    	return description;
    }

	public void setDescription(String description) {
    	this.description = description;
    }
	
	public String getSortDescription() {
    	return description;
    }
	
	public String getShortDescription() {
		String shortDescription = getDescription();
		if(StringUtils.isNotEmpty(shortDescription)){
			shortDescription = removeHtml(shortDescription);
			if(shortDescription.length() > Constants.SHORT_DESCRIPTION_MAX_LENGTH){
				shortDescription = shortDescription.substring(0, Constants.SHORT_DESCRIPTION_MAX_LENGTH) + "...";
			}
		}
	    return shortDescription;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }
    
	public String getMetaShareTitle() {
		String metaShareTitle = getName();
		metaShareTitle = encodeQuote(metaShareTitle);
	    return metaShareTitle;
    }
	
	public String getMetaShareDescription() {
		String metaShareDescription = getDescription();
		metaShareDescription = encodeQuote(metaShareDescription);
		if(StringUtils.isNotEmpty(metaShareDescription)){
			metaShareDescription = removeHtml(metaShareDescription);
			metaShareDescription = encodeQuote(metaShareDescription);
			if(metaShareDescription.length() > Constants.SHARE_META_DESCRIPTION_MAX_LENGTH){
				metaShareDescription = metaShareDescription.substring(0, Constants.SHARE_META_DESCRIPTION_MAX_LENGTH) + "...";
			}
		}
	    return metaShareDescription;
    }
	
    public String getMetaShareImage() {
        return getImg();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getChangeContextUrl() {
        return changeContextUrl;
    }

    public void setChangeContextUrl(String changeContextUrl) {
        this.changeContextUrl = changeContextUrl;
    }
    
    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }
    
}