/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.pool;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.aop.target.CommonsPoolTargetSource;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * See http://blog.ippon.fr/2010/12/06/creer-et-monitorer-un-pool-dobjets-avec-spring/trackback/
 * 
 */
@ManagedResource
public class ManagedCommonsPoolTargetSource extends CommonsPoolTargetSource {

    private AtomicInteger numWaitingCallers = new AtomicInteger();

    @Override
    public Object getTarget() throws Exception {
        numWaitingCallers.incrementAndGet();

        try {
            return super.getTarget();
        } finally {
            numWaitingCallers.decrementAndGet();
        }
    }

    @ManagedAttribute
    public int getNumWaitingCallers() {
        return numWaitingCallers.get();
    }

    @Override
    @ManagedAttribute
    public int getActiveCount() {
        return super.getActiveCount();
    }

    @Override
    @ManagedAttribute
    public int getMaxSize() {
        return super.getMaxSize();
    }

}