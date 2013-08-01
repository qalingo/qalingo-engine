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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamListener;
import com.opensymphony.clickstream.ClickstreamRequest;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import fr.hoteia.qalingo.core.service.EngineSettingService;

public abstract class AbstractRequestUtilImpl {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected EngineSettingService engineSettingService;
	
	@Value("${env.name}")  
	protected String environmentName;
	
	@Value("${app.name}")  
	protected String applicationName;
	
	@Value("${context.name}")  
	protected String contextName;
	
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
	public DateFormat getFormatDate(final HttpServletRequest request, final int dateStyle, final int timeStyle) throws Exception {
		final Localization localization = getCurrentLocalization(request);
		final Locale locale = localization.getLocale();
		DateFormat formatter = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale); 
		return formatter;
	}
	
	public abstract Localization getCurrentLocalization(final HttpServletRequest request) throws Exception;
	
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
	public String getRootAssetFilePath(final HttpServletRequest request) throws Exception {
		EngineSetting engineSetting = engineSettingService.getAssetFileRootPath();
		String prefixPath  = "";
		if(engineSetting != null){
			prefixPath  = engineSetting.getDefaultValue();
		}
		if(prefixPath.endsWith("/")){
			prefixPath = prefixPath.substring(0, prefixPath.length() - 1);
		}
		return prefixPath;
	}
	
	/**
     * 
     */
	public String getRootAssetWebPath(final HttpServletRequest request) throws Exception {
		EngineSetting engineSetting = engineSettingService.getAssetWebRootPath();
		String prefixPath  = "";
		if(engineSetting != null){
			prefixPath  = engineSetting.getDefaultValue();
		}
		if(prefixPath.endsWith("/")){
			prefixPath = prefixPath.substring(0, prefixPath.length() - 1);
		}
		return prefixPath;
	}
	
	/**
     * 
     */
	public String getCatalogImageWebPath(final HttpServletRequest request, final Asset asset) throws Exception {
		EngineSetting engineSetting = engineSettingService.getAssetCatalogFilePath();
		String prefixPath  = "";
		if(engineSetting != null){
			prefixPath  = engineSetting.getDefaultValue();
		}
		String catalogImageWebPath = getRootAssetWebPath(request) + prefixPath + "/" + asset.getType().getPropertyKey().toLowerCase() + "/" + asset.getPath();
		if(catalogImageWebPath.endsWith("/")){
			catalogImageWebPath = catalogImageWebPath.substring(0, catalogImageWebPath.length() - 1);
		}
		return catalogImageWebPath;
	}
	
	/**
     * 
     */
	public String getProductMarketingImageWebPath(final HttpServletRequest request, final Asset asset) throws Exception {
		EngineSetting engineSetting = engineSettingService.getAssetProductMarketingFilePath();
		String prefixPath  = "";
		if(engineSetting != null){
			prefixPath  = engineSetting.getDefaultValue();
		}
		String productMarketingImageWebPath = getRootAssetWebPath(request) + prefixPath + "/" + asset.getType().getPropertyKey().toLowerCase() + "/" + asset.getPath();
		if(productMarketingImageWebPath.endsWith("/")){
			productMarketingImageWebPath = productMarketingImageWebPath.substring(0, productMarketingImageWebPath.length() - 1);
		}
		return productMarketingImageWebPath;
	}
	
	/**
     * 
     */
	public String getProductSkuImageWebPath(final HttpServletRequest request, final Asset asset) throws Exception {
		EngineSetting engineSetting = engineSettingService.getAssetPoductSkuFilePath();
		String prefixPath  = "";
		if(engineSetting != null){
			prefixPath  = engineSetting.getDefaultValue();
		}
		String productSkuImageWebPath = getRootAssetWebPath(request) + prefixPath + "/" + asset.getType().getPropertyKey().toLowerCase() + "/" + asset.getPath();
		if(productSkuImageWebPath.endsWith("/")){
			productSkuImageWebPath = productSkuImageWebPath.substring(0, productSkuImageWebPath.length() - 1);
		}
		return productSkuImageWebPath;
	}
	
	/**
     * 
     */
	public String getCurrentThemeResourcePrefixPath(final HttpServletRequest request) throws Exception {
		EngineSetting engineSetting = engineSettingService.getThemeResourcePrefixPath();
		try {
			String contextValue = getCurrentContextNameValue(request);
			EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(contextValue);
			String prefixPath  = engineSetting.getDefaultValue();
			if(engineSettingValue != null){
				prefixPath  = engineSettingValue.getValue();
			} else {
				LOG.warn("This engine setting is request, but doesn't exist: " + engineSetting.getCode() + "/" + contextValue);
			}
			String currentThemeResourcePrefixPath = prefixPath + getCurrentTheme(request);
			if(currentThemeResourcePrefixPath.endsWith("/")){
				currentThemeResourcePrefixPath = currentThemeResourcePrefixPath.substring(0, currentThemeResourcePrefixPath.length() - 1);
			}
			return currentThemeResourcePrefixPath;
	        
        } catch (Exception e) {
        	LOG.error("Context name, " + getContextName() + " can't be resolve by EngineSettingWebAppContext class.", e);
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
	public String getCurrentVelocityPrefix(final HttpServletRequest request) throws Exception {
		String velocityPath = "/" + getCurrentTheme(request) + "/www/" + getCurrentDevice(request) + "/content/";
		return velocityPath;
	}
	
	public abstract String getCurrentDevice(final HttpServletRequest request) throws Exception;
	
	public abstract void updateCurrentDevice(final HttpServletRequest request, final String device) throws Exception;
	
	public abstract String getCurrentTheme(final HttpServletRequest request) throws Exception;
	
	public abstract void updateCurrentTheme(final HttpServletRequest request, final String theme) throws Exception;
	
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
