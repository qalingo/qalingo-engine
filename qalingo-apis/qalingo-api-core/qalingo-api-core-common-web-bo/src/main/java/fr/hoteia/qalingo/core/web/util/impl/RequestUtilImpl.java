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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Company;
import fr.hoteia.qalingo.core.domain.EngineBoSession;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.LocalizationService;
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
	protected EngineSettingService engineSettingService;
	
	@Autowired
	protected LocalizationService localizationService;
	
	/**
     * 
     */
	public EngineBoSession getCurrentBoSession(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = (EngineBoSession) request.getSession().getAttribute(Constants.ENGINE_BO_SESSION_OBJECT);
		engineBoSession = checkEngineBoSession(request, engineBoSession);
		return engineBoSession;
	}

	/**
     * 
     */
	public void updateCurrentBoSession(final HttpServletRequest request, final EngineBoSession engineBoSession) throws Exception {
		setCurrentBoSession(request, engineBoSession);
	}
	
	/**
     * 
     */
	public void setCurrentBoSession(final HttpServletRequest request, final EngineBoSession engineBoSession) throws Exception {
		request.getSession().setAttribute(Constants.ENGINE_BO_SESSION_OBJECT, engineBoSession);
	}
	
	
	/**
     * 
     */
	public Localization getCurrentLocalization(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = getCurrentBoSession(request);
		Localization localization = engineBoSession.getCurrentLocalization();
		return localization;
	}

	/**
     * 
     */
	public Locale getCurrentLocale(final HttpServletRequest request) throws Exception {
		Localization localization = getCurrentLocalization(request);
		if(localization != null){
			return localization.getLocale();
		} else {
			LOG.warn("Current Locale is null and it is not possible. Need to reset session.");
			initBoSession(request);
			localization = getCurrentLocalization(request);
			return localization.getLocale();
		}
	}
	
	/**
     * 
     */
	public void updateCurrentLocalization(final HttpServletRequest request, final Localization localization) throws Exception {
		if (localization != null) {
			EngineBoSession engineBoSession = getCurrentBoSession(request);
			engineBoSession.setCurrentLocalization(localization);
			updateCurrentBoSession(request, engineBoSession);
		}
	}
	
	/**
     * 
     */
	public User getCurrentUser(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = getCurrentBoSession(request);
		return engineBoSession.getCurrentUser();
	}
	
	/**
     * 
     */
	public Long getCurrentUserId(final HttpServletRequest request) throws Exception {
		User user = getCurrentUser(request);
		if(user == null){
			return null;
		}
		return user.getId();
	}
	
	/**
     * 
     */
	public void updateCurrentUser(final HttpServletRequest request, final User user) throws Exception {
		if(user != null){
			final EngineBoSession engineBoSSession = getCurrentBoSession(request);
			engineBoSSession.setCurrentUser(user);
			updateCurrentBoSession(request, engineBoSSession);
		}
	}
	
	/**
     * 
     */
	public MarketPlace getCurrentMarketPlace(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = getCurrentBoSession(request);
		MarketPlace marketPlace = engineBoSession.getCurrentMarketPlace();
		if(marketPlace == null){
			initDefaultBoMarketPlace(request);
		}
		return marketPlace;
	}
	
	/**
     * 
     */
	public Market getCurrentMarket(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = getCurrentBoSession(request);
		Market market = null;
		market = engineBoSession.getCurrentMarket();
		if(market == null){
			initDefaultBoMarketPlace(request);
		}
		return market;
	}
	
	/**
     * 
     */
	public MarketArea getCurrentMarketArea(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = getCurrentBoSession(request);
		MarketArea marketArea = null;
		marketArea = engineBoSession.getCurrentMarketArea();
		if(marketArea == null){
			initDefaultBoMarketPlace(request);
		}
		return marketArea;
	}
	
	/**
     * 
     */
	public Localization getCurrentMarketLocalization(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = getCurrentBoSession(request);
		Localization localization = engineBoSession.getCurrentMarketLocalization();
		return localization;
	}

	/**
     * 
     */
	public void updateCurrentMarketLocalization(final HttpServletRequest request, final Localization localization) throws Exception {
		if (localization != null) {
			EngineBoSession engineBoSession = getCurrentBoSession(request);
			engineBoSession.setCurrentLocalization(localization);
			updateCurrentBoSession(request, engineBoSession);
		}
	}
	
	/**
     * 
     */
	public Retailer getCurrentRetailer(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = getCurrentBoSession(request);
		Retailer retailer = null;
		retailer = engineBoSession.getCurrentRetailer();
		if(retailer == null){
			initDefaultBoMarketPlace(request);
		}
		return retailer;
	}
	
	/**
     * 
     */
	public Company getCurrentCompany(final HttpServletRequest request) throws Exception {
		EngineBoSession engineBoSession = getCurrentBoSession(request);
		User user = engineBoSession.getCurrentUser();
		if(user == null){
			return null;
		}
		return user.getCompany();
	}
	
	
	/**
	  * 
	  */
    public void handleBackofficeUrlParameters(final HttpServletRequest request) throws Exception {
    	String marketPlaceCode = request.getParameter(Constants.REQUEST_PARAMETER_MARKET_PLACE_CODE);
    	String marketCode = request.getParameter(Constants.REQUEST_PARAMETER_MARKET_CODE);
    	String marketAreaCode = request.getParameter(Constants.REQUEST_PARAMETER_MARKET_AREA_CODE);
    	String marketLanguageCode = request.getParameter(Constants.REQUEST_PARAMETER_MARKET_LANGUAGE);
    	String retailerCode = request.getParameter(Constants.REQUEST_PARAMETER_RETAILER_CODE);

    	EngineBoSession engineBoSession = getCurrentBoSession(request);
 
    	MarketPlace currentMarketPlace = engineBoSession.getCurrentMarketPlace();
    	if(StringUtils.isNotEmpty(marketPlaceCode)
    			&& StringUtils.isNotEmpty(marketCode)
    			&& StringUtils.isNotEmpty(marketAreaCode)
    			&& StringUtils.isNotEmpty(marketLanguageCode)){
        	if(currentMarketPlace != null 
    				&& !currentMarketPlace.getCode().equalsIgnoreCase(marketPlaceCode)){
        		// RESET ALL SESSION AND CHANGE THE MARKET PLACE
        		initBoSession(request);
        		MarketPlace newMarketPlace = marketPlaceService.getMarketPlaceByCode(marketPlaceCode);
        		if(newMarketPlace == null){
        			// INIT A DEFAULT MARKET PLACE
        			initDefaultBoMarketPlace(request);
        		} else {
        			// MARKET PLACE
            		engineBoSession.setCurrentMarketPlace(newMarketPlace);
            		updateCurrentTheme(request, newMarketPlace.getTheme());

            		// MARKET
            		Market market = newMarketPlace.getMarket(marketCode);
            		if(market == null){
            			market = newMarketPlace.getDefaultMarket();
            		}
            		engineBoSession.setCurrentMarket(market);

            		// MARKET MODE
            		MarketArea marketArea = market.getMarketArea(marketAreaCode);
            		if(marketArea == null){
            			marketArea = market.getDefaultMarketArea();
            		}
            		engineBoSession.setCurrentMarketArea(marketArea);
            		
            		// LOCALE
            		Localization localization = marketArea.getLocalization(marketLanguageCode);
            		if(localization == null){
            			Localization defaultLocalization = marketArea.getDefaultLocalization();
                		engineBoSession.setCurrentMarketLocalization(defaultLocalization);
            		} else {
                		engineBoSession.setCurrentMarketLocalization(localization);
            		}
            		
            		// RETAILER
            		Retailer retailer = marketArea.getRetailer(retailerCode);
            		if(retailer == null){
            			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                		engineBoSession.setCurrentRetailer(defaultRetailer);
            		} else {
                		engineBoSession.setCurrentRetailer(retailer);
            		}  
        		}

        	} else {
            	Market market = engineBoSession.getCurrentMarket();
            	if(market != null 
        				&& !market.getCode().equalsIgnoreCase(marketCode)){
            		
            		// CHANGE THE MARKET
            		Market newMarket = marketService.getMarketByCode(marketCode);
            		if(newMarket == null){
            			newMarket = currentMarketPlace.getDefaultMarket();
            		}
            		engineBoSession.setCurrentMarket(newMarket);
            		updateCurrentTheme(request, newMarket.getTheme());

            		// MARKET MODE
            		MarketArea marketArea = newMarket.getMarketArea(marketAreaCode);
            		if(marketArea == null){
            			marketArea = market.getDefaultMarketArea();
            		}
            		engineBoSession.setCurrentMarketArea(marketArea);
            		
            		// LOCALE
            		Localization localization = marketArea.getLocalization(marketLanguageCode);
            		if(localization == null){
            			Localization defaultLocalization = marketArea.getDefaultLocalization();
                		engineBoSession.setCurrentMarketLocalization(defaultLocalization);
            		} else {
                		engineBoSession.setCurrentMarketLocalization(localization);
            		}
            		
            		// RETAILER
            		Retailer retailer = marketArea.getRetailer(retailerCode);
            		if(retailer == null){
            			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                		engineBoSession.setCurrentRetailer(defaultRetailer);
            		} else {
                		engineBoSession.setCurrentRetailer(retailer);
            		}
            	} else {
                	MarketArea marketArea = engineBoSession.getCurrentMarketArea();
                	if(marketArea != null 
            				&& !marketArea.getCode().equalsIgnoreCase(marketAreaCode)){

                		// CHANGE THE MARKET MODE
                		MarketArea newMarketArea = market.getMarketArea(marketAreaCode);
                		if(newMarketArea == null){
                			newMarketArea = market.getDefaultMarketArea();
                		}
                		engineBoSession.setCurrentMarketArea(newMarketArea);
                		updateCurrentTheme(request, newMarketArea.getTheme());
                		
                		// LOCALE
                		Localization localization = newMarketArea.getLocalization(marketLanguageCode);
                		if(localization == null){
                			Localization defaultLocalization = marketArea.getDefaultLocalization();
                    		engineBoSession.setCurrentMarketLocalization(defaultLocalization);
                		} else {
                    		engineBoSession.setCurrentMarketLocalization(localization);
                		}
                		
                		// RETAILER
                		Retailer retailer = marketArea.getRetailer(retailerCode);
                		if(retailer == null){
                			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                    		engineBoSession.setCurrentRetailer(defaultRetailer);
                		} else {
                    		engineBoSession.setCurrentRetailer(retailer);
                		}
                	} else {
                		Localization localization = engineBoSession.getCurrentMarketLocalization();
                    	if(localization != null 
                				&& !localization.getLocale().toString().equalsIgnoreCase(marketLanguageCode)){
                    		// CHANGE THE LOCALE
                    		Localization newLocalization = marketArea.getLocalization(marketLanguageCode);
                    		if(newLocalization == null){
                    			Localization defaultLocalization = marketArea.getDefaultLocalization();
                        		engineBoSession.setCurrentMarketLocalization(defaultLocalization);
                    		} else {
                        		engineBoSession.setCurrentMarketLocalization(newLocalization);
                    		}
                    		
                    		// RETAILER
                    		Retailer retailer = marketArea.getRetailer(retailerCode);
                    		if(retailer == null){
                    			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                        		engineBoSession.setCurrentRetailer(defaultRetailer);
                    		} else {
                        		engineBoSession.setCurrentRetailer(retailer);
                    		}  
                    		
                    	} else {
                    		Retailer retailer = engineBoSession.getCurrentRetailer();
                        	if(retailer != null 
                    				&& !retailer.getCode().toString().equalsIgnoreCase(retailerCode)){
                        		// CHANGE THE RETAILER
                        		Retailer newRetailer = marketArea.getRetailer(retailerCode);
                        		if(newRetailer == null){
                        			Retailer defaultRetailer = marketArea.getDefaultRetailer();
                            		engineBoSession.setCurrentRetailer(defaultRetailer);
                        		} else {
                            		engineBoSession.setCurrentRetailer(newRetailer);
                        		}
                        	}
                    	}
                	}
            	}
        	}
		}
    	
    	// CHECK BACKOFFICE LANGUAGES
    	String localeCode = request.getParameter(Constants.REQUEST_PARAMETER_LOCALE_CODE);
		// LOCALIZATIONS
		Company company = getCurrentCompany(request);
		if(company != null){
			Localization localization = company.getLocalization(localeCode);
			if(localization != null){
				engineBoSession.setCurrentLocalization(localization);
			}
		}
		
		// SAVE THE ENGINE SESSION
		updateCurrentBoSession(request, engineBoSession);
    }
    
	/**
     * 
     */
	@Override
	public String getCurrentDevice(final HttpServletRequest request) throws Exception {
		final EngineBoSession engineBoSession = getCurrentBoSession(request);
		String currenDevice = engineBoSession.getDevice();
		// SANITY CHECK
		if(StringUtils.isEmpty(currenDevice)){
			return "default";
		}
		return currenDevice;
	}
	
	/**
     * 
     */
    @Override
	public void updateCurrentDevice(final HttpServletRequest request, final String device) throws Exception {
		final EngineBoSession engineBoSession = getCurrentBoSession(request);
		if(StringUtils.isNotEmpty(device)){
			engineBoSession.setDevice(device);
			updateCurrentBoSession(request, engineBoSession);
		}
	}
	
	/**
     * 
     */
	@Override
	public String getCurrentTheme(final HttpServletRequest request) throws Exception {
		final EngineBoSession engineBoSession = getCurrentBoSession(request);
		String currenTheme = engineBoSession.getTheme();
		// SANITY CHECK
		if(StringUtils.isEmpty(currenTheme)){
			return "default";
		}
		return currenTheme;
	}
	
	/**
     * 
     */
	@Override
	public void updateCurrentTheme(final HttpServletRequest request, final String theme) throws Exception {
		final EngineBoSession engineBoSession = getCurrentBoSession(request);
		if(StringUtils.isNotEmpty(theme)){
			engineBoSession.setTheme(theme);
			updateCurrentBoSession(request, engineBoSession);
		}
	}
	
	/**
	 * 
	 */
	protected EngineBoSession initBoSession(final HttpServletRequest request) throws Exception {
		final EngineBoSession engineBoSession = new EngineBoSession();
		setCurrentBoSession(request, engineBoSession);
		String jSessionId = request.getSession().getId();
		engineBoSession.setjSessionId(jSessionId);
		initDefaultBoMarketPlace(request);
		
		// Default Localization
		Company company = getCurrentCompany(request);
		if(company != null){
			// USER IS LOGGED
			engineBoSession.setCurrentLocalization(company.getDefaultLocalization());
		} else {
			Localization defaultLocalization = localizationService.getLocalizationByCode("en");
			engineBoSession.setCurrentLocalization(defaultLocalization);
		}
		
		updateCurrentBoSession(request, engineBoSession);
		return engineBoSession;
	}
	
	/**
     * 
     */
	protected EngineBoSession checkEngineBoSession(final HttpServletRequest request, EngineBoSession engineBoSession) throws Exception {
		if(engineBoSession == null) {
			engineBoSession = initBoSession(request);
		}
		String jSessionId = request.getSession().getId();
		if(!engineBoSession.getjSessionId().equals(jSessionId)){
			engineBoSession.setjSessionId(jSessionId);
			updateCurrentBoSession(request, engineBoSession);
		}
		return engineBoSession;
	}
	
	/**
     * 
     */
	protected void initDefaultBoMarketPlace(final HttpServletRequest request) throws Exception {
		final EngineBoSession engineBoSession = getCurrentBoSession(request);
		MarketPlace marketPlace = marketPlaceService.getDefaultMarketPlace();
		engineBoSession.setCurrentMarketPlace(marketPlace);
		Market market = marketPlace.getDefaultMarket();
		engineBoSession.setCurrentMarket(market);
		MarketArea marketArea = market.getDefaultMarketArea();
		engineBoSession.setCurrentMarketArea(marketArea);
		Localization localization = marketArea.getDefaultLocalization();
		engineBoSession.setCurrentMarketLocalization(localization);
		Retailer retailer = marketArea.getDefaultRetailer();
		engineBoSession.setCurrentRetailer(retailer);
		updateCurrentBoSession(request, engineBoSession);
	}

	
}
