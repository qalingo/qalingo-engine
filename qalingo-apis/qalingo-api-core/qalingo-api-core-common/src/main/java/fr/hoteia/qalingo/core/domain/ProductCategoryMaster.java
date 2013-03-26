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
@Table(name="TECO_PRODUCT_CATEGORY_MASTER", uniqueConstraints = {@UniqueConstraint(columnNames= {"code"})})
@FilterDefs(
	value = {
			@FilterDef(name="filterProductCategoryMasterAttributeIsGlobal"),
			@FilterDef(name="filterProductCategoryMasterAttributeByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") })
	})
public class ProductCategoryMaster implements Serializable {

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
	private ProductCategoryMaster defaultParentProductCategory;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_CATEGORY_ID")
	@Filter(name="filterProductCategoryMasterAttributeIsGlobal", condition="IS_GLOBAL = '1'")
	private Set<ProductCategoryMasterAttribute> productCategoryGlobalAttributes = new HashSet<ProductCategoryMasterAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_CATEGORY_ID")
	@Filter(name="filterProductCategoryMasterAttributeByMarketArea", condition="IS_GLOBAL = '0' AND MARKET_AREA_ID = :marketAreaId")
	private Set<ProductCategoryMasterAttribute> productCategoryMarketAreaAttributes = new HashSet<ProductCategoryMasterAttribute>(); 
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.domain.ProductCategoryMaster.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_PRODUCT_CATEGORY_MASTER_CHILD_CATEGORY_REL",
	        joinColumns=@JoinColumn(name="PARENT_MASTER_PRODUCT_CATEGORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="CHILD_MASTER_PRODUCT_CATEGORY_ID")
	    )	
	private Set<ProductCategoryMaster> productCategories = new HashSet<ProductCategoryMaster>();
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.domain.ProductMarketing.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_PRODUCT_CATEGORY_MASTER_PRODUCT_MARKETING_REL",
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

