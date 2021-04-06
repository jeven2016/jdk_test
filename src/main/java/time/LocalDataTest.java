/*
 * Copyright (c) 2015. ZJTech Inc. All Rights Reserved.
 */

package time;

import java.time.LocalDate;

public class LocalDataTest {
  public static void main(String[] args) {
    t1();
  }

  public static void t1() {
    LocalDate date = LocalDate.ofYearDay(2015, 2);
    System.out.println("the secodary day of 2015: " + date.toString());

    System.out.println("now is " + LocalDate.now().toString());
    System.out.println("the 31rd day of month Jan. in 2015 is " + LocalDate.of(2015, 1, 31).toString());
//    System.out.println(LocalDate.parse(" 2015-01-02", DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)).toString());
  }

}
