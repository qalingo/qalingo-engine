package org.hoteia.qalingo.core.web.resolver;

import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component(value="requestDataResolver")
public class RequestDataResolver implements HandlerMethodArgumentResolver {
 
    @Autowired
    protected RequestUtil requestUtil;
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }
 
    @Override
    public Object resolveArgument(MethodParameter parameter, 
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, 
                                  WebDataBinderFactory binderFactory) throws Exception {
        return null;
    }
}