/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.customer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.BoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.web.mvc.form.CustomerForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("customerController")
public class CustomerController extends AbstractBusinessBackofficeController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = BoUrls.CUSTOMER_LIST_URL, method = RequestMethod.GET)
	public ModelAndView customerList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.CUSTOMER_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
		final String contentText = getSpecificMessage(ScopeWebMessage.CUSTOMER, BoMessageKey.MAIN_CONTENT_TEXT, locale);
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		String url = request.getRequestURI();
		String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		String sessionKey = "PagedListHolder_Customers";
		
		PagedListHolder<CustomerViewBean> customerViewBeanPagedListHolder = new PagedListHolder<CustomerViewBean>();
		
        if(StringUtils.isEmpty(page)){
        	customerViewBeanPagedListHolder = initList(request, sessionKey);
    		
        } else {
        	customerViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (customerViewBeanPagedListHolder == null) { 
	        	customerViewBeanPagedListHolder = initList(request, sessionKey);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = customerViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		customerViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
	        		customerViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
		modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
		modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, customerViewBeanPagedListHolder);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.CUSTOMER_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView customerDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.CUSTOMER_DETAILS.getVelocityPage());

		final String currentCustomerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_CODE);
		final Customer customer = customerService.getCustomerByCode(currentCustomerCode);
		
		if(customer != null){
			initCustomerDetailsPage(request, model, modelAndView, customer);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.CUSTOMER_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView customerEdit(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.CUSTOMER_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final String currentCustomerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_CODE);
		final Customer customer = customerService.getCustomerById(currentCustomerCode);
		modelAndView.addObject(Constants.CUSTOMER_VIEW_BEAN, backofficeViewBeanFactory.buildCustomerViewBean(requestData, customer));
		modelAndView.addObject(Constants.CUSTOMER_FORM, backofficeFormFactory.buildCustomerForm(requestData, customer));
		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.CUSTOMER_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView customerEdit(final HttpServletRequest request, final Model model, @Valid CustomerForm customerForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		final String currentCustomerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_CODE);
		final Customer customer = customerService.getCustomerById(currentCustomerCode);
		
		if (result.hasErrors()) {
			return customerEdit(request, model);
		}
		
		// UPDATE CUSTOMER
//		webBackofficeService.updateCustomer(customer, customerForm);
		
		final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.CUSTOMER_DETAILS, requestUtil.getRequestData(request), customer);;
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	private PagedListHolder<CustomerViewBean> initList(final HttpServletRequest request, String sessionKey) throws Exception{
		PagedListHolder<CustomerViewBean> customerViewBeanPagedListHolder = new PagedListHolder<CustomerViewBean>();
		
		final List<CustomerViewBean> customerViewBeans = new ArrayList<CustomerViewBean>();

		final List<Customer> customers = customerService.findCustomers();
		for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
			Customer customer = (Customer) iterator.next();
			customerViewBeans.add(backofficeViewBeanFactory.buildCustomerViewBean(requestUtil.getRequestData(request), customer));
		}
		customerViewBeanPagedListHolder = new PagedListHolder<CustomerViewBean>(customerViewBeans);
		customerViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, customerViewBeanPagedListHolder); 
        
        return customerViewBeanPagedListHolder;
	}
    
	protected void initCustomerDetailsPage(final HttpServletRequest request, final Model model, final ModelAndViewThemeDevice modelAndView, final Customer user) throws Exception{
		modelAndView.addObject(Constants.CUSTOMER_VIEW_BEAN, backofficeViewBeanFactory.buildCustomerViewBean(requestUtil.getRequestData(request), user));
	}
}