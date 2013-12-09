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

public class CatalogCategoryViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8297814352511603492L;
	
	protected String businessName;
	protected String code;
	protected String description;
	protected int countProduct;
	protected CatalogCategoryViewBean defaultParentCategory;
	
	protected List<CatalogCategoryViewBean> subCategories = new ArrayList<CatalogCategoryViewBean>();

	private Map<String, String> globalAttributes = new HashMap<String, String>();
	private Map<String, String> marketAreaAttributes = new HashMap<String, String>();

	protected List<ProductMarketingViewBean> productMarketings = new ArrayList<ProductMarketingViewBean>();
	protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();

	private String createdDate;
	private String updatedDate;
	
	protected String detailsUrl;
	protected String editUrl;
	
	public CatalogCategoryViewBean() {
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
	
	public CatalogCategoryViewBean getDefaultParentCategory() {
		return defaultParentCategory;
	}
	
	public void setDefaultParentCategory(CatalogCategoryViewBean defaultParentCategory) {
		this.defaultParentCategory = defaultParentCategory;
	}
	
	public List<CatalogCategoryViewBean> getSubCategories() {
		return subCategories;
	}
	
	public void setSubCategories(List<CatalogCategoryViewBean> subCategories) {
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
		return productMarketings;
	}
	
	public void setProductMarketings(List<ProductMarketingViewBean> ProductMarketings) {
		this.productMarketings = ProductMarketings;
	}
	
	public List<AssetViewBean> getAssets() {
		return assets;
	}
	
	public void setAssets(List<AssetViewBean> assets) {
		this.assets = assets;
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

}