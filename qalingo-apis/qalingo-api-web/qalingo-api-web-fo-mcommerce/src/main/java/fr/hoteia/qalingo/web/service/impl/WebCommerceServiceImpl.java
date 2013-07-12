/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.Cart;
import fr.hoteia.qalingo.core.domain.CartItem;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.OrderItem;
import fr.hoteia.qalingo.core.email.bean.ContactUsEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.NewsletterRegistrationConfirmationEmailBean;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.CustomerGroupService;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.service.EmailService;
import fr.hoteia.qalingo.core.service.OrderService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.form.ContactUsForm;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import fr.hoteia.qalingo.web.service.WebCommerceService;

@Service("webCommerceService")
@Transactional
public class WebCommerceServiceImpl implements WebCommerceService {

	@Autowired
    protected EmailService emailService;
	
	@Autowired
    protected CustomerService customerService;
	
	@Autowired
    protected OrderService orderService;
	
	@Autowired
    protected CustomerGroupService customerGroupService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
    /**
     * 
     */
	public Customer buildAndSaveNewCustomer(final HttpServletRequest request, final Market market, final MarketArea marketArea, final CreateAccountForm createAccountForm) throws Exception {
		Customer customer = new Customer();
		
		customer.setLogin(createAccountForm.getEmail());
		customer.setFirstname(createAccountForm.getFirstname());
		customer.setLastname(createAccountForm.getLastname());
		customer.setPassword(securityUtil.generatePassword(createAccountForm.getPassword()));
		
		customer.setDefaultLocale(marketArea.getDefaultLocalization().getCode());
		customer.setActive(true);
		customer.setDateCreate(new Date());
		customer.setDateUpdate(new Date());

		customer.setEmail(createAccountForm.getEmail());

		CustomerAddress defaultAddress = new CustomerAddress();
		if(StringUtils.isNotEmpty(createAccountForm.getAddressName())){
			defaultAddress.setAddressName(createAccountForm.getAddressName());
		} else {
			defaultAddress.setAddressName(createAccountForm.getAddress1() + "(" + createAccountForm.getCity() + ")");
		}
		defaultAddress.setTitle(createAccountForm.getTitle());
		defaultAddress.setLastname(createAccountForm.getLastname());
		defaultAddress.setFirstname(createAccountForm.getFirstname());
		defaultAddress.setAddress1(createAccountForm.getAddress1());
		defaultAddress.setAddress2(createAccountForm.getAddress2());
		defaultAddress.setAddressAdditionalInformation(createAccountForm.getAddressAdditionalInformation());
		defaultAddress.setPostalCode(createAccountForm.getPostalCode());
		defaultAddress.setCity(createAccountForm.getCity());
		defaultAddress.setStateCode(createAccountForm.getStateCode());
		defaultAddress.setAreaCode(createAccountForm.getAreaCode());
		defaultAddress.setCountryCode(createAccountForm.getCountryCode());
		defaultAddress.setDefaultBilling(true);
		defaultAddress.setDefaultShipping(true);
		Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
		addresses.add(defaultAddress);
		customer.setAddresses(addresses);
		
		CustomerGroup customerGroup = customerGroupService.getCustomerGroupByCode("GROUP_FO_CUSTOMER");
		customer.getCustomerGroups().add(customerGroup);
		
		customerService.saveOrUpdateCustomer(customer);
		
		customer = customerService.getCustomerByLoginOrEmail(requestUtil.getCurrentCustomerLogin(request));
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer updateCurrentCustomer(final HttpServletRequest request, final Market market, final MarketArea marketArea, final CustomerEditForm customerEditForm) throws Exception {
		String userId = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(userId);
		
		customer.setLogin(customerEditForm.getEmail());
		customer.setFirstname(customerEditForm.getFirstname());
		customer.setLastname(customerEditForm.getLastname());
		
		customer.setDefaultLocale(marketArea.getDefaultLocalization().toString());
		customer.setActive(true);
		customer.setDateCreate(new Date());
		customer.setDateUpdate(new Date());

		customer.setEmail(customerEditForm.getEmail());
		
		// save market context phone etc
		
		customerService.saveOrUpdateCustomer(customer);
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer buildAndSaveNewAddressCustomer(final HttpServletRequest request, final Market market, final MarketArea marketArea, final CustomerAddressForm customerAddressForm) throws Exception {
		Customer customer = customerService.getCustomerByLoginOrEmail(requestUtil.getCurrentCustomerLogin(request));
		
		CustomerAddress defaultAddress = new CustomerAddress();
		if(StringUtils.isNotEmpty(customerAddressForm.getAddressName())){
			defaultAddress.setAddressName(customerAddressForm.getAddressName());
		} else {
			defaultAddress.setAddressName(customerAddressForm.getAddress1() + "(" + customerAddressForm.getCity() + ")");
		}
		defaultAddress.setAddressName(customerAddressForm.getAddressName());
		defaultAddress.setTitle(customerAddressForm.getTitle());
		defaultAddress.setLastname(customerAddressForm.getLastname());
		defaultAddress.setFirstname(customerAddressForm.getFirstname());
		defaultAddress.setAddress1(customerAddressForm.getAddress1());
		defaultAddress.setAddress2(customerAddressForm.getAddress2());
		defaultAddress.setAddressAdditionalInformation(customerAddressForm.getAddressAdditionalInformation());
		defaultAddress.setPostalCode(customerAddressForm.getPostalCode());
		defaultAddress.setCity(customerAddressForm.getCity());
		defaultAddress.setStateCode(customerAddressForm.getStateCode());
		defaultAddress.setAreaCode(customerAddressForm.getAreaCode());
		defaultAddress.setCountryCode(customerAddressForm.getCountryCode());
		defaultAddress.setDefaultBilling(true);
		defaultAddress.setDefaultShipping(true);
		customer.getAddresses().add(defaultAddress);
		
		customerService.saveOrUpdateCustomer(customer);
		
		customer = customerService.getCustomerByLoginOrEmail(requestUtil.getCurrentCustomerLogin(request));
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer deleteAddressCustomer(final HttpServletRequest request, final String customerAddressId) throws Exception {
		Customer customer = customerService.getCustomerByLoginOrEmail(requestUtil.getCurrentCustomerLogin(request));
		CustomerAddress customerAddress = customer.getAddress(new Long(customerAddressId));
		
		// SANITY CHECK : wrong address id for this customer
		if(customerAddress == null){
			throw new Exception(""); 
		}
		
		Set<CustomerAddress> customerAddresses = customer.getAddresses();
		
		// SANITY CHECK : min address equal zero
		if(customerAddresses.size() < 2){
			throw new Exception(""); 
		}
		
		for (Iterator<CustomerAddress> iterator = customerAddresses.iterator(); iterator.hasNext();) {
			CustomerAddress customerAddressIterator = (CustomerAddress) iterator.next();
			if(customerAddressIterator.getId().equals(new Long(customerAddressId))){
				customerAddress = customerAddressIterator;
			}
		}
		customer.getAddresses().remove(customerAddress);
		
		customerService.saveOrUpdateCustomer(customer);
		customer = customerService.getCustomerByLoginOrEmail(requestUtil.getCurrentCustomerLogin(request));
		requestUtil.updateCurrentCustomer(request, customer);
		return customer;
	}
	
	public Customer addProductSkuToWishlist(final HttpServletRequest request, final String productSkuCode) throws Exception {
		final MarketArea marketArea = requestUtil.getCurrentMarketArea(request);
		String userId = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(userId);
		customer = checkCustomerMarketArea(request, customer);
		
		final CustomerMarketArea customerMarketContext = customer.getCurrentCustomerMarketArea(marketArea.getCode());
		
		CustomerWishlist customerWishlist = customerMarketContext.getCustomerWishlistByProductSkuCode(productSkuCode);
		if(customerWishlist == null){
			customerWishlist = new CustomerWishlist();
			customerWishlist.setCustomerMarketAreaId(customerMarketContext.getId());
			customerWishlist.setProductSkuCode(productSkuCode);
			customerWishlist.setPosition(customerMarketContext.getWishlistProducts().size() + 1);
			customerMarketContext.getWishlistProducts().add(customerWishlist);
			customer.getCustomerMarketAreas().add(customerMarketContext);
			customerService.saveOrUpdateCustomer(customer);
			customer = customerService.getCustomerByLoginOrEmail(requestUtil.getCurrentCustomerLogin(request));
			requestUtil.updateCurrentCustomer(request, customer);
		} else {
			// Wishlist for this product sku code already exist
			throw new Exception(""); 
		}
		return customer;
	}
	
	public Customer removeProductSkuFromWishlist(final HttpServletRequest request, final String productSkuCode) throws Exception {
		final MarketArea marketArea = requestUtil.getCurrentMarketArea(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(requestUtil.getCurrentCustomerLogin(request));
		customer = checkCustomerMarketArea(request, customer);
		
		final CustomerMarketArea customerMarketContext = customer.getCurrentCustomerMarketArea(marketArea.getCode());
		
		CustomerWishlist customerWishlist = customerMarketContext.getCustomerWishlistByProductSkuCode(productSkuCode);
		if(customerWishlist != null){
			customerMarketContext.getWishlistProducts().remove(customerWishlist);
			customer.getCustomerMarketAreas().add(customerMarketContext);
			customerService.saveOrUpdateCustomer(customer);
			customer = customerService.getCustomerByLoginOrEmail(requestUtil.getCurrentCustomerLogin(request));
			requestUtil.updateCurrentCustomer(request, customer);
		}
		return customer;
	}
	
	public Order buildAndSaveNewOrder(final HttpServletRequest request, final Market market, final MarketArea marketArea) throws Exception {
		Customer customer = requestUtil.getCurrentCustomer(request);
		Cart cart = requestUtil.getCurrentCart(request);
		
		Order order = new Order();
		order.setStatus(Order.ORDER_STATUS_PENDING);
		order.setCustomerId(customer.getId());
		order.setBillingAddressId(cart.getBillingAddressId());
		order.setShippingAddressId(cart.getShippingAddressId());
		
		Set<CartItem> cartItems = cart.getCartItems();
		Set<OrderItem> orderItems = new HashSet<OrderItem>();
		for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
			CartItem cartItem = (CartItem) iterator.next();
			OrderItem orderItem = new OrderItem();
			orderItem.setProductSkuCode(cartItem.getProductSkuCode());
			orderItem.setProductSku(cartItem.getProductSku());
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItems.add(orderItem);
		}
		order.setOrderItems(orderItems);
		
		order = orderService.createNewOrder(order);
		
		// Clean Cart
		requestUtil.cleanCurrentCart(request);

		requestUtil.saveLastOrder(request, order);
		
		return order;
	}
	
	private Customer checkCustomerMarketArea(final HttpServletRequest request, Customer customer) throws Exception{
		final MarketArea marketArea = requestUtil.getCurrentMarketArea(request);
		CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getCode());
		if(customerMarketArea == null){
			// Create a CustomerMarketArea for this MarketArea
			customerMarketArea = new CustomerMarketArea();
			customerMarketArea.setMarketAreaCode(marketArea.getCode());
			customer.getCustomerMarketAreas().add(customerMarketArea);
			customerService.saveOrUpdateCustomer(customer);
			customer = customerService.getCustomerById(customer.getId().toString());
		}
		return customer;
	}
	
    /**
     * 
     */
	public void buildAndSaveContactUsMail(final HttpServletRequest request, final ContactUsForm contactUsForm) throws Exception {
		final Customer customer = requestUtil.getCurrentCustomer(request);
		final String VelocityPath = requestUtil.getCurrentVelocityPrefix(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final ContactUsEmailBean contactUsEmailBean = new ContactUsEmailBean();
		BeanUtils.copyProperties(contactUsForm, contactUsEmailBean);
		
		emailService.buildAndSaveContactUsMail(localization, customer, VelocityPath, contactUsEmailBean);
	}
	
    /**
     * 
     */
	public void saveAndBuildNewsletterRegistrationConfirmationMail(final HttpServletRequest request, final FollowUsForm followUsForm) throws Exception {
		final Customer customer = requestUtil.getCurrentCustomer(request);
		final String VelocityPath = requestUtil.getCurrentVelocityPrefix(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final NewsletterRegistrationConfirmationEmailBean newsletterRegistrationConfirmationEmailBean = new NewsletterRegistrationConfirmationEmailBean();
		BeanUtils.copyProperties(followUsForm, newsletterRegistrationConfirmationEmailBean);
		
		emailService.saveAndBuildNewsletterRegistrationConfirmationMail(localization, customer, VelocityPath, newsletterRegistrationConfirmationEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerForgottenPasswordMail(final HttpServletRequest request, final ForgottenPasswordForm forgottenPasswordForm) throws Exception {
		final Customer customer = requestUtil.getCurrentCustomer(request);
		final String VelocityPath = requestUtil.getCurrentVelocityPrefix(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean = new CustomerForgottenPasswordEmailBean();
		BeanUtils.copyProperties(forgottenPasswordForm, customerForgottenPasswordEmailBean);
		
		emailService.buildAndSaveCustomerForgottenPasswordMail(localization, customer, VelocityPath, customerForgottenPasswordEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerNewAccountMail(final HttpServletRequest request, final CreateAccountForm createAccountForm) throws Exception {
		final Customer customer = requestUtil.getCurrentCustomer(request);
		final String VelocityPath = requestUtil.getCurrentVelocityPrefix(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean = new CustomerNewAccountConfirmationEmailBean();
		BeanUtils.copyProperties(createAccountForm, customerNewAccountConfirmationEmailBean);
		
		emailService.buildAndSaveCustomerNewAccountMail(localization, customer, VelocityPath, customerNewAccountConfirmationEmailBean);
	}

	
}
