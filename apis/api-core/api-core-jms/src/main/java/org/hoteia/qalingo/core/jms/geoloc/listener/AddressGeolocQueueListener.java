/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.jms.geoloc.listener;

import java.beans.ExceptionListener;
import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hoteia.qalingo.core.domain.GeolocAddress;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.jms.geoloc.producer.AddressGeolocMessageJms;
import org.hoteia.qalingo.core.mapper.XmlMapper;
import org.hoteia.qalingo.core.service.GeolocService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "addressGeolocQueueListener")
public class AddressGeolocQueueListener implements MessageListener, ExceptionListener {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected XmlMapper xmlMapper;
    
    @Autowired
    protected RetailerService retailerService;
    
    @Autowired
    protected GeolocService geolocService;
    
    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String valueJMSMessage = tm.getText();
                
                if(StringUtils.isNotEmpty(valueJMSMessage)){
                    final AddressGeolocMessageJms doucmentMessageJms = xmlMapper.getXmlMapper().readValue(valueJMSMessage, AddressGeolocMessageJms.class);
                    
                    String address = doucmentMessageJms.getAddress();
                    String postalCode = doucmentMessageJms.getPostalCode();
                    String city = doucmentMessageJms.getCity();
                    String countryCode = doucmentMessageJms.getCountryCode();

                    final Store store = retailerService.getStoreById(doucmentMessageJms.getStoreId());
                    if(store != null){
                        GeolocAddress geolocAddress = geolocService.getGeolocAddressByFormatedAddress(address);
                        if (geolocAddress != null
                                && StringUtils.isNotEmpty(geolocAddress.getLatitude())
                                && StringUtils.isNotEmpty(geolocAddress.getLongitude())) {
                            store.setLatitude(geolocAddress.getLatitude());
                            store.setLongitude(geolocAddress.getLongitude());
                            retailerService.saveOrUpdateStore(store);
                        } else {
                            // LATITUDE/LONGITUDE DOESN'T EXIST - WE USE GOOGLE GEOLOC TO FOUND IT
                            geolocAddress = geolocService.geolocByAddress(address, postalCode, city, countryCode);
                            if (geolocAddress != null
                                    && StringUtils.isNotEmpty(geolocAddress.getLatitude())
                                    && StringUtils.isNotEmpty(geolocAddress.getLongitude())) {
                                store.setLatitude(geolocAddress.getLatitude());
                                store.setLongitude(geolocAddress.getLongitude());
                                retailerService.saveOrUpdateStore(store);
                            }
                        }
                    }
                    
                    if (logger.isDebugEnabled()) {
                        logger.debug("Processed message, value: " + valueJMSMessage);
                    }
                } else {
                    logger.warn("Document generation: Jms Message is empty");
                }
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void exceptionThrown(Exception e) {
        logger.debug("Exception on queue listener: " + e.getCause() + ":" + e.getLocalizedMessage());
    }

}