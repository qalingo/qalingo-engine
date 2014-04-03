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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.RetailerContactForm;
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
	
	@RequestMapping(value = FoUrls.RETAILER_CONTACT_URL, method = RequestMethod.GET)
	public ModelAndView displayContactForm(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode,
										   Model model, @ModelAttribute("retailerContactForm") RetailerContactForm retailerContactForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_CONTACT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
		
		modelAndView.addObject(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
		Retailer retailer = retailerService.getRetailerByCode(currentMarketArea.getId(), currentRetailer.getId(), retailerCode);

		// SANITY CHECK
		if(retailer.getDefaultAddress() == null
				|| StringUtils.isEmpty(retailer.getDefaultAddress().getEmail())){
			final String url = urlService.generateUrl(FoUrls.RETAILER_DETAILS, requestUtil.getRequestData(request), retailer);
	        return new ModelAndView(new RedirectView(url));
		}

		RetailerViewBean retailerViewBean = frontofficeViewBeanFactory.buildViewBeanRetailer(requestUtil.getRequestData(request), retailer);
		model.addAttribute(ModelConstants.RETAILER_VIEW_BEAN, retailerViewBean);
		
		if(retailerContactForm == null 
        		|| retailerContactForm.equals(new RetailerContactForm())){
			retailerContactForm = formFactory.buildRetailerContactForm(requestData, retailer);
			model.addAttribute("retailerContactForm", retailerContactForm);
		}
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.RETAILER_CONTACT.getKey());
		
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.RETAILER_CONTACT_URL, method = RequestMethod.POST)
	public ModelAndView submitContact(final HttpServletRequest request, @PathVariable(RequestConstants.URL_PATTERN_RETAILER_CODE) final String retailerCode,
									  @Valid @ModelAttribute("retailerContactForm") RetailerContactForm retailerContactForm,
								BindingResult result, Model model) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RETAILER_CONTACT_SUCCESS_VELOCITY_PAGE);

		if (result.hasErrors()) {
			return displayContactForm(request, retailerCode, model, retailerContactForm);
		}

		webManagementService.buildAndSaveRetailerContactMail(requestUtil.getRequestData(request), retailerContactForm);

        return modelAndView;
	}
	
    @ModelAttribute(ModelConstants.COUNTRIES)
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
		List<ValueBean> countriesValues = new ArrayList<ValueBean>();
		try {
	        final RequestData requestData = requestUtil.getRequestData(request);
	        final Locale locale = requestData.getLocale();
	        
			final Map<String, String> countries = referentialDataService.getCountriesByLocale(locale);
			Set<String> countriesKey = countries.keySet();
			for (Iterator<String> iterator = countriesKey.iterator(); iterator.hasNext();) {
				final String countryKey = (String) iterator.next();
				countriesValues.add(new ValueBean(countryKey.replace(Constants.COUNTRY_MESSAGE_PREFIX, ""), countries.get(countryKey)));
			}
			Collections.sort(countriesValues, new Comparator<ValueBean>() {
				@Override
				public int compare(ValueBean o1, ValueBean o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			});
		} catch (Exception e) {
		    logger.error("", e);
		}
		return countriesValues;
    }
}