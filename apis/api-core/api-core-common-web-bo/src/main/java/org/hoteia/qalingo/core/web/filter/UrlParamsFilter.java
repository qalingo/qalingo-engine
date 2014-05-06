/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import org.hoteia.qalingo.core.web.util.RequestUtil;

public class UrlParamsFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected ApplicationContext ctx;
    protected FilterConfig filterConfig;

    /**
     * Processes the given httpServletRequest and/or response.
     *
     * @param httpServletRequest The httpServletRequest
     * @param response The response
     * @param chain The processing chain
     * @throws IOException If an error occurs
     * @throws ServletException If an error occurs
     */
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {
    	final HttpServletRequest  httpServletRequest = (HttpServletRequest) servletRequest;
		
    	RequestUtil requestUtil = (RequestUtil) ctx.getBean("requestUtil");
    	
    	try {
			requestUtil.handleBackofficeUrlParameters(httpServletRequest);
		} catch (Exception e) {
			logger.error("UrlParams Filter & HandleUrlParameters failed, e");
		}
		
        // pass the servletRequest on
        chain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Initializes this filter.
     *
     * @param filterConfig The filter configuration
     * @throws ServletException If an error occurs
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
		final ServletContext context = filterConfig.getServletContext();
		if(ctx == null){
			this.ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		}
    }

    /**
     * Destroys this filter.
     */
    public void destroy() {
    }
    
}