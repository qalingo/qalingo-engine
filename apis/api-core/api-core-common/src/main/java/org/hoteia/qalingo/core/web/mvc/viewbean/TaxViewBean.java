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

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;

public class TaxViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387549593050575454L;

    // ENTITY
    protected Long id;
    protected int version;
	protected String code;
	protected String name;
	protected String description;
    protected String percent;
	
	protected String detailsUrl;
    protected String editUrl;
    
    // MENU
    protected String img;
    protected String changeContextUrl;
    protected String homeUrl;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

	public void setCode(String code) {
    	this.code = code;
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
	
	public String getSortDescription() {
    	return description;
    }
	
	public String getShortDescription() {
		String shortDescription = getDescription();
		if(StringUtils.isNotEmpty(shortDescription)){
			shortDescription = removeHtml(shortDescription);
			if(shortDescription.length() > Constants.SHORT_DESCRIPTION_MAX_LENGTH){
				shortDescription = shortDescription.substring(0, Constants.SHORT_DESCRIPTION_MAX_LENGTH) + "...";
			}
		}
	    return shortDescription;
    }
	
	public String getPercent() {
        return percent;
    }
	
	public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }

	public String getMetaShareTitle() {
		String metaShareTitle = getName();
		metaShareTitle = encodeQuote(metaShareTitle);
	    return metaShareTitle;
    }
	
	public String getMetaShareDescription() {
		String metaShareDescription = getDescription();
		metaShareDescription = encodeQuote(metaShareDescription);
		if(StringUtils.isNotEmpty(metaShareDescription)){
			metaShareDescription = removeHtml(metaShareDescription);
			metaShareDescription = encodeQuote(metaShareDescription);
			if(metaShareDescription.length() > Constants.SHARE_META_DESCRIPTION_MAX_LENGTH){
				metaShareDescription = metaShareDescription.substring(0, Constants.SHARE_META_DESCRIPTION_MAX_LENGTH) + "...";
			}
		}
	    return metaShareDescription;
    }
	
    public String getMetaShareImage() {
        return getImg();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getChangeContextUrl() {
        return changeContextUrl;
    }

    public void setChangeContextUrl(String changeContextUrl) {
        this.changeContextUrl = changeContextUrl;
    }
    
    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }
    
}