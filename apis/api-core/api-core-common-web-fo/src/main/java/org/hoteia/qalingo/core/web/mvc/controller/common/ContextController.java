/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.web.mvc.controller.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.VelocityPageContextDataPojo;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.UrlPojo;
import org.hoteia.qalingo.core.service.UrlService;
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

        final VelocityPageContextDataPojo context = buildContext(requestData);
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

    protected VelocityPageContextDataPojo buildContext(RequestData requestData) {
        final VelocityPageContextDataPojo context = new VelocityPageContextDataPojo();

        // TODO : move this value in a EngineSetting
        context.setCartMaxItemQuantity(5);

        addUrlToContext(requestData, context, FoUrls.NAVIGATOR_GEOLOCATION_AJAX, "POST");
        addUrlToContext(requestData, context, FoUrls.ADD_TO_WISHLIST_AJAX, "GET");
        addUrlToContext(requestData, context, FoUrls.GET_CART_AJAX, "GET");
        addUrlToContext(requestData, context, FoUrls.ADD_TO_CART_AJAX, "GET");
        addUrlToContext(requestData, context, FoUrls.ADD_TO_CART_FROM_STORE_AJAX, "GET");
        addUrlToContext(requestData, context, FoUrls.UPDATE_CART_ITEM_AJAX, "GET");
        addUrlToContext(requestData, context, FoUrls.DELETE_CART_ITEM_AJAX, "GET");
        addUrlToContext(requestData, context, FoUrls.SET_SHIPPING_ADDRESS_AJAX, "GET");
        addUrlToContext(requestData, context, FoUrls.SET_BILLING_ADDRESS_AJAX, "GET");
        addUrlToContext(requestData, context, FoUrls.SET_DELIVERY_METHOD_AJAX, "GET");

        return context;
    }

    protected void addUrlToContext(RequestData requestData, VelocityPageContextDataPojo context, FoUrls foUrls, String post) {
        UrlPojo url = new UrlPojo();
        String code = foUrls.name();
        url.setCode(code);
        url.setUrl(urlService.generateUrl(foUrls, requestData));
        url.setMethod(post);
        context.getUrls().put(code, url);
    }

}