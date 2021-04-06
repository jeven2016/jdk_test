package json;

import java.util.HashMap;
import java.util.Map;

/**
 * 加载组件的配置定义
 */
public class ComponentDefinition {
  private String key;
  private String defaultKey;
  private String interfaceClass;
  private Map<String, String> items = new HashMap<>();

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getDefaultKey() {
    return defaultKey;
  }

  public void setDefaultKey(String defaultKey) {
    this.defaultKey = defaultKey;
  }

  public String getInterfaceClass() {
    return interfaceClass;
  }

  public void setInterfaceClass(String interfaceClass) {
    this.interfaceClass = interfaceClass;
  }

  public Map<String, String> getItems() {
    return items;
  }

  public void setItems(Map<String, String> items) {
    this.items = items;
  }

  public void addItem(String key, String value) {
    this.items.put(key, value);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(30);
    sb.append("{ key=").append(key).append(",")
            .append("defaultKey=").append(defaultKey).append(",")
            .append("interfaceClass=").append(interfaceClass).append(",")
            .append("items={");
    items.forEach((key, value) -> {
      sb.append(key).append("=").append(value).append(",");
    });

    if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
      sb.deleteCharAt(sb.length() - 1);
    }

    sb.append("}}");
    return sb.toString();
  }
}
