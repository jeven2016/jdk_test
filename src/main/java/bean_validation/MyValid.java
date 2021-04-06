package bean_validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//特殊字符校验器
public class MyValid implements ConstraintValidator<SepcialCharCheck, String> {
  Pattern pt = Pattern.compile("$");

  @Override
  public void initialize(SepcialCharCheck constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Matcher mt = pt.matcher(value);
    if (mt.find())
      return false;
    else
      return true;
  }
}