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
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.ProductCommentForm;
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
												final Model model, @ModelAttribute(ModelConstants.PRODUCT_COMMENT_FORM) ProductCommentForm productCommentForm) throws Exception {
        return displayProductCommentForm(request, productCode, model, productCommentForm);
	}
	
	@RequestMapping(value = FoUrls.PRODUCT_COMMENT_URL, method = RequestMethod.GET)
	public ModelAndView displayProductCommentForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productCode,
												   final Model model, @ModelAttribute(ModelConstants.PRODUCT_COMMENT_FORM) ProductCommentForm productCommentForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PRODUCT_COMMENT.getVelocityPage());
		
		model.addAttribute(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
		// STAR
		String qualityOfServiceMax = "5";
		EngineSetting engineSetting = engineSettingService.getProductMaxScoreValue();
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
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
        
		ProductMarketing productMarketing = productService.getProductMarketingByCode(productCode);
		
		if(productCommentForm == null 
	    		|| productCommentForm.equals(new ProductCommentForm())){
			productCommentForm = formFactory.buildProductCommentForm(requestData, productMarketing);
			model.addAttribute("productContactForm", productCommentForm);
		}
		
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.PRODUCT_COMMENT_URL, method = RequestMethod.POST)
	public ModelAndView submitProductComment(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE) final String productCode,
											 @Valid @ModelAttribute(ModelConstants.PRODUCT_COMMENT_FORM) ProductCommentForm productCommentForm,
								BindingResult result, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
        final Locale locale = requestData.getLocale();
        
        //binding form
      	bindProductCommentForm(request, productCommentForm);
		
		if (result.hasErrors()) {
			return displayProductCommentForm(request, productCode, model, productCommentForm);
		}
		
		int qualityOfService = 0;
		int ratioQualityPrice = 0;
		int priceScore = 0;
		
		try {
			qualityOfService = Integer.parseInt(productCommentForm.getQualityOfService());
        } catch (Exception e) {
        	logger.warn("Product Vote qualityOfService value can't be parse: " + productCommentForm.getQualityOfService());
        }
		
		try {
			ratioQualityPrice = Integer.parseInt(productCommentForm.getRatioQualityPrice());
        } catch (Exception e) {
        	logger.warn("Product Vote ratioQualityPrice value can't be parse: " + productCommentForm.getRatioQualityPrice());
        }
		
		try {
			priceScore = Integer.parseInt(productCommentForm.getPriceScore());
        } catch (Exception e) {
        	logger.warn("Product Vote priceScore value can't be parse: " + productCommentForm.getPriceScore());
        }
		
		if (StringUtils.isEmpty(productCommentForm.getComment())
				&& qualityOfService == 0 
				&& ratioQualityPrice == 0 
				&& priceScore == 0) {
			// WARNING
			addInfoMessage(request, getSpecificMessage(ScopeWebMessage.PRODUCT_MARKETING, "comment_form_empty_warning_message",  locale));
			return displayProductCommentForm(request, productCode, model, productCommentForm);
		}
		
		final ProductMarketing product = productService.getProductMarketingByCode(productCode);
		final Customer customer = requestData.getCustomer();
		
		if (qualityOfService != 0) {
			ProductMarketingCustomerRate productCustomerRate = new ProductMarketingCustomerRate();
			productCustomerRate.setRate(qualityOfService);
			productCustomerRate.setProductMarketingId(product.getId());
			productCustomerRate.setCustomerId(customer.getId());
			productCustomerRate.setType(Constants.PRODUCT_QUALITY_RATING_TYPE);
			productService.saveOrUpdateProductMarketingCustomerRate(productCustomerRate);
		}
		
		if (ratioQualityPrice != 0) {
			ProductMarketingCustomerRate productCustomerRate = new ProductMarketingCustomerRate();
			productCustomerRate.setRate(ratioQualityPrice);
			productCustomerRate.setProductMarketingId(product.getId());
			productCustomerRate.setCustomerId(customer.getId());
			productCustomerRate.setType(Constants.PRODUCT_PRICE_RATING_TYPE);
			productService.saveOrUpdateProductMarketingCustomerRate(productCustomerRate);
		}
		
		if (priceScore != 0) {
			ProductMarketingCustomerRate productCustomerRate = new ProductMarketingCustomerRate();
			productCustomerRate.setRate(priceScore);
			productCustomerRate.setProductMarketingId(product.getId());
			productCustomerRate.setCustomerId(customer.getId());
			productCustomerRate.setType(Constants.PRODUCT_VALUE_RATING_TYPE);
			productService.saveOrUpdateProductMarketingCustomerRate(productCustomerRate);
		}
		
		if (StringUtils.isNotEmpty(productCommentForm.getComment())) {
			ProductMarketingCustomerComment productCustomerComment = new ProductMarketingCustomerComment();
			productCustomerComment.setComment(productCommentForm.getComment());
			productCustomerComment.setProductMarketingId(product.getId());
			productCustomerComment.setCustomer(customer);
			productService.saveOrUpdateProductMarketingCustomerComment(productCustomerComment);
		}
		
		addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.PRODUCT_MARKETING, "comment_form_success_message",  locale));
		
		final String urlRedirect = urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestUtil.getRequestData(request), 
														product.getDefaultCatalogCategory(), product, product.getDefaultProductSku());
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	//TODO: refactor it and find why the bean form cannot be binded?
	private void bindProductCommentForm(final HttpServletRequest request, final ProductCommentForm productCommentForm){
		if(request == null || productCommentForm == null){
			return;
		}
		
		String productCode = request.getParameter("productCommentForm.productCode");
		String qualityOfService = request.getParameter("productCommentForm.qualityOfService");
		String ratioQualityPrice = request.getParameter("productCommentForm.ratioQualityPrice");
		String priceScore = request.getParameter("productCommentForm.priceScore");
		String comment = request.getParameter("productCommentForm.comment");
		
		productCommentForm.setComment(comment);
		productCommentForm.setPriceScore(priceScore);
		productCommentForm.setQualityOfService(qualityOfService);
		productCommentForm.setRatioQualityPrice(ratioQualityPrice);
		productCommentForm.setProductCode(productCode);
	}
	
}