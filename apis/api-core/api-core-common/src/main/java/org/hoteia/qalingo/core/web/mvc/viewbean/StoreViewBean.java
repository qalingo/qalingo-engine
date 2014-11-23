/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StoreViewBean extends AbstractAddressViewBean {

	/**
	 * Generated UID
	 */
	protected static final long serialVersionUID = 2538607600492625532L;

	protected String code;
    protected String name;
    protected String description;

    protected String i18nName;
    protected String i18nDescription;

    protected String email;
    protected String phone;
    protected String fax;
    protected String website;
    
	protected String longitude;
	protected String latitude;
    protected String distance;

    protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();

	protected String detailsUrl;
	protected String editUrl;

    protected List<String> sliders;

	protected StoreBusinessHourViewBean businessHour;
	
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

    public String getI18nName() {
        if(StringUtils.isNotEmpty(i18nName)){
            return i18nName;
        }
        return name;
    }
    
    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }
    
    public String getI18nDescription() {
        if(StringUtils.isNotEmpty(i18nDescription)){
            return i18nDescription;
        }
        return description;
    }
    
    public void setI18nDescription(String i18nDescription) {
        this.i18nDescription = i18nDescription;
    }

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getWebsiteHttpUrl() {
        if (StringUtils.isNotEmpty(website)
                && !website.contains("http")) {
            return "http://" + website;
        }
        return website;
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
	
	public String getDistance() {
        return distance;
    }
	
	public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<AssetViewBean> getAssets() {
        return assets;
    }
    
    public String getAssetPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getPath();
            }
        }
        return null;
    }
    
    public String getAssetAbsoluteWebPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getAbsoluteWebPath();
            }
        }
        return null;
    }
    
    public String getAssetRelativeWebPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getRelativeWebPath();
            }
        }
        return null;
    }

    public void setAssets(List<AssetViewBean> assets) {
        this.assets = assets;
    }

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

	public List<String> getSliders() {
		return sliders;
	}

	public void setSliders(List<String> sliders) {
		this.sliders = sliders;
	}

	public String getEditUrl() {
		return editUrl;
	}

	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

	public StoreBusinessHourViewBean getBusinessHour() {
        return businessHour;
    }
	
	public void setBusinessHour(StoreBusinessHourViewBean businessHour) {
        this.businessHour = businessHour;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
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
        StoreViewBean other = (StoreViewBean) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "StoreViewBean [code=" + code + ", name=" + name + ", i18nName=" + i18nName + ", address1=" + address1 + ", address2=" + address2 + ", addressAdditionalInformation="
                + addressAdditionalInformation + ", postalCode=" + postalCode + ", city=" + city + ", stateCode=" + stateCode + ", areaCode=" + areaCode + ", countryCode=" + countryCode
                + ", email=" + email + ", phone=" + phone + ", fax=" + fax + ", website=" + website + ", longitude=" + longitude + ", latitude=" + latitude + ", distance="
                + distance + ", detailsUrl=" + detailsUrl + ", editUrl=" + editUrl + "]";
    }

	
}