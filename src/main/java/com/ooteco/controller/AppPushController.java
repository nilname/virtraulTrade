package com.ooteco.controller;

import com.alibaba.fastjson.JSONObject;
import com.ooteco.service.JPushService;
import com.ooteco.service.UploaderService;
import com.ooteco.swagger.JsonResult;
import com.ooteco.utils.ResultFactory;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Created by shenyu on 2018/02/13.
 */
@Api(value = "/app/push", description = "app推送相关接口")
@RestController
@RequestMapping(value = "/app/push")
public class AppPushController extends AbstractController{

    private static Logger log = Logger.getLogger(AppPushController.class);


    @Autowired
    private JPushService jPushService;

    @ApiOperation(value = "推送消息", notes = "推送消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "string", paramType="query"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "string", paramType="query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = ResultFactory.SUCCESS, message = ResultFactory.SUCCESS_MSG) ,
            @ApiResponse(code = ResultFactory.FAIL, message = ResultFactory.FAIL_MSG)
    })
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String image(@RequestParam("title") String title, @RequestParam("content") String content){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);
        jsonObject.put("content", content);
         return renderResult(jPushService.pushAll(jsonObject));
    }
}
