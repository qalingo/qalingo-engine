/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.tax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.TaxService;
import org.hoteia.qalingo.core.web.mvc.form.TaxForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.TaxViewBean;
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
@Controller("taxController")
public class TaxController extends AbstractBusinessBackofficeController {

    @Autowired
    private TaxService taxService;

    @RequestMapping(value = BoUrls.TAX_LIST_URL, method = RequestMethod.GET)
    public ModelAndView taxList(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.TAX_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        
        displayList(request, model, requestData);
        
        Object[] params = {marketArea.getName() + " (" + marketArea.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.TAX_LIST.getKey() + ".by.market.area", params);

        model.addAttribute(ModelConstants.URL_ADD, backofficeUrlService.generateUrl(BoUrls.TAX_ADD, requestData));

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.TAX_DETAILS_URL, method = RequestMethod.GET)
    public ModelAndView taxDetails(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.TAX_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String taxCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_TAX_CODE);
        final Tax tax = taxService.getTaxByCode(taxCode);
        
        // SANITY CHECK
        if(tax != null){
            modelAndView.addObject(ModelConstants.TAX_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanTax(requestData, tax));
        } else {
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }

        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.TAX_LIST, requestData));
        
        Object[] params = {tax.getName() + " (" + tax.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.TAX_DETAILS.getKey(), params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.TAX_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView taxEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.TAX_FORM) TaxForm taxForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.TAX_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String taxCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_TAX_CODE);
        if(StringUtils.isNotEmpty(taxCode)){
            // EDIT MODE
            final Tax tax = taxService.getTaxByCode(taxCode);

            TaxViewBean taxViewBean = backofficeViewBeanFactory.buildViewBeanTax(requestData, tax);
            request.setAttribute(ModelConstants.TAX_VIEW_BEAN, taxViewBean);

            Object[] params = {tax.getName() + " (" + tax.getCode() + ")"};
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.TAX_EDIT.getKey(), params);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.TAX_DETAILS, requestData, tax));
        } else {
            // ADD MODE

            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.TAX_ADD.getKey(), null);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.TAX_LIST, requestData));
        }
        
        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.TAX_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitTaxEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.TAX_FORM) TaxForm taxForm,
                                BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        if (result.hasErrors()) {
            return taxEdit(request, model, taxForm);
        }
        
        Tax tax = null;
        if(StringUtils.isNotEmpty(taxForm.getId())){
            tax = taxService.getTaxById(taxForm.getId());
        }

        try {
            // CREATE OR UPDATE TAX
            webBackofficeService.createOrUpdateTax(tax, taxForm);
            
            if (tax == null) {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.TAX, "create_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.TAX_LIST, requestData);
                return new ModelAndView(new RedirectView(urlRedirect));
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.TAX, "update_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.TAX_DETAILS, requestData, tax);
                return new ModelAndView(new RedirectView(urlRedirect));
            }
            
        } catch (Exception e) {
            addMessageError(result, null, "code", "code", getSpecificMessage(ScopeWebMessage.TAX, "create_or_update_message", locale));
            logger.error("Can't save or update Tax:" + taxForm.getId() + "/" + taxForm.getCode(), e);
            return taxEdit(request, model, taxForm);
        }
    }

    /**
     * 
     */
    @ModelAttribute(ModelConstants.TAX_FORM)
    protected TaxForm initTaxForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String taxCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_TAX_CODE);
        if(StringUtils.isNotEmpty(taxCode)){
            final Tax taxEdit = taxService.getTaxByCode(taxCode);
            return backofficeFormFactory.buildTaxForm(requestData, taxEdit);
        }
        
        return backofficeFormFactory.buildTaxForm(requestData, null);
    }
    
    protected void displayList(final HttpServletRequest request, final Model model, final RequestData requestData) throws Exception {
        String url = request.getRequestURI();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
        String sessionKey = "PagedListHolder_Taxes";
        
        PagedListHolder<TaxViewBean> taxViewBeanPagedListHolder = new PagedListHolder<TaxViewBean>();

        if(StringUtils.isEmpty(page)){
            taxViewBeanPagedListHolder = initList(request, sessionKey, requestData);
            
        } else {
            taxViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
            if (taxViewBeanPagedListHolder == null) { 
                taxViewBeanPagedListHolder = initList(request, sessionKey, requestData);
            }
            int pageTarget = new Integer(page).intValue() - 1;
            int pageCurrent = taxViewBeanPagedListHolder.getPage();
            if (pageCurrent < pageTarget) { 
                for (int i = pageCurrent; i < pageTarget; i++) {
                    taxViewBeanPagedListHolder.nextPage(); 
                }
            } else if (pageCurrent > pageTarget) { 
                for (int i = pageTarget; i < pageCurrent; i++) {
                    taxViewBeanPagedListHolder.previousPage(); 
                }
            } 
        }
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, taxViewBeanPagedListHolder);
    }
    
    protected PagedListHolder<TaxViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        List<Tax> taxs = taxService.findTaxesByMarketAreaId(marketArea.getId());

        PagedListHolder<TaxViewBean> TaxViewBeanPagedListHolder = new PagedListHolder<TaxViewBean>();
        
        final List<TaxViewBean> TaxViewBeans = new ArrayList<TaxViewBean>();
        for (Iterator<Tax> iterator = taxs.iterator(); iterator.hasNext();) {
            Tax taxIt = (Tax) iterator.next();
            TaxViewBeans.add(backofficeViewBeanFactory.buildViewBeanTax(requestData, taxIt));
        }
        TaxViewBeanPagedListHolder = new PagedListHolder<TaxViewBean>(TaxViewBeans);
        TaxViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, TaxViewBeanPagedListHolder); 
        
        return TaxViewBeanPagedListHolder;
    }
 
}