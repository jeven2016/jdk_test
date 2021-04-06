package bean_validation;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.*;
import java.util.Set;

/**
 * 组顺序校验
 * 不同组别的约束验证是无序的，然而在某些情况下，约束验证的顺序却很重要，如下面两个例子：
 * （1）第二个组中的约束验证依赖于一个稳定状态来运行，而这个稳定状态是由第一个组来进行验证的。
 * （2）某个组的验证比较耗时，CPU 和内存的使用率相对比较大，最优的选择是将其放在最后进行验证。
 * 因此，在进行组验证的时候尚需提供一种有序的验证方式，这就提出了组序列的概念。
 * 一个组可以定义为其他组的序列，使用它进行验证的时候必须符合该序列规定的顺序。
 * 在使用组序列验证的时候，如果序列前边的组验证失败，则后面的组将不再给予验证。
 */
public class GroupSequenceValdation {
  public static void main(String[] args) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    validateByGroupSeq(validator);
    validateByGroupSeq2(validator);
    validate(validator);
    validateTypeEmpty(validator);
  }

  /**
   * GroupSeq,将提醒根据序列进行校验
   */
  private static void validateByGroupSeq(Validator validator) {
    Person person = new Person();
    Set<ConstraintViolation<Person>> resultSet = validator.validate(person, GroupSeq.class);
    System.out.println("1. result of validateByGroupSeq========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

  /**
   * GroupSeq,将提醒根据序列进行校验
   */
  private static void validateByGroupSeq2(Validator validator) {
    Person person = new Person();
    Set<ConstraintViolation<Person>> resultSet = validator.validate(person, GroupSeq2.class);
    System.out.println("2. result of validateByGroupSeq2========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

  /**
   * GroupSeq,将提醒根据序列进行校验,这里没有错误产生
   */
  private static void validate(Validator validator) {
    Person person = new Person();
    person.setName("hello").setDesc("hello").setType("hello");
    Set<ConstraintViolation<Person>> resultSet = validator.validate(person, GroupSeq.class);
    System.out.println("3. result of validate========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }

  /**
   * GroupSeq,将提醒根据序列进行校验,这里没Type为空
   */
  private static void validateTypeEmpty(Validator validator) {
    Person person = new Person();
    person.setName("hello").setDesc("hello").setType(null);
    Set<ConstraintViolation<Person>> resultSet = validator.validate(person, GroupSeq.class);
    System.out.println("4. result of validateTypeEmpty========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }


}

interface Group1 {

}

interface Group2 {

}

@GroupSequence({Group2.class, Group1.class})
interface GroupSeq {

}

@GroupSequence({Group1.class, Group2.class})
interface GroupSeq2 {

}

class Person {
  private String name;
  private String desc;
  private String type;

  @NotEmpty(message = "the name cannot be null.", groups = Group1.class)
  public String getName() {
    return name;
  }

  @NotEmpty(message = "the desc cannot be null.", groups = Group1.class)
  public String getDesc() {
    return desc;
  }

  public Person setName(String name) {
    this.name = name;
    return this;
  }


  public Person setDesc(String desc) {
    this.desc = desc;
    return this;
  }

  @NotEmpty(message = "the type cannot be null.", groups = Group2.class)
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
