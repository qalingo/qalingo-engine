package fr.hoteia.qalingo.core.jms.syncserveur.listener;

import java.beans.ExceptionListener;
import java.io.IOException;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.ServerStatus;
import fr.hoteia.qalingo.core.service.ServerService;

@Component(value = "syncServeurStatusQueueListener")
public class SyncServeurStatusQueueListener implements MessageListener, ExceptionListener {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected ServerService serverService;
    
    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String valueJMSMessage = tm.getText();
                
                ServerStatus serverStatus = new ServerStatus();
                serverStatus.setLastCheckReceived(new Date());
                serverStatus.setServerName("");
                serverStatus.setServerIp("");
                
                serverService.saveOrUpdateServerStatus(serverStatus, valueJMSMessage);
                
                if (logger.isDebugEnabled()) {
                    logger.debug("Processed message, value: " + valueJMSMessage);
                }
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    @Override
    public void exceptionThrown(Exception e) {
        logger.debug("Exception on queue listener: " + e.getCause() + ":" + e.getLocalizedMessage());
    }

}