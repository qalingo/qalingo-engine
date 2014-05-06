/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.ContextPojo;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.UrlPojo;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
@Controller("contextController")
public class ContextController extends AbstractFrontofficeQalingoController {

	@RequestMapping(FoUrls.CONTEXT_URL)
	public ModelAndView context(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CONTEXT.getVelocityPage());

        final RequestData requestData = requestUtil.getRequestData(request);
        final ContextPojo context = new ContextPojo();
        
        // TODO : move this value in a EngineSetting
        context.setCartMaxItemQuantity(5);
        
        UrlPojo url = new UrlPojo();
        
        url = new UrlPojo();
        url.setCode(FoUrls.ADD_TO_WISHLIST_AJAX.name());
        url.setUrl(urlService.generateUrl(FoUrls.ADD_TO_WISHLIST_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);
        
        url = new UrlPojo();
        url.setCode(FoUrls.GET_CART_AJAX.name());
        url.setUrl(urlService.generateUrl(FoUrls.GET_CART_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);

        url = new UrlPojo();
        url.setCode(FoUrls.ADD_TO_CART_AJAX.name());
        url.setUrl(urlService.generateUrl(FoUrls.ADD_TO_CART_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);
        
        url = new UrlPojo();
        url.setCode(FoUrls.UPDATE_CART_ITEM_AJAX.name());
        url.setUrl(urlService.generateUrl(FoUrls.UPDATE_CART_ITEM_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);
        
        url = new UrlPojo();
        url.setCode(FoUrls.DELETE_CART_ITEM_AJAX.name());
        url.setUrl(urlService.generateUrl(FoUrls.DELETE_CART_ITEM_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);
        
        url = new UrlPojo();
        url.setCode(FoUrls.SET_SHIPPING_ADDRESS_AJAX.name());
        url.setUrl(urlService.generateUrl(FoUrls.SET_SHIPPING_ADDRESS_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);
        
        url = new UrlPojo();
        url.setCode(FoUrls.SET_BILLING_ADDRESS_AJAX.name());
        url.setUrl(urlService.generateUrl(FoUrls.SET_BILLING_ADDRESS_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);
        
        url = new UrlPojo();
        url.setCode(FoUrls.SET_DELIVERY_METHOD_AJAX.name());
        url.setUrl(urlService.generateUrl(FoUrls.SET_DELIVERY_METHOD_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            String contextJson = mapper.writeValueAsString(context);
            model.addAttribute(ModelConstants.CONTEXT_JSON, contextJson);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage());
        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
	}

}