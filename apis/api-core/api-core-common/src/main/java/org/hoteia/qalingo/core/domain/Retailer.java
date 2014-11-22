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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.AssetType;

@Entity
@Table(name = "TECO_RETAILER")
public class Retailer extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 7735245064467350070L;

    public static final String CACHE_NAME = "web_cache_retailer";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean active;
    
    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "IS_OFFICIAL_RETAILER", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isOfficialRetailer;

    @Column(name = "IS_BRAND", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isBrand;

    @Column(name = "IS_ECOMMERCE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isEcommerce;

    @Column(name = "IS_CORNER", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isCorner;

    @Column(name = "QUALITY_OF_SERVICE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private int qualityOfService;

    @Column(name = "PRICE_SCORE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private int priceScore;

    @Column(name = "RATIO_QUALITY_PRICE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private int ratioQualityPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Warehouse.class)
    @JoinColumn(name = "RETAILER_ID")
    private Set<Warehouse> warehouses = new HashSet<Warehouse>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.RetailerLink.class)
    @JoinColumn(name = "RETAILER_ID")
    private Set<RetailerLink> links = new HashSet<RetailerLink>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.RetailerAddress.class)
    @JoinColumn(name = "RETAILER_ID")
    private Set<RetailerAddress> addresses = new HashSet<RetailerAddress>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Asset.class)
    @JoinColumn(name = "RETAILER_ID")
    private Set<Store> stores = new HashSet<Store>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Asset.class)
    @JoinColumn(name = "RETAILER_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.RetailerAttribute.class)
    @JoinColumn(name = "RETAILER_ID")
    private Set<RetailerAttribute> attributes = new HashSet<RetailerAttribute>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductBrand.class)
    @JoinTable(name = "TECO_RETAILER_BRAND_REL", joinColumns = @JoinColumn(name = "RETAILER_ID"), inverseJoinColumns = @JoinColumn(name = "BRAND_ID"))
    private Set<ProductBrand> brands = new HashSet<ProductBrand>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RETAILER_ID")
    private Set<RetailerCustomerRate> customerRates = new HashSet<RetailerCustomerRate>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RETAILER_ID")
    private Set<RetailerCustomerComment> customerComments = new HashSet<RetailerCustomerComment>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.RetailerTag.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TECO_RETAILER_RETAILER_TAG_REL", joinColumns = @JoinColumn(name = "RETAILER_ID"), inverseJoinColumns = @JoinColumn(name = "RETAILER_TAG_ID"))
    private Set<RetailerTag> retailerTags;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public Retailer() {
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
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isOfficialRetailer() {
        return isOfficialRetailer;
    }

    public void setOfficialRetailer(boolean isOfficialRetailer) {
        this.isOfficialRetailer = isOfficialRetailer;
    }

    public boolean isBrand() {
        return isBrand;
    }

    public void setBrand(boolean isBrand) {
        this.isBrand = isBrand;
    }

    public boolean isEcommerce() {
        return isEcommerce;
    }

    public void setEcommerce(boolean isEcommerce) {
        this.isEcommerce = isEcommerce;
    }
    
    public boolean isCorner() {
        return isCorner;
    }

    public void setCorner(boolean isCorner) {
        this.isCorner = isCorner;
    }

    public int getQualityOfService() {
        return qualityOfService;
    }

    public void setQualityOfService(int qualityOfService) {
        this.qualityOfService = qualityOfService;
    }

    public int getPriceScore() {
        return priceScore;
    }

    public void setPriceScore(int priceScore) {
        this.priceScore = priceScore;
    }

    public int getRatioQualityPrice() {
        return ratioQualityPrice;
    }

    public void setRatioQualityPrice(int ratioQualityPrice) {
        this.ratioQualityPrice = ratioQualityPrice;
    }

    public Warehouse getDefaultWarehouse() {
        if(warehouses != null
                && Hibernate.isInitialized(warehouses)
                && warehouses.size() > 0){
            return warehouses.iterator().next();
        }
        return null;
    }
    
    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }
    
    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
    
    public Set<RetailerLink> getLinks() {
        return links;
    }

    public void setLinks(Set<RetailerLink> links) {
        this.links = links;
    }

    public Set<RetailerAddress> getAddresses() {
        return addresses;
    }

    public RetailerAddress getDefaultAddress() {
        RetailerAddress defaultAddress = null;
        if (addresses != null 
                && Hibernate.isInitialized(addresses)) {
            for (Iterator<RetailerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
                RetailerAddress retailerAddress = (RetailerAddress) iterator.next();
                if (retailerAddress.isDefault()) {
                    defaultAddress = retailerAddress;
                }
            }
        }
        return defaultAddress;
    }

    public void setAddresses(Set<RetailerAddress> addresses) {
        this.addresses = addresses;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
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
    
    public Asset getDefaultBackgroundImage() {
        Asset defaultImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if (assetsIsGlobal != null) {
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.BACKGROUND.getPropertyKey().equals(productImage.getType()) 
                        && productImage.isDefault()) {
                    defaultImage = productImage;
                }
            }
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.BACKGROUND.getPropertyKey().equals(productImage.getType())) {
                    defaultImage = productImage;
                }
            }
        }
        return defaultImage;
    }

    public Asset getDefaultSlideshowImage() {
        Asset defaultImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if (assetsIsGlobal != null) {
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.SLIDESHOW.getPropertyKey().equals(productImage.getType()) 
                        && productImage.isDefault()) {
                    defaultImage = productImage;
                }
            }
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.SLIDESHOW.getPropertyKey().equals(productImage.getType())) {
                    defaultImage = productImage;
                }
            }
        }
        return defaultImage;
    }

    public Asset getDefaultPackshotImage(String size) {
        Asset defaultImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if (assetsIsGlobal != null && StringUtils.isNotEmpty(size)) {
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productAsset = (Asset) iterator.next();
                if (AssetType.PACKSHOT.getPropertyKey().equals(productAsset.getType()) 
                        && size.equals(productAsset.getSize()) 
                        && productAsset.isDefault()) {
                    defaultImage = productAsset;
                }
            }
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.PACKSHOT.getPropertyKey().equals(productImage.getType()) 
                        && size.equals(productImage.getSize())) {
                    defaultImage = productImage;
                }
            }
        }
        return defaultImage;
    }

    public Asset getDefaultThumbnailImage() {
        Asset defaultImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if (assetsIsGlobal != null) {
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.THUMBNAIL.getPropertyKey().equals(productImage.getType()) 
                        && productImage.isDefault()) {
                    defaultImage = productImage;
                }
            }
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.THUMBNAIL.getPropertyKey().equals(productImage.getType())) {
                    defaultImage = productImage;
                }
            }
        }
        return defaultImage;
    }
    
    public Set<RetailerAttribute> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Set<RetailerAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<ProductBrand> getBrands() {
        return brands;
    }
    
    public void setBrands(Set<ProductBrand> brands) {
        this.brands = brands;
    }
    
    public Set<RetailerCustomerRate> getCustomerRates() {
        return customerRates;
    }

    public void setCustomerRates(Set<RetailerCustomerRate> customerRates) {
        this.customerRates = customerRates;
    }

    public Set<RetailerCustomerComment> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(Set<RetailerCustomerComment> customerComments) {
        this.customerComments = customerComments;
    }

    public Set<RetailerTag> getRetailerTags() {
        return retailerTags;
    }

    public void setRetailerTags(Set<RetailerTag> retailerTags) {
        this.retailerTags = retailerTags;
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

    public RetailerAttribute getAttribute(String attributeCode) {
        return getAttribute(attributeCode, null, null);
    }

    public RetailerAttribute getAttribute(String attributeCode, String localizationCode) {
        return getAttribute(attributeCode, null, localizationCode);
    }

    public RetailerAttribute getAttribute(String attributeCode, Long marketAreaId) {
        return getAttribute(attributeCode, marketAreaId, null);
    }

    public RetailerAttribute getAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
        RetailerAttribute attributeToReturn = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            List<RetailerAttribute> attributesFilter = new ArrayList<RetailerAttribute>();
            for (Iterator<RetailerAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                RetailerAttribute attribute = (RetailerAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    attributesFilter.add(attribute);
                }
            }
            if (marketAreaId != null) {
                for (Iterator<RetailerAttribute> iterator = attributesFilter.iterator(); iterator.hasNext();) {
                    RetailerAttribute attribute = (RetailerAttribute) iterator.next();
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
                for (Iterator<RetailerAttribute> iterator = attributesFilter.iterator(); iterator.hasNext();) {
                    RetailerAttribute attribute = (RetailerAttribute) iterator.next();
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

                    for (Iterator<RetailerAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                        RetailerAttribute attribute = (RetailerAttribute) iterator.next();

                        // TODO : get a default locale code from setting
                        // database ?

                        if (attribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)) {
                            attributeToReturn = attribute;
                        }
                    }
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
        RetailerAttribute storeAttribute = getAttribute(attributeCode, marketAreaId, localizationCode);
        if (storeAttribute != null) {
            return storeAttribute.getValue();
        }
        return null;
    }

    public String getI18nName(String localizationCodeNavigation) {
        String i18Name = (String) getValue(RetailerAttribute.RETAILER_ATTRIBUTE_I18N_NAME, null, localizationCodeNavigation);
        if(StringUtils.isNotEmpty(i18Name)){
            return i18Name;
        }
        return name;
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
        Retailer other = (Retailer) obj;
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
        return "Retailer [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", logo=" + logo + ", isOfficialRetailer=" + isOfficialRetailer + ", isBrand="
                + isBrand + ", isEcommerce=" + isEcommerce + ", isCorner=" + isCorner + ", code=" + code + ", qualityOfService=" + qualityOfService + ", priceScore=" + priceScore
                + ", ratioQualityPrice=" + ratioQualityPrice + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}