package org.hoteia.qalingo.core.pojo;

import java.util.List;

import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.market.MarketPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

public class RequestCms {

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