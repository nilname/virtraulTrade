package com.ooteco.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isEmpty(String str) {
        return (str == null) || (str.trim().isEmpty());
    }

    public static boolean isNotEmpty(String... str) {
        for (String string : str) {
            if (isEmpty(string))
                return false;
        }
        return true;
    }

    public static String altString(String str1, String str2) {
        return isNotEmpty(str1) ? str1 : str2;
    }

    /**
     * 加星号
     */
    public static String encryte(String name) {
        String rs = "";
        String star = "*******";
        if (name != null) {
            if (name.length() >= 6) {
                int index = (name.length() - 4) / 2;
                rs = name.substring(0, index) + "****" + name.substring(index + 4);
            } else if (name.length() >= 2) {
                rs = name.substring(0, 1) + star.substring(0, name.length() - 2) + name.substring(name.length() - 1);
            } else {
                rs = name + "****";
            }
        }
        return rs;
    }

    public static String join(Object... args) {
        if (args == null || args.length == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                sb.append(",");
            }

            Object arg = args[i];
            if (arg != null) {
                if (arg.getClass().isArray()) {
                    sb.append(Arrays.toString((Object[]) arg));
                } else {
                    sb.append(arg);
                }
            }
        }
        return sb.toString();
    }
    
    public static String parseQuote(String str) {
        Pattern p = Pattern.compile("'([^']*)");
        Matcher m = p.matcher(str);
        if (m.find()) {
            String res = m.group(1);
            return res;
        }
        return null;
    }
    
}
