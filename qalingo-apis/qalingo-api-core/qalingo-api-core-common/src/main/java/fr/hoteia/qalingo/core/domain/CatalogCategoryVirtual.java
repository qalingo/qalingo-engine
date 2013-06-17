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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
@Table(name="TECO_CATALOG_VIRTUAL_CATEGORY", uniqueConstraints = {@UniqueConstraint(columnNames= {"code"})})
@FilterDefs(
	value = {
			@FilterDef(name="filterCatalogVirtualCategoryAttributeIsGlobal"),
			@FilterDef(name="filterCatalogVirtualCategoryAttributeByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") }),
			@FilterDef(name="filterCatalogVirtualCategoryAssetIsGlobal"),
			@FilterDef(name="filterCatalogVirtualCategoryAssetByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") })
	})
public class CatalogCategoryVirtual implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4953461049508842305L;

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
	
	@Column(name="CODE", nullable=false)
	private String code;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;

//	@Column(name="IS_ROOT", nullable=false, columnDefinition="tinyint(1) default 0")
//	private boolean isRoot;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name="DEFAULT_PARENT_CATEGORY_ID")
	private CatalogCategoryVirtual defaultParentCatalogCategory;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="MASTER_CATEGORY_ID")
	private CatalogCategoryMaster categoryMaster;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="VIRTUAL_CATEGORY_ID")
	@Filter(name="filterCatalogCategoryVirtualAttributeIsGlobal", condition="IS_GLOBAL = '1'")
	@OrderBy(clause = "ordering asc")
	private Set<CatalogCategoryVirtualAttribute> catalogCategoryGlobalAttributes = new HashSet<CatalogCategoryVirtualAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="VIRTUAL_CATEGORY_ID")
	@Filter(name="filterCatalogCategoryVirtualAttributeByMarketArea", condition="IS_GLOBAL = '0' AND MARKET_AREA_ID = :marketAreaId")
	@OrderBy(clause = "ordering asc")
	private Set<CatalogCategoryVirtualAttribute> catalogCategoryMarketAreaAttributes = new HashSet<CatalogCategoryVirtualAttribute>(); 
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_CATALOG_VIRTUAL_CATEGORY_CHILD_CATEGORY_REL",
	        joinColumns=@JoinColumn(name="PARENT_VIRTUAL_CATALOG_CATEGORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="CHILD_VIRTUAL_CATALOG_CATEGORY_ID")
	    )	
	private Set<CatalogCategoryVirtual> catalogCategories = new HashSet<CatalogCategoryVirtual>();
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.domain.ProductMarketing.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_CATALOG_VIRTUAL_CATEGORY_PRODUCT_MARKETING_REL",
	        joinColumns=@JoinColumn(name="VIRTUAL_CATEGORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="PRODUCT_MARKETING_ID")
	    )	
	private Set<ProductMarketing> productMarketings = new HashSet<ProductMarketing>();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="VIRTUAL_CATEGORY_ID")
	@Filter(name="filterCatalogVirtualCategoryAssetIsGlobal", condition="IS_GLOBAL = '1' AND SCOPE = 'VIRTUAL_CATEGORY'")
	@OrderBy(clause = "ordering asc")
	private Set<Asset> assetsIsGlobal = new HashSet<Asset>(); 

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="VIRTUAL_CATEGORY_ID")
	@Filter(name="filterCatalogVirtualCategoryAssetByMarketArea", condition="IS_GLOBAL = '0' AND MARKET_AREA_ID = :marketAreaId AND SCOPE = 'VIRTUAL_CATEGORY'")
	@OrderBy(clause = "ordering asc")
	private Set<Asset> assetsByMarketArea = new HashSet<Asset>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public CatalogCategoryVirtual(){
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

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public CatalogCategoryType getCatalogCategoryType() {
		return getCategoryMaster().getCatalogCategoryType();
	}
	
	public boolean isDefault() {
		return isDefault;
	}
	
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public boolean isRoot() {
		if(getDefaultParentCatalogCategory() == null){
			return false;
		}
		return true;
	}
	
	public CatalogCategoryVirtual getDefaultParentCatalogCategory() {
		return defaultParentCatalogCategory;
	}
	
	public void setDefaultParentCatalogCategory(CatalogCategoryVirtual defaultParentCatalogCategory) {
		this.defaultParentCatalogCategory = defaultParentCatalogCategory;
	}
	
	public CatalogCategoryMaster getCategoryMaster() {
		return categoryMaster;
	}
	
	public void setCategoryMaster(CatalogCategoryMaster categoryMaster) {
		this.categoryMaster = categoryMaster;
	}
	
	public Set<CatalogCategoryVirtualAttribute> getCatalogCategoryGlobalAttributes() {
		return catalogCategoryGlobalAttributes;
	}

	public void setCatalogCategoryGlobalAttributes(Set<CatalogCategoryVirtualAttribute> catalogCategoryGlobalAttributes) {
		this.catalogCategoryGlobalAttributes = catalogCategoryGlobalAttributes;
	}

	public Set<CatalogCategoryVirtualAttribute> getCatalogCategoryMarketAreaAttributes() {
		return catalogCategoryMarketAreaAttributes;
	}

	public void setCatalogCategoryMarketAreaAttributes(Set<CatalogCategoryVirtualAttribute> catalogCategoryMarketAreaAttributes) {
		this.catalogCategoryMarketAreaAttributes = catalogCategoryMarketAreaAttributes;
	}

	public Set<CatalogCategoryVirtual> getCatalogCategories() {
		return catalogCategories;
	}
	
	public List<CatalogCategoryVirtual> getCatalogCategories(final Long marketAreaId) {
		List<CatalogCategoryVirtual> sortedObjects = new LinkedList<CatalogCategoryVirtual>(catalogCategories);
		Collections.sort(sortedObjects, new Comparator<CatalogCategoryVirtual>() {
			@Override
			public int compare(CatalogCategoryVirtual o1, CatalogCategoryVirtual o2) {
				if(o1 != null
						&& o2 != null){
					Integer order1 = o1.getOrder(marketAreaId);
					Integer order2 = o2.getOrder(marketAreaId);
					if(order1 != null
							&& order2 != null){
						return order1.compareTo(order2);				
					} else {
						return o1.getId().compareTo(o2.getId());	
					}
				}
				return 0;
			}
		});
		return sortedObjects;
	}
	
	public void setCatalogCategories(Set<CatalogCategoryVirtual> catalogCategories) {
		this.catalogCategories = catalogCategories;
	}
	
	public Set<ProductMarketing> getProductMarketings() {
		return productMarketings;
	}
	
	public void setProductMarketings(Set<ProductMarketing> productMarketings) {
		this.productMarketings = productMarketings;
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
	
	public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode) {
		return getCatalogCategoryAttribute(attributeCode, null, null);
	}
	
	public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, String localizationCode) {
		return getCatalogCategoryAttribute(attributeCode, null, localizationCode);
	}
	
	public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, Long marketAreaId) {
		return getCatalogCategoryAttribute(attributeCode, marketAreaId, null);
	}
	
	public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		CatalogCategoryVirtualAttribute catalogCategoryAttributeToReturn = null;
		List<CatalogCategoryVirtualAttribute> catalogCategoryAttributesFilter = new ArrayList<CatalogCategoryVirtualAttribute>();
		if(catalogCategoryMarketAreaAttributes != null) {
			for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
				CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
				AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					catalogCategoryAttributesFilter.add(catalogCategoryAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
					AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(catalogCategoryAttribute.getMarketAreaId() != null
								&& BooleanUtils.negate(catalogCategoryAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
					AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = catalogCategoryAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode)
								&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
				if(catalogCategoryAttributesFilter.size() == 0){
					// TODO : throw error ?
					
					for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
						CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(catalogCategoryAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)){
							catalogCategoryAttributeToReturn = catalogCategoryAttribute;
						}
					}
				}
			}
		}
		
		if(catalogCategoryGlobalAttributes != null) {
			for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryGlobalAttributes.iterator(); iterator.hasNext();) {
				CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
				AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					catalogCategoryAttributesFilter.add(catalogCategoryAttribute);
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
					AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = catalogCategoryAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode)
								&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
				if(catalogCategoryAttributesFilter.size() == 0){
					// TODO : throw error ?
					
					for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
						CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(catalogCategoryAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)){
							catalogCategoryAttributeToReturn = catalogCategoryAttribute;
						}
					}
				}
			}
		}
		
		if(catalogCategoryAttributesFilter.size() == 1){
			catalogCategoryAttributeToReturn = catalogCategoryAttributesFilter.get(0);
		} else {
			// TODO : throw error ?
		}
		
		return catalogCategoryAttributeToReturn;
	}
	
	public Object getValue(String attributeCode) {
		return getValue(attributeCode, null, null);
	}
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
		CatalogCategoryVirtualAttribute catalogCategoryAttribute = getCatalogCategoryAttribute(attributeCode, marketAreaId, localizationCode);
		if(catalogCategoryAttribute != null) {
			return catalogCategoryAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nName(String localizationCode) {
		String i18nName = (String) getValue(CatalogCategoryVirtualAttribute.CATALOG_CATEGORY_ATTRIBUTE_I18N_NAME, null, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getBusinessName();
		}
		return i18nName;
	}
	
	public Integer getOrder(Long marketAreaId) {
		return (Integer) getValue(CatalogCategoryVirtualAttribute.CATALOG_CATEGORY_ATTRIBUTE_ORDER, marketAreaId, null);
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
		result = prime
				* result
				+ ((defaultParentCatalogCategory == null) ? 0
						: defaultParentCatalogCategory.hashCode());
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
		CatalogCategoryVirtual other = (CatalogCategoryVirtual) obj;
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
		if (defaultParentCatalogCategory == null) {
			if (other.defaultParentCatalogCategory != null)
				return false;
		} else if (!defaultParentCatalogCategory
				.equals(other.defaultParentCatalogCategory))
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
		return "ProductCategoryVirtual [id=" + id + ", version=" + version
				+ ", businessName=" + businessName + ", description="
				+ description + ", code=" + code + ", isDefault=" + isDefault
				+ ", defaultParentProductCategory="
				+ defaultParentCatalogCategory
				+ ", productCategoryGlobalAttributes="
				+ catalogCategoryGlobalAttributes
				+ ", productCategoryMarketAreaAttributes="
				+ catalogCategoryMarketAreaAttributes + ", productCategories="
				+ catalogCategories + ", productMarketings="
				+ productMarketings + ", dateCreate=" + dateCreate
				+ ", dateUpdate=" + dateUpdate + "]";
	}
}
