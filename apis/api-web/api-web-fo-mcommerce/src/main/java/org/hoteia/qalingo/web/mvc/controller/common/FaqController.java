/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.viewbean.FaqViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("faqController")
public class FaqController extends AbstractMCommerceController {

    @RequestMapping(FoUrls.FAQ_URL)
    public ModelAndView faq(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.FAQ.getVelocityPage());
        
        final FaqViewBean faq = frontofficeViewBeanFactory.buildViewBeanFaq(requestUtil.getRequestData(request));
        modelAndView.addObject(ModelConstants.FAQ_VIEW_BEAN, faq);

        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.FAQ.getKey());

        return modelAndView;
    }

}
