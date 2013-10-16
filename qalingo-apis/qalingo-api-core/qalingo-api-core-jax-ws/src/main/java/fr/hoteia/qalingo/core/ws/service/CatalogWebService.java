package fr.hoteia.qalingo.core.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

import fr.hoteia.qalingo.core.pojo.catalog.CatalogMasterPojo;
import org.springframework.stereotype.Service;

@WebService(name = "catalogWsClient", targetNamespace = "http://www.qalingo.com")
public interface CatalogWebService {

    @WebMethod(operationName = "getAllCatalogMasters")
    List<CatalogMasterPojo> getAllCatalogMasters();

    @WebMethod(operationName = "getProductCatalogById")
    CatalogMasterPojo getProductCatalogById(@WebParam(name = "id") String productCatalogId);
}
