/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.jms.geoloc.producer;

import org.hoteia.qalingo.core.jms.cacheserveurmanagement.listener.AbstractMessageJms;

public class AddressGeolocMessageJms extends AbstractMessageJms {

    private String geolocType;
    
    public String getGeolocType() {
        return geolocType;
    }
    
    public void setGeolocType(String geolocType) {
        this.geolocType = geolocType;
    }
    
}
