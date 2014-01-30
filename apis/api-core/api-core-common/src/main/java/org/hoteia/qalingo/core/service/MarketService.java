/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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

    MarketPlace getDefaultMarketPlace();

    MarketPlace getMarketPlaceById(Long marketPlaceId);

    MarketPlace getMarketPlaceById(String marketPlaceId);

    MarketPlace getMarketPlaceByCode(String marketPlaceCode);

    List<MarketPlace> findMarketPlaces();

    void saveOrUpdateMarketPlace(MarketPlace marketPlace);

    void deleteMarketPlace(MarketPlace marketPlace);

    // MARKET

    Market getDefaultMarket();

    Market getMarketById(Long marketId);

    Market getMarketById(String marketId);

    Market getMarketByCode(String marketCode);

    List<Market> findMarkets();

    List<Market> getMarketsByMarketPlaceCode(String marketPlaceCode);

    void saveOrUpdateMarket(Market market);

    void deleteMarket(Market market);

    // MARKET AREA

    MarketArea getMarketAreaById(Long marketAreaId);

    MarketArea getMarketAreaById(String marketAreaId);

    MarketArea getMarketAreaByCode(String marketAreaCode);

    List<MarketArea> getMarketAreaByGeolocCountryCode(String countryCode);

}