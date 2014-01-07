/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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

    public MarketPlace getDefaultMarketPlace() {
        return marketDao.getDefaultMarketPlace();
    }

    public MarketPlace getMarketPlaceById(final Long marketPlaceId) {
        return marketDao.getMarketPlaceById(marketPlaceId);
    }

    public MarketPlace getMarketPlaceById(final String rawMarketPlaceId) {
        long marketPlaceId = -1;
        try {
            marketPlaceId = Long.parseLong(rawMarketPlaceId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getMarketPlaceById(marketPlaceId);
    }

    public MarketPlace getMarketPlaceByCode(final String marketPlaceCode) {
        return marketDao.getMarketPlaceByCode(marketPlaceCode);
    }

    public List<MarketPlace> findMarketPlaces() {
        return marketDao.findMarketPlaces();
    }

    public void saveOrUpdateMarketPlace(final MarketPlace marketPlace) {
        marketDao.saveOrUpdateMarketPlace(marketPlace);
    }

    public void deleteMarketPlace(final MarketPlace marketPlace) {
        marketDao.deleteMarketPlace(marketPlace);
    }

    // MARKET

    public Market getDefaultMarket() {
        return marketDao.getDefaultMarket();
    }

    public Market getMarketById(final Long marketId) {
        return marketDao.getMarketById(marketId);
    }

    public Market getMarketById(final String rawMarketId) {
        long marketId = -1;
        try {
            marketId = Long.parseLong(rawMarketId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getMarketById(marketId);
    }

    public Market getMarketByCode(final String marketCode) {
        return marketDao.getMarketByCode(marketCode);
    }

    public List<Market> findMarkets() {
        return marketDao.findMarkets();
    }

    public List<Market> getMarketsByMarketPlaceCode(final String marketPlaceCode) {
        return marketDao.getMarketsByMarketPlaceCode(marketPlaceCode);
    }

    public void saveOrUpdateMarket(Market market) {
        marketDao.saveOrUpdateMarket(market);
    }

    public void deleteMarket(Market market) {
        marketDao.deleteMarket(market);
    }

    // MARKET AREA

    public MarketArea getMarketAreaById(final Long marketAreaId) {
        return marketDao.getMarketAreaById(marketAreaId);
    }

    public MarketArea getMarketAreaById(final String rawMarketAreaId) {
        long marketAreaId = -1;
        try {
            marketAreaId = Long.parseLong(rawMarketAreaId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getMarketAreaById(marketAreaId);
    }

    public MarketArea getMarketAreaByCode(final String marketAreaCode) {
        return marketDao.getMarketAreaByCode(marketAreaCode);
    }
}
