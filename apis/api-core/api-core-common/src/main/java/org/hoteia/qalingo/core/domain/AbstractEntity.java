/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
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
