/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service;

import java.util.List;

import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;

public interface MarketService {

	// MARKET
	
	Market getDefaultMarket();

	Market getMarketById(String marketId);
	
	Market getMarketByCode(String marketCode);
	
	List<Market> findMarkets();

	void saveOrUpdateMarket(Market market);
	
	void deleteMarket(Market market);

	// MARKET AREA
	
	MarketArea getMarketAreaById(String rawMarketAreaId);
	
	MarketArea getMarketAreaByCode(String marketAreaCode);

}
