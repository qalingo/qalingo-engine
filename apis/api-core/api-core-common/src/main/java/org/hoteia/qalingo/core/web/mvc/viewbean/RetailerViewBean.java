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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.hoteia.qalingo.core.Constants;

public class RetailerViewBean extends AbstractViewBean implements Serializable {

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

	protected boolean isDefault;
	protected boolean isOfficialRetailer;

	protected boolean isBrand;
	protected boolean isEcommerce;
	protected boolean isCorner;

	protected String detailsUrl;
	protected String editUrl;

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

	protected List<ShareOptionViewBean> shareOptions = new ArrayList<ShareOptionViewBean>();

	protected String createdDate;
	protected String updatedDate;

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
		if (StringUtils.isNotEmpty(shortDescription)) {
			shortDescription = removeHtml(shortDescription);
			if (shortDescription.length() > Constants.SHORT_DESCRIPTION_MAX_LENGTH) {
				shortDescription = shortDescription.substring(0,
						Constants.SHORT_DESCRIPTION_MAX_LENGTH) + "...";
			}
		}
		return shortDescription;
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

	public boolean isCorner() {
		return isCorner;
	}

	public void setCorner(boolean isCorner) {
		this.isCorner = isCorner;
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

	public void setSocialNetworkFeed(
			Map<String, List<SocialNetworkFeedItemViewBean>> socialNetworkFeed) {
		this.socialNetworkFeed = socialNetworkFeed;
	}

	public List<StoreViewBean> getStores() {
		return stores;
	}

	public void setStores(List<StoreViewBean> stores) {
		this.stores = stores;
	}

	public List<ShareOptionViewBean> getShareOptions() {
		return shareOptions;
	}

	public void setShareOptions(List<ShareOptionViewBean> shareOptions) {
		this.shareOptions = shareOptions;
	}

	public String getMetaShareTitle() {
		String metaShareTitle = getName();
		metaShareTitle = encodeQuote(metaShareTitle);
		return metaShareTitle;
	}

	public String getMetaShareDescription() {
		String metaShareDescription = getDescription();
		metaShareDescription = encodeQuote(metaShareDescription);
		if (StringUtils.isNotEmpty(metaShareDescription)) {
			metaShareDescription = removeHtml(metaShareDescription);
			metaShareDescription = encodeQuote(metaShareDescription);
			if (metaShareDescription.length() > Constants.SHARE_META_DESCRIPTION_MAX_LENGTH) {
				metaShareDescription = metaShareDescription.substring(0,
						Constants.SHARE_META_DESCRIPTION_MAX_LENGTH) + "...";
			}
		}
		return metaShareDescription;
	}

	public String getMetaShareImage() {
		return getImg();
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
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