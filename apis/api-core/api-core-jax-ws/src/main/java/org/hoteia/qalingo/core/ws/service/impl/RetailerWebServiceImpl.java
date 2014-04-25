package org.hoteia.qalingo.core.ws.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoFactory;
import org.hoteia.qalingo.core.ws.service.RetailerWebService;

@Service("retailerWebService")
@WebService(endpointInterface="org.hoteia.qalingo.core.ws.service.RetailerWebService")
public class RetailerWebServiceImpl implements RetailerWebService {

    @Autowired private RetailerPojoFactory retailerService;

    @Override
    public List<RetailerPojo> findAllRetailers() {
        return retailerService.findAllRetailers();
    }

    @Override
    public RetailerPojo getRetailerById(String retailerId) {
        return retailerService.getRetailerById(retailerId);
    }
}
