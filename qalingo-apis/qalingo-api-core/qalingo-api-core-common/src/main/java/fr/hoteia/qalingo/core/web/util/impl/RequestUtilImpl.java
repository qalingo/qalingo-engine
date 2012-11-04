/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.util.impl;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamListener;
import com.opensymphony.clickstream.ClickstreamRequest;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.common.domain.Cart;
import fr.hoteia.qalingo.core.common.domain.CartItem;
import fr.hoteia.qalingo.core.common.domain.Company;
import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.EngineSession;
import fr.hoteia.qalingo.core.common.domain.EngineSetting;
import fr.hoteia.qalingo.core.common.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Order;
import fr.hoteia.qalingo.core.common.domain.ProductSku;
import fr.hoteia.qalingo.core.common.domain.Retailer;
import fr.hoteia.qalingo.core.common.domain.User;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;
import fr.hoteia.qalingo.core.common.service.MarketPlaceService;
import fr.hoteia.qalingo.core.common.service.MarketService;
import fr.hoteia.qalingo.core.common.service.ProductSkuService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;

/**
 * <p>
 * <a href="RequestUtilImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@Service("requestUtil")
@Transactional
public class RequestUtilImpl implements RequestUtil {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected MarketPlaceService marketPlaceService;
	
	@Autowired
	protected MarketService marketService;
	
	@Autowired
	protected ProductSkuService productSkuService;
	
	@Autowired
	protected EngineSettingService engineSettingService;
	
	@Value("${env.name}")  
	protected String environmentName;
	
	/**
	 *
	 */
	public boolean isLocalHostMode(final HttpServletRequest request) throws Exception {
		if (StringUtils.isNotEmpty(getHost(request)) 
				&& getHost(request).equalsIgnoreCase("localhost:8080")) {
			return true;
		}
		return false;
	}
	
	/**
	 *
	 */
	public String getHost(final HttpServletRequest request) throws Exception {
		return (String) request.getHeader(Constants.HOST);
	}
	
	/**
	 *
	 */
	public String getEnvironmentName() throws Exception {
		return environmentName;
	}
	
	/**
	 *
	 */
	public DateFormat getFormatDate(final HttpServletRequest request, final int dateStyle, final int timeStyle) throws Exception {
		final Localization localization = getCurrentLocalization(request);
		final Locale locale = localization.getLocale();
		DateFormat formatter = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale); 
		return formatter;
	}
	
	/**
	 *
	 */
	public SimpleDateFormat getRssFormatDate(final HttpServletRequest request) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
		return formatter;
	}
	
	/**
	 *
	 */
	public SimpleDateFormat getAtomFormatDate(final HttpServletRequest request) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
		return formatter;
	}
	
	/**
	 *
	 */
	public NumberFormat getCartItemPriceNumberFormat(final HttpServletRequest request, final String currencyCode) throws Exception {
		return getNumberFormat(request, currencyCode, RoundingMode.HALF_EVEN, 2, 2, 1000000, 1);
	}
	
	/**
	 *
	 */
	public NumberFormat getNumberFormat(final HttpServletRequest request, final String currencyCode, final RoundingMode roundingMode, final int maximumFractionDigits,
			final int minimumFractionDigits, final int maximumIntegerDigits, final int minimumIntegerDigits) throws Exception {
		final Localization localization = getCurrentLocalization(request);
		final Locale locale = localization.getLocale();
		NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
		formatter.setGroupingUsed(true);
		formatter.setParseIntegerOnly(false);
		formatter.setRoundingMode(roundingMode);
		Currency currency = Currency.getInstance(currencyCode);
		formatter.setCurrency(currency);
		
		formatter.setMaximumFractionDigits(maximumFractionDigits);
		formatter.setMinimumFractionDigits(minimumFractionDigits);

		formatter.setMaximumIntegerDigits(maximumIntegerDigits);
		formatter.setMinimumIntegerDigits(minimumIntegerDigits);
		
		return formatter;
	}
	
	/**
     * 
     */
	public String getLastRequestUrlNotSecurity(final HttpServletRequest request) throws Exception {
		final List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add("login");
		excludedPatterns.add("auth");
		excludedPatterns.add("logout");
		excludedPatterns.add("timeout");
		excludedPatterns.add("forbidden");
        return getRequestUrl(request, excludedPatterns, 1);
	}
	
	/**
     * 
     */
	public String getCurrentRequestUrl(final HttpServletRequest request, final List<String> excludedPatterns) throws Exception {
        return getRequestUrl(request, excludedPatterns, 0);
	}
	
	/**
     * 
     */
	public String getCurrentRequestUrl(final HttpServletRequest request) throws Exception {
        return getRequestUrl(request, new ArrayList<String>(), 0);
	}
	
	/**
     * 
     */
	public String getCurrentRequestUrlNotSecurity(final HttpServletRequest request) throws Exception {
		final List<String> excludedPatterns = new ArrayList<String>();
		excludedPatterns.add("login");
		excludedPatterns.add("auth");
		excludedPatterns.add("logout");
		excludedPatterns.add("timeout");
		excludedPatterns.add("forbidden");
        return getRequestUrl(request, excludedPatterns, 0);
	}
	
	/**
     * 
     */
	public String getLastRequestUrl(final HttpServletRequest request, final List<String> excludedPatterns) throws Exception {
        return getRequestUrl(request, excludedPatterns, 1);
	}
	
	/**
     * 
     */
	public String getLastRequestUrl(final HttpServletRequest request) throws Exception {
        return getRequestUrl(request, new ArrayList<String>(), 1);
	}
	
	/**
     * 
     */
	public String getRequestUrl(final HttpServletRequest request,  final List<String> excludedPatterns, int position) throws Exception {
		
		String url = Constants.EMPTY;
		final List<ClickstreamRequest> clickstreams = getClickStreamRequests(request);

        if(clickstreams != null
				&& !clickstreams.isEmpty()) {
            if(clickstreams != null
    				&& !clickstreams.isEmpty()) {
            	// Clean not html values or exluded patterns
            	List<ClickstreamRequest> cleanClickstreams = new ArrayList<ClickstreamRequest>();
        		Iterator<ClickstreamRequest> it = clickstreams.iterator();
        		while (it.hasNext()) {
    	        	ClickstreamRequest clickstream = (ClickstreamRequest) it.next();
    	        	String uri = clickstream.getRequestURI();
    	        	if(uri.endsWith(".html")){
	        			// TEST IF THE URL IS EXCLUDE
    	        		CharSequence[] excludedPatternsCharSequence = excludedPatterns.toArray(new CharSequence[excludedPatterns.size()]);
    	        		boolean isExclude = false;
	    	    		for (int i = 0; i < excludedPatternsCharSequence.length; i++) {
	    	    			CharSequence string = excludedPatternsCharSequence[i];
	    	    			if(uri.contains(string)){
	    	    				isExclude = true;
	    	    			}
	    	    		}
	    	    		if(BooleanUtils.negate(isExclude)){
	    	        		cleanClickstreams.add(clickstream);
	    	    		}
    	        	}
        		}
            	
            	if(cleanClickstreams.size() == 1) {
            		Iterator<ClickstreamRequest> itCleanClickstreams = cleanClickstreams.iterator();
            		while (itCleanClickstreams.hasNext()) {
        	        	ClickstreamRequest clickstream = (ClickstreamRequest) itCleanClickstreams.next();
        	        	String uri = clickstream.getRequestURI();
       	        		url = uri;
        	        }
            	} else {
            		Iterator<ClickstreamRequest> itCleanClickstreams = cleanClickstreams.iterator();
            		int countCleanClickstream = 1;
            		while (itCleanClickstreams.hasNext()) {
        	        	ClickstreamRequest clickstream = (ClickstreamRequest) itCleanClickstreams.next();
        	        	String uri = clickstream.getRequestURI();
        	        	// The last url is the current URI, so we need to get the url previous the last
        	        	if(countCleanClickstream == (cleanClickstreams.size() - position)) {
        	        		url = uri;
        	        	}
        	        	countCleanClickstream++;
        	        }
            	}
            }
		}
        return handleUrl(url);
	}
	
	/**
     * 
     */
	public EngineSession getCurrentSession(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = (EngineSession) request.getSession().getAttribute(Constants.ECO_SESSION_OBJECT);
		engineSession = checkEngineSession(request, engineSession);
		return engineSession;
	}

	/**
     * 
     */
	public void updateCurrentSession(final HttpServletRequest request, final EngineSession engineSession) throws Exception {
		setCurrentSession(request, engineSession);
	}
	
	/**
     * 
     */
	public void setCurrentSession(final HttpServletRequest request, final EngineSession engineSession) throws Exception {
		request.getSession().setAttribute(Constants.ECO_SESSION_OBJECT, engineSession);
	}
	
	/**
     * 
     */
	public Cart getCurrentCart(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		return engineSession.getCart();
	}
	
	/**
     * 
     */
	public void updateCurrentCart(final HttpServletRequest request, final Cart cart) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		engineSession.setCart(cart);
		updateCurrentSession(request, engineSession);
	}
	
	/**
     * 
     */
	public void updateCurrentCart(final HttpServletRequest request, final String skuCode, final int quantity) throws Exception {
		// SANITY CHECK : sku code is empty or null : no sense
		if(StringUtils.isEmpty(skuCode)){
			throw new Exception(""); 
		}

		// SANITY CHECK : quantity is equal zero : no sense
		if(StringUtils.isEmpty(skuCode)){
			throw new Exception(""); 
		}
		
		Cart cart = getCurrentCart(request);
		Set<CartItem> cartItems = cart.getCartItems();
		boolean productSkuIsNew = true;
		for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
			CartItem cartItem = (CartItem) iterator.next();
			if(cartItem.getProductSkuCode().equalsIgnoreCase(skuCode)){
				int newQuantity = cartItem.getQuantity() + quantity;
				cartItem.setQuantity(newQuantity);
				productSkuIsNew = false;
			}
		}
		if(productSkuIsNew){
			final MarketArea marketArea = getCurrentMarketArea(request);
			final Retailer retailer = getCurrentRetailer(request);
			CartItem cartItem = new CartItem();
			ProductSku productSku = productSkuService.getProductSkuByCode(marketArea.getId(), retailer.getId(), skuCode);
			cartItem.setProductSkuCode(skuCode);
			cartItem.setProductSku(productSku);
			cartItem.setQuantity(quantity);
			cart.getCartItems().add(cartItem);
		}
		updateCurrentCart(request, cart);
		
		// TODO update session/cart db ?
	}
	
	/**
     * 
     */
	public void updateCurrentCart(final HttpServletRequest request, final Long billingAddressId, final Long shippingAddressId) throws Exception {
		Cart cart = getCurrentCart(request);
		cart.setBillingAddressId(billingAddressId);
		cart.setShippingAddressId(shippingAddressId);
		updateCurrentCart(request, cart);
		
		// TODO update session/cart db ?
	}
	
	/**
     * 
     */
	public void cleanCurrentCart(final HttpServletRequest request) throws Exception {
		Cart cart = new Cart();
		updateCurrentCart(request, cart);
		
		// TODO update session/cart db ?
	}
	
	/**
     * 
     */
	public Order getLastOrder(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		return engineSession.getLastOrder();
	}
	
	/**
     * 
     */
	public void saveLastOrder(final HttpServletRequest request, final Order order) throws Exception {
		if(order != null){
			EngineSession engineSession = getCurrentSession(request);
			engineSession.setLastOrder(order);
			updateCurrentSession(request, engineSession);
		}
	}
	
	/**
     * 
     */
	public void removeCartItemFromCurrentCart(final HttpServletRequest request, final String skuCode) throws Exception {
		Cart cart = getCurrentCart(request);
		Set<CartItem> cartItems = cart.getCartItems();
		for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
			CartItem cartItem = (CartItem) iterator.next();
			if(cartItem.getProductSkuCode().equalsIgnoreCase(skuCode)){
				cartItems.remove(cartItem);
			}
		}
		cart.setCartItems(cartItems);
		updateCurrentCart(request, cart);
		
		// TODO update session/cart db ?
		
	}
	
	/**
     * 
     */
	public MarketPlace getCurrentMarketPlace(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		MarketPlace marketPlace = engineSession.getCurrentMarketPlace();
		if(marketPlace == null){
			initDefaultMarketPlace(request);
		}
		return marketPlace;
	}
	
	/**
     * 
     */
	public Market getCurrentMarket(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		Market market = null;
		market = engineSession.getCurrentMarket();
		if(market == null){
			initDefaultMarketPlace(request);
		}
		return market;
	}
	
	/**
     * 
     */
	public MarketArea getCurrentMarketArea(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		MarketArea marketArea = null;
		marketArea = engineSession.getCurrentMarketArea();
		if(marketArea == null){
			initDefaultMarketPlace(request);
		}
		return marketArea;
	}
	
	/**
     * 
     */
	public Localization getCurrentLocalization(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		Localization localization = engineSession.getCurrentLocalization();
		return localization;
	}

	/**
     * 
     */
	public void updateCurrentLocalization(final HttpServletRequest request, final Localization localization) throws Exception {
		if (localization != null) {
			EngineSession engineSession = getCurrentSession(request);
			engineSession.setCurrentLocalization(localization);
			updateCurrentSession(request, engineSession);
		}
	}
	
	/**
     * 
     */
	public Retailer getCurrentRetailer(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		Retailer retailer = null;
		retailer = engineSession.getCurrentRetailer();
		if(retailer == null){
			initDefaultMarketPlace(request);
		}
		return retailer;
	}
	
