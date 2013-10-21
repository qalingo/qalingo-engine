package fr.hoteia.qalingo.core.service.pojo;

import fr.hoteia.qalingo.core.pojo.catalog.CatalogMasterPojo;

import java.util.List;

public interface CatalogPojoService {

    List<CatalogMasterPojo> getAllCatalogMasters();

    CatalogMasterPojo getProductCatalogById(String productCatalogId);

}
