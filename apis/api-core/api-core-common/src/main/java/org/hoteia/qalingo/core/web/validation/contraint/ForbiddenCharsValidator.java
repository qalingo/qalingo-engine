/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.validation.contraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import org.hoteia.qalingo.core.web.validation.annotation.ForbiddenChars;

public class ForbiddenCharsValidator implements ConstraintValidator<ForbiddenChars, String> {

	private char[] forbiddenCharList;
	
	@Override
	public void initialize(ForbiddenChars constraintAnnotation) {
		forbiddenCharList = constraintAnnotation.forbiddenChars();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !StringUtils.containsAny(value, forbiddenCharList);
	}

}