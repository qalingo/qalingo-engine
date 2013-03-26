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

public class ProductCategoryViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8297814352511603492L;
	
	// VIEW/FORM LABEL
	private String businessNameLabel;
	private String businessNameInformation;
	private String descriptionLabel;
	private String descriptionInformation;
	private String isDefaultLabel;
	private String codeLabel;
	private String defaultParentCategoryLabel;
	private String productBrandLabel;
	private String productMarketingGlobalAttributesLabel; 
	private String productMarketingMarketAreaAttributesLabel; 
	private String productMarketingLabel;
	private String subCategoriesLabel;
	private String dateCreateLabel;
	private String dateUpdateLabel;
	
	protected String businessName;
	protected String code;
	protected String description;
	protected int countProduct;
	protected ProductCategoryViewBean defaultParentCategory;
	
	protected List<ProductCategoryViewBean> subCategories = new ArrayList<ProductCategoryViewBean>();

	private Map<String, String> globalAttributes = new HashMap<String, String>();
	private Map<String, String> marketAreaAttributes = new HashMap<String, String>();

	protected List<ProductMarketingViewBean> ProductMarketings = new ArrayList<ProductMarketingViewBean>();

	private String createdDate;
	private String updatedDate;
	
	protected String categoryDetailsLabel;
	protected String categoryDetailsUrl;

	protected String categoryEditLabel;
	protected String categoryEditUrl;
	
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

	public String getDefaultParentCategoryLabel() {
		return defaultParentCategoryLabel;
	}
	
	public void setDefaultParentCategoryLabel(String defaultParentCategoryLabel) {
		this.defaultParentCategoryLabel = defaultParentCategoryLabel;
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

	public String getProductMarketingLabel() {
		return productMarketingLabel;
	}
	
	public void setProductMarketingLabel(String productMarketingLabel) {
		this.productMarketingLabel = productMarketingLabel;
	}

	public String getSubCategoriesLabel() {
		return subCategoriesLabel;
	}
	
	public void setSubCategoriesLabel(String subCategoriesLabel) {
		this.subCategoriesLabel = subCategoriesLabel;
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
	
	public String getBusinessName() {
		return businessName;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCountProduct() {
		return countProduct;
	}
	
	public void setCountProduct(int countProduct) {
		this.countProduct = countProduct;
	}
	
	public ProductCategoryViewBean getDefaultParentCategory() {
		return defaultParentCategory;
	}
	
	public void setDefaultParentCategory(ProductCategoryViewBean defaultParentCategory) {
		this.defaultParentCategory = defaultParentCategory;
	}
	
	public List<ProductCategoryViewBean> getSubCategories() {
		return subCategories;
	}
	
	public void setSubCategories(List<ProductCategoryViewBean> subCategories) {
		this.subCategories = subCategories;
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
	
	public List<ProductMarketingViewBean> getProductMarketings() {
		return ProductMarketings;
	}
	
	public void setProductMarketings(List<ProductMarketingViewBean> ProductMarketings) {
		this.ProductMarketings = ProductMarketings;
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

	public String getCategoryDetailsLabel() {
		return categoryDetailsLabel;
	}

	public void setCategoryDetailsLabel(String categoryDetailsLabel) {
		this.categoryDetailsLabel = categoryDetailsLabel;
	}

	public String getCategoryDetailsUrl() {
		return categoryDetailsUrl;
	}

	public void setCategoryDetailsUrl(String categoryDetailsUrl) {
		this.categoryDetailsUrl = categoryDetailsUrl;
	}

	public String getCategoryEditLabel() {
		return categoryEditLabel;
	}

	public void setCategoryEditLabel(String categoryEditLabel) {
		this.categoryEditLabel = categoryEditLabel;
	}

	public String getCategoryEditUrl() {
		return categoryEditUrl;
	}

	public void setCategoryEditUrl(String categoryEditUrl) {
		this.categoryEditUrl = categoryEditUrl;
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
