package bean_validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
//指定验证器
@Constraint(validatedBy = MyValid.class)
@Documented
@interface SepcialCharCheck {

  //默认错误消息
  String message() default "invalid input.";

  //分组
  Class<?>[] groups() default {};

  //负载
  Class<? extends Payload>[] payload() default {};

}
