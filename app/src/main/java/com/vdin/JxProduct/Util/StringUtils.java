package com.vdin.JxProduct.Util;

public class StringUtils {

    public static boolean isEmpty(String string) {
        if (string == null || string.length() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String cutStrToName(String name){

        String countStr = name.substring(0,1);
        for (int i = 0; i < name.length()-1; i++) {
            countStr = countStr + "*";
        }
        return countStr;
    }
}
