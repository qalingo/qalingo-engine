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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.wurfl.core.Device;

import org.apache.commons.lang.BooleanUtils;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestPlatformDeviceHandlerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
            RequestData requestData = requestUtil.getRequestData(request);
            Device device = requestData.getDevice();
            String deviceFolder = "default";
            if (device != null) {
                boolean isSmartPhone = BooleanUtils.toBoolean(device.getVirtualCapability("is_smartphone"));
                boolean isIPhoneOs = BooleanUtils.toBoolean(device.getVirtualCapability("is_iphone_os"));
                boolean isAndroid = BooleanUtils.toBoolean(device.getVirtualCapability("is_android"));
                if (isSmartPhone || isIPhoneOs || isAndroid) {
                    deviceFolder = "mobile";
                }
            }
            requestUtil.updateCurrentDevice(requestData, deviceFolder);

        } catch (Exception e) {
            logger.error("inject common datas failed", e);
        }
    }

}