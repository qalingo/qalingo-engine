/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.handler.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;

@Component(value="accessDeniedHandler")
public class ExtAccessDeniedHandlerImpl extends AccessDeniedHandlerImpl {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
    protected BackofficeUrlService backofficeUrlService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		try {
			String url = backofficeUrlService.generateUrl(BoUrls.FORBIDDEN, requestUtil.getRequestData(request));
			setErrorPage(url);
		} catch (Exception e) {
			LOG.error("", e);
		}
		super.handle(request, response, accessDeniedException);
	}
	
}