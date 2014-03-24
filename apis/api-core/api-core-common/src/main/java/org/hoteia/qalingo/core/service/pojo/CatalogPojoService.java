package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.pojo.product.ProductSkuPojo;

public interface CatalogPojoService {

    List<CatalogPojo> getAllCatalogMasters();

    CatalogPojo getMasterCatalogById(String catalogId);

    CatalogPojo getVirtualCatalogById(String catalogId);

    CatalogPojo getMasterCatalog(CatalogMaster catalogMaster);

    CatalogPojo getVirtualCatalog(CatalogVirtual catalogVirtual);

    CatalogCategoryPojo buildCatalogCategory(CatalogCategoryMaster catalogCategory);
    
    CatalogCategoryPojo buildCatalogCategory(CatalogCategoryVirtual catalogCategory);
    
    ProductMarketingPojo buildProductMarketing(ProductMarketing productMarketing);
    
    ProductSkuPojo buildProductSku(ProductSku productSku);
    
}