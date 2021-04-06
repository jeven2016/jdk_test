package script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * java7以前，JDK内置了一个基于Mozilla Rhino的javascript脚本引擎。在java8里面，
 * 基于JSR292和invokedynamic重新提供了一个新的javascript引擎-Oracle NaComponents.jsshorn。
 * 它更符合ECMA标准的javascript规范，而且基于invokedynamic调用拥有更好的性能
 */
public class HelloTest {
  public static void main(String[] args) {
    HelloTest ht = new HelloTest();
    ht.method1();
  }

  public void method1() {
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("nashorn");

    try {
      Reader reader = new InputStreamReader(this.getClass()
              .getResourceAsStream("/Components.js"));

      engine.eval(reader);

      String val = (String)engine.eval("Definitions.compiler.list.adaptive");
      System.out.println(val);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
