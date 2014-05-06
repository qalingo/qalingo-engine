/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.notification;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.hoteia.qalingo.core.domain.Email;
import org.hoteia.qalingo.core.jms.notification.producer.EmailNotificationMessageProducer;
import org.hoteia.qalingo.core.jms.notification.producer.EmailnotificationMessageJms;

@Component(value = "emailNotificationAspect")
public class EmailNotificationAspect {

    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private EmailNotificationMessageProducer emailNotificationMessageProducer;
    
    @Value("${env.name}")  
    protected String environmentName;
    
    @Value("${env.id}")  
    protected String environmentId;
    
    @Value("${app.name}")  
    protected String applicationName;
    
    public void before(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("EmailNotificationAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("EmailNotificationAspect, afterReturning");
        }
        try {
            final Email email = (Email) result;
            
            final EmailnotificationMessageJms emailnotificationMessageJms = new EmailnotificationMessageJms();
            emailnotificationMessageJms.setEnvironmentName(environmentName);
            emailnotificationMessageJms.setEnvironmentId(environmentId);
            emailnotificationMessageJms.setApplicationName(applicationName);
            emailnotificationMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            emailnotificationMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            if(email != null){
                emailnotificationMessageJms.setEmailType(email.getType());
            }
            
            // Generate and send the JMS message
            emailNotificationMessageProducer.generateMessages(emailnotificationMessageJms);
            
        } catch (Exception e) {
            logger.error("EmailNotificationAspect Target Object error: " + e);
        }
    }

}