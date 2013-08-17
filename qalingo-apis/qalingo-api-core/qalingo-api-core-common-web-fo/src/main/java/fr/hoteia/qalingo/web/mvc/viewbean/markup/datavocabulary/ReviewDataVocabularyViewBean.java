package fr.hoteia.qalingo.web.mvc.viewbean.markup.datavocabulary;

import java.io.Serializable;

/**
 *   http://data-vocabulary.org/Review
 */
public class ReviewDataVocabularyViewBean extends AbstractDataVocabularyViewBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 2497989500437950281L;

    private String itemreviewed;
    private String reviewer;
    private String dtreviewed;
    private String summary;
    private String description;
    private String rating;
    
    public String getItemreviewed() {
	    return itemreviewed;
    }
    
    public void setItemreviewed(String itemreviewed) {
	    this.itemreviewed = itemreviewed;
    }

	public String getReviewer() {
    	return reviewer;
    }

	public void setReviewer(String reviewer) {
    	this.reviewer = reviewer;
    }

	public String getDtreviewed() {
    	return dtreviewed;
    }

	public void setDtreviewed(String dtreviewed) {
    	this.dtreviewed = dtreviewed;
    }

	public String getSummary() {
    	return summary;
    }

	public void setSummary(String summary) {
    	this.summary = summary;
    }

	public String getDescription() {
    	return description;
    }

	public void setDescription(String description) {
    	this.description = description;
    }

	public String getRating() {
    	return rating;
    }

	public void setRating(String rating) {
    	this.rating = rating;
    }
    
}