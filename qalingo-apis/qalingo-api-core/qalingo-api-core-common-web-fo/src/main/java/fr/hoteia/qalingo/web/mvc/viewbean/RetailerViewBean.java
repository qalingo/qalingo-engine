/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import fr.hoteia.qalingo.core.Constants;

public class RetailerViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387549593050575454L;
	
	protected String code;
	protected String name;
	protected String description;
	
	protected boolean isDefault;
	protected boolean isOfficialRetailer;
	
	protected boolean isBrand;
	protected boolean isEcommerce;
	
	protected String img;
	protected String url;
	
	protected int qualityOfService = 0;
	protected int priceScore = 0;
	protected int ratioQualityPrice = 0;

	protected int reviewCount = 0;
	protected String reviewCountLabel;

	protected RetailerAddressViewBean defaultAddress = new RetailerAddressViewBean();

	protected List<RetailerCustomerCommentViewBean> comments = new ArrayList<RetailerCustomerCommentViewBean>();
	protected List<RetailerTagViewBean> tags = new ArrayList<RetailerTagViewBean>();

	protected Map<String, List<SocialNetworkFeedItemViewBean>> socialNetworkFeed = new HashMap<String, List<SocialNetworkFeedItemViewBean>>();

	protected List<StoreViewBean> stores = new ArrayList<StoreViewBean>();

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

	public boolean isDefault() {
    	return isDefault;
    }

	public void setDefault(boolean isDefault) {
    	this.isDefault = isDefault;
    }

	public boolean isOfficialRetailer() {
    	return isOfficialRetailer;
    }

	public void setOfficialRetailer(boolean isOfficialRetailer) {
    	this.isOfficialRetailer = isOfficialRetailer;
    }

	public boolean isBrand() {
    	return isBrand;
    }

	public void setBrand(boolean isBrand) {
    	this.isBrand = isBrand;
    }

	public boolean isEcommerce() {
    	return isEcommerce;
    }

	public void setEcommerce(boolean isEcommerce) {
    	this.isEcommerce = isEcommerce;
    }

	public String getImg() {
    	return img;
    }

	public void setImg(String img) {
    	this.img = img;
    }

	public String getUrl() {
    	return url;
    }

	public void setUrl(String url) {
    	this.url = url;
    }

	public int getQualityOfService() {
    	return qualityOfService;
    }

	public void setQualityOfService(int qualityOfService) {
    	this.qualityOfService = qualityOfService;
    }

	public int getPriceScore() {
    	return priceScore;
    }

	public void setPriceScore(int priceScore) {
    	this.priceScore = priceScore;
    }

	public int getRatioQualityPrice() {
    	return ratioQualityPrice;
    }

	public void setRatioQualityPrice(int ratioQualityPrice) {
    	this.ratioQualityPrice = ratioQualityPrice;
    }

	public int getReviewCount() {
	    return reviewCount;
    }
	
	public void setReviewCount(int reviewCount) {
	    this.reviewCount = reviewCount;
    }
	
	public String getReviewCountLabel() {
	    return reviewCountLabel;
    }
	
	public void setReviewCountLabel(String reviewCountLabel) {
		this.reviewCountLabel = reviewCountLabel;
	}

	public RetailerAddressViewBean getDefaultAddress() {
	    return defaultAddress;
    }
	
	public void setDefaultAddress(RetailerAddressViewBean defaultAddress) {
	    this.defaultAddress = defaultAddress;
    }

	public List<RetailerCustomerCommentViewBean> getComments() {
    	return comments;
    }

	public void setComments(List<RetailerCustomerCommentViewBean> comments) {
    	this.comments = comments;
    }

	public List<RetailerTagViewBean> getTags() {
    	return tags;
    }

	public void setTags(List<RetailerTagViewBean> tags) {
    	this.tags = tags;
    }
	
	public Map<String, List<SocialNetworkFeedItemViewBean>> getSocialNetworkFeed() {
	    return socialNetworkFeed;
    }
	
	public void setSocialNetworkFeed(Map<String, List<SocialNetworkFeedItemViewBean>> socialNetworkFeed) {
	    this.socialNetworkFeed = socialNetworkFeed;
    }
	
	public List<StoreViewBean> getStores() {
	    return stores;
    }
	
	public void setStores(List<StoreViewBean> stores) {
	    this.stores = stores;
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
	
}