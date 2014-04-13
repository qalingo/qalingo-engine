package org.hoteia.qalingo.core.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

public abstract class AbstractCatalogCategory<A, B, C, D> extends AbstractEntity {

    /**
	 * Generated UID
	 */
    private static final long serialVersionUID = 1592929754749916781L;

    public abstract Long getId();

    public abstract int getVersion();

    public abstract String getCode();

    public abstract CatalogCategoryType getCatalogCategoryType();
    
    public abstract String getName();

    public abstract String getDescription();

    public abstract boolean isDefault() ;

    public abstract Integer getRanking();
    
    public abstract A getCatalog();
    
    public abstract boolean isRoot();

    public abstract B getParentCatalogCategory();

    public abstract Set<C> getCatalogCategoryAttributes();

    public abstract List<C> getCatalogCategoryGlobalAttributes();

    public abstract List<C> getCatalogCategoryMarketAreaAttributes(Long marketAreaId);

    public abstract Set<B> getCatalogCategories();

    public abstract List<B> getSortedChildCatalogCategories();    

    public abstract Set<D> getCatalogCategoryProductSkuRels();
    
    public abstract List<ProductSku> getSortedProductSkus();
    
    public abstract List<ProductMarketing> getSortedProductMarketings();

    public abstract Set<Asset> getAssets();

    public abstract List<Asset> getAssetsIsGlobal();

    public abstract List<Asset> getAssetsByMarketArea();

    public abstract Date getDateCreate();

    public abstract Date getDateUpdate();

//
//    public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode);
//
//    public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, String localizationCode) {
//        return getCatalogCategoryAttribute(attributeCode, null, localizationCode);
//    }
//
//    public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, Long marketAreaId) {
//        return getCatalogCategoryAttribute(attributeCode, marketAreaId, null);
//    }
//
//    public CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
//        CatalogCategoryVirtualAttribute catalogCategoryAttributeToReturn = null;
//
//        // 1: GET THE GLOBAL VALUE
//        CatalogCategoryVirtualAttribute catalogCategoryGlobalAttribute = getCatalogCategoryAttribute(getCatalogCategoryGlobalAttributes(), attributeCode, marketAreaId, localizationCode);
//
//        // 2: GET THE MARKET AREA VALUE
//        CatalogCategoryVirtualAttribute catalogCategoryMarketAreaAttribute = getCatalogCategoryAttribute(getCatalogCategoryMarketAreaAttributes(marketAreaId), attributeCode, marketAreaId,
//                localizationCode);
//
//        if (catalogCategoryMarketAreaAttribute != null) {
//            catalogCategoryAttributeToReturn = catalogCategoryMarketAreaAttribute;
//        } else if (catalogCategoryGlobalAttribute != null) {
//            catalogCategoryAttributeToReturn = catalogCategoryGlobalAttribute;
//        }
//
//        return catalogCategoryAttributeToReturn;
//    }
//
//    private CatalogCategoryVirtualAttribute getCatalogCategoryAttribute(List<CatalogCategoryVirtualAttribute> catalogCategoryAttributes, String attributeCode, Long marketAreaId,
//            String localizationCode) {
//        CatalogCategoryVirtualAttribute catalogCategoryAttributeToReturn = null;
//        List<CatalogCategoryVirtualAttribute> catalogCategoryAttributesFilter = new ArrayList<CatalogCategoryVirtualAttribute>();
//        if (catalogCategoryAttributes != null) {
//            // GET ALL CategoryAttributes FOR THIS ATTRIBUTE
//            for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributes.iterator(); iterator.hasNext();) {
//                CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
//                AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
//                if (attributeDefinition != null && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
//                    catalogCategoryAttributesFilter.add(catalogCategoryAttribute);
//                }
//            }
////            // REMOVE ALL CategoryAttributes NOT ON THIS MARKET AREA
////            if (marketAreaId != null) {
////                for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
////                    CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
////                    AttributeDefinition attributeDefinition = catalogCategoryAttribute.getAttributeDefinition();
////                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
////                        if (catalogCategoryAttribute.getMarketAreaId() != null && BooleanUtils.negate(catalogCategoryAttribute.getMarketAreaId().equals(marketAreaId))) {
////                            iterator.remove();
////                        }
////                    }
////                }
////            }
//            // FINALLY RETAIN ONLY CategoryAttributes FOR THIS LOCALIZATION CODE
//            if (StringUtils.isNotEmpty(localizationCode)) {
//                for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributesFilter.iterator(); iterator.hasNext();) {
//                    CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
//                    String attributeLocalizationCode = catalogCategoryAttribute.getLocalizationCode();
//                    if (StringUtils.isNotEmpty(attributeLocalizationCode) && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))) {
//                        iterator.remove();
//                    }
//                }
//                if (catalogCategoryAttributesFilter.size() == 0) {
//                    // TODO : warning ?
//
//                    // NOT ANY CategoryAttributes FOR THIS LOCALIZATION CODE -
//                    // GET A FALLBACK
//                    for (Iterator<CatalogCategoryVirtualAttribute> iterator = catalogCategoryAttributes.iterator(); iterator.hasNext();) {
//                        CatalogCategoryVirtualAttribute catalogCategoryAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
//
//                        // TODO : get a default locale code from setting
//                        // database ?
//
//                        if (Constants.DEFAULT_LOCALE_CODE.equals(catalogCategoryAttribute.getLocalizationCode())) {
//                            catalogCategoryAttributeToReturn = catalogCategoryAttribute;
//                        }
//                    }
//                }
//            }
//        }
//
//        if (catalogCategoryAttributesFilter.size() == 1) {
//            catalogCategoryAttributeToReturn = catalogCategoryAttributesFilter.get(0);
//        } else {
//            // TODO : throw error ?
//        }
//
//        return catalogCategoryAttributeToReturn;
//    }
//
//    public Object getValue(String attributeCode) {
//        return getValue(attributeCode, null, null);
//    }
//
//    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
//        CatalogCategoryVirtualAttribute catalogCategoryAttribute = getCatalogCategoryAttribute(attributeCode, marketAreaId, localizationCode);
//        if (catalogCategoryAttribute != null) {
//            return catalogCategoryAttribute.getValue();
//        }
//        return null;
//    }

    public abstract String getI18nName(String localizationCode);
    
//
//    public Integer getOrder(Long marketAreaId) {
//        return (Integer) getValue(CatalogCategoryVirtualAttribute.CATALOG_CATEGORY_ATTRIBUTE_ORDER, marketAreaId, null);
//    }
//
//    // ASSET
    
    public abstract Asset getDefaultBackgroundImage();

    public abstract Asset getDefaultSlideshowImage();

    public abstract Asset getDefaultPaskshotImage(String size);

    public abstract Asset getDefaultIconImage();
    
}