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

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Cart;
import fr.hoteia.qalingo.core.domain.CartItem;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.EngineEcoSession;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.service.MarketPlaceService;
import fr.hoteia.qalingo.core.service.MarketService;
import fr.hoteia.qalingo.core.service.ProductSkuService;
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
public class RequestUtilImpl extends AbstractRequestUtilImpl implements RequestUtil {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected MarketPlaceService marketPlaceService;

	@Autowired
	protected MarketService marketService;

	@Autowired
	protected ProductSkuService productSkuService;

	@Autowired
	protected CustomerService customerService;

	/**
     * 
     */
	public EngineEcoSession getCurrentEcoSession(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = (EngineEcoSession) request.getSession().getAttribute(Constants.ENGINE_ECO_SESSION_OBJECT);
		engineEcoSession = checkEngineEcoSession(request, engineEcoSession);
		return engineEcoSession;
	}

	/**
     * 
     */
	public void updateCurrentEcoSession(final HttpServletRequest request, final EngineEcoSession engineEcoSession) throws Exception {
		setCurrentEcoSession(request, engineEcoSession);
	}

	/**
     * 
     */
	public void setCurrentEcoSession(final HttpServletRequest request, final EngineEcoSession engineEcoSession) throws Exception {
		request.getSession().setAttribute(Constants.ENGINE_ECO_SESSION_OBJECT, engineEcoSession);
	}

	/**
     * 
     */
	public Cart getCurrentCart(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		return engineEcoSession.getCart();
	}

