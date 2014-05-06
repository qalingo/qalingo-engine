/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.security.util.impl;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.security.util.SecurityUtil;
import org.hoteia.qalingo.core.service.EngineSessionService;
import org.hoteia.qalingo.core.web.util.RequestUtil;

@Service("securityUtil")
@Transactional
public class SecurityUtilImpl implements SecurityUtil {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EngineSessionService engineSessionService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RequestUtil requestUtil;
	
	@Autowired
	protected PasswordEncoder encoder;
	
	public String generatePermalink() {

		// TODO : setting in database
		
		return new String(generatePswd(10, 10, 1, 1, 0));
	}
	
	public void authenticationCustomer(final HttpServletRequest request, final Customer customer) {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword());
			token.setDetails(new WebAuthenticationDetails(request)); 
			Authentication authenticatedUser = authenticationManager.authenticate(token); 
	
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser); 
			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext()); 
		
			EngineEcoSession engineEcoSessionWithTransientValues = requestUtil.getCurrentEcoSession(request);
			engineEcoSessionWithTransientValues.setCurrentCustomer(customer);
			engineEcoSessionWithTransientValues.getCart().setCustomerId(customer.getId());
			engineEcoSessionWithTransientValues.getCart().setBillingAddressId(customer.getDefaultBillingAddressId());
			engineEcoSessionWithTransientValues.getCart().setShippingAddressId(customer.getDefaultShippingAddressId());
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
	
	public String encodePassword(String clearPassword) {
		String result = encoder.encode(clearPassword);
		return result;
	}
	
	public String generateAndEncodePassword() {
		return encodePassword(generatePassword());
	}
		
	public String generatePassword() {

		// TODO : setting in database
		
		return new String(generatePswd(6, 6, 1, 1, 1));
	}
	
	public static char[] generatePswd(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
        if(minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        if( (noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen )
            throw new IllegalArgumentException
            ("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
        }
        for(int i = 0; i < len; i++) {
            if(pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }
 
    private static int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while(pswd[index = rnd.nextInt(len)] != 0);
        return index;
    }
    
}