//	/**
//     * 
//     */
//	public CatalogVirtual getCurrentProductCatalog(final HttpServletRequest request) throws Exception {
//		final MarketArea marketArea = getCurrentMarketArea(request);
//		CatalogVirtual productCatalog = null;
//		if(marketArea != null) {
//			productCatalog = marketArea.getVirtualCatalog();
//		}
//		return productCatalog;
//	}
	
	/**
     * 
     */
	public User getCurrentUser(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		return engineSession.getCurrentUser();
	}
	
	/**
     * 
     */
	public Long getCurrentUserId(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		User user = engineSession.getCurrentUser();
		if(user == null){
			return null;
		}
		return engineSession.getCurrentUser().getId();
	}
	
	/**
     * 
     */
	public void updateCurrentUser(final HttpServletRequest request, final User user) throws Exception {
		if(user != null){
			final EngineSession engineSession = getCurrentSession(request);
			engineSession.setCurrentUser(user);
			updateCurrentSession(request, engineSession);
		}
	}
	
	/**
     * 
     */
	public Company getCurrentCompany(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		User user = engineSession.getCurrentUser();
		if(user == null){
			return null;
		}
		return user.getCompany();
	}
	
	/**
     * 
     */
	public Customer getCurrentCustomer(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		return engineSession.getCustomer();
	}
	
	/**
     * 
     */
	public String getCurrentCustomerId(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		Customer customer = engineSession.getCustomer();
		if(customer == null){
			return null;
		}
		return customer.getId().toString();
	}
	
	/**
     * 
     */
	public String getCurrentCustomerLogin(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		Customer customer = engineSession.getCustomer();
		if(customer == null){
			return null;
		}
		return customer.getLogin();
	}
	
	/**
     * 
     */
	public void updateCurrentCustomer(final HttpServletRequest request, final Customer customer) throws Exception {
		if(customer != null){
			final EngineSession engineSession = getCurrentSession(request);
			engineSession.setCustomer(customer);
			updateCurrentSession(request, engineSession);
		}
	}
	
	/**
	  * 
	  */
    public void handleUrlParameters(final HttpServletRequest request) throws Exception {
    	String marketPlaceCode = request.getParameter(Constants.REQUEST_PARAMETER_MARKET_PLACE_CODE);
    	String marketCode = request.getParameter(Constants.REQUEST_PARAMETER_MARKET_CODE);
    	String marketAreaCode = request.getParameter(Constants.REQUEST_PARAMETER_MARKET_AREA_CODE);
    	String localeCode = request.getParameter(Constants.REQUEST_PARAMETER_LOCALE_CODE);
    	String retailerCode = request.getParameter(Constants.REQUEST_PARAMETER_LOCALE_CODE);
    	EngineSession engineSession = getCurrentSession(request);
 
    	MarketPlace currentMarketPlace = engineSession.getCurrentMarketPlace();
    	if(StringUtils.isNotEmpty(marketPlaceCode)
    			&& StringUtils.isNotEmpty(marketCode)
    			&& StringUtils.isNotEmpty(marketAreaCode)
    			&& StringUtils.isNotEmpty(localeCode)){
        	if(currentMarketPlace != null 
    				&& !currentMarketPlace.getCode().equalsIgnoreCase(marketPlaceCode)){
        		// RESET ALL SESSION AND CHANGE THE MARKET PLACE
        		resetCart(request);
        		initSession(request);
        		MarketPlace newMarketPlace = marketPlaceService.getMarketPlaceByCode(marketPlaceCode);
        		if(newMarketPlace == null){
        			// INIT A DEFAULT MARKET PLACE
        			initDefaultMarketPlace(request);
        		} else {
        			// MARKET PLACE
            		engineSession.setCurrentMarketPlace(newMarketPlace);
            		updateCurrentTheme(request, newMarketPlace.getTheme());

            		// MARKET
            		Market market = newMarketPlace.getMarket(marketCode);
            		if(market == null){
            			market = newMarketPlace.getDefaultMarket();
            		}
            		engineSession.setCurrentMarket(market);

            		// MARKET MODE
            		MarketArea marketArea = market.getMarketArea(marketAreaCode);
            		if(marketArea == null){
            			marketArea = market.getDefaultMarketArea();
            		}
            		engineSession.setCurrentMarketArea(marketArea);
            		
            		// LOCALE
            		Localization localization = marketArea.getLocalization(localeCode);
            		if(localization == null){
            			Localization defaultLocalization = marketArea.getDefaultLocalization();
                		engineSession.setCurrentLocalization(defaultLocalization);
            		} else {
                		engineSession.setCurrentLocalization(localization);
            		}
            		
            		// RETAILER
            		Retailer retailer = marketArea.getRetailer(retailerCode);
            		if(retailer == null){
            			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                		engineSession.setCurrentRetailer(defaultRetailer);
            		} else {
                		engineSession.setCurrentRetailer(retailer);
            		}  
        		}

        	} else {
            	Market market = engineSession.getCurrentMarket();
            	if(market != null 
        				&& !market.getCode().equalsIgnoreCase(marketCode)){
            		// RESET CART
            		resetCart(request);
            		
            		// CHANGE THE MARKET
            		Market newMarket = marketService.getMarketByCode(marketCode);
            		if(newMarket == null){
            			newMarket = currentMarketPlace.getDefaultMarket();
            		}
            		engineSession.setCurrentMarket(newMarket);
            		updateCurrentTheme(request, newMarket.getTheme());

            		// MARKET MODE
            		MarketArea marketArea = newMarket.getMarketArea(marketAreaCode);
            		if(marketArea == null){
            			marketArea = market.getDefaultMarketArea();
            		}
            		engineSession.setCurrentMarketArea(marketArea);
            		
            		// LOCALE
            		Localization localization = marketArea.getLocalization(localeCode);
            		if(localization == null){
            			Localization defaultLocalization = marketArea.getDefaultLocalization();
                		engineSession.setCurrentLocalization(defaultLocalization);
            		} else {
                		engineSession.setCurrentLocalization(localization);
            		}
            		
            		// RETAILER
            		Retailer retailer = marketArea.getRetailer(retailerCode);
            		if(retailer == null){
            			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                		engineSession.setCurrentRetailer(defaultRetailer);
            		} else {
                		engineSession.setCurrentRetailer(retailer);
            		}
            	} else {
                	MarketArea marketArea = engineSession.getCurrentMarketArea();
                	if(marketArea != null 
            				&& !marketArea.getCode().equalsIgnoreCase(marketAreaCode)){
                		// RESET CART
                		resetCart(request);

                		// CHANGE THE MARKET MODE
                		MarketArea newMarketArea = market.getMarketArea(marketAreaCode);
                		if(newMarketArea == null){
                			newMarketArea = market.getDefaultMarketArea();
                		}
                		engineSession.setCurrentMarketArea(newMarketArea);
                		updateCurrentTheme(request, newMarketArea.getTheme());
                		
                		// LOCALE
                		Localization localization = newMarketArea.getLocalization(localeCode);
                		if(localization == null){
                			Localization defaultLocalization = marketArea.getDefaultLocalization();
                    		engineSession.setCurrentLocalization(defaultLocalization);
                		} else {
                    		engineSession.setCurrentLocalization(localization);
                		}
                		
                		// RETAILER
                		Retailer retailer = marketArea.getRetailer(retailerCode);
                		if(retailer == null){
                			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                    		engineSession.setCurrentRetailer(defaultRetailer);
                		} else {
                    		engineSession.setCurrentRetailer(retailer);
                		}
                	} else {
                		Localization localization = engineSession.getCurrentLocalization();
                    	if(localization != null 
                				&& !localization.getLocale().toString().equalsIgnoreCase(localeCode)){
                    		// CHANGE THE LOCALE
                    		Localization newLocalization = marketArea.getLocalization(localeCode);
                    		if(newLocalization == null){
                    			Localization defaultLocalization = marketArea.getDefaultLocalization();
                        		engineSession.setCurrentLocalization(defaultLocalization);
                    		} else {
                        		engineSession.setCurrentLocalization(newLocalization);
                    		}
                    		
                    		// RETAILER
                    		Retailer retailer = marketArea.getRetailer(retailerCode);
                    		if(retailer == null){
                    			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                        		engineSession.setCurrentRetailer(defaultRetailer);
                    		} else {
                        		engineSession.setCurrentRetailer(retailer);
                    		}  
                    		
                    	} else {
                    		Retailer retailer = engineSession.getCurrentRetailer();
                        	if(retailer != null 
                    				&& !retailer.getCode().toString().equalsIgnoreCase(retailerCode)){
                        		// CHANGE THE RETAILER
                        		Retailer newRetailer = marketArea.getRetailer(retailerCode);
                        		if(newRetailer == null){
                        			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                            		engineSession.setCurrentRetailer(defaultRetailer);
                        		} else {
                            		engineSession.setCurrentRetailer(newRetailer);
                        		}
                        	}
                    	}
                	}
            	}
        	}
		}
    	
    	// THEME
		final MarketArea marketArea = getCurrentMarketArea(request);
		String themeFolder = "default";
		if(StringUtils.isNotEmpty(marketArea.getTheme())){
			themeFolder = marketArea.getTheme();
		}
		updateCurrentTheme(request, themeFolder);
		
		// SAVE THE ENGINE SESSION
		updateCurrentSession(request, engineSession);
    }
        
	/**
     * 
     */
	public String getCurrentTheme(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		String currenTheme = engineSession.getTheme();
		// SANITY CHECK
		if(StringUtils.isEmpty(currenTheme)){
			return "default";
		}
		return currenTheme;
	}
	
	/**
     * 
     */
	public void updateCurrentTheme(final HttpServletRequest request, final String theme) throws Exception {
		final EngineSession engineSession = getCurrentSession(request);
		if(StringUtils.isNotEmpty(theme)){
			engineSession.setTheme(theme);
			updateCurrentSession(request, engineSession);
		}
	}
	
	/**
     * 
     */
	public String getCurrentThemeResourcePrefixPath(final HttpServletRequest request, final String context) throws Exception {
		EngineSetting engineSetting = engineSettingService.getThemeResourcePrefixPath();
		EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
		String prefixPath  = engineSetting.getDefaultValue();
		if(engineSettingValue != null){
			prefixPath  = engineSettingValue.getValue();
		} else {
			LOG.warn("This engine setting is request, but doesn't exist: " + engineSetting.getCode() + "/" + context);
		}
		String currenTheme = prefixPath + getCurrentTheme(request);
		return currenTheme;
	}
	
	/**
     * 
     */
	public String getCurrentCatalogImageResourcePrefixPath(final HttpServletRequest request, final String context) throws Exception {
		EngineSetting engineSetting = engineSettingService.getCatalogImageResourcePrefixPath();
		String prefixPath  = "";
		if(engineSetting != null){
			EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
			prefixPath  = engineSetting.getDefaultValue();
			if(engineSettingValue != null){
				prefixPath = engineSettingValue.getValue();
			} else {
				prefixPath = engineSetting.getDefaultValue();
				LOG.warn("EngineSetting default value selected. Specific Engine setting is request, but doesn't exist: " + engineSetting.getCode() + "/" + context);
			}
		}
		String currenTheme = prefixPath + getCurrentTheme(request);
		return currenTheme;
	}
	
	/**
     * 
     */
	public String getCurrentDevice(final HttpServletRequest request) throws Exception {
		EngineSession engineSession = getCurrentSession(request);
		String currenDevice = engineSession.getDevice();
		// SANITY CHECK
		if(StringUtils.isEmpty(currenDevice)){
			return "default";
		}
		return currenDevice;
	}
	
	/**
     * 
     */
	public void updateCurrentDevice(final HttpServletRequest request, final String device) throws Exception {
		final EngineSession engineSession = getCurrentSession(request);
		if(StringUtils.isNotEmpty(device)){
			engineSession.setDevice(device);
			updateCurrentSession(request, engineSession);
		}
	}
	
	/**
     * 
     */
	public String getCurrentVelocityPrefix(final HttpServletRequest request) throws Exception {
		String velocityPath = "/" + getCurrentTheme(request) + "/www/" + getCurrentDevice(request) + "/";
		return velocityPath;
	}
	
	/**
	 * 
	 */
	protected EngineSession initSession(final HttpServletRequest request) throws Exception {
		final EngineSession engineSession = new EngineSession();
		setCurrentSession(request, engineSession);
		String jSessionId = request.getSession().getId();
		engineSession.setjSessionId(jSessionId);
		initCart(request);
		initDefaultMarketPlace(request);
		updateCurrentSession(request, engineSession);
		return engineSession;
	}
	
	/**
     * 
     */
	protected void initCart(final HttpServletRequest request) throws Exception {
		final EngineSession engineSession = getCurrentSession(request);
		Cart cart = engineSession.getCart();
		if(cart == null){
			// Init a new empty Cart with a default configuration
			cart = new Cart();
		}
		engineSession.setCart(cart);
		updateCurrentSession(request, engineSession);
	}
	
	/**
	 * @throws Exception 
     * 
     */
	protected void resetCart(final HttpServletRequest request) throws Exception {
		// TODO : save the current cart
		
		// Reset Cart
		updateCurrentCart(request, new Cart());
	}
	
	/**
     * 
     */
	protected void initDefaultMarketPlace(final HttpServletRequest request) throws Exception {
		final EngineSession engineSession = getCurrentSession(request);
		MarketPlace marketPlace = marketPlaceService.getDefaultMarketPlace();
    	engineSession.setCurrentMarketPlace(marketPlace);
		Market market = marketPlace.getDefaultMarket();
		engineSession.setCurrentMarket(market);
		MarketArea marketArea = market.getDefaultMarketArea();
		engineSession.setCurrentMarketArea(marketArea);
		Localization localization = marketArea.getDefaultLocalization();
		engineSession.setCurrentLocalization(localization);
		Retailer retailer = marketArea.getDefaultRetailer();
		engineSession.setCurrentRetailer(retailer);
		updateCurrentSession(request, engineSession);
	}
	
	/**
     * 
     */
	protected EngineSession checkEngineSession(final HttpServletRequest request, EngineSession engineSession) throws Exception {
		if(engineSession == null) {
			engineSession = initSession(request);
		}
		String jSessionId = request.getSession().getId();
		if(!engineSession.getjSessionId().equals(jSessionId)){
			engineSession.setjSessionId(jSessionId);
			updateCurrentSession(request, engineSession);
		}
		return engineSession;
	}
	
	/**
     * 
     */
	@SuppressWarnings("unchecked")
	protected List<ClickstreamRequest> getClickStreamRequests(final HttpServletRequest request) {
		final List<ClickstreamRequest> clickstreams = getClickStream(request).getStream();
		return clickstreams;
	}
	
	/**
     * 
     */
	protected Clickstream getClickStream(final HttpServletRequest request) {
		final Clickstream stream = (Clickstream) request.getSession().getAttribute(ClickstreamListener.SESSION_ATTRIBUTE_KEY);
		return stream;
	}
		
	/**
     * 
     */
	protected String handleUrl(String url) {
		return url;
	}
	
}
