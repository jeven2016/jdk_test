package bean_validation;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


/**
 * 自定义校验器
 */
public class CustomerValidation {
  public static void main(String[] args) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    validate(validator);
  }

  /**
   * GroupSeq,将提醒根据序列进行校验
   */
  private static void validate(Validator validator) {
    City city = new City();
    city.setName("$hello").setDesc("kkwang").setType(null);
    Set<ConstraintViolation<City>> resultSet = validator.validate(city);
    System.out.println("1. result of validate========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

}


class City {
  private String name;
  private String desc;
  private String type;

  @SepcialCharCheck(message = "There're special character(s).")
  @NotEmpty(message = "the name cannot be null")
  public String getName() {
    return name;
  }

  @SepcialCharCheck(message = "There're special character(s).")
  @NotEmpty(message = "the desc cannot be null")
  public String getDesc() {
    return desc;
  }

  public City setName(String name) {
    this.name = name;
    return this;
  }


  public City setDesc(String desc) {
    this.desc = desc;
    return this;
  }

  @NotEmpty(message = "the type cannot be null")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}