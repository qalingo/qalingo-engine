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
import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.beans.Field;

public class CatalogCategorySolr {

    @Field
	private Long id;
	
    @Field
    private String name;

    @Field
	private String description;
	
    @Field
	private Boolean isDefault;
	
    @Field
	private Boolean isMaster;
	
    @Field
	private String code;
	
    @Field
    private Set<CatalogCategorySolr> catalogCategories = new HashSet<CatalogCategorySolr>();
    
    @Field
    private Set<ProductSkuSolr> productMarketings = new HashSet<ProductSkuSolr>();
    
    @Field("datecreate")
    private Date dateCreate;

    @Field("dateupdate")
    private Date dateUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(Boolean isMaster) {
		this.isMaster = isMaster;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<CatalogCategorySolr> getCatalogCategories() {
		return catalogCategories;
	}

	public void setCatalogCategories(Set<CatalogCategorySolr> catalogCategories) {
		this.catalogCategories = catalogCategories;
	}

	public Set<ProductSkuSolr> getProductMarketings() {
		return productMarketings;
	}

	public void setProductMarketings(Set<ProductSkuSolr> productMarketings) {
		this.productMarketings = productMarketings;
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