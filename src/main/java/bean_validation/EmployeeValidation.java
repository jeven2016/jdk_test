package bean_validation;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class EmployeeValidation {

  @NotNull(message = "The id of employee can not be null")
  private Integer id;

  @NotNull(message = "The name of employee can not be null")
  @Size(min = 1, max = 10, message = "The size of employee's name must between 1 and 10")
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static void main(String[] args) {
    EmployeeValidation employee = new EmployeeValidation();
    employee.setName("test");
    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    Validator validator = vf.getValidator();
    Set<ConstraintViolation<EmployeeValidation>> set = validator.validate(employee);
    for (ConstraintViolation<EmployeeValidation> constraintViolation : set) {
      System.out.println(constraintViolation.getMessage());
    }
  }
}
