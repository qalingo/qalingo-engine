package org.hoteia.qalingo.core.ws.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;

@WebService(name = "catalogWsClient", targetNamespace = "http://www.qalingo.com")
public interface CatalogWebService {

    @WebMethod(operationName = "getAllCatalogMasters")
    List<CatalogPojo> getAllCatalogMasters();

    @WebMethod(operationName = "getProductCatalogById")
    CatalogPojo getProductCatalogById(@WebParam(name = "id") String productCatalogId);
}
