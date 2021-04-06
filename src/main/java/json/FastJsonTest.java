package json;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-5-18.
 */
public class FastJsonTest {
  public static void main(String[] args) {
    FastJsonTest fj = new FastJsonTest();
    fj.test1();
    fj.testWrite();
  }

  public static StringBuilder getFileContent(String path, Charset charset) throws IOException {
    BufferedReader fileReader = Files.newBufferedReader(Paths.get(path));

    StringBuilder linesBuilder = new StringBuilder(30);
    fileReader.lines().forEach(line -> {
      linesBuilder.append(line);
    });
    return linesBuilder;
  }

  public void testWrite() {
    List<ComponentDefinition> list = new ArrayList<>();
    ComponentDefinition componentDefinition = null;
    for (int i = 0; i < 3; i++) {
      componentDefinition = new ComponentDefinition();
      componentDefinition.setDefaultKey("defaultKey" + i);
      componentDefinition.setKey("key" + i);
      componentDefinition.setInterfaceClass("com.test.tt");
      componentDefinition.addItem("tt" + i, "val" + i);
      componentDefinition.addItem("tt" + i * 3, "val" + i * 3);
      list.add(componentDefinition);
    }

    System.out.println("========================");
    String output = JSON.toJSONString(list);
    System.out.println(output);

    List<ComponentDefinition> list1 = JSON.parseArray(output, ComponentDefinition
            .class);

    System.out.println(list1.size());
    list1.forEach(definition -> {
      System.out.println(definition);
    });
  }

  public void test1() {
    InputStreamReader ir = new InputStreamReader(this.getClass()
            .getResourceAsStream("/Components.json"));

    StringBuilder sb = new StringBuilder(30);

    BufferedReader br = new BufferedReader(ir);
    br.lines().forEach(sb::append);

    List<ComponentDefinition> componentDefinitions = JSON.parseArray(sb.toString(),
            ComponentDefinition.class);
    System.out.println(componentDefinitions.size());

    //打印toString结果
    componentDefinitions.forEach(System.out::println);
  }
}
