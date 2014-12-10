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

import java.util.ArrayList;
import java.util.List;

import org.hoteia.tools.richsnippets.mapping.datavocabulary.pojo.ReviewDataVocabularyPojo;

public class ProductSkuCustomerCommentViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 6890622625611735056L;
	
	protected String customerDisplayName;
	protected String customerAvatarImg;
	protected String customerUrl;
	protected String comment;
	
	protected ReviewDataVocabularyPojo reviewDataVocabulary = new ReviewDataVocabularyPojo();
	
	protected List<ProductSkuCustomerCommentViewBean> comments = new ArrayList<ProductSkuCustomerCommentViewBean>();

	public String getCustomerDisplayName() {
	    return customerDisplayName;
    }
	
	public void setCustomerDisplayName(String customerDisplayName) {
	    this.customerDisplayName = customerDisplayName;
    }

	public String getCustomerAvatarImg() {
	    return customerAvatarImg;
    }
	
	public void setCustomerAvatarImg(String customerAvatarImg) {
	    this.customerAvatarImg = customerAvatarImg;
    }
	
	public String getCustomerUrl() {
    	return customerUrl;
    }
	
	public void setCustomerUrl(String customerUrl) {
    	this.customerUrl = customerUrl;
    }
	
	public String getComment() {
    	return comment;
    }
	
	public void setComment(String comment) {
    	this.comment = comment;
    }
	
	public ReviewDataVocabularyPojo getReviewDataVocabulary() {
	    return reviewDataVocabulary;
    }
	
	public void setReviewDataVocabulary(ReviewDataVocabularyPojo reviewDataVocabulary) {
	    this.reviewDataVocabulary = reviewDataVocabulary;
    }

	public List<ProductSkuCustomerCommentViewBean> getComments() {
    	return comments;
    }

	public void setComments(List<ProductSkuCustomerCommentViewBean> comments) {
    	this.comments = comments;
    }
	
}