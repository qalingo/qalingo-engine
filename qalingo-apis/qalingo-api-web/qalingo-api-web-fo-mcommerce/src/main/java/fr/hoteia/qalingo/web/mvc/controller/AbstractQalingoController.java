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

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Retailer;
import fr.hoteia.qalingo.core.common.service.CustomerService;
import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.factory.ModelAndViewFactory;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.CutomerMenuViewBean;

/**
 * 
 * <p>
 * <a href="AbstractQalingoController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractQalingoController extends AbstractController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static ApplicationContext ctx = null;

	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected ModelAndViewFactory modelAndViewFactory;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	@Autowired
    protected FormFactory formFactory;

	@Autowired
	protected CoreMessageSource coreMessageSource;

	@Autowired
	protected CustomerService customerService;

	@Autowired
    protected UrlService urlService;
	
	/**
	 * 
	 */
	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * 
	 */
	protected void initPage(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    }
	
	/**
	 * 
	 */
	protected void initPage(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final String titleKeyPrefixSufix) throws Exception {
		initPage(request, response);
		initCommon(request, response, modelAndView, titleKeyPrefixSufix);
		initBreadcrumb(request, modelAndView, titleKeyPrefixSufix);
		initSeo(request, modelAndView, titleKeyPrefixSufix);
		initCustomer(request, modelAndView);
    }
	
	/**
	 * 
	 */
	protected void initCommon(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView, final String titleKeyPrefixSufix) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String customerId = getCustomerId();
		modelAndView.addObject("localeLanguageCode", locale.getLanguage());
		modelAndView.addObject("contextPath", request.getContextPath());
		modelAndView.addObject("theme", requestUtil.getCurrentTheme(request));
		modelAndViewFactory.initCommonModelAndView(request, response, modelAndView, customerId);
	}
	
	/**
	 * 
	 */
	protected void initBreadcrumb(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKeyPrefixSufix) throws Exception {
	}
	
	/**
	 * 
	 */
	protected void initSeo(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKeyPrefixSufix) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		String seoPageMetaAuthor = coreMessageSource.getMessage("seo.meta.author", null, locale);
        modelAndView.addObject("seoPageMetaAuthor", seoPageMetaAuthor);

		String seoPageMetaKeywords = coreMessageSource.getMessage("page.meta.keywords", null, locale);
        modelAndView.addObject("seoPageMetaKeywords", seoPageMetaKeywords);

		String seoPageMetaDescription = coreMessageSource.getMessage("page.meta.description", null, locale);
        modelAndView.addObject("seoPageMetaDescription", seoPageMetaDescription);

		String pageTitleKey = "header.title." + titleKeyPrefixSufix;
		String seoPageTitle = coreMessageSource.getMessage("page.title.prefix", null, locale) + " - " + coreMessageSource.getMessage(pageTitleKey, null, locale);
        modelAndView.addObject("seoPageTitle", seoPageTitle);

		String mainContentTitleKey = "main.content.title." + titleKeyPrefixSufix;
		try {
			String mainContentTitle = coreMessageSource.getMessage(mainContentTitleKey, null, locale);
	        modelAndView.addObject("mainContentTitle", mainContentTitle);
		} catch (Exception e) {
			// NOTHING
		}
	}
    
	/**
	 * 
	 */
	protected void initCustomer(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final Customer customer = getCustomer();
		modelAndView.addObject("customer", customer);
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		List<CutomerMenuViewBean> customerLinks = viewBeanFactory.buildCutomerMenuViewBeans(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("customerLinks", customerLinks);
	}
	
	/**
	 * 
	 */
	public ApplicationContext getContext() {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		}
		return ctx;
	}

	/**
	 * 
	 */
	protected Customer getCustomer(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = null;
		if(StringUtils.isNotEmpty(username)
				&& !username.equalsIgnoreCase("anonymousUser")){
			customer = customerService.getCustomerByLoginOrEmail(username);
		}
		return customer;
	}
	
	/**
	 * 
	 */
	protected String getCustomerId(){
		Customer customer = getCustomer();
		if(customer != null){
			Long customerId = customer.getId();
			if(customerId != null){
				return customerId.toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
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
		return requestUtil.getCurrentVelocityPrefix(request);
	}
	
}
