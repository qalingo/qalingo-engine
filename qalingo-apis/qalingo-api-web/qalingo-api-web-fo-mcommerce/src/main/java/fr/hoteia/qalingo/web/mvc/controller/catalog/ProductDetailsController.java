/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.catalog;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.service.CatalogCategoryService;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.service.ProductSkuService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductCategoryViewBean;

/**
 * 
 */
@Controller("productDetailsController")
public class ProductDetailsController extends AbstractMCommerceController {

	@Autowired
	protected CatalogCategoryService productCategoryService;
	
	@Autowired
	protected ProductMarketingService productMarketingService;
	
	@Autowired
	protected ProductSkuService productSkuService;
	
	@RequestMapping(FoUrls.PRODUCT_DETAILS_URL)
	public ModelAndView productDetails(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode,
			 						   @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productMarketingCode,
			 						   @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_SKU_CODE) final String productSkuCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PRODUCT_DETAILS.getVelocityPage());
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);

		CatalogCategoryVirtual productCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), categoryCode);
		ProductMarketing productMarketing = productMarketingService.getProductMarketingByCode(currentMarketArea.getId(), currentRetailer.getId(), productMarketingCode);
		
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