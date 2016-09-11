/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.eco;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.OrderPurchase;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.service.DocumentService;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("cartOrderConfirmationController")
public class CartOrderConfirmationController extends AbstractMCommerceController {

    @Autowired
    protected DocumentService documentService;
    
    @RequestMapping(FoUrls.CART_ORDER_CONFIRMATION_URL)
    public ModelAndView displayOrderConfirmation(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CART_ORDER_CONFIRMATION.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        // SANITY CHECK
        final OrderPurchase lastOrder = requestUtil.getLastOrder(requestData);
        
        if (lastOrder == null) {
            return new ModelAndView(new RedirectView(urlService.generateRedirectUrl(FoUrls.HOME, requestUtil.getRequestData(request))));
        }

        final OrderViewBean orderViewBean = frontofficeViewBeanFactory.buildViewBeanOrder(requestUtil.getRequestData(request), lastOrder);

        Object[] params = { orderViewBean.getOrderNum(), orderViewBean.getExpectedDeliveryDate() };
        final String confirmationMessage = getSpecificMessage(ScopeWebMessage.CHECKOUT_SHOPPING_CART, "order_confirmation_message", params, locale);
        orderViewBean.setConfirmationMessage(confirmationMessage);

        modelAndView.addObject(ModelConstants.ORDER_VIEW_BEAN, orderViewBean);

        try {
            modelAndView.addObject(ModelConstants.ORDER_CONFIRMATION_PDF_URL, documentService.getOrderConfirmationWebPath(lastOrder));
            
        } catch (Exception e) {
            logger.error("Failed to create document order confirmation link", e);
        }
            
        modelAndView.addObject(ModelConstants.CHECKOUT_STEP, 5);

        overrideDefaultPageTitle(request, modelAndView, FoUrls.CART_ORDER_CONFIRMATION.getKey());

        return modelAndView;
    }

}