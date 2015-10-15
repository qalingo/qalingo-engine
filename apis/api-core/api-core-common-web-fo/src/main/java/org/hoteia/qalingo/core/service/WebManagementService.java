/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.*;
import org.hoteia.qalingo.core.domain.enumtype.CustomerPlatformOrigin;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.email.bean.*;
import org.hoteia.qalingo.core.exception.UniqueNewsletterSubscriptionException;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.fetchplan.customer.FetchPlanGraphCustomer;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.security.helper.SecurityUtil;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.hoteia.qalingo.core.web.mvc.form.*;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;

@Service("webManagementService")
@Transactional
public class WebManagementService {
    
    @Autowired
    protected CustomerService customerService;
    
    @Autowired
    protected RetailerService retailerService;
    
    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    @Autowired
    protected ProductService productService;
    
    @Autowired
    protected CheckoutService checkoutService;
    
    @Autowired
    protected CartService cartService;
    
    @Autowired
    protected OrderPurchaseService orderPurchaseService;
    
    @Autowired
    protected EngineSettingService engineSettingService;
    
    @Autowired
    protected EngineSessionService engineSessionService;
    
    @Autowired
    protected ReferentialDataService referentialDataService;
    
    @Autowired
    protected UrlService urlService;
    
    @Autowired
    protected EmailService emailService;
    
    @Autowired
    protected RequestUtil requestUtil;
    
    @Autowired
    protected SecurityUtil securityUtil;
    
    /**
     * 
     */
    public void addToCart(final RequestData requestData, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        final Retailer retailer = requestData.getMarketAreaRetailer();
        addToCart(requestData, retailer, catalogCategoryCode, productSkuCode, quantity);
    }
    
    /**
     * 
     */
    public void addToCart(final RequestData requestData, final Retailer retailer, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        // SANITY CHECK : sku code is empty or null : no sense
        if (StringUtils.isEmpty(productSkuCode)) {
            throw new Exception("");
        }

        // SANITY CHECK : quantity is equal zero : no sense
        if (quantity == 0) {
            throw new Exception("");
        }

        Cart cart = requestData.getCart();
//        int finalQuantity = quantity;
//        if(cart != null){
//            Set<CartItem> cartItems = cart.getCartItems();
//            for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
//                CartItem cartItem = (CartItem) iterator.next();
//                if (cartItem.getProductSkuCode().equalsIgnoreCase(productSkuCode)
//                        && cartItem.getRetailerId().equals(retailer)) {
//                    finalQuantity = finalQuantity + cartItem.getQuantity();
//                }
//            }
//        }
        final HttpServletRequest request = requestData.getRequest();
        cart = cartService.addProductSkuToCart(cart, retailer, requestData.getVirtualCatalogCode(), catalogCategoryCode, productSkuCode, quantity);
        
        // RELOAD BECAUSE PREVIOUS PERSIT BREAK THE FETCHPLAN
        Cart newCart = cartService.getCartById(cart.getId());
        newCart.copyTransient(cart);
        
        requestUtil.updateCurrentCart(request, newCart);
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
    public void updateCart(final RequestData requestData, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        final Retailer retailer = requestData.getMarketAreaRetailer();
        updateCart(requestData, retailer, catalogCategoryCode, productSkuCode, quantity);
    }
    
    /**
     * 
     */
    public void updateCart(final RequestData requestData, final Retailer retailer, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
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

        cartService.updateCartItem(cart, retailer, requestData.getVirtualCatalogCode(), catalogCategoryCode, productSkuCode, quantity);
        
        // RELOAD BECAUSE PREVIOUS PERSIT BREAK THE FETCHPLAN
        Cart newCart = cartService.getCartById(cart.getId());
        newCart.copyTransient(cart);
        
        requestUtil.updateCurrentCart(request, newCart);
    }
    
    /**
     * 
     */
    public void linkAndUpdateCartWithCustomer(final RequestData requestData, final Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Cart cart = requestData.getCart();
        if(cart != null){
            cart.setCustomerId(customer.getId());
            cart.setBillingAddressId(customer.getDefaultBillingAddressId());
            cart.setShippingAddressId(customer.getDefaultShippingAddressId());
        }
        
        cart = cartService.saveOrUpdateCart(cart);
        
        // RELOAD BECAUSE PREVIOUS PERSIT BREAK THE FETCHPLAN
        Cart newCart = cartService.getCartById(cart.getId());
        newCart.copyTransient(cart);
        
        requestUtil.updateCurrentCart(request, newCart);
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
        
        cart = cartService.saveOrUpdateCart(cart);
        
        // RELOAD BECAUSE PREVIOUS PERSIT BREAK THE FETCHPLAN
        Cart newCart = cartService.getCartById(cart.getId());
        newCart.copyTransient(cart);
        
        requestUtil.updateCurrentCart(request, newCart);
    }

    /**
     * 
     */
    public void deleteCartItem(final RequestData requestData, final String productSkuCode) throws Exception {
        final Retailer retailer = requestData.getMarketAreaRetailer();
        deleteCartItem(requestData, retailer, productSkuCode);
    }
    
    /**
     * 
     */
    public void deleteCartItem(final RequestData requestData, final Retailer retailer, final String productSkuCode) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Cart cart = requestData.getCart();
        
        cartService.deleteCartItem(cart, retailer, productSkuCode);
        
        // RELOAD BECAUSE PREVIOUS PERSIT BREAK THE FETCHPLAN
        Cart newCart = cartService.getCartById(cart.getId());
        newCart.copyTransient(cart);
        
        requestUtil.updateCurrentCart(request, newCart);
    }
    
