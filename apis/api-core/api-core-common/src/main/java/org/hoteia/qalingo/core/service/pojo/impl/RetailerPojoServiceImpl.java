package org.hoteia.qalingo.core.service.pojo.impl;

import static org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoService;

@Service("retailerPojoService")
@Transactional(readOnly = true)
public class RetailerPojoServiceImpl implements RetailerPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired 
    private Mapper dozerBeanMapper;
    
    @Autowired 
    private RetailerService retailerService;

    @Override
    public List<RetailerPojo> findAllRetailers() {
        List<Retailer> allRetailers = retailerService.findAllRetailers();
        logger.debug("Found {} retailers", allRetailers.size());
        return mapAll(dozerBeanMapper, allRetailers, RetailerPojo.class);
    }

    @Override
    public RetailerPojo getRetailerById(String retailerId) {
        final Retailer retailer = retailerService.getRetailerById(retailerId);
        logger.debug("Found {} retailer for id {}", retailer, retailerId);
        return retailer == null ? null : dozerBeanMapper.map(retailer, RetailerPojo.class);
    }
    
}