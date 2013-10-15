package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.ProductSkuStock;
import fr.hoteia.qalingo.core.pojo.ProductSkuStockPojo;
import org.springframework.stereotype.Component;

@Component("productSkuStockMapper")
public class ProductSkuStockMapper extends AbstractPojoMapper<ProductSkuStock, ProductSkuStockPojo> {
    @Override
    public Class<ProductSkuStock> getObjectType() {
        return ProductSkuStock.class;
    }

    @Override
    public Class<ProductSkuStockPojo> getPojoType() {
        return ProductSkuStockPojo.class;
    }
}
