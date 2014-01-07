package org.hoteia.qalingo.core.web.mvc.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.EngineSessionService;
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
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                             HttpServletResponse response, Object handler) throws Exception {
        // SAVE COOKIE VALUE FOR ENGINE SESSION GUID
        EngineEcoSession engineEcoSession = requestUtil.getCurrentEcoSession(request);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie ecoEngineSessionGuid = null;
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (Constants.COOKIE_ECO_ENGINE_SESSION_ID.equals(cookie.getName())) {
                    cookie.setValue(engineEcoSession.getEngineSessionGuid());
                    break;
                }
            }
            if(ecoEngineSessionGuid == null){
                ecoEngineSessionGuid = new Cookie(Constants.COOKIE_ECO_ENGINE_SESSION_ID, engineEcoSession.getEngineSessionGuid());
                ecoEngineSessionGuid.setMaxAge(Constants.COOKIES_LENGTH);
                ecoEngineSessionGuid.setPath("/");
                ecoEngineSessionGuid.setDomain("fo-mcommerce.dev.qalingo.com");
                ecoEngineSessionGuid.setSecure(true);
                response.addCookie(ecoEngineSessionGuid);
            }
        }
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
            
            modelAndView.getModelMap().put(ModelConstants.COMMON_VIEW_BEAN, frontofficeViewBeanFactory.buildCommonViewBean(requestData));
            
            final MarketPlace currentMarketPlace = requestData.getMarketPlace();
            final Market currentMarket = requestData.getMarket();
            final MarketArea currentMarketArea = requestData.getMarketArea();
            final Localization currentLocalization = requestData.getMarketAreaLocalization();
            final Customer customer = requestData.getCustomer();
            if(customer != null){
                modelAndView.getModelMap().put(ModelConstants.CUSTOMER_VIEW_BEAN, frontofficeViewBeanFactory.buildCustomerViewBean(requestData, customer));
            }
            
            modelAndView.getModelMap().put(ModelConstants.LEGAl_TERMS_VIEW_BEAN, frontofficeViewBeanFactory.buildLegalTermsViewBean(requestData));
            
            modelAndView.getModelMap().put(ModelConstants.CONDITIONS_OF_USE_VIEW_BEAN, frontofficeViewBeanFactory.buildConditionsViewBean(requestData));
            
            modelAndView.getModelMap().put(ModelConstants.HEADER_CART, frontofficeViewBeanFactory.buildHeaderCartViewBean(requestData));
            
            // ALL MARKETPLACES
            modelAndView.getModelMap().put(ModelConstants.MARKET_PLACES_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketPlaceViewBeans(requestData));
            
            // MARKETS FOR THE CURRENT MARKETPLACE
            Set<Market> marketList = currentMarketPlace.getMarkets();
            modelAndView.getModelMap().put(ModelConstants.MARKETS_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketViewBeans(requestData, currentMarketPlace, new ArrayList<Market>(marketList)));

            // MARKET AREAS FOR THE CURRENT MARKET
            Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREAS_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketAreaViewBeans(requestData, currentMarket, new ArrayList<MarketArea>(marketAreaList)));

            // CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketAreaViewBean(requestData, currentMarketArea));

            // LOCALIZATIONS FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_LANGUAGES_VIEW_BEAN, frontofficeViewBeanFactory.buildLocalizationViewBeansByMarketArea(requestData, currentLocalization));

            // RETAILERS FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_RETAILERS_VIEW_BEAN, frontofficeViewBeanFactory.buildRetailerViewBeansByMarketArea(requestData));

            // CURRENCIES FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_CURRENCIES_VIEW_BEAN, frontofficeViewBeanFactory.buildCurrenciesViewBeansByMarketArea(requestData));

            // HEADER
            modelAndView.getModelMap().put(ModelConstants.MENUS_VIEW_BEAN, frontofficeViewBeanFactory.buildMenuViewBeans(requestData));
            
            // FOOTER
            modelAndView.getModelMap().put(ModelConstants.FOOTER_MENUS_VIEW_BEAN, frontofficeViewBeanFactory.buildFooterMenuViewBeans(requestData));

            final List<CatalogCategoryViewBean> catalogCategoryViewBeans = frontofficeViewBeanFactory.buildListRootCatalogCategories(requestUtil.getRequestData(request), currentMarketArea);
            modelAndView.getModelMap().put(ModelConstants.CATALOG_CATEGORIES_VIEW_BEAN, catalogCategoryViewBeans);

        } catch (Exception e) {
            logger.error("inject common datas failed", e);
        }
        
    }

}