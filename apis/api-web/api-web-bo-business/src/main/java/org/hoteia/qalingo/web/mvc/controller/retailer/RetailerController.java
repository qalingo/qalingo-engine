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
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.BoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.WarehouseService;
import org.hoteia.qalingo.core.web.mvc.form.RetailerForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("retailerController")
public class RetailerController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String SESSION_KEY = "PagedListHolder_Retailers";

	@Autowired
	private RetailerService retailerService;
	
	@Autowired
	private WarehouseService warehouseService;

	@RequestMapping(value = BoUrls.RETAILER_LIST_URL, method = RequestMethod.GET)
	public ModelAndView retailerList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RETAILER_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
		final String contentText = getSpecificMessage(ScopeWebMessage.RETAILER, BoMessageKey.MAIN_CONTENT_TEXT, locale);
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		displayList(request, model, requestData, null);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.RETAILER_LIST_URL, method = RequestMethod.POST)
	public ModelAndView searchRetailerList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RETAILER_LIST.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final MarketArea marketArea = requestData.getMarketArea();
		final Retailer retailer = requestData.getMarketAreaRetailer();
		
		final String searchText = request.getParameter(RequestConstants.REQUEST_PARAMETER_SEARCH_TXT);
		if(StringUtils.isNotEmpty(searchText)){
			// DEFAULT WAY - BEFORE INTEGRATE SEARCH WITH SOLR
	        request.getSession().removeAttribute(SESSION_KEY); 
			List<Retailer> retailers = retailerService.findRetailersByText(marketArea.getId(), retailer.getId(), searchText);
			displayList(request, model, requestData, retailers);
		} else {
			return retailerList(request, model);
		}
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.RETAILER_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView retailerDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RETAILER_DETAILS.getVelocityPage());
		
		RetailerViewBean retailerViewBean = initRetailerViewBean(request, model);
		if(retailerViewBean == null){
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
		request.setAttribute(ModelConstants.RETAILER_VIEW_BEAN, retailerViewBean);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.RETAILER_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView retailerEdit(final HttpServletRequest request, final Model model, @ModelAttribute("retailerForm") RetailerForm retailerForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RETAILER_EDIT.getVelocityPage());

		RetailerViewBean retailerViewBean = initRetailerViewBean(request, model);
		if(retailerViewBean == null){
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}

		request.setAttribute(ModelConstants.RETAILER_VIEW_BEAN, retailerViewBean);
		
		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.RETAILER_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitRuleEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute("retailerForm") RetailerForm retailerForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		
		if (result.hasErrors()) {
			return retailerEdit(request, model, retailerForm);
		}
		
		Retailer retailerEdit = new Retailer();
		if(StringUtils.isNotEmpty(retailerForm.getId())){
			retailerEdit = retailerService.getRetailerById(retailerForm.getId());
		}
		
		try {
			// UPDATE RETAILER
			webBackofficeService.createOrUpdateRetailer(retailerEdit, retailerForm);
	        
        } catch (Exception e) {
        	logger.error("Can't save or update Retailer:" + retailerForm.getId() + "/" + retailerForm.getCode(), e);
			return retailerEdit(request, model, retailerForm);
        }
		
		final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.RETAILER_DETAILS, requestData, retailerEdit);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	private void displayList(final HttpServletRequest request, final Model model, final RequestData requestData, List<Retailer> retailers) throws Exception{
		final MarketArea marketArea = requestData.getMarketArea();
		final Retailer retailer = requestData.getMarketAreaRetailer();
		
		String url = request.getRequestURI();
		String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		
		PagedListHolder<RetailerViewBean> RetailerViewBeanPagedListHolder = new PagedListHolder<RetailerViewBean>();

       	if(retailers == null){
    		retailers = retailerService.findRetailers(marketArea.getId(), retailer.getId());
    	}
       	
        if(StringUtils.isEmpty(page)){
        	RetailerViewBeanPagedListHolder = initList(request, SESSION_KEY, requestData, retailers);
    		
        } else {
        	RetailerViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(SESSION_KEY); 
	        if (RetailerViewBeanPagedListHolder == null) { 
	        	RetailerViewBeanPagedListHolder = initList(request, SESSION_KEY, requestData, retailers);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = RetailerViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		RetailerViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
	        		RetailerViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, RetailerViewBeanPagedListHolder);
	}
	
	private PagedListHolder<RetailerViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData, final List<Retailer> retailers) throws Exception {
		PagedListHolder<RetailerViewBean> RetailerViewBeanPagedListHolder = new PagedListHolder<RetailerViewBean>();
		
		final List<RetailerViewBean> RetailerViewBeans = new ArrayList<RetailerViewBean>();
		for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
			Retailer retailerIt = (Retailer) iterator.next();
			RetailerViewBeans.add(backofficeViewBeanFactory.buildViewBeanRetailer(requestData, retailerIt));
		}
		RetailerViewBeanPagedListHolder = new PagedListHolder<RetailerViewBean>(RetailerViewBeans);
		RetailerViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, RetailerViewBeanPagedListHolder); 
        
        return RetailerViewBeanPagedListHolder;
	}
    
	/**
	 * 
	 */
    @ModelAttribute("retailerForm")
	protected RetailerForm initRetailerForm(final HttpServletRequest request, final Model model) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		final String retailerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_RETAILER_CODE);
		if(StringUtils.isNotEmpty(retailerCode)){
	        final Retailer retailer = retailerService.getRetailerByCode(retailerCode);
	        return backofficeFormFactory.buildRetailerForm(requestData, retailer);
		}
    	return backofficeFormFactory.buildRetailerForm(requestData, null);
	}
    
	/**
	 * 
	 */
	protected RetailerViewBean initRetailerViewBean(final HttpServletRequest request, final Model model) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		final MarketArea marketArea = requestData.getMarketArea();
		final Retailer retailer = requestData.getMarketAreaRetailer();
		final String retailerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_RETAILER_CODE);
        if(StringUtils.isNotEmpty(retailerCode)){
            final Retailer retailerEdit = retailerService.getRetailerByCode(marketArea.getId(), retailer.getId(), retailerCode);
            return backofficeViewBeanFactory.buildViewBeanRetailer(requestData, retailerEdit);
        }
        return null;
	}
	
    @ModelAttribute(ModelConstants.COUNTRIES)
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
		List<ValueBean> countriesValues = new ArrayList<ValueBean>();
		try {
	        final RequestData requestData = requestUtil.getRequestData(request);
	        final Locale locale = requestData.getLocale();
	        
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
			logger.error("", e);
		}
		return countriesValues;
    }
    
    @ModelAttribute(ModelConstants.WAREHOUSES_VIEW_BEAN)
    public List<ValueBean> getWarehouses(HttpServletRequest request) throws Exception{
    	List<ValueBean> warehousesValues = new ArrayList<ValueBean>();
		try {
	        final RequestData requestData = requestUtil.getRequestData(request);
	        final MarketArea marketArea = requestData.getMarketArea();
	        List<Warehouse> warehouses = warehouseService.findWarehousesByMarketAreaId(marketArea.getId());
	        if(warehouses != null){
		        for (Iterator<Warehouse> iterator = warehouses.iterator(); iterator.hasNext();) {
		            Warehouse warehouseIt = (Warehouse) iterator.next();
		            final String warehouseId = warehouseIt.getId().toString();
					warehousesValues.add(new ValueBean(warehouseId, warehouseIt.getName()));
		        }
		        Collections.sort(warehousesValues, new Comparator<ValueBean>() {
					@Override
					public int compare(ValueBean o1, ValueBean o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				});
	        }
		} catch (Exception e) {
			logger.error("", e);
		}
		return warehousesValues;
    }

}