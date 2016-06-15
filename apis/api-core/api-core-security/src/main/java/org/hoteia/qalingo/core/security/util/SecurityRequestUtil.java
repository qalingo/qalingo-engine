/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.security.util;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.service.EngineSessionService;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("securityRequestUtil")
@Transactional
public class SecurityRequestUtil {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    static final String NUM = "0123456789";
    static final String SPL_CHARS = "!@#$%*";

	@Autowired
	private EngineSessionService engineSessionService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RequestUtil requestUtil;
	
	public void authenticationCustomer(final HttpServletRequest request, final Customer customer) {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword());
			token.setDetails(new WebAuthenticationDetails(request)); 
			Authentication authenticatedUser = authenticationManager.authenticate(token); 
	
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser); 
			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext()); 
		
			EngineEcoSession engineEcoSessionWithTransientValues = requestUtil.getCurrentEcoSession(request);
			engineEcoSessionWithTransientValues.setCurrentCustomer(customer);
			if(engineEcoSessionWithTransientValues.getCart() != null){
	            engineEcoSessionWithTransientValues.getCart().setCustomerId(customer.getId());
	            engineEcoSessionWithTransientValues.getCart().setBillingAddressId(customer.getDefaultBillingAddressId());
	            engineEcoSessionWithTransientValues.getCart().setShippingAddressId(customer.getDefaultShippingAddressId());
			}
			engineSessionService.updateAndSynchronizeEngineEcoSession(engineEcoSessionWithTransientValues);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void authenticationUser(final HttpServletRequest request, final User user) {
		try {
		
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
			token.setDetails(new WebAuthenticationDetails(request)); 
			Authentication authenticatedUser = authenticationManager.authenticate(token); 
	
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser); 
			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext()); 
		
			EngineBoSession engineBoSession = requestUtil.getCurrentBoSession(request);
			engineBoSession.setCurrentUser(user);
			engineSessionService.saveOrUpdateEngineBoSession(engineBoSession);
			
		} catch (Exception e) {
			logger.error("", e);
		}
	}
    
}
