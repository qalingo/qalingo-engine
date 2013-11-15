package org.hoteia.qalingo.core.web.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hoteia.qalingo.core.web.validation.contraint.TwoFieldsOrValidator;

@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TwoFieldsOrValidator.class)
public @interface TwoFieldsOr {

	String message() default "{errors.twofields}";

	String[] highlightFieldNames();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
