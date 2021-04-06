package expression;

/**
 * Created by root on 4/24/14.
 */
public class SimpleInterTest {
  public static void main(String[] args) {
    Operator<Person> operator1 = p -> System.out.println(p.getName() + "; tag=" + 1);
    Operator<Person> operator2 = p -> System.out.println(p.getAge() + "; tag=" + 2);
    Operator<Person> operator3 = p -> {
      if (p.getAge() > 10) {
        System.out.println(p.getName() + "is older than 10,tag=" + 3);
      }
    };


    Person p = new Person();
    p.setName("test");
    p.setAge(20);
    operator1.print(p);
    operator2.print(p);
    operator3.print(p);

  }

  interface Operator<T> {
    void print(T obj);
  }
}
