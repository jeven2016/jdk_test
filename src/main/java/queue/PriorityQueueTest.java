package queue;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by root on 15-3-10.
 */
public class PriorityQueueTest {
  public static void main(String[] args) {
    treatAsNoComparator();
    treatAsComparator();
  }



  public static void treatAsComparator() {
    System.out.println("============ treatAsComparator ===========");
    PriorityQueue<Wang> queue = new PriorityQueue(new Comp());

    Wang wang1 = new Wang("wang1", 3);
    Wang wang3 = new Wang("wang3", 32);
    Wang wang = new Wang("wang", 4);
    Wang wang2 = new Wang("wang2", 7);

    queue.add(wang1);
    queue.add(wang3);
    queue.add(wang);
    queue.add(wang2);

    queue.forEach(p -> {
      System.out.println((Wang) p);
    });
  }


  public static void treatAsNoComparator() {

    System.out.println("============ treatAsNoComparator ===========");
    PriorityQueue<Wang> queue = new PriorityQueue();
    assert queue.size() == 11;

    Wang wang1 = new Wang("wang1", 3);
    Wang wang3 = new Wang("wang3", 32);
    Wang wang = new Wang("wang", 4);
    Wang wang2 = new Wang("wang2", 7);

    queue.add(wang1);
    queue.add(wang3);
    queue.add(wang);
    queue.add(wang2);

    queue.forEach(p -> {
      System.out.println((Wang) p);
    });

  }
}


class Person {
  String name;
  int age;

  public Person() {

  }

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }
}

class Wang extends Person implements Comparable<Person> {

  public Wang(String name, int age) {
    super(name, age);
  }

  @Override
  public int compareTo(Person p) {
    return age - ((Wang) p).age;
  }

  public String toString() {
    return "name: " + name + ", age=" + age;
  }
}

class Comp implements Comparator<Wang>{

  @Override
  public int compare(Wang o1, Wang o2) {
    return o1.age - o2.age;
  }
}
