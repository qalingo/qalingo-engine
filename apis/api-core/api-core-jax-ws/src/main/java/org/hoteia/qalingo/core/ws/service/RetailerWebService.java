/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.ws.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

@WebService(name="retailerWsClient", targetNamespace="http://www.qalingo.com")
public interface RetailerWebService {

    @WebMethod(operationName="findAllRetailers")
    List<RetailerPojo> findAllRetailers();

    @WebMethod(operationName="getRetailerById")
    RetailerPojo getRetailerById(String retailerId);

}
