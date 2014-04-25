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
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphProduct;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView productMarketingDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_MARKETING_DETAILS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);
		
        final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, FetchPlanGraphProduct.productMarketingBackofficeCatalogueViewFetchPlan());
        final ProductSku productSku = productMarketing.getDefaultProductSku();
        final List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId());
        
        final CatalogCategoryVirtual defaultVirtualCatalogCategory = productService.getDefaultVirtualCatalogCategory(productSku, catalogCategories, true);
        
        ProductMarketingViewBean productMarketingViewBean = backofficeViewBeanFactory.buildViewBeanProductMarketing(requestUtil.getRequestData(request), defaultVirtualCatalogCategory, productMarketing);
        modelAndView.addObject(ModelConstants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);

        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductMarketingGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductMarketingMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData));

        Object[] params = {productMarketing.getName() + " (" + productMarketing.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView,  BoUrls.PRODUCT_MARKETING_DETAILS.getKey(), params);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView productMarketingEdit(final HttpServletRequest request, final HttpServletResponse response, @ModelAttribute(ModelConstants.PRODUCT_MARKETING_FORM) ProductMarketingForm productMarketingForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_MARKETING_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		
		final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);
		final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, FetchPlanGraphProduct.productMarketingBackofficeCatalogueViewFetchPlan());
        final ProductSku productSku = productMarketing.getDefaultProductSku();
		final List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId());
		final CatalogCategoryVirtual defaultVirtualCatalogCategory = productService.getDefaultVirtualCatalogCategory(productMarketing, catalogCategories, true);
        ProductMarketingViewBean productMarketingViewBean = backofficeViewBeanFactory.buildViewBeanProductMarketing(requestUtil.getRequestData(request), defaultVirtualCatalogCategory, productMarketing);
        modelAndView.addObject(ModelConstants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);
        
		modelAndView.addObject(ModelConstants.PRODUCT_MARKETING_FORM, backofficeFormFactory.buildProductMarketingForm(requestData, productMarketing));
		
        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductMarketingGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductMarketingMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing));
		
        Object[] params = {productMarketing.getName() + " (" + productMarketing.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PRODUCT_MARKETING_DETAILS.getKey(), params);

		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView productMarketingEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid @ModelAttribute(ModelConstants.PRODUCT_MARKETING_FORM) ProductMarketingForm productMarketingForm,
								BindingResult result, ModelMap modelMap) throws Exception {
	    final RequestData requestData = requestUtil.getRequestData(request);
	    final String productMarketingCode = productMarketingForm.getCode();

		String urlRedirect = backofficeUrlService.generateUrl(BoUrls.HOME, requestUtil.getRequestData(request));

		if(StringUtils.isNotEmpty(productMarketingCode)){
			if (result.hasErrors()) {
				return productMarketingEdit(request, response, productMarketingForm);
			}
			
			// SANITY CHECK
			final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode);
			webBackofficeService.createOrUpdateProductMarketing(productMarketing, productMarketingForm);
			
	        urlRedirect = backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing);
		}
		
        return new ModelAndView(new RedirectView(urlRedirect));
	}
    
	@RequestMapping(value = BoUrls.PRODUCT_SKU_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView productSkuDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_SKU_DETAILS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);

        ProductSkuViewBean productSkuViewBean = backofficeViewBeanFactory.buildViewBeanProductSku(requestUtil.getRequestData(request), productSku);
        modelAndView.addObject(ModelConstants.PRODUCT_SKU_VIEW_BEAN, productSkuViewBean);
        
        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductSkuGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductSkuMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productSku.getProductMarketing()));
        
        Object[] params = {productSku.getName() + " (" + productSku.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PRODUCT_SKU_DETAILS.getKey(), params);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_SKU_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView productSkuEdit(final HttpServletRequest request, final HttpServletResponse response, @ModelAttribute(ModelConstants.PRODUCT_SKU_FORM) ProductSkuForm productSkuForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_SKU_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
		final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
		
        ProductSkuViewBean productSkuViewBean = backofficeViewBeanFactory.buildViewBeanProductSku(requestUtil.getRequestData(request), productSku);
        modelAndView.addObject(ModelConstants.PRODUCT_SKU_VIEW_BEAN, productSkuViewBean);

        modelAndView.addObject(ModelConstants.PRODUCT_SKU_FORM, backofficeFormFactory.buildProductSkuForm(requestData, productSku));

        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductSkuGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductSkuMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, productSku));

		Object[] params = {productSku.getName() + " (" + productSku.getCode() + ")"};
		initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PRODUCT_SKU_EDIT.getKey(), params);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_SKU_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView productSkuEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid @ModelAttribute(ModelConstants.PRODUCT_SKU_FORM) ProductSkuForm productSkuForm,
								BindingResult result, ModelMap modelMap) throws Exception {
	    final RequestData requestData = requestUtil.getRequestData(request);
		final String productSkuCode = productSkuForm.getCode();
		
	    String urlRedirect = backofficeUrlService.generateUrl(BoUrls.HOME, requestUtil.getRequestData(request));
		
		if(StringUtils.isNotEmpty(productSkuCode)){
			if (result.hasErrors()) {
				return productSkuEdit(request, response, productSkuForm);
			}
			
			// SANITY CHECK
			final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
            webBackofficeService.createOrUpdateProductSku(productSku, productSkuForm);
			
            urlRedirect = backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, productSku);
		}
		
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
}