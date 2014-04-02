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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogBreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductRatesViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.ProductCommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("productDetailsController")
@SessionAttributes
public class ProductDetailsController extends AbstractMCommerceController {

	@Autowired
	protected CatalogCategoryService catalogCategoryService;
	
	@Autowired
	protected ProductService productService;
	
	@RequestMapping(FoUrls.PRODUCT_DETAILS_URL)
	public ModelAndView productDetails(final HttpServletRequest request, final HttpServletResponse response ,final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode,
			 						   @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productMarketingCode,
			 						   @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_SKU_CODE) final String productSkuCode,
			 						   @ModelAttribute(ModelConstants.PRODUCT_COMMENT_FORM) ProductCommentForm productCommentForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PRODUCT_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();

		CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), categoryCode);
		ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode);
		
		final CatalogCategoryViewBean catalogCategoryViewBean = frontofficeViewBeanFactory.buildViewBeanCatalogCategory(requestUtil.getRequestData(request), catalogCategory);
		model.addAttribute(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);

        final ProductMarketingViewBean productMarketingViewBean = frontofficeViewBeanFactory.buildViewBeanProductMarketing(requestUtil.getRequestData(request), catalogCategory, productMarketing);
        model.addAttribute(ModelConstants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);
        
        final CatalogBreadcrumbViewBean catalogBreadcrumbViewBean = frontofficeViewBeanFactory.buildViewBeanCatalogBreadcrumb(requestUtil.getRequestData(request) , catalogCategory);
		model.addAttribute(ModelConstants.CATALOG_BREADCRUMB_VIEW_BEAN, catalogBreadcrumbViewBean);

        //for now, get the featured products in same category
        //TODO: define related products
        final List<ProductMarketingViewBean> relatedProducts = catalogCategoryViewBean.getFeaturedProductMarketings();
        model.addAttribute(ModelConstants.RELATED_PPRODUCT_MARKETING_VIEW_BEAN, relatedProducts);
        
        final CustomerProductRatesViewBean customerProductRatesViewBean = productService.getProductMarketingCustomerRateDetails(productMarketing.getId());
        model.addAttribute(ModelConstants.CUSTOMER_PRODUCT_RATES_VIEW_BEAN, customerProductRatesViewBean);
        
        //Check if has authorized user
        if(requestData.getCustomer() != null){
	        productCommentForm = formFactory.buildProductCommentForm(requestData, productMarketing);
	        model.addAttribute(ModelConstants.PRODUCT_COMMENT_FORM, productCommentForm);
	        model.addAttribute(ModelConstants.PRODUCT_COMMENT_SUBMIT_URL, urlService.generateUrl(FoUrls.PRODUCT_COMMENT, requestData, productMarketing));
        }
        
        requestUtil.addOrUpdateRecentProductToCookie(productMarketing.getId(), request, response);
        
        // SEO
        model.addAttribute(ModelConstants.PAGE_META_OG_TITLE, productMarketingViewBean.getI18nName() );
        
        model.addAttribute(ModelConstants.PAGE_META_OG_DESCRIPTION, productMarketingViewBean.getI18nDescription());
        
        model.addAttribute(ModelConstants.PAGE_META_OG_IMAGE, urlService.buildAbsoluteUrl(requestData, productMarketingViewBean.getCarouselImage()));

        Object[] params = { productMarketingViewBean.getI18nName() };
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.PRODUCT_DETAILS.getKey(), params);
        
        return modelAndView;
	}

}