/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.pojo.UrlPojo;

public class LightStorePojo {

    protected Long id;
    protected int version;
    protected String code;
    protected String type;
    protected String name;

    protected String i18nName;
    protected String i18nDescription;
    protected String i18nShortDescription;
    
    protected String longitude;
    protected String latitude;
    
    protected String addressOnLine;
    
    protected String phone;
    protected String fax;
    protected String website;

    protected String detailsUrl;

    protected List<UrlPojo> urls = new ArrayList<UrlPojo>();

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

    public String getAddressOnLine() {
        return addressOnLine;
    }

    public void setAddressOnLine(String addressOnLine) {
        this.addressOnLine = addressOnLine;
    }

    public String getI18nName() {
        return i18nName;
    }

    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }

    public String getI18nDescription() {
        return i18nDescription;
    }

    public void setI18nDescription(String i18nDescription) {
        this.i18nDescription = i18nDescription;
    }

    public String getI18nShortDescription() {
        return i18nShortDescription;
    }

    public void setI18nShortDescription(String i18nShortDescription) {
        this.i18nShortDescription = i18nShortDescription;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
    
    public List<UrlPojo> getUrls() {
        return urls;
    }
    
    public UrlPojo getUrl(String code) {
        if(StringUtils.isNotEmpty(code)){
            for (UrlPojo url : urls) {
                if(code.equals(url.getCode())){
                    return url;
                }
            }
        }
        return null;
    }
    
    public void setUrls(List<UrlPojo> urls) {
        this.urls = urls;
    }
    
}