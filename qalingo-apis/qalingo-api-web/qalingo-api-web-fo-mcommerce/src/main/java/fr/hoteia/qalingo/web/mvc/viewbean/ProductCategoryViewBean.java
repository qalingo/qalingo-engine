/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5107897401068601858L;
	
	protected String name;
	protected String description;
	protected String backgroundImage;
	protected String carouselImage;
	protected String iconImage;
	protected boolean isRoot;
	
	protected String productAxeUrl;
	protected String productLineUrl;
	
	protected List<ProductCategoryViewBean> subCategories = new ArrayList<ProductCategoryViewBean>();
	protected List<ProductMarketingViewBean> productMarketings = new ArrayList<ProductMarketingViewBean>();

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

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public String getProductAxeUrl() {
		return productAxeUrl;
	}
	
	public void setProductAxeUrl(String productAxeUrl) {
		this.productAxeUrl = productAxeUrl;
	}
	
	public String getProductLineUrl() {
		return productLineUrl;
	}
	
	public void setProductLineUrl(String productLineUrl) {
		this.productLineUrl = productLineUrl;
	}
	
	public List<ProductCategoryViewBean> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<ProductCategoryViewBean> subCategories) {
		this.subCategories = subCategories;
	}

	public List<ProductMarketingViewBean> getProductMarketings() {
		return productMarketings;
	}

	public void setProductMarketings(
			List<ProductMarketingViewBean> productMarketings) {
		this.productMarketings = productMarketings;
	}
	
	
}
