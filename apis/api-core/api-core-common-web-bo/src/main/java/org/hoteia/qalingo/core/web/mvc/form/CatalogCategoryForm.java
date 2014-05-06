/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.form;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class CatalogCategoryForm {
	
    private String id;
	private String code;
    private String name;
	private String description;
    private String ranking;
    private String catalogCode;
    private String defaultParentCategoryId;
    private String masterCategoryId;
    
	private Map<String, String> globalAttributes = new HashMap<String, String>();
	private Map<String, String> marketAreaAttributes = new HashMap<String, String>();

    public String getId() {
		if(id == null){
			return "";
		}
		return id;
	}
    
    public void setId(String id) {
		this.id = id;
	}
    
    @NotEmpty(message = "bo.catalog_category.error_form_code_is_empty")
    public String getCode() {
        if(code == null){
            return "";
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @NotEmpty(message = "bo.catalog_category.error_form_name_is_empty")
    public String getName() {
		if(name == null){
			return "";
		}
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		if(description == null){
			return "";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRanking() {
	    if(ranking == null){
	        return "0";
	    }
        return ranking;
    }
	
	public void setRanking(String ranking) {
        this.ranking = ranking;
    }
	
    public String getCatalogCode() {
        if(catalogCode == null){
            return "";
        }
        return catalogCode;
    }
    
    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public String getDefaultParentCategoryId() {
        return defaultParentCategoryId;
    }
    
    public void setDefaultParentCategoryId(String defaultParentCategoryId) {
        this.defaultParentCategoryId = defaultParentCategoryId;
    }
    
    public String getMasterCategoryId() {
        return masterCategoryId;
    }
    
    public void setMasterCategoryId(String masterCategoryId) {
        this.masterCategoryId = masterCategoryId;
    }
    
	public Map<String, String> getGlobalAttributes() {
		return globalAttributes;
	}
	
	public void setGlobalAttributes(Map<String, String> globalAttributes) {
		this.globalAttributes = globalAttributes;
	}
	
	public Map<String, String> getMarketAreaAttributes() {
		return marketAreaAttributes;
	}
	
	public void setMarketAreaAttributes(Map<String, String> marketAreaAttributes) {
		this.marketAreaAttributes = marketAreaAttributes;
	}
}