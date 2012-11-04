/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
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
@Table(name="TECO_PRODUCT_MARKETING")
@FilterDefs(
	value = {
		@FilterDef(name="filterProductMarketingAttributeIsGlobal"),
		@FilterDef(name="filterProductMarketingAttributeByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") })
	})
public class ProductMarketing implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5408836788685407465L;

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
	
	@Column(name="CODE")
	private String code;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="BRAND_ID", insertable=false, updatable=false)
	private ProductBrand productBrand;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETTING_ID")
	@Filter(name="filterProductMarketingAttributeIsGlobal", condition="IS_GLOBAL = '1'")
	private Set<ProductMarketingAttribute> productMarketingGlobalAttributes = new HashSet<ProductMarketingAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETTING_ID")
	@Filter(name="filterProductMarketingAttributeByMarketArea", condition="MARKET_AREA_ID = :marketAreaId")
	private Set<ProductMarketingAttribute> productMarketingMarketAreaAttributes = new HashSet<ProductMarketingAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETING_ID")
	private Set<ProductSku> productSkus = new HashSet<ProductSku>();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETING_ID")
	private Set<ProductCrossLink> productCrossLinks = new HashSet<ProductCrossLink>();
	
	@ManyToMany(
			fetch = FetchType.EAGER,
	        targetEntity=fr.hoteia.qalingo.core.common.domain.ProductImage.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_PRODUCT_MARKETING_IMAGE_REL",
	        joinColumns=@JoinColumn(name="PRODUCT_MARKETING_ID"),
	        inverseJoinColumns=@JoinColumn(name="PRODUCT_IMAGE_ID")
	    )	
	private Set<ProductImage> images = new HashSet<ProductImage>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public ProductMarketing(){
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
	
	public ProductBrand getProductBrand() {
		return productBrand;
	}
	
	public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
	}
	
	public Set<ProductMarketingAttribute> getProductMarketingGlobalAttributes() {
		return productMarketingGlobalAttributes;
	}

	public void setProductMarketingGlobalAttributes(Set<ProductMarketingAttribute> productMarketingGlobalAttributes) {
		this.productMarketingGlobalAttributes = productMarketingGlobalAttributes;
	}

	public Set<ProductMarketingAttribute> getProductMarketingMarketAreaAttributes() {
		return productMarketingMarketAreaAttributes;
	}

	public void setProductMarketingMarketAreaAttributes(Set<ProductMarketingAttribute> productMarketingMarketAreaAttributes) {
		this.productMarketingMarketAreaAttributes = productMarketingMarketAreaAttributes;
	}

	public Set<ProductSku> getProductSkus() {
		return productSkus;
	}
	
	public ProductSku getDefaultProductSku() {
		ProductSku defaultProductSku = null;
		Set<ProductSku> productSkus = getProductSkus();
		if(productSkus != null
				&& productSkus.size() > 0){
			for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSku productSku = (ProductSku) iterator.next();
				if(productSku.isDefault()){
					defaultProductSku = productSku;
				}
			}
		}
		if(defaultProductSku == null){
			Iterator<ProductSku> iterator = productSkus.iterator();
			defaultProductSku = (ProductSku) iterator.next();
		}
		
		return defaultProductSku;
	}
	
	public void setProductSkus(Set<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}
	
	public Set<ProductCrossLink> getProductCrossLinks() {
		return productCrossLinks;
	}
	
	public void setProductCrossLinks(Set<ProductCrossLink> productCrossLinks) {
		this.productCrossLinks = productCrossLinks;
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
	
	public ProductMarketingAttribute getProductMarketingAttribute(String attributeCode) {
		return getProductMarketingAttribute(attributeCode, null, null);
	}
	
	public ProductMarketingAttribute getProductMarketingAttribute(String attributeCode, String localizationCode) {
		return getProductMarketingAttribute(attributeCode, null, localizationCode);
	}
	
	public ProductMarketingAttribute getProductMarketingAttribute(String attributeCode, Long marketAreaId) {
		return getProductMarketingAttribute(attributeCode, marketAreaId, null);
	}
	
	public ProductMarketingAttribute getProductMarketingAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductMarketingAttribute productMarketingAttributeToReturn = null;
		if(productMarketingMarketAreaAttributes != null) {
			List<ProductMarketingAttribute> productMarketingAttributesFilter = new ArrayList<ProductMarketingAttribute>();
			for (Iterator<ProductMarketingAttribute> iterator = productMarketingMarketAreaAttributes.iterator(); iterator.hasNext();) {
				ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
				AttributeDefinition attributeDefinition = productMarketingAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					productMarketingAttributesFilter.add(productMarketingAttribute);
				}
			}
			if(marketAreaId != null) {
				for (Iterator<ProductMarketingAttribute> iterator = productMarketingAttributesFilter.iterator(); iterator.hasNext();) {
					ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
					AttributeDefinition attributeDefinition = productMarketingAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(productMarketingAttribute.getMarketAreaId() != null
								&& BooleanUtils.negate(productMarketingAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
				if(productMarketingAttributesFilter.size() == 0){
					// TODO : throw error ?
				}
			}
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<ProductMarketingAttribute> iterator = productMarketingAttributesFilter.iterator(); iterator.hasNext();) {
					ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
					AttributeDefinition attributeDefinition = productMarketingAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						String attributeLocalizationCode = productMarketingAttribute.getLocalizationCode();
						if(StringUtils.isNotEmpty(attributeLocalizationCode)
								&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
							iterator.remove();
						}
					}
				}
				if(productMarketingAttributesFilter.size() == 0){
					// TODO : throw error ?
					
					for (Iterator<ProductMarketingAttribute> iterator = productMarketingMarketAreaAttributes.iterator(); iterator.hasNext();) {
						ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(productMarketingAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)){
							productMarketingAttributeToReturn = productMarketingAttribute;
						}
					}
					
				}
			}
			if(productMarketingAttributesFilter.size() == 1){
				productMarketingAttributeToReturn = productMarketingAttributesFilter.get(0);
			} else {
				// TODO : throw error ?
			}
		}
		return productMarketingAttributeToReturn;
	}
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductMarketingAttribute productMarketingAttribute = getProductMarketingAttribute(attributeCode, marketAreaId, localizationCode);
		if(productMarketingAttribute != null) {
			return productMarketingAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nName(String localizationCode) {
		String i18nName = (String) getValue(ProductSkuAttribute.PRODUCT_MARKETING_ATTRIBUTE_I18N_NAME, null, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getBusinessName();
		}
		return i18nName;
	}
	
	public Integer getOrder(Long marketAreaId) {
		return (Integer) getValue(ProductSkuAttribute.PRODUCT_MARKETING_ATTRIBUTE_ORDER, marketAreaId, null);
	}

}
