/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CartItemTax;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.CustomerCredential;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.CustomerOptin;
import org.hoteia.qalingo.core.domain.CustomerPaymentInformation;
import org.hoteia.qalingo.core.domain.CustomerWishlist;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.Email;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.OrderAddress;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.OrderItem;
import org.hoteia.qalingo.core.domain.OrderShipment;
import org.hoteia.qalingo.core.domain.OrderTax;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.domain.enumtype.OrderStatus;
import org.hoteia.qalingo.core.email.bean.ContactEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerResetPasswordConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.NewsletterEmailBean;
import org.hoteia.qalingo.core.email.bean.OrderConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.RetailerContactEmailBean;
import org.hoteia.qalingo.core.exception.ProductAlreadyExistInWishlistException;
import org.hoteia.qalingo.core.exception.UniqueNewsletterSubscriptionException;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.security.util.SecurityUtil;
import org.hoteia.qalingo.core.service.CartService;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.EmailService;
import org.hoteia.qalingo.core.service.EngineSessionService;
import org.hoteia.qalingo.core.service.GroupRoleService;
import org.hoteia.qalingo.core.service.OrderCustomerService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.ReferentialDataService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.service.WebManagementService;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.hoteia.qalingo.web.mvc.form.ContactForm;
import org.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import org.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import org.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import org.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import org.hoteia.qalingo.web.mvc.form.PaymentForm;
import org.hoteia.qalingo.web.mvc.form.ResetPasswordForm;
import org.hoteia.qalingo.web.mvc.form.RetailerContactForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("webManagementService")
@Transactional
public class WebManagementServiceImpl implements WebManagementService {
    
    @Autowired
    protected EmailService emailService;
    
    @Autowired
    protected CustomerService customerService;
    
    @Autowired
    protected RetailerService retailerService;
    
    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    @Autowired
    protected ProductService productService;
    
    @Autowired
    protected CartService cartService;
    
    @Autowired
    protected OrderCustomerService orderCustomerService;
    
    @Autowired
    protected GroupRoleService customerGroupService;
    
    @Autowired
    protected EngineSessionService engineSessionService;
    
    @Autowired
    protected UrlService urlService;
    
    @Autowired
    protected ReferentialDataService referentialDataService;

    @Autowired
    protected RequestUtil requestUtil;
    
    @Autowired
    protected SecurityUtil securityUtil;
    
    /**
     * 
     */
    public void addToCart(final RequestData requestData, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        // SANITY CHECK : sku code is empty or null : no sense
        if (StringUtils.isEmpty(productSkuCode)) {
            throw new Exception("");
        }

        // SANITY CHECK : quantity is equal zero : no sense
        if (quantity == 0) {
            throw new Exception("");
        }

        Cart cart = requestData.getCart();
        int finalQuantity = quantity;
        if(cart != null){
            Set<CartItem> cartItems = cart.getCartItems();
            for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
                CartItem cartItem = (CartItem) iterator.next();
                if (cartItem.getProductSkuCode().equalsIgnoreCase(productSkuCode)) {
                    finalQuantity = finalQuantity + cartItem.getQuantity();
                }
            }
        }
        
