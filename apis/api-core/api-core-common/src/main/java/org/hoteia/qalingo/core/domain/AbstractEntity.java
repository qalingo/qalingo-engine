package org.hoteia.qalingo.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public abstract class AbstractEntity implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -171297053896353957L;

    @Transient
    private List<SpecificFetchMode> fetchModes;

    public List<SpecificFetchMode> getFetchModes() {
        return fetchModes;
    }
    
    public void setFetchModes(List<SpecificFetchMode> fetchModes) {
        this.fetchModes = fetchModes;
    }
    
}
