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

import java.util.List;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.market.MarketPojo;
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.pojo.MarketPojoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("marketPojoService")
@Transactional(readOnly = true)
public class MarketPojoFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Mapper dozerBeanMapper;
    
    @Autowired
    protected MarketService marketService;

    public MarketPlacePojo getMarketPlaceByCode(final String marketPlaceCode) {
        final MarketPlace marketPlace = marketService.getMarketPlaceByCode(marketPlaceCode);
        logger.debug("Found {} marketPlace:", marketPlace.getName());
        return dozerBeanMapper.map(marketPlace, MarketPlacePojo.class);
    }
    
    public List<MarketPlacePojo> getMarketPlaces() {
        final List<MarketPlace> allMarketPlaces = marketService.findMarketPlaces();
        logger.debug("Found {} MarketPlaces", allMarketPlaces.size());
        return PojoUtil.mapAll(dozerBeanMapper, allMarketPlaces, MarketPlacePojo.class);
    }
    
    public MarketPojo getMarketByCode(final String marketCode) {
        final Market market = marketService.getMarketByCode(marketCode);
        logger.debug("Found {} market:", market.getName());
        return dozerBeanMapper.map(market, MarketPojo.class);
    }
    
    public List<MarketPojo> getMarketsByMarketPlaceCode(final String marketPlaceCode) {
        final List<Market> markets = marketService.findMarkets();
        logger.debug("Found {} Markets", markets.size());
        return PojoUtil.mapAll(dozerBeanMapper, markets, MarketPojo.class);
    }
    
    public MarketAreaPojo getMarketAreaByCode(final String marketAreaCode) {
        final MarketArea marketArea = marketService.getMarketAreaByCode(marketAreaCode);
        logger.debug("Found {} marketArea:", marketArea.getName());
        return dozerBeanMapper.map(marketArea, MarketAreaPojo.class);
    }

}