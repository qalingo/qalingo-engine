/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.i18n.message;

import java.util.Locale;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.Constants;

@Service("coreMessageSource")
public class CoreMessageSource implements MessageSource {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(String code, Object args[], String defaultMessage, Locale locale) {
		try {
			return messageSource.getMessage(code, args, defaultMessage, locale);
		} catch (Exception e) {
			LOG.error("This message key doesn't exist: " + code + ", for this locale: " + locale.toString());
			if(BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))){
				return getMessage(code, args, defaultMessage, new Locale(Constants.DEFAULT_LOCALE_CODE));
			}
		}
		return null;
	}

	public String getMessage(String code, Object args[], Locale locale) throws NoSuchMessageException {
		try {
			return messageSource.getMessage(code, args, locale);
		} catch (Exception e) {
			LOG.error("This message key doesn't exist: " + code + ", for this locale: " + locale.toString());
			if(BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))){
				return getMessage(code, args, new Locale(Constants.DEFAULT_LOCALE_CODE));
			}
		}
		return null;
	}

	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		try {
			return messageSource.getMessage(resolvable, locale);
		} catch (Exception e) {
			LOG.error("This message key doesn't exist: " + resolvable.getCodes() + ", for this locale: " + locale.toString());
			if(BooleanUtils.negate(locale.toString().equalsIgnoreCase(Constants.DEFAULT_LOCALE_CODE))){
				return getMessage(resolvable, new Locale(Constants.DEFAULT_LOCALE_CODE));
			}
		}
		return null;
	}

}
