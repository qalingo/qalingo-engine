/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Retailer;
import fr.hoteia.qalingo.web.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.viewbean.MarketAreaViewBean;
import fr.hoteia.qalingo.web.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.viewbean.MarketViewBean;

public interface ViewBeanFactory {

	CommonViewBean buildCommonViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer) throws Exception;
	
	LegacyViewBean buildLegacyViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer) throws Exception;
	
	List<MarketPlaceViewBean> buildMarketPlaceViewBeans(HttpServletRequest request) throws Exception;
	
	MarketPlaceViewBean buildMarketPlaceViewBean(HttpServletRequest request, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildMarketViewBeans(HttpServletRequest request, List<Market> markets) throws Exception;
	
	List<MarketAreaViewBean> buildMarketAreaViewBeans(HttpServletRequest request, List<MarketArea> marketAreas) throws Exception;
	
	List<LocalizationViewBean> buildLocalizationViewBeans(HttpServletRequest request, MarketArea marketArea) throws Exception;
}
