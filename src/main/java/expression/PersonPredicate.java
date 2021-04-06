package expression;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public class PersonPredicate {

  public static void main(String[] args) {
    List<Person> personList = initPersonList();

    //打印年龄在20-25的person
    filerPersons(personList, p -> p.getAge() >= 20 && p.getAge() <= 25);

    //打印年龄在26-30的person
    filerPersons(personList, p -> p.getAge() >= 26 && p.getAge() <= 30);

    //打印年龄在26-30的person
    filerPersons(personList, p -> p.getAge() >= 21 && p.getAge() <= 35);

    //过滤list中的年龄在（20-27）之间的人员，并以名称+年龄的格式输出
    System.out.println("step. 3");
    personList.stream().filter((Person p) -> p.getAge() > 20 && p.getAge() < 27)
            .map(p -> p.getName() + "&" + p.getAge())
            .forEach(System.out::println);

    System.out.println("step. 3");
    personList.stream().filter((Person p) -> p.getAge() > 20 && p.getAge() < 27)
            .map(p -> p.getName() + "&" + p.getAge())
            .forEach(System.out::println);
  }

  public static List<Person> initPersonList() {
    List<Person> list = new ArrayList<Person>();

    Person person;
    for (int i = 0; i < 40; i++) {
      person = new Person();
      person.setAge(20 + i).setGender(Person.Sex.MALE).setName("user_" + (20 + i));
      list.add(person);
    }
    return list;
  }

  public static void filerPersons(List<Person> personList, Predicate<Person> tester) {
    for (Person p : personList) {
      if (tester.test(p)) {
        p.printPerson();
      }
    }
  }

  public static void filterwithOthers(List<Person> personList, Consumer<Person> consumer,
                                      Function<Person, Object> func) {

  }
}


