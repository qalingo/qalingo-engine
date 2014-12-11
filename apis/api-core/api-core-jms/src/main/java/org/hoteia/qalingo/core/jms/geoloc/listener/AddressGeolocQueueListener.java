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
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hoteia.qalingo.core.domain.GeolocAddress;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.Retailer_;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
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
    
    protected List<SpecificFetchMode> retailerFetchPlans = new ArrayList<SpecificFetchMode>();
    
    public AddressGeolocQueueListener() {
        retailerFetchPlans.add(new SpecificFetchMode(Retailer_.addresses.getName()));
    }

    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String valueJMSMessage = tm.getText();
                
                if(StringUtils.isNotEmpty(valueJMSMessage)){
                    final AddressGeolocMessageJms documentMessageJms = xmlMapper.getXmlMapper().readValue(valueJMSMessage, AddressGeolocMessageJms.class);
                    
                    String address = documentMessageJms.getAddress();
                    String postalCode = documentMessageJms.getPostalCode();
                    String city = documentMessageJms.getCity();
                    String countryCode = documentMessageJms.getCountryCode();

                    String formatedAddress = geolocService.encodeGoogleAddress(address, postalCode, city, countryCode);

                    if("Store".equals(documentMessageJms.getObjectType())){
                        final Store store = retailerService.getStoreById(documentMessageJms.getObjectId());
                        if(store != null
                                && StringUtils.isEmpty(store.getLatitude())){
                            GeolocAddress geolocAddress = geolocService.getGeolocAddressByFormatedAddress(formatedAddress);
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
                    } else if("Retailer".equals(documentMessageJms.getObjectType())){
                        final Retailer retailer = retailerService.getRetailerById(documentMessageJms.getObjectId(), new FetchPlan(retailerFetchPlans));
                        RetailerAddress retailerAddress = retailer.getAddressByValue(address);
                        if(retailer != null
                                && retailerAddress != null
                                && StringUtils.isEmpty(retailerAddress.getLatitude())){
                            GeolocAddress geolocAddress = geolocService.getGeolocAddressByFormatedAddress(formatedAddress);
                            if (geolocAddress != null
                                    && StringUtils.isNotEmpty(geolocAddress.getLatitude())
                                    && StringUtils.isNotEmpty(geolocAddress.getLongitude())) {
                                retailerAddress.setLatitude(geolocAddress.getLatitude());
                                retailerAddress.setLongitude(geolocAddress.getLongitude());
                                retailerService.saveOrUpdateRetailer(retailer);
                            } else {
                                // LATITUDE/LONGITUDE DOESN'T EXIST - WE USE GOOGLE GEOLOC TO FOUND IT
                                geolocAddress = geolocService.geolocByAddress(address, postalCode, city, countryCode);
                                if (geolocAddress != null
                                        && StringUtils.isNotEmpty(geolocAddress.getLatitude())
                                        && StringUtils.isNotEmpty(geolocAddress.getLongitude())) {
                                    retailerAddress.setLatitude(geolocAddress.getLatitude());
                                    retailerAddress.setLongitude(geolocAddress.getLongitude());
                                    retailerService.saveOrUpdateRetailer(retailer);
                                }
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