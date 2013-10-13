/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.rest.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
import fr.hoteia.qalingo.core.json.pojo.AbstractJsonPojo;

/**
 *
 * <p>
 * <a href="Customer.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductCategoryJsonPojo extends AbstractJsonPojo {
	
	private Long id;
	private int version;
	private String businessName;
	private String description;
	private String code;
	private boolean isDefault;
	private boolean isRoot;
	
	private List<CatalogCategoryVirtualAttribute>  productCategoryVirtualAttribute = new ArrayList<CatalogCategoryVirtualAttribute>();
	
	private List<CatalogCategoryVirtualAttribute>  productCategoryMarketAreaAttributes = new ArrayList<CatalogCategoryVirtualAttribute>();
	
	private List<ProductCategoryJsonPojo>  productCategories = new ArrayList<ProductCategoryJsonPojo>();

	private List<ProductMarketingJsonPojo>  productMarketings = new ArrayList<ProductMarketingJsonPojo>();
	
	private List<Asset> assets = new ArrayList<Asset>();
	
	private Date dateCreate;
	private Date dateUpdate;

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

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public List<CatalogCategoryVirtualAttribute> getProductCategoryVirtualAttribute() {
		return productCategoryVirtualAttribute;
	}

	public void setProductCategoryVirtualAttribute(List<CatalogCategoryVirtualAttribute> productCategoryVirtualAttribute) {
		this.productCategoryVirtualAttribute = productCategoryVirtualAttribute;
	}

	public List<CatalogCategoryVirtualAttribute> getProductCategoryMarketAreaAttributes() {
		return productCategoryMarketAreaAttributes;
	}

	public void setProductCategoryMarketAreaAttributes(List<CatalogCategoryVirtualAttribute> productCategoryMarketAreaAttributes) {
		this.productCategoryMarketAreaAttributes = productCategoryMarketAreaAttributes;
	}

	public List<ProductCategoryJsonPojo> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(List<ProductCategoryJsonPojo> productCategories) {
		this.productCategories = productCategories;
	}

	public List<ProductMarketingJsonPojo> getProductMarketings() {
		return productMarketings;
	}

	public void setProductMarketings(List<ProductMarketingJsonPojo> productMarketings) {
		this.productMarketings = productMarketings;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setImages(List<Asset> assets) {
		this.assets = assets;
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

	@JsonAnySetter
	@Override
	public void handleUnknown(String key, Object value) {
		super.handleUnknown(key, value);
	}
}