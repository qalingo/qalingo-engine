/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;

/**
 * 
 * 
 */
public class ProductCommentForm implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 5942140146714255049L;

	private String productCode;

	private String qualityOfService;
	private String ratioQualityPrice;
	private String priceScore;

	private String comment;

	public ProductCommentForm() {
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getQualityOfService() {
		return qualityOfService;
	}

	public void setQualityOfService(String qualityOfService) {
		this.qualityOfService = qualityOfService;
	}

	public String getRatioQualityPrice() {
		return ratioQualityPrice;
	}

	public void setRatioQualityPrice(String ratioQualityPrice) {
		this.ratioQualityPrice = ratioQualityPrice;
	}

	public String getPriceScore() {
		return priceScore;
	}

	public void setPriceScore(String priceScore) {
		this.priceScore = priceScore;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((priceScore == null) ? 0 : priceScore.hashCode());
		result = prime * result
				+ ((productCode == null) ? 0 : productCode.hashCode());
		result = prime
				* result
				+ ((qualityOfService == null) ? 0 : qualityOfService.hashCode());
		result = prime
				* result
				+ ((ratioQualityPrice == null) ? 0 : ratioQualityPrice
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductCommentForm other = (ProductCommentForm) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (priceScore == null) {
			if (other.priceScore != null)
				return false;
		} else if (!priceScore.equals(other.priceScore))
			return false;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		if (qualityOfService == null) {
			if (other.qualityOfService != null)
				return false;
		} else if (!qualityOfService.equals(other.qualityOfService))
			return false;
		if (ratioQualityPrice == null) {
			if (other.ratioQualityPrice != null)
				return false;
		} else if (!ratioQualityPrice.equals(other.ratioQualityPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductCommentForm [productCode=" + productCode
				+ ", qualityOfService=" + qualityOfService
				+ ", ratioQualityPrice=" + ratioQualityPrice + ", priceScore="
				+ priceScore + ", comment=" + comment + "]";
	}

}