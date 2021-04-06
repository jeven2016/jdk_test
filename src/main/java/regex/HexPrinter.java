/*
 * Copyright (c) 2015. ZJTech Inc. All Rights Reserved.
 */

package regex;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexPrinter {
  private char[] chars = {'\n', '\t', '\r', '\f'};


  public static void main(String[] args) {
    HexPrinter mp = new HexPrinter();
    mp.print();
//    System.out.println("\\n=" + Character.);
  }

  public void print() {
    for (char c : chars) {
      char c2 = (char) Integer.parseInt(Integer.toHexString(c), 16);

      System.out.print("\\0x" + Integer.toHexString(c) + " " + (c2 == c) + ",");
    }

    Pattern pt = Pattern.compile("", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);

    System.out.println("\nhello matches ^hello$ :" + "hello".matches("^hello$"));
    System.out.println("\nhello matches ^hello $ :" + "hello".matches("^hello $"));
    System.out.println("\nhello  matches ^hello $ :" + "hello ".matches("^hello $"));
    System.out.println("\nhello  matches hello  :" + "hello ".matches("hello "));

    pt = Pattern.compile("\\d+");
    Matcher matcher = pt.matcher("tell me the 123dd,and44");
    if (matcher.find()) {
      //查看匹配的起始位置及长度
      int start = matcher.start();
      int end = matcher.end();
      System.out.println(String.format("start=%d,end=%d, length=%d", start, end, (end - start)));
    }

    //<b>2</b> 3 ,4 5<b>  7dd</b>, 需要过滤出2、7,这里使用两个Matcher匹配器
    String msg = "<b>2</b> 3 ,4 5<b>  7dd</b>";
    pt = Pattern.compile("<b>(.*?)</b>", Pattern.DOTALL); //加了？后就变成非贪婪匹配
    matcher = pt.matcher(msg);

    Pattern pt2 = Pattern.compile("\\d+");
    Matcher mt2 = pt2.matcher(msg);

    while (matcher.find()) {
      mt2.region(matcher.start(), matcher.end());
      while (mt2.find()) {
        System.out.print(mt2.group() + ",");
      }
    }

    //将t1=value1等号两边的值都替换掉
    msg = Pattern.compile("(\\w+)=(\\w+)").matcher("t1=t2").replaceAll("$2=$1");
    System.out.println("\nreplace msg=" + msg);

    /**
     * 将其中的数字+10并返回到原来的字符串中去
     */
    String msg2 = "yes 2015-12-24 16:33:33.240";
    StringBuffer sb = new StringBuffer();
    matcher = Pattern.compile("\\d+").matcher(msg2);
    while (matcher.find()) {
      int value = Integer.parseInt(matcher.group());
      matcher.appendReplacement(sb, (value + 10) + "");
    }
    matcher.appendTail(sb);
    System.out.println("the final data=" + sb.toString());

  }

}
