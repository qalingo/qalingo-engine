package fr.hoteia.qalingo.core.service.pojo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.pojo.BoCatalogCategoryPojo;
import fr.hoteia.qalingo.core.pojo.BoProductMarketingPojo;
import fr.hoteia.qalingo.core.pojo.BoProductSkuPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import fr.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import fr.hoteia.qalingo.core.pojo.product.ProductSkuPojo;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;

public class BoCatalogPojoServiceImpl extends CatalogPojoServiceImpl {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    protected BackofficeUrlService backofficeUrlService;
    
    @Override
    public CatalogCategoryPojo buildJsonCatalogCategory(final CatalogCategoryMaster catalogCategory) {
        final BoCatalogCategoryPojo catalogCategoryPojo = new BoCatalogCategoryPojo();
        BeanUtils.copyProperties(super.buildJsonCatalogCategory(catalogCategory), catalogCategoryPojo);
        
        try {
            catalogCategoryPojo.setAddChildCategoryUrl(backofficeUrlService.buildAddVirtualProductCategoryUrl(catalogCategory.getCode()));
            catalogCategoryPojo.setDetailsUrl(backofficeUrlService.buildProductVirtualCategoryDetailsUrl(catalogCategory.getCode()));
            catalogCategoryPojo.setEditUrl(backofficeUrlService.buildVirtualProductCategoryEditUrl(catalogCategory.getCode()));
        } catch (Exception e) {
            LOG.error("Failed to build URLs in JSON!", e);
        }
        
        return catalogCategoryPojo;
    }
    
    @Override
    public CatalogCategoryPojo buildJsonCatalogCategory(final CatalogCategoryVirtual catalogCategory) {
        final BoCatalogCategoryPojo catalogCategoryPojo = new BoCatalogCategoryPojo();
        BeanUtils.copyProperties(super.buildJsonCatalogCategory(catalogCategory), catalogCategoryPojo);

        try {
            catalogCategoryPojo.setAddChildCategoryUrl(backofficeUrlService.buildAddVirtualProductCategoryUrl(catalogCategory.getCode()));
            catalogCategoryPojo.setDetailsUrl(backofficeUrlService.buildProductVirtualCategoryDetailsUrl(catalogCategory.getCode()));
            catalogCategoryPojo.setEditUrl(backofficeUrlService.buildVirtualProductCategoryEditUrl(catalogCategory.getCode()));
        } catch (Exception e) {
            LOG.error("Failed to build URLs in JSON!", e);
        }
        
        return catalogCategoryPojo;
    }
    
    @Override
    public ProductMarketingPojo buildJsonProductMarketing(final ProductMarketing productMarketing) {
        final BoProductMarketingPojo productMarketingPojo = new BoProductMarketingPojo();
        BeanUtils.copyProperties(super.buildJsonProductMarketing(productMarketing), productMarketingPojo);

        try {
            productMarketingPojo.setDetailsUrl(backofficeUrlService.buildProductMarketingDetailsUrl(productMarketing.getCode()));
            productMarketingPojo.setEditUrl(backofficeUrlService.buildProductMarketingEditUrl(productMarketing.getCode()));
        } catch (Exception e) {
            LOG.error("Failed to build URLs in JSON!", e);
        }
        
        return productMarketingPojo;
    }
    
    @Override
    public ProductSkuPojo buildJsonProductSku(final ProductSku productSku) {
        final BoProductSkuPojo productSkuPojo = new BoProductSkuPojo();
        BeanUtils.copyProperties(super.buildJsonProductSku(productSku), productSkuPojo);

        try {
            productSkuPojo.setDetailsUrl(backofficeUrlService.buildProductSkuDetailsUrl(productSku.getCode()));
            productSkuPojo.setEditUrl(backofficeUrlService.buildProductSkuEditUrl(productSku.getCode()));
        } catch (Exception e) {
            LOG.error("Failed to build URLs in JSON!", e);
        }

        return productSkuPojo;
    }
    
    public void setBackofficeUrlService(BackofficeUrlService backofficeUrlService) {
        this.backofficeUrlService = backofficeUrlService;
    }
    
}