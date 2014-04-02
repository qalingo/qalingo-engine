/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.retailer;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.RetailerCommentForm;
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
@Controller("retailerCommentController")
public class RetailerCommentController extends AbstractMCommerceController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected RetailerService retailerService;
	
	@RequestMapping(value = FoUrls.RETAILER_VOTE_URL, method = RequestMethod.GET)
	public ModelAndView displayRetailerVoteForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode,
												final Model model, @ModelAttribute("retailerCommentForm") RetailerCommentForm retailerCommentForm) throws Exception {
        return displayRetailerCommentForm(request, retailerCode, model, retailerCommentForm);
	}
	
	@RequestMapping(value = FoUrls.RETAILER_COMMENT_URL, method = RequestMethod.GET)
	public ModelAndView displayRetailerCommentForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode,
												   final Model model, @ModelAttribute("retailerCommentForm") RetailerCommentForm retailerCommentForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_COMMENT.getVelocityPage());
		
		model.addAttribute(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
		// STAR
		String qualityOfServiceMax = "5";
		EngineSetting engineSetting = engineSettingService.getRetailerMaxScoreValue();
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
		
		Retailer retailer = retailerService.getRetailerByCode(currentMarketArea.getId(), currentRetailer.getId(), retailerCode);
		
		if(retailerCommentForm == null 
	    		|| retailerCommentForm.equals(new RetailerCommentForm())){
			retailerCommentForm = formFactory.buildRetailerCommentForm(requestData, retailer);
			model.addAttribute("retailerContactForm", retailerCommentForm);
		}
		
		model.addAttribute("urlSubmit", urlService.generateUrl(FoUrls.RETAILER_COMMENT, requestData, retailer));
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.RETAILER_COMMENT.getKey());

        return modelAndView;
	}

	@RequestMapping(value = FoUrls.RETAILER_COMMENT_URL, method = RequestMethod.POST)
	public ModelAndView submitRetailerComment(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode,
											  @Valid @ModelAttribute("retailerCommentForm") RetailerCommentForm retailerCommentForm,
								BindingResult result, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
        final Locale locale = requestData.getLocale();
		
		if (result.hasErrors()) {
			return displayRetailerCommentForm(request, retailerCode, model, retailerCommentForm);
		}
		
		int qualityOfService = 0;
		int ratioQualityPrice = 0;
		int priceScore = 0;
		
		try {
			qualityOfService = Integer.parseInt(retailerCommentForm.getQualityOfService());
        } catch (Exception e) {
        	logger.warn("Retailer Vote qualityOfService value can't be parse: " + retailerCommentForm.getQualityOfService());
        }
		
		try {
			ratioQualityPrice = Integer.parseInt(retailerCommentForm.getRatioQualityPrice());
        } catch (Exception e) {
        	logger.warn("Retailer Vote ratioQualityPrice value can't be parse: " + retailerCommentForm.getRatioQualityPrice());
        }
		
		try {
			priceScore = Integer.parseInt(retailerCommentForm.getPriceScore());
        } catch (Exception e) {
        	logger.warn("Retailer Vote priceScore value can't be parse: " + retailerCommentForm.getPriceScore());
        }
		
		if (StringUtils.isEmpty(retailerCommentForm.getComment())
				&& qualityOfService == 0 
				&& ratioQualityPrice == 0 
				&& priceScore == 0) {
			// WARNING
			addInfoMessage(request, getSpecificMessage(ScopeWebMessage.RETAILER, "comment_form_empty_warning_message",  locale));
			return displayRetailerCommentForm(request, retailerCode, model, retailerCommentForm);
		}
		
		final Retailer retailer = retailerService.getRetailerByCode(currentMarketArea.getId(), currentRetailer.getId(), retailerCode);
		final Customer customer = requestData.getCustomer();
		
		if (qualityOfService != 0) {
			RetailerCustomerRate retailerCustomerRate = new RetailerCustomerRate();
			retailerCustomerRate.setRate(qualityOfService);
			retailerCustomerRate.setRetailerId(retailer.getId());
			retailerCustomerRate.setCustomerId(customer.getId());
			retailerCustomerRate.setType("QUALITY_OF_SERVICE");
			retailerService.saveOrUpdateRetailerCustomerRate(retailerCustomerRate);
		}
		
		if (ratioQualityPrice != 0) {
			RetailerCustomerRate retailerCustomerRate = new RetailerCustomerRate();
			retailerCustomerRate.setRate(ratioQualityPrice);
			retailerCustomerRate.setRetailerId(retailer.getId());
			retailerCustomerRate.setCustomerId(customer.getId());
			retailerCustomerRate.setType("RATIO_QUALITY_PRICE");
			retailerService.saveOrUpdateRetailerCustomerRate(retailerCustomerRate);
		}
		
		if (priceScore != 0) {
			RetailerCustomerRate retailerCustomerRate = new RetailerCustomerRate();
			retailerCustomerRate.setRate(priceScore);
			retailerCustomerRate.setRetailerId(retailer.getId());
			retailerCustomerRate.setCustomerId(customer.getId());
			retailerCustomerRate.setType("PRICE_SCORE");
			retailerService.saveOrUpdateRetailerCustomerRate(retailerCustomerRate);
		}
		
		if (StringUtils.isNotEmpty(retailerCommentForm.getComment())) {
			RetailerCustomerComment retailerCustomerComment = new RetailerCustomerComment();
			retailerCustomerComment.setComment(retailerCommentForm.getComment());
			retailerCustomerComment.setRetailerId(retailer.getId());
			retailerCustomerComment.setCustomer(customer);
			retailerService.saveOrUpdateRetailerCustomerComment(retailerCustomerComment);
		}
		
		addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.RETAILER, "comment_form_success_message",  locale));
		
		final String urlRedirect = urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestUtil.getRequestData(request), retailer);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
}