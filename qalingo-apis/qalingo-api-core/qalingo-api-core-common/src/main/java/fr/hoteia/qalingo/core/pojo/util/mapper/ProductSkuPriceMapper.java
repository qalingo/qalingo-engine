package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.CurrencyReferential;
import fr.hoteia.qalingo.core.domain.ProductSkuPrice;
import fr.hoteia.qalingo.core.pojo.CurrencyReferentialPojo;
import fr.hoteia.qalingo.core.pojo.ProductSkuPricePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("productSkuPriceMapper")
public class ProductSkuPriceMapper extends AbstractPojoMapper<ProductSkuPrice, ProductSkuPricePojo> {

    private static final String[] IGNORED_PROPERTIES = {"currency"};

    @Autowired @Qualifier("currencyReferentialMapper") private PojoMapper<CurrencyReferential, CurrencyReferentialPojo> currencyReferentialMapper;

    @Override
    public Class<ProductSkuPrice> getObjectType() {
        return ProductSkuPrice.class;
    }

    @Override
    public Class<ProductSkuPricePojo> getPojoType() {
        return ProductSkuPricePojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(ProductSkuPricePojo jsonPojo, ProductSkuPrice object) {
        object.setCurrency(currencyReferentialMapper.fromPojo(jsonPojo.getCurrency()));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(ProductSkuPrice object, ProductSkuPricePojo jsonPojo) {
        jsonPojo.setCurrency(currencyReferentialMapper.toPojo(object.getCurrency()));
    }
}
