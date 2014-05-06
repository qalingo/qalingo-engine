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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.AssetType;

@Entity
@Table(name = "TECO_CATALOG_VIRTUAL_CATEGORY")
public class CatalogCategoryVirtual extends AbstractCatalogCategory<CatalogVirtual, CatalogCategoryVirtual, CatalogCategoryVirtualAttribute, CatalogCategoryVirtualProductSkuRel> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 4953461049508842305L;

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

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "IS_DEFAULT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefault;

    @Column(name = "RANKING")
    private Integer ranking;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIRTUAL_CATALOG_ID", insertable = true, updatable = true)
    private CatalogVirtual catalog;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CATEGORY_ID", insertable = true, updatable = true)
    private CatalogCategoryVirtual parentCatalogCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASTER_CATEGORY_ID", insertable = true, updatable = true)
    private CatalogCategoryMaster categoryMaster;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Set<CatalogCategoryVirtualAttribute> attributes = new HashSet<CatalogCategoryVirtualAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CatalogCategoryVirtual.class)
    @JoinColumn(name = "PARENT_CATEGORY_ID")
    private Set<CatalogCategoryVirtual> catalogCategories = new HashSet<CatalogCategoryVirtual>();

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuRel.class)
    @JoinColumn(name = "VIRTUAL_CATEGORY_ID")
    private Set<CatalogCategoryVirtualProductSkuRel> catalogCategoryProductSkuRels = new HashSet<CatalogCategoryVirtualProductSkuRel>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIRTUAL_CATEGORY_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public CatalogCategoryVirtual() {
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
        if(categoryMaster != null 
                && Hibernate.isInitialized(categoryMaster)){
            return categoryMaster.getCode();
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CatalogCategoryType getCatalogCategoryType() {
        if(categoryMaster != null && Hibernate.isInitialized(categoryMaster)
                && Hibernate.isInitialized(categoryMaster.getCatalogCategoryType())){
            return categoryMaster.getCatalogCategoryType();
        }
        return null;
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
    
    public Integer getRanking() {
        return ranking;
    }
    
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public CatalogVirtual getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogVirtual catalog) {
        this.catalog = catalog;
    }
    
    public boolean isRoot() {
        if (getParentCatalogCategory() == null) {
            return true;
        }
        return false;
    }

    public CatalogCategoryVirtual getParentCatalogCategory() {
        return parentCatalogCategory;
    }

    public void setParentCatalogCategory(CatalogCategoryVirtual parentCatalogCategory) {
        this.parentCatalogCategory = parentCatalogCategory;
    }

    public CatalogCategoryMaster getCategoryMaster() {
        return categoryMaster;
    }

    public void setCategoryMaster(CatalogCategoryMaster categoryMaster) {
        this.categoryMaster = categoryMaster;
    }

    public Set<CatalogCategoryVirtualAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CatalogCategoryVirtualAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<CatalogCategoryVirtualAttribute> getGlobalAttributes() {
        List<CatalogCategoryVirtualAttribute> catalogCategoryGlobalAttributes = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            catalogCategoryGlobalAttributes = new ArrayList<CatalogCategoryVirtualAttribute>();
            for (Iterator<CatalogCategoryVirtualAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtualAttribute attribute = (CatalogCategoryVirtualAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null && attributeDefinition.isGlobal()) {
                    catalogCategoryGlobalAttributes.add(attribute);
                }
            }
        }
        return catalogCategoryGlobalAttributes;
    }

    public List<CatalogCategoryVirtualAttribute> getMarketAreaAttributes(Long marketAreaId) {
        List<CatalogCategoryVirtualAttribute> catalogCategoryMarketAreaAttributes = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            catalogCategoryMarketAreaAttributes = new ArrayList<CatalogCategoryVirtualAttribute>();
            for (Iterator<CatalogCategoryVirtualAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtualAttribute attribute = (CatalogCategoryVirtualAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null && !attributeDefinition.isGlobal()) {
                    catalogCategoryMarketAreaAttributes.add(attribute);
                }
            }
        }
        return catalogCategoryMarketAreaAttributes;
    }

    public Set<CatalogCategoryVirtual> getCatalogCategories() {
        return catalogCategories;
    }

    public List<CatalogCategoryVirtual> getSortedChildCatalogCategories() {
        List<CatalogCategoryVirtual> sortedCatalogCategories = null;
        if (catalogCategories != null 
                && Hibernate.isInitialized(catalogCategories)) {
            sortedCatalogCategories = new LinkedList<CatalogCategoryVirtual>(catalogCategories);
            Collections.sort(sortedCatalogCategories, new Comparator<CatalogCategoryVirtual>() {
                @Override
                public int compare(CatalogCategoryVirtual o1, CatalogCategoryVirtual o2) {
                    if (o1 != null && o1.getRanking() != null && o2 != null && o2.getRanking() != null) {
                        return o1.getRanking().compareTo(o2.getRanking());
                    }
                    return 0;
                }
            });
        }
        return sortedCatalogCategories;
    }
    
    public void setCatalogCategories(Set<CatalogCategoryVirtual> catalogCategories) {
        this.catalogCategories = catalogCategories;
    }

    public Set<CatalogCategoryVirtualProductSkuRel> getCatalogCategoryProductSkuRels() {
        return catalogCategoryProductSkuRels;
    }
    
    public void setCatalogCategoryProductSkuRels(Set<CatalogCategoryVirtualProductSkuRel> catalogCategoryProductSkuRels) {
        this.catalogCategoryProductSkuRels = catalogCategoryProductSkuRels;
    }
    
    public List<String> getProductSkuCodes() {
        List<String> productSkuCodes = null;
        if (catalogCategoryProductSkuRels != null
                && Hibernate.isInitialized(catalogCategoryProductSkuRels)) {
            productSkuCodes = new ArrayList<String>();
            for (Iterator<CatalogCategoryVirtualProductSkuRel> iterator = catalogCategoryProductSkuRels.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtualProductSkuRel catalogCategoryProductSkuRel = (CatalogCategoryVirtualProductSkuRel) iterator.next();
                ProductSku productSku = catalogCategoryProductSkuRel.getProductSku();
                if(productSku != null){
                    productSkuCodes.add(catalogCategoryProductSkuRel.getProductSku().getCode());
                }
            }
        }
        return productSkuCodes;
    }
    
    public List<ProductSku> getSortedProductSkus() {
        List<ProductSku> productSkus = null;
        List<ProductSku> sortedProductSkus = null;
        if (catalogCategoryProductSkuRels != null
                && Hibernate.isInitialized(catalogCategoryProductSkuRels)) {
            productSkus = new ArrayList<ProductSku>();
            for (Iterator<CatalogCategoryVirtualProductSkuRel> iterator = catalogCategoryProductSkuRels.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel = (CatalogCategoryVirtualProductSkuRel) iterator.next();
                ProductSku productSku = catalogCategoryVirtualProductSkuRel.getProductSku();
                if(productSku != null){
                    productSku.setRanking(catalogCategoryVirtualProductSkuRel.getRanking());
                    productSkus.add(catalogCategoryVirtualProductSkuRel.getProductSku());
                }
            }
            sortedProductSkus = new LinkedList<ProductSku>(productSkus);
            Collections.sort(sortedProductSkus, new Comparator<ProductSku>() {
                @Override
                public int compare(ProductSku o1, ProductSku o2) {
                    if (o1 != null && o2 != null) {
                        return (o1.getRanking() < o2.getRanking() ? -1 : (o1.getRanking() == o2.getRanking() ? 0 : 1));
                    }
                    return 0;
                }
            });
            int ranking = 1;
            for (Iterator<ProductSku> iterator = sortedProductSkus.iterator(); iterator.hasNext();) {
                ProductSku productSku = (ProductSku) iterator.next();
                productSku.setRanking(ranking);
                ranking++;
            }
        }
        return sortedProductSkus;
    }
    
    public List<ProductMarketing> getSortedProductMarketings() {
        List<ProductMarketing> productMarketings = null;
        List<ProductSku> productSkus = getSortedProductSkus();
        if (productSkus != null) {
            Map<String, ProductMarketing> mapProductMarketing = new HashMap<String, ProductMarketing>();
            int ranking = 1;
            for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
                ProductSku productSku = (ProductSku) iterator.next();
                if (productSku.getProductMarketing() != null
                        && Hibernate.isInitialized(productSku.getProductMarketing())) {
                    if(!mapProductMarketing.containsKey(productSku.getProductMarketing().getCode())){
                        productSku.getProductMarketing().setRanking(ranking);
                        mapProductMarketing.put(productSku.getProductMarketing().getCode(), productSku.getProductMarketing());
                        ranking++;
                    }
                }
            }
            productMarketings = new ArrayList<ProductMarketing>(mapProductMarketing.values());
        }
        return productMarketings;
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
                if (asset != null && asset.isGlobal()) {
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
                if (asset != null && !asset.isGlobal()) {
                    assetsByMarketArea.add(asset);
                }
            }
        }
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

    public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode) {
        return getCatalogCategoryAttribute(attributeCode, null, null);
    }

    public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, String localizationCode) {
        return getCatalogCategoryAttribute(attributeCode, null, localizationCode);
    }

    public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, Long marketAreaId) {
        return getCatalogCategoryAttribute(attributeCode, marketAreaId, null);
    }

    public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
        CatalogCategoryVirtualAttribute catalogCategoryAttributeToReturn = null;

        // 1: GET THE GLOBAL VALUE
        CatalogCategoryVirtualAttribute catalogCategoryGlobalAttribute = getCatalogCategoryAttribute(getGlobalAttributes(), attributeCode, marketAreaId, localizationCode);

        // 2: GET THE MARKET AREA VALUE
        CatalogCategoryVirtualAttribute catalogCategoryMarketAreaAttribute = getCatalogCategoryAttribute(getMarketAreaAttributes(marketAreaId), attributeCode, marketAreaId,
                localizationCode);

        if (catalogCategoryMarketAreaAttribute != null) {
            catalogCategoryAttributeToReturn = catalogCategoryMarketAreaAttribute;
        } else if (catalogCategoryGlobalAttribute != null) {
            catalogCategoryAttributeToReturn = catalogCategoryGlobalAttribute;
        }

        return catalogCategoryAttributeToReturn;
    }

    private CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(List<CatalogCategoryVirtualAttribute> catalogCategoryAttributes, String attributeCode, Long marketAreaId,
            String localizationCode) {
        CatalogCategoryVirtualAttribute catalogCategoryAttributeToReturn = null;
        List<CatalogCategoryVirtualAttribute> catalogCategoryAttributesFilter = new ArrayList<CatalogCategoryVirtualAttribute>();
        if (catalogCategoryAttributes != null) {
            // GET ALL CategoryAttributes FOR THIS ATTRIBUTE
            for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributes.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
                AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
                if (attributeDefinition != null && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    catalogCategoryAttributesFilter.add(catalogCategoryAttribute);
                }
            }
//            // REMOVE ALL CategoryAttributes NOT ON THIS MARKET AREA
//            if (marketAreaId != null) {
//                for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
//                    CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
//                    AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
//                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
//                        if (catalogCategoryAttribute.getMarketAreaId() != null && BooleanUtils.negate(catalogCategoryAttribute.getMarketAreaId().equals(marketAreaId))) {
//                            iterator.remove();
//                        }
//                    }
//                }
//            }
            // FINALLY RETAIN ONLY CategoryAttributes FOR THIS LOCALIZATION CODE
            if (StringUtils.isNotEmpty(localizationCode)) {
                for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
                    CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
                    String attributeLocalizationCode = catalogCategoryAttribute.getLocalizationCode();
                    if (StringUtils.isNotEmpty(attributeLocalizationCode) && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))) {
                        iterator.remove();
                    }
                }
                if (catalogCategoryAttributesFilter.size() == 0) {
                    // TODO : warning ?

                    // NOT ANY CategoryAttributes FOR THIS LOCALIZATION CODE -
                    // GET A FALLBACK
                    for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributes.iterator(); iterator.hasNext();) {
                        CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();

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
        CatalogCategoryVirtualAttribute catalogCategoryAttribute = getCatalogCategoryAttribute(attributeCode, marketAreaId, localizationCode);
        if (catalogCategoryAttribute != null) {
            return catalogCategoryAttribute.getValue();
        }
        return null;
    }

    public String getI18nName(String localizationCode) {
        String i18nName = (String) getValue(CatalogCategoryVirtualAttribute.CATALOG_CATEGORY_ATTRIBUTE_I18N_NAME, null, localizationCode);
        if (StringUtils.isEmpty(i18nName)) {
            i18nName = getName();
        }
        return i18nName;
    }

    // ASSET
    
    public Asset getDefaultBackgroundImage() {
        Asset defaultProductImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
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

    public Asset getDefaultSlideshowImage() {
        Asset defaultProductImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if (assetsIsGlobal != null) {
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.SLIDESHOW.equals(productImage.getType()) 
                        && productImage.isDefault()) {
                    defaultProductImage = productImage;
                }
            }
            for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
                Asset productImage = (Asset) iterator.next();
                if (AssetType.SLIDESHOW.equals(productImage.getType())) {
                    defaultProductImage = productImage;
                }
            }
        }
        return defaultProductImage;
    }

    public Asset getDefaultPaskshotImage(String size) {
        Asset defaultProductImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if (assetsIsGlobal != null && StringUtils.isNotEmpty(size)) {
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

    public Asset getDefaultIconImage() {
        Asset defaultProductImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
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
        CatalogCategoryVirtual other = (CatalogCategoryVirtual) obj;
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
        return "CatalogCategoryVirtual [id=" + id + ", version=" + version + ", ranking=" + ranking + ", name=" + name + ", description=" + description + ", code=" + code + ", isDefault=" + isDefault
                + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}