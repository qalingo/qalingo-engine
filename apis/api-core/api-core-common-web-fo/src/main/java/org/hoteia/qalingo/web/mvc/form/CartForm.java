/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;

/**
 * 
 * 
 */
public class CartForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = -18080132978988541L;
    
	private String billingAddressId;
    private String shippingAddressId;
    
	public String getBillingAddressId() {
		return billingAddressId;
	}
	
	public void setBillingAddressId(String billingAddressId) {
		this.billingAddressId = billingAddressId;
	}
	
	public String getShippingAddressId() {
		return shippingAddressId;
	}
	
	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((billingAddressId == null) ? 0 : billingAddressId.hashCode());
	    result = prime * result + ((shippingAddressId == null) ? 0 : shippingAddressId.hashCode());
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
	    CartForm other = (CartForm) obj;
	    if (billingAddressId == null) {
		    if (other.billingAddressId != null)
			    return false;
	    } else if (!billingAddressId.equals(other.billingAddressId))
		    return false;
	    if (shippingAddressId == null) {
		    if (other.shippingAddressId != null)
			    return false;
	    } else if (!shippingAddressId.equals(other.shippingAddressId))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "CartForm [billingAddressId=" + billingAddressId + ", shippingAddressId=" + shippingAddressId + "]";
    }
    
}