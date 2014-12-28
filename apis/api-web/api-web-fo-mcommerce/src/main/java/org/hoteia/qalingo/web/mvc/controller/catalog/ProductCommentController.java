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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import org.hoteia.qalingo.web.mvc.form.CustomerCommentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Controller("productCommentController")
public class ProductCommentController extends AbstractMCommerceController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected ProductService productService;
	
	@RequestMapping(value = FoUrls.PRODUCT_VOTE_URL, method = RequestMethod.GET)
	public ModelAndView displayProductVoteForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productCode,
												final Model model, @ModelAttribute(ModelConstants.CUSTOMER_COMMENT_FORM) CustomerCommentForm customerCommentForm) throws Exception {
        return displayProductCommentForm(request, productCode, model, customerCommentForm);
	}
	
	@RequestMapping(value = FoUrls.PRODUCT_COMMENT_URL, method = RequestMethod.GET)
	public ModelAndView displayProductCommentForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productCode,
												   final Model model, @ModelAttribute(ModelConstants.CUSTOMER_COMMENT_FORM) CustomerCommentForm customerCommentForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = (ModelAndViewThemeDevice) getModelAndView(request);
		
		model.addAttribute(ModelConstants.URL_BACK, requestUtil.getLastRequestUrl(request));
		
		// STAR
		String qualityOfServiceMax = "5";
		EngineSetting engineSetting = engineSettingService.getSettingProductMaxScoreValue();
		if(engineSetting != null){
			qualityOfServiceMax = engineSetting.getDefaultValue();
			EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_STAR_SCORE_MAX);
			if(engineSettingValue != null){
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
		if(engineSetting != null){
			priceMax = engineSetting.getDefaultValue();
			EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_STAR_SCORE_MAX);
			if(engineSettingValue != null){
				priceMax = engineSettingValue.getValue();
			}
		}

		try {
			model.addAttribute(ModelConstants.PRICE_SCORE_MAX, Integer.parseInt(priceMax));
        } catch (Exception e) {
    		model.addAttribute(ModelConstants.PRICE_SCORE_MAX, 5);
        }
		
        final RequestData requestData = requestUtil.getRequestData(request);
        
		ProductMarketing productMarketing = productService.getProductMarketingByCode(productCode);
		
		if(customerCommentForm == null 
	    		|| customerCommentForm.equals(new CustomerCommentForm())){
			customerCommentForm = formFactory.buildCustomerCommentForm(requestData, productMarketing.getCode());
			model.addAttribute(ModelConstants.CUSTOMER_COMMENT_FORM, customerCommentForm);
		}
		
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.PRODUCT_COMMENT_URL, method = RequestMethod.POST)
	public ModelAndView submitProductComment(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productCode,
											 @Valid @ModelAttribute(ModelConstants.CUSTOMER_COMMENT_FORM) CustomerCommentForm customerCommentForm,
								             BindingResult result, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Market currentMarket = requestData.getMarket();
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();
        Customer customer = requestData.getCustomer();
        
        //binding form
//      	bindProductCommentForm(request, customerCommentForm);
		
		if (result.hasErrors()) {
			return displayProductCommentForm(request, productCode, model, customerCommentForm);
		}
		
        if (customer == null) {
            if(StringUtils.isNotEmpty(customerCommentForm.getName())
                    && StringUtils.isNotEmpty(customerCommentForm.getEmail())){
                // CHECK IF THE CUSTOMER EXIST
                final Customer customerCheck = customerService.getCustomerByLoginOrEmail(customerCommentForm.getEmail());
                if(customerCheck == null){
                 // CREATE A LIGHT ACCOUNT
                    CreateAccountForm createAccountForm = new CreateAccountForm();
                    createAccountForm.setEmail(customerCommentForm.getEmail());
                    createAccountForm.setLastname(customerCommentForm.getName());
                    customer = webManagementService.buildAndSaveQuickNewCustomer(requestData, currentMarket, currentMarketArea, createAccountForm);
                    // Save the email confirmation
                    webManagementService.buildAndSaveCustomerNewAccountMail(requestData, createAccountForm);
                    // Login the new customer
                    securityRequestUtil.authenticationCustomer(request, customer);
                } else {
                    // WARNING
                    addErrorMessage(request, getSpecificMessage(ScopeWebMessage.COMMENT_VOTE, "customer_must_be_logged",  locale));
                    return displayProductCommentForm(request, productCode, model, customerCommentForm);
                }
            } else {
                // WARNING
                addErrorMessage(request, getSpecificMessage(ScopeWebMessage.COMMENT_VOTE, "customer_must_be_logged",  locale));
                return displayProductCommentForm(request, productCode, model, customerCommentForm);
            }
        }
		
		int qualityOfService = customerCommentForm.getQualityOfService();
		int ratioQualityPrice = customerCommentForm.getRatioQualityPrice();
		int priceScore = customerCommentForm.getPriceScore();
		
		if (StringUtils.isEmpty(customerCommentForm.getComment())
				&& qualityOfService == 0 
				&& ratioQualityPrice == 0 
				&& priceScore == 0) {
			// WARNING
		    addErrorMessage(request, getSpecificMessage(ScopeWebMessage.COMMENT_VOTE, "message_cant_be_empty",  locale));
			return displayProductCommentForm(request, productCode, model, customerCommentForm);
		}
		
		final ProductMarketing productMarketing = productService.getProductMarketingByCode(productCode);
		
		if (qualityOfService != 0) {
			ProductMarketingCustomerRate productCustomerRate = new ProductMarketingCustomerRate();
			productCustomerRate.setRate(qualityOfService);
			productCustomerRate.setProductMarketingId(productMarketing.getId());
			productCustomerRate.setCustomerId(customer.getId());
			productCustomerRate.setType(Constants.PRODUCT_QUALITY_RATING_TYPE);
			productService.saveOrUpdateProductMarketingCustomerRate(productCustomerRate);
		}
		
		if (ratioQualityPrice != 0) {
			ProductMarketingCustomerRate productCustomerRate = new ProductMarketingCustomerRate();
			productCustomerRate.setRate(ratioQualityPrice);
			productCustomerRate.setProductMarketingId(productMarketing.getId());
			productCustomerRate.setCustomerId(customer.getId());
			productCustomerRate.setType(Constants.PRODUCT_PRICE_RATING_TYPE);
			productService.saveOrUpdateProductMarketingCustomerRate(productCustomerRate);
		}
		
		if (priceScore != 0) {
			ProductMarketingCustomerRate productCustomerRate = new ProductMarketingCustomerRate();
			productCustomerRate.setRate(priceScore);
			productCustomerRate.setProductMarketingId(productMarketing.getId());
			productCustomerRate.setCustomerId(customer.getId());
			productCustomerRate.setType(Constants.PRODUCT_VALUE_RATING_TYPE);
			productService.saveOrUpdateProductMarketingCustomerRate(productCustomerRate);
		}
		
		if (StringUtils.isNotEmpty(customerCommentForm.getComment())) {
			ProductMarketingCustomerComment productCustomerComment = new ProductMarketingCustomerComment();
			productCustomerComment.setComment(customerCommentForm.getComment());
            productCustomerComment.setTitle(customerCommentForm.getTitle());
			productCustomerComment.setProductMarketingId(productMarketing.getId());
			productCustomerComment.setCustomer(customer);
            productCustomerComment.setMarketAreaId(currentMarketArea.getId());
			productService.saveOrUpdateProductMarketingCustomerComment(productCustomerComment);
		}
		
		addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.COMMENT_VOTE, "comment_success_message",  locale));
		
		final String urlRedirect = requestUtil.getLastProductDetailsRequestUrl(request);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	protected ModelAndView getModelAndView(final HttpServletRequest request) throws Exception{
	    return new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PRODUCT_COMMENT.getVelocityPage());
	}
	
//	//TODO: refactor it and find why the bean form cannot be binded?
//	private void bindProductCommentForm(final HttpServletRequest request, final CustomerCommentForm customerCommentForm){
//		if(request == null || customerCommentForm == null){
//			return;
//		}
//		
//		String productCode = request.getParameter("customerCommentForm.productCode");
//		String qualityOfService = request.getParameter("customerCommentForm.qualityOfService");
//		String ratioQualityPrice = request.getParameter("customerCommentForm.ratioQualityPrice");
//		String priceScore = request.getParameter("customerCommentForm.priceScore");
//		String comment = request.getParameter("customerCommentForm.comment");
//		
//		customerCommentForm.setComment(comment);
//		customerCommentForm.setPriceScore(priceScore);
//		customerCommentForm.setQualityOfService(qualityOfService);
//		customerCommentForm.setRatioQualityPrice(ratioQualityPrice);
//		customerCommentForm.setObjectCode(productCode);
//	}
	
}