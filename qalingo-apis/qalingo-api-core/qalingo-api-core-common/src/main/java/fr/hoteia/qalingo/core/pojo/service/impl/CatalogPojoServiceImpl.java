package fr.hoteia.qalingo.core.pojo.service.impl;

import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogMasterPojo;
import fr.hoteia.qalingo.core.pojo.service.CatalogPojoService;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import fr.hoteia.qalingo.core.service.CatalogService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("catalogPojoService")
@Transactional(readOnly = true)
public class CatalogPojoServiceImpl implements CatalogPojoService {

    @Autowired private DozerBeanMapper mapper;
    @Autowired private CatalogService catalogService;

    @Override
    public List<CatalogMasterPojo> getAllCatalogMasters() {
        List<CatalogMaster> allCatalogMasters = catalogService.findAllCatalogMasters();
        return PojoUtil.mapAll(mapper, allCatalogMasters, CatalogMasterPojo.class);
    }

    @Override
    public CatalogMasterPojo getProductCatalogById(String productCatalogId) {
        CatalogMaster productCatalogById = catalogService.getProductCatalogById(productCatalogId);
        return mapper.map(productCatalogById, CatalogMasterPojo.class);
    }
}
