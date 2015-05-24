package com.reven.englishnumber.util;

/**
 * Created by CS on 2015/5/15.
 * Number stuff
 */
public class NumberUtil {
    private static String[] english20 = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static String[] english90 = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private static String[] german20 = {"null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn", "elf", "zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn"};
    private static String[] german90 = {"zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig"};
    private static String[] german100 = {"hundert", "tausend", "Million", "milliarde"};

    private static String getEnglish(int number) {
        StringBuilder sb = new StringBuilder();
        if (number < 20) {
            return english20[number];
        } else if (number < 100) {
            int decade = number / 10;
            sb.append(english90[decade - 2]);
            int rest = number % 10;
            if (rest != 0) {
                sb.append("-").append(english20[number % 10]);
            }
        } else if (number < 1000) {
            int hundred = number / 100;
            sb.append(english20[hundred]).append(" hundred");
            int rest = number % 100;
            if (rest > 0) {
                sb.append(" and ").append(getEnglish(rest));
            }
        } else if (number < 1000000) {
            int thousand = number / 1000;
            sb.append(getEnglish(thousand)).append(" thousand");
            int rest = number % 1000;
            if (rest > 0) {
                sb.append("\n").append(getEnglish(rest));
            }
        } else if (number < 1000000000) {
            int million = number / 1000000;
            sb.append(getEnglish(million)).append(" million");
            int rest = number % 1000000;
            if (rest > 0) {
                sb.append("\n").append(getEnglish(rest));
            }
        } else {
            int billion = number / 1000000000;
            sb.append(getEnglish(billion)).append(" billion");
            int rest = number % 1000000000;
            if (rest > 0) {
                sb.append("\n").append(getEnglish(rest));
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
            sb.append(german90[decade - 2]);
        } else if (number < 1000) {
            int hundred = number / 100;
            if (hundred > 1) {
                sb.append(german20[hundred]);
            }
            sb.append("hundert");
            int rest = number % 100;
            if (rest > 0) {
                sb.append(getGerman(rest));
            }
        }
        return sb.toString();
    }

    public static String getNumberString(int number, int language) {
        if (language == 1) {
            return getGerman(number);
        }
        return getEnglish(number);
    }

    public static String getNumber(String number) {
        StringBuilder sb = new StringBuilder(number);
        if (sb.length() > 3) {
            sb.insert(sb.length() - 3, ",");
        }
        if (sb.length() > 7) {
            sb.insert(sb.length() - 7, ",");
        }
        if (sb.length() > 11) {
            sb.insert(sb.length() - 11, ",");
        }
        return sb.toString();
    }
}
