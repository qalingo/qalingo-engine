/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.mvc.viewbean.CommonViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ConditionsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FooterMenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.HeaderCartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LegalTermsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketAreaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketPlaceViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.hoteia.qalingo.web.mvc.factory.FormFactory;
import org.hoteia.qalingo.web.service.WebCommerceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 
 * <p>
 * <a href="AbstractMCommerceController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractMCommerceController extends AbstractFrontofficeQalingoController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    protected WebCommerceService webCommerceService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected FormFactory formFactory;

	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.COMMON_VIEW_BEAN)
	protected CommonViewBean initCommon(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildCommonViewBean(requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.CUSTOMER_VIEW_BEAN)
	protected CustomerViewBean initCustomer(final HttpServletRequest request, final Model model) throws Exception {
		final Customer customer = requestUtil.getCurrentCustomer(request);
		if(customer != null){
			return viewBeanFactory.buildCustomerViewBean(requestUtil.getRequestData(request), customer);
		}
		return null;
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.LEGAl_TERMS_VIEW_BEAN)
	protected LegalTermsViewBean initLegalTerms(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildLegalTermsViewBean(requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.CONDITIONS_OF_USE_VIEW_BEAN)
	protected ConditionsViewBean initConditionOfUse(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildConditionsViewBean(requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.HEADER_CART)
	protected HeaderCartViewBean initCart(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildHeaderCartViewBean(requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MARKET_PLACES_VIEW_BEAN)
	protected List<MarketPlaceViewBean> initAllMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// ALL MARKETPLACES
		return viewBeanFactory.buildMarketPlaceViewBeans(requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.LANGUAGES_VIEW_BEAN)
	protected List<LocalizationViewBean> initLocalizationForCurrentMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// LOCALIZATIONS FOR THE CURRENT MARKET AREA
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		return viewBeanFactory.buildLocalizationViewBeansByMarketArea(requestUtil.getRequestData(request), currentLocalization);
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MARKET_AREA_VIEW_BEAN)
	protected MarketAreaViewBean initCurrentMaketArea(final HttpServletRequest request, final Model model) throws Exception {
		// CURRENT MARKET AREA
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		return viewBeanFactory.buildMarketAreaViewBean(requestUtil.getRequestData(request), currentMarketArea);
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MARKET_AREAS_VIEW_BEAN)
	protected List<MarketAreaViewBean> initMaketAreasForCurrentMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// MARKET AREAS FOR THE CURRENT MARKET
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
		return viewBeanFactory.buildMarketAreaViewBeans(requestUtil.getRequestData(request), currentMarket, new ArrayList<MarketArea>(marketAreaList));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MARKETS_VIEW_BEAN)
	protected List<MarketViewBean> initMarketsForCurrentMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// MARKETS FOR THE CURRENT MARKETPLACE
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		Set<Market> marketList = currentMarketPlace.getMarkets();
		return viewBeanFactory.buildMarketViewBeans(requestUtil.getRequestData(request), currentMarketPlace, new ArrayList<Market>(marketList));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.RETAILERS_VIEW_BEAN)
	protected List<RetailerViewBean> initRetailersForCurrentMarketArea(final HttpServletRequest request, final Model model) throws Exception {
		// RETAILERS FOR THE CURRENT MARKET AREA
		return viewBeanFactory.buildRetailerViewBeansForTheMarketArea(requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MENUS_VIEW_BEAN)
	protected List<MenuViewBean> initHeader(final HttpServletRequest request, final Model model) throws Exception {
		// HEADER
		return viewBeanFactory.buildMenuViewBeans(requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.FOOTER_MENUS_VIEW_BEAN)
	protected List<FooterMenuViewBean> initFooter(final HttpServletRequest request, final Model model) throws Exception {
		// FOOTER
		return viewBeanFactory.buildFooterMenuViewBeans(requestUtil.getRequestData(request));
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected Localization getCurrentLocalization(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentLocalization(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected Locale getCurrentLocale(HttpServletRequest request) throws Exception {
		return getCurrentLocalization(request).getLocale();
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentTheme(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentTheme(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentDevice(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentDevice(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentVelocityPath(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentVelocityWebPrefix(request);
	}
	
}