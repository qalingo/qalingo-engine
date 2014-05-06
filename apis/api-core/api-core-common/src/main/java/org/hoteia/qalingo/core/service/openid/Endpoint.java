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

/**
 * 
 */
public final class Endpoint {

    static final String DEFAULT_ALIAS = "ext1";

    private final String url;
    private final String alias;
    private final long expired;

    public Endpoint(String url, String alias, long maxAgeInMilliSeconds) {
        if (url == null){
            throw new NullPointerException("Url is null.");
        }
        this.url = url;
        this.alias = alias;
        this.expired = System.currentTimeMillis() + maxAgeInMilliSeconds;
    }

    public String getUrl() {
        return url;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expired;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((alias == null) ? 0 : alias.hashCode());
	    result = prime * result + (int) (expired ^ (expired >>> 32));
	    result = prime * result + ((url == null) ? 0 : url.hashCode());
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
	    Endpoint other = (Endpoint) obj;
	    if (alias == null) {
		    if (other.alias != null)
			    return false;
	    } else if (!alias.equals(other.alias))
		    return false;
	    if (expired != other.expired)
		    return false;
	    if (url == null) {
		    if (other.url != null)
			    return false;
	    } else if (!url.equals(other.url))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "Endpoint [url=" + url + ", alias=" + alias + ", expired=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expired) + "]";
    }
    
}