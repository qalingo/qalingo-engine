/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.document;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.enumtype.OrderDocumentType;
import org.hoteia.qalingo.core.domain.enumtype.OrderStatus;
import org.hoteia.qalingo.core.jms.document.producer.DocumentMessageProducer;
import org.hoteia.qalingo.core.jms.document.producer.GenerationDocumentMessageJms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "documentGeneratorAspect")
public class DocumentGeneratorAspect {

    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private DocumentMessageProducer documentMessageProducer;
    
    @Value("${env.name}")  
    protected String environmentName;
    
    @Value("${env.id}")  
    protected String environmentId;
    
    @Value("${app.name}")  
    protected String applicationName;
    
    public void before(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("DocumentGeneratorAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("DocumentGeneratorAspect, afterReturning");
        }
        try {
            final OrderCustomer order = (OrderCustomer) result;
            
            final GenerationDocumentMessageJms generationDocumentMessageJms = new GenerationDocumentMessageJms();
            generationDocumentMessageJms.setEnvironmentName(environmentName);
            generationDocumentMessageJms.setEnvironmentId(environmentId);
            generationDocumentMessageJms.setApplicationName(applicationName);
            generationDocumentMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            generationDocumentMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            if(order != null){
                generationDocumentMessageJms.setOrderId(order.getId());
                if(order.getStatus().equals(OrderStatus.ORDER_STATUS_PENDING.getPropertyKey())){
                    generationDocumentMessageJms.setDocumentType(OrderDocumentType.ORDER_CONFIRMATION.getPropertyKey());

                } else if(order.getStatus().equals(OrderStatus.ORDER_STATUS_SENDED.getPropertyKey())){
                    generationDocumentMessageJms.setDocumentType(OrderDocumentType.SHIPPING_CONFIRMATION.getPropertyKey());
                    
                }  else if(order.getStatus().equals(OrderStatus.ORDER_STATUS_CHARGED.getPropertyKey())){
                    generationDocumentMessageJms.setDocumentType(OrderDocumentType.SHIPPING_CONFIRMATION.getPropertyKey());
                    
                }
                
                // Generate and send the JMS message
                documentMessageProducer.generateAndSendMessages(generationDocumentMessageJms);
            }
            
        } catch (Exception e) {
            logger.error("DocumentGeneratorAspect Target Object error: " + e);
        }
    }

}