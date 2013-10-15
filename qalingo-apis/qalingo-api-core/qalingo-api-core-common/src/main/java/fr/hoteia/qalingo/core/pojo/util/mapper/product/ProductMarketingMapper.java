package fr.hoteia.qalingo.core.pojo.util.mapper.product;

import fr.hoteia.qalingo.core.domain.*;
import fr.hoteia.qalingo.core.pojo.*;
import fr.hoteia.qalingo.core.pojo.product.*;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("productMarketingMapper")
public class ProductMarketingMapper extends AbstractPojoMapper<ProductMarketing, ProductMarketingPojo> {

    private static final String[] IGNORED_PROPERTIES = {"productBrand", "productMarketingType", "productMarketingGlobalAttributes", "productMarketingMarketAreaAttributes",
            "productSkus", "productAssociationLinks", "assetsIsGlobal", "assetsByMarketArea"};

    @Autowired @Qualifier("productBrandMapper") private PojoMapper<ProductBrand, ProductBrandPojo> brandMapper;
    @Autowired @Qualifier("productMarketingTypeMapper") private PojoMapper<ProductMarketingType, ProductMarketingTypePojo> marketingTypeMapper;
    @Autowired @Qualifier("productMarketingAttributeMapper") private PojoMapper<ProductMarketingAttribute, ProductMarketingAttributePojo> marketingAttributeMapper;
    @Autowired @Qualifier("productSkuMapper") private PojoMapper<ProductSku, ProductSkuPojo> productSkuMapper;
    @Autowired @Qualifier("productAssociationLinkMapper") private PojoMapper<ProductAssociationLink, ProductAssociationLinkPojo> associationLinkMapper;
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
        object.setProductBrand(brandMapper.fromPojo(jsonPojo.getProductBrand()));
        object.setProductMarketingType(marketingTypeMapper.fromPojo(jsonPojo.getProductMarketingType()));
        object.setProductMarketingGlobalAttributes(asSet(marketingAttributeMapper.fromPojo(jsonPojo.getProductMarketingGlobalAttributes())));
        object.setProductMarketingMarketAreaAttributes(asSet(marketingAttributeMapper.fromPojo(jsonPojo.getProductMarketingMarketAreaAttributes())));
        object.setProductSkus(asSet(productSkuMapper.fromPojo(jsonPojo.getProductSkus())));
        object.setProductAssociationLinks(asSet(associationLinkMapper.fromPojo(jsonPojo.getProductAssociationLinks())));
        object.setAssetsIsGlobal(asSet(assetMapper.fromPojo(jsonPojo.getAssetsIsGlobal())));
        object.setAssetsByMarketArea(asSet(assetMapper.fromPojo(jsonPojo.getAssetsByMarketArea())));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(ProductMarketing object, ProductMarketingPojo jsonPojo) {
        jsonPojo.setProductBrand(brandMapper.toPojo(object.getProductBrand()));
        jsonPojo.setProductMarketingType(marketingTypeMapper.toPojo(object.getProductMarketingType()));
        jsonPojo.setProductMarketingGlobalAttributes(marketingAttributeMapper.toPojo(object.getProductMarketingGlobalAttributes()));
        jsonPojo.setProductMarketingMarketAreaAttributes(marketingAttributeMapper.toPojo(object.getProductMarketingMarketAreaAttributes()));
        jsonPojo.setProductSkus(productSkuMapper.toPojo(object.getProductSkus()));
        jsonPojo.setProductAssociationLinks(associationLinkMapper.toPojo(object.getProductAssociationLinks()));
        jsonPojo.setAssetsIsGlobal(assetMapper.toPojo(object.getAssetsIsGlobal()));
        jsonPojo.setAssetsByMarketArea(assetMapper.toPojo(object.getAssetsByMarketArea()));
    }
}
