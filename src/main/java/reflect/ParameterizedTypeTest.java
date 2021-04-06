package reflect;


import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ParameterizedTypeTest {

  @Test
  public void checkTheType() throws NoSuchMethodException {
    Method method = Person.class.getMethod("getPerson", Object.class);

    System.out.println(method.getReturnType());
    Arrays.stream(method.getGenericParameterTypes()).forEach(type -> {
      System.out.println(type.getClass());
    });
  }


  @Test
  public void checkCollectionType() throws NoSuchMethodException {
    Arrays.stream(Person.class.getMethods())
        .filter(md -> md.getName().contains("getList"))
        .findFirst().ifPresent(md2 -> {

      System.out.println(md2.getGenericReturnType());
      Arrays.stream(md2.getGenericParameterTypes()).forEach(type -> {
        System.out.println(type.getClass());
      });
    });

  }

}

class Person {

  public <P> P getPerson(P person) {
    return person;
  }

  public <C> Collection<C> getList(List<C> list) {
    return list;
  }
}
