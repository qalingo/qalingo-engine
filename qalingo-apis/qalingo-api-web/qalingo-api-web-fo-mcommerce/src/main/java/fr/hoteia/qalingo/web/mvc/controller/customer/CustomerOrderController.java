/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.customer;

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
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.service.OrderService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderViewBean;

/**
 * 
 */
@Controller("customerOrderController")
public class CustomerOrderController extends AbstractCustomerController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected OrderService orderService;
	
	@RequestMapping(FoUrls.PERSONAL_ORDER_LIST_URL)
	public ModelAndView customerWishList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_ORDER_LIST.getVelocityPage());
		
		final Customer customer = requestUtil.getCurrentCustomer(request);
		
		List<Order> orders = orderService.findOrdersByCustomerId(customer.getId().toString());
		if(orders != null
				&& orders.size() > 0){
			String url = requestUtil.getCurrentRequestUrl(request);
			
			String sessionKey = "PagedListHolder_Search_List_Product_" + request.getSession().getId();
	        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
			PagedListHolder<OrderViewBean> orderViewBeanPagedListHolder;

	        if(StringUtils.isEmpty(page)){
	        	orderViewBeanPagedListHolder = initList(request, sessionKey, orders, new PagedListHolder<OrderViewBean>());
	        } else {
		        orderViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
		        if (orderViewBeanPagedListHolder == null) { 
		        	orderViewBeanPagedListHolder = initList(request, sessionKey, orders, orderViewBeanPagedListHolder);
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
		
		final String orderId = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ORDER_ID);
		if(StringUtils.isNotEmpty(orderId)){
			final Order order = orderService.getOrderById(orderId);
			if(order != null){
				// SANITY CHECK
				final Customer customer = requestUtil.getCurrentCustomer(request);
				List<Order> orders = orderService.findOrdersByCustomerId(customer.getId().toString());
				if(orders.contains(order)){
			        return modelAndView;
				} else {
					LOG.warn("Customer, " + customer.getId() + "/" + customer.getEmail() + ", try to acces to a customer order, " + orderId + ", which does not belong");
				}
			}
		}
		final String url = requestUtil.getLastRequestUrl(request);
		return new ModelAndView(new RedirectView(url));
	}

	protected PagedListHolder<OrderViewBean> initList(final HttpServletRequest request, final String sessionKey, final List<Order> orders,
			PagedListHolder<OrderViewBean> orderViewBeanPagedListHolder) throws Exception {
		List<OrderViewBean> orderViewBeans = viewBeanFactory.buildOrderViewBeans(requestUtil.getRequestData(request), orders);
		orderViewBeanPagedListHolder = new PagedListHolder<OrderViewBean>(orderViewBeans);
		orderViewBeanPagedListHolder.setPageSize(Constants.PAGINATION_DEFAULT_PAGE_SIZE); 
        request.getSession().setAttribute(sessionKey, orderViewBeanPagedListHolder);
        return orderViewBeanPagedListHolder;
	}

}