	/**
     * 
     */
	public void updateCurrentCart(final HttpServletRequest request, final Cart cart) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		engineEcoSession.setCart(cart);
		updateCurrentEcoSession(request, engineEcoSession);
	}

	/**
     * 
     */
	public void updateCurrentCart(final HttpServletRequest request, final String skuCode, final int quantity) throws Exception {
		// SANITY CHECK : sku code is empty or null : no sense
		if (StringUtils.isEmpty(skuCode)) {
			throw new Exception("");
		}

		// SANITY CHECK : quantity is equal zero : no sense
		if (StringUtils.isEmpty(skuCode)) {
			throw new Exception("");
		}

		Cart cart = getCurrentCart(request);
		Set<CartItem> cartItems = cart.getCartItems();
		boolean productSkuIsNew = true;
		for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
			CartItem cartItem = (CartItem) iterator.next();
			if (cartItem.getProductSkuCode().equalsIgnoreCase(skuCode)) {
				int newQuantity = cartItem.getQuantity() + quantity;
				cartItem.setQuantity(newQuantity);
				productSkuIsNew = false;
			}
		}
		if (productSkuIsNew) {
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
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		return engineEcoSession.getLastOrder();
	}

	/**
     * 
     */
	public void saveLastOrder(final HttpServletRequest request, final Order order) throws Exception {
		if (order != null) {
			EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
			engineEcoSession.setLastOrder(order);
			updateCurrentEcoSession(request, engineEcoSession);
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
			if (cartItem.getProductSkuCode().equalsIgnoreCase(skuCode)) {
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
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		MarketPlace marketPlace = engineEcoSession.getCurrentMarketPlace();
		if (marketPlace == null) {
			initDefaultEcoMarketPlace(request);
		}
		return marketPlace;
	}

	/**
     * 
     */
	public Market getCurrentMarket(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		Market market = null;
		market = engineEcoSession.getCurrentMarket();
		if (market == null) {
			initDefaultEcoMarketPlace(request);
		}
		return market;
	}

	/**
     * 
     */
	public MarketArea getCurrentMarketArea(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		MarketArea marketArea = null;
		marketArea = engineEcoSession.getCurrentMarketArea();
		if (marketArea == null) {
			initDefaultEcoMarketPlace(request);
		}
		return marketArea;
	}

	/**
     * 
     */
	public Localization getCurrentLocalization(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		Localization localization = engineEcoSession.getCurrentLocalization();
		return localization;
	}

	/**
     * 
     */
	public Locale getCurrentLocale(final HttpServletRequest request) throws Exception {
		Localization localization = getCurrentLocalization(request);
		if (localization != null) {
			return localization.getLocale();
		} else {
			LOG.warn("Current Locale is null and it is not possible. Need to reset session.");
			initDefaultEcoMarketPlace(request);
			localization = getCurrentLocalization(request);
			return localization.getLocale();
		}
	}

	/**
     * 
     */
	public void updateCurrentLocalization(final HttpServletRequest request, final Localization localization) throws Exception {
		if (localization != null) {
			EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
			engineEcoSession.setCurrentLocalization(localization);
			updateCurrentEcoSession(request, engineEcoSession);
		}
	}

	/**
     * 
     */
	public Retailer getCurrentRetailer(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		Retailer retailer = null;
		retailer = engineEcoSession.getCurrentRetailer();
		if (retailer == null) {
			initDefaultEcoMarketPlace(request);
		}
		return retailer;
	}

	/**
     * 
     */
	public Customer getCurrentCustomer(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		Customer customer = engineEcoSession.getCurrentCustomer();
		if (customer == null) {
			// CHECK
			if(SecurityContextHolder.getContext().getAuthentication() != null){
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				if (StringUtils.isNotEmpty(username) && !username.equalsIgnoreCase("anonymousUser")) {
					customer = customerService.getCustomerByLoginOrEmail(username);
					engineEcoSession.setCurrentCustomer(customer);
				}
			}
		}
		return customer;
	}

	/**
     * 
     */
	public String getCustomerAvatar(final HttpServletRequest request, final Customer customer) throws Exception {
		String customerAvatar = null;
		if (customer != null){
			if ( StringUtils.isNotEmpty(customer.getAvatarImg())) {
				customerAvatar = customer.getAvatarImg();
			} else {
				String email = customer.getEmail().toLowerCase().trim();
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] array = md.digest(email.getBytes("CP1252"));
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < array.length; ++i) {
					sb.append(Integer.toHexString((array[i]
								& 0xFF) | 0x100).substring(1,3));        
				}
				String gravatarId = sb.toString();
				if("https".equals(request.getScheme())){
					customerAvatar = "https://secure.gravatar.com/avatar/" + gravatarId;
				} else {
					customerAvatar = "http://www.gravatar.com/avatar/" + gravatarId;
				}
			}
		}
		return customerAvatar;
	}
	
	/**
     * 
     */
	public boolean hasKnownCustomerLogged(final HttpServletRequest request) throws Exception {
		final Customer customer = getCurrentCustomer(request);
		if (customer != null) {
			return true;
		}
		return false;
	}

	/**
     * 
     */
	public Long getCurrentCustomerId(final HttpServletRequest request) throws Exception {
		Customer customer = getCurrentCustomer(request);
		if (customer == null) {
			return null;
		}
		return customer.getId();
	}

	/**
     * 
     */
	public String getCurrentCustomerLogin(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		Customer customer = engineEcoSession.getCurrentCustomer();
		if (customer == null) {
			return null;
		}
		return customer.getLogin();
	}

	/**
     * 
     */
	public void updateCurrentCustomer(final HttpServletRequest request, final Customer customer) throws Exception {
		if (customer != null) {
			final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
			engineEcoSession.setCurrentCustomer(customer);
			updateCurrentEcoSession(request, engineEcoSession);
		}
	}

	/**
     * 
     */
	public void cleanCurrentCustomer(final HttpServletRequest request) throws Exception {
		final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		engineEcoSession.setCurrentCustomer(null);
		updateCurrentEcoSession(request, engineEcoSession);
	}

	/**
	  * 
	  */
	public void handleFrontofficeUrlParameters(final HttpServletRequest request) throws Exception {
		String marketPlaceCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE);
		String marketCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_CODE);
		String marketAreaCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE);
		String localeCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_LOCALE_CODE);
		String retailerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_RETAILER_CODE);
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);

		MarketPlace currentMarketPlace = engineEcoSession.getCurrentMarketPlace();
		if (StringUtils.isNotEmpty(marketPlaceCode) && StringUtils.isNotEmpty(marketCode) && StringUtils.isNotEmpty(marketAreaCode) && StringUtils.isNotEmpty(localeCode)) {
			if (currentMarketPlace != null && !currentMarketPlace.getCode().equalsIgnoreCase(marketPlaceCode)) {
				// RESET ALL SESSION AND CHANGE THE MARKET PLACE
				resetCart(request);
				initEcoSession(request);
				MarketPlace newMarketPlace = marketPlaceService.getMarketPlaceByCode(marketPlaceCode);
				if (newMarketPlace == null) {
					// INIT A DEFAULT MARKET PLACE
					initDefaultEcoMarketPlace(request);
				} else {
					// MARKET PLACE
					engineEcoSession.setCurrentMarketPlace(newMarketPlace);
					updateCurrentTheme(request, newMarketPlace.getTheme());

					// MARKET
					Market market = newMarketPlace.getMarket(marketCode);
					if (market == null) {
						market = newMarketPlace.getDefaultMarket();
					}
					engineEcoSession.setCurrentMarket(market);

					// MARKET MODE
					MarketArea marketArea = market.getMarketArea(marketAreaCode);
					if (marketArea == null) {
						marketArea = market.getDefaultMarketArea();
					}
					engineEcoSession.setCurrentMarketArea(marketArea);

					// LOCALE
					Localization localization = marketArea.getLocalization(localeCode);
					if (localization == null) {
						Localization defaultLocalization = marketArea.getDefaultLocalization();
						engineEcoSession.setCurrentLocalization(defaultLocalization);
					} else {
						engineEcoSession.setCurrentLocalization(localization);
					}

					// RETAILER
					Retailer retailer = marketArea.getRetailer(retailerCode);
					if (retailer == null) {
						Retailer defaultRetailer = marketArea.getDefaultRetailer();
						engineEcoSession.setCurrentRetailer(defaultRetailer);
					} else {
						engineEcoSession.setCurrentRetailer(retailer);
					}
				}

			} else {
				Market market = engineEcoSession.getCurrentMarket();
				if (market != null && !market.getCode().equalsIgnoreCase(marketCode)) {
					// RESET CART
					resetCart(request);

					// CHANGE THE MARKET
					Market newMarket = marketService.getMarketByCode(marketCode);
					if (newMarket == null) {
						newMarket = currentMarketPlace.getDefaultMarket();
					}
					engineEcoSession.setCurrentMarket(newMarket);
					updateCurrentTheme(request, newMarket.getTheme());

					// MARKET MODE
					MarketArea marketArea = newMarket.getMarketArea(marketAreaCode);
					if (marketArea == null) {
						marketArea = market.getDefaultMarketArea();
					}
					engineEcoSession.setCurrentMarketArea(marketArea);

					// LOCALE
					Localization localization = marketArea.getLocalization(localeCode);
					if (localization == null) {
						Localization defaultLocalization = marketArea.getDefaultLocalization();
						engineEcoSession.setCurrentLocalization(defaultLocalization);
					} else {
						engineEcoSession.setCurrentLocalization(localization);
					}

					// RETAILER
					Retailer retailer = marketArea.getRetailer(retailerCode);
					if (retailer == null) {
						Retailer defaultRetailer = marketArea.getDefaultRetailer();
						engineEcoSession.setCurrentRetailer(defaultRetailer);
					} else {
						engineEcoSession.setCurrentRetailer(retailer);
					}
				} else {
					MarketArea marketArea = engineEcoSession.getCurrentMarketArea();
					if (marketArea != null && !marketArea.getCode().equalsIgnoreCase(marketAreaCode)) {
						// RESET CART
						resetCart(request);

						// CHANGE THE MARKET MODE
						MarketArea newMarketArea = market.getMarketArea(marketAreaCode);
						if (newMarketArea == null) {
							newMarketArea = market.getDefaultMarketArea();
						}
						engineEcoSession.setCurrentMarketArea(newMarketArea);
						updateCurrentTheme(request, newMarketArea.getTheme());

						// LOCALE
						Localization localization = newMarketArea.getLocalization(localeCode);
						if (localization == null) {
							Localization defaultLocalization = marketArea.getDefaultLocalization();
							engineEcoSession.setCurrentLocalization(defaultLocalization);
						} else {
							engineEcoSession.setCurrentLocalization(localization);
						}

						// RETAILER
						Retailer retailer = marketArea.getRetailer(retailerCode);
						if (retailer == null) {
							Retailer defaultRetailer = marketArea.getDefaultRetailer();
							engineEcoSession.setCurrentRetailer(defaultRetailer);
						} else {
							engineEcoSession.setCurrentRetailer(retailer);
						}
					} else {
						Localization localization = engineEcoSession.getCurrentLocalization();
						if (localization != null && !localization.getLocale().toString().equalsIgnoreCase(localeCode)) {
							// CHANGE THE LOCALE
							Localization newLocalization = marketArea.getLocalization(localeCode);
							if (newLocalization == null) {
								Localization defaultLocalization = marketArea.getDefaultLocalization();
								engineEcoSession.setCurrentLocalization(defaultLocalization);
							} else {
								engineEcoSession.setCurrentLocalization(newLocalization);
							}

							// RETAILER
							Retailer retailer = marketArea.getRetailer(retailerCode);
							if (retailer == null) {
								Retailer defaultRetailer = marketArea.getDefaultRetailer();
								engineEcoSession.setCurrentRetailer(defaultRetailer);
							} else {
								engineEcoSession.setCurrentRetailer(retailer);
							}

						} else {
							Retailer retailer = engineEcoSession.getCurrentRetailer();
							if (retailer != null && !retailer.getCode().toString().equalsIgnoreCase(retailerCode)) {
								// CHANGE THE RETAILER
								Retailer newRetailer = marketArea.getRetailer(retailerCode);
								if (newRetailer == null) {
									Retailer defaultRetailer = marketArea.getDefaultRetailer();
									engineEcoSession.setCurrentRetailer(defaultRetailer);
								} else {
									engineEcoSession.setCurrentRetailer(newRetailer);
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
		if (StringUtils.isNotEmpty(marketArea.getTheme())) {
			themeFolder = marketArea.getTheme();
		}
		updateCurrentTheme(request, themeFolder);

		// SAVE THE ENGINE SESSION
		updateCurrentEcoSession(request, engineEcoSession);
	}

	/**
     * 
     */
	@Override
	public String getCurrentTheme(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		String currenTheme = engineEcoSession.getTheme();
		// SANITY CHECK
		if (StringUtils.isEmpty(currenTheme)) {
			return "default";
		}
		return currenTheme;
	}

	/**
     * 
     */
	@Override
	public void updateCurrentTheme(final HttpServletRequest request, final String theme) throws Exception {
		final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		if (StringUtils.isNotEmpty(theme)) {
			engineEcoSession.setTheme(theme);
			updateCurrentEcoSession(request, engineEcoSession);
		}
	}

	/**
     * 
     */
	@Override
	public String getCurrentDevice(final HttpServletRequest request) throws Exception {
		EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		String currenDevice = engineEcoSession.getDevice();
		// SANITY CHECK
		if (StringUtils.isEmpty(currenDevice)) {
			return "default";
		}
		return currenDevice;
	}

	/**
     * 
     */
	@Override
	public void updateCurrentDevice(final HttpServletRequest request, final String device) throws Exception {
		final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		if (StringUtils.isNotEmpty(device)) {
			engineEcoSession.setDevice(device);
			updateCurrentEcoSession(request, engineEcoSession);
		}
	}

	/**
	 * 
	 */
	protected EngineEcoSession initEcoSession(final HttpServletRequest request) throws Exception {
		final EngineEcoSession engineEcoSession = new EngineEcoSession();
		setCurrentEcoSession(request, engineEcoSession);
		String jSessionId = request.getSession().getId();
		engineEcoSession.setjSessionId(jSessionId);
		initCart(request);
		initDefaultEcoMarketPlace(request);
		updateCurrentEcoSession(request, engineEcoSession);
		return engineEcoSession;
	}

	/**
     * 
     */
	protected EngineEcoSession checkEngineEcoSession(final HttpServletRequest request, EngineEcoSession engineEcoSession) throws Exception {
		if (engineEcoSession == null) {
			engineEcoSession = initEcoSession(request);
		}
		String jSessionId = request.getSession().getId();
		if (!engineEcoSession.getjSessionId().equals(jSessionId)) {
			engineEcoSession.setjSessionId(jSessionId);
			updateCurrentEcoSession(request, engineEcoSession);
		}
		return engineEcoSession;
	}

	/**
     * 
     */
	protected void initCart(final HttpServletRequest request) throws Exception {
		final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		Cart cart = engineEcoSession.getCart();
		if (cart == null) {
			// Init a new empty Cart with a default configuration
			cart = new Cart();
		}
		engineEcoSession.setCart(cart);
		updateCurrentEcoSession(request, engineEcoSession);
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
	protected void initDefaultEcoMarketPlace(final HttpServletRequest request) throws Exception {
		final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
		MarketPlace marketPlace = marketPlaceService.getDefaultMarketPlace();
		engineEcoSession.setCurrentMarketPlace(marketPlace);
		
		Market market = marketPlace.getDefaultMarket();
		engineEcoSession.setCurrentMarket(market);
		
		MarketArea marketArea = market.getDefaultMarketArea();
		engineEcoSession.setCurrentMarketArea(marketArea);
		
		// DEFAULT LOCALE IS FROM THE REQUEST OR FROM THE MARKET AREA
		String requestLocale = request.getLocale().toString();
		Localization localization  = marketArea.getDefaultLocalization();
		if(marketArea.getLocalization(requestLocale) != null){
			localization = marketArea.getLocalization(requestLocale);
		} else {
			if(requestLocale.length() > 2){
				String localeLanguage = request.getLocale().getLanguage();
				if(marketArea.getLocalization(localeLanguage) != null){
					localization = marketArea.getLocalization(localeLanguage);
				}
			}
		}
		engineEcoSession.setCurrentLocalization(localization);
		
		Retailer retailer = marketArea.getDefaultRetailer();
		engineEcoSession.setCurrentRetailer(retailer);
		
		updateCurrentEcoSession(request, engineEcoSession);
	}

}
