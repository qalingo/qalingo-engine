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
import org.springframework.web.bind.annotation.ModelAttribute;
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
        
		displayList(request, model, requestData);
		
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.CUSTOMER_LIST.getKey(), null);

        model.addAttribute(ModelConstants.URL_ADD, backofficeUrlService.generateUrl(BoUrls.CUSTOMER_ADD, requestData));

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.CUSTOMER_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView customerDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.CUSTOMER_DETAILS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		
		final String customerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_CODE);
		final Customer customer = customerService.getCustomerByCode(customerCode);
		
		if(customer != null){
	        modelAndView.addObject(ModelConstants.CUSTOMER_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanCustomer(requestUtil.getRequestData(request), customer));
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}

		modelAndView.addObject(ModelConstants.CUSTOMER_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanCustomer(requestData, customer));
		
        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.CUSTOMER_LIST, requestData));
		
        Object[] params = {customer.getLastname() + " " + customer.getFirstname()};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.CUSTOMER_DETAILS.getKey(), params);

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.CUSTOMER_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView customerEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.CUSTOMER_FORM) CustomerForm customerForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.CUSTOMER_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		
        final String customerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_CODE);
        if(StringUtils.isNotEmpty(customerCode)){
            // EDIT MODE
            final Customer customer = customerService.getCustomerByCode(customerCode);

            CustomerViewBean customerViewBean = backofficeViewBeanFactory.buildViewBeanCustomer(requestData, customer);
            request.setAttribute(ModelConstants.CUSTOMER_VIEW_BEAN, customerViewBean);

            Object[] params = {customer.getLastname() + " " + customer.getFirstname()};
            initPageTitleAndMainContentTitle(request, modelAndView,  BoUrls.CUSTOMER_EDIT.getKey(), params);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.CUSTOMER_DETAILS, requestData, customer));
        } else {
            // ADD MODE

            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.CUSTOMER_ADD.getKey(), null);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.CUSTOMER_LIST, requestData));
        }
        
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.CUSTOMER_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView customerEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.CUSTOMER_FORM) CustomerForm customerForm,
								BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
		if (result.hasErrors()) {
			return customerEdit(request, model, customerForm);
		}
		
		Customer customer = null;
        if(StringUtils.isNotEmpty(customerForm.getId())){
            customer = customerService.getCustomerById(customerForm.getId());
        }
		
        try {
            // CREATE OR UPDATE CUSTOMER
            webBackofficeService.createOrUpdateCustomer(customer, customerForm);
            
            if(customer == null){
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.CUSTOMER, "create_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.CUSTOMER_LIST, requestUtil.getRequestData(request));
                return new ModelAndView(new RedirectView(urlRedirect));
                
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.CUSTOMER, "update_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.CUSTOMER_DETAILS, requestUtil.getRequestData(request), customer);
                return new ModelAndView(new RedirectView(urlRedirect));
            }
            
        } catch (Exception e) {
            addMessageError(result, null, "login", "login", getSpecificMessage(ScopeWebMessage.CUSTOMER, "create_or_update_message", locale));
            logger.error("Can't save or update Customer:" + customerForm.getId() + "/" + customerForm.getLogin(), e);
            return customerEdit(request, model, customerForm);
        }
	}

    /**
     * 
     */
    @ModelAttribute(ModelConstants.CUSTOMER_FORM)
    protected CustomerForm initCustomerForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String customerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_CODE);
        if(StringUtils.isNotEmpty(customerCode)){
            final Customer customerEdit = customerService.getCustomerByCode(customerCode);
            return backofficeFormFactory.buildCustomerForm(requestData, customerEdit);
        }
        
        return backofficeFormFactory.buildCustomerForm(requestData, null);
    }
    
    protected void displayList(final HttpServletRequest request, final Model model, final RequestData requestData) throws Exception {
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
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, customerViewBeanPagedListHolder);
    }
    
	private PagedListHolder<CustomerViewBean> initList(final HttpServletRequest request, String sessionKey) throws Exception{
		PagedListHolder<CustomerViewBean> customerViewBeanPagedListHolder = new PagedListHolder<CustomerViewBean>();
		
		final List<CustomerViewBean> customerViewBeans = new ArrayList<CustomerViewBean>();

		final List<Customer> customers = customerService.findCustomers();
		for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
			Customer customer = (Customer) iterator.next();
			customerViewBeans.add(backofficeViewBeanFactory.buildViewBeanCustomer(requestUtil.getRequestData(request), customer));
		}
		customerViewBeanPagedListHolder = new PagedListHolder<CustomerViewBean>(customerViewBeans);
		customerViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, customerViewBeanPagedListHolder); 
        
        return customerViewBeanPagedListHolder;
	}
    
}