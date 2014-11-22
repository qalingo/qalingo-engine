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
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.jms.geoloc.producer.AddressGeolocMessageJms;
import org.hoteia.qalingo.core.jms.geoloc.producer.AddressGeolocMessageProducer;
import org.hoteia.qalingo.core.service.GeolocService;
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
    
    @Autowired
    protected GeolocService geolocService;
    
    public void before(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("GeolocAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("GeolocAspect, afterReturning");
        }
        try {
            final AddressGeolocMessageJms addressGeolocMessageJms = new AddressGeolocMessageJms();
            addressGeolocMessageJms.setEnvironmentName(environmentName);
            addressGeolocMessageJms.setEnvironmentId(environmentId);
            addressGeolocMessageJms.setApplicationName(applicationName);
            addressGeolocMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            addressGeolocMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            
            if(result != null && result instanceof Store){
                Store store = (Store) result;
                addressGeolocMessageJms.setObjectId(store.getId());
                addressGeolocMessageJms.setObjectType("Store");
                addressGeolocMessageJms.setGeolocType("GeolocAddress");
                addressGeolocMessageJms.setAddress(store.getAddress1());
                addressGeolocMessageJms.setPostalCode(store.getPostalCode());
                addressGeolocMessageJms.setCity(store.getCity());
                addressGeolocMessageJms.setCountryCode(store.getCountryCode());
            } else if(result != null && result instanceof Retailer){
                Retailer retailer = (Retailer) result;
                addressGeolocMessageJms.setObjectId(retailer.getId());
                addressGeolocMessageJms.setObjectType("Retailer");
                addressGeolocMessageJms.setGeolocType("GeolocAddress");
                addressGeolocMessageJms.setAddress(retailer.getDefaultAddress().getAddress1());
                addressGeolocMessageJms.setPostalCode(retailer.getDefaultAddress().getPostalCode());
                addressGeolocMessageJms.setCity(retailer.getDefaultAddress().getCity());
                addressGeolocMessageJms.setCountryCode(retailer.getDefaultAddress().getCountryCode());
            }
            
            // Generate and send the JMS message
            addressGeolocMessageProducer.generateMessages(addressGeolocMessageJms);
            
        } catch (Exception e) {
            logger.error("GeolocAspect Target Object error: " + e);
        }
    }

}