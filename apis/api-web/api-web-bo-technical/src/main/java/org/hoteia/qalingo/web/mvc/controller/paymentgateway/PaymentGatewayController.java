/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.paymentgateway;

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
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.hoteia.qalingo.core.fetchplan.market.FetchPlanGraphMarket;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.PaymentGatewayService;
import org.hoteia.qalingo.core.web.mvc.form.PaymentGatewayForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentGatewayViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
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
@Controller("paymentGatewayController")
public class PaymentGatewayController extends AbstractTechnicalBackofficeController {

	@Autowired
	protected PaymentGatewayService paymentGatewayService;
	
    @Autowired
    protected MarketService marketService;
    
    @RequestMapping(value = BoUrls.PAYMENT_GATEWAY_LIST_URL, method = RequestMethod.GET)
    public ModelAndView paymentGatewayList(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PAYMENT_GATEWAY_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        
        displayList(request, model, requestData);
        
        Object[] params = {marketArea.getName() + " (" + marketArea.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PAYMENT_GATEWAY_LIST.getKey(), params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.PAYMENT_GATEWAY_DETAILS_URL, method = RequestMethod.GET)
    public ModelAndView paymentGatewayDetails(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PAYMENT_GATEWAY_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String currentPaymentGatewayCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PAYMENT_GATEWAY_CODE);
        
        // SANITY CHECK
        if(StringUtils.isEmpty(currentPaymentGatewayCode)){
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_LIST, requestData);
            return new ModelAndView(new RedirectView(urlRedirect));
        }
        
        final AbstractPaymentGateway paymentGateway = paymentGatewayService.getPaymentGatewayByCode(currentPaymentGatewayCode, FetchPlanGraphCommon.fullPaymentGatewayFetchPlan());
        
        // SANITY CHECK
        if(paymentGateway != null){
            modelAndView.addObject(ModelConstants.PAYMENT_GATEWAY_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanPaymentGateway(requestData, paymentGateway));
        } else {
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }
        
