package fr.hoteia.qalingo.core.service.pojo.impl;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
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

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DozerBeanMapper mapper;
    
    @Autowired
    protected CatalogService catalogService;

    @Override
    public List<CatalogPojo> getAllCatalogMasters() {
        List<CatalogMaster> allCatalogMasters = catalogService.findAllCatalogMasters();
        LOG.debug("Found {} catalogs", allCatalogMasters.size());
        return mapAll(mapper, allCatalogMasters, CatalogPojo.class);
    }

    @Override
    public CatalogPojo getCatalogById(String catalogId) {
        CatalogMaster catalog = catalogService.getProductCatalogById(catalogId);
        LOG.debug("Found catalog {} for id {}", catalog, catalogId);
        return mapper.map(catalog, CatalogPojo.class);
    }
    
    public CatalogPojo getCatalog(CatalogMaster catalogMaster) {
        return buildJsonCatalog(catalogMaster);
    }

    public CatalogPojo getCatalog(CatalogVirtual catalogVirutal) {
        return buildJsonCatalog(catalogVirutal);
    }
    
    
    
    
    
    
    
    public CatalogPojo buildJsonCatalog(final CatalogMaster catalogMaster) {
        final CatalogPojo catalogMasterPojo = new CatalogPojo();
        catalogMasterPojo.setId(catalogMaster.getId());
        catalogMasterPojo.setVersion(catalogMaster.getVersion());
        catalogMasterPojo.setCode(catalogMaster.getCode());
        catalogMasterPojo.setBusinessName(catalogMaster.getBusinessName());
        catalogMasterPojo.setDescription(catalogMaster.getDescription());
        catalogMasterPojo.setDefault(catalogMaster.isDefault());
        catalogMasterPojo.setMaster(true);
        catalogMasterPojo.setDateCreate(catalogMaster.getDateCreate());
        catalogMasterPojo.setDateUpdate(catalogMaster.getDateUpdate());

        final Set<CatalogCategoryMaster> catalogCategories = catalogMaster.getCatalogCategories();
        if (catalogCategories != null) {
            for (Iterator<CatalogCategoryMaster> iterator = catalogCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryMaster catalogCategory = iterator.next();
                CatalogCategoryPojo catalogCategoryPojo = buildJsonCatalogCategory(catalogCategory);
                catalogMasterPojo.getCatalogCategories().add(catalogCategoryPojo);
            }
        }
        return catalogMasterPojo;
    }
    
    public CatalogCategoryPojo buildJsonCatalogCategory(final CatalogCategoryMaster catalogCategory) {
        final CatalogCategoryPojo catalogCategoryPojo = new CatalogCategoryPojo();

        catalogCategoryPojo.setId(catalogCategory.getId());
        catalogCategoryPojo.setVersion(catalogCategory.getVersion());
        catalogCategoryPojo.setBusinessName(catalogCategory.getBusinessName());
        catalogCategoryPojo.setDescription(catalogCategory.getDescription());
        catalogCategoryPojo.setCode(catalogCategory.getCode());
        catalogCategoryPojo.setDefault(catalogCategory.isDefault());
        catalogCategoryPojo.setRoot(catalogCategory.isRoot());

        // catalogCategoryPojo.setProductCategoryMasterAttribute(catalogCategoryMasterAttribute);
        // catalogCategoryPojo.setProductCategoryMarketAreaAttributes(catalogCategoryMarketAreaAttributes);

        Set<CatalogCategoryMaster> catalogCategories = catalogCategory.getCatalogCategories();
        catalogCategoryPojo.setCatalogCategories(buildJsonMasterCatalogCategories(catalogCategories));

        List<ProductMarketing> productMarketings = new ArrayList<ProductMarketing>(catalogCategory.getProductMarketings());
        catalogCategoryPojo.setProductMarketings(buildJsonProductMarketings(productMarketings));

        // catalogCategoryPojo.setProductMarketings(productMarketings);

        // catalogCategoryPojo.setImages(images);

        catalogCategoryPojo.setDateCreate(catalogCategory.getDateCreate());
        catalogCategoryPojo.setDateUpdate(catalogCategory.getDateUpdate());

        return catalogCategoryPojo;
    }
    
    public List<CatalogCategoryPojo> buildJsonMasterCatalogCategories(final Set<CatalogCategoryMaster> catalogCategories) {
        final List<CatalogCategoryPojo> catalogCategoryPojos = new ArrayList<CatalogCategoryPojo>();
        if (catalogCategories != null) {
            for (Iterator<CatalogCategoryMaster> iterator = catalogCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryMaster catalogCategory = iterator.next();
                CatalogCategoryPojo catalogCategoryMasterPojo = buildJsonCatalogCategory(catalogCategory);
                catalogCategoryPojos.add(catalogCategoryMasterPojo);
            }
        }
        return catalogCategoryPojos;
    }
    
    
    
    
    public CatalogPojo buildJsonCatalog(final CatalogVirtual catalogVirtual) {
        final CatalogPojo catalogVirtualPojo = new CatalogPojo();
        catalogVirtualPojo.setId(catalogVirtual.getId());
        catalogVirtualPojo.setVersion(catalogVirtual.getVersion());
        catalogVirtualPojo.setBusinessName(catalogVirtual.getBusinessName());
        catalogVirtualPojo.setDescription(catalogVirtual.getDescription());
        catalogVirtualPojo.setDefault(catalogVirtual.isDefault());
        catalogVirtualPojo.setCode(catalogVirtual.getCode());
        catalogVirtualPojo.setDateCreate(catalogVirtual.getDateCreate());
        catalogVirtualPojo.setDateUpdate(catalogVirtual.getDateUpdate());

        final Set<CatalogCategoryVirtual> catalogCategories = catalogVirtual.getCatalogCategories();
        if (catalogCategories != null) {
            for (Iterator<CatalogCategoryVirtual> iterator = catalogCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtual catalogCategory = iterator.next();
                CatalogCategoryPojo catalogCategoryPojo = buildJsonCatalogCategory(catalogCategory);
                catalogVirtualPojo.getCatalogCategories().add(catalogCategoryPojo);
            }
        }
        return catalogVirtualPojo;
    }
    
    public CatalogCategoryPojo buildJsonCatalogCategory(final CatalogCategoryVirtual catalogCategory) {
        final CatalogCategoryPojo catalogCategoryPojo = new CatalogCategoryPojo();

        catalogCategoryPojo.setId(catalogCategory.getId());
        catalogCategoryPojo.setVersion(catalogCategory.getVersion());
        catalogCategoryPojo.setBusinessName(catalogCategory.getBusinessName());
        catalogCategoryPojo.setDescription(catalogCategory.getDescription());
        catalogCategoryPojo.setCode(catalogCategory.getCode());
        catalogCategoryPojo.setDefault(catalogCategory.isDefault());
        catalogCategoryPojo.setRoot(catalogCategory.isRoot());

        // catalogCategoryPojo.setProductCategoryVirtualAttribute(catalogCategoryVirtualAttribute);
        // catalogCategoryPojo.setProductCategoryMarketAreaAttributes(catalogCategoryMarketAreaAttributes);

        Set<CatalogCategoryVirtual> catalogCategories = catalogCategory.getCatalogCategories();
        catalogCategoryPojo.setCatalogCategories(buildJsonVirtualCatalogCategories(catalogCategories));

        List<ProductMarketing> productMarketings = new ArrayList<ProductMarketing>(catalogCategory.getProductMarketings());
        catalogCategoryPojo.setProductMarketings(buildJsonProductMarketings(productMarketings));

        // catalogCategoryPojo.setProductMarketings(productMarketings);

        // catalogCategoryPojo.setImages(images);

        catalogCategoryPojo.setDateCreate(catalogCategory.getDateCreate());
        catalogCategoryPojo.setDateUpdate(catalogCategory.getDateUpdate());

        return catalogCategoryPojo;
    }
    
    public List<CatalogCategoryPojo> buildJsonVirtualCatalogCategories(final Set<CatalogCategoryVirtual> catalogCategories) {
        final List<CatalogCategoryPojo> catalogCategoryPojos = new ArrayList<CatalogCategoryPojo>();
        if (catalogCategories != null) {
            for (Iterator<CatalogCategoryVirtual> iterator = catalogCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtual catalogCategory = iterator.next();
                CatalogCategoryPojo catalogCategoryVirtualPojo = buildJsonCatalogCategory(catalogCategory);
                catalogCategoryPojos.add(catalogCategoryVirtualPojo);
            }
        }
        return catalogCategoryPojos;
    }
    
    
    
    
    
    
    public List<ProductMarketingPojo> buildJsonProductMarketings(final List<ProductMarketing> productMarketings) {
        final List<ProductMarketingPojo> productMarketingJsonPojos = new ArrayList<ProductMarketingPojo>();
        if (productMarketings != null) {
            for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
                ProductMarketing productMarketing = iterator.next();
                ProductMarketingPojo productMarketingJsonPojo = buildJsonProductMarketing(productMarketing);
                productMarketingJsonPojos.add(productMarketingJsonPojo);
            }
        }
        return productMarketingJsonPojos;
    }
    
    public ProductMarketingPojo buildJsonProductMarketing(final ProductMarketing productMarketing) {
        final ProductMarketingPojo productMarketingJsonPojo = new ProductMarketingPojo();

        productMarketingJsonPojo.setId(productMarketing.getId());
        productMarketingJsonPojo.setVersion(productMarketing.getVersion());
        productMarketingJsonPojo.setBusinessName(productMarketing.getBusinessName());
        productMarketingJsonPojo.setDescription(productMarketing.getDescription());
        productMarketingJsonPojo.setDefault(productMarketing.isDefault());
        productMarketingJsonPojo.setCode(productMarketing.getCode());

        // productMarketingJsonPojo.setProductBrand(productBrand);
        // productMarketingJsonPojo.setProductMarketingGlobalAttributes(productMarketingGlobalAttributes);
        // productMarketingJsonPojo.setProductMarketingMarketAreaAttributes(productMarketingMarketAreaAttributes);

        List<ProductSku> productSkus = new ArrayList<ProductSku>(productMarketing.getProductSkus());
        productMarketingJsonPojo.setProductSkus(buildJsonProductSkus(productSkus));

        // productMarketingJsonPojo.setProductCrossLinks(productCrossLinks);
        // productMarketingJsonPojo.setImages(images);

        productMarketingJsonPojo.setDateCreate(productMarketing.getDateCreate());
        productMarketingJsonPojo.setDateUpdate(productMarketing.getDateUpdate());

        return productMarketingJsonPojo;
    }
    
    public List<ProductSkuPojo> buildJsonProductSkus(final List<ProductSku> productSkus) {
        final List<ProductSkuPojo> productSkuPojos = new ArrayList<ProductSkuPojo>();
        if (productSkus != null) {
            for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
                ProductSku productSku = iterator.next();
                ProductSkuPojo productSkuPojo = buildJsonProductSku(productSku);
                productSkuPojos.add(productSkuPojo);
            }
        }
        return productSkuPojos;
    }
    
    public ProductSkuPojo buildJsonProductSku(final ProductSku productSku) {
        final ProductSkuPojo productSkuPojo = new ProductSkuPojo();

        productSkuPojo.setId(productSku.getId());
        productSkuPojo.setVersion(productSku.getVersion());
        productSkuPojo.setBusinessName(productSku.getBusinessName());
        productSkuPojo.setDescription(productSku.getDescription());
        productSkuPojo.setDefault(productSku.isDefault());
        productSkuPojo.setCode(productSku.getCode());

        // productSkuPojo.setProductSkuGlobalAttributes(productSkuGlobalAttributes);
        // productSkuPojo.setProductSkuMarketAreaAttributes(productSkuMarketAreaAttributes);
        // productSkuPojo.setProductMarketing(productMarketing);
        // productSkuPojo.setImages(images);
        // productSkuPojo.setPrices(prices);
        // productSkuPojo.setStocks(stocks);
        // productSkuPojo.setRetailers(retailers);

        productSkuPojo.setDateCreate(productSku.getDateCreate());
        productSkuPojo.setDateUpdate(productSku.getDateUpdate());

        return productSkuPojo;
    }
    
}