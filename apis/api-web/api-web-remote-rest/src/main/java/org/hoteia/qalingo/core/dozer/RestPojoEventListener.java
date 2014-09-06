/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dozer;

import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "restPojoEventListener")
public class RestPojoEventListener implements DozerEventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    public RestPojoEventListener() {
    }

    @Override
    public void mappingStarted(DozerEvent event) {
        logger.debug("mapping started, SourceObject: " + event.getSourceObject());
    }

    @Override
    public void preWritingDestinationValue(DozerEvent event) {
        logger.debug("pre writing destination value, SourceObject: " + event.getSourceObject());
    }

    @Override
    public void postWritingDestinationValue(DozerEvent event) {
        logger.debug("post writing destination value, SourceObject: " + event.getSourceObject());
    }

    @Override
    public void mappingFinished(DozerEvent event) {
        logger.debug("mapping finished, SourceObject: " + event.getSourceObject());
    }
    
}