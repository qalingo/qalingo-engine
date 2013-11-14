/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.CutomerMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ValueBean;

/**
 * 
 */
public abstract class AbstractCustomerController extends AbstractMCommerceController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
    @ModelAttribute(ModelConstants.TITLES)
    public List<ValueBean> getTitles(HttpServletRequest request) throws Exception {
		List<ValueBean> titlesValues = new ArrayList<ValueBean>();
		try {
			final Locale locale = getCurrentLocale(request);
			final Map<String, String> titles = referentialDataService.getTitlesByLocale(locale);
			if(titles != null){
				Set<String> titlesKey = titles.keySet();
				for (Iterator<String> iterator = titlesKey.iterator(); iterator.hasNext();) {
					final String titleKey = (String) iterator.next();
					titlesValues.add(new ValueBean(titleKey.replace(Constants.TITLE_MESSAGE_PREFIX, ""), titles.get(titleKey)));
				}
				Collections.sort(titlesValues, new Comparator<ValueBean>() {
					@Override
					public int compare(ValueBean o1, ValueBean o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				});
			}
		} catch (Exception e) {
			LOG.error("", e);
		}
		return titlesValues;
    }
    
    @ModelAttribute(ModelConstants.COUNTRIES)
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
		List<ValueBean> countriesValues = new ArrayList<ValueBean>();
		try {
			final Locale locale = getCurrentLocale(request);
			final Map<String, String> countries = referentialDataService.getCountriesByLocale(locale);
			if(countries != null){
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
			}
		} catch (Exception e) {
			LOG.error("", e);
		}
		return countriesValues;
    }
    
    @ModelAttribute(ModelConstants.CUSTOMER_DETAILS_LINKS_VIEW_BEAN)
    public List<CutomerMenuViewBean> getCutomerMenus(HttpServletRequest request) throws Exception {
    	return viewBeanFactory.buildCutomerMenuViewBeans(requestUtil.getRequestData(request));
    }

}