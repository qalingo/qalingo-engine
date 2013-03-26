/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.form;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class ProductCategoryForm {
	
    private String id;
	private String catalogCode;
	private String name;
	private String defaultParentCategoryCode;
	private String code;
	private String description;

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
    
    public String getCatalogCode() {
		if(catalogCode == null){
			return "";
		}
		return catalogCode;
	}
    
    public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}
    
	@NotEmpty(message = "error.form.product.category.name.is.empty")
	public String getName() {
		if(name == null){
			return "";
		}
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultParentCategoryCode() {
		return defaultParentCategoryCode;
	}
	
	public void setDefaultParentCategoryCode(String defaultParentCategoryCode) {
		this.defaultParentCategoryCode = defaultParentCategoryCode;
	}
	
	public String getCode() {
		if(code == null){
			return "";
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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