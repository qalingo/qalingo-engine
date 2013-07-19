/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

/**
 * 
 * <p>
 * <a href="AbstractBackofficeQalingoController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractTechnicalBackofficeController extends AbstractBackofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
    protected FormFactory formFactory;

	@Autowired
	protected WebBackofficeService webBackofficeService;
	
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initAllPlaces(final HttpServletRequest request, final Model model) throws Exception {
		// ALL MARKETPLACES
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		List<MarketPlaceViewBean> marketPlaceViewBeans = viewBeanFactory.buildMarketPlaceViewBeans(request, currentLocalization);
		model.addAttribute(ModelConstants.MARKET_PLACES_VIEW_BEAN, marketPlaceViewBeans);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// MARKETS FOR THE CURRENT MARKETPLACE
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		Set<Market> marketList = currentMarketPlace.getMarkets();
		model.addAttribute(ModelConstants.MARKETS_VIEW_BEAN, viewBeanFactory.buildMarketViewBeans(request, currentMarketPlace, new ArrayList<Market>(marketList), currentLocalization));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMarkets(final HttpServletRequest request, final Model model) throws Exception {
		// MARKET AREAS FOR THE CURRENT MARKET
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
		model.addAttribute(ModelConstants.MARKET_AREAS_VIEW_BEAN, viewBeanFactory.buildMarketAreaViewBeans(request, currentMarket, new ArrayList<MarketArea>(marketAreaList), currentLocalization));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMarletAreas(final HttpServletRequest request, final Model model) throws Exception {
		// LOCALIZATIONS FOR THE CURRENT MARKET AREA
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		model.addAttribute(ModelConstants.MARKET_LANGUAGES_VIEW_BEAN, viewBeanFactory.buildLocalizationViewBeans(request, currentMarketArea, currentLocalization));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initRetailers(final HttpServletRequest request, final Model model) throws Exception {
		// RETAILERS FOR THE CURRENT MARKET AREA
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		model.addAttribute(ModelConstants.RETAILERS_VIEW_BEAN, viewBeanFactory.buildRetailerViewBeans(request, currentMarketArea, currentLocalization));
	}
	
}