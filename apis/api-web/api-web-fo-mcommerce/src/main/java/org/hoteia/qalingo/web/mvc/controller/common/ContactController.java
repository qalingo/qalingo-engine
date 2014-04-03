/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.common;

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

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.ContactForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("contactController")
public class ContactController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = FoUrls.CONTACT_URL, method = RequestMethod.GET)
	public ModelAndView displayContactForm(final HttpServletRequest request, Model model, @ModelAttribute("contactForm") ContactForm contactForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CONTACT.getVelocityPage());
		
		modelAndView.addObject(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.CONTACT.getKey());

        return modelAndView;
	}

	@RequestMapping(value = FoUrls.CONTACT_URL, method = RequestMethod.POST)
	public ModelAndView submitContact(final HttpServletRequest request, @Valid @ModelAttribute("contactForm") ContactForm contactForm,
								BindingResult result, Model model) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "contact/contact-success");

		if (result.hasErrors()) {
			return displayContactForm(request, model, contactForm);
		}

		modelAndView.addObject(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));

		try {
			webManagementService.buildAndSaveContactMail(requestUtil.getRequestData(request), contactForm);
	        
        } catch (Exception e) {
        	logger.error("Can't send contact email!", e);
	        displayContactForm(request, model, contactForm);
        }
		
        return modelAndView;
	}
	
	/**
	 * 
	 */
    @ModelAttribute(ModelConstants.CONTACT_FORM)
	protected ContactForm getContactForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
    	return formFactory.buildContactForm(requestData);
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