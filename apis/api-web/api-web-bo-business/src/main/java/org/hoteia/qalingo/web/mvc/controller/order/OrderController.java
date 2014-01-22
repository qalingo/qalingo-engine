/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.BoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.OrderCustomerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("orderController")
public class OrderController extends AbstractBusinessBackofficeController {

	@Autowired
	private OrderCustomerService orderCustomerService;

	@RequestMapping(value = BoUrls.ORDER_LIST_URL, method = RequestMethod.GET)
	public ModelAndView orderList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ORDER_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
		final String contentText = getSpecificMessage(ScopeWebMessage.ORDER, BoMessageKey.MAIN_CONTENT_TEXT, locale);
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		String url = request.getRequestURI();
		String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		String sessionKey = "PagedListHolder_Orders";
		
		PagedListHolder<OrderViewBean> orderViewBeanPagedListHolder = new PagedListHolder<OrderViewBean>();
		
        if(StringUtils.isEmpty(page)){
        	orderViewBeanPagedListHolder = initList(request, sessionKey);
    		
        } else {
        	orderViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (orderViewBeanPagedListHolder == null) { 
	        	orderViewBeanPagedListHolder = initList(request, sessionKey);
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
	
	@RequestMapping(value = BoUrls.ORDER_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView orderDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ORDER_DETAILS.getVelocityPage());

		final String orderNum = request.getParameter(RequestConstants.REQUEST_PARAMETER_ORDER_NUM);
		final OrderCustomer orderCustomer = orderCustomerService.getOrderByOrderNum(orderNum);
		
		if(orderCustomer != null){
			initOrderDetailsPage(request, model, modelAndView, orderCustomer);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	private PagedListHolder<OrderViewBean> initList(final HttpServletRequest request, String sessionKey) throws Exception{
		PagedListHolder<OrderViewBean> orderViewBeanPagedListHolder = new PagedListHolder<OrderViewBean>();
		
		final List<OrderViewBean> orderViewBeans = new ArrayList<OrderViewBean>();

		final List<OrderCustomer> orderCustomers = orderCustomerService.findOrders();
		for (Iterator<OrderCustomer> iterator = orderCustomers.iterator(); iterator.hasNext();) {
			OrderCustomer orderCustomer = (OrderCustomer) iterator.next();
			orderViewBeans.add(backofficeViewBeanFactory.buildOrderViewBean(requestUtil.getRequestData(request), orderCustomer));
		}
		orderViewBeanPagedListHolder = new PagedListHolder<OrderViewBean>(orderViewBeans);
		orderViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, orderViewBeanPagedListHolder); 
        return orderViewBeanPagedListHolder;
	}
    
	protected void initOrderDetailsPage(final HttpServletRequest request, final Model model, final ModelAndViewThemeDevice modelAndView, final OrderCustomer user) throws Exception{
		modelAndView.addObject(ModelConstants.ORDER_VIEW_BEAN, backofficeViewBeanFactory.buildOrderViewBean(requestUtil.getRequestData(request), user));
	}

}