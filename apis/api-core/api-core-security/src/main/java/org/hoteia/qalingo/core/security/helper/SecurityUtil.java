/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.security.helper;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("securityUtil")
public class SecurityUtil {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    static final String NUM = "0123456789";
    static final String SPL_CHARS = "!@#$%*";

	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	public boolean isAuthenticated(){
	    if(SecurityContextHolder.getContext().getAuthentication() != null
	            && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
	            && !StringUtils.equals("anonymousUser", SecurityContextHolder.getContext().getAuthentication().getName())){
	        return true;
	    }
	    return false;
	}
	
	public String generatePermalink() {

		// TODO : setting in database
		
		return new String(generatePswd(10, 10, 1, 1, 0));
	}
	
   public String generateToken() {
       String token = new String(generatePswd(8, 8, 1, 1, 1)) + new String(generatePswd(8, 8, 1, 1, 1)) + new String(generatePswd(8, 8, 1, 1, 1)) + new String(generatePswd(8, 8, 1, 1, 1));
       return token;
    }
	
   public boolean passwordMatches(String rawPassword, String encodedPassword) {
       return passwordEncoder.matches(rawPassword, encodedPassword);
   }
   
	public String encodePassword(String clearPassword) {
		String result = passwordEncoder.encode(clearPassword);
		return result;
	}
	
	public String generateAndEncodePassword() {
		return encodePassword(generatePassword());
	}
		
	public String generatePassword() {

		// TODO : setting in database
		
		return new String(generatePswd(8, 8, 1, 1, 1));
	}
	
	protected static char[] generatePswd(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
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