	public ProductCategoryMaster(){
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
		if(getDefaultParentProductCategory() == null){
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
	
	public ProductCategoryMaster getDefaultParentProductCategory() {
		return defaultParentProductCategory;
	}
	
	public void setDefaultParentProductCategory(ProductCategoryMaster defaultParentProductCategory) {
		this.defaultParentProductCategory = defaultParentProductCategory;
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
	
	public Set<ProductCategoryMasterAttribute> getProductCategoryGlobalAttributes() {
		return productCategoryGlobalAttributes;
	}

	public void setProductCategoryGlobalAttributes(
			Set<ProductCategoryMasterAttribute> productCategoryGlobalAttributes) {
		this.productCategoryGlobalAttributes = productCategoryGlobalAttributes;
	}

	public Set<ProductCategoryMasterAttribute> getProductCategoryMarketAreaAttributes() {
		return productCategoryMarketAreaAttributes;
	}

	public void setProductCategoryMarketAreaAttributes(
			Set<ProductCategoryMasterAttribute> productCategoryMarketAreaAttributes) {
		this.productCategoryMarketAreaAttributes = productCategoryMarketAreaAttributes;
	}

	public Set<ProductCategoryMaster> getProductCategories() {
		return productCategories;
	}
	
	public void setProductCategories(Set<ProductCategoryMaster> productCategories) {
		this.productCategories = productCategories;
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
	
	public ProductCategoryMasterAttribute getProductCategoryAttribute(String attributeCode) {
		return getProductCategoryAttribute(attributeCode, null, null);
	}
	
	public ProductCategoryMasterAttribute getProductCategoryAttribute(String attributeCode, String localizationCode) {
		return getProductCategoryAttribute(attributeCode, null, localizationCode);
	}
	
	public ProductCategoryMasterAttribute getProductCategoryAttribute(String attributeCode, Long marketAreaId) {
		return getProductCategoryAttribute(attributeCode, marketAreaId, null);
	}
	
	public ProductCategoryMasterAttribute getProductCategoryAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductCategoryMasterAttribute productCategoryAttributeToReturn = null;
		List<ProductCategoryMasterAttribute> productCategoryAttributesFilter = new ArrayList<ProductCategoryMasterAttribute>();
		if(productCategoryMarketAreaAttributes != null) {
			for (Iterator<ProductCategoryMasterAttribute> iterator = productCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
				ProductCategoryMasterAttribute productCategoryAttribute = (ProductCategoryMasterAttribute) iterator.next();
				AttributeDefinition attributeDefinition = productCategoryAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					productCategoryAttributesFilter.add(productCategoryAttribute);
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<ProductCategoryMasterAttribute> iterator = productCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					ProductCategoryMasterAttribute productCategoryAttribute = (ProductCategoryMasterAttribute) iterator.next();
					AttributeDefinition attributeDefinition = productCategoryAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = productCategoryAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode)
								&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
			}
		}
		if(productCategoryGlobalAttributes != null) {
			for (Iterator<ProductCategoryMasterAttribute> iterator = productCategoryGlobalAttributes.iterator(); iterator.hasNext();) {
				ProductCategoryMasterAttribute productCategoryAttribute = (ProductCategoryMasterAttribute) iterator.next();
				AttributeDefinition attributeDefinition = productCategoryAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					productCategoryAttributesFilter.add(productCategoryAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<ProductCategoryMasterAttribute> iterator = productCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					ProductCategoryMasterAttribute productCategoryAttribute = (ProductCategoryMasterAttribute) iterator.next();
					AttributeDefinition attributeDefinition = productCategoryAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(productCategoryAttribute.getMarketAreaId() != null
								&& BooleanUtils.negate(productCategoryAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<ProductCategoryMasterAttribute> iterator = productCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					ProductCategoryMasterAttribute productCategoryAttribute = (ProductCategoryMasterAttribute) iterator.next();
					String attributeLocalizationCode = productCategoryAttribute.getLocalizationCode();
					if(StringUtils.isNotEmpty(attributeLocalizationCode)
							&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
						iterator.remove();
					}
				}
			}
		}
		if(productCategoryAttributesFilter.size() == 1){
			productCategoryAttributeToReturn = productCategoryAttributesFilter.get(0);
		} else {
			// TODO : throw error ?
		}
				
		return productCategoryAttributeToReturn;
	}
	
	public Object getValue(String attributeCode) {
		return getValue(attributeCode, null);
	}
	
	public Object getValue(String attributeCode, String localizationCode) {
		ProductCategoryMasterAttribute productCategoryAttribute = getProductCategoryAttribute(attributeCode, localizationCode);
		if(productCategoryAttribute != null) {
			return productCategoryAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nName(String localizationCode) {
		String i18nName = (String) getValue(ProductCategoryMasterAttribute.PRODUCT_CATEGORY_ATTRIBUTE_I18N_NAME, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getBusinessName();
		}
		return i18nName;
	}
	
	public Integer getOrder() {
		return (Integer) getValue(ProductCategoryMasterAttribute.PRODUCT_CATEGORY_ATTRIBUTE_ORDER, null);
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
				+ ((defaultParentProductCategory == null) ? 0
						: defaultParentProductCategory.hashCode());
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
		ProductCategoryMaster other = (ProductCategoryMaster) obj;
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
		if (defaultParentProductCategory == null) {
			if (other.defaultParentProductCategory != null)
				return false;
		} else if (!defaultParentProductCategory
				.equals(other.defaultParentProductCategory))
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
				+ defaultParentProductCategory
				+ ", productCategoryGlobalAttributes="
				+ productCategoryGlobalAttributes
				+ ", productCategoryMarketAreaAttributes="
				+ productCategoryMarketAreaAttributes + ", productCategories="
				+ productCategories + ", productMarketings="
				+ productMarketings + ", dateCreate=" + dateCreate
				+ ", dateUpdate=" + dateUpdate + "]";
	}
	
}