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

import java.io.Serializable;

public class ProductAssociationLinkViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 7398066505291279593L;
	
	protected int orderItem;
	protected String name;
	protected String type;
	protected String description;
	protected String backgroundImage;
	protected String crossLinkImage;
	protected String iconImage;
	protected String productDetailsUrl;

	public int getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(int orderItem) {
		this.orderItem = orderItem;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
	
	public String getCrossLinkImage() {
		return crossLinkImage;
	}
	
	public void setCrossLinkImage(String crossLinkImage) {
		this.crossLinkImage = crossLinkImage;
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
	
}
