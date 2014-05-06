/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class TrackingViewBean implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -976850377460914001L;
    
	protected String trackingNumber;
	protected String trackingName;
	
	public String getTrackingNumber() {
	    return trackingNumber;
    }
	
	public void setTrackingNumber(String trackingNumber) {
	    this.trackingNumber = trackingNumber;
    }

	public String getTrackingName() {
    	return trackingName;
    }

	public void setTrackingName(String trackingName) {
    	this.trackingName = trackingName;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((trackingName == null) ? 0 : trackingName.hashCode());
	    result = prime * result + ((trackingNumber == null) ? 0 : trackingNumber.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    TrackingViewBean other = (TrackingViewBean) obj;
	    if (trackingName == null) {
		    if (other.trackingName != null)
			    return false;
	    } else if (!trackingName.equals(other.trackingName))
		    return false;
	    if (trackingNumber == null) {
		    if (other.trackingNumber != null)
			    return false;
	    } else if (!trackingNumber.equals(other.trackingNumber))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "TrackingViewBean [trackingNumber=" + trackingNumber + ", trackingName=" + trackingName + "]";
    }
	
}