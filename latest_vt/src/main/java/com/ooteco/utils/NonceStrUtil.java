package com.ooteco.utils;

import java.util.Random;

public class NonceStrUtil {

    private static final String[] strArr = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String createUserName() {
        Random random = new Random();
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            String randStr = String.valueOf(strArr[random.nextInt(strArr.length)]);
            sRand.append(randStr);
        }
        int randNum = (int)((Math.random()*9+1)*1000);
        sRand.append(randNum);
        return sRand.toString();
    }

    public static void main(String[] args) {
        System.out.println(createUserName());
    }
}
