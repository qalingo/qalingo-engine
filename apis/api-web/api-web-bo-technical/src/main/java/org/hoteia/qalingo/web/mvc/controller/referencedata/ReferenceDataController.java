/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.referencedata;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.qalingo.core.service.CurrencyReferentialService;
import org.hoteia.qalingo.core.web.mvc.viewbean.AttributeDefinitionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CurrencyReferentialViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("referenceDataController")
public class ReferenceDataController extends AbstractTechnicalBackofficeController {

    @Autowired
    protected AttributeService attributeService;
    
	@Autowired
	protected CurrencyReferentialService currencyReferentialService;

	@RequestMapping(BoUrls.REFERENCE_DATAS_URL)
	public ModelAndView searchReferenceDatas(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.REFERENCE_DATAS.getVelocityPage());

	    // CURRENCY REFERENTIAL
        List<AttributeDefinition> attributeDefinitions = attributeService.findAttributeDefinitions();
        List<AttributeDefinitionViewBean> attributeDefinitionViewBeans = backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestUtil.getRequestData(request), attributeDefinitions);
        modelAndView.addObject("attributeDefinitions", attributeDefinitionViewBeans);

		// CURRENCY REFERENTIAL
		List<CurrencyReferential> currencyReferentials = currencyReferentialService.findCurrencyReferentials();
		List<CurrencyReferentialViewBean> currencyReferentialViewBeans = backofficeViewBeanFactory.buildListViewBeanCurrencyReferential(requestUtil.getRequestData(request), currencyReferentials);
		modelAndView.addObject("currencyReferentials", currencyReferentialViewBeans);
		
		// LOCALIZATION
		List<Localization> localizations = localizationService.findLocalizations();
		List<LocalizationViewBean> localizationViewBeans = backofficeViewBeanFactory.buildListViewBeanLocalization(requestUtil.getRequestData(request), localizations);
		modelAndView.addObject("localizations", localizationViewBeans);
		
        return modelAndView;
	}
	
}