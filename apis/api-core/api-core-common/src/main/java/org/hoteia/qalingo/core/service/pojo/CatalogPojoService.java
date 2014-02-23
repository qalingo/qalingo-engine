package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.pojo.product.ProductSkuPojo;

public interface CatalogPojoService {

    List<CatalogPojo> getAllCatalogMasters();

    CatalogPojo getMasterCatalogById(String catalogId);

    CatalogPojo getVirtualCatalogById(String catalogId);

    CatalogPojo getMasterCatalog(CatalogMaster catalogMaster);

    CatalogPojo getVirtualCatalog(CatalogVirtual catalogVirtual);

    ProductMarketingPojo buildProductMarketing(ProductMarketing productMarketing);
    
    ProductSkuPojo buildProductSku(ProductSku productSku);
    
}