/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.referencedata;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import fr.hoteia.qalingo.core.domain.CurrencyReferential;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.service.CurrencyReferentialService;
import fr.hoteia.qalingo.core.service.PaymentGatewayService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.core.web.servlet.view.RedirectView;
import fr.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.PaymentGatewayForm;
import fr.hoteia.qalingo.web.mvc.viewbean.CurrencyReferentialViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.PaymentGatewayViewBean;

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
		List<CurrencyReferentialViewBean> currencyReferentialViewBeans = viewBeanFactory.buildCurrencyReferentialViewBeans(requestUtil.getRequestData(request), currencyReferentials);
		modelAndView.addObject("currencyReferentials", currencyReferentialViewBeans);
		
		List<Localization> localizations = localizationService.findLocalizations();
		List<LocalizationViewBean> localizationViewBeans = viewBeanFactory.buildLocalizationViewBeans(requestUtil.getRequestData(request), localizations);
		modelAndView.addObject("localizations", localizationViewBeans);
		
		List<AbstractPaymentGateway> paymentGateways = paymentGatewayService.findPaymentGateways();
		List<PaymentGatewayViewBean> paymentGatewayViewBeans = viewBeanFactory.buildPaymentGatewayViewBeans(requestUtil.getRequestData(request), paymentGateways);
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
                modelAndView.addObject("paymentGateway", viewBeanFactory.buildPaymentGatewayViewBean(requestUtil.getRequestData(request), paymentGateway));
                return modelAndView;
            }
        }

        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.REFERENCE_DATAS, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
    }
    
    @RequestMapping(value = BoUrls.PAYMENT_GATEWAY_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView paymentGatewayEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PAYMENT_GATEWAY_EDIT.getVelocityPage());
        
        final String paymentGatewayId = request.getParameter(RequestConstants.REQUEST_PARAMETER_PAYMENT_GATEWAY_ID);
        if(StringUtils.isNotEmpty(paymentGatewayId)){
            final AbstractPaymentGateway paymentGateway = paymentGatewayService.getPaymentGatewayById(paymentGatewayId);
            if(paymentGateway != null){
                modelAndView.addObject("paymentGateway", viewBeanFactory.buildPaymentGatewayViewBean(requestUtil.getRequestData(request), paymentGateway));
                formFactory.buildPaymentGatewayForm(request, modelAndView, paymentGateway);
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