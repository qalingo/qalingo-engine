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
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Store_;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
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
@Controller("storeContactController")
public class StoreContactController extends AbstractMCommerceController {

	@Autowired
	protected RetailerService retailerService;
	
    protected List<SpecificFetchMode> storeFetchPlans = new ArrayList<SpecificFetchMode>();;

    public StoreContactController() {
        storeFetchPlans.add(new SpecificFetchMode(Store_.attributes.getName()));
        storeFetchPlans.add(new SpecificFetchMode(Store_.assets.getName()));
    }

	@RequestMapping(value = FoUrls.STORE_CONTACT_URL, method = RequestMethod.GET)
	public ModelAndView displayContactForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_STORE_CODE) final String storeCode,
										   Model model, @ModelAttribute(ModelConstants.CUSTOMER_COMMENT_FORM) CustomerContactForm customerContactForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.STORE_CONTACT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
		
		modelAndView.addObject(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
		Store store = retailerService.getStoreByCode(storeCode, new FetchPlan(storeFetchPlans));

		StoreViewBean storeViewBean = frontofficeViewBeanFactory.buildViewBeanStore(requestUtil.getRequestData(request), store);
		model.addAttribute(ModelConstants.STORE_VIEW_BEAN, storeViewBean);
		
		if(customerContactForm == null 
        		|| customerContactForm.equals(new CustomerContactForm())){
			customerContactForm = formFactory.buildCustomerContactForm(requestData, store.getCode());
			model.addAttribute("customerContactForm", customerContactForm);
		}
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.RETAILER_CONTACT.getKey());
		
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.RETAILER_CONTACT_URL, method = RequestMethod.POST)
	public ModelAndView submitContact(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String storeCode,
									  @Valid @ModelAttribute("customerContactForm") CustomerContactForm customerContactForm,
								BindingResult result, Model model) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_CONTACT_SUCCESS_VELOCITY_PAGE);

		if (result.hasErrors()) {
			return displayContactForm(request, storeCode, model, customerContactForm);
		}

		webManagementService.buildAndSaveStoreContactMail(requestUtil.getRequestData(request), customerContactForm);

        return modelAndView;
	}
	
    @ModelAttribute(ModelConstants.COUNTRIES)
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        return getCountries(requestData);
    }
}