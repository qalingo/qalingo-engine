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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Change context
 */
@Controller("changeContextController")
public class ChangeContextController extends AbstractFrontofficeQalingoController {

	@RequestMapping(FoUrls.CHANGE_LANGUAGE_URL)
	public ModelAndView changeLanguage(final HttpServletRequest request) throws Exception {
	    final RequestData requestData = requestUtil.getRequestData(request);
        String redirectUrl = urlService.generateUrl(getTargetUrl(requestData), true, requestData);
        RedirectView redirectView = new RedirectView(redirectUrl);
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
	}
	
	@RequestMapping(FoUrls.CHANGE_CONTEXT_URL)
	public ModelAndView changeContext(final HttpServletRequest request) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        String redirectUrl = urlService.generateUrl(getTargetUrl(requestData), true, requestData);
        RedirectView redirectView = new RedirectView(redirectUrl);
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
	}
	
	private String getTargetUrl(final RequestData requestData) throws Exception{
	    final HttpServletRequest request = requestData.getRequest();
	    final Locale locale = requestData.getLocale();
        List<String> excludedPatterns = requestUtil.getCommonExcludedPatterns();
        excludedPatterns.add(FoUrls.CHANGE_LANGUAGE.getUrlWithoutWildcard());
        String fallbackUrl = urlService.generateUrl(FoUrls.HOME, requestData);
        final String lastRequestUrl = requestUtil.getLastRequestUrl(request, excludedPatterns, fallbackUrl);
        String lastRequestUri = lastRequestUrl.replace(request.getContextPath(), "");
        if (lastRequestUri.startsWith("/")) {
            lastRequestUri = lastRequestUri.substring(1, lastRequestUri.length());
        }
        String[] uriSegments = lastRequestUri.toString().split("/");
        String url = "";
        int uriSegmentCount = 4;
        String seoSegmentMain = urlService.getSeoSegmentMain(locale);
        if(StringUtils.isNotEmpty(seoSegmentMain)){
            // ALSO REMOVE DEFAULT SEO SEGMENT
            uriSegmentCount = 5;
        }
        if (uriSegments.length > uriSegmentCount) {
            // SEO URL : Keep the last part
            int uriSegmentIt = uriSegmentCount + 1;
            for (int i = uriSegmentIt; i < uriSegments.length; i++) {
                url = url + "/" + uriSegments[i];
            }
        }
        return url;
	}
}
