package spi;

/**
 * Created by root on 15-5-15.
 */
public class BasicService implements IService {
  @Override
  public String getServiceName() {
    return "BasicService";
  }

  @Override
  public String invoke() {
    return "invoke BasicService";
  }
}
