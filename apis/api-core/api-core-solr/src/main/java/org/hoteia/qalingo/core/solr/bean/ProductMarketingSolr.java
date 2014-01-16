/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr.bean;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class ProductMarketingSolr {

	@Field
	private Long id;

	@Field
	private String description;

	private Boolean isDefault;

	@Field
	private String code;

	@Field
	private String businessname;

    @Field
	private String defaultCategoryCode;

    @Field
	private String defaultProductSkuCode;

    @Field
    private String price;
    
    @Field("datecreate")
	private Date dateCreate;

    @Field("dateupdate")
	private Date dateUpdate;

	public String getBusinessname() {
		return businessname;
	}

	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDefaultCategoryCode() {
		return defaultCategoryCode;
	}

	public void setDefaultCategoryCode(String defaultCategoryCode) {
		this.defaultCategoryCode = defaultCategoryCode;
	}

	public String getDefaultProductSkuCode() {
		return defaultProductSkuCode;
	}

	public void setDefaultProductSkuCode(String defaultProductSkuCode) {
		this.defaultProductSkuCode = defaultProductSkuCode;
	}
	
	public String getPrice() {
        return price;
    }
	
	public void setPrice(String price) {
        this.price = price;
    }

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

}