package bean_validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class CombinationValidationTest {

  public static void main(String[] args) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    validate(validator);
    validate2(validator);
    validate3(validator);
  }

  /**
   * @NotNull 生效
   */
  private static void validate(Validator validator) {
    NanJing city = new NanJing();
    Set<ConstraintViolation<NanJing>> resultSet = validator.validate(city);
    System.out.println("1. result of validate========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

  /**
   * @Length 生效
   */
  private static void validate2(Validator validator) {
    NanJing city = new NanJing();
    city.setName("A");
    Set<ConstraintViolation<NanJing>> resultSet = validator.validate(city);
    System.out.println("2. result of validate========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

  /**
   * CombinationValid 生效,提示名称不能为hello
   */
  private static void validate3(Validator validator) {
    NanJing city = new NanJing();
    city.setName("hello");
    Set<ConstraintViolation<NanJing>> resultSet = validator.validate(city);
    System.out.println("3. result of validate========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getPropertyPath()+"---"+cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }
}


class NanJing {
  private String name;

  @CombinationCheck(message = "The value cannot be hello.")
  public String getName() {
    return name;
  }

  public NanJing setName(String name) {
    this.name = name;
    return this;
  }

}
