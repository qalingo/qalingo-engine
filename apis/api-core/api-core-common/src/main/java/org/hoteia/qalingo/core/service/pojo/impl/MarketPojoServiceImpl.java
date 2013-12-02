package org.hoteia.qalingo.core.service.pojo.impl;

import java.util.List;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.pojo.MarketPojoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("marketPojoService")
@Transactional(readOnly = true)
public class MarketPojoServiceImpl implements MarketPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Mapper dozerBeanMapper;
    
    @Autowired
    protected MarketService marketService;

    @Override
    public List<MarketPlacePojo> getMarketPlaces() {
        final List<MarketPlace> allMarketPlaces = marketService.findMarketPlaces();
        logger.debug("Found {} MarketPlaces", allMarketPlaces.size());
        return PojoUtil.mapAll(dozerBeanMapper, allMarketPlaces, MarketPlacePojo.class);
    }

}