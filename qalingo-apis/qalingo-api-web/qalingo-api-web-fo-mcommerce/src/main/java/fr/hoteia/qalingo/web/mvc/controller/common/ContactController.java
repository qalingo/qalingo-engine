/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.form.ContactUsForm;
import fr.hoteia.qalingo.web.mvc.viewbean.ValueBean;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller
public class ContactController extends AbstractMCommerceController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping(value = "/contact.html*", method = RequestMethod.GET)
	public ModelAndView displayContactForm(final HttpServletRequest request, Model model,  @ModelAttribute("contactUsForm") ContactUsForm contactUsForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "contact/contact-form");
		
		// "contactus";

		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);

		modelAndView.addObject(ModelConstants.URL_BACK, urlService.buildHomeUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer));
		
        return modelAndView;
	}

	@RequestMapping(value = "/contact.html*", method = RequestMethod.POST)
	public ModelAndView submitContact(final HttpServletRequest request, @Valid  @ModelAttribute("contactUsForm") ContactUsForm contactUsForm,
								BindingResult result, Model model) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "contact/contact-success");

		// "contactus";
		
		if (result.hasErrors()) {
			return displayContactForm(request, model, contactUsForm);
		}
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);

		modelAndView.addObject(ModelConstants.URL_BACK, urlService.buildHomeUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer));

		webCommerceService.buildAndSaveContactUsMail(request, contactUsForm);

        return modelAndView;
	}
	
	/**
	 * 
	 */
    @ModelAttribute("contactUsForm")
	protected ContactUsForm getContactUsForm(final HttpServletRequest request, final Model model) throws Exception {
    	return formFactory.buildContactUsForm(request);
	}
    
    @ModelAttribute("countries")
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
		List<ValueBean> countriesValues = new ArrayList<ValueBean>();
		try {
			final Locale locale = getCurrentLocale(request);
			final ResourceBundle countriesResourceBundle = ResourceBundle.getBundle(Constants.COUNTRIES_RESOURCE_BUNDLE, locale);
			Set<String> countriesKey = countriesResourceBundle.keySet();
			
			for (Iterator<String> iterator = countriesKey.iterator(); iterator.hasNext();) {
				final String countryKey = (String) iterator.next();
				countriesValues.add(new ValueBean(countryKey.replace("countries.", ""), (String)countriesResourceBundle.getObject(countryKey)));
			}
			
			Collections.sort(countriesValues, new Comparator<ValueBean>() {
				@Override
				public int compare(ValueBean o1, ValueBean o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			});
		} catch (Exception e) {
			LOG.error("", e);
		}
		return countriesValues;
    }
    
}