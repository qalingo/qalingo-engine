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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.BoPageConstants;
import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.CustomerForm;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;

/**
 * 
 */
@Controller("customerController")
public class CustomerController extends AbstractBusinessBackofficeController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/customer-list.html*", method = RequestMethod.GET)
	public ModelAndView orderList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CUSTOMER_LIST_VELOCITY_PAGE);
		
		final String contentText = getSpecificMessage(ScopeWebMessage.CUSTOMER, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
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
	
	@RequestMapping(value = "/customer-details.html*", method = RequestMethod.GET)
	public ModelAndView customerDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CUSTOMER_DETAILS_VELOCITY_PAGE);

		final String currentCustomerCode = request.getParameter(RequestConstants.REQUEST_PARAM_CUSTOMER_CODE);
		final Customer customer = customerService.getCustomerByCode(currentCustomerCode);
		
		if(customer != null){
			initCustomerDetailsPage(request, response, modelAndView, customer);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/customer-edit.html*", method = RequestMethod.GET)
	public ModelAndView customerEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CUSTOMER_FORM_VELOCITY_PAGE);
		
		// "customer.edit";

		final String currentCustomerCode = request.getParameter(RequestConstants.REQUEST_PARAM_CUSTOMER_CODE);
		final Customer customer = customerService.getCustomerById(currentCustomerCode);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		modelAndView.addObject(Constants.CUSTOMER_VIEW_BEAN, viewBeanFactory.buildCustomerViewBean(request, currentLocalization, customer));
		modelAndView.addObject(Constants.CUSTOMER_FORM, formFactory.buildCustomerForm(request, customer));
		return modelAndView;
	}
	
	@RequestMapping(value = "/customer-edit.html*", method = RequestMethod.POST)
	public ModelAndView customerEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid CustomerForm customerForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		// "customer.edit";
		final String currentCustomerCode = request.getParameter(RequestConstants.REQUEST_PARAM_CUSTOMER_CODE);
		final Customer customer = customerService.getCustomerById(currentCustomerCode);
		
		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CUSTOMER_FORM_VELOCITY_PAGE);
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			modelAndView.addObject(Constants.CUSTOMER_VIEW_BEAN, viewBeanFactory.buildCustomerViewBean(request, currentLocalization, customer));
			modelAndView.addObject(Constants.CUSTOMER_FORM, formFactory.buildCustomerForm(request, customer));
			return modelAndView;
		}
		
		// UPDATE CUSTOMER
//		webBackofficeService.updateCustomer(customer, customerForm);
		
		final String urlRedirect = backofficeUrlService.buildCustomerDetailsUrl(currentCustomerCode);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	private PagedListHolder<CustomerViewBean> initList(final HttpServletRequest request, String sessionKey) throws Exception{
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		PagedListHolder<CustomerViewBean> customerViewBeanPagedListHolder = new PagedListHolder<CustomerViewBean>();
		
		final List<CustomerViewBean> customerViewBeans = new ArrayList<CustomerViewBean>();

		final List<Customer> customers = customerService.findCustomers();
		for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
			Customer customer = (Customer) iterator.next();
			customerViewBeans.add(viewBeanFactory.buildCustomerViewBean(request, currentLocalization, customer));
		}
		customerViewBeanPagedListHolder = new PagedListHolder<CustomerViewBean>(customerViewBeans);
		customerViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, customerViewBeanPagedListHolder); 
        
        return customerViewBeanPagedListHolder;
	}
    
	protected void initCustomerDetailsPage(final HttpServletRequest request, final HttpServletResponse response, final ModelAndViewThemeDevice modelAndView, final Customer user) throws Exception{
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		// "customer.details";

		modelAndView.addObject(Constants.CUSTOMER_VIEW_BEAN, viewBeanFactory.buildCustomerViewBean(request, currentLocalization, user));
	}
}