package expression;

/**
 * Created by root on 4/14/14.
 */
public class Person {

  public static enum Sex {
    MALE, FEMALE
  }

  private String name;
  private Sex gender;
  private int age;

  public String getName() {
    return name;
  }

  public Person setName(String name) {
    this.name = name;
    return this;
  }

  public Sex getGender() {
    return gender;
  }

  public Person setGender(Sex gender) {
    this.gender = gender;
    return this;
  }


  public Person setAge(int age) {
    this.age = age;
    return this;
  }


  public int getAge() {
    return age;
  }

  public void printPerson() {
    System.out.println(name + "," + age);
  }
}
