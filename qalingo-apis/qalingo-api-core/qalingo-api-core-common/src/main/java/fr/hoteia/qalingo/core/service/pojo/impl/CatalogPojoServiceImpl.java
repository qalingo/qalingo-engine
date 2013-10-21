package fr.hoteia.qalingo.core.service.pojo.impl;

import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogMasterPojo;
import fr.hoteia.qalingo.core.service.pojo.CatalogPojoService;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import fr.hoteia.qalingo.core.service.CatalogService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

@Service("catalogPojoService")
@Transactional(readOnly = true)
public class CatalogPojoServiceImpl implements CatalogPojoService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired private DozerBeanMapper mapper;
    @Autowired private CatalogService catalogService;

    @Override
    public List<CatalogMasterPojo> getAllCatalogMasters() {
        List<CatalogMaster> allCatalogMasters = catalogService.findAllCatalogMasters();
        LOG.debug("Found {} catalogs", allCatalogMasters.size());
        return mapAll(mapper, allCatalogMasters, CatalogMasterPojo.class);
    }

    @Override
    public CatalogMasterPojo getProductCatalogById(String productCatalogId) {
        CatalogMaster catalog = catalogService.getProductCatalogById(productCatalogId);
        LOG.debug("Found catalog {} for id {}", catalog, productCatalogId);
        return mapper.map(catalog, CatalogMasterPojo.class);
    }
}
