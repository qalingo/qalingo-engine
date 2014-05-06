/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan;

import java.io.Serializable;
import java.util.List;

public class FetchPlan implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -9219504676619760439L;
    
    protected List<SpecificFetchMode> fetchModes = null;
    
    public FetchPlan() {
    }
    
    public FetchPlan(List<SpecificFetchMode> fetchModes) {
        this.fetchModes = fetchModes;
    }
    
    public List<SpecificFetchMode> getFetchModes() {
        return fetchModes;
    }
    
    public void setFetchModes(List<SpecificFetchMode> fetchModes) {
        this.fetchModes = fetchModes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fetchModes == null) ? 0 : fetchModes.hashCode());
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
        FetchPlan other = (FetchPlan) obj;
        if (fetchModes == null) {
            if (other.fetchModes != null)
                return false;
        } else if (!fetchModes.equals(other.fetchModes))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FetchPlan [fetchModes=" + fetchModes + "]";
    }
    
}
