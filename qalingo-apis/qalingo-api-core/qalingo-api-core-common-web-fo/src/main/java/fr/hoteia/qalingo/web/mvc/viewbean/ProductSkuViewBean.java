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

public class ProductSkuViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5163066749293135126L;
	
	protected int positionItem;
	protected String name;
	protected String description;
	protected String backgroundImage;
	protected String carouselImage;
	protected String iconImage;
	
	protected String addToCartUrl;
	protected String removeFromCartUrl;
	protected String addToWishlistUrl;
	protected String removeFromWishlistUrl;
	protected String productDetailsUrl;

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

	public String getAddToCartUrl() {
		return addToCartUrl;
	}

	public void setAddToCartUrl(String addToCartUrl) {
		this.addToCartUrl = addToCartUrl;
	}

	public String getRemoveFromCartUrl() {
		return removeFromCartUrl;
	}

	public void setRemoveFromCartUrl(String removeFromCartUrl) {
		this.removeFromCartUrl = removeFromCartUrl;
	}

	public String getAddToWishlistUrl() {
		return addToWishlistUrl;
	}

	public void setAddToWishlistUrl(String addToWishlistUrl) {
		this.addToWishlistUrl = addToWishlistUrl;
	}

	public String getRemoveFromWishlistUrl() {
		return removeFromWishlistUrl;
	}

	public void setRemoveFromWishlistUrl(String removeFromWishlistUrl) {
		this.removeFromWishlistUrl = removeFromWishlistUrl;
	}

	public String getProductDetailsUrl() {
		return productDetailsUrl;
	}

	public void setProductDetailsUrl(String productDetailsUrl) {
		this.productDetailsUrl = productDetailsUrl;
	}

}