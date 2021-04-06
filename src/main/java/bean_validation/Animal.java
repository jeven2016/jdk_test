package bean_validation;

import org.hibernate.validator.constraints.NotEmpty;

public interface Animal {
  @NotEmpty(message = "the name cannot be null.")
  String getName();

  @NotEmpty(message = "the desc cannot be null.")
  String getDesc();
}
