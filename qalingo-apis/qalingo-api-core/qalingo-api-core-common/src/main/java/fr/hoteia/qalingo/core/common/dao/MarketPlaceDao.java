/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao;

import java.util.List;

import fr.hoteia.qalingo.core.common.domain.MarketPlace;

public interface MarketPlaceDao {

	MarketPlace getDefaultMarketPlace();
	
	MarketPlace getMarketPlaceById(Long marketPlaceId);

	MarketPlace getMarketPlaceByCode(String code);
	
	List<MarketPlace> findMarketPlaces();
	
	List<MarketPlace> findByExample(MarketPlace marketPlaceExample);

	void saveOrUpdateMarketPlace(MarketPlace marketPlace);

	void deleteMarketPlace(MarketPlace marketPlace);

}
