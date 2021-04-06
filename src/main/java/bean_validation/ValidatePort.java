package bean_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.*;

/**
 * 校验端口
 */
@Min(1)
@Max(65535)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidatePort {
  String message() default "" + 101;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
