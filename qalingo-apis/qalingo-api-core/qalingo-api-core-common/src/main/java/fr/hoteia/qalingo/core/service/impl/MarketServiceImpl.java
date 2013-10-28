/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.MarketDao;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.service.MarketService;

@Service("marketService")
@Transactional
public class MarketServiceImpl implements MarketService {

	@Autowired
	private MarketDao marketDao;
	
	// MARKET
	
	public Market getDefaultMarket() {
		return marketDao.getDefaultMarket();
	}
	
	public Market getMarketById(String rawMarketId) {
		long marketId = -1;
		try {
			marketId = Long.parseLong(rawMarketId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return marketDao.getMarketById(marketId);
	}
	
	public Market getMarketByCode(String marketCode) {
		return marketDao.getMarketByCode(marketCode);
	}

	public List<Market> findMarkets() {
		return marketDao.findMarkets();
	}
	
	public void saveOrUpdateMarket(Market market) {
		marketDao.saveOrUpdateMarket(market);
	}

	public void deleteMarket(Market market) {
		marketDao.deleteMarket(market);
	}

	// MARKET AREA
	
	public MarketArea getMarketAreaById(String rawMarketAreaId) {
		long marketAreaId = -1;
		try {
			marketAreaId = Long.parseLong(rawMarketAreaId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return marketDao.getMarketAreaById(marketAreaId);
	}
	
	public MarketArea getMarketAreaByCode(String marketAreaCode) {
		return marketDao.getMarketAreaByCode(marketAreaCode);
	}
}
