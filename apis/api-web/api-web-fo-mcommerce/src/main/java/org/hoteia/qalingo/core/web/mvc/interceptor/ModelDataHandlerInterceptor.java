/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.EngineSessionService;
import org.hoteia.qalingo.core.service.GeolocService;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ModelDataHandlerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected FrontofficeViewBeanFactory frontofficeViewBeanFactory;

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected EngineSessionService engineSessionService;
    
    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    @Autowired
    protected GeolocService geolocService;
    
    protected List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();;

    public ModelDataHandlerInterceptor() {
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
    }
    
    @Override
    public boolean preHandle(final HttpServletRequest request, 
                             final HttpServletResponse response, Object handler) throws Exception {
        // SAVE COOKIE VALUE FOR ENGINE SESSION GUID
//        EngineEcoSession engineEcoSession = requestUtil.getCurrentEcoSession(request);
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            Cookie ecoEngineSessionGuid = null;
//            for (int i = 0; i < cookies.length; i++) {
//                Cookie cookie = cookies[i];
//                if (Constants.COOKIE_ECO_ENGINE_SESSION_ID.equals(cookie.getName())) {
//                    cookie.setValue(engineEcoSession.getEngineSessionGuid());
//                    break;
//                }
//            }
//            if(ecoEngineSessionGuid == null){
//                ecoEngineSessionGuid = new Cookie(Constants.COOKIE_ECO_ENGINE_SESSION_ID, engineEcoSession.getEngineSessionGuid());
//                ecoEngineSessionGuid.setMaxAge(Constants.COOKIES_LENGTH);
//                ecoEngineSessionGuid.setPath("/");
//                ecoEngineSessionGuid.setDomain("fo-mcommerce.dev.qalingo.com");
//                ecoEngineSessionGuid.setSecure(true);
//                response.addCookie(ecoEngineSessionGuid);
//            }
//        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception exception) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler, ModelAndView modelAndView) throws Exception {
        
        try {
            final RequestData requestData = requestUtil.getRequestData(request);
            
            modelAndView.getModelMap().put(ModelConstants.COMMON_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanCommon(requestData));
            
            final MarketPlace currentMarketPlace = requestData.getMarketPlace();
            final Market currentMarket = requestData.getMarket();
            final MarketArea currentMarketArea = requestData.getMarketArea();
            final Localization currentLocalization = requestData.getMarketAreaLocalization();
            final Customer customer = requestData.getCustomer();
            if(customer != null){
                modelAndView.getModelMap().put(ModelConstants.CUSTOMER_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanCustomer(requestData, customer));
            }
            
            modelAndView.getModelMap().put(ModelConstants.LEGAl_TERMS_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanLegalTerms(requestData));
            
            modelAndView.getModelMap().put(ModelConstants.HEADER_CART, frontofficeViewBeanFactory.buildViewBeanHeaderCart(requestData));
            
            // ALL MARKETPLACES
            modelAndView.getModelMap().put(ModelConstants.MARKET_PLACES_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanMarketPlace(requestData));
            
            // MARKETS FOR THE CURRENT MARKETPLACE
            Set<Market> marketList = currentMarketPlace.getMarkets();
            modelAndView.getModelMap().put(ModelConstants.MARKETS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanMarket(requestData, currentMarketPlace, new ArrayList<Market>(marketList)));

            // MARKET AREAS FOR THE CURRENT MARKET
            Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREAS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanMarketArea(requestData, currentMarket, new ArrayList<MarketArea>(marketAreaList)));

            // CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanMarketArea(requestData, currentMarketArea));

            // LOCALIZATIONS FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_LANGUAGES_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanLocalizationByMarketArea(requestData, currentLocalization));

            // RETAILERS FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_RETAILERS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanRetailerByMarketArea(requestData));

            // CURRENCIES FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_CURRENCIES_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanCurrenciesByMarketArea(requestData));

            // HEADER
            modelAndView.getModelMap().put(ModelConstants.MENUS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanMenu(requestData, new FetchPlan(categoryVirtualFetchPlans)));
            
            // FOOTER
            modelAndView.getModelMap().put(ModelConstants.FOOTER_MENUS_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanFooterMenu(requestData));

            final List<CatalogCategoryVirtual> virtualRootCategories = catalogCategoryService.findRootVirtualCatalogCategoriesByCatalogCode(currentMarketArea.getCatalog().getCode(), new FetchPlan(categoryVirtualFetchPlans));
            final List<CatalogCategoryViewBean> virtualRootCategoryViewBeans = frontofficeViewBeanFactory.buildListViewBeanRootCatalogCategory(requestUtil.getRequestData(request), virtualRootCategories, new FetchPlan(categoryVirtualFetchPlans), null, null);
            modelAndView.getModelMap().put(ModelConstants.CATALOG_CATEGORIES_VIEW_BEAN, virtualRootCategoryViewBeans);

            // GEOLOC
            if(requestData.getGeolocData() != null){
                modelAndView.getModelMap().put(ModelConstants.GEOLOC_REMOTE_ADDRESS, requestData.getGeolocData().getRemoteAddress());
                modelAndView.getModelMap().put(ModelConstants.GEOLOC_COUNTRY, requestData.getGeolocData().getCountry());
                modelAndView.getModelMap().put(ModelConstants.GEOLOC_CITY, requestData.getGeolocData().getCity());
            }
            
        } catch (Exception e) {
            logger.error("Inject common datas failed", e);
        }
        
    }

}