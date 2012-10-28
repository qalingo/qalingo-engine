/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.MarketDao;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.service.MarketService;

@Service("marketService")
@Transactional
public class MarketServiceImpl implements MarketService {

	@Autowired
	private MarketDao marketDao;
	
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
	
	public List<Market> findMarkets(Market criteria) {
		return marketDao.findByExample(criteria);
	}

	public void saveOrUpdateMarket(Market market) {
		marketDao.saveOrUpdateMarket(market);
	}

	public void deleteMarket(Market market) {
		marketDao.deleteMarket(market);
	}

}
