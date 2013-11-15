package org.hoteia.qalingo.core.web.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hoteia.qalingo.core.web.validation.contraint.ForbiddenCharsValidator;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ForbiddenCharsValidator.class)
public @interface ForbiddenChars {
	
	char[] forbiddenChars() default { ';' };

	Class<?>[] groups() default {};

	String message() default "{errors.twofields}";

	Class<? extends Payload>[] payload() default {};
}
