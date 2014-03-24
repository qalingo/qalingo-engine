/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.warehouse;

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
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.BoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.WarehouseService;
import org.hoteia.qalingo.core.web.mvc.form.WarehouseForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.WarehouseViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
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
@Controller("warehouseController")
public class WarehouseController extends AbstractBusinessBackofficeController {

    public static final String SESSION_KEY = "PagedListHolder_Warehouses";

    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping(value = BoUrls.WAREHOUSE_LIST_URL, method = RequestMethod.GET)
    public ModelAndView warehouseList(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        final String contentText = getSpecificMessage(ScopeWebMessage.WAREHOUSE, BoMessageKey.MAIN_CONTENT_TEXT, locale);
        modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
        
        displayList(request, model, requestData, null);
        
        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.WAREHOUSE_DETAILS_URL, method = RequestMethod.GET)
    public ModelAndView warehouseDetails(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String warehouseCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_WAREHOUSE_CODE);
        final Warehouse warehouse = warehouseService.getWarehouseByCode(warehouseCode);
        
        WarehouseViewBean warehouseViewBean = backofficeViewBeanFactory.buildViewBeanWarehouse(requestData, warehouse);

        if(warehouseViewBean == null){
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }
        
        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_LIST, requestData));
        
        request.setAttribute(ModelConstants.WAREHOUSE_VIEW_BEAN, warehouseViewBean);
        
        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.WAREHOUSE_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView warehouseEdit(final HttpServletRequest request, final Model model, @ModelAttribute("warehouseForm") WarehouseForm warehouseForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String warehouseCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_WAREHOUSE_CODE);
        final Warehouse warehouse = warehouseService.getWarehouseByCode(warehouseCode);
        
        WarehouseViewBean warehouseViewBean = backofficeViewBeanFactory.buildViewBeanWarehouse(requestData, warehouse);
        
        if(warehouseViewBean == null){
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }
        
        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_DETAILS, requestData, warehouse));
        
        request.setAttribute(ModelConstants.WAREHOUSE_VIEW_BEAN, warehouseViewBean);
        
        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.WAREHOUSE_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitRuleEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute("warehouseForm") WarehouseForm warehouseForm,
                                BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        if (result.hasErrors()) {
            return warehouseEdit(request, model, warehouseForm);
        }
        
        Warehouse warehouse = new Warehouse();
        if(StringUtils.isNotEmpty(warehouseForm.getId())){
            warehouse = warehouseService.getWarehouseById(warehouseForm.getId());
        }
        
        try {
            // UPDATE WAREHOUSE
            webBackofficeService.createOrUpdateWarehouse(warehouse, warehouseForm);
            
        } catch (Exception e) {
            logger.error("Can't save or update Warehouse:" + warehouseForm.getId() + "/" + warehouseForm.getCode(), e);
            return warehouseEdit(request, model, warehouseForm);
        }
        
        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_DETAILS, requestData, warehouse);
        return new ModelAndView(new RedirectView(urlRedirect));
    }

    private void displayList(final HttpServletRequest request, final Model model, final RequestData requestData, List<Warehouse> warehouses) throws Exception{
        final MarketArea marketArea = requestData.getMarketArea();
        
        String url = request.getRequestURI();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
        
        PagedListHolder<WarehouseViewBean> WarehouseViewBeanPagedListHolder = new PagedListHolder<WarehouseViewBean>();

        if(warehouses == null){
            warehouses = warehouseService.findWarehousesByMarketAreaId(marketArea.getId());
        }
        
        if(StringUtils.isEmpty(page)){
            WarehouseViewBeanPagedListHolder = initList(request, SESSION_KEY, requestData, warehouses);
            
        } else {
            WarehouseViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(SESSION_KEY); 
            if (WarehouseViewBeanPagedListHolder == null) { 
                WarehouseViewBeanPagedListHolder = initList(request, SESSION_KEY, requestData, warehouses);
            }
            int pageTarget = new Integer(page).intValue() - 1;
            int pageCurrent = WarehouseViewBeanPagedListHolder.getPage();
            if (pageCurrent < pageTarget) { 
                for (int i = pageCurrent; i < pageTarget; i++) {
                    WarehouseViewBeanPagedListHolder.nextPage(); 
                }
            } else if (pageCurrent > pageTarget) { 
                for (int i = pageTarget; i < pageCurrent; i++) {
                    WarehouseViewBeanPagedListHolder.previousPage(); 
                }
            } 
        }
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, WarehouseViewBeanPagedListHolder);
    }
    
    private PagedListHolder<WarehouseViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData, final List<Warehouse> warehouses) throws Exception {
        PagedListHolder<WarehouseViewBean> WarehouseViewBeanPagedListHolder = new PagedListHolder<WarehouseViewBean>();
        
        final List<WarehouseViewBean> WarehouseViewBeans = new ArrayList<WarehouseViewBean>();
        for (Iterator<Warehouse> iterator = warehouses.iterator(); iterator.hasNext();) {
            Warehouse warehouseIt = (Warehouse) iterator.next();
            WarehouseViewBeans.add(backofficeViewBeanFactory.buildViewBeanWarehouse(requestData, warehouseIt));
        }
        WarehouseViewBeanPagedListHolder = new PagedListHolder<WarehouseViewBean>(WarehouseViewBeans);
        WarehouseViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, WarehouseViewBeanPagedListHolder); 
        
        return WarehouseViewBeanPagedListHolder;
    }
    
    /**
     * 
     */
    @ModelAttribute("warehouseForm")
    protected WarehouseForm initWarehouseForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String warehouseCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_WAREHOUSE_CODE);
        if(StringUtils.isNotEmpty(warehouseCode)){
            final Warehouse warehouseEdit = warehouseService.getWarehouseByCode(warehouseCode);
            return backofficeFormFactory.buildWarehouseForm(requestData, warehouseEdit);
        }
        
        return backofficeFormFactory.buildWarehouseForm(requestData, null);
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

}