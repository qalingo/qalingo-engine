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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.RetailerCreateForm;
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
@Controller("retailerController")
public class RetailerController extends AbstractMCommerceController {

	@Autowired
	protected RetailerService retailerService;
	
	@RequestMapping(FoUrls.RETAILER_DETAILS_URL)
	public ModelAndView displayRetailerDetails(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();

		Retailer retailer = retailerService.getRetailerByCode(currentMarketArea.getId(), currentRetailer.getId(), retailerCode);
		
		RetailerViewBean retailerViewBean = frontofficeViewBeanFactory.buildViewBeanRetailer(requestUtil.getRequestData(request), retailer);
		model.addAttribute(ModelConstants.RETAILER_VIEW_BEAN, retailerViewBean);
		
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
		
		model.addAttribute("urlRetailerContact", urlService.generateUrl(FoUrls.RETAILER_CONTACT, requestData, retailer));
		model.addAttribute("urlRetailerVote", urlService.generateUrl(FoUrls.RETAILER_VOTE, requestData, retailer));
		model.addAttribute("urlRetailerComment", urlService.generateUrl(FoUrls.RETAILER_COMMENT, requestData, retailer));
		
		model.addAttribute("withMap", true);
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.RETAILER_DETAILS.getKey());

        return modelAndView;
	}
    
	@RequestMapping(value = FoUrls.RETAILER_CREATE_URL, method = RequestMethod.GET)
	public ModelAndView displayRetailerCreateForm(final HttpServletRequest request, final Model model, @ModelAttribute("retailerCreateForm") RetailerCreateForm retailerCreateForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_CREATE.getVelocityPage());
		
		model.addAttribute(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.RETAILER_CREATE_URL, method = RequestMethod.POST)
	public ModelAndView submitRetailerCreate(final HttpServletRequest request, @Valid @ModelAttribute("retailerCreateForm") RetailerCreateForm retailerCreateForm,
								BindingResult result, final Model model) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = (ModelAndViewThemeDevice) displayRetailerCreateForm(request, model, retailerCreateForm);

		if (result.hasErrors()) {
			return displayRetailerCreateForm(request, model, retailerCreateForm);
		}
		
		model.addAttribute(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));

		// CREATE
		
		
		
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.RETAILER_CREATE.getKey());

        return modelAndView;
	}
	
	/**
	 * 
	 */
    @ModelAttribute("retailerCreateForm")
	protected RetailerCreateForm getRetailerCreateForm(final HttpServletRequest request, final Model model) throws Exception {
    	return new RetailerCreateForm();
	}
    
}