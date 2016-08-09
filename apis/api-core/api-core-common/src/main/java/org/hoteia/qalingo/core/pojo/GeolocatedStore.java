/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo;


public class GeolocatedStore extends AbstractPojo<GeolocatedStore> {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String code;
    private Double distance;

    public GeolocatedStore() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDistance() {
		return distance;
	}
    
    public void setDistance(Double distance) {
		this.distance = distance;
	}
    
}