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

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.AssetType;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.annotations.OrderBy;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "TECO_CATALOG_MASTER_CATEGORY", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@FilterDefs(
        value = {
                @FilterDef(name = "filterCatalogMasterCategoryAttributeIsGlobal"),
                @FilterDef(name = "filterCatalogMasterCategoryAttributeByMarketArea", parameters = {@ParamDef(name = "marketAreaId", type = "long")}),
                @FilterDef(name = "filterCatalogMasterCategoryAssetIsGlobal"),
                @FilterDef(name = "filterCatalogMasterCategoryAssetByMarketArea", parameters = {@ParamDef(name = "marketAreaId", type = "long")})
        })
public class CatalogCategoryMaster implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -9123721692905623486L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "BUSINESS_NAME")
    private String businessName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CODE", nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CATALOG_CATEGORY_TYPE_ID", insertable = false, updatable = false)
    private CatalogCategoryType catalogCategoryType;

    @Column(name = "IS_DEFAULT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefault;

//	@Column(name="IS_ROOT", nullable=false, columnDefinition="tinyint(1) default 0")
//	private boolean isRoot;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "DEFAULT_PARENT_CATEGORY_ID")
    private CatalogCategoryMaster defaultParentCatalogCategory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MASTER_CATEGORY_ID")
    @Filter(name = "filterCatalogMasterCategoryAttributeIsGlobal", condition = "IS_GLOBAL = '1'")
    @OrderBy(clause = "ordering asc")
    private Set<CatalogCategoryMasterAttribute> catalogCategoryGlobalAttributes = new HashSet<CatalogCategoryMasterAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MASTER_CATEGORY_ID")
    @Filter(name = "filterCatalogMasterCategoryAttributeByMarketArea", condition = "IS_GLOBAL = '0' AND MARKET_AREA_ID = :marketAreaId")
    @OrderBy(clause = "ordering asc")
    private Set<CatalogCategoryMasterAttribute> catalogCategoryMarketAreaAttributes = new HashSet<CatalogCategoryMasterAttribute>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            targetEntity = org.hoteia.qalingo.core.domain.CatalogCategoryMaster.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "TECO_CATALOG_MASTER_CATEGORY_CHILD_CATEGORY_REL",
            joinColumns = @JoinColumn(name = "PARENT_MASTER_CATALOG_CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_MASTER_CATALOG_CATEGORY_ID")
    )
    private Set<CatalogCategoryMaster> catalogCategories = new HashSet<CatalogCategoryMaster>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            targetEntity = org.hoteia.qalingo.core.domain.ProductMarketing.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "TECO_CATALOG_MASTER_CATEGORY_PRODUCT_MARKETING_REL",
            joinColumns = @JoinColumn(name = "MASTER_CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_MARKETING_ID")
    )
    private Set<ProductMarketing> productMarketings = new HashSet<ProductMarketing>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MASTER_CATEGORY_ID")
    @Filter(name = "filterCatalogMasterCategoryAssetIsGlobal", condition = "IS_GLOBAL = '1' AND SCOPE = 'MASTER_CATEGORY'")
    @OrderBy(clause = "ordering asc")
    private Set<Asset> assetsIsGlobal = new HashSet<Asset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MASTER_CATEGORY_ID")
    @Filter(name = "filterCatalogMasterCategoryAssetByMarketArea", condition = "IS_GLOBAL = '0' AND MARKET_AREA_ID = :marketAreaId AND SCOPE = 'MASTER_CATEGORY'")
    @OrderBy(clause = "ordering asc")
    private Set<Asset> assetsByMarketArea = new HashSet<Asset>();

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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

