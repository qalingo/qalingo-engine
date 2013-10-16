package fr.hoteia.qalingo.core.ws.service.impl;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

import fr.hoteia.qalingo.core.pojo.catalog.CatalogMasterPojo;
import fr.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import fr.hoteia.qalingo.core.service.pojo.CatalogPojoService;
import fr.hoteia.qalingo.core.ws.service.CatalogWebService;
import fr.hoteia.qalingo.core.ws.service.CustomerWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("catalogWebService")
@WebService(endpointInterface="fr.hoteia.qalingo.core.ws.service.CatalogWebService")
public class CatalogWebServiceImpl implements CatalogWebService {

    @Autowired private CatalogPojoService catalogService;

    @Override
    public List<CatalogMasterPojo> getAllCatalogMasters() {
        return catalogService.getAllCatalogMasters();
    }

    @Override
    public CatalogMasterPojo getProductCatalogById(String productCatalogId) {
        return catalogService.getProductCatalogById(productCatalogId);
    }
}
