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

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

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
import fr.hoteia.qalingo.core.domain.CustomerCredential;
import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.OrderItem;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.email.bean.ContactEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerResetPasswordConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.NewsletterRegistrationConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.RetailerContactEmailBean;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.CustomerGroupService;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.service.EmailService;
import fr.hoteia.qalingo.core.service.OrderService;
import fr.hoteia.qalingo.core.service.RetailerService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.form.ContactForm;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import fr.hoteia.qalingo.web.mvc.form.ResetPasswordForm;
import fr.hoteia.qalingo.web.mvc.form.RetailerContactForm;
import fr.hoteia.qalingo.web.service.WebCommerceService;

@Service("webCommerceService")
@Transactional
public class WebCommerceServiceImpl implements WebCommerceService {

	@Autowired
    protected EmailService emailService;
	
	@Autowired
    protected CustomerService customerService;
	
	@Autowired
    protected RetailerService retailerService;
	
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
	public CustomerCredential flagCustomeCredentialWithToken(final HttpServletRequest request, final Customer customer) throws Exception {
		if(customer != null){
			String token = UUID.randomUUID().toString();
			CustomerCredential customerCredential = customer.getCurrentCredential();
			customerCredential.setResetToken(token);
			Date date = new Date();
			customerCredential.setTokenTimestamp(new Timestamp(date.getTime()));
			customerService.saveOrUpdateCustomerCredential(customerCredential);
			return customerCredential;
		}
		return null;
	}
	
    /**
     * 
     */
	public void resetCustomeCredential(final HttpServletRequest request, final Customer customer, final ResetPasswordForm resetPasswordForm) throws Exception {
		if(customer != null){
			String clearPassword = resetPasswordForm.getNewPassword();
			String encorePassword = securityUtil.encodePassword(clearPassword);
			CustomerCredential customerCredential = new CustomerCredential();
			customerCredential.setPassword(encorePassword);
			customer.getCredentials().add(customerCredential);
			
			customer.setPassword(encorePassword);
			
			customerService.saveOrUpdateCustomer(customer);
		}
	}
	
