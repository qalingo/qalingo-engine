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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchProductItemViewBean extends AbstractViewBean implements
		Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8803558928157558979L;

	private String name;
	private String code;
	private String description;
	private String categoryName;
	private String categoryCode;

	protected String backgroundImage;
	protected String carouselImage;
	protected String iconImage;

	protected List<ProductSkuViewBean> productSkus = new ArrayList<ProductSkuViewBean>();

	private String addToCartUrl;
	private String detailsUrl;

	protected CustomerProductRatesViewBean customerProductRates;

	public SearchProductItemViewBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAddToCartUrl() {
		return addToCartUrl;
	}

	public void setAddToCartUrl(String addToCartUrl) {
		this.addToCartUrl = addToCartUrl;
	}

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
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

	public void setProductSkus(List<ProductSkuViewBean> productSkus) {
		this.productSkus = productSkus;
	}

	public List<ProductSkuViewBean> getProductSkus() {
		return productSkus;
	}

	public String getPriceWithCurrencySign() {
		if (productSkus != null) {
			for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator
					.hasNext();) {
				ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator
						.next();
				if (productSkuViewBean.isDefault()) {
					if (productSkuViewBean.getPriceWithCurrencySign() != null) {
						return productSkuViewBean.getPriceWithCurrencySign();
					}
				}
			}
			if (!productSkus.isEmpty()) {
				ProductSkuViewBean productSkuViewBean = productSkus.get(0);
				if (productSkuViewBean != null
						&& productSkuViewBean.getPriceWithCurrencySign() != null) {
					return productSkuViewBean.getPriceWithCurrencySign();
				}
			}
		}
		return null;
	}

	public BigDecimal getPrice() {
		if (productSkus != null) {
			for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator
					.hasNext();) {
				ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator
						.next();
				if (productSkuViewBean.isDefault()) {
					if (productSkuViewBean.getPriceWithCurrencySign() != null) {
						return new BigDecimal(productSkuViewBean.getSalePrice());
					}
				}
			}
			if (!productSkus.isEmpty()) {
				ProductSkuViewBean productSkuViewBean = productSkus.get(0);
				if (productSkuViewBean != null
						&& productSkuViewBean.getPriceWithCurrencySign() != null) {
					return new BigDecimal(productSkuViewBean.getSalePrice());
				}
			}
		}
		return null;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public CustomerProductRatesViewBean getCustomerProductRates() {
		return customerProductRates;
	}

	public void setCustomerProductRates(
			CustomerProductRatesViewBean customerProductRates) {
		this.customerProductRates = customerProductRates;
	}

}
