package org.hoteia.qalingo.core.web.validation.contraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hoteia.qalingo.core.web.validation.annotation.TwoFieldsEquals;
import org.hoteia.qalingo.core.web.validation.annotation.TwoFieldsLink;

public class TwoFieldsEqualsValidator implements ConstraintValidator<TwoFieldsEquals, TwoFieldsLink<? extends Object>> {
  private String[] highlightFieldNames;

  @Override
  public void initialize(TwoFieldsEquals constraintAnnotation) {
    this.highlightFieldNames = constraintAnnotation.highlightFieldNames();
  }

  @Override
  public boolean isValid(TwoFieldsLink<? extends Object> value, ConstraintValidatorContext ctx) {
    if (value.getField() == null && value.getField2() == null) return true;
    if (value.getField() == null) return false;
    boolean isValid = value.getField().equals(value.getField2());
    if(!isValid) {
      ctx.disableDefaultConstraintViolation();
      String template = ctx.getDefaultConstraintMessageTemplate();
      for(String highlightFieldName  : highlightFieldNames) {
        ctx.buildConstraintViolationWithTemplate(template).addNode(highlightFieldName).addConstraintViolation();
      }
    }
    return isValid;
  }
  
}