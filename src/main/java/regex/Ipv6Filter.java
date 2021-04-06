/*
 * Copyright (c) 2015. ZJTech Inc. All Rights Reserved.
 */

package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 15-1-26.
 */
public class Ipv6Filter {
  /**
   * [111::111]:64
   * [::11.1.1.1]:64
   *
   * 111::111
   * ::10.11.11.1
   *
   * 10.11.11.1
   * 10.11.11.1:24
   *

   * 1111:0000:.....:11
   *
   * @param args
   */

  public static void main(String[] args) {
   /* fillIpv6Byexp("[111::111]:64");
    fillIpv6Byexp("111::111");
    fillIpv6Byexp("10.11.11.1");
    fillIpv6Byexp("10.11.11.1:24");
    fillIpv6Byexp("::10.11.11.1");
    fillIpv6Byexp("[::11.1.1.1]:64");
    fillIpv6Byexp("1111:0000:.....:11");
*/
    test1();

  }

  public static void filterIPv6(String msg) {
    Pattern pt = Pattern.compile("\\[.*?]");
    Pattern pt2 = Pattern.compile("\\[|\\]");

    Pattern pt3 = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}:\\d+");//get IPv4
    Matcher mt = pt.matcher(msg);

    Matcher mt3 = pt3.matcher(msg);

    if (mt.find()) {
      System.out.println(pt2.matcher(mt.group()).replaceAll(""));
    } else if (mt3.matches()) {
      System.out.println(msg.split(":")[0]);
    } else {
      System.out.println(msg);
    }


  }

  public static void fillIpv6Byexp(String msg){
    Pattern p = Pattern.compile("(?<=\\[).*?(?=\\])");
    Matcher mt = p.matcher(msg);
    if(mt.find()){
      System.out.println(mt.group());
    }
    else{
      System.out.println("old-"+msg);
    }
  }

  public static void test1(){
    Pattern p = Pattern.compile("[a-z]+(?=\\d+)");
    Matcher matcher = p.matcher("abc100");
    System.out.println(matcher.groupCount());
    while (matcher.find()){
      System.out.println(matcher.group());
    }
  }

}
