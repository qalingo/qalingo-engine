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
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.util.CoreUtil;

public class StoreViewBean extends AbstractAddressViewBean {

	/**
	 * Generated UID
	 */
	protected static final long serialVersionUID = 2538607600492625532L;

	protected String code;
    protected String type;
    protected String name;
    protected String description;

    protected String i18nName;
    protected String i18nDescription;
    protected String i18nShortDescription;

    protected String email;
    protected String phone;
    protected String fax;
    protected String website;
    
	protected String longitude;
	protected String latitude;
    protected String distance;

    protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();
    
    protected List<StoreCustomerCommentViewBean> comments = new ArrayList<StoreCustomerCommentViewBean>();
    protected List<StoreTagViewBean> tags = new ArrayList<StoreTagViewBean>();
    protected List<StoreBusinessHourViewBean> businessHours = new ArrayList<StoreBusinessHourViewBean>();
    protected List<StoreServiceViewBean> services = new ArrayList<StoreServiceViewBean>();

    protected List<String> sliders;

	protected String detailsUrl;
    protected String productLineUrl;
	protected String editUrl;
	
    protected List<UrlBean> specificUrls = new ArrayList<UrlBean>();
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getType() {
        return type;
    }
	
    public boolean containsType(String selectedType) {
        if(type != null){
            return type.contains(selectedType);
        }
        return false;
    }
	
	public void setType(String type) {
        this.type = type;
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
    
    public String getI18nTruncatedName() {
        int size = Constants.POJO_NAME_MAX_LENGTH;
        if (StringUtils.isNotEmpty(getI18nName())){
            if(getI18nName().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nName(), size);
            } else {
                return getI18nName();
            }
        }
        return "";
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
    
    public String getI18nShortDescription() {
        return i18nShortDescription;
    }
    
    public void setI18nShortDescription(String i18nShortDescription) {
        this.i18nShortDescription = i18nShortDescription;
    }
    
    public String getI18nTruncatedDescription() {
        int size = Constants.POJO_DESCRIPTION_MAX_LENGTH;
        if(StringUtils.isNotEmpty(getI18nShortDescription())){
            if(getI18nShortDescription().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nShortDescription(), size);
            } else {
                return getI18nShortDescription();
            }
        } else if (StringUtils.isNotEmpty(getI18nDescription())){
            if(getI18nDescription().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nDescription(), size);
            } else {
                return getI18nDescription();
            }
        }
        return "";
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

    public String getWebsiteWithoutHttp() {
        if (StringUtils.isNotEmpty(website)
                && website.contains("http")) {
            if(website.endsWith("/")){
                website = website.substring(0, website.length() - 1);
            }
            return website.replace("http://", "");
        }
        return website;
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
    
    public List<AssetViewBean> getAssets(String type) {
        List<AssetViewBean> assetsByType = new ArrayList<AssetViewBean>();
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                assetsByType.add(assetViewBean);
            }
        }
        if(assetsByType.size() == 0){
            assetsByType.add(getDefaultAsset());
        }
        return assetsByType;
    }
    
    public String getAssetPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getPath();
            }
        }
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getPath();
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
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getAbsoluteWebPath();
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
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getRelativeWebPath();
        }
        return null;
    }

    public AssetViewBean getDefaultAsset() {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if("default".equals(assetViewBean.getType())){
                return assetViewBean;
            }
        }
        return null;
    }
    
    public void setAssets(List<AssetViewBean> assets) {
        this.assets = assets;
    }
    
    public List<StoreCustomerCommentViewBean> getComments() {
        return comments;
    }

    public void setComments(List<StoreCustomerCommentViewBean> comments) {
        this.comments = comments;
    }

    public List<StoreTagViewBean> getTags() {
        return tags;
    }

    public void setTags(List<StoreTagViewBean> tags) {
        this.tags = tags;
    }

    public List<StoreBusinessHourViewBean> getBusinessHours() {
        return businessHours;
    }
    
    public void setBusinessHours(List<StoreBusinessHourViewBean> businessHours) {
        this.businessHours = businessHours;
    }
    
    public List<StoreServiceViewBean> getServices() {
        return services;
    }
    
    public List<StoreServiceViewBean> getServices(String type) {
        List<StoreServiceViewBean> storeServices = null;
        if (services != null 
                && Hibernate.isInitialized(services)) {
            storeServices = new ArrayList<StoreServiceViewBean>();
            for (Iterator<StoreServiceViewBean> iterator = services.iterator(); iterator.hasNext();) {
                StoreServiceViewBean storeService = (StoreServiceViewBean) iterator.next();
                if (storeService != null && storeService.getType() != null
                        && storeService.getType().equals(type)) {
                    storeServices.add(storeService);
                }
            }
        }
        return storeServices;
    }
    
    public void setServices(List<StoreServiceViewBean> services) {
        this.services = services;
    }
    
    public List<String> getSliders() {
        return sliders;
    }

    public void setSliders(List<String> sliders) {
        this.sliders = sliders;
    }

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

	public String getProductLineUrl() {
        return productLineUrl;
    }
	
	public void setProductLineUrl(String productLineUrl) {
        this.productLineUrl = productLineUrl;
    }

	public String getEditUrl() {
		return editUrl;
	}

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }

    public List<UrlBean> getSpecificUrls() {
        return specificUrls;
    }
    
    public void setSpecificUrls(List<UrlBean> specificUrls) {
        this.specificUrls = specificUrls;
    }
    
    public String getSpecificUrl(String key) {
        if(specificUrls != null){
            for(UrlBean specificUrl : specificUrls){
                if(specificUrl.getKey().equals(key)){
                    return specificUrl.value;
                }
            }
        }
        return null;
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