package fr.hoteia.qalingo.core.pojo.util.mapper.catalog;

import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
import fr.hoteia.qalingo.core.pojo.AssetPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryMasterPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryVirtualAttributePojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryVirtualPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("catalogCategoryVirtualMapper")
public class CatalogCategoryVirtualMapper extends AbstractPojoMapper<CatalogCategoryVirtual, CatalogCategoryVirtualPojo> {

    private static final String[] IGNORED_PROPERTIES = {"defaultParentCatalogCategory", "categoryMaster", "catalogCategoryGlobalAttributes",
            "catalogCategoryMarketAreaAttributes", "catalogCategories", "productMarketings", "assetsIsGlobal", "assetsByMarketArea"};

    @Autowired @Qualifier("catalogMasterMapper") private PojoMapper<CatalogCategoryMaster, CatalogCategoryMasterPojo> categoryMasterMapper;
    @Autowired @Qualifier("catalogCategoryVirtualAttributeMapper") private PojoMapper<CatalogCategoryVirtualAttribute, CatalogCategoryVirtualAttributePojo> attributeMapper;
    @Autowired @Qualifier("assetMapper") private PojoMapper<Asset, AssetPojo> assetMapper;

    @Override
    public Class<CatalogCategoryVirtual> getObjectType() {
        return CatalogCategoryVirtual.class;
    }

    @Override
    public Class<CatalogCategoryVirtualPojo> getPojoType() {
        return CatalogCategoryVirtualPojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(CatalogCategoryVirtualPojo jsonPojo, CatalogCategoryVirtual object) {
        object.setDefaultParentCatalogCategory(fromPojo(jsonPojo.getDefaultParentCatalogCategory()));
        object.setCategoryMaster(categoryMasterMapper.fromPojo(jsonPojo.getCategoryMaster()));
        object.setCatalogCategoryGlobalAttributes(asSet(attributeMapper.fromPojo(jsonPojo.getCatalogCategoryGlobalAttributes())));
        object.setCatalogCategoryMarketAreaAttributes(asSet(attributeMapper.fromPojo(jsonPojo.getCatalogCategoryMarketAreaAttributes())));
        object.setAssetsIsGlobal(asSet(assetMapper.fromPojo(jsonPojo.getAssetsIsGlobal())));
        object.setAssetsByMarketArea(asSet(assetMapper.fromPojo(jsonPojo.getAssetsByMarketArea())));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(CatalogCategoryVirtual object, CatalogCategoryVirtualPojo jsonPojo) {
        jsonPojo.setDefaultParentCatalogCategory(toPojo(object.getDefaultParentCatalogCategory()));
        jsonPojo.setCategoryMaster(categoryMasterMapper.toPojo(object.getCategoryMaster()));
        jsonPojo.setCatalogCategoryGlobalAttributes(asSet(attributeMapper.toPojo(object.getCatalogCategoryGlobalAttributes())));
        jsonPojo.setCatalogCategoryMarketAreaAttributes(asSet(attributeMapper.toPojo(object.getCatalogCategoryMarketAreaAttributes())));
        jsonPojo.setAssetsIsGlobal(assetMapper.toPojo(object.getAssetsIsGlobal()));
        jsonPojo.setAssetsByMarketArea(assetMapper.toPojo(object.getAssetsByMarketArea()));
    }
}
