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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.annotation.CacheEntityInformation;
import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name="TECO_PRODUCT_BRAND")
@CacheEntityInformation(cacheName="web_cache_product_brand")
public class ProductBrand extends AbstractExtendEntity<ProductBrand, ProductBrandAttribute> implements DomainEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3980707210914384779L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false) // , columnDefinition = "int(11) default 1"
    private int version;

    @Column(name = "SCORING", nullable = false) // , columnDefinition = "default 1"
    private long scoring;
    
    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "ENABLED", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean enabled = false;
    
    @Column(name = "IS_ENABLED_B2B", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean enabledB2B = false;

    @Column(name = "IS_ENABLED_B2C", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean enabledB2C = false;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductBrandAttribute.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private Set<ProductBrandAttribute> attributes = new HashSet<ProductBrandAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Asset.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketing.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private Set<ProductMarketing> productMarketings = new HashSet<ProductMarketing>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductBrandCustomerRate.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private Set<ProductBrandCustomerRate> customerRates = new HashSet<ProductBrandCustomerRate>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductBrandCustomerComment.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private Set<ProductBrandCustomerComment> customerComments = new HashSet<ProductBrandCustomerComment>();
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, targetEntity = org.hoteia.qalingo.core.domain.ProductBrandTag.class)
    @JoinTable(name = "TECO_PRODUCT_BRAND_TAG_REL", joinColumns = @JoinColumn(name = "PRODUCT_BRAND_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_BRAND_TAG_ID"))
    private Set<ProductBrandTag> tags = new HashSet<ProductBrandTag>();
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Company.class)
    @JoinColumn(name = "COMPANY_ID", insertable = true, updatable = true)
    private Company company;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

	public ProductBrand(){
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
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

    public long getScoring() {
        return scoring;
    }

    public void setScoring(long scoring) {
        this.scoring = scoring;
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
	
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabledB2B() {
        return enabledB2B;
    }

    public void setEnabledB2B(boolean enabledB2B) {
        this.enabledB2B = enabledB2B;
    }
    
    public boolean isEnabledB2C() {
        return enabledB2C;
    }

    public void setEnabledB2C(boolean enabledB2C) {
        this.enabledB2C = enabledB2C;
    }

    public Set<ProductBrandAttribute> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Set<ProductBrandAttribute> attributes) {
        this.attributes = attributes;
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
	
    public Set<ProductBrandCustomerRate> getCustomerRates() {
        return customerRates;
    }

    public void setCustomerRates(Set<ProductBrandCustomerRate> customerRates) {
        this.customerRates = customerRates;
    }

    public Set<ProductBrandCustomerComment> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(Set<ProductBrandCustomerComment> customerComments) {
        this.customerComments = customerComments;
    }
	
	public Set<ProductBrandTag> getTags() {
        return tags;
    }
	
	public void setTags(Set<ProductBrandTag> tags) {
        this.tags = tags;
    }
	
	public Company getCompany() {
        return company;
    }
	
	public void setCompany(Company company) {
        this.company = company;
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

    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
        AbstractAttribute attribute = getAttribute(attributeCode, marketAreaId, localizationCode);
        if (attribute != null) {
            return attribute.getValue();
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
    
    public String getI18nLongDescription(String localizationCode) {
        String i18Description = (String) getValue(ProductBrandAttribute.PRODUCT_BRAND_ATTRIBUTE_I18N_LONG_DESCRIPTION, null, localizationCode);
        if(StringUtils.isNotEmpty(i18Description)){
            return i18Description;
        }
        return description;
    }
    
    public String getI18nShortDescription(String localizationCode) {
        String i18Description = (String) getValue(ProductBrandAttribute.PRODUCT_BRAND_ATTRIBUTE_I18N_SHORT_DESCRIPTION, null, localizationCode);
        if(StringUtils.isNotEmpty(i18Description)){
            return i18Description;
        }
        return description;
    }
    
    public String getOriginCountryCode() {
        String originCountryCode = (String) getValue(ProductBrandAttribute.PRODUCT_BRAND_ATTRIBUTE_I18N_ORIGIN_COUNTRY_CODE, null, null);
        if(StringUtils.isNotEmpty(originCountryCode)){
            return originCountryCode;
        }
        return null;
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