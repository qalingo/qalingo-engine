package org.hoteia.qalingo.core.aop.document;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.jms.document.producer.DocumentMessageProducer;
import org.hoteia.qalingo.core.jms.document.producer.DoucmentMessageJms;
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
    
    public void beforeRuning(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("DocumentGeneratorAspect, beforeRuning");
        }
    }

    public void afterRuning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("DocumentGeneratorAspect, afterRuning");
        }
        try {
            final OrderCustomer order = (OrderCustomer) result;
            
            final DoucmentMessageJms doucmentMessageJms = new DoucmentMessageJms();
            doucmentMessageJms.setEnvironmentName(environmentName);
            doucmentMessageJms.setEnvironmentId(environmentId);
            doucmentMessageJms.setApplicationName(applicationName);
            doucmentMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            doucmentMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            if(order != null){
                doucmentMessageJms.setOrderId(order.getId());
                doucmentMessageJms.setDocumentType("OrderConfirmation");
            }
            
            // Generate and send the JMS message
            documentMessageProducer.generateAndSendMessages(doucmentMessageJms);
            
        } catch (Exception e) {
            logger.error("DocumentGeneratorAspect Target Object error: " + e);
        }
    }

}