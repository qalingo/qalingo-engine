package org.hoteia.qalingo.core.ws.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoService;
import org.hoteia.qalingo.core.ws.service.CatalogWebService;

@Service("catalogWebService")
@WebService(endpointInterface="org.hoteia.qalingo.core.ws.service.CatalogWebService")
public class CatalogWebServiceImpl implements CatalogWebService {

    @Autowired private CatalogPojoService catalogService;

    @Override
    public List<CatalogPojo> getAllCatalogMasters() {
        return catalogService.getAllCatalogMasters();
    }

    @Override
    public CatalogPojo getProductCatalogById(String productCatalogId) {
        return catalogService.getCatalogById(productCatalogId);
    }
}
