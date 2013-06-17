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

import fr.hoteia.qalingo.core.domain.ProductBrand;
import fr.hoteia.qalingo.core.domain.ProductAssociationLink;
import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.ProductMarketingAttribute;

/**
 *
 * <p>
 * <a href="ProductMarketingJsonPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductMarketingJsonPojo extends AbstractJsonPojo {
	
	private Long id;
	private int version;
	private String businessName;
	private String description;
	private boolean isDefault;
	private String code;
	private ProductBrand productBrand;

	private List<ProductMarketingAttribute> productMarketingGlobalAttributes = new ArrayList<ProductMarketingAttribute>(); 

	private List<ProductMarketingAttribute> productMarketingMarketAreaAttributes = new ArrayList<ProductMarketingAttribute>(); 
	
	private List<ProductSkuJsonPojo> productSkus = new ArrayList<ProductSkuJsonPojo>();
	
	private List<ProductAssociationLink> productCrossLinks = new ArrayList<ProductAssociationLink>();
	
	private List<Asset> assets = new ArrayList<Asset>(); 
	
	private Date dateCreate;
	private Date dateUpdate;

	public ProductMarketingJsonPojo() {
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

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ProductBrand getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
	}

	public List<ProductMarketingAttribute> getProductMarketingGlobalAttributes() {
		return productMarketingGlobalAttributes;
	}

	public void setProductMarketingGlobalAttributes(
			List<ProductMarketingAttribute> productMarketingGlobalAttributes) {
		this.productMarketingGlobalAttributes = productMarketingGlobalAttributes;
	}

	public List<ProductMarketingAttribute> getProductMarketingMarketAreaAttributes() {
		return productMarketingMarketAreaAttributes;
	}

	public void setProductMarketingMarketAreaAttributes(
			List<ProductMarketingAttribute> productMarketingMarketAreaAttributes) {
		this.productMarketingMarketAreaAttributes = productMarketingMarketAreaAttributes;
	}

	public List<ProductSkuJsonPojo> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSkuJsonPojo> productSkus) {
		this.productSkus = productSkus;
	}

	public List<ProductAssociationLink> getProductCrossLinks() {
		return productCrossLinks;
	}

	public void setProductCrossLinks(List<ProductAssociationLink> productCrossLinks) {
		this.productCrossLinks = productCrossLinks;
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