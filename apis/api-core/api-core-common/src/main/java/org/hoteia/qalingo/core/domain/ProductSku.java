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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

@Entity
@Table(name="TECO_PRODUCT_SKU")
public class ProductSku extends AbstractExtendEntity<ProductSku, ProductSkuAttribute> {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -2239109542749803870L;

    public static final String CACHE_NAME = "web_cache_product_sku";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "EAN")
    private String ean;
    
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "IS_DEFAULT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefault;

    @Column(name = "IS_ENABLED_TO_B2C", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean enabledToB2C;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuAttribute.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<ProductSkuAttribute> attributes = new HashSet<ProductSkuAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuOptionRel.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<ProductSkuOptionRel> optionRels = new HashSet<ProductSkuOptionRel>();
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketing.class)
    @JoinColumn(name = "PRODUCT_MARKETING_ID", insertable = true, updatable = true)
    private ProductMarketing productMarketing;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Asset.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuPrice.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<ProductSkuPrice> prices = new HashSet<ProductSkuPrice>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuStock.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<ProductSkuStock> stocks = new HashSet<ProductSkuStock>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Store.class)
    @JoinTable(name = "TECO_PRODUCT_SKU_STORE_REL", joinColumns = @JoinColumn(name = "PRODUCT_SKU_ID"), inverseJoinColumns = @JoinColumn(name = "STORE_ID"))
    @Deprecated
    private Set<Store> stores = new HashSet<Store>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuStoreRel.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<ProductSkuStoreRel> productSkuStoreRels = new HashSet<ProductSkuStoreRel>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CatalogCategoryMasterProductSkuRel.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<CatalogCategoryMasterProductSkuRel> catalogCategoryMasterProductSkuRels = new HashSet<CatalogCategoryMasterProductSkuRel>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuRel.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<CatalogCategoryVirtualProductSkuRel> catalogCategoryVirtualProductSkuRels = new HashSet<CatalogCategoryVirtualProductSkuRel>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuCustomerRate.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<ProductSkuCustomerRate> customerRates = new HashSet<ProductSkuCustomerRate>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuCustomerComment.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<ProductSkuCustomerComment> customerComments = new HashSet<ProductSkuCustomerComment>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuTagRel.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private Set<ProductSkuTagRel> tagRels = new HashSet<ProductSkuTagRel>();
    
    @Transient
    private Integer ranking;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

	public ProductSku(){
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
    
    public String getEan() {
        return ean;
    }
    
    public void setEan(String ean) {
        this.ean = ean;
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

    public Set<ProductSkuAttribute> getAttributes() {
        return attributes;
    }
	
	public void setAttributes(Set<ProductSkuAttribute> attributes) {
        this.attributes = attributes;
    }
	
	public Set<ProductSkuOptionRel> getOptionRels() {
        return optionRels;
    }

    public List<ProductSkuOptionDefinition> getOptions() {
        List<ProductSkuOptionDefinition> options = null;
        if (Hibernate.isInitialized(optionRels) && !optionRels.isEmpty()) {
            options = new ArrayList<ProductSkuOptionDefinition>();
            for (Iterator<ProductSkuOptionRel> iterator = optionRels.iterator(); iterator.hasNext();) {
                ProductSkuOptionRel productSkuOptionRel = (ProductSkuOptionRel) iterator.next();
                if(Hibernate.isInitialized(productSkuOptionRel.getProductSkuOptionDefinition()) && productSkuOptionRel.getProductSkuOptionDefinition() != null){
                    options.add(productSkuOptionRel.getProductSkuOptionDefinition());
                }
            }
        }
        return options;
    }
	
	public void setOptionRels(Set<ProductSkuOptionRel> optionRels) {
        this.optionRels = optionRels;
    }
	
	public ProductMarketing getProductMarketing() {
		return productMarketing;
	}
	
	public void setProductMarketing(ProductMarketing productMarketing) {
		this.productMarketing = productMarketing;
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
	
	public Set<ProductSkuPrice> getPrices() {
		return prices;
	}

    public ProductSkuPrice getBestPrice(final Long marketAreaId) {
        if (prices != null && Hibernate.isInitialized(prices)) {
            for (ProductSkuPrice productSkuPrice : prices) {
                if (productSkuPrice.getMarketAreaId().equals(marketAreaId) && productSkuPrice.isDiscount()) {
                    return productSkuPrice;
                }
            }
        }
        return getPublicPrice(marketAreaId);
    }
    
    public ProductSkuPrice getPublicPrice(final Long marketAreaId){
        if(prices != null
                && Hibernate.isInitialized(prices)){
            for (ProductSkuPrice productSkuPrice : prices) {
                if (productSkuPrice.getMarketAreaId().equals(marketAreaId) && productSkuPrice.isCatalogPrice()) {
                    return productSkuPrice;
                }
            }    
        }
        return null;
    }

	@Deprecated
	public ProductSkuPrice getCatalogPrice(final Long marketAreaId){
	    if(prices != null
	            && Hibernate.isInitialized(prices)){
	        for (ProductSkuPrice productSkuPrice : prices) {
	            if(productSkuPrice.getMarketAreaId().equals(marketAreaId) 
	                    && productSkuPrice.isCatalogPrice()) {
	                return productSkuPrice;
	            }
	        }    
	    }
	    return null;
	}
	
    @Deprecated
    public ProductSkuPrice getSalePrice(final Long marketAreaId, final Long storeId){
        if(prices != null
                && Hibernate.isInitialized(prices)){
            for (ProductSkuPrice productSkuPrice : prices) {
                if(productSkuPrice.getMarketAreaId().equals(marketAreaId) 
                        && productSkuPrice.isCatalogPrice()) {
                    return productSkuPrice;
                }
            }    
        }
        return null;
    }
	
	public void setPrices(Set<ProductSkuPrice> prices) {
		this.prices = prices;
	}
	
	public Set<ProductSkuStock> getStocks() {
		return stocks;
	}
	
	public void setStocks(Set<ProductSkuStock> stocks) {
		this.stocks = stocks;
	}
	
	public Set<Store> getStores() {
        return stores;
    }
	
	public void setStores(Set<Store> stores) {
        this.stores = stores;
    }
	
	public Set<ProductSkuStoreRel> getProductSkuStoreRels() {
        return productSkuStoreRels;
    }
	
    public List<Store> getProductSkuStores() {
        List<Store> stores = null;
        if (Hibernate.isInitialized(productSkuStoreRels) && !productSkuStoreRels.isEmpty()) {
            stores = new ArrayList<Store>();
            for (Iterator<ProductSkuStoreRel> iterator = productSkuStoreRels.iterator(); iterator.hasNext();) {
                ProductSkuStoreRel productSkuStoreRel = (ProductSkuStoreRel) iterator.next();
                if(Hibernate.isInitialized(productSkuStoreRel.getPk().getStore()) && productSkuStoreRel.getPk().getStore() != null){
                    stores.add(productSkuStoreRel.getStore());
                }
            }
        }
        return stores;
    }
    
	public void setProductSkuStoreRels(Set<ProductSkuStoreRel> productSkuStoreRels) {
        this.productSkuStoreRels = productSkuStoreRels;
    }
	
	public Set<CatalogCategoryMasterProductSkuRel> getCatalogCategoryMasterProductSkuRels() {
        return catalogCategoryMasterProductSkuRels;
    }
	
	public void setCatalogCategoryMasterProductSkuRels(Set<CatalogCategoryMasterProductSkuRel> catalogCategoryMasterProductSkuRels) {
        this.catalogCategoryMasterProductSkuRels = catalogCategoryMasterProductSkuRels;
    }
	
	public Set<CatalogCategoryVirtualProductSkuRel> getCatalogCategoryVirtualProductSkuRels() {
        return catalogCategoryVirtualProductSkuRels;
    }
	
	public void setCatalogCategoryVirtualProductSkuRels(Set<CatalogCategoryVirtualProductSkuRel> catalogCategoryVirtualProductSkuRels) {
        this.catalogCategoryVirtualProductSkuRels = catalogCategoryVirtualProductSkuRels;
    }
	
    public CatalogCategoryVirtual getDefaultCatalogCategoryVirtual(CatalogVirtual catalog) {
        CatalogCategoryVirtual defaultCatalogCategory = null;
        if (catalogCategoryVirtualProductSkuRels != null && Hibernate.isInitialized(catalogCategoryVirtualProductSkuRels) && !catalogCategoryVirtualProductSkuRels.isEmpty()) {
            for (Iterator<CatalogCategoryVirtualProductSkuRel> iterator = catalogCategoryVirtualProductSkuRels.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel = (CatalogCategoryVirtualProductSkuRel) iterator.next();
                CatalogCategoryVirtual catalogCategoryVirtual = catalogCategoryVirtualProductSkuRel.getCatalogCategoryVirtual();
                if (catalogCategoryVirtual.isDefault() && Hibernate.isInitialized(catalogCategoryVirtual.getCatalog()) && catalog.getId().equals(catalogCategoryVirtual.getCatalog().getId())) {
                    defaultCatalogCategory = catalogCategoryVirtual;
                }
            }
            if (defaultCatalogCategory == null) {
                for (Iterator<CatalogCategoryVirtualProductSkuRel> iterator = catalogCategoryVirtualProductSkuRels.iterator(); iterator.hasNext();) {
                    CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel = (CatalogCategoryVirtualProductSkuRel) iterator.next();
                    CatalogCategoryVirtual catalogCategoryVirtual = catalogCategoryVirtualProductSkuRel.getCatalogCategoryVirtual();
                    if (Hibernate.isInitialized(catalogCategoryVirtual.getCatalog()) && catalog.getId().equals(catalogCategoryVirtual.getCatalog().getId())) {
                        defaultCatalogCategory = catalogCategoryVirtual;
                    }
                }
            }
        }
        return defaultCatalogCategory;
    }
    
    public Set<ProductSkuCustomerRate> getCustomerRates() {
        return customerRates;
    }

    public void setCustomerRates(Set<ProductSkuCustomerRate> customerRates) {
        this.customerRates = customerRates;
    }

    public Set<ProductSkuCustomerComment> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(Set<ProductSkuCustomerComment> customerComments) {
        this.customerComments = customerComments;
    }
    
    public Set<ProductSkuTagRel> getTagRels() {
        return tagRels;
    }
    
    public List<Tag> getTags() {
        List<Tag> tags = null;
        if (Hibernate.isInitialized(tagRels) && !tagRels.isEmpty()) {
            tags = new ArrayList<Tag>();
            for (Iterator<ProductSkuTagRel> iterator = tagRels.iterator(); iterator.hasNext();) {
                ProductSkuTagRel productSkuTagRel = (ProductSkuTagRel) iterator.next();
                if(Hibernate.isInitialized(productSkuTagRel.getPk().getTag()) && productSkuTagRel.getPk().getTag() != null){
                    tags.add(productSkuTagRel.getProductSkuTag());
                }
            }
        }
        return tags;
    }
    
    public void setTagRels(Set<ProductSkuTagRel> tagRels) {
        this.tagRels = tagRels;
    }
    
    public Integer getRanking() {
        if(ranking == null){
            return new Integer(0);
        }
        return ranking;
    }
    
    public void setRanking(Integer ranking) {
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
		String i18nName = (String) getValue(ProductSkuAttribute.PRODUCT_SKU_ATTRIBUTE_I18N_NAME, null, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getName();
		}
		return i18nName;
	}
	
    public String getI18nDescription(String localizationCode) {
        String i18Description = (String) getValue(ProductSkuAttribute.PRODUCT_SKU_ATTRIBUTE_I18N_DESCRIPTION, null, localizationCode);
        if(StringUtils.isNotEmpty(i18Description)){
            return i18Description;
        }
        return description;
    }
	
    public Integer getWidth() {
        return (Integer) getValue(ProductSkuAttribute.PRODUCT_SKU_ATTRIBUTE_WIDTH, null, null);
    }
    
    public Integer getHeight() {
        return (Integer) getValue(ProductSkuAttribute.PRODUCT_SKU_ATTRIBUTE_HEIGHT, null, null);
    }
    
    public Integer getLength() {
        return (Integer) getValue(ProductSkuAttribute.PRODUCT_SKU_ATTRIBUTE_LENGTH, null, null);
    }
    
    public Integer getWeight() {
        return (Integer) getValue(ProductSkuAttribute.PRODUCT_SKU_ATTRIBUTE_WEIGHT, null, null);
    }

    public Boolean isSalable(final Long marketAreaId){
        Boolean isSalable = (Boolean) getValue(ProductMarketingAttribute.PRODUCT_SKU_ATTRIBUTE_IS_SALABLE, marketAreaId, null);
         if (isSalable == null) {
             return Boolean.FALSE;
         } else {
             return isSalable;
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
        ProductSku other = (ProductSku) obj;
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
        return "ProductSku [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", isDefault=" + isDefault + ", code=" + code + ", dateCreate="
                + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
	
}