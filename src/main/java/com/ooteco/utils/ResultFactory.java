package com.ooteco.utils;

import com.ooteco.swagger.JsonResult;

public class ResultFactory {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int NOAUTH = -11;
    public static final int EXCEPTION = -99;

    public static final String SUCCESS_MSG = "success";
    public static final String FAIL_MSG = "fail";
    public static final String EXCEPTION_MSG = "服务异常，请稍后重试";


    public static boolean isSucc(JsonResult result) {
        if(result == null) {
            return false;
        }
        return Integer.parseInt(result.getStatus()) == 1;
    }
    public static boolean isFail(JsonResult result) {
        if(result == null) {
            return true;
        }
        return Integer.parseInt(result.getStatus()) != 1;
    }

    public static JsonResult succ() {
        JsonResult data = new JsonResult();
        data.setStatus(String.valueOf(SUCCESS));
        data.setResult(SUCCESS_MSG);
        return data;
    }
    public static JsonResult newSucc(Object result) {
        JsonResult data = new JsonResult();
        data.setStatus(String.valueOf(SUCCESS));
        data.setResult(result);
        return data;
    }

    public static JsonResult fail() {
        JsonResult data = new JsonResult();
        data.setStatus(String.valueOf(FAIL));
        data.setResult(FAIL_MSG);
        return data;
    }

    public static JsonResult newFail(String result) {
        JsonResult data = new JsonResult();
        data.setStatus(String.valueOf(FAIL));
        data.setResult(result);
        return data;
    }

    public static JsonResult newFail(int code, String result) {
        JsonResult data = new JsonResult();
        data.setStatus(String.valueOf(code));
        data.setResult(result);
        return data;
    }

    public static JsonResult exception() {
        JsonResult data = new JsonResult();
        data.setStatus(String.valueOf(EXCEPTION));
        data.setResult(EXCEPTION_MSG);
        return data;
    }

    public static JsonResult newException(String result) {
        JsonResult data = new JsonResult();
        data.setStatus(String.valueOf(EXCEPTION));
        data.setResult(result);
        return data;
    }
}