        updateCart(requestData, catalogCategoryCode, productSkuCode, finalQuantity);
    }
    
    /**
     * 
     */
    public void updateCart(final RequestData requestData, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        
        // SANITY CHECK : sku code is empty or null : no sense
        if (StringUtils.isEmpty(productSkuCode)) {
            throw new Exception("");
        }

        // SANITY CHECK : quantity is equal zero : no sense
        if (quantity == 0) {
            throw new Exception("");
        }

        Cart cart = requestData.getCart();
        if(cart == null){
            EngineEcoSession engineEcoSession = requestUtil.getCurrentEcoSession(request);
            cart = engineEcoSession.addNewCart();
        }
        Set<CartItem> cartItems = cart.getCartItems();
        boolean productSkuIsNew = true;
        for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
            CartItem cartItem = (CartItem) iterator.next();
            if (cartItem.getProductSkuCode().equalsIgnoreCase(productSkuCode)) {
                cartItem.setQuantity(quantity);
                productSkuIsNew = false;
            }
        }
        if (productSkuIsNew) {
            final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
            if(productSku != null){
                CartItem cartItem = new CartItem();
                cartItem.setProductSkuCode(productSkuCode);
                cartItem.setProductSku(productSku);

                cartItem.setProductMarketingCode(productSku.getProductMarketing().getCode());
                cartItem.setQuantity(quantity);
                if(StringUtils.isNotEmpty(catalogCategoryCode)){
                    cartItem.setCatalogCategoryCode(catalogCategoryCode);
                } else {
                    final ProductMarketing reloadedProductMarketing = productService.getProductMarketingByCode(productSku.getProductMarketing().getCode());
                    cartItem.setCatalogCategoryCode(reloadedProductMarketing.getDefaultCatalogCategory().getCode());
                }
                cart.getCartItems().add(cartItem);
            } else {
                // TODO : throw ??
            }
        }
        requestUtil.updateCurrentCart(request, cart);
    }
    
    /**
     * 
     */
    public void updateCart(final RequestData requestData, final String skuCode, final int quantity) throws Exception {
        updateCart(requestData, null, skuCode, quantity);
    }
    
    /**
     * 
     */
    public void updateCart(final RequestData requestData, final Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Cart cart = requestData.getCart();
        if(cart != null){
            cart.setCustomerId(customer.getId());
            cart.setBillingAddressId(customer.getDefaultBillingAddressId());
            cart.setShippingAddressId(customer.getDefaultShippingAddressId());
        }
        requestUtil.updateCurrentCart(request, cart);
    }
    
    /**
     * 
     */
    public void updateCart(final RequestData requestData, final Long billingAddressId, final Long shippingAddressId) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Cart cart = requestData.getCart();
        if(cart != null){
            cart.setBillingAddressId(billingAddressId);
            cart.setShippingAddressId(shippingAddressId);
        }
        requestUtil.updateCurrentCart(request, cart);
    }

    /**
     * 
     */
    public void deleteCartItem(final RequestData requestData, final String skuCode) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Cart cart = requestData.getCart();
        if(cart != null){
            Set<CartItem> cartItems = new HashSet<CartItem>(cart.getCartItems());
            for (Iterator<CartItem> iterator = cart.getCartItems().iterator(); iterator.hasNext();) {
                CartItem cartItem = (CartItem) iterator.next();
                if (cartItem.getProductSkuCode().equalsIgnoreCase(skuCode)) {
                    cartItems.remove(cartItem);
                }
            }
            cart.setCartItems(cartItems);
        }
        requestUtil.updateCurrentCart(request, cart);
    }
    
    /**
     * 
     */
    public void cleanCart(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        requestUtil.resetCurrentCart(request);
    }    
    
    /**
     * 
     */
    public void resetCustomerCredential(final RequestData requestData, final Customer customer, final ResetPasswordForm resetPasswordForm) throws Exception {
        resetCustomerCredential(requestData, customer, resetPasswordForm.getNewPassword());
    }
    
    /**
     * 
     */
    public Customer buildAndSaveNewCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, 
                                            final CreateAccountForm createAccountForm) throws Exception {
        Customer customer = new Customer();
        
        customer.setLogin(createAccountForm.getEmail());
        customer.setFirstname(createAccountForm.getFirstname());
        customer.setLastname(createAccountForm.getLastname());
        customer.setPassword(securityUtil.encodePassword(createAccountForm.getPassword()));
        
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
        
        return buildAndSaveNewCustomer(requestData, market, marketArea, customer);
    }
    
    public Customer updateCurrentCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, final CustomerEditForm customerEditForm) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        String customerLogin = requestUtil.getCurrentCustomerLogin(request);
        Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
        
        customer.setLogin(customerEditForm.getEmail());
        customer.setFirstname(customerEditForm.getFirstname());
        customer.setLastname(customerEditForm.getLastname());
        
        return updateCurrentCustomer(requestData, customer);
    }
    
    public Customer activeNewCustomer(final RequestData requestData, Customer customer) throws Exception {
        customer.setValidated(true);
        return updateCurrentCustomer(requestData, customer);
    }
    
    public Customer updateOrSaveAddressCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, final CustomerAddressForm customerAddressForm) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
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
        
        return updateOrSaveAddressCustomer(requestData, market, marketArea, customer);
    }
    
    public Customer saveNewsletterSubscriptionAndSendEmail(final RequestData requestData, final String email) throws Exception {
        Customer customer = saveNewsletterSubscription(requestData, email);
        saveAndBuildNewsletterSubscriptionConfirmationMail(requestData, email);
        return customer;
    }
    
    public Customer saveNewsletterUnsubscriptionAndSendEmail(final RequestData requestData, final String email) throws Exception {
        Customer customer = saveNewsletterUnsubscription(requestData, email);
        saveAndBuildNewsletterUnsubscriptionConfirmationMail(requestData, email);
        return customer;
    }
    
    /**
     * 
     */
    public void buildAndSaveContactMail(final RequestData requestData, final ContactForm contactForm) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        final ContactEmailBean contactEmailBean = new ContactEmailBean();
        BeanUtils.copyProperties(contactForm, contactEmailBean);
        contactEmailBean.setFromAddress(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_CONTACT));
        contactEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_CONTACT));
        contactEmailBean.setReplyToEmail(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_CONTACT));
        contactEmailBean.setToEmail(marketArea.getEmailToContact(contextNameValue));
        
        buildAndSaveContactMail(requestData, contactEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveRetailerContactMail(final RequestData requestData, final RetailerContactForm retailerContactForm) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final String contextNameValue = requestData.getContextNameValue();

        final Retailer retailerToContact = retailerService.getRetailerByCode(marketArea.getId(), retailer.getId(), retailerContactForm.getRetailerCode());
        
        final RetailerContactEmailBean retailerContactEmailBean = new RetailerContactEmailBean();
        BeanUtils.copyProperties(retailerContactForm, retailerContactEmailBean);
        retailerContactEmailBean.setFromAddress(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
        retailerContactEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
        retailerContactEmailBean.setReplyToEmail(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
        retailerContactEmailBean.setToEmail(retailerToContact.getDefaultAddress().getEmail());
        
        buildAndSaveRetailerContactMail(requestData, retailerToContact, retailerContactEmailBean);
    }
    
    /**
     * 
     */
    public void saveAndBuildNewsletterSubscriptionConfirmationMail(final RequestData requestData, final String email) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        final NewsletterEmailBean newsletterEmailBean = new NewsletterEmailBean();
        newsletterEmailBean.setFromAddress(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION));
        newsletterEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION));
        newsletterEmailBean.setReplyToEmail(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION));
        newsletterEmailBean.setToEmail(email);

        saveAndBuildNewsletterSubscriptionConfirmationMail(requestData, newsletterEmailBean);
    }
    
    /**
     * 
     */
    public void saveAndBuildNewsletterUnsubscriptionConfirmationMail(final RequestData requestData, final String email) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        final NewsletterEmailBean newsletterEmailBean = new NewsletterEmailBean();
        newsletterEmailBean.setFromAddress(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_UNSUBSCRIPTION));
        newsletterEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_UNSUBSCRIPTION));
        newsletterEmailBean.setReplyToEmail(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_UNSUBSCRIPTION));
        newsletterEmailBean.setToEmail(email);
        
        saveAndBuildNewsletterUnsubscriptionConfirmationMail(requestData, newsletterEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveCustomerNewAccountMail(final RequestData requestData, final CreateAccountForm createAccountForm) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean = new CustomerNewAccountConfirmationEmailBean();
        BeanUtils.copyProperties(createAccountForm, customerNewAccountConfirmationEmailBean);
        customerNewAccountConfirmationEmailBean.setFromAddress(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        customerNewAccountConfirmationEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        customerNewAccountConfirmationEmailBean.setReplyToEmail(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        customerNewAccountConfirmationEmailBean.setToEmail(createAccountForm.getEmail());

        customerNewAccountConfirmationEmailBean.setTitle(createAccountForm.getTitle());
        customerNewAccountConfirmationEmailBean.setFirstname(createAccountForm.getFirstname());
        customerNewAccountConfirmationEmailBean.setLastname(createAccountForm.getLastname());
        customerNewAccountConfirmationEmailBean.setEmail(createAccountForm.getEmail());
        
        customerNewAccountConfirmationEmailBean.setCustomerDetailsUrl(urlService.buildAbsoluteUrl(requestData, urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData)));
        
        buildAndSaveCustomerNewAccountMail(requestData, customerNewAccountConfirmationEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveCustomerForgottenPasswordMail(final RequestData requestData, final Customer customer, final CustomerCredential customerCredential, final ForgottenPasswordForm forgottenPasswordForm) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();
        final String contextNameValue = requestData.getContextNameValue();

        final CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean = new CustomerForgottenPasswordEmailBean();
        BeanUtils.copyProperties(forgottenPasswordForm, customerForgottenPasswordEmailBean);
        customerForgottenPasswordEmailBean.setFromAddress(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
        customerForgottenPasswordEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
        customerForgottenPasswordEmailBean.setReplyToEmail(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
        customerForgottenPasswordEmailBean.setToEmail(customer.getEmail());
        customerForgottenPasswordEmailBean.setToken(customerCredential.getResetToken());
        
        customerForgottenPasswordEmailBean.setTitle(referentialDataService.getTitleByLocale(customer.getTitle(), locale));
        customerForgottenPasswordEmailBean.setFirstname(customer.getFirstname());
        customerForgottenPasswordEmailBean.setLastname(customer.getLastname());
        customerForgottenPasswordEmailBean.setEmail(customer.getEmail());
        
        buildAndSaveCustomerForgottenPasswordMail(requestData, customer, customerCredential, customerForgottenPasswordEmailBean);
    }
    

    /**
     * 
     */
    public CustomerCredential flagCustomerCredentialWithToken(final RequestData requestData, final Customer customer) throws Exception {
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
    public void cancelCustomerCredentialToken(final RequestData requestData, final Customer customer) throws Exception {
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
    public void resetCustomerCredential(final RequestData requestData, final Customer customer, final String newPassword) throws Exception {
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
    public Customer buildAndSaveNewCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
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

        customerService.saveOrUpdateCustomer(customer);
        
        CustomerGroup customerGroup = customerGroupService.getCustomerGroupByCode(CustomerGroup.GROUP_FO_CUSTOMER);
        customer.getGroups().add(customerGroup);
        
        customerService.saveOrUpdateCustomer(customer);
        
        customer = customerService.getCustomerByLoginOrEmail(customer.getLogin());
        requestUtil.updateCurrentCustomer(request, customer);
        
        return customer;
    }
    
    public Customer updateCurrentCustomer(final RequestData requestData, Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
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
    
    public Customer updateOrSaveAddressCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        // UPDATE CUSTOMER
        customerService.saveOrUpdateCustomer(customer);
        
        // UPDATE SESSION
        customer = customerService.getCustomerByLoginOrEmail(customer.getLogin());
        requestUtil.updateCurrentCustomer(request, customer);
        
        return customer;
    }
    
    public Customer deleteAddressCustomer(final RequestData requestData, final String customerAddressId) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
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
        customer = customerService.getCustomerByLoginOrEmail(customer.getEmail());
        requestUtil.updateCurrentCustomer(request, customer);
        
        return customer;
    }
    
    public Customer addProductSkuToWishlist(final RequestData requestData, final String productSkuCode) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
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
            throw new ProductAlreadyExistInWishlistException(); 
        }
        return customer;
    }
    
    public Customer removeProductSkuFromWishlist(final RequestData requestData, final String productSkuCode) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
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
    
    public void savePaymentInformation(RequestData requestData, PaymentForm paymentForm) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        Customer customer = requestData.getCustomer();
        
        final CustomerPaymentInformation customerPaymentInformation = new CustomerPaymentInformation();
        customerPaymentInformation.setPaymentType(paymentForm.getPaymentType());
        customerPaymentInformation.setCardHolderName(paymentForm.getCardHolderName());
        customerPaymentInformation.setCardNumber(paymentForm.getCardNumber());
        customerPaymentInformation.setCardExpMonth(paymentForm.getCardExpMonth());
        customerPaymentInformation.setCardExpYear(paymentForm.getCardExpYear());
        customerPaymentInformation.setCardCVV(paymentForm.getCardCVV());
        customerPaymentInformation.setCustomerMarketAreaId(marketArea.getId());
        customerPaymentInformation.setDateCreate(new Date());
        customerPaymentInformation.setDateUpdate(new Date());
        customer.getPaymentInformations().add(customerPaymentInformation);

        customerService.saveOrUpdateCustomer(customer);
        customer = customerService.getCustomerByLoginOrEmail(customer.getEmail());
        requestUtil.updateCurrentCustomer(request, customer);
    }
    
    public OrderCustomer buildAndSaveNewOrder(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Customer customer = requestData.getCustomer();
        final Cart cart = requestData.getCart();
        
        OrderCustomer orderCustomer = new OrderCustomer();
        // ORDER NUMBER IS CREATE BY DAO
        
        orderCustomer.setStatus(OrderStatus.ORDER_STATUS_PENDING.getPropertyKey());

        orderCustomer.setCurrency(cart.getCurrency());
        orderCustomer.setMarketAreaId(cart.getMarketAreaId());
        orderCustomer.setRetailerId(cart.getRetailerId());
        orderCustomer.setLocalizationId(cart.getLocalizationId());
        orderCustomer.setCustomerId(customer.getId());
        
        OrderAddress billingAddress = new OrderAddress();
        BeanUtils.copyProperties(customer.getAddress(cart.getBillingAddressId()), billingAddress);
        orderCustomer.setBillingAddress(billingAddress);

        OrderAddress shippingAddress = new OrderAddress();
        BeanUtils.copyProperties(customer.getAddress(cart.getShippingAddressId()), shippingAddress);
        orderCustomer.setShippingAddress(shippingAddress);
        
        // SHIPMENT
        Set<OrderShipment> orderShipments = new HashSet<OrderShipment>();
        Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
        if(deliveryMethods != null){
            for (Iterator<DeliveryMethod> iteratorDeliveryMethod = deliveryMethods.iterator(); iteratorDeliveryMethod.hasNext();) {
                DeliveryMethod deliveryMethod = (DeliveryMethod) iteratorDeliveryMethod.next();
                OrderShipment orderShipment = new OrderShipment();
                orderShipment.setName(deliveryMethod.getName());
                orderShipment.setExpectedDeliveryDate(null);
                orderShipment.setDeliveryMethodId(deliveryMethod.getId());
                orderShipment.setPrice(deliveryMethod.getPrice(cart.getMarketAreaId(), cart.getRetailerId()));
                
                Set<CartItem> cartItems = cart.getCartItems();
                Set<OrderItem> orderItems = new HashSet<OrderItem>();
                for (Iterator<CartItem> iteratorCartItem = cartItems.iterator(); iteratorCartItem.hasNext();) {
                    CartItem cartItem = (CartItem) iteratorCartItem.next();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setCurrency(cart.getCurrency());
                    orderItem.setProductSkuCode(cartItem.getProductSkuCode());
                    orderItem.setProductSku(cartItem.getProductSku());
                    orderItem.setPrice(cartItem.getPrice(cart.getMarketAreaId(), cart.getRetailerId()).getSalePrice());
                    orderItem.setQuantity(cartItem.getQuantity());
                    
                    // TAXES
                    Set<CartItemTax> taxes = cartItem.getTaxes();
                    if(taxes != null){
                        for (Iterator<CartItemTax> iteratorCartItemTax = taxes.iterator(); iteratorCartItemTax.hasNext();) {
                            CartItemTax cartItemTax = (CartItemTax) iteratorCartItemTax.next();
                            OrderTax orderTax = new OrderTax();
                            orderTax.setName(cartItemTax.getTax().getName());
                            orderTax.setPercent(cartItemTax.getTax().getPercent());
                            orderTax.setAmount(cartItemTax.getTaxAmount());
                            orderItem.getOrderTaxes().add(orderTax);
                        }
                    }
                    
                    orderItems.add(orderItem);
                }
                orderShipment.setOrderItems(orderItems);
                orderShipments.add(orderShipment);
            }
        }
        orderCustomer.setOrderShipments(orderShipments);
        
        orderCustomer = orderCustomerService.createNewOrder(orderCustomer);
        
        requestUtil.deleteCurrentCartAndSaveEngineSession(request);

        // RELOAD ORDER FOR THE SESSION
        orderCustomer = orderCustomerService.getOrderByOrderNum(orderCustomer.getOrderNum());
        requestUtil.keepLastOrderInSession(requestData, orderCustomer);
        
        buildAndSaveNewOrderConfirmationMail(requestData, customer, orderCustomer);
        
        return orderCustomer;
    }
    
    public Customer saveNewsletterSubscription(final RequestData requestData, final String email) throws Exception {
        Customer customer = customerService.getCustomerByLoginOrEmail(email);
        MarketArea marketArea = requestData.getMarketArea();
        
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
        
        customer = updateCurrentCustomer(requestData, customer);
        return customer;
    }
    
    public Customer saveNewsletterUnsubscription(final RequestData requestData, final String email) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Customer customer = customerService.getCustomerByLoginOrEmail(email);
        MarketArea marketArea = requestData.getMarketArea();
        
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
        
        customer = updateCurrentCustomer(requestData, customer);
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
        final Locale locale = requestData.getLocale();
        final String contextNameValue = requestData.getContextNameValue();
        final String velocityPath = requestData.getVelocityEmailPrefix();

        final CustomerResetPasswordConfirmationEmailBean customerResetPasswordConfirmationEmailBean = new CustomerResetPasswordConfirmationEmailBean();
        customerResetPasswordConfirmationEmailBean.setFromAddress(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        customerResetPasswordConfirmationEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        customerResetPasswordConfirmationEmailBean.setReplyToEmail(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        customerResetPasswordConfirmationEmailBean.setToEmail(customer.getEmail());
        
        customerResetPasswordConfirmationEmailBean.setTitle(referentialDataService.getTitleByLocale(customer.getTitle(), locale));
        customerResetPasswordConfirmationEmailBean.setFirstname(customer.getFirstname());
        customerResetPasswordConfirmationEmailBean.setLastname(customer.getLastname());
        customerResetPasswordConfirmationEmailBean.setEmail(customer.getEmail());
        
        customerResetPasswordConfirmationEmailBean.setCustomerDetailsUrl(urlService.buildAbsoluteUrl(requestData, urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData)));
        
        emailService.buildAndSaveCustomerResetPasswordConfirmationMail(requestData, customer, velocityPath, customerResetPasswordConfirmationEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveNewOrderConfirmationMail(final RequestData requestData, final Customer customer, final OrderCustomer order) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();
        final String velocityPath = requestData.getVelocityEmailPrefix();

        final OrderConfirmationEmailBean orderConfirmationEmailBean = new OrderConfirmationEmailBean();
        orderConfirmationEmailBean.setFromAddress(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        orderConfirmationEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        orderConfirmationEmailBean.setReplyToEmail(marketArea.getEmailFromAddress(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        orderConfirmationEmailBean.setToEmail(customer.getEmail());
        
        if (order != null) {
            orderConfirmationEmailBean.setOrderNumber(order.getOrderNum());
            
            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            if (order.getExpectedDeliveryDate() != null) {
                orderConfirmationEmailBean.setExpectedDeliveryDate(dateFormat.format(order.getExpectedDeliveryDate()));
            } else {
                orderConfirmationEmailBean.setExpectedDeliveryDate("NA");
            }
            
            orderConfirmationEmailBean.setOrderItemsTotalWithCurrencySign(order.getOrderItemTotalWithStandardCurrencySign());
            orderConfirmationEmailBean.setOrderShippingTotalWithCurrencySign(order.getShippingTotalWithStandardCurrencySign());
            orderConfirmationEmailBean.setOrderTaxesTotalWithCurrencySign(order.getTaxTotalWithStandardCurrencySign());
            orderConfirmationEmailBean.setOrderTotalWithCurrencySign(order.getOrderTotalWithStandardCurrencySign());
        }
        
        emailService.buildAndSaveNewOrderConfirmationMail(requestData, customer, velocityPath, orderConfirmationEmailBean);
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