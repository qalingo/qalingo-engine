/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.geoloc;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.domain.GeolocAddress;
import org.hoteia.qalingo.core.domain.GeolocCity;
import org.hoteia.qalingo.core.jms.geoloc.producer.AddressGeolocMessageJms;
import org.hoteia.qalingo.core.jms.geoloc.producer.AddressGeolocMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "geolocAspect")
public class GeolocAspect {

    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private AddressGeolocMessageProducer addressGeolocMessageProducer;
    
    @Value("${env.name}")  
    protected String environmentName;
    
    @Value("${env.id}")  
    protected String environmentId;
    
    @Value("${app.name}")  
    protected String applicationName;
    
    public void before(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("EmailNotificationAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("EmailNotificationAspect, afterReturning");
        }
        try {
            final AddressGeolocMessageJms addressGeolocMessageJms = new AddressGeolocMessageJms();
            addressGeolocMessageJms.setEnvironmentName(environmentName);
            addressGeolocMessageJms.setEnvironmentId(environmentId);
            addressGeolocMessageJms.setApplicationName(applicationName);
            addressGeolocMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            addressGeolocMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            if(result != null && result instanceof GeolocAddress){
                GeolocAddress geolocAddress = (GeolocAddress) result;
                addressGeolocMessageJms.setGeolocType("GeolocAddress");
            } else if(result != null && result instanceof GeolocCity){
                GeolocCity geolocCity = (GeolocCity) result;
                addressGeolocMessageJms.setGeolocType("GeolocCity");
            }
            
            // Generate and send the JMS message
            addressGeolocMessageProducer.generateMessages(addressGeolocMessageJms);
            
        } catch (Exception e) {
            logger.error("AddressGeolocAspect Target Object error: " + e);
        }
    }

}