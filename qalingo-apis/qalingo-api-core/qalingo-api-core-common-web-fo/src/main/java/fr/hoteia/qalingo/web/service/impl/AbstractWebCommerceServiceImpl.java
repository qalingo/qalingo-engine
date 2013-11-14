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
import org.springframework.beans.factory.annotation.Autowired;

import fr.hoteia.qalingo.core.domain.Cart;
import fr.hoteia.qalingo.core.domain.CartItem;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerCredential;
import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.domain.CustomerOptin;
import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.OrderItem;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.email.bean.ContactEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerResetPasswordConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.NewsletterEmailBean;
import fr.hoteia.qalingo.core.email.bean.RetailerContactEmailBean;
import fr.hoteia.qalingo.core.exception.UniqueNewsletterSubscriptionException;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.CustomerGroupService;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.service.EmailService;
import fr.hoteia.qalingo.core.service.OrderService;
import fr.hoteia.qalingo.core.service.RetailerService;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.service.pojo.RequestData;
import fr.hoteia.qalingo.core.web.util.RequestUtil;

public class AbstractWebCommerceServiceImpl {

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
    protected UrlService urlService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
    /**
     * 
     */
	public CustomerCredential flagCustomerCredentialWithToken(final HttpServletRequest request, final RequestData requestData, final Customer customer) throws Exception {
		if(customer != null){
			String token = UUID.randomUUID().toString();
			CustomerCredential customerCredential = customer.getCurrentCredential();
			if(customerCredential != null){
				customerCredential.setResetToken(token);
				Date date = new Date();
				customerCredential.setTokenTimestamp(new Timestamp(date.getTime()));
				customerService.saveOrUpdateCustomerCredential(customerCredential);
			}
			return customerCredential;
		}
		return null;
	}
	
    /**
     * 
     */
	public void cancelCustomerCredentialToken(final HttpServletRequest request, final RequestData requestData, final Customer customer) throws Exception {
		if(customer != null){
			CustomerCredential customerCredential = customer.getCurrentCredential();
			if(customerCredential != null){
				customerCredential.setResetToken("");
				customerCredential.setTokenTimestamp(null);
				customerService.saveOrUpdateCustomerCredential(customerCredential);
			}
		}
	}
	
    /**
     * 
     */
	public void resetCustomerCredential(final HttpServletRequest request, final RequestData requestData, final Customer customer, final String newPassword) throws Exception {
		if(customer != null){
			String clearPassword = newPassword;
			String encorePassword = securityUtil.encodePassword(clearPassword);
			CustomerCredential customerCredential = new CustomerCredential();
			customerCredential.setPassword(encorePassword);
			customerCredential.setDateUpdate(new Date());
			customer.getCredentials().add(customerCredential);
			
			customer.setPassword(encorePassword);
			
			customerService.saveOrUpdateCustomer(customer);
		}
	}
	
