package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.ProductMarketingType;
import fr.hoteia.qalingo.core.pojo.ProductMarketingTypePojo;
import org.springframework.stereotype.Component;

@Component("productMarketingTypeMapper")
public class ProductMarketingTypeMapper extends AbstractPojoMapper<ProductMarketingType, ProductMarketingTypePojo> {

    @Override
    public Class<ProductMarketingType> getObjectType() {
        return ProductMarketingType.class;
    }

    @Override
    public Class<ProductMarketingTypePojo> getPojoType() {
        return ProductMarketingTypePojo.class;
    }
}
