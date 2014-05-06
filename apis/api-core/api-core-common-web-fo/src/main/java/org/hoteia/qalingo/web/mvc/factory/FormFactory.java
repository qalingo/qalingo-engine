/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.factory;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.web.mvc.form.CartForm;
import org.hoteia.qalingo.web.mvc.form.ContactForm;
import org.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import org.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import org.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import org.hoteia.qalingo.web.mvc.form.FollowUsForm;
import org.hoteia.qalingo.web.mvc.form.PaymentForm;
import org.hoteia.qalingo.web.mvc.form.ProductCommentForm;
import org.hoteia.qalingo.web.mvc.form.QuickSearchForm;
import org.hoteia.qalingo.web.mvc.form.RetailerCommentForm;
import org.hoteia.qalingo.web.mvc.form.RetailerContactForm;
import org.hoteia.qalingo.web.mvc.form.SearchForm;

public interface FormFactory {

	ContactForm buildContactForm(RequestData requestData) throws Exception;

	SearchForm buildSearchForm(RequestData requestData) throws Exception;

	QuickSearchForm buildQuickSearchForm(RequestData requestData) throws Exception;

	FollowUsForm buildFollowUsForm(RequestData requestData) throws Exception;
	 
	CreateAccountForm buildCreateAccountForm(RequestData requestData) throws Exception;
	 
	CustomerEditForm buildCustomerEditForm(RequestData requestData, Customer customer) throws Exception;
	 
	CustomerAddressForm buildCustomerAddressForm(RequestData requestData, CustomerAddress customerAddress) throws Exception;

    PaymentForm buildPaymentForm(RequestData requestData) throws Exception;

	CartForm buildCartForm(RequestData requestData) throws Exception;
	 
	ProductCommentForm buildProductCommentForm(RequestData requestData, ProductMarketing productMarketing) throws Exception;
	
    RetailerContactForm buildRetailerContactForm(RequestData requestData, Retailer retailer) throws Exception;
    
    RetailerCommentForm buildRetailerCommentForm(RequestData requestData, Retailer retailer) throws Exception;
}