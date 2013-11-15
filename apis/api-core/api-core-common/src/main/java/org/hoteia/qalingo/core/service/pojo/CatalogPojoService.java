package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;

public interface CatalogPojoService {

    List<CatalogPojo> getAllCatalogMasters();

    CatalogPojo getCatalogById(String catalogId);

    CatalogPojo getCatalog(CatalogMaster catalogMaster);

    CatalogPojo getCatalog(CatalogVirtual catalogVirtual);

}