    /**
     * 
     */
	public Customer buildAndSaveNewCustomer(final HttpServletRequest request, final Market market, final MarketArea marketArea, final CreateAccountForm createAccountForm) throws Exception {
		Customer customer = new Customer();
		
		if(customer.getCode() == null){
			customer.setCode(UUID.randomUUID().toString());
		}
		
		customer.setLogin(createAccountForm.getEmail());
		customer.setFirstname(createAccountForm.getFirstname());
		customer.setLastname(createAccountForm.getLastname());
		customer.setPassword(securityUtil.encodePassword(createAccountForm.getPassword()));
		
		if(customer.getPermalink() == null){
			String permalink = securityUtil.generatePermalink();
			customer.setPermalink(permalink);
		}
		
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
		String customerLogin = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
		
		customer.setLogin(customerEditForm.getEmail());
		customer.setFirstname(customerEditForm.getFirstname());
		customer.setLastname(customerEditForm.getLastname());
		
		customer.setDefaultLocale(marketArea.getDefaultLocalization().toString());
		customer.setActive(true);
		customer.setDateCreate(new Date());
		customer.setDateUpdate(new Date());

		customer.setEmail(customerEditForm.getEmail());
		
		// save market context phone etc
		
		// UPDATE CUSTOMER
		customerService.saveOrUpdateCustomer(customer);
		
		// UPDATE SESSION
		customer = customerService.getCustomerByLoginOrEmail(customerLogin);
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer updateOrSaveAddressCustomer(final HttpServletRequest request, final Market market, final MarketArea marketArea, final CustomerAddressForm customerAddressForm) throws Exception {
		String customerLogin = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
		
		CustomerAddress customerAddress = new CustomerAddress();
		String addressId = customerAddressForm.getIdOrGuid();
		if(StringUtils.isNotEmpty(addressId)){
			customerAddress = customer.getAddress(new Long(addressId));
		}
		
		if(StringUtils.isNotEmpty(customerAddressForm.getAddressName())){
			customerAddress.setAddressName(customerAddressForm.getAddressName());
		} else {
			customerAddress.setAddressName(customerAddressForm.getAddress1() + "(" + customerAddressForm.getCity() + ")");
		}
		customerAddress.setAddressName(customerAddressForm.getAddressName());
		customerAddress.setTitle(customerAddressForm.getTitle());
		customerAddress.setLastname(customerAddressForm.getLastname());
		customerAddress.setFirstname(customerAddressForm.getFirstname());
		customerAddress.setAddress1(customerAddressForm.getAddress1());
		customerAddress.setAddress2(customerAddressForm.getAddress2());
		customerAddress.setAddressAdditionalInformation(customerAddressForm.getAddressAdditionalInformation());
		customerAddress.setPostalCode(customerAddressForm.getPostalCode());
		customerAddress.setCity(customerAddressForm.getCity());
		customerAddress.setStateCode(customerAddressForm.getStateCode());
		customerAddress.setCountryCode(customerAddressForm.getCountryCode());
		customerAddress.setDefaultBilling(true);
		customerAddress.setDefaultShipping(true);
		
		if(StringUtils.isEmpty(addressId)){
			customer.getAddresses().add(customerAddress);
		}
		
		// UPDATE CUSTOMER
		customerService.saveOrUpdateCustomer(customer);
		
		// UPDATE SESSION
		customer = customerService.getCustomerByLoginOrEmail(customerLogin);
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer deleteAddressCustomer(final HttpServletRequest request, final String customerAddressId) throws Exception {
		String customerLogin = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
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
		
		// UPDATE CUSTOMER
		customerService.saveOrUpdateCustomer(customer);
		
		// UPDATE SESSION
		customer = customerService.getCustomerByLoginOrEmail(customerLogin);
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer addProductSkuToWishlist(final HttpServletRequest request, final String productSkuCode) throws Exception {
		final MarketArea marketArea = requestUtil.getCurrentMarketArea(request);
		String customerLogin = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
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
	public void buildAndSaveContactMail(final HttpServletRequest request, final ContactForm contactForm) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final String VelocityPath = requestUtil.getCurrentVelocityEmailPrefix(request);
		final String contextNameValue = requestUtil.getCurrentContextNameValue(request);

		final ContactEmailBean contactEmailBean = new ContactEmailBean();
		BeanUtils.copyProperties(contactForm, contactEmailBean);
		contactEmailBean.setFromEmail(currentMarketArea.getEmailFrom(contextNameValue));
		contactEmailBean.setToEmail(currentMarketArea.getEmailContact(contextNameValue));
		contactEmailBean.setReplyToEmail(currentMarketArea.getEmailFrom(contextNameValue));
		
		emailService.buildAndSaveContactMail(localization, VelocityPath, contactEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveRetailerContactMail(final HttpServletRequest request, final RetailerContactForm retailerContactForm) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final Customer customer = requestUtil.getCurrentCustomer(request);
		final String VelocityPath = requestUtil.getCurrentVelocityEmailPrefix(request);
		final String contextNameValue = requestUtil.getCurrentContextNameValue(request);

		final Retailer retailer = retailerService.getRetailerByCode(currentMarketArea.getId(), currentRetailer.getId(), retailerContactForm.getRetailerCode());
		
		final RetailerContactEmailBean retailerContactEmailBean = new RetailerContactEmailBean();
		BeanUtils.copyProperties(retailerContactForm, retailerContactEmailBean);
		retailerContactEmailBean.setFromEmail(currentMarketArea.getEmailFrom(contextNameValue));
		retailerContactEmailBean.setReplyToEmail(currentMarketArea.getEmailFrom(contextNameValue));
		retailerContactEmailBean.setToEmail(retailer.getDefaultAddress().getEmail());
		
		emailService.buildAndSaveRetailerContactMail(localization, customer, VelocityPath, retailerContactEmailBean);
	}
	
    /**
     * 
     */
	public void saveAndBuildNewsletterRegistrationConfirmationMail(final HttpServletRequest request, final FollowUsForm followUsForm) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final String VelocityPath = requestUtil.getCurrentVelocityEmailPrefix(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final String contextNameValue = requestUtil.getCurrentContextNameValue(request);

		final NewsletterRegistrationConfirmationEmailBean newsletterRegistrationConfirmationEmailBean = new NewsletterRegistrationConfirmationEmailBean();
		BeanUtils.copyProperties(followUsForm, newsletterRegistrationConfirmationEmailBean);
		newsletterRegistrationConfirmationEmailBean.setFromEmail(currentMarketArea.getEmailFrom(contextNameValue));
		newsletterRegistrationConfirmationEmailBean.setReplyToEmail(currentMarketArea.getEmailFrom(contextNameValue));
		newsletterRegistrationConfirmationEmailBean.setToEmail(followUsForm.getEmail());
		
		emailService.saveAndBuildNewsletterRegistrationConfirmationMail(localization, VelocityPath, newsletterRegistrationConfirmationEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerNewAccountMail(final HttpServletRequest request, final CreateAccountForm createAccountForm) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final String VelocityPath = requestUtil.getCurrentVelocityEmailPrefix(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final String contextNameValue = requestUtil.getCurrentContextNameValue(request);

		final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean = new CustomerNewAccountConfirmationEmailBean();
		BeanUtils.copyProperties(createAccountForm, customerNewAccountConfirmationEmailBean);
		customerNewAccountConfirmationEmailBean.setFromEmail(currentMarketArea.getEmailFrom(contextNameValue));
		customerNewAccountConfirmationEmailBean.setReplyToEmail(currentMarketArea.getEmailFrom(contextNameValue));
		customerNewAccountConfirmationEmailBean.setToEmail(createAccountForm.getEmail());
		
		emailService.buildAndSaveCustomerNewAccountMail(localization, VelocityPath, customerNewAccountConfirmationEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerForgottenPasswordMail(final HttpServletRequest request,  final Customer customer, final CustomerCredential customerCredential, final ForgottenPasswordForm forgottenPasswordForm) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final String VelocityPath = requestUtil.getCurrentVelocityEmailPrefix(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final String contextNameValue = requestUtil.getCurrentContextNameValue(request);

		final CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean = new CustomerForgottenPasswordEmailBean();
		BeanUtils.copyProperties(forgottenPasswordForm, customerForgottenPasswordEmailBean);
		customerForgottenPasswordEmailBean.setFromEmail(currentMarketArea.getEmailFrom(contextNameValue));
		customerForgottenPasswordEmailBean.setReplyToEmail(currentMarketArea.getEmailFrom(contextNameValue));
		customerForgottenPasswordEmailBean.setToEmail(customer.getEmail());
		customerForgottenPasswordEmailBean.setToken(customerCredential.getResetToken());
		
		emailService.buildAndSaveCustomerForgottenPasswordMail(localization, customer, VelocityPath, customerForgottenPasswordEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerResetPasswordConfirmationMail(final HttpServletRequest request, final Customer customer) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final String VelocityPath = requestUtil.getCurrentVelocityEmailPrefix(request);
		final Localization localization = requestUtil.getCurrentLocalization(request);
		final String contextNameValue = requestUtil.getCurrentContextNameValue(request);

		final CustomerResetPasswordConfirmationEmailBean customerResetPasswordConfirmationEmailBean = new CustomerResetPasswordConfirmationEmailBean();
		customerResetPasswordConfirmationEmailBean.setFromEmail(currentMarketArea.getEmailFrom(contextNameValue));
		customerResetPasswordConfirmationEmailBean.setReplyToEmail(currentMarketArea.getEmailFrom(contextNameValue));
		customerResetPasswordConfirmationEmailBean.setToEmail(customer.getEmail());
		
		emailService.buildAndSaveCustomerResetPasswordConfirmationMail(localization, customer, VelocityPath, customerResetPasswordConfirmationEmailBean);
	}

}