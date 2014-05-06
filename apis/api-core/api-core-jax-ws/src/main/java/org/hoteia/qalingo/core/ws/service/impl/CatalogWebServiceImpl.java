/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.ws.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoFactory;
import org.hoteia.qalingo.core.ws.service.CatalogWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("catalogWebService")
@WebService(endpointInterface="org.hoteia.qalingo.core.ws.service.CatalogWebService")
public class CatalogWebServiceImpl implements CatalogWebService {

    @Autowired private CatalogPojoFactory catalogService;

    @Override
    public List<CatalogPojo> getAllCatalogMasters() {
        return catalogService.getAllCatalogMasters();
    }

    @Override
    public CatalogPojo getProductCatalogById(String productCatalogId) {
        return catalogService.getMasterCatalogById(productCatalogId);
    }

}
