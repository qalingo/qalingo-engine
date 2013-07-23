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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.service.OrderService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderViewBean;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller("customerOrderController")
public class CustomerOrderController extends AbstractMCommerceController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected WebCommerceService webCommerceService;
	
	@Autowired
    protected OrderService orderService;
	
	@RequestMapping("/customer-order-list.html*")
	public ModelAndView customerWishList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-order-list");
		
		// "customer.order.list";
		final Customer customer = requestUtil.getCurrentCustomer(request);
		
		List<Order> orders = orderService.findOrdersByCustomerId(customer.getId().toString());
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
		modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
		modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, orderViewBeanPagedListHolder);
		
        return modelAndView;
	}

	@RequestMapping("/customer-order-details.html*")
	public ModelAndView removeFromWishlist(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-order-details");
		
		final String orderId = request.getParameter(RequestConstants.REQUEST_PARAM_CUSTOMER_ORDER_ID);
		if(StringUtils.isNotEmpty(orderId)){
			final Order order = orderService.getOrderById(orderId);
			if(order != null){

				// SANITY CHECK
				final Customer customer = requestUtil.getCurrentCustomer(request);
				List<Order> orders = orderService.findOrdersByCustomerId(customer.getId().toString());
				if(orders.contains(order)){
					
					
					// "customer.order.details";
					
					
					
					
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
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		List<OrderViewBean> orderViewBeans = viewBeanFactory.buildOrderViewBeans(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, orders);
		orderViewBeanPagedListHolder = new PagedListHolder<OrderViewBean>(orderViewBeans);
		orderViewBeanPagedListHolder.setPageSize(Constants.PAGINATION_DEFAULT_PAGE_SIZE); 
        request.getSession().setAttribute(sessionKey, orderViewBeanPagedListHolder);
        return orderViewBeanPagedListHolder;
	}
}