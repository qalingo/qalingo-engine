/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.openid;

import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * 
 */
public class Association {

    public static final String SESSION_TYPE_NO_ENCRYPTION = "no-encryption";
    public static final String ASSOC_TYPE_HMAC_SHA1 = "HMAC-SHA1";

    private String sessionType;
    private String associationType;
    private String associationHandle;
    private String macKey;
    private byte[] rawMacKey;
    private long expired;

    public String getSessionType() {
    	return sessionType;
    }

	public void setSessionType(String sessionType) {
    	this.sessionType = sessionType;
    }

	public String getAssociationType() {
    	return associationType;
    }

	public void setAssociationType(String associationType) {
    	this.associationType = associationType;
    }

	public String getAssociationHandle() {
    	return associationHandle;
    }

	public void setAssociationHandle(String associationHandle) {
    	this.associationHandle = associationHandle;
    }

	public String getMacKey() {
    	return macKey;
    }

    public void setMacKey(String mac_key) {
        this.macKey = mac_key;
        this.rawMacKey = Base64.decode(mac_key);
    }

    public byte[] getRawMacKey() {
        return rawMacKey;
    }
    
    public void setRawMacKey(byte[] rawMacKey) {
	    this.rawMacKey = rawMacKey;
    }

    public void setMaxAge(long maxAgeInMilliseconds) {
        this.expired = System.currentTimeMillis() + maxAgeInMilliseconds;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expired;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((associationHandle == null) ? 0 : associationHandle.hashCode());
	    result = prime * result + ((associationType == null) ? 0 : associationType.hashCode());
	    result = prime * result + (int) (expired ^ (expired >>> 32));
	    result = prime * result + ((macKey == null) ? 0 : macKey.hashCode());
	    result = prime * result + Arrays.hashCode(rawMacKey);
	    result = prime * result + ((sessionType == null) ? 0 : sessionType.hashCode());
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
	    Association other = (Association) obj;
	    if (associationHandle == null) {
		    if (other.associationHandle != null)
			    return false;
	    } else if (!associationHandle.equals(other.associationHandle))
		    return false;
	    if (associationType == null) {
		    if (other.associationType != null)
			    return false;
	    } else if (!associationType.equals(other.associationType))
		    return false;
	    if (expired != other.expired)
		    return false;
	    if (macKey == null) {
		    if (other.macKey != null)
			    return false;
	    } else if (!macKey.equals(other.macKey))
		    return false;
	    if (!Arrays.equals(rawMacKey, other.rawMacKey))
		    return false;
	    if (sessionType == null) {
		    if (other.sessionType != null)
			    return false;
	    } else if (!sessionType.equals(other.sessionType))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "Association [sessionType=" + sessionType + ", associationType=" + associationType + ", associationHandle=" + associationHandle + ", macKey=" + macKey + ", rawMacKey="
	            + Arrays.toString(rawMacKey) + ", expired=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expired) + "]";
    }

}