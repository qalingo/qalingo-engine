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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Retailer_;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.CustomerContactForm;
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
@Controller("retailerContactController")
public class RetailerContactController extends AbstractMCommerceController {

	@Autowired
	protected RetailerService retailerService;
	
    protected List<SpecificFetchMode> retailerFetchPlans = new ArrayList<SpecificFetchMode>();;

    public RetailerContactController() {
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.attributes.getName()));
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.assets.getName()));
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.stores.getName()));
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.addresses.getName()));
    }
        
	@RequestMapping(value = FoUrls.RETAILER_CONTACT_URL, method = RequestMethod.GET)
	public ModelAndView displayContactForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode,
										   Model model, @ModelAttribute("customerContactForm") CustomerContactForm customerContactForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_CONTACT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
		
		modelAndView.addObject(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
		Retailer retailer = retailerService.getRetailerByCode(retailerCode, new FetchPlan(retailerFetchPlans));

		// SANITY CHECK
		if(retailer.getDefaultAddress() == null
				|| StringUtils.isEmpty(retailer.getDefaultAddress().getEmail())){
			final String url = urlService.generateRedirectUrl(FoUrls.RETAILER_DETAILS, requestUtil.getRequestData(request), retailer);
	        return new ModelAndView(new RedirectView(url));
		}

		RetailerViewBean retailerViewBean = frontofficeViewBeanFactory.buildViewBeanRetailer(requestUtil.getRequestData(request), retailer);
		model.addAttribute(ModelConstants.RETAILER_VIEW_BEAN, retailerViewBean);
		
		if(customerContactForm == null 
        		|| customerContactForm.equals(new CustomerContactForm())){
			customerContactForm = formFactory.buildCustomerContactForm(requestData, retailer.getCode());
			model.addAttribute("customerContactForm", customerContactForm);
		}
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.RETAILER_CONTACT.getKey());
		
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.RETAILER_CONTACT_URL, method = RequestMethod.POST)
	public ModelAndView submitContact(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode,
									  @Valid @ModelAttribute("customerContactForm") CustomerContactForm customerContactForm,
								BindingResult result, Model model) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_CONTACT_SUCCESS_VELOCITY_PAGE);

		if (result.hasErrors()) {
			return displayContactForm(request, retailerCode, model, customerContactForm);
		}

		webManagementService.buildAndSaveRetailerContactMail(requestUtil.getRequestData(request), customerContactForm);

        return modelAndView;
	}
	
    @ModelAttribute(ModelConstants.COUNTRIES)
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        return getCountries(requestData);
    }
}