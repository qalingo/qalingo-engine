/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *		  http://www.apache.org/licenses/LICENSE-2.0
 *
 *				     Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.util.impl;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.AbstractEngineSession;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import org.hoteia.qalingo.core.domain.enumtype.EnvironmentType;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CartService;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.EngineSessionService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.LocalizationService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.UserService;
import org.hoteia.qalingo.core.web.clickstream.ClickstreamRequest;
import org.hoteia.qalingo.core.web.clickstream.ClickstreamSession;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected EngineSettingService engineSettingService;

    @Value("${env.name}")
    protected String environmentName;

    @Value("${app.name}")
    protected String applicationName;

    @Value("${context.name}")
    protected String contextName;

    @Autowired
    protected MarketService marketService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    @Autowired
    protected ProductService productService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected LocalizationService localizationService;
    
    @Autowired
    protected RetailerService retailerService;
    
    @Autowired
    protected UserService userService;
    
    @Autowired
    protected EngineSessionService engineSessionService;
    
    @Autowired
    protected CartService cartService;
    
    /**
	 *
	 */
    public boolean isLocalHostMode(final HttpServletRequest request) throws Exception {
        if (StringUtils.isNotEmpty(getHost(request)) && (getHost(request).contains("localhost") || getHost(request).equalsIgnoreCase("127.0.0.1"))) {
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
    public String getApplicationName() throws Exception {
        return applicationName;
    }

    /**
	 *
	 */
    public String getContextName() throws Exception {
        return contextName;
    }

    /**
	 *
	 */
    public DateFormat getFormatDate(final RequestData requestData, final int dateStyle, final int timeStyle) throws Exception {
        final Locale locale = requestData.getLocale();
        DateFormat formatter = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
        return formatter;
    }

    /**
	 *
	 */
    public SimpleDateFormat getRssFormatDate(final RequestData requestData) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        return formatter;
    }

    /**
	 *
	 */
    public SimpleDateFormat getDataVocabularyFormatDate(final RequestData requestData) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter;
    }

    /**
	 *
	 */
    public SimpleDateFormat getAtomFormatDate(final RequestData requestData) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        return formatter;
    }

    /**
    * 
    */
    public ClickstreamSession getClickstreamSession(final HttpServletRequest request) throws Exception {
        ClickstreamSession clickstream = (ClickstreamSession) request.getSession().getAttribute(Constants.ENGINE_CLICKSTREAM);
        if(clickstream == null){
            clickstream = new ClickstreamSession();
            clickstream.setInitialReferrer(request.getHeader("REFERER"));
        }
        return clickstream;
    }
    
    /**
    * 
    */
    public void addClickstream(final HttpServletRequest request) throws Exception {
        ClickstreamSession clickstream = getClickstreamSession(request);
        Date lastRequest = new Date();
        clickstream.setLastRequest(lastRequest);
        clickstream.setHostname(request.getRemoteHost());
        
        clickstream.getRequests().add(new ClickstreamRequest(request, lastRequest));

        request.getSession().setAttribute(Constants.ENGINE_CLICKSTREAM, clickstream);
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
    public String getLastRequestForEmptyCartUrl(final HttpServletRequest request, final String fallbackUrl) throws Exception {
        final List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add("login");
        excludedPatterns.add("auth");
        excludedPatterns.add("logout");
        excludedPatterns.add("timeout");
        excludedPatterns.add("forbidden");
        excludedPatterns.add("cart");
        String lastUrl = getLastRequestUrl(request, excludedPatterns, fallbackUrl);
        return lastUrl;
    }
    
    /**
    * 
    */
    public String getLastRequestUrl(final HttpServletRequest request, final List<String> excludedPatterns, String fallbackUrl) throws Exception {
        String url = getRequestUrl(request, excludedPatterns, 1);
        if (StringUtils.isEmpty(url)) {
            return fallbackUrl;
        }
        return url;
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
    public String getRequestUrl(final HttpServletRequest request, final List<String> excludedPatterns, int position) throws Exception {
        String url = Constants.EMPTY;
        ClickstreamSession clickstreamSession = getClickstreamSession(request);
        
        final List<ClickstreamRequest> clickstreams = clickstreamSession.getRequests();

        if (clickstreams != null && !clickstreams.isEmpty()) {
            if (clickstreams != null && !clickstreams.isEmpty()) {
                // Clean not html values or exluded patterns
                List<ClickstreamRequest> cleanClickstreams = new ArrayList<ClickstreamRequest>();
                Iterator<ClickstreamRequest> it = clickstreams.iterator();
                while (it.hasNext()) {
                    ClickstreamRequest clickstream = (ClickstreamRequest) it.next();
                    String uri = clickstream.getRequestURI();
                    if (uri.endsWith(".html")) {
                        // TEST IF THE URL IS EXCLUDE
                        CharSequence[] excludedPatternsCharSequence = excludedPatterns.toArray(new CharSequence[excludedPatterns.size()]);
                        boolean isExclude = false;
                        for (int i = 0; i < excludedPatternsCharSequence.length; i++) {
                            CharSequence string = excludedPatternsCharSequence[i];
                            if (uri.contains(string)) {
                                isExclude = true;
                            }
                        }
                        if (BooleanUtils.negate(isExclude)) {
                            cleanClickstreams.add(clickstream);
                        }
                    }
                }

                if (cleanClickstreams.size() == 1) {
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
                        if (countCleanClickstream == (cleanClickstreams.size() - position)) {
                            url = uri;
                        }
                        countCleanClickstream++;
                    }
                }
            }
        }

        // CLEAN CONTEXT FROM URL
        if (StringUtils.isNotEmpty(url) && !isLocalHostMode(request) && url.contains(request.getContextPath())) {
            url = url.replace(request.getContextPath(), "");
        }
        return handleUrl(url);
    }

    /**
     * 
     */
    public String getRootAssetFilePath(final HttpServletRequest request) throws Exception {
        EngineSetting engineSetting = engineSettingService.getAssetFileRootPath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        if (prefixPath.endsWith("/")) {
            prefixPath = prefixPath.substring(0, prefixPath.length() - 1);
        }
        return prefixPath;
    }

    /**
     * 
     */
    public String getRootAssetWebPath(final HttpServletRequest request) throws Exception {
        EngineSetting engineSetting = engineSettingService.getAssetWebRootPath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        if (prefixPath.endsWith("/")) {
            prefixPath = prefixPath.substring(0, prefixPath.length() - 1);
        }
        return prefixPath;
    }

    /**
     * 
     */
    public String getCatalogImageWebPath(final HttpServletRequest request, final Asset asset) throws Exception {
        EngineSetting engineSetting = engineSettingService.getAssetCatalogFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String catalogImageWebPath = getRootAssetWebPath(request) + prefixPath + "/" + asset.getType().getPropertyKey().toLowerCase() + "/" + asset.getPath();
        if (catalogImageWebPath.endsWith("/")) {
            catalogImageWebPath = catalogImageWebPath.substring(0, catalogImageWebPath.length() - 1);
        }
        return catalogImageWebPath;
    }

    /**
     * 
     */
    public String getProductMarketingImageWebPath(final HttpServletRequest request, final Asset asset) throws Exception {
        EngineSetting engineSetting = engineSettingService.getAssetProductMarketingFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String productMarketingImageWebPath = getRootAssetWebPath(request) + prefixPath + "/" + asset.getType().getPropertyKey().toLowerCase() + "/" + asset.getPath();
        if (productMarketingImageWebPath.endsWith("/")) {
            productMarketingImageWebPath = productMarketingImageWebPath.substring(0, productMarketingImageWebPath.length() - 1);
        }
        return productMarketingImageWebPath;
    }

    /**
     * 
     */
    public String getProductSkuImageWebPath(final HttpServletRequest request, final Asset asset) throws Exception {
        EngineSetting engineSetting = engineSettingService.getAssetPoductSkuFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String productSkuImageWebPath = getRootAssetWebPath(request) + prefixPath + "/" + asset.getType().getPropertyKey().toLowerCase() + "/" + asset.getPath();
        if (productSkuImageWebPath.endsWith("/")) {
            productSkuImageWebPath = productSkuImageWebPath.substring(0, productSkuImageWebPath.length() - 1);
        }
        return productSkuImageWebPath;
    }
    
    /**
     * 
     */
    public String getRetailerOrStoreImageWebPath(final HttpServletRequest request, final Asset asset) throws Exception {
        EngineSetting engineSetting = engineSettingService.getAssetRetailerAndStoreFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String storeImageWebPath = getRootAssetWebPath(request) + prefixPath + "/" + asset.getScope().name().toLowerCase() + "/" + asset.getType().getPropertyKey().toLowerCase() + "/" + asset.getPath();
        if (storeImageWebPath.endsWith("/")) {
            storeImageWebPath = storeImageWebPath.substring(0, storeImageWebPath.length() - 1);
        }
        return storeImageWebPath;
    }

    /**
     * 
     */
    public String getCurrentThemeResourcePrefixPath(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        EngineSetting engineSetting = engineSettingService.getThemeResourcePrefixPath();
        try {
            String contextValue = getCurrentContextNameValue(request);
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(contextValue);
            String prefixPath = engineSetting.getDefaultValue();
            if (engineSettingValue != null) {
                prefixPath = engineSettingValue.getValue();
            } else {
                logger.warn("This engine setting is request, but doesn't exist: " + engineSetting.getCode() + "/" + contextValue);
            }
            String currentThemeResourcePrefixPath = prefixPath + getCurrentTheme(request);
            if (currentThemeResourcePrefixPath.endsWith("/")) {
                currentThemeResourcePrefixPath = currentThemeResourcePrefixPath.substring(0, currentThemeResourcePrefixPath.length() - 1);
            }
            return currentThemeResourcePrefixPath;

        } catch (Exception e) {
            logger.error("Context name, " + getContextName() + " can't be resolve by EngineSettingWebAppContext class.", e);
        }
        return null;
    }

    /**
     * 
     */
    public String getCurrentContextNameValue(final HttpServletRequest request) throws Exception {
        return EngineSettingWebAppContext.valueOf(getContextName()).getPropertyKey();
    }

    /**
     * 
     */
    public String getCurrentVelocityWebPrefix(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        String velocityPath = "/" + getCurrentTheme(request) + "/www/" + getCurrentDevice(requestData) + "/content/";
        return velocityPath;
    }

    /**
     * 
     */
    public String getCurrentVelocityEmailPrefix(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        String velocityPath = "/" + getCurrentTheme(request) + "/email/";
        return velocityPath;
    }

    /**
     * 
     */
    protected String handleUrl(String url) {
        return url;
    }

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
    public EngineEcoSession updateCurrentEcoSession(final HttpServletRequest request, EngineEcoSession engineEcoSession) throws Exception {
        setCurrentEcoSession(request, engineEcoSession);
        return engineEcoSession;
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
    public void resetCurrentCart(final HttpServletRequest request) throws Exception {
        EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
        engineEcoSession.resetCurrentCart();
        updateCurrentEcoSession(request, engineEcoSession);
    }
    
    /**
     * 
     */
    public void updateCurrentCart(final HttpServletRequest request, final Cart cart) throws Exception {
        // SAVE AND UPDATE THE ENGINE SESSION AT THE END
        EngineEcoSession engineEcoSessionWithTransientValues = getCurrentEcoSession(request);
        engineEcoSessionWithTransientValues.updateCart(cart);
        engineEcoSessionWithTransientValues = engineSessionService.updateAndSynchronizeEngineEcoSession(engineEcoSessionWithTransientValues);
        updateCurrentEcoSession(request, engineEcoSessionWithTransientValues); 
    }
    
    /**
     * 
     */
    public void deleteCurrentCartAndSaveEngineSession(final HttpServletRequest request) throws Exception {
        EngineEcoSession engineEcoSessionWithTransientValues = getCurrentEcoSession(request);
        engineEcoSessionWithTransientValues.deleteCurrentCart();
        engineSessionService.updateAndSynchronizeEngineEcoSession(engineEcoSessionWithTransientValues);
        updateCurrentEcoSession(request, engineEcoSessionWithTransientValues); 
    }
    
    /**
     * 
     */
    public OrderCustomer getLastOrder(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
        return engineEcoSession.getLastOrder();
    }

    /**
     * 
     */
    public void keepLastOrderInSession(final RequestData requestData, final OrderCustomer order) throws Exception {
        if (order != null) {
            final HttpServletRequest request = requestData.getRequest();
            EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            engineEcoSession.setLastOrder(order);
            updateCurrentEcoSession(request, engineEcoSession);
        }
    }

    /**
     * 
     */
    protected MarketPlace getCurrentMarketPlace(final RequestData requestData) throws Exception {
        MarketPlace marketPlace = null;
        final HttpServletRequest request = requestData.getRequest();
        if (requestData.isBackoffice()) {
            EngineBoSession engineBoSession = getCurrentBoSession(request);
            marketPlace = engineBoSession.getCurrentMarketPlace();
            if (marketPlace == null) {
                initDefaultBoMarketPlace(request);
                marketPlace = engineBoSession.getCurrentMarketPlace();
            }
        } else {
            EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            marketPlace = engineEcoSession.getCurrentMarketPlace();
            if (marketPlace == null) {
                initDefaultEcoMarketPlace(request);
                marketPlace = engineEcoSession.getCurrentMarketPlace();
            }
        }
        return marketPlace;
    }

    /**
     * 
     */
    protected Market getCurrentMarket(final RequestData requestData) throws Exception {
        Market market = null;
        final HttpServletRequest request = requestData.getRequest();
        if (requestData.isBackoffice()) {
            EngineBoSession engineBoSession = getCurrentBoSession(request);
            market = engineBoSession.getCurrentMarket();
            if (market == null) {
                initDefaultEcoMarketPlace(request);
                market = engineBoSession.getCurrentMarket();
            }
        } else {
            EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            market = engineEcoSession.getCurrentMarket();
            if (market == null) {
                initDefaultEcoMarketPlace(request);
                market = engineEcoSession.getCurrentMarket();
            }
        }
        return market;
    }

    /**
     * 
     */
    protected MarketArea getCurrentMarketArea(final RequestData requestData) throws Exception {
        MarketArea marketArea = null;
        final HttpServletRequest request = requestData.getRequest();
        if (requestData.isBackoffice()) {
            EngineBoSession engineBoSession = getCurrentBoSession(request);
            marketArea = engineBoSession.getCurrentMarketArea();
            if (marketArea == null) {
                initDefaultBoMarketPlace(request);
                marketArea = engineBoSession.getCurrentMarketArea();
            }
        } else {
            EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            marketArea = engineEcoSession.getCurrentMarketArea();
            if (marketArea == null) {
                initDefaultEcoMarketPlace(request);
                marketArea = engineEcoSession.getCurrentMarketArea();
            }
        }
        return marketArea;
    }

    /**
     * 
     */
    protected Localization getCurrentMarketLocalization(final RequestData requestData) throws Exception {
        Localization localization = null;
        final HttpServletRequest request = requestData.getRequest();
        if (requestData.isBackoffice()) {
            EngineBoSession engineBoSession = getCurrentBoSession(request);
            localization = engineBoSession.getCurrentMarketAreaLocalization();
        } else {
            EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            localization = engineEcoSession.getCurrentMarketAreaLocalization();
        }
        return localization;
    }

    /**
     * 
     */
    protected Localization getCurrentMarketAreaLocalization(final RequestData requestData) throws Exception {
        Localization localization = null;
        final HttpServletRequest request = requestData.getRequest();
        if (requestData.isBackoffice()) {
            EngineBoSession engineBoSession = getCurrentBoSession(request);
            localization = engineBoSession.getCurrentMarketAreaLocalization();
        } else {
            EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            localization = engineEcoSession.getCurrentMarketAreaLocalization();
        }
        return localization;
    }

    /**
     * 
     */
    public void updateCurrentLocalization(final RequestData requestData, final Localization localization) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        if (localization != null) {
            if (requestData.isBackoffice()) {
                EngineBoSession engineBoSession = getCurrentBoSession(request);
                engineBoSession.setCurrentBackofficeLocalization(localization);
                updateCurrentBoSession(request, engineBoSession);
            } else {
                EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
                setSessionMarketAreaLocalization(engineEcoSession, localization);
                updateCurrentEcoSession(request, engineEcoSession);
            }
        }
    }

    /**
     * 
     */
    protected Retailer getCurrentRetailer(final RequestData requestData) throws Exception {
        Retailer retailer = null;
        final HttpServletRequest request = requestData.getRequest();
        if (requestData.isBackoffice()) {
            EngineBoSession engineBoSession = getCurrentBoSession(request);
            retailer = engineBoSession.getCurrentMarketAreaRetailer();
            if (retailer == null) {
                initDefaultBoMarketPlace(request);
            }
        } else {
            EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            retailer = engineEcoSession.getCurrentMarketAreaRetailer();
            if (retailer == null) {
                initDefaultEcoMarketPlace(request);
            }
        }
        return retailer;
    }

    /**
     * 
     */
    protected CurrencyReferential getCurrentCurrency(final HttpServletRequest request) throws Exception {
        EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
        return engineEcoSession.getCurrentMarketAreaCurrency();
    }

    /**
     * 
     */
    protected Cart getCurrentCart(final HttpServletRequest request) throws Exception {
        EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
        return engineEcoSession.getCart();
    }
    
    /**
     * 
     */
    protected Customer getCurrentCustomer(final HttpServletRequest request) throws Exception {
        EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
        Customer customer = engineEcoSession.getCurrentCustomer();
        if (customer == null) {
            // CHECK
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                if (StringUtils.isNotEmpty(username) && !username.equalsIgnoreCase("anonymousUser")) {
                    customer = customerService.getCustomerByLoginOrEmail(username);
                    updateCurrentCustomer(request, customer);
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
        if (customer != null) {
            if (StringUtils.isNotEmpty(customer.getAvatarImg())) {
                customerAvatar = customer.getAvatarImg();
            } else {
                String email = customer.getEmail().toLowerCase().trim();
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] array = md.digest(email.getBytes("CP1252"));
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < array.length; ++i) {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
                }
                String gravatarId = sb.toString();
                if ("https".equals(request.getScheme())) {
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
        String marketPlaceCode = null;
        String marketCode = null;
        String marketAreaCode = null;
        String localizationCode = null;
        String retailerCode = null;
        String currencyCode = null;

        // TEMP
        String requestUri = request.getRequestURI();
        requestUri = requestUri.replace(request.getContextPath(), "");
        if (requestUri.startsWith("/")) {
            requestUri = requestUri.substring(1, requestUri.length());
        }
        String[] uriSegments = requestUri.toString().split("/");
        if (uriSegments.length > 4) {
            marketPlaceCode = uriSegments[0];
            marketCode = uriSegments[1];
            marketAreaCode = uriSegments[2];
            localizationCode = uriSegments[3];
            retailerCode = uriSegments[4];
        } else {
            marketPlaceCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE);
            marketCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_CODE);
            marketAreaCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE);
            localizationCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_LANGUAGE);
            retailerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_RETAILER_CODE);
            currencyCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CURRENCY_CODE);
        }

        EngineEcoSession engineEcoSession = getCurrentEcoSession(request);

        MarketPlace currentMarketPlace = engineEcoSession.getCurrentMarketPlace();
        if (StringUtils.isNotEmpty(marketPlaceCode) && StringUtils.isNotEmpty(marketCode) && StringUtils.isNotEmpty(marketAreaCode) && StringUtils.isNotEmpty(localizationCode)) {
            if (currentMarketPlace != null && !currentMarketPlace.getCode().equalsIgnoreCase(marketPlaceCode)) {
                // RESET ALL SESSION AND CHANGE THE MARKET PLACE
                initEcoSession(request);
                MarketPlace newMarketPlace = marketService.getMarketPlaceByCode(marketPlaceCode);
                if (newMarketPlace == null) {
                    // INIT A DEFAULT MARKET PLACE
                    initDefaultEcoMarketPlace(request);
                } else {
                    // MARKET PLACE
                    setSessionMarketPlace(engineEcoSession, newMarketPlace);
                    updateCurrentTheme(request, newMarketPlace.getTheme());

                    // MARKET
                    Market market = newMarketPlace.getMarket(marketCode);
                    if (market == null) {
                        market = newMarketPlace.getDefaultMarket();
                    }
                    setSessionMarket(engineEcoSession, market);

                    // MARKET MODE
                    MarketArea marketArea = market.getMarketArea(marketAreaCode);
                    if (marketArea == null) {
                        marketArea = market.getDefaultMarketArea();
                    }
                    engineEcoSession.setCurrentMarketArea(marketArea);

                    // LOCALE
                    Localization localization = marketArea.getLocalization(localizationCode);
                    if (localization == null) {
                        Localization defaultLocalization = marketArea.getDefaultLocalization();
                        setSessionMarketAreaLocalization(engineEcoSession, defaultLocalization);
                    } else {
                        setSessionMarketAreaLocalization(engineEcoSession, localization);
                    }

                    // RETAILER
                    Retailer retailer = marketArea.getRetailer(retailerCode);
                    if (retailer == null) {
                        Retailer defaultRetailer = marketArea.getDefaultRetailer();
                        setSessionMarketAreaRetailer(engineEcoSession, defaultRetailer);
                    } else {
                        setSessionMarketAreaRetailer(engineEcoSession, retailer);
                    }
                    
                    // CURRENCY
                    CurrencyReferential currency = marketArea.getCurrency(currencyCode);
                    if (currency == null) {
                        CurrencyReferential defaultCurrency = marketArea.getDefaultCurrency();
                        setSessionMarketAreaCurrency(engineEcoSession, defaultCurrency);
                    } else {
                        setSessionMarketAreaCurrency(engineEcoSession, currency);
                    }
                }

            } else {
                Market market = engineEcoSession.getCurrentMarket();
                if (market != null && !market.getCode().equalsIgnoreCase(marketCode)) {

                    // CHANGE THE MARKET
                    Market newMarket = marketService.getMarketByCode(marketCode);
                    if (newMarket == null) {
                        newMarket = currentMarketPlace.getDefaultMarket();
                    }
                    setSessionMarket(engineEcoSession, market);
                    updateCurrentTheme(request, newMarket.getTheme());

                    // MARKET MODE
                    MarketArea marketArea = newMarket.getMarketArea(marketAreaCode);
                    if (marketArea == null) {
                        marketArea = market.getDefaultMarketArea();
                    }
                    setSessionMarketArea(engineEcoSession, marketArea);

                    // LOCALE
                    Localization localization = marketArea.getLocalization(localizationCode);
                    if (localization == null) {
                        Localization defaultLocalization = marketArea.getDefaultLocalization();
                        setSessionMarketAreaLocalization(engineEcoSession, defaultLocalization);
                    } else {
                        setSessionMarketAreaLocalization(engineEcoSession, localization);
                    }

                    // RETAILER
                    Retailer retailer = marketArea.getRetailer(retailerCode);
                    if (retailer == null) {
                        Retailer defaultRetailer = marketArea.getDefaultRetailer();
                        setSessionMarketAreaRetailer(engineEcoSession, defaultRetailer);
                    } else {
                        setSessionMarketAreaRetailer(engineEcoSession, retailer);
                    }
                    
                    // CURRENCY
                    CurrencyReferential currency = marketArea.getCurrency(currencyCode);
                    if (currency == null) {
                        CurrencyReferential defaultCurrency = marketArea.getDefaultCurrency();
                        setSessionMarketAreaCurrency(engineEcoSession, defaultCurrency);
                    } else {
                        setSessionMarketAreaCurrency(engineEcoSession, currency);
                    }
                    
                } else {
                    MarketArea marketArea = engineEcoSession.getCurrentMarketArea();
                    if (marketArea != null && !marketArea.getCode().equalsIgnoreCase(marketAreaCode)) {

                        // CHANGE THE MARKET MODE
                        MarketArea newMarketArea = market.getMarketArea(marketAreaCode);
                        if (newMarketArea == null) {
                            newMarketArea = market.getDefaultMarketArea();
                        }
                        setSessionMarketArea(engineEcoSession, newMarketArea);
                        updateCurrentTheme(request, newMarketArea.getTheme());

                        // LOCALE
                        Localization localization = newMarketArea.getLocalization(localizationCode);
                        if (localization == null) {
                            Localization defaultLocalization = marketArea.getDefaultLocalization();
                            setSessionMarketAreaLocalization(engineEcoSession, defaultLocalization);
                        } else {
                            setSessionMarketAreaLocalization(engineEcoSession, localization);
                        }

                        // RETAILER
                        Retailer retailer = marketArea.getRetailer(retailerCode);
                        if (retailer == null) {
                            Retailer defaultRetailer = marketArea.getDefaultRetailer();
                            setSessionMarketAreaRetailer(engineEcoSession, defaultRetailer);
                        } else {
                            setSessionMarketAreaRetailer(engineEcoSession, retailer);
                        }
                        
                        // CURRENCY
                        CurrencyReferential currency = marketArea.getCurrency(currencyCode);
                        if (currency == null) {
                            CurrencyReferential defaultCurrency = marketArea.getDefaultCurrency();
                            setSessionMarketAreaCurrency(engineEcoSession, defaultCurrency);
                        } else {
                            setSessionMarketAreaCurrency(engineEcoSession, currency);
                        }
                        
                    } else {
                        Localization localization = engineEcoSession.getCurrentMarketAreaLocalization();
                        if (localization != null && !localization.getLocale().toString().equalsIgnoreCase(localizationCode)) {
                            // CHANGE THE LOCALE
                            Localization newLocalization = marketArea.getLocalization(localizationCode);
                            if (newLocalization == null) {
                                Localization defaultLocalization = marketArea.getDefaultLocalization();
                                setSessionMarketAreaLocalization(engineEcoSession, defaultLocalization);
                            } else {
                                setSessionMarketAreaLocalization(engineEcoSession, newLocalization);
                            }
                        }
                        
                        Retailer retailer = engineEcoSession.getCurrentMarketAreaRetailer();
                        if (retailer != null && !retailer.getCode().toString().equalsIgnoreCase(retailerCode)) {
                            // CHANGE THE RETAILER
                            Retailer newRetailer = marketArea.getRetailer(retailerCode);
                            if (newRetailer == null) {
                                Retailer defaultRetailer = marketArea.getDefaultRetailer();
                                setSessionMarketAreaRetailer(engineEcoSession, defaultRetailer);
                            } else {
                                setSessionMarketAreaRetailer(engineEcoSession, newRetailer);
                            }
                            
                        } 
                        
                        CurrencyReferential currency = engineEcoSession.getCurrentMarketAreaCurrency();
                        if (currency != null && !currency.getCode().toString().equalsIgnoreCase(currencyCode)) {
                            // CHANGE THE CURRENCY
                            CurrencyReferential newCurrency = marketArea.getCurrency(currencyCode);
                            if (newCurrency == null) {
                                CurrencyReferential defaultCurrency = marketArea.getDefaultCurrency();
                                if(engineEcoSession.getCart() != null){
                                    engineEcoSession.getCart().setCurrency(defaultCurrency);
                                }
                                setSessionMarketAreaCurrency(engineEcoSession, defaultCurrency);
                            } else {
                                if(engineEcoSession.getCart() != null){
                                    engineEcoSession.getCart().setCurrency(newCurrency);
                                }
                                setSessionMarketAreaCurrency(engineEcoSession, newCurrency);
                            }
                        }
                    }
                }
            }
        }

        // THEME
        final MarketArea marketArea = engineEcoSession.getCurrentMarketArea();
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
    public User getCurrentUser(final HttpServletRequest request) throws Exception {
        EngineBoSession engineBoSession = getCurrentBoSession(request);
        return engineBoSession.getCurrentUser();
    }

    /**
     * 
     */
    public void updateCurrentUser(final HttpServletRequest request, final User user) throws Exception {
        if (user != null) {
            final EngineBoSession engineBoSession = getCurrentBoSession(request);
            engineBoSession.setCurrentUser(user);
            updateCurrentCompany(request, user.getCompany());
            updateCurrentBoSession(request, engineBoSession);
        }
    }

    /**
     * 
     */
    public Company getCurrentCompany(final HttpServletRequest request) throws Exception {
        EngineBoSession engineBoSession = getCurrentBoSession(request);
        return engineBoSession.getCurrentCompany();
    }

    public void updateCurrentCompany(final HttpServletRequest request, final Company company) throws Exception {
        if (company != null) {
            final EngineBoSession engineBoSession = getCurrentBoSession(request);
            Company reloadedCompany = userService.getCompanyById(company.getId().toString());
            engineBoSession.setCurrentCompany(reloadedCompany);
            updateCurrentBoSession(request, engineBoSession);
        }
    }
    
    /**
	  * 
	  */
    public void handleBackofficeUrlParameters(final HttpServletRequest request) throws Exception {
        String marketPlaceCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE);
        String marketCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_CODE);
        String marketAreaCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE);
        String marketAreaLanguageCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_LANGUAGE);
        String marketAreaRetailerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_RETAILER_CODE);

        EngineBoSession engineBoSession = getCurrentBoSession(request);

        MarketPlace currentMarketPlace = engineBoSession.getCurrentMarketPlace();
        if (StringUtils.isNotEmpty(marketPlaceCode) 
                && StringUtils.isNotEmpty(marketCode) 
                && StringUtils.isNotEmpty(marketAreaCode) 
                && StringUtils.isNotEmpty(marketAreaLanguageCode)) {
            if (currentMarketPlace != null && !currentMarketPlace.getCode().equalsIgnoreCase(marketPlaceCode)) {
                // RESET ALL SESSION AND CHANGE THE MARKET PLACE
                initBoSession(request);
                MarketPlace newMarketPlace = marketService.getMarketPlaceByCode(marketPlaceCode);
                if (newMarketPlace == null) {
                    // INIT A DEFAULT MARKET PLACE
                    initDefaultBoMarketPlace(request);
                } else {
                    // MARKET PLACE
                    setSessionMarketPlace(engineBoSession, newMarketPlace);
                    updateCurrentTheme(request, newMarketPlace.getTheme());

                    // MARKET
                    Market market = newMarketPlace.getMarket(marketCode);
                    if (market == null) {
                        market = newMarketPlace.getDefaultMarket();
                    }
                    setSessionMarket(engineBoSession, market);

                    // MARKET MODE
                    MarketArea marketArea = market.getMarketArea(marketAreaCode);
                    if (marketArea == null) {
                        marketArea = market.getDefaultMarketArea();
                    }
                    setSessionMarketArea(engineBoSession, marketArea);

                    // LOCALE
                    Localization localization = marketArea.getLocalization(marketAreaLanguageCode);
                    if (localization == null) {
                        Localization defaultLocalization = marketArea.getDefaultLocalization();
                        setSessionMarketAreaLocalization(engineBoSession, defaultLocalization);
                    } else {
                        setSessionMarketAreaLocalization(engineBoSession, localization);
                    }

                    // RETAILER
                    Retailer retailer = marketArea.getRetailer(marketAreaRetailerCode);
                    if (retailer == null) {
                        Retailer defaultRetailer = marketArea.getDefaultRetailer();
                        setSessionMarketAreaRetailer(engineBoSession, defaultRetailer);
                    } else {
                        setSessionMarketAreaRetailer(engineBoSession, retailer);
                    }
                }

            } else {
                Market market = engineBoSession.getCurrentMarket();
                if (market != null && !market.getCode().equalsIgnoreCase(marketCode)) {

                    // CHANGE THE MARKET
                    Market newMarket = marketService.getMarketByCode(marketCode);
                    if (newMarket == null) {
                        newMarket = currentMarketPlace.getDefaultMarket();
                    }
                    setSessionMarket(engineBoSession, market);
                    updateCurrentTheme(request, newMarket.getTheme());

                    // MARKET MODE
                    MarketArea marketArea = newMarket.getMarketArea(marketAreaCode);
                    if (marketArea == null) {
                        marketArea = market.getDefaultMarketArea();
                    }
                    setSessionMarketArea(engineBoSession, marketArea);

                    // LOCALE
                    Localization localization = marketArea.getLocalization(marketAreaLanguageCode);
                    if (localization == null) {
                        Localization defaultLocalization = marketArea.getDefaultLocalization();
                        setSessionMarketAreaLocalization(engineBoSession, defaultLocalization);
                    } else {
                        setSessionMarketAreaLocalization(engineBoSession, localization);
                    }

                    // RETAILER
                    Retailer retailer = marketArea.getRetailer(marketAreaRetailerCode);
                    if (retailer == null) {
                        Retailer defaultRetailer = marketArea.getDefaultRetailer();
                        setSessionMarketAreaRetailer(engineBoSession, defaultRetailer);
                    } else {
                        setSessionMarketAreaRetailer(engineBoSession, retailer);
                    }
                    
                } else {
                    MarketArea marketArea = engineBoSession.getCurrentMarketArea();
                    if (marketArea != null && !marketArea.getCode().equalsIgnoreCase(marketAreaCode)) {

                        // CHANGE THE MARKET MODE
                        MarketArea newMarketArea = market.getMarketArea(marketAreaCode);
                        if (newMarketArea == null) {
                            newMarketArea = market.getDefaultMarketArea();
                        }
                        setSessionMarketArea(engineBoSession, marketArea);
                        updateCurrentTheme(request, newMarketArea.getTheme());

                        // LOCALE
                        Localization localization = newMarketArea.getLocalization(marketAreaLanguageCode);
                        if (localization == null) {
                            Localization defaultLocalization = marketArea.getDefaultLocalization();
                            setSessionMarketAreaLocalization(engineBoSession, defaultLocalization);
                        } else {
                            setSessionMarketAreaLocalization(engineBoSession, localization);
                        }

                        // RETAILER
                        Retailer retailer = marketArea.getRetailer(marketAreaRetailerCode);
                        if (retailer == null) {
                            Retailer defaultRetailer = marketArea.getDefaultRetailer();
                            setSessionMarketAreaRetailer(engineBoSession, defaultRetailer);
                        } else {
                            setSessionMarketAreaRetailer(engineBoSession, retailer);
                        }
                        
                    } else {
                        Localization localization = engineBoSession.getCurrentMarketAreaLocalization();
                        if (localization != null && !localization.getLocale().toString().equalsIgnoreCase(marketAreaLanguageCode)) {
                            // CHANGE THE LOCALE
                            Localization newLocalization = marketArea.getLocalization(marketAreaLanguageCode);
                            if (newLocalization == null) {
                                Localization defaultLocalization = marketArea.getDefaultLocalization();
                                setSessionMarketAreaLocalization(engineBoSession, defaultLocalization);
                            } else {
                                setSessionMarketAreaLocalization(engineBoSession, newLocalization);
                            }

                            // RETAILER
                            Retailer retailer = marketArea.getRetailer(marketAreaRetailerCode);
                            if (retailer == null) {
                                Retailer defaultRetailer = marketArea.getDefaultRetailer();
                                setSessionMarketAreaRetailer(engineBoSession, defaultRetailer);
                            } else {
                                setSessionMarketAreaRetailer(engineBoSession, retailer);
                            }

                        } else {
                            Retailer retailer = engineBoSession.getCurrentMarketAreaRetailer();
                            if (retailer != null && !retailer.getCode().toString().equalsIgnoreCase(marketAreaRetailerCode)) {
                                // CHANGE THE RETAILER
                                Retailer newRetailer = marketArea.getRetailer(marketAreaRetailerCode);
                                if (newRetailer == null) {
                                    Retailer defaultRetailer = marketArea.getDefaultRetailer();
                                    setSessionMarketAreaRetailer(engineBoSession, defaultRetailer);
                                } else {
                                    setSessionMarketAreaRetailer(engineBoSession, newRetailer);
                                }
                            }
                        }
                    }
                }
            }
        }

        // CHECK BACKOFFICE LANGUAGES
        String localizationCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_LOCALE_CODE);
        // LOCALIZATIONS
        Company company = getCurrentCompany(request);
        if (company != null) {
            Localization localization = company.getLocalization(localizationCode);
            if (localization != null) {
                engineBoSession.setCurrentBackofficeLocalization(localization);
            }
        }

        // SAVE THE ENGINE SESSION
        updateCurrentBoSession(request, engineBoSession);
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
    public String getCurrentDevice(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        String currenDevice = "default";
        if (requestData.isBackoffice()) {
            EngineBoSession engineBoSession = getCurrentBoSession(request);
            if (StringUtils.isNotEmpty(engineBoSession.getDevice())) {
                currenDevice = engineBoSession.getDevice();
            }
        } else {
            EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            if (StringUtils.isNotEmpty(engineEcoSession.getDevice())) {
                currenDevice = engineEcoSession.getDevice();
            }
        }
        return currenDevice;
    }

    /**
     * 
     */
    @Override
    public void updateCurrentDevice(final RequestData requestData, final String device) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        if (requestData.isBackoffice()) {
            final EngineBoSession engineBoSession = getCurrentBoSession(request);
            if (StringUtils.isNotEmpty(device)) {
                engineBoSession.setDevice(device);
                updateCurrentBoSession(request, engineBoSession);
            }
        } else {
            final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
            if (StringUtils.isNotEmpty(device)) {
                engineEcoSession.setDevice(device);
                updateCurrentEcoSession(request, engineEcoSession);
            }
        }
    }

    /**
     * 
     */
    public RequestData getRequestData(final HttpServletRequest request) throws Exception {
        final RequestData requestData = new RequestData();
        requestData.setRequest(request);
        String contextPath = "";
        if (request.getRequestURL().toString().contains("localhost") || request.getRequestURL().toString().contains("127.0.0.1")) {
            contextPath = contextPath + request.getContextPath() + "/";
        } else {
            contextPath = "/";
        }
        requestData.setContextPath(contextPath);
        requestData.setContextNameValue(getCurrentContextNameValue(request));

        requestData.setVelocityEmailPrefix(getCurrentVelocityEmailPrefix(requestData));

        Customer customer = getCurrentCustomer(request);
        if (customer != null) {
            requestData.setCustomer(customer);
        }
        
        User user = getCurrentUser(request);
        if (user != null) {
            requestData.setUser(user);
        }

        Company company = getCurrentCompany(request);
        if (company != null) {
            requestData.setCompany(company);
        }
        
        requestData.setMarketPlace(getCurrentMarketPlace(requestData));
        requestData.setMarket(getCurrentMarket(requestData));
        requestData.setMarketArea(getCurrentMarketArea(requestData));
        requestData.setMarketAreaLocalization(getCurrentMarketAreaLocalization(requestData));
        requestData.setMarketAreaRetailer(getCurrentRetailer(requestData));
        requestData.setMarketAreaCurrency(getCurrentCurrency(request));

        requestData.setCart(getCurrentCart(request));

        return requestData;
    }

    /**
	 * 
	 */
    protected EngineEcoSession initEcoSession(final HttpServletRequest request) throws Exception {
        EngineEcoSession engineEcoSession = new EngineEcoSession();
        EngineSetting engineSettingEnvironmentStagingModeEnabled = engineSettingService.getEnvironmentStagingModeEnabled();
        if (engineSettingEnvironmentStagingModeEnabled != null) {
            engineEcoSession.setEnvironmentStagingModeEnabled(BooleanUtils.toBoolean(engineSettingEnvironmentStagingModeEnabled.getDefaultValue()));
        } else {
            engineEcoSession.setEnvironmentStagingModeEnabled(false);
            logger.warn("Environment Type is not define in your database. Check the " + EngineSettingService.ENGINE_SETTING_ENVIRONMENT_STAGING_MODE_ENABLED + " value in settings table.");
        }
        EngineSetting engineSettingEnvironmentType = engineSettingService.getEnvironmentType();
        if (engineSettingEnvironmentType != null) {
            String environmentType = engineSettingEnvironmentType.getDefaultValue();
            try {
                engineEcoSession.setEnvironmentType(EnvironmentType.valueOf(environmentType));
            } catch (Exception e) {
                logger.error("Environment Type has wrong value define in your database. Check the " + EngineSettingService.ENGINE_SETTING_ENVIRONMENT_TYPE + " value in settings table.");
            }
        } else {
            engineEcoSession.setEnvironmentType(EnvironmentType.REEL);
            logger.warn("Environment Type is not define in your database. Check the " + EngineSettingService.ENGINE_SETTING_ENVIRONMENT_TYPE + " value in settings table.");
        }

        // INIT STAGING OR REEL ENVIRONMENT

        setCurrentEcoSession(request, engineEcoSession);
        String jSessionId = request.getSession().getId();
        engineEcoSession.setjSessionId(jSessionId);
        engineEcoSession = initDefaultEcoMarketPlace(request);
        engineEcoSession = initCart(request);
        
        engineEcoSession = updateCurrentEcoSession(request, engineEcoSession);
        
        engineEcoSession = engineSessionService.saveOrUpdateEngineEcoSession(engineEcoSession);
        
        return engineEcoSession;
    }

    /**
     * 
     */
    protected EngineEcoSession checkEngineEcoSession(final HttpServletRequest request, EngineEcoSession engineEcoSession) throws Exception {
        String jSessionId = request.getSession().getId();
        if (engineEcoSession == null) {
            // RELOAD OLD SESSION
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Cookie ecoEngineSessionGuid = null;
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (Constants.COOKIE_ECO_ENGINE_SESSION_ID.equals(cookie.getName())) {
                        ecoEngineSessionGuid = cookies[i];
                        break;
                    }
                }
                if(ecoEngineSessionGuid != null){
                    EngineEcoSession engineEcoSessionWithTransientValues = initEcoSession(request);
                    engineSessionService.synchronizeEngineEcoSession(engineEcoSessionWithTransientValues, ecoEngineSessionGuid.getValue());
                }
            }
            if(engineEcoSession == null){
                engineEcoSession = initEcoSession(request);
            }
            
        }
        // SANITY CHECK
        if (!engineEcoSession.getjSessionId().equals(jSessionId)) {
            engineEcoSession.setjSessionId(jSessionId);
            updateCurrentEcoSession(request, engineEcoSession);
        }
        return engineEcoSession;
    }

    /**
     * 
     */
    protected EngineBoSession checkEngineBoSession(final HttpServletRequest request, EngineBoSession engineBoSession) throws Exception {
        if (engineBoSession == null) {
            engineBoSession = initBoSession(request);
        }
        String jSessionId = request.getSession().getId();
        if (!engineBoSession.getjSessionId().equals(jSessionId)) {
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
        MarketPlace marketPlace = marketService.getDefaultMarketPlace();
        setSessionMarketPlace(engineBoSession, marketPlace);

        Market market = marketPlace.getDefaultMarket();
        setSessionMarket(engineBoSession, market);

        MarketArea marketArea = market.getDefaultMarketArea();
        setSessionMarketArea(engineBoSession, marketArea);

        // DEFAULT LOCALE IS FROM THE REQUEST OR FROM THE MARKET AREA
        String requestLocale = request.getLocale().toString();
        Localization localization = marketArea.getDefaultLocalization();
        if (marketArea.getLocalization(requestLocale) != null) {
            localization = marketArea.getLocalization(requestLocale);
        } else {
            if (requestLocale.length() > 2) {
                String localeLanguage = request.getLocale().getLanguage();
                if (marketArea.getLocalization(localeLanguage) != null) {
                    localization = marketArea.getLocalization(localeLanguage);
                }
            }
        }
        setSessionMarketAreaLocalization(engineBoSession, localization);

        Retailer retailer = marketArea.getDefaultRetailer();
        setSessionMarketAreaRetailer(engineBoSession, retailer);

        updateCurrentBoSession(request, engineBoSession);
    }

    /**
	 * 
	 */
    protected EngineBoSession initBoSession(final HttpServletRequest request) throws Exception {
        final EngineBoSession engineBoSession = new EngineBoSession();
        EngineSetting engineSettingEnvironmentStagingModeEnabled = engineSettingService.getEnvironmentStagingModeEnabled();
        if (engineSettingEnvironmentStagingModeEnabled != null) {
            engineBoSession.setEnvironmentStagingModeEnabled(BooleanUtils.toBoolean(engineSettingEnvironmentStagingModeEnabled.getDefaultValue()));
        } else {
            engineBoSession.setEnvironmentStagingModeEnabled(false);
            logger.warn("Environment Type is not define in your database. Check the " + EngineSettingService.ENGINE_SETTING_ENVIRONMENT_STAGING_MODE_ENABLED + " value in settings table.");
        }
        EngineSetting engineSetting = engineSettingService.getEnvironmentType();
        if (engineSetting != null) {
            String environmentType = engineSetting.getDefaultValue();
            try {
                engineBoSession.setEnvironmentType(EnvironmentType.valueOf(environmentType));
            } catch (Exception e) {
                logger.error("Environment Type has wrong value define in your database. Check the " + EngineSettingService.ENGINE_SETTING_ENVIRONMENT_TYPE + " value in settings table.");
            }
        } else {
            engineBoSession.setEnvironmentType(EnvironmentType.REEL);
            logger.warn("Environment Type is not define in your database. Check the " + EngineSettingService.ENGINE_SETTING_ENVIRONMENT_TYPE + " value in settings table.");
        }

        setCurrentBoSession(request, engineBoSession);
        String jSessionId = request.getSession().getId();
        engineBoSession.setjSessionId(jSessionId);
        initDefaultBoMarketPlace(request);

        // Default Localization
        Company company = getCurrentCompany(request);
        if (company != null) {
            // USER IS LOGGED
            engineBoSession.setCurrentBackofficeLocalization(company.getDefaultLocalization());
        } else {
            Localization defaultLocalization = localizationService.getLocalizationByCode("en");
            engineBoSession.setCurrentBackofficeLocalization(defaultLocalization);
        }

        updateCurrentBoSession(request, engineBoSession);
        return engineBoSession;
    }

    /**
     * 
     */
    protected EngineEcoSession initCart(final HttpServletRequest request) throws Exception {
        final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
        Cart cart = engineEcoSession.getCart();
        if (cart == null) {
            // Init a new empty Cart with a default configuration
            engineEcoSession.addNewCart();
        }
        updateCurrentEcoSession(request, engineEcoSession);
        return engineEcoSession;
    }

    /**
     * @throws Exception
     * 
     */
    protected void resetCart(final HttpServletRequest request) throws Exception {
        // Reset Cart
        final EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
        engineEcoSession.resetCurrentCart();
        updateCurrentEcoSession(request, engineEcoSession);
    }

    /**
     * 
     */
    protected EngineEcoSession initDefaultEcoMarketPlace(final HttpServletRequest request) throws Exception {
        EngineEcoSession engineEcoSession = getCurrentEcoSession(request);
        MarketPlace marketPlace = marketService.getDefaultMarketPlace();
        engineEcoSession = (EngineEcoSession) setSessionMarketPlace(engineEcoSession, marketPlace);

        Market market = marketPlace.getDefaultMarket();
        engineEcoSession = (EngineEcoSession) setSessionMarket(engineEcoSession, market);

        MarketArea marketArea = market.getDefaultMarketArea();
        engineEcoSession = (EngineEcoSession) setSessionMarketArea(engineEcoSession, marketArea);

        // DEFAULT LOCALE IS FROM THE REQUEST OR FROM THE MARKET AREA
        String requestLocale = request.getLocale().toString();
        Localization localization = marketArea.getDefaultLocalization();
        if (marketArea.getLocalization(requestLocale) != null) {
            localization = marketArea.getLocalization(requestLocale);
        } else {
            if (requestLocale.length() > 2) {
                String localeLanguage = request.getLocale().getLanguage();
                if (marketArea.getLocalization(localeLanguage) != null) {
                    localization = marketArea.getLocalization(localeLanguage);
                }
            }
        }
        engineEcoSession = (EngineEcoSession) setSessionMarketAreaLocalization(engineEcoSession, localization);

        Retailer retailer = marketArea.getDefaultRetailer();
        engineEcoSession = (EngineEcoSession) setSessionMarketAreaRetailer(engineEcoSession, retailer);

        CurrencyReferential currency = marketArea.getDefaultCurrency();
        engineEcoSession = (EngineEcoSession) setSessionMarketAreaCurrency(engineEcoSession, currency);

        setCurrentEcoSession(request, engineEcoSession);
        
        return  engineEcoSession; 
    }
    
    protected AbstractEngineSession setSessionMarketPlace(final AbstractEngineSession session, final MarketPlace marketPlace){
        session.setCurrentMarketPlace(marketPlace);
        return session;
    }

    protected AbstractEngineSession setSessionMarket(final AbstractEngineSession session, final Market market){
        // TODO : why : SET A RELOAD OBJECT MARKET -> event
        // LazyInitializationException: could not initialize proxy -
        // no Session
        session.setCurrentMarket(marketService.getMarketById(market.getId().toString()));
        return session;
    }

    protected AbstractEngineSession setSessionMarketArea(final AbstractEngineSession session, final MarketArea marketArea){
        // TODO : why : SET A RELOAD OBJECT MARKET AREA -> event
        // LazyInitializationException: could not initialize proxy -
        // no Session
        session.setCurrentMarketArea(marketService.getMarketAreaById(marketArea.getId().toString()));
        return session;
    }

    protected AbstractEngineSession setSessionMarketAreaLocalization(final AbstractEngineSession session, final Localization localization){
        session.setCurrentMarketAreaLocalization(localizationService.getLocalizationById(localization.getId().toString()));
        return session;
    }

    protected AbstractEngineSession setSessionMarketAreaRetailer(final AbstractEngineSession session, final Retailer retailer){
        session.setCurrentMarketAreaRetailer(retailerService.getRetailerById(retailer.getId().toString()));
        return session;
    }
    
    protected AbstractEngineSession setSessionMarketAreaCurrency(final AbstractEngineSession session, final CurrencyReferential currency){
        session.setCurrentMarketAreaCurrency(currency);
        return session;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getRecentProductIdsFromCookie(final HttpServletRequest request){
		Cookie info=null;
        Cookie[] cookies = request.getCookies();
        Boolean found = false;
        if(cookies !=  null){
	        for(int i=0;i<cookies.length;i++)
	        {
	            info=cookies[i];
	            if(Constants.COOKIE_RECENT_PRODUCT_COOKIE_NAME.equals(info.getName()))
	            {
	                found = true;
	                break;
	            }
	        }
        }   
        List<String> listId = new ArrayList<String>();
        if(found){
        	if(!info.getValue().isEmpty()){
	        	String[] splits = info.getValue().split(" ");
	        	if(splits.length >= 3){
		        	for (int i = splits.length - 1; i >= splits.length - 3 ; i--) {
		        		listId.add(splits[i]);
		        	}
	        	} else {
	        		for (int i = splits.length - 1; i >= 0 ; i--) {
	        			listId.add(splits[i]);
					}
	        	}
        	}
        } 
        
        return listId;
    }
    
    @Override
    public void addOrUpdateRecentProductToCookie(final Long productId, final HttpServletRequest request, final HttpServletResponse response)
    		throws Exception {
        Cookie info=null;
        Cookie[] cookies = request.getCookies();
        Boolean found = false;
        if(cookies !=  null){
	        for(int i=0;i<cookies.length;i++)
	        {
	            info=cookies[i];
	            if(Constants.COOKIE_RECENT_PRODUCT_COOKIE_NAME.equals(info.getName()))
	            {
	                found = true;
	                break;
	            }
	        }
        }   
        if(found){
        	Boolean flag = false;
        	String[] splits = info.getValue().split(" ");
        	for(String value:splits){
        		if(value.equals(Long.toString(productId))){
        			flag = true;
        		} 
        	}
        	if(!flag){
        		String values = info.getValue();
        		values += " "+ Long.toString(productId);
        		info.setValue(values);
        		info.setPath("/");
        		info.setMaxAge(Constants.COOKIES_LENGTH);
        		info.setDomain(request.getServerName());
    			response.addCookie(info);    			
        	} 
        } else {
			info = new Cookie(Constants.COOKIE_RECENT_PRODUCT_COOKIE_NAME, Long.toString(productId));
			info.setMaxAge(Constants.COOKIES_LENGTH);
			info.setPath("/");
			info.setDomain(request.getServerName());
			response.addCookie(info);
        }
    }
}