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

public class RetailerViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387549593050575454L;
	
	protected String code;
	protected String name;
	protected String description;
	
	protected boolean isDefault;
	protected boolean isOfficialRetailer;
	
	protected boolean isBrand;
	protected boolean isEcommerce;
	
	protected String img;
	protected String url;
	
	protected int score = 0;

	protected RetailerAddressViewBean address = new RetailerAddressViewBean();
	
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

	public boolean isDefault() {
    	return isDefault;
    }

	public void setDefault(boolean isDefault) {
    	this.isDefault = isDefault;
    }

	public boolean isOfficialRetailer() {
    	return isOfficialRetailer;
    }

	public void setOfficialRetailer(boolean isOfficialRetailer) {
    	this.isOfficialRetailer = isOfficialRetailer;
    }

	public boolean isBrand() {
    	return isBrand;
    }

	public void setBrand(boolean isBrand) {
    	this.isBrand = isBrand;
    }

	public boolean isEcommerce() {
    	return isEcommerce;
    }

	public void setEcommerce(boolean isEcommerce) {
    	this.isEcommerce = isEcommerce;
    }

	public String getImg() {
    	return img;
    }

	public void setImg(String img) {
    	this.img = img;
    }

	public String getUrl() {
    	return url;
    }

	public void setUrl(String url) {
    	this.url = url;
    }

	public int getScore() {
    	return score;
    }

	public void setScore(int score) {
    	this.score = score;
    }
	
	public RetailerAddressViewBean getAddress() {
	    return address;
    }
	
	public void setAddress(RetailerAddressViewBean address) {
	    this.address = address;
    }
	
}