/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.cms;

import java.util.List;

import org.hoteia.qalingo.core.pojo.LocalizationPojo;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.market.MarketPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

public class CmsContext {

    protected List<MarketPlacePojo> marketPlaces;
    protected List<MarketPojo> markets;
    protected List<MarketAreaPojo> marketAreas;
    protected List<RetailerPojo> retailers;
    protected List<LocalizationPojo> localizations;

    public List<MarketPlacePojo> getMarketPlaces() {
        return marketPlaces;
    }
    
    public void setMarketPlaces(List<MarketPlacePojo> marketPlaces) {
        this.marketPlaces = marketPlaces;
    }

    public List<MarketPojo> getMarkets() {
        return markets;
    }

    public void setMarkets(List<MarketPojo> markets) {
        this.markets = markets;
    }

    public List<MarketAreaPojo> getMarketAreas() {
        return marketAreas;
    }

    public void setMarketAreas(List<MarketAreaPojo> marketAreas) {
        this.marketAreas = marketAreas;
    }

    public List<RetailerPojo> getRetailers() {
        return retailers;
    }

    public void setRetailers(List<RetailerPojo> retailers) {
        this.retailers = retailers;
    }

    public List<LocalizationPojo> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(List<LocalizationPojo> localizations) {
        this.localizations = localizations;
    }

}