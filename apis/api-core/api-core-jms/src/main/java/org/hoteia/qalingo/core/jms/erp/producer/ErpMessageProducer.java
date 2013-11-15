package org.hoteia.qalingo.core.jms.erp.producer;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component(value = "erpMessageProducer")
public class ErpMessageProducer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="erpJmsTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * Generates JMS messages
     * 
     */
    public void generateAndSendMessages(final String valueJMSMessage) {
        try {
            jmsTemplate.send( new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage(valueJMSMessage);
                    if (logger.isDebugEnabled()) {
                        logger.info("Sending JMS message: " + valueJMSMessage);
                    }
                    return message;
                }
            });
        } catch (Exception e) {
            logger.error("Exception during create/send message process");
        }
    }

}