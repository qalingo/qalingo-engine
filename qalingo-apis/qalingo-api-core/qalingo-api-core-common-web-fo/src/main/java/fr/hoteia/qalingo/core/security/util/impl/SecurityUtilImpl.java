/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.security.util.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.EngineEcoSession;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.EngineSessionService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;

@Service("securityUtil")
@Transactional
public class SecurityUtilImpl implements SecurityUtil {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private EngineSessionService engineSessionService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RequestUtil requestUtil;
	
	public String generatePassword(final String clearPassword){
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		String result = encoder.encode(clearPassword);
		return result;
	}
	
	public void authenticationCustomer(final HttpServletRequest request, final Customer customer) {
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword());
		token.setDetails(new WebAuthenticationDetails(request)); 
		Authentication authenticatedUser = authenticationManager.authenticate(token); 

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser); 
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext()); 
		
		try {
			EngineEcoSession engineEcoSession = requestUtil.getCurrentEcoSession(request);
			engineEcoSession.setCustomer(customer);
			engineEcoSession.getCart().setCustomerId(customer.getId());
			engineSessionService.saveOrUpdateEngineEcoSession(engineEcoSession);
			
		} catch (Exception e) {
			LOG.error("", e);
		}
		
	}
	
}
