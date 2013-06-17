/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.enumtype.AssetType;

@Entity
@Table(name="TECO_PRODUCT_SKU", uniqueConstraints = {@UniqueConstraint(columnNames= {"code"})})
@FilterDefs(
	value = {
		@FilterDef(name="filterProductSkuAttributeIsGlobal"),
		@FilterDef(name="filterProductSkuAttributeByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") }),
		@FilterDef(name="filterProductSkuAssetIsGlobal"),
		@FilterDef(name="filterProductSkuAssetByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") }),
		@FilterDef(name="filterProductSkuPriceByMarketAreaAndRetailer", parameters= { @ParamDef(name="marketAreaId", type="long"),  @ParamDef(name="retailerId", type="long") }),
		@FilterDef(name="filterProductSkuStockByMarketAreaAndRetailer", parameters= { @ParamDef(name="marketAreaId", type="long"),  @ParamDef(name="retailerId", type="long") })
	}
)
public class ProductSku implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -2239109542749803870L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="BUSINESS_NAME")
	private String businessName;

	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;
	
	@Column(name="CODE", nullable=false)
	private String code;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_SKU_ID")
	@Filter(name="filterProductSkuAttributeIsGlobal", condition="IS_GLOBAL = '1'")
	private Set<ProductSkuAttribute> productSkuGlobalAttributes = new HashSet<ProductSkuAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_SKU_ID")
	@Filter(name="filterProductSkuAttributeByMarketArea", condition="MARKET_AREA_ID = :marketAreaId")
	private Set<ProductSkuAttribute> productSkuMarketAreaAttributes = new HashSet<ProductSkuAttribute>(); 
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETING_ID", insertable=false, updatable=false)
	private ProductMarketing productMarketing;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_SKU_ID")
	@Filter(name="filterAssetIsGlobal", condition="IS_GLOBAL = '1' AND SCOPE = 'PRODUCT_SKU'")
	@OrderBy(clause = "ordering asc")
	private Set<Asset> assetsIsGlobal = new HashSet<Asset>(); 

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_SKU_ID")
	@Filter(name="filterAssetByMarketArea", condition="IS_GLOBAL = '0' AND MARKET_AREA_ID = :marketAreaId AND SCOPE = 'PRODUCT_SKU'")
	@OrderBy(clause = "ordering asc")
	private Set<Asset> assetsByMarketArea = new HashSet<Asset>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_SKU_ID")
	@Filter(name="filterProductSkuPriceByMarketAreaAndRetailer", condition="MARKET_AREA_ID = :marketAreaId AND RETAILER_ID = :retailerId")
	private Set<ProductSkuPrice> prices = new HashSet<ProductSkuPrice>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_SKU_ID")
	@Filter(name="filterProductSkuStockByMarketAreaAndRetailer", condition="MARKET_AREA_ID = :marketAreaId AND RETAILER_ID = :retailerId")
	private Set<ProductSkuStock> stocks = new HashSet<ProductSkuStock>(); 
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.domain.Retailer.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_PRODUCT_SKU_RETAILER_REL",
	        joinColumns=@JoinColumn(name="PRODUCT_SKU_ID"),
	        inverseJoinColumns=@JoinColumn(name="RETAILER_ID")
	    )	
	private Set<Retailer> retailers = new HashSet<Retailer>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public ProductSku(){
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

	public boolean isDefault() {
		return isDefault;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public Set<ProductSkuAttribute> getProductSkuGlobalAttributes() {
		return productSkuGlobalAttributes;
	}

	public void setProductSkuGlobalAttributes(
			Set<ProductSkuAttribute> productSkuGlobalAttributes) {
		this.productSkuGlobalAttributes = productSkuGlobalAttributes;
	}

	public Set<ProductSkuAttribute> getProductSkuMarketAreaAttributes() {
		return productSkuMarketAreaAttributes;
	}

	public void setProductSkuMarketAreaAttributes(
			Set<ProductSkuAttribute> productSkuMarketAreaAttributes) {
		this.productSkuMarketAreaAttributes = productSkuMarketAreaAttributes;
	}

	public ProductMarketing getProductMarketing() {
		return productMarketing;
	}
	
	public void setProductMarketing(ProductMarketing productMarketing) {
		this.productMarketing = productMarketing;
	}
	
	public Set<Asset> getAssetsIsGlobal() {
		return assetsIsGlobal;
	}
	
	public void setAssetsIsGlobal(Set<Asset> assetsIsGlobal) {
		this.assetsIsGlobal = assetsIsGlobal;
	}
	
	public Set<Asset> getAssetsByMarketArea() {
		return assetsByMarketArea;
	}
	
	public void setAssetsByMarketArea(Set<Asset> assetsByMarketArea) {
		this.assetsByMarketArea = assetsByMarketArea;
	}
	
	public Set<ProductSkuPrice> getPrices() {
		return prices;
	}
	
	public void setPrices(Set<ProductSkuPrice> prices) {
		this.prices = prices;
	}
	
	public Set<ProductSkuStock> getStocks() {
		return stocks;
	}
	
	public void setStocks(Set<ProductSkuStock> stocks) {
		this.stocks = stocks;
	}
	
	public Set<Retailer> getRetailers() {
		return retailers;
	}
	
	public void setRetailers(Set<Retailer> retailers) {
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

	// Attributes
	
	public ProductSkuAttribute getProductSkuAttribute(String attributeCode) {
		return getProductSkuAttribute(attributeCode, null, null);
	}
	
	public ProductSkuAttribute getProductSkuAttribute(String attributeCode, String localizationCode) {
		return getProductSkuAttribute(attributeCode, null, localizationCode);
	}
	
	public ProductSkuAttribute getProductSkuAttribute(String attributeCode, Long marketAreaId) {
		return getProductSkuAttribute(attributeCode, marketAreaId, null);
	}
	
	public ProductSkuAttribute getProductSkuAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductSkuAttribute productSkuAttributeToReturn = null;
		if(productSkuMarketAreaAttributes != null) {
			List<ProductSkuAttribute> productSkuAttributesFilter = new ArrayList<ProductSkuAttribute>();
			for (Iterator<ProductSkuAttribute> iterator = productSkuMarketAreaAttributes.iterator(); iterator.hasNext();) {
				ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
				AttributeDefinition attributeDefinition = productSkuAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					productSkuAttributesFilter.add(productSkuAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<ProductSkuAttribute> iterator = productSkuAttributesFilter.iterator(); iterator.hasNext();) {
					ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
					AttributeDefinition attributeDefinition = productSkuAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(productSkuAttribute.getMarketAreaId() != null
								&& BooleanUtils.negate(productSkuAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
				if(productSkuAttributesFilter.size() == 0){
					// TODO : throw error ?
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<ProductSkuAttribute> iterator = productSkuAttributesFilter.iterator(); iterator.hasNext();) {
					ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
					AttributeDefinition attributeDefinition = productSkuAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = productSkuAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode)
								&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
				if(productSkuAttributesFilter.size() == 0){
					// TODO : throw error ?
					
					for (Iterator<ProductSkuAttribute> iterator = productSkuMarketAreaAttributes.iterator(); iterator.hasNext();) {
						ProductSkuAttribute productSkuAttribute = (ProductSkuAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(productSkuAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)){
							productSkuAttributeToReturn = productSkuAttribute;
						}
					}
					
				}
			}
			if(productSkuAttributesFilter.size() == 1){
				productSkuAttributeToReturn = productSkuAttributesFilter.get(0);
			} else {
				// TODO : throw error ?
			}
		}
		return productSkuAttributeToReturn;
	}
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductSkuAttribute productSkuAttribute = getProductSkuAttribute(attributeCode, marketAreaId, localizationCode);
		if(productSkuAttribute != null) {
			return productSkuAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nName(String localizationCode) {
		String i18nName = (String) getValue(ProductSkuAttribute.PRODUCT_SKU_ATTRIBUTE_I18N_NAME, null, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getBusinessName();
		}
		return i18nName;
	}
	
	public Integer getOrder(Long marketAreaId) {
		return (Integer) getValue(ProductSkuAttribute.PRODUCT_SKU_ATTRIBUTE_ORDER, marketAreaId, null);
	}

	// ASSET
	public Asset getDefaultPaskshotImage(String size) {
		Asset defaultProductImage = null;
		if(getAssetsIsGlobal() != null
				&& StringUtils.isNotEmpty(size)){
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productAsset = (Asset) iterator.next();
				if(AssetType.PACKSHOT.equals(productAsset.getType())
						&& size.equals(productAsset.getSize())
						&& productAsset.isDefault()){
					defaultProductImage = productAsset;
				}
			}
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.PACKSHOT.equals(productImage.getType())
						&& size.equals(productImage.getSize())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
	public Asset getDefaultBackgroundImage() {
		Asset defaultProductImage = null;
		if(getAssetsIsGlobal() != null){
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.BACKGROUND.equals(productImage.getType())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.BACKGROUND.equals(productImage.getType())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
	public Asset getDefaultIconImage() {
		Asset defaultProductImage = null;
		if(getAssetsIsGlobal() != null){
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.ICON.equals(productImage.getType())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.ICON.equals(productImage.getType())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result
				+ ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isDefault ? 1231 : 1237);
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductSku other = (ProductSku) obj;
		if (businessName == null) {
			if (other.businessName != null)
				return false;
		} else if (!businessName.equals(other.businessName))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (dateCreate == null) {
			if (other.dateCreate != null)
				return false;
		} else if (!dateCreate.equals(other.dateCreate))
			return false;
		if (dateUpdate == null) {
			if (other.dateUpdate != null)
				return false;
		} else if (!dateUpdate.equals(other.dateUpdate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDefault != other.isDefault)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductSku [id=" + id + ", version=" + version
				+ ", businessName=" + businessName + ", description="
				+ description + ", isDefault=" + isDefault + ", code=" + code
				+ ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
				+ "]";
	}
	
}