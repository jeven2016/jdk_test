package default_method;

/**
 * Created by root on 4/24/14.
 */
public interface ICar {


  default String getSpeed() {
    return "Non-Speed";
  }

  static String getName() {
    return "Non-Speed";
  }

  ;
}
