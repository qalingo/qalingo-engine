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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;

@Entity
@Table(name = "TECO_RETAILER", uniqueConstraints = {@UniqueConstraint(columnNames= {"CODE"})})
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

    @Column(name = "CODE", nullable = false)
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "WAREHOUSE_ID")
    private Warehouse warehouse;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RETAILER_ID")
    private Set<RetailerLink> links = new HashSet<RetailerLink>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RETAILER_ID")
    private Set<RetailerAddress> addresses = new HashSet<RetailerAddress>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RETAILER_ID")
    private Set<Store> stores = new HashSet<Store>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RETAILER_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RETAILER_ID")
    private Set<RetailerAttribute> retailerAttributes = new HashSet<RetailerAttribute>();

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

    public Warehouse getWarehouse() {
        return warehouse;
    }
    
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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
    
    public Set<RetailerAttribute> getRetailerAttributes() {
        return retailerAttributes;
    }
    
    public void setRetailerAttributes(Set<RetailerAttribute> retailerAttributes) {
        this.retailerAttributes = retailerAttributes;
    }

    public List<RetailerAttribute> getRetailerGlobalAttributes() {
        List<RetailerAttribute> retailerGlobalAttributes = null;
        if (retailerAttributes != null
                && Hibernate.isInitialized(retailerAttributes)) {
            retailerGlobalAttributes = new ArrayList<RetailerAttribute>();
            for (Iterator<RetailerAttribute> iterator = retailerAttributes.iterator(); iterator.hasNext();) {
                RetailerAttribute attribute = (RetailerAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && attributeDefinition.isGlobal()) {
                    retailerGlobalAttributes.add(attribute);
                }
            }
        }        
        return retailerGlobalAttributes;
    }

    public List<RetailerAttribute> getRetailerMarketAreaAttributes(Long marketAreaId) {
        List<RetailerAttribute> retailerMarketAreaAttributes = null;
        if (retailerAttributes != null
                && Hibernate.isInitialized(retailerAttributes)) {
            retailerMarketAreaAttributes = new ArrayList<RetailerAttribute>();
            for (Iterator<RetailerAttribute> iterator = retailerAttributes.iterator(); iterator.hasNext();) {
                RetailerAttribute attribute = (RetailerAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && !attributeDefinition.isGlobal()) {
                    retailerMarketAreaAttributes.add(attribute);
                }
            }
        }        
        return retailerMarketAreaAttributes;
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

    public RetailerAttribute getCatalogCategoryAttribute(String attributeCode) {
        return getRetailerAttribute(attributeCode, null, null);
    }

    public RetailerAttribute getRetailerAttribute(String attributeCode, String localizationCode) {
        return getRetailerAttribute(attributeCode, null, localizationCode);
    }

    public RetailerAttribute getProductCategoryAttribute(String attributeCode, Long marketAreaId) {
        return getRetailerAttribute(attributeCode, marketAreaId, null);
    }

    public RetailerAttribute getRetailerAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
        RetailerAttribute retailerAttributeToReturn = null;

        // 1: GET THE GLOBAL VALUE
        RetailerAttribute retailerGlobalAttribute = getRetailerAttribute(getRetailerGlobalAttributes(), attributeCode, marketAreaId, localizationCode);

        // 2: GET THE MARKET AREA VALUE
        RetailerAttribute retailerMarketAreaAttribute = getRetailerAttribute(getRetailerMarketAreaAttributes(marketAreaId), attributeCode, marketAreaId, localizationCode);

        if (retailerMarketAreaAttribute != null) {
            retailerAttributeToReturn = retailerMarketAreaAttribute;
        } else if (retailerGlobalAttribute != null) {
            retailerAttributeToReturn = retailerGlobalAttribute;
        }

        return retailerAttributeToReturn;
    }

    private RetailerAttribute getRetailerAttribute(List<RetailerAttribute> retailerAttributes, String attributeCode, Long marketAreaId, String localizationCode) {
        RetailerAttribute retailerAttributeToReturn = null;
        List<RetailerAttribute> retailerAttributesFilter = new ArrayList<RetailerAttribute>();
        if (retailerAttributes != null) {
            // GET ALL CategoryAttributes FOR THIS ATTRIBUTE
            for (Iterator<RetailerAttribute> iterator = retailerAttributes.iterator(); iterator.hasNext();) {
                RetailerAttribute attribute = (RetailerAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    retailerAttributesFilter.add(attribute);
                }
            }
            // REMOVE ALL CategoryAttributes NOT ON THIS MARKET AREA
            if (marketAreaId != null) {
                for (Iterator<RetailerAttribute> iterator = retailerAttributesFilter.iterator(); iterator.hasNext();) {
                    RetailerAttribute attribute = (RetailerAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        if (attribute.getMarketAreaId() != null && BooleanUtils.negate(attribute.getMarketAreaId().equals(marketAreaId))) {
                            iterator.remove();
                        }
                    }
                }
            }
            // FINALLY RETAIN ONLY CategoryAttributes FOR THIS LOCALIZATION CODE
            if (StringUtils.isNotEmpty(localizationCode)) {
                for (Iterator<RetailerAttribute> iterator = retailerAttributesFilter.iterator(); iterator.hasNext();) {
                    RetailerAttribute attribute = (RetailerAttribute) iterator.next();
                    String attributeLocalizationCode = attribute.getLocalizationCode();
                    if (StringUtils.isNotEmpty(attributeLocalizationCode) && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))) {
                        iterator.remove();
                    }
                }
                if (retailerAttributesFilter.size() == 0) {
                    // TODO : warning ?

                    // NOT ANY CategoryAttributes FOR THIS LOCALIZATION CODE -
                    // GET A FALLBACK
                    for (Iterator<RetailerAttribute> iterator = retailerAttributes.iterator(); iterator.hasNext();) {
                        RetailerAttribute attribute = (RetailerAttribute) iterator.next();

                        // TODO : get a default locale code from setting
                        // database ?

                        if (Constants.DEFAULT_LOCALE_CODE.equals(attribute.getLocalizationCode())) {
                            retailerAttributeToReturn = attribute;
                        }
                    }
                }
            }
        }

        if (retailerAttributesFilter.size() == 1) {
            retailerAttributeToReturn = retailerAttributesFilter.get(0);
        } else {
            // TODO : throw error ?
        }

        return retailerAttributeToReturn;
    }

    public Object getValue(String attributeCode) {
        return getValue(attributeCode, null);
    }

    public Object getValue(String attributeCode, String localizationCode) {
        RetailerAttribute productCategoryAttribute = getRetailerAttribute(attributeCode, localizationCode);
        if (productCategoryAttribute != null) {
            return productCategoryAttribute.getValue();
        }
        return null;
    }

    public String getI18nName(String localizationCode) {
        String i18nName = (String) getValue(RetailerAttribute.RETAILER_ATTRIBUTE_I18N_NAME, localizationCode);
        if (StringUtils.isEmpty(i18nName)) {
            i18nName = getName();
        }
        return i18nName;
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