/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.quartz.syncserveur;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.hoteia.qalingo.core.jms.syncserveur.producer.SyncServeurMessageJms;
import org.hoteia.qalingo.core.jms.syncserveur.producer.SyncServeurStatusMessageProducer;

@Component(value = "syncServeurStatusTargetObject")
public class SyncServeurStatusTargetObject {
    
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private SyncServeurStatusMessageProducer syncServeurStatusQueueProducer;

    @Value("${env.name}")  
    protected String environmentName;
    
    @Value("${env.id}")  
    protected String environmentId;
    
    @Value("${app.name}")  
    protected String applicationName;
    
    public void syncServeurStatus(){
        if(logger.isDebugEnabled()){
            logger.debug("Begin, sync status");
        }

        try {
            final SyncServeurMessageJms syncServeurMessageJms = new SyncServeurMessageJms();
            syncServeurMessageJms.setEnvironmentName(environmentName);
            syncServeurMessageJms.setEnvironmentId(environmentId);
            syncServeurMessageJms.setApplicationName(applicationName);
            syncServeurMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            syncServeurMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            
            // Generate and send the JMS message
            syncServeurStatusQueueProducer.generateAndSendMessages(syncServeurMessageJms);
            
        } catch (UnknownHostException e) {
            logger.error("Exception during get ip/hostname server");
        }
        
    }
    
}
