package core;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;

import java.beans.PropertyEditor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ResourceTest {

  @Test
  public void testResourceEditor() {
    System.setProperty("cls", "classpath:spring/core/ResourceTest.class");
    PropertyEditor propertyEditor = new ResourceEditor();
    propertyEditor.setAsText("${cls}");

    Resource resource = (Resource) propertyEditor.getValue();
    assert resource.exists();
  }

  @Test
  public void testResourceEditor2() throws IOException {
    PropertyEditor propertyEditor = new ResourceEditor();
    propertyEditor.setAsText("file:build.gradle");

    Resource resource = (Resource) propertyEditor.getValue();
    assertTrue(resource.exists());

    BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
    reader.lines().forEach(System.out::println);
  }

}
