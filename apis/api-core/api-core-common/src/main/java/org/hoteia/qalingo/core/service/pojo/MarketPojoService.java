package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.market.MarketPojo;

public interface MarketPojoService {

    MarketPlacePojo getMarketPlaceByCode(String marketPlaceCode);

    List<MarketPlacePojo> getMarketPlaces();

    MarketPojo getMarketByCode(String marketCode);

    List<MarketPojo> getMarketsByMarketPlaceCode(String marketPlaceCode);

    MarketAreaPojo getMarketAreaByCode(String marketAreaCode);

}