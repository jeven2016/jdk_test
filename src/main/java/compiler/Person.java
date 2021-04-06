package compiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Person类
 */
public class Person {

  String name;
  int age;
  Map<String, List<String>> maps = new HashMap();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Map<String, List<String>> getMaps() {
    return maps;
  }

  public void setMaps(Map<String, List<String>> maps) {
    this.maps = maps;
  }
}
