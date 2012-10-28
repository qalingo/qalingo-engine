/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.CustomerAddress;

public interface FormFactory {

	 void buildContactUsForm(HttpServletRequest request, ModelAndView modelAndView) throws Exception;

	 void buildSearchForm(HttpServletRequest request, ModelAndView modelAndView) throws Exception;

	 void buildQuickSearchForm(HttpServletRequest request, ModelAndView modelAndView) throws Exception;

	 void buildFollowUsForm(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	 
	 void buildCustomerCreateAccountForm(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	 
	 void buildCustomerEditAccountForm(HttpServletRequest request, Customer customer, ModelAndView modelAndView) throws Exception;
	 
	 void buildCustomerAddressForm(HttpServletRequest request, ModelAndView modelAndView) throws Exception;

	 void buildCustomerAddressForm(HttpServletRequest request, ModelAndView modelAndView, CustomerAddress customerAddress) throws Exception;

	 void buildCartForm(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	 
	 void buildPaymentForm(HttpServletRequest request, ModelAndView modelAndView) throws Exception;

}
