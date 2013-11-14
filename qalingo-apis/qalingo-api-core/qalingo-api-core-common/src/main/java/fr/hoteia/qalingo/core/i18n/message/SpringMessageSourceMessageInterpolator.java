/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.i18n.message;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

public class SpringMessageSourceMessageInterpolator implements MessageInterpolator, MessageSourceAware, InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(SpringMessageSourceMessageInterpolator.class);

	private CoreMessageSource coreMessageSource;
	private MessageSource messageSource;
	
	@Override
	public String interpolate(String messageTemplate, Context context) {
		String message = "";
		try {
			message = coreMessageSource.getMessage(messageTemplate, new Object[] {}, Locale.getDefault());
		} catch (Exception e) {
			LOG.error("this key doesn't exist: " + messageTemplate);
		}
		return message;
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		String message = "";
		try {
			message = coreMessageSource.getMessage(messageTemplate, new Object[] {}, locale);
		} catch (Exception e) {
			LOG.error("this key doesn't exist: " + messageTemplate);
		}
		return message;
	}

	public void setCoreMessageSource(CoreMessageSource coreMessageSource) {
		this.coreMessageSource = coreMessageSource;
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (coreMessageSource == null) {
			throw new IllegalStateException(
					"MessageSource was not injected, could not initialize "
							+ this.getClass().getSimpleName());
		}
	}

}