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

public class ProductMarketingViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 9190853998911450184L;
	
	protected int positionItem;
	protected String name;
	protected String description;

	protected String backgroundImage;
	protected String carouselImage;
	protected String iconImage;

	protected String productDetailsUrl;
	protected String brandDetailsUrl;
	protected String brandLineDetailsUrl;

	protected List<ProductSkuViewBean> productSkus = new ArrayList<ProductSkuViewBean>();
	protected List<ProductCrossLinkViewBean> productCrossLinks = new ArrayList<ProductCrossLinkViewBean>();
	
	public int getPositionItem() {
		return positionItem;
	}

	public void setPositionItem(int positionItem) {
		this.positionItem = positionItem;
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

	public String getProductDetailsUrl() {
		return productDetailsUrl;
	}
	
	public void setProductDetailsUrl(String productDetailsUrl) {
		this.productDetailsUrl = productDetailsUrl;
	}
	
	public String getBrandDetailsUrl() {
		return brandDetailsUrl;
	}

	public void setBrandDetailsUrl(String brandDetailsUrl) {
		this.brandDetailsUrl = brandDetailsUrl;
	}

	public String getBrandLineDetailsUrl() {
		return brandLineDetailsUrl;
	}

	public void setBrandLineDetailsUrl(String brandLineDetailsUrl) {
		this.brandLineDetailsUrl = brandLineDetailsUrl;
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
	
}
