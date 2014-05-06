/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private String name;

	@Field
	private String defaultCategoryCode;

	@Field
	private String defaultProductSkuCode;

    @Field
    private List<String> catalogCode = new ArrayList<String>();
    
	@Field
	private Float price;

	@Field("datecreate")
	private Date dateCreate;

	@Field("dateupdate")
	private Date dateUpdate;

	@Field
	private List<String> catalogCategories = new ArrayList<String>();

	public String getName() {
        return name;
    }
	
	public void setName(String name) {
        this.name = name;
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

	public List<String> getCatalogCode() {
        return catalogCode;
    }
	
	public void setCatalogCode(List<String> catalogCode) {
        this.catalogCode = catalogCode;
    }
	
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
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

	public List<String> getCatalogCategories() {
		return catalogCategories;
	}

	public void setCatalogCategories(List<String> catalogCategories) {
		this.catalogCategories = catalogCategories;
	}
	
	public void addCatalogCategories(String catalogCategoryCode){
		if(this.catalogCategories == null){
			this.catalogCategories = new ArrayList<String>();
		}
		
		if(!this.catalogCategories.contains(catalogCategoryCode)){
			this.catalogCategories.add(catalogCategoryCode);
		}
	}
}