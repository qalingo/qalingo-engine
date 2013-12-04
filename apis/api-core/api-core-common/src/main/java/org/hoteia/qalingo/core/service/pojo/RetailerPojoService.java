package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

public interface RetailerPojoService {

    RetailerPojo getRetailerById(String retailerId);

    RetailerPojo getRetailerByCode(String retailerCode);

    List<RetailerPojo> findAllRetailers();
    
    List<RetailerPojo> findRetailersByMarketAreaCode(String marketAreaCode);

}