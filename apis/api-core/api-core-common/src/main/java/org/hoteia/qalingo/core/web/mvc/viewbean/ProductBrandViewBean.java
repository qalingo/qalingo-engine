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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.util.CoreUtil;

public class ProductBrandViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6549054968245197081L;
	
    protected String code;
	protected String name;
	protected String description;

    protected String i18nName;
    protected String i18nLongDescription;
    protected String i18nShortDescription;
    protected String originCountryCode;
    
	protected List<ProductMarketingViewBean> productMarketings = new ArrayList<ProductMarketingViewBean>();
	
    private Map<String, AttributeValueViewBean> globalAttributes = new HashMap<String, AttributeValueViewBean>();
    private Map<String, AttributeValueViewBean> marketAreaAttributes = new HashMap<String, AttributeValueViewBean>();
    protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();

    protected List<ProductBrandCustomerCommentViewBean> comments = new ArrayList<ProductBrandCustomerCommentViewBean>();
    protected List<ProductBrandTagViewBean> tags = new ArrayList<ProductBrandTagViewBean>();

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
    
    @Deprecated
    public String getI18nDescription() {
        return getI18nLongDescription();
    }
    
    public String getI18nLongDescription() {
        if(StringUtils.isNotEmpty(i18nLongDescription)){
            return i18nLongDescription;
        }
        return description;
    }
    
    public void setI18nLongDescription(String i18nLongDescription) {
        this.i18nLongDescription = i18nLongDescription;
    }
    
    public String getI18nShortDescription() {
        return i18nShortDescription;
    }
    
    public void setI18nShortDescription(String i18nShortDescription) {
        this.i18nShortDescription = i18nShortDescription;
    }
    
    public String getI18nTruncatedDescription() {
        if(StringUtils.isNotEmpty(getI18nShortDescription())){
            if(getI18nShortDescription().length() >= 150){
                return CoreUtil.handleTruncatedDescription(getI18nShortDescription());
            } else {
                return getI18nShortDescription();
            }
        } else if (StringUtils.isNotEmpty(getI18nLongDescription())){
            if(getI18nLongDescription().length() >= 150){
                return CoreUtil.handleTruncatedDescription(getI18nLongDescription());
            } else {
                return getI18nLongDescription();
            }
        }
        return "";
    }
    
    public String getOriginCountryCode() {
        return originCountryCode;
    }
    
    public void setOriginCountryCode(String originCountryCode) {
        this.originCountryCode = originCountryCode;
    }
    
	public List<ProductMarketingViewBean> getProductMarketings() {
		return productMarketings;
	}
	
	public void setProductMarketings(List<ProductMarketingViewBean> productMarketings) {
		this.productMarketings = productMarketings;
	}

    public Map<String, AttributeValueViewBean> getGlobalAttributes() {
        return globalAttributes;
    }
    
    public AttributeValueViewBean getGlobalAttribute(String code) {
        if(globalAttributes != null){
            AttributeValueViewBean attributeValue = globalAttributes.get(code);
            if(attributeValue != null){
                return attributeValue;
            }
        }
        return null;
    }
    
    public void setGlobalAttributes(Map<String, AttributeValueViewBean> globalAttributes) {
        this.globalAttributes = globalAttributes;
    }
    
    public Map<String, AttributeValueViewBean> getMarketAreaAttributes() {
        return marketAreaAttributes;
    }
    
    public AttributeValueViewBean getMarketAreaAttribute(String code) {
        if(marketAreaAttributes != null){
            AttributeValueViewBean attributeValue = marketAreaAttributes.get(code);
            if(attributeValue != null){
                return attributeValue;
            }
        }
        return null;
    }
    
    public void setMarketAreaAttributes(Map<String, AttributeValueViewBean> marketAreaAttributes) {
        this.marketAreaAttributes = marketAreaAttributes;
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

    public String getAbsoluteWebPathLogo() {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals("LOGO")){
                return assetViewBean.getAbsoluteWebPath();
            }
        }
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getAbsoluteWebPath();
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
	
    public List<ProductBrandCustomerCommentViewBean> getComments() {
        return comments;
    }

    public void setComments(List<ProductBrandCustomerCommentViewBean> comments) {
        this.comments = comments;
    }

    public List<ProductBrandTagViewBean> getTags() {
        return tags;
    }

    public void setTags(List<ProductBrandTagViewBean> tags) {
        this.tags = tags;
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
        ProductBrandViewBean other = (ProductBrandViewBean) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductBrandViewBean [code=" + code + ", name=" + name + ", description=" + description + ", i18nName=" + i18nName + ", i18nLongDescription=" + i18nLongDescription
                + ", i18nShortDescription=" + i18nShortDescription + ", originCountryCode=" + originCountryCode + ", detailsUrl=" + detailsUrl + ", productLineUrl=" + productLineUrl + ", editUrl="
                + editUrl + ", specificUrls=" + specificUrls + "]";
    }
	
}