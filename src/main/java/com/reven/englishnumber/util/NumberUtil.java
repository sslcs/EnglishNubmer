package com.reven.englishnumber.util;

/**
 * Created by CS on 2015/5/15.
 * Number stuff
 */
public class NumberUtil {
    private static String[] str20 = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    private static String[] str99 = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    public static String getNumberString(int number) {
        StringBuilder sb = new StringBuilder();
        if (number < 20) {
            return str20[number];
        }
        else if (number < 100) {
            int decade = number / 10;
            sb.append(str99[decade - 2]).append(" ");
            int rest = number % 10;
            if (rest != 0) {
                sb.append(str20[number % 10]);
            }
        }
        return sb.toString();
    }
}
