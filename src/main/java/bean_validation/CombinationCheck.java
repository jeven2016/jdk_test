package bean_validation;


import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@NotNull()
@Length(min = 3, max = 10, message = "The lenght is between min and max.")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CombinationValid.class)
@Documented
public @interface CombinationCheck {
  String message() default "{errorCode}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

