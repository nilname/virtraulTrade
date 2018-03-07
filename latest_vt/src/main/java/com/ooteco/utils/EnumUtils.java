package com.ooteco.utils;

public class EnumUtils {

    public static <T extends Enum<T>> T getEnumByOrdinal(Integer ordinal, Class<T> clazz){
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
