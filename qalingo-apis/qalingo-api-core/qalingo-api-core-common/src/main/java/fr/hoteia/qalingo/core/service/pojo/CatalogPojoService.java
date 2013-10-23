package fr.hoteia.qalingo.core.service.pojo;

import java.util.List;

import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogPojo;

public interface CatalogPojoService {

    List<CatalogPojo> getAllCatalogMasters();

    CatalogPojo getCatalogById(String catalogId);

    CatalogPojo getCatalog(CatalogMaster catalogMaster);

    CatalogPojo getCatalog(CatalogVirtual catalogVirtual);

}