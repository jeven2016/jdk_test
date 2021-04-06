package spi;

public class ComplexService implements IService {
  @Override
  public String getServiceName() {
    return "ComplexService";
  }

  @Override
  public String invoke() {
    return "invoke ComplexService";
  }
}
