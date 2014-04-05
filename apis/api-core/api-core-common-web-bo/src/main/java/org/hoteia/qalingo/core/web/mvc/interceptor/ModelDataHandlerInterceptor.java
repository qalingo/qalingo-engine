package org.hoteia.qalingo.core.web.mvc.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.LocalizationService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.web.mvc.factory.BackofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ModelDataHandlerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BackofficeViewBeanFactory backofficeViewBeanFactory;

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected MarketService marketService;
    
    @Autowired
    protected LocalizationService localizationService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                             HttpServletResponse response, Object handler) throws Exception {
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
            modelAndView.getModelMap().put(ModelConstants.COMMON_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanCommon(requestData));
            
            final MarketPlace currentMarketPlace = requestData.getMarketPlace();
            final Market currentMarket = requestData.getMarket();
            final MarketArea currentMarketArea = requestData.getMarketArea();
            final Localization currentLocalization = requestData.getMarketAreaLocalization();
            final User user = requestData.getUser();
            final Company company = requestData.getCompany();
            
            if(user != null){
                modelAndView.getModelMap().put(ModelConstants.AUTHENTICATED_USER_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanUser(requestData, user));
            }
            
            modelAndView.getModelMap().put(ModelConstants.LEGAl_TERMS_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanLegalTerms(requestData));
            
            modelAndView.getModelMap().put(ModelConstants.HEADER_CART, backofficeViewBeanFactory.buildViewBeanHeaderCart(requestData));
            
            // ALL MARKETPLACES
            modelAndView.getModelMap().put(ModelConstants.MARKET_PLACES_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanMarketPlace(requestData));

            // MARKETS FOR THE CURRENT MARKETPLACE
            Set<Market> marketList = currentMarketPlace.getMarkets();
            modelAndView.getModelMap().put(ModelConstants.MARKETS_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanMarket(requestData, currentMarketPlace, new ArrayList<Market>(marketList)));

            // MARKET AREAS FOR THE CURRENT MARKET
            Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREAS_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanMarketArea(requestData, currentMarket, new ArrayList<MarketArea>(marketAreaList)));

            // LOCALIZATIONS FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_LANGUAGES_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanLocalizationByMarketArea(requestData, currentLocalization));

            // RETAILERS FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_RETAILERS_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanRetailerByMarketArea(requestData));

            // CURRENCIES FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_CURRENCIES_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanCurrenciesByMarketArea(requestData));

            // CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanMarketArea(requestData, currentMarketArea));

            // BACKOFFICE LOCALIZATIONS
            List<Localization> backofficeLocalizations = new ArrayList<Localization>();
            if(company != null){
                backofficeLocalizations = new ArrayList<Localization>(company.getLocalizations());
            } else {
                backofficeLocalizations.add(localizationService.getLocalizationByCode("en"));
                backofficeLocalizations.add(localizationService.getLocalizationByCode("fr"));
            }
            modelAndView.getModelMap().put(ModelConstants.BACKOFFICE_LOCALIZATION_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanLocalization(requestData, backofficeLocalizations));
            
            // HEADER
            modelAndView.getModelMap().put(ModelConstants.MENUS_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanMenu(requestData));
            modelAndView.getModelMap().put(ModelConstants.MORE_PAGE_MENUS_VIEW_BEAN, backofficeViewBeanFactory.buildListViewBeanMorePageMenu(requestData));
            
            // FOOTER
            modelAndView.getModelMap().put(ModelConstants.FOOTER_MENUS_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanFooterMenu(requestData));
            
        } catch (Exception e) {
            logger.error("inject common datas failed", e);
        }
        
    }

}