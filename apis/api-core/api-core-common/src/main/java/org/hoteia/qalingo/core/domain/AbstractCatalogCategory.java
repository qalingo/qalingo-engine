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

    public abstract Set<C> getAttributes();

    public abstract List<C> getGlobalAttributes();

    public abstract List<C> getMarketAreaAttributes(Long marketAreaId);

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

    public abstract String getI18nName(String localizationCode);
    
    // ASSET
    
    public abstract Asset getDefaultBackgroundImage();

    public abstract Asset getDefaultSlideshowImage();

    public abstract Asset getDefaultPaskshotImage(String size);

    public abstract Asset getDefaultIconImage();
    
}