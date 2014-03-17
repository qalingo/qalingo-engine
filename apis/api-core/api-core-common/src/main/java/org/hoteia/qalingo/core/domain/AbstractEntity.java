package org.hoteia.qalingo.core.domain;

import java.io.Serializable;

import javax.persistence.Transient;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;

public abstract class AbstractEntity implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -171297053896353957L;

    @Transient
    private FetchPlan fetchPlan;

    public FetchPlan getFetchPlan() {
        return fetchPlan;
    }
    
    public void setFetchPlan(FetchPlan fetchPlan) {
        this.fetchPlan = fetchPlan;
    }
    
}
