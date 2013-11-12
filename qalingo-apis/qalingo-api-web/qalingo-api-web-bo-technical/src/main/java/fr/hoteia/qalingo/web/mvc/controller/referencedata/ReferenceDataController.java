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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import fr.hoteia.qalingo.core.domain.CurrencyReferential;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.service.CurrencyReferentialService;
import fr.hoteia.qalingo.core.service.PaymentGatewayService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
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
		
		//get the local referenced data
		List<Localization> localizations = localizationService.findLocalizations();
		List<LocalizationViewBean> localizationViewBeans = viewBeanFactory.buildLocalizationViewBeans(requestUtil.getRequestData(request), localizations);
		modelAndView.addObject("localizations", localizationViewBeans);
		
		List<AbstractPaymentGateway> paymentGateways = paymentGatewayService.findPaymentGateways();
		List<PaymentGatewayViewBean> paymentGatewayViewBeans = viewBeanFactory.buildPaymentGatewayViewBeans(requestUtil.getRequestData(request), paymentGateways);
		modelAndView.addObject("paymentGateways", paymentGatewayViewBeans);
		
        return modelAndView;
	}
}