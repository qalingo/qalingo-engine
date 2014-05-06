/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.deliverymethod;

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
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphDeliveryMethod;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.DeliveryMethodService;
import org.hoteia.qalingo.core.service.WarehouseService;
import org.hoteia.qalingo.core.web.mvc.form.DeliveryMethodForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.DeliveryMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.WarehouseViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
@Controller("deliveryMethodController")
public class DeliveryMethodController extends AbstractBusinessBackofficeController {

	@Autowired
	private DeliveryMethodService deliveryMethodService;

    @Autowired
    private WarehouseService warehouseService;
    
	@RequestMapping(value = BoUrls.DELIVERY_METHOD_LIST_URL, method = RequestMethod.GET)
	public ModelAndView deliveryMethodList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.DELIVERY_METHOD_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        
		displayList(request, model, requestData);
		
        Object[] params = {marketArea.getName() + " (" + marketArea.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.DELIVERY_METHOD_LIST.getKey() + ".by.market.area", params);

        model.addAttribute(ModelConstants.URL_ADD, backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_ADD, requestData));
        
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.DELIVERY_METHOD_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView deliveryMethodDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.DELIVERY_METHOD_DETAILS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		
		final String deliveryMethodCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_DELIVERY_METHOD_CODE);
		
        // SANITY CHECK
        if(StringUtils.isEmpty(deliveryMethodCode)){
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_LIST, requestData);
            return new ModelAndView(new RedirectView(urlRedirect));
        }
        
		final DeliveryMethod deliveryMethod = deliveryMethodService.getDeliveryMethodByCode(deliveryMethodCode);
		
        // SANITY CHECK
		if(deliveryMethod != null){
	        modelAndView.addObject(ModelConstants.DELIVERY_METHOD_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanDeliveryMethod(requestData, deliveryMethod));
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        final List<WarehouseViewBean> warehouseViewBeans = new ArrayList<WarehouseViewBean>();
        final List<Warehouse> warehouses = warehouseService.findWarehousesByDeliveryMethodId(deliveryMethod.getId());
        for (Iterator<Warehouse> iterator = warehouses.iterator(); iterator.hasNext();) {
            Warehouse warehouse = (Warehouse) iterator.next();
            warehouseViewBeans.add(backofficeViewBeanFactory.buildViewBeanWarehouse(requestUtil.getRequestData(request), warehouse));
        }
        request.setAttribute(ModelConstants.WAREHOUSES_VIEW_BEAN, warehouseViewBeans);
        
        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_LIST, requestData));
		
        Object[] params = {deliveryMethod.getName() + " (" + deliveryMethod.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.DELIVERY_METHOD_DETAILS.getKey(), params);

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.DELIVERY_METHOD_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView deliveryMethodEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.DELIVERY_METHOD_FORM) DeliveryMethodForm deliveryMethodForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.DELIVERY_METHOD_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String deliveryMethodCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_DELIVERY_METHOD_CODE);
        if(StringUtils.isNotEmpty(deliveryMethodCode)){
            // EDIT MODE
            final DeliveryMethod deliveryMethod = deliveryMethodService.getDeliveryMethodByCode(deliveryMethodCode, FetchPlanGraphDeliveryMethod.fullDeliveryMethodFetchPlan());

            DeliveryMethodViewBean deliveryMethodViewBean = backofficeViewBeanFactory.buildViewBeanDeliveryMethod(requestData, deliveryMethod);
            request.setAttribute(ModelConstants.DELIVERY_METHOD_VIEW_BEAN, deliveryMethodViewBean);

            Object[] params = {deliveryMethod.getName() + " (" + deliveryMethod.getCode() + ")"};
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.DELIVERY_METHOD_EDIT.getKey(), params);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_DETAILS, requestData, deliveryMethod));
        } else {
            // ADD MODE

            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.DELIVERY_METHOD_ADD.getKey(), null);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_LIST, requestData));
        }
        
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.DELIVERY_METHOD_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitDeliveryMethodEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.DELIVERY_METHOD_FORM) DeliveryMethodForm deliveryMethodForm,
								                 BindingResult result) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

		if (result.hasErrors()) {
			return deliveryMethodEdit(request, model, deliveryMethodForm);
		}

        DeliveryMethod deliveryMethod = null;
        if(StringUtils.isNotEmpty(deliveryMethodForm.getId())){
            deliveryMethod = deliveryMethodService.getDeliveryMethodById(deliveryMethodForm.getId());
        }

		try {
    		// CREATE OR UPDATE DELIVERY METHOD
		    DeliveryMethod savedDeliveryMethod = webBackofficeService.createOrUpdateDeliveryMethod(deliveryMethod, deliveryMethodForm);
    		
            if (deliveryMethod == null) {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.DELIVERY_METHOD, "create_success_message", locale));
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.DELIVERY_METHOD, "update_success_message", locale));
            }
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_DETAILS, requestUtil.getRequestData(request), savedDeliveryMethod);
            return new ModelAndView(new RedirectView(urlRedirect));
 
        } catch (Exception e) {
            addMessageError(result, null, "code", "code", getSpecificMessage(ScopeWebMessage.DELIVERY_METHOD, "create_or_update_message", locale));
            logger.error("Can't save or update Delivery Method:" + deliveryMethodForm.getId() + "/" + deliveryMethodForm.getCode(), e);
            return deliveryMethodEdit(request, model, deliveryMethodForm);
        }
	}

    /**
     * 
     */
    @ModelAttribute(ModelConstants.DELIVERY_METHOD_FORM)
    protected DeliveryMethodForm initDeliveryMethodForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String deliveryMethodCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_DELIVERY_METHOD_CODE);
        if(StringUtils.isNotEmpty(deliveryMethodCode)){
            final DeliveryMethod deliveryMethodEdit = deliveryMethodService.getDeliveryMethodByCode(deliveryMethodCode);
            return backofficeFormFactory.buildDeliveryMethodForm(requestData, deliveryMethodEdit);
        }
        
        return backofficeFormFactory.buildDeliveryMethodForm(requestData, null);
    }
    
    protected void displayList(final HttpServletRequest request, final Model model, final RequestData requestData) throws Exception {
        String url = request.getRequestURI();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
        String sessionKey = "PagedListHolder_DeliveryMethods";
        
        PagedListHolder<DeliveryMethodViewBean> deliveryMethodViewBeanPagedListHolder = new PagedListHolder<DeliveryMethodViewBean>();
        
        if(StringUtils.isEmpty(page)){
            deliveryMethodViewBeanPagedListHolder = initList(request, sessionKey, requestData);
            
        } else {
            deliveryMethodViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
            if (deliveryMethodViewBeanPagedListHolder == null) { 
                deliveryMethodViewBeanPagedListHolder = initList(request, sessionKey, requestData);
            }
            int pageTarget = new Integer(page).intValue() - 1;
            int pageCurrent = deliveryMethodViewBeanPagedListHolder.getPage();
            if (pageCurrent < pageTarget) { 
                for (int i = pageCurrent; i < pageTarget; i++) {
                    deliveryMethodViewBeanPagedListHolder.nextPage(); 
                }
            } else if (pageCurrent > pageTarget) { 
                for (int i = pageTarget; i < pageCurrent; i++) {
                    deliveryMethodViewBeanPagedListHolder.previousPage(); 
                }
            } 
        }
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, deliveryMethodViewBeanPagedListHolder);
    }
    
    protected PagedListHolder<DeliveryMethodViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData) throws Exception {
	    final MarketArea marketArea = requestData.getMarketArea();
	    
		PagedListHolder<DeliveryMethodViewBean> deliveryMethodViewBeanPagedListHolder = new PagedListHolder<DeliveryMethodViewBean>();
		
		final List<DeliveryMethodViewBean> deliveryMethodViewBeans = new ArrayList<DeliveryMethodViewBean>();

		final List<DeliveryMethod> deliveryMethods = deliveryMethodService.findDeliveryMethodsByMarketAreaId(marketArea.getId());
		for (Iterator<DeliveryMethod> iterator = deliveryMethods.iterator(); iterator.hasNext();) {
			DeliveryMethod deliveryMethod = (DeliveryMethod) iterator.next();
			deliveryMethodViewBeans.add(backofficeViewBeanFactory.buildViewBeanDeliveryMethod(requestUtil.getRequestData(request), deliveryMethod));
		}
		deliveryMethodViewBeanPagedListHolder = new PagedListHolder<DeliveryMethodViewBean>(deliveryMethodViewBeans);
		deliveryMethodViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, deliveryMethodViewBeanPagedListHolder);
        
        return deliveryMethodViewBeanPagedListHolder;
	}
    
}