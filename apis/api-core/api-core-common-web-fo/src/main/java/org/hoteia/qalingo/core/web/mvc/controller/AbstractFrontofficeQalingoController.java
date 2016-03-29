/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.FoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.security.helper.SecurityUtil;
import org.hoteia.qalingo.core.security.util.SecurityRequestUtil;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.HeadLinkViewBean;
import org.hoteia.qalingo.core.web.util.PropertiesUtil;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * <p>
 * <a href="AbstractFrontofficeQalingoController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractFrontofficeQalingoController extends AbstractQalingoController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CustomerService customerService;

	@Autowired
    protected FrontofficeViewBeanFactory frontofficeViewBeanFactory;
	
    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected SecurityRequestUtil securityRequestUtil;
    
    @Autowired
    protected SecurityUtil securityUtil;
    
    /**
     * @throws Exception 
     * 
     */
    protected void overrideDefaultPageTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKey) throws Exception {
        overrideDefaultPageTitle(request, modelAndView, titleKey, null);
    }
    
    /**
     * @throws Exception 
     * 
     */
    protected void overrideDefaultPageTitle(final HttpServletRequest request, final ModelAndView modelAndView, String pageTitleKey, Object[] params) throws Exception {
        final Locale locale = requestUtil.getCurrentMarketAreaLocalization(request).getLocale();
        String headerTitle = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.PAGE_META_OG_TITLE, locale);
        if(StringUtils.isNotEmpty(pageTitleKey)){
            pageTitleKey = pageTitleKey.replace("-", "_");
            if(params != null){
                headerTitle += " - " + getSpecificMessage(ScopeWebMessage.HEADER_TITLE, pageTitleKey, params, locale);
            } else {
                headerTitle += " - " + getSpecificMessage(ScopeWebMessage.HEADER_TITLE, pageTitleKey, locale);
            }
            modelAndView.addObject(ModelConstants.PAGE_TITLE, headerTitle);
        }
    }
	
    /**
     * @throws Exception 
     * 
     */
	protected String getMessageTitleKey(final String pageKey) throws Exception {
		return "page_title_" + pageKey;
	}
	
	protected Map<String, String> getWordingMap(final HttpServletRequest request){
		try {
	        final Locale locale = requestUtil.getCurrentMarketAreaLocalization(request).getLocale();
			String contextName = requestUtil.getContextName();
			String contextValue = PropertiesUtil.getWebappContextKey(contextName);
			return coreMessageSource.loadWordingByContext(contextValue, locale);
	        
        } catch (Exception e) {
        	logger.error("Failed to load wording map.", e);
        }
		return null;
	}
	
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Locale locale){
		return getSpecificMessage(scope.getPropertyKey(), key, locale);
	}
	
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale){
		return getSpecificMessage(scope.getPropertyKey(), key, params, locale);
	}
	
    protected String getSpecificMessage(String scope, String key, Locale locale){
        return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO.getPropertyKey(), scope, key, locale);
    }
    
    protected String getSpecificMessage(String scope, String key, Object[] params, Locale locale){
        return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO.getPropertyKey(), scope, key, params, locale);
    }

    protected List<HeadLinkViewBean> buildConditionsOfUseLinkViewBeans(final RequestData requestData, FoUrls url) {
        final MarketPlace marketPlace = requestData.getMarketPlace();
        final MarketArea marketArea = requestData.getMarketArea();
        
        List<Long> allOpenedMarketAreaIds = marketService.findMarketAreaOpenedByMarketPlace(marketPlace);
        List<MarketArea> allMarketArea = new ArrayList<MarketArea>();
        for (Long marketareaId : allOpenedMarketAreaIds) {
            MarketArea reloadedMarketArea = marketService.getMarketAreaById(marketareaId);
            allMarketArea.add(reloadedMarketArea);
        }
        List<HeadLinkViewBean> alternateLinks = new ArrayList<HeadLinkViewBean>();
        try {
            for (MarketArea marketAreaIt : allMarketArea) {
                for (Localization localizationIt : marketAreaIt.getLocalizations()) {
                    if(!marketAreaIt.getCode().equals(marketArea.getCode())){
    
                        RequestData requestDataStore = new RequestData();
                        requestDataStore.setMarketPlace(marketPlace);
                        requestDataStore.setMarket(marketAreaIt.getMarket());
                        requestDataStore.setMarketArea(marketAreaIt);
                        requestDataStore.setMarketAreaLocalization(localizationIt);
                        requestDataStore.setContextNameValue("FO_MCOMMERCE");
                        
                        String alternateUrl = urlService.buildAbsoluteUrl(requestDataStore, urlService.generateUrl(url, requestDataStore));
                        String hreflang = localizationIt.getCode().replace("_", "-");
                        if(marketAreaIt.getCode().equals("INT") && localizationIt.getCode().equals("en")){
                            hreflang = "x-default";
                        }
                        HeadLinkViewBean metaLink = new HeadLinkViewBean("alternate", hreflang, alternateUrl);
                        alternateLinks.add(metaLink);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return alternateLinks;
    }
    
}