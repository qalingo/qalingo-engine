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

public class ProductMarketingViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 9190853998911450184L;
	
	// VIEW/FORM LABEL
	private String businessNameLabel;
	private String businessNameInformation;
	private String descriptionLabel;
	private String descriptionInformation;
	private String isDefaultLabel;
	private String codeLabel;
	private String productBrandLabel;
	private String productMarketingGlobalAttributesLabel; 
	private String productMarketingMarketAreaAttributesLabel; 
	private String productSkusLabel;
	private String productCrossLinksLabel;
	private String assetsLabel; 
	private String dateCreateLabel;
	private String dateUpdateLabel;
	
	protected int positionItem = 1;
	protected String businessName;
	protected String description;
	protected String code;
	protected boolean isDefault;

	private Map<String, String> globalAttributes = new HashMap<String, String>();
	private Map<String, String> marketAreaAttributes = new HashMap<String, String>();
	
	protected BrandViewBean brand;
	
//	protected String backgroundImage;
//	protected String carouselImage;
//	protected String iconImage;

	private String createdDate;
	private String updatedDate;
	
	protected String productDetailsLabel;
	protected String productDetailsUrl;

	protected String productEditLabel;
	protected String productEditUrl;

	protected List<ProductSkuViewBean> productSkus = new ArrayList<ProductSkuViewBean>();
	protected List<ProductCrossLinkViewBean> productCrossLinks = new ArrayList<ProductCrossLinkViewBean>();
	
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

	public String getCodeLabel() {
		return codeLabel;
	}

	public void setCodeLabel(String codeLabel) {
		this.codeLabel = codeLabel;
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
	
	public BrandViewBean getBrand() {
		return brand;
	}
	
	public void setBrand(BrandViewBean brand) {
		this.brand = brand;
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
	
	public String getProductDetailsLabel() {
		return productDetailsLabel;
	}
	
	public void setProductDetailsLabel(String productDetailsLabel) {
		this.productDetailsLabel = productDetailsLabel;
	}
	
	public String getProductDetailsUrl() {
		return productDetailsUrl;
	}
	
	public void setProductDetailsUrl(String productDetailsUrl) {
		this.productDetailsUrl = productDetailsUrl;
	}
	
	public String getProductEditLabel() {
		return productEditLabel;
	}
	
	public void setProductEditLabel(String productEditLabel) {
		this.productEditLabel = productEditLabel;
	}
	
	public String getProductEditUrl() {
		return productEditUrl;
	}
	
	public void setProductEditUrl(String productEditUrl) {
		this.productEditUrl = productEditUrl;
	}
	
	public List<ProductSkuViewBean> getProductSkus() {
		return productSkus;
	}
	
	public void setProductSkus(List<ProductSkuViewBean> productSkus) {
		this.productSkus = productSkus;
	}
	
	public List<ProductCrossLinkViewBean> getProductCrossLinks() {
		return productCrossLinks;
	}
	
	public void setProductCrossLinks(List<ProductCrossLinkViewBean> productCrossLinks) {
		this.productCrossLinks = productCrossLinks;
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
