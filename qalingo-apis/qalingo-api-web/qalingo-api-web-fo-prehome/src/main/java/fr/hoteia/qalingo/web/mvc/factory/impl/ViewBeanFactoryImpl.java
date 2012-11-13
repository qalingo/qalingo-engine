/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Retailer;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;
import fr.hoteia.qalingo.core.common.service.MarketPlaceService;
import fr.hoteia.qalingo.core.common.service.MarketService;
import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.viewbean.MarketAreaViewBean;
import fr.hoteia.qalingo.web.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.viewbean.MarketViewBean;

/**
 * 
 */
@Service("viewBeanFactory")
public class ViewBeanFactoryImpl implements ViewBeanFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
	protected MarketPlaceService marketPlaceService;
	
	@Autowired
	protected MarketService marketService;
	
	@Autowired
    protected UrlService urlService;
	
	
	/**
     * 
     */
	public CommonViewBean buildCommonViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final CommonViewBean commonViewBean = new CommonViewBean();
		
		// NO CACHE FOR THIS PART
		
		final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(request, EngineSettingService.ENGINE_SETTING_CONTEXT_FO_PREHOME);
		commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);
		commonViewBean.setHomeUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer));
		
		return commonViewBean;
	}
	
	/**
     * 
     */
	public LegacyViewBean buildLegacyViewBean(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer) throws Exception {
		final Locale locale = localization.getLocale();
		
		final LegacyViewBean legacy = new LegacyViewBean();
		
		legacy.setPageTitle(coreMessageSource.getMessage("header.title.legacy", null, locale));
		legacy.setTextHtml(coreMessageSource.getMessage("legacy.content.text", null, locale));

		legacy.setWarning(coreMessageSource.getMessage("legacy.warning", null, locale));
		legacy.setCopyright(coreMessageSource.getMessage("footer.copyright", null, locale));
		
		return legacy;
	}
	
	/**
     * 
     */
	public List<MarketPlaceViewBean> buildMarketPlaceViewBeans(final HttpServletRequest request) throws Exception {
		final List<MarketPlaceViewBean> marketPlaceViewBeans = new ArrayList<MarketPlaceViewBean>();
		final List<MarketPlace> marketPlaceList = marketPlaceService.findMarketPlaces();
		for (Iterator<MarketPlace> iteratorMarketPlace = marketPlaceList.iterator(); iteratorMarketPlace.hasNext();) {
			MarketPlace marketPlaceNavigation = (MarketPlace) iteratorMarketPlace.next();
			marketPlaceViewBeans.add(buildMarketPlaceViewBean(request, marketPlaceNavigation));
		}
		return marketPlaceViewBeans;
	}
	
	/**
     * 
     */
	public MarketPlaceViewBean buildMarketPlaceViewBean(final HttpServletRequest request, final MarketPlace marketPlace) throws Exception {
		final Market defaultMarket = marketPlace.getDefaultMarket();
		final MarketArea defaultMarketArea = defaultMarket.getDefaultMarketArea();
		final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
		final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();
		
		MarketPlaceViewBean marketPlaceViewBean = new MarketPlaceViewBean();
		marketPlaceViewBean.setName(marketPlace.getName());
		marketPlaceViewBean.setUrl(urlService.buildHomeUrl(request, marketPlace, defaultMarket, defaultMarketArea, defaultLocalization, defaultRetailer, false));
		
		marketPlaceViewBean.setMarkets(buildMarketViewBeans(request, new ArrayList<Market>(marketPlace.getMarkets())));
		
		return marketPlaceViewBean;
	}
	
	/**
     * 
     */
	public List<MarketViewBean> buildMarketViewBeans(final HttpServletRequest request, final List<Market> markets) throws Exception {
		final List<MarketViewBean> marketViewBeans = new ArrayList<MarketViewBean>();
		for (Iterator<Market> iteratorMarket = markets.iterator(); iteratorMarket.hasNext();) {
			Market marketNavigation = (Market) iteratorMarket.next();
			marketViewBeans.add(buildMarketViewBean(request, marketNavigation));
		}
		return marketViewBeans;
	}
	
	/**
     * 
     */
	public MarketViewBean buildMarketViewBean(final HttpServletRequest request, final Market market) throws Exception {
		final MarketPlace marketPlace = market.getMarketPlace();
		final MarketArea defaultMarketArea = market.getDefaultMarketArea();
		final Localization defaultLocalization = defaultMarketArea.getDefaultLocalization();
		final Retailer defaultRetailer = defaultMarketArea.getDefaultRetailer();
		
		final MarketViewBean marketViewBean = new MarketViewBean();
		marketViewBean.setName(market.getName());
		marketViewBean.setUrl(urlService.buildHomeUrl(request, marketPlace, market, defaultMarketArea, defaultLocalization, defaultRetailer, false));
		
		marketViewBean.setMarketAreas(buildMarketAreaViewBeans(request, new ArrayList<MarketArea>(market.getMarketAreas())));
		
		return marketViewBean;
	}
	
	/**
     * 
     */
	public List<MarketAreaViewBean> buildMarketAreaViewBeans(final HttpServletRequest request, final List<MarketArea> marketAreas) throws Exception {
		final List<MarketAreaViewBean> marketAreaViewBeans = new ArrayList<MarketAreaViewBean>();
		for (Iterator<MarketArea> iteratorMarketArea = marketAreas.iterator(); iteratorMarketArea.hasNext();) {
			MarketArea marketArea = (MarketArea) iteratorMarketArea.next();
			marketAreaViewBeans.add(buildMarketAreaViewBean(request, marketArea));
		}
		return marketAreaViewBeans;
	}
	
	/**
     * 
     */
	public MarketAreaViewBean buildMarketAreaViewBean(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		final Market market = marketArea.getMarket();
		final MarketPlace marketPlace = market.getMarketPlace();
		final Localization defaultLocalization = marketArea.getDefaultLocalization();
		final Retailer defaultRetailer = marketArea.getDefaultRetailer();
		
		final MarketAreaViewBean marketAreaViewBean = new MarketAreaViewBean();
		marketAreaViewBean.setName(marketArea.getName());
		marketAreaViewBean.setUrl(urlService.buildHomeUrl(request, marketPlace, market, marketArea, defaultLocalization, defaultRetailer, false));
		return marketAreaViewBean;
	}
	
	/**
     * 
     */
	public List<LocalizationViewBean> buildLocalizationViewBeans(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		final Market market = marketArea.getMarket();
		final MarketPlace marketPlace = market.getMarketPlace();
		final List<Localization> translationAvailables = new ArrayList<Localization>(marketArea.getLocalizations());
		final Retailer retailer = requestUtil.getCurrentRetailer(request);
		
		final List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
		for (Iterator<Localization> iterator = translationAvailables.iterator(); iterator.hasNext();) {
			Localization localization = (Localization) iterator.next();
			String localeCode = localization.getLocaleCode();
			Locale locale = localization.getLocale();
			LocalizationViewBean localizationViewBean = new LocalizationViewBean();
			
			if(StringUtils.isNotEmpty(localeCode)
					&& localeCode.length() == 2){
				localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCode.toLowerCase(), null, locale));
			} else {
				localizationViewBean.setName(coreMessageSource.getMessage("languages." + localeCode, null, locale));
			}
			
			localizationViewBean.setUrl(urlService.buildChangeLanguageUrl(request, marketPlace, market, marketArea, localization, retailer, false));
			localizationViewBeans.add(localizationViewBean);
		}
		return localizationViewBeans;
	}
}
