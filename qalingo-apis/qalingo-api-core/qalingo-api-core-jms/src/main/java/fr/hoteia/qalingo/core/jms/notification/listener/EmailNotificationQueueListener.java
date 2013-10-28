package fr.hoteia.qalingo.core.jms.notification.listener;

import java.beans.ExceptionListener;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "emailNotificationMessageListener")
public class EmailNotificationQueueListener implements MessageListener, ExceptionListener {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected JobLauncher jobLauncher;
    
    @Autowired
    protected Job emailSyncJob;
    
    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String valueJMSMessage = tm.getText();
                if (logger.isDebugEnabled()) {
                    logger.debug("Processed message, value: " + valueJMSMessage);
                }
            }
            
            // TRIGGER A BATCH TO PROCESS THE EMAIL
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
//            jobParametersBuilder.addString("inputFile", "file:./products.txt");
            jobParametersBuilder.addDate("date", new Date());
            JobParameters params = jobParametersBuilder.toJobParameters();
            jobLauncher.run(emailSyncJob, params);
            
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        } catch (JobExecutionAlreadyRunningException e) {
            logger.error(e.getMessage(), e);
        } catch (JobRestartException e) {
            logger.error(e.getMessage(), e);
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.error(e.getMessage(), e);
        } catch (JobParametersInvalidException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void exceptionThrown(Exception e) {
        logger.debug("Exception on queue listener: " + e.getCause() + ":" + e.getLocalizedMessage());
    }

}