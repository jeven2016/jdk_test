package visitor;


public class SimpleWorker implements IWorker {
  private String name;
  private int age;
  private int salary;

  public SimpleWorker() {

  }

  public SimpleWorker(String name, int age, int salary) {
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  @Override
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  @Override
  public void calculateSalary(Istatistics handler) {
    handler.calculateSalary(this);
  }
}
