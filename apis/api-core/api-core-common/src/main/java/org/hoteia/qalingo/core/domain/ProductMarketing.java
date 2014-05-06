/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.AssetType;

@Entity
@Table(name="TECO_PRODUCT_MARKETING", uniqueConstraints = {@UniqueConstraint(columnNames= {"CODE"})})
public class ProductMarketing extends AbstractEntity {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 5408836788685407465L;

    public static final String CACHE_NAME = "web_cache_product_marketing";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "IS_DEFAULT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRAND_ID", insertable = true, updatable = true)
    private ProductBrand productBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_MARKETING_TYPE_ID", insertable = true, updatable = true)
    private ProductMarketingType productMarketingType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductMarketingAttribute> attributes = new HashSet<ProductMarketingAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductSku> productSkus = new HashSet<ProductSku>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductAssociationLink> productAssociationLinks = new HashSet<ProductAssociationLink>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @Transient
    private int ranking;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
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
	
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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

	public ProductBrand getProductBrand() {
		return productBrand;
	}
	
	public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
	}
	
	public ProductMarketingType getProductMarketingType() {
		return productMarketingType;
	}
	
	public void setProductMarketingType(ProductMarketingType productMargetingType) {
		this.productMarketingType = productMargetingType;
	}
	
	public Set<ProductMarketingAttribute> getAttributes() {
        return attributes;
    }
	
	public void setAttributes(Set<ProductMarketingAttribute> attributes) {
        this.attributes = attributes;
    }
	
	public List<ProductMarketingAttribute> getGlobalAttributes() {
        List<ProductMarketingAttribute> productMarketingGlobalAttributes = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            productMarketingGlobalAttributes = new ArrayList<ProductMarketingAttribute>();
            for (Iterator<ProductMarketingAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                ProductMarketingAttribute attribute = (ProductMarketingAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && attributeDefinition.isGlobal()) {
                    productMarketingGlobalAttributes.add(attribute);
                }
            }
        }        
        return productMarketingGlobalAttributes;
	}

	public List<ProductMarketingAttribute> getMarketAreaAttributes(Long marketAreaId) {
        List<ProductMarketingAttribute> productMarketingMarketAreaAttributes = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            productMarketingMarketAreaAttributes = new ArrayList<ProductMarketingAttribute>();
            for (Iterator<ProductMarketingAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                ProductMarketingAttribute attribute = (ProductMarketingAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && !attributeDefinition.isGlobal()) {
                    productMarketingMarketAreaAttributes.add(attribute);
                }
            }
        }        
        return productMarketingMarketAreaAttributes;
	}

	public Set<ProductSku> getProductSkus() {
		return productSkus;
	}
	
	public ProductSku getDefaultProductSku() {
		ProductSku defaultProductSku = null;
		Set<ProductSku> productSkus = getProductSkus();
		if(productSkus != null
		        && Hibernate.isInitialized(productSkus)){
			for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSku productSku = (ProductSku) iterator.next();
				if(productSku.isDefault()){
					defaultProductSku = productSku;
				}
			}
			if(defaultProductSku == null){
				Iterator<ProductSku> iterator = productSkus.iterator();
				defaultProductSku = (ProductSku) iterator.next();
			}
		}
		return defaultProductSku;
	}
	
	public List<String> getSkuCodes() {
        List<String> skuCodes = new ArrayList<String>();
        if(productSkus != null
                && Hibernate.isInitialized(productSkus)){
            for (Iterator<ProductSku> iterator = getProductSkus().iterator(); iterator.hasNext();) {
                ProductSku sku = (ProductSku) iterator.next();
                skuCodes.add(sku.getCode());
            }
        }
        return skuCodes;
	}
	
	public void setProductSkus(Set<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}
	
	public Set<ProductAssociationLink> getProductAssociationLinks() {
		return productAssociationLinks;
	}
	
	public void setProductAssociationLinks(Set<ProductAssociationLink> productAssociationLinks) {
		this.productAssociationLinks = productAssociationLinks;
	}
	
	public Set<Asset> getAssets() {
        return assets;
    }
	
	public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }
	
	public List<Asset> getAssetsIsGlobal() {
        List<Asset> assetsIsGlobal = null;
        if (assets != null
                && Hibernate.isInitialized(assets)) {
            assetsIsGlobal = new ArrayList<Asset>();
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                if (asset != null 
                        && asset.isGlobal()) {
                    assetsIsGlobal.add(asset);
                }
            }
        }        
        return assetsIsGlobal;
	}
	
	public List<Asset> getAssetsByMarketArea() {
        List<Asset> assetsByMarketArea = null;
        if (assets != null
                && Hibernate.isInitialized(assets)) {
            assetsByMarketArea = new ArrayList<Asset>();
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                if (asset != null 
                        && !asset.isGlobal()) {
                    assetsByMarketArea.add(asset);
                }
            }
        }        
        return assetsByMarketArea;
	}
	
