package fr.hoteia.qalingo.core.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.CatalogCategoryType;

public class CatalogCategoryMasterPojo {

    private Long id;
    private int version;
    private String businessName;
    private String description;
    private String code;
    private boolean isDefault;
    private Date dateCreate;
    private Date dateUpdate;

    private CatalogCategoryTypePojo catalogCategoryType;
    private CatalogCategoryMasterPojo defaultParentCatalogCategory;

    private Collection<CatalogCategoryMasterAttributePojo> catalogCategoryGlobalAttributes = new ArrayList<CatalogCategoryMasterAttributePojo>();
    private Collection<CatalogCategoryMasterAttributePojo> catalogCategoryMarketAreaAttributes = new ArrayList<CatalogCategoryMasterAttributePojo>();
    private Collection<CatalogCategoryMasterPojo> catalogCategories = new ArrayList<CatalogCategoryMasterPojo>();
    private Collection<ProductMarketingPojo> productMarketings = new ArrayList<ProductMarketingPojo>();
    private Collection<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private Collection<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();
}
