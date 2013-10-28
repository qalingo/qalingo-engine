package fr.hoteia.qalingo.core.quartz.syncserveur;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.jms.syncserveur.producer.SyncServeurStatusMessageProducer;

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
    
    @Value("${context.name}")  
    protected String contextName;
    
    public void syncServeurStatus(){
        if(logger.isDebugEnabled()){
            logger.debug("Begin, sync status");
        }

        try {
            // JMS message
            String message = environmentName + " : " + environmentId + "" + applicationName;
            syncServeurStatusQueueProducer.generateAndSendMessages(message);
            
        } catch (Exception e) {
            logger.error("SyncServeurStatus Target Object error: " + e);
        }
        
    }
    
}
