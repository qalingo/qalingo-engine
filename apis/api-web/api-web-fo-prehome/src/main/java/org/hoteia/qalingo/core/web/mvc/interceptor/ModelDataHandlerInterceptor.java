package org.hoteia.qalingo.core.web.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
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
            
            modelAndView.getModelMap().put(ModelConstants.COMMON_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanCommon(requestData));
            
            final Customer customer = requestData.getCustomer();
            if(customer != null){
                modelAndView.getModelMap().put(ModelConstants.CUSTOMER_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanCustomer(requestData, customer));
            }
            
            modelAndView.getModelMap().put(ModelConstants.LEGAl_TERMS_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanLegalTerms(requestData));
            
            modelAndView.getModelMap().put(ModelConstants.HEADER_CART, frontofficeViewBeanFactory.buildViewBeanHeaderCart(requestData));
            
        } catch (Exception e) {
            logger.error("inject common datas failed", e);
        }
        
    }

}