//	public boolean isRoot() {
//	return isRoot;
//}
//
//public void setRoot(boolean isRoot) {
//	this.isRoot = isRoot;
//}

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

    public Set<CatalogCategoryMasterAttribute> getCatalogCategoryGlobalAttributes() {
        return catalogCategoryGlobalAttributes;
    }

    public void setCatalogCategoryGlobalAttributes(Set<CatalogCategoryMasterAttribute> catalogCategoryGlobalAttributes) {
        this.catalogCategoryGlobalAttributes = catalogCategoryGlobalAttributes;
    }

    public Set<CatalogCategoryMasterAttribute> getCatalogCategoryMarketAreaAttributes() {
        return catalogCategoryMarketAreaAttributes;
    }

    public void setCatalogCategoryMarketAreaAttributes(Set<CatalogCategoryMasterAttribute> catalogCategoryMarketAreaAttributes) {
        this.catalogCategoryMarketAreaAttributes = catalogCategoryMarketAreaAttributes;
    }

    public Set<CatalogCategoryMaster> getCatalogCategories() {
        return catalogCategories;
    }

    public List<CatalogCategoryMaster> getCatalogCategories(final Long marketAreaId) {
        List<CatalogCategoryMaster> sortedObjects = new LinkedList<CatalogCategoryMaster>(catalogCategories);
        Collections.sort(sortedObjects, new Comparator<CatalogCategoryMaster>() {
            @Override
            public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
                if (o1 != null
                        && o2 != null) {
                    Integer order1 = o1.getOrder(marketAreaId);
                    Integer order2 = o2.getOrder(marketAreaId);
                    if (order1 != null
                            && order2 != null) {
                        return order1.compareTo(order2);
                    } else {
                        return o1.getId().compareTo(o2.getId());
                    }
                }
                return 0;
            }
        });
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

    public Set<Asset> getAssetsIsGlobal() {
        return assetsIsGlobal;
    }

    public void setAssetsIsGlobal(Set<Asset> assetsIsGlobal) {
        this.assetsIsGlobal = assetsIsGlobal;
    }

    public Set<Asset> getAssetsByMarketArea() {
        return assetsByMarketArea;
    }

    public void setAssetsByMarketArea(Set<Asset> assetsByMarketArea) {
        this.assetsByMarketArea = assetsByMarketArea;
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
        CatalogCategoryMasterAttribute catalogCategoryGlobalAttribute = getCatalogCategoryAttribute(catalogCategoryGlobalAttributes, attributeCode, marketAreaId, localizationCode);

        // 2: GET THE MARKET AREA VALUE
        CatalogCategoryMasterAttribute catalogCategoryMarketAreaAttribute = getCatalogCategoryAttribute(catalogCategoryMarketAreaAttributes, attributeCode, marketAreaId,
                localizationCode);

        if (catalogCategoryMarketAreaAttribute != null) {
            catalogCategoryAttributeToReturn = catalogCategoryMarketAreaAttribute;
        } else if (catalogCategoryGlobalAttribute != null) {
            catalogCategoryAttributeToReturn = catalogCategoryGlobalAttribute;
        }

        return catalogCategoryAttributeToReturn;
    }

    private CatalogCategoryMasterAttribute getCatalogCategoryAttribute(Set<CatalogCategoryMasterAttribute> catalogCategoryAttributes, String attributeCode, Long marketAreaId,
                                                                       String localizationCode) {
        CatalogCategoryMasterAttribute catalogCategoryAttributeToReturn = null;
        List<CatalogCategoryMasterAttribute> catalogCategoryAttributesFilter = new ArrayList<CatalogCategoryMasterAttribute>();
        if (catalogCategoryAttributes != null) {
            // GET ALL CategoryAttributes FOR THIS ATTRIBUTE
            for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributes.iterator(); iterator.hasNext(); ) {
                CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
                if (attributeDefinition != null
                        && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    catalogCategoryAttributesFilter.add(catalogCategoryAttribute);
                }
            }
            // REMOVE ALL CategoryAttributes NOT ON THIS MARKET AREA
            if (marketAreaId != null) {
                for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext(); ) {
                    CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        if (catalogCategoryAttribute.getMarketAreaId() != null
                                && BooleanUtils.negate(catalogCategoryAttribute.getMarketAreaId().equals(marketAreaId))) {
                            iterator.remove();
                        }
                    }
                }
            }
            // FINALLY RETAIN ONLY CategoryAttributes FOR THIS LOCALIZATION CODE
            if (StringUtils.isNotEmpty(localizationCode)) {
                for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext(); ) {
                    CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        String attributeLocalizationCode = catalogCategoryAttribute.getLocalizationCode();
                        if (StringUtils.isNotEmpty(attributeLocalizationCode)
                                && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))) {
                            iterator.remove();
                        }
                    }
                }
                if (catalogCategoryAttributesFilter.size() == 0) {
                    // TODO : warning ?

                    // NOT ANY CategoryAttributes FOR THIS LOCALIZATION CODE - GET A FALLBACK
                    for (Iterator<CatalogCategoryMasterAttribute> iterator = catalogCategoryMarketAreaAttributes.iterator(); iterator.hasNext(); ) {
                        CatalogCategoryMasterAttribute catalogCategoryAttribute = (CatalogCategoryMasterAttribute) iterator.next();

                        // TODO : get a default locale code from setting database ?

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
            i18nName = getBusinessName();
        }
        return i18nName;
    }

    public Integer getOrder(Long marketAreaId) {
        return (Integer) getValue(CatalogCategoryVirtualAttribute.CATALOG_CATEGORY_ATTRIBUTE_ORDER, marketAreaId, null);
    }

    // ASSET
    public Asset getDefaultPaskshotImage(String size) {
        Asset defaultProductImage = null;
        if (getAssetsIsGlobal() != null
                && StringUtils.isNotEmpty(size)) {
            for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext(); ) {
                Asset productAsset = (Asset) iterator.next();
                if (AssetType.PACKSHOT.equals(productAsset.getType())
                        && size.equals(productAsset.getSize())
                        && productAsset.isDefault()) {
                    defaultProductImage = productAsset;
                }
            }
            for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext(); ) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.PACKSHOT.equals(productImage.getType())
                        && size.equals(productImage.getSize())) {
                    defaultProductImage = productImage;
                }
            }
        }
        return defaultProductImage;
    }

    public Asset getDefaultBackgroundImage() {
        Asset defaultProductImage = null;
        if (getAssetsIsGlobal() != null) {
            for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext(); ) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.BACKGROUND.equals(productImage.getType())
                        && productImage.isDefault()) {
                    defaultProductImage = productImage;
                }
            }
            for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext(); ) {
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
        if (getAssetsIsGlobal() != null) {
            for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext(); ) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.ICON.equals(productImage.getType())
                        && productImage.isDefault()) {
                    defaultProductImage = productImage;
                }
            }
            for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext(); ) {
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
        result = prime * result
                + ((businessName == null) ? 0 : businessName.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result
                + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result
                + ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
        result = prime
                * result
                + ((defaultParentCatalogCategory == null) ? 0
                : defaultParentCatalogCategory.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (isDefault ? 1231 : 1237);
        result = prime * result + version;
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
        if (businessName == null) {
            if (other.businessName != null)
                return false;
        } else if (!businessName.equals(other.businessName))
            return false;
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
        if (dateUpdate == null) {
            if (other.dateUpdate != null)
                return false;
        } else if (!dateUpdate.equals(other.dateUpdate))
            return false;
        if (defaultParentCatalogCategory == null) {
            if (other.defaultParentCatalogCategory != null)
                return false;
        } else if (!defaultParentCatalogCategory
                .equals(other.defaultParentCatalogCategory))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isDefault != other.isDefault)
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductCategoryMaster [id=" + id + ", version=" + version
                + ", businessName=" + businessName + ", description="
                + description + ", code=" + code + ", isDefault=" + isDefault
                + ", defaultParentProductCategory="
                + defaultParentCatalogCategory
                + ", productCategoryGlobalAttributes="
                + catalogCategoryGlobalAttributes
                + ", productCategoryMarketAreaAttributes="
                + catalogCategoryMarketAreaAttributes +
                ", productMarketings="
                + productMarketings + ", dateCreate=" + dateCreate
                + ", dateUpdate=" + dateUpdate + "]";
    }

}