        modelAndView.addObject("availablePaymentGatewayOptions", paymentGatewayService.findPaymentGatewayOptions());

        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_LIST, requestData));
        
        Object[] params = {paymentGateway.getName() + " (" + paymentGateway.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PAYMENT_GATEWAY_DETAILS.getKey(), params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.PAYMENT_GATEWAY_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView paymentGatewayEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.PAYMENT_GATEWAY_FORM) PaymentGatewayForm paymentGatewayForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PAYMENT_GATEWAY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String paymentGatewayCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PAYMENT_GATEWAY_CODE);
        if(StringUtils.isNotEmpty(paymentGatewayCode)){
            // EDIT MODE
            final AbstractPaymentGateway paymentGateway = paymentGatewayService.getPaymentGatewayByCode(paymentGatewayCode, FetchPlanGraphCommon.defaultPaymentGatewayFetchPlan());

            PaymentGatewayViewBean paymentGatewayViewBean = backofficeViewBeanFactory.buildViewBeanPaymentGateway(requestData, paymentGateway);
            request.setAttribute(ModelConstants.PAYMENT_GATEWAY_VIEW_BEAN, paymentGatewayViewBean);

            modelAndView.addObject("availableGlobaleAttributeDefinitions", attributeService.findPaymentGatewayGlobalAttributeDefinitions());
            modelAndView.addObject("availableMarketAreaAttributeDefinitions", attributeService.findPaymentGatewayMarketAreaAttributeDefinitions());
            modelAndView.addObject("availableOptions", paymentGatewayService.findPaymentGatewayOptions());

            Object[] params = {paymentGateway.getName() + " (" + paymentGateway.getCode() + ")"};
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.PAYMENT_GATEWAY_EDIT.getKey(), params);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_DETAILS, requestData, paymentGateway));
            
        } else {
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_LIST, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(urlRedirect));
        }
        
        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.PAYMENT_GATEWAY_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitAbstractPaymentGatewayEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.PAYMENT_GATEWAY_FORM) PaymentGatewayForm paymentGatewayForm,
                                                 BindingResult result) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        if (result.hasErrors()) {
            return paymentGatewayEdit(request, model, paymentGatewayForm);
        }

        AbstractPaymentGateway paymentGateway = null;
        if(StringUtils.isNotEmpty(paymentGatewayForm.getId())){
            paymentGateway = paymentGatewayService.getPaymentGatewayById(paymentGatewayForm.getId(), FetchPlanGraphCommon.fullPaymentGatewayFetchPlan());
        }

        try {
            // CREATE OR UPDATE PAYMENT GATEWAY
            webBackofficeService.createOrUpdatePaymentGateway(requestData.getMarketArea(), paymentGateway, paymentGatewayForm);
            
            if (paymentGateway == null) {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.PAYMENT_GATEWAY, "create_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_LIST, requestUtil.getRequestData(request));
                return new ModelAndView(new RedirectView(urlRedirect));
                
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.PAYMENT_GATEWAY, "update_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_DETAILS, requestUtil.getRequestData(request), paymentGateway);
                return new ModelAndView(new RedirectView(urlRedirect));
            }
 
        } catch (Exception e) {
            addMessageError(result, null, "code", "code", getSpecificMessage(ScopeWebMessage.PAYMENT_GATEWAY, "create_or_update_message", locale));
            logger.error("Can't save or update Payment Gateway:" + paymentGatewayForm.getId() + "/" + paymentGatewayForm.getCode(), e);
            return paymentGatewayEdit(request, model, paymentGatewayForm);
        }
    }

    /**
     * 
     */
    @ModelAttribute(ModelConstants.PAYMENT_GATEWAY_FORM)
    protected PaymentGatewayForm initPaymentGatewayForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = marketService.getMarketAreaByCode(requestData.getMarketArea().getCode(), FetchPlanGraphMarket.fullMarketAreaFetchPlan());
        final String paymentGatewayCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PAYMENT_GATEWAY_CODE);
        if(StringUtils.isNotEmpty(paymentGatewayCode)){
            final AbstractPaymentGateway paymentGateway = paymentGatewayService.getPaymentGatewayByCode(paymentGatewayCode, FetchPlanGraphCommon.fullPaymentGatewayFetchPlan());
            return backofficeFormFactory.buildPaymentGatewayForm(marketArea, paymentGateway);
        }
        return backofficeFormFactory.buildPaymentGatewayForm(marketArea, null);
    }
    
    protected void displayList(final HttpServletRequest request, final Model model, final RequestData requestData) throws Exception {
        String url = request.getRequestURI();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
        String sessionKey = "PagedListHolder_PaymentGateways";
        
        PagedListHolder<PaymentGatewayViewBean> paymentGatewayViewBeanPagedListHolder = new PagedListHolder<PaymentGatewayViewBean>();
        
        if(StringUtils.isEmpty(page)){
            paymentGatewayViewBeanPagedListHolder = initList(request, sessionKey, requestData);
            
        } else {
            paymentGatewayViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
            if (paymentGatewayViewBeanPagedListHolder == null) { 
                paymentGatewayViewBeanPagedListHolder = initList(request, sessionKey, requestData);
            }
            int pageTarget = new Integer(page).intValue() - 1;
            int pageCurrent = paymentGatewayViewBeanPagedListHolder.getPage();
            if (pageCurrent < pageTarget) { 
                for (int i = pageCurrent; i < pageTarget; i++) {
                    paymentGatewayViewBeanPagedListHolder.nextPage(); 
                }
            } else if (pageCurrent > pageTarget) { 
                for (int i = pageTarget; i < pageCurrent; i++) {
                    paymentGatewayViewBeanPagedListHolder.previousPage(); 
                }
            } 
        }
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, paymentGatewayViewBeanPagedListHolder);
    }
    
    protected PagedListHolder<PaymentGatewayViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData) throws Exception {
        PagedListHolder<PaymentGatewayViewBean> paymentGatewayViewBeanPagedListHolder = new PagedListHolder<PaymentGatewayViewBean>();
        
        final List<PaymentGatewayViewBean> paymentGatewayViewBeans = new ArrayList<PaymentGatewayViewBean>();

        final List<AbstractPaymentGateway> paymentGateways = paymentGatewayService.findPaymentGateways(FetchPlanGraphCommon.fullPaymentGatewayFetchPlan());
        for (Iterator<AbstractPaymentGateway> iterator = paymentGateways.iterator(); iterator.hasNext();) {
            AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) iterator.next();
            paymentGatewayViewBeans.add(backofficeViewBeanFactory.buildViewBeanPaymentGateway(requestUtil.getRequestData(request), paymentGateway));
        }
        paymentGatewayViewBeanPagedListHolder = new PagedListHolder<PaymentGatewayViewBean>(paymentGatewayViewBeans);
        paymentGatewayViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, paymentGatewayViewBeanPagedListHolder);
        
        return paymentGatewayViewBeanPagedListHolder;
    }

}