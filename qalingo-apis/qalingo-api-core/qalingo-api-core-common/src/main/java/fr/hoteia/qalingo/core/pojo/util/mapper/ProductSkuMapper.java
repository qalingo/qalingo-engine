package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.*;
import fr.hoteia.qalingo.core.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("productSkuMapper")
public class ProductSkuMapper extends AbstractPojoMapper<ProductSku, ProductSkuPojo> {

    private static final String[] IGNORED_PROPERTIES = {"productMarketing", "productSkuGlobalAttributes", "productSkuMarketAreaAttributes", "assetsIsGlobal",
            "assetsByMarketArea", "prices", "stocks", "retailers"};

    @Autowired @Qualifier("productMarketingMapper") private PojoMapper<ProductMarketing, ProductMarketingPojo> productMarketingMapper;
    @Autowired @Qualifier("productSkuAttributeMapper") private PojoMapper<ProductSkuAttribute, ProductSkuAttributePojo> attributeMapper;
    @Autowired @Qualifier("assetMapper") private PojoMapper<Asset, AssetPojo> assetMapper;
    @Autowired @Qualifier("productSkuPriceMapper") private PojoMapper<ProductSkuPrice, ProductSkuPricePojo> priceMapper;
    @Autowired @Qualifier("productSkuStockMapper") private PojoMapper<ProductSkuStock, ProductSkuStockPojo> stockMapper;
    @Autowired @Qualifier("retailerMapper") private PojoMapper<Retailer, RetailerPojo> retailerMapper;

    @Override
    public Class<ProductSku> getObjectType() {
        return ProductSku.class;
    }

    @Override
    public Class<ProductSkuPojo> getPojoType() {
        return ProductSkuPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final ProductSkuPojo jsonPojo, final ProductSku object) {
        object.setProductMarketing(productMarketingMapper.fromPojo(jsonPojo.getProductMarketing()));
        object.setProductSkuGlobalAttributes(asSet(attributeMapper.fromPojo(jsonPojo.getProductSkuGlobalAttributes())));
        object.setProductSkuMarketAreaAttributes(asSet(attributeMapper.fromPojo(jsonPojo.getProductSkuMarketAreaAttributes())));
        object.setAssetsIsGlobal(asSet(assetMapper.fromPojo(jsonPojo.getAssetsIsGlobal())));
        object.setAssetsByMarketArea(asSet(assetMapper.fromPojo(jsonPojo.getAssetsByMarketArea())));
        object.setPrices(asSet(priceMapper.fromPojo(jsonPojo.getPrices())));
        object.setStocks(asSet(stockMapper.fromPojo(jsonPojo.getStocks())));
        object.setRetailers(asSet(retailerMapper.fromPojo(jsonPojo.getRetailers())));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final ProductSku object, final ProductSkuPojo jsonPojo) {
        jsonPojo.setProductMarketing(productMarketingMapper.toPojo(object.getProductMarketing()));
        jsonPojo.setProductSkuGlobalAttributes(attributeMapper.toPojo(object.getProductSkuGlobalAttributes()));
        jsonPojo.setProductSkuMarketAreaAttributes(attributeMapper.toPojo(object.getProductSkuMarketAreaAttributes()));
        jsonPojo.setAssetsIsGlobal(assetMapper.toPojo(object.getAssetsIsGlobal()));
        jsonPojo.setAssetsByMarketArea(assetMapper.toPojo(object.getAssetsByMarketArea()));
        jsonPojo.setPrices(priceMapper.toPojo(object.getPrices()));
        jsonPojo.setStocks(stockMapper.toPojo(object.getStocks()));
        jsonPojo.setRetailers(retailerMapper.toPojo(object.getRetailers()));
    }
}
