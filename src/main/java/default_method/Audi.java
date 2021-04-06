package default_method;

/**
 * Created by root on 4/24/14.
 */
public class Audi implements ICar {
}

class BWM implements ICar{
  @Override
  public String getSpeed() {
    return "400";
  }

  public static String getName() {
    return "Non-Speed";
  }
}
