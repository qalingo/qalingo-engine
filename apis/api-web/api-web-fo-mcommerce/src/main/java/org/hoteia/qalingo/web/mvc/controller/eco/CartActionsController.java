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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("cartActionsController")
public class CartActionsController extends AbstractMCommerceController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(FoUrls.CART_MULTIPLE_ADD_PRODUCT_URL)
    public ModelAndView multipleAddToCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String skuCodes = request.getParameter(RequestConstants.REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_SKU_CODES);
        final String quantities = request.getParameter(RequestConstants.REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_QUANTITIES);
        if(StringUtils.isNotEmpty(skuCodes)){
            try {
                String[] splitSkuCodes = skuCodes.split(";");
                String[] splitQuantities = null;
                if(StringUtils.isNotEmpty(quantities)){
                    splitQuantities = skuCodes.split(";");
                }
                for (int i = 0; i < splitSkuCodes.length; i++) {
                    String skuCode = splitSkuCodes[i];
                    int quantity = 1;
                    try {
                        if(splitQuantities != null){
                            quantity = Integer.parseInt(splitQuantities[i]);
                        }
                        webManagementService.updateCart(requestData, skuCode, quantity);
                    } catch (Exception e) {
                        logger.error("Error to add product sku to cart, skuCode:" + skuCode, e);
                    }
                }
                
            } catch (Exception e) {
                logger.error("Error to add product sku to cart.", e);
            }
            
            final String url = urlService.generateUrl(FoUrls.CART_DETAILS, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(url));
        }
        List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add(FoUrls.CART_ADD_PRODUCT_URL);
        String fallbackUrl = urlService.generateUrl(FoUrls.HOME, requestData);
        final String lastRequestUrl = requestUtil.getLastRequestUrl(request, excludedPatterns, fallbackUrl);
        return new ModelAndView(new RedirectView(lastRequestUrl));
    }
    
    @RequestMapping(FoUrls.CART_ADD_PRODUCT_URL)
    public ModelAndView addToCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
        final String categoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        if(StringUtils.isNotEmpty(skuCode)){
            try {
                if(StringUtils.isNotEmpty(categoryCode)){
                    webManagementService.updateCart(requestData, categoryCode, skuCode, 1);
                    
                } else {
                    webManagementService.updateCart(requestData, skuCode, 1);
                }
                
            } catch (Exception e) {
                logger.error("Error to add product sku to cart, skuCode:" + skuCode, e);
            }
            
            final String url = urlService.generateUrl(FoUrls.CART_DETAILS, requestUtil.getRequestData(request));
            
            return new ModelAndView(new RedirectView(url));
        }
        List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add(FoUrls.CART_ADD_PRODUCT_URL);
        String fallbackUrl = urlService.generateUrl(FoUrls.HOME, requestData);
        final String lastRequestUrl = requestUtil.getLastRequestUrl(request, excludedPatterns, fallbackUrl);
        return new ModelAndView(new RedirectView(lastRequestUrl));
    }

    @RequestMapping(FoUrls.CART_REMOVE_ITEM_URL)
    public ModelAndView removeFromCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
        webManagementService.deleteCartItem(requestData, productSkuCode);

        return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.CART_DETAILS, requestUtil.getRequestData(request))));
    }
    
}