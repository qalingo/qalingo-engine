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
public class RetailerCommentForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 5942140146714255049L;

	private String retailerCode;

    private String qualityOfService;
    private String ratioQualityPrice;
    private String priceScore;
    
    private String comment;
    
    public String getRetailerCode() {
	    return retailerCode;
    }
    
    public void setRetailerCode(String retailerCode) {
	    this.retailerCode = retailerCode;
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
	    RetailerCommentForm other = (RetailerCommentForm) obj;
	    if (comment == null) {
		    if (other.comment != null)
			    return false;
	    } else if (!comment.equals(other.comment))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "RetailerCommentForm [comment=" + comment + "]";
    }

}