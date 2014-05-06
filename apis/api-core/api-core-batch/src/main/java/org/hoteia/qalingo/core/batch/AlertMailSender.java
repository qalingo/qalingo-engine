/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * 
 */
public class AlertMailSender {

    private final Logger logger = LoggerFactory.getLogger(getClass());
	
    private MailSender mailSender;
    
    private SimpleMailMessage templateMessage;

    private String urlPrefix;
    
    private String emailTo;
    
    public void alertEmail(StepExecution stepExecution) {
        try {
        	
        	String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
        	String jobInstanceId = "" + stepExecution.getJobExecution().getJobInstance().getId();
        	String jobExecutionId = "" + stepExecution.getJobExecution().getId();
        	String stepExecutionId = "" + stepExecution.getId();
        	
            SimpleMailMessage message = new SimpleMailMessage(this.templateMessage);
            message.setTo(emailTo);
            StringBuffer messageText = new StringBuffer();
            messageText.append("\n\n");
            messageText.append("This job fail: " + jobName + "\n");
            messageText.append("\n\n");
            messageText.append("## Job");
            messageText.append("\n\n");
            messageText.append("Name: " + jobName + "");
            messageText.append("\n");
            messageText.append("Id: " + jobInstanceId + "");
            messageText.append("\n");
            messageText.append("Job instance list url: " + urlPrefix + "jobs/" + jobName);
            messageText.append("\n\n");
            messageText.append("## Job Execution");
            messageText.append("\n\n");
            messageText.append("Job execution list url: " + urlPrefix + "jobs/" + jobName + "/" + jobInstanceId);
            messageText.append("\n\n");
            messageText.append("## Step Error Details");
            messageText.append("\n\n");
            messageText.append("Job execution url: " + urlPrefix + "jobs/executions/" + jobExecutionId + "");
            messageText.append("\n\n");
            messageText.append("Job error details url: " + urlPrefix + "jobs/executions/" + jobExecutionId + "/steps/" + stepExecutionId + "/progress");
            messageText.append("\n\n");
            messageText.append("## Step Summary");
            messageText.append("\n\n");
            messageText.append("" + stepExecution.getSummary());
            message.setText(messageText.toString());
            this.mailSender.send(message);

        } catch(MailException e) {
        	logger.error("erro with the email MailSender", e); 
        }
    }
    
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    
    public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

    public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
}
