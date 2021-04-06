package core;


import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StringUtilsTest {


  @Test
  public void testCleanPath() {
    String path = "/var/log/test/.././root";
    String cleanedPath = StringUtils.cleanPath(path);
    assertEquals("/var/log/root", cleanedPath);

    String path2 = "c:\\system32\\installed\\current\\..\\.\\new";
    String cleanedPath2 = StringUtils.cleanPath(path2);
    assertEquals("c:/system32/installed/new", cleanedPath2);
  }

  @Test
  public void testCollectionToCommaDelimitedString() {
    ArrayList list = new ArrayList<String>();
    list.add("str1");
    list.add("str2");

    String value = StringUtils.collectionToCommaDelimitedString(list);
    assertEquals("str1,str2", value);

    String value2 = StringUtils.collectionToDelimitedString(list, "@");
    assertEquals("str1@str2", value2);
  }

  @Test
  public void testArrayToCommaDelimitedString() {
    String[] array = {"a", "b"};
    String value = StringUtils.arrayToCommaDelimitedString(array);

    assertEquals("a,b", value);

    value = StringUtils.arrayToDelimitedString(array, "@");
    assertEquals("a@b", value);
  }

  @Test
  public void testApplyRelativePath() {
    String path = StringUtils.applyRelativePath("/root/path/", "/current");
    System.out.println(path);

  }
}
