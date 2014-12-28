/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("customerProductCommentController")
public class CustomerProductCommentController extends AbstractCustomerController {

    @Autowired
    protected ProductService productService;
    
	@RequestMapping(FoUrls.PERSONAL_PRODUCT_COMMENT_LIST_URL)
	public ModelAndView customerProductComments(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_PRODUCT_COMMENT_LIST.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
        final Customer customer = requestData.getCustomer();
		
		List<ProductMarketingCustomerComment> productMarketingCustomerComments = productService.findProductMarketingCustomerCommentsByCustomerId(customer.getId());
        model.addAttribute(ModelConstants.PRODUCT_MARKETING_COMMENTS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanCustomerProductComments(requestData, productMarketingCustomerComments));

        Object[] params = { customer.getLastname(), customer.getFirstname() };
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.PERSONAL_PRODUCT_COMMENT_LIST.getKey(), params);
        
        return modelAndView;
	}

	@RequestMapping(FoUrls.PERSONAL_REMOVE_PRODUCT_COMMENT_LIST_URL)
	public ModelAndView removeProductComment(final HttpServletRequest request, final Model model) throws Exception {
		
		// TODO

		final String url = urlService.generateUrl(FoUrls.PERSONAL_PRODUCT_COMMENT_LIST, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(url));
	}
	
	@RequestMapping(FoUrls.PERSONAL_ADD_PRODUCT_COMMENT_LIST_URL)
	public ModelAndView addProductComment(final HttpServletRequest request, final Model model) throws Exception {
		
		// TODO
		
		final String url = urlService.generateUrl(FoUrls.PERSONAL_PRODUCT_COMMENT_LIST, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(url));
	}

}