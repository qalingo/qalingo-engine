/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;

import fr.hoteia.qalingo.core.Constants;

@Entity
@Table(name="TECO_PRODUCT_CATEGORY_MASTER")
@FilterDefs(
	value = {
		@FilterDef(name="filterProductCategoryMasterAttributeIsGlobal", parameters= { @ParamDef(name="isGlobal", type="boolean") }),
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
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;

	@Column(name="IS_ROOT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isRoot;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_CATEGORY_ID")
	@Filter(name="filterProductCategoryMasterAttributeIsGlobal", condition="IS_GLOBAL = '1'")
	private Set<ProductCategoryMasterAttribute> productCategoryGlobalAttributes = new HashSet<ProductCategoryMasterAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_CATEGORY_ID")
	@Filter(name="filterProductCategoryMasterAttributeByMarketArea", condition="MARKET_AREA_ID = :marketAreaId")
	private Set<ProductCategoryMasterAttribute> productCategoryMarketAreaAttributes = new HashSet<ProductCategoryMasterAttribute>(); 
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.common.domain.ProductCategoryMaster.class,
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
	        targetEntity=fr.hoteia.qalingo.core.common.domain.ProductMarketing.class,
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
	
	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
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
		if(productCategoryMarketAreaAttributes != null) {
			List<ProductCategoryMasterAttribute> productCategoryAttributesFilter = new ArrayList<ProductCategoryMasterAttribute>();
			for (Iterator<ProductCategoryMasterAttribute> iterator = productCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
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
				if(productCategoryAttributesFilter.size() == 0){
					// TODO : throw error ?
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
				if(productCategoryAttributesFilter.size() == 0){
					// TODO : throw error ?
					
					for (Iterator<ProductCategoryMasterAttribute> iterator = productCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
						ProductCategoryMasterAttribute productCategoryAttribute = (ProductCategoryMasterAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(productCategoryAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)){
							productCategoryAttributeToReturn = productCategoryAttribute;
						}
					}
				}
			}
			if(productCategoryAttributesFilter.size() == 1){
				productCategoryAttributeToReturn = productCategoryAttributesFilter.get(0);
			} else {
				// TODO : throw error ?
			}
		}
		return productCategoryAttributeToReturn;
	}
	
	public Object getValue(String attributeCode) {
		return getValue(attributeCode, null, null);
	}
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductCategoryMasterAttribute productCategoryAttribute = getProductCategoryAttribute(attributeCode, marketAreaId, localizationCode);
		if(productCategoryAttribute != null) {
			return productCategoryAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nName(String localizationCode) {
		String i18nName = (String) getValue(ProductCategoryMasterAttribute.PRODUCT_CATEGORY_ATTRIBUTE_I18N_NAME, null, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getBusinessName();
		}
		return i18nName;
	}
	
	public Integer getOrder(Long marketAreaId) {
		return (Integer) getValue(ProductCategoryMasterAttribute.PRODUCT_CATEGORY_ATTRIBUTE_ORDER, marketAreaId, null);
	}
}
