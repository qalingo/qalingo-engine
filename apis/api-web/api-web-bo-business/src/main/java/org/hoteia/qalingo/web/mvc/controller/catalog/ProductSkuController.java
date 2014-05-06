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
import org.hoteia.qalingo.core.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
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
@Controller("productSkuController")
public class ProductSkuController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ProductService productService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    protected List<SpecificFetchMode> productSkuFetchPlans = new ArrayList<SpecificFetchMode>();;
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();

    public ProductSkuController() {
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName() + "." + ProductSkuPrice_.currency.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.assets.getName()));
        
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
    }
    
	@RequestMapping(value = BoUrls.PRODUCT_SKU_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView productSkuDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_SKU_DETAILS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		
		final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final ProductSku productSku = productService.getProductSkuByCode(productSkuCode, new FetchPlan(productSkuFetchPlans));

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
	public ModelAndView productSkuEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.PRODUCT_SKU_FORM) ProductSkuForm productSkuForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PRODUCT_SKU_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
		final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);

        if (StringUtils.isNotEmpty(productSkuCode)) {
            // EDIT MODE
            final ProductSku productSku = productService.getProductSkuByCode(productSkuCode, new FetchPlan(productSkuFetchPlans));
            ProductSkuViewBean productSkuViewBean = backofficeViewBeanFactory.buildViewBeanProductSku(requestUtil.getRequestData(request), productSku);
            modelAndView.addObject(ModelConstants.PRODUCT_SKU_VIEW_BEAN, productSkuViewBean);

            modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, productSku));

            Object[] params = { productSku.getName() + " (" + productSku.getCode() + ")" };
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PRODUCT_SKU_EDIT.getKey(), params);

        } else {
            // ADD/CREATE MODE
            final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);
            if (StringUtils.isNotEmpty(productMarketingCode)) {
                final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, new FetchPlan(productMarketingFetchPlans));
                
                modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing));

                Object[] params = { "New Sku" };
                initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PRODUCT_SKU_EDIT.getKey(), params);
                
            } else {
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData);
                return new ModelAndView(new RedirectView(urlRedirect));
            }
         }

        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductSkuGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findProductSkuMarketAreaAttributeDefinitions()));

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.PRODUCT_SKU_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView productSkuEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.PRODUCT_SKU_FORM) ProductSkuForm productSkuForm,
								BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        if (result.hasErrors()) {
            return productSkuEdit(request, model, productSkuForm);
        }
        
        ProductSku productSku = null;
        if(StringUtils.isNotEmpty(productSkuForm.getId())){
            productSku = productService.getProductSkuById(productSkuForm.getId(), new FetchPlan(productSkuFetchPlans));
        }

        try {
            // CREATE OR UPDATE WAREHOUSE
            ProductSku savedProductSku = webBackofficeService.createOrUpdateProductSku(productSku, productSkuForm);
            
            if (productSku == null) {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.PRODUCT_SKU, "create_success_message", locale));
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.PRODUCT_SKU, "update_success_message", locale));
            }
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, savedProductSku);
            return new ModelAndView(new RedirectView(urlRedirect));
            
        } catch (Exception e) {
            addMessageError(result, null, "code", "code", getSpecificMessage(ScopeWebMessage.PRODUCT_SKU, "create_or_update_message", locale));
            logger.error("Can't save or update Product Sku:" + productSkuForm.getId() + "/" + productSkuForm.getCode(), e);
            return productSkuEdit(request, model, productSkuForm);
        }
        
	}
	
    /**
     * 
     */
    @ModelAttribute(ModelConstants.PRODUCT_SKU_FORM)
    protected ProductSkuForm initProductSkuForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
        if(StringUtils.isNotEmpty(productSkuCode)){
            final ProductSku productSku = productService.getProductSkuByCode(productSkuCode, new FetchPlan(productSkuFetchPlans));
            return backofficeFormFactory.buildProductSkuForm(requestData, productSku.getProductMarketing(), productSku);
        }
        final String productMarketingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE);
        if (StringUtils.isNotEmpty(productMarketingCode)) {
            final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, new FetchPlan(productMarketingFetchPlans));
            return backofficeFormFactory.buildProductSkuForm(requestData, productMarketing, null);
        }
        return backofficeFormFactory.buildProductSkuForm(requestData, null, null);
    }

}