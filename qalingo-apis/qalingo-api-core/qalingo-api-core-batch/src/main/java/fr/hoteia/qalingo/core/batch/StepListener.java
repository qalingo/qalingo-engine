/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 */
public class StepListener extends StepListenerSupport<Long, Long> implements InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

//	private AlertMailSender alertMailSender;

	public final void afterPropertiesSet() throws Exception {
//		Assert.notNull(alertMailSender, "You must provide a alertMailSender.");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
//		if(stepExecution.getStatus().equals(BatchStatus.FAILED)){
//			alertMailSender.alertEmail(stepExecution);
//			LOG.error("Step " + stepExecution.getStepName() + " failed, execution Id: " + stepExecution.getJobExecutionId(), 
//					stepExecution.toString());
//		}
		
		return super.afterStep(stepExecution);
	}
	
//	public void setAlertMailSender(AlertMailSender alertMailSender) {
//		this.alertMailSender = alertMailSender;
//	}

}