    /**
     * 
     */
	public Customer buildAndSaveNewCustomer(final HttpServletRequest request, final RequestData requestData, final Market market, final MarketArea marketArea, Customer customer) throws Exception {
		
		if(customer.getCode() == null){
			customer.setCode(UUID.randomUUID().toString());
		}
		
		if(customer.getPermalink() == null){
			String permalink = securityUtil.generatePermalink();
			customer.setPermalink(permalink);
		}
		
		customer.setDefaultLocale(marketArea.getDefaultLocalization().getCode());
		customer.setActive(true);
		customer.setDateCreate(new Date());
		customer.setDateUpdate(new Date());

		CustomerGroup customerGroup = customerGroupService.getCustomerGroupByCode(CustomerGroup.GROUP_FO_CUSTOMER);
		customer.getCustomerGroups().add(customerGroup);
		
		customerService.saveOrUpdateCustomer(customer);
		
		customer = customerService.getCustomerByLoginOrEmail(customer.getLogin());
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer updateCurrentCustomer(final HttpServletRequest request, final RequestData requestData, Customer customer) throws Exception {
		customer.setActive(true);
		customer.setDateUpdate(new Date());

		// save market context phone etc
		
		// UPDATE CUSTOMER
		customerService.saveOrUpdateCustomer(customer);
		
		// UPDATE SESSION
		customer = customerService.getCustomerByLoginOrEmail(customer.getLogin());
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer updateOrSaveAddressCustomer(final HttpServletRequest request, final RequestData requestData, final Market market, final MarketArea marketArea, Customer customer) throws Exception {
		// UPDATE CUSTOMER
		customerService.saveOrUpdateCustomer(customer);
		
		// UPDATE SESSION
		customer = customerService.getCustomerByLoginOrEmail(customer.getLogin());
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer deleteAddressCustomer(final HttpServletRequest request, final RequestData requestData, final String customerAddressId) throws Exception {
		Customer customer = requestData.getCustomer();
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
		customer = customerService.getCustomerByLoginOrEmail(customer.getEmail());
		requestUtil.updateCurrentCustomer(request, customer);
		
		return customer;
	}
	
	public Customer addProductSkuToWishlist(final HttpServletRequest request, final RequestData requestData, final String productSkuCode) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		Customer customer = requestData.getCustomer();
		customer = checkCustomerMarketArea(requestData, customer);
		
		final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
		
		CustomerWishlist customerWishlist = customerMarketArea.getCustomerWishlistByProductSkuCode(productSkuCode);
		if(customerWishlist == null){
			customerWishlist = new CustomerWishlist();
			customerWishlist.setCustomerMarketAreaId(customerMarketArea.getId());
			customerWishlist.setProductSkuCode(productSkuCode);
			customerWishlist.setPosition(customerMarketArea.getWishlistProducts().size() + 1);
			customerMarketArea.getWishlistProducts().add(customerWishlist);
			customer.getCustomerMarketAreas().add(customerMarketArea);
			customerService.saveOrUpdateCustomer(customer);
			customer = customerService.getCustomerByLoginOrEmail(customer.getEmail());
			requestUtil.updateCurrentCustomer(request, customer);
		} else {
			// Wishlist for this product sku code already exist
			throw new Exception(""); 
		}
		return customer;
	}
	
	public Customer removeProductSkuFromWishlist(final HttpServletRequest request, final RequestData requestData, final String productSkuCode) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		Customer customer = requestData.getCustomer();
		customer = checkCustomerMarketArea(requestData, customer);
		
		final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
		
		CustomerWishlist customerWishlist = customerMarketArea.getCustomerWishlistByProductSkuCode(productSkuCode);
		if(customerWishlist != null){
			customerMarketArea.getWishlistProducts().remove(customerWishlist);
			customer.getCustomerMarketAreas().add(customerMarketArea);
			customerService.saveOrUpdateCustomer(customer);
			customer = customerService.getCustomerByLoginOrEmail(customer.getEmail());
			requestUtil.updateCurrentCustomer(request, customer);
		}
		return customer;
	}
	
	public Order buildAndSaveNewOrder(final HttpServletRequest request, final RequestData requestData, final Market market, final MarketArea marketArea) throws Exception {
		Customer customer = requestData.getCustomer();
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
	
	public Customer saveNewsletterSubscription(final RequestData requestData, final String email) throws Exception {
		Customer customer = customerService.getCustomerByLoginOrEmail(email);
		MarketArea marketArea = requestData.getMarketArea();
		HttpServletRequest request = requestData.getRequest();
		Market market = requestData.getMarket();
		
		// SANITY CHECK : CHECK IF THE OPTIN ALREADY EXIST FOR THE MARKET AREA
		if(customer != null){
			CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
			if(customerMarketArea != null){
				CustomerOptin customerOptin = customerMarketArea.getOptins(CustomerOptin.OPTIN_TYPE_WWW_NEWSLETTER);
				if(customerOptin != null){
					throw new UniqueNewsletterSubscriptionException();
				}
			}
		}
		
		CustomerOptin customerOptin = new CustomerOptin();
		customerOptin.setType(CustomerOptin.OPTIN_TYPE_WWW_NEWSLETTER);
		customerOptin.setOrigin("MCOMMERCE");

		// HANDLE OPTIN
		if(customer != null){
			customer = checkCustomerMarketArea(requestData, customer);
			CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
			if(customerMarketArea == null){
				customerMarketArea = new CustomerMarketArea();
				customerMarketArea.addOptins(customerOptin);
				customer.getCustomerMarketAreas().add(customerMarketArea);
			} else {
				customerMarketArea.addOptins(customerOptin);
			}
		} else {
			customer = new Customer();
			customer.setEmail(email);
			customer.setAnonymous(true);
			customer = checkCustomerMarketArea(requestData, customer);
			CustomerMarketArea customerMarketArea = new CustomerMarketArea();
			customerMarketArea.addOptins(customerOptin);
			customer.getCustomerMarketAreas().add(customerMarketArea);
		}
		
		customer = updateCurrentCustomer(request, requestData, customer);
		return customer;
    }
	
	public Customer saveNewsletterUnsubscription(final RequestData requestData, final String email) throws Exception {
		Customer customer = customerService.getCustomerByLoginOrEmail(email);
		MarketArea marketArea = requestData.getMarketArea();
		HttpServletRequest request = requestData.getRequest();
		Market market = requestData.getMarket();
		
		// SANITY CHECK : CHECK IF THE OPTIN ALREADY EXIST FOR THE MARKET AREA
		if(customer != null){
			CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
			if(customerMarketArea != null){
				CustomerOptin customerOptin = customerMarketArea.getOptins(CustomerOptin.OPTIN_TYPE_WWW_NEWSLETTER);
				if(customerOptin != null){
					customerMarketArea.getOptins().remove(customerOptin);
				}
			}
		}
		
		customer = updateCurrentCustomer(request, requestData, customer);
		return customer;
    }
	
    /**
     * 
     */
	public void buildAndSaveContactMail(final RequestData requestData, final ContactEmailBean contactEmailBean) throws Exception {
		emailService.buildAndSaveContactMail(requestData, requestData.getVelocityEmailPrefix(), contactEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveRetailerContactMail(final RequestData requestData, final Retailer retailerToContact, final RetailerContactEmailBean retailerContactEmailBean) throws Exception {
		emailService.buildAndSaveRetailerContactMail(requestData, requestData.getCustomer(), requestData.getVelocityEmailPrefix(), retailerContactEmailBean);
	}
	
    /**
     * 
     */
	public void saveAndBuildNewsletterSubscriptionConfirmationMail(final RequestData requestData, final NewsletterEmailBean newsletterEmailBean) throws Exception {
		emailService.saveAndBuildNewsletterSubscriptionnConfirmationMail(requestData, requestData.getVelocityEmailPrefix(), newsletterEmailBean);
	}
	
    /**
     * 
     */
	public void saveAndBuildNewsletterUnsubscriptionConfirmationMail(final RequestData requestData, final NewsletterEmailBean newsletterEmailBean) throws Exception {
		emailService.saveAndBuildNewsletterUnsubscriptionConfirmationMail(requestData, requestData.getVelocityEmailPrefix(), newsletterEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerNewAccountMail(final RequestData requestData, final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean) throws Exception {
		emailService.buildAndSaveCustomerNewAccountMail(requestData, requestData.getVelocityEmailPrefix(), customerNewAccountConfirmationEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerForgottenPasswordMail(final RequestData requestData, final Customer customer, final CustomerCredential customerCredential, final CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean) throws Exception {
		emailService.buildAndSaveCustomerForgottenPasswordMail(requestData, customer, requestData.getVelocityEmailPrefix(), customerForgottenPasswordEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerResetPasswordConfirmationMail(final RequestData requestData, final Customer customer) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final String velocityPath = requestData.getVelocityEmailPrefix();
		final String contextNameValue = requestData.getContextNameValue();

		final CustomerResetPasswordConfirmationEmailBean customerResetPasswordConfirmationEmailBean = new CustomerResetPasswordConfirmationEmailBean();
		customerResetPasswordConfirmationEmailBean.setFromEmail(marketArea.getEmailFrom(contextNameValue));
		customerResetPasswordConfirmationEmailBean.setReplyToEmail(marketArea.getEmailFrom(contextNameValue));
		customerResetPasswordConfirmationEmailBean.setToEmail(customer.getEmail());
		
		customerResetPasswordConfirmationEmailBean.setTitle(customer.getTitle());
		customerResetPasswordConfirmationEmailBean.setFirstname(customer.getFirstname());
		customerResetPasswordConfirmationEmailBean.setLastname(customer.getLastname());
		customerResetPasswordConfirmationEmailBean.setEmail(customer.getEmail());
		
		customerResetPasswordConfirmationEmailBean.setCustomerDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
		
		emailService.buildAndSaveCustomerResetPasswordConfirmationMail(requestData, customer, velocityPath, customerResetPasswordConfirmationEmailBean);
	}
	
	protected Customer checkCustomerMarketArea(final RequestData requestData, Customer customer) throws Exception{
		final MarketArea marketArea = requestData.getMarketArea();
		CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
		if(customerMarketArea == null){
			// Create a CustomerMarketArea for this MarketArea
			customerMarketArea = new CustomerMarketArea();
			customerMarketArea.setMarketAreaId(marketArea.getId());
			customer.getCustomerMarketAreas().add(customerMarketArea);
			customerService.saveOrUpdateCustomer(customer);
			if(StringUtils.isNotEmpty(customer.getEmail())){
				customer = customerService.getCustomerByLoginOrEmail(customer.getEmail());
			}
		}
		return customer;
	}

}