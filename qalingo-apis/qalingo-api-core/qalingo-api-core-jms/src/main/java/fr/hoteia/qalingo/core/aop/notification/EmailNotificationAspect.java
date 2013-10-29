package fr.hoteia.qalingo.core.aop.notification;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.Email;
import fr.hoteia.qalingo.core.jms.notification.producer.EmailNotificationMessageProducer;
import fr.hoteia.qalingo.core.jms.notification.producer.EmailnotificationMessageJms;

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
        if(logger.isDebugEnabled()){
            logger.debug("EmailNotificationAspect, beforeRuning");
        }
    }

    public void afterRuning(StaticPart staticPart, Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("EmailNotificationAspect, afterRuning");
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