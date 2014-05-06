/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.factory.impl;

import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketAreaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketPlaceViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.springframework.beans.BeanUtils;

/**
 * 
 */
public class PrehomeViewBeanFactoryImpl extends FrontofficeViewBeanFactory {

    @Override
    public RetailerViewBean buildViewBeanRetailer(final RequestData requestData, final Retailer retailer) throws Exception {
        RetailerViewBean retailerViewBean = super.buildViewBeanRetailer(requestData, retailer);
        
        // CLONE THE CURRENT REQUEST DATE TO BUILD THE CHANGE CONTEXT URL (MENU)
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        String contextNameValue = EngineSettingWebAppContext.valueOf("FO_MCOMMERCE").getPropertyKey();
        requestDataChangecontext.setContextNameValue(contextNameValue);
        
        retailerViewBean.setHomeUrl(urlService.buildAbsoluteUrl(requestDataChangecontext, urlService.generateUrl(FoUrls.HOME, requestDataChangecontext)));
        return retailerViewBean;
    }
    
    @Override
    public MarketPlaceViewBean buildViewBeanMarketPlace(final RequestData requestData, final MarketPlace marketPlace) throws Exception {
        MarketPlaceViewBean marketPlaceViewBean = super.buildViewBeanMarketPlace(requestData, marketPlace);
        
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        String contextNameValue = EngineSettingWebAppContext.valueOf("FO_MCOMMERCE").getPropertyKey();
        requestDataChangecontext.setContextNameValue(contextNameValue);
        
        marketPlaceViewBean.setHomeUrl(urlService.buildAbsoluteUrl(requestDataChangecontext, urlService.generateUrl(FoUrls.HOME, requestDataChangecontext)));
        return marketPlaceViewBean;
    }
    
    @Override
    public MarketViewBean buildViewBeanMarket(final RequestData requestData, final Market market) throws Exception {
        MarketViewBean marketViewBean = super.buildViewBeanMarket(requestData, market);
        
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        String contextNameValue = EngineSettingWebAppContext.valueOf("FO_MCOMMERCE").getPropertyKey();
        requestDataChangecontext.setContextNameValue(contextNameValue);
        
        marketViewBean.setHomeUrl(urlService.buildAbsoluteUrl(requestDataChangecontext, urlService.generateUrl(FoUrls.HOME, requestDataChangecontext)));
        return marketViewBean;
    }
    
    @Override
    public MarketAreaViewBean buildViewBeanMarketArea(final RequestData requestData, final MarketArea marketArea) throws Exception {
        MarketAreaViewBean marketAreaViewBean = super.buildViewBeanMarketArea(requestData, marketArea);
        
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        String contextNameValue = EngineSettingWebAppContext.valueOf("FO_MCOMMERCE").getPropertyKey();
        requestDataChangecontext.setContextNameValue(contextNameValue);
        
        marketAreaViewBean.setHomeUrl(urlService.buildAbsoluteUrl(requestDataChangecontext, urlService.generateUrl(FoUrls.HOME, requestDataChangecontext)));
        return marketAreaViewBean;
    }
    
    @Override
    public LocalizationViewBean buildViewBeanLocalization(final RequestData requestData, final Localization localization) throws Exception {
        LocalizationViewBean localizationViewBean = super.buildViewBeanLocalization(requestData, localization);
        
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        String contextNameValue = EngineSettingWebAppContext.valueOf("FO_MCOMMERCE").getPropertyKey();
        requestDataChangecontext.setContextNameValue(contextNameValue);
        
        localizationViewBean.setHomeUrl(urlService.buildAbsoluteUrl(requestDataChangecontext, urlService.generateUrl(FoUrls.HOME, requestDataChangecontext)));
        return localizationViewBean;
    }

}