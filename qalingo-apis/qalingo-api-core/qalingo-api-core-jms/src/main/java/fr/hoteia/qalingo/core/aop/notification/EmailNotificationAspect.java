package fr.hoteia.qalingo.core.aop.notification;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.jms.notification.producer.EmailNotificationMessageProducer;

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
    
    public void beforeRuning(JoinPoint joinPoint) {
        System.out.println("beforeRuning");
    }

    public void afterRuning(StaticPart staticPart, Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("EmailNotificationAspect, afterRuning");
        }
        try {
            // JMS message
            String message = environmentName + " : " + environmentId + "" + applicationName;
            emailNotificationMessageProducer.generateMessages(message);
            
        } catch (Exception e) {
            logger.error("EmailNotificationAspect Target Object error: " + e);
        }
    }

}
