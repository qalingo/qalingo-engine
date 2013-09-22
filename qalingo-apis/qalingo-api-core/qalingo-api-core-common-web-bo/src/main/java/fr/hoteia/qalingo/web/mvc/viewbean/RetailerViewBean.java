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
	
	// MENU
	protected String img;
	protected String changeContextUrl;

	// ENTITY
	private Long id;
	private int version;
	protected String code;
	protected String name;
	private String description;
	
	private RetailerAddressViewBean defaultAddress = new RetailerAddressViewBean();
	
	private String createdDate;
	private String updatedDate;

	private String detailsUrl;
	private String editUrl;
	
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
	
	public RetailerAddressViewBean getDefaultAddress() {
	    return defaultAddress;
    }
	
	public void setDefaultAddress(RetailerAddressViewBean defaultAddress) {
	    this.defaultAddress = defaultAddress;
    }

	public String getCreatedDate() {
    	return createdDate;
    }

	public void setCreatedDate(String createdDate) {
    	this.createdDate = createdDate;
    }

	public String getUpdatedDate() {
    	return updatedDate;
    }

	public void setUpdatedDate(String updatedDate) {
    	this.updatedDate = updatedDate;
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

}