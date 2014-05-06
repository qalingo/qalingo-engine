/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;

public interface MarketService {

    // MARKET PLACE

    MarketPlace getDefaultMarketPlace(Object... params);

    MarketPlace getMarketPlaceById(Long marketPlaceId, Object... params);

    MarketPlace getMarketPlaceById(String marketPlaceId, Object... params);

    MarketPlace getMarketPlaceByCode(String marketPlaceCode, Object... params);

    List<MarketPlace> findMarketPlaces(Object... params);

    void saveOrUpdateMarketPlace(MarketPlace marketPlace);

    void deleteMarketPlace(MarketPlace marketPlace);

    // MARKET

    Market getDefaultMarket(Object... params);

    Market getMarketById(Long marketId, Object... params);

    Market getMarketById(String marketId, Object... params);

    Market getMarketByCode(String marketCode, Object... params);

    List<Market> findMarkets(Object... params);

    List<Market> getMarketsByMarketPlaceCode(String marketPlaceCode, Object... params);

    void saveOrUpdateMarket(Market market);

    void deleteMarket(Market market);

    // MARKET AREA

    MarketArea getMarketAreaById(Long marketAreaId, Object... params);

    MarketArea getMarketAreaById(String marketAreaId, Object... params);

    MarketArea getMarketAreaByCode(String marketAreaCode, Object... params);

    List<MarketArea> getMarketAreaByGeolocCountryCode(String countryCode, Object... params);

}