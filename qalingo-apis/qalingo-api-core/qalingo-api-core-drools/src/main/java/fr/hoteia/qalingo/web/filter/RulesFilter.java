/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import fr.hoteia.qalingo.core.domain.EngineEcoSession;
import fr.hoteia.qalingo.core.rule.util.RuleUtil;
import fr.hoteia.qalingo.core.web.util.RequestUtil;

public class RulesFilter implements Filter {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

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
		
    	RuleUtil ruleUtil = (RuleUtil) ctx.getBean("ruleUtil");
    	RequestUtil requestUtil = (RequestUtil) ctx.getBean("requestUtil");
    	
		List<Object> objects = new ArrayList<Object>();
		try {
			EngineEcoSession engineEcoSession = requestUtil.getCurrentEcoSession(httpServletRequest);
			objects.add(engineEcoSession);
		} catch (Exception e) {
			LOG.error("Failed to load EngineEcoSession from Request", e);
		}
    	ruleUtil.handleRuleSession(objects);
		
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