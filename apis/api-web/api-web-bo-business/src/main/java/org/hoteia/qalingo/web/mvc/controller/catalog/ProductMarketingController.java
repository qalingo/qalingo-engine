/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuPrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("productMarketingController")
public class ProductMarketingController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ProductService productService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();
    
    public ProductMarketingController() {
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName() + "." + ProductSkuPrice_.currency.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productAssociationLinks.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.assets.getName()));
    }
    
	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView productMarketingDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_MARKETING_DETAILS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);
		
        final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, new FetchPlan(productMarketingFetchPlans));
        final ProductSku productSku = productMarketing.getDefaultProductSku();
        final List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId());
        
        final CatalogCategoryVirtual defaultVirtualCatalogCategory = productService.getDefaultVirtualCatalogCategory(productSku, catalogCategories, true);
        
        ProductMarketingViewBean productMarketingViewBean = backofficeViewBeanFactory.buildViewBeanProductMarketing(requestUtil.getRequestData(request), defaultVirtualCatalogCategory, productMarketing, productSku);
        modelAndView.addObject(ModelConstants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);

        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductMarketingGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductMarketingMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData));

        Object[] params = {productMarketing.getName() + " (" + productMarketing.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView,  BoUrls.PRODUCT_MARKETING_DETAILS.getKey(), params);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView productMarketingEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.PRODUCT_MARKETING_FORM) ProductMarketingForm productMarketingForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_MARKETING_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		
		final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);

		if(StringUtils.isNotEmpty(productMarketingCode)){
		    // EDIT MODE
	        final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, new FetchPlan(productMarketingFetchPlans));
	        final ProductSku productSku = productMarketing.getDefaultProductSku();
	        final List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId());
	        final CatalogCategoryVirtual defaultVirtualCatalogCategory = productService.getDefaultVirtualCatalogCategory(productMarketing, catalogCategories, true);
	        
	        ProductMarketingViewBean productMarketingViewBean = backofficeViewBeanFactory.buildViewBeanProductMarketing(requestUtil.getRequestData(request), defaultVirtualCatalogCategory, productMarketing, productSku);
	        modelAndView.addObject(ModelConstants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);
	        
	        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductMarketingGlobalAttributeDefinitions()));
	        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductMarketingMarketAreaAttributeDefinitions()));

	        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing));
	        
	        Object[] params = {productMarketing.getName() + " (" + productMarketing.getCode() + ")"};
	        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PRODUCT_MARKETING_EDIT.getKey(), params);

		} else {
		    // ADD/CREATE MODE
            modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData));

            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PRODUCT_MARKETING_ADD.getKey(), null);
		}

		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_MARKETING_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView productMarketingEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.PRODUCT_MARKETING_FORM) ProductMarketingForm productMarketingForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        if (result.hasErrors()) {
            return productMarketingEdit(request, model, productMarketingForm);
        }
        
        ProductMarketing productMarketing = null;
        if(StringUtils.isNotEmpty(productMarketingForm.getId())){
            productMarketing = productService.getProductMarketingById(productMarketingForm.getId(), new FetchPlan(productMarketingFetchPlans));
        }

        try {
            // CREATE OR UPDATE WAREHOUSE
            webBackofficeService.createOrUpdateProductMarketing(productMarketing, productMarketingForm);
            
            if (productMarketing == null) {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.PRODUCT_MARKETING, "create_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData);
                return new ModelAndView(new RedirectView(urlRedirect));
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.PRODUCT_MARKETING, "update_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing);
                return new ModelAndView(new RedirectView(urlRedirect));
            }
            
        } catch (Exception e) {
            addMessageError(result, null, "code", "code", getSpecificMessage(ScopeWebMessage.PRODUCT_MARKETING, "create_or_update_message", locale));
            logger.error("Can't save or update ProductMarketing:" + productMarketingForm.getId() + "/" + productMarketingForm.getCode(), e);
            return productMarketingEdit(request, model, productMarketingForm);
        }
	}
    
    /**
     * 
     */
    @ModelAttribute(ModelConstants.PRODUCT_MARKETING_FORM)
    protected ProductMarketingForm initProductMarketingForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);
        if(StringUtils.isNotEmpty(productMarketingCode)){
            final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, new FetchPlan(productMarketingFetchPlans));
            return backofficeFormFactory.buildProductMarketingForm(requestData, productMarketing);
        }
        return backofficeFormFactory.buildProductMarketingForm(requestData, null);
    }
	
}