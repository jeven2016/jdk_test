package bean_validation;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidatePortTest {
  public static void main(String[] args) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Server server = new Server();
    server.setPort(34444444);
    Set<ConstraintViolation<Server>> resultSet = validator.validate(server);
    System.out.println("1. result of validate========");
    resultSet.forEach(cv -> {
      System.out.println(cv.getMessage());
    });
    System.out.println("  ----------------------------------------");
  }
}


class Server {
  @ValidatePort
  private int port;

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}