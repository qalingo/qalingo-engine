/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.retailer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Store_;
import org.hoteia.qalingo.core.domain.StoreCustomerComment;
import org.hoteia.qalingo.core.domain.StoreCustomerRate;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
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
@Controller("storeCommentController")
public class StoreCommentController extends AbstractMCommerceController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RetailerService retailerService;

    protected List<SpecificFetchMode> storeFetchPlans = new ArrayList<SpecificFetchMode>();;

    public StoreCommentController() {
        storeFetchPlans.add(new SpecificFetchMode(Store_.attributes.getName()));
        storeFetchPlans.add(new SpecificFetchMode(Store_.assets.getName()));
    }
	    
	@RequestMapping(value = FoUrls.STORE_VOTE_URL, method = RequestMethod.GET)
	public ModelAndView displayStoreVoteForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_STORE_CODE) final String storeCode,
												final Model model, @ModelAttribute("customerCommentForm") CustomerCommentForm customerCommentForm) throws Exception {
        return displayCustomerCommentForm(request, storeCode, model, customerCommentForm);
	}
	
	@RequestMapping(value = FoUrls.STORE_COMMENT_URL, method = RequestMethod.GET)
	public ModelAndView displayCustomerCommentForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_STORE_CODE) final String storeCode,
												   final Model model, @ModelAttribute("customerCommentForm") CustomerCommentForm customerCommentForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.STORE_COMMENT.getVelocityPage());
		
		model.addAttribute(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
		// STAR
		String qualityOfServiceMax = "5";
		EngineSetting engineSetting = engineSettingService.getSettingRetailerMaxScoreValue();
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
		
		Store store = retailerService.getStoreByCode(storeCode, new FetchPlan(storeFetchPlans));
		
		if(customerCommentForm == null 
	    		|| customerCommentForm.equals(new CustomerCommentForm())){
			customerCommentForm = formFactory.buildCustomerCommentForm(requestData, store.getCode());
			model.addAttribute(ModelConstants.CUSTOMER_COMMENT_FORM, customerCommentForm);
		}
		
		model.addAttribute("urlSubmit", urlService.generateUrl(FoUrls.STORE_COMMENT, requestData, store));
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.STORE_COMMENT.getKey());

        return modelAndView;
	}

	@RequestMapping(value = FoUrls.STORE_COMMENT_URL, method = RequestMethod.POST)
	public ModelAndView submitStoreComment(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_STORE_CODE) final String storeCode,
											  @Valid @ModelAttribute("customerCommentForm") CustomerCommentForm customerCommentForm,
								BindingResult result, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
		
		if (result.hasErrors()) {
			return displayCustomerCommentForm(request, storeCode, model, customerCommentForm);
		}
		
        int qualityOfService = customerCommentForm.getQualityOfService();
        int ratioQualityPrice = customerCommentForm.getRatioQualityPrice();
        int priceScore = customerCommentForm.getPriceScore();
		
		if (StringUtils.isEmpty(customerCommentForm.getComment())
				&& qualityOfService == 0 
				&& ratioQualityPrice == 0 
				&& priceScore == 0) {
			// WARNING
			addInfoMessage(request, getSpecificMessage(ScopeWebMessage.STORE, "comment_form_empty_warning_message",  locale));
			return displayCustomerCommentForm(request, storeCode, model, customerCommentForm);
		}
		
		final Store store = retailerService.getStoreByCode(storeCode, new FetchPlan(storeFetchPlans));
		final Customer customer = requestData.getCustomer();
		
		if (qualityOfService != 0) {
			StoreCustomerRate retailerCustomerRate = new StoreCustomerRate();
			retailerCustomerRate.setRate(qualityOfService);
			retailerCustomerRate.setStoreId(store.getId());
			retailerCustomerRate.setCustomerId(customer.getId());
			retailerCustomerRate.setType("QUALITY_OF_SERVICE");
			retailerService.saveOrUpdateStoreCustomerRate(retailerCustomerRate);
		}
		
		if (ratioQualityPrice != 0) {
			StoreCustomerRate retailerCustomerRate = new StoreCustomerRate();
			retailerCustomerRate.setRate(ratioQualityPrice);
			retailerCustomerRate.setStoreId(store.getId());
			retailerCustomerRate.setCustomerId(customer.getId());
			retailerCustomerRate.setType("RATIO_QUALITY_PRICE");
			retailerService.saveOrUpdateStoreCustomerRate(retailerCustomerRate);
		}
		
		if (priceScore != 0) {
			StoreCustomerRate retailerCustomerRate = new StoreCustomerRate();
			retailerCustomerRate.setRate(priceScore);
			retailerCustomerRate.setStoreId(store.getId());
			retailerCustomerRate.setCustomerId(customer.getId());
			retailerCustomerRate.setType("PRICE_SCORE");
			retailerService.saveOrUpdateStoreCustomerRate(retailerCustomerRate);
		}
		
		if (StringUtils.isNotEmpty(customerCommentForm.getComment())) {
			StoreCustomerComment retailerCustomerComment = new StoreCustomerComment();
			retailerCustomerComment.setComment(customerCommentForm.getComment());
			retailerCustomerComment.setStore(store);
			retailerCustomerComment.setCustomer(customer);
			retailerService.saveOrUpdateStoreCustomerComment(retailerCustomerComment);
		}
		
		addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.STORE, "comment_form_success_message",  locale));
		
		final String urlRedirect = urlService.generateRedirectUrl(FoUrls.STORE_DETAILS, requestUtil.getRequestData(request), store);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
}