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
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

@Entity
@Table(name="TECO_PRODUCT_MARKETING")
public class ProductMarketing extends AbstractExtendEntity<ProductMarketing, ProductMarketingAttribute> {

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

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "IS_DEFAULT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefault;

    @Column(name = "IS_ENABLED_TO_B2C", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean enabledToB2C;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductBrand.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID", insertable = true, updatable = true)
    private ProductBrand productBrand;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketingType.class)
    @JoinColumn(name = "PRODUCT_MARKETING_TYPE_ID", insertable = true, updatable = true)
    private ProductMarketingType productMarketingType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketingAttribute.class)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductMarketingAttribute> attributes = new HashSet<ProductMarketingAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductSku> productSkus = new HashSet<ProductSku>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductAssociationLink.class)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductAssociationLink> productAssociationLinks = new HashSet<ProductAssociationLink>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Asset.class)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate.class)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductMarketingCustomerRate> customerRates = new HashSet<ProductMarketingCustomerRate>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment.class)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductMarketingCustomerComment> customerComments = new HashSet<ProductMarketingCustomerComment>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketingTagRel.class)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductMarketingTagRel> tagRels = new HashSet<ProductMarketingTagRel>();
    
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

    public boolean isEnabledToB2C() {
        return enabledToB2C;
    }

    public void setEnabledToB2C(boolean enabledToB2C) {
        this.enabledToB2C = enabledToB2C;
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
	
	public Set<ProductSku> getProductSkus() {
		return productSkus;
	}
	
	public ProductSku getDefaultProductSku() {
		ProductSku defaultProductSku = null;
		if(productSkus != null
		        && Hibernate.isInitialized(productSkus)
		        && !productSkus.isEmpty()){
			for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSku productSku = (ProductSku) iterator.next();
				if(productSku.isDefault()){
					defaultProductSku = productSku;
				}
			}
			if(defaultProductSku == null){
				defaultProductSku = (ProductSku) productSkus.iterator().next();
			}
		}
		return defaultProductSku;
	}
	
	public List<String> getSkuCodes() {
        List<String> skuCodes = new ArrayList<String>();
        if(productSkus != null
                && Hibernate.isInitialized(productSkus)
                && !productSkus.isEmpty()){
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
    
    public Set<ProductMarketingCustomerRate> getCustomerRates() {
        return customerRates;
    }

    public void setCustomerRates(Set<ProductMarketingCustomerRate> customerRates) {
        this.customerRates = customerRates;
    }

    public Set<ProductMarketingCustomerComment> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(Set<ProductMarketingCustomerComment> customerComments) {
        this.customerComments = customerComments;
    }
    
    public Set<ProductMarketingTagRel> getTagRels() {
        return tagRels;
    }
    
    public List<Tag> getTags() {
        List<Tag> tags = null;
        if (Hibernate.isInitialized(tagRels) && !tagRels.isEmpty()) {
            tags = new ArrayList<Tag>();
            for (Iterator<ProductMarketingTagRel> iterator = tagRels.iterator(); iterator.hasNext();) {
                ProductMarketingTagRel productMarketingTagRel = (ProductMarketingTagRel) iterator.next();
                if(Hibernate.isInitialized(productMarketingTagRel.getPk().getTag()) && productMarketingTagRel.getPk().getTag() != null){
                    tags.add(productMarketingTagRel.getProductMarketingTag());
                }
            }
        }
        return tags;
    }
    
    public void setTagRels(Set<ProductMarketingTagRel> tagRels) {
        this.tagRels = tagRels;
    }
	
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
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
	    AbstractAttribute attribute = getAttribute(attributeCode, marketAreaId, localizationCode);
		if(attribute != null) {
			return attribute.getValue();
		}
		return null;
	}
	
    public String getI18nName(String localizationCode) {
        String i18nName = (String) getValue(ProductMarketingAttribute.PRODUCT_MARKETING_ATTRIBUTE_I18N_NAME, null, localizationCode);
        if (StringUtils.isEmpty(i18nName)) {
            i18nName = getName();
        }
        return i18nName;
    }

    public String getI18nDescription(String localizationCode) {
        String i18Description = (String) getValue(ProductMarketingAttribute.PRODUCT_MARKETING_ATTRIBUTE_I18N_DESCRIPTION, null, localizationCode);
        if (StringUtils.isNotEmpty(i18Description)) {
            return i18Description;
        }
        return description;
    }

    public Boolean isFeatured() {
        Boolean isFeatured = (Boolean) getValue(ProductMarketingAttribute.PRODUCT_MARKETING_ATTRIBUTE_FEATURED, null, null);
        if (isFeatured == null) {
            return Boolean.FALSE;
        } else {
            return isFeatured;
        }
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
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
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