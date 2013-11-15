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

import org.hoteia.qalingo.core.dao.MarketPlaceDao;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.service.MarketPlaceService;

@Service("marketPlaceService")
@Transactional
public class MarketPlaceServiceImpl implements MarketPlaceService {

	@Autowired
	private MarketPlaceDao marketPlaceDao;

	public MarketPlace getDefaultMarketPlace() {
		return marketPlaceDao.getDefaultMarketPlace();
	}
	
	public MarketPlace getMarketPlaceById(String rawMarketPlaceId) {
		long marketPlaceId = -1;
		try {
			marketPlaceId = Long.parseLong(rawMarketPlaceId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return marketPlaceDao.getMarketPlaceById(marketPlaceId);
	}

	public MarketPlace getMarketPlaceByCode(String marketPlaceCode) {
		return marketPlaceDao.getMarketPlaceByCode(marketPlaceCode);
	}
	
	public List<MarketPlace> findMarketPlaces() {
		return marketPlaceDao.findMarketPlaces();
	}
	
	public void saveOrUpdateMarketPlace(MarketPlace marketPlace) {
		marketPlaceDao.saveOrUpdateMarketPlace(marketPlace);
	}

	public void deleteMarketPlace(MarketPlace marketPlace) {
		marketPlaceDao.deleteMarketPlace(marketPlace);
	}

}
