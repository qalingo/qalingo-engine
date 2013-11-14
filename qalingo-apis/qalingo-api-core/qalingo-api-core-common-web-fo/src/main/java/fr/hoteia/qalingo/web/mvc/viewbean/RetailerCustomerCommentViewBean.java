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
import java.util.ArrayList;
import java.util.List;

import org.hoteia.tools.richsnippets.mapping.datavocabulary.pojo.ReviewDataVocabularyPojo;

public class RetailerCustomerCommentViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 6890622615711735056L;
	
	protected String customerDisplayName;
	protected String customerAvatarImg;
	protected String customerUrl;
	protected String comment;
	protected String dateCreate;
	
	ReviewDataVocabularyPojo reviewDataVocabulary = new ReviewDataVocabularyPojo();
	
	protected List<RetailerCustomerCommentViewBean> comments = new ArrayList<RetailerCustomerCommentViewBean>();

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
	
	public String getDateCreate() {
    	return dateCreate;
    }
	
	public void setDateCreate(String dateCreate) {
    	this.dateCreate = dateCreate;
    }
	
	public ReviewDataVocabularyPojo getReviewDataVocabulary() {
	    return reviewDataVocabulary;
    }
	
	public void setReviewDataVocabulary(ReviewDataVocabularyPojo reviewDataVocabulary) {
	    this.reviewDataVocabulary = reviewDataVocabulary;
    }

	public List<RetailerCustomerCommentViewBean> getComments() {
    	return comments;
    }

	public void setComments(List<RetailerCustomerCommentViewBean> comments) {
    	this.comments = comments;
    }
	
}