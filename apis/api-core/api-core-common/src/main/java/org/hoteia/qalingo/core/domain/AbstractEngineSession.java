/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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
	
	abstract public void setCurrentMarketPlace(MarketPlace currentMarketPlace);
	
	abstract public Market getCurrentMarket();
	
	abstract public void setCurrentMarket(Market currentMarket);
	
	abstract public MarketArea getCurrentMarketArea();
	
	abstract public void setCurrentMarketArea(MarketArea currentMarketArea);
	
	abstract public Localization getCurrentMarketAreaLocalization();
	
	abstract public void setCurrentMarketAreaLocalization(Localization currentMarketAreaLocalization);
	
	abstract public Retailer getCurrentRetailer();
	
	abstract public void setCurrentRetailer(Retailer currentRetailer);
	
}