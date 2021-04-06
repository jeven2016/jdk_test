package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 14-12-2.
 */
public class TestMatch {
  static Pattern pt = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

  static void findIPs(String str) {

    Matcher matcher = pt.matcher(str);
    while (matcher.find()) {
      String value = matcher.group();
      System.out.println("find=" + value);
    }

  }

  public static void main(String[] args) {
    String[] strs = new String[]{
            "(16.23.22.33)","(())","(76.32.22.1,,66::322", "fcee:22:32:33:33,66.2.2", "" ,
            "(((()223)1.1.1.2,34.33.3.2,2323::23"
    };
    for(String str: strs)
    findIPs(str);
  }


}
