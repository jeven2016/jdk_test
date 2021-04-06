package core;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Map;

public class AliasForAttributeTest {

  @MyConfig(path = "/myPath")
  public void myAnnotatedMethod() {

  }

  @Test
  public void test1() throws NoSuchMethodException {
    Method method = MyConfig.class.getMethod("path");
    Annotation annotation = AnnotationUtils
        .findAnnotation(method, AliasFor.class);

    Map<String, Object> map = AnnotationUtils.getAnnotationAttributes(annotation);
    map.entrySet().forEach(action -> {
      System.out.println(action.getKey() + "=" + action.getValue());
    });
  }

}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface MyConfig {

  @AliasFor(annotation = GetMapping.class)
  String[] path() default {};

}



