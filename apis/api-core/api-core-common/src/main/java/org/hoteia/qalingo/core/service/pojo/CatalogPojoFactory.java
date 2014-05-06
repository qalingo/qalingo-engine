/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoFactory;

@Service("catalogPojoService")
@Transactional(readOnly = true)
public class CatalogPojoFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Mapper dozerBeanMapper;
    
    @Autowired
    protected CatalogService catalogService;

    public List<CatalogPojo> getAllCatalogMasters() {
        final List<CatalogMaster> allCatalogMasters = catalogService.findAllCatalogMasters();
        logger.debug("Found {} catalogs", allCatalogMasters.size());
        return PojoUtil.mapAll(dozerBeanMapper, allCatalogMasters, CatalogPojo.class);
    }

    public CatalogPojo getMasterCatalogById(final String catalogId) {
        final CatalogMaster catalog = catalogService.getMasterCatalogById(catalogId);
        logger.debug("Found catalog {} for id {}", catalog, catalogId);
        return dozerBeanMapper.map(catalog, CatalogPojo.class);
    }
    
    public CatalogPojo getVirtualCatalogById(final String catalogId) {
        final CatalogVirtual catalog = catalogService.getVirtualCatalogById(catalogId);
        logger.debug("Found catalog {} for id {}", catalog, catalogId);
        return dozerBeanMapper.map(catalog, CatalogPojo.class);
    }
    
    public CatalogPojo getMasterCatalog(final CatalogMaster catalogMaster) {
        final CatalogPojo catalogPojo = dozerBeanMapper.map(catalogMaster, CatalogPojo.class);
        logger.debug("Load {} catalog", catalogMaster.getCode());
        return catalogPojo;
    }

    public CatalogPojo getVirtualCatalog(final CatalogVirtual catalogVirtual) {
        final CatalogPojo catalogPojo = dozerBeanMapper.map(catalogVirtual, CatalogPojo.class);
        logger.debug("Load {} catalog", catalogVirtual.getCode());
        return catalogPojo;
    }
    
    public CatalogCategoryPojo buildCatalogCategory(final CatalogCategoryMaster catalogCategory) {
        final CatalogCategoryPojo catalogCategoryPojo = dozerBeanMapper.map(catalogCategory, CatalogCategoryPojo.class);
        logger.debug("Load {} category", catalogCategory.getCode());
        return catalogCategoryPojo;
    }
    
    public CatalogCategoryPojo buildCatalogCategory(final CatalogCategoryVirtual catalogCategory) {
        final CatalogCategoryPojo catalogCategoryPojo = dozerBeanMapper.map(catalogCategory, CatalogCategoryPojo.class);
        logger.debug("Load {} category", catalogCategory.getCode());
        return catalogCategoryPojo;
    }
    
    public ProductMarketingPojo buildProductMarketing(final ProductMarketing productMarketing) {
        final ProductMarketingPojo productMarketingPojo = dozerBeanMapper.map(productMarketing, ProductMarketingPojo.class);
        logger.debug("Load {} product marketing", productMarketing.getCode());
        return productMarketingPojo;
    }
    
    public ProductSkuPojo buildProductSku(final ProductSku productSku) {
        final ProductSkuPojo productSkuPojo = dozerBeanMapper.map(productSku, ProductSkuPojo.class);
        logger.debug("Load {} sku", productSku.getCode());
        return productSkuPojo;
    }
    
}