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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("productController")
public class ProductController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ProductService productService;

	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView productMarketingDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_MARKETING_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
		final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);
		final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode);
		
		// "business.product.marketing.details";
		initProductMarketingModelAndView(request, modelAndView, productMarketing);
		initSpecificSeo(request, modelAndView, "", productMarketing.getBusinessName());
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView productMarketingEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_MARKETING_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		
		final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);
		final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode);

		initProductMarketingModelAndView(request, modelAndView, productMarketing);
		modelAndView.addObject("productMarketingForm", backofficeFormFactory.buildProductMarketingForm(requestData, productMarketing));
		initSpecificSeo(request, modelAndView, "", productMarketing.getBusinessName());

//		modelAndView.addObject("productMarketingDetails", viewBeanFactory.buildUserEditViewBean(request, currentLocalization, user));

		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView productMarketingEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid ProductMarketingForm productMarketingForm,
								BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
		final String productMarketingCode = productMarketingForm.getCode();

		String urlRedirect = backofficeUrlService.generateUrl(BoUrls.HOME, requestUtil.getRequestData(request));

		if(StringUtils.isNotEmpty(productMarketingCode)){
			if (result.hasErrors()) {
				return productMarketingEdit(request, response, modelMap);
			}
			
			// SANITY CHECK
			final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode);
			if(productMarketing != null){
				// UPDATE PRODUCT MARKETING
				webBackofficeService.updateProductMarketing(productMarketing, productMarketingForm);
				
			} else {
				// CREATE PRODUCT MARKETING
				webBackofficeService.createProductMarketing(productMarketing, productMarketingForm);
			}
			
		     urlRedirect = backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestUtil.getRequestData(request), productMarketing);
		}
		
        return new ModelAndView(new RedirectView(urlRedirect));
	}
    
	@RequestMapping(value = BoUrls.PRODUCT_SKU_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView productSkuDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_SKU_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
		final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);

		// "business.product.sku.details";
		initProductSkuModelAndView(request, modelAndView, productSku);
		modelAndView.addObject("productSkuForm", backofficeFormFactory.buildProductSkuForm(requestData, productSku));
		initSpecificSeo(request, modelAndView, "", productSku.getBusinessName());
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_SKU_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView productSkuEdit(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_SKU_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
		final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
		
		initProductSkuModelAndView(request, modelAndView, productSku);
		modelAndView.addObject("productSkuForm", backofficeFormFactory.buildProductSkuForm(requestData, productSku));
		initSpecificSeo(request, modelAndView, "", productSku.getBusinessName());
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_SKU_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView productSkuEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid ProductSkuForm productSkuForm,
								BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
		final String productSkuCode = productSkuForm.getCode();
		
	    String urlRedirect = backofficeUrlService.generateUrl(BoUrls.HOME, requestUtil.getRequestData(request));
		
		if(StringUtils.isNotEmpty(productSkuCode)){
			if (result.hasErrors()) {
				return productSkuEdit(request, response);
			}
			
			// SANITY CHECK
			final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
			if(productSku != null){
				// UPDATE PRODUCT MARKETING
				webBackofficeService.updateProductSku(productSku, productSkuForm);
				
			} else {
				// CREATE PRODUCT MARKETING
				webBackofficeService.createProductSku(productSku, productSkuForm);
			}
			
	          urlRedirect = backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestUtil.getRequestData(request), productSku);
		}
		
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	/**
	 * 
	 */
	protected void initSpecificSeo(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKeyPrefixSufix, String productName) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

		String pageTitleKey = "header.title." + titleKeyPrefixSufix;
		String appName = (String) modelAndView.getModelMap().get(Constants.APP_NAME);
		Object[] params = {productName};
		String headerTitle = coreMessageSource.getMessage(pageTitleKey, params, locale);
        modelAndView.addObject("seoPageTitle", appName + " - " + headerTitle);
        modelAndView.addObject("mainContentTitle", headerTitle);
	}
	
	/**
     * 
     */
	protected void initProductMarketingModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductMarketing productMarketing) throws Exception {
		
		ProductMarketingViewBean productMarketingViewBean = backofficeViewBeanFactory.buildProductMarketingViewBean(requestUtil.getRequestData(request), productMarketing, true);
		
		modelAndView.addObject(Constants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);
	}
	
	/**
     * 
     */
	protected void initProductSkuModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductSku productSku) throws Exception {
		
		ProductSkuViewBean productSkuViewBean = backofficeViewBeanFactory.buildProductSkuViewBean(requestUtil.getRequestData(request), productSku);
		
		modelAndView.addObject(Constants.PRODUCT_SKU_VIEW_BEAN, productSkuViewBean);
	}
	
}