//	public CatalogCategoryVirtual getDefaultCatalogCategory() {
//	    if(productSkus != null
//                && Hibernate.isInitialized(productSkus)){
//            for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
//                ProductSku productSku = (ProductSku) iterator.next();
//                if(productSku.isDefault()){
//                    if(productSku.getDefaultCatalogCategory() != null
//                            && Hibernate.isInitialized(productSku.getDefaultCatalogCategory())){
//                        // DEFAULT CATEGORY FOR THE DEFAULT SKU - BEST SOLUTION
//                        return productSku.getDefaultCatalogCategory();
//                    }
//                }
//            }
//            // NO DEFAULT CATEGORY FOR THE DEFAULT SKU - RETURN A FALLBACK VALUE
//            for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
//                ProductSku productSku = (ProductSku) iterator.next();
//                if(productSku.getDefaultCatalogCategory() != null
//                        && Hibernate.isInitialized(productSku.getDefaultCatalogCategory())){
//                    // DEFAULT CATEGORY FOR THE DEFAULT SKU - BEST SOLUTION
//                    return productSku.getDefaultCatalogCategory();
//                }
//            }
//	    }
//        return null;
//    }
	
	public int getRanking() {
        return ranking;
    }
	
	public void setRanking(int ranking) {
        this.ranking = ranking;
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

		// 1: GET THE GLOBAL VALUE
		ProductMarketingAttribute productMarketingGlobalAttribute = getProductMarketingAttribute(getGlobalAttributes(), attributeCode, marketAreaId, localizationCode);

		// 2: GET THE MARKET AREA VALUE
		ProductMarketingAttribute productMarketingMarketAreaAttribute = getProductMarketingAttribute(getMarketAreaAttributes(marketAreaId), attributeCode, marketAreaId, localizationCode);
		
		if(productMarketingMarketAreaAttribute != null){
			productMarketingAttributeToReturn = productMarketingMarketAreaAttribute;
		} else if (productMarketingGlobalAttribute != null){
			productMarketingAttributeToReturn = productMarketingGlobalAttribute;
		}
		
		return productMarketingAttributeToReturn;
	}
	
	private ProductMarketingAttribute getProductMarketingAttribute(List<ProductMarketingAttribute> attributes, String attributeCode, Long marketAreaId, String localizationCode) {
		ProductMarketingAttribute productMarketingAttributeToReturn = null;
		List<ProductMarketingAttribute> attributesFilter = new ArrayList<ProductMarketingAttribute>();
		if(attributes != null) {
			// GET ALL attributes FOR THIS ATTRIBUTE
			for (Iterator<ProductMarketingAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
				ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
				AttributeDefinition attributeDefinition = productMarketingAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					attributesFilter.add(productMarketingAttribute);
				}
			}
			// REMOVE ALL attributes NOT ON THIS MARKET AREA
			if(marketAreaId != null) {
				for (Iterator<ProductMarketingAttribute> iterator = attributesFilter.iterator(); iterator.hasNext();) {
					ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
					AttributeDefinition attributeDefinition = productMarketingAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(productMarketingAttribute.getMarketAreaId() != null
								&& BooleanUtils.negate(productMarketingAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
			}
			// FINALLY RETAIN ONLY attributes FOR THIS LOCALIZATION CODE
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<ProductMarketingAttribute> iterator = attributesFilter.iterator(); iterator.hasNext();) {
					ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
					String attributeLocalizationCode = productMarketingAttribute.getLocalizationCode();
					if(StringUtils.isNotEmpty(attributeLocalizationCode)
							&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
						iterator.remove();
					}
				}
				if(attributesFilter.size() == 0){
					// TODO : warning ?

					// NOT ANY attributes FOR THIS LOCALIZATION CODE - GET A FALLBACK
					for (Iterator<ProductMarketingAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
						ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(Constants.DEFAULT_LOCALE_CODE.equals(productMarketingAttribute.getLocalizationCode())){
							productMarketingAttributeToReturn = productMarketingAttribute;
						}
					}
				}
			}
		}
		
		if(attributesFilter.size() == 1){
			productMarketingAttributeToReturn = attributesFilter.get(0);
		} else {
			// TODO : throw error ?
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
		String i18nName = (String) getValue(ProductMarketingAttribute.PRODUCT_MARKETING_ATTRIBUTE_I18N_NAME, null, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getName();
		}
		return i18nName;
	}
	
   public Boolean isFeatured(){
       Boolean isFeatured = (Boolean) getValue(ProductMarketingAttribute.PRODUCT_MARKETING_ATTRIBUTE_FEATURED, null, null);
        if (isFeatured == null) {
            return Boolean.FALSE;
        } else {
            return isFeatured;
        }
    }
  
	// ASSET
   
	public Asset getDefaultPaskshotImage(String size) {
		Asset defaultProductImage = null;
		List<Asset> assetsIsGlobal = getAssetsIsGlobal();
		if(assetsIsGlobal != null
		        && Hibernate.isInitialized(assetsIsGlobal)
				&& size != null){
			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
				Asset productAsset = (Asset) iterator.next();
				if(AssetType.PACKSHOT.equals(productAsset.getType())
						&& size.equals(productAsset.getSize().name())
						&& productAsset.isDefault()){
					defaultProductImage = productAsset;
				}
			}
			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.PACKSHOT.equals(productImage.getType())
						&& size.equals(productImage.getSize().name())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
	public Asset getDefaultBackgroundImage() {
		Asset defaultProductImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if(assetsIsGlobal != null
                && Hibernate.isInitialized(assetsIsGlobal)){
			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.BACKGROUND.equals(productImage.getType())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
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
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if(assetsIsGlobal != null
                && Hibernate.isInitialized(assetsIsGlobal)){
			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.ICON.equals(productImage.getType())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
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
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        ProductMarketing other = (ProductMarketing) obj;
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductMarketing [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", isDefault=" + isDefault + ", code=" + code
                + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
	
}