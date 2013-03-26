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
import java.util.HashMap;
import java.util.Map;

public class ProductSkuViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 9149101287167262715L;
	
	// VIEW/FORM LABEL
	private String businessNameLabel;
	private String businessNameInformation;
	private String descriptionLabel;
	private String descriptionInformation;
	private String isDefaultLabel;
	private String productSkuGlobalAttributesLabel; 
	private String productSkuMarketAreaAttributesLabel; 
	private String codeLabel;
	private String productMarketingAssociatedLabel;
	private String productBrandLabel;
	private String productMarketingGlobalAttributesLabel; 
	private String productMarketingMarketAreaAttributesLabel; 
	private String productSkusLabel;
	private String productCrossLinksLabel;
	private String assetsLabel; 
	private String dateCreateLabel;
	private String dateUpdateLabel;
	
	protected int positionItem;
	protected String businessName;
	protected String description;
	protected String code;
	protected boolean isDefault;
	
	private Map<String, String> globalAttributes = new HashMap<String, String>();
	private Map<String, String> marketAreaAttributes = new HashMap<String, String>();
	
	protected ProductMarketingViewBean productMarketing;
	
	private String createdDate;
	private String updatedDate;
	
	protected String backgroundImage;
	protected String carouselImage;
	protected String iconImage;
	
	protected String addToCartUrl;
	protected String addToCartLabel;
	protected String removeFromCartUrl;
	protected String removeFromCartLabel;

	protected String addToWishlistUrl;
	protected String addToWishlistLabel;
	protected String removeFromWishlistUrl;
	protected String removeFromWishlistLabel;
	
	protected String productSkuDetailsUrl;
	protected String productSkuDetailsLabel;

	protected String productSkuEditUrl;
	protected String productSkuEditLabel;
	
	private String backUrl;
	private String cancelLabel;
	private String formSubmitUrl;
	private String submitLabel;
	
	public String getBusinessNameLabel() {
		return businessNameLabel;
	}

	public void setBusinessNameLabel(String businessNameLabel) {
		this.businessNameLabel = businessNameLabel;
	}

	public String getBusinessNameInformation() {
		return businessNameInformation;
	}
	
	public void setBusinessNameInformation(String businessNameInformation) {
		this.businessNameInformation = businessNameInformation;
	}
	
	public String getDescriptionLabel() {
		return descriptionLabel;
	}

	public void setDescriptionLabel(String descriptionLabel) {
		this.descriptionLabel = descriptionLabel;
	}

	public String getDescriptionInformation() {
		return descriptionInformation;
	}
	
	public void setDescriptionInformation(String descriptionInformation) {
		this.descriptionInformation = descriptionInformation;
	}

	public String getIsDefaultLabel() {
		return isDefaultLabel;
	}

	public void setIsDefaultLabel(String isDefaultLabel) {
		this.isDefaultLabel = isDefaultLabel;
	}

	public String getProductSkuGlobalAttributesLabel() {
		return productSkuGlobalAttributesLabel;
	}

	public void setProductSkuGlobalAttributesLabel(String productSkuGlobalAttributesLabel) {
		this.productSkuGlobalAttributesLabel = productSkuGlobalAttributesLabel;
	}

	public String getProductSkuMarketAreaAttributesLabel() {
		return productSkuMarketAreaAttributesLabel;
	}

	public void setProductSkuMarketAreaAttributesLabel(String productSkuMarketAreaAttributesLabel) {
		this.productSkuMarketAreaAttributesLabel = productSkuMarketAreaAttributesLabel;
	}

	public String getCodeLabel() {
		return codeLabel;
	}

	public void setCodeLabel(String codeLabel) {
		this.codeLabel = codeLabel;
	}

	public String getProductMarketingAssociatedLabel() {
		return productMarketingAssociatedLabel;
	}
	
	public void setProductMarketingAssociatedLabel(String productMarketingAssociatedLabel) {
		this.productMarketingAssociatedLabel = productMarketingAssociatedLabel;
	}
	
	public String getProductBrandLabel() {
		return productBrandLabel;
	}

	public void setProductBrandLabel(String productBrandLabel) {
		this.productBrandLabel = productBrandLabel;
	}

	public String getProductMarketingGlobalAttributesLabel() {
		return productMarketingGlobalAttributesLabel;
	}

	public void setProductMarketingGlobalAttributesLabel(String productMarketingGlobalAttributesLabel) {
		this.productMarketingGlobalAttributesLabel = productMarketingGlobalAttributesLabel;
	}

	public String getProductMarketingMarketAreaAttributesLabel() {
		return productMarketingMarketAreaAttributesLabel;
	}

	public void setProductMarketingMarketAreaAttributesLabel(String productMarketingMarketAreaAttributesLabel) {
		this.productMarketingMarketAreaAttributesLabel = productMarketingMarketAreaAttributesLabel;
	}

	public String getProductSkusLabel() {
		return productSkusLabel;
	}

	public void setProductSkusLabel(String productSkusLabel) {
		this.productSkusLabel = productSkusLabel;
	}

	public String getProductCrossLinksLabel() {
		return productCrossLinksLabel;
	}

	public void setProductCrossLinksLabel(String productCrossLinksLabel) {
		this.productCrossLinksLabel = productCrossLinksLabel;
	}

	public String getAssetsLabel() {
		return assetsLabel;
	}

	public void setAssetsLabel(String assetsLabel) {
		this.assetsLabel = assetsLabel;
	}

	public String getDateCreateLabel() {
		return dateCreateLabel;
	}

	public void setDateCreateLabel(String dateCreateLabel) {
		this.dateCreateLabel = dateCreateLabel;
	}

	public String getDateUpdateLabel() {
		return dateUpdateLabel;
	}

	public void setDateUpdateLabel(String dateUpdateLabel) {
		this.dateUpdateLabel = dateUpdateLabel;
	}
	
	public int getPositionItem() {
		return positionItem;
	}

	public void setPositionItem(int positionItem) {
		this.positionItem = positionItem;
	}
	
	public String getBusinessName() {
		return businessName;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public Map<String, String> getGlobalAttributes() {
		return globalAttributes;
	}
	
	public void setGlobalAttributes(Map<String, String> globalAttributes) {
		this.globalAttributes = globalAttributes;
	}
	
	public Map<String, String> getMarketAreaAttributes() {
		return marketAreaAttributes;
	}
	
	public void setMarketAreaAttributes(Map<String, String> marketAreaAttributes) {
		this.marketAreaAttributes = marketAreaAttributes;
	}
	
	public ProductMarketingViewBean getProductMarketing() {
		return productMarketing;
	}
	
	public void setProductMarketing(ProductMarketingViewBean productMarketing) {
		this.productMarketing = productMarketing;
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

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getCarouselImage() {
		return carouselImage;
	}
	
	public void setCarouselImage(String carouselImage) {
		this.carouselImage = carouselImage;
	}
	
	public String getIconImage() {
		return iconImage;
	}

	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}

	public String getAddToCartUrl() {
		return addToCartUrl;
	}

	public void setAddToCartUrl(String addToCartUrl) {
		this.addToCartUrl = addToCartUrl;
	}

	public String getAddToCartLabel() {
		return addToCartLabel;
	}

	public void setAddToCartLabel(String addToCartLabel) {
		this.addToCartLabel = addToCartLabel;
	}

	public String getRemoveFromCartUrl() {
		return removeFromCartUrl;
	}

	public void setRemoveFromCartUrl(String removeFromCartUrl) {
		this.removeFromCartUrl = removeFromCartUrl;
	}

	public String getRemoveFromCartLabel() {
		return removeFromCartLabel;
	}

	public void setRemoveFromCartLabel(String removeFromCartLabel) {
		this.removeFromCartLabel = removeFromCartLabel;
	}

	public String getAddToWishlistUrl() {
		return addToWishlistUrl;
	}

	public void setAddToWishlistUrl(String addToWishlistUrl) {
		this.addToWishlistUrl = addToWishlistUrl;
	}

	public String getAddToWishlistLabel() {
		return addToWishlistLabel;
	}

	public void setAddToWishlistLabel(String addToWishlistLabel) {
		this.addToWishlistLabel = addToWishlistLabel;
	}

	public String getRemoveFromWishlistUrl() {
		return removeFromWishlistUrl;
	}

	public void setRemoveFromWishlistUrl(String removeFromWishlistUrl) {
		this.removeFromWishlistUrl = removeFromWishlistUrl;
	}

	public String getRemoveFromWishlistLabel() {
		return removeFromWishlistLabel;
	}

	public void setRemoveFromWishlistLabel(String removeFromWishlistLabel) {
		this.removeFromWishlistLabel = removeFromWishlistLabel;
	}

	public String getProductSkuDetailsUrl() {
		return productSkuDetailsUrl;
	}
	
	public void setProductSkuDetailsUrl(String productSkuDetailsUrl) {
		this.productSkuDetailsUrl = productSkuDetailsUrl;
	}
	
	public String getProductSkuDetailsLabel() {
		return productSkuDetailsLabel;
	}
	
	public void setProductSkuDetailsLabel(String productSkuDetailsLabel) {
		this.productSkuDetailsLabel = productSkuDetailsLabel;
	}
	
	public String getProductSkuEditUrl() {
		return productSkuEditUrl;
	}
	
	public void setProductSkuEditUrl(String productSkuEditUrl) {
		this.productSkuEditUrl = productSkuEditUrl;
	}

	public String getProductSkuEditLabel() {
		return productSkuEditLabel;
	}
	
	public void setProductSkuEditLabel(String productSkuEditLabel) {
		this.productSkuEditLabel = productSkuEditLabel;
	}
	
	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getCancelLabel() {
		return cancelLabel;
	}

	public void setCancelLabel(String cancelLabel) {
		this.cancelLabel = cancelLabel;
	}

	public String getFormSubmitUrl() {
		return formSubmitUrl;
	}

	public void setFormSubmitUrl(String formSubmitUrl) {
		this.formSubmitUrl = formSubmitUrl;
	}

	public String getSubmitLabel() {
		return submitLabel;
	}

	public void setSubmitLabel(String submitLabel) {
		this.submitLabel = submitLabel;
	}
}
