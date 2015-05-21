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
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.ProductBrand_;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductBrandCustomerComment;
import org.hoteia.qalingo.core.domain.ProductBrandCustomerRate;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.form.CreateAccountForm;
import org.hoteia.qalingo.core.web.mvc.form.CustomerCommentForm;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("brandCommentController")
public class BrandCommentController extends AbstractMCommerceController {

	@Autowired
	protected ProductService productService;
	
    protected List<SpecificFetchMode> productBrandFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();

    public BrandCommentController() {
        productBrandFetchPlans.add(new SpecificFetchMode(ProductBrand_.attributes.getName()));
        productBrandFetchPlans.add(new SpecificFetchMode(ProductBrand_.assets.getName()));
    }
    
    @RequestMapping(value = FoUrls.BRAND_COMMENT_URL, method = RequestMethod.GET)
    public ModelAndView displayProductCommentForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_BRAND_CODE) final String brandCode, final Model model,
            @ModelAttribute(ModelConstants.CUSTOMER_COMMENT_FORM) CustomerCommentForm customerCommentForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = (ModelAndViewThemeDevice) getModelAndView(request);

        model.addAttribute(ModelConstants.URL_BACK, requestUtil.getLastRequestUrl(request));

        // STAR
        String qualityOfServiceMax = "5";
        EngineSetting engineSetting = engineSettingService.getSettingProductMaxScoreValue();
        if (engineSetting != null) {
            qualityOfServiceMax = engineSetting.getDefaultValue();
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_STAR_SCORE_MAX);
            if (engineSettingValue != null) {
                qualityOfServiceMax = engineSettingValue.getValue();
            }
        }

        try {
            model.addAttribute(ModelConstants.QUALITY_OF_SERVICE_SCORE_MAX, Integer.parseInt(qualityOfServiceMax));
        } catch (Exception e) {
            model.addAttribute(ModelConstants.QUALITY_OF_SERVICE_SCORE_MAX, 5);
        }

        // PRICE
        String priceMax = "5";
        if (engineSetting != null) {
            priceMax = engineSetting.getDefaultValue();
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_STAR_SCORE_MAX);
            if (engineSettingValue != null) {
                priceMax = engineSettingValue.getValue();
            }
        }

        try {
            model.addAttribute(ModelConstants.PRICE_SCORE_MAX, Integer.parseInt(priceMax));
        } catch (Exception e) {
            model.addAttribute(ModelConstants.PRICE_SCORE_MAX, 5);
        }

        final RequestData requestData = requestUtil.getRequestData(request);

        ProductBrand productMarketing = productService.getProductBrandByCode(brandCode);

        if (customerCommentForm == null || customerCommentForm.equals(new CustomerCommentForm())) {
            customerCommentForm = formFactory.buildCustomerCommentForm(requestData, productMarketing.getCode());
            model.addAttribute(ModelConstants.CUSTOMER_COMMENT_FORM, customerCommentForm);
        }

        return modelAndView;
    }

    @RequestMapping(value = FoUrls.BRAND_COMMENT_URL, method = RequestMethod.POST)
    public ModelAndView submitProductComment(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_BRAND_CODE) final String brandCode,
            @Valid @ModelAttribute(ModelConstants.CUSTOMER_COMMENT_FORM) CustomerCommentForm customerCommentForm, BindingResult result, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Market currentMarket = requestData.getMarket();
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();
        Customer customer = requestData.getCustomer();

        if (result.hasErrors()) {
            return displayProductCommentForm(request, brandCode, model, customerCommentForm);
        }

        if (customer == null) {
            if (StringUtils.isNotEmpty(customerCommentForm.getName()) && StringUtils.isNotEmpty(customerCommentForm.getEmail())) {
                // CHECK IF THE CUSTOMER EXIST
                final Customer customerCheck = customerService.getCustomerByLoginOrEmail(customerCommentForm.getEmail());
                if (customerCheck == null) {
                    // CREATE A LIGHT ACCOUNT
                    CreateAccountForm createAccountForm = new CreateAccountForm();
                    createAccountForm.setEmail(customerCommentForm.getEmail());
                    createAccountForm.setLastname(customerCommentForm.getName());
                    customer = webManagementService.buildAndSaveQuickNewCustomer(requestData, currentMarket, currentMarketArea, createAccountForm);
                    
                    // Save the email confirmation
                    webManagementService.buildAndSaveCustomerNewAccountMail(requestData, customer);
                    
                    // Login the new customer
                    securityRequestUtil.authenticationCustomer(request, customer);
                    
                } else {
                    // WARNING
                    addErrorMessage(request, getSpecificMessage(ScopeWebMessage.COMMENT_VOTE, "customer_must_be_logged", locale));
                    return displayProductCommentForm(request, brandCode, model, customerCommentForm);
                }
            } else {
                // WARNING
                addErrorMessage(request, getSpecificMessage(ScopeWebMessage.COMMENT_VOTE, "customer_must_be_logged", locale));
                return displayProductCommentForm(request, brandCode, model, customerCommentForm);
            }
        }

        int qualityOfService = customerCommentForm.getQualityOfService();
        int ratioQualityPrice = customerCommentForm.getRatioQualityPrice();
        int priceScore = customerCommentForm.getPriceScore();

        if (StringUtils.isEmpty(customerCommentForm.getComment()) && qualityOfService == 0 && ratioQualityPrice == 0 && priceScore == 0) {
            // WARNING
            addErrorMessage(request, getSpecificMessage(ScopeWebMessage.COMMENT_VOTE, "message_cant_be_empty", locale));
            return displayProductCommentForm(request, brandCode, model, customerCommentForm);
        }

        final ProductBrand productBrand = productService.getProductBrandByCode(brandCode);

        if (qualityOfService != 0) {
            ProductBrandCustomerRate productCustomerRate = new ProductBrandCustomerRate();
            productCustomerRate.setRate(qualityOfService);
            productCustomerRate.setProductBrandId(productBrand.getId());
            productCustomerRate.setCustomerId(customer.getId());
            productCustomerRate.setType(Constants.PRODUCT_QUALITY_RATING_TYPE);
            productService.saveOrUpdateProductBrandCustomerRate(productCustomerRate);
        }

        if (ratioQualityPrice != 0) {
            ProductBrandCustomerRate productCustomerRate = new ProductBrandCustomerRate();
            productCustomerRate.setRate(ratioQualityPrice);
            productCustomerRate.setProductBrandId(productBrand.getId());
            productCustomerRate.setCustomerId(customer.getId());
            productCustomerRate.setType(Constants.PRODUCT_PRICE_RATING_TYPE);
            productService.saveOrUpdateProductBrandCustomerRate(productCustomerRate);
        }

        if (priceScore != 0) {
            ProductBrandCustomerRate productCustomerRate = new ProductBrandCustomerRate();
            productCustomerRate.setRate(priceScore);
            productCustomerRate.setProductBrandId(productBrand.getId());
            productCustomerRate.setCustomerId(customer.getId());
            productCustomerRate.setType(Constants.PRODUCT_VALUE_RATING_TYPE);
            productService.saveOrUpdateProductBrandCustomerRate(productCustomerRate);
        }

        if (StringUtils.isNotEmpty(customerCommentForm.getComment())) {
            ProductBrandCustomerComment productCustomerComment = new ProductBrandCustomerComment();
            productCustomerComment.setComment(customerCommentForm.getComment());
            productCustomerComment.setTitle(customerCommentForm.getTitle());
            productCustomerComment.setProductBrand(productBrand);
            productCustomerComment.setCustomer(customer);
            productCustomerComment.setMarketAreaId(currentMarketArea.getId());
            productService.saveOrUpdateProductBrandCustomerComment(productCustomerComment);
        }

        addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.COMMENT_VOTE, "comment_success_message", locale));

        final String urlRedirect = requestUtil.getLastProductDetailsRequestUrl(request);
        return new ModelAndView(new RedirectView(urlRedirect));
    }
	
    protected ModelAndView getModelAndView(final HttpServletRequest request) throws Exception{
        return new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PRODUCT_COMMENT.getVelocityPage());
    }
    
}