package bean_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 组合了多个校验的校验器
 */
public class CombinationValid implements ConstraintValidator<CombinationCheck, String> {
  @Override
  public void initialize(CombinationCheck constraintAnnotation) {
    String messg = constraintAnnotation.message();
    System.out.println("INFO: the message is " + messg);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value != null && value.equals("hello")) {
      return false;
    }
    return true;
  }

}
