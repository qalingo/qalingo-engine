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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobLauncherDetails extends QuartzJobBean {

	/**
	 * Special key in job data map for the name of a job to run.
	 */
	static public final String JOB_NAME = "jobName";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected JobLocator jobLocator;

	protected JobLauncher jobLauncher;

	protected DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss' 'z");
	
	/**
	 * Public setter for the {@link JobLocator}.
	 * @param jobLocator the {@link JobLocator} to set
	 */
	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	/**
	 * Public setter for the {@link JobLauncher}.
	 * @param jobLauncher the {@link JobLauncher} to set
	 */
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	@SuppressWarnings("unchecked")
	protected void executeInternal(JobExecutionContext context) {
		Map<String, Object> jobDataMap = context.getMergedJobDataMap();
		
		String jobName = (String) jobDataMap.get(JOB_NAME);
		logger.info("Quartz trigger firing with Spring Batch jobName=" + jobName);
		
		JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);
		try {
			jobLauncher.run(jobLocator.getJob(jobName), jobParameters);

		} catch (JobExecutionException e) {
		    logger.error("Could not execute job.", e);
		}
	}

	/**
	 * Copy parameters that are of the correct type over to
	 * {@link JobParameters}, ignoring jobName.
	 * 
	 * @return a {@link JobParameters} instance
	 */
	protected JobParameters getJobParametersFromJobMap(Map<String, Object> jobDataMap) {

		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addString("Exec ISO date", isoDateFormat.format(new Date()));  
		
		for (Entry<String, Object> entry : jobDataMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String 
					&& !key.equals(JOB_NAME)) {
				builder.addString(key, (String) value);
				
			} else if (value instanceof Float 
					|| value instanceof Double) {
				builder.addDouble(key, ((Number) value).doubleValue());
				
			} else if (value instanceof Integer 
					|| value instanceof Long) {
				builder.addLong(key, ((Number)value).longValue());
				
			} else if (value instanceof Date) {
				builder.addDate(key, (Date) value);
				
			} else {
			    logger.debug("JobDataMap contains values which are not job parameters (ignoring).");
			}
		}

		return builder.toJobParameters();
	}

}