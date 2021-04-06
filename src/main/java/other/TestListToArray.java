package other;

import java.util.ArrayList;

/**
 * Created by root on 4/17/14.
 */
public class TestListToArray {
  public static void main(String[] args) {
    ArrayList<Integer> list =new ArrayList();
    list.add(1);
    list.add(2);

    Integer[] array = new Integer[]{4,5,6,7,8,9};


    array = list.toArray(array);
    System.out.println(array.length);
    for(Integer value:array)
    System.out.println(value);

  }
}
