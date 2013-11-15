package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

public interface RetailerPojoService {

    List<RetailerPojo> findAllRetailers();

    RetailerPojo getRetailerById(String retailerId);
    
}