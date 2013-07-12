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
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;
import fr.hoteia.qalingo.web.mvc.viewbean.LegalTermsViewBean;

/**
 * 
 * <p>
 * <a href="AbstractPrehomeFrontofficeController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractPrehomeFrontofficeController extends AbstractFrontofficeQalingoController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initCommon(final HttpServletRequest request, final Model model) throws Exception {
		// COMMON
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		model.addAttribute(ModelConstants.COMMON_VIEW_BEAN, viewBeanFactory.buildCommonViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initLegalTerms(final HttpServletRequest request, final Model model) throws Exception {
		// LEGAL-TERMS
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		LegalTermsViewBean legalTermsViewBean = viewBeanFactory.buildLegalTermsViewBean(request, currentLocalization);
		model.addAttribute(ModelConstants.LEGAl_TERMS_VIEW_BEAN, legalTermsViewBean);
	}
	
}