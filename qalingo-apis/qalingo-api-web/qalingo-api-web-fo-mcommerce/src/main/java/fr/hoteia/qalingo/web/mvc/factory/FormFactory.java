/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.web.mvc.form.CartForm;
import fr.hoteia.qalingo.web.mvc.form.ContactForm;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.mvc.form.PaymentForm;
import fr.hoteia.qalingo.web.mvc.form.QuickSearchForm;
import fr.hoteia.qalingo.web.mvc.form.SearchForm;

public interface FormFactory {

	ContactForm buildContactForm(HttpServletRequest request) throws Exception;

	SearchForm buildSearchForm(HttpServletRequest request) throws Exception;

	QuickSearchForm buildQuickSearchForm(HttpServletRequest request) throws Exception;

	FollowUsForm buildFollowUsForm(HttpServletRequest request) throws Exception;
	 
	CreateAccountForm buildCreateAccountForm(HttpServletRequest request) throws Exception;
	 
	CustomerEditForm buildCustomerEditForm(HttpServletRequest request, Customer customer) throws Exception;
	 
	CustomerAddressForm buildCustomerAddressForm(HttpServletRequest request, CustomerAddress customerAddress) throws Exception;

	CartForm buildCartForm(HttpServletRequest request) throws Exception;
	 
	PaymentForm buildPaymentForm(HttpServletRequest request) throws Exception;

}