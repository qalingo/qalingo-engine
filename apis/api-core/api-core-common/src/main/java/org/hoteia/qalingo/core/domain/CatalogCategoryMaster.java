/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OrderBy;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.AssetType;

@Entity
@Table(name = "TECO_CATALOG_MASTER_CATEGORY", uniqueConstraints = { @UniqueConstraint(columnNames = { "CODE" }) })
public class CatalogCategoryMaster extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -9123721692905623486L;

    public static final String CACHE_NAME = "web_cache_category";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATALOG_CATEGORY_TYPE_ID", insertable = true, updatable = true)
    private CatalogCategoryType catalogCategoryType;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "IS_DEFAULT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_PARENT_CATEGORY_ID", insertable = false, updatable = false)
    private CatalogCategoryMaster defaultParentCatalogCategory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MASTER_CATEGORY_ID")
    @OrderBy(clause = "ordering asc")
    private Set<CatalogCategoryMasterAttribute> catalogCategoryAttributes = new HashSet<CatalogCategoryMasterAttribute>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CatalogCategoryMaster.class)
    @JoinTable(name = "TECO_CATALOG_MASTER_CATEGORY_CHILD_CATEGORY_REL", joinColumns = @JoinColumn(name = "PARENT_MASTER_CATALOG_CATEGORY_ID"), inverseJoinColumns = @JoinColumn(name = "CHILD_MASTER_CATALOG_CATEGORY_ID"))
    private Set<CatalogCategoryMaster> catalogCategories = new HashSet<CatalogCategoryMaster>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductMarketing.class)
    @JoinTable(name = "TECO_CATALOG_MASTER_CATEGORY_PRODUCT_MARKETING_REL", joinColumns = @JoinColumn(name = "MASTER_CATEGORY_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_MARKETING_ID"))
    private Set<ProductMarketing> productMarketings = new HashSet<ProductMarketing>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASTER_CATEGORY_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public CatalogCategoryMaster() {
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

    public CatalogCategoryType getCatalogCategoryType() {
        return catalogCategoryType;
    }

    public void setCatalogCategoryType(CatalogCategoryType catalogCategoryType) {
        this.catalogCategoryType = catalogCategoryType;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isRoot() {
        if (getDefaultParentCatalogCategory() == null) {
            return false;
        }
        return true;
    }

    public CatalogCategoryMaster getDefaultParentCatalogCategory() {
        return defaultParentCatalogCategory;
    }

    public void setDefaultParentCatalogCategory(CatalogCategoryMaster defaultParentCatalogCategory) {
        this.defaultParentCatalogCategory = defaultParentCatalogCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CatalogCategoryMasterAttribute> getCatalogCategoryAttributes() {
        return catalogCategoryAttributes;
    }

    public void setCatalogCategoryAttributes(Set<CatalogCategoryMasterAttribute> catalogCategoryAttributes) {
        this.catalogCategoryAttributes = catalogCategoryAttributes;
    }

    public List<CatalogCategoryMasterAttribute> getCatalogCategoryGlobalAttributes() {
        List<CatalogCategoryMasterAttribute> catalogCategoryGlobalAttributes = null;
//        try {
            if (catalogCategoryAttributes != null
                    && Hibernate.isInitialized(catalogCategoryAttributes)) {
                catalogCategoryGlobalAttributes = new ArrayList<CatalogCategoryMasterAttribute>();
                for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryMasterAttribute attribute = (CatalogCategoryMasterAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                    if (attributeDefinition != null && attributeDefinition.isGlobal()) {
                        catalogCategoryGlobalAttributes.add(attribute);
                    }
                }
            }
//        } catch (LazyInitializationException e) {
//            logger.warn("LazyInitializationException on catalogCategoryAttributes");
//        }
        return catalogCategoryGlobalAttributes;
    }

    public List<CatalogCategoryMasterAttribute> getCatalogCategoryMarketAreaAttributes(Long marketAreaId) {
        List<CatalogCategoryMasterAttribute> catalogCategoryMarketAreaAttributes = null;
//        try {
            if (catalogCategoryAttributes != null
                    && Hibernate.isInitialized(catalogCategoryAttributes)) {
                catalogCategoryMarketAreaAttributes = new ArrayList<CatalogCategoryMasterAttribute>();
                for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryMasterAttribute attribute = (CatalogCategoryMasterAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                    if (attributeDefinition != null && !attributeDefinition.isGlobal()) {
                        catalogCategoryMarketAreaAttributes.add(attribute);
                    }
                }
            }
//        } catch (LazyInitializationException e) {
//            logger.warn("LazyInitializationException on catalogCategoryAttributes");
//        }
        return catalogCategoryMarketAreaAttributes;
    }

    public Set<CatalogCategoryMaster> getCatalogCategories() {
        return catalogCategories;
    }

    public List<CatalogCategoryMaster> getCatalogCategories(final Long marketAreaId) {
        List<CatalogCategoryMaster> sortedObjects = null;
        if (catalogCategories != null
                && Hibernate.isInitialized(catalogCategories)) {
//            try {
                sortedObjects = new LinkedList<CatalogCategoryMaster>(catalogCategories);
                Collections.sort(sortedObjects, new Comparator<CatalogCategoryMaster>() {
                    @Override
                    public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
                        if (o1 != null && o2 != null) {
                            Integer order1 = o1.getOrder(marketAreaId);
                            Integer order2 = o2.getOrder(marketAreaId);
                            if (order1 != null && order2 != null) {
                                return order1.compareTo(order2);
                            } else {
                                return o1.getId().compareTo(o2.getId());
                            }
                        }
                        return 0;
                    }
                });
//            } catch (LazyInitializationException e) {
//                logger.warn("LazyInitializationException on catalogCategories");
//            }
        }
        return sortedObjects;
    }

    public void setCatalogCategories(Set<CatalogCategoryMaster> catalogCategories) {
        this.catalogCategories = catalogCategories;
    }

    public Set<ProductMarketing> getProductMarketings() {
        return productMarketings;
    }

    public void setProductMarketings(Set<ProductMarketing> productMarketings) {
        this.productMarketings = productMarketings;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public List<Asset> getAssetsIsGlobal() {
        List<Asset> assetsIsGlobal = null;
//        try {
            if (assets != null
                    && Hibernate.isInitialized(assets)) {
                assetsIsGlobal = new ArrayList<Asset>();
                for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                    Asset asset = (Asset) iterator.next();
                    if (asset != null && asset.isGlobal()) {
                        assetsIsGlobal.add(asset);
                    }
                }
            }
//        } catch (LazyInitializationException e) {
//            logger.warn("LazyInitializationException on assets");
//        }
        return assetsIsGlobal;
    }

    public List<Asset> getAssetsByMarketArea() {
        List<Asset> assetsByMarketArea = null;
//        try {
            if (assets != null
                    && Hibernate.isInitialized(assets)) {
                assetsByMarketArea = new ArrayList<Asset>();
                for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                    Asset asset = (Asset) iterator.next();
                    if (asset != null && !asset.isGlobal()) {
                        assetsByMarketArea.add(asset);
                    }
                }
            }
//        } catch (LazyInitializationException e) {
//            logger.warn("LazyInitializationException on assets");
//        }
        return assetsByMarketArea;
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

    public CatalogCategoryMasterAttribute getCatalogCategoryAttribute(String attributeCode) {
        return getCatalogCategoryAttribute(attributeCode, null, null);
    }

    public CatalogCategoryMasterAttribute getCatalogCategoryAttribute(String attributeCode, String localizationCode) {
        return getCatalogCategoryAttribute(attributeCode, null, localizationCode);
    }

    public CatalogCategoryMasterAttribute getProductCategoryAttribute(String attributeCode, Long marketAreaId) {
        return getCatalogCategoryAttribute(attributeCode, marketAreaId, null);
    }

    public CatalogCategoryMasterAttribute getCatalogCategoryAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
        CatalogCategoryMasterAttribute catalogCategoryAttributeToReturn = null;

        // 1: GET THE GLOBAL VALUE
        CatalogCategoryMasterAttribute catalogCategoryGlobalAttribute = getCatalogCategoryAttribute(getCatalogCategoryGlobalAttributes(), attributeCode, marketAreaId, localizationCode);

        // 2: GET THE MARKET AREA VALUE
        CatalogCategoryMasterAttribute catalogCategoryMarketAreaAttribute = getCatalogCategoryAttribute(getCatalogCategoryMarketAreaAttributes(marketAreaId), attributeCode, marketAreaId,
                localizationCode);

        if (catalogCategoryMarketAreaAttribute != null) {
            catalogCategoryAttributeToReturn = catalogCategoryMarketAreaAttribute;
        } else if (catalogCategoryGlobalAttribute != null) {
            catalogCategoryAttributeToReturn = catalogCategoryGlobalAttribute;
        }

        return catalogCategoryAttributeToReturn;
    }

    private CatalogCategoryMasterAttribute getCatalogCategoryAttribute(List<CatalogCategoryMasterAttribute> catalogCategoryAttributes, String attributeCode, Long marketAreaId, String localizationCode) {
        CatalogCategoryMasterAttribute catalogCategoryAttributeToReturn = null;
        List<CatalogCategoryMasterAttribute> catalogCategoryAttributesFilter = new ArrayList<CatalogCategoryMasterAttribute>();
        if (catalogCategoryAttributes != null) {
            // GET ALL CategoryAttributes FOR THIS ATTRIBUTE
            for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributes.iterator(); iterator.hasNext();) {
                CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
                if (attributeDefinition != null && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    catalogCategoryAttributesFilter.add(catalogCategoryAttribute);
                }
            }
            // REMOVE ALL CategoryAttributes NOT ON THIS MARKET AREA
            if (marketAreaId != null) {
                for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
                    CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        if (catalogCategoryAttribute.getMarketAreaId() != null && BooleanUtils.negate(catalogCategoryAttribute.getMarketAreaId().equals(marketAreaId))) {
                            iterator.remove();
                        }
                    }
                }
            }
            // FINALLY RETAIN ONLY CategoryAttributes FOR THIS LOCALIZATION CODE
            if (StringUtils.isNotEmpty(localizationCode)) {
                for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
                    CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        String attributeLocalizationCode = catalogCategoryAttribute.getLocalizationCode();
                        if (StringUtils.isNotEmpty(attributeLocalizationCode) && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))) {
                            iterator.remove();
                        }
                    }
                }
                if (catalogCategoryAttributesFilter.size() == 0) {
                    // TODO : warning ?

                    // NOT ANY CategoryAttributes FOR THIS LOCALIZATION CODE -
                    // GET A FALLBACK
                    for (Iterator<CatalogCategoryMasterAttribute> iterator = getCatalogCategoryMarketAreaAttributes(marketAreaId).iterator(); iterator.hasNext();) {
                        CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();

                        // TODO : get a default locale code from setting
                        // database ?

                        if (Constants.DEFAULT_LOCALE_CODE.equals(catalogCategoryAttribute.getLocalizationCode())) {
                            catalogCategoryAttributeToReturn = catalogCategoryAttribute;
                        }
                    }
                }
            }
        }

        if (catalogCategoryAttributesFilter.size() == 1) {
            catalogCategoryAttributeToReturn = catalogCategoryAttributesFilter.get(0);
        } else {
            // TODO : throw error ?
        }

        return catalogCategoryAttributeToReturn;
    }

    public Object getValue(String attributeCode) {
        return getValue(attributeCode, null, null);
    }

    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
        CatalogCategoryMasterAttribute productCategoryAttribute = getCatalogCategoryAttribute(attributeCode, marketAreaId, localizationCode);
        if (productCategoryAttribute != null) {
            return productCategoryAttribute.getValue();
        }
        return null;
    }

    public String getI18nName(String localizationCode) {
        String i18nName = (String) getValue(CatalogCategoryMasterAttribute.CATALOG_CATEGORY_ATTRIBUTE_I18N_NAME, null, localizationCode);
        if (StringUtils.isEmpty(i18nName)) {
            i18nName = getName();
        }
        return i18nName;
    }

    public Integer getOrder(Long marketAreaId) {
        return (Integer) getValue(CatalogCategoryVirtualAttribute.CATALOG_CATEGORY_ATTRIBUTE_ORDER, marketAreaId, null);
    }

    // ASSET
    
    public Asset getDefaultPaskshotImage(String size) {
        Asset defaultProductImage = null;
        List<Asset> assetsIsGlobal = new ArrayList<Asset>();
        if (assetsIsGlobal != null 
                && StringUtils.isNotEmpty(size)) {
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productAsset = (Asset) iterator.next();
                if (AssetType.PACKSHOT.equals(productAsset.getType()) 
                        && size.equals(productAsset.getSize().name()) 
                        && productAsset.isDefault()) {
                    defaultProductImage = productAsset;
                }
            }
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.PACKSHOT.equals(productImage.getType()) 
                        && size.equals(productImage.getSize().name())) {
                    defaultProductImage = productImage;
                }
            }
        }
        return defaultProductImage;
    }

    public Asset getDefaultBackgroundImage() {
        Asset defaultProductImage = null;
        List<Asset> assetsIsGlobal = new ArrayList<Asset>();
        if (assetsIsGlobal != null) {
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.BACKGROUND.equals(productImage.getType()) 
                        && productImage.isDefault()) {
                    defaultProductImage = productImage;
                }
            }
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.BACKGROUND.equals(productImage.getType())) {
                    defaultProductImage = productImage;
                }
            }
        }
        return defaultProductImage;
    }

    public Asset getDefaultIconImage() {
        Asset defaultProductImage = null;
        List<Asset> assetsIsGlobal = new ArrayList<Asset>();
        if (assetsIsGlobal != null) {
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.ICON.equals(productImage.getType()) 
                        && productImage.isDefault()) {
                    defaultProductImage = productImage;
                }
            }
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.ICON.equals(productImage.getType())) {
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
        CatalogCategoryMaster other = (CatalogCategoryMaster) obj;
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
        return "CatalogCategoryMaster [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", code=" + code + ", isDefault=" + isDefault
                + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}