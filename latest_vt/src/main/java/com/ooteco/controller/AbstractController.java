package com.ooteco.controller;

import com.alibaba.fastjson.JSON;
import com.ooteco.service.UploaderService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.ResultFactory;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;

abstract class AbstractController {

    protected String renderResult(JsonResult result) {
        return JSON.toJSONString(result);
    }

    protected JsonResult validator(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //只返回第一个错误信息
            return ResultFactory.newFail(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return ResultFactory.succ();
    }

    protected JsonResult validator(Errors errors) {
        if (errors.hasErrors()) {
            //只返回第一个错误信息
            return ResultFactory.newFail(errors.getAllErrors().get(0).getDefaultMessage());
        }
        return ResultFactory.succ();
    }

    protected JsonResult validImageFile(MultipartFile file) {
        //校验文件是否为空
        if(file.isEmpty()) {
            return ResultFactory.newFail("文件不能为空");
        }
        //校验文件类型
        UploaderService uploaderService = new UploaderService();
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        if(!uploaderService.validImageFileType(suffix)) {
            return ResultFactory.newFail("文件格式不正确");
        }
        //校验文件大小
        if(!uploaderService.validImageFileSize(file.getSize())) {
            return ResultFactory.newFail("文件大小超出限制");
        }
        return ResultFactory.succ();
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
