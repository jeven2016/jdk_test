package bean_validation;

import org.hibernate.validator.constraints.NotEmpty;

public interface Type {
  @NotEmpty(message = "the type cannot be null.")
  String getType();
}
