/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.catalog;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductCategoryViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;

/**
 * 
 */
@Controller("productLineController")
public class ProductLineController extends AbstractMCommerceController {

	@Autowired
	protected CatalogCategoryService productCategoryService;
	
	@RequestMapping(FoUrls.CATEGORY_AS_LINE_URL)
	public ModelAndView productLine(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CATEGORY_AS_LINE.getVelocityPage());
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);

		final CatalogCategoryVirtual productCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), categoryCode);
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		String seoPageMetaKeywords = coreMessageSource.getMessage("page.meta.keywords", locale);
        model.addAttribute("seoPageMetaKeywords", seoPageMetaKeywords);

		String seoPageMetaDescription = coreMessageSource.getMessage("page.meta.description", locale);
        model.addAttribute("seoPageMetaDescription", seoPageMetaDescription);

		String pageTitleKey = "header.title." + "";
		String seoPageTitle = coreMessageSource.getMessage("page.title.prefix", locale) + " - " + coreMessageSource.getMessage(pageTitleKey, locale);
        model.addAttribute("seoPageTitle", seoPageTitle);
        
		final ProductCategoryViewBean productCategoryViewBean = viewBeanFactory.buildProductCategoryViewBean(requestUtil.getRequestData(request), productCategory);
		model.addAttribute("productCategory", productCategoryViewBean);
		
        return modelAndView;
	}
    
}