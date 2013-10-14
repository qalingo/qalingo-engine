package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.pojo.ProductSkuPojo;
import org.springframework.stereotype.Component;

@Component("productSkuMapper")
public class ProductSkuMapper extends AbstractPojoMapper<ProductSku, ProductSkuPojo> {
    @Override
    public Class<ProductSku> getObjectType() {
        return ProductSku.class;
    }

    @Override
    public Class<ProductSkuPojo> getPojoType() {
        return ProductSkuPojo.class;
    }
}
