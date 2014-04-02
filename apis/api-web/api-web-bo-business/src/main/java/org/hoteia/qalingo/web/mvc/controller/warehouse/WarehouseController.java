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
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.DeliveryMethodService;
import org.hoteia.qalingo.core.service.WarehouseService;
import org.hoteia.qalingo.core.web.mvc.form.WarehouseForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.DeliveryMethodViewBean;
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

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private DeliveryMethodService deliveryMethodService;
    
    @RequestMapping(value = BoUrls.WAREHOUSE_LIST_URL, method = RequestMethod.GET)
    public ModelAndView warehouseList(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        
        displayList(request, model, requestData);
        
        Object[] params = {marketArea.getName() + " (" + marketArea.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.WAREHOUSE_LIST.getKey() + ".by.market.area", params);

        model.addAttribute(ModelConstants.URL_ADD, backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_ADD, requestData));

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.WAREHOUSE_DETAILS_URL, method = RequestMethod.GET)
    public ModelAndView warehouseDetails(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String warehouseCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_WAREHOUSE_CODE);
        
        // SANITY CHECK
        if(StringUtils.isEmpty(warehouseCode)){
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_LIST, requestData);
            return new ModelAndView(new RedirectView(urlRedirect));
        }
        
        final Warehouse warehouse = warehouseService.getWarehouseByCode(warehouseCode);

        // SANITY CHECK
        if(warehouse != null){
            modelAndView.addObject(ModelConstants.WAREHOUSE_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanWarehouse(requestData, warehouse));
        } else {
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }
        
        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_LIST, requestData));
        
        final List<DeliveryMethodViewBean> deliveryMethodViewBeans = new ArrayList<DeliveryMethodViewBean>();
        final List<DeliveryMethod> deliveryMethods = deliveryMethodService.findDeliveryMethodsByWarehouseId(warehouse.getId());
        for (Iterator<DeliveryMethod> iterator = deliveryMethods.iterator(); iterator.hasNext();) {
            DeliveryMethod deliveryMethod = (DeliveryMethod) iterator.next();
            deliveryMethodViewBeans.add(backofficeViewBeanFactory.buildViewBeanDeliveryMethod(requestUtil.getRequestData(request), deliveryMethod));
        }
        request.setAttribute(ModelConstants.DELIVERY_METHODS_VIEW_BEAN, deliveryMethodViewBeans);

        Object[] params = {warehouse.getName() + " (" + warehouse.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.WAREHOUSE_DETAILS.getKey(), params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.WAREHOUSE_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView warehouseEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.WAREHOUSE_FORM) WarehouseForm warehouseForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String warehouseCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_WAREHOUSE_CODE);
        if(StringUtils.isNotEmpty(warehouseCode)){
            // EDIT MODE
            final Warehouse warehouse = warehouseService.getWarehouseByCode(warehouseCode);

            WarehouseViewBean warehouseViewBean = backofficeViewBeanFactory.buildViewBeanWarehouse(requestData, warehouse);
            request.setAttribute(ModelConstants.WAREHOUSE_VIEW_BEAN, warehouseViewBean);

            Object[] params = {warehouse.getName() + " (" + warehouse.getCode() + ")"};
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.WAREHOUSE_EDIT.getKey(), params);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_DETAILS, requestData, warehouse));
        } else {
            // ADD MODE

            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.WAREHOUSE_ADD.getKey(), null);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_LIST, requestData));
        }

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.WAREHOUSE_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitWarehouseEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.WAREHOUSE_FORM) WarehouseForm warehouseForm,
                                BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        if (result.hasErrors()) {
            return warehouseEdit(request, model, warehouseForm);
        }
        
        Warehouse warehouse = null;
        if(StringUtils.isNotEmpty(warehouseForm.getId())){
            warehouse = warehouseService.getWarehouseById(warehouseForm.getId());
        }

        try {
            // CREATE OR UPDATE WAREHOUSE
            webBackofficeService.createOrUpdateWarehouse(requestData, warehouse, warehouseForm);
            
            if (warehouse == null) {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.WAREHOUSE, "create_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_LIST, requestData);
                return new ModelAndView(new RedirectView(urlRedirect));
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.WAREHOUSE, "update_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.WAREHOUSE_DETAILS, requestData, warehouse);
                return new ModelAndView(new RedirectView(urlRedirect));
            }
            
        } catch (Exception e) {
            addMessageError(result, null, "code", "code", getSpecificMessage(ScopeWebMessage.WAREHOUSE, "create_or_update_message", locale));
            logger.error("Can't save or update Warehouse:" + warehouseForm.getId() + "/" + warehouseForm.getCode(), e);
            return warehouseEdit(request, model, warehouseForm);
        }
    }

    /**
     * 
     */
    @ModelAttribute(ModelConstants.WAREHOUSE_FORM)
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

    protected void displayList(final HttpServletRequest request, final Model model, final RequestData requestData) throws Exception {
        String url = request.getRequestURI();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
        String sessionKey = "PagedListHolder_Warehouses";
        
        PagedListHolder<WarehouseViewBean> warehouseViewBeanPagedListHolder = new PagedListHolder<WarehouseViewBean>();

        if(StringUtils.isEmpty(page)){
            warehouseViewBeanPagedListHolder = initList(request, sessionKey, requestData);
            
        } else {
            warehouseViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
            if (warehouseViewBeanPagedListHolder == null) { 
                warehouseViewBeanPagedListHolder = initList(request, sessionKey, requestData);
            }
            int pageTarget = new Integer(page).intValue() - 1;
            int pageCurrent = warehouseViewBeanPagedListHolder.getPage();
            if (pageCurrent < pageTarget) { 
                for (int i = pageCurrent; i < pageTarget; i++) {
                    warehouseViewBeanPagedListHolder.nextPage(); 
                }
            } else if (pageCurrent > pageTarget) { 
                for (int i = pageTarget; i < pageCurrent; i++) {
                    warehouseViewBeanPagedListHolder.previousPage(); 
                }
            } 
        }
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, warehouseViewBeanPagedListHolder);
    }
    
    protected PagedListHolder<WarehouseViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        List<Warehouse> warehouses = warehouseService.findWarehousesByMarketAreaId(marketArea.getId());

        PagedListHolder<WarehouseViewBean> warehouseViewBeanPagedListHolder = new PagedListHolder<WarehouseViewBean>();
        
        final List<WarehouseViewBean> warehouseViewBeans = new ArrayList<WarehouseViewBean>();
        for (Iterator<Warehouse> iterator = warehouses.iterator(); iterator.hasNext();) {
            Warehouse warehouseIt = (Warehouse) iterator.next();
            warehouseViewBeans.add(backofficeViewBeanFactory.buildViewBeanWarehouse(requestData, warehouseIt));
        }
        warehouseViewBeanPagedListHolder = new PagedListHolder<WarehouseViewBean>(warehouseViewBeans);
        warehouseViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, warehouseViewBeanPagedListHolder); 
        
        return warehouseViewBeanPagedListHolder;
    }
 
}