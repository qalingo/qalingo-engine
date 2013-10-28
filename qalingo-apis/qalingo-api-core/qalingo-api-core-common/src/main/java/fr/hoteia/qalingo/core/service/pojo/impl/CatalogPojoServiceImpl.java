package fr.hoteia.qalingo.core.service.pojo.impl;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import fr.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import fr.hoteia.qalingo.core.pojo.product.ProductSkuPojo;
import fr.hoteia.qalingo.core.service.CatalogService;
import fr.hoteia.qalingo.core.service.pojo.CatalogPojoService;

@Service("catalogPojoService")
@Transactional(readOnly = true)
public class CatalogPojoServiceImpl implements CatalogPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Mapper mapper;
    
    @Autowired
    protected CatalogService catalogService;

    @Override
    public List<CatalogPojo> getAllCatalogMasters() {
        final List<CatalogMaster> allCatalogMasters = catalogService.findAllCatalogMasters();
        logger.debug("Found {} catalogs", allCatalogMasters.size());
        return mapAll(mapper, allCatalogMasters, CatalogPojo.class);
    }

    @Override
    public CatalogPojo getCatalogById(String catalogId) {
        final CatalogMaster catalog = catalogService.getProductCatalogById(catalogId);
        logger.debug("Found catalog {} for id {}", catalog, catalogId);
        return mapper.map(catalog, CatalogPojo.class);
    }
    
    @Override
    public CatalogPojo getCatalog(CatalogMaster catalogMaster) {
        final CatalogPojo catalogPojo = mapper.map(catalogMaster, CatalogPojo.class);
        logger.debug("Load {} catalog", catalogMaster.getCode());
        return catalogPojo;
    }

    @Override
    public CatalogPojo getCatalog(CatalogVirtual catalogVirtual) {
        final CatalogPojo catalogPojo = mapper.map(catalogVirtual, CatalogPojo.class);
        logger.debug("Load {} catalog", catalogVirtual.getCode());
        return catalogPojo;
    }
    
    public CatalogCategoryPojo buildCatalogCategory(final CatalogCategoryMaster catalogCategory) {
        final CatalogCategoryPojo catalogCategoryPojo = mapper.map(catalogCategory, CatalogCategoryPojo.class);
        logger.debug("Load {} category", catalogCategory.getCode());
        return catalogCategoryPojo;
    }
    
    public CatalogCategoryPojo buildCatalogCategory(final CatalogCategoryVirtual catalogCategory) {
        final CatalogCategoryPojo catalogCategoryPojo = mapper.map(catalogCategory, CatalogCategoryPojo.class);
        logger.debug("Load {} category", catalogCategory.getCode());
        return catalogCategoryPojo;
    }
    
    public ProductMarketingPojo buildProductMarketing(final ProductMarketing productMarketing) {
        final ProductMarketingPojo productMarketingPojo = mapper.map(productMarketing, ProductMarketingPojo.class);
        logger.debug("Load {} product marketing", productMarketing.getCode());
        return productMarketingPojo;
    }
    
    
    public ProductSkuPojo buildProductSku(final ProductSku productSku) {
        final ProductSkuPojo productSkuPojo = mapper.map(productSku, ProductSkuPojo.class);
        logger.debug("Load {} sku", productSku.getCode());
        return productSkuPojo;
    }
    
}