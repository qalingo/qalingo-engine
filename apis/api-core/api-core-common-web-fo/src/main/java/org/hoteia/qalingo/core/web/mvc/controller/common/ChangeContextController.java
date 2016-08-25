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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.resolver.RequestData;
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
        String redirectUrl = urlService.generateUrl(getTargetUrl(requestData), true, true, requestData);
        RedirectView redirectView = new RedirectView(redirectUrl);
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
	}
	
	@RequestMapping(FoUrls.CHANGE_CONTEXT_URL)
	public ModelAndView changeContext(final HttpServletRequest request) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        String redirectUrl = urlService.generateUrl(getTargetUrl(requestData), true, true, requestData);
        RedirectView redirectView = new RedirectView(redirectUrl);
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
	}
	
	protected String getTargetUrl(final RequestData requestData) throws Exception{
	    final HttpServletRequest request = requestData.getRequest();
	    String fallbackUrl = urlService.generateRedirectUrl(FoUrls.HOME, requestData);
        final String currentRequestUrl = requestUtil.getRequestUrlAfterChangeContext(request, fallbackUrl);
        String currentRequestUri = currentRequestUrl.replace(request.getContextPath(), "");
        if (currentRequestUri.startsWith("/")) {
            currentRequestUri = currentRequestUri.substring(1, currentRequestUri.length());
        }
        String[] uriSegments = currentRequestUri.toString().split("/");
        String url = "";
        int uriSegmentCount = 0;
        String seoSegmentMain = urlService.getFullPrefixUrl(requestData, true);
        if (seoSegmentMain.startsWith("/")) {
            seoSegmentMain = seoSegmentMain.substring(1, seoSegmentMain.length());
        }
        if (StringUtils.isNotEmpty(seoSegmentMain)) {
            String[] seoSegmentSegments = seoSegmentMain.split("/");
            // ALSO REMOVE DEFAULT SEO SEGMENT
            for (int i = 0; i < seoSegmentSegments.length; i++) {
                uriSegmentCount++;
            }
        }
        // SEO URL : Keep the last part
        int uriSegmentIt = uriSegmentCount + 1;
        for (int i = uriSegmentIt; i < uriSegments.length; i++) {
            url = url + "/" + uriSegments[i];
        }
        if (StringUtils.isEmpty(url)) {
            url = currentRequestUrl;
        }
        return url;
	}
}
