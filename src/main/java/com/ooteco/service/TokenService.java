package com.ooteco.service;

import com.ooteco.utils.AES;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final static String delimiter = ",";

    public String encrypt(Integer userId, long time) throws Exception{
        String content = userId + "," + time;
        return AES.aesEncrypt(content);
    }

    public String[] getDecryptInfo(String token){
        String decryptStr = aesDecrypt(token);
        return decryptStr.split(delimiter);
    }

    public String encrypt(Integer userId) throws Exception{
        String content = userId+","+System.currentTimeMillis();
        return AES.aesEncrypt(content);
    }

    public String decryptToken(String token){
        String decryptStr = aesDecrypt(token);
        return decryptStr.split(delimiter)[0];
    }

    public long decryptTokenTime(String token){
        String decryptStr = aesDecrypt(token);
        return Long.parseLong(decryptStr.split(delimiter)[1]);
    }

    private String aesDecrypt(String token){
        try {
            String result = AES.aesDecrypt(token);
            if(result != null)
                return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws Exception{
        String encrypt = new TokenService().encrypt(2, System.currentTimeMillis());
        System.out.println(encrypt);
        System.out.println(new TokenService().decryptToken(encrypt));
    }
}
