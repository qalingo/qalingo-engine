/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.MarketDao;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.service.MarketService;

@Service("marketService")
@Transactional
public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketDao marketDao;

    // MARKET PLACE

    public MarketPlace getDefaultMarketPlace(Object... params) {
        return marketDao.getDefaultMarketPlace(params);
    }

    public MarketPlace getMarketPlaceById(final Long marketPlaceId, Object... params) {
        return marketDao.getMarketPlaceById(marketPlaceId, params);
    }

    public MarketPlace getMarketPlaceById(final String rawMarketPlaceId, Object... params) {
        long marketPlaceId = -1;
        try {
            marketPlaceId = Long.parseLong(rawMarketPlaceId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getMarketPlaceById(marketPlaceId, params);
    }

    public MarketPlace getMarketPlaceByCode(final String marketPlaceCode, Object... params) {
        return marketDao.getMarketPlaceByCode(marketPlaceCode, params);
    }

    public List<MarketPlace> findMarketPlaces(Object... params) {
        return marketDao.findMarketPlaces(params);
    }

    public void saveOrUpdateMarketPlace(final MarketPlace marketPlace) {
        marketDao.saveOrUpdateMarketPlace(marketPlace);
    }

    public void deleteMarketPlace(final MarketPlace marketPlace) {
        marketDao.deleteMarketPlace(marketPlace);
    }

    // MARKET

    public Market getDefaultMarket(Object... params) {
        return marketDao.getDefaultMarket(params);
    }

    public Market getMarketById(final Long marketId, Object... params) {
        return marketDao.getMarketById(marketId, params);
    }

    public Market getMarketById(final String rawMarketId, Object... params) {
        long marketId = -1;
        try {
            marketId = Long.parseLong(rawMarketId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getMarketById(marketId, params);
    }

    public Market getMarketByCode(final String marketCode, Object... params) {
        return marketDao.getMarketByCode(marketCode, params);
    }

    public List<Market> findMarkets(Object... params) {
        return marketDao.findMarkets(params);
    }

    public List<Market> getMarketsByMarketPlaceCode(final String marketPlaceCode, Object... params) {
        return marketDao.getMarketsByMarketPlaceCode(marketPlaceCode, params);
    }

    public void saveOrUpdateMarket(Market market) {
        marketDao.saveOrUpdateMarket(market);
    }

    public void deleteMarket(Market market) {
        marketDao.deleteMarket(market);
    }

    // MARKET AREA

    public MarketArea getMarketAreaById(final Long marketAreaId, Object... params) {
        return marketDao.getMarketAreaById(marketAreaId, params);
    }

    public MarketArea getMarketAreaById(final String rawMarketAreaId, Object... params) {
        long marketAreaId = -1;
        try {
            marketAreaId = Long.parseLong(rawMarketAreaId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getMarketAreaById(marketAreaId, params);
    }

    public MarketArea getMarketAreaByCode(final String marketAreaCode, Object... params) {
        return marketDao.getMarketAreaByCode(marketAreaCode, params);
    }
    
    public List<MarketArea> getMarketAreaByGeolocCountryCode(final String countryCode, Object... params) {
        return marketDao.getMarketAreaByGeolocCountryCode(countryCode, params);
    }
    
}