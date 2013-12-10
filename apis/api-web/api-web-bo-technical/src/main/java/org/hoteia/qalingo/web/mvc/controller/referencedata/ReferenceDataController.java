/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.referencedata;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CurrencyReferentialService;
import org.hoteia.qalingo.core.service.PaymentGatewayService;
import org.hoteia.qalingo.core.web.mvc.form.PaymentGatewayForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.CurrencyReferentialViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentGatewayViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("referenceDataController")
public class ReferenceDataController extends AbstractTechnicalBackofficeController {

	@Autowired
	protected CurrencyReferentialService currencyReferentialService;

	@Autowired
	protected PaymentGatewayService paymentGatewayService;
	
	@RequestMapping(BoUrls.REFERENCE_DATAS_URL)
	public ModelAndView searchReferenceDatas(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.REFERENCE_DATAS.getVelocityPage());

		List<CurrencyReferential> currencyReferentials = currencyReferentialService.findCurrencyReferentials();
		List<CurrencyReferentialViewBean> currencyReferentialViewBeans = backofficeViewBeanFactory.buildCurrencyReferentialViewBeans(requestUtil.getRequestData(request), currencyReferentials);
		modelAndView.addObject("currencyReferentials", currencyReferentialViewBeans);
		
		//get the local referenced data
		List<Localization> localizations = localizationService.findLocalizations();
		List<LocalizationViewBean> localizationViewBeans = backofficeViewBeanFactory.buildLocalizationViewBeans(requestUtil.getRequestData(request), localizations);
		modelAndView.addObject("localizations", localizationViewBeans);
		
		List<AbstractPaymentGateway> paymentGateways = paymentGatewayService.findPaymentGateways();
		List<PaymentGatewayViewBean> paymentGatewayViewBeans = backofficeViewBeanFactory.buildPaymentGatewayViewBeans(requestUtil.getRequestData(request), paymentGateways);
		modelAndView.addObject("paymentGateways", paymentGatewayViewBeans);
		
        return modelAndView;
	}
	
    @RequestMapping(value = BoUrls.PAYMENT_GATEWAY_DETAILS_URL, method = RequestMethod.GET)
    public ModelAndView paymentGatewayDetails(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PAYMENT_GATEWAY_DETAILS.getVelocityPage());
        
        final String paymentGatewayId = request.getParameter(RequestConstants.REQUEST_PARAMETER_PAYMENT_GATEWAY_ID);
        if(StringUtils.isNotEmpty(paymentGatewayId)){
            final AbstractPaymentGateway paymentGateway = paymentGatewayService.getPaymentGatewayById(paymentGatewayId);
            if(paymentGateway != null){
                modelAndView.addObject("paymentGateway", backofficeViewBeanFactory.buildPaymentGatewayViewBean(requestUtil.getRequestData(request), paymentGateway));
                return modelAndView;
            }
        }

        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.REFERENCE_DATAS, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
    }
    
    @RequestMapping(value = BoUrls.PAYMENT_GATEWAY_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView paymentGatewayEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PAYMENT_GATEWAY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String paymentGatewayId = request.getParameter(RequestConstants.REQUEST_PARAMETER_PAYMENT_GATEWAY_ID);
        if(StringUtils.isNotEmpty(paymentGatewayId)){
            final AbstractPaymentGateway paymentGateway = paymentGatewayService.getPaymentGatewayById(paymentGatewayId);
            if(paymentGateway != null){
                modelAndView.addObject("paymentGateway", backofficeViewBeanFactory.buildPaymentGatewayViewBean(requestUtil.getRequestData(request), paymentGateway));
                backofficeFormFactory.buildPaymentGatewayForm(requestData, paymentGateway);
                return modelAndView;
            }
        }

        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.REFERENCE_DATAS, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
    }

    @RequestMapping(value = BoUrls.PAYMENT_GATEWAY_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitPaymentGatewayEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid PaymentGatewayForm paymentGatewayForm,
                                BindingResult result, ModelMap modelMap) throws Exception {

        if (result.hasErrors()) {
            return paymentGatewayEdit(request, response, modelMap);
        }

        final String paymentGatewayId = paymentGatewayForm.getId();
        final AbstractPaymentGateway paymentGateway = paymentGatewayService.getPaymentGatewayById(paymentGatewayId);

        // UPDATE PAYMENT GATEWAY ATTRIBUTES
//        webBackofficeService.updateEngineSettingValue(paymentGateway, paymentGatewayForm);

        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_DETAILS, requestUtil.getRequestData(request), paymentGateway);
        return new ModelAndView(new RedirectView(urlRedirect));
    }
}