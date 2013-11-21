package org.hoteia.qalingo.core.web.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.rule.util.RuleUtil;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RuleEngineHandlerInterceptor implements HandlerInterceptor  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected RuleUtil ruleUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                             HttpServletResponse response, Object handler) throws Exception {
         
        List<Object> objects = new ArrayList<Object>();
        try {
            EngineEcoSession engineEcoSession = requestUtil.getCurrentEcoSession(request);
            objects.add(engineEcoSession);
        } catch (Exception e) {
            logger.error("Failed to load EngineEcoSession from Request", e);
        }
        ruleUtil.handleRuleSession(objects);
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception exception) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

}