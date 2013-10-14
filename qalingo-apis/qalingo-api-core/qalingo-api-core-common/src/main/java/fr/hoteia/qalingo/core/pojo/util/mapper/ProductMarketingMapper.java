package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.*;
import fr.hoteia.qalingo.core.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("productMarketingMapper")
public class ProductMarketingMapper extends AbstractPojoMapper<ProductMarketing, ProductMarketingPojo> {

    private static final String[] IGNORED_PROPERTIES = {"productBrand", "productMarketingType", "productMarketingGlobalAttributes", "productMarketingMarketAreaAttributes",
            "productSkus", "productAssociationLinks", "assetsIsGlobal", "assetsByMarketArea"};

    @Autowired @Qualifier("productBrandMapper") private PojoMapper<ProductBrand, ProductBrandPojo> productBrandMapper;
    @Autowired @Qualifier("productMarketingTypeMapper") private PojoMapper<ProductMarketingType, ProductMarketingTypePojo> productMarketingTypeMapper;
    @Autowired @Qualifier("productMarketingAttributeMapper") private PojoMapper<ProductMarketingAttribute, ProductMarketingAttributePojo> productMarketingAttributeMapper;
    @Autowired @Qualifier("productSkuMapper") private PojoMapper<ProductSku, ProductSkuPojo> productSkuMapper;
    @Autowired @Qualifier("productAssociationLinkMapper") private PojoMapper<ProductAssociationLink, ProductAssociationLinkPojo> productAssociationLinkMapper;
    @Autowired @Qualifier("assetMapper") private PojoMapper<Asset, AssetPojo> assetMapper;

    @Override
    public Class<ProductMarketing> getObjectType() {
        return ProductMarketing.class;
    }

    @Override
    public Class<ProductMarketingPojo> getPojoType() {
        return ProductMarketingPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(ProductMarketingPojo jsonPojo, ProductMarketing object) {
        object.setProductBrand(productBrandMapper.fromPojo(jsonPojo.getProductBrand()));
        object.setProductMarketingType(productMarketingTypeMapper.fromPojo(jsonPojo.getProductMarketingType()));
        object.setProductMarketingGlobalAttributes(asSet(productMarketingAttributeMapper.fromPojo(jsonPojo.getProductMarketingGlobalAttributes())));
        object.setProductMarketingMarketAreaAttributes(asSet(productMarketingAttributeMapper.fromPojo(jsonPojo.getProductMarketingMarketAreaAttributes())));
        object.setProductSkus(asSet(productSkuMapper.fromPojo(jsonPojo.getProductSkus())));
        object.setProductAssociationLinks(asSet(productAssociationLinkMapper.fromPojo(jsonPojo.getProductAssociationLinks())));
        object.setAssetsIsGlobal(asSet(assetMapper.fromPojo(jsonPojo.getAssetsIsGlobal())));
        object.setAssetsByMarketArea(asSet(assetMapper.fromPojo(jsonPojo.getAssetsByMarketArea())));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(ProductMarketing object, ProductMarketingPojo jsonPojo) {
        jsonPojo.setProductBrand(productBrandMapper.toPojo(object.getProductBrand()));
        jsonPojo.setProductMarketingType(productMarketingTypeMapper.toPojo(object.getProductMarketingType()));
        jsonPojo.setProductMarketingGlobalAttributes(productMarketingAttributeMapper.toPojo(object.getProductMarketingGlobalAttributes()));
        jsonPojo.setProductMarketingMarketAreaAttributes(productMarketingAttributeMapper.toPojo(object.getProductMarketingMarketAreaAttributes()));
        jsonPojo.setProductSkus(productSkuMapper.toPojo(object.getProductSkus()));
        jsonPojo.setProductAssociationLinks(productAssociationLinkMapper.toPojo(object.getProductAssociationLinks()));
        jsonPojo.setAssetsIsGlobal(assetMapper.toPojo(object.getAssetsIsGlobal()));
        jsonPojo.setAssetsByMarketArea(assetMapper.toPojo(object.getAssetsByMarketArea()));
    }
}
