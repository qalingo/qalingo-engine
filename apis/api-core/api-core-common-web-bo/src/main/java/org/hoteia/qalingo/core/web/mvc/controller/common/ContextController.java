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
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.ContextPojo;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.UrlPojo;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractBackofficeQalingoController;
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
public class ContextController extends AbstractBackofficeQalingoController {

	@RequestMapping(BoUrls.CONTEXT_URL)
	public ModelAndView context(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.CONTEXT.getVelocityPage());

        final RequestData requestData = requestUtil.getRequestData(request);
        final ContextPojo context = new ContextPojo();
        
        UrlPojo url = new UrlPojo();
        url.setCode(BoUrls.GET_CATALOG_AJAX.name());
        url.setUrl(backofficeUrlService.generateUrl(BoUrls.GET_CATALOG_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);

        url = new UrlPojo();
        url.setCode(BoUrls.GET_PRODUCT_LIST_AJAX.name());
        url.setUrl(backofficeUrlService.generateUrl(BoUrls.GET_PRODUCT_LIST_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);

        url = new UrlPojo();
        url.setCode(BoUrls.GET_PRODUCT_LIST_FOR_CATALOG_CATEGORY_AJAX.name());
        url.setUrl(backofficeUrlService.generateUrl(BoUrls.GET_PRODUCT_LIST_FOR_CATALOG_CATEGORY_AJAX, requestData));
        url.setMethod("GET");
        context.getUrls().add(url);
        
        url = new UrlPojo();
        url.setCode(BoUrls.SET_PRODUCT_LIST_FOR_CATALOG_CATEGORY_AJAX.name());
        url.setUrl(backofficeUrlService.generateUrl(BoUrls.SET_PRODUCT_LIST_FOR_CATALOG_CATEGORY_AJAX, requestData));
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