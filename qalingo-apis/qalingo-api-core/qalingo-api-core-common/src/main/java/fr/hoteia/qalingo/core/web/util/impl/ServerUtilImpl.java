/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.util.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.core.web.util.ServerUtil;

@Service(value="serverUtil")
@Transactional
public class ServerUtilImpl implements ServerUtil {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
    protected UrlService urlService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	public UrlService getUrlService() {
		return urlService;
	}
	
	public RequestUtil getRequestUtil() {
		return requestUtil;
	}
	
	public MessageSource getMessageSource() {
		return coreMessageSource;
	}

}
