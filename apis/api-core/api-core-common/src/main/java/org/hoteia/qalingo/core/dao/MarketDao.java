/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;

public interface MarketDao {

    // MARKET PLACE

    MarketPlace getDefaultMarketPlace(Object... params);

    MarketPlace getMarketPlaceById(Long marketPlaceId, Object... params);

    MarketPlace getMarketPlaceByCode(String code, Object... params);

    List<MarketPlace> findMarketPlaces(Object... params);

    MarketPlace saveOrUpdateMarketPlace(MarketPlace marketPlace);

    void deleteMarketPlace(MarketPlace marketPlace);

    // MARKET

    Market getDefaultMarket(Object... params);

    Market getMarketById(Long marketId, Object... params);

    Market getMarketByCode(String code, Object... params);

    List<Market> findMarkets(Object... params);

    List<Market> getMarketsByMarketPlaceCode(String marketPlaceCode, Object... params);

    Market saveOrUpdateMarket(Market market);

    void deleteMarket(Market market);

    // MARKET AREA

    MarketArea getMarketAreaById(Long marketAreaId, Object... params);

    MarketArea getMarketAreaByCode(String code, Object... params);

    List<MarketArea> getMarketAreaByGeolocCountryCode(String countryCode, Object... params);

    MarketArea saveOrUpdateMarketArea(MarketArea marketArea);

    void deleteMarketArea(MarketArea marketArea);
    
}