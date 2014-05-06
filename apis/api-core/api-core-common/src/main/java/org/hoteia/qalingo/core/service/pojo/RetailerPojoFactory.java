/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo;

import static org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

import java.util.List;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("retailerPojoService")
@Transactional(readOnly = true)
public class RetailerPojoFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired 
    private Mapper dozerBeanMapper;
    
    @Autowired 
    private RetailerService retailerService;

    public RetailerPojo getRetailerById(final String retailerId) {
        final Retailer retailer = retailerService.getRetailerById(retailerId);
        logger.debug("Found {} retailer for id {}", retailer, retailerId);
        return retailer == null ? null : dozerBeanMapper.map(retailer, RetailerPojo.class);
    }
    
    public RetailerPojo getRetailerByCode(final String retailerCode) {
        final Retailer retailer = retailerService.getRetailerByCode(retailerCode);
        logger.debug("Found {} retailer for code {}", retailer, retailerCode);
        return retailer == null ? null : dozerBeanMapper.map(retailer, RetailerPojo.class);
    }
    
    public List<RetailerPojo> findAllRetailers() {
        List<Retailer> allRetailers = retailerService.findAllRetailers();
        logger.debug("Found {} retailers", allRetailers.size());
        return mapAll(dozerBeanMapper, allRetailers, RetailerPojo.class);
    }
    
    public List<RetailerPojo> findRetailersByMarketAreaCode(final String marketAreaCode) {
        List<Retailer> retailersByMarketAreaCode = retailerService.findRetailersByMarketAreaCode(marketAreaCode);
        logger.debug("Found {} retailers", retailersByMarketAreaCode.size());
        return mapAll(dozerBeanMapper, retailersByMarketAreaCode, RetailerPojo.class);
    }

}