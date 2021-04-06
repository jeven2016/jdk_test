package bean_validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 按组部分校验
 */
public class GroupValidation {
  public static void main(String[] args) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    validateByAnimalGroup(validator);
    validateByCatGroup(validator);
    validateWithoutGroup(validator);
  }


  /**
   * Group使用Animal,将提醒desc为empty
   *
   * @param validator
   */
  private static void validateByAnimalGroup(Validator validator) {
    Cat cat = new Cat();
    cat.setName("cat").setDesc(null);
    Set<ConstraintViolation<Cat>> resultSet = validator.validate(cat, Animal.class);
    System.out.println("1. result of validateByAnimalGroup========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }


  /**
   * Group使用Cat,将提醒type为empty
   *
   * @param validator
   */
  private static void validateByCatGroup(Validator validator) {
    Cat cat = new Cat();
    Set<ConstraintViolation<Cat>> resultSet = validator.validate(cat, Type.class);
    System.out.println("2. result of validateByCatGroup========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

  /**
   * 不使用Group,将全部校验
   */
  private static void validateWithoutGroup(Validator validator) {
    Cat cat = new Cat();
    Set<ConstraintViolation<Cat>> resultSet = validator.validate(cat);
    System.out.println("3. result of validateWithoutGroup========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

}

class Cat implements Animal, Type {
  private String name;
  private String desc;


  private String type;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDesc() {
    return desc;
  }

  public Cat setName(String name) {
    this.name = name;
    return this;
  }


  public Cat setDesc(String desc) {
    this.desc = desc;
    return this;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
