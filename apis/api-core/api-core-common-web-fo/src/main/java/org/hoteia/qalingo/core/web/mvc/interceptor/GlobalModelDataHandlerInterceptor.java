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
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.EngineSessionService;
import org.hoteia.qalingo.core.service.GeolocService;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.util.PropertiesUtil;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class GlobalModelDataHandlerInterceptor implements HandlerInterceptor {

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
    
    @Autowired
	protected CoreMessageSource coreMessageSource;
    
    protected List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();

    public GlobalModelDataHandlerInterceptor() {
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.attributes.getName()));
    }
    
    @Override
    public boolean preHandle(final HttpServletRequest request, 
                             final HttpServletResponse response, Object handler) throws Exception {
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
        	final Locale locale = requestData.getLocale();
        	
            if(modelAndView != null){
                modelAndView.getModelMap().put(ModelConstants.COMMON_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanCommon(requestData));
                modelAndView.getModelMap().put(ModelConstants.SECURITY_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanSecurity(requestUtil.getRequestData(request)));

                modelAndView.getModelMap().put("requestScheme", request.getScheme());
                
                final Customer customer = requestData.getCustomer();
                if(customer != null){
                    modelAndView.getModelMap().put(ModelConstants.CUSTOMER_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanCustomer(requestData, customer));
                }
                
                if(requestData.getGeolocData() != null){
                    modelAndView.getModelMap().put("geolocData", requestData.getGeolocData());
                }
                
                String contextName = requestUtil.getContextName();
        		try {
        			String contextValue = PropertiesUtil.getWebappContextKey(contextName);
        			modelAndView.getModelMap().addAttribute(ModelConstants.WORDING, coreMessageSource.loadWordingByContext(contextValue, locale));
        	        
                } catch (Exception e) {
                	logger.error("Context name, " + contextName + " can't be resolve by EngineSettingWebAppContext class.", e);
                }
            }
            
        } catch (Exception e) {
            logger.error("Inject common datas failed", e);
        }
        
    }

}