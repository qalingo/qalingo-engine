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

public class MonitoringViewBean implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -8457845951601852753L;
	
	protected String monitoringNumber;
	protected String monitoringName;
	
	public String getMonitoringNumber() {
	    return monitoringNumber;
    }
	
	public void setMonitoringNumber(String monitoringNumber) {
	    this.monitoringNumber = monitoringNumber;
    }
	
	public String getMonitoringName() {
	    return monitoringName;
    }
	
	public void setMonitoringName(String monitoringName) {
	    this.monitoringName = monitoringName;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((monitoringName == null) ? 0 : monitoringName.hashCode());
	    result = prime * result + ((monitoringNumber == null) ? 0 : monitoringNumber.hashCode());
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
	    MonitoringViewBean other = (MonitoringViewBean) obj;
	    if (monitoringName == null) {
		    if (other.monitoringName != null)
			    return false;
	    } else if (!monitoringName.equals(other.monitoringName))
		    return false;
	    if (monitoringNumber == null) {
		    if (other.monitoringNumber != null)
			    return false;
	    } else if (!monitoringNumber.equals(other.monitoringNumber))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "MonitoringViewBean [monitoringNumber=" + monitoringNumber + ", monitoringName=" + monitoringName + "]";
    }
	
}