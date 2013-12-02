package org.hoteia.qalingo.core.pojo;

import java.util.List;

import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.market.MarketPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

public class RequestCms {

    protected List<MarketPlacePojo> marketPlaces;

    protected MarketPlacePojo selectedMarketPlace;
    protected MarketPojo selectedMarket;
    protected MarketAreaPojo selectedMarketArea;
    protected RetailerPojo selectedRetailer;
    protected LocalizationPojo selectedLocalization;

    public List<MarketPlacePojo> getMarketPlaces() {
        return marketPlaces;
    }
    
    public void setMarketPlaces(List<MarketPlacePojo> marketPlaces) {
        this.marketPlaces = marketPlaces;
    }

    public MarketPlacePojo getSelectedMarketPlace() {
        return selectedMarketPlace;
    }

    public void setSelectedMarketPlace(MarketPlacePojo selectedMarketPlace) {
        this.selectedMarketPlace = selectedMarketPlace;
    }

    public MarketPojo getSelectedMarket() {
        return selectedMarket;
    }

    public void setSelectedMarket(MarketPojo selectedMarket) {
        this.selectedMarket = selectedMarket;
    }

    public MarketAreaPojo getSelectedMarketArea() {
        return selectedMarketArea;
    }

    public void setSelectedMarketArea(MarketAreaPojo selectedMarketArea) {
        this.selectedMarketArea = selectedMarketArea;
    }

    public RetailerPojo getSelectedRetailer() {
        return selectedRetailer;
    }

    public void setSelectedRetailer(RetailerPojo selectedRetailer) {
        this.selectedRetailer = selectedRetailer;
    }

    public LocalizationPojo getSelectedLocalization() {
        return selectedLocalization;
    }

    public void setSelectedLocalization(LocalizationPojo selectedLocalization) {
        this.selectedLocalization = selectedLocalization;
    }

}