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