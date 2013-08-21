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
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.ConditionsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CutomerMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.HeaderCartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegalTermsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.service.WebCommerceService;

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

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
    protected WebCommerceService webCommerceService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected FormFactory formFactory;

	/**
	 * 
	 */
	@ModelAttribute
	protected void initCommon(final HttpServletRequest request, final Model model) throws Exception {
		model.addAttribute(ModelConstants.COMMON_VIEW_BEAN, viewBeanFactory.buildCommonViewBean(request, requestUtil.getRequestData(request)));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initCustomer(final HttpServletRequest request, final Model model) throws Exception {
		final Customer customer = requestUtil.getCurrentCustomer(request);
		if(customer != null){
			final CustomerViewBean customerView = viewBeanFactory.buildCustomerViewBean(request, requestUtil.getRequestData(request), customer);
			model.addAttribute("customer", customerView);
		}
		
		List<CutomerMenuViewBean> customerLinks = viewBeanFactory.buildCutomerMenuViewBeans(request, requestUtil.getRequestData(request));
		model.addAttribute("customerLinks", customerLinks);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initLegalTerms(final HttpServletRequest request, final Model model) throws Exception {
		// LEGAL-TERMS
		LegalTermsViewBean legalTermsViewBean = viewBeanFactory.buildLegalTermsViewBean(request, requestUtil.getRequestData(request));
		model.addAttribute(ModelConstants.LEGAl_TERMS_VIEW_BEAN, legalTermsViewBean);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initConditionOfUse(final HttpServletRequest request, final Model model) throws Exception {
		// CONDITIONS OF USE
		ConditionsViewBean conditionsViewBean = viewBeanFactory.buildConditionsViewBean(request, requestUtil.getRequestData(request));
		model.addAttribute("conditions", conditionsViewBean);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initCart(final HttpServletRequest request, final Model model) throws Exception {
		// CART
		HeaderCartViewBean headerCartViewBean = viewBeanFactory.buildHeaderCartViewBean(request, requestUtil.getRequestData(request));
		model.addAttribute("headerCart", headerCartViewBean);
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initAllMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// ALL MARKETPLACES
		List<MarketPlaceViewBean> marketPlaceViewBeans = viewBeanFactory.buildMarketPlaceViewBeans(request);
		model.addAttribute("marketPlaces", marketPlaceViewBeans);
	}

	/**
	 * 
	 */
	@ModelAttribute
	protected void initLocalizationForCurrentMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// LOCALIZATIONS FOR THE CURRENT MARKET AREA
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		model.addAttribute("languages", viewBeanFactory.buildLocalizationViewBeansByMarketArea(request, requestUtil.getRequestData(request), currentLocalization));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMaketAreasForCurrentMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// MARKET AREAS FOR THE CURRENT MARKET
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
		model.addAttribute("marketAreas", viewBeanFactory.buildMarketAreaViewBeans(request, currentMarket, new ArrayList<MarketArea>(marketAreaList)));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initMaketsForCurrentMarketPlace(final HttpServletRequest request, final Model model) throws Exception {
		// MARKETS FOR THE CURRENT MARKETPLACE
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		Set<Market> marketList = currentMarketPlace.getMarkets();
		model.addAttribute("markets", viewBeanFactory.buildMarketViewBeans(request, requestUtil.getRequestData(request), currentMarketPlace, new ArrayList<Market>(marketList)));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initRetailersForCurrentMarketArea(final HttpServletRequest request, final Model model) throws Exception {
		// RETAILERS FOR THE CURRENT MARKET AREA
		model.addAttribute("retailers", viewBeanFactory.buildRetailerViewBeansForTheMarketArea(request, requestUtil.getRequestData(request)));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initHeader(final HttpServletRequest request, final Model model) throws Exception {
		// HEADER
		model.addAttribute("menus", viewBeanFactory.buildMenuViewBeans(request, requestUtil.getRequestData(request)));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initFooter(final HttpServletRequest request, final Model model) throws Exception {
		// FOOTER
		model.addAttribute("footerMenus", viewBeanFactory.buildFooterMenuViewBeans(request, requestUtil.getRequestData(request)));
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