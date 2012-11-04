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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.common.domain.Company;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.service.ProductMarketingService;
import fr.hoteia.qalingo.core.common.service.StoreService;
import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.factory.ModelAndViewFactory;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

/**
 * 
 */
@Service("modelAndViewFactory")
public class ModelAndViewFactoryImpl implements ModelAndViewFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected UrlService urlService;
	
	@Autowired
    protected StoreService storeService;
	
	@Autowired
    protected ProductMarketingService productMarketingService;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	@Autowired
    protected FormFactory formFactory;
	
	/**
     * 
     */
	public void initCommonModelAndView(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		
		// COMMON
		CommonViewBean commonViewBean = viewBeanFactory.buildCommonViewBean(request, currentLocalization);
		modelAndView.addObject("common", commonViewBean);

		// QUICK SEARCH
		QuickSearchViewBean quickSearchViewBean = viewBeanFactory.buildQuickSearchViewBean(request, currentLocalization);
		modelAndView.addObject("quickSearch", quickSearchViewBean);
		
		// HEADER
		modelAndView.addObject("menus", viewBeanFactory.buildMenuViewBeans(request, currentLocalization));

		// LOCALIZATIONS
		Company company = requestUtil.getCurrentCompany(request);
		if(company != null){
			Set<Localization> localizations = company.getLocalizations();
			modelAndView.addObject("languages", viewBeanFactory.buildLocalizationViewBeans(request, new ArrayList<Localization>(localizations)));
		}

		// LEGACY
		LegacyViewBean legacyViewBean = viewBeanFactory.buildLegacyViewBean(request, currentLocalization);
		modelAndView.addObject("legacy", legacyViewBean);

		// FOOTER
		modelAndView.addObject("footerMenus", viewBeanFactory.buildFooterMenuViewBeans(request, currentLocalization));
	}
	
	/**
     * 
     */
	public void initLoginModelAndView(final HttpServletRequest request, ModelAndView modelAndView) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		SecurityViewBean security = viewBeanFactory.buildSecurityViewBean(request, currentLocalization);
		modelAndView.addObject("security", security);
	}

}
