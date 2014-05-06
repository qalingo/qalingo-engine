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
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 
 */
public class StepListener extends StepListenerSupport<Long, Long> implements InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private AlertMailSender alertMailSender;

	public final void afterPropertiesSet() throws Exception {
		Assert.notNull(alertMailSender, "You must provide a alertMailSender.");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		if(stepExecution.getStatus().equals(BatchStatus.FAILED)){
			alertMailSender.alertEmail(stepExecution);
			logger.error("Step " + stepExecution.getStepName() + " failed, execution Id: " + stepExecution.getJobExecutionId(), 
					stepExecution.toString());
		}
		
		return super.afterStep(stepExecution);
	}
	
	public void setAlertMailSender(AlertMailSender alertMailSender) {
		this.alertMailSender = alertMailSender;
	}

}