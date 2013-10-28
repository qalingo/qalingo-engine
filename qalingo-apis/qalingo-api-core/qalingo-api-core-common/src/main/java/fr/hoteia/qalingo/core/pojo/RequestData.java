package fr.hoteia.qalingo.core.pojo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;

public class RequestData implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 6012861562514088614L;

	private String contextNameValue;
	private String contextPath;
	private String VelocityEmailPrefix;

	private HttpServletRequest request;
	
	private MarketPlace marketPlace;
	private Market market;
	private MarketArea marketArea;
	private Localization localization;
	private Retailer retailer;
	
	private Customer customer;
	
	public RequestData() {
    }
	
    public RequestData(String contextPath) {
        this.contextPath = contextPath;
    }

	public RequestData(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) {
		this.marketPlace = marketPlace;
		this.market = market;
		this.marketArea = marketArea;
		this.localization = localization;
		this.retailer = retailer;
    }
	
	public String getContextNameValue() {
	    return contextNameValue;
    }
	
	public void setContextNameValue(String contextNameValue) {
	    this.contextNameValue = contextNameValue;
    }
	
	public String getContextPath() {
		if(StringUtils.isNotEmpty(contextPath)
				&& contextPath.endsWith("/")){
			return contextPath.substring(0, contextPath.length() - 1);
		}
	    return contextPath;
    }
	
	public void setContextPath(String contextPath) {
	    this.contextPath = contextPath;
    }
	
	public void switchContextPathByMarketAreaDomainName(){
		this.contextPath = "http://" + marketArea.getDomainName(contextNameValue) + "/";
	}
	
	public String getVelocityEmailPrefix() {
	    return VelocityEmailPrefix;
    }
	
	public void setVelocityEmailPrefix(String velocityEmailPrefix) {
	    VelocityEmailPrefix = velocityEmailPrefix;
    }
	
	public HttpServletRequest getRequest() {
	    return request;
    }
	
	public void setRequest(HttpServletRequest request) {
	    this.request = request;
    }
	
	public MarketPlace getMarketPlace() {
	    return marketPlace;
    }
	
	public void setMarketPlace(MarketPlace marketPlace) {
	    this.marketPlace = marketPlace;
    }

	public Market getMarket() {
    	return market;
    }

	public void setMarket(Market market) {
    	this.market = market;
    }

	public MarketArea getMarketArea() {
    	return marketArea;
    }

	public void setMarketArea(MarketArea marketArea) {
    	this.marketArea = marketArea;
    }

	public Localization getLocalization() {
    	return localization;
    }

	public void setLocalization(Localization localization) {
    	this.localization = localization;
    }

	public Retailer getRetailer() {
    	return retailer;
    }

	public void setRetailer(Retailer retailer) {
    	this.retailer = retailer;
    }
	
	public Customer getCustomer() {
	    return customer;
    }
	
	public void setCustomer(Customer customer) {
	    this.customer = customer;
    }

}