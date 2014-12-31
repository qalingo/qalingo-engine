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

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Retailer_;
import org.hoteia.qalingo.core.domain.Store_;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
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
	
	protected List<SpecificFetchMode> retailerFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> storeFetchPlans = new ArrayList<SpecificFetchMode>();
    
	public RetailerController() {
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.attributes.getName()));
	    retailerFetchPlans.add(new SpecificFetchMode(Retailer_.assets.getName()));
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.stores.getName()));
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.addresses.getName()));
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.company.getName()));
        
        storeFetchPlans.add(new SpecificFetchMode(Store_.attributes.getName()));
        storeFetchPlans.add(new SpecificFetchMode(Store_.assets.getName()));
    }
	
	@RequestMapping(FoUrls.RETAILER_DETAILS_URL)
	public ModelAndView displayRetailerDetails(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

		Retailer retailer = retailerService.getRetailerByCode(retailerCode, new FetchPlan(retailerFetchPlans));
		
		RetailerViewBean retailerViewBean = frontofficeViewBeanFactory.buildViewBeanRetailer(requestUtil.getRequestData(request), retailer);
		model.addAttribute(ModelConstants.RETAILER_VIEW_BEAN, retailerViewBean);
		
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
		
		model.addAttribute("urlRetailerContact", urlService.generateUrl(FoUrls.RETAILER_CONTACT, requestData, retailer));
		model.addAttribute("urlRetailerVote", urlService.generateUrl(FoUrls.RETAILER_VOTE, requestData, retailer));
		model.addAttribute("urlRetailerComment", urlService.generateUrl(FoUrls.RETAILER_COMMENT, requestData, retailer));
		
		model.addAttribute("withMap", true);
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.RETAILER_DETAILS.getKey());

        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData, retailer));

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
		
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.RETAILER_CREATE.getKey());

        return modelAndView;
	}
	
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData, Retailer retailer) {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final Locale locale = requestData.getLocale();
        Object[] params = { retailer.getI18nName(localizationCode) };

        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, "retailer_details", params, locale));

        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
        MenuViewBean menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "store_location", locale));
        menu.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "retailer_details", params, locale));
        menu.setUrl(urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestData, retailer));
        menu.setActive(true);
        menuViewBeans.add(menu);

        breadcrumbViewBean.setMenus(menuViewBeans);
        return breadcrumbViewBean;
    }
    
	/**
	 * 
	 */
    @ModelAttribute("retailerCreateForm")
	protected RetailerCreateForm getRetailerCreateForm(final HttpServletRequest request, final Model model) throws Exception {
    	return new RetailerCreateForm();
	}
    
}