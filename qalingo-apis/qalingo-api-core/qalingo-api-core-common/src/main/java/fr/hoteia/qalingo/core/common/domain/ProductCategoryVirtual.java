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
import javax.persistence.ManyToOne;
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
import fr.hoteia.qalingo.core.common.domain.enumtype.ImageType;

@Entity
@Table(name="TECO_PRODUCT_CATEGORY_VIRTUAL")
@FilterDefs(
	value = {
		@FilterDef(name="filterProductCategoryVirtualAttributeIsGlobal"),
		@FilterDef(name="filterProductCategoryVirtualAttributeByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") })
	})
public class ProductCategoryVirtual implements Serializable {

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
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;

	@Column(name="IS_ROOT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isRoot;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="MASTER_CATEGORY_ID", insertable=false, updatable=false)
	private ProductCategoryMaster categoryMaster;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_CATEGORY_ID")
	@Filter(name="filterProductCategoryVirtualAttributeIsGlobal", condition="IS_GLOBAL = '1'")
	private Set<ProductCategoryVirtualAttribute> productCategoryGlobalAttributes = new HashSet<ProductCategoryVirtualAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_CATEGORY_ID")
	@Filter(name="filterProductCategoryVirtualAttributeByMarketArea", condition="MARKET_AREA_ID = :marketAreaId")
	private Set<ProductCategoryVirtualAttribute> productCategoryMarketAreaAttributes = new HashSet<ProductCategoryVirtualAttribute>(); 
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.common.domain.ProductCategoryVirtual.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_PRODUCT_CATEGORY_VIRTUAL_CHILD_CATEGORY_REL",
	        joinColumns=@JoinColumn(name="PARENT_VIRTUAL_PRODUCT_CATEGORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="CHILD_VIRTUAL_PRODUCT_CATEGORY_ID")
	    )	
	private Set<ProductCategoryVirtual> productCategories = new HashSet<ProductCategoryVirtual>();
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.common.domain.ProductMarketing.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_PRODUCT_CATEGORY_VIRTUAL_PRODUCT_MARKETING_REL",
	        joinColumns=@JoinColumn(name="VIRTUAL_CATEGORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="PRODUCT_MARKETING_ID")
	    )	
	private Set<ProductMarketing> productMarketings = new HashSet<ProductMarketing>();
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.common.domain.ProductImage.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_PRODUCT_CATEGORY_VIRTUAL_IMAGE_REL",
	        joinColumns=@JoinColumn(name="VIRTUAL_CATEGORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="PRODUCT_IMAGE_ID")
	    )	
	private Set<ProductImage> images = new HashSet<ProductImage>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public ProductCategoryVirtual(){
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
	
	public ProductCategoryMaster getCategoryMaster() {
		return categoryMaster;
	}
	
	public void setCategoryMaster(ProductCategoryMaster categoryMaster) {
		this.categoryMaster = categoryMaster;
	}
	
	public Set<ProductCategoryVirtualAttribute> getProductCategoryGlobalAttributes() {
		return productCategoryGlobalAttributes;
	}

	public void setProductCategoryGlobalAttributes(
			Set<ProductCategoryVirtualAttribute> productCategoryGlobalAttributes) {
		this.productCategoryGlobalAttributes = productCategoryGlobalAttributes;
	}

	public Set<ProductCategoryVirtualAttribute> getProductCategoryMarketAreaAttributes() {
		return productCategoryMarketAreaAttributes;
	}

	public void setProductCategoryMarketAreaAttributes(
			Set<ProductCategoryVirtualAttribute> productCategoryMarketAreaAttributes) {
		this.productCategoryMarketAreaAttributes = productCategoryMarketAreaAttributes;
	}

	public Set<ProductCategoryVirtual> getProductCategories() {
		return productCategories;
	}
	
	public void setProductCategories(Set<ProductCategoryVirtual> productCategories) {
		this.productCategories = productCategories;
	}
	
	public Set<ProductMarketing> getProductMarketings() {
		return productMarketings;
	}
	
	public void setProductMarketings(Set<ProductMarketing> productMarketings) {
		this.productMarketings = productMarketings;
	}

	public Set<ProductImage> getImages() {
		return images;
	}
	
	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}
	
	public ProductImage getDefaultPaskshotImage(String size) {
		ProductImage defaultProductImage = null;
		if(images != null
				&& StringUtils.isNotEmpty(size)){
			for (Iterator<ProductImage> iterator = images.iterator(); iterator.hasNext();) {
				ProductImage productImage = (ProductImage) iterator.next();
				if(ImageType.PACKSHOT.getPropertyKey().equals(productImage.getType())
						&& size.equals(productImage.getSize())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<ProductImage> iterator = images.iterator(); iterator.hasNext();) {
				ProductImage productImage = (ProductImage) iterator.next();
				if(ImageType.PACKSHOT.getPropertyKey().equals(productImage.getType())
						&& size.equals(productImage.getSize())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
	public ProductImage getDefaultBackgroundImage() {
		ProductImage defaultProductImage = null;
		if(images != null){
			for (Iterator<ProductImage> iterator = images.iterator(); iterator.hasNext();) {
				ProductImage productImage = (ProductImage) iterator.next();
				if(ImageType.BACKGROUND.getPropertyKey().equals(productImage.getType())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<ProductImage> iterator = images.iterator(); iterator.hasNext();) {
				ProductImage productImage = (ProductImage) iterator.next();
				if(ImageType.BACKGROUND.getPropertyKey().equals(productImage.getType())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
	public ProductImage getDefaultIconImage() {
		ProductImage defaultProductImage = null;
		if(images != null){
			for (Iterator<ProductImage> iterator = images.iterator(); iterator.hasNext();) {
				ProductImage productImage = (ProductImage) iterator.next();
				if(ImageType.ICON.getPropertyKey().equals(productImage.getType())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<ProductImage> iterator = images.iterator(); iterator.hasNext();) {
				ProductImage productImage = (ProductImage) iterator.next();
				if(ImageType.ICON.getPropertyKey().equals(productImage.getType())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
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
	
	public ProductCategoryVirtualAttribute getProductCategoryAttribute(String attributeCode) {
		return getProductCategoryAttribute(attributeCode, null, null);
	}
	
	public ProductCategoryVirtualAttribute getProductCategoryAttribute(String attributeCode, String localizationCode) {
		return getProductCategoryAttribute(attributeCode, null, localizationCode);
	}
	
	public ProductCategoryVirtualAttribute getProductCategoryAttribute(String attributeCode, Long marketAreaId) {
		return getProductCategoryAttribute(attributeCode, marketAreaId, null);
	}
	
	public ProductCategoryVirtualAttribute getProductCategoryAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductCategoryVirtualAttribute productCategoryAttributeToReturn = null;
		if(productCategoryMarketAreaAttributes != null) {
			List<ProductCategoryVirtualAttribute> productCategoryAttributesFilter = new ArrayList<ProductCategoryVirtualAttribute>();
			for (Iterator<ProductCategoryVirtualAttribute> iterator = productCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
				ProductCategoryVirtualAttribute productCategoryAttribute = (ProductCategoryVirtualAttribute) iterator.next();
				AttributeDefinition attributeDefinition = productCategoryAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					productCategoryAttributesFilter.add(productCategoryAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<ProductCategoryVirtualAttribute> iterator = productCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					ProductCategoryVirtualAttribute productCategoryAttribute = (ProductCategoryVirtualAttribute) iterator.next();
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
				for (Iterator<ProductCategoryVirtualAttribute> iterator = productCategoryAttributesFilter.iterator(); iterator.hasNext();) {
					ProductCategoryVirtualAttribute productCategoryAttribute = (ProductCategoryVirtualAttribute) iterator.next();
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
					
					for (Iterator<ProductCategoryVirtualAttribute> iterator = productCategoryMarketAreaAttributes.iterator(); iterator.hasNext();) {
						ProductCategoryVirtualAttribute productCategoryAttribute = (ProductCategoryVirtualAttribute) iterator.next();
						
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
		ProductCategoryVirtualAttribute productCategoryAttribute = getProductCategoryAttribute(attributeCode, marketAreaId, localizationCode);
		if(productCategoryAttribute != null) {
			return productCategoryAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nName(String localizationCode) {
		String i18nName = (String) getValue(ProductCategoryVirtualAttribute.PRODUCT_CATEGORY_ATTRIBUTE_I18N_NAME, null, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getBusinessName();
		}
		return i18nName;
	}
	
	public Integer getOrder(Long marketAreaId) {
		return (Integer) getValue(ProductCategoryVirtualAttribute.PRODUCT_CATEGORY_ATTRIBUTE_ORDER, marketAreaId, null);
	}
}
