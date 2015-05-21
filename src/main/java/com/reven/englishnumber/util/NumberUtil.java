package com.reven.englishnumber.util;

/**
 * Created by CS on 2015/5/15.
 * Number stuff
 */
public class NumberUtil {
    private static String[] english20 = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static String[] english99 = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private static String[] german20 = {"null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn", "elf", "zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn"};
    private static String[] german99 = {"zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig"};

    private static String getEnglish(int number) {
        StringBuilder sb = new StringBuilder();
        if (number < 20) {
            return english20[number];
        } else if (number < 100) {
            int decade = number / 10;
            sb.append(english99[decade - 2]).append(" ");
            int rest = number % 10;
            if (rest != 0) {
                sb.append(english20[number % 10]);
            }
        }
        return sb.toString();
    }

    private static String getGerman(int number) {
        StringBuilder sb = new StringBuilder();
        if (number < 20) {
            return german20[number];
        } else if (number < 100) {
            int rest = number % 10;
            if (rest != 0) {
                sb.append(german20[number % 10]).append("und");
            }
            int decade = number / 10;
            sb.append(german99[decade - 2]);
        }
        return sb.toString();
    }

    public static String getNumberString(int number, int language) {
        if (language == 1) {
            return getGerman(number);
        }
        return getEnglish(number);
    }
}