    /**
     * 
     */
    public void setShippingAddress(final RequestData requestData, final String customerShippingAddressId) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Customer customer = requestData.getCustomer();
        Cart cart = requestData.getCart();
        Long customerAddressId = Long.parseLong(customerShippingAddressId);
        cartService.setShippingAddress(cart, customer, customerAddressId);
        
        // RELOAD BECAUSE PREVIOUS PERSIT BREAK THE FETCHPLAN
        Cart newCart = cartService.getCartById(cart.getId());
        newCart.copyTransient(cart);
        
        requestUtil.updateCurrentCart(request, newCart);
    }
    
    /**
     * 
     */
    public void setBillingAddress(final RequestData requestData, final String customerBillingAddressId) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Customer customer = requestData.getCustomer();
        Cart cart = requestData.getCart();
        Long customerAddressId = Long.parseLong(customerBillingAddressId);
        cartService.setBillingAddress(cart, customer, customerAddressId);
        
        // RELOAD BECAUSE PREVIOUS PERSIT BREAK THE FETCHPLAN
        Cart newCart = cartService.getCartById(cart.getId());
        newCart.copyTransient(cart);
        
        requestUtil.updateCurrentCart(request, newCart);
    }
    
    /**
     * 
     */
    public void setDeliveryMethod(final RequestData requestData, final String deliveryMethodCode) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        Cart cart = requestData.getCart();
        cartService.setDeliveryMethod(cart, deliveryMethodCode);
        
        // RELOAD BECAUSE PREVIOUS PERSIT BREAK THE FETCHPLAN
        Cart newCart = cartService.getCartById(cart.getId());
        newCart.copyTransient(cart);
        
        requestUtil.updateCurrentCart(request, newCart);
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
    public Customer buildAndSaveQuickNewCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, 
                                                 final CreateAccountForm createAccountForm) throws Exception {
        Customer customer = new Customer();
        
        customer.setLogin(createAccountForm.getEmail());
        customer.setLastname(createAccountForm.getLastname());
        customer.setPassword(securityUtil.generateAndEncodePassword());
        
        customer.setEmail(createAccountForm.getEmail());

        return buildAndSaveNewCustomer(requestData, market, marketArea, customer);
    }
    
    /**
     * 
     */
    public Customer buildAndSaveNewCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, 
                                            final CreateAccountForm createAccountForm) throws Exception {
        Customer customer = new Customer();
        
        customer.setLogin(createAccountForm.getEmail());
        customer.setTitle(createAccountForm.getTitle());
        customer.setFirstname(createAccountForm.getFirstname());
        customer.setLastname(createAccountForm.getLastname());
        customer.setPassword(securityUtil.encodePassword(createAccountForm.getPassword()));
        
        customer.setEmail(createAccountForm.getEmail());

        customer = checkCustomerMarketArea(requestData, customer);
        CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
        if(customerMarketArea != null){
            customerMarketArea.setMobile(createAccountForm.getMobile());
            customerMarketArea.setPhone(createAccountForm.getPhone());
            customerMarketArea.setFax(createAccountForm.getFax());
            
            if(createAccountForm.isOptin()){
                CustomerOptin customerOptin = new CustomerOptin();
                customerOptin.setType("NEWSLETTER");
                customerOptin.setOrigin("STANDARD");
                customerOptin.setDateCreate(new Date());
                customerOptin.setDateUpdate(new Date());
                customerOptin.setCustomerMarketAreaId(customerMarketArea.getId());
                customerMarketArea.getOptins().add(customerOptin);
            }
        }

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
        customer.getAddresses().add(defaultAddress);
        
        return buildAndSaveNewCustomer(requestData, market, marketArea, customer);
    }
    
    public Customer updateCurrentCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, final CustomerEditForm customerEditForm) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        String customerLogin = requestUtil.getCurrentCustomerLogin(request);
        Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
        
        customer.setTitle(customerEditForm.getTitle());
        customer.setFirstname(customerEditForm.getFirstname());
        customer.setLastname(customerEditForm.getLastname());

        customer.setEmail(customerEditForm.getEmail());

        customer = checkCustomerMarketArea(requestData, customer);
        CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
        if(customerMarketArea != null){
            customerMarketArea.setMobile(customerEditForm.getMobile());
            customerMarketArea.setPhone(customerEditForm.getPhone());
            customerMarketArea.setFax(customerEditForm.getFax());
            
            if(customerEditForm.isOptin()){
                CustomerOptin customerOptin = new CustomerOptin();
                customerOptin.setType("NEWSLETTER");
                customerOptin.setOrigin("STANDARD");
                customerOptin.setDateCreate(new Date());
                customerOptin.setDateUpdate(new Date());
                customerOptin.setCustomerMarketAreaId(customerMarketArea.getId());
                customerMarketArea.getOptins().add(customerOptin);
            }
        }

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
        customerAddress.setDateCreate(new Date());
        customerAddress.setDateUpdate(new Date());
        
        String addressId = customerAddressForm.getId();
        if(StringUtils.isNotEmpty(addressId)){
            customerAddress = customer.getAddress(new Long(addressId));
        }
        
        if(StringUtils.isNotEmpty(customerAddressForm.getAddressName())){
            customerAddress.setAddressName(customerAddressForm.getAddressName());
        } else {
            customerAddress.setAddressName(customerAddressForm.getAddress1() + "(" + customerAddressForm.getCity() + ")");
        }
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
        if(StringUtils.isEmpty(email)){
            throw new Exception(""); 
        }
        Customer customer = saveNewsletterSubscription(requestData, email);
        saveAndBuildNewsletterSubscriptionConfirmationMail(requestData, email);
        return customer;
    }
    
    public Customer saveNewsletterUnsubscriptionAndSendEmail(final RequestData requestData, final String email) throws Exception {
        if(StringUtils.isEmpty(email)){
            throw new Exception(""); 
        }
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
        contactEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_CONTACT));
        contactEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_CONTACT));
        contactEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_CONTACT));
        contactEmailBean.setToEmail(marketArea.getEmailToContact(contextNameValue));
        
        buildAndSaveContactMail(requestData, contactEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveRetailerContactMail(final RequestData requestData, final CustomerContactForm customerContactForm) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        final List<SpecificFetchMode> retailerFetchPlans = new ArrayList<SpecificFetchMode>();
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.addresses.getName()));
        final Retailer retailerToContact = retailerService.getRetailerByCode(customerContactForm.getObjectCode(), new FetchPlan(retailerFetchPlans));
        
        final RetailerContactEmailBean retailerContactEmailBean = new RetailerContactEmailBean();
        BeanUtils.copyProperties(customerContactForm, retailerContactEmailBean);
        retailerContactEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
        retailerContactEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
        retailerContactEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
        retailerContactEmailBean.setToEmail(retailerToContact.getDefaultAddress().getEmail());
        
        buildAndSaveRetailerContactMail(requestData, retailerToContact, retailerContactEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveStoreContactMail(final RequestData requestData, final CustomerContactForm customerContactForm) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        final List<SpecificFetchMode> storeFetchPlans = new ArrayList<SpecificFetchMode>();
        final Retailer retailerToContact = retailerService.getRetailerByCode(customerContactForm.getObjectCode(), new FetchPlan(storeFetchPlans));
        
        final RetailerContactEmailBean retailerContactEmailBean = new RetailerContactEmailBean();
        BeanUtils.copyProperties(customerContactForm, retailerContactEmailBean);
        retailerContactEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
        retailerContactEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
        retailerContactEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RETAILER_CONTACT));
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
        newsletterEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION));
        newsletterEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION));
        newsletterEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION));
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
        newsletterEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_UNSUBSCRIPTION));
        newsletterEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_UNSUBSCRIPTION));
        newsletterEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_NEWSLETTER_UNSUBSCRIPTION));
        newsletterEmailBean.setToEmail(email);
        
        saveAndBuildNewsletterUnsubscriptionConfirmationMail(requestData, newsletterEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveCustomerNewAccountMail(final RequestData requestData, final Customer customer) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean = new CustomerNewAccountConfirmationEmailBean();
        BeanUtils.copyProperties(customer, customerNewAccountConfirmationEmailBean);
        customerNewAccountConfirmationEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        customerNewAccountConfirmationEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        customerNewAccountConfirmationEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        customerNewAccountConfirmationEmailBean.setToEmail(customer.getEmail());

        customerNewAccountConfirmationEmailBean.setTitle(customer.getTitle());
        customerNewAccountConfirmationEmailBean.setFirstname(customer.getFirstname());
        customerNewAccountConfirmationEmailBean.setLastname(customer.getLastname());
        customerNewAccountConfirmationEmailBean.setEmail(customer.getEmail());
        
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
        customerForgottenPasswordEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
        customerForgottenPasswordEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
        customerForgottenPasswordEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
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
    public void resetCustomerCredential(final RequestData requestData, final Customer customer, final ResetPasswordForm resetPasswordForm) throws Exception {
        // FLAG LAST CURRENT CREDENTIEL
        CustomerCredential customerCredential = customer.getCurrentCredential();
        if(customerCredential != null){
            customerCredential.setDateUpdate(new Date());
            customerCredential.setResetProcessedDate(new Date());
        }
        
        // ADD A NEW ONE
        addNewCustomerCredential(requestData, customer, resetPasswordForm.getNewPassword());
    }
    
    /**
     * 
     */
    public CustomerCredential flagCustomerCredentialWithToken(final RequestData requestData, final Customer customer) throws Exception {
        if(customer != null){
            String token = UUID.randomUUID().toString();
            CustomerCredential customerCredential = customer.getCurrentCredential();
            if(customerCredential == null){
                customerCredential = new CustomerCredential();
                customerCredential.setDateCreate(new Date());
                customer.getCredentials().add(customerCredential);
            }
            customerCredential.setDateUpdate(new Date());
            customerCredential.setResetToken(token);
            customerCredential.setTokenTimestamp(new Timestamp(new Date().getTime()));
            
            // UPDATE CUSTOMER
            customerService.saveOrUpdateCustomer(customer);
            
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
                customerCredential.setDateUpdate(new Date());
                customerCredential.setResetToken("");
                customerCredential.setTokenTimestamp(null);
                customerService.saveOrUpdateCustomerCredential(customerCredential);
            }
        }
    }
    
    /**
     * 
     */
    public void addNewCustomerCredential(final RequestData requestData, final Customer customer, final String newPassword) throws Exception {
        if(customer != null){
            String clearPassword = newPassword;
            String encodePassword = securityUtil.encodePassword(clearPassword);
            CustomerCredential customerCredential = new CustomerCredential();
            customerCredential.setPassword(encodePassword);
            customerCredential.setDateCreate(new Date());
            customerCredential.setDateUpdate(new Date());
            customer.getCredentials().add(customerCredential);
            
            customer.setPassword(encodePassword);
            
            customerService.saveOrUpdateCustomer(customer);
        }
    }
    
    /**
     * 
     */
    public Customer buildAndSaveNewCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        if(customer.getCode() == null){
            customer.setCode(CoreUtil.generateEntityCode());
        }
        
        if(customer.getPermalink() == null){
            String permalink = securityUtil.generatePermalink();
            customer.setPermalink(permalink);
        }
        
        customer.setPlatformOrigin(CustomerPlatformOrigin.STANDARD.getPropertyKey());
        customer.setMarketAreaOrigin(marketArea.getCode());
        
        customer.setDefaultLocale(marketArea.getDefaultLocalization().getCode());
        customer.setActive(true);
        customer.setDateCreate(new Date());
        customer.setDateUpdate(new Date());

        // WE SAVE A FIRST TIME TO EVICT DETACH ENTITY ISSUE WITH CUSTOMERGROUP - NOT THE BEST WAY
        Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);
        
        CustomerGroup customerGroup = customerService.getCustomerGroupByCode(CustomerGroup.GROUP_FO_CUSTOMER);
        savedCustomer.getGroups().add(customerGroup);
        
        savedCustomer = customerService.saveOrUpdateCustomer(savedCustomer);
        
        requestUtil.updateCurrentCustomer(request, savedCustomer);
        
        return savedCustomer;
    }
    
    public Customer updateCurrentCustomer(final RequestData requestData, Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        customer.setActive(true);
        customer.setDateUpdate(new Date());

        // save market context phone etc
        
        // UPDATE CUSTOMER
        Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);
        
        // UPDATE SESSION
        requestUtil.updateCurrentCustomer(request, savedCustomer);
        
        return savedCustomer;
    }
    
    public Customer updateOrSaveAddressCustomer(final RequestData requestData, final Market market, final MarketArea marketArea, Customer customer) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        
        // UPDATE CUSTOMER
        Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);
        
        // UPDATE SESSION
        requestUtil.updateCurrentCustomer(request, savedCustomer);
        
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
        Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);
        
        // UPDATE SESSION
        requestUtil.updateCurrentCustomer(request, savedCustomer);
        
        return customer;
    }
    
    public Customer addProductSkuToWishlist(final RequestData requestData, final String catalogCategoryCode, final String productSkuCode) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        Customer customer = requestData.getCustomer();
        customer = checkCustomerMarketArea(requestData, customer);
        
        customerService.addProductSkuToWishlist(marketArea, customer, catalogCategoryCode, productSkuCode);
        
        // UPDATE SESSION
        requestUtil.updateCurrentCustomer(request, customer);

        return customer;
    }
    
    public Customer removeProductSkuFromWishlist(final RequestData requestData, final String productSkuCode) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        Customer customer = requestData.getCustomer();
        customer = checkCustomerMarketArea(requestData, customer);
        
        customerService.removeProductSkuFromWishlist(marketArea, customer, productSkuCode);
        
        // UPDATE SESSION
        requestUtil.updateCurrentCustomer(request, customer);
        
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
        
        customerService.savePaymentInformation(customer, customerPaymentInformation);
        
        requestUtil.updateCurrentCustomer(request, customer);
    }
    
    public OrderPurchase buildAndSaveNewOrder(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Customer customer = requestData.getCustomer();
        final Cart cart = requestData.getCart();
        
        OrderPurchase orderPurchase = checkoutService.checkout(customer, cart);
        
        requestUtil.deleteCurrentCartAndSaveEngineSession(request);

        // RELOAD ORDER FOR THE SESSION
        orderPurchase = orderPurchaseService.getOrderByOrderNum(orderPurchase.getOrderNum());
        requestUtil.keepLastOrderInSession(requestData, orderPurchase);
        
        buildAndSaveNewOrderConfirmationMail(requestData, customer, orderPurchase);
        
        return orderPurchase;
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
                customerMarketArea = new CustomerMarketArea(marketArea.getId());
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
            CustomerMarketArea customerMarketArea = new CustomerMarketArea(marketArea.getId());
            customerMarketArea.addOptins(customerOptin);
            customer.getCustomerMarketAreas().add(customerMarketArea);
        }
        
        customer = updateCurrentCustomer(requestData, customer);
        return customer;
    }
    
    public Customer saveNewsletterUnsubscription(final RequestData requestData, final String email) throws Exception {
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
        emailService.buildAndSaveNewsletterSubscriptionConfirmationMail(requestData, requestData.getVelocityEmailPrefix(), newsletterEmailBean);
    }
    
    /**
     * 
     */
    public void saveAndBuildNewsletterUnsubscriptionConfirmationMail(final RequestData requestData, final NewsletterEmailBean newsletterEmailBean) throws Exception {
        emailService.buildAndSaveNewsletterUnsubscriptionConfirmationMail(requestData, requestData.getVelocityEmailPrefix(), newsletterEmailBean);
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
        customerResetPasswordConfirmationEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        customerResetPasswordConfirmationEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        customerResetPasswordConfirmationEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
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
    public void buildAndSaveNewOrderConfirmationMail(final RequestData requestData, final Customer customer, final OrderPurchase order) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();
        final String velocityPath = requestData.getVelocityEmailPrefix();

        final OrderConfirmationEmailBean orderConfirmationEmailBean = new OrderConfirmationEmailBean();
        orderConfirmationEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        orderConfirmationEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        orderConfirmationEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        orderConfirmationEmailBean.setToEmail(customer.getEmail());
        
        if (order != null) {
            orderConfirmationEmailBean.setOrderNumber(order.getOrderNum());
            
            DateFormat dateFormat = requestUtil.getCommonFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
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
    
    protected String getEmailFromAddress(final RequestData requestData, final MarketArea marketArea, final String contextNameValue, final String emailType) throws Exception{
        String emailFromAddress = marketArea.getEmailFromAddress(contextNameValue, emailType);
        if(StringUtils.isEmpty(emailFromAddress)){
            final HttpServletRequest request = requestData.getRequest();
            final String contextValue = requestUtil.getCurrentContextNameValue();
            emailFromAddress = engineSettingService.getDefaultEmailAddress(contextValue);
        }
        return emailFromAddress;
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
                customer = customerService.getCustomerByLoginOrEmail(customer.getEmail(), FetchPlanGraphCustomer.fullCustomerFetchPlan());
            }
        }
        return customer;
    }
    
}
