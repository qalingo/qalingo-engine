package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.*;
import fr.hoteia.qalingo.core.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("retailerMapper")
public class RetailerMapper extends AbstractPojoMapper<Retailer, RetailerPojo> {

    private static final String[] IGNORED_PROPERTIES = {"links", "addresses", "stores", "assetsIsGlobal", "assetsByMarketArea", "retailerGlobalAttributes",
            "retailerMarketAreaAttributes", "customerRates", "customerComments", "retailerTags"};

    @Autowired @Qualifier("retailerLinkMapper") private PojoMapper<RetailerLink, RetailerLinkPojo> linkMapper;
    @Autowired @Qualifier("retailerAddressMapper") private PojoMapper<RetailerAddress, RetailerAddressPojo> addressMapper;
    @Autowired @Qualifier("storeMapper") private PojoMapper<Store, StorePojo> storeMapper;
    @Autowired @Qualifier("assetMapper") private PojoMapper<Asset, AssetPojo> assetMapper;
    @Autowired @Qualifier("retailerAttributeMapper") private PojoMapper<RetailerAttribute, RetailerAttributePojo> attributeMapper;
    @Autowired @Qualifier("retailerCustomerRateMapper") private PojoMapper<RetailerCustomerRate, RetailerCustomerRatePojo> rateMapper;
    @Autowired @Qualifier("retailerCustomerCommentMapper") private PojoMapper<RetailerCustomerComment, RetailerCustomerCommentPojo> commentMapper;
    @Autowired @Qualifier("retailerTagMapper") private PojoMapper<RetailerTag, RetailerTagPojo> tagMapper;

    @Override
    public Class<Retailer> getObjectType() {
        return Retailer.class;
    }

    @Override
    public Class<RetailerPojo> getPojoType() {
        return RetailerPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final RetailerPojo jsonPojo, final Retailer object) {
        object.setLinks(asSet(linkMapper.fromPojo(jsonPojo.getLinks())));
        object.setAddresses(asSet(addressMapper.fromPojo(jsonPojo.getAddresses())));
        object.setStores(asSet(storeMapper.fromPojo(jsonPojo.getStores())));
        object.setAssetsIsGlobal(asSet(assetMapper.fromPojo(jsonPojo.getAssetsIsGlobal())));
        object.setAssetsByMarketArea(asSet(assetMapper.fromPojo(jsonPojo.getAssetsByMarketArea())));
        object.setRetailerGlobalAttributes(asSet(attributeMapper.fromPojo(jsonPojo.getRetailerGlobalAttributes())));
        object.setRetailerMarketAreaAttributes(asSet(attributeMapper.fromPojo(jsonPojo.getRetailerMarketAreaAttributes())));
        object.setCustomerRates(asSet(rateMapper.fromPojo(jsonPojo.getCustomerRates())));
        object.setCustomerComments(asSet(commentMapper.fromPojo(jsonPojo.getCustomerComments())));
        object.setRetailerTags(asSet(tagMapper.fromPojo(jsonPojo.getRetailerTags())));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final Retailer object, final RetailerPojo jsonPojo) {
        jsonPojo.setLinks(linkMapper.toPojo(object.getLinks()));
        jsonPojo.setAddresses(addressMapper.toPojo(object.getAddresses()));
        jsonPojo.setStores(storeMapper.toPojo(object.getStores()));
        jsonPojo.setAssetsIsGlobal(assetMapper.toPojo(object.getAssetsIsGlobal()));
        jsonPojo.setAssetsByMarketArea(assetMapper.toPojo(object.getAssetsByMarketArea()));
        jsonPojo.setRetailerGlobalAttributes(attributeMapper.toPojo(object.getRetailerGlobalAttributes()));
        jsonPojo.setRetailerMarketAreaAttributes(attributeMapper.toPojo(object.getRetailerMarketAreaAttributes()));
        jsonPojo.setCustomerRates(rateMapper.toPojo(object.getCustomerRates()));
        jsonPojo.setCustomerComments(commentMapper.toPojo(object.getCustomerComments()));
        jsonPojo.setRetailerTags(tagMapper.toPojo(object.getRetailerTags()));
    }
}
