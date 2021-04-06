package bean_validation;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 对象之间级联检查
 */
public class CascadeValidation {
  public static void main(String[] args) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    validate(validator);
    validate2(validator);
    validate3(validator);
  }

  private static void validate(Validator validator) {
    A a = new A();
    Set<ConstraintViolation<A>> resultSet = validator.validate(a);
    System.out.println("1. ========================================");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

  private static void validate2(Validator validator) {
    A a = new A();
    a.setName("hello");
    Set<ConstraintViolation<A>> resultSet = validator.validate(a);
    System.out.println("2. ========================================");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }


  private static void validate3(Validator validator) {
    A a = new A();
    a.setName("hello");
    B b = new B();
    a.setB(b);
    Set<ConstraintViolation<A>> resultSet = validator.validate(a);
    System.out.println("3. ========================================");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

}

class A {
  @NotEmpty(message = "A's name cannot be empty")
  private String name;

  @Valid
  @NotNull(message = "B cannot be null.")
  private B b;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public B getB() {
    return b;
  }

  public void setB(B b) {
    this.b = b;
  }
}

class B {
  private String name;

  @NotEmpty(message = "B's name cannot be empty")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
