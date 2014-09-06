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
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("catalogPojoService")
@Transactional(readOnly = true)
public class CatalogPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Mapper dozerBeanMapper;
    
    @Autowired
    protected CatalogService catalogService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    @Autowired
    protected ProductService productService;
    
    public List<CatalogPojo> getAllCatalogMasters() {
        final List<CatalogMaster> allCatalogMasters = catalogService.findAllCatalogMasters();
        logger.debug("Found {} catalogs", allCatalogMasters.size());
        return PojoUtil.mapAll(dozerBeanMapper, allCatalogMasters, CatalogPojo.class);
    }

    public CatalogPojo getMasterCatalogByCode(final String catalogCode) {
        final CatalogMaster catalog = catalogService.getMasterCatalogById(catalogCode);
        logger.debug("Found catalog {} for catalogCode {}", catalog, catalogCode);
        return buildMasterCatalog(catalog);
    }
    
    public CatalogPojo getVirtualCatalogByCode(final String catalogCode) {
        final CatalogVirtual catalog = catalogService.getVirtualCatalogByCode(catalogCode);
        logger.debug("Found catalog {} for catalogCode {}", catalog, catalogCode);
        return buildVirtualCatalog(catalog);
    }
    
    public CatalogPojo buildMasterCatalog(final CatalogMaster catalogMaster) {
        final CatalogPojo catalogPojo = dozerBeanMapper.map(catalogMaster, CatalogPojo.class);
        logger.debug("Load {} catalog", catalogMaster.getCode());
        return catalogPojo;
    }

    public CatalogPojo buildVirtualCatalog(final CatalogVirtual catalogVirtual) {
        final CatalogPojo catalogPojo = dozerBeanMapper.map(catalogVirtual, CatalogPojo.class);
        logger.debug("Load {} catalog", catalogVirtual.getCode());
        return catalogPojo;
    }

    public List<CatalogCategoryPojo> getAllVirtualCategories(final String catalogVirtualCode) {
        final List<CatalogCategoryVirtual> allCategories = catalogCategoryService.findAllVirtualCatalogCategoriesByCatalogCode(catalogVirtualCode);
        logger.debug("Found {} categories", allCategories.size());
        return PojoUtil.mapAll(dozerBeanMapper, allCategories, CatalogCategoryPojo.class);
    }

    public CatalogCategoryPojo buildCatalogCategory(final CatalogCategoryMaster catalogCategory) {
        final CatalogCategoryPojo catalogCategoryPojo = dozerBeanMapper.map(catalogCategory, CatalogCategoryPojo.class);
        logger.debug("Load {} category", catalogCategory.getCode());
        return catalogCategoryPojo;
    }
    
    public CatalogCategoryPojo getCatalogCategoryByCode(final String catalogVirtualCode, final String categoryCode) {
        final CatalogCategoryVirtual catalogCategoryVirtual = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, catalogVirtualCode, categoryCode);
        logger.debug("Found catalogCategoryVirtual {} for catalogCode {}", catalogCategoryVirtual, categoryCode);
        return buildCatalogCategory(catalogCategoryVirtual);
    }
    
    public CatalogCategoryPojo buildCatalogCategory(final CatalogCategoryVirtual catalogCategory) {
        final CatalogCategoryPojo catalogCategoryPojo = dozerBeanMapper.map(catalogCategory, CatalogCategoryPojo.class);
        logger.debug("Load {} category", catalogCategory.getCode());
        return catalogCategoryPojo;
    }
    
    public List<ProductMarketingPojo> getProductMarketingsByCategoryCode(final String catalogCode, final String categoryCode) {
        final List<ProductMarketing> productMarketings = productService.findProductMarketingsByVirtualCatalogCategoryCode(catalogCode, categoryCode);
        logger.debug("Found {} productMarketings", productMarketings.size());
        return PojoUtil.mapAll(dozerBeanMapper, productMarketings, ProductMarketingPojo.class);
    }
    
    public ProductMarketingPojo getProductMarketing(final String productMarketingCode) {
        final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode);
        return buildProductMarketing(productMarketing);
    }
    
    public ProductMarketingPojo buildProductMarketing(final ProductMarketing productMarketing) {
        final ProductMarketingPojo productMarketingPojo = dozerBeanMapper.map(productMarketing, ProductMarketingPojo.class);
        logger.debug("Load {} product marketing", productMarketing.getCode());
        return productMarketingPojo;
    }
    
    public List<ProductSkuPojo> getProductSkusByProductMarketingCode(final String productMarketingCode) {
        final List<ProductSku> productSkus = productService.findProductSkusByProductMarketingCode(productMarketingCode);
        logger.debug("Found {} productSkus", productSkus.size());
        return PojoUtil.mapAll(dozerBeanMapper, productSkus, ProductSkuPojo.class);
    }
    
    public ProductSkuPojo getProductSku(final String productSkuCode) {
        final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
        return buildProductSku(productSku);
    }
    
    public ProductSkuPojo buildProductSku(final ProductSku productSku) {
        final ProductSkuPojo productSkuPojo = dozerBeanMapper.map(productSku, ProductSkuPojo.class);
        logger.debug("Load {} sku", productSku.getCode());
        return productSkuPojo;
    }
    
}