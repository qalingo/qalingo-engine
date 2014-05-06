/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;


public abstract class AbstractEngineSession extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -4246956151302392700L;

	public AbstractEngineSession(){
	}
	
	abstract  public MarketPlace getCurrentMarketPlace();
	
	abstract public void setCurrentMarketPlace(MarketPlace marketPlace);
	
	abstract public Market getCurrentMarket();
	
	abstract public void setCurrentMarket(Market market);
	
	abstract public MarketArea getCurrentMarketArea();
	
	abstract public void setCurrentMarketArea(MarketArea marketArea);
	
	abstract public Localization getCurrentMarketAreaLocalization();
	
	abstract public void setCurrentMarketAreaLocalization(Localization localization);
	
	abstract public Retailer getCurrentMarketAreaRetailer();
	
	abstract public void setCurrentMarketAreaRetailer(Retailer retailer);
    
    abstract public CurrencyReferential getCurrentMarketAreaCurrency();
    
    abstract public void setCurrentMarketAreaCurrency(CurrencyReferential currency);
	
}