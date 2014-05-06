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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.OrderCustomerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;

/**
 * 
 */
@Controller("customerOrderController")
public class CustomerOrderController extends AbstractCustomerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected OrderCustomerService orderCustomerService;
	
	@RequestMapping(FoUrls.PERSONAL_ORDER_LIST_URL)
	public ModelAndView customerWishList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_ORDER_LIST.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
        final Customer currentCustomer = requestData.getCustomer();
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(currentCustomer.getLogin());
		
		List<OrderCustomer> orderCustomers = orderCustomerService.findOrdersByCustomerId(reloadedCustomer.getId().toString());
		if(orderCustomers != null
				&& orderCustomers.size() > 0){
			String url = requestUtil.getCurrentRequestUrl(request);
			
			String sessionKey = "PagedListHolder_Search_List_Product_" + request.getSession().getId();
	        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
			PagedListHolder<OrderViewBean> orderViewBeanPagedListHolder;

	        if(StringUtils.isEmpty(page)){
	        	orderViewBeanPagedListHolder = initList(request, sessionKey, orderCustomers, new PagedListHolder<OrderViewBean>());
	        } else {
		        orderViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
		        if (orderViewBeanPagedListHolder == null) { 
		        	orderViewBeanPagedListHolder = initList(request, sessionKey, orderCustomers, orderViewBeanPagedListHolder);
		        }
		        int pageTarget = new Integer(page).intValue() - 1;
		        int pageCurrent = orderViewBeanPagedListHolder.getPage();
		        if (pageCurrent < pageTarget) { 
		        	for (int i = pageCurrent; i < pageTarget; i++) {
		        		orderViewBeanPagedListHolder.nextPage(); 
					}
		        } else if (pageCurrent > pageTarget) { 
		        	for (int i = pageTarget; i < pageCurrent; i++) {
			        	orderViewBeanPagedListHolder.previousPage(); 
					}
		        } 
	        }
			model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
			model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, orderViewBeanPagedListHolder);
		}
		
        return modelAndView;
	}

	@RequestMapping(FoUrls.PERSONAL_ORDER_DETAILS_URL)
	public ModelAndView removeFromWishlist(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_ORDER_DETAILS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final String orderCustomerId = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ORDER_GUID);
		if(StringUtils.isNotEmpty(orderCustomerId)){
			final OrderCustomer orderCustomer = orderCustomerService.getOrderById(orderCustomerId);
			if(orderCustomer != null){
				// SANITY CHECK

		        final Customer currentCustomer = requestData.getCustomer();
				
				// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
				// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
				final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(currentCustomer.getLogin());
				
				List<OrderCustomer> orderCustomers = orderCustomerService.findOrdersByCustomerId(reloadedCustomer.getId().toString());
				if(orderCustomers.contains(orderCustomer)){
			        return modelAndView;
				} else {
					logger.warn("Customer, " + reloadedCustomer.getId() + "/" + reloadedCustomer.getEmail() + ", try to acces to a customer order, " + orderCustomerId + ", which does not belong");
				}
			}
		}
		final String url = requestUtil.getLastRequestUrl(request);
		return new ModelAndView(new RedirectView(url));
	}

	protected PagedListHolder<OrderViewBean> initList(final HttpServletRequest request, final String sessionKey, final List<OrderCustomer> orders,
			PagedListHolder<OrderViewBean> orderViewBeanPagedListHolder) throws Exception {
		List<OrderViewBean> orderViewBeans = frontofficeViewBeanFactory.buildListViewBeanOrder(requestUtil.getRequestData(request), orders);
		orderViewBeanPagedListHolder = new PagedListHolder<OrderViewBean>(orderViewBeans);
		orderViewBeanPagedListHolder.setPageSize(Constants.PAGINATION_DEFAULT_PAGE_SIZE); 
        request.getSession().setAttribute(sessionKey, orderViewBeanPagedListHolder);
        return orderViewBeanPagedListHolder;
	}

}