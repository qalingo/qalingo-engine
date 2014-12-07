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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;

@Entity
@Table(name="TECO_PRODUCT_BRAND")
public class ProductBrand extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3980707210914384779L;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductBrandAttribute.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private Set<ProductBrandAttribute> attributes = new HashSet<ProductBrandAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Asset.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketing.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private Set<ProductMarketing> productMarketings = new HashSet<ProductMarketing>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

	public ProductBrand(){
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
	
    public Set<ProductBrandAttribute> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Set<ProductBrandAttribute> attributes) {
        this.attributes = attributes;
    }
    
    public List<ProductBrandAttribute> getGlobalAttributes() {
        List<ProductBrandAttribute> productBrandGlobalAttributes = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            productBrandGlobalAttributes = new ArrayList<ProductBrandAttribute>();
            for (Iterator<ProductBrandAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                ProductBrandAttribute attribute = (ProductBrandAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && attributeDefinition.isGlobal()) {
                    productBrandGlobalAttributes.add(attribute);
                }
            }
        }        
        return productBrandGlobalAttributes;
    }

    public List<ProductBrandAttribute> getMarketAreaAttributes(Long marketAreaId) {
        List<ProductBrandAttribute> productBrandMarketAreaAttributes = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            productBrandMarketAreaAttributes = new ArrayList<ProductBrandAttribute>();
            for (Iterator<ProductBrandAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                ProductBrandAttribute attribute = (ProductBrandAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && !attributeDefinition.isGlobal()) {
                    productBrandMarketAreaAttributes.add(attribute);
                }
            }
        }        
        return productBrandMarketAreaAttributes;
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
	
    // ATTRIBUTES

    public ProductBrandAttribute getAttribute(String attributeCode) {
        return getAttribute(attributeCode, null, null);
    }

    public ProductBrandAttribute getAttribute(String attributeCode, String localizationCode) {
        return getAttribute(attributeCode, null, localizationCode);
    }

    public ProductBrandAttribute getAttribute(String attributeCode, Long marketAreaId) {
        return getAttribute(attributeCode, marketAreaId, null);
    }

    public ProductBrandAttribute getAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
        ProductBrandAttribute attributeToReturn = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            List<ProductBrandAttribute> attributesFilter = new ArrayList<ProductBrandAttribute>();
            for (Iterator<ProductBrandAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                ProductBrandAttribute attribute = (ProductBrandAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    attributesFilter.add(attribute);
                }
            }
            if (marketAreaId != null) {
                for (Iterator<ProductBrandAttribute> iterator = attributesFilter.iterator(); iterator.hasNext();) {
                    ProductBrandAttribute attribute = (ProductBrandAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        if (attribute.getMarketAreaId() != null && BooleanUtils.negate(attribute.getMarketAreaId().equals(marketAreaId))) {
                            iterator.remove();
                        }
                    }
                }
                if (attributesFilter.size() == 0) {
                    // TODO : throw error ?
                }
            }
            if (StringUtils.isNotEmpty(localizationCode)) {
                for (Iterator<ProductBrandAttribute> iterator = attributesFilter.iterator(); iterator.hasNext();) {
                    ProductBrandAttribute attribute = (ProductBrandAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        String attributeLocalizationCode = attribute.getLocalizationCode();
                        if (StringUtils.isNotEmpty(attributeLocalizationCode) && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))) {
                            iterator.remove();
                        }
                    }
                }
                if (attributesFilter.size() == 0) {
                    // TODO : throw error ?
                }
            }
            if (attributesFilter.size() == 1) {
                attributeToReturn = attributesFilter.get(0);
            } else {
                // TODO : throw error ?
            }
        }
        return attributeToReturn;
    }

    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
        ProductBrandAttribute storeAttribute = getAttribute(attributeCode, marketAreaId, localizationCode);
        if (storeAttribute != null) {
            return storeAttribute.getValue();
        }
        return null;
    }

    public String getI18nName(String localizationCode) {
        String i18Name = (String) getValue(ProductBrandAttribute.PRODUCT_BRAND_ATTRIBUTE_I18N_NAME, null, localizationCode);
        if(StringUtils.isNotEmpty(i18Name)){
            return i18Name;
        }
        return name;
    }
    
    public String getI18nDescription(String localizationCode) {
        String i18Description = (String) getValue(ProductBrandAttribute.PRODUCT_BRAND_ATTRIBUTE_I18N_DESCRIPTION, null, localizationCode);
        if(StringUtils.isNotEmpty(i18Description)){
            return i18Description;
        }
        return description;
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
        ProductBrand other = (ProductBrand) obj;
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
        return "ProductBrand [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", code=" + code + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
                + "]";
    }

}