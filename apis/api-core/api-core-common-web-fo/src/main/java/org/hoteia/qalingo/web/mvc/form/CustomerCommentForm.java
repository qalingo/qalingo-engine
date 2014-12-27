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
public class CustomerCommentForm implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 5942140146714255049L;

	private String objectCode;

	private int qualityOfService = 0;
	private int ratioQualityPrice = 0;
	private int priceScore = 0;

    private String name;
    private String email;

    private String title;
	private String comment;

	public CustomerCommentForm() {
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public int getQualityOfService() {
		return qualityOfService;
	}

	public void setQualityOfService(int qualityOfService) {
		this.qualityOfService = qualityOfService;
	}

	public int getRatioQualityPrice() {
		return ratioQualityPrice;
	}

	public void setRatioQualityPrice(int ratioQualityPrice) {
		this.ratioQualityPrice = ratioQualityPrice;
	}

	public int getPriceScore() {
		return priceScore;
	}

	public void setPriceScore(int priceScore) {
		this.priceScore = priceScore;
	}


	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CustomerCommentForm [objectCode=" + objectCode + ", qualityOfService=" + qualityOfService + ", ratioQualityPrice=" + ratioQualityPrice + ", priceScore=" + priceScore + ", name="
                + name + ", email=" + email + ", title=" + title + ", comment=" + comment + "]";
    }

}