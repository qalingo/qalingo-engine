/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

import fr.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import fr.hoteia.qalingo.core.pojo.store.StorePojo;

@WebService(name="retailerWsClient", targetNamespace="http://www.qalingo.com")
public interface RetailerWebService {

    @WebMethod(operationName="findAllRetailers")
    List<RetailerPojo> findAllRetailers();

    @WebMethod(operationName="getRetailerById")
    RetailerPojo getRetailerById(String retailerId);

}
