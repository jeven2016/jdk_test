package compiler;

import java.util.List;
import java.util.Map;

/**
 * 接口
 */
public interface IPerson {

  public void setName(String name);

  public int getAge();

  public void setAge(int age);

  public Map<String, List<String>> getMaps();

  public void setMaps(Map<String, List<String>> maps);
}
