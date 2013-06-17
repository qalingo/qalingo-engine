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
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSkuAttribute;
import fr.hoteia.qalingo.core.domain.ProductSkuPrice;
import fr.hoteia.qalingo.core.domain.ProductSkuStock;
import fr.hoteia.qalingo.core.domain.Retailer;

/**
 *
 * <p>
 * <a href="ProductSkuJsonPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductSkuJsonPojo extends AbstractJsonPojo {
	
	private Long id;
	private int version;
	private String businessName;
	private String description;
	private boolean isDefault;
	private String code;
	
	private List<ProductSkuAttribute> productSkuGlobalAttributes = new ArrayList<ProductSkuAttribute>(); 
	private List<ProductSkuAttribute> productSkuMarketAreaAttributes = new ArrayList<ProductSkuAttribute>(); 
	
	private ProductMarketing productMarketing;
	
	private List<Asset> assets = new ArrayList<Asset>(); 
	private List<ProductSkuPrice> prices = new ArrayList<ProductSkuPrice>(); 
	private List<ProductSkuStock> stocks = new ArrayList<ProductSkuStock>(); 
	private List<Retailer> retailers = new ArrayList<Retailer>();
	
	private Date dateCreate;
	private Date dateUpdate;
	
	public ProductSkuJsonPojo() {
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

	public List<ProductSkuAttribute> getProductSkuGlobalAttributes() {
		return productSkuGlobalAttributes;
	}

	public void setProductSkuGlobalAttributes(
			List<ProductSkuAttribute> productSkuGlobalAttributes) {
		this.productSkuGlobalAttributes = productSkuGlobalAttributes;
	}

	public List<ProductSkuAttribute> getProductSkuMarketAreaAttributes() {
		return productSkuMarketAreaAttributes;
	}

	public void setProductSkuMarketAreaAttributes(
			List<ProductSkuAttribute> productSkuMarketAreaAttributes) {
		this.productSkuMarketAreaAttributes = productSkuMarketAreaAttributes;
	}

	public ProductMarketing getProductMarketing() {
		return productMarketing;
	}

	public void setProductMarketing(ProductMarketing productMarketing) {
		this.productMarketing = productMarketing;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setImages(List<Asset> assets) {
		this.assets = assets;
	}

	public List<ProductSkuPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<ProductSkuPrice> prices) {
		this.prices = prices;
	}

	public List<ProductSkuStock> getStocks() {
		return stocks;
	}

	public void setStocks(List<ProductSkuStock> stocks) {
		this.stocks = stocks;
	}

	public List<Retailer> getRetailers() {
		return retailers;
	}

	public void setRetailers(List<Retailer> retailers) {
		this.retailers = retailers;
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