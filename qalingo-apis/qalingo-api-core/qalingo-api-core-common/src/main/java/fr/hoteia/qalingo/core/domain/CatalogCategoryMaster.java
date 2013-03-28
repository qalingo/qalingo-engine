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
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name="TECO_CATALOG_CATEGORY_MASTER", uniqueConstraints = {@UniqueConstraint(columnNames= {"code"})})
@FilterDefs(
	value = {
			@FilterDef(name="filterCatalogCategoryMasterAttributeIsGlobal"),
			@FilterDef(name="filterCatalogCategoryMasterAttributeByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") })
	})
public class CatalogCategoryMaster implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -9123721692905623486L;

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
	private CatalogCategoryMaster defaultParentCatalogCategory;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="CATALOG_CATEGORY_ID")
	@Filter(name="filterCatalogCategoryMasterAttributeIsGlobal", condition="IS_GLOBAL = '1'")
	private Set<CatalogCategoryMasterAttribute> catalogCategoryGlobalAttributes = new HashSet<CatalogCategoryMasterAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="CATALOG_CATEGORY_ID")
	@Filter(name="filterCatalogCategoryMasterAttributeByMarketArea", condition="IS_GLOBAL = '0' AND MARKET_AREA_ID = :marketAreaId")
	private Set<CatalogCategoryMasterAttribute> catalogCategoryMarketAreaAttributes = new HashSet<CatalogCategoryMasterAttribute>(); 
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.domain.CatalogCategoryMaster.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_CATALOG_CATEGORY_MASTER_CHILD_CATEGORY_REL",
	        joinColumns=@JoinColumn(name="PARENT_MASTER_CATALOG_CATEGORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="CHILD_MASTER_CATALOG_CATEGORY_ID")
	    )	
	private Set<CatalogCategoryMaster> catalogCategories = new HashSet<CatalogCategoryMaster>();
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.domain.ProductMarketing.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_CATALOG_CATEGORY_MASTER_PRODUCT_MARKETING_REL",
	        joinColumns=@JoinColumn(name="MASTER_CATEGORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="PRODUCT_MARKETING_ID")
	    )	
	private Set<ProductMarketing> productMarketings = new HashSet<ProductMarketing>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public CatalogCategoryMaster(){
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
	
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public boolean isRoot() {
		if(getDefaultParentCatalogCategory() == null){
			return false;
		}
		return true;
	}
	
//	public boolean isRoot() {
//	return isRoot;
//}
//
//public void setRoot(boolean isRoot) {
//	this.isRoot = isRoot;
//}
	
	public CatalogCategoryMaster getDefaultParentCatalogCategory() {
		return defaultParentCatalogCategory;
	}
	
	public void setDefaultParentCatalogCategory(CatalogCategoryMaster defaultParentCatalogCategory) {
		this.defaultParentCatalogCategory = defaultParentCatalogCategory;
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
	
	public Set<CatalogCategoryMasterAttribute> getCatalogCategoryGlobalAttributes() {
		return catalogCategoryGlobalAttributes;
	}

	public void setCatalogCategoryGlobalAttributes(Set<CatalogCategoryMasterAttribute> catalogCategoryGlobalAttributes) {
		this.catalogCategoryGlobalAttributes = catalogCategoryGlobalAttributes;
	}

	public Set<CatalogCategoryMasterAttribute> getCatalogCategoryMarketAreaAttributes() {
		return catalogCategoryMarketAreaAttributes;
	}

	public void setCatalogCategoryMarketAreaAttributes(Set<CatalogCategoryMasterAttribute> catalogCategoryMarketAreaAttributes) {
		this.catalogCategoryMarketAreaAttributes = catalogCategoryMarketAreaAttributes;
	}

	public Set<CatalogCategoryMaster> getCatalogCategories() {
		return catalogCategories;
	}
	
	public void setCatalogCategories(Set<CatalogCategoryMaster> catalogCategories) {
		this.catalogCategories = catalogCategories;
	}
	
	public Set<ProductMarketing> getProductMarketings() {
		return productMarketings;
	}
	
	public void setProductMarketings(Set<ProductMarketing> productMarketings) {
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

	// Attributes
	
	public CatalogCategoryMasterAttribute getCatalogCategoryAttribute(String attributeCode) {
		return getCatalogCategoryAttribute(attributeCode, null, null);
	}
	
	public CatalogCategoryMasterAttribute getCatalogCategoryAttribute(String attributeCode, String localizationCode) {
		return getCatalogCategoryAttribute(attributeCode, null, localizationCode);
	}
	
	public CatalogCategoryMasterAttribute getProductCategoryAttribute(String attributeCode, Long marketAreaId) {
		return getCatalogCategoryAttribute(attributeCode, marketAreaId, null);
	}
	
	public CatalogCategoryMasterAttribute getCatalogCategoryAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		CatalogCategoryMasterAttribute catalogCategoryAttributeToReturn = null;
		List<CatalogCategoryMasterAttribute> catalogCategoryAttributesFilter = new ArrayList<CatalogCategoryMasterAttribute>();
		if(catalogCategoryMarketAreaAttributes != null) {
			for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
				CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
				AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					catalogCategoryAttributesFilter.add(catalogCategoryAttribute);
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
					AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = catalogCategoryAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode)
								&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
			}
		}
		if(catalogCategoryGlobalAttributes != null) {
			for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryGlobalAttributes.iterator(); iterator.hasNext();) {
				CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
				AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					catalogCategoryAttributesFilter.add(catalogCategoryAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
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
				for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
					String attributeLocalizationCode = catalogCategoryAttribute.getLocalizationCode();
					if(StringUtils.isNotEmpty(attributeLocalizationCode)
							&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
						iterator.remove();
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
		return getValue(attributeCode, null);
	}
	
	public Object getValue(String attributeCode, String localizationCode) {
		CatalogCategoryMasterAttribute productCategoryAttribute = getCatalogCategoryAttribute(attributeCode, localizationCode);
		if(productCategoryAttribute != null) {
			return productCategoryAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nName(String localizationCode) {
		String i18nName = (String) getValue(CatalogCategoryMasterAttribute.CATALOG_CATEGORY_ATTRIBUTE_I18N_NAME, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getBusinessName();
		}
		return i18nName;
	}
	
	public Integer getOrder() {
		return (Integer) getValue(CatalogCategoryMasterAttribute.CATALOG_CATEGORY_ATTRIBUTE_ORDER, null);
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
		CatalogCategoryMaster other = (CatalogCategoryMaster) obj;
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
		return "ProductCategoryMaster [id=" + id + ", version=" + version
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