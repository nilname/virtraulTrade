package com.ooteco.utils;

import org.apache.tomcat.util.security.MD5Encoder;

public class Main {

    public static void main(String[] args) {
        String smsCode = String.valueOf((int)((Math.random()*9+1)*100000));
        int id = 1234;
        int subLen = 1;
        while(String.valueOf(subLen).length()<8-String.valueOf(id).length()) {
            subLen = subLen * 10;
        }
        System.out.println(subLen);
        int invitecode = id + (int)((Math.random()*9+1)*subLen);
        System.out.println(invitecode);
    }

}
