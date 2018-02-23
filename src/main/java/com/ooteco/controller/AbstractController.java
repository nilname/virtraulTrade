package com.ooteco.controller;

import com.alibaba.fastjson.JSON;
import com.ooteco.swagger.JsonResult;

import java.io.UnsupportedEncodingException;

abstract class AbstractController {

    protected String renderResult(JsonResult result) {
        return JSON.toJSONString(result);
    }

    protected String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
