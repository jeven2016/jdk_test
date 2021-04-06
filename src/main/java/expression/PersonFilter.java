package expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/14/14.
 */
public class PersonFilter {
  public static void main(String[] args) {
    List<Person> users = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Person p = new Person();
      p.setName("user_" + i);
      p.setAge(i + 10);
      users.add(p);
    }
  }

 static void filterOne(List<Person> users